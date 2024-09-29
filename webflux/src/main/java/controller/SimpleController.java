package controller;

import model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;
import repository.EmployeeRepository;

/**
 * To begin with, on the server, we’ll create an annotated controller that
 * publishes a reactive stream of the Employee resource.
 */
@RestController
@RequestMapping("/employee")
public class SimpleController {
  private static final Logger log = Loggers.getLogger(SimpleController.class);
  private final EmployeeRepository employeeRepository;

  public SimpleController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  /**
   * We’ll wrap a single Employee resource in a Mono because we return at
   * most one employee
   */
  @GetMapping("/{id}")
  private Mono<Employee> getEmployeeById(@PathVariable String id) {
    return employeeRepository.findEmployeeById(id);
  }

  /**
   * For the collection resource, we’ll use a Flux of type Employee since that’s
   * the publisher for 0..n elements.
   */
  @GetMapping
  private Flux<Employee> getEmployee() {
    return employeeRepository.findEmployees();
  }
}
