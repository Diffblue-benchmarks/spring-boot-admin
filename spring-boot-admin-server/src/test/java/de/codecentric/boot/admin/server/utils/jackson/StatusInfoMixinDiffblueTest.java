package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class StatusInfoMixinDiffblueTest {
  /**
   * Method under test: {@link StatusInfoMixin#valueOf(String, Map)}
   */
  @Test
  public void testValueOf() {
    // Arrange and Act
    StatusInfo actualValueOfResult = StatusInfoMixin.valueOf("Status Code", new HashMap<>());

    // Assert
    assertEquals("STATUS CODE", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }

  /**
   * Method under test: {@link StatusInfoMixin#valueOf(String, Map)}
   */
  @Test
  public void testValueOf2() {
    // Arrange and Act
    StatusInfo actualValueOfResult = StatusInfoMixin.valueOf("Status Code", null);

    // Assert
    assertEquals("STATUS CODE", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }

  /**
   * Method under test: {@link StatusInfoMixin#valueOf(String, Map)}
   */
  @Test
  public void testValueOf3() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent("'status' must not be empty.", mock(BiFunction.class));

    // Act
    StatusInfo actualValueOfResult = StatusInfoMixin.valueOf("Status Code", details);

    // Assert
    assertEquals("STATUS CODE", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }
}
