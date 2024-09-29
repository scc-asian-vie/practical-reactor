package webflux.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webflux.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class SimpleReactiveRestTest {

  @InjectMocks
  SimpleReactiveRest controller;

  @Mock
  EmployeeService employeeService;

  @BeforeEach
  void setup() {

  }

  @Test
  void getEmployeeByIdRoute() {
  }

  @Test
  void getAllEmployeeRoute() {
  }

  @Test
  void updateEmployeeRoute() {
  }

  @Test
  void springSecurityFilterChain() {
  }
}