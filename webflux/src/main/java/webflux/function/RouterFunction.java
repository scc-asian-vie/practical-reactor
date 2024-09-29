package webflux.function;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * RouterFunction serves as an alternative to the @RequestMapping annotation.
 * We can use it to route requests to the handler functions:
 */
@FunctionalInterface
public interface RouterFunction<T extends ServerResponse> {
  Mono<HandlerFunction<T>> route(ServerRequest request);
}