package webflux.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.model.Employee;
import webflux.repository.EmployeeRepository;

@Service
public class EmployeeService {
  final EmployeeRepository repository;

  @Autowired
  public EmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }

  public Mono<Employee> findById(String id) {
    return repository.findById(id);
  }

  public Flux<Employee> findAll() {
    return repository.findAll();
  }

  public void updateEmployee(Employee employee) {
    Objects.requireNonNull(employee, "employee is Null");
    repository.save(Objects.requireNonNull(employee));
  }



}