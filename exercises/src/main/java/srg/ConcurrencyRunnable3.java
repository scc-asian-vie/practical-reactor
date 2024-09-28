package srg;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * All of our previous examples have currently run on the main thread. However,
 * we can control which thread our code runs on if we want. The Scheduler
 * interface provides an abstraction around asynchronous code, for which
 * many implementations are provided for us. Let’s try subscribing to a different
 * thread than main:
 */
public class ConcurrencyRunnable3 {
  private static final ExecutorService executor = Executors.newFixedThreadPool(4);
  private static final Scheduler scheduler = Schedulers.fromExecutor(executor);
  private static final Flux<Integer> CON = Flux.just(1,2,3,4,5,6,7,8);

  static Runnable runner = () -> {
    System.out.printf("%s - Runner: %s\n", Instant.now(), Thread.currentThread().getName());

  };

  public static void main(String[] args) throws InterruptedException {
    List<Integer> elements = new ArrayList<>();

    /* this run on Pool-1/Thread-1 */
    CON.log().map(i -> i*i)
        .subscribeOn(scheduler)
        .subscribe(elements::add);

    /* This is need when data on subscribing can't available the same time for using */
    Thread.sleep(100);

    /* This run on main thread */
    elements.stream().map(i -> applyFunc(i,
        String::valueOf
    )).forEach(item -> {
      scheduler.schedule(runner);
      System.out.printf("%s - %s : %s\n",Instant.now(), Thread.currentThread().getName(), item);
    });

    /* Scheduler no more be used */
    scheduler.dispose();
  }

  static String applyFunc(Integer item, Transformer3<Integer, String> transformer) {
    return transformer.transform(item);
  }
}

interface Transformer3<T,V> {
  V transform(T i);
}
