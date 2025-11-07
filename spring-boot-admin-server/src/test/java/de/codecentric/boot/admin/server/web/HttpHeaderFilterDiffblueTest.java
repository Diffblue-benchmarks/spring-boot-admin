package de.codecentric.boot.admin.server.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.List;
import org.junit.Test;
import org.springframework.http.HttpHeaders;

public class HttpHeaderFilterDiffblueTest {
  /**
   * Method under test: {@link HttpHeaderFilter#filterHeaders(HttpHeaders)}
   */
  @Test
  public void testFilterHeaders() {
    // Arrange
    HttpHeaderFilter httpHeaderFilter = new HttpHeaderFilter(new HashSet<>());

    // Act and Assert
    assertTrue(httpHeaderFilter.filterHeaders(new HttpHeaders()).isEmpty());
  }

  /**
   * Method under test: {@link HttpHeaderFilter#filterHeaders(HttpHeaders)}
   */
  @Test
  public void testFilterHeaders2() {
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
   * Method under test: {@link HttpHeaderFilter#filterHeaders(HttpHeaders)}
   */
  @Test
  public void testFilterHeaders3() {
    // Arrange
    HttpHeaderFilter httpHeaderFilter = new HttpHeaderFilter(new HashSet<>());

    HttpHeaders headers = new HttpHeaders();
    headers.add("Header Name", "42");
    headers.add("https://example.org/example", "https://example.org/example");

    // Act and Assert
    assertEquals(headers, httpHeaderFilter.filterHeaders(headers));
  }
}
