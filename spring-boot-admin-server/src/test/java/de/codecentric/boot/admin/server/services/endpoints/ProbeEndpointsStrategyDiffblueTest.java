package de.codecentric.boot.admin.server.services.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.services.endpoints.ProbeEndpointsStrategy.DetectedEndpoint;
import de.codecentric.boot.admin.server.services.endpoints.ProbeEndpointsStrategy.EndpointDefinition;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

public class ProbeEndpointsStrategyDiffblueTest {
  /**
   * Test {@link ProbeEndpointsStrategy#convert(List)} with {@code endpoints}.
   * <ul>
   *   <li>Given {@link EndpointDefinition#EndpointDefinition(String, String)} with id is {@code 42} and {@code Path}.</li>
   *   <li>Then calls {@link Builder#build()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono ProbeEndpointsStrategy.convert(List)"})
  public void testConvertWithEndpoints_givenEndpointDefinitionWithIdIs42AndPath_thenCallsBuild() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    ProbeEndpointsStrategy probeEndpointsStrategy = new ProbeEndpointsStrategy(instanceWebClient,
        new String[]{"https://config.us-east-2.amazonaws.com"});

    ArrayList<DetectedEndpoint> endpoints = new ArrayList<>();
    EndpointDefinition definition = new EndpointDefinition("42", "Path");

    String id = "42";
    String url = "https://example.org/example";
    Endpoint endpoint = Endpoint.of(id, url);
    endpoints.add(new DetectedEndpoint(definition, endpoint));

    // Act and Assert
    FirstStep<Endpoints> createResult = StepVerifier.create(probeEndpointsStrategy.convert(endpoints));
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
    verify(builder).build();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#convert(List)} with {@code endpoints}.
   * <ul>
   *   <li>Given {@link EndpointDefinition#EndpointDefinition(String, String)} with id is {@code 42} and {@code Path}.</li>
   *   <li>Then calls {@link Builder#build()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono ProbeEndpointsStrategy.convert(List)"})
  public void testConvertWithEndpoints_givenEndpointDefinitionWithIdIs42AndPath_thenCallsBuild2()
      throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    ProbeEndpointsStrategy probeEndpointsStrategy = new ProbeEndpointsStrategy(instanceWebClient,
        new String[]{"https://config.us-east-2.amazonaws.com"});

    ArrayList<DetectedEndpoint> endpoints = new ArrayList<>();
    EndpointDefinition definition = new EndpointDefinition("42", "Path");

    String id = "42";
    String url = "https://example.org/example";
    Endpoint endpoint = Endpoint.of(id, url);
    endpoints.add(new DetectedEndpoint(definition, endpoint));
    EndpointDefinition definition2 = new EndpointDefinition("42", "Path");

    endpoints.add(new DetectedEndpoint(definition2, Endpoint.of("42", "https://example.org/example")));

    // Act and Assert
    FirstStep<Endpoints> createResult = StepVerifier.create(probeEndpointsStrategy.convert(endpoints));
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
    verify(builder).build();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#convert(List)} with {@code endpoints}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link Builder#build()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono ProbeEndpointsStrategy.convert(List)"})
  public void testConvertWithEndpoints_whenArrayList_thenCallsBuild() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    ProbeEndpointsStrategy probeEndpointsStrategy = new ProbeEndpointsStrategy(instanceWebClient,
        new String[]{"https://config.us-east-2.amazonaws.com"});

    // Act and Assert
    FirstStep<Endpoints> createResult = StepVerifier.create(probeEndpointsStrategy.convert(new ArrayList<>()));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#convert(InstanceId, EndpointDefinition, URI)} with {@code instanceId}, {@code endpointDefinition}, {@code uri}.
   * <ul>
   *   <li>Then calls {@link ClientResponse#releaseBody()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProbeEndpointsStrategy#convert(InstanceId, EndpointDefinition, URI)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Function ProbeEndpointsStrategy.convert(InstanceId, EndpointDefinition, URI)"})
  public void testConvertWithInstanceIdEndpointDefinitionUri_thenCallsReleaseBody() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    ProbeEndpointsStrategy probeEndpointsStrategy = new ProbeEndpointsStrategy(instanceWebClient,
        new String[]{"https://config.us-east-2.amazonaws.com"});
    InstanceId instanceId = InstanceId.of("42");

    // Act
    Function<ClientResponse, Mono<DetectedEndpoint>> actualConvertResult = probeEndpointsStrategy.convert(instanceId,
        new EndpointDefinition("42", "Path"), PagerdutyNotifier.DEFAULT_URI);
    ClientResponse delegate = mock(ClientResponse.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(delegate.releaseBody()).thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));
    when(delegate.statusCode()).thenReturn(HttpStatusCode.valueOf(200));
    Mono<DetectedEndpoint> actualPublisher = actualConvertResult.apply(new ClientResponseWrapper(delegate));

    // Assert
    verify(delegate).releaseBody();
    verify(delegate).statusCode();
    verify(builder).build();
    FirstStep<DetectedEndpoint> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test DetectedEndpoint {@link DetectedEndpoint#equals(Object)}, and {@link DetectedEndpoint#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DetectedEndpoint#equals(Object)}
   *   <li>{@link DetectedEndpoint#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DetectedEndpoint.equals(Object)", "int DetectedEndpoint.hashCode()"})
  public void testDetectedEndpointEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    EndpointDefinition definition = new EndpointDefinition("42", "Path");

    DetectedEndpoint detectedEndpoint = new DetectedEndpoint(definition,
        Endpoint.of("42", "https://example.org/example"));
    EndpointDefinition definition2 = new EndpointDefinition("42", "Path");

    DetectedEndpoint detectedEndpoint2 = new DetectedEndpoint(definition2,
        Endpoint.of("42", "https://example.org/example"));

    // Act and Assert
    assertEquals(detectedEndpoint, detectedEndpoint2);
    int expectedHashCodeResult = detectedEndpoint.hashCode();
    assertEquals(expectedHashCodeResult, detectedEndpoint2.hashCode());
  }

  /**
   * Test DetectedEndpoint {@link DetectedEndpoint#equals(Object)}, and {@link DetectedEndpoint#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DetectedEndpoint#equals(Object)}
   *   <li>{@link DetectedEndpoint#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DetectedEndpoint.equals(Object)", "int DetectedEndpoint.hashCode()"})
  public void testDetectedEndpointEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    EndpointDefinition definition = new EndpointDefinition("42", "Path");

    DetectedEndpoint detectedEndpoint = new DetectedEndpoint(definition,
        Endpoint.of("42", "https://example.org/example"));

    // Act and Assert
    assertEquals(detectedEndpoint, detectedEndpoint);
    int expectedHashCodeResult = detectedEndpoint.hashCode();
    assertEquals(expectedHashCodeResult, detectedEndpoint.hashCode());
  }

  /**
   * Test DetectedEndpoint {@link DetectedEndpoint#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link DetectedEndpoint#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DetectedEndpoint.equals(Object)", "int DetectedEndpoint.hashCode()"})
  public void testDetectedEndpointEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    EndpointDefinition definition = new EndpointDefinition("Path", "Path");

    DetectedEndpoint detectedEndpoint = new DetectedEndpoint(definition,
        Endpoint.of("42", "https://example.org/example"));
    EndpointDefinition definition2 = new EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(detectedEndpoint,
        new DetectedEndpoint(definition2, Endpoint.of("42", "https://example.org/example")));
  }

  /**
   * Test DetectedEndpoint {@link DetectedEndpoint#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link DetectedEndpoint#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DetectedEndpoint.equals(Object)", "int DetectedEndpoint.hashCode()"})
  public void testDetectedEndpointEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    EndpointDefinition definition = new EndpointDefinition("42", "Path");

    DetectedEndpoint detectedEndpoint = new DetectedEndpoint(definition,
        Endpoint.of("Path", "https://example.org/example"));
    EndpointDefinition definition2 = new EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(detectedEndpoint,
        new DetectedEndpoint(definition2, Endpoint.of("42", "https://example.org/example")));
  }

  /**
   * Test DetectedEndpoint {@link DetectedEndpoint#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link DetectedEndpoint#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DetectedEndpoint.equals(Object)", "int DetectedEndpoint.hashCode()"})
  public void testDetectedEndpointEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    EndpointDefinition definition = new EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(new DetectedEndpoint(definition, Endpoint.of("42", "https://example.org/example")), null);
  }

  /**
   * Test DetectedEndpoint {@link DetectedEndpoint#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link DetectedEndpoint#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DetectedEndpoint.equals(Object)", "int DetectedEndpoint.hashCode()"})
  public void testDetectedEndpointEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    EndpointDefinition definition = new EndpointDefinition("42", "Path");

    // Act and Assert
    assertNotEquals(new DetectedEndpoint(definition, Endpoint.of("42", "https://example.org/example")),
        "Different type to DetectedEndpoint");
  }

  /**
   * Test DetectedEndpoint getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DetectedEndpoint#DetectedEndpoint(EndpointDefinition, Endpoint)}
   *   <li>{@link DetectedEndpoint#toString()}
   *   <li>{@link DetectedEndpoint#getDefinition()}
   *   <li>{@link DetectedEndpoint#getEndpoint()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DetectedEndpoint.<init>(EndpointDefinition, Endpoint)",
      "EndpointDefinition DetectedEndpoint.getDefinition()", "Endpoint DetectedEndpoint.getEndpoint()",
      "String DetectedEndpoint.toString()"})
  public void testDetectedEndpointGettersAndSetters() {
    // Arrange
    EndpointDefinition definition = new EndpointDefinition("42", "Path");

    Endpoint endpoint = Endpoint.of("42", "https://example.org/example");

    // Act
    DetectedEndpoint actualDetectedEndpoint = new DetectedEndpoint(definition, endpoint);
    String actualToStringResult = actualDetectedEndpoint.toString();
    EndpointDefinition actualDefinition = actualDetectedEndpoint.getDefinition();

    // Assert
    assertEquals("ProbeEndpointsStrategy.DetectedEndpoint(definition=ProbeEndpointsStrategy.EndpointDefinition(id=42,"
        + " path=Path), endpoint=Endpoint(id=42, url=https://example.org/example))", actualToStringResult);
    assertSame(definition, actualDefinition);
    assertSame(endpoint, actualDetectedEndpoint.getEndpoint());
  }

  /**
   * Test EndpointDefinition {@link EndpointDefinition#equals(Object)}, and {@link EndpointDefinition#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EndpointDefinition#equals(Object)}
   *   <li>{@link EndpointDefinition#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointDefinition.equals(Object)", "int EndpointDefinition.hashCode()"})
  public void testEndpointDefinitionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    EndpointDefinition endpointDefinition = new EndpointDefinition("42", "Path");
    EndpointDefinition endpointDefinition2 = new EndpointDefinition("42", "Path");

    // Act and Assert
    assertEquals(endpointDefinition, endpointDefinition2);
    int expectedHashCodeResult = endpointDefinition.hashCode();
    assertEquals(expectedHashCodeResult, endpointDefinition2.hashCode());
  }

  /**
   * Test EndpointDefinition {@link EndpointDefinition#equals(Object)}, and {@link EndpointDefinition#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EndpointDefinition#equals(Object)}
   *   <li>{@link EndpointDefinition#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointDefinition.equals(Object)", "int EndpointDefinition.hashCode()"})
  public void testEndpointDefinitionEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    EndpointDefinition endpointDefinition = new EndpointDefinition("42", "Path");

    // Act and Assert
    assertEquals(endpointDefinition, endpointDefinition);
    int expectedHashCodeResult = endpointDefinition.hashCode();
    assertEquals(expectedHashCodeResult, endpointDefinition.hashCode());
  }

  /**
   * Test EndpointDefinition {@link EndpointDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointDefinition#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointDefinition.equals(Object)", "int EndpointDefinition.hashCode()"})
  public void testEndpointDefinitionEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    EndpointDefinition endpointDefinition = new EndpointDefinition("Path", "Path");

    // Act and Assert
    assertNotEquals(endpointDefinition, new EndpointDefinition("42", "Path"));
  }

  /**
   * Test EndpointDefinition {@link EndpointDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointDefinition#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointDefinition.equals(Object)", "int EndpointDefinition.hashCode()"})
  public void testEndpointDefinitionEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    EndpointDefinition endpointDefinition = new EndpointDefinition("42", "42");

    // Act and Assert
    assertNotEquals(endpointDefinition, new EndpointDefinition("42", "Path"));
  }

  /**
   * Test EndpointDefinition {@link EndpointDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointDefinition#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointDefinition.equals(Object)", "int EndpointDefinition.hashCode()"})
  public void testEndpointDefinitionEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new EndpointDefinition("42", "Path"), null);
  }

  /**
   * Test EndpointDefinition {@link EndpointDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointDefinition#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointDefinition.equals(Object)", "int EndpointDefinition.hashCode()"})
  public void testEndpointDefinitionEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new EndpointDefinition("42", "Path"), "Different type to EndpointDefinition");
  }

  /**
   * Test EndpointDefinition getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EndpointDefinition#EndpointDefinition(String, String)}
   *   <li>{@link EndpointDefinition#toString()}
   *   <li>{@link EndpointDefinition#getId()}
   *   <li>{@link EndpointDefinition#getPath()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EndpointDefinition.<init>(String, String)", "String EndpointDefinition.getId()",
      "String EndpointDefinition.getPath()", "String EndpointDefinition.toString()"})
  public void testEndpointDefinitionGettersAndSetters() {
    // Arrange and Act
    EndpointDefinition actualEndpointDefinition = new EndpointDefinition("42", "Path");
    String actualToStringResult = actualEndpointDefinition.toString();
    String actualId = actualEndpointDefinition.getId();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Path", actualEndpointDefinition.getPath());
    assertEquals("ProbeEndpointsStrategy.EndpointDefinition(id=42, path=Path)", actualToStringResult);
  }
}
