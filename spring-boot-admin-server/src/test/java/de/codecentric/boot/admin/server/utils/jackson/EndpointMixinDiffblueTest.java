package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EndpointMixinDiffblueTest {
  /**
   * Test {@link EndpointMixin#of(String, String)}.
   * <ul>
   *   <li>When {@code https://example.org/example}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointMixin#of(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Endpoint EndpointMixin.of(String, String)"})
  public void testOf_whenHttpsExampleOrgExample_thenReturnIdIs42() {
    // Arrange and Act
    Endpoint actualOfResult = EndpointMixin.of("42", "https://example.org/example");

    // Assert
    assertEquals("42", actualOfResult.getId());
    assertEquals("https://example.org/example", actualOfResult.getUrl());
  }
}
