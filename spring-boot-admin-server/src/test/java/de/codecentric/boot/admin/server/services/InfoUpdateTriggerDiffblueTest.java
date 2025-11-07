package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.sun.security.auth.UserPrincipal;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.Decoder;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Encoder;
import jakarta.websocket.Extension;
import java.io.IOException;
import java.security.Principal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.tomcat.websocket.AsyncChannelWrapperNonSecure;
import org.apache.tomcat.websocket.EndpointHolder;
import org.apache.tomcat.websocket.PojoClassHolder;
import org.apache.tomcat.websocket.WsRemoteEndpointImplClient;
import org.apache.tomcat.websocket.WsSession;
import org.apache.tomcat.websocket.WsWebSocketContainer;
import org.apache.tomcat.websocket.pojo.PojoEndpointServer;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.adapter.StandardWebSocketSession;
import reactor.core.Scannable;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

public class InfoUpdateTriggerDiffblueTest {
  /**
   * Method under test: {@link InfoUpdateTrigger#handle(Flux)}
   */
  @Test
  public void testHandle() throws DeploymentException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<InstanceEvent> publisher2 = mock(Flux.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(flux);

    // Act
    Publisher<Void> actualHandleResult = infoUpdateTrigger.handle(publisher2);
    ClientEndpointConfig clientEndpointConfig = mock(ClientEndpointConfig.class);
    Mockito.<List<Class<? extends Encoder>>>when(clientEndpointConfig.getEncoders()).thenReturn(new ArrayList<>());
    when(clientEndpointConfig.getUserProperties()).thenReturn(new HashMap<>());
    EndpointHolder clientEndpointHolder = new EndpointHolder(new PojoEndpointServer(new HashMap<>(), "Pojo"));
    WsRemoteEndpointImplClient wsRemoteEndpoint = new WsRemoteEndpointImplClient(
        new AsyncChannelWrapperNonSecure(null));
    WsWebSocketContainer wsWebSocketContainer = new WsWebSocketContainer();
    ArrayList<Extension> negotiatedExtensions = new ArrayList<>();
    WsSession session = new WsSession(clientEndpointHolder, wsRemoteEndpoint, wsWebSocketContainer,
        negotiatedExtensions, "Sub Protocol", new HashMap<>(), true, clientEndpointConfig);

    HttpHeaders headers = new HttpHeaders();
    Mono<Principal> principal = Mono.just(new UserPrincipal("data"));
    HandshakeInfo info = new HandshakeInfo(PagerdutyNotifier.DEFAULT_URI, headers, principal, "Protocol");

    actualHandleResult.subscribe(new StandardWebSocketSession(session, info, new DefaultDataBufferFactory()));

    // Assert
    verify(clientEndpointConfig).getEncoders();
    verify(clientEndpointConfig).getUserProperties();
    verify(publisher2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#handle(Flux)}
   */
  @Test
  public void testHandle2() throws DeploymentException, IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<InstanceEvent> publisher2 = mock(Flux.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(flux);

    // Act
    Publisher<Void> actualHandleResult = infoUpdateTrigger.handle(publisher2);
    ClientEndpointConfig clientEndpointConfig = mock(ClientEndpointConfig.class);
    Mockito.<List<Class<? extends Decoder>>>when(clientEndpointConfig.getDecoders()).thenReturn(new ArrayList<>());
    Class<Object> pojoClazz = Object.class;
    PojoClassHolder clientEndpointHolder = new PojoClassHolder(pojoClazz, clientEndpointConfig);

    WsRemoteEndpointImplClient wsRemoteEndpoint = mock(WsRemoteEndpointImplClient.class);
    doNothing().when(wsRemoteEndpoint).setBatchingAllowed(anyBoolean());
    doNothing().when(wsRemoteEndpoint).setSendTimeout(anyLong());
    ClientEndpointConfig clientEndpointConfig2 = mock(ClientEndpointConfig.class);
    when(clientEndpointConfig2.getUserProperties()).thenReturn(new HashMap<>());
    WsWebSocketContainer wsWebSocketContainer = new WsWebSocketContainer();
    ArrayList<Extension> negotiatedExtensions = new ArrayList<>();
    WsSession session = new WsSession(clientEndpointHolder, wsRemoteEndpoint, wsWebSocketContainer,
        negotiatedExtensions, "Sub Protocol", new HashMap<>(), true, clientEndpointConfig2);

    HttpHeaders headers = new HttpHeaders();
    Mono<Principal> principal = Mono.just(new UserPrincipal("data"));
    HandshakeInfo info = new HandshakeInfo(PagerdutyNotifier.DEFAULT_URI, headers, principal, "Protocol");

    StandardWebSocketSession standardWebSocketSession = new StandardWebSocketSession(session, info,
        new DefaultDataBufferFactory());

    actualHandleResult.subscribe(standardWebSocketSession);

    // Assert
    verify(clientEndpointConfig).getDecoders();
    verify(clientEndpointConfig2).getUserProperties();
    verify(wsRemoteEndpoint).setBatchingAllowed(eq(false));
    verify(wsRemoteEndpoint).setSendTimeout(eq(-1L));
    verify(publisher2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    assertFalse(standardWebSocketSession.isOpen());
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#handle(Flux)}
   */
  @Test
  public void testHandle3() throws DeploymentException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<InstanceEvent> publisher2 = mock(Flux.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(flux);

    // Act
    Publisher<Void> actualHandleResult = infoUpdateTrigger.handle(publisher2);
    ClientEndpointConfig clientEndpointConfig = mock(ClientEndpointConfig.class);
    Mockito.<List<Class<? extends Decoder>>>when(clientEndpointConfig.getDecoders()).thenReturn(new ArrayList<>());
    Class<Object> pojoClazz = Object.class;
    PojoClassHolder clientEndpointHolder = new PojoClassHolder(pojoClazz, clientEndpointConfig);

    WsRemoteEndpointImplClient wsRemoteEndpoint = mock(WsRemoteEndpointImplClient.class);
    doNothing().when(wsRemoteEndpoint).setSendTimeout(anyLong());
    ClientEndpointConfig clientEndpointConfig2 = mock(ClientEndpointConfig.class);
    when(clientEndpointConfig2.getUserProperties()).thenReturn(new HashMap<>());
    WsWebSocketContainer wsWebSocketContainer = new WsWebSocketContainer();
    ArrayList<Extension> negotiatedExtensions = new ArrayList<>();
    WsSession session = new WsSession(clientEndpointHolder, wsRemoteEndpoint, wsWebSocketContainer,
        negotiatedExtensions, "Sub Protocol", new HashMap<>(), true, clientEndpointConfig2);

    HttpHeaders headers = new HttpHeaders();
    Mono<Principal> principal = Mono.just(new UserPrincipal("data"));
    HandshakeInfo info = new HandshakeInfo(PagerdutyNotifier.DEFAULT_URI, headers, principal, "Protocol");

    StandardWebSocketSession standardWebSocketSession = new StandardWebSocketSession(session, info,
        new DefaultDataBufferFactory());

    actualHandleResult.subscribe(standardWebSocketSession);

    // Assert
    verify(clientEndpointConfig).getDecoders();
    verify(clientEndpointConfig2).getUserProperties();
    verify(wsRemoteEndpoint).setSendTimeout(eq(-1L));
    verify(publisher2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    assertTrue(standardWebSocketSession.isOpen());
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#handle(Flux)}
   */
  @Test
  public void testHandle4() throws DeploymentException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);
    Flux<InstanceEvent> flux = mock(Flux.class);
    EmitterProcessor<Object> createResult = EmitterProcessor.create(3, true);
    when(flux.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any())).thenReturn(createResult);
    Flux<InstanceEvent> publisher2 = mock(Flux.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(flux);

    // Act
    Publisher<Void> actualHandleResult = infoUpdateTrigger.handle(publisher2);
    ClientEndpointConfig clientEndpointConfig = mock(ClientEndpointConfig.class);
    Mockito.<List<Class<? extends Decoder>>>when(clientEndpointConfig.getDecoders()).thenReturn(new ArrayList<>());
    Class<Object> pojoClazz = Object.class;
    PojoClassHolder clientEndpointHolder = new PojoClassHolder(pojoClazz, clientEndpointConfig);

    WsRemoteEndpointImplClient wsRemoteEndpoint = mock(WsRemoteEndpointImplClient.class);
    doNothing().when(wsRemoteEndpoint).setSendTimeout(anyLong());
    ClientEndpointConfig clientEndpointConfig2 = mock(ClientEndpointConfig.class);
    when(clientEndpointConfig2.getUserProperties()).thenReturn(new HashMap<>());
    WsWebSocketContainer wsWebSocketContainer = new WsWebSocketContainer();
    ArrayList<Extension> negotiatedExtensions = new ArrayList<>();
    WsSession session = new WsSession(clientEndpointHolder, wsRemoteEndpoint, wsWebSocketContainer,
        negotiatedExtensions, "Sub Protocol", new HashMap<>(), true, clientEndpointConfig2);

    HttpHeaders headers = new HttpHeaders();
    Mono<Principal> principal = Mono.just(new UserPrincipal("data"));
    HandshakeInfo info = new HandshakeInfo(PagerdutyNotifier.DEFAULT_URI, headers, principal, "Protocol");

    StandardWebSocketSession standardWebSocketSession = new StandardWebSocketSession(session, info,
        new DefaultDataBufferFactory());

    actualHandleResult.subscribe(standardWebSocketSession);

    // Assert
    verify(clientEndpointConfig).getDecoders();
    verify(clientEndpointConfig2).getUserProperties();
    verify(wsRemoteEndpoint).setSendTimeout(eq(-1L));
    verify(publisher2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    assertTrue(actualHandleResult instanceof EmitterProcessor);
    assertNull(((EmitterProcessor<Void>) actualHandleResult).getError());
    assertEquals(0, ((EmitterProcessor<Void>) actualHandleResult).getPending());
    ParallelFlux<Void> parallelResult = ((EmitterProcessor<Void>) actualHandleResult).parallel();
    assertEquals(256, parallelResult.getPrefetch());
    ParallelFlux<Void> checkpointResult = parallelResult.checkpoint();
    assertEquals(256, checkpointResult.getPrefetch());
    ParallelFlux<Void> checkpointResult2 = checkpointResult.checkpoint();
    assertEquals(256, checkpointResult2.getPrefetch());
    ParallelFlux<Void> checkpointResult3 = checkpointResult2.checkpoint();
    assertEquals(256, checkpointResult3.getPrefetch());
    ParallelFlux<Void> checkpointResult4 = checkpointResult3.checkpoint();
    assertEquals(256, checkpointResult4.getPrefetch());
    ParallelFlux<Void> checkpointResult5 = checkpointResult4.checkpoint();
    assertEquals(256, checkpointResult5.getPrefetch());
    ParallelFlux<Void> checkpointResult6 = checkpointResult5.checkpoint();
    assertEquals(256, checkpointResult6.getPrefetch());
    assertEquals(256, checkpointResult6.checkpoint().getPrefetch());
    assertEquals(3, ((EmitterProcessor<Void>) actualHandleResult).getBufferSize());
    assertEquals(3, ((EmitterProcessor<Void>) actualHandleResult).getPrefetch());
    assertFalse(((EmitterProcessor<Void>) actualHandleResult).isDisposed());
    assertFalse(((EmitterProcessor<Void>) actualHandleResult).isTerminated());
    assertFalse(((EmitterProcessor<Void>) actualHandleResult).hasCompleted());
    assertFalse(((EmitterProcessor<Void>) actualHandleResult).hasError());
    assertFalse(((EmitterProcessor<Void>) actualHandleResult).isSerialized());
    Stream<? extends Scannable> actualsResult = ((EmitterProcessor<Void>) actualHandleResult).actuals();
    assertTrue(actualsResult.limit(5).collect(Collectors.toList()).isEmpty());
    Stream<? extends Scannable> parentsResult = ((EmitterProcessor<Void>) actualHandleResult).parents();
    assertTrue(parentsResult.limit(5).collect(Collectors.toList()).isEmpty());
    assertTrue(standardWebSocketSession.isOpen());
    assertTrue(((EmitterProcessor<Void>) actualHandleResult).isScanAvailable());
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo3() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(flux.collectList()).thenReturn(justResult);
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(flux).collectList();
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo4() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Mono<List<InstanceEvent>> mono = mock(Mono.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(mono.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(justResult);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.collectList()).thenReturn(mono);
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(flux).collectList();
    verify(mono).filter(isA(Predicate.class));
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo5() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Mono<List<InstanceEvent>> mono = mock(Mono.class);
    Mono<Object> justResult = Mono.just("Data");
    when(mono.map(Mockito.<Function<List<InstanceEvent>, Object>>any())).thenReturn(justResult);
    Mono<List<InstanceEvent>> mono2 = mock(Mono.class);
    when(mono2.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(mono);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.collectList()).thenReturn(mono2);
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(flux).collectList();
    verify(mono2).filter(isA(Predicate.class));
    verify(mono).map(isA(Function.class));
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo6() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SnapshottingInstanceRepository repository = new SnapshottingInstanceRepository(new InMemoryEventStore());
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo7() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(mono.then()).thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.computeIfPresent(Mockito.<InstanceId>any(),
        Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any())).thenReturn(mono);
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(repository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo8() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));
    Mono<Instance> mono = mock(Mono.class);
    when(mono.then()).thenReturn(channelSendOperator);
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.computeIfPresent(Mockito.<InstanceId>any(),
        Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any())).thenReturn(mono);
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectError().verify();
    verify(repository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    verify(mono).then();
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 = new ChannelSendOperator<>(source, mock(Function.class));

    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any())).thenReturn(channelSendOperator2);
    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);
    Mono<Instance> mono = mock(Mono.class);
    when(mono.then()).thenReturn(channelSendOperator3);
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.computeIfPresent(Mockito.<InstanceId>any(),
        Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any())).thenReturn(mono);
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(repository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    verify(mono).then();
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  public void testUpdateInfo10() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any()))
        .thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());
    InfoUpdateTrigger infoUpdateTrigger = new InfoUpdateTrigger(infoUpdater, publisher, null, null, null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
  }

  /**
   * Method under test:
   * {@link InfoUpdateTrigger#InfoUpdateTrigger(InfoUpdater, Publisher, Duration, Duration, Duration)}
   */
  @Test
  public void testNewInfoUpdateTrigger() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act and Assert
    assertFalse((new InfoUpdateTrigger(infoUpdater, publisher, null, null, null)).createScheduler().isDisposed());
  }

  /**
   * Method under test:
   * {@link InfoUpdateTrigger#InfoUpdateTrigger(InfoUpdater, Publisher, Duration, Duration, Duration)}
   */
  @Test
  public void testNewInfoUpdateTrigger2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(null);
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act and Assert
    assertFalse((new InfoUpdateTrigger(infoUpdater, publisher, null, null, null)).createScheduler().isDisposed());
  }

  /**
   * Method under test:
   * {@link InfoUpdateTrigger#InfoUpdateTrigger(InfoUpdater, Publisher, Duration, Duration, Duration)}
   */
  @Test
  public void testNewInfoUpdateTrigger3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));
    InfoUpdater infoUpdater = new InfoUpdater(repository, null, new ApiMediaTypeHandler());

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act and Assert
    assertFalse((new InfoUpdateTrigger(infoUpdater, publisher, null, null, null)).createScheduler().isDisposed());
  }
}
