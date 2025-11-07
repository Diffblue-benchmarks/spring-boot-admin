package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Registration.Builder;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {Builder.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationDiffblueTest {
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
   *   <li>{@link Builder#source(String)}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Builder.<init>()", "Registration Builder.build()", "Builder Builder.healthUrl(String)",
      "Builder Builder.managementUrl(String)", "Builder Builder.name(String)", "Builder Builder.serviceUrl(String)",
      "Builder Builder.source(String)", "String Builder.toString()"})
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
   * Test Builder {@link Builder#clearMetadata()}.
   * <p>
   * Method under test: {@link Builder#clearMetadata()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.clearMetadata()"})
  public void testBuilderClearMetadata() {
    // Arrange
    Builder builderResult = Registration.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.clearMetadata());
  }

  /**
   * Test Builder {@link Builder#metadata(String, String)} with {@code metadataKey}, {@code metadataValue}.
   * <p>
   * Method under test: {@link Builder#metadata(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.metadata(String, String)"})
  public void testBuilderMetadataWithMetadataKeyMetadataValue() {
    // Arrange
    Builder builderResult = Registration.builder();

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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.metadata(Map)"})
  public void testBuilderMetadataWithMetadata_givenFoo_whenHashMapFooIsFoo() {
    // Arrange
    Builder builderResult = Registration.builder();

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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Builder.metadata(Map)"})
  public void testBuilderMetadataWithMetadata_whenHashMap() {
    // Arrange
    Builder builderResult = Registration.builder();

    // Act and Assert
    assertSame(builderResult, builderResult.metadata(new HashMap<>()));
  }

  /**
   * Test {@link Registration#equals(Object)}, and {@link Registration#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Registration#equals(Object)}
   *   <li>{@link Registration#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link Registration#equals(Object)}, and {@link Registration#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Registration#equals(Object)}
   *   <li>{@link Registration#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl(null)
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl(null)
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link Registration#equals(Object)}, and {@link Registration#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Registration#equals(Object)}
   *   <li>{@link Registration#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl(null)
        .source("Source")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl(null)
        .source("Source")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link Registration#equals(Object)}, and {@link Registration#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Registration#equals(Object)}
   *   <li>{@link Registration#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link Registration#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Registration#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl(null)
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Registration#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Registration#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Registration#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Registration#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("https://example.org/example")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Registration#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Registration#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl(null)
        .source("Source")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Registration#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Registration#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("")
        .source("Source")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Registration#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Registration#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Name")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Registration#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Registration#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/examplehttps://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    Registration buildResult2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Registration#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Registration#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link Registration#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Registration#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Registration.equals(Object)", "int Registration.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to Registration");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Registration#copyOf(Registration)}
   *   <li>{@link Registration#create(String, String)}
   *   <li>{@link Registration#toString()}
   *   <li>{@link Registration#getHealthUrl()}
   *   <li>{@link Registration#getManagementUrl()}
   *   <li>{@link Registration#getName()}
   *   <li>{@link Registration#getServiceUrl()}
   *   <li>{@link Registration#getSource()}
   *   <li>{@link Registration#toBuilder()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Registration.copyOf(Registration)", "Builder Registration.create(String, String)",
      "String Registration.getHealthUrl()", "String Registration.getManagementUrl()", "String Registration.getName()",
      "String Registration.getServiceUrl()", "String Registration.getSource()", "Builder Registration.toBuilder()",
      "String Registration.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    Registration registration = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act
    buildResult.copyOf(registration);
    buildResult.create("Name", "https://example.org/example");
    String actualToStringResult = buildResult.toString();
    String actualHealthUrl = buildResult.getHealthUrl();
    String actualManagementUrl = buildResult.getManagementUrl();
    String actualName = buildResult.getName();
    String actualServiceUrl = buildResult.getServiceUrl();
    String actualSource = buildResult.getSource();
    buildResult.toBuilder();

    // Assert
    assertEquals("Name", actualName);
    assertEquals(
        "Registration(name=Name, managementUrl=https://example.org/example, healthUrl=https://example.org/example,"
            + " serviceUrl=https://example.org/example, source=Source)",
        actualToStringResult);
    assertEquals("Source", actualSource);
    assertEquals("https://example.org/example", actualHealthUrl);
    assertEquals("https://example.org/example", actualManagementUrl);
    assertEquals("https://example.org/example", actualServiceUrl);
  }

  /**
   * Test {@link Registration#getMetadata()}.
   * <p>
   * Method under test: {@link Registration#getMetadata()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map Registration.getMetadata()"})
  public void testGetMetadata() {
    // Arrange
    Registration buildResult = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertTrue(buildResult.getMetadata().isEmpty());
  }
}
