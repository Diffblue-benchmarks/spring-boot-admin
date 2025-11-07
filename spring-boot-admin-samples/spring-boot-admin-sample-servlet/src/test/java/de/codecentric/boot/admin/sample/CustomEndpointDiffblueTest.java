package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link CustomEndpoint#invoke()}.
   * <p>
   * Method under test: {@link CustomEndpoint#invoke()}
   */
  @Test
  @DisplayName("Test invoke()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String CustomEndpoint.invoke()"})
  void testInvoke() {
    // Arrange, Act and Assert
    assertEquals("Hello World!", customEndpoint.invoke());
  }

  /**
   * Test new {@link CustomEndpoint} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CustomEndpoint}
   */
  @Test
  @DisplayName("Test new CustomEndpoint (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CustomEndpoint.<init>()"})
  void testNewCustomEndpoint() {
    // Arrange, Act and Assert
    assertEquals("Hello World!", (new CustomEndpoint()).invoke());
  }
}
