package de.codecentric.boot.admin.server.cloud.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.cloud.discovery.DefaultServiceInstanceConverter;
import de.codecentric.boot.admin.server.cloud.discovery.EurekaServiceInstanceConverter;
import de.codecentric.boot.admin.server.cloud.discovery.InstanceDiscoveryListener;
import de.codecentric.boot.admin.server.cloud.discovery.KubernetesServiceInstanceConverter;
import de.codecentric.boot.admin.server.cloud.discovery.ServiceInstanceConverter;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;

class AdminServerDiscoveryAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link AdminServerDiscoveryAutoConfiguration.EurekaConverterConfiguration#serviceInstanceConverter()}
   */
  @Test
  void testEurekaConverterConfigurationServiceInstanceConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    EurekaServiceInstanceConverter actualServiceInstanceConverterResult = (new AdminServerDiscoveryAutoConfiguration.EurekaConverterConfiguration())
        .serviceInstanceConverter();

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }

  /**
   * Method under test:
   * {@link AdminServerDiscoveryAutoConfiguration#instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository)}
   */
  @Test
  void testInstanceDiscoveryListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerDiscoveryAutoConfiguration adminServerDiscoveryAutoConfiguration = new AdminServerDiscoveryAutoConfiguration();
    ServiceInstanceConverter serviceInstanceConverter = mock(ServiceInstanceConverter.class);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act
    InstanceDiscoveryListener actualInstanceDiscoveryListenerResult = adminServerDiscoveryAutoConfiguration
        .instanceDiscoveryListener(serviceInstanceConverter, discoveryClient, registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Assert
    Set<String> services = actualInstanceDiscoveryListenerResult.getServices();
    assertEquals(1, services.size());
    assertTrue(actualInstanceDiscoveryListenerResult.getIgnoredInstancesMetadata().isEmpty());
    assertTrue(actualInstanceDiscoveryListenerResult.getInstancesMetadata().isEmpty());
    assertTrue(services.contains("*"));
    assertTrue(actualInstanceDiscoveryListenerResult.getIgnoredServices().isEmpty());
  }

  /**
   * Method under test:
   * {@link AdminServerDiscoveryAutoConfiguration#instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository)}
   */
  @Test
  void testInstanceDiscoveryListener2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerDiscoveryAutoConfiguration adminServerDiscoveryAutoConfiguration = new AdminServerDiscoveryAutoConfiguration();
    DefaultServiceInstanceConverter serviceInstanceConverter = new DefaultServiceInstanceConverter();
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act
    InstanceDiscoveryListener actualInstanceDiscoveryListenerResult = adminServerDiscoveryAutoConfiguration
        .instanceDiscoveryListener(serviceInstanceConverter, discoveryClient, registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Assert
    Set<String> services = actualInstanceDiscoveryListenerResult.getServices();
    assertEquals(1, services.size());
    assertTrue(actualInstanceDiscoveryListenerResult.getIgnoredInstancesMetadata().isEmpty());
    assertTrue(actualInstanceDiscoveryListenerResult.getInstancesMetadata().isEmpty());
    assertTrue(services.contains("*"));
    assertTrue(actualInstanceDiscoveryListenerResult.getIgnoredServices().isEmpty());
  }

  /**
   * Method under test:
   * {@link AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  void testKubernetesConverterConfigurationServiceInstanceConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration kubernetesConverterConfiguration = new AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration();
    HashSet<String> namespaces = new HashSet<>();
    HashSet<Integer> knownSecurePorts = new HashSet<>();
    HashMap<String, String> serviceLabels = new HashMap<>();

    // Act
    KubernetesServiceInstanceConverter actualServiceInstanceConverterResult = kubernetesConverterConfiguration
        .serviceInstanceConverter(new KubernetesDiscoveryProperties(true, true, namespaces, true, 1L, true, "Filter",
            knownSecurePorts, serviceLabels, "Primary Port Name", new KubernetesDiscoveryProperties.Metadata(true,
                "Labels Prefix", true, "Annotations Prefix", true, "Ports Prefix"),
            1, true));

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }

  /**
   * Method under test:
   * {@link AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  void testKubernetesConverterConfigurationServiceInstanceConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration kubernetesConverterConfiguration = new AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration();
    HashSet<String> namespaces = new HashSet<>();
    HashSet<Integer> knownSecurePorts = new HashSet<>();

    // Act
    KubernetesServiceInstanceConverter actualServiceInstanceConverterResult = kubernetesConverterConfiguration
        .serviceInstanceConverter(new KubernetesDiscoveryProperties(true, true, namespaces, true, 1L, true, "Filter",
            knownSecurePorts, new HashMap<>(), "Primary Port Name", null, 1, true));

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }

  /**
   * Method under test:
   * {@link AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  void testKubernetesConverterConfigurationServiceInstanceConverter3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration kubernetesConverterConfiguration = new AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration();

    HashMap<String, String> serviceLabels = new HashMap<>();
    serviceLabels.computeIfPresent("/actuator", mock(BiFunction.class));
    HashSet<String> namespaces = new HashSet<>();
    HashSet<Integer> knownSecurePorts = new HashSet<>();

    // Act
    KubernetesServiceInstanceConverter actualServiceInstanceConverterResult = kubernetesConverterConfiguration
        .serviceInstanceConverter(new KubernetesDiscoveryProperties(true, true, namespaces, true, 1L, true, "Filter",
            knownSecurePorts, serviceLabels, "Primary Port Name", new KubernetesDiscoveryProperties.Metadata(true,
                "Labels Prefix", true, "Annotations Prefix", true, "Ports Prefix"),
            1, true));

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }

  /**
   * Method under test:
   * {@link AdminServerDiscoveryAutoConfiguration#serviceInstanceConverter()}
   */
  @Test
  void testServiceInstanceConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    DefaultServiceInstanceConverter actualServiceInstanceConverterResult = (new AdminServerDiscoveryAutoConfiguration())
        .serviceInstanceConverter();

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }
}
