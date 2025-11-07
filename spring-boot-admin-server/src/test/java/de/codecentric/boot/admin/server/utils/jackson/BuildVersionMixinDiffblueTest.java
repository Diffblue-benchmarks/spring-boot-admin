package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import de.codecentric.boot.admin.server.domain.values.BuildVersion;
import org.junit.Test;

public class BuildVersionMixinDiffblueTest {
  /**
   * Method under test: {@link BuildVersionMixin#valueOf(String)}
   */
  @Test
  public void testValueOf() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersionMixin.valueOf("foo");

    // Assert
    assertEquals("foo", actualValueOfResult.getValue());
    assertEquals("foo", actualValueOfResult.toString());
  }

  /**
   * Method under test: {@link BuildVersionMixin#valueOf(String)}
   */
  @Test
  public void testValueOf2() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersionMixin.valueOf(null);

    // Assert
    assertEquals("UNKNOWN", actualValueOfResult.getValue());
    assertEquals("UNKNOWN", actualValueOfResult.toString());
  }
}
