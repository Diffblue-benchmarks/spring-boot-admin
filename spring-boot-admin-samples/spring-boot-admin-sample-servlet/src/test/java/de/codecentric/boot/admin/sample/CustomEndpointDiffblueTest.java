package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomEndpoint.class})
@ExtendWith(SpringExtension.class)
class CustomEndpointDiffblueTest {
  @Autowired
  private CustomEndpoint customEndpoint;

  /**
   * Method under test: {@link CustomEndpoint#invoke()}
   */
  @Test
  void testInvoke() {
    // Arrange, Act and Assert
    assertEquals("Hello World!", customEndpoint.invoke());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link CustomEndpoint}
   */
  @Test
  void testNewCustomEndpoint() {
    // Arrange, Act and Assert
    assertEquals("Hello World!", (new CustomEndpoint()).invoke());
  }
}
