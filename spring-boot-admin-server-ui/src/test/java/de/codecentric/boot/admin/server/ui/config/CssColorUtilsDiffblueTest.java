package de.codecentric.boot.admin.server.ui.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CssColorUtils.class})
@ExtendWith(SpringExtension.class)
class CssColorUtilsDiffblueTest {
  @Autowired
  private CssColorUtils cssColorUtils;

  /**
   * Method under test: {@link CssColorUtils#hexToRgb(String)}
   */
  @Test
  void testHexToRgb() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> cssColorUtils.hexToRgb("0123456789ABCDEF"));
    assertEquals("153, 153, 153", cssColorUtils.hexToRgb("#999999"));
  }
}
