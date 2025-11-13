package de.codecentric.boot.admin.server.ui.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

class UiExtensionsScannerDiffblueTest {
  /**
   * Test {@link UiExtensionsScanner#scan(String[])}.
   *
   * <ul>
   *   <li>Then return {@link UiExtensions#EMPTY}.
   * </ul>
   *
   * <p>Method under test: {@link UiExtensionsScanner#scan(String[])}
   */
  @Test
  @DisplayName("Test scan(String[]); then return EMPTY")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"UiExtensions UiExtensionsScanner.scan(String[])"})
  void testScan_thenReturnEmpty() throws IOException {
    // Arrange, Act and Assert
    assertEquals(
        UiExtensions.EMPTY,
        new UiExtensionsScanner(new PathMatchingResourcePatternResolver()).scan("Locations"));
  }
}
