package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.util.ArrayList;
import java.util.function.Function;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.http.server.reactive.ChannelSendOperator;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class RemindingNotifierDiffblueTest {
  /**
   * Method under test:
   * {@link RemindingNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(delegate.notify(Mockito.<InstanceEvent>any()))
        .thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));
    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(remindingNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectComplete().verify();
    verify(delegate).notify(isA(InstanceEvent.class));
  }

  /**
   * Method under test: {@link RemindingNotifier#sendReminders()}
   */
  @Test
  public void testSendReminders() throws AssertionError {
    // Arrange
    Notifier delegate = mock(Notifier.class);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create((new RemindingNotifier(delegate, new EventsourcingInstanceRepository(new InMemoryEventStore())))
            .sendReminders());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link RemindingNotifier#shouldStartReminder(InstanceEvent)}
   */
  @Test
  public void testShouldStartReminder() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertFalse(remindingNotifier.shouldStartReminder(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
  }

  /**
   * Method under test: {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}
   */
  @Test
  public void testShouldEndReminder() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    RemindingNotifier remindingNotifier = new RemindingNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertTrue(remindingNotifier.shouldEndReminder(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
  }

  /**
   * Method under test: {@link RemindingNotifier#shouldEndReminder(InstanceEvent)}
   */
  @Test
  public void testShouldEndReminder2() {
    // Arrange
    Notifier delegate = mock(Notifier.class);

    // Act and Assert
    assertFalse((new RemindingNotifier(delegate, new EventsourcingInstanceRepository(new InMemoryEventStore())))
        .shouldEndReminder(null));
  }

  /**
   * Method under test:
   * {@link RemindingNotifier#RemindingNotifier(Notifier, InstanceRepository)}
   */
  @Test
  public void testNewRemindingNotifier() {
    // Arrange
    Notifier delegate = mock(Notifier.class);

    // Act and Assert
    assertTrue(
        (new RemindingNotifier(delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()))).isEnabled());
  }
}
