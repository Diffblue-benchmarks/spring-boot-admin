package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

class SpringBootAdminWarApplicationDiffblueTest {
  /**
   * Test {@link SpringBootAdminWarApplication#configure(SpringApplicationBuilder)}.
   * <p>
   * Method under test: {@link SpringBootAdminWarApplication#configure(SpringApplicationBuilder)}
   */
  @Test
  @DisplayName("Test configure(SpringApplicationBuilder)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"SpringApplicationBuilder SpringBootAdminWarApplication.configure(SpringApplicationBuilder)"})
  void testConfigure() {
    // Arrange
    SpringBootAdminWarApplication springBootAdminWarApplication = new SpringBootAdminWarApplication();
    Class<Object> forNameResult = Object.class;
    SpringApplicationBuilder application = new SpringApplicationBuilder(forNameResult);

    // Act and Assert
    assertSame(application, springBootAdminWarApplication.configure(application));
  }
}
