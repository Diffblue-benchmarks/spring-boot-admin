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
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        new EventsourcingInstanceRepository(mock(InstanceEventStore.class));

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
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(-1L));

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
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(mock(InMemoryEventStore.class));

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
  void testNewStatusUpdateTrigger6() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

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
  void testNewStatusUpdateTrigger7() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

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
  void testNewStatusUpdateTrigger8() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>When {@link InMemoryEventStore#InMemoryEventStore()} append {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#StatusUpdateTrigger(StatusUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName(
      "Test new StatusUpdateTrigger(StatusUpdater, Publisher, Duration, Duration, Duration); given ArrayList(); when InMemoryEventStore() append ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StatusUpdateTrigger.<init>(StatusUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewStatusUpdateTrigger_givenArrayList_whenInMemoryEventStoreAppendArrayList() {
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

    // Act
    new StatusUpdateTrigger(
        statusUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(-1L));

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
  void testHandle3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(Publisher.class),
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
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(Publisher.class),
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
  void testHandle5() {
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
  void testUpdateStatus11() {
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
  void testUpdateStatus12() {
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
  void testUpdateStatus13() {
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
  void testUpdateStatus14() {
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
  void testUpdateStatus17() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    DirectProcessor<Object> source = DirectProcessor.create();
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
  void testUpdateStatus19() {
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
    DirectProcessor<Object> source = DirectProcessor.create();
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

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
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
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

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
            mock(Publisher.class),
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
            mock(InMemoryEventStore.class),
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
  void testUpdateStatus32() {
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
  void testUpdateStatus33() {
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
    DirectProcessor<InstanceEvent> publisher = DirectProcessor.create();

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
  void testUpdateStatus39() {
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
  void testUpdateStatus40() {
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
  void testUpdateStatus41() {
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
            Duration.ofSeconds(Long.MAX_VALUE));

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
  void testUpdateStatus42() {
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
  void testUpdateStatus43() {
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
  void testUpdateStatus44() {
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
  void testUpdateStatus45() {
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
  void testUpdateStatus46() {
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
  void testUpdateStatus47() {
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
  void testUpdateStatus49() {
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
  void testUpdateStatus50() {
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
    EmitterProcessor<InstanceEvent> publisher = EmitterProcessor.create(3, true);

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
  void testUpdateStatus51() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(Mono.class));

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
        .thenReturn(mock(Mono.class));

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
            mock(HazelcastEventStore.class),
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
  void testUpdateStatus53() {
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
  void testUpdateStatus54() {
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
            Duration.ofSeconds(0L),
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
  void testUpdateStatus55() {
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
  void testUpdateStatus56() {
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
  void testUpdateStatus57() {
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
            mock(InMemoryEventStore.class),
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
  void testUpdateStatus58() {
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
            mock(InMemoryEventStore.class),
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
  void testUpdateStatus59() {
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
            Duration.ofSeconds(Long.MAX_VALUE),
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
   *   <li>Given {@link ChannelSendOperator} {@link ChannelSendOperator#doFinally(Consumer)} return
   *       {@link Mono}.
   *   <li>Then calls {@link ChannelSendOperator#doFinally(Consumer)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); given ChannelSendOperator doFinally(Consumer) return Mono; then calls doFinally(Consumer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_givenChannelSendOperatorDoFinallyReturnMono_thenCallsDoFinally() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(Mono.class));

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
   *   <li>Given {@link ChannelSendOperator} {@link ChannelSendOperator#doFinally(Consumer)} return
   *       {@link Mono}.
   *   <li>Then calls {@link ChannelSendOperator#doFinally(Consumer)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); given ChannelSendOperator doFinally(Consumer) return Mono; then calls doFinally(Consumer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_givenChannelSendOperatorDoFinallyReturnMono_thenCallsDoFinally2() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(mock(Mono.class));

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
  void testUpdateStatus_whenInstanceIdWithValueIsCheckForAllInstances3() {
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
    Mono<Void> actualUpdateStatusResult =
        statusUpdateTrigger.updateStatus(InstanceId.of("check {} for all instances"));

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
   *   <li>When {@link InstanceId} with value is {@code Scheduled {}-check every {}}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); when InstanceId with value is 'Scheduled {}-check every {}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_whenInstanceIdWithValueIsScheduledCheckEvery() {
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
        statusUpdateTrigger.updateStatus(InstanceId.of("Scheduled {}-check every {}"));

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
   *   <li>When {@link InstanceId} with value is {@code Scheduled {}-check every {}}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); when InstanceId with value is 'Scheduled {}-check every {}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdateTrigger.updateStatus(InstanceId)"})
  void testUpdateStatus_whenInstanceIdWithValueIsScheduledCheckEvery2() {
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
    Mono<Void> actualUpdateStatusResult =
        statusUpdateTrigger.updateStatus(InstanceId.of("Scheduled {}-check every {}"));

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
  void testStart8() {
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
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
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
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(Long.MIN_VALUE),
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
  void testStart11() {
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
            Duration.ofSeconds(Long.MIN_VALUE),
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
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(-1L));
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
  void testStart15() {
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
            Duration.ofSeconds(0L),
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
  void testStart16() {
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
            Duration.ofSeconds(-1L),
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
  void testStart17() {
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
  void testStart18() {
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
  void testStart19() {
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
  void testStart20() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
  void testStart22() {
    // Arrange
    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
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
            Duration.ofSeconds(Long.MIN_VALUE));
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(0L));
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
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setInterval(Duration.ofSeconds(0L));

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
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore()} append {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given InMemoryEventStore() append ArrayList(); then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenInMemoryEventStoreAppendArrayList_thenCallsWebClient() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
   *   <li>Given ofSeconds zero addTo ofEpochDay minus one.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given ofSeconds zero addTo ofEpochDay minus one; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenOfSecondsZeroAddToOfEpochDayMinusOne_thenCallsWebClient() {
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

    Duration updateInterval = Duration.ofSeconds(0L);
    updateInterval.addTo(LocalDate.ofEpochDay(-1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
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
   *   <li>Given ofSeconds zero addTo ofEpochDay minus one.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given ofSeconds zero addTo ofEpochDay minus one; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.start()"})
  void testStart_givenOfSecondsZeroAddToOfEpochDayMinusOne_thenCallsWebClient2() {
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

    Duration updateInterval = Duration.ofSeconds(0L);
    updateInterval.addTo(LocalDate.ofEpochDay(-1L));

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L));
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
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
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop3() {
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
  void testStop5() {
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
  void testStop6() {
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
  void testStop7() {
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop8() {
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop9() {
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop10() {
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
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();

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
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
  void testStop14() {
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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(),
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
  void testStop15() {
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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(3),
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
  void testStop16() {
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
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(HazelcastEventStore.class),
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
  void testStop17() {
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
            Duration.ofSeconds(-1L),
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
  void testStop18() {
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
    statusUpdater.timeout(Duration.ofSeconds(1L));
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
  void testStop19() {
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop20() {
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
  void testStop21() {
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
            Duration.ofSeconds(0L));

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
  void testStop22() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop23() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            mock(HazelcastEventStore.class),
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop25() {
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
            Duration.ofSeconds(0L));
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop26() {
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
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

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
  void testStop27() {
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
    ReplayProcessor<InstanceEvent> publisher = ReplayProcessor.create(3, true);

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
  void testStop28() {
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
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

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
  void testStop29() {
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
    events.add(new InstanceRegisteredEvent(instance, 1L, registration));

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
  void testStop30() {
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
    events.add(new InstanceRegisteredEvent(instance, 1L, registration));

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop31() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            new InMemoryEventStore(3),
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
  void testStop32() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any()))
        .thenReturn(InstanceWebClient.builder(webClient));
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

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
  void testStop33() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
            Duration.ofSeconds(0L),
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
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given ArrayList() add 'null'; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenArrayListAddNull_thenCallsWebClient() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(null);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given ArrayList() add 'null'; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenArrayListAddNull_thenCallsWebClient2() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(null);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given ArrayList() add 'null'; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenArrayListAddNull_thenCallsWebClient3() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(null);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given ArrayList() add 'null'; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenArrayListAddNull_thenCallsWebClient4() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(null);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given ArrayList() add 'null'; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenArrayListAddNull_thenCallsWebClient5() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(null);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

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
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop(); given ArrayList() add 'null'; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.stop()"})
  void testStop_givenArrayListAddNull_thenCallsWebClient6() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(null);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
    eventStore.append(events);
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
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
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
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(null);
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
  void testStop_givenBuilderWebClientReturnBuilder_thenCallsWebClient3() {
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
  void testStop_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore3() {
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
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    statusUpdateTrigger.stop();

    // Assert
    verify(eventStore).append(isA(List.class));
    verify(builder).webClient(isA(Builder.class));
    verify(webClient).build();
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} addAll {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName(
      "Test setInterval(Duration); given ArrayList() addAll ArrayList(); then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval_givenArrayListAddAllArrayList_thenCallsWebClient() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.addAll(new ArrayList<>());
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <ul>
   *   <li>Given {@link Builder} {@link Builder#build()} return {@link WebClient}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName(
      "Test setInterval(Duration); given Builder build() return WebClient; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval_givenBuilderBuildReturnWebClient_thenCallsBuild() {
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
   * <ul>
   *   <li>Given {@link InstanceWebClient.Builder} {@link
   *       InstanceWebClient.Builder#webClient(Builder)} return builder.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName(
      "Test setInterval(Duration); given Builder webClient(Builder) return builder; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval_givenBuilderWebClientReturnBuilder_thenCallsWebClient() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
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
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdateTrigger#setInterval(Duration)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore()} append {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName(
      "Test setInterval(Duration); given InMemoryEventStore() append ArrayList(); then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setInterval(Duration)"})
  void testSetInterval_givenInMemoryEventStoreAppendArrayList_thenCallsWebClient() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(new ArrayList<>());
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.addAll(new ArrayList<>());
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
    statusUpdateTrigger.setInterval(Duration.ofSeconds(1L));

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
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    StatusUpdateTrigger statusUpdateTrigger =
        new StatusUpdateTrigger(
            statusUpdater,
            publisher,
            Duration.ofSeconds(-1L),
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
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore()} append {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration); given InMemoryEventStore() append ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime_givenInMemoryEventStoreAppendArrayList() {
    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore();
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

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

    // Act
    statusUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }
}
