package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

public class AbstractEventNotifierDiffblueTest {
  /**
   * Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
   */
  @Test
  public void testNotify() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(remindingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
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
   * Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
   */
  @Test
  public void testNotify3() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate,
        new SnapshottingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(remindingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link AbstractEventNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testShouldNotify() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertTrue(remindingNotifier.shouldNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test:
   * {@link AbstractEventNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testShouldNotify2() {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertFalse(loggingNotifier.shouldNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test: {@link AbstractEventNotifier#setEnabled(boolean)}
   */
  @Test
  public void testSetEnabled() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act
    (new LoggingNotifier(new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)))).setEnabled(true);

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test: {@link AbstractEventNotifier#isEnabled()}
   */
  @Test
  public void testIsEnabled() {
    // Arrange, Act and Assert
    assertTrue((new LoggingNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()))).isEnabled());
  }

  /**
   * Method under test: {@link AbstractEventNotifier#isEnabled()}
   */
  @Test
  public void testIsEnabled2() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act
    boolean actualIsEnabledResult = (new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)))).isEnabled();

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
    assertTrue(actualIsEnabledResult);
  }

  /**
   * Method under test: {@link AbstractEventNotifier#isEnabled()}
   */
  @Test
  public void testIsEnabled3() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)));
    loggingNotifier.setEnabled(false);

    // Act
    boolean actualIsEnabledResult = loggingNotifier.isEnabled();

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
    assertFalse(actualIsEnabledResult);
  }
}
