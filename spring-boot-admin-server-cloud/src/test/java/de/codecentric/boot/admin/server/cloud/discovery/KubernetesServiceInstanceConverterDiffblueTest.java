package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {KubernetesServiceInstanceConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class KubernetesServiceInstanceConverterDiffblueTest {
  @MockBean
  private KubernetesDiscoveryProperties kubernetesDiscoveryProperties;

  @Autowired
  private KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter;

  /**
   * Method under test:
   * {@link KubernetesServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  void testGetManagementPort() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<String> namespaces = new HashSet<>();
    HashSet<Integer> knownSecurePorts = new HashSet<>();
    HashMap<String, String> serviceLabels = new HashMap<>();
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter = new KubernetesServiceInstanceConverter(
        new KubernetesDiscoveryProperties(true, true, namespaces, true, 1L, true, "Filter", knownSecurePorts,
            serviceLabels, "Primary Port Name", new KubernetesDiscoveryProperties.Metadata(true, "Labels Prefix", true,
                "Annotations Prefix", true, "Ports Prefix"),
            1, true));

    // Act and Assert
    assertEquals(8080, kubernetesServiceInstanceConverter
        .getManagementPort(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Method under test:
   * {@link KubernetesServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  void testGetManagementPort2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, String> serviceLabels = new HashMap<>();
    serviceLabels.computeIfPresent(KubernetesServiceInstanceConverter.MANAGEMENT_PORT_NAME, mock(BiFunction.class));
    HashSet<String> namespaces = new HashSet<>();
    HashSet<Integer> knownSecurePorts = new HashSet<>();
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter = new KubernetesServiceInstanceConverter(
        new KubernetesDiscoveryProperties(true, true, namespaces, true, 1L, true, "Filter", knownSecurePorts,
            serviceLabels, "Primary Port Name", new KubernetesDiscoveryProperties.Metadata(true, "Labels Prefix", true,
                "Annotations Prefix", true, "Ports Prefix"),
            1, true));

    // Act and Assert
    assertEquals(8080, kubernetesServiceInstanceConverter
        .getManagementPort(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Method under test:
   * {@link KubernetesServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  void testGetManagementPort3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<String> namespaces = new HashSet<>();
    HashSet<Integer> knownSecurePorts = new HashSet<>();
    HashMap<String, String> serviceLabels = new HashMap<>();
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter = new KubernetesServiceInstanceConverter(
        new KubernetesDiscoveryProperties(false, true, namespaces, true, 1L, true, "Filter", knownSecurePorts,
            serviceLabels, "Primary Port Name", new KubernetesDiscoveryProperties.Metadata(true, "Labels Prefix", true,
                "Annotations Prefix", true, "Ports Prefix"),
            1, true));

    HashMap<String, String> metadata = new HashMap<>();
    metadata.put(KubernetesServiceInstanceConverter.MANAGEMENT_PORT_NAME, "42");
    InstanceInfo.PortWrapper port = new InstanceInfo.PortWrapper(true, 8080);

    InstanceInfo.PortWrapper securePort = new InstanceInfo.PortWrapper(true, 8080);

    DataCenterInfo dataCenterInfo = mock(DataCenterInfo.class);

    // Act and Assert
    assertEquals(42,
        kubernetesServiceInstanceConverter.getManagementPort(new EurekaServiceInstance(new InstanceInfo("42",
            "App Name", "App Group Name", "42 Main St", "Sid", port, securePort, "https://example.org/example",
            "https://example.org/example", "https://example.org/example", "https://example.org/example", "42 Main St",
            "42 Main St", 1, dataCenterInfo, "Host Name", InstanceInfo.InstanceStatus.UP,
            InstanceInfo.InstanceStatus.UP, InstanceInfo.InstanceStatus.UP, new LeaseInfo(42, 1, 1L, 1L, 1L, 1L, 1L),
            true, metadata, 1L, 1L, InstanceInfo.ActionType.ADDED, "Asg Name"))));
  }

  /**
   * Method under test:
   * {@link KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  void testNewKubernetesServiceInstanceConverter() {
    // Arrange
    when(kubernetesDiscoveryProperties.metadata()).thenReturn(new KubernetesDiscoveryProperties.Metadata(true,
        "Labels Prefix", true, "Annotations Prefix", true, "Ports Prefix"));

    // Act
    KubernetesServiceInstanceConverter actualKubernetesServiceInstanceConverter = new KubernetesServiceInstanceConverter(
        kubernetesDiscoveryProperties);

    // Assert
    verify(kubernetesDiscoveryProperties, atLeast(1)).metadata();
    assertEquals("/actuator", actualKubernetesServiceInstanceConverter.getManagementContextPath());
    assertEquals("health", actualKubernetesServiceInstanceConverter.getHealthEndpointPath());
  }

  /**
   * Method under test:
   * {@link KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  void testNewKubernetesServiceInstanceConverter2() {
    // Arrange
    when(kubernetesDiscoveryProperties.metadata()).thenReturn(
        new KubernetesDiscoveryProperties.Metadata(true, "Labels Prefix", true, "Annotations Prefix", true, null));

    // Act
    KubernetesServiceInstanceConverter actualKubernetesServiceInstanceConverter = new KubernetesServiceInstanceConverter(
        kubernetesDiscoveryProperties);

    // Assert
    verify(kubernetesDiscoveryProperties, atLeast(1)).metadata();
    assertEquals("/actuator", actualKubernetesServiceInstanceConverter.getManagementContextPath());
    assertEquals("health", actualKubernetesServiceInstanceConverter.getHealthEndpointPath());
  }

  /**
   * Method under test:
   * {@link KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  void testNewKubernetesServiceInstanceConverter3() {
    // Arrange
    when(kubernetesDiscoveryProperties.metadata()).thenReturn(null);

    // Act
    KubernetesServiceInstanceConverter actualKubernetesServiceInstanceConverter = new KubernetesServiceInstanceConverter(
        kubernetesDiscoveryProperties);

    // Assert
    verify(kubernetesDiscoveryProperties).metadata();
    assertEquals("/actuator", actualKubernetesServiceInstanceConverter.getManagementContextPath());
    assertEquals("health", actualKubernetesServiceInstanceConverter.getHealthEndpointPath());
  }
}
