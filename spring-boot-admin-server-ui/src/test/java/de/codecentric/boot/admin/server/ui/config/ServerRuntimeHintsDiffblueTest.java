package de.codecentric.boot.admin.server.ui.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.management.loading.MLet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aot.hint.JavaSerializationHint;
import org.springframework.aot.hint.ReflectionHints;
import org.springframework.aot.hint.ResourceHints;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.SerializationHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ServerRuntimeHints.class})
@ExtendWith(SpringExtension.class)
class ServerRuntimeHintsDiffblueTest {
  @Autowired
  private ServerRuntimeHints serverRuntimeHints;

  /**
   * Method under test:
   * {@link ServerRuntimeHints#registerHints(RuntimeHints, ClassLoader)}
   */
  @Test
  void testRegisterHints() {
    // Arrange
    RuntimeHints hints = new RuntimeHints();

    // Act
    serverRuntimeHints.registerHints(hints, new MLet());

    // Assert
    Stream<JavaSerializationHint> javaSerializationHintsResult = hints.serialization().javaSerializationHints();
    List<JavaSerializationHint> collectResult = javaSerializationHintsResult.limit(5).collect(Collectors.toList());
    assertEquals(5, collectResult.size());
    assertNull(collectResult.get(0).getReachableType());
    assertNull(collectResult.get(1).getReachableType());
    assertNull(collectResult.get(3).getReachableType());
    assertNull(collectResult.get(4).getReachableType());
  }

  /**
   * Method under test:
   * {@link ServerRuntimeHints#registerHints(RuntimeHints, ClassLoader)}
   */
  @Test
  void testRegisterHints2() {
    // Arrange
    RuntimeHints hints = mock(RuntimeHints.class);
    when(hints.serialization()).thenReturn(new SerializationHints());
    when(hints.resources()).thenReturn(new ResourceHints());
    when(hints.reflection()).thenReturn(new ReflectionHints());

    // Act
    serverRuntimeHints.registerHints(hints, new MLet());

    // Assert
    verify(hints).reflection();
    verify(hints).resources();
    verify(hints).serialization();
  }
}
