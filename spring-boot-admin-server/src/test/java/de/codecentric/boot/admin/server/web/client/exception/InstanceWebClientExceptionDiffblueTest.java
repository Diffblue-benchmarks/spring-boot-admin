package de.codecentric.boot.admin.server.web.client.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceWebClientExceptionDiffblueTest {
  /**
   * Test {@link InstanceWebClientException#InstanceWebClientException(String)}.
   *
   * <ul>
   *   <li>When {@code An error occurred}.
   *   <li>Then return Cause is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceWebClientException#InstanceWebClientException(String)}
   */
  @Test
  @DisplayName(
      "Test new InstanceWebClientException(String); when 'An error occurred'; then return Cause is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceWebClientException.<init>(String)",
    "void InstanceWebClientException.<init>(String, Throwable)"
  })
  void testNewInstanceWebClientException_whenAnErrorOccurred_thenReturnCauseIsNull() {
    // Arrange and Act
    InstanceWebClientException actualInstanceWebClientException =
        new InstanceWebClientException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualInstanceWebClientException.getMessage());
    assertNull(actualInstanceWebClientException.getCause());
    assertEquals(0, actualInstanceWebClientException.getSuppressed().length);
  }

  /**
   * Test {@link InstanceWebClientException#InstanceWebClientException(String, Throwable)}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   *   <li>Then return Cause is {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceWebClientException#InstanceWebClientException(String,
   * Throwable)}
   */
  @Test
  @DisplayName(
      "Test new InstanceWebClientException(String, Throwable); when Throwable(); then return Cause is Throwable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceWebClientException.<init>(String)",
    "void InstanceWebClientException.<init>(String, Throwable)"
  })
  void testNewInstanceWebClientException_whenThrowable_thenReturnCauseIsThrowable() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    InstanceWebClientException actualInstanceWebClientException =
        new InstanceWebClientException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualInstanceWebClientException.getMessage());
    assertEquals(0, actualInstanceWebClientException.getSuppressed().length);
    assertSame(cause, actualInstanceWebClientException.getCause());
  }
}
