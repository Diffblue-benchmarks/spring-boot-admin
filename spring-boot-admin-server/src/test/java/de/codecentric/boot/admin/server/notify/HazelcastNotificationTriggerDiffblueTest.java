package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.http.server.reactive.ChannelSendOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class HazelcastNotificationTriggerDiffblueTest {
  /**
   * Method under test:
   * {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  public void testSendNotifications() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Notifier notifier = mock(Notifier.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(notifier.notify(Mockito.<InstanceEvent>any()))
        .thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());
    HazelcastNotificationTrigger hazelcastNotificationTrigger = new HazelcastNotificationTrigger(notifier, events,
        new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
  }

  /**
   * Method under test:
   * {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  public void testSendNotifications2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(channelSendOperator.doOnError(Mockito.<Consumer<Throwable>>any()))
        .thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));
    Notifier notifier = mock(Notifier.class);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());
    HazelcastNotificationTrigger hazelcastNotificationTrigger = new HazelcastNotificationTrigger(notifier, events,
        new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(channelSendOperator).doOnError(isA(Consumer.class));
  }

  /**
   * Method under test:
   * {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  public void testSendNotifications3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 = new ChannelSendOperator<>(source, mock(Function.class));

    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);
    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.doOnError(Mockito.<Consumer<Throwable>>any())).thenReturn(channelSendOperator);
    Notifier notifier = mock(Notifier.class);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator3);
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());
    HazelcastNotificationTrigger hazelcastNotificationTrigger = new HazelcastNotificationTrigger(notifier, events,
        new ConcurrentHashMap<>());

    // Act
    Mono<Void> actualSendNotificationsResult = hazelcastNotificationTrigger
        .sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(channelSendOperator3).doOnError(isA(Consumer.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualSendNotificationsResult);
  }

  /**
   * Method under test:
   * {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  public void testSendNotifications4() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ConcurrentHashMap<InstanceId, Long> sentNotifications = new ConcurrentHashMap<>();
    sentNotifications.put(InstanceId.of("42"), 59L);
    Notifier notifier = mock(Notifier.class);
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());
    HazelcastNotificationTrigger hazelcastNotificationTrigger = new HazelcastNotificationTrigger(notifier, events,
        sentNotifications);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(hazelcastNotificationTrigger.sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  public void testSendNotifications5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 = new ChannelSendOperator<>(source, mock(Function.class));

    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);
    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.doOnError(Mockito.<Consumer<Throwable>>any())).thenReturn(channelSendOperator);
    Notifier notifier = mock(Notifier.class);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator3);

    ConcurrentHashMap<InstanceId, Long> sentNotifications = new ConcurrentHashMap<>();
    sentNotifications.put(InstanceId.of("42"), 59L);
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());
    HazelcastNotificationTrigger hazelcastNotificationTrigger = new HazelcastNotificationTrigger(notifier, events,
        sentNotifications);

    // Act
    Mono<Void> actualSendNotificationsResult = hazelcastNotificationTrigger
        .sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), Long.MAX_VALUE));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(channelSendOperator3).doOnError(isA(Consumer.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualSendNotificationsResult);
  }
}
