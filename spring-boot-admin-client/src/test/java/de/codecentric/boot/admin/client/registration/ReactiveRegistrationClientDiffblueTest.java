package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

class ReactiveRegistrationClientDiffblueTest {
  /**
   * Method under test:
   * {@link ReactiveRegistrationClient#setRequestHeaders(HttpHeaders)}
   */
  @Test
  void testSetRequestHeaders() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ReactiveRegistrationClient reactiveRegistrationClient = new ReactiveRegistrationClient(mock(WebClient.class), null);
    HttpHeaders headers = new HttpHeaders();

    // Act
    reactiveRegistrationClient.setRequestHeaders(headers);

    // Assert
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.ACCEPT);
    assertEquals(1, getResult.size());
    assertEquals("application/json", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
  }
}
