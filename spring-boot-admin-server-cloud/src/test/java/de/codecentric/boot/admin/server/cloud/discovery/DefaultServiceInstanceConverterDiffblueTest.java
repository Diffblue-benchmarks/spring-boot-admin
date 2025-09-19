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
   * Test {@link DefaultServiceInstanceConverter#getMetadataValue(ServiceInstance, String[])}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadataValue(ServiceInstance,
   * String[])}
   */
  @Test
  @DisplayName("Test getMetadataValue(ServiceInstance, String[]); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String DefaultServiceInstanceConverter.getMetadataValue(ServiceInstance, String[])"
  })
  void testGetMetadataValue_thenReturnNull() {
    // Arrange
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertNull(DefaultServiceInstanceConverter.getMetadataValue(instance, "Keys"));
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
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code foo}.
   *   <li>Then return Metadata size is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test convert(ServiceInstance); given 'foo'; when HashMap() 'foo' is 'foo'; then return Metadata size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_givenFoo_whenHashMapFooIsFoo_thenReturnMetadataSizeIsOne() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", "foo");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Registration actualConvertResult = defaultServiceInstanceConverter.convert(instance);

    // Assert
    assertEquals("42", actualConvertResult.getName());
    Map<String, String> metadata2 = actualConvertResult.getMetadata();
    assertEquals(1, metadata2.size());
    assertEquals("foo", metadata2.get("foo"));
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
    metadata.put("localhost", "localhost");
    metadata.put("foo", "foo");
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
   *   <li>Then return Metadata size is two.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test convert(ServiceInstance); given MANAGEMENT_PORT_NAME; then return Metadata size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_givenManagement_port_name_thenReturnMetadataSizeIsTwo() {
    // Arrange
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);

    HashMap<String, String> metadata = new HashMap<>();
    metadata.put(KubernetesServiceInstanceConverter.MANAGEMENT_PORT_NAME, "42");
    metadata.put(
        "Converting service '{}' running at '{}' with metadata {}",
        "Converting service '{}' running at '{}' with metadata {}");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Registration actualConvertResult = kubernetesServiceInstanceConverter.convert(instance);

    // Assert
    Map<String, String> metadata2 = actualConvertResult.getMetadata();
    assertEquals(2, metadata2.size());
    assertEquals("42", metadata2.get(KubernetesServiceInstanceConverter.MANAGEMENT_PORT_NAME));
    assertEquals("https://localhost:42/actuator", actualConvertResult.getManagementUrl());
    assertEquals("https://localhost:42/actuator/health", actualConvertResult.getHealthUrl());
    assertTrue(metadata2.containsKey("Converting service '{}' running at '{}' with metadata {}"));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName("Test convert(ServiceInstance); given 'null'; when HashMap() 'foo' is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_givenNull_whenHashMapFooIsNull() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", null);
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
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName("Test convert(ServiceInstance); given 'null'; when HashMap() 'null' is 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_givenNull_whenHashMapNullIsFoo() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put(null, "foo");
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
    // Arrange
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);

    // Act and Assert
    assertEquals(
        "http://null:80/actuator/health",
        kubernetesServiceInstanceConverter.getHealthUrl(new DefaultServiceInstance()).toString());
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
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals(
        "https://localhost:8080/actuator/health",
        defaultServiceInstanceConverter.getHealthUrl(instance).toString());
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
    // Arrange
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals("health", defaultServiceInstanceConverter.getHealthPath(instance));
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
    // Arrange
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);

    // Act and Assert
    assertEquals(
        "http://null:80/actuator",
        kubernetesServiceInstanceConverter
            .getManagementUrl(new DefaultServiceInstance())
            .toString());
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
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals(
        "https://localhost:8080/actuator",
        defaultServiceInstanceConverter.getManagementUrl(instance).toString());
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
    // Arrange
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals("localhost", defaultServiceInstanceConverter.getManagementHost(instance));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@link DefaultServiceInstanceConverter}.
   *   <li>Then return {@code 8080}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getManagementPort(ServiceInstance); given DefaultServiceInstanceConverter; then return '8080'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultServiceInstanceConverter.getManagementPort(ServiceInstance)"})
  void testGetManagementPort_givenDefaultServiceInstanceConverter_thenReturn8080() {
    // Arrange
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals(8080, defaultServiceInstanceConverter.getManagementPort(instance));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}.
   *
   * <ul>
   *   <li>Then return eighty.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementPort(ServiceInstance); then return eighty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultServiceInstanceConverter.getManagementPort(ServiceInstance)"})
  void testGetManagementPort_thenReturnEighty() {
    // Arrange
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);

    // Act and Assert
    assertEquals(
        80, kubernetesServiceInstanceConverter.getManagementPort(new DefaultServiceInstance()));
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
    // Arrange
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals("/actuator", defaultServiceInstanceConverter.getManagementPath(instance));
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
    // Arrange
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals(
        "https://localhost:8080",
        defaultServiceInstanceConverter.getServiceUrl(instance).toString());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getMetadata(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata() {
    // Arrange
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertTrue(defaultServiceInstanceConverter.getMetadata(instance).isEmpty());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.
   *   <li>Then return {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getMetadata(ServiceInstance); given '42'; when HashMap() '42' is '42'; then return HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata_given42_whenHashMap42Is42_thenReturnHashMap() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("42", "42");
    metadata.put("foo", "foo");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act and Assert
    assertEquals(metadata, defaultServiceInstanceConverter.getMetadata(instance));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code foo}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getMetadata(ServiceInstance); given 'foo'; when HashMap() 'foo' is 'foo'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata_givenFoo_whenHashMapFooIsFoo_thenReturnSizeIsOne() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", "foo");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Map<String, String> actualMetadata = defaultServiceInstanceConverter.getMetadata(instance);

    // Assert
    assertEquals(1, actualMetadata.size());
    assertEquals("foo", actualMetadata.get("foo"));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getMetadata(ServiceInstance); given 'null'; when HashMap() 'foo' is 'null'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata_givenNull_whenHashMapFooIsNull_thenReturnEmpty() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", null);
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
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code foo}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test getMetadata(ServiceInstance); given 'null'; when HashMap() 'null' is 'foo'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata_givenNull_whenHashMapNullIsFoo_thenReturnEmpty() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put(null, "foo");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act and Assert
    assertTrue(defaultServiceInstanceConverter.getMetadata(instance).isEmpty());
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
