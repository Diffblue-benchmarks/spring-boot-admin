package de.codecentric.boot.admin.client.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.management.loading.MLet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aot.hint.ReflectionHints;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClientRuntimeHints.class})
@ExtendWith(SpringExtension.class)
class ClientRuntimeHintsDiffblueTest {
  @Autowired
  private ClientRuntimeHints clientRuntimeHints;

  /**
   * Method under test:
   * {@link ClientRuntimeHints#registerHints(RuntimeHints, ClassLoader)}
   */
  @Test
  void testRegisterHints() {
    // Arrange
    RuntimeHints hints = mock(RuntimeHints.class);
    when(hints.reflection()).thenReturn(new ReflectionHints());

    // Act
    clientRuntimeHints.registerHints(hints, new MLet());

    // Assert
    verify(hints).reflection();
  }
}
