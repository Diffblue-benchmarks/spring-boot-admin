package de.codecentric.boot.admin.server.domain.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceIdDiffblueTest {
  /**
   * Test {@link InstanceId#of(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return Value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceId#of(String)}
   */
  @Test
  @DisplayName("Test of(String); when '42'; then return Value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceId InstanceId.of(String)"})
  void testOf_when42_thenReturnValueIs42() {
    // Arrange and Act
    InstanceId actualOfResult = InstanceId.of("42");

    // Assert
    assertEquals("42", actualOfResult.getValue());
    assertEquals("42", actualOfResult.toString());
  }

  /**
   * Test {@link InstanceId#toString()}.
   *
   * <p>Method under test: {@link InstanceId#toString()}
   */
  @Test
  @DisplayName("Test toString()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String InstanceId.toString()"})
  void testToString() {
    // Arrange, Act and Assert
    assertEquals("42", InstanceId.of("42").toString());
  }

  /**
   * Test {@link InstanceId#compareTo(InstanceId)} with {@code InstanceId}.
   *
   * <p>Method under test: {@link InstanceId#compareTo(InstanceId)}
   */
  @Test
  @DisplayName("Test compareTo(InstanceId) with 'InstanceId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int InstanceId.compareTo(InstanceId)"})
  void testCompareToWithInstanceId() {
    // Arrange
    InstanceId ofResult = InstanceId.of("42");

    // Act and Assert
    assertEquals(0, ofResult.compareTo(InstanceId.of("42")));
  }
}
