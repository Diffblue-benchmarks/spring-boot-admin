package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

class ReactiveRegistrationClientDiffblueTest {
  /**
   * Test {@link ReactiveRegistrationClient#setRequestHeaders(HttpHeaders)}.
   *
   * <p>Method under test: {@link ReactiveRegistrationClient#setRequestHeaders(HttpHeaders)}
   */
  @Test
  @DisplayName("Test setRequestHeaders(HttpHeaders)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveRegistrationClient.setRequestHeaders(HttpHeaders)"})
  void testSetRequestHeaders() {
    // Arrange
    ReactiveRegistrationClient reactiveRegistrationClient =
        new ReactiveRegistrationClient(mock(WebClient.class), Duration.ofSeconds(1L));
    HttpHeaders headers = new HttpHeaders();

    // Act
    reactiveRegistrationClient.setRequestHeaders(headers);

    // Assert
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult.size());
    assertEquals("application/json", getResult.get(0));
    assertEquals(getResult, headers.get(HttpHeaders.ACCEPT));
  }
}
