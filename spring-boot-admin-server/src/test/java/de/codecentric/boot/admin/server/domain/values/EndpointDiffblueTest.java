package de.codecentric.boot.admin.server.domain.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EndpointDiffblueTest {
  /**
   * Test {@link Endpoint#of(String, String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Endpoint#of(String, String)}
   */
  @Test
  @DisplayName("Test of(String, String); when '42'; then return Id is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoint Endpoint.of(String, String)"})
  void testOf_when42_thenReturnIdIs42() {
    // Arrange and Act
    Endpoint actualOfResult = Endpoint.of("42", "https://example.org/example");

    // Assert
    assertEquals("42", actualOfResult.getId());
    assertEquals("https://example.org/example", actualOfResult.getUrl());
  }
}
