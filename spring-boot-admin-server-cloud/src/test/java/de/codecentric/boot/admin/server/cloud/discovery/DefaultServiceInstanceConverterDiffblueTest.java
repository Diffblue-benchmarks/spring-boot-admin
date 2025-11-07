package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Registration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultServiceInstanceConverter.class})
@ExtendWith(SpringExtension.class)
class DefaultServiceInstanceConverterDiffblueTest {
  @Autowired
  private DefaultServiceInstanceConverter defaultServiceInstanceConverter;

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadataValue(ServiceInstance, String[])}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#getMetadataValue(ServiceInstance, String[])}
   */
  @Test
  @DisplayName("Test getMetadataValue(ServiceInstance, String[]); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultServiceInstanceConverter.getMetadataValue(ServiceInstance, String[])"})
  void testGetMetadataValue_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DefaultServiceInstanceConverter
        .getMetadataValue(new DefaultServiceInstance("42", "42", "localhost", 8080, true), "Keys"));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}.
   * <ul>
   *   <li>Then return Name is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  @DisplayName("Test convert(ServiceInstance); then return Name is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Registration DefaultServiceInstanceConverter.convert(ServiceInstance)"})
  void testConvert_thenReturnNameIs42() {
    // Arrange and Act
    Registration actualConvertResult = defaultServiceInstanceConverter
        .convert(new DefaultServiceInstance("42", "42", "localhost", 8080, true));

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
   * <ul>
   *   <li>Then return toString is {@code https://localhost:8080/actuator/health}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#getHealthUrl(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getHealthUrl(ServiceInstance); then return toString is 'https://localhost:8080/actuator/health'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.net.URI DefaultServiceInstanceConverter.getHealthUrl(ServiceInstance)"})
  void testGetHealthUrl_thenReturnToStringIsHttpsLocalhost8080ActuatorHealth() {
    // Arrange, Act and Assert
    assertEquals("https://localhost:8080/actuator/health",
        defaultServiceInstanceConverter.getHealthUrl(new DefaultServiceInstance("42", "42", "localhost", 8080, true))
            .toString());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getHealthPath(ServiceInstance)}.
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#getHealthPath(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getHealthPath(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultServiceInstanceConverter.getHealthPath(ServiceInstance)"})
  void testGetHealthPath() {
    // Arrange, Act and Assert
    assertEquals("health",
        defaultServiceInstanceConverter.getHealthPath(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementUrl(ServiceInstance)}.
   * <ul>
   *   <li>Then return toString is {@code https://localhost:8080/actuator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#getManagementUrl(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementUrl(ServiceInstance); then return toString is 'https://localhost:8080/actuator'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.net.URI DefaultServiceInstanceConverter.getManagementUrl(ServiceInstance)"})
  void testGetManagementUrl_thenReturnToStringIsHttpsLocalhost8080Actuator() {
    // Arrange, Act and Assert
    assertEquals("https://localhost:8080/actuator",
        defaultServiceInstanceConverter
            .getManagementUrl(new DefaultServiceInstance("42", "42", "localhost", 8080, true))
            .toString());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementHost(ServiceInstance)}.
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#getManagementHost(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementHost(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultServiceInstanceConverter.getManagementHost(ServiceInstance)"})
  void testGetManagementHost() {
    // Arrange, Act and Assert
    assertEquals("localhost", defaultServiceInstanceConverter
        .getManagementHost(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}.
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementPort(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"int DefaultServiceInstanceConverter.getManagementPort(ServiceInstance)"})
  void testGetManagementPort() {
    // Arrange, Act and Assert
    assertEquals(8080, defaultServiceInstanceConverter
        .getManagementPort(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getManagementPath(ServiceInstance)}.
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#getManagementPath(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementPath(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultServiceInstanceConverter.getManagementPath(ServiceInstance)"})
  void testGetManagementPath() {
    // Arrange, Act and Assert
    assertEquals("/actuator", defaultServiceInstanceConverter
        .getManagementPath(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getServiceUrl(ServiceInstance)}.
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#getServiceUrl(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getServiceUrl(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.net.URI DefaultServiceInstanceConverter.getServiceUrl(ServiceInstance)"})
  void testGetServiceUrl() {
    // Arrange, Act and Assert
    assertEquals("https://localhost:8080",
        defaultServiceInstanceConverter.getServiceUrl(new DefaultServiceInstance("42", "42", "localhost", 8080, true))
            .toString());
  }

  /**
   * Test {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}.
   * <p>
   * Method under test: {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getMetadata(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Map DefaultServiceInstanceConverter.getMetadata(ServiceInstance)"})
  void testGetMetadata() {
    // Arrange, Act and Assert
    assertTrue(
        defaultServiceInstanceConverter.getMetadata(new DefaultServiceInstance("42", "42", "localhost", 8080, true))
            .isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultServiceInstanceConverter.<init>()",
      "String DefaultServiceInstanceConverter.getHealthEndpointPath()",
      "String DefaultServiceInstanceConverter.getManagementContextPath()",
      "void DefaultServiceInstanceConverter.setHealthEndpointPath(String)",
      "void DefaultServiceInstanceConverter.setManagementContextPath(String)"})
  void testGettersAndSetters() {
    // Arrange and Act
    DefaultServiceInstanceConverter actualDefaultServiceInstanceConverter = new DefaultServiceInstanceConverter();
    actualDefaultServiceInstanceConverter.setHealthEndpointPath("https://config.us-east-2.amazonaws.com");
    actualDefaultServiceInstanceConverter.setManagementContextPath("Management Context Path");
    String actualHealthEndpointPath = actualDefaultServiceInstanceConverter.getHealthEndpointPath();

    // Assert
    assertEquals("Management Context Path", actualDefaultServiceInstanceConverter.getManagementContextPath());
    assertEquals("https://config.us-east-2.amazonaws.com", actualHealthEndpointPath);
  }
}
