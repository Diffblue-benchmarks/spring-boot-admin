package de.codecentric.boot.admin.server.eventstore;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.mockito.Mockito;

public class InstanceEventPublisherDiffblueTest {
  /**
   * Method under test: {@link InstanceEventPublisher#publish(List)}
   */
  @Test
  public void testPublish() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    HazelcastEventStore hazelcastEventStore = new HazelcastEventStore(eventLogs);

    // Act
    hazelcastEventStore.publish(new ArrayList<>());

    // Assert that nothing has changed
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test: {@link InstanceEventPublisher#publish(List)}
   */
  @Test
  public void testPublish2() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    HazelcastEventStore hazelcastEventStore = new HazelcastEventStore(eventLogs);

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act
    hazelcastEventStore.publish(events);

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test: {@link InstanceEventPublisher#publish(List)}
   */
  @Test
  public void testPublish3() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    HazelcastEventStore hazelcastEventStore = new HazelcastEventStore(eventLogs);

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act
    hazelcastEventStore.publish(events);

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }
}
