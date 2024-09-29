package webflux.controller;

import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.WebFlux;
import webflux.model.Employee;
import webflux.service.EmployeeService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes= WebFlux.class)
public class SimpleControllerIntegrationTest {
  @Autowired
  WebTestClient client;

  @MockBean
  private EmployeeService employeeService;

  Employee employee;

  @BeforeEach
  void setup() {
    employee = Employee.builder()
        .id(BigDecimal.ONE)
        .firstName("firstname")
        .lastName("lastname")
        .email("firstname@email.com")
        .build();

  }

  @Test
  @WithMockUser(username = "admin", roles = { "ADMIN" })
  void test_getEmployeeById_then_correct_employee() {
    // Given
    given(employeeService.findById("1")).willReturn(Mono.just(employee));
    // When & Then
    client.get()
        .uri("/employee/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody(Employee.class).isEqualTo(employee);
  }

  @Test
  @WithMockUser(username = "admin", roles = { "ADMIN" })
  void test_getEmployees_then_correct_employees() {
    // Given
    List<Employee> employees = Arrays.asList(
        new Employee(BigDecimal.ONE, "John", "London", "john@mail.com"),
        new Employee(BigDecimal.valueOf(2), "Smith", "Paris", "smith@email.com")
    );
    Flux<Employee> employeesFlux = Flux.fromIterable(employees);
    // When & Then
    given(employeeService.findAll()).willReturn(employeesFlux);
    client.get()
        .uri("/employee")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Employee.class).isEqualTo(employees);
  }


}
