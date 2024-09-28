package srg.debug;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * The Reactor library provides a Hooks class that lets us configure the behavior
 * of Flux and Mono operators.
 * By simply adding the following statement, our application will instrument
 * the calls to the publishers’ methods, wrap the construction of the operator,
 * and capture a stack trace:
 * Hooks.onOperatorDebug();
 */
public class DebugProcessMultiThread {
  private static final Logger logger = Loggers.getLogger(DebugProcess.class);
  private static final Flux<String> STRING = Flux.just("Peter","Hulu","4","Odin","Thor","Titan");
  private static final Scheduler sche1 = new Schedulers2("thread-1").getScheduler();
  private static final Scheduler sche2 = new Schedulers2("thread-2").getScheduler();
  private static final Scheduler sche3 = new Schedulers2("thread-start").getScheduler();
  static String concatName(String src) {
    return src + "-Agent";
  }

  static String subString(String src) {
    return src.substring(9);
  }
  static void reportResult(String src) {
    logger.info("Result is: {}", src);
  }

  public static void main(String[] args) throws InterruptedException {
    logger.info("Do the debugging");
    // do err handler on Publisher
    STRING.publishOn(sche1)
        .map(DebugProcessMultiThread::concatName)
        .publishOn(sche2)
        .map(DebugProcessMultiThread::subString)
        .publishOn(sche3)
        .doOnError(err -> {
            logger.error("The following error happened on processing method by {}",err);
            sche1.dispose();
            sche2.dispose();
            sche3.dispose();
        })
        .subscribe();
  }
}


class Schedulers2 implements Disposable {
  private final Scheduler scheduler;

  public Schedulers2(String name) {
    this.scheduler = Schedulers.newSingle(name);
  }

  public Scheduler getScheduler() {
    return scheduler;
  }

  @Override
  public void dispose() {
    scheduler.dispose();
  }
}