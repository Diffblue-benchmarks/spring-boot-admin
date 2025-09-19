package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class AbstractEventNotifierDiffblueTest {
  /**
   * Test {@link AbstractEventNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   *
   * <p>Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
   */
  @Test
  @DisplayName("Test notify(InstanceEvent) with 'InstanceEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono AbstractEventNotifier.notify(InstanceEvent)"})
  void testNotifyWithInstanceEvent() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act
    Mono<Void> actualPublisher =
        remindingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AbstractEventNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   *
   * <p>Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
   */
  @Test
  @DisplayName("Test notify(InstanceEvent) with 'InstanceEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono AbstractEventNotifier.notify(InstanceEvent)"})
  void testNotifyWithInstanceEvent2() throws AssertionError {
    // Arrange
    DingTalkNotifier dingTalkNotifier =
        new DingTalkNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    dingTalkNotifier.setEnabled(false);

    // Act
    Mono<Void> actualPublisher =
        dingTalkNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AbstractEventNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   *
   * <p>Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
   */
  @Test
  @DisplayName("Test notify(InstanceEvent) with 'InstanceEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono AbstractEventNotifier.notify(InstanceEvent)"})
  void testNotifyWithInstanceEvent3() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new SnapshottingInstanceRepository(new InMemoryEventStore()));

    // Act
    Mono<Void> actualPublisher =
        remindingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AbstractEventNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test shouldNotify(InstanceEvent, Instance); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractEventNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify_thenReturnFalse() {
    // Arrange
    LoggingNotifier loggingNotifier =
        new LoggingNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertFalse(
        loggingNotifier.shouldNotify(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
  }

  /**
   * Test {@link AbstractEventNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test shouldNotify(InstanceEvent, Instance); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractEventNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify_thenReturnTrue() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier =
        new RemindingNotifier(
            delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertTrue(remindingNotifier.shouldNotify(mock(InstanceEvent.class), mock(Instance.class)));
  }

  /**
   * Test {@link AbstractEventNotifier#isEnabled()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventNotifier#isEnabled()}
   */
  @Test
  @DisplayName("Test isEnabled(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractEventNotifier.isEnabled()"})
  void testIsEnabled_thenReturnFalse() {
    // Arrange
    LoggingNotifier loggingNotifier =
        new LoggingNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()));
    loggingNotifier.setEnabled(false);

    // Act and Assert
    assertFalse(loggingNotifier.isEnabled());
  }

  /**
   * Test {@link AbstractEventNotifier#isEnabled()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventNotifier#isEnabled()}
   */
  @Test
  @DisplayName("Test isEnabled(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractEventNotifier.isEnabled()"})
  void testIsEnabled_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        new LoggingNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()))
            .isEnabled());
  }
}
