package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;

@ContextConfiguration(classes = {RestClientRegistrationClient.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RestClientRegistrationClientDiffblueTest {
  @MockBean
  private RestClient restClient;

  @Autowired
  private RestClientRegistrationClient restClientRegistrationClient;

  /**
   * Method under test:
   * {@link RestClientRegistrationClient#register(String, Application)}
   */
  @Test
  void testRegister() {
    // Arrange
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("id", "42");
    RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
    when(responseSpec.body(Mockito.<ParameterizedTypeReference<Map<String, Object>>>any())).thenReturn(stringObjectMap);
    RestClient.RequestBodySpec requestBodySpec = mock(RestClient.RequestBodySpec.class);
    when(requestBodySpec.retrieve()).thenReturn(responseSpec);
    RestClient.RequestBodySpec requestBodySpec2 = mock(RestClient.RequestBodySpec.class);
    when(requestBodySpec2.body(Mockito.<Object>any())).thenReturn(requestBodySpec);
    RestClient.RequestBodySpec requestBodySpec3 = mock(RestClient.RequestBodySpec.class);
    when(requestBodySpec3.headers(Mockito.<Consumer<HttpHeaders>>any())).thenReturn(requestBodySpec2);
    RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
    when(requestBodyUriSpec.uri(Mockito.<String>any(), isA(Object[].class))).thenReturn(requestBodySpec3);
    when(restClient.post()).thenReturn(requestBodyUriSpec);

    // Act
    String actualRegisterResult = restClientRegistrationClient.register("https://example.org/example",
        new Application("Name", "https://example.org/example", "https://example.org/example",
            "https://example.org/example", new HashMap<>()));

    // Assert
    verify(restClient).post();
    verify(requestBodySpec2).body(isA(Object.class));
    verify(requestBodySpec3).headers(isA(Consumer.class));
    verify(requestBodySpec).retrieve();
    verify(responseSpec).body(isA(ParameterizedTypeReference.class));
    verify(requestBodyUriSpec).uri(eq("https://example.org/example"), isA(Object[].class));
    assertEquals("42", actualRegisterResult);
  }

  /**
   * Method under test:
   * {@link RestClientRegistrationClient#deregister(String, String)}
   */
  @Test
  void testDeregister() {
    // Arrange
    RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
    when(responseSpec.toBodilessEntity()).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
    RestClient.RequestBodySpec requestBodySpec = mock(RestClient.RequestBodySpec.class);
    when(requestBodySpec.retrieve()).thenReturn(responseSpec);
    RestClient.RequestHeadersUriSpec<RestClient.RequestBodySpec> requestHeadersUriSpec = mock(
        RestClient.RequestHeadersUriSpec.class);
    when(requestHeadersUriSpec.uri(Mockito.<String>any(), isA(Object[].class))).thenReturn(requestBodySpec);
    Mockito.<RestClient.RequestHeadersUriSpec<?>>when(restClient.delete()).thenReturn(requestHeadersUriSpec);

    // Act
    restClientRegistrationClient.deregister("https://example.org/example", "42");

    // Assert
    verify(restClient).delete();
    verify(requestBodySpec).retrieve();
    verify(responseSpec).toBodilessEntity();
    verify(requestHeadersUriSpec).uri(eq("https://example.org/example/42"), isA(Object[].class));
  }

  /**
   * Method under test:
   * {@link RestClientRegistrationClient#setRequestHeaders(HttpHeaders)}
   */
  @Test
  void testSetRequestHeaders() {
    // Arrange
    HttpHeaders headers = new HttpHeaders();

    // Act
    restClientRegistrationClient.setRequestHeaders(headers);

    // Assert
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.ACCEPT);
    assertEquals(1, getResult.size());
    assertEquals("application/json", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
  }

  /**
   * Method under test:
   * {@link RestClientRegistrationClient#setRequestHeaders(HttpHeaders)}
   */
  @Test
  void testSetRequestHeaders2() {
    // Arrange
    HashMap<String, List<String>> map = new HashMap<>();
    map.computeIfPresent("foo", mock(BiFunction.class));

    HttpHeaders headers = new HttpHeaders();
    headers.putAll(map);

    // Act
    restClientRegistrationClient.setRequestHeaders(headers);

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
