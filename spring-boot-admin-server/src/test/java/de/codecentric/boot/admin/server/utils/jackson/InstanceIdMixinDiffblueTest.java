package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InstanceIdMixinDiffblueTest {
  /**
   * Test {@link InstanceIdMixin#of(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return Value is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceIdMixin#of(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"InstanceId InstanceIdMixin.of(String)"})
  public void testOf_when42_thenReturnValueIs42() {
    // Arrange and Act
    InstanceId actualOfResult = InstanceIdMixin.of("42");

    // Assert
    assertEquals("42", actualOfResult.getValue());
    assertEquals("42", actualOfResult.toString());
  }
}
