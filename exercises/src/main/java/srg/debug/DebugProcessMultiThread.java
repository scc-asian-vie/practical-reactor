package srg.debug;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * Executing Operators on Different Threads
 * other aspect to keep in mind is that the assembly trace is generated
 * properly even if there are different threads operating on the stream.
 */
public class DebugProcessMultiThread {
  private static final Logger logger = Loggers.getLogger(DebugProcess.class);
  private static final Flux<String> STRING = Flux.just("Peter","Hulu","4","Odin","Thor","Titan");
  private static final Scheduler s1 = new Schedulers2("thread-1").getScheduler();
  private static final Scheduler s2 = new Schedulers2("thread-2").getScheduler();
  private static final Scheduler s3 = new Schedulers2("thread-start").getScheduler();
  static String concatName(String src) {
    return src + "-Agent";
  }

  static String subString(String src) {
    return src.substring(9);
  }
  static void reportResult(String src) {
    logger.info("Result is: {}", src);
  }

  /**
   * if we check the logs, we’ll appreciate that in this case, the first section
   * might change a little bit, but the last two remain fairly the same.
   * The first part is the thread stack trace; therefore, it’ll show only the
   * operations carried out by a particular thread.
   */
  public static void main(String[] args) throws InterruptedException {
    logger.info("Do the debugging");
    // do err handler on Publisher
    STRING.publishOn(s1)
        .map(DebugProcessMultiThread::concatName)
        .publishOn(s2)
        .map(DebugProcessMultiThread::subString)
        .publishOn(s3)
        .doOnError(err -> {
            logger.error("The following error happened on processing method by {}",err);
            s1.dispose();
            s2.dispose();
            s3.dispose();
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