package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class InfoMixinDiffblueTest {
  /**
   * Method under test: {@link InfoMixin#from(Map)}
   */
  @Test
  public void testFrom() {
    // Arrange, Act and Assert
    assertTrue(InfoMixin.from(new HashMap<>()).getValues().isEmpty());
    assertTrue(InfoMixin.from(null).getValues().isEmpty());
  }

  /**
   * Method under test: {@link InfoMixin#from(Map)}
   */
  @Test
  public void testFrom2() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.put("foo", "42");

    // Act and Assert
    Map<String, Object> values2 = InfoMixin.from(values).getValues();
    assertEquals(1, values2.size());
    assertEquals("42", values2.get("foo"));
  }

  /**
   * Method under test: {@link InfoMixin#from(Map)}
   */
  @Test
  public void testFrom3() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.computeIfPresent("foo", mock(BiFunction.class));
    values.put("foo", "42");

    // Act and Assert
    Map<String, Object> values2 = InfoMixin.from(values).getValues();
    assertEquals(1, values2.size());
    assertEquals("42", values2.get("foo"));
  }
}
