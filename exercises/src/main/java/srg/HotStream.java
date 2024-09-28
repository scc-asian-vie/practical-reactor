package srg;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

/**
 * These are static, fixed-Length streams that are easy to deal with.
 * A more realistic use case for reactive might be something that happens infinitely.
 * For example, we could have a stream of mouse movements that constantly
 * needs to be reacted to, or a Twitter feed. These types of streams are called
 * hot streams, as they are always running and can be subscribed to at any
 * point in time, missing the start of the data.
 * -
 * Notice that this also run on main thread only
 */
@SuppressWarnings("InfiniteLoopStatement")
public class HotStream {
  /**
   * Create a Connectable Flux
   * -
   * we’re given a ConnectableFlux. This means that calling
   * subscribe() won’t cause it to start emitting, allowing us to add multiple
   * subscriptions:
   */
  public static final ConnectableFlux<Object> publisher = Flux.create(fluxSink ->{
    while(true) {
        fluxSink.next(System.currentTimeMillis());
    }
  }).publish();

  public static void main(String[] args) throws InterruptedException {
    publisher.log().subscribe(System.out::println);
    publisher.log().subscribe(System.out::println);


    publisher.connect();
  }
}
