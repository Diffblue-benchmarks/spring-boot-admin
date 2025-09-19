package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
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
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class HazelcastNotificationTriggerDiffblueTest {
  @Mock private ConcurrentMap<InstanceId, Long> concurrentMap;

  @InjectMocks private HazelcastNotificationTrigger hazelcastNotificationTrigger;

  @Mock private Notifier notifier;

  /**
   * Test {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}.
   *
   * <p>Method under test: {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @DisplayName("Test sendNotifications(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono HazelcastNotificationTrigger.sendNotifications(InstanceEvent)"})
  void testSendNotifications() throws AssertionError {
    // Arrange
    when(concurrentMap.getOrDefault(Mockito.<Object>any(), Mockito.<Long>any())).thenReturn(1L);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            hazelcastNotificationTrigger.sendNotifications(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
    verify(concurrentMap).getOrDefault(isA(Object.class), eq(-1L));
  }

  /**
   * Test {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}.
   *
   * <p>Method under test: {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @DisplayName("Test sendNotifications(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono HazelcastNotificationTrigger.sendNotifications(InstanceEvent)"})
  void testSendNotifications2() {
    // Arrange
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);
    when(concurrentMap.replace(Mockito.<InstanceId>any(), Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(true);
    when(concurrentMap.getOrDefault(Mockito.<Object>any(), Mockito.<Long>any())).thenReturn(1L);

    // Act
    hazelcastNotificationTrigger.sendNotifications(
        new InstanceDeregisteredEvent(InstanceId.of("42"), Long.MAX_VALUE));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(concurrentMap).getOrDefault(isA(Object.class), eq(-1L));
    verify(concurrentMap).replace(isA(InstanceId.class), eq(1L), eq(9223372036854775807L));
  }

  /**
   * Test {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}.
   *
   * <p>Method under test: {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @DisplayName("Test sendNotifications(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono HazelcastNotificationTrigger.sendNotifications(InstanceEvent)"})
  void testSendNotifications3() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doOnError(Mockito.<Consumer<Throwable>>any()))
        .thenReturn(channelSendOperator2);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);
    when(concurrentMap.replace(Mockito.<InstanceId>any(), Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(true);
    when(concurrentMap.getOrDefault(Mockito.<Object>any(), Mockito.<Long>any())).thenReturn(1L);

    // Act
    hazelcastNotificationTrigger.sendNotifications(
        new InstanceDeregisteredEvent(InstanceId.of("42"), Long.MAX_VALUE));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(concurrentMap).getOrDefault(isA(Object.class), eq(-1L));
    verify(concurrentMap).replace(isA(InstanceId.class), eq(1L), eq(9223372036854775807L));
    verify(channelSendOperator).doOnError(isA(Consumer.class));
  }

  /**
   * Test {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}.
   *
   * <p>Method under test: {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @DisplayName("Test sendNotifications(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono HazelcastNotificationTrigger.sendNotifications(InstanceEvent)"})
  void testSendNotifications4() {
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
    when(concurrentMap.replace(Mockito.<InstanceId>any(), Mockito.<Long>any(), Mockito.<Long>any()))
        .thenReturn(true);
    when(concurrentMap.getOrDefault(Mockito.<Object>any(), Mockito.<Long>any())).thenReturn(1L);

    // Act
    Mono<Void> actualSendNotificationsResult =
        hazelcastNotificationTrigger.sendNotifications(
            new InstanceDeregisteredEvent(InstanceId.of("42"), Long.MAX_VALUE));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(concurrentMap).getOrDefault(isA(Object.class), eq(-1L));
    verify(concurrentMap).replace(isA(InstanceId.class), eq(1L), eq(9223372036854775807L));
    verify(channelSendOperator3).doOnError(isA(Consumer.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualSendNotificationsResult);
  }

  /**
   * Test {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link ConcurrentMap#putIfAbsent(Object, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link HazelcastNotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @DisplayName("Test sendNotifications(InstanceEvent); then calls putIfAbsent(Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono HazelcastNotificationTrigger.sendNotifications(InstanceEvent)"})
  void testSendNotifications_thenCallsPutIfAbsent() {
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
    when(concurrentMap.putIfAbsent(Mockito.<InstanceId>any(), Mockito.<Long>any()))
        .thenReturn(null);
    when(concurrentMap.getOrDefault(Mockito.<Object>any(), Mockito.<Long>any())).thenReturn(-1L);

    // Act
    Mono<Void> actualSendNotificationsResult =
        hazelcastNotificationTrigger.sendNotifications(
            new InstanceDeregisteredEvent(InstanceId.of("42"), Long.MAX_VALUE));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(concurrentMap).getOrDefault(isA(Object.class), eq(-1L));
    verify(concurrentMap).putIfAbsent(isA(InstanceId.class), eq(9223372036854775807L));
    verify(channelSendOperator3).doOnError(isA(Consumer.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualSendNotificationsResult);
  }
}
