package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
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
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.mockito.Mock;
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
import reactor.test.StepVerifier.Step;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class StatusUpdateTriggerDiffblueTest {
  @Mock private Duration duration;

  @Mock private Publisher<InstanceEvent> publisher;

  @InjectMocks private StatusUpdateTrigger statusUpdateTrigger;

  @Mock private StatusUpdater statusUpdater;

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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        new InMemoryEventStore(),
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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

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
  void testNewStatusUpdateTrigger5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
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
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceRegisteredEvent(instance, 1L, timestamp, registration));

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
  void testNewStatusUpdateTrigger6() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
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

    InstanceRegisteredEvent instanceRegisteredEvent =
        new InstanceRegisteredEvent(instance, Long.MAX_VALUE, timestamp, registration);
    Mono<InstanceEvent> publisher = Mono.just(instanceRegisteredEvent);

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
   * <ul>
   *   <li>Given {@link StatusUpdater}.
   *   <li>When {@link StatusUpdater}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); given StatusUpdater; when StatusUpdater")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_givenStatusUpdater_whenStatusUpdater() throws AssertionError {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("Value"), 1L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), Long.MIN_VALUE));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));
    it.add(
        new InstanceStatusChangedEvent(
            mock(InstanceId.class),
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            mock(StatusInfo.class)));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(-1L),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(Long.MAX_VALUE));

    // Assert
    FirstStep<InstanceEvent> createResult = StepVerifier.create(publisher);
    Step<InstanceEvent> assertNextResult = createResult.assertNext(i -> {});
    Step<InstanceEvent> assertNextResult2 =
        assertNextResult.assertNext(
            i2 -> {
              assertTrue(i2 instanceof InstanceDeregisteredEvent);
              return;
            });
    Step<InstanceEvent> assertNextResult3 =
        assertNextResult2.assertNext(
            i3 -> {
              assertTrue(i3 instanceof InstanceDeregisteredEvent);
              return;
            });
    Step<InstanceEvent> assertNextResult4 =
        assertNextResult3.assertNext(
            i4 -> {
              assertTrue(i4 instanceof InstanceDeregisteredEvent);
              return;
            });
    assertNextResult4
        .assertNext(
            i5 -> {
              InstanceEvent instanceEvent = i5;
              assertTrue(instanceEvent instanceof InstanceStatusChangedEvent);
              Instant timestamp = instanceEvent.getTimestamp();
              assertEquals(0L, timestamp.getEpochSecond());
              assertEquals(0, timestamp.getNano());
              return;
            })
        .expectComplete()
        .verify();
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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    verify(builder).build();
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
  void testHandle2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(fromIterableResult);

    // Act
    statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    verify(builder).build();
    verify(publisher2).filter(isA(Predicate.class));
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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    verify(builder).build();
    verify(publisher2).filter(isA(Predicate.class));
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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

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
    verify(builder).build();
    verify(publisher2).filter(isA(Predicate.class));
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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
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
    verify(builder).build();
    verify(publisher2).filter(isA(Predicate.class));
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
  void testHandle7() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
    verify(publisher2).filter(isA(Predicate.class));
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
  void testHandle8() throws DeploymentException, NoSuchAlgorithmException {
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
  void testHandle_givenInstanceDeregisteredEventWithInstanceIsInstanceIdAndVersionIsOne() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> publisher2 = Flux.fromIterable(it);

    // Act
    statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
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
  void testHandle_givenInstanceDeregisteredEventWithInstanceIsInstanceIdAndVersionIsOne2()
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

    // Assert
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
  void testHandle_thenCreateThreeAndTrueParentsLimitFiveCollectToListEmpty() {
    // Arrange
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);
    StandardWebSocketSession standardWebSocketSession = mock(StandardWebSocketSession.class);
    doNothing().when(standardWebSocketSession).onSubscribe(Mockito.<Subscription>any());
    actualHandleResult.subscribe(standardWebSocketSession);

    // Assert that nothing has changed
    verify(standardWebSocketSession).onSubscribe(isA(Subscription.class));
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
    assertTrue(publisher.isScanAvailable());
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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    verify(builder).build();
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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    verify(builder).build();
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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
  void testUpdateStatus3() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

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
  void testUpdateStatus6() throws AssertionError {
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
  void testUpdateStatus7() throws AssertionError {
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
  void testUpdateStatus8() {
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
  void testUpdateStatus9() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

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
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus11() {
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
  void testUpdateStatus12() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

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
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus14() {
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
  void testUpdateStatus15() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(new InMemoryEventStore(3), mock(Function.class));
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
  void testUpdateStatus16() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source2, mock(Function.class));
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
  void testUpdateStatus17() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(mock(InMemoryEventStore.class), mock(Function.class));
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
  void testUpdateStatus18() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(new InMemoryEventStore(3), mock(Function.class));
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus19() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source2, mock(Function.class));
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus20() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source2, mock(Function.class));
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
  void testUpdateStatus21() {
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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(Publisher.class),
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
  void testUpdateStatus22() {
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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            new InMemoryEventStore(),
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
  void testUpdateStatus23() {
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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(HazelcastEventStore.class),
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
  void testUpdateStatus24() {
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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(InMemoryEventStore.class),
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
  void testUpdateStatus25() {
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

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(Publisher.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus26() {
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

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            new InMemoryEventStore(),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus27() {
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

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            new InMemoryEventStore(),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testUpdateStatus28() {
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

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(HazelcastEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus29() {
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

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(HazelcastEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testUpdateStatus30() {
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
            Duration.ofSeconds(Long.MAX_VALUE),
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
  void testUpdateStatus31() {
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testUpdateStatus32() {
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
            Duration.ofSeconds(Long.MAX_VALUE));

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
  void testUpdateStatus33() {
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
            Duration.ofSeconds(Long.MAX_VALUE));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus34() {
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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            new InMemoryEventStore(3),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

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
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus35() {
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
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

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
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus36() {
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
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

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
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus37() {
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
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

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
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus38() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus39() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus40() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(Publisher.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus41() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus42() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(Publisher.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus43() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus44() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(mock(HazelcastEventStore.class), mock(Function.class));
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus45() {
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
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus46() {
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
            Duration.ofSeconds(-1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus47() {
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(Long.MAX_VALUE));

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
  void testUpdateStatus48() {
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
    Mono<Void> actualUpdateStatusResult = statusUpdateTrigger.updateStatus(InstanceId.of("42"));

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
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus49() {
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
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testUpdateStatus50() {
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
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testUpdateStatus51() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            new InMemoryEventStore(),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus52() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus53() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus54() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus55() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus56() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(Long.MAX_VALUE));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus57() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus58() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus59() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus60() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus61() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(-1L));

    // Act
    statusUpdateTrigger.updateStatus(InstanceId.of("42"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus62() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(mock(ChannelSendOperator.class), mock(Function.class));
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
  void testUpdateStatus63() {
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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(Publisher.class),
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
  void testUpdateStatus64() {
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
            Duration.ofSeconds(-1L),
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
  void testUpdateStatus65() {
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
            Duration.ofSeconds(-1L),
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
  void testUpdateStatus66() {
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
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testUpdateStatus67() {
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
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testUpdateStatus68() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(new InMemoryEventStore(3), mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

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
  void testUpdateStatus69() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(new InMemoryEventStore(3), mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

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
  void testUpdateStatus70() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(new InMemoryEventStore(3), mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(HazelcastEventStore.class),
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
  void testUpdateStatus71() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(new InMemoryEventStore(3), mock(Function.class));
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
            Duration.ofSeconds(-1L),
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
  void testUpdateStatus72() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source2, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            new InMemoryEventStore(3),
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
  void testUpdateStatus73() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source2, mock(Function.class));
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
            Duration.ofSeconds(0L),
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
  void testUpdateStatus74() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source2, mock(Function.class));
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
            Duration.ofSeconds(Long.MAX_VALUE),
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
  void testUpdateStatus75() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source2, mock(Function.class));
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
            Duration.ofSeconds(-1L),
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
  void testUpdateStatus76() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(mock(InMemoryEventStore.class), mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(Publisher.class),
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
  void testUpdateStatus77() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(mock(InMemoryEventStore.class), mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            new InMemoryEventStore(3),
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
  void testUpdateStatus78() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(mock(InMemoryEventStore.class), mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    StatusUpdater statusUpdater2 = mock(StatusUpdater.class);
    when(statusUpdater2.timeout(Mockito.<Duration>any())).thenReturn(statusUpdater);
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            mock(InMemoryEventStore.class),
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
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
    assertNull(actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       minus one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); given InMemoryEventStore(int) with maxLogSizePerAggregate is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsMinusOne() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(new InMemoryEventStore(-1), mock(Function.class));
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
  void testUpdateStatus_thenCallsFind2() throws AssertionError {
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
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

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
    statusUpdateTrigger.updateStatus(InstanceId.of("Value"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
  void testUpdateStatus_whenInstanceIdWithValue2() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source2, mock(Function.class));
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
   * Test {@link StatusUpdateTrigger#updateStatus(InstanceId)}.
   *
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code check {} for all instances}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); when InstanceId with value is 'check {} for all instances'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_whenInstanceIdWithValueIsCheckForAllInstances() {
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
    Mono<Void> actualUpdateStatusResult =
        statusUpdateTrigger.updateStatus(InstanceId.of("check {} for all instances"));

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
   *   <li>When {@link InstanceId} with value is {@code check {} for all instances}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); when InstanceId with value is 'check {} for all instances'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_whenInstanceIdWithValueIsCheckForAllInstances2() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(ChannelSendOperator.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    StatusUpdater statusUpdater = mock(StatusUpdater.class);
    when(statusUpdater.updateStatus(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);

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
    statusUpdateTrigger.updateStatus(InstanceId.of("check {} for all instances"));

    // Assert
    verify(statusUpdater2).timeout(isA(Duration.class));
    verify(statusUpdater).updateStatus(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));
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
  void testStart4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
  void testStart5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testStart7() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

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
  void testStart8() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
  void testStart9() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart10() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(0L));

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
  void testStart11() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
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
  void testStart12() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

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
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).subscribe(isA(Subscriber.class));
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
  void testStart13() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    Duration updateInterval = Duration.ofSeconds(0L);
    LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
    updateInterval.addTo(ofResult);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(updateInterval);

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <ul>
   *   <li>Given {@link InstanceWebClient.Builder} {@link
   *       InstanceWebClient.Builder#webClient(Builder)} return builder {@link Builder}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given Builder webClient(Builder) return builder Builder; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenBuilderWebClientReturnBuilderBuilder_thenCallsWebClient() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    Duration updateInterval = Duration.ofSeconds(0L);
    LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
    updateInterval.addTo(ofResult);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(updateInterval);

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).subscribe(isA(Subscriber.class));
    verify(webClient).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <ul>
   *   <li>Given {@link InstanceWebClient.Builder} {@link
   *       InstanceWebClient.Builder#webClient(Builder)} return {@link
   *       InstanceWebClient.Builder#Builder()}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given Builder webClient(Builder) return Builder(); then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenBuilderWebClientReturnBuilder_thenCallsWebClient() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(new InstanceWebClient.Builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

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
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <ul>
   *   <li>Given {@link InstanceWebClient.Builder} {@link
   *       InstanceWebClient.Builder#webClient(Builder)} return builder.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given Builder webClient(Builder) return builder; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenBuilderWebClientReturnBuilder_thenCallsWebClient2() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    Duration updateInterval = Duration.ofSeconds(0L);
    LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
    updateInterval.addTo(ofResult);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(updateInterval);

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <ul>
   *   <li>Given {@link Duration} {@link Duration#toNanos()} return one.
   *   <li>Then calls {@link Duration#toNanos()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start(); given Duration toNanos() return one; then calls toNanos()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenDurationToNanosReturnOne_thenCallsToNanos() {
    // Arrange
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());
    when(duration.toNanos()).thenReturn(1L);

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(duration, atLeast(1)).toNanos();
    verify(publisher).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <ul>
   *   <li>Given ofSeconds one addTo {@link LocalDateTime} with one and one and one and one and one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given ofSeconds one addTo LocalDateTime with one and one and one and one and one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenOfSecondsOneAddToLocalDateTimeWithOneAndOneAndOneAndOneAndOne() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    Duration updateInterval = Duration.ofSeconds(1L);
    LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
    updateInterval.addTo(ofResult);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(updateInterval);

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).subscribe(isA(Subscriber.class));
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
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

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
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

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
  void testStop4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

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
  void testStop5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testStop8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
  void testStop9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(mock(Publisher.class), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source2, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(mock(InMemoryEventStore.class), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop13() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    DirectProcessor<Object> source = DirectProcessor.create();
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop14() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(mock(Publisher.class), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop15() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, mock(Function.class));
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source2, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop16() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(mock(InMemoryEventStore.class), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop17() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(mock(InMemoryEventStore.class), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
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
  void testStop18() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
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
  void testStop19() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(mock(InMemoryEventStore.class), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
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
  void testStop20() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

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
    verify(builder).webClient(isA(Builder.class));
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
  void testStop21() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
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
    verify(builder).webClient(isA(Builder.class));
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
  void testStop22() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Mono<?> source = Mono.just("Data");
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
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
  void testStop23() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
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
  void testStop24() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(mock(HazelcastEventStore.class), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
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
  void testStop25() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

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
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given ArrayList() add '42'; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenArrayListAdd42_thenCallsWebClient() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InstanceWebClient.Builder} {@link
   *       InstanceWebClient.Builder#webClient(Builder)} return builder {@link Builder}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given Builder webClient(Builder) return builder Builder; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenBuilderWebClientReturnBuilderBuilder_thenCallsWebClient() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InstanceWebClient.Builder} {@link
   *       InstanceWebClient.Builder#webClient(Builder)} return builder {@link Builder}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given Builder webClient(Builder) return builder Builder; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenBuilderWebClientReturnBuilderBuilder_thenCallsWebClient2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
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
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
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
  void testStop_givenBuilderWebClientReturnBuilder_thenCallsWebClient2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link HazelcastEventStore}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is HazelcastEventStore")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenEventsourcingInstanceRepositoryWithEventStoreIsHazelcastEventStore() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore#InMemoryEventStore()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore#InMemoryEventStore()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore#InMemoryEventStore()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore#InMemoryEventStore(int)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore()} append {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given InMemoryEventStore() append ArrayList(); then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreAppendArrayList_thenCallsBuild() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(new ArrayList<>());
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

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#append(List)} return {@link
   *       ChannelSendOperator}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given InMemoryEventStore append(List) return ChannelSendOperator")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreAppendReturnChannelSendOperator() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any()))
        .thenReturn(mock(ChannelSendOperator.class));
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#append(List)} return {@link
   *       ChannelSendOperator}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given InMemoryEventStore append(List) return ChannelSendOperator")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreAppendReturnChannelSendOperator2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any()))
        .thenReturn(mock(ChannelSendOperator.class));
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#append(List)} return {@link
   *       Mono}.
   *   <li>Then calls {@link InMemoryEventStore#append(List)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given InMemoryEventStore append(List) return Mono; then calls append(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreAppendReturnMono_thenCallsAppend() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(mock(Mono.class));
    eventStore.append(new ArrayList<>());
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

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#append(List)} return {@code
   *       null}.
   *   <li>Then calls {@link InMemoryEventStore#append(List)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given InMemoryEventStore append(List) return 'null'; then calls append(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreAppendReturnNull_thenCallsAppend() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(new ArrayList<>());
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

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#append(List)} return {@code
   *       null}.
   *   <li>Then calls {@link InMemoryEventStore#append(List)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given InMemoryEventStore append(List) return 'null'; then calls append(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreAppendReturnNull_thenCallsAppend2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(new ArrayList<>());
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#append(List)} return {@code
   *       null}.
   *   <li>Then calls {@link InMemoryEventStore#append(List)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given InMemoryEventStore append(List) return 'null'; then calls append(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreAppendReturnNull_thenCallsAppend3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(new ArrayList<>());
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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#append(List)} return {@code
   *       null}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given InMemoryEventStore append(List) return 'null'; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreAppendReturnNull_thenCallsWebClient() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(3), mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(LocalDate.ofEpochDay(1L));

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(OffsetTime.now());

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

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
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(null);

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(OffsetTime.now());

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given {@link LocalTime#MIDNIGHT}.
   *   <li>When ofSeconds zero addTo {@link LocalTime#MIDNIGHT}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration); given MIDNIGHT; when ofSeconds zero addTo MIDNIGHT")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenMidnight_whenOfSecondsZeroAddToMidnight() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(LocalTime.MIDNIGHT);

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given now.
   *   <li>When ofSeconds zero addTo now.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration); given now; when ofSeconds zero addTo now")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenNow_whenOfSecondsZeroAddToNow() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(LocalDate.ofEpochDay(1L));
    statusLifetime.addTo(OffsetTime.now());

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When ofSeconds zero addTo {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration); given 'null'; when ofSeconds zero addTo 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenNull_whenOfSecondsZeroAddToNull() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(null);

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When ofSeconds zero addTo {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration); given 'null'; when ofSeconds zero addTo 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenNull_whenOfSecondsZeroAddToNull2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(null);

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When ofSeconds zero addTo {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration); given 'null'; when ofSeconds zero addTo 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenNull_whenOfSecondsZeroAddToNull3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(LocalDate.ofEpochDay(1L));
    statusLifetime.addTo(null);

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given ofEpochDay one.
   *   <li>When ofSeconds zero addTo ofEpochDay one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName(
      "Test setLifetime(Duration); given ofEpochDay one; when ofSeconds zero addTo ofEpochDay one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenOfEpochDayOne_whenOfSecondsZeroAddToOfEpochDayOne() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(LocalDate.ofEpochDay(1L));

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given ofEpochDay one.
   *   <li>When ofSeconds zero addTo ofEpochDay one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName(
      "Test setLifetime(Duration); given ofEpochDay one; when ofSeconds zero addTo ofEpochDay one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenOfEpochDayOne_whenOfSecondsZeroAddToOfEpochDayOne2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(InMemoryEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    Duration statusLifetime = Duration.ofSeconds(0L);
    statusLifetime.addTo(LocalDate.ofEpochDay(1L));

    // Act
    statusUpdateTrigger.setLifetime(statusLifetime);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>When ofSeconds one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration); when ofSeconds one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_whenOfSecondsOne() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
}
