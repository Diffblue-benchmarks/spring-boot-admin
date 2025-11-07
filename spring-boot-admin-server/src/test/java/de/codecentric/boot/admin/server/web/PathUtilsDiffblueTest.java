package de.codecentric.boot.admin.server.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class PathUtilsDiffblueTest {
  /**
   * Method under test: {@link PathUtils#normalizePath(String)}
   */
  @Test
  public void testNormalizePath() {
    // Arrange, Act and Assert
    assertEquals("/Path", PathUtils.normalizePath("Path"));
    assertEquals("", PathUtils.normalizePath("/"));
    assertNull(PathUtils.normalizePath(null));
    assertEquals("/", PathUtils.normalizePath("///"));
  }
}
