package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

class SpringBootAdminWarApplicationDiffblueTest {
  /**
   * Method under test:
   * {@link SpringBootAdminWarApplication#configure(SpringApplicationBuilder)}
   */
  @Test
  void testConfigure() {
    // Arrange
    SpringBootAdminWarApplication springBootAdminWarApplication = new SpringBootAdminWarApplication();
    Class<Object> forNameResult = Object.class;
    SpringApplicationBuilder application = new SpringApplicationBuilder(forNameResult);

    // Act and Assert
    assertSame(application, springBootAdminWarApplication.configure(application));
  }
}
