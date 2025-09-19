package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.util.ArrayList;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.server.reactive.ChannelSendOperator;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class RemindingNotifierDiffblueTest {
  /**
   * Test {@link RemindingNotifier#RemindingNotifier(Notifier, InstanceRepository)}.
   *
   * <ul>
   *   <li>When {@link Notifier}.
   *   <li>Then return Enabled.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#RemindingNotifier(Notifier, InstanceRepository)}
   */
  @Test
  @DisplayName(
      "Test new RemindingNotifier(Notifier, InstanceRepository); when Notifier; then return Enabled")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RemindingNotifier.<init>(Notifier, InstanceRepository)"})
  void testNewRemindingNotifier_whenNotifier_thenReturnEnabled() {
    // Arrange
    Notifier delegate = mock(Notifier.class);

    // Act
    RemindingNotifier actualRemindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Assert
    assertTrue(actualRemindingNotifier.isEnabled());
  }

  /**
   * Test {@link RemindingNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link Notifier#notify(InstanceEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance); then calls notify(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono RemindingNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify_thenCallsNotify() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(delegate.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            remindingNotifier.doNotify(mock(InstanceEvent.class), mock(Instance.class)));
    createResult.expectComplete().verify();
    verify(delegate).notify(isA(InstanceEvent.class));
  }

  /**
   * Test {@link RemindingNotifier#sendReminders()}.
   *
   * <p>Method under test: {@link RemindingNotifier#sendReminders()}
   */
  @Test
  @DisplayName("Test sendReminders()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono RemindingNotifier.sendReminders()"})
  void testSendReminders() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(remindingNotifier.sendReminders());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link RemindingNotifier#shouldStartReminder(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code DOWN}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#shouldStartReminder(InstanceEvent)}
   */
  @Test
  @DisplayName("Test shouldStartReminder(InstanceEvent); given 'DOWN'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RemindingNotifier.shouldStartReminder(InstanceEvent)"})
  void testShouldStartReminder_givenDown_thenReturnTrue() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("DOWN");

    // Act
    boolean actualShouldStartReminderResult =
        remindingNotifier.shouldStartReminder(
            new InstanceStatusChangedEvent(mock(InstanceId.class), 1L, statusInfo));

    // Assert
    verify(statusInfo).getStatus();
    assertTrue(actualShouldStartReminderResult);
  }

  /**
   * Test {@link RemindingNotifier#shouldStartReminder(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#shouldStartReminder(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test shouldStartReminder(InstanceEvent); given 'Status'; when StatusInfo getStatus() return 'Status'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RemindingNotifier.shouldStartReminder(InstanceEvent)"})
  void testShouldStartReminder_givenStatus_whenStatusInfoGetStatusReturnStatus() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    boolean actualShouldStartReminderResult =
        remindingNotifier.shouldStartReminder(
            new InstanceStatusChangedEvent(mock(InstanceId.class), 1L, statusInfo));

    // Assert
    verify(statusInfo).getStatus();
    assertFalse(actualShouldStartReminderResult);
  }

  /**
   * Test {@link RemindingNotifier#shouldStartReminder(InstanceEvent)}.
   *
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code 42}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#shouldStartReminder(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test shouldStartReminder(InstanceEvent); when InstanceId with value is '42'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RemindingNotifier.shouldStartReminder(InstanceEvent)"})
  void testShouldStartReminder_whenInstanceIdWithValueIs42_thenReturnFalse() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act
    boolean actualShouldStartReminderResult =
        remindingNotifier.shouldStartReminder(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    assertFalse(actualShouldStartReminderResult);
  }

  /**
   * Test {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code DOWN}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}
   */
  @Test
  @DisplayName("Test shouldEndReminder(InstanceEvent); given 'DOWN'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RemindingNotifier.shouldEndReminder(InstanceEvent)"})
  void testShouldEndReminder_givenDown_thenReturnFalse() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("DOWN");

    // Act
    boolean actualShouldEndReminderResult =
        remindingNotifier.shouldEndReminder(
            new InstanceStatusChangedEvent(mock(InstanceId.class), 1L, statusInfo));

    // Assert
    verify(statusInfo).getStatus();
    assertFalse(actualShouldEndReminderResult);
  }

  /**
   * Test {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test shouldEndReminder(InstanceEvent); given 'Status'; when StatusInfo getStatus() return 'Status'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RemindingNotifier.shouldEndReminder(InstanceEvent)"})
  void testShouldEndReminder_givenStatus_whenStatusInfoGetStatusReturnStatus() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    boolean actualShouldEndReminderResult =
        remindingNotifier.shouldEndReminder(
            new InstanceStatusChangedEvent(mock(InstanceId.class), 1L, statusInfo));

    // Assert
    verify(statusInfo).getStatus();
    assertTrue(actualShouldEndReminderResult);
  }

  /**
   * Test {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}.
   *
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test shouldEndReminder(InstanceEvent); when InstanceId with value is '42'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RemindingNotifier.shouldEndReminder(InstanceEvent)"})
  void testShouldEndReminder_whenInstanceIdWithValueIs42_thenReturnTrue() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act
    boolean actualShouldEndReminderResult =
        remindingNotifier.shouldEndReminder(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    assertTrue(actualShouldEndReminderResult);
  }
}
