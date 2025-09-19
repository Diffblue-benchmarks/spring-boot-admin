package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEndpointsDetectedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegistrationUpdatedEvent;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class StatusUpdateTriggerDiffblueTest {
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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        new InMemoryEventStore(3),
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
    InstanceRepository repository = mock(InstanceRepository.class);

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
  void testNewStatusUpdateTrigger4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        new InMemoryEventStore(3),
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
  void testNewStatusUpdateTrigger5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        new InMemoryEventStore(3),
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
    InstanceRepository repository = mock(InstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(Long.MAX_VALUE));

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
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        new InMemoryEventStore(3),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(Long.MAX_VALUE),
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
  void testNewStatusUpdateTrigger8() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(100));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher = Mono.just(mock(InstanceRegisteredEvent.class));

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
  void testNewStatusUpdateTrigger9() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(-1));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher = Mono.just(mock(InstanceRegisteredEvent.class));

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
  void testNewStatusUpdateTrigger10() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(100));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher = Mono.just(mock(InstanceRegisteredEvent.class));

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(Long.MAX_VALUE),
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
   *   <li>When create.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenCreate() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(-1));

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
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <ul>
   *   <li>When create three and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenCreateThreeAndTrue() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    ReplayProcessor<InstanceEvent> publisher = ReplayProcessor.create(3, true);

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(Long.MAX_VALUE),
        Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenInstanceIdWithValueIs42() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

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
   *   <li>When {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenInstanceIdWithValueIs422() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(100));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

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
   *   <li>When ofSeconds minus one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when ofSeconds minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenOfSecondsMinusOne() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    ReplayProcessor<InstanceEvent> publisher = ReplayProcessor.create(3, true);

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
            Duration.ofSeconds(Long.MAX_VALUE),
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
  void testHandle4() {
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
  void testHandle5() {
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
            Duration.ofSeconds(Long.MAX_VALUE),
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
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    ReplayProcessor<InstanceEvent> publisher = ReplayProcessor.create(3, true);

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
  void testHandle7() {
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
  void testHandle8() {
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
            new InMemoryEventStore(),
            Duration.ofSeconds(Long.MAX_VALUE),
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
    verify(builder).build();
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given ArrayList() add '42'; then return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenArrayListAdd42_thenReturnFromIterableArrayList() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    ReplayProcessor<InstanceEvent> publisher = ReplayProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
    verify(publisher2).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertNull(actualHandleResult);
  }

  /**
   * Test {@link StatusUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); given fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenFromIterableArrayList() {
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
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); given InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenInstanceIdWithValueIs42() {
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
   *   <li>Given {@link
   *       SnapshottingInstanceRepository#SnapshottingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore#InMemoryEventStore()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given SnapshottingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_givenSnapshottingInstanceRepositoryWithEventStoreIsInMemoryEventStore() {
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
   * <ul>
   *   <li>Then return create three and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); then return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher StatusUpdateTrigger.handle(Flux)"})
  void testHandle_thenReturnCreateThreeAndTrue() {
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    EmitterProcessor<Object> createResult = EmitterProcessor.create(3, true);
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(createResult);

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
    verify(publisher2).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(createResult, actualHandleResult);
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
  void testUpdateStatus3() throws AssertionError {
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
  void testUpdateStatus4() throws AssertionError {
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
  void testUpdateStatus5() throws AssertionError {
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
  void testUpdateStatus6() {
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
  void testUpdateStatus7() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Mono<?> source = Mono.just("Data");
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
  void testUpdateStatus9() {
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
            Duration.ofSeconds(0L),
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
  void testUpdateStatus12() {
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
            Duration.ofSeconds(0L),
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
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater2,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));

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
  void testUpdateStatus14() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Mono<?> source = Mono.just(Integer.MIN_VALUE);
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
  void testUpdateStatus15() {
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
  void testUpdateStatus17() {
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
  void testUpdateStatus18() {
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
            Duration.ofSeconds(-1L),
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
  void testUpdateStatus20() {
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
            Duration.ofSeconds(0L));

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
            Duration.ofSeconds(Long.MIN_VALUE),
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
  void testUpdateStatus22() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Mono<?> source = Mono.just("Data");
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
            Duration.ofSeconds(Long.MIN_VALUE),
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
    ReplayProcessor<InstanceEvent> publisher = ReplayProcessor.create(3, true);

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
   *   <li>Given {@link ChannelSendOperator} {@link ChannelSendOperator#doFinally(Consumer)} return
   *       {@link ChannelSendOperator}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); given ChannelSendOperator doFinally(Consumer) return ChannelSendOperator")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_givenChannelSendOperatorDoFinallyReturnChannelSendOperator() {
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
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); given InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() {
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
            new InMemoryEventStore(3),
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
            mock(Publisher.class),
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
    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(new ArrayList<>());
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
  void testStart7() {
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
            Duration.ofSeconds(0L));

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
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(0L));

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
            Duration.ofSeconds(0L),
            Duration.ofSeconds(0L));

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
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(0L));
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
  void testStart13() {
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
            Duration.ofSeconds(0L),
            Duration.ofSeconds(0L));
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
  void testStart14() {
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
            Duration.ofSeconds(0L),
            Duration.ofSeconds(0L));
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
  void testStart15() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore(3);
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
  void testStart16() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    events.add(new InstanceEndpointsDetectedEvent(instance, -1L, Endpoints.empty()));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
  void testStart17() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();
    events.add(new InstanceRegistrationUpdatedEvent(instance, -1L, registration));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
  void testStart18() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    events.add(new InstanceEndpointsDetectedEvent(instance, -1L, Endpoints.empty()));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
  void testStart19() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(null);

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
  void testStart20() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(null);

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
  void testStart21() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();

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
  void testStart22() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();

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
  void testStart23() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
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
  void testStart24() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
  void testStart25() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
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
  void testStart26() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
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
  void testStart27() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
            Duration.ofSeconds(-1L));

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
  void testStart28() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
            Duration.ofSeconds(0L));

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
  void testStart29() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
            Duration.ofSeconds(-1L));
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
  void testStart30() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore(3);
    eventStore.append(new ArrayList<>());
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
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
  void testStart31() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(new ArrayList<>());
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
            Duration.ofSeconds(Long.MIN_VALUE));

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
  void testStart32() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    statusUpdater.timeout(Duration.ofSeconds(1L));

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));

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
  void testStart33() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    statusUpdater.timeout(Duration.ofSeconds(1L));

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
  void testStart34() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
  void testStart35() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
  void testStart36() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
  void testStart37() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));
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
  void testStart38() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
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
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
  void testStart39() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
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
  void testStart40() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
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
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
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
  void testStart41() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
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
  void testStart42() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
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
  void testStart43() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(0L));

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
  void testStart44() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
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
  void testStart45() {
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));
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
  void testStart46() {
    // Arrange
    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            mock(StatusUpdater.class),
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
    verify(publisher).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#start()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link InstanceDeregisteredEvent}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start(); given ArrayList() add InstanceDeregisteredEvent; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenArrayListAddInstanceDeregisteredEvent_thenCallsBuild() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(mock(InstanceDeregisteredEvent.class));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start(); given ArrayList() add 'null'; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenArrayListAddNull_thenCallsBuild() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(null);

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
  void testStart_givenBuilderWebClientReturnBuilder_thenCallsWebClient2() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(new InstanceWebClient.Builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));
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
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore2() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));
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
   *   <li>Given ofSeconds zero addTo ofEpochDay minus one.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start(); given ofSeconds zero addTo ofEpochDay minus one; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenOfSecondsZeroAddToOfEpochDayMinusOne_thenCallsBuild() {
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

    Duration maxBackoff = Duration.ofSeconds(0L);
    maxBackoff.addTo(LocalDate.ofEpochDay(-1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater, publisher, Duration.ofSeconds(1L), Duration.ofSeconds(1L), maxBackoff);

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
   *   <li>Given {@link
   *       SnapshottingInstanceRepository#SnapshottingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore#InMemoryEventStore()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given SnapshottingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenSnapshottingInstanceRepositoryWithEventStoreIsInMemoryEventStore() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
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
    InstanceRepository repository = mock(InstanceRepository.class);

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
  void testStop6() {
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
  void testStop7() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    InstanceRepository repository = mock(InstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));

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
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();
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
  void testStop9() {
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
            Duration.ofSeconds(Long.MAX_VALUE),
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
  void testStop10() {
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
            Duration.ofSeconds(-1L));
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
  void testStop11() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(3),
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop12() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(3),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));
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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
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
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    ReplayProcessor<InstanceEvent> publisher = ReplayProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    ReplayProcessor<InstanceEvent> publisher = ReplayProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testSetInterval5() {
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
            mock(Publisher.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link InstanceDeregisteredEvent}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName(
      "Test setInterval(Duration); given ArrayList() add InstanceDeregisteredEvent; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval_givenArrayListAddInstanceDeregisteredEvent_thenCallsBuild() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.addAll(new ArrayList<>());
    events.add(mock(InstanceDeregisteredEvent.class));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(Publisher.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore()} append {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName(
      "Test setInterval(Duration); given InMemoryEventStore() append ArrayList(); then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval_givenInMemoryEventStoreAppendArrayList_thenCallsBuild() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(Publisher.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName(
      "Test setInterval(Duration); given InstanceId with value is '42'; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval_givenInstanceIdWithValueIs42_thenCallsBuild() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.addAll(new ArrayList<>());
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(Publisher.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
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
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Duration updateInterval = Duration.ofSeconds(1L);
    OffsetDateTime ofResult =
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC);
    updateInterval.addTo(ofResult);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(updateInterval);

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
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
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Duration updateInterval = Duration.ofSeconds(1L);
    OffsetDateTime ofResult =
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC);
    updateInterval.addTo(ofResult);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(updateInterval);

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
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
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Duration updateInterval = Duration.ofSeconds(1L);
    OffsetDateTime ofResult =
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC);
    updateInterval.addTo(ofResult);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(updateInterval);

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
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
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Duration updateInterval = Duration.ofSeconds(1L);
    OffsetDateTime ofResult =
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC);
    updateInterval.addTo(ofResult);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setInterval(updateInterval);

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setLifetime(Duration)}.
   *
   * <ul>
   *   <li>Given ofSeconds one addTo {@link LocalTime#MIDNIGHT}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName(
      "Test setLifetime(Duration); given ofSeconds one addTo MIDNIGHT; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenOfSecondsOneAddToMidnight_thenCallsWebClient() {
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

    Duration updateInterval = Duration.ofSeconds(1L);
    updateInterval.addTo(LocalTime.MIDNIGHT);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(updateInterval);

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
  }
}
