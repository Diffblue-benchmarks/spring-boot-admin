package de.codecentric.boot.admin.server.web.client.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ResolveEndpointExceptionDiffblueTest {
  /**
   * Test {@link ResolveEndpointException#ResolveEndpointException(String)}.
   *
   * <ul>
   *   <li>When {@code An error occurred}.
   *   <li>Then return Cause is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ResolveEndpointException#ResolveEndpointException(String)}
   */
  @Test
  @DisplayName(
      "Test new ResolveEndpointException(String); when 'An error occurred'; then return Cause is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ResolveEndpointException.<init>(String)",
    "void ResolveEndpointException.<init>(String, Throwable)"
  })
  void testNewResolveEndpointException_whenAnErrorOccurred_thenReturnCauseIsNull() {
    // Arrange and Act
    ResolveEndpointException actualResolveEndpointException =
        new ResolveEndpointException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualResolveEndpointException.getMessage());
    assertNull(actualResolveEndpointException.getCause());
    assertEquals(0, actualResolveEndpointException.getSuppressed().length);
  }

  /**
   * Test {@link ResolveEndpointException#ResolveEndpointException(String, Throwable)}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   *   <li>Then return Cause is {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link ResolveEndpointException#ResolveEndpointException(String,
   * Throwable)}
   */
  @Test
  @DisplayName(
      "Test new ResolveEndpointException(String, Throwable); when Throwable(); then return Cause is Throwable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ResolveEndpointException.<init>(String)",
    "void ResolveEndpointException.<init>(String, Throwable)"
  })
  void testNewResolveEndpointException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ResolveEndpointException actualResolveEndpointException =
        new ResolveEndpointException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualResolveEndpointException.getMessage());
    assertEquals(0, actualResolveEndpointException.getSuppressed().length);
    assertSame(cause, actualResolveEndpointException.getCause());
  }
}
