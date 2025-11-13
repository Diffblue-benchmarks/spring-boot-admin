package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class AbstractStatusChangeNotifierDiffblueTest {
  /**
   * Test {@link AbstractStatusChangeNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#notify(InstanceEvent)}
   */
  @Test
  @DisplayName("Test notify(InstanceEvent) with 'InstanceEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono AbstractStatusChangeNotifier.notify(InstanceEvent)"})
  void testNotifyWithInstanceEvent() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    LoggingNotifier loggingNotifier = new LoggingNotifier(repository);

    // Act
    Mono<Void> actualPublisher =
        loggingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AbstractStatusChangeNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#notify(InstanceEvent)}
   */
  @Test
  @DisplayName("Test notify(InstanceEvent) with 'InstanceEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono AbstractStatusChangeNotifier.notify(InstanceEvent)"})
  void testNotifyWithInstanceEvent2() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));
    LoggingNotifier loggingNotifier = new LoggingNotifier(repository);

    // Act
    Mono<Void> actualPublisher =
        loggingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AbstractStatusChangeNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#notify(InstanceEvent)}
   */
  @Test
  @DisplayName("Test notify(InstanceEvent) with 'InstanceEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono AbstractStatusChangeNotifier.notify(InstanceEvent)"})
  void testNotifyWithInstanceEvent3() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    LoggingNotifier loggingNotifier = new LoggingNotifier(repository);
    loggingNotifier.setEnabled(false);

    // Act
    Mono<Void> actualPublisher =
        loggingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AbstractStatusChangeNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#shouldNotify(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test shouldNotify(InstanceEvent, Instance); given 'Status'; when StatusInfo getStatus() return 'Status'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractStatusChangeNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify_givenStatus_whenStatusInfoGetStatusReturnStatus_thenReturnTrue() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    LoggingNotifier loggingNotifier = new LoggingNotifier(repository);

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    boolean actualShouldNotifyResult =
        loggingNotifier.shouldNotify(
            new InstanceStatusChangedEvent(mock(InstanceId.class), 1L, statusInfo),
            mock(Instance.class));

    // Assert
    verify(statusInfo).getStatus();
    assertTrue(actualShouldNotifyResult);
  }

  /**
   * Test {@link AbstractStatusChangeNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code UP}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code UP}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#shouldNotify(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test shouldNotify(InstanceEvent, Instance); given 'UP'; when StatusInfo getStatus() return 'UP'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractStatusChangeNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify_givenUp_whenStatusInfoGetStatusReturnUp_thenReturnFalse() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    LoggingNotifier loggingNotifier = new LoggingNotifier(repository);

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");

    // Act
    boolean actualShouldNotifyResult =
        loggingNotifier.shouldNotify(
            new InstanceStatusChangedEvent(mock(InstanceId.class), 1L, statusInfo),
            mock(Instance.class));

    // Assert
    verify(statusInfo).getStatus();
    assertFalse(actualShouldNotifyResult);
  }

  /**
   * Test {@link AbstractStatusChangeNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>When {@link InstanceEvent}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#shouldNotify(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test shouldNotify(InstanceEvent, Instance); when InstanceEvent; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractStatusChangeNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify_whenInstanceEvent_thenReturnFalse() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    assertFalse(
        new LoggingNotifier(repository)
            .shouldNotify(mock(InstanceEvent.class), mock(Instance.class)));
  }

  /**
   * Test {@link AbstractStatusChangeNotifier#getLastStatus(InstanceId)}.
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#getLastStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test getLastStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String AbstractStatusChangeNotifier.getLastStatus(InstanceId)"})
  void testGetLastStatus() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    LoggingNotifier loggingNotifier = new LoggingNotifier(repository);

    // Act and Assert
    assertEquals("UNKNOWN", loggingNotifier.getLastStatus(InstanceId.of("42")));
  }

  /**
   * Test {@link AbstractStatusChangeNotifier#updateLastStatus(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>Then calls {@link StatusInfo#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#updateLastStatus(InstanceEvent)}
   */
  @Test
  @DisplayName("Test updateLastStatus(InstanceEvent); given 'Status'; then calls getStatus()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractStatusChangeNotifier.updateLastStatus(InstanceEvent)"})
  void testUpdateLastStatus_givenStatus_thenCallsGetStatus() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    LoggingNotifier loggingNotifier = new LoggingNotifier(repository);

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    loggingNotifier.updateLastStatus(
        new InstanceStatusChangedEvent(mock(InstanceId.class), 1L, statusInfo));

    // Assert
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link AbstractStatusChangeNotifier#setIgnoreChanges(String[])}.
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#setIgnoreChanges(String[])}
   */
  @Test
  @DisplayName("Test setIgnoreChanges(String[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractStatusChangeNotifier.setIgnoreChanges(String[])"})
  void testSetIgnoreChanges() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    LoggingNotifier loggingNotifier = new LoggingNotifier(repository);

    // Act
    loggingNotifier.setIgnoreChanges(new String[] {"Ignore Changes"});

    // Assert
    assertArrayEquals(new String[] {"Ignore Changes"}, loggingNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link AbstractStatusChangeNotifier#getIgnoreChanges()}.
   *
   * <p>Method under test: {@link AbstractStatusChangeNotifier#getIgnoreChanges()}
   */
  @Test
  @DisplayName("Test getIgnoreChanges()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] AbstractStatusChangeNotifier.getIgnoreChanges()"})
  void testGetIgnoreChanges() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    assertArrayEquals(
        new String[] {"UNKNOWN:UP"}, new LoggingNotifier(repository).getIgnoreChanges());
  }
}
