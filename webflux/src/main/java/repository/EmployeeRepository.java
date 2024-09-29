package repository;

import model.Employee;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EmployeeRepository  {

  Mono<Employee> findEmployeeById(String id);

  Flux<Employee> findEmployees();
}
