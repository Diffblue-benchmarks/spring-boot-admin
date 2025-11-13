package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.BuildVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BuildVersionMixinDiffblueTest {
  /**
   * Test {@link BuildVersionMixin#valueOf(String)}.
   *
   * <ul>
   *   <li>When {@code foo}.
   *   <li>Then return Value is {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersionMixin#valueOf(String)}
   */
  @Test
  @DisplayName("Test valueOf(String); when 'foo'; then return Value is 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersionMixin.valueOf(String)"})
  void testValueOf_whenFoo_thenReturnValueIsFoo() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersionMixin.valueOf("foo");

    // Assert
    assertEquals("foo", actualValueOfResult.getValue());
    assertEquals("foo", actualValueOfResult.toString());
  }

  /**
   * Test {@link BuildVersionMixin#valueOf(String)}.
   *
   * <ul>
   *   <li>When space.
   *   <li>Then return Value is {@code UNKNOWN}.
   * </ul>
   *
   * <p>Method under test: {@link BuildVersionMixin#valueOf(String)}
   */
  @Test
  @DisplayName("Test valueOf(String); when space; then return Value is 'UNKNOWN'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion BuildVersionMixin.valueOf(String)"})
  void testValueOf_whenSpace_thenReturnValueIsUnknown() {
    // Arrange and Act
    BuildVersion actualValueOfResult = BuildVersionMixin.valueOf(" ");

    // Assert
    assertEquals("UNKNOWN", actualValueOfResult.getValue());
    assertEquals("UNKNOWN", actualValueOfResult.toString());
  }
}
