package utils;

import lombok.Getter;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient, introduced in Spring 5, is a non-blocking client with support for
 * reactive streams.
 * We can use WebClient to create a client to retrieve data from the endpoints
 * provided by the EmployeeController.
 */
@Getter
public class SimpleWebClient {
  private final WebClient webClient;

  /* Can't be instantiated directly */
  private SimpleWebClient(String baseUrl) {
    this.webClient = WebClient.create(baseUrl);
  }

  /* Get the WebClient instance to local development */
  public static SimpleWebClient create() {
    return new SimpleWebClient("http://localhost:8080");
  }

  /* Get the WebClient instance to the specified base URL */
  public static SimpleWebClient create(String baseUrl) {
    return new SimpleWebClient(baseUrl);
  }

}
