package de.codecentric.boot.admin.server.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.Disposable;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {SnapshottingInstanceRepository.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class SnapshottingInstanceRepositoryDiffblueTest {
  @MockBean
  private InstanceEventStore instanceEventStore;

  @Autowired
  private SnapshottingInstanceRepository snapshottingInstanceRepository;

  /**
   * Method under test: {@link SnapshottingInstanceRepository#findAll()}
   */
  @Test
  public void testFindAll() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(snapshottingInstanceRepository.findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link SnapshottingInstanceRepository#find(InstanceId)}
   */
  @Test
  public void testFind() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.find(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link SnapshottingInstanceRepository#save(Instance)}
   */
  @Test
  public void testSave() throws AssertionError {
    // Arrange
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(instanceEventStore.append(Mockito.<List<InstanceEvent>>any()))
        .thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.save(Instance.create(InstanceId.of("42"))));
    createResult.expectError().verify();
    verify(instanceEventStore).append(isA(List.class));
  }

  /**
   * Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  public void testStart() {
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
   * Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  public void testStart2() {
    // Arrange
    doThrow(new OptimisticLockingException("An error occurred")).when(instanceEventStore)
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
   * Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  public void testStart3() {
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
   * Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  public void testStart4() {
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
   * Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  public void testStart5() {
    // Arrange
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(instanceEventStore.findAll()).thenReturn(createResult);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
  }

  /**
   * Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  public void testStart6() {
    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.concatWith(Mockito.<Publisher<InstanceEvent>>any())).thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(flux);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(flux).concatWith(isA(Publisher.class));
  }

  /**
   * Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  public void testStart7() {
    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.subscribe(Mockito.<Consumer<InstanceEvent>>any())).thenReturn(mock(Disposable.class));
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.concatWith(Mockito.<Publisher<InstanceEvent>>any())).thenReturn(flux);
    when(instanceEventStore.findAll()).thenReturn(flux2);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceEventStore).findAll();
    verify(flux2).concatWith(isA(Publisher.class));
    verify(flux).subscribe(isA(Consumer.class));
  }

  /**
   * Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  public void testStart8() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getVersion()).thenThrow(new OptimisticLockingException("An error occurred"));
    when(instanceDeregisteredEvent.getInstance()).thenReturn(InstanceId.of("42"));

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(instanceDeregisteredEvent);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.concatWith(Mockito.<Publisher<InstanceEvent>>any())).thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(flux);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceDeregisteredEvent).getInstance();
    verify(instanceDeregisteredEvent).getVersion();
    verify(instanceEventStore).findAll();
    verify(flux).concatWith(isA(Publisher.class));
  }

  /**
   * Method under test: {@link SnapshottingInstanceRepository#start()}
   */
  @Test
  public void testStart9() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getInstance()).thenReturn(null);

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(instanceDeregisteredEvent);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.concatWith(Mockito.<Publisher<InstanceEvent>>any())).thenReturn(fromIterableResult);
    when(instanceEventStore.findAll()).thenReturn(flux);

    // Act
    snapshottingInstanceRepository.start();

    // Assert
    verify(instanceDeregisteredEvent).getInstance();
    verify(instanceEventStore).findAll();
    verify(flux).concatWith(isA(Publisher.class));
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  public void testRehydrateSnapshot() throws AssertionError {
    // Arrange
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceEventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.rehydrateSnapshot(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(instanceEventStore).find(isA(InstanceId.class));
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  public void testRehydrateSnapshot2() throws AssertionError {
    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(flux.collectList()).thenReturn(justResult);
    when(instanceEventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.rehydrateSnapshot(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(instanceEventStore).find(isA(InstanceId.class));
    verify(flux).collectList();
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  public void testRehydrateSnapshot3() throws AssertionError {
    // Arrange
    Mono<List<InstanceEvent>> mono = mock(Mono.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(mono.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(justResult);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.collectList()).thenReturn(mono);
    when(instanceEventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.rehydrateSnapshot(id));
    createResult.assertNext(i -> {
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
    }).expectComplete().verify();
    verify(instanceEventStore).find(isA(InstanceId.class));
    verify(flux).collectList();
    verify(mono).filter(isA(Predicate.class));
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#rehydrateSnapshot(InstanceId)}
   */
  @Test
  public void testRehydrateSnapshot4() throws AssertionError {
    // Arrange
    Mono<List<InstanceEvent>> mono = mock(Mono.class);
    Mono<Object> justResult = Mono.just("Data");
    when(mono.map(Mockito.<Function<List<InstanceEvent>, Object>>any())).thenReturn(justResult);
    Mono<List<InstanceEvent>> mono2 = mock(Mono.class);
    when(mono2.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(mono);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.collectList()).thenReturn(mono2);
    when(instanceEventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.rehydrateSnapshot(InstanceId.of("42")));
    createResult.expectError().verify();
    verify(instanceEventStore).find(isA(InstanceId.class));
    verify(flux).collectList();
    verify(mono2).filter(isA(Predicate.class));
    verify(mono).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  public void testUpdateSnapshot() {
    // Arrange
    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getVersion()).thenReturn(1L);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event, atLeast(1)).getInstance();
    verify(event, atLeast(1)).getVersion();
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  public void testUpdateSnapshot2() {
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
   * Method under test:
   * {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  public void testUpdateSnapshot3() {
    // Arrange
    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getInstance()).thenReturn(null);

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event).getInstance();
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  public void testUpdateSnapshot4() {
    // Arrange
    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getRegistration()).thenReturn(null);
    when(event.getTimestamp()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(event.getVersion()).thenReturn(1L);
    when(event.getInstance()).thenReturn(InstanceId.of("4242"));

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event, atLeast(1)).getInstance();
    verify(event).getTimestamp();
    verify(event, atLeast(1)).getVersion();
    verify(event).getRegistration();
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#updateSnapshot(InstanceEvent)}
   */
  @Test
  public void testUpdateSnapshot5() {
    // Arrange
    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getRegistration()).thenThrow(new OptimisticLockingException("An error occurred"));
    when(event.getVersion()).thenReturn(1L);
    when(event.getInstance()).thenReturn(InstanceId.of("4242"));

    // Act
    snapshottingInstanceRepository.updateSnapshot(event);

    // Assert
    verify(event, atLeast(1)).getInstance();
    verify(event, atLeast(1)).getVersion();
    verify(event).getRegistration();
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#SnapshottingInstanceRepository(InstanceEventStore)}
   */
  @Test
  public void testNewSnapshottingInstanceRepository() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new SnapshottingInstanceRepository(new InMemoryEventStore())).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link SnapshottingInstanceRepository#SnapshottingInstanceRepository(InstanceEventStore)}
   */
  @Test
  public void testNewSnapshottingInstanceRepository2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new SnapshottingInstanceRepository(mock(HazelcastEventStore.class))).findAll());
    createResult.expectComplete().verify();
  }
}
