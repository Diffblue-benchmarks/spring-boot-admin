package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Info;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InfoMixinDiffblueTest {
  /**
   * Test {@link InfoMixin#from(Map)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.
   *   <li>Then return Values is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoMixin#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given 'foo'; when HashMap() 'foo' is '42'; then return Values is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Info InfoMixin.from(Map)"})
  void testFrom_givenFoo_whenHashMapFooIs42_thenReturnValuesIsHashMap() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.put("foo", "42");

    // Act
    Info actualFromResult = InfoMixin.from(values);

    // Assert
    assertEquals(values, actualFromResult.getValues());
  }

  /**
   * Test {@link InfoMixin#from(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link InfoMixin#from(Map)}
   */
  @Test
  @DisplayName("Test from(Map); when HashMap(); then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Info InfoMixin.from(Map)"})
  void testFrom_whenHashMap_thenReturnValuesEmpty() {
    // Arrange and Act
    Info actualFromResult = InfoMixin.from(new HashMap<>());

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }

  /**
   * Test {@link InfoMixin#from(Map)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link InfoMixin#from(Map)}
   */
  @Test
  @DisplayName("Test from(Map); when 'null'; then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Info InfoMixin.from(Map)"})
  void testFrom_whenNull_thenReturnValuesEmpty() {
    // Arrange and Act
    Info actualFromResult = InfoMixin.from(null);

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }
}
