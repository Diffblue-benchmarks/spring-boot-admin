package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class RegistrationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Registration.Builder#build()}
   *   <li>{@link Registration.Builder#healthUrl(String)}
   *   <li>{@link Registration.Builder#managementUrl(String)}
   *   <li>{@link Registration.Builder#name(String)}
   *   <li>{@link Registration.Builder#serviceUrl(String)}
   *   <li>{@link Registration.Builder#source(String)}
   * </ul>
   */
  @Test
  public void testBuilderBuild() {
    // Arrange and Act
    Registration actualBuildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Assert
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("Source", actualBuildResult.getSource());
    assertEquals("https://example.org/example", actualBuildResult.getHealthUrl());
    assertEquals("https://example.org/example", actualBuildResult.getManagementUrl());
    assertEquals("https://example.org/example", actualBuildResult.getServiceUrl());
    assertTrue(actualBuildResult.getMetadata().isEmpty());
  }

  /**
   * Method under test: {@link Registration.Builder#clearMetadata()}
   */
  @Test
  public void testBuilderClearMetadata() {
    // Arrange
    Registration.Builder builderResult = Registration.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.clearMetadata());
  }

  /**
   * Method under test: {@link Registration.Builder#metadata(String, String)}
   */
  @Test
  public void testBuilderMetadata() {
    // Arrange
    Registration.Builder builderResult = Registration.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.metadata("Metadata Key", "42"));
  }

  /**
   * Method under test: {@link Registration.Builder#metadata(Map)}
   */
  @Test
  public void testBuilderMetadata2() {
    // Arrange
    Registration.Builder builderResult = Registration.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.metadata(new HashMap<>()));
  }

  /**
   * Method under test: {@link Registration.Builder#metadata(Map)}
   */
  @Test
  public void testBuilderMetadata3() {
    // Arrange
    Registration.Builder builderResult = Registration.builder();

    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", "foo");

    // Act and Assert
    assertSame(builderResult, builderResult.metadata(metadata));
  }

  /**
   * Method under test: {@link Registration.Builder#metadata(Map)}
   */
  @Test
  public void testBuilderMetadata4() {
    // Arrange
    Registration.Builder builderResult = Registration.builder();

    HashMap<String, String> metadata = new HashMap<>();
    metadata.computeIfPresent("foo", mock(BiFunction.class));
    metadata.put("foo", "foo");

    // Act and Assert
    assertSame(builderResult, builderResult.metadata(metadata));
  }
}
