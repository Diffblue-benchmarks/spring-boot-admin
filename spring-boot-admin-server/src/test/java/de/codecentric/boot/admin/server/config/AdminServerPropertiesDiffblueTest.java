package de.codecentric.boot.admin.server.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AdminServerPropertiesDiffblueTest {
  /**
   * Test {@link AdminServerProperties#setContextPath(String)}.
   *
   * <ul>
   *   <li>Then {@link AdminServerProperties} (default constructor) ContextPath is {@code /Context
   *       Path}.
   * </ul>
   *
   * <p>Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  @DisplayName(
      "Test setContextPath(String); then AdminServerProperties (default constructor) ContextPath is '/Context Path'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AdminServerProperties.setContextPath(String)"})
  void testSetContextPath_thenAdminServerPropertiesContextPathIsContextPath() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath("Context Path");

    // Assert
    assertEquals("/Context Path", adminServerProperties.getContextPath());
  }

  /**
   * Test {@link AdminServerProperties#setContextPath(String)}.
   *
   * <ul>
   *   <li>Then {@link AdminServerProperties} (default constructor) ContextPath is {@code /}.
   * </ul>
   *
   * <p>Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  @DisplayName(
      "Test setContextPath(String); then AdminServerProperties (default constructor) ContextPath is '/'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AdminServerProperties.setContextPath(String)"})
  void testSetContextPath_thenAdminServerPropertiesContextPathIsSlash() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath("///");

    // Assert
    assertEquals("/", adminServerProperties.getContextPath());
  }

  /**
   * Test {@link AdminServerProperties#setContextPath(String)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then {@link AdminServerProperties} (default constructor) ContextPath is empty string.
   * </ul>
   *
   * <p>Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  @DisplayName(
      "Test setContextPath(String); when '/'; then AdminServerProperties (default constructor) ContextPath is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AdminServerProperties.setContextPath(String)"})
  void testSetContextPath_whenSlash_thenAdminServerPropertiesContextPathIsEmptyString() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath("/");

    // Assert that nothing has changed
    assertEquals("", adminServerProperties.getContextPath());
  }

  /**
   * Test {@link AdminServerProperties#setContextPath(String)}.
   *
   * <ul>
   *   <li>When space.
   *   <li>Then {@link AdminServerProperties} (default constructor) ContextPath is space.
   * </ul>
   *
   * <p>Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  @DisplayName(
      "Test setContextPath(String); when space; then AdminServerProperties (default constructor) ContextPath is space")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AdminServerProperties.setContextPath(String)"})
  void testSetContextPath_whenSpace_thenAdminServerPropertiesContextPathIsSpace() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath(" ");

    // Assert
    assertEquals(" ", adminServerProperties.getContextPath());
  }

  /**
   * Test {@link AdminServerProperties#path(String)}.
   *
   * <p>Method under test: {@link AdminServerProperties#path(String)}
   */
  @Test
  @DisplayName("Test path(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String AdminServerProperties.path(String)"})
  void testPath() {
    // Arrange, Act and Assert
    assertEquals("Path", new AdminServerProperties().path("Path"));
  }
}
