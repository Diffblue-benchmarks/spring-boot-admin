package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class TagsDiffblueTest {
  /**
   * Method under test: {@link Tags#append(Tags)}
   */
  @Test
  public void testAppend() {
    // Arrange
    Tags emptyResult = Tags.empty();

    // Act and Assert
    assertTrue(emptyResult.append(Tags.empty()).getValues().isEmpty());
  }

  /**
   * Method under test: {@link Tags#append(Tags)}
   */
  @Test
  public void testAppend2() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));
    Tags fromResult = Tags.from(map, "Prefix");
    Tags other = Tags.empty();

    // Act and Assert
    assertEquals(other, fromResult.append(other));
  }

  /**
   * Method under test: {@link Tags#append(Tags)}
   */
  @Test
  public void testAppend3() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "42");
    map.computeIfPresent("foo", mock(BiFunction.class));
    Tags fromResult = Tags.from(map, "");

    // Act and Assert
    assertEquals(fromResult, fromResult.append(Tags.empty()));
  }

  /**
   * Method under test: {@link Tags#empty()}
   */
  @Test
  public void testEmpty() {
    // Arrange, Act and Assert
    assertTrue(Tags.empty().getValues().isEmpty());
  }

  /**
   * Method under test: {@link Tags#from(Map)}
   */
  @Test
  public void testFrom() {
    // Arrange, Act and Assert
    assertTrue(Tags.from(new HashMap<>()).getValues().isEmpty());
    assertTrue(Tags.from(new HashMap<>(), "Prefix").getValues().isEmpty());
  }

  /**
   * Method under test: {@link Tags#from(Map)}
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
    assertTrue(Tags.from(map).getValues().isEmpty());
  }

  /**
   * Method under test: {@link Tags#from(Map)}
   */
  @Test
  public void testFrom3() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act and Assert
    Map<String, String> values = Tags.from(map).getValues();
    assertEquals(1, values.size());
    assertEquals("42", values.get("foo"));
  }

  /**
   * Method under test: {@link Tags#from(Map)}
   */
  @Test
  public void testFrom4() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "42");
    map.put("foo", "42");

    // Act and Assert
    assertEquals(map, Tags.from(map).getValues());
  }

  /**
   * Method under test: {@link Tags#from(Map)}
   */
  @Test
  public void testFrom5() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));
    map.put("foo", "42");

    // Act and Assert
    Map<String, String> values = Tags.from(map).getValues();
    assertEquals(1, values.size());
    assertEquals("42", values.get("foo"));
  }

  /**
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  public void testFrom6() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act and Assert
    assertTrue(Tags.from(map, "Prefix").getValues().isEmpty());
  }

  /**
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  public void testFrom7() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "42");
    map.put("foo", "42");

    // Act and Assert
    assertTrue(Tags.from(map, "Prefix").getValues().isEmpty());
  }

  /**
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  public void testFrom8() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));
    map.put("foo", "42");

    // Act and Assert
    assertTrue(Tags.from(map, "Prefix").getValues().isEmpty());
  }

  /**
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  public void testFrom9() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put(null, "42");

    // Act and Assert
    assertTrue(Tags.from(map, "Prefix").getValues().isEmpty());
  }

  /**
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  public void testFrom10() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act and Assert
    Map<String, String> values = Tags.from(map, null).getValues();
    assertEquals(1, values.size());
    assertEquals("42", values.get("foo"));
  }

  /**
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  public void testFrom11() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "42");
    map.put("foo", "42");

    // Act and Assert
    assertEquals(map, Tags.from(map, null).getValues());
  }

  /**
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  public void testFrom12() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put(null, "42");

    // Act and Assert
    assertTrue(Tags.from(map, null).getValues().isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Tags#equals(Object)}
   *   <li>{@link Tags#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Tags emptyResult = Tags.empty();
    Tags emptyResult2 = Tags.empty();

    // Act and Assert
    assertEquals(emptyResult, emptyResult2);
    int expectedHashCodeResult = emptyResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Tags#equals(Object)}
   *   <li>{@link Tags#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));
    Tags fromResult = Tags.from(map, "Prefix");
    Tags emptyResult = Tags.empty();

    // Act and Assert
    assertEquals(fromResult, emptyResult);
    int expectedHashCodeResult = fromResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Tags#equals(Object)}
   *   <li>{@link Tags#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put(null, "42");
    map.computeIfPresent("foo", mock(BiFunction.class));
    Tags fromResult = Tags.from(map, "");
    Tags emptyResult = Tags.empty();

    // Act and Assert
    assertEquals(fromResult, emptyResult);
    int expectedHashCodeResult = fromResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Tags#equals(Object)}
   *   <li>{@link Tags#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Tags emptyResult = Tags.empty();

    // Act and Assert
    assertEquals(emptyResult, emptyResult);
    int expectedHashCodeResult = emptyResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult.hashCode());
  }

  /**
   * Method under test: {@link Tags#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Tags.empty(), 1);
  }

  /**
   * Method under test: {@link Tags#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("", "42");
    map.computeIfPresent("foo", mock(BiFunction.class));
    Tags fromResult = Tags.from(map, "");

    // Act and Assert
    assertNotEquals(fromResult, Tags.empty());
  }

  /**
   * Method under test: {@link Tags#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Tags.empty(), null);
  }

  /**
   * Method under test: {@link Tags#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Tags.empty(), "Different type to Tags");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Tags#toString()}
   *   <li>{@link Tags#getValues()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Tags emptyResult = Tags.empty();

    // Act
    String actualToStringResult = emptyResult.toString();

    // Assert
    assertEquals("Tags(values={})", actualToStringResult);
    assertTrue(emptyResult.getValues().isEmpty());
  }
}
