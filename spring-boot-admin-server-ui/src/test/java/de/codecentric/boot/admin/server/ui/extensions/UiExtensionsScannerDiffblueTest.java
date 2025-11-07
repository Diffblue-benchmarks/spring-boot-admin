package de.codecentric.boot.admin.server.ui.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

class UiExtensionsScannerDiffblueTest {
  /**
   * Method under test: {@link UiExtensionsScanner#scan(String[])}
   */
  @Test
  void testScan() throws IOException {
    // Arrange and Act
    UiExtensions actualScanResult = (new UiExtensionsScanner(new PathMatchingResourcePatternResolver()))
        .scan("Locations");

    // Assert
    assertEquals(actualScanResult.EMPTY, actualScanResult);
  }
}
