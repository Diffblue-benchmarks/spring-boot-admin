package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EndpointMixinDiffblueTest {
  /**
   * Test {@link EndpointMixin#of(String, String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointMixin#of(String, String)}
   */
  @Test
  @DisplayName("Test of(String, String); when '42'; then return Id is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoint EndpointMixin.of(String, String)"})
  void testOf_when42_thenReturnIdIs42() {
    // Arrange and Act
    Endpoint actualOfResult = EndpointMixin.of("42", "https://example.org/example");

    // Assert
    assertEquals("42", actualOfResult.getId());
    assertEquals("https://example.org/example", actualOfResult.getUrl());
  }
}
