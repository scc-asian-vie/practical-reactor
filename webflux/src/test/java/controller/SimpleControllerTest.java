package controller;


import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import repository.EmployeeRepository;

class SimpleControllerTest {

  @InjectMocks
  private SimpleController simpleController;

  @Mock
  EmployeeRepository employeeRepository;

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
    public void testGetEmployeeById() {
      // Given

      // When
      when(employeeRepository.findById("1")).thenReturn(Mono.just(employee));
      Mono<Employee> employeeMono = client.get()
          .uri("/employee/1")
          .retrieve()
          .bodyToMono(Employee.class);

      // Then
      employeeMono.subscribe(System.out::println);
    }

  }

}