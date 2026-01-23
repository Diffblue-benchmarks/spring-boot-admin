package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.sun.security.auth.UserPrincipal;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegistrationUpdatedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ClientEndpointConfig.Configurator;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Extension;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.adapter.StandardWebSocketSession;
import reactor.core.Scannable;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.UnicastProcessor;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class StatusUpdateTriggerDiffblueTest {
  @InjectMocks private StatusUpdateTrigger statusUpdateTrigger;

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger2() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore(0);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger3() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore(3);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(-1L),
        Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger4() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore(3);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    statusUpdater.timeout(Duration.ofSeconds(1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(-1L),
        Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger5() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore(3);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    statusUpdater.timeout(Duration.ofSeconds(1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(-1L),
        Duration.ofSeconds(0L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger6() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    statusUpdater.timeout(Duration.ofSeconds(1L));

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.addAll(new ArrayList<>());
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(-1L),
        Duration.ofSeconds(0L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger7() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore(3);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    statusUpdater.timeout(Duration.ofSeconds(1L));

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.addAll(new ArrayList<>());
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(-1L),
        Duration.ofSeconds(0L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); given InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_givenInstanceIdWithValueIs42() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore(3);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    statusUpdater.timeout(Duration.ofSeconds(1L));

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.addAll(new ArrayList<>());
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(-1L),
        Duration.ofSeconds(0L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <ul>
   *   <li>When {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when InMemoryEventStore(int) with maxLogSizePerAggregate is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenInMemoryEventStoreWithMaxLogSizePerAggregateIsOne() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(1));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    Flux<InstanceEvent> publisher2 = Flux.fromIterable(new ArrayList<>());

    // Act
    statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle2() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(fromIterableResult);

    // Act
    statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher2).filter(isA(Predicate.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle3() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(3),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle4() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(EmitterProcessor.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle5() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(3),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle6() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(EmitterProcessor.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle7() throws DeploymentException, NoSuchAlgorithmException {
    // Arrange
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);
    EndpointHolder clientEndpointHolder =
        new EndpointHolder(new PojoEndpointServer(new HashMap<>(), "Pojo"));
    WsRemoteEndpointImplClient wsRemoteEndpoint =
        new WsRemoteEndpointImplClient(new AsyncChannelWrapperNonSecure(null));
    WsWebSocketContainer wsWebSocketContainer = new WsWebSocketContainer();
    ArrayList<Extension> negotiatedExtensions = new ArrayList<>();
    HashMap<String, String> pathParameters = new HashMap<>();
    ClientEndpointConfig.Builder createResult = ClientEndpointConfig.Builder.create();
    ClientEndpointConfig.Builder configuratorResult = createResult.configurator(new Configurator());
    ClientEndpointConfig.Builder decodersResult = configuratorResult.decoders(new ArrayList<>());
    ClientEndpointConfig.Builder encodersResult = decodersResult.encoders(new ArrayList<>());
    ClientEndpointConfig.Builder extensionsResult = encodersResult.extensions(new ArrayList<>());
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
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return {@code
   *       null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given DirectProcessor flatMap(Function) return 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenDirectProcessorFlatMapReturnNull_thenReturnNull() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(EmitterProcessor.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertNull(actualHandleResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       minus one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given InMemoryEventStore(int) with maxLogSizePerAggregate is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsMinusOne() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(-1));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(EmitterProcessor.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given InMemoryEventStore(int) with maxLogSizePerAggregate is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsOne() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(1));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(fromIterableResult);

    // Act
    statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher2).filter(isA(Predicate.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given InMemoryEventStore(int) with maxLogSizePerAggregate is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsZero() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    Flux<InstanceEvent> publisher2 = Flux.fromIterable(new ArrayList<>());

    // Act
    statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId, long)} with
   *       instance is {@link InstanceId} and version is one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given InstanceDeregisteredEvent(InstanceId, long) with instance is InstanceId and version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenInstanceDeregisteredEventWithInstanceIsInstanceIdAndVersionIsOne()
      throws AssertionError {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    String value = "42";
    InstanceId instance = InstanceId.of(value);
    long version = 1L;

    InstanceDeregisteredEvent instanceDeregisteredEvent =
        new InstanceDeregisteredEvent(instance, version);
    it.add(instanceDeregisteredEvent);
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);
    StandardWebSocketSession standardWebSocketSession = mock(StandardWebSocketSession.class);
    doNothing().when(standardWebSocketSession).onComplete();
    doNothing().when(standardWebSocketSession).onSubscribe(Mockito.<Subscription>any());
    actualHandleResult.subscribe(standardWebSocketSession);

    // Assert that nothing has changed
    verify(standardWebSocketSession).onComplete();
    verify(standardWebSocketSession).onSubscribe(isA(Subscription.class));
    FirstStep<InstanceEvent> createResult = StepVerifier.create(publisher);
    createResult
        .assertNext(
            i -> {
              assertSame(instanceDeregisteredEvent, i);
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link StatusUpdateTrigger}.
   *   <li>When create.
   *   <li>Then create Empty.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); given StatusUpdateTrigger; when create; then create Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenStatusUpdateTrigger_whenCreate_thenCreateEmpty() {
    // Arrange
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);
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
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link StatusUpdateTrigger}.
   *   <li>When create.
   *   <li>Then create three and {@code true} Error is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given StatusUpdateTrigger; when create; then create three and 'true' Error is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenStatusUpdateTrigger_whenCreate_thenCreateThreeAndTrueErrorIsNull() {
    // Arrange
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);
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
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link StatusUpdateTrigger}.
   *   <li>When create.
   *   <li>Then not create hasDownstreams.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given StatusUpdateTrigger; when create; then not create hasDownstreams")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenStatusUpdateTrigger_whenCreate_thenNotCreateHasDownstreams() {
    // Arrange
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);
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
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Then create three and {@code true} parents limit five collect toList Empty.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); then create three and 'true' parents limit five collect toList Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_thenCreateThreeAndTrueParentsLimitFiveCollectToListEmpty()
      throws DeploymentException, NoSuchAlgorithmException {
    // Arrange
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);
    EndpointHolder clientEndpointHolder =
        new EndpointHolder(new PojoEndpointServer(new HashMap<>(), "Pojo"));
    WsRemoteEndpointImplClient wsRemoteEndpoint =
        new WsRemoteEndpointImplClient(new AsyncChannelWrapperNonSecure(null));
    WsWebSocketContainer wsWebSocketContainer = new WsWebSocketContainer();
    ArrayList<Extension> negotiatedExtensions = new ArrayList<>();
    HashMap<String, String> pathParameters = new HashMap<>();
    ClientEndpointConfig.Builder createResult = ClientEndpointConfig.Builder.create();
    ClientEndpointConfig.Builder configuratorResult = createResult.configurator(new Configurator());
    ClientEndpointConfig.Builder decodersResult = configuratorResult.decoders(new ArrayList<>());
    ClientEndpointConfig.Builder encodersResult = decodersResult.encoders(new ArrayList<>());
    ClientEndpointConfig.Builder extensionsResult = encodersResult.extensions(new ArrayList<>());
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

    // Assert that nothing has changed
    DataBufferFactory bufferFactoryResult = standardWebSocketSession.bufferFactory();
    assertTrue(bufferFactoryResult instanceof DefaultDataBufferFactory);
    assertEquals(0, publisher.getPending());
    assertEquals(3, publisher.getBufferSize());
    assertEquals(3, publisher.getPrefetch());
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
    assertSame(factory, bufferFactoryResult);
    assertSame(info, standardWebSocketSession.getHandshakeInfo());
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Then return fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); then return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_thenReturnFromIterableArrayList() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher2).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>When create.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); when create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_whenCreate() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    DirectProcessor<InstanceEvent> publisher2 = DirectProcessor.create();

    // Act
    statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>When fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); when fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_whenFromIterableArrayList() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    Flux<InstanceEvent> publisher2 = Flux.fromIterable(new ArrayList<>());

    // Act
    statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>When fromIterable {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link StandardWebSocketSession#onComplete()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); when fromIterable ArrayList(); then calls onComplete()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_whenFromIterableArrayList_thenCallsOnComplete() throws AssertionError {
    // Arrange
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);
    StandardWebSocketSession standardWebSocketSession = mock(StandardWebSocketSession.class);
    doNothing().when(standardWebSocketSession).onComplete();
    doNothing().when(standardWebSocketSession).onSubscribe(Mockito.<Subscription>any());
    actualHandleResult.subscribe(standardWebSocketSession);

    // Assert that nothing has changed
    verify(standardWebSocketSession).onComplete();
    verify(standardWebSocketSession).onSubscribe(isA(Subscription.class));
    FirstStep<InstanceEvent> createResult = StepVerifier.create(publisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdateTrigger.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus2() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdateTrigger.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus3() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdateTrigger.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus4() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdateTrigger.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus5() throws AssertionError {
    // Arrange
    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdateTrigger.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus6() throws AssertionError {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdateTrigger.updateStatus(InstanceId.of("42")));
    createResult.expectError().verify();
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus7() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus8() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus9() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("Value"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus10() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator2).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus11() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator2).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus12() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add(-1);
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator2).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus13() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("Value"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId); given ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_givenArrayListAdd42() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator2).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link ChannelSendOperator} {@link ChannelSendOperator#doFinally(Consumer)} return
   *       {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); given ChannelSendOperator doFinally(Consumer) return 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_givenChannelSendOperatorDoFinallyReturnNull_thenReturnNull() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any())).thenReturn(null);

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("Value"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
    assertNull(actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId); given InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_givenInstanceIdWithValueIs42() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);

    ArrayList<InstanceEvent> it2 = new ArrayList<>();
    it2.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it2);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator2).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_thenCallsFind() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdateTrigger.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <ul>
   *   <li>When {@link InstanceId} with {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId); when InstanceId with 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_whenInstanceIdWithValue() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("Value"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(publisher).subscribe(isA(Subscriber.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    InstanceRepository repository = mock(InstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(publisher).subscribe(isA(Subscriber.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart6() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    InstanceRepository repository = mock(InstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(publisher).subscribe(isA(Subscriber.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <ul>
   *   <li>Given builder webClient {@link Builder}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start(); given builder webClient Builder; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenBuilderWebClientBuilder_thenCallsWebClient() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builderResult = InstanceWebClient.builder();
    builderResult.webClient(builder);

    InstanceWebClient.Builder builder2 = mock(InstanceWebClient.Builder.class);
    when(builder2.webClient(Mockito.<Builder>any())).thenReturn(builderResult);
    InstanceWebClient instanceWebClient = builder2.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(Integer.MIN_VALUE));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(builder2).webClient(isA(Builder.class));
    verify(publisher).subscribe(isA(Subscriber.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(DirectProcessor.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop6() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} addAll {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given ArrayList() addAll ArrayList(); then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenArrayListAddAllArrayList_thenCallsBuild() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.addAll(new ArrayList<>());
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InstanceWebClient.Builder} {@link
   *       InstanceWebClient.Builder#webClient(Builder)} return builder.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given Builder webClient(Builder) return builder; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenBuilderWebClientReturnBuilder_thenCallsWebClient() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();
    it.add(new InstanceRegistrationUpdatedEvent(instance, 1L, timestamp, registration));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       one.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given InMemoryEventStore(int) with maxLogSizePerAggregate is one; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsOne_thenCallsBuild() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(1));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given InMemoryEventStore(int) with maxLogSizePerAggregate is three; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree_thenCallsBuild() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       zero.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given InMemoryEventStore(int) with maxLogSizePerAggregate is zero; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsZero_thenCallsBuild() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay atZone {@link
   *       ZoneOffset#UTC} toInstant.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given LocalDate with '1970' and one and one atStartOfDay atZone UTC toInstant")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenLocalDateWith1970AndOneAndOneAtStartOfDayAtZoneUtcToInstant() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();
    it.add(new InstanceRegistrationUpdatedEvent(instance, 1L, timestamp, registration));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName("Test setInterval(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName("Test setInterval(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName("Test setInterval(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(DirectProcessor.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName("Test setInterval(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(Long.MAX_VALUE));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime6() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(3),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime7() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(-1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime8() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(HazelcastEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime9() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(Long.MAX_VALUE));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime10() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(Long.MAX_VALUE));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime11() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore(3);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(3),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime12() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(3),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime13() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       {@link Integer#MIN_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName(
      "Test setLifetime(Duration); given InMemoryEventStore(int) with maxLogSizePerAggregate is MIN_VALUE")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsMin_value() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(Integer.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName(
      "Test setLifetime(Duration); given InMemoryEventStore(int) with maxLogSizePerAggregate is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsZero() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }
}
