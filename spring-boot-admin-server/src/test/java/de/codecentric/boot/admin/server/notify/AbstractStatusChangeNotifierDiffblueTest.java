package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.mockito.Mockito;
import reactor.test.StepVerifier;

public class AbstractStatusChangeNotifierDiffblueTest {
  /**
   * Method under test: {@link AbstractStatusChangeNotifier#notify(InstanceEvent)}
   */
  @Test
  public void testNotify() throws AssertionError {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(loggingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link AbstractStatusChangeNotifier#notify(InstanceEvent)}
   */
  @Test
  public void testNotify2() throws AssertionError {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));
    loggingNotifier.setEnabled(false);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(loggingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link AbstractStatusChangeNotifier#notify(InstanceEvent)}
   */
  @Test
  public void testNotify3() throws AssertionError {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(loggingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectError().verify();
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test: {@link AbstractStatusChangeNotifier#notify(InstanceEvent)}
   */
  @Test
  public void testNotify4() throws AssertionError {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(new SnapshottingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(loggingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link AbstractStatusChangeNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testShouldNotify() {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertFalse(loggingNotifier.shouldNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test:
   * {@link AbstractStatusChangeNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testShouldNotify2() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)));

    // Act
    boolean actualShouldNotifyResult = loggingNotifier
        .shouldNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
    assertFalse(actualShouldNotifyResult);
  }

  /**
   * Method under test:
   * {@link AbstractStatusChangeNotifier#getLastStatus(InstanceId)}
   */
  @Test
  public void testGetLastStatus() {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertEquals("UNKNOWN", loggingNotifier.getLastStatus(InstanceId.of("42")));
  }

  /**
   * Method under test:
   * {@link AbstractStatusChangeNotifier#getLastStatus(InstanceId)}
   */
  @Test
  public void testGetLastStatus2() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)));

    // Act
    String actualLastStatus = loggingNotifier.getLastStatus(InstanceId.of("42"));

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
    assertEquals("UNKNOWN", actualLastStatus);
  }

  /**
   * Method under test:
   * {@link AbstractStatusChangeNotifier#updateLastStatus(InstanceEvent)}
   */
  @Test
  public void testUpdateLastStatus() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)));

    // Act
    loggingNotifier.updateLastStatus(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test:
   * {@link AbstractStatusChangeNotifier#updateLastStatus(InstanceEvent)}
   */
  @Test
  public void testUpdateLastStatus2() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act
    (new LoggingNotifier(new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs))))
        .updateLastStatus(null);

    // Assert that nothing has changed
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test:
   * {@link AbstractStatusChangeNotifier#setIgnoreChanges(String[])}
   */
  @Test
  public void testSetIgnoreChanges() {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act
    loggingNotifier.setIgnoreChanges(new String[]{"Ignore Changes"});

    // Assert
    assertArrayEquals(new String[]{"Ignore Changes"}, loggingNotifier.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AbstractStatusChangeNotifier#setIgnoreChanges(String[])}
   */
  @Test
  public void testSetIgnoreChanges2() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)));

    // Act
    loggingNotifier.setIgnoreChanges(new String[]{"Ignore Changes"});

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
    assertArrayEquals(new String[]{"Ignore Changes"}, loggingNotifier.getIgnoreChanges());
  }

  /**
   * Method under test: {@link AbstractStatusChangeNotifier#getIgnoreChanges()}
   */
  @Test
  public void testGetIgnoreChanges() {
    // Arrange, Act and Assert
    assertArrayEquals(new String[]{"UNKNOWN:UP"},
        (new LoggingNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()))).getIgnoreChanges());
  }

  /**
   * Method under test: {@link AbstractStatusChangeNotifier#getIgnoreChanges()}
   */
  @Test
  public void testGetIgnoreChanges2() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act
    String[] actualIgnoreChanges = (new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)))).getIgnoreChanges();

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualIgnoreChanges);
  }
}
