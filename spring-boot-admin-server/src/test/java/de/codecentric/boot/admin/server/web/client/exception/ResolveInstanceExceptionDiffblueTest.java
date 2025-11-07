package de.codecentric.boot.admin.server.web.client.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ResolveInstanceExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link ResolveInstanceException#ResolveInstanceException(String)}
   */
  @Test
  public void testNewResolveInstanceException() {
    // Arrange and Act
    ResolveInstanceException actualResolveInstanceException = new ResolveInstanceException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualResolveInstanceException.getMessage());
    assertNull(actualResolveInstanceException.getCause());
    assertEquals(0, actualResolveInstanceException.getSuppressed().length);
  }

  /**
   * Method under test:
   * {@link ResolveInstanceException#ResolveInstanceException(String, Throwable)}
   */
  @Test
  public void testNewResolveInstanceException2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ResolveInstanceException actualResolveInstanceException = new ResolveInstanceException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualResolveInstanceException.getMessage());
    assertEquals(0, actualResolveInstanceException.getSuppressed().length);
    assertSame(cause, actualResolveInstanceException.getCause());
  }
}
