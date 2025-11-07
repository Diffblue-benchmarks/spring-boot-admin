package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BuildVersionDiffblueTest {
  /**
   * Test {@link BuildVersion#valueOf(String)}.
   * <ul>
   *   <li>When {@code foo}.</li>
   *   <li>Then return Value is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#valueOf(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BuildVersion BuildVersion.valueOf(String)"})
  public void testValueOf_whenFoo_thenReturnValueIsFoo() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersion.valueOf("foo");

    // Assert
    assertEquals("foo", actualValueOfResult.getValue());
    assertEquals("foo", actualValueOfResult.toString());
  }

  /**
   * Test {@link BuildVersion#valueOf(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Value is {@link StatusInfo#STATUS_UNKNOWN}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#valueOf(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BuildVersion BuildVersion.valueOf(String)"})
  public void testValueOf_whenNull_thenReturnValueIsStatus_unknown() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersion.valueOf(null);

    // Assert
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualValueOfResult.getValue());
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualValueOfResult.toString());
  }

  /**
   * Test {@link BuildVersion#from(Map)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>Then return Value is {@link StatusInfo#STATUS_UNKNOWN}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  public void testFrom_givenEmptyString_thenReturnValueIsStatus_unknown() {
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
   * Test {@link BuildVersion#from(Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  public void testFrom_givenFoo_whenHashMapFooIs42_thenReturnNull() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act and Assert
    assertNull(BuildVersion.from(map));
  }

  /**
   * Test {@link BuildVersion#from(Map)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code version} is {@code Map}.</li>
   *   <li>Then return Value is {@code Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  public void testFrom_givenHashMapVersionIsMap_thenReturnValueIsMap() {
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
   * Test {@link BuildVersion#from(Map)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code version} is {@code null}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code build.version} is {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  public void testFrom_givenHashMapVersionIsNull_whenHashMapBuildVersionIsNull_thenReturnNull() {
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
   * Test {@link BuildVersion#from(Map)}.
   * <ul>
   *   <li>Given {@code Map}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code build.version} is {@code Map}.</li>
   *   <li>Then return Value is {@code Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  public void testFrom_givenMap_whenHashMapBuildVersionIsMap_thenReturnValueIsMap() {
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
   * Test {@link BuildVersion#from(Map)}.
   * <ul>
   *   <li>Given {@code Map}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code version} is {@code Map}.</li>
   *   <li>Then return Value is {@code Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  public void testFrom_givenMap_whenHashMapVersionIsMap_thenReturnValueIsMap() {
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
   * Test {@link BuildVersion#from(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  public void testFrom_whenHashMap_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BuildVersion.from(new HashMap<>()));
  }

  /**
   * Test {@link BuildVersion#equals(Object)}, and {@link BuildVersion#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BuildVersion#equals(Object)}
   *   <li>{@link BuildVersion#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean BuildVersion.equals(Object)", "int BuildVersion.hashCode()"})
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
   * Test {@link BuildVersion#equals(Object)}, and {@link BuildVersion#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BuildVersion#equals(Object)}
   *   <li>{@link BuildVersion#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean BuildVersion.equals(Object)", "int BuildVersion.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");

    // Act and Assert
    assertEquals(valueOfResult, valueOfResult);
    int expectedHashCodeResult = valueOfResult.hashCode();
    assertEquals(expectedHashCodeResult, valueOfResult.hashCode());
  }

  /**
   * Test {@link BuildVersion#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean BuildVersion.equals(Object)", "int BuildVersion.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf(StatusInfo.STATUS_UNKNOWN);

    // Act and Assert
    assertNotEquals(valueOfResult, BuildVersion.valueOf("foo"));
  }

  /**
   * Test {@link BuildVersion#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean BuildVersion.equals(Object)", "int BuildVersion.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(BuildVersion.valueOf("foo"), null);
  }

  /**
   * Test {@link BuildVersion#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean BuildVersion.equals(Object)", "int BuildVersion.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(BuildVersion.valueOf("foo"), "Different type to BuildVersion");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BuildVersion#getValue()}
   *   <li>{@link BuildVersion#toString()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String BuildVersion.getValue()", "String BuildVersion.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");

    // Act
    String actualValue = valueOfResult.getValue();

    // Assert
    assertEquals("foo", actualValue);
    assertEquals("foo", valueOfResult.toString());
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   * <ul>
   *   <li>Given valueOf {@code 42}.</li>
   *   <li>When valueOf {@code 42}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  public void testCompareToWithBuildVersion_givenValueOf42_whenValueOf42_thenReturnZero() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("42");

    // Act and Assert
    assertEquals(0, valueOfResult.compareTo(BuildVersion.valueOf("42")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   * <ul>
   *   <li>Given valueOf {@code 42}.</li>
   *   <li>When valueOf {@code foo}.</li>
   *   <li>Then return minus fifty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  public void testCompareToWithBuildVersion_givenValueOf42_whenValueOfFoo_thenReturnMinusFifty() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("42");

    // Act and Assert
    assertEquals(-50, valueOfResult.compareTo(BuildVersion.valueOf("foo")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   * <ul>
   *   <li>Given valueOf {@code build.version}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  public void testCompareToWithBuildVersion_givenValueOfBuildVersion_thenReturnOne() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("build.version");

    // Act and Assert
    assertEquals(1, valueOfResult.compareTo(BuildVersion.valueOf("build")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   * <ul>
   *   <li>Given valueOf {@code build}.</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  public void testCompareToWithBuildVersion_givenValueOfBuild_thenReturnMinusOne() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("build");

    // Act and Assert
    assertEquals(-1, valueOfResult.compareTo(BuildVersion.valueOf("build.version")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   * <ul>
   *   <li>Given valueOf {@code foo}.</li>
   *   <li>When valueOf {@code foo}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  public void testCompareToWithBuildVersion_givenValueOfFoo_whenValueOfFoo_thenReturnZero() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");

    // Act and Assert
    assertEquals(0, valueOfResult.compareTo(BuildVersion.valueOf("foo")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   * <ul>
   *   <li>Then return minus eleven.</li>
   * </ul>
   * <p>
   * Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  public void testCompareToWithBuildVersion_thenReturnMinusEleven() {
    // Arrange
    BuildVersion valueOfResult = BuildVersion.valueOf("[.\\-+]");

    // Act and Assert
    assertEquals(-11, valueOfResult.compareTo(BuildVersion.valueOf("foo")));
  }
}
