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
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.
   *   <li>Then return Values is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given '42'; when HashMap() '42' is '42'; then return Values is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags TagsMixin.from(Map)"})
  void testFrom_given42_whenHashMap42Is42_thenReturnValuesIsHashMap() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "42");
    map.put("foo", "42");

    // Act
    Tags actualFromResult = TagsMixin.from(map);

    // Assert
    assertEquals(map, actualFromResult.getValues());
  }

  /**
   * Test {@link TagsMixin#from(Map)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.
   *   <li>Then return Values size is one.
   * </ul>
   *
   * <p>Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given 'foo'; when HashMap() 'foo' is '42'; then return Values size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags TagsMixin.from(Map)"})
  void testFrom_givenFoo_whenHashMapFooIs42_thenReturnValuesSizeIsOne() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act
    Tags actualFromResult = TagsMixin.from(map);

    // Assert
    Map<String, String> values = actualFromResult.getValues();
    assertEquals(1, values.size());
    assertEquals("42", values.get("foo"));
  }

  /**
   * Test {@link TagsMixin#from(Map)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code null} is {@link HashMap#HashMap()}.
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link TagsMixin#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given HashMap() 'null' is HashMap(); when HashMap() 'null' is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tags TagsMixin.from(Map)"})
  void testFrom_givenHashMapNullIsHashMap_whenHashMapNullIsHashMap() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(null, new HashMap<>());

    HashMap<Object, Object> objectObjectMap2 = new HashMap<>();
    objectObjectMap2.put(null, objectObjectMap);

    HashMap<String, Object> map = new HashMap<>();
    map.put(null, objectObjectMap2);

    // Act
    Tags actualFromResult = TagsMixin.from(map);

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
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
