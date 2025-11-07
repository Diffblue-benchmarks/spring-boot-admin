package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class TagsMixinDiffblueTest {
  /**
   * Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  public void testFrom() {
    // Arrange, Act and Assert
    assertTrue(TagsMixin.from(new HashMap<>()).getValues().isEmpty());
  }

  /**
   * Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  public void testFrom2() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(null, new HashMap<>());

    HashMap<Object, Object> objectObjectMap2 = new HashMap<>();
    objectObjectMap2.put(null, objectObjectMap);

    HashMap<String, Object> map = new HashMap<>();
    map.put(null, objectObjectMap2);

    // Act and Assert
    assertTrue(TagsMixin.from(map).getValues().isEmpty());
  }

  /**
   * Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  public void testFrom3() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act and Assert
    Map<String, String> values = TagsMixin.from(map).getValues();
    assertEquals(1, values.size());
    assertEquals("42", values.get("foo"));
  }

  /**
   * Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  public void testFrom4() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "42");
    map.put("foo", "42");

    // Act and Assert
    assertEquals(map, TagsMixin.from(map).getValues());
  }

  /**
   * Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  public void testFrom5() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));
    map.put("foo", "42");

    // Act and Assert
    Map<String, String> values = TagsMixin.from(map).getValues();
    assertEquals(1, values.size());
    assertEquals("42", values.get("foo"));
  }
}
