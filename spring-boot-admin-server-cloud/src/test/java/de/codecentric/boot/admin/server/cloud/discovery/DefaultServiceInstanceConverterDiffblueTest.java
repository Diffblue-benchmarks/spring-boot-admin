package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultServiceInstanceConverter.class})
@ExtendWith(SpringExtension.class)
class DefaultServiceInstanceConverterDiffblueTest {
  @Autowired
  private DefaultServiceInstanceConverter defaultServiceInstanceConverter;

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getMetadataValue(ServiceInstance, String[])}
   */
  @Test
  void testGetMetadataValue() {
    // Arrange, Act and Assert
    assertNull(DefaultServiceInstanceConverter
        .getMetadataValue(new DefaultServiceInstance("42", "42", "localhost", 8080, true), "Keys"));
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#convert(ServiceInstance)}
   */
  @Test
  void testConvert() {
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
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getHealthUrl(ServiceInstance)}
   */
  @Test
  void testGetHealthUrl() {
    // Arrange, Act and Assert
    assertEquals("https://localhost:8080/actuator/health",
        defaultServiceInstanceConverter.getHealthUrl(new DefaultServiceInstance("42", "42", "localhost", 8080, true))
            .toString());
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getHealthPath(ServiceInstance)}
   */
  @Test
  void testGetHealthPath() {
    // Arrange, Act and Assert
    assertEquals("health",
        defaultServiceInstanceConverter.getHealthPath(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getHealthPath(ServiceInstance)}
   */
  @Test
  void testGetHealthPath2() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    // Act and Assert
    assertEquals("health",
        defaultServiceInstanceConverter.getHealthPath(
            new EurekaServiceInstance(new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
                securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
                "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
                InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP,
                leaseInfo, true, new HashMap<>(), 1L, 1L, InstanceInfo.ActionType.ADDED, "Asg Name"))));
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getManagementUrl(ServiceInstance)}
   */
  @Test
  void testGetManagementUrl() {
    // Arrange, Act and Assert
    assertEquals("https://localhost:8080/actuator",
        defaultServiceInstanceConverter
            .getManagementUrl(new DefaultServiceInstance("42", "42", "localhost", 8080, true))
            .toString());
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getManagementHost(ServiceInstance)}
   */
  @Test
  void testGetManagementHost() {
    // Arrange, Act and Assert
    assertEquals("localhost", defaultServiceInstanceConverter
        .getManagementHost(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  void testGetManagementPort() {
    // Arrange, Act and Assert
    assertEquals(8080, defaultServiceInstanceConverter
        .getManagementPort(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getManagementPath(ServiceInstance)}
   */
  @Test
  void testGetManagementPath() {
    // Arrange, Act and Assert
    assertEquals("/actuator", defaultServiceInstanceConverter
        .getManagementPath(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getManagementPath(ServiceInstance)}
   */
  @Test
  void testGetManagementPath2() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    // Act and Assert
    assertEquals("/actuator",
        defaultServiceInstanceConverter.getManagementPath(
            new EurekaServiceInstance(new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid", port,
                securePort, "https://example.org/example", "https://example.org/example", "https://example.org/example",
                "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo, "Host Name",
                InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP,
                leaseInfo, true, new HashMap<>(), 1L, 1L, InstanceInfo.ActionType.ADDED, "Asg Name"))));
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getServiceUrl(ServiceInstance)}
   */
  @Test
  void testGetServiceUrl() {
    // Arrange, Act and Assert
    assertEquals("https://localhost:8080",
        defaultServiceInstanceConverter.getServiceUrl(new DefaultServiceInstance("42", "42", "localhost", 8080, true))
            .toString());
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  void testGetMetadata() {
    // Arrange, Act and Assert
    assertTrue(
        defaultServiceInstanceConverter.getMetadata(new DefaultServiceInstance("42", "42", "localhost", 8080, true))
            .isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  void testGetMetadata2() {
    // Arrange
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);
    LeaseInfo leaseInfo = new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L);

    // Act and Assert
    assertTrue(defaultServiceInstanceConverter
        .getMetadata(new EurekaServiceInstance(new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid",
            port, securePort, "https://example.org/example", "https://example.org/example",
            "https://example.org/example", "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo,
            "Host Name", InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP,
            leaseInfo, true, new HashMap<>(), 1L, 1L, InstanceInfo.ActionType.ADDED, "Asg Name")))
        .isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  void testGetMetadata3() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", "foo");
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);

    // Act
    Map<String, String> actualMetadata = defaultServiceInstanceConverter
        .getMetadata(new EurekaServiceInstance(new InstanceInfo("42", "App Name", "App Group Name", "42 Main St", "Sid",
            port, securePort, "https://example.org/example", "https://example.org/example",
            "https://example.org/example", "https://example.org/example", "42 Main St", "42 Main St", 1, dataCenterInfo,
            "Host Name", InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP,
            new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L), true, metadata, 1L, 1L, InstanceInfo.ActionType.ADDED,
            "Asg Name")));

    // Assert
    assertEquals(1, actualMetadata.size());
    assertEquals("foo", actualMetadata.get("foo"));
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  void testGetMetadata4() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("42", "42");
    metadata.put("foo", "foo");
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);

    // Act and Assert
    assertEquals(metadata,
        defaultServiceInstanceConverter.getMetadata(new EurekaServiceInstance(new InstanceInfo("42", "App Name",
            "App Group Name", "42 Main St", "Sid", port, securePort, "https://example.org/example",
            "https://example.org/example", "https://example.org/example", "https://example.org/example", "42 Main St",
            "42 Main St", 1, dataCenterInfo, "Host Name", InstanceInfo.InstanceStatus.UP,
            InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L),
            true, metadata, 1L, 1L, InstanceInfo.ActionType.ADDED, "Asg Name"))));
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  void testGetMetadata5() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put(null, "foo");
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);

    // Act and Assert
    assertTrue(
        defaultServiceInstanceConverter
            .getMetadata(new EurekaServiceInstance(new InstanceInfo("42", "App Name", "App Group Name", "42 Main St",
                "Sid", port, securePort, "https://example.org/example", "https://example.org/example",
                "https://example.org/example", "https://example.org/example", "42 Main St", "42 Main St", 1,
                dataCenterInfo, "Host Name", InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP,
                InstanceInfo.InstanceStatus.UP, new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L), true, metadata, 1L, 1L,
                InstanceInfo.ActionType.ADDED, "Asg Name")))
            .isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultServiceInstanceConverter#getMetadata(ServiceInstance)}
   */
  @Test
  void testGetMetadata6() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", null);
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);

    // Act and Assert
    assertTrue(
        defaultServiceInstanceConverter
            .getMetadata(new EurekaServiceInstance(new InstanceInfo("42", "App Name", "App Group Name", "42 Main St",
                "Sid", port, securePort, "https://example.org/example", "https://example.org/example",
                "https://example.org/example", "https://example.org/example", "42 Main St", "42 Main St", 1,
                dataCenterInfo, "Host Name", InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP,
                InstanceInfo.InstanceStatus.UP, new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L), true, metadata, 1L, 1L,
                InstanceInfo.ActionType.ADDED, "Asg Name")))
            .isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link DefaultServiceInstanceConverter}
   *   <li>{@link DefaultServiceInstanceConverter#setHealthEndpointPath(String)}
   *   <li>{@link DefaultServiceInstanceConverter#setManagementContextPath(String)}
   *   <li>{@link DefaultServiceInstanceConverter#getHealthEndpointPath()}
   *   <li>{@link DefaultServiceInstanceConverter#getManagementContextPath()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    DefaultServiceInstanceConverter actualDefaultServiceInstanceConverter = new DefaultServiceInstanceConverter();
    actualDefaultServiceInstanceConverter.setHealthEndpointPath("https://config.us-east-2.amazonaws.com");
    actualDefaultServiceInstanceConverter.setManagementContextPath("Management Context Path");
    String actualHealthEndpointPath = actualDefaultServiceInstanceConverter.getHealthEndpointPath();

    // Assert that nothing has changed
    assertEquals("Management Context Path", actualDefaultServiceInstanceConverter.getManagementContextPath());
    assertEquals("https://config.us-east-2.amazonaws.com", actualHealthEndpointPath);
  }
}
