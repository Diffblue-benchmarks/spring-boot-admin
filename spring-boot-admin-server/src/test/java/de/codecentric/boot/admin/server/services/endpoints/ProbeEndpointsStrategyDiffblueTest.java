package de.codecentric.boot.admin.server.services.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ProbeEndpointsStrategyDiffblueTest {
  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy#convert(InstanceId, ProbeEndpointsStrategy.EndpointDefinition, URI)}
   */
  @Test
  public void testConvert() throws AssertionError {
    // Arrange
    ProbeEndpointsStrategy probeEndpointsStrategy = new ProbeEndpointsStrategy(mock(InstanceWebClient.class),
        new String[]{"https://config.us-east-2.amazonaws.com"});
    InstanceId instanceId = InstanceId.of("42");

    // Act
    Function<ClientResponse, Mono<ProbeEndpointsStrategy.DetectedEndpoint>> actualConvertResult = probeEndpointsStrategy
        .convert(instanceId, new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"),
            PagerdutyNotifier.DEFAULT_URI);
    ClientResponse delegate = mock(ClientResponse.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(delegate.releaseBody()).thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));
    when(delegate.statusCode()).thenReturn(HttpStatusCode.valueOf(200));
    Mono<ProbeEndpointsStrategy.DetectedEndpoint> actualPublisher = actualConvertResult
        .apply(new ClientResponseWrapper(delegate));

    // Assert
    verify(delegate).releaseBody();
    verify(delegate).statusCode();
    StepVerifier.FirstStep<ProbeEndpointsStrategy.DetectedEndpoint> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  public void testConvert2() throws AssertionError {
    // Arrange
    ProbeEndpointsStrategy probeEndpointsStrategy = new ProbeEndpointsStrategy(mock(InstanceWebClient.class),
        new String[]{"https://config.us-east-2.amazonaws.com"});

    // Act and Assert
    StepVerifier.FirstStep<Endpoints> createResult = StepVerifier
        .create(probeEndpointsStrategy.convert(new ArrayList<>()));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  public void testConvert3() throws AssertionError {
    // Arrange
    ProbeEndpointsStrategy probeEndpointsStrategy = new ProbeEndpointsStrategy(mock(InstanceWebClient.class),
        new String[]{"https://config.us-east-2.amazonaws.com"});

    ArrayList<ProbeEndpointsStrategy.DetectedEndpoint> endpoints = new ArrayList<>();
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    String id = "42";
    String url = "https://example.org/example";
    Endpoint endpoint = Endpoint.of(id, url);
    endpoints.add(new ProbeEndpointsStrategy.DetectedEndpoint(definition, endpoint));

    // Act and Assert
    StepVerifier.FirstStep<Endpoints> createResult = StepVerifier.create(probeEndpointsStrategy.convert(endpoints));
    createResult.assertNext(e -> {
      Endpoints endpoints2 = e;
      Iterator<Endpoint> iteratorResult = endpoints2.iterator();
      Endpoint actualNextResult = iteratorResult.next();
      assertFalse(iteratorResult.hasNext());
      assertSame(endpoint, actualNextResult);
      Stream<Endpoint> streamResult = endpoints2.stream();
      List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
      assertEquals(1, collectResult.size());
      assertSame(endpoint, collectResult.get(0));
      return;
    }).expectComplete().verify();
  }

  /**
   * Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  public void testConvert4() throws AssertionError {
    // Arrange
    ProbeEndpointsStrategy probeEndpointsStrategy = new ProbeEndpointsStrategy(mock(InstanceWebClient.class),
        new String[]{"https://config.us-east-2.amazonaws.com"});

    ArrayList<ProbeEndpointsStrategy.DetectedEndpoint> endpoints = new ArrayList<>();
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    String id = "42";
    String url = "https://example.org/example";
    Endpoint endpoint = Endpoint.of(id, url);
    endpoints.add(new ProbeEndpointsStrategy.DetectedEndpoint(definition, endpoint));
    ProbeEndpointsStrategy.EndpointDefinition definition2 = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    endpoints.add(
        new ProbeEndpointsStrategy.DetectedEndpoint(definition2, Endpoint.of("42", "https://example.org/example")));

    // Act and Assert
    StepVerifier.FirstStep<Endpoints> createResult = StepVerifier.create(probeEndpointsStrategy.convert(endpoints));
    createResult.assertNext(e -> {
      Endpoints endpoints2 = e;
      Iterator<Endpoint> iteratorResult = endpoints2.iterator();
      Endpoint actualNextResult = iteratorResult.next();
      assertFalse(iteratorResult.hasNext());
      assertSame(endpoint, actualNextResult);
      Stream<Endpoint> streamResult = endpoints2.stream();
      List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
      assertEquals(1, collectResult.size());
      assertSame(endpoint, collectResult.get(0));
      return;
    }).expectComplete().verify();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#hashCode()}
   * </ul>
   */
  @Test
  public void testDetectedEndpointEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(definition,
        Endpoint.of("42", "https://example.org/example"));
    ProbeEndpointsStrategy.EndpointDefinition definition2 = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint2 = new ProbeEndpointsStrategy.DetectedEndpoint(definition2,
        Endpoint.of("42", "https://example.org/example"));

    // Act and Assert
    assertEquals(detectedEndpoint, detectedEndpoint2);
    int expectedHashCodeResult = detectedEndpoint.hashCode();
    assertEquals(expectedHashCodeResult, detectedEndpoint2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#hashCode()}
   * </ul>
   */
  @Test
  public void testDetectedEndpointEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(null,
        Endpoint.of("42", "https://example.org/example"));
    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint2 = new ProbeEndpointsStrategy.DetectedEndpoint(null,
        Endpoint.of("42", "https://example.org/example"));

    // Act and Assert
    assertEquals(detectedEndpoint, detectedEndpoint2);
    int expectedHashCodeResult = detectedEndpoint.hashCode();
    assertEquals(expectedHashCodeResult, detectedEndpoint2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#hashCode()}
   * </ul>
   */
  @Test
  public void testDetectedEndpointEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(
        new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"), null);
    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint2 = new ProbeEndpointsStrategy.DetectedEndpoint(
        new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"), null);

    // Act and Assert
    assertEquals(detectedEndpoint, detectedEndpoint2);
    int expectedHashCodeResult = detectedEndpoint.hashCode();
    assertEquals(expectedHashCodeResult, detectedEndpoint2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#hashCode()}
   * </ul>
   */
  @Test
  public void testDetectedEndpointEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(definition,
        Endpoint.of("42", "https://example.org/example"));

    // Act and Assert
    assertEquals(detectedEndpoint, detectedEndpoint);
    int expectedHashCodeResult = detectedEndpoint.hashCode();
    assertEquals(expectedHashCodeResult, detectedEndpoint.hashCode());
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   */
  @Test
  public void testDetectedEndpointEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("Path",
        "Path");

    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(definition,
        Endpoint.of("42", "https://example.org/example"));
    ProbeEndpointsStrategy.EndpointDefinition definition2 = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(detectedEndpoint,
        new ProbeEndpointsStrategy.DetectedEndpoint(definition2, Endpoint.of("42", "https://example.org/example")));
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   */
  @Test
  public void testDetectedEndpointEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(null,
        Endpoint.of("42", "https://example.org/example"));
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(detectedEndpoint,
        new ProbeEndpointsStrategy.DetectedEndpoint(definition, Endpoint.of("42", "https://example.org/example")));
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   */
  @Test
  public void testDetectedEndpointEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition definition = mock(ProbeEndpointsStrategy.EndpointDefinition.class);
    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(definition,
        Endpoint.of("42", "https://example.org/example"));
    ProbeEndpointsStrategy.EndpointDefinition definition2 = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(detectedEndpoint,
        new ProbeEndpointsStrategy.DetectedEndpoint(definition2, Endpoint.of("42", "https://example.org/example")));
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   */
  @Test
  public void testDetectedEndpointEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(definition,
        Endpoint.of("Path", "https://example.org/example"));
    ProbeEndpointsStrategy.EndpointDefinition definition2 = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(detectedEndpoint,
        new ProbeEndpointsStrategy.DetectedEndpoint(definition2, Endpoint.of("42", "https://example.org/example")));
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   */
  @Test
  public void testDetectedEndpointEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ProbeEndpointsStrategy.DetectedEndpoint detectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(
        new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"), null);
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(detectedEndpoint,
        new ProbeEndpointsStrategy.DetectedEndpoint(definition, Endpoint.of("42", "https://example.org/example")));
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   */
  @Test
  public void testDetectedEndpointEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(
        new ProbeEndpointsStrategy.DetectedEndpoint(definition, Endpoint.of("42", "https://example.org/example")),
        null);
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.DetectedEndpoint#equals(Object)}
   */
  @Test
  public void testDetectedEndpointEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(
        new ProbeEndpointsStrategy.DetectedEndpoint(definition, Endpoint.of("42", "https://example.org/example")),
        "Different type to DetectedEndpoint");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProbeEndpointsStrategy.DetectedEndpoint#DetectedEndpoint(ProbeEndpointsStrategy.EndpointDefinition, Endpoint)}
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#toString()}
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#getDefinition()}
   *   <li>{@link ProbeEndpointsStrategy.DetectedEndpoint#getEndpoint()}
   * </ul>
   */
  @Test
  public void testDetectedEndpointGettersAndSetters() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition definition = new ProbeEndpointsStrategy.EndpointDefinition("42", "Path");

    Endpoint endpoint = Endpoint.of("42", "https://example.org/example");

    // Act
    ProbeEndpointsStrategy.DetectedEndpoint actualDetectedEndpoint = new ProbeEndpointsStrategy.DetectedEndpoint(
        definition, endpoint);
    String actualToStringResult = actualDetectedEndpoint.toString();
    ProbeEndpointsStrategy.EndpointDefinition actualDefinition = actualDetectedEndpoint.getDefinition();

    // Assert
    assertEquals("ProbeEndpointsStrategy.DetectedEndpoint(definition=ProbeEndpointsStrategy.EndpointDefinition(id=42,"
        + " path=Path), endpoint=Endpoint(id=42, url=https://example.org/example))", actualToStringResult);
    assertSame(definition, actualDefinition);
    assertSame(endpoint, actualDetectedEndpoint.getEndpoint());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#hashCode()}
   * </ul>
   */
  @Test
  public void testEndpointDefinitionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition = new ProbeEndpointsStrategy.EndpointDefinition("42",
        "Path");
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition2 = new ProbeEndpointsStrategy.EndpointDefinition("42",
        "Path");

    // Act and Assert
    assertEquals(endpointDefinition, endpointDefinition2);
    int expectedHashCodeResult = endpointDefinition.hashCode();
    assertEquals(expectedHashCodeResult, endpointDefinition2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#hashCode()}
   * </ul>
   */
  @Test
  public void testEndpointDefinitionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition = new ProbeEndpointsStrategy.EndpointDefinition(null,
        "Path");
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition2 = new ProbeEndpointsStrategy.EndpointDefinition(null,
        "Path");

    // Act and Assert
    assertEquals(endpointDefinition, endpointDefinition2);
    int expectedHashCodeResult = endpointDefinition.hashCode();
    assertEquals(expectedHashCodeResult, endpointDefinition2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#hashCode()}
   * </ul>
   */
  @Test
  public void testEndpointDefinitionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition = new ProbeEndpointsStrategy.EndpointDefinition("42",
        null);
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition2 = new ProbeEndpointsStrategy.EndpointDefinition("42",
        null);

    // Act and Assert
    assertEquals(endpointDefinition, endpointDefinition2);
    int expectedHashCodeResult = endpointDefinition.hashCode();
    assertEquals(expectedHashCodeResult, endpointDefinition2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#hashCode()}
   * </ul>
   */
  @Test
  public void testEndpointDefinitionEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition = new ProbeEndpointsStrategy.EndpointDefinition("42",
        "Path");

    // Act and Assert
    assertEquals(endpointDefinition, endpointDefinition);
    int expectedHashCodeResult = endpointDefinition.hashCode();
    assertEquals(expectedHashCodeResult, endpointDefinition.hashCode());
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   */
  @Test
  public void testEndpointDefinitionEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition = new ProbeEndpointsStrategy.EndpointDefinition("Path",
        "Path");

    // Act and Assert
    assertNotEquals(endpointDefinition, new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"));
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   */
  @Test
  public void testEndpointDefinitionEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition = new ProbeEndpointsStrategy.EndpointDefinition(null,
        "Path");

    // Act and Assert
    assertNotEquals(endpointDefinition, new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"));
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   */
  @Test
  public void testEndpointDefinitionEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition = new ProbeEndpointsStrategy.EndpointDefinition("42",
        "42");

    // Act and Assert
    assertNotEquals(endpointDefinition, new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"));
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   */
  @Test
  public void testEndpointDefinitionEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ProbeEndpointsStrategy.EndpointDefinition endpointDefinition = new ProbeEndpointsStrategy.EndpointDefinition("42",
        null);

    // Act and Assert
    assertNotEquals(endpointDefinition, new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"));
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   */
  @Test
  public void testEndpointDefinitionEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"), null);
  }

  /**
   * Method under test:
   * {@link ProbeEndpointsStrategy.EndpointDefinition#equals(Object)}
   */
  @Test
  public void testEndpointDefinitionEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProbeEndpointsStrategy.EndpointDefinition("42", "Path"),
        "Different type to EndpointDefinition");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProbeEndpointsStrategy.EndpointDefinition#EndpointDefinition(String, String)}
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#toString()}
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#getId()}
   *   <li>{@link ProbeEndpointsStrategy.EndpointDefinition#getPath()}
   * </ul>
   */
  @Test
  public void testEndpointDefinitionGettersAndSetters() {
    // Arrange and Act
    ProbeEndpointsStrategy.EndpointDefinition actualEndpointDefinition = new ProbeEndpointsStrategy.EndpointDefinition(
        "42", "Path");
    String actualToStringResult = actualEndpointDefinition.toString();
    String actualId = actualEndpointDefinition.getId();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Path", actualEndpointDefinition.getPath());
    assertEquals("ProbeEndpointsStrategy.EndpointDefinition(id=42, path=Path)", actualToStringResult);
  }
}
