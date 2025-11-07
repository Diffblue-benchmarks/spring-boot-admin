package de.codecentric.boot.admin.server.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import org.junit.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class EventsourcingInstanceRepositoryDiffblueTest {
  /**
   * Method under test: {@link EventsourcingInstanceRepository#save(Instance)}
   */
  @Test
  public void testSave() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new InMemoryEventStore());
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(eventsourcingInstanceRepository.save(Instance.create(id)));
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
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#save(Instance)}
   */
  @Test
  public void testSave2() throws AssertionError {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new HazelcastEventStore(eventLogs));
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(eventsourcingInstanceRepository.save(Instance.create(id)));
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
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#save(Instance)}
   */
  @Test
  public void testSave3() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository = new SnapshottingInstanceRepository(
        new InMemoryEventStore());
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.save(Instance.create(id)));
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
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#findAll()}
   */
  @Test
  public void testFindAll() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new EventsourcingInstanceRepository(new InMemoryEventStore())).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#findAll()}
   */
  @Test
  public void testFindAll2() throws AssertionError {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs))).findAll());
    createResult.expectComplete().verify();
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#findAll()}
   */
  @Test
  public void testFindAll3() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new SnapshottingInstanceRepository(new InMemoryEventStore())).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#find(InstanceId)}
   */
  @Test
  public void testFind() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(eventsourcingInstanceRepository.find(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#find(InstanceId)}
   */
  @Test
  public void testFind2() throws AssertionError {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new HazelcastEventStore(eventLogs));

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(eventsourcingInstanceRepository.find(InstanceId.of("42")));
    createResult.expectError().verify();
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#find(InstanceId)}
   */
  @Test
  public void testFind3() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository = new SnapshottingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.find(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#findByName(String)}
   */
  @Test
  public void testFindByName() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new EventsourcingInstanceRepository(new InMemoryEventStore())).findByName("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#findByName(String)}
   */
  @Test
  public void testFindByName2() throws AssertionError {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs))).findByName("Name"));
    createResult.expectComplete().verify();
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test: {@link EventsourcingInstanceRepository#findByName(String)}
   */
  @Test
  public void testFindByName3() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new SnapshottingInstanceRepository(new InMemoryEventStore())).findByName("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}
   */
  @Test
  public void testCompute() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(eventsourcingInstanceRepository.compute(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}
   */
  @Test
  public void testCompute2() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository = new SnapshottingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.compute(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId, BiFunction)}
   */
  @Test
  public void testComputeIfPresent() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(eventsourcingInstanceRepository.computeIfPresent(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId, BiFunction)}
   */
  @Test
  public void testComputeIfPresent2() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository = new SnapshottingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.computeIfPresent(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)}
   */
  @Test
  public void testNewEventsourcingInstanceRepository() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new EventsourcingInstanceRepository(new InMemoryEventStore())).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)}
   */
  @Test
  public void testNewEventsourcingInstanceRepository2() throws AssertionError {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs))).findAll());
    createResult.expectComplete().verify();
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }
}
