package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.notify.CompositeNotifier;
import de.codecentric.boot.admin.server.notify.HazelcastNotificationTrigger;
import de.codecentric.boot.admin.server.notify.NotificationTrigger;
import de.codecentric.boot.admin.server.notify.Notifier;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {NotificationTrigger.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class AbstractEventHandlerDiffblueTest {
  @Autowired
  private AbstractEventHandler<InstanceEvent> abstractEventHandler;

  @MockBean
  private Notifier notifier;

  @MockBean
  private Publisher<InstanceEvent> publisher;

  /**
   * Test {@link AbstractEventHandler#start()}.
   * <ul>
   *   <li>Given {@link ConcurrentHashMap#ConcurrentHashMap()} All is {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link IMap#addEntryListener(MapListener, boolean)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  public void testStart_givenConcurrentHashMapAllIsHashMap_thenCallsAddEntryListener() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLog = mock(IMap.class);
    when(eventLog.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    HazelcastEventStore events = new HazelcastEventStore(3, eventLog);

    ConcurrentHashMap<InstanceId, Long> sentNotifications = new ConcurrentHashMap<>();
    sentNotifications.putAll(new HashMap<>());

    // Act
    (new HazelcastNotificationTrigger(mock(CompositeNotifier.class), events, sentNotifications)).start();

    // Assert
    verify(eventLog).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   * <ul>
   *   <li>Given {@link ConcurrentHashMap#ConcurrentHashMap()} IfAbsent {@link InstanceId} with value is {@code 42} is {@link Long#MAX_VALUE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  public void testStart_givenConcurrentHashMapIfAbsentInstanceIdWithValueIs42IsMax_value() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLog = mock(IMap.class);
    when(eventLog.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    HazelcastEventStore events = new HazelcastEventStore(3, eventLog);

    ConcurrentHashMap<InstanceId, Long> sentNotifications = new ConcurrentHashMap<>();
    sentNotifications.putIfAbsent(InstanceId.of("42"), Long.MAX_VALUE);

    // Act
    (new HazelcastNotificationTrigger(mock(CompositeNotifier.class), events, sentNotifications)).start();

    // Assert
    verify(eventLog).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   * <ul>
   *   <li>Given {@link ConcurrentHashMap#ConcurrentHashMap()} IfAbsent {@link InstanceId} with value is {@code 42} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  public void testStart_givenConcurrentHashMapIfAbsentInstanceIdWithValueIs42IsOne() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLog = mock(IMap.class);
    when(eventLog.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    HazelcastEventStore events = new HazelcastEventStore(3, eventLog);

    ConcurrentHashMap<InstanceId, Long> sentNotifications = new ConcurrentHashMap<>();
    sentNotifications.putIfAbsent(InstanceId.of("42"), 1L);
    sentNotifications.putAll(new HashMap<>());

    // Act
    (new HazelcastNotificationTrigger(mock(CompositeNotifier.class), events, sentNotifications)).start();

    // Assert
    verify(eventLog).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   * <ul>
   *   <li>Given {@link ConcurrentHashMap#ConcurrentHashMap()} IfAbsent {@link InstanceId} with value is {@code 42} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  public void testStart_givenConcurrentHashMapIfAbsentInstanceIdWithValueIs42IsOne2() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLog = mock(IMap.class);
    when(eventLog.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    HazelcastEventStore events = new HazelcastEventStore(3, eventLog);

    ConcurrentHashMap<InstanceId, Long> sentNotifications = new ConcurrentHashMap<>();
    sentNotifications.putIfAbsent(InstanceId.of("42"), 1L);
    sentNotifications.putIfAbsent(InstanceId.of("42"), 0L);
    sentNotifications.putIfAbsent(InstanceId.of("42"), Long.MAX_VALUE);

    // Act
    (new HazelcastNotificationTrigger(mock(CompositeNotifier.class), events, sentNotifications)).start();

    // Assert
    verify(eventLog).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   * <ul>
   *   <li>Given {@link ConcurrentHashMap#ConcurrentHashMap()} IfAbsent {@link InstanceId} with value is {@code 42} is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  public void testStart_givenConcurrentHashMapIfAbsentInstanceIdWithValueIs42IsZero() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLog = mock(IMap.class);
    when(eventLog.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    HazelcastEventStore events = new HazelcastEventStore(3, eventLog);

    ConcurrentHashMap<InstanceId, Long> sentNotifications = new ConcurrentHashMap<>();
    sentNotifications.putIfAbsent(InstanceId.of("42"), 0L);
    sentNotifications.putIfAbsent(InstanceId.of("42"), Long.MAX_VALUE);

    // Act
    (new HazelcastNotificationTrigger(mock(CompositeNotifier.class), events, sentNotifications)).start();

    // Assert
    verify(eventLog).addEntryListener(isA(MapListener.class), eq(true));
  }

  /**
   * Test {@link AbstractEventHandler#createScheduler()}.
   * <p>
   * Method under test: {@link AbstractEventHandler#createScheduler()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.scheduler.Scheduler AbstractEventHandler.createScheduler()"})
  public void testCreateScheduler() {
    // Arrange, Act and Assert
    assertFalse(abstractEventHandler.createScheduler().isDisposed());
  }
}
