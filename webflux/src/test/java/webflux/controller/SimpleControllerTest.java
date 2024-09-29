package webflux.controller;


import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import webflux.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import webflux.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class SimpleControllerTest {

  @InjectMocks
  private SimpleController simpleController;

  @Mock
  EmployeeService employeeService;

  @DisplayName("Given a SimpleController")
  @Nested
  public class TestEmployee {

    private final WebClient client = WebClient.create("http://localhost:8080");
    private Employee employee;
    @BeforeEach
    public void setUp() {
      employee = new Employee(BigDecimal.ONE, "John", "Doe", "John@email.com");
    }

    @Test
    void testGetEmployeeById() {
      // Given

      // When
      doReturn(Mono.just(employee)).when(employeeService).findById("1");
      Mono<Employee> employeeMono = simpleController.getEmployeeById("1");
      //      Mono<Employee> employeeMono = client.get()
      //          .uri("/employee/1")
      //          .retrieve()
      //          .bodyToMono(Employee.class);

      // Then
      StepVerifier.create(employeeMono).expectNext(employee).verifyComplete();

    }

    @Test
    void testGetEmployees() {
      // Given

      // When
      doReturn(Flux.just(employee)).when(employeeService).findAll();
      Flux<Employee> employeeFlux = simpleController.getEmployee();

      // Then
      StepVerifier.create(employeeFlux).expectNext(employee).verifyComplete();
    }

  }

}