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
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ExtendWith(MockitoExtension.class)
class AbstractEventNotifierDiffblueTest {
  @Mock private InstanceRepository instanceRepository;

  @Mock private Notifier notifier;

  @InjectMocks private RemindingNotifier remindingNotifier;

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
   * <ul>
   *   <li>Then calls {@link InstanceRepository#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
   */
  @Test
  @DisplayName("Test notify(InstanceEvent) with 'InstanceEvent'; then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono AbstractEventNotifier.notify(InstanceEvent)"})
  void testNotifyWithInstanceEvent_thenCallsFind() throws AssertionError {
    // Arrange
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(instanceRepository.find(Mockito.<InstanceId>any())).thenReturn(justResult);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            remindingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectError().verify();
    verify(instanceRepository).find(isA(InstanceId.class));
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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate, repository);

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    LoggingNotifier loggingNotifier = new LoggingNotifier(repository);
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
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    assertTrue(new LoggingNotifier(repository).isEnabled());
  }
}
