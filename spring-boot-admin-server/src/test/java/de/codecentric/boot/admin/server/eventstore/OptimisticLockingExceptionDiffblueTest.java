package de.codecentric.boot.admin.server.eventstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class OptimisticLockingExceptionDiffblueTest {
  /**
   * Test {@link OptimisticLockingException#OptimisticLockingException(String)}.
   *
   * <p>Method under test: {@link OptimisticLockingException#OptimisticLockingException(String)}
   */
  @Test
  @DisplayName("Test new OptimisticLockingException(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OptimisticLockingException.<init>(String)"})
  void testNewOptimisticLockingException() {
    // Arrange and Act
    OptimisticLockingException actualOptimisticLockingException =
        new OptimisticLockingException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualOptimisticLockingException.getMessage());
    assertNull(actualOptimisticLockingException.getCause());
    assertEquals(0, actualOptimisticLockingException.getSuppressed().length);
  }
}
