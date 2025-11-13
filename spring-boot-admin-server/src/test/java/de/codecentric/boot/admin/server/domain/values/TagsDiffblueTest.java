package de.codecentric.boot.admin.server.domain.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TagsDiffblueTest {
  /**
   * Test {@link Tags#getValues()}.
   *
   * <p>Method under test: {@link Tags#getValues()}
   */
  @Test
  @DisplayName("Test getValues()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map Tags.getValues()"})
  void testGetValues() {
    // Arrange, Act and Assert
    assertTrue(Tags.empty().getValues().isEmpty());
  }

  /**
   * Test {@link Tags#append(Tags)}.
   *
   * <p>Method under test: {@link Tags#append(Tags)}
   */
  @Test
  @DisplayName("Test append(Tags)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.append(Tags)"})
  void testAppend() {
    // Arrange
    Tags emptyResult = Tags.empty();

    // Act and Assert
    assertTrue(emptyResult.append(Tags.empty()).getValues().isEmpty());
  }

  /**
   * Test {@link Tags#empty()}.
   *
   * <p>Method under test: {@link Tags#empty()}
   */
  @Test
  @DisplayName("Test empty()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.empty()"})
  void testEmpty() {
    // Arrange, Act and Assert
    assertTrue(Tags.empty().getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code Value}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @DisplayName(
      "Test from(Map, String) with 'map', 'prefix'; given '42'; when HashMap() '42' is 'Value'; then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  void testFromWithMapPrefix_given42_whenHashMap42IsValue_thenReturnValuesEmpty() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "Value");
    map.put("Key", "Value");

    // Act
    Tags actualFromResult = Tags.from(map, "Prefix");

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code Value}.
   *   <li>Then return Values is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @DisplayName(
      "Test from(Map, String) with 'map', 'prefix'; given '42'; when HashMap() '42' is 'Value'; then return Values is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  void testFromWithMapPrefix_given42_whenHashMap42IsValue_thenReturnValuesIsHashMap() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "Value");
    map.put("Key", "Value");

    // Act
    Tags actualFromResult = Tags.from(map, null);

    // Assert
    assertEquals(map, actualFromResult.getValues());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@code null}.
   *   <li>Then return Values size is one.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @DisplayName(
      "Test from(Map, String) with 'map', 'prefix'; given 'Key'; when 'null'; then return Values size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  void testFromWithMapPrefix_givenKey_whenNull_thenReturnValuesSizeIsOne() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("Key", "Value");

    // Act
    Tags actualFromResult = Tags.from(map, null);

    // Assert
    Map<String, String> values = actualFromResult.getValues();
    assertEquals(1, values.size());
    assertEquals("Value", values.get("Key"));
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@code Prefix}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @DisplayName(
      "Test from(Map, String) with 'map', 'prefix'; given 'Key'; when 'Prefix'; then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  void testFromWithMapPrefix_givenKey_whenPrefix_thenReturnValuesEmpty() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("Key", "Value");

    // Act
    Tags actualFromResult = Tags.from(map, "Prefix");

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code Value}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @DisplayName(
      "Test from(Map, String) with 'map', 'prefix'; given 'null'; when HashMap() 'null' is 'Value'; then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  void testFromWithMapPrefix_givenNull_whenHashMapNullIsValue_thenReturnValuesEmpty() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put(null, "Value");

    // Act
    Tags actualFromResult = Tags.from(map, "Prefix");

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code Value}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @DisplayName(
      "Test from(Map, String) with 'map', 'prefix'; given 'null'; when HashMap() 'null' is 'Value'; then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  void testFromWithMapPrefix_givenNull_whenHashMapNullIsValue_thenReturnValuesEmpty2() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put(null, "Value");

    // Act
    Tags actualFromResult = Tags.from(map, null);

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code Value}.
   *   <li>Then return Values size is one.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @DisplayName(
      "Test from(Map, String) with 'map', 'prefix'; given 'null'; when HashMap() 'null' is 'Value'; then return Values size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  void testFromWithMapPrefix_givenNull_whenHashMapNullIsValue_thenReturnValuesSizeIsOne() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put(null, "Value");
    map.put("Key", "Value");

    // Act
    Tags actualFromResult = Tags.from(map, null);

    // Assert
    Map<String, String> values = actualFromResult.getValues();
    assertEquals(1, values.size());
    assertEquals("Value", values.get("Key"));
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @DisplayName(
      "Test from(Map, String) with 'map', 'prefix'; when HashMap(); then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  void testFromWithMapPrefix_whenHashMap_thenReturnValuesEmpty() {
    // Arrange and Act
    Tags actualFromResult = Tags.from(new HashMap<>(), "Prefix");

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map)} with {@code map}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code Value}.
   *   <li>Then return Values is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map) with 'map'; given '42'; when HashMap() '42' is 'Value'; then return Values is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map)"})
  void testFromWithMap_given42_whenHashMap42IsValue_thenReturnValuesIsHashMap() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "Value");
    map.put("Key", "Value");

    // Act
    Tags actualFromResult = Tags.from(map);

    // Assert
    assertEquals(map, actualFromResult.getValues());
  }

  /**
   * Test {@link Tags#from(Map)} with {@code map}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code Value}.
   *   <li>Then return Values size is one.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map) with 'map'; given 'Key'; when HashMap() 'Key' is 'Value'; then return Values size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map)"})
  void testFromWithMap_givenKey_whenHashMapKeyIsValue_thenReturnValuesSizeIsOne() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("Key", "Value");

    // Act
    Tags actualFromResult = Tags.from(map);

    // Assert
    Map<String, String> values = actualFromResult.getValues();
    assertEquals(1, values.size());
    assertEquals("Value", values.get("Key"));
  }

  /**
   * Test {@link Tags#from(Map)} with {@code map}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code Value}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map) with 'map'; given 'null'; when HashMap() 'null' is 'Value'; then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map)"})
  void testFromWithMap_givenNull_whenHashMapNullIsValue_thenReturnValuesEmpty() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put(null, "Value");

    // Act
    Tags actualFromResult = Tags.from(map);

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map)} with {@code map}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link Tags#from(Map)}
   */
  @Test
  @DisplayName("Test from(Map) with 'map'; when HashMap(); then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags Tags.from(Map)"})
  void testFromWithMap_whenHashMap_thenReturnValuesEmpty() {
    // Arrange and Act
    Tags actualFromResult = Tags.from(new HashMap<>());

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }
}
