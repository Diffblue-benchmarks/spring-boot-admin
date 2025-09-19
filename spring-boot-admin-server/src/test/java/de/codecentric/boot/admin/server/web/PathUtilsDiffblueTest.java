package de.codecentric.boot.admin.server.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class PathUtilsDiffblueTest {
  /**
   * Test {@link PathUtils#normalizePath(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link PathUtils#normalizePath(String)}
   */
  @Test
  @DisplayName("Test normalizePath(String); when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String PathUtils.normalizePath(String)"})
  void testNormalizePath_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(PathUtils.normalizePath(null));
  }

  /**
   * Test {@link PathUtils#normalizePath(String)}.
   *
   * <ul>
   *   <li>When {@code Path}.
   *   <li>Then return {@code /Path}.
   * </ul>
   *
   * <p>Method under test: {@link PathUtils#normalizePath(String)}
   */
  @Test
  @DisplayName("Test normalizePath(String); when 'Path'; then return '/Path'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String PathUtils.normalizePath(String)"})
  void testNormalizePath_whenPath_thenReturnPath() {
    // Arrange, Act and Assert
    assertEquals("/Path", PathUtils.normalizePath("Path"));
  }

  /**
   * Test {@link PathUtils#normalizePath(String)}.
   *
   * <ul>
   *   <li>When {@code ///}.
   *   <li>Then return {@code /}.
   * </ul>
   *
   * <p>Method under test: {@link PathUtils#normalizePath(String)}
   */
  @Test
  @DisplayName("Test normalizePath(String); when '///'; then return '/'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String PathUtils.normalizePath(String)"})
  void testNormalizePath_whenSlashSlashSlash_thenReturnSlash() {
    // Arrange, Act and Assert
    assertEquals("/", PathUtils.normalizePath("///"));
  }

  /**
   * Test {@link PathUtils#normalizePath(String)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link PathUtils#normalizePath(String)}
   */
  @Test
  @DisplayName("Test normalizePath(String); when '/'; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String PathUtils.normalizePath(String)"})
  void testNormalizePath_whenSlash_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", PathUtils.normalizePath("/"));
  }
}
