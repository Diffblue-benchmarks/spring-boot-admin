package de.codecentric.boot.admin.server.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

class HttpHeaderFilterDiffblueTest {
  /**
   * Test {@link HttpHeaderFilter#filterHeaders(HttpHeaders)}.
   *
   * <ul>
   *   <li>Given {@code Header Name}.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link HttpHeaderFilter#filterHeaders(HttpHeaders)}
   */
  @Test
  @DisplayName("Test filterHeaders(HttpHeaders); given 'Header Name'; then return size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders HttpHeaderFilter.filterHeaders(HttpHeaders)"})
  void testFilterHeaders_givenHeaderName_thenReturnSizeIsTwo() {
    // Arrange
    HttpHeaderFilter httpHeaderFilter = new HttpHeaderFilter(new HashSet<>());

    HttpHeaders headers = new HttpHeaders();
    headers.add("Header Name", "42");
    headers.add("https://example.org/example", "https://example.org/example");

    // Act
    HttpHeaders actualFilterHeadersResult = httpHeaderFilter.filterHeaders(headers);

    // Assert
    assertEquals(2, actualFilterHeadersResult.size());
    List<String> getResult = actualFilterHeadersResult.get("Header Name");
    assertEquals(1, getResult.size());
    assertEquals("42", getResult.get(0));
    List<String> getResult2 = actualFilterHeadersResult.get("https://example.org/example");
    assertEquals(1, getResult2.size());
    assertEquals("https://example.org/example", getResult2.get(0));
  }

  /**
   * Test {@link HttpHeaderFilter#filterHeaders(HttpHeaders)}.
   *
   * <ul>
   *   <li>Given {@code https://example.org/example}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link HttpHeaderFilter#filterHeaders(HttpHeaders)}
   */
  @Test
  @DisplayName(
      "Test filterHeaders(HttpHeaders); given 'https://example.org/example'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders HttpHeaderFilter.filterHeaders(HttpHeaders)"})
  void testFilterHeaders_givenHttpsExampleOrgExample_thenReturnSizeIsOne() {
    // Arrange
    HttpHeaderFilter httpHeaderFilter = new HttpHeaderFilter(new HashSet<>());

    HttpHeaders headers = new HttpHeaders();
    headers.add("https://example.org/example", "https://example.org/example");

    // Act
    HttpHeaders actualFilterHeadersResult = httpHeaderFilter.filterHeaders(headers);

    // Assert
    assertEquals(1, actualFilterHeadersResult.size());
    List<String> getResult = actualFilterHeadersResult.get("https://example.org/example");
    assertEquals(1, getResult.size());
    assertEquals("https://example.org/example", getResult.get(0));
  }

  /**
   * Test {@link HttpHeaderFilter#filterHeaders(HttpHeaders)}.
   *
   * <ul>
   *   <li>When {@link HttpHeaders#HttpHeaders()}.
   *   <li>Then return {@link HttpHeaders#HttpHeaders()}.
   * </ul>
   *
   * <p>Method under test: {@link HttpHeaderFilter#filterHeaders(HttpHeaders)}
   */
  @Test
  @DisplayName("Test filterHeaders(HttpHeaders); when HttpHeaders(); then return HttpHeaders()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders HttpHeaderFilter.filterHeaders(HttpHeaders)"})
  void testFilterHeaders_whenHttpHeaders_thenReturnHttpHeaders() {
    // Arrange
    HttpHeaderFilter httpHeaderFilter = new HttpHeaderFilter(new HashSet<>());
    HttpHeaders headers = new HttpHeaders();

    // Act
    HttpHeaders actualFilterHeadersResult = httpHeaderFilter.filterHeaders(headers);

    // Assert
    assertEquals(headers, actualFilterHeadersResult);
  }
}
