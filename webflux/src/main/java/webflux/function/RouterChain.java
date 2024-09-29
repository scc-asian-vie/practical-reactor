package webflux.function;

import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Because the route() method returns a RouterFunction, we can chain it to build
 * powerful and complex routing schemes.
 */
public class RouterChain {
  /* the `route` method is used to route a request to a handler function */
  public static <T extends ServerResponse> RouterFunction<T> route(RequestPredicate predicate, HandlerFunction<T> handler) {
    return request -> predicate.test(request) ? Mono.just(handler) : Mono.empty();
  }

  /* The `nest` method is used to test request to nest to another RouterFunction */
  public static <T extends ServerResponse> RouterFunction<T> nest(RequestPredicate predicate, RouterFunction<T> routerFunction) {
    return request -> predicate.test(request) ? routerFunction.route(request) : Mono.empty();
  }
  /* The `or` method is used to selective routing in case of empty return Mono.empty() */
  public static <T extends ServerResponse> RouterFunction<T> or(RouterFunction<T> route1, RouterFunction<T> route2) {
    return request -> route1.route(request).switchIfEmpty(route2.route(request));
  }
  /* The `and` method is used to chain two RouterFunctions into a single RouterFunction. */
  public static <T extends ServerResponse> RouterFunction<T> and(RouterFunction<T> route1, RouterFunction<T> route2) {
    return request -> route1.route(request).then(route2.route(request));
  }

}