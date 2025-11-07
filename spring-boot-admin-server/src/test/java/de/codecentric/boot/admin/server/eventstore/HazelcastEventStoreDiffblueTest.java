package de.codecentric.boot.admin.server.eventstore;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.hazelcast.map.IMap;
import com.hazelcast.map.impl.proxy.MapProxyImpl;
import com.hazelcast.map.listener.MapListener;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.mockito.Mockito;
import reactor.test.StepVerifier;

public class HazelcastEventStoreDiffblueTest {
  /**
   * Method under test: {@link HazelcastEventStore#HazelcastEventStore(int, IMap)}
   */
  @Test
  public void testNewHazelcastEventStore() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    MapProxyImpl<InstanceId, List<InstanceEvent>> eventLog = mock(MapProxyImpl.class);
    when(eventLog.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act and Assert
    StepVerifier.FirstStep<InstanceEvent> createResult = StepVerifier
        .create((new HazelcastEventStore(3, eventLog)).findAll());
    createResult.expectComplete().verify();
    verify(eventLog).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Method under test: {@link HazelcastEventStore#HazelcastEventStore(IMap)}
   */
  @Test
  public void testNewHazelcastEventStore2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    MapProxyImpl<InstanceId, List<InstanceEvent>> eventLogs = mock(MapProxyImpl.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    // Act and Assert
    StepVerifier.FirstStep<InstanceEvent> createResult = StepVerifier
        .create((new HazelcastEventStore(eventLogs)).findAll());
    createResult.expectComplete().verify();
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
  }
}
