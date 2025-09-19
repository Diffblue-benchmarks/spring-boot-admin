package de.codecentric.boot.admin.server.domain.values;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RegistrationDiffblueTest {
  /**
   * Test {@link Registration#getMetadata()}.
   *
   * <p>Method under test: {@link Registration#getMetadata()}
   */
  @Test
  @DisplayName("Test getMetadata()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Map Registration.getMetadata()"})
  void testGetMetadata() {
    // Arrange, Act and Assert
    assertTrue(
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build()
            .getMetadata()
            .isEmpty());
  }
}
