package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.springframework.http.MediaType;

public class ApiMediaTypeHandlerDiffblueTest {
  /**
   * Method under test: {@link ApiMediaTypeHandler#isApiMediaType(MediaType)}
   */
  @Test
  public void testIsApiMediaType() {
    // Arrange, Act and Assert
    assertFalse((new ApiMediaTypeHandler()).isApiMediaType(null));
  }
}
