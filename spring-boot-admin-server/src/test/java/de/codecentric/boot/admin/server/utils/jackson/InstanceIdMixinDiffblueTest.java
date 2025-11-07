package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import org.junit.Test;

public class InstanceIdMixinDiffblueTest {
  /**
   * Method under test: {@link InstanceIdMixin#of(String)}
   */
  @Test
  public void testOf() {
    // Arrange and Act
    InstanceId actualOfResult = InstanceIdMixin.of("42");

    // Assert
    assertEquals("42", actualOfResult.getValue());
    assertEquals("42", actualOfResult.toString());
  }
}
