package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {BlockingRegistrationClient.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BlockingRegistrationClientDiffblueTest {
  @Autowired
  private BlockingRegistrationClient blockingRegistrationClient;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Method under test:
   * {@link BlockingRegistrationClient#deregister(String, String)}
   */
  @Test
  void testDeregister() throws RestClientException {
    // Arrange
    doNothing().when(restTemplate).delete(Mockito.<String>any(), isA(Object[].class));

    // Act
    blockingRegistrationClient.deregister("https://example.org/example", "42");

    // Assert that nothing has changed
    verify(restTemplate).delete(eq("https://example.org/example/42"), isA(Object[].class));
  }

  /**
   * Method under test: {@link BlockingRegistrationClient#createRequestHeaders()}
   */
  @Test
  void testCreateRequestHeaders() {
    // Arrange and Act
    HttpHeaders actualCreateRequestHeadersResult = blockingRegistrationClient.createRequestHeaders();

    // Assert
    assertEquals(2, actualCreateRequestHeadersResult.size());
    List<String> getResult = actualCreateRequestHeadersResult.get(HttpHeaders.ACCEPT);
    assertEquals(1, getResult.size());
    assertEquals("application/json", getResult.get(0));
    List<String> getResult2 = actualCreateRequestHeadersResult.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
  }
}
