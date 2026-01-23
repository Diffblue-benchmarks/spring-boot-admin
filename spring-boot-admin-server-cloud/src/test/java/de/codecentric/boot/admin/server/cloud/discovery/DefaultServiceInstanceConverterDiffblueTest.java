package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultServiceInstanceConverter.class})
@ExtendWith(SpringExtension.class)
class DefaultServiceInstanceConverterDiffblueTest {
  @Autowired private DefaultServiceInstanceConverter defaultServiceInstanceConverter;

  /**
   * Test {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}.
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName("Test convert(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert() {
    // Arrange
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Registration actualConvertResult = defaultServiceInstanceConverter.convert(instance);

    // Assert
    assertEquals("42", actualConvertResult.getName());
    assertEquals("https://localhost:8080", actualConvertResult.getServiceUrl());
    assertEquals("https://localhost:8080/actuator", actualConvertResult.getManagementUrl());
    assertEquals("https://localhost:8080/actuator/health", actualConvertResult.getHealthUrl());
    assertNull(actualConvertResult.getSource());
    assertTrue(actualConvertResult.getMetadata().isEmpty());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}.
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName("Test convert(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert2() {
    // Arrange
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, new HashMap<>());

    // Act
    Registration actualConvertResult = kubernetesServiceInstanceConverter.convert(instance);

    // Assert
    assertEquals("42", actualConvertResult.getName());
    assertEquals("https://localhost:8080", actualConvertResult.getServiceUrl());
    assertNull(actualConvertResult.getSource());
    assertTrue(actualConvertResult.getMetadata().isEmpty());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code 42}.
   *   <li>Then return Metadata {@code Key} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test convert(ServiceInstance); given 'Key'; when HashMap() 'Key' is '42'; then return Metadata 'Key' is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_givenKey_whenHashMapKeyIs42_thenReturnMetadataKeyIs42() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("Key", "42");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Registration actualConvertResult = defaultServiceInstanceConverter.convert(instance);

    // Assert
    assertEquals("42", actualConvertResult.getName());
    Map<String, String> metadata2 = actualConvertResult.getMetadata();
    assertEquals(1, metadata2.size());
    assertEquals("42", metadata2.get("Key"));
    assertEquals("https://localhost:8080", actualConvertResult.getServiceUrl());
    assertEquals("https://localhost:8080/actuator", actualConvertResult.getManagementUrl());
    assertEquals("https://localhost:8080/actuator/health", actualConvertResult.getHealthUrl());
    assertNull(actualConvertResult.getSource());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code localhost}.
   *   <li>Then return Metadata is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test convert(ServiceInstance); given 'localhost'; then return Metadata is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_givenLocalhost_thenReturnMetadataIsHashMap() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("localhost", "Converting service '{}' running at '{}' with metadata {}");
    metadata.put("Key", "42");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act and Assert
    assertEquals(metadata, defaultServiceInstanceConverter.convert(instance).getMetadata());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@link KubernetesServiceInstanceConverter#MANAGEMENT_PORT_NAME}.
   *   <li>Then return Metadata {@link KubernetesServiceInstanceConverter#MANAGEMENT_PORT_NAME} is
   *       {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test convert(ServiceInstance); given MANAGEMENT_PORT_NAME; then return Metadata MANAGEMENT_PORT_NAME is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_givenManagement_port_name_thenReturnMetadataManagement_port_nameIs42() {
    // Arrange
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);

    HashMap<String, String> metadata = new HashMap<>();
    metadata.put(KubernetesServiceInstanceConverter.MANAGEMENT_PORT_NAME, "42");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Registration actualConvertResult = kubernetesServiceInstanceConverter.convert(instance);

    // Assert
    Map<String, String> metadata2 = actualConvertResult.getMetadata();
    assertEquals(1, metadata2.size());
    assertEquals("42", metadata2.get(KubernetesServiceInstanceConverter.MANAGEMENT_PORT_NAME));
    assertEquals("https://localhost:42/actuator", actualConvertResult.getManagementUrl());
    assertEquals("https://localhost:42/actuator/health", actualConvertResult.getHealthUrl());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName("Test convert(ServiceInstance); given 'null'; when HashMap() 'Key' is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_givenNull_whenHashMapKeyIsNull() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("Key", null);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Registration actualConvertResult = defaultServiceInstanceConverter.convert(instance);

    // Assert
    assertEquals("42", actualConvertResult.getName());
    assertEquals("https://localhost:8080", actualConvertResult.getServiceUrl());
    assertEquals("https://localhost:8080/actuator", actualConvertResult.getManagementUrl());
    assertEquals("https://localhost:8080/actuator/health", actualConvertResult.getHealthUrl());
    assertNull(actualConvertResult.getSource());
    assertTrue(actualConvertResult.getMetadata().isEmpty());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName("Test convert(ServiceInstance); given 'null'; when HashMap() 'null' is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_givenNull_whenHashMapNullIs42() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put(null, "42");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Registration actualConvertResult = defaultServiceInstanceConverter.convert(instance);

    // Assert
    assertEquals("42", actualConvertResult.getName());
    assertEquals("https://localhost:8080", actualConvertResult.getServiceUrl());
    assertEquals("https://localhost:8080/actuator", actualConvertResult.getManagementUrl());
    assertEquals("https://localhost:8080/actuator/health", actualConvertResult.getHealthUrl());
    assertNull(actualConvertResult.getSource());
    assertTrue(actualConvertResult.getMetadata().isEmpty());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getHealthUrl(ServiceInstance)}.
   *
   * <ul>
   *   <li>Then return toString is {@code http://null:80/actuator/health}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getHealthUrl(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getHealthUrl(ServiceInstance); then return toString is 'http://null:80/actuator/health'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.net.URI DefaultServiceInstanceConverter.getHealthUrl(ServiceInstance)"})
  void testGetHealthUrl_thenReturnToStringIsHttpNull80ActuatorHealth() {
    // Arrange, Act and Assert
    assertEquals(
        "http://null:80/actuator/health",
        defaultServiceInstanceConverter.getHealthUrl(new DefaultServiceInstance()).toString());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getHealthUrl(ServiceInstance)}.
   *
   * <ul>
   *   <li>Then return toString is {@code https://localhost:8080/actuator/health}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getHealthUrl(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getHealthUrl(ServiceInstance); then return toString is 'https://localhost:8080/actuator/health'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.net.URI DefaultServiceInstanceConverter.getHealthUrl(ServiceInstance)"})
  void testGetHealthUrl_thenReturnToStringIsHttpsLocalhost8080ActuatorHealth() {
    // Arrange
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals(
        "https://localhost:8080/actuator/health",
        kubernetesServiceInstanceConverter.getHealthUrl(instance).toString());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getHealthPath(ServiceInstance)}.
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getHealthPath(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getHealthPath(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DefaultServiceInstanceConverter.getHealthPath(ServiceInstance)"})
  void testGetHealthPath() {
    // Arrange, Act and Assert
    assertEquals(
        "health", defaultServiceInstanceConverter.getHealthPath(new DefaultServiceInstance()));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementUrl(ServiceInstance)}.
   *
   * <ul>
   *   <li>Then return toString is {@code http://null:80/actuator}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getManagementUrl(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getManagementUrl(ServiceInstance); then return toString is 'http://null:80/actuator'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.net.URI DefaultServiceInstanceConverter.getManagementUrl(ServiceInstance)"
  })
  void testGetManagementUrl_thenReturnToStringIsHttpNull80Actuator() {
    // Arrange, Act and Assert
    assertEquals(
        "http://null:80/actuator",
        defaultServiceInstanceConverter.getManagementUrl(new DefaultServiceInstance()).toString());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementUrl(ServiceInstance)}.
   *
   * <ul>
   *   <li>Then return toString is {@code https://localhost:8080/actuator}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getManagementUrl(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getManagementUrl(ServiceInstance); then return toString is 'https://localhost:8080/actuator'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.net.URI DefaultServiceInstanceConverter.getManagementUrl(ServiceInstance)"
  })
  void testGetManagementUrl_thenReturnToStringIsHttpsLocalhost8080Actuator() {
    // Arrange
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals(
        "https://localhost:8080/actuator",
        kubernetesServiceInstanceConverter.getManagementUrl(instance).toString());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementHost(ServiceInstance)}.
   *
   * <p>Method under test: {@link
   * DefaultServiceInstanceConverter#getManagementHost(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementHost(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DefaultServiceInstanceConverter.getManagementHost(ServiceInstance)"})
  void testGetManagementHost() {
    // Arrange, Act and Assert
    assertEquals(
        "null", defaultServiceInstanceConverter.getManagementHost(new DefaultServiceInstance()));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@link DefaultServiceInstanceConverter}.
   *   <li>Then return eighty.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getManagementPort(ServiceInstance); given DefaultServiceInstanceConverter; then return eighty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultServiceInstanceConverter.getManagementPort(ServiceInstance)"})
  void testGetManagementPort_givenDefaultServiceInstanceConverter_thenReturnEighty() {
    // Arrange, Act and Assert
    assertEquals(
        80, defaultServiceInstanceConverter.getManagementPort(new DefaultServiceInstance()));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}.
   *
   * <ul>
   *   <li>Then return {@code 8080}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementPort(ServiceInstance); then return '8080'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultServiceInstanceConverter.getManagementPort(ServiceInstance)"})
  void testGetManagementPort_thenReturn8080() {
    // Arrange
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    int actualManagementPort = kubernetesServiceInstanceConverter.getManagementPort(instance);

    // Assert
    assertEquals(8080, actualManagementPort);
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementPath(ServiceInstance)}.
   *
   * <p>Method under test: {@link
   * DefaultServiceInstanceConverter#getManagementPath(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementPath(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DefaultServiceInstanceConverter.getManagementPath(ServiceInstance)"})
  void testGetManagementPath() {
    // Arrange, Act and Assert
    assertEquals(
        "/actuator",
        defaultServiceInstanceConverter.getManagementPath(new DefaultServiceInstance()));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getServiceUrl(ServiceInstance)}.
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getServiceUrl(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getServiceUrl(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.net.URI DefaultServiceInstanceConverter.getServiceUrl(ServiceInstance)"})
  void testGetServiceUrl() {
    // Arrange, Act and Assert
    assertEquals(
        "http://null:80",
        defaultServiceInstanceConverter.getServiceUrl(new DefaultServiceInstance()).toString());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code 42}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getMetadata(ServiceInstance); given 'Key'; when HashMap() 'Key' is '42'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata_givenKey_whenHashMapKeyIs42_thenReturnSizeIsOne() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("Key", "42");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Map<String, String> actualMetadata = defaultServiceInstanceConverter.getMetadata(instance);

    // Assert
    assertEquals(1, actualMetadata.size());
    assertEquals("42", actualMetadata.get("Key"));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getMetadata(ServiceInstance); given 'null'; when HashMap() 'Key' is 'null'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata_givenNull_whenHashMapKeyIsNull_thenReturnEmpty() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("Key", null);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act and Assert
    assertTrue(defaultServiceInstanceConverter.getMetadata(instance).isEmpty());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code 42}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getMetadata(ServiceInstance); given 'null'; when HashMap() 'null' is '42'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata_givenNull_whenHashMapNullIs42_thenReturnEmpty() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put(null, "42");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act and Assert
    assertTrue(defaultServiceInstanceConverter.getMetadata(instance).isEmpty());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code Value}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code Value}.
   *   <li>Then return {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getMetadata(ServiceInstance); given 'Value'; when HashMap() '42' is 'Value'; then return HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata_givenValue_whenHashMap42IsValue_thenReturnHashMap() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("42", "Value");
    metadata.put("Key", "42");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act and Assert
    assertEquals(metadata, defaultServiceInstanceConverter.getMetadata(instance));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>When {@link DefaultServiceInstance#DefaultServiceInstance()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getMetadata(ServiceInstance); when DefaultServiceInstance(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata_whenDefaultServiceInstance_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(defaultServiceInstanceConverter.getMetadata(new DefaultServiceInstance()).isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link DefaultServiceInstanceConverter}
   *   <li>{@link DefaultServiceInstanceConverter#setHealthEndpointPath(String)}
   *   <li>{@link DefaultServiceInstanceConverter#setManagementContextPath(String)}
   *   <li>{@link DefaultServiceInstanceConverter#getHealthEndpointPath()}
   *   <li>{@link DefaultServiceInstanceConverter#getManagementContextPath()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultServiceInstanceConverter.<init>()",
    "String DefaultServiceInstanceConverter.getHealthEndpointPath()",
    "String DefaultServiceInstanceConverter.getManagementContextPath()",
    "void DefaultServiceInstanceConverter.setHealthEndpointPath(String)",
    "void DefaultServiceInstanceConverter.setManagementContextPath(String)"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    DefaultServiceInstanceConverter actualDefaultServiceInstanceConverter =
        new DefaultServiceInstanceConverter();
    actualDefaultServiceInstanceConverter.setHealthEndpointPath(
        "https://config.us-east-2.amazonaws.com");
    actualDefaultServiceInstanceConverter.setManagementContextPath("Management Context Path");
    String actualHealthEndpointPath = actualDefaultServiceInstanceConverter.getHealthEndpointPath();

    // Assert
    assertEquals(
        "Management Context Path",
        actualDefaultServiceInstanceConverter.getManagementContextPath());
    assertEquals("https://config.us-east-2.amazonaws.com", actualHealthEndpointPath);
  }
}
