package de.codecentric.boot.admin.server.ui.extensions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

class UiRoutesScannerDiffblueTest {
  /**
   * Method under test: {@link UiRoutesScanner#scan(String[])}
   */
  @Test
  void testScan() throws IOException {
    // Arrange, Act and Assert
    assertTrue((new UiRoutesScanner(new PathMatchingResourcePatternResolver())).scan("Locations").isEmpty());
  }
}
