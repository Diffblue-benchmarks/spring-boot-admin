package de.codecentric.boot.admin.server.eventstore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class OptimisticLockingExceptionDiffblueTest {
  /**
   * Test {@link OptimisticLockingException#OptimisticLockingException(String)}.
   * <p>
   * Method under test: {@link OptimisticLockingException#OptimisticLockingException(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void OptimisticLockingException.<init>(String)"})
  public void testNewOptimisticLockingException() {
    // Arrange and Act
    OptimisticLockingException actualOptimisticLockingException = new OptimisticLockingException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualOptimisticLockingException.getMessage());
    assertNull(actualOptimisticLockingException.getCause());
    assertEquals(0, actualOptimisticLockingException.getSuppressed().length);
  }
}
