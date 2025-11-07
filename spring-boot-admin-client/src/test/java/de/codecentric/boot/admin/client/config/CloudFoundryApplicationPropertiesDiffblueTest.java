package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CloudFoundryApplicationPropertiesDiffblueTest {
  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}, and {@link CloudFoundryApplicationProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CloudFoundryApplicationProperties#equals(Object)}
   *   <li>{@link CloudFoundryApplicationProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties2 = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties2.setApplicationId("42");
    cloudFoundryApplicationProperties2.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties2.setUris(new ArrayList<>());

    // Act and Assert
    assertEquals(cloudFoundryApplicationProperties, cloudFoundryApplicationProperties2);
    int expectedHashCodeResult = cloudFoundryApplicationProperties.hashCode();
    assertEquals(expectedHashCodeResult, cloudFoundryApplicationProperties2.hashCode());
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}, and {@link CloudFoundryApplicationProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CloudFoundryApplicationProperties#equals(Object)}
   *   <li>{@link CloudFoundryApplicationProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId(null);
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties2 = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties2.setApplicationId(null);
    cloudFoundryApplicationProperties2.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties2.setUris(new ArrayList<>());

    // Act and Assert
    assertEquals(cloudFoundryApplicationProperties, cloudFoundryApplicationProperties2);
    int expectedHashCodeResult = cloudFoundryApplicationProperties.hashCode();
    assertEquals(expectedHashCodeResult, cloudFoundryApplicationProperties2.hashCode());
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}, and {@link CloudFoundryApplicationProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CloudFoundryApplicationProperties#equals(Object)}
   *   <li>{@link CloudFoundryApplicationProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex(null);
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties2 = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties2.setApplicationId("42");
    cloudFoundryApplicationProperties2.setInstanceIndex(null);
    cloudFoundryApplicationProperties2.setUris(new ArrayList<>());

    // Act and Assert
    assertEquals(cloudFoundryApplicationProperties, cloudFoundryApplicationProperties2);
    int expectedHashCodeResult = cloudFoundryApplicationProperties.hashCode();
    assertEquals(expectedHashCodeResult, cloudFoundryApplicationProperties2.hashCode());
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}, and {@link CloudFoundryApplicationProperties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CloudFoundryApplicationProperties#equals(Object)}
   *   <li>{@link CloudFoundryApplicationProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    // Act and Assert
    assertEquals(cloudFoundryApplicationProperties, cloudFoundryApplicationProperties);
    int expectedHashCodeResult = cloudFoundryApplicationProperties.hashCode();
    assertEquals(expectedHashCodeResult, cloudFoundryApplicationProperties.hashCode());
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("Instance Index");
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties2 = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties2.setApplicationId("42");
    cloudFoundryApplicationProperties2.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties2.setUris(new ArrayList<>());

    // Act and Assert
    assertNotEquals(cloudFoundryApplicationProperties, cloudFoundryApplicationProperties2);
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId(null);
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties2 = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties2.setApplicationId("42");
    cloudFoundryApplicationProperties2.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties2.setUris(new ArrayList<>());

    // Act and Assert
    assertNotEquals(cloudFoundryApplicationProperties, cloudFoundryApplicationProperties2);
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex("42");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties2 = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties2.setApplicationId("42");
    cloudFoundryApplicationProperties2.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties2.setUris(new ArrayList<>());

    // Act and Assert
    assertNotEquals(cloudFoundryApplicationProperties, cloudFoundryApplicationProperties2);
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex(null);
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties2 = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties2.setApplicationId("42");
    cloudFoundryApplicationProperties2.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties2.setUris(new ArrayList<>());

    // Act and Assert
    assertNotEquals(cloudFoundryApplicationProperties, cloudFoundryApplicationProperties2);
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ArrayList<String> uris = new ArrayList<>();
    uris.add("42");

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(uris);

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties2 = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties2.setApplicationId("42");
    cloudFoundryApplicationProperties2.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties2.setUris(new ArrayList<>());

    // Act and Assert
    assertNotEquals(cloudFoundryApplicationProperties, cloudFoundryApplicationProperties2);
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    // Act and Assert
    assertNotEquals(cloudFoundryApplicationProperties, null);
  }

  /**
   * Test {@link CloudFoundryApplicationProperties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CloudFoundryApplicationProperties.equals(Object)",
      "int CloudFoundryApplicationProperties.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    // Act and Assert
    assertNotEquals(cloudFoundryApplicationProperties, "Different type to CloudFoundryApplicationProperties");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CloudFoundryApplicationProperties}
   *   <li>{@link CloudFoundryApplicationProperties#setApplicationId(String)}
   *   <li>{@link CloudFoundryApplicationProperties#setInstanceIndex(String)}
   *   <li>{@link CloudFoundryApplicationProperties#setUris(List)}
   *   <li>{@link CloudFoundryApplicationProperties#toString()}
   *   <li>{@link CloudFoundryApplicationProperties#getApplicationId()}
   *   <li>{@link CloudFoundryApplicationProperties#getInstanceIndex()}
   *   <li>{@link CloudFoundryApplicationProperties#getUris()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CloudFoundryApplicationProperties.<init>()",
      "String CloudFoundryApplicationProperties.getApplicationId()",
      "String CloudFoundryApplicationProperties.getInstanceIndex()", "List CloudFoundryApplicationProperties.getUris()",
      "void CloudFoundryApplicationProperties.setApplicationId(String)",
      "void CloudFoundryApplicationProperties.setInstanceIndex(String)",
      "void CloudFoundryApplicationProperties.setUris(List)", "String CloudFoundryApplicationProperties.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    CloudFoundryApplicationProperties actualCloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    actualCloudFoundryApplicationProperties.setApplicationId("42");
    actualCloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    ArrayList<String> uris = new ArrayList<>();
    actualCloudFoundryApplicationProperties.setUris(uris);
    String actualToStringResult = actualCloudFoundryApplicationProperties.toString();
    String actualApplicationId = actualCloudFoundryApplicationProperties.getApplicationId();
    String actualInstanceIndex = actualCloudFoundryApplicationProperties.getInstanceIndex();
    List<String> actualUris = actualCloudFoundryApplicationProperties.getUris();

    // Assert
    assertEquals("42", actualApplicationId);
    assertEquals("CloudFoundryApplicationProperties(applicationId=42, instanceIndex=Instance Index, uris=[])",
        actualToStringResult);
    assertEquals("Instance Index", actualInstanceIndex);
    assertTrue(actualUris.isEmpty());
    assertSame(uris, actualUris);
  }
}
