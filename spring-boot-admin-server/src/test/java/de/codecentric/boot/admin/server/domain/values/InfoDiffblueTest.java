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

class InfoDiffblueTest {
  /**
   * Test {@link Info#from(Map)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.
   *   <li>Then return Values is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link Info#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given 'foo'; when HashMap() 'foo' is '42'; then return Values is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Info Info.from(Map)"})
  void testFrom_givenFoo_whenHashMapFooIs42_thenReturnValuesIsHashMap() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.put("foo", "42");

    // Act
    Info actualFromResult = Info.from(values);

    // Assert
    assertEquals(values, actualFromResult.getValues());
  }

  /**
   * Test {@link Info#from(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link Info#from(Map)}
   */
  @Test
  @DisplayName("Test from(Map); when HashMap(); then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Info Info.from(Map)"})
  void testFrom_whenHashMap_thenReturnValuesEmpty() {
    // Arrange and Act
    Info actualFromResult = Info.from(new HashMap<>());

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }

  /**
   * Test {@link Info#from(Map)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Values Empty.
   * </ul>
   *
   * <p>Method under test: {@link Info#from(Map)}
   */
  @Test
  @DisplayName("Test from(Map); when 'null'; then return Values Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Info Info.from(Map)"})
  void testFrom_whenNull_thenReturnValuesEmpty() {
    // Arrange and Act
    Info actualFromResult = Info.from(null);

    // Assert
    assertTrue(actualFromResult.getValues().isEmpty());
  }

  /**
   * Test {@link Info#empty()}.
   *
   * <p>Method under test: {@link Info#empty()}
   */
  @Test
  @DisplayName("Test empty()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Info Info.empty()"})
  void testEmpty() {
    // Arrange, Act and Assert
    assertTrue(Info.empty().getValues().isEmpty());
  }

  /**
   * Test {@link Info#getValues()}.
   *
   * <p>Method under test: {@link Info#getValues()}
   */
  @Test
  @DisplayName("Test getValues()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map Info.getValues()"})
  void testGetValues() {
    // Arrange, Act and Assert
    assertTrue(Info.empty().getValues().isEmpty());
  }
}
