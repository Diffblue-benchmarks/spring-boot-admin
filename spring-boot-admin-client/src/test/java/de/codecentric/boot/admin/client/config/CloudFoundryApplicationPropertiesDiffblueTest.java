package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CloudFoundryApplicationPropertiesDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CloudFoundryApplicationProperties#equals(Object)}
   *   <li>{@link CloudFoundryApplicationProperties#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link CloudFoundryApplicationProperties#equals(Object)}
   *   <li>{@link CloudFoundryApplicationProperties#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link CloudFoundryApplicationProperties#equals(Object)}
   *   <li>{@link CloudFoundryApplicationProperties#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link CloudFoundryApplicationProperties#equals(Object)}
   *   <li>{@link CloudFoundryApplicationProperties#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link CloudFoundryApplicationProperties#equals(Object)}
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link CloudFoundryApplicationProperties}
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

    // Assert that nothing has changed
    assertEquals("42", actualApplicationId);
    assertEquals("CloudFoundryApplicationProperties(applicationId=42, instanceIndex=Instance Index, uris=[])",
        actualToStringResult);
    assertEquals("Instance Index", actualInstanceIndex);
    assertTrue(actualUris.isEmpty());
    assertSame(uris, actualUris);
  }
}
