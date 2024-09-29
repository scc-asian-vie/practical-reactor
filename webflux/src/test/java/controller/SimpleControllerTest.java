package controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import repository.EmployeeRepository;

class SimpleControllerTest {

  @InjectMocks
  private SimpleController simpleController;

  @Mock
  EmployeeRepository employeeRepository;

  @DisplayName("Given a SimpleController")
  @Nested
  public class TestEmployee {

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testGetEmployeeById() {

    }

  }

}