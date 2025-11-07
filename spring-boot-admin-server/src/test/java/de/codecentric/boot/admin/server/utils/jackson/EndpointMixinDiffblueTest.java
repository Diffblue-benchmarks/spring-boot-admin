package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import org.junit.Test;

public class EndpointMixinDiffblueTest {
  /**
   * Method under test: {@link EndpointMixin#of(String, String)}
   */
  @Test
  public void testOf() {
    // Arrange and Act
    Endpoint actualOfResult = EndpointMixin.of("42", "https://example.org/example");

    // Assert
    assertEquals("42", actualOfResult.getId());
    assertEquals("https://example.org/example", actualOfResult.getUrl());
  }
}
