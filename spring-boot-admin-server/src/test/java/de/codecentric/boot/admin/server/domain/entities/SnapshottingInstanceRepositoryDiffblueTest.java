package de.codecentric.boot.admin.server.domain.entities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEndpointsDetectedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceInfoChangedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegistrationUpdatedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.Info;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.eventstore.OptimisticLockingException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.Disposable;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {SnapshottingInstanceRepository.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class SnapshottingInstanceRepositoryDiffblueTest {
  @MockitoBean private InstanceEventStore instanceEventStore;

  @Autowired private SnapshottingInstanceRepository snapshottingInstanceRepository;

  /**
   * Test {@link SnapshottingInstanceRepository#SnapshottingInstanceRepository(InstanceEventStore)}.
   *
   * <p>Method under test: {@link
   * SnapshottingInstanceRepository#SnapshottingInstanceRepository(InstanceEventStore)}
   */
  @Test
  @DisplayName("Test new SnapshottingInstanceRepository(InstanceEventStore)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.<init>(InstanceEventStore)"})
  void testNewSnapshottingInstanceRepository() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            new SnapshottingInstanceRepository(new InMemoryEventStore(3)).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#findAll()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#findAll()}
   */
  @Test
  @DisplayName("Test findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux SnapshottingInstanceRepository.findAll()"})
  void testFindAll() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(snapshottingInstanceRepository.findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#find(InstanceId)}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#find(InstanceId)}
   */
  @Test
  @DisplayName("Test find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.find(InstanceId)"})
  void testFind() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(snapshottingInstanceRepository.find(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#save(Instance)}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#save(Instance)}
   */
  @Test
  @DisplayName("Test save(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.save(Instance)"})
  void testSave() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(eventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);
    SnapshottingInstanceRepository snapshottingInstanceRepository =
        new SnapshottingInstanceRepository(eventStore);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            snapshottingInstanceRepository.save(Instance.create(InstanceId.of("42"))));
    createResult.expectError().verify();
    verify(eventStore).append(isA(List.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#save(Instance)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#save(Instance)}
   */
  @Test
  @DisplayName(
      "Test save(Instance); given InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.save(Instance)"})
  void testSave_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(snapshottingInstanceRepository.save(Instance.create(id)));
    createResult
        .assertNext(
            i -> {
              Instance instance = i;
              assertNull(instance.getBuildVersion());
              assertSame(id, instance.getId());
              assertTrue(instance.getUnsavedEvents().isEmpty());
              assertEquals(-1L, instance.getVersion());
              assertFalse(instance.isRegistered());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#save(Instance)}.
   *
   * <ul>
   *   <li>Given {@link InstanceEventStore}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#save(Instance)}
   */
  @Test
  @DisplayName("Test save(Instance); given InstanceEventStore")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.save(Instance)"})
  void testSave_givenInstanceEventStore() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getUnsavedEvents())
        .thenThrow(new OptimisticLockingException("An error occurred"));

    // Act and Assert
    assertThrows(
        OptimisticLockingException.class, () -> snapshottingInstanceRepository.save(instance));
    verify(instance).getUnsavedEvents();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#save(Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEventStore#append(List)}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#save(Instance)}
   */
  @Test
  @DisplayName("Test save(Instance); then calls append(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.save(Instance)"})
  void testSave_thenCallsAppend() {
    // Arrange
    when(instanceEventStore.append(Mockito.<List<InstanceEvent>>any()))
        .thenThrow(new OptimisticLockingException("An error occurred"));

    // Act and Assert
    assertThrows(
        OptimisticLockingException.class,
        () -> snapshottingInstanceRepository.save(Instance.create(InstanceId.of("42"))));
    verify(instanceEventStore).append(isA(List.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#save(Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEventStore#append(List)}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#save(Instance)}
   */
  @Test
  @DisplayName("Test save(Instance); then calls append(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.save(Instance)"})
  void testSave_thenCallsAppend2() {
    // Arrange
    when(instanceEventStore.append(Mockito.<List<InstanceEvent>>any()))
        .thenThrow(new OptimisticLockingException("An error occurred"));

    Instance instance = mock(Instance.class);
    when(instance.getUnsavedEvents()).thenReturn(new ArrayList<>());

    // Act and Assert
    assertThrows(
        OptimisticLockingException.class, () -> snapshottingInstanceRepository.save(instance));
    verify(instance).getUnsavedEvents();
    verify(instanceEventStore).append(isA(List.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart() {
    // Arrange
    doThrow(new OptimisticLockingException("An error occurred"))
        .when(instanceEventStore)
        .subscribe(Mockito.<Subscriber<InstanceEvent>>any());
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceEventStore.findAll()).thenReturn(fromIterableResult);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(instanceEventStore).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart2() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    doNothing().when(instanceEventStore).subscribe(Mockito.<Subscriber<InstanceEvent>>any());
    when(instanceEventStore.findAll()).thenReturn(fromIterableResult);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(instanceEventStore).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart3() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(directProcessor);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart4() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(mock(InstanceId.class), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(directProcessor);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart5() {
    // Arrange
    InstanceEvent instanceEvent = mock(InstanceEvent.class);
    when(instanceEvent.getInstance())
        .thenThrow(new OptimisticLockingException("An error occurred"));

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(instanceEvent);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(directProcessor);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEvent).getInstance();
    verify(instanceEventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart6() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();
    it.add(new InstanceRegisteredEvent(instance, 59L, registration));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);

    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);

    // Act
    new SnapshottingInstanceRepository(eventStore).start();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart7() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();
    it.add(new InstanceRegistrationUpdatedEvent(instance, 59L, registration));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);

    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);

    // Act
    new SnapshottingInstanceRepository(eventStore).start();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart8() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceStatusChangedEvent(InstanceId.of("42"), 59L, mock(StatusInfo.class)));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);

    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);

    // Act
    new SnapshottingInstanceRepository(eventStore).start();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart9() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    it.add(new InstanceEndpointsDetectedEvent(instance, 59L, Endpoints.empty()));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);

    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);

    // Act
    new SnapshottingInstanceRepository(eventStore).start();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart10() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    it.add(new InstanceInfoChangedEvent(instance, 59L, Info.empty()));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);

    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);

    // Act
    new SnapshottingInstanceRepository(eventStore).start();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link DirectProcessor#concatWith(Publisher)}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start(); given ArrayList() add 'null'; then calls concatWith(Publisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_givenArrayListAddNull_thenCallsConcatWith() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(null);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(directProcessor);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#subscribe(Consumer)} return {@link
   *       Disposable}.
   *   <li>Then calls {@link DirectProcessor#subscribe(Consumer)}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given DirectProcessor subscribe(Consumer) return Disposable; then calls subscribe(Consumer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_givenDirectProcessorSubscribeReturnDisposable_thenCallsSubscribe() {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.subscribe(Mockito.<Consumer<InstanceEvent>>any()))
        .thenReturn(mock(Disposable.class));

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(directProcessor);
    when(instanceEventStore.findAll()).thenReturn(directProcessor2);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(directProcessor2).concatWith(isA(Publisher.class));
    verify(directProcessor).subscribe(isA(Consumer.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Given {@link InstanceEvent} {@link InstanceEvent#getInstance()} return {@code null}.
   *   <li>Then calls {@link InstanceEvent#getInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given InstanceEvent getInstance() return 'null'; then calls getInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_givenInstanceEventGetInstanceReturnNull_thenCallsGetInstance() {
    // Arrange
    InstanceEvent instanceEvent = mock(InstanceEvent.class);
    when(instanceEvent.getInstance()).thenReturn(null);

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(instanceEvent);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(directProcessor);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEvent).getInstance();
    verify(instanceEventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Given {@link InstanceEventStore} {@link InstanceEventStore#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start(); given InstanceEventStore findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_givenInstanceEventStoreFindAllReturnCreateThreeAndTrue() {
    // Arrange
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(instanceEventStore.findAll()).thenReturn(createResult);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Given {@link InstanceEventStore} {@link InstanceEventStore#findAll()} return create.
   *   <li>Then calls {@link InstanceEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given InstanceEventStore findAll() return create; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_givenInstanceEventStoreFindAllReturnCreate_thenCallsFindAll() {
    // Arrange
    DirectProcessor<InstanceEvent> createResult = DirectProcessor.create();
    when(instanceEventStore.findAll()).thenReturn(createResult);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Given {@link InstanceEventStore} {@link InstanceEventStore#findAll()} return {@link
   *       DirectProcessor}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start(); given InstanceEventStore findAll() return DirectProcessor")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_givenInstanceEventStoreFindAllReturnDirectProcessor() {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(directProcessor);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceRegisteredEvent#getInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start(); then calls getInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_thenCallsGetInstance() {
    // Arrange
    InstanceRegisteredEvent instanceRegisteredEvent = mock(InstanceRegisteredEvent.class);
    when(instanceRegisteredEvent.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instanceRegisteredEvent.getTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instanceRegisteredEvent.getVersion()).thenReturn(1L);
    when(instanceRegisteredEvent.getInstance()).thenReturn(InstanceId.of("42"));

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(instanceRegisteredEvent);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);

    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);

    // Act
    new SnapshottingInstanceRepository(eventStore).start();

    // Assert
    verify(instanceRegisteredEvent, atLeast(1)).getInstance();
    verify(instanceRegisteredEvent).getTimestamp();
    verify(instanceRegisteredEvent, atLeast(1)).getVersion();
    verify(instanceRegisteredEvent).getRegistration();
    verify(eventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEvent#getVersion()}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start(); then calls getVersion()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_thenCallsGetVersion() {
    // Arrange
    InstanceEvent instanceEvent = mock(InstanceEvent.class);
    when(instanceEvent.getVersion()).thenThrow(new OptimisticLockingException("An error occurred"));
    when(instanceEvent.getInstance()).thenReturn(InstanceId.of("42"));

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(instanceEvent);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.concatWith(Mockito.<Publisher<InstanceEvent>>any()))
        .thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(directProcessor);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEvent).getInstance();
    verify(instanceEvent).getVersion();
    verify(instanceEventStore).findAll();
    verify(directProcessor).concatWith(isA(Publisher.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEventStore#subscribe(Subscriber)}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start(); then calls subscribe(Subscriber)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_thenCallsSubscribe() {
    // Arrange
    doNothing().when(instanceEventStore).subscribe(Mockito.<Subscriber<InstanceEvent>>any());
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceEventStore.findAll()).thenReturn(fromIterableResult);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(instanceEventStore).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEventStore#subscribe(Subscriber)}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start(); then calls subscribe(Subscriber)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_thenCallsSubscribe2() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    doNothing().when(instanceEventStore).subscribe(Mockito.<Subscriber<InstanceEvent>>any());
    when(instanceEventStore.findAll()).thenReturn(fromIterableResult);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(instanceEventStore).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> new SnapshottingInstanceRepository(new InMemoryEventStore(3)).start());
  }

  /**
   * Test {@link SnapshottingInstanceRepository#start()}.
   *
   * <ul>
   *   <li>Then throw {@link OptimisticLockingException}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  @DisplayName("Test start(); then throw OptimisticLockingException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.start()"})
  void testStart_thenThrowOptimisticLockingException() {
    // Arrange
    when(instanceEventStore.findAll())
        .thenThrow(new OptimisticLockingException("An error occurred"));

    // Act and Assert
    assertThrows(OptimisticLockingException.class, () -> snapshottingInstanceRepository.start());
    verify(instanceEventStore).findAll();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#collectList()} return just {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test rehydrateSnapshot(InstanceId); given DirectProcessor collectList() return just ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.rehydrateSnapshot(InstanceId)"})
  void testRehydrateSnapshot_givenDirectProcessorCollectListReturnJustArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(directProcessor.collectList()).thenReturn(justResult);
    when(instanceEventStore.find(Mockito.<InstanceId>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(snapshottingInstanceRepository.rehydrateSnapshot(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(instanceEventStore).find(isA(InstanceId.class));
    verify(directProcessor).collectList();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test rehydrateSnapshot(InstanceId); given InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.rehydrateSnapshot(InstanceId)"})
  void testRehydrateSnapshot_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree()
      throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(snapshottingInstanceRepository.rehydrateSnapshot(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link InstanceEventStore} {@link InstanceEventStore#find(InstanceId)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test rehydrateSnapshot(InstanceId); given InstanceEventStore find(InstanceId) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.rehydrateSnapshot(InstanceId)"})
  void testRehydrateSnapshot_givenInstanceEventStoreFindReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceEventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(snapshottingInstanceRepository.rehydrateSnapshot(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(instanceEventStore).find(isA(InstanceId.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#filter(Predicate)} return just {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link Mono#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test rehydrateSnapshot(InstanceId); given Mono filter(Predicate) return just ArrayList(); then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.rehydrateSnapshot(InstanceId)"})
  void testRehydrateSnapshot_givenMonoFilterReturnJustArrayList_thenCallsFilter()
      throws AssertionError {
    // Arrange
    Mono<List<InstanceEvent>> mono = mock(Mono.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(mono.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(justResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.collectList()).thenReturn(mono);
    when(instanceEventStore.find(Mockito.<InstanceId>any())).thenReturn(directProcessor);
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(snapshottingInstanceRepository.rehydrateSnapshot(id));
    createResult
        .assertNext(
            i -> {
              Instance instance = i;
              assertNull(instance.getBuildVersion());
              assertSame(id, instance.getId());
              Map<String, Object> values = instance.getInfo().getValues();
              assertTrue(values.isEmpty());
              StatusInfo statusInfo = instance.getStatusInfo();
              assertTrue(statusInfo.getDetails().isEmpty());
              assertEquals("UNKNOWN", statusInfo.getStatus());
              assertFalse(statusInfo.isDown());
              assertFalse(statusInfo.isOffline());
              assertTrue(statusInfo.isUnknown());
              assertFalse(statusInfo.isUp());
              Instant statusTimestamp = instance.getStatusTimestamp();
              assertEquals(0L, statusTimestamp.getEpochSecond());
              assertEquals(0, statusTimestamp.getNano());
              assertSame(values, instance.getTags().getValues());
              assertTrue(instance.getUnsavedEvents().isEmpty());
              assertEquals(-1L, instance.getVersion());
              assertFalse(instance.isRegistered());
              return;
            })
        .expectComplete()
        .verify();
    verify(instanceEventStore).find(isA(InstanceId.class));
    verify(directProcessor).collectList();
    verify(mono).filter(isA(Predicate.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#map(Function)} return just {@code Data}.
   *   <li>Then calls {@link Mono#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test rehydrateSnapshot(InstanceId); given Mono map(Function) return just 'Data'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.rehydrateSnapshot(InstanceId)"})
  void testRehydrateSnapshot_givenMonoMapReturnJustData_thenCallsMap() throws AssertionError {
    // Arrange
    Mono<List<InstanceEvent>> mono = mock(Mono.class);
    Mono<Object> justResult = Mono.just("Data");
    when(mono.map(Mockito.<Function<List<InstanceEvent>, Object>>any())).thenReturn(justResult);

    Mono<List<InstanceEvent>> mono2 = mock(Mono.class);
    when(mono2.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(mono);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.collectList()).thenReturn(mono2);
    when(instanceEventStore.find(Mockito.<InstanceId>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(snapshottingInstanceRepository.rehydrateSnapshot(InstanceId.of("42")));
    createResult.expectError().verify();
    verify(instanceEventStore).find(isA(InstanceId.class));
    verify(directProcessor).collectList();
    verify(mono2).filter(isA(Predicate.class));
    verify(mono).map(isA(Function.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}.
   *
   * <ul>
   *   <li>Then throw {@link OptimisticLockingException}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  @DisplayName("Test rehydrateSnapshot(InstanceId); then throw OptimisticLockingException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono SnapshottingInstanceRepository.rehydrateSnapshot(InstanceId)"})
  void testRehydrateSnapshot_thenThrowOptimisticLockingException() {
    // Arrange
    when(instanceEventStore.find(Mockito.<InstanceId>any()))
        .thenThrow(new OptimisticLockingException("An error occurred"));

    // Act and Assert
    assertThrows(
        OptimisticLockingException.class,
        () -> snapshottingInstanceRepository.rehydrateSnapshot(InstanceId.of("42")));
    verify(instanceEventStore).find(isA(InstanceId.class));
  }

  /**
   * Test {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}.
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  @DisplayName("Test updateSnapshot(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.updateSnapshot(InstanceEvent)"})
  void testUpdateSnapshot() {
    // Arrange
    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getInstance()).thenThrow(new OptimisticLockingException("An error occurred"));

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event).getInstance();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  @DisplayName("Test updateSnapshot(InstanceEvent); given InstanceId")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.updateSnapshot(InstanceEvent)"})
  void testUpdateSnapshot_givenInstanceId() {
    // Arrange
    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getVersion()).thenThrow(new OptimisticLockingException("An error occurred"));
    when(event.getInstance()).thenReturn(mock(InstanceId.class));

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event).getInstance();
    verify(event).getVersion();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  @DisplayName("Test updateSnapshot(InstanceEvent); given InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.updateSnapshot(InstanceEvent)"})
  void testUpdateSnapshot_givenInstanceIdWithValueIs42() {
    // Arrange
    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getVersion()).thenThrow(new OptimisticLockingException("An error occurred"));
    when(event.getInstance()).thenReturn(InstanceId.of("42"));

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event).getInstance();
    verify(event).getVersion();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link InstanceRegisteredEvent} {@link InstanceRegisteredEvent#getInstance()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test updateSnapshot(InstanceEvent); given 'null'; when InstanceRegisteredEvent getInstance() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.updateSnapshot(InstanceEvent)"})
  void testUpdateSnapshot_givenNull_whenInstanceRegisteredEventGetInstanceReturnNull() {
    // Arrange
    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getInstance()).thenReturn(null);

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event).getInstance();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceRegisteredEvent#getTimestamp()}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  @DisplayName("Test updateSnapshot(InstanceEvent); then calls getTimestamp()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.updateSnapshot(InstanceEvent)"})
  void testUpdateSnapshot_thenCallsGetTimestamp() {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));

    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(event.getTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(event.getVersion()).thenReturn(1L);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event, atLeast(1)).getInstance();
    verify(event).getTimestamp();
    verify(event, atLeast(1)).getVersion();
    verify(event).getRegistration();
  }

  /**
   * Test {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceRegisteredEvent#getTimestamp()}.
   * </ul>
   *
   * <p>Method under test: {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  @DisplayName("Test updateSnapshot(InstanceEvent); then calls getTimestamp()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SnapshottingInstanceRepository.updateSnapshot(InstanceEvent)"})
  void testUpdateSnapshot_thenCallsGetTimestamp2() {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));

    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(event.getTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(event.getVersion()).thenReturn(1L);
    when(event.getInstance()).thenReturn(mock(InstanceId.class));

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event, atLeast(1)).getInstance();
    verify(event).getTimestamp();
    verify(event, atLeast(1)).getVersion();
    verify(event).getRegistration();
  }
}
