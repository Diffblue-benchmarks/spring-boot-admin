package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class ApplicationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Application.Builder#build()}
   *   <li>{@link Application.Builder#healthUrl(String)}
   *   <li>{@link Application.Builder#managementUrl(String)}
   *   <li>{@link Application.Builder#name(String)}
   *   <li>{@link Application.Builder#serviceUrl(String)}
   * </ul>
   */
  @Test
  void testBuilderBuild() {
    // Arrange and Act
    Application actualBuildResult = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Assert
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("https://example.org/example", actualBuildResult.getHealthUrl());
    assertEquals("https://example.org/example", actualBuildResult.getManagementUrl());
    assertEquals("https://example.org/example", actualBuildResult.getServiceUrl());
    assertTrue(actualBuildResult.getMetadata().isEmpty());
  }

  /**
   * Method under test: {@link Application.Builder#clearMetadata()}
   */
  @Test
  void testBuilderClearMetadata() {
    // Arrange
    Application.Builder builderResult = Application.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.clearMetadata());
  }

  /**
   * Method under test: {@link Application.Builder#metadata(String, String)}
   */
  @Test
  void testBuilderMetadata() {
    // Arrange
    Application.Builder builderResult = Application.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.metadata("Metadata Key", "42"));
  }

  /**
   * Method under test: {@link Application.Builder#metadata(Map)}
   */
  @Test
  void testBuilderMetadata2() {
    // Arrange
    Application.Builder builderResult = Application.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.metadata(new HashMap<>()));
  }

  /**
   * Method under test: {@link Application.Builder#metadata(Map)}
   */
  @Test
  void testBuilderMetadata3() {
    // Arrange
    Application.Builder builderResult = Application.builder();

    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", "foo");

    // Act and Assert
    assertSame(builderResult, builderResult.metadata(metadata));
  }

  /**
   * Method under test: {@link Application.Builder#metadata(Map)}
   */
  @Test
  void testBuilderMetadata4() {
    // Arrange
    Application.Builder builderResult = Application.builder();

    HashMap<String, String> metadata = new HashMap<>();
    metadata.computeIfPresent("foo", mock(BiFunction.class));
    metadata.put("foo", "foo");

    // Act and Assert
    assertSame(builderResult, builderResult.metadata(metadata));
  }

  /**
   * Method under test: {@link Application#getMetadata()}
   */
  @Test
  void testGetMetadata() {
    // Arrange, Act and Assert
    assertTrue((new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>())).getMetadata().isEmpty());
  }

  /**
   * Method under test: {@link Application#getMetadata()}
   */
  @Test
  void testGetMetadata2() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertTrue((new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", metadata)).getMetadata().isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Application#equals(Object)}
   *   <li>{@link Application#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Application application = new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>());
    Application application2 = new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>());

    // Act and Assert
    assertEquals(application, application2);
    int expectedHashCodeResult = application.hashCode();
    assertEquals(expectedHashCodeResult, application2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Application#equals(Object)}
   *   <li>{@link Application#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Application application = new Application("Name", null, "https://example.org/example",
        "https://example.org/example", new HashMap<>());
    Application application2 = new Application("Name", null, "https://example.org/example",
        "https://example.org/example", new HashMap<>());

    // Act and Assert
    assertEquals(application, application2);
    int expectedHashCodeResult = application.hashCode();
    assertEquals(expectedHashCodeResult, application2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Application#equals(Object)}
   *   <li>{@link Application#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Application application = new Application("Name", "https://example.org/example", "https://example.org/example",
        null, new HashMap<>());
    Application application2 = new Application("Name", "https://example.org/example", "https://example.org/example",
        null, new HashMap<>());

    // Act and Assert
    assertEquals(application, application2);
    int expectedHashCodeResult = application.hashCode();
    assertEquals(expectedHashCodeResult, application2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Application#equals(Object)}
   *   <li>{@link Application#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Application application = new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>());

    // Act and Assert
    assertEquals(application, application);
    int expectedHashCodeResult = application.hashCode();
    assertEquals(expectedHashCodeResult, application.hashCode());
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Application application = new Application("https://example.org/example", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", new HashMap<>());

    // Act and Assert
    assertNotEquals(application, new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()));
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Application application = new Application("Name", "Name", "https://example.org/example",
        "https://example.org/example", new HashMap<>());

    // Act and Assert
    assertNotEquals(application, new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()));
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Application application = new Application("Name", null, "https://example.org/example",
        "https://example.org/example", new HashMap<>());

    // Act and Assert
    assertNotEquals(application, new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()));
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Application application = new Application("Name", "https://example.org/example", "Name",
        "https://example.org/example", new HashMap<>());

    // Act and Assert
    assertNotEquals(application, new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()));
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Application application = new Application("Name", "https://example.org/example", "https://example.org/example",
        "Name", new HashMap<>());

    // Act and Assert
    assertNotEquals(application, new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()));
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Application application = new Application("Name", "https://example.org/example", "https://example.org/example",
        null, new HashMap<>());

    // Act and Assert
    assertNotEquals(application, new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()));
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("Name", "Name");
    Application application = new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", metadata);

    // Act and Assert
    assertNotEquals(application, new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()));
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.computeIfPresent("Name", mock(BiFunction.class));
    metadata.put("Name", "Name");
    Application application = new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", metadata);

    // Act and Assert
    assertNotEquals(application, new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()));
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()), null);
  }

  /**
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>()), "Different type to Application");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Application#create(String)}
   *   <li>{@link Application#toString()}
   *   <li>{@link Application#getHealthUrl()}
   *   <li>{@link Application#getManagementUrl()}
   *   <li>{@link Application#getName()}
   *   <li>{@link Application#getServiceUrl()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    Application application = new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>());

    // Act
    application.create("Name");
    String actualToStringResult = application.toString();
    String actualHealthUrl = application.getHealthUrl();
    String actualManagementUrl = application.getManagementUrl();
    String actualName = application.getName();

    // Assert
    assertEquals(
        "Application(name=Name, managementUrl=https://example.org/example, healthUrl=https://example.org/example,"
            + " serviceUrl=https://example.org/example)",
        actualToStringResult);
    assertEquals("Name", actualName);
    assertEquals("https://example.org/example", actualHealthUrl);
    assertEquals("https://example.org/example", actualManagementUrl);
    assertEquals("https://example.org/example", application.getServiceUrl());
  }

  /**
   * Method under test:
   * {@link Application#Application(String, String, String, String, Map)}
   */
  @Test
  void testNewApplication() {
    // Arrange and Act
    Application actualApplication = new Application("Name", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", new HashMap<>());

    // Assert
    assertEquals("Name", actualApplication.getName());
    assertEquals("https://example.org/example", actualApplication.getHealthUrl());
    assertEquals("https://example.org/example", actualApplication.getManagementUrl());
    assertEquals("https://example.org/example", actualApplication.getServiceUrl());
    assertTrue(actualApplication.getMetadata().isEmpty());
  }

  /**
   * Method under test:
   * {@link Application#Application(String, String, String, String, Map)}
   */
  @Test
  void testNewApplication2() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.computeIfPresent("name must not be empty!", mock(BiFunction.class));

    // Act
    Application actualApplication = new Application("Name", "https://example.org/example",
        "https://example.org/example", "https://example.org/example", metadata);

    // Assert
    assertEquals("Name", actualApplication.getName());
    assertEquals("https://example.org/example", actualApplication.getHealthUrl());
    assertEquals("https://example.org/example", actualApplication.getManagementUrl());
    assertEquals("https://example.org/example", actualApplication.getServiceUrl());
    assertTrue(actualApplication.getMetadata().isEmpty());
  }
}
