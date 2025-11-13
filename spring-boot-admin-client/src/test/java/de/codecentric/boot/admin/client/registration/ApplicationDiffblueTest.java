package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ApplicationDiffblueTest {
  /**
   * Test {@link Application#Application(String, String, String, String, Map)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then return {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link Application#Application(String, String, String, String, Map)}
   */
  @Test
  @DisplayName(
      "Test new Application(String, String, String, String, Map); when 'Name'; then return 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Application.<init>(String, String, String, String, Map)"})
  void testNewApplication_whenName_thenReturnName() {
    // Arrange and Act
    Application actualApplication =
        new Application(
            "Name",
            "https://example.org/example",
            "https://example.org/example",
            "https://example.org/example",
            new HashMap<>());

    // Assert
    assertEquals("Name", actualApplication.getName());
    assertEquals("https://example.org/example", actualApplication.getHealthUrl());
    assertEquals("https://example.org/example", actualApplication.getManagementUrl());
    assertEquals("https://example.org/example", actualApplication.getServiceUrl());
    assertTrue(actualApplication.getMetadata().isEmpty());
  }

  /**
   * Test {@link Application#getMetadata()}.
   *
   * <p>Method under test: {@link Application#getMetadata()}
   */
  @Test
  @DisplayName("Test getMetadata()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map Application.getMetadata()"})
  void testGetMetadata() {
    // Arrange
    Application application =
        new Application(
            "Name",
            "https://example.org/example",
            "https://example.org/example",
            "https://example.org/example",
            new HashMap<>());

    // Act and Assert
    assertTrue(application.getMetadata().isEmpty());
  }
}
