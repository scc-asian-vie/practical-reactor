package srg;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Notice:
 * The core difference is that Reactive is a push model, whereas the Java 8
 * Streams are a pull model. In a reactive approach, events are pushed to the
 * subscribers as they come in.
 * The data won’t start flowing until we subscribe
 * -
 * Notice that this also run on main thread only
 */
public class ProductStaticStreamData {
  // Simple static Flux
  private static final Flux<Integer> just = Flux.just(1,2,3,4,5);
  // Simple static Mono
  private static final Mono<Integer> mono = Mono.just(1);

  // Publisher of Flux
  Publisher<Integer> justP = Flux.just(1,2,3,4,5);
  Publisher<Integer> monoP = Mono.just(1);

  public static void main(String[] args) {
    // Ex1: Subscriber to Flux & Mono
    just.log().subscribe(System.out::println);
    mono.log().subscribe(System.out::println);
    // Ex2: Synchronously by block to get fist
    just.log().blockFirst();
    mono.log().block();
    // Ex3: Provide Subscriber interface directly to static Flux
    just.log().subscribe(new Subscriber<Integer>() {
      private Subscription s;
      private Integer sum = 0;
      @Override
      public void onSubscribe(Subscription subscription) {
        // subscription as much as possible by `request`
        s = subscription;
        s.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(Integer integer) {
        sum += integer;
      }

      @Override
      public void onError(Throwable throwable) {
        throw new RuntimeException(throwable);
      }

      @Override
      public void onComplete() {
        System.out.printf("Completed with sum: %s", sum);
      }
    });
  }

}
