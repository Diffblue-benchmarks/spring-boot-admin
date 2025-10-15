package de.codecentric.boot.admin.server.services;

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
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.time.Duration;
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
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

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
  void testNewStatusUpdateTrigger3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

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
   *   <li>When {@link DirectProcessor}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when DirectProcessor")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenDirectProcessor() {
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
        mock(DirectProcessor.class),
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
   * <ul>
   *   <li>When {@link DirectProcessor}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when DirectProcessor")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenDirectProcessor2() {
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
        mock(DirectProcessor.class),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(-1L),
        Duration.ofSeconds(Long.MAX_VALUE));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher, Duration,
   * Duration, Duration)}.
   *
   * <ul>
   *   <li>When {@link InMemoryEventStore}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when InMemoryEventStore")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenInMemoryEventStore() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        mock(InMemoryEventStore.class),
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
   *   <li>When ofSeconds {@link Long#MAX_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); when ofSeconds MAX_VALUE")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_whenOfSecondsMax_value() {
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
        Duration.ofSeconds(-1L),
        Duration.ofSeconds(Long.MAX_VALUE));

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
            Duration.ofSeconds(Long.MIN_VALUE));

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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testHandle3() {
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE));

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
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE));

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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    EmitterProcessor<Object> createResult = EmitterProcessor.create(3, true);
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(createResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = statusUpdateTrigger.handle(publisher);

    // Assert
    verify(builder).build();
    verify(publisher).filter(isA(Predicate.class));
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
  void testUpdateStatus10() {
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
  void testUpdateStatus11() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(mock(Publisher.class), mock(Function.class));
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
  void testUpdateStatus12() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(mock(Publisher.class), mock(Function.class));
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
  void testUpdateStatus13() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ReplayProcessor<?> source = ReplayProcessor.create(3, true);
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
  void testUpdateStatus14() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    ReplayProcessor<?> source = ReplayProcessor.create(3, true);
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
  void testUpdateStatus15() {
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
  void testStart7() {
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
  void testStart8() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
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
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
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
  void testStart10() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));

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
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
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
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
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
  void testStart13() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));
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
  void testStart15() {
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
  void testStart16() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

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
            Duration.ofSeconds(Long.MAX_VALUE));

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
  void testStart17() {
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
            Duration.ofSeconds(Long.MAX_VALUE));

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
  void testStart18() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart19() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

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
            Duration.ofSeconds(Long.MAX_VALUE));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart20() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(Long.MAX_VALUE));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart21() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(Long.MAX_VALUE));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart22() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(Long.MAX_VALUE));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart23() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart24() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart25() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
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
            Duration.ofSeconds(Long.MAX_VALUE));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart26() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

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
            Duration.ofSeconds(Long.MAX_VALUE));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart27() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(Long.MAX_VALUE));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart28() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(Long.MAX_VALUE));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart29() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart30() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

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
            Duration.ofSeconds(Long.MAX_VALUE));

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
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart31() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

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
    verify(builder).webClient(isA(Builder.class));
    verify(publisher).subscribe(isA(Subscriber.class));
    verify(webClient).build();
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(Long.MAX_VALUE));

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
  void testStart33() {
    // Arrange
    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            mock(StatusUpdater.class),
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(Long.MAX_VALUE));

    // Act
    statusUpdateTrigger.start();

    // Assert
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
  void testStart34() {
    // Arrange
    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            mock(StatusUpdater.class),
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(Long.MAX_VALUE));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
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
  void testStart35() {
    // Arrange
    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            mock(StatusUpdater.class),
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.start();

    // Assert
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
  void testStart_givenBuilderWebClientReturnBuilder_thenCallsWebClient() {
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));

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
  void testStop2() {
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
  void testStop3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

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
  void testStop4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE),
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
  void testStop5() {
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(Long.MIN_VALUE));

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
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(0L));
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
  void testStop7() {
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
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(Long.MIN_VALUE));

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
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(Long.MIN_VALUE));

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
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(Long.MAX_VALUE));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(Long.MIN_VALUE));

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(Long.MIN_VALUE));

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
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(Long.MIN_VALUE));

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
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenInstanceIdWithValueIs42() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(Long.MIN_VALUE));

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
            Duration.ofSeconds(Long.MAX_VALUE));

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
            Duration.ofSeconds(Long.MAX_VALUE));
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
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));
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
   *   <li>Given {@link ArrayList#ArrayList()} addAll {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName("Test setInterval(Duration); given ArrayList() addAll ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval_givenArrayListAddAllArrayList() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository = mock(SnapshottingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.addAll(new ArrayList<>());
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE));
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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InstanceEventStore.class));

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
  void testSetLifetime3() {
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
            Duration.ofSeconds(Long.MIN_VALUE),
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
  void testSetLifetime4() {
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
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }
}
