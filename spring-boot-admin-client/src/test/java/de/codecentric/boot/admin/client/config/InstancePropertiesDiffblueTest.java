package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InstanceProperties.class})
@ExtendWith(SpringExtension.class)
class InstancePropertiesDiffblueTest {
  @Autowired
  private InstanceProperties instanceProperties;

  /**
   * Test {@link InstanceProperties#equals(Object)}, and {@link InstanceProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceProperties#equals(Object)}
   *   <li>{@link InstanceProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertEquals(instanceProperties, instanceProperties2);
    int expectedHashCodeResult = instanceProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceProperties2.hashCode());
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}, and {@link InstanceProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceProperties#equals(Object)}
   *   <li>{@link InstanceProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl(null);
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl(null);
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertEquals(instanceProperties, instanceProperties2);
    int expectedHashCodeResult = instanceProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceProperties2.hashCode());
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}, and {@link InstanceProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceProperties#equals(Object)}
   *   <li>{@link InstanceProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl(null);
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl(null);
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertEquals(instanceProperties, instanceProperties2);
    int expectedHashCodeResult = instanceProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceProperties2.hashCode());
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}, and {@link InstanceProperties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceProperties#equals(Object)}
   *   <li>{@link InstanceProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertEquals(instanceProperties, instanceProperties);
    int expectedHashCodeResult = instanceProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceProperties.hashCode());
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("Service Path");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl(null);
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("Service Path");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl(null);
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("Service Path");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl(null);
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("https://example.org/example", "https://example.org/example");

    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(metadata);
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("https://example.org/example");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(false);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("Service Path");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl(null);
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.HOST_NAME);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("https://example.org/example");
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath(null);
    instanceProperties.setServiceUrl("https://example.org/example");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("Service Path");

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl(null);

    InstanceProperties instanceProperties2 = new InstanceProperties();
    instanceProperties2.setHealthUrl("https://example.org/example");
    instanceProperties2.setManagementBaseUrl("https://example.org/example");
    instanceProperties2.setManagementUrl("https://example.org/example");
    instanceProperties2.setMetadata(new HashMap<>());
    instanceProperties2.setName("Name");
    instanceProperties2.setPreferIp(true);
    instanceProperties2.setServiceBaseUrl("https://example.org/example");
    instanceProperties2.setServiceHostType(ServiceHostType.IP);
    instanceProperties2.setServicePath("Service Path");
    instanceProperties2.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, instanceProperties2);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, null);
  }

  /**
   * Test {@link InstanceProperties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceProperties.equals(Object)", "int InstanceProperties.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    instanceProperties.setMetadata(new HashMap<>());
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceProperties, "Different type to InstanceProperties");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceProperties#setHealthUrl(String)}
   *   <li>{@link InstanceProperties#setManagementBaseUrl(String)}
   *   <li>{@link InstanceProperties#setManagementUrl(String)}
   *   <li>{@link InstanceProperties#setMetadata(Map)}
   *   <li>{@link InstanceProperties#setName(String)}
   *   <li>{@link InstanceProperties#setPreferIp(boolean)}
   *   <li>{@link InstanceProperties#setServiceBaseUrl(String)}
   *   <li>{@link InstanceProperties#setServiceHostType(ServiceHostType)}
   *   <li>{@link InstanceProperties#setServicePath(String)}
   *   <li>{@link InstanceProperties#setServiceUrl(String)}
   *   <li>{@link InstanceProperties#toString()}
   *   <li>{@link InstanceProperties#getHealthUrl()}
   *   <li>{@link InstanceProperties#getManagementBaseUrl()}
   *   <li>{@link InstanceProperties#getManagementUrl()}
   *   <li>{@link InstanceProperties#getMetadata()}
   *   <li>{@link InstanceProperties#getName()}
   *   <li>{@link InstanceProperties#getServiceBaseUrl()}
   *   <li>{@link InstanceProperties#getServiceHostType()}
   *   <li>{@link InstanceProperties#getServicePath()}
   *   <li>{@link InstanceProperties#getServiceUrl()}
   *   <li>{@link InstanceProperties#isPreferIp()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String InstanceProperties.getHealthUrl()", "String InstanceProperties.getManagementBaseUrl()",
      "String InstanceProperties.getManagementUrl()", "Map InstanceProperties.getMetadata()",
      "String InstanceProperties.getName()", "String InstanceProperties.getServiceBaseUrl()",
      "ServiceHostType InstanceProperties.getServiceHostType()", "String InstanceProperties.getServicePath()",
      "String InstanceProperties.getServiceUrl()", "boolean InstanceProperties.isPreferIp()",
      "void InstanceProperties.setHealthUrl(String)", "void InstanceProperties.setManagementBaseUrl(String)",
      "void InstanceProperties.setManagementUrl(String)", "void InstanceProperties.setMetadata(Map)",
      "void InstanceProperties.setName(String)", "void InstanceProperties.setPreferIp(boolean)",
      "void InstanceProperties.setServiceBaseUrl(String)",
      "void InstanceProperties.setServiceHostType(ServiceHostType)", "void InstanceProperties.setServicePath(String)",
      "void InstanceProperties.setServiceUrl(String)", "String InstanceProperties.toString()"})
  void testGettersAndSetters() {
    // Arrange
    InstanceProperties instanceProperties = new InstanceProperties();

    // Act
    instanceProperties.setHealthUrl("https://example.org/example");
    instanceProperties.setManagementBaseUrl("https://example.org/example");
    instanceProperties.setManagementUrl("https://example.org/example");
    HashMap<String, String> metadata = new HashMap<>();
    instanceProperties.setMetadata(metadata);
    instanceProperties.setName("Name");
    instanceProperties.setPreferIp(true);
    instanceProperties.setServiceBaseUrl("https://example.org/example");
    instanceProperties.setServiceHostType(ServiceHostType.IP);
    instanceProperties.setServicePath("Service Path");
    instanceProperties.setServiceUrl("https://example.org/example");
    String actualToStringResult = instanceProperties.toString();
    String actualHealthUrl = instanceProperties.getHealthUrl();
    String actualManagementBaseUrl = instanceProperties.getManagementBaseUrl();
    String actualManagementUrl = instanceProperties.getManagementUrl();
    Map<String, String> actualMetadata = instanceProperties.getMetadata();
    String actualName = instanceProperties.getName();
    String actualServiceBaseUrl = instanceProperties.getServiceBaseUrl();
    ServiceHostType actualServiceHostType = instanceProperties.getServiceHostType();
    String actualServicePath = instanceProperties.getServicePath();
    String actualServiceUrl = instanceProperties.getServiceUrl();

    // Assert
    assertEquals(
        "InstanceProperties(managementUrl=https://example.org/example, managementBaseUrl=https://example.org/example,"
            + " serviceUrl=https://example.org/example, serviceBaseUrl=https://example.org/example, servicePath=Service"
            + " Path, healthUrl=https://example.org/example, name=Name, preferIp=true, serviceHostType=IP,"
            + " metadata={})",
        actualToStringResult);
    assertEquals("Name", actualName);
    assertEquals("Service Path", actualServicePath);
    assertEquals("https://example.org/example", actualHealthUrl);
    assertEquals("https://example.org/example", actualManagementBaseUrl);
    assertEquals("https://example.org/example", actualManagementUrl);
    assertEquals("https://example.org/example", actualServiceBaseUrl);
    assertEquals("https://example.org/example", actualServiceUrl);
    assertEquals(ServiceHostType.IP, actualServiceHostType);
    assertTrue(instanceProperties.isPreferIp());
    assertTrue(actualMetadata.isEmpty());
    assertSame(metadata, actualMetadata);
  }

  /**
   * Test new {@link InstanceProperties} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link InstanceProperties}
   */
  @Test
  @DisplayName("Test new InstanceProperties (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceProperties.<init>()"})
  void testNewInstanceProperties() {
    // Arrange and Act
    InstanceProperties actualInstanceProperties = new InstanceProperties();

    // Assert
    assertEquals("spring-boot-application", actualInstanceProperties.getName());
    assertNull(actualInstanceProperties.getHealthUrl());
    assertNull(actualInstanceProperties.getManagementBaseUrl());
    assertNull(actualInstanceProperties.getManagementUrl());
    assertNull(actualInstanceProperties.getServiceBaseUrl());
    assertNull(actualInstanceProperties.getServicePath());
    assertNull(actualInstanceProperties.getServiceUrl());
    assertEquals(ServiceHostType.CANONICAL_HOST_NAME, actualInstanceProperties.getServiceHostType());
    assertFalse(actualInstanceProperties.isPreferIp());
    assertTrue(actualInstanceProperties.getMetadata().isEmpty());
  }
}
