package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceIdMixinDiffblueTest {
  /**
   * Test {@link InstanceIdMixin#of(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return Value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceIdMixin#of(String)}
   */
  @Test
  @DisplayName("Test of(String); when '42'; then return Value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceId InstanceIdMixin.of(String)"})
  void testOf_when42_thenReturnValueIs42() {
    // Arrange and Act
    InstanceId actualOfResult = InstanceIdMixin.of("42");

    // Assert
    assertEquals("42", actualOfResult.getValue());
    assertEquals("42", actualOfResult.toString());
  }
}
