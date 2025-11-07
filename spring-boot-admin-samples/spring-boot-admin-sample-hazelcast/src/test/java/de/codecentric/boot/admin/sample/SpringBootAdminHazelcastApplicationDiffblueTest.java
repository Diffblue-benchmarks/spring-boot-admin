package de.codecentric.boot.admin.sample;

import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.Notifier;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class SpringBootAdminHazelcastApplicationDiffblueTest {
  /**
   * Method under test:
   * {@link SpringBootAdminHazelcastApplication#loggingNotifier()}
   */
  @Test
  void testLoggingNotifier() throws AssertionError {
    // Arrange and Act
    Notifier actualLoggingNotifierResult = (new SpringBootAdminHazelcastApplication()).loggingNotifier();

    // Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(actualLoggingNotifierResult.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link SpringBootAdminHazelcastApplication#loggingNotifier()}
   */
  @Test
  void testLoggingNotifier2() throws AssertionError {
    // Arrange and Act
    Notifier actualLoggingNotifierResult = (new SpringBootAdminHazelcastApplication()).loggingNotifier();

    // Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(actualLoggingNotifierResult.notify(new InstanceDeregisteredEvent(InstanceId.of("Value"), 1L)));
    createResult.expectComplete().verify();
  }
}
