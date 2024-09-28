package srg.debug;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * Activating the Debug Output on a Single Process
 */
public class DebugOutputSingleProcess {

  private static final Logger logger = Loggers.getLogger(DebugProcess.class);

  private static final Flux<String> STRING = Flux.just("Peter","Hulu","4","Odin","Thor","Titan");

  private static final Scheduler s1 = new ScheduleUtil("thread-1").getScheduler();

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
   * we should implement the former approach only in critical cases.
   * Reactor also provides a way to enable the debug mode on single crucial
   * processes, which is less memory-consuming.
   */
  public static void main(String[] args) throws InterruptedException {
    logger.info("Do the debugging");
    // do err handler on Publisher
    STRING.publishOn(s1)
        .map(DebugProcessMultiThread::concatName)
        .map(DebugProcessMultiThread::subString)
        .doOnError(err -> {
          s1.dispose();
        })
        .checkpoint("Observed error on process {}",true)
        .subscribe();
  }
}
