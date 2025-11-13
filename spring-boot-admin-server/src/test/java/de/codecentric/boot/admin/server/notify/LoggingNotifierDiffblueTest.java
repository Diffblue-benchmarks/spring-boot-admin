package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class LoggingNotifierDiffblueTest {
  /**
   * Test {@link LoggingNotifier#LoggingNotifier(InstanceRepository)}.
   *
   * <p>Method under test: {@link LoggingNotifier#LoggingNotifier(InstanceRepository)}
   */
  @Test
  @DisplayName("Test new LoggingNotifier(InstanceRepository)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LoggingNotifier.<init>(InstanceRepository)"})
  void testNewLoggingNotifier() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act
    LoggingNotifier actualLoggingNotifier = new LoggingNotifier(repository);

    // Assert
    assertTrue(actualLoggingNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualLoggingNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link LoggingNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link LoggingNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono LoggingNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            new LoggingNotifier(repository)
                .doNotify(mock(InstanceEvent.class), mock(Instance.class)));
    createResult.expectError().verify();
  }
}
