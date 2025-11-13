package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Tags;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TagsMixinDiffblueTest {
  /**
   * Test {@link TagsMixin#from(Map)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code Value}.
   *   <li>Then return Values is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given '42'; when HashMap() '42' is 'Value'; then return Values is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags TagsMixin.from(Map)"})
  void testFrom_given42_whenHashMap42IsValue_thenReturnValuesIsHashMap() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "Value");
    map.put("Key", "Value");

    // Act
    Tags actualFromResult = TagsMixin.from(map);

    // Assert
    assertEquals(map, actualFromResult.getValues());
  }

  /**
   * Test {@link TagsMixin#from(Map)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code Value}.
   *   <li>Then return Values size is one.
   * </ul>
   *
   * <p>Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given 'Key'; when HashMap() 'Key' is 'Value'; then return Values size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags TagsMixin.from(Map)"})
  void testFrom_givenKey_whenHashMapKeyIsValue_thenReturnValuesSizeIsOne() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("Key", "Value");

    // Act
    Tags actualFromResult = TagsMixin.from(map);

    // Assert
    Map<String, String> values = actualFromResult.getValues();
    assertEquals(1, values.size());
    assertEquals("Value", values.get("Key"));
  }

  /**
   * Test {@link TagsMixin#from(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  @DisplayName("Test from(Map); when HashMap(); then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags TagsMixin.from(Map)"})
  void testFrom_whenHashMap_thenReturnValuesEmpty() {
    // Arrange and Act
    Tags actualFromResult = TagsMixin.from(new HashMap<>());

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }
}
