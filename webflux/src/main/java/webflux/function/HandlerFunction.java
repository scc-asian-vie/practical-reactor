package webflux.function;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * The functional web framework introduces a new programming model
 * where we use functions to route and handle requests.
 * As opposed to the annotation-based model where we use annotation
 * mappings, here we’ll use HandlerFunction and RouterFunctions.
 * -
 * The HandlerFunction represents a function that generates responses for
 * requests routed to them
 */
@FunctionalInterface
public interface HandlerFunction<T extends ServerResponse> {
  Mono<T> handle(ServerRequest request);

}
