package srg.debug;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.util.LoggerUtils;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * Sometimes, we can add useful context information by providing a
 * Consumer as a second parameter of the subscribe method
 * -
 * Note: It’s worth mentioning that if we don’t need to carry out further
 * processing on the subscribe method, we can chain the doOnError
 * function on our publisher
 */
public class DebugProcess {
  private static final Logger logger = Loggers.getLogger(DebugProcess.class);
  private static final Flux<String> STRING = Flux.just(1,2,3,4,5,6,7,8).map(String::valueOf);

  public static void main(String[] args) throws InterruptedException {
    logger.info("Do the debugging");
    // do err handler on subscribe
    STRING.subscribeOn(Schedulers.parallel()).subscribe(
        num -> {
          logger.info("Finished process num : {}", num);
        },err -> {
          logger.error("The following error happened on processing method by {}",err);
        }
    );

    // do err handler on Publisher
    STRING.doOnError(err ->
      logger.error("The following error happened on processing method by {}",err)
    ).subscribe();

  }
}
