package webflux.controller;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static webflux.function.RouterChain.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.util.Logger;
import reactor.util.Loggers;
import webflux.function.RouterFunction;
import webflux.model.Employee;
import webflux.service.EmployeeService;

/**
 * Now we’ll implement the same logic using router and handler functions.
 * First, we’ll need to create routes using RouterFunction to publish and
 * consume our reactive streams of Employees.
 * -
 * Routes are registered as Spring beans, and can be created inside any
 * configuration class.
 */
@Configuration
public class SimpleReactiveRest {
  private static final Logger log = Loggers.getLogger(SimpleReactiveRest.class);
  private final EmployeeService employeeService;

  @Autowired
  public SimpleReactiveRest(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  /**
   * Single Employee resource
   * Let’s create our first route using RouterFunction that publishes a single
   * Employee resource.
   * -
   * The first argument is a request predicate. Notice how we used a statically
   * imported RequestPredicates.GET method here. The second parameter
   * defines a handler function that will be used if the predicate applies.
   * -
   * In other words, the above example routes all the GET requests for the /
   * employees/{id} to employeeService.findById(id).
   */
  @Bean
  public RouterFunction<ServerResponse> getEmployeeByIdRoute() {
    return route(GET("/employees/{id}"), req -> {
      log.info("Request: {} for EmployeeId({})",req, req.pathVariable("id"));
      String id = req.pathVariable("id");
      return ok().body(employeeService.findById(id), Employee.class);
    });
  }

  /**
   * for publishing a collection resource, we’ll add another route
   */
  @Bean
  public RouterFunction<ServerResponse> getAllEmployeeRoute() {
    return route(GET("/employees"),
        req -> ok().body(employeeService.findAll(), Employee.class)
        );
  }

  /**
   *
   */
  @Bean
  public RouterFunction<ServerResponse> updateEmployeeRoute() {
    return route(PUT("/employees"),
        req -> req.body(toMono(Employee.class))
            .doOnNext(employeeService::updateEmployee)
            .then(ok().build())
        );
  }

  //  @Bean
  //  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
  //    http.csrf().disable()
  //        .authorizeExchange(
  //            exchanges -> exchanges.anyExchange().permitAll()
  //        );
  //    return http.build();
  //  }
}
