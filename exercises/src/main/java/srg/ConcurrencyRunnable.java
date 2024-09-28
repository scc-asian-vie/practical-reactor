package srg;

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
public class ConcurrencyRunnable {
  private static final ExecutorService executor = Executors.newFixedThreadPool(4);
  private static final Scheduler scheduler = Schedulers.fromExecutor(executor);
  private static final Flux<Integer> CON = Flux.just(1,2,3,4,5,6,7,8);

  static Runnable runner = () -> {
    System.out.println(Thread.currentThread().getName());

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
    //elements.forEach(i -> System.out.printf("Run on %s: %d\n", Thread.currentThread().getName(),i));

    elements.forEach(i -> applyFunc(i,
        System.out::println
    ));

    /* Scheduler no more be used */
    scheduler.dispose();
  }

  static void applyFunc(Integer item, Transformer<Integer> transformer) {
    System.out.println(Thread.currentThread().getName());
    transformer.transform(item);
  }
}

interface Transformer<T> {
  void transform(T i);
}
