package de.codecentric.boot.admin.server.web.client.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ResolveInstanceExceptionDiffblueTest {
  /**
   * Test {@link ResolveInstanceException#ResolveInstanceException(String)}.
   *
   * <ul>
   *   <li>When {@code An error occurred}.
   *   <li>Then return Cause is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ResolveInstanceException#ResolveInstanceException(String)}
   */
  @Test
  @DisplayName(
      "Test new ResolveInstanceException(String); when 'An error occurred'; then return Cause is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ResolveInstanceException.<init>(String)",
    "void ResolveInstanceException.<init>(String, Throwable)"
  })
  void testNewResolveInstanceException_whenAnErrorOccurred_thenReturnCauseIsNull() {
    // Arrange and Act
    ResolveInstanceException actualResolveInstanceException =
        new ResolveInstanceException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualResolveInstanceException.getMessage());
    assertNull(actualResolveInstanceException.getCause());
    assertEquals(0, actualResolveInstanceException.getSuppressed().length);
  }

  /**
   * Test {@link ResolveInstanceException#ResolveInstanceException(String, Throwable)}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   *   <li>Then return Cause is {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link ResolveInstanceException#ResolveInstanceException(String,
   * Throwable)}
   */
  @Test
  @DisplayName(
      "Test new ResolveInstanceException(String, Throwable); when Throwable(); then return Cause is Throwable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ResolveInstanceException.<init>(String)",
    "void ResolveInstanceException.<init>(String, Throwable)"
  })
  void testNewResolveInstanceException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ResolveInstanceException actualResolveInstanceException =
        new ResolveInstanceException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualResolveInstanceException.getMessage());
    assertEquals(0, actualResolveInstanceException.getSuppressed().length);
    assertSame(cause, actualResolveInstanceException.getCause());
  }
}
