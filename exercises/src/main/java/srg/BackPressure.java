package srg;


import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

/**
 * Backpressure is when a downstream can tell an upstream to send it less
 * data in order to prevent it from being overwhelmed.
 */
public class BackPressure {

  private static Flux<Integer> just = Flux.just(1,2,3,4,5,6,7,8,9,10,11);

  public static void main(String[] args) {
    //Tell the upstream only send two elements at a time by `request`
    just.log().subscribe(new Subscriber<Integer>() {
      Subscription s;
      final List<Integer> elements = new ArrayList<>();
      int onNextAmount;

      @Override
      public void onSubscribe(Subscription subscription) {
        this.s = subscription;

        // Case1: sequence of request
        // s.request(1L);

        // Case2: Deal with Backpressure
        s.request(2L);
      }

      /**
       * this is reactive pull backpressure. We’re requesting the upstream
       * to only push a certain amount of elements, and only when we’re ready.
       * -
       * If we imagine we’re being streamed tweets from Twitter, it would then be up
       * to the upstream to decide what to do. If tweets were coming in, but there
       * were no requests from the downstream, then the upstream could drop
       * items, store them in a buffer, or use some other strategy.
       */
      @Override
      public void onNext(Integer integer) {
        elements.add(integer);
        onNextAmount++;

        // Case1: sequence of request
        //s.request(1L);

        // Case2: deal with backpressure and only fetch next 2 item after that
        if (onNextAmount % 2 == 0) {
          s.request(2L);
        }
      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onComplete() {
        System.out.printf("Completed %s", elements.toString());
      }
    });
  }

}
