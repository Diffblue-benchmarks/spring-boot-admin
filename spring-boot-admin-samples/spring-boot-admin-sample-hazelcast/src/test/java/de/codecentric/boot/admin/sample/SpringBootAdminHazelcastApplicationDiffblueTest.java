package de.codecentric.boot.admin.sample;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.Notifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class SpringBootAdminHazelcastApplicationDiffblueTest {
  /**
   * Test {@link SpringBootAdminHazelcastApplication#loggingNotifier()}.
   *
   * <p>Method under test: {@link SpringBootAdminHazelcastApplication#loggingNotifier()}
   */
  @Test
  @DisplayName("Test loggingNotifier()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Notifier SpringBootAdminHazelcastApplication.loggingNotifier()"})
  void testLoggingNotifier() throws AssertionError {
    // Arrange and Act
    Notifier actualLoggingNotifierResult =
        new SpringBootAdminHazelcastApplication().loggingNotifier();
    Mono<Void> actualPublisher =
        actualLoggingNotifierResult.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link SpringBootAdminHazelcastApplication#loggingNotifier()}.
   *
   * <p>Method under test: {@link SpringBootAdminHazelcastApplication#loggingNotifier()}
   */
  @Test
  @DisplayName("Test loggingNotifier()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Notifier SpringBootAdminHazelcastApplication.loggingNotifier()"})
  void testLoggingNotifier2() throws AssertionError {
    // Arrange and Act
    Notifier actualLoggingNotifierResult =
        new SpringBootAdminHazelcastApplication().loggingNotifier();
    Mono<Void> actualPublisher =
        actualLoggingNotifierResult.notify(
            new InstanceDeregisteredEvent(InstanceId.of("Value"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }
}
