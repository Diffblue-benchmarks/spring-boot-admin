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
import de.codecentric.boot.admin.server.domain.events.InstanceEndpointsDetectedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.util.ArrayList;
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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ExtendWith(MockitoExtension.class)
class RemindingNotifierDiffblueTest {
  @Mock private Notifier notifier;

  @InjectMocks private RemindingNotifier remindingNotifier;

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act
    RemindingNotifier actualRemindingNotifier = new RemindingNotifier(delegate, repository);

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
   * <p>Method under test: {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}
   */
  @Test
  @DisplayName("Test shouldEndReminder(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RemindingNotifier.shouldEndReminder(InstanceEvent)"})
  void testShouldEndReminder() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

    // Act
    boolean actualShouldEndReminderResult =
        remindingNotifier.shouldEndReminder(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    assertTrue(actualShouldEndReminderResult);
  }

  /**
   * Test {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code DOWN}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code DOWN}.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test shouldEndReminder(InstanceEvent); given 'DOWN'; when StatusInfo getStatus() return 'DOWN'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RemindingNotifier.shouldEndReminder(InstanceEvent)"})
  void testShouldEndReminder_givenDown_whenStatusInfoGetStatusReturnDown() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
   *   <li>Given {@link Notifier}.
   * </ul>
   *
   * <p>Method under test: {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}
   */
  @Test
  @DisplayName("Test shouldEndReminder(InstanceEvent); given Notifier")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RemindingNotifier.shouldEndReminder(InstanceEvent)"})
  void testShouldEndReminder_givenNotifier() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertFalse(
        remindingNotifier.shouldEndReminder(
            new InstanceEndpointsDetectedEvent(instance, 1L, Endpoints.empty())));
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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
}
