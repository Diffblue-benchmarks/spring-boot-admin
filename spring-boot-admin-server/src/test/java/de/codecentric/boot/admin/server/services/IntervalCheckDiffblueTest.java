package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Duration;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class IntervalCheckDiffblueTest {
  /**
   * Test {@link IntervalCheck#IntervalCheck(String, Function, Duration, Duration, Duration)}.
   *
   * <p>Method under test: {@link IntervalCheck#IntervalCheck(String, Function, Duration, Duration,
   * Duration)}
   */
  @Test
  @DisplayName("Test new IntervalCheck(String, Function, Duration, Duration, Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IntervalCheck.<init>(String, Function, Duration, Duration, Duration)"})
  void testNewIntervalCheck() {
    // Arrange
    Duration interval = Duration.ofSeconds(1L);

    // Act
    IntervalCheck actualIntervalCheck =
        new IntervalCheck(
            "Name", mock(Function.class), interval, Duration.ofSeconds(1L), Duration.ofSeconds(1L));

    // Assert
    Duration interval2 = actualIntervalCheck.getInterval();
    assertEquals(1000000000L, interval2.toNanos());
    assertSame(interval, interval2);
  }

  /**
   * Test {@link IntervalCheck#checkAllInstances()}.
   *
   * <p>Method under test: {@link IntervalCheck#checkAllInstances()}
   */
  @Test
  @DisplayName("Test checkAllInstances()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono IntervalCheck.checkAllInstances()"})
  void testCheckAllInstances() throws AssertionError {
    // Arrange
    IntervalCheck intervalCheck =
        new IntervalCheck(
            "Name",
            mock(Function.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(intervalCheck.checkAllInstances());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link IntervalCheck#checkAllInstances()}.
   *
   * <p>Method under test: {@link IntervalCheck#checkAllInstances()}
   */
  @Test
  @DisplayName("Test checkAllInstances()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono IntervalCheck.checkAllInstances()"})
  void testCheckAllInstances2() throws AssertionError {
    // Arrange
    IntervalCheck intervalCheck =
        new IntervalCheck(
            "Name",
            mock(Function.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    intervalCheck.setMinRetention(Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(intervalCheck.checkAllInstances());
    createResult.expectComplete().verify();
  }
}
