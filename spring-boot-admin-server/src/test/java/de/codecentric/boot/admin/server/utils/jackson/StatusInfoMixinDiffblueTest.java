package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StatusInfoMixinDiffblueTest {
  /**
   * Test {@link StatusInfoMixin#valueOf(String, Map)}.
   *
   * <ul>
   *   <li>When {@code not blank}.
   *   <li>Then return Status is {@code NOT BLANK}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfoMixin#valueOf(String, Map)}
   */
  @Test
  @DisplayName("Test valueOf(String, Map); when 'not blank'; then return Status is 'NOT BLANK'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfoMixin.valueOf(String, Map)"})
  void testValueOf_whenNotBlank_thenReturnStatusIsNotBlank() {
    // Arrange and Act
    StatusInfo actualValueOfResult = StatusInfoMixin.valueOf("not blank", null);

    // Assert
    assertEquals("NOT BLANK", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }

  /**
   * Test {@link StatusInfoMixin#valueOf(String, Map)}.
   *
   * <ul>
   *   <li>When {@code Status Code}.
   *   <li>Then return Status is {@code STATUS CODE}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfoMixin#valueOf(String, Map)}
   */
  @Test
  @DisplayName("Test valueOf(String, Map); when 'Status Code'; then return Status is 'STATUS CODE'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfoMixin.valueOf(String, Map)"})
  void testValueOf_whenStatusCode_thenReturnStatusIsStatusCode() {
    // Arrange and Act
    StatusInfo actualValueOfResult = StatusInfoMixin.valueOf("Status Code", new HashMap<>());

    // Assert
    assertEquals("STATUS CODE", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }
}
