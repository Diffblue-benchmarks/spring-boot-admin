package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Duration;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class IntervalCheckDiffblueTest {
  @Mock private Duration duration;

  @InjectMocks private IntervalCheck intervalCheck;

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
   * Test {@link IntervalCheck#start()}.
   *
   * <ul>
   *   <li>Given {@link Duration} {@link Duration#toNanos()} return {@link Long#MAX_VALUE}.
   *   <li>Then calls {@link Duration#toNanos()}.
   * </ul>
   *
   * <p>Method under test: {@link IntervalCheck#start()}
   */
  @Test
  @DisplayName("Test start(); given Duration toNanos() return MAX_VALUE; then calls toNanos()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IntervalCheck.start()"})
  void testStart_givenDurationToNanosReturnMax_value_thenCallsToNanos() {
    // Arrange
    when(duration.toNanos()).thenReturn(Long.MAX_VALUE);

    // Act
    intervalCheck.start();

    // Assert
    verify(duration, atLeast(1)).toNanos();
  }

  /**
   * Test {@link IntervalCheck#start()}.
   *
   * <ul>
   *   <li>Given {@link Duration} {@link Duration#toNanos()} return one.
   *   <li>Then calls {@link Duration#toNanos()}.
   * </ul>
   *
   * <p>Method under test: {@link IntervalCheck#start()}
   */
  @Test
  @DisplayName("Test start(); given Duration toNanos() return one; then calls toNanos()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IntervalCheck.start()"})
  void testStart_givenDurationToNanosReturnOne_thenCallsToNanos() {
    // Arrange
    when(duration.toNanos()).thenReturn(1L);

    // Act
    intervalCheck.start();

    // Assert
    verify(duration, atLeast(1)).toNanos();
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
            "check {} for all instances",
            mock(Function.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(intervalCheck.checkAllInstances());
    createResult.expectComplete().verify();
  }
}
