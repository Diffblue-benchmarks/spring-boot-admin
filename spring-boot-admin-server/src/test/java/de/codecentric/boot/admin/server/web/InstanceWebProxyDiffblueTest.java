package de.codecentric.boot.admin.server.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class InstanceWebProxyDiffblueTest {
  /**
   * Method under test:
   * {@link InstanceWebProxy#forward(Flux, InstanceWebProxy.ForwardRequest)}
   */
  @Test
  public void testForward() throws AssertionError {
    // Arrange
    InstanceWebProxy instanceWebProxy = new InstanceWebProxy(mock(InstanceWebClient.class));
    Flux<Instance> instances = Flux.fromIterable(new ArrayList<>());
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier
        .create(instanceWebProxy.forward(instances, new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
            method, new HttpHeaders(), mock(BodyInserter.class))));
    createResult.expectComplete().verify();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceWebProxy.ForwardRequest#equals(Object)}
   *   <li>{@link InstanceWebProxy.ForwardRequest#hashCode()}
   * </ul>
   */
  @Test
  public void testForwardRequestEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
        method, new HttpHeaders(), null);
    HttpMethod method2 = HttpMethod.valueOf("https://example.org/example");
    InstanceWebProxy.ForwardRequest forwardRequest2 = new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
        method2, new HttpHeaders(), null);

    // Act and Assert
    assertEquals(forwardRequest, forwardRequest2);
    int expectedHashCodeResult = forwardRequest.hashCode();
    assertEquals(expectedHashCodeResult, forwardRequest2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceWebProxy.ForwardRequest#equals(Object)}
   *   <li>{@link InstanceWebProxy.ForwardRequest#hashCode()}
   * </ul>
   */
  @Test
  public void testForwardRequestEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
        method, new HttpHeaders(), mock(BodyInserter.class));

    // Act and Assert
    assertEquals(forwardRequest, forwardRequest);
    int expectedHashCodeResult = forwardRequest.hashCode();
    assertEquals(expectedHashCodeResult, forwardRequest.hashCode());
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
        method, new HttpHeaders(), mock(BodyInserter.class));
    HttpMethod method2 = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(forwardRequest, new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method2,
        new HttpHeaders(), mock(BodyInserter.class)));
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    URI uri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(uri, method, new HttpHeaders(),
        mock(BodyInserter.class));
    HttpMethod method2 = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(forwardRequest, new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method2,
        new HttpHeaders(), mock(BodyInserter.class)));
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(null, method,
        new HttpHeaders(), mock(BodyInserter.class));
    HttpMethod method2 = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(forwardRequest, new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method2,
        new HttpHeaders(), mock(BodyInserter.class)));
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    HttpMethod method = HttpMethod.valueOf("Method");
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
        method, new HttpHeaders(), mock(BodyInserter.class));
    HttpMethod method2 = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(forwardRequest, new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method2,
        new HttpHeaders(), mock(BodyInserter.class)));
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
        null, new HttpHeaders(), mock(BodyInserter.class));
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(forwardRequest, new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method,
        new HttpHeaders(), mock(BodyInserter.class)));
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    HttpHeaders headers = new HttpHeaders();
    headers.add("https://example.org/example", "https://example.org/example");
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
        HttpMethod.valueOf("https://example.org/example"), headers, mock(BodyInserter.class));
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(forwardRequest, new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method,
        new HttpHeaders(), mock(BodyInserter.class)));
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
        method, new HttpHeaders(), null);
    HttpMethod method2 = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(forwardRequest, new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method2,
        new HttpHeaders(), mock(BodyInserter.class)));
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(null, method,
        new HttpHeaders(), mock(BodyInserter.class));
    HttpMethod method2 = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(forwardRequest,
        new InstanceWebProxy.ForwardRequest(null, method2, new HttpHeaders(), mock(BodyInserter.class)));
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    InstanceWebProxy.ForwardRequest forwardRequest = new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI,
        null, new HttpHeaders(), mock(BodyInserter.class));

    // Act and Assert
    assertNotEquals(forwardRequest, new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, null,
        new HttpHeaders(), mock(BodyInserter.class)));
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method, new HttpHeaders(),
        mock(BodyInserter.class)), null);
  }

  /**
   * Method under test: {@link InstanceWebProxy.ForwardRequest#equals(Object)}
   */
  @Test
  public void testForwardRequestEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    assertNotEquals(new InstanceWebProxy.ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method, new HttpHeaders(),
        mock(BodyInserter.class)), "Different type to ForwardRequest");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link InstanceWebProxy.ForwardRequest#ForwardRequest(URI, HttpMethod, HttpHeaders, BodyInserter)}
   *   <li>{@link InstanceWebProxy.ForwardRequest#toString()}
   *   <li>{@link InstanceWebProxy.ForwardRequest#getBody()}
   *   <li>{@link InstanceWebProxy.ForwardRequest#getHeaders()}
   *   <li>{@link InstanceWebProxy.ForwardRequest#getMethod()}
   *   <li>{@link InstanceWebProxy.ForwardRequest#getUri()}
   * </ul>
   */
  @Test
  public void testForwardRequestGettersAndSetters() {
    // Arrange
    URI uri = PagerdutyNotifier.DEFAULT_URI;
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    HttpHeaders headers = new HttpHeaders();
    BodyInserter<Object, ClientHttpRequest> body = mock(BodyInserter.class);

    // Act
    InstanceWebProxy.ForwardRequest actualForwardRequest = new InstanceWebProxy.ForwardRequest(uri, method, headers,
        body);
    actualForwardRequest.toString();
    BodyInserter<?, ? super ClientHttpRequest> actualBody = actualForwardRequest.getBody();
    HttpHeaders actualHeaders = actualForwardRequest.getHeaders();
    HttpMethod actualMethod = actualForwardRequest.getMethod();
    URI actualUri = actualForwardRequest.getUri();

    // Assert
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualUri.toString());
    assertSame(headers, actualHeaders);
    assertSame(method, actualMethod);
    assertSame(uri, actualUri);
    assertSame(body, actualBody);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceWebProxy.ForwardRequest.Builder#build()}
   *   <li>{@link InstanceWebProxy.ForwardRequest.Builder#body(BodyInserter)}
   *   <li>{@link InstanceWebProxy.ForwardRequest.Builder#headers(HttpHeaders)}
   *   <li>{@link InstanceWebProxy.ForwardRequest.Builder#method(HttpMethod)}
   *   <li>{@link InstanceWebProxy.ForwardRequest.Builder#uri(URI)}
   * </ul>
   */
  @Test
  public void testForwardRequest_BuilderBuild() {
    // Arrange
    InstanceWebProxy.ForwardRequest.Builder bodyResult = InstanceWebProxy.ForwardRequest.builder()
        .body(mock(BodyInserter.class));
    HttpHeaders headers = new HttpHeaders();
    InstanceWebProxy.ForwardRequest.Builder headersResult = bodyResult.headers(headers);
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");

    // Act
    InstanceWebProxy.ForwardRequest actualBuildResult = headersResult.method(method)
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();

    // Assert
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json",
        actualBuildResult.getUri().toString());
    assertSame(headers, actualBuildResult.getHeaders());
    assertSame(method, actualBuildResult.getMethod());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceWebProxy.InstanceResponse#equals(Object)}
   *   <li>{@link InstanceWebProxy.InstanceResponse#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceResponseEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        "Not all who wander are lost", "text/plain");
    InstanceWebProxy.InstanceResponse instanceResponse2 = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        "Not all who wander are lost", "text/plain");

    // Act and Assert
    assertEquals(instanceResponse, instanceResponse2);
    int expectedHashCodeResult = instanceResponse.hashCode();
    assertEquals(expectedHashCodeResult, instanceResponse2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceWebProxy.InstanceResponse#equals(Object)}
   *   <li>{@link InstanceWebProxy.InstanceResponse#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceResponseEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(null, 1,
        "Not all who wander are lost", "text/plain");
    InstanceWebProxy.InstanceResponse instanceResponse2 = new InstanceWebProxy.InstanceResponse(null, 1,
        "Not all who wander are lost", "text/plain");

    // Act and Assert
    assertEquals(instanceResponse, instanceResponse2);
    int expectedHashCodeResult = instanceResponse.hashCode();
    assertEquals(expectedHashCodeResult, instanceResponse2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceWebProxy.InstanceResponse#equals(Object)}
   *   <li>{@link InstanceWebProxy.InstanceResponse#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceResponseEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        null, "text/plain");
    InstanceWebProxy.InstanceResponse instanceResponse2 = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        null, "text/plain");

    // Act and Assert
    assertEquals(instanceResponse, instanceResponse2);
    int expectedHashCodeResult = instanceResponse.hashCode();
    assertEquals(expectedHashCodeResult, instanceResponse2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceWebProxy.InstanceResponse#equals(Object)}
   *   <li>{@link InstanceWebProxy.InstanceResponse#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceResponseEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        "Not all who wander are lost", null);
    InstanceWebProxy.InstanceResponse instanceResponse2 = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        "Not all who wander are lost", null);

    // Act and Assert
    assertEquals(instanceResponse, instanceResponse2);
    int expectedHashCodeResult = instanceResponse.hashCode();
    assertEquals(expectedHashCodeResult, instanceResponse2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceWebProxy.InstanceResponse#equals(Object)}
   *   <li>{@link InstanceWebProxy.InstanceResponse#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceResponseEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        "Not all who wander are lost", "text/plain");

    // Act and Assert
    assertEquals(instanceResponse, instanceResponse);
    int expectedHashCodeResult = instanceResponse.hashCode();
    assertEquals(expectedHashCodeResult, instanceResponse.hashCode());
  }

  /**
   * Method under test: {@link InstanceWebProxy.InstanceResponse#equals(Object)}
   */
  @Test
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(
        InstanceId.of("Not all who wander are lost"), 1, "Not all who wander are lost", "text/plain");

    // Act and Assert
    assertNotEquals(instanceResponse,
        new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1, "Not all who wander are lost", "text/plain"));
  }

  /**
   * Method under test: {@link InstanceWebProxy.InstanceResponse#equals(Object)}
   */
  @Test
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(null, 1,
        "Not all who wander are lost", "text/plain");

    // Act and Assert
    assertNotEquals(instanceResponse,
        new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1, "Not all who wander are lost", "text/plain"));
  }

  /**
   * Method under test: {@link InstanceWebProxy.InstanceResponse#equals(Object)}
   */
  @Test
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 3,
        "Not all who wander are lost", "text/plain");

    // Act and Assert
    assertNotEquals(instanceResponse,
        new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1, "Not all who wander are lost", "text/plain"));
  }

  /**
   * Method under test: {@link InstanceWebProxy.InstanceResponse#equals(Object)}
   */
  @Test
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        "42", "text/plain");

    // Act and Assert
    assertNotEquals(instanceResponse,
        new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1, "Not all who wander are lost", "text/plain"));
  }

  /**
   * Method under test: {@link InstanceWebProxy.InstanceResponse#equals(Object)}
   */
  @Test
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        null, "text/plain");

    // Act and Assert
    assertNotEquals(instanceResponse,
        new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1, "Not all who wander are lost", "text/plain"));
  }

  /**
   * Method under test: {@link InstanceWebProxy.InstanceResponse#equals(Object)}
   */
  @Test
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        "Not all who wander are lost", "Not all who wander are lost");

    // Act and Assert
    assertNotEquals(instanceResponse,
        new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1, "Not all who wander are lost", "text/plain"));
  }

  /**
   * Method under test: {@link InstanceWebProxy.InstanceResponse#equals(Object)}
   */
  @Test
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    InstanceWebProxy.InstanceResponse instanceResponse = new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1,
        "Not all who wander are lost", null);

    // Act and Assert
    assertNotEquals(instanceResponse,
        new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1, "Not all who wander are lost", "text/plain"));
  }

  /**
   * Method under test: {@link InstanceWebProxy.InstanceResponse#equals(Object)}
   */
  @Test
  public void testInstanceResponseEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1, "Not all who wander are lost", "text/plain"),
        null);
  }

  /**
   * Method under test: {@link InstanceWebProxy.InstanceResponse#equals(Object)}
   */
  @Test
  public void testInstanceResponseEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new InstanceWebProxy.InstanceResponse(InstanceId.of("42"), 1, "Not all who wander are lost", "text/plain"),
        "Different type to InstanceResponse");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link InstanceWebProxy.InstanceResponse#InstanceResponse(InstanceId, int, String, String)}
   *   <li>{@link InstanceWebProxy.InstanceResponse#toString()}
   *   <li>{@link InstanceWebProxy.InstanceResponse#getBody()}
   *   <li>{@link InstanceWebProxy.InstanceResponse#getContentType()}
   *   <li>{@link InstanceWebProxy.InstanceResponse#getInstanceId()}
   *   <li>{@link InstanceWebProxy.InstanceResponse#getStatus()}
   * </ul>
   */
  @Test
  public void testInstanceResponseGettersAndSetters() {
    // Arrange
    InstanceId instanceId = InstanceId.of("42");

    // Act
    InstanceWebProxy.InstanceResponse actualInstanceResponse = new InstanceWebProxy.InstanceResponse(instanceId, 1,
        "Not all who wander are lost", "text/plain");
    String actualToStringResult = actualInstanceResponse.toString();
    String actualBody = actualInstanceResponse.getBody();
    String actualContentType = actualInstanceResponse.getContentType();
    InstanceId actualInstanceId = actualInstanceResponse.getInstanceId();

    // Assert
    assertEquals("InstanceWebProxy.InstanceResponse(instanceId=42, status=1, body=Not all who wander are lost,"
        + " contentType=text/plain)", actualToStringResult);
    assertEquals("Not all who wander are lost", actualBody);
    assertEquals("text/plain", actualContentType);
    assertEquals(1, actualInstanceResponse.getStatus());
    assertSame(instanceId, actualInstanceId);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceWebProxy.InstanceResponse.Builder#build()}
   *   <li>{@link InstanceWebProxy.InstanceResponse.Builder#body(String)}
   *   <li>{@link InstanceWebProxy.InstanceResponse.Builder#contentType(String)}
   *   <li>{@link InstanceWebProxy.InstanceResponse.Builder#instanceId(InstanceId)}
   *   <li>{@link InstanceWebProxy.InstanceResponse.Builder#status(int)}
   * </ul>
   */
  @Test
  public void testInstanceResponse_BuilderBuild() {
    // Arrange
    InstanceWebProxy.InstanceResponse.Builder contentTypeResult = InstanceWebProxy.InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceId instanceId = InstanceId.of("42");

    // Act
    InstanceWebProxy.InstanceResponse actualBuildResult = contentTypeResult.instanceId(instanceId).status(1).build();

    // Assert
    assertEquals("Not all who wander are lost", actualBuildResult.getBody());
    assertEquals("text/plain", actualBuildResult.getContentType());
    assertEquals(1, actualBuildResult.getStatus());
    assertSame(instanceId, actualBuildResult.getInstanceId());
  }
}
