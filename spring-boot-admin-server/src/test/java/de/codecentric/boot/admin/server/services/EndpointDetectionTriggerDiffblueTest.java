package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegistrationUpdatedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.services.endpoints.EndpointDetectionStrategy;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code 4242}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EndpointDetectionTrigger#EndpointDetectionTrigger(EndpointDetector, Publisher)}
   */
  @Test
  @DisplayName(
      "Test new EndpointDetectionTrigger(EndpointDetector, Publisher); when InstanceId with value is '4242'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndpointDetectionTrigger.<init>(EndpointDetector, Publisher)"})
  void testNewEndpointDetectionTrigger_whenInstanceIdWithValueIs4242() throws AssertionError {
    // Arrange
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("4242"), 1L));

    // Act
    new EndpointDetectionTrigger(endpointDetector, publisher);

    // Assert
    FirstStep<InstanceEvent> createResult = StepVerifier.create(publisher);
    createResult
        .assertNext(
            i -> {
              assertTrue(i instanceof InstanceDeregisteredEvent);
              return;
            })
        .expectComplete()
        .verify();
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
  void testHandle() {
    // Arrange
    EndpointDetector endpointDetector =
        new EndpointDetector(
            mock(EventsourcingInstanceRepository.class), mock(EndpointDetectionStrategy.class));
    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, mock(Publisher.class));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher);

    // Assert
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given ArrayList() add '42'; then return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_givenArrayListAdd42_thenReturnFromIterableArrayList() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(Integer.MIN_VALUE));
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));
    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, mock(Publisher.class));

    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    it.addAll(new ArrayList<>());
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher);

    // Assert
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return {@code
   *       null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given DirectProcessor flatMap(Function) return 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_givenDirectProcessorFlatMapReturnNull_thenReturnNull() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));
    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, mock(Publisher.class));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher);

    // Assert
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertNull(actualHandleResult);
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); given fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_givenFromIterableArrayList() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, publisher);

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(fromIterableResult);

    // Act
    endpointDetectionTrigger.handle(publisher2);

    // Assert
    verify(publisher2).filter(isA(Predicate.class));
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       {@link Integer#MIN_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given InMemoryEventStore(int) with maxLogSizePerAggregate is MIN_VALUE")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsMin_value() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(Integer.MIN_VALUE));
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));
    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, mock(Publisher.class));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher);

    // Assert
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given InMemoryEventStore(int) with maxLogSizePerAggregate is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsZero() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(0));
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));
    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, mock(Publisher.class));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher = mock(DirectProcessor.class);
    when(publisher.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher);

    // Assert
    verify(publisher).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link EndpointDetectionTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Then return fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); then return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher EndpointDetectionTrigger.handle(Flux)"})
  void testHandle_thenReturnFromIterableArrayList() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    EndpointDetectionTrigger endpointDetectionTrigger =
        new EndpointDetectionTrigger(endpointDetector, publisher);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher2);

    // Assert
    verify(publisher2).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
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
  void testDetectEndpoints3() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
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
  void testDetectEndpoints4() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));
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
  }

  /**
   * Test {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceEvent); given ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetectionTrigger.detectEndpoints(InstanceEvent)"})
  void testDetectEndpoints_givenArrayListAdd42() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);
    when(endpointDetector.detectEndpoints(Mockito.<InstanceId>any()))
        .thenReturn(channelSendOperator2);

    // Act
    Mono<Void> actualDetectEndpointsResult =
        endpointDetectionTrigger.detectEndpoints(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(endpointDetector).detectEndpoints(isA(InstanceId.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator, actualDetectEndpointsResult);
  }

  /**
   * Test {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add two.
   *   <li>Then calls {@link InstanceRegistrationUpdatedEvent#getInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test detectEndpoints(InstanceEvent); given ArrayList() add two; then calls getInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetectionTrigger.detectEndpoints(InstanceEvent)"})
  void testDetectEndpoints_givenArrayListAddTwo_thenCallsGetInstance() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add(2);
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);
    when(endpointDetector.detectEndpoints(Mockito.<InstanceId>any()))
        .thenReturn(channelSendOperator2);

    InstanceRegistrationUpdatedEvent event = mock(InstanceRegistrationUpdatedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));

    // Act
    Mono<Void> actualDetectEndpointsResult = endpointDetectionTrigger.detectEndpoints(event);

    // Assert
    verify(event).getInstance();
    verify(endpointDetector).detectEndpoints(isA(InstanceId.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator, actualDetectEndpointsResult);
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
