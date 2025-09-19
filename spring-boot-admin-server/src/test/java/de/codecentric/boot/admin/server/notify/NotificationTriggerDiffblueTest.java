package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {NotificationTrigger.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class NotificationTriggerDiffblueTest {
  @Autowired private NotificationTrigger notificationTrigger;

  @MockitoBean private Notifier notifier;

  @MockitoBean private Publisher<InstanceEvent> publisher;

  /**
   * Test {@link NotificationTrigger#sendNotifications(InstanceEvent)}.
   *
   * <p>Method under test: {@link NotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @DisplayName("Test sendNotifications(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono NotificationTrigger.sendNotifications(InstanceEvent)"})
  void testSendNotifications() {
    // Arrange
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);

    // Act
    notificationTrigger.sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
  }

  /**
   * Test {@link NotificationTrigger#sendNotifications(InstanceEvent)}.
   *
   * <p>Method under test: {@link NotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @DisplayName("Test sendNotifications(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono NotificationTrigger.sendNotifications(InstanceEvent)"})
  void testSendNotifications2() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doOnError(Mockito.<Consumer<Throwable>>any()))
        .thenReturn(channelSendOperator2);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);

    // Act
    notificationTrigger.sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(channelSendOperator).doOnError(isA(Consumer.class));
  }

  /**
   * Test {@link NotificationTrigger#sendNotifications(InstanceEvent)}.
   *
   * <p>Method under test: {@link NotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @DisplayName("Test sendNotifications(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono NotificationTrigger.sendNotifications(InstanceEvent)"})
  void testSendNotifications3() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.doOnError(Mockito.<Consumer<Throwable>>any()))
        .thenReturn(channelSendOperator);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator3);

    // Act
    Mono<Void> actualSendNotificationsResult =
        notificationTrigger.sendNotifications(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(channelSendOperator3).doOnError(isA(Consumer.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualSendNotificationsResult);
  }

  /**
   * Test {@link NotificationTrigger#sendNotifications(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@link ConcurrentHashMap#ConcurrentHashMap()} {@link InstanceId} with value is
   *       {@code 42} is one.
   * </ul>
   *
   * <p>Method under test: {@link NotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test sendNotifications(InstanceEvent); given ConcurrentHashMap() InstanceId with value is '42' is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono NotificationTrigger.sendNotifications(InstanceEvent)"})
  void testSendNotifications_givenConcurrentHashMapInstanceIdWithValueIs42IsOne()
      throws AssertionError {
    // Arrange
    ConcurrentHashMap<InstanceId, Long> sentNotifications = new ConcurrentHashMap<>();
    sentNotifications.put(InstanceId.of("42"), 1L);
    Notifier notifier = mock(Notifier.class);
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, sentNotifications);

    // Act
    Mono<Void> actualPublisher =
        hazelcastNotificationTrigger.sendNotifications(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }
}
