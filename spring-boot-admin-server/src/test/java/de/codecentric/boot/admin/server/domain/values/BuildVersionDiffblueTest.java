package de.codecentric.boot.admin.server.domain.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BuildVersionDiffblueTest {
  /**
   * Test {@link BuildVersion#valueOf(String)}.
   *
   * <ul>
   *   <li>When {@code foo}.
   *   <li>Then return Value is {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#valueOf(String)}
   */
  @Test
  @DisplayName("Test valueOf(String); when 'foo'; then return Value is 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersion.valueOf(String)"})
  void testValueOf_whenFoo_thenReturnValueIsFoo() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersion.valueOf("foo");

    // Assert
    assertEquals("foo", actualValueOfResult.getValue());
    assertEquals("foo", actualValueOfResult.toString());
  }

  /**
   * Test {@link BuildVersion#valueOf(String)}.
   *
   * <ul>
   *   <li>When space.
   *   <li>Then return Value is {@link StatusInfo#STATUS_UNKNOWN}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#valueOf(String)}
   */
  @Test
  @DisplayName("Test valueOf(String); when space; then return Value is STATUS_UNKNOWN")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersion.valueOf(String)"})
  void testValueOf_whenSpace_thenReturnValueIsStatus_unknown() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersion.valueOf(" ");

    // Assert
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualValueOfResult.getValue());
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualValueOfResult.toString());
  }

  /**
   * Test {@link BuildVersion#from(Map)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code build} is {@code not blank}.
   *   <li>Then return Value is {@code not blank}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given HashMap() 'build' is 'not blank'; then return Value is 'not blank'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  void testFrom_givenHashMapBuildIsNotBlank_thenReturnValueIsNotBlank() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("build", "not blank");

    HashMap<String, Object> map = new HashMap<>();
    map.put("build", objectObjectMap);
    map.put("build.version", "not blank");
    map.put("version", "not blank");

    // Act
    BuildVersion actualFromResult = BuildVersion.from(map);

    // Assert
    assertEquals("not blank", actualFromResult.getValue());
    assertEquals("not blank", actualFromResult.toString());
  }

  /**
   * Test {@link BuildVersion#from(Map)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code version} is {@code not blank}.
   *   <li>Then return Value is {@code not blank}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given HashMap() 'version' is 'not blank'; then return Value is 'not blank'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  void testFrom_givenHashMapVersionIsNotBlank_thenReturnValueIsNotBlank() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("version", "not blank");

    HashMap<String, Object> map = new HashMap<>();
    map.put("build", objectObjectMap);
    map.put("build.version", "not blank");
    map.put("version", "not blank");

    // Act
    BuildVersion actualFromResult = BuildVersion.from(map);

    // Assert
    assertEquals("not blank", actualFromResult.getValue());
    assertEquals("not blank", actualFromResult.toString());
  }

  /**
   * Test {@link BuildVersion#from(Map)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code version} is space.
   *   <li>Then return Value is {@link StatusInfo#STATUS_UNKNOWN}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given HashMap() 'version' is space; then return Value is STATUS_UNKNOWN")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  void testFrom_givenHashMapVersionIsSpace_thenReturnValueIsStatus_unknown() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("version", " ");

    HashMap<String, Object> map = new HashMap<>();
    map.put("build", objectObjectMap);
    map.put("build.version", "not blank");
    map.put("version", "not blank");

    // Act
    BuildVersion actualFromResult = BuildVersion.from(map);

    // Assert
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualFromResult.getValue());
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualFromResult.toString());
  }

  /**
   * Test {@link BuildVersion#from(Map)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code build.version} is {@code Value}.
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given 'Key'; when HashMap() 'build.version' is 'Value'; then return 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  void testFrom_givenKey_whenHashMapBuildVersionIsValue_thenReturnValue() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("build.version", "Value");
    map.put("Key", "Value");

    // Act
    BuildVersion actualFromResult = BuildVersion.from(map);

    // Assert
    assertEquals("Value", actualFromResult.getValue());
    assertEquals("Value", actualFromResult.toString());
  }

  /**
   * Test {@link BuildVersion#from(Map)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code Value}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @DisplayName("Test from(Map); given 'Key'; when HashMap() 'Key' is 'Value'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  void testFrom_givenKey_whenHashMapKeyIsValue_thenReturnNull() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("Key", "Value");

    // Act and Assert
    assertNull(BuildVersion.from(map));
  }

  /**
   * Test {@link BuildVersion#from(Map)}.
   *
   * <ul>
   *   <li>Given {@code Value}.
   *   <li>When {@link HashMap#HashMap()} {@code version} is {@code Value}.
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given 'Value'; when HashMap() 'version' is 'Value'; then return 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  void testFrom_givenValue_whenHashMapVersionIsValue_thenReturnValue() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("version", "Value");

    // Act
    BuildVersion actualFromResult = BuildVersion.from(map);

    // Assert
    assertEquals("Value", actualFromResult.getValue());
    assertEquals("Value", actualFromResult.toString());
  }

  /**
   * Test {@link BuildVersion#from(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#from(Map)}
   */
  @Test
  @DisplayName("Test from(Map); when HashMap(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersion.from(Map)"})
  void testFrom_whenHashMap_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BuildVersion.from(new HashMap<>()));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link BuildVersion#getValue()}
   *   <li>{@link BuildVersion#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BuildVersion.getValue()", "String BuildVersion.toString()"})
  void testGettersAndSetters() {
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
   *
   * <ul>
   *   <li>Given valueOf {@code 42}.
   *   <li>When valueOf {@code 42}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @DisplayName(
      "Test compareTo(BuildVersion) with 'BuildVersion'; given valueOf '42'; when valueOf '42'; then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  void testCompareToWithBuildVersion_givenValueOf42_whenValueOf42_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, BuildVersion.valueOf("42").compareTo(BuildVersion.valueOf("42")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   *
   * <ul>
   *   <li>Given valueOf {@code 42}.
   *   <li>When valueOf {@code foo}.
   *   <li>Then return minus fifty.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @DisplayName(
      "Test compareTo(BuildVersion) with 'BuildVersion'; given valueOf '42'; when valueOf 'foo'; then return minus fifty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  void testCompareToWithBuildVersion_givenValueOf42_whenValueOfFoo_thenReturnMinusFifty() {
    // Arrange, Act and Assert
    assertEquals(-50, BuildVersion.valueOf("42").compareTo(BuildVersion.valueOf("foo")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   *
   * <ul>
   *   <li>Given valueOf {@code build.version}.
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @DisplayName(
      "Test compareTo(BuildVersion) with 'BuildVersion'; given valueOf 'build.version'; then return one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  void testCompareToWithBuildVersion_givenValueOfBuildVersion_thenReturnOne() {
    // Arrange, Act and Assert
    assertEquals(1, BuildVersion.valueOf("build.version").compareTo(BuildVersion.valueOf("build")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   *
   * <ul>
   *   <li>Given valueOf {@code build}.
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @DisplayName(
      "Test compareTo(BuildVersion) with 'BuildVersion'; given valueOf 'build'; then return minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  void testCompareToWithBuildVersion_givenValueOfBuild_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(
        -1, BuildVersion.valueOf("build").compareTo(BuildVersion.valueOf("build.version")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   *
   * <ul>
   *   <li>Given valueOf {@code foo}.
   *   <li>When valueOf {@code foo}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @DisplayName(
      "Test compareTo(BuildVersion) with 'BuildVersion'; given valueOf 'foo'; when valueOf 'foo'; then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  void testCompareToWithBuildVersion_givenValueOfFoo_whenValueOfFoo_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, BuildVersion.valueOf("foo").compareTo(BuildVersion.valueOf("foo")));
  }

  /**
   * Test {@link BuildVersion#compareTo(BuildVersion)} with {@code BuildVersion}.
   *
   * <ul>
   *   <li>Then return minus eleven.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersion#compareTo(BuildVersion)}
   */
  @Test
  @DisplayName("Test compareTo(BuildVersion) with 'BuildVersion'; then return minus eleven")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int BuildVersion.compareTo(BuildVersion)"})
  void testCompareToWithBuildVersion_thenReturnMinusEleven() {
    // Arrange, Act and Assert
    assertEquals(-11, BuildVersion.valueOf("[.\\-+]").compareTo(BuildVersion.valueOf("foo")));
  }
}
