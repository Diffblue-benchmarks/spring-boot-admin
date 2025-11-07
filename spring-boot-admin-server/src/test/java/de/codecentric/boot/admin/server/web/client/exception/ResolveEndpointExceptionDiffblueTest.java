package de.codecentric.boot.admin.server.web.client.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ResolveEndpointExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link ResolveEndpointException#ResolveEndpointException(String)}
   */
  @Test
  public void testNewResolveEndpointException() {
    // Arrange and Act
    ResolveEndpointException actualResolveEndpointException = new ResolveEndpointException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualResolveEndpointException.getMessage());
    assertNull(actualResolveEndpointException.getCause());
    assertEquals(0, actualResolveEndpointException.getSuppressed().length);
  }

  /**
   * Method under test:
   * {@link ResolveEndpointException#ResolveEndpointException(String, Throwable)}
   */
  @Test
  public void testNewResolveEndpointException2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ResolveEndpointException actualResolveEndpointException = new ResolveEndpointException("An error occurred", cause);

    // Assert
    assertEquals("An error occurred", actualResolveEndpointException.getMessage());
    assertEquals(0, actualResolveEndpointException.getSuppressed().length);
    assertSame(cause, actualResolveEndpointException.getCause());
  }
}
