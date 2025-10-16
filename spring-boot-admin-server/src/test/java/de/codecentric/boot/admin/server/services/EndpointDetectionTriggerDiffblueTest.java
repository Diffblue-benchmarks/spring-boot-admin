package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.sun.security.auth.UserPrincipal;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceInfoChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.services.endpoints.EndpointDetectionStrategy;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ClientEndpointConfig.Builder;
import jakarta.websocket.ClientEndpointConfig.Configurator;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Extension;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.net.ssl.SSLContext;
import org.apache.tomcat.websocket.AsyncChannelWrapperNonSecure;
import org.apache.tomcat.websocket.EndpointHolder;
import org.apache.tomcat.websocket.WsRemoteEndpointImplClient;
import org.apache.tomcat.websocket.WsSession;
import org.apache.tomcat.websocket.WsWebSocketContainer;
import org.apache.tomcat.websocket.pojo.PojoEndpointServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.adapter.StandardWebSocketSession;
import reactor.core.Scannable;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {EndpointDetectionTrigger.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class EndpointDetectionTriggerDiffblueTest {
  @Autowired private EndpointDetectionTrigger endpointDetectionTrigger;

  @MockitoBean private EndpointDetector endpointDetector;

  @MockitoBean private Publisher<InstanceEvent> publisher;

  /**
   * Test {@link EndpointDetectionTrigger#EndpointDetectionTrigger(EndpointDetector, Publisher)}.
   *
   * <p>Method under test: {@link
   * EndpointDetectionTrigger#EndpointDetectionTrigger(EndpointDetector, Publisher)}
   */
  @Test
  @DisplayName("Test new EndpointDetectionTrigger(EndpointDetector, Publisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndpointDetectionTrigger.<init>(EndpointDetector, Publisher)"})
  void testNewEndpointDetectionTrigger() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    EndpointDetector endpointDetector =
        new EndpointDetector(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(EndpointDetectionStrategy.class));
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    EndpointDetectionTrigger actualEndpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, publisher);

    // Assert
    assertFalse(actualEndpointDetectionTrigger.createScheduler().isDisposed());
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle() throws DeploymentException, NoSuchAlgorithmException {
    // Arrange
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher);
    EndpointHolder clientEndpointHolder =
        new EndpointHolder(new PojoEndpointServer(new HashMap<>(), "Pojo"));
    WsRemoteEndpointImplClient wsRemoteEndpoint =
        new WsRemoteEndpointImplClient(new AsyncChannelWrapperNonSecure(null));
    WsWebSocketContainer wsWebSocketContainer = new WsWebSocketContainer();
    ArrayList<Extension> negotiatedExtensions = new ArrayList<>();
    HashMap<String, String> pathParameters = new HashMap<>();
    Builder createResult = Builder.create();
    Builder configuratorResult = createResult.configurator(new Configurator());
    Builder decodersResult = configuratorResult.decoders(new ArrayList<>());
    Builder encodersResult = decodersResult.encoders(new ArrayList<>());
    Builder extensionsResult = encodersResult.extensions(new ArrayList<>());
    ClientEndpointConfig clientEndpointConfig =
        extensionsResult
            .preferredSubprotocols(new ArrayList<>())
            .sslContext(SSLContext.getDefault())
            .build();
    WsSession session =
        new WsSession(
            clientEndpointHolder,
            wsRemoteEndpoint,
            wsWebSocketContainer,
            negotiatedExtensions,
            "Sub Protocol",
            pathParameters,
            true,
            clientEndpointConfig);
    HttpHeaders headers = new HttpHeaders();
    Mono<Principal> principal = Mono.just(new UserPrincipal("data"));
    HandshakeInfo info =
        new HandshakeInfo(PagerdutyNotifier.DEFAULT_URI, headers, principal, "Protocol");
    DefaultDataBufferFactory factory = new DefaultDataBufferFactory();
    StandardWebSocketSession standardWebSocketSession =
        new StandardWebSocketSession(session, info, factory);
    actualHandleResult.subscribe(standardWebSocketSession);

    // Assert
    DataBufferFactory bufferFactoryResult = standardWebSocketSession.bufferFactory();
    assertTrue(bufferFactoryResult instanceof DefaultDataBufferFactory);
    assertNull(publisher.getError());
    assertFalse(publisher.isDisposed());
    assertFalse(publisher.isTerminated());
    assertFalse(publisher.hasCompleted());
    assertFalse(publisher.hasError());
    assertFalse(publisher.isSerialized());
    Stream<? extends Scannable> actualsResult = publisher.actuals();
    assertTrue(actualsResult.limit(5).collect(Collectors.toList()).isEmpty());
    Stream<? extends Scannable> parentsResult = publisher.parents();
    assertTrue(parentsResult.limit(5).collect(Collectors.toList()).isEmpty());
    assertTrue(standardWebSocketSession.getAttributes().isEmpty());
    assertTrue(standardWebSocketSession.isOpen());
    assertTrue(publisher.isScanAvailable());
    assertTrue(publisher.hasDownstreams());
    assertEquals(Integer.MAX_VALUE, publisher.getPrefetch());
    assertEquals(Integer.MAX_VALUE, publisher.getBufferSize());
    assertSame(factory, bufferFactoryResult);
    assertSame(info, standardWebSocketSession.getHandshakeInfo());
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>When create.
   *   <li>Then create Empty.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); when create; then create Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_whenCreate_thenCreateEmpty() {
    // Arrange
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher);
    UnicastProcessor<Object> createResult = UnicastProcessor.create();
    actualHandleResult.subscribe(createResult);

    // Assert
    assertNull(publisher.getError());
    assertFalse(publisher.isDisposed());
    assertFalse(publisher.isTerminated());
    assertFalse(publisher.hasCompleted());
    assertFalse(publisher.hasError());
    assertFalse(publisher.isSerialized());
    Stream<? extends Scannable> actualsResult = publisher.actuals();
    assertTrue(actualsResult.limit(5).collect(Collectors.toList()).isEmpty());
    Stream<? extends Scannable> parentsResult = publisher.parents();
    assertTrue(parentsResult.limit(5).collect(Collectors.toList()).isEmpty());
    assertTrue(publisher.isScanAvailable());
    assertTrue(publisher.hasDownstreams());
    assertTrue(createResult.isEmpty());
    assertEquals(Integer.MAX_VALUE, publisher.getPrefetch());
    assertEquals(Integer.MAX_VALUE, publisher.getBufferSize());
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>When create.
   *   <li>Then create three and {@code true} Error is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); when create; then create three and 'true' Error is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_whenCreate_thenCreateThreeAndTrueErrorIsNull() {
    // Arrange
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher);
    EmitterProcessor<? super Void> createResult = EmitterProcessor.create(3, true);
    actualHandleResult.subscribe(createResult);

    // Assert
    assertNull(publisher.getError());
    assertNull(createResult.getError());
    assertEquals(0, createResult.getPending());
    assertEquals(3, createResult.getBufferSize());
    assertEquals(3, createResult.getPrefetch());
    Stream<? extends Scannable> parentsResult = createResult.parents();
    assertEquals(4, parentsResult.limit(5).collect(Collectors.toList()).size());
    assertFalse(publisher.isDisposed());
    assertFalse(publisher.isTerminated());
    assertFalse(createResult.isDisposed());
    assertFalse(createResult.isTerminated());
    assertFalse(publisher.hasCompleted());
    assertFalse(createResult.hasCompleted());
    assertFalse(publisher.hasError());
    assertFalse(createResult.hasError());
    assertFalse(publisher.isSerialized());
    assertFalse(createResult.isSerialized());
    Stream<? extends Scannable> actualsResult = publisher.actuals();
    assertTrue(actualsResult.limit(5).collect(Collectors.toList()).isEmpty());
    Stream<? extends Scannable> actualsResult2 = createResult.actuals();
    assertTrue(actualsResult2.limit(5).collect(Collectors.toList()).isEmpty());
    Stream<? extends Scannable> parentsResult2 = publisher.parents();
    assertTrue(parentsResult2.limit(5).collect(Collectors.toList()).isEmpty());
    assertTrue(publisher.isScanAvailable());
    assertTrue(createResult.isScanAvailable());
    assertTrue(publisher.hasDownstreams());
    assertEquals(Integer.MAX_VALUE, publisher.getPrefetch());
    assertEquals(Integer.MAX_VALUE, publisher.getBufferSize());
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>When create.
   *   <li>Then not create hasDownstreams.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); when create; then not create hasDownstreams")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_whenCreate_thenNotCreateHasDownstreams() {
    // Arrange
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher);
    DirectProcessor<? super Void> createResult = DirectProcessor.create();
    actualHandleResult.subscribe(createResult);

    // Assert
    assertNull(publisher.getError());
    assertNull(createResult.getError());
    assertFalse(publisher.isDisposed());
    assertFalse(createResult.isDisposed());
    assertFalse(createResult.hasDownstreams());
    assertFalse(publisher.isTerminated());
    assertFalse(createResult.isTerminated());
    assertFalse(publisher.hasCompleted());
    assertFalse(createResult.hasCompleted());
    assertFalse(publisher.hasError());
    assertFalse(createResult.hasError());
    assertFalse(publisher.isSerialized());
    assertFalse(createResult.isSerialized());
    Stream<? extends Scannable> actualsResult = publisher.actuals();
    assertTrue(actualsResult.limit(5).collect(Collectors.toList()).isEmpty());
    Stream<? extends Scannable> actualsResult2 = createResult.actuals();
    assertTrue(actualsResult2.limit(5).collect(Collectors.toList()).isEmpty());
    Stream<? extends Scannable> parentsResult = publisher.parents();
    assertTrue(parentsResult.limit(5).collect(Collectors.toList()).isEmpty());
    Stream<? extends Scannable> parentsResult2 = createResult.parents();
    assertTrue(parentsResult2.limit(5).collect(Collectors.toList()).isEmpty());
    assertTrue(publisher.isScanAvailable());
    assertTrue(createResult.isScanAvailable());
    assertTrue(publisher.hasDownstreams());
    assertEquals(Integer.MAX_VALUE, publisher.getPrefetch());
    assertEquals(Integer.MAX_VALUE, createResult.getPrefetch());
    assertEquals(Integer.MAX_VALUE, publisher.getBufferSize());
    assertEquals(Integer.MAX_VALUE, createResult.getBufferSize());
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>When fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); when fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_whenFromIterableArrayList() throws AssertionError {
    // Arrange
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    endpointDetectionTrigger.handle(publisher);

    // Assert that nothing has changed
    FirstStep<InstanceEvent> createResult = StepVerifier.create(publisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}.
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetectionTrigger.detectEndpoints(InstanceEvent)"})
  void testDetectEndpoints() {
    // Arrange
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(endpointDetector.detectEndpoints(Mockito.<InstanceId>any()))
        .thenReturn(channelSendOperator);

    // Act
    endpointDetectionTrigger.detectEndpoints(
        new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(endpointDetector).detectEndpoints(isA(InstanceId.class));
  }

  /**
   * Test {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}.
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetectionTrigger.detectEndpoints(InstanceEvent)"})
  void testDetectEndpoints2() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);
    when(endpointDetector.detectEndpoints(Mockito.<InstanceId>any()))
        .thenReturn(channelSendOperator);

    // Act
    Mono<Void> actualDetectEndpointsResult =
        endpointDetectionTrigger.detectEndpoints(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(endpointDetector).detectEndpoints(isA(InstanceId.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualDetectEndpointsResult);
  }

  /**
   * Test {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}.
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetectionTrigger.detectEndpoints(InstanceEvent)"})
  void testDetectEndpoints3() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);
    when(endpointDetector.detectEndpoints(Mockito.<InstanceId>any()))
        .thenReturn(channelSendOperator);

    InstanceInfoChangedEvent event = mock(InstanceInfoChangedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));

    // Act
    Mono<Void> actualDetectEndpointsResult = endpointDetectionTrigger.detectEndpoints(event);

    // Assert
    verify(event).getInstance();
    verify(endpointDetector).detectEndpoints(isA(InstanceId.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualDetectEndpointsResult);
  }

  /**
   * Test {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}.
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetectionTrigger.detectEndpoints(InstanceEvent)"})
  void testDetectEndpoints4() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);
    when(endpointDetector.detectEndpoints(Mockito.<InstanceId>any()))
        .thenReturn(channelSendOperator);

    InstanceInfoChangedEvent event = mock(InstanceInfoChangedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));

    // Act
    Mono<Void> actualDetectEndpointsResult = endpointDetectionTrigger.detectEndpoints(event);

    // Assert
    verify(event).getInstance();
    verify(endpointDetector).detectEndpoints(isA(InstanceId.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualDetectEndpointsResult);
  }

  /**
   * Test {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}.
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetectionTrigger.detectEndpoints(InstanceEvent)"})
  void testDetectEndpoints5() throws AssertionError {
    // Arrange
    EndpointDetector endpointDetector =
        new EndpointDetector(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(EndpointDetectionStrategy.class));
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, publisher);

    // Act
    Mono<Void> actualPublisher =
        endpointDetectionTrigger.detectEndpoints(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}.
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetectionTrigger.detectEndpoints(InstanceEvent)"})
  void testDetectEndpoints6() throws AssertionError {
    // Arrange
    EndpointDetector endpointDetector =
        new EndpointDetector(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(EndpointDetectionStrategy.class));
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, publisher);

    // Act
    Mono<Void> actualPublisher =
        endpointDetectionTrigger.detectEndpoints(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceEvent); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetectionTrigger.detectEndpoints(InstanceEvent)"})
  void testDetectEndpoints_thenCallsFind() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, publisher);

    // Act
    Mono<Void> actualPublisher =
        endpointDetectionTrigger.detectEndpoints(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
  }
}
