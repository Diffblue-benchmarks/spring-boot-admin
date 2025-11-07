package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

public class AbstractEventNotifierDiffblueTest {
  /**
   * Test {@link AbstractEventNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   * <p>
   * Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono AbstractEventNotifier.notify(InstanceEvent)"})
  public void testNotifyWithInstanceEvent() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(remindingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AbstractEventNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   * <p>
   * Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono AbstractEventNotifier.notify(InstanceEvent)"})
  public void testNotifyWithInstanceEvent2() throws AssertionError {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));
    loggingNotifier.setEnabled(false);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(loggingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AbstractEventNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   * <p>
   * Method under test: {@link AbstractEventNotifier#notify(InstanceEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono AbstractEventNotifier.notify(InstanceEvent)"})
  public void testNotifyWithInstanceEvent3() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate,
        new SnapshottingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(remindingNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AbstractEventNotifier#shouldNotify(InstanceEvent, Instance)}.
   * <p>
   * Method under test: {@link AbstractEventNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEventNotifier.shouldNotify(InstanceEvent, Instance)"})
  public void testShouldNotify() {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertFalse(loggingNotifier.shouldNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test {@link AbstractEventNotifier#isEnabled()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventNotifier#isEnabled()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEventNotifier.isEnabled()"})
  public void testIsEnabled_thenReturnFalse() {
    // Arrange
    LoggingNotifier loggingNotifier = new LoggingNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()));
    loggingNotifier.setEnabled(false);

    // Act and Assert
    assertFalse(loggingNotifier.isEnabled());
  }

  /**
   * Test {@link AbstractEventNotifier#isEnabled()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventNotifier#isEnabled()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractEventNotifier.isEnabled()"})
  public void testIsEnabled_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new LoggingNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()))).isEnabled());
  }
}
