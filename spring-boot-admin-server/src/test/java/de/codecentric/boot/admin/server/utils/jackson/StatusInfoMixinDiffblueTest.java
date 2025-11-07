package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class StatusInfoMixinDiffblueTest {
  /**
   * Test {@link StatusInfoMixin#valueOf(String, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return Status is {@code STATUS CODE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfoMixin#valueOf(String, Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfoMixin.valueOf(String, Map)"})
  public void testValueOf_whenHashMap_thenReturnStatusIsStatusCode() {
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

  /**
   * Test {@link StatusInfoMixin#valueOf(String, Map)}.
   * <ul>
   *   <li>When {@code Status Code}.</li>
   *   <li>Then return Status is {@code STATUS CODE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfoMixin#valueOf(String, Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfoMixin.valueOf(String, Map)"})
  public void testValueOf_whenStatusCode_thenReturnStatusIsStatusCode() {
    // Arrange and Act
    StatusInfo actualValueOfResult = StatusInfoMixin.valueOf("Status Code", null);

    // Assert
    assertEquals("STATUS CODE", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }
}
