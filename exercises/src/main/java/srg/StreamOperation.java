package srg;

import reactor.core.publisher.Flux;

/**
 * * Notice that this also run on main thread only
 */
public class StreamOperation {
  private static final Flux<Integer> INTEGER = Flux.just(1,2,3,4,5);
  private static final Flux<String> STRING = Flux.just("2", "4", "8", "16", "32");

  public static void main(String[] args) {
    // Simple map to used to transform INTEGER Flux by multiply it by 2
    INTEGER.log().map(i -> i * 2).doOnNext(System.out::println).subscribe();

    // Using map to transform INTEGER Flux to power to 2 and Collect it to new Flux<String>
    Flux<String> str = INTEGER.log().map(i -> Math.pow(2,i)).map(String::valueOf);
    str.doOnNext(System.out::println).doOnComplete(()-> System.out.println("Completed")).subscribe();

    // Combine two Flux streams using Zip
    INTEGER.log().map(i -> i)
        .zipWith(STRING, (src,dst) -> String.format("First Flux: %d, Dest Flux: %s", src,dst))
        .doOnEach(System.out::println)
        .subscribe();
  }

}
