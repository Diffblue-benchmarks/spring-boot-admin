package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.client.registration.Application.Builder;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Builder.class})
@ExtendWith(SpringExtension.class)
class ApplicationDiffblueTest {
  @Autowired
  private Builder builder;

  /**
   * Test Builder {@link Builder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Builder#build()}
   *   <li>{@link Builder#healthUrl(String)}
   *   <li>{@link Builder#managementUrl(String)}
   *   <li>{@link Builder#name(String)}
   *   <li>{@link Builder#serviceUrl(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test Builder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Builder.<init>()", "Application Builder.build()", "Builder Builder.healthUrl(String)",
      "Builder Builder.managementUrl(String)", "Builder Builder.name(String)", "Builder Builder.serviceUrl(String)",
      "String Builder.toString()"})
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
   * Test Builder {@link Builder#clearMetadata()}.
   * <p>
   * Method under test: {@link Builder#clearMetadata()}
   */
  @Test
  @DisplayName("Test Builder clearMetadata()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Builder Builder.clearMetadata()"})
  void testBuilderClearMetadata() {
    // Arrange
    Builder builderResult = Application.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.clearMetadata());
  }

  /**
   * Test Builder {@link Builder#metadata(String, String)} with {@code metadataKey}, {@code metadataValue}.
   * <p>
   * Method under test: {@link Builder#metadata(String, String)}
   */
  @Test
  @DisplayName("Test Builder metadata(String, String) with 'metadataKey', 'metadataValue'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Builder Builder.metadata(String, String)"})
  void testBuilderMetadataWithMetadataKeyMetadataValue() {
    // Arrange
    Builder builderResult = Application.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.metadata("Metadata Key", "42"));
  }

  /**
   * Test Builder {@link Builder#metadata(Map)} with {@code metadata}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#metadata(Map)}
   */
  @Test
  @DisplayName("Test Builder metadata(Map) with 'metadata'; given 'foo'; when HashMap() 'foo' is 'foo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Builder Builder.metadata(Map)"})
  void testBuilderMetadataWithMetadata_givenFoo_whenHashMapFooIsFoo() {
    // Arrange
    Builder builderResult = Application.builder();

    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", "foo");

    // Act and Assert
    assertSame(builderResult, builderResult.metadata(metadata));
  }

  /**
   * Test Builder {@link Builder#metadata(Map)} with {@code metadata}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#metadata(Map)}
   */
  @Test
  @DisplayName("Test Builder metadata(Map) with 'metadata'; when HashMap()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Builder Builder.metadata(Map)"})
  void testBuilderMetadataWithMetadata_whenHashMap() {
    // Arrange
    Builder builderResult = Application.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.metadata(new HashMap<>()));
  }

  /**
   * Test {@link Application#Application(String, String, String, String, Map)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#Application(String, String, String, String, Map)}
   */
  @Test
  @DisplayName("Test new Application(String, String, String, String, Map); when 'Name'; then return 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Application.<init>(String, String, String, String, Map)"})
  void testNewApplication_whenName_thenReturnName() {
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
   * Test {@link Application#equals(Object)}, and {@link Application#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Application#equals(Object)}
   *   <li>{@link Application#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Application buildResult = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();
    Application buildResult2 = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link Application#equals(Object)}, and {@link Application#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Application#equals(Object)}
   *   <li>{@link Application#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Application buildResult = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Application buildResult = Application.builder()
        .healthUrl("Name")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();
    Application buildResult2 = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Application buildResult = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("Name")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();
    Application buildResult2 = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Application buildResult = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("https://example.org/example")
        .serviceUrl("https://example.org/example")
        .build();
    Application buildResult2 = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Application buildResult = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("Name")
        .build();
    Application buildResult2 = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Application buildResult = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Application buildResult = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to Application");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Builder Application.create(String)", "String Application.getHealthUrl()",
      "String Application.getManagementUrl()", "String Application.getName()", "String Application.getServiceUrl()",
      "String Application.toString()"})
  void testGettersAndSetters() {
    // Arrange
    Application buildResult = Application.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .build();

    // Act
    buildResult.create("Name");
    String actualToStringResult = buildResult.toString();
    String actualHealthUrl = buildResult.getHealthUrl();
    String actualManagementUrl = buildResult.getManagementUrl();
    String actualName = buildResult.getName();

    // Assert
    assertEquals(
        "Application(name=Name, managementUrl=https://example.org/example, healthUrl=https://example.org/example,"
            + " serviceUrl=https://example.org/example)",
        actualToStringResult);
    assertEquals("Name", actualName);
    assertEquals("https://example.org/example", actualHealthUrl);
    assertEquals("https://example.org/example", actualManagementUrl);
    assertEquals("https://example.org/example", buildResult.getServiceUrl());
  }

  /**
   * Test {@link Application#getMetadata()}.
   * <p>
   * Method under test: {@link Application#getMetadata()}
   */
  @Test
  @DisplayName("Test getMetadata()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map Application.getMetadata()"})
  void testGetMetadata() {
    // Arrange, Act and Assert
    assertTrue((new Application("Name", "https://example.org/example", "https://example.org/example",
        "https://example.org/example", new HashMap<>())).getMetadata().isEmpty());
  }
}
