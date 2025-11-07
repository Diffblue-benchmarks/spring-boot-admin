package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
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
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
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

public class LoggingNotifierDiffblueTest {
  /**
   * Method under test: {@link LoggingNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() throws AssertionError {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(loggingNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link LoggingNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify2() throws AssertionError {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(loggingNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test:
   * {@link LoggingNotifier#LoggingNotifier(InstanceRepository)}
   */
  @Test
  public void testNewLoggingNotifier() {
    // Arrange and Act
    LoggingNotifier actualLoggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Assert
    assertTrue(actualLoggingNotifier.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualLoggingNotifier.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link LoggingNotifier#LoggingNotifier(InstanceRepository)}
   */
  @Test
  public void testNewLoggingNotifier2() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act
    LoggingNotifier actualLoggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)));

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
    assertTrue(actualLoggingNotifier.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualLoggingNotifier.getIgnoreChanges());
  }
}
