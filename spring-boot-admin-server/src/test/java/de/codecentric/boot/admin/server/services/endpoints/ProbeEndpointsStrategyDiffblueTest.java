package de.codecentric.boot.admin.server.services.endpoints;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class ProbeEndpointsStrategyDiffblueTest {
  /**
   * Test {@link ProbeEndpointsStrategy#ProbeEndpointsStrategy(InstanceWebClient, String[])}.
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#ProbeEndpointsStrategy(InstanceWebClient,
   * String[])}
   */
  @Test
  @DisplayName("Test new ProbeEndpointsStrategy(InstanceWebClient, String[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProbeEndpointsStrategy.<init>(InstanceWebClient, String[])"})
  void testNewProbeEndpointsStrategy() {
    // Arrange
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    // Act
    new ProbeEndpointsStrategy(mock(InstanceWebClient.class), endpoints);

    // Assert that nothing has changed
    assertArrayEquals(new String[] {"https://config.us-east-2.amazonaws.com"}, endpoints);
  }

  /**
   * Test {@link ProbeEndpointsStrategy#ProbeEndpointsStrategy(InstanceWebClient, String[])}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#ProbeEndpointsStrategy(InstanceWebClient,
   * String[])}
   */
  @Test
  @DisplayName("Test new ProbeEndpointsStrategy(InstanceWebClient, String[]); then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProbeEndpointsStrategy.<init>(InstanceWebClient, String[])"})
  void testNewProbeEndpointsStrategy_thenCallsGetId() throws AssertionError {
    // Arrange
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    // Act
    ProbeEndpointsStrategy actualProbeEndpointsStrategy =
        new ProbeEndpointsStrategy(mock(InstanceWebClient.class), endpoints);
    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl(null)
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    Mono<Endpoints> actualPublisher = actualProbeEndpointsStrategy.detectEndpoints(instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    assertArrayEquals(new String[] {"https://config.us-east-2.amazonaws.com"}, endpoints);
    FirstStep<Endpoints> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#ProbeEndpointsStrategy(InstanceWebClient, String[])}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getRegistration()}.
   * </ul>
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#ProbeEndpointsStrategy(InstanceWebClient,
   * String[])}
   */
  @Test
  @DisplayName(
      "Test new ProbeEndpointsStrategy(InstanceWebClient, String[]); then calls getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProbeEndpointsStrategy.<init>(InstanceWebClient, String[])"})
  void testNewProbeEndpointsStrategy_thenCallsGetRegistration() throws AssertionError {
    // Arrange
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    // Act
    ProbeEndpointsStrategy actualProbeEndpointsStrategy =
        new ProbeEndpointsStrategy(mock(InstanceWebClient.class), endpoints);
    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    Mono<Endpoints> actualPublisher = actualProbeEndpointsStrategy.detectEndpoints(instance);

    // Assert
    verify(instance).getRegistration();
    assertArrayEquals(new String[] {"https://config.us-east-2.amazonaws.com"}, endpoints);
    FirstStep<Endpoints> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#detectEndpoints(Instance)}.
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#detectEndpoints(Instance)}
   */
  @Test
  @DisplayName("Test detectEndpoints(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ProbeEndpointsStrategy.detectEndpoints(Instance)"})
  void testDetectEndpoints() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    ProbeEndpointsStrategy probeEndpointsStrategy =
        new ProbeEndpointsStrategy(instanceWebClient, endpoints);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(probeEndpointsStrategy.detectEndpoints(instance));
    StepVerifier expectErrorResult = createResult.expectError();
    verify(instance).getRegistration();
    verify(builder).build();
    expectErrorResult.verify();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#detectEndpoints(Instance)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link Instance#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#detectEndpoints(Instance)}
   */
  @Test
  @DisplayName(
      "Test detectEndpoints(Instance); given InstanceId with value is '42'; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ProbeEndpointsStrategy.detectEndpoints(Instance)"})
  void testDetectEndpoints_givenInstanceIdWithValueIs42_thenCallsGetId() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    ProbeEndpointsStrategy probeEndpointsStrategy =
        new ProbeEndpointsStrategy(instanceWebClient, endpoints);

    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl(null)
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(probeEndpointsStrategy.detectEndpoints(instance));
    createResult.expectComplete().verify();
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(builder).build();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#convert(List)} with {@code endpoints}.
   *
   * <ul>
   *   <li>Given {@link EndpointDefinition#EndpointDefinition(String, String)} with id is {@code 42}
   *       and {@code Path}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'endpoints'; given EndpointDefinition(String, String) with id is '42' and 'Path'; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ProbeEndpointsStrategy.convert(List)"})
  void testConvertWithEndpoints_givenEndpointDefinitionWithIdIs42AndPath_thenCallsBuild()
      throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    ProbeEndpointsStrategy probeEndpointsStrategy =
        new ProbeEndpointsStrategy(instanceWebClient, endpoints);

    ArrayList<DetectedEndpoint> endpoints2 = new ArrayList<>();
    EndpointDefinition definition = new EndpointDefinition("42", "Path");
    String id = "42";
    String url = "https://example.org/example";

    Endpoint endpoint = Endpoint.of(id, url);

    DetectedEndpoint detectedEndpoint = new DetectedEndpoint(definition, endpoint);
    endpoints2.add(detectedEndpoint);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(probeEndpointsStrategy.convert(endpoints2));
    createResult
        .assertNext(
            e -> {
              Endpoints endpoints3 = e;
              Iterator<Endpoint> iteratorResult = endpoints3.iterator();
              Endpoint nextResult = iteratorResult.next();
              assertFalse(iteratorResult.hasNext());
              assertEquals("42", nextResult.getId());
              assertSame(endpoint, nextResult);
              Stream<Endpoint> streamResult = endpoints3.stream();
              List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
              assertEquals(1, collectResult.size());
              assertSame(endpoint, collectResult.get(0));
              return;
            })
        .expectComplete()
        .verify();
    verify(builder).build();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#convert(List)} with {@code endpoints}.
   *
   * <ul>
   *   <li>Given {@link EndpointDefinition#EndpointDefinition(String, String)} with id is {@code 42}
   *       and {@code Path}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'endpoints'; given EndpointDefinition(String, String) with id is '42' and 'Path'; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ProbeEndpointsStrategy.convert(List)"})
  void testConvertWithEndpoints_givenEndpointDefinitionWithIdIs42AndPath_thenCallsBuild2()
      throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    ProbeEndpointsStrategy probeEndpointsStrategy =
        new ProbeEndpointsStrategy(instanceWebClient, endpoints);

    ArrayList<DetectedEndpoint> endpoints2 = new ArrayList<>();
    EndpointDefinition definition = new EndpointDefinition("42", "Path");
    String id = "42";
    String url = "https://example.org/example";

    Endpoint endpoint = Endpoint.of(id, url);

    DetectedEndpoint detectedEndpoint = new DetectedEndpoint(definition, endpoint);
    endpoints2.add(detectedEndpoint);
    EndpointDefinition definition2 = new EndpointDefinition("42", "Path");
    DetectedEndpoint detectedEndpoint2 =
        new DetectedEndpoint(definition2, Endpoint.of("42", "https://example.org/example"));
    endpoints2.add(detectedEndpoint2);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(probeEndpointsStrategy.convert(endpoints2));
    createResult
        .assertNext(
            e -> {
              Endpoints endpoints3 = e;
              Iterator<Endpoint> iteratorResult = endpoints3.iterator();
              Endpoint nextResult = iteratorResult.next();
              assertFalse(iteratorResult.hasNext());
              assertEquals("42", nextResult.getId());
              assertSame(endpoint, nextResult);
              Stream<Endpoint> streamResult = endpoints3.stream();
              List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
              assertEquals(1, collectResult.size());
              assertSame(endpoint, collectResult.get(0));
              return;
            })
        .expectComplete()
        .verify();
    verify(builder).build();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#convert(List)} with {@code endpoints}.
   *
   * <ul>
   *   <li>Given {@link Endpoint} with {@code Id} and url is {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'endpoints'; given Endpoint with 'Id' and url is 'https://example.org/example'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ProbeEndpointsStrategy.convert(List)"})
  void testConvertWithEndpoints_givenEndpointWithIdAndUrlIsHttpsExampleOrgExample()
      throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    ProbeEndpointsStrategy probeEndpointsStrategy =
        new ProbeEndpointsStrategy(instanceWebClient, endpoints);

    ArrayList<DetectedEndpoint> endpoints2 = new ArrayList<>();
    EndpointDefinition definition = new EndpointDefinition("42", "Path");
    String id = "Id";
    String url = "https://example.org/example";

    Endpoint endpoint = Endpoint.of(id, url);

    DetectedEndpoint detectedEndpoint = new DetectedEndpoint(definition, endpoint);
    endpoints2.add(detectedEndpoint);
    EndpointDefinition definition2 = new EndpointDefinition("https", "Path");
    String id2 = "42";
    String url2 = "https://example.org/example";

    Endpoint endpoint2 = Endpoint.of(id2, url2);

    DetectedEndpoint detectedEndpoint2 = new DetectedEndpoint(definition2, endpoint2);
    endpoints2.add(detectedEndpoint2);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(probeEndpointsStrategy.convert(endpoints2));
    createResult
        .assertNext(
            e -> {
              Endpoints endpoints3 = e;
              Iterator<Endpoint> iteratorResult = endpoints3.iterator();
              Endpoint nextResult = iteratorResult.next();
              Endpoint actualNextResult = iteratorResult.next();
              assertFalse(iteratorResult.hasNext());
              assertEquals("Id", nextResult.getId());
              assertSame(endpoint, nextResult);
              assertSame(endpoint2, actualNextResult);
              Stream<Endpoint> streamResult = endpoints3.stream();
              List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
              assertEquals(2, collectResult.size());
              Endpoint actualGetResult = collectResult.get(0);
              assertSame(endpoint, actualGetResult);
              assertSame(endpoint2, collectResult.get(1));
              return;
            })
        .expectComplete()
        .verify();
    verify(builder).build();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#convert(List)} with {@code endpoints}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'endpoints'; when ArrayList(); then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ProbeEndpointsStrategy.convert(List)"})
  void testConvertWithEndpoints_whenArrayList_thenCallsBuild() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    ProbeEndpointsStrategy probeEndpointsStrategy =
        new ProbeEndpointsStrategy(instanceWebClient, endpoints);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(probeEndpointsStrategy.convert(new ArrayList<>()));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link ProbeEndpointsStrategy#convert(InstanceId, EndpointDefinition, URI)} with {@code
   * instanceId}, {@code endpointDefinition}, {@code uri}.
   *
   * <ul>
   *   <li>Then calls {@link ClientResponse#releaseBody()}.
   * </ul>
   *
   * <p>Method under test: {@link ProbeEndpointsStrategy#convert(InstanceId, EndpointDefinition,
   * URI)}
   */
  @Test
  @DisplayName(
      "Test convert(InstanceId, EndpointDefinition, URI) with 'instanceId', 'endpointDefinition', 'uri'; then calls releaseBody()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Function ProbeEndpointsStrategy.convert(InstanceId, EndpointDefinition, URI)"
  })
  void testConvertWithInstanceIdEndpointDefinitionUri_thenCallsReleaseBody() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    String[] endpoints = new String[] {"https://config.us-east-2.amazonaws.com"};

    ProbeEndpointsStrategy probeEndpointsStrategy =
        new ProbeEndpointsStrategy(instanceWebClient, endpoints);
    InstanceId instanceId = InstanceId.of("42");

    // Act
    Function<ClientResponse, Mono<DetectedEndpoint>> actualConvertResult =
        probeEndpointsStrategy.convert(
            instanceId, new EndpointDefinition("42", "Path"), PagerdutyNotifier.DEFAULT_URI);
    ClientResponse delegate = mock(ClientResponse.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(delegate.releaseBody()).thenReturn(channelSendOperator);
    when(delegate.statusCode()).thenReturn(HttpStatus.OK);
    Mono<DetectedEndpoint> actualPublisher =
        actualConvertResult.apply(new ClientResponseWrapper(delegate));

    // Assert
    verify(delegate).releaseBody();
    verify(delegate).statusCode();
    verify(builder).build();
    FirstStep<DetectedEndpoint> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }
}
