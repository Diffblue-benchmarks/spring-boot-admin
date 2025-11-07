package de.codecentric.boot.admin.server.web.client.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class InstanceWebClientExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link InstanceWebClientException#InstanceWebClientException(String)}
   */
  @Test
  public void testNewInstanceWebClientException() {
    // Arrange and Act
    InstanceWebClientException actualInstanceWebClientException = new InstanceWebClientException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualInstanceWebClientException.getMessage());
    assertNull(actualInstanceWebClientException.getCause());
    assertEquals(0, actualInstanceWebClientException.getSuppressed().length);
  }

  /**
   * Method under test:
   * {@link InstanceWebClientException#InstanceWebClientException(String, Throwable)}
   */
  @Test
  public void testNewInstanceWebClientException2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    InstanceWebClientException actualInstanceWebClientException = new InstanceWebClientException("An error occurred",
        cause);

    // Assert
    assertEquals("An error occurred", actualInstanceWebClientException.getMessage());
    assertEquals(0, actualInstanceWebClientException.getSuppressed().length);
    assertSame(cause, actualInstanceWebClientException.getCause());
  }
}
