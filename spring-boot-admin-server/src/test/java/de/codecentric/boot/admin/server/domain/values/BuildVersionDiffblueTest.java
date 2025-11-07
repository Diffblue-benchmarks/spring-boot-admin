package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class BuildVersionDiffblueTest {
  /**
   * Method under test: {@link BuildVersion#valueOf(String)}
   */
  @Test
  public void testValueOf() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersion.valueOf("foo");

    // Assert
    assertEquals("foo", actualValueOfResult.getValue());
    assertEquals("foo", actualValueOfResult.toString());
  }

  /**
   * Method under test: {@link BuildVersion#valueOf(String)}
   */
  @Test
  public void testValueOf2() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersion.valueOf(null);

    // Assert
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualValueOfResult.getValue());
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualValueOfResult.toString());
  }

  /**
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  public void testFrom() {
    // Arrange, Act and Assert
    assertNull(BuildVersion.from(new HashMap<>()));
  }

  /**
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  public void testFrom2() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("version", null);

    HashMap<String, Object> map = new HashMap<>();
    map.put("build", objectObjectMap);
    map.put("build.version", null);
    map.put("version", null);

    // Act and Assert
    assertNull(BuildVersion.from(map));
  }

  /**
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  public void testFrom3() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("version", null);

    HashMap<String, Object> map = new HashMap<>();
    map.put("build", objectObjectMap);
    map.put("build.version", null);
    map.put("version", "Map");

    // Act
    BuildVersion actualFromResult = BuildVersion.from(map);

    // Assert
    assertEquals("Map", actualFromResult.getValue());
    assertEquals("Map", actualFromResult.toString());
  }

  /**
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  public void testFrom4() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("version", null);

    HashMap<String, Object> map = new HashMap<>();
    map.put("build", objectObjectMap);
    map.put("build.version", "Map");
    map.put("version", null);

    // Act
    BuildVersion actualFromResult = BuildVersion.from(map);

    // Assert
    assertEquals("Map", actualFromResult.getValue());
    assertEquals("Map", actualFromResult.toString());
  }

  /**
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  public void testFrom5() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("version", "Map");

    HashMap<String, Object> map = new HashMap<>();
    map.put("build", objectObjectMap);
    map.put("build.version", null);
    map.put("version", null);

    // Act
    BuildVersion actualFromResult = BuildVersion.from(map);

    // Assert
    assertEquals("Map", actualFromResult.getValue());
    assertEquals("Map", actualFromResult.toString());
  }

  /**
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  public void testFrom6() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act and Assert
    assertNull(BuildVersion.from(map));
  }

  /**
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  public void testFrom7() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.computeIfPresent("build", mock(BiFunction.class));
    map.put("foo", "42");

    // Act and Assert
    assertNull(BuildVersion.from(map));
  }

  /**
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  public void testFrom8() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("build.version", "");
    map.put("foo", "42");

    // Act
    BuildVersion actualFromResult = BuildVersion.from(map);

    // Assert
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualFromResult.getValue());
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualFromResult.toString());
  }

  /**
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  public void testCompareTo() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");

    // Act and Assert
    assertEquals(0, valueOfResult.compareTo(BuildVersion.valueOf("foo")));
  }

  /**
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  public void testCompareTo2() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("[.\\-+]");

    // Act and Assert
    assertEquals(-11, valueOfResult.compareTo(BuildVersion.valueOf("foo")));
  }

  /**
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  public void testCompareTo3() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("42");

    // Act and Assert
    assertEquals(-50, valueOfResult.compareTo(BuildVersion.valueOf("foo")));
  }

  /**
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  public void testCompareTo4() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("42");

    // Act and Assert
    assertEquals(0, valueOfResult.compareTo(BuildVersion.valueOf("42")));
  }

  /**
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  public void testCompareTo5() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("build.version");

    // Act and Assert
    assertEquals(1, valueOfResult.compareTo(BuildVersion.valueOf("build")));
  }

  /**
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  public void testCompareTo6() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("build");

    // Act and Assert
    assertEquals(-1, valueOfResult.compareTo(BuildVersion.valueOf("build.version")));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BuildVersion#equals(Object)}
   *   <li>{@link BuildVersion#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");
    BuildVersion valueOfResult2 = BuildVersion.valueOf("foo");

    // Act and Assert
    assertEquals(valueOfResult, valueOfResult2);
    int expectedHashCodeResult = valueOfResult.hashCode();
    assertEquals(expectedHashCodeResult, valueOfResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BuildVersion#equals(Object)}
   *   <li>{@link BuildVersion#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");

    // Act and Assert
    assertEquals(valueOfResult, valueOfResult);
    int expectedHashCodeResult = valueOfResult.hashCode();
    assertEquals(expectedHashCodeResult, valueOfResult.hashCode());
  }

  /**
   * Method under test: {@link BuildVersion#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf(null);

    // Act and Assert
    assertNotEquals(valueOfResult, BuildVersion.valueOf("foo"));
  }

  /**
   * Method under test: {@link BuildVersion#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(BuildVersion.valueOf("foo"), null);
  }

  /**
   * Method under test: {@link BuildVersion#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(BuildVersion.valueOf("foo"), "Different type to BuildVersion");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BuildVersion#getValue()}
   *   <li>{@link BuildVersion#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");

    // Act
    String actualValue = valueOfResult.getValue();

    // Assert
    assertEquals("foo", actualValue);
    assertEquals("foo", valueOfResult.toString());
  }
}
