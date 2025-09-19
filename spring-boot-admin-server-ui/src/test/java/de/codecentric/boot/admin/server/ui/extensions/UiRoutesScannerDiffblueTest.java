package de.codecentric.boot.admin.server.ui.extensions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

class UiRoutesScannerDiffblueTest {
  /**
   * Test {@link UiRoutesScanner#scan(String[])}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UiRoutesScanner#scan(String[])}
   */
  @Test
  @DisplayName("Test scan(String[]); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List UiRoutesScanner.scan(String[])"})
  void testScan_thenReturnEmpty() throws IOException {
    // Arrange, Act and Assert
    assertTrue(
        new UiRoutesScanner(new PathMatchingResourcePatternResolver()).scan("Locations").isEmpty());
  }
}
