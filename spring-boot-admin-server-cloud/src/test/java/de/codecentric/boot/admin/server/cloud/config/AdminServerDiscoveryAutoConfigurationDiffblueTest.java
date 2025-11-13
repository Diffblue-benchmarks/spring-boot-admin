package de.codecentric.boot.admin.server.cloud.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.cloud.config.AdminServerDiscoveryAutoConfiguration.EurekaConverterConfiguration;
import de.codecentric.boot.admin.server.cloud.config.AdminServerDiscoveryAutoConfiguration.KubernetesConverterConfiguration;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;

class AdminServerDiscoveryAutoConfigurationDiffblueTest {
  /**
   * Test EurekaConverterConfiguration {@link
   * EurekaConverterConfiguration#serviceInstanceConverter()}.
   *
   * <p>Method under test: {@link EurekaConverterConfiguration#serviceInstanceConverter()}
   */
  @Test
  @DisplayName("Test EurekaConverterConfiguration serviceInstanceConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "EurekaServiceInstanceConverter EurekaConverterConfiguration.serviceInstanceConverter()"
  })
  void testEurekaConverterConfigurationServiceInstanceConverter() {
    // Arrange and Act
    EurekaServiceInstanceConverter actualServiceInstanceConverterResult =
        new EurekaConverterConfiguration().serviceInstanceConverter();

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }

  /**
   * Test {@link
   * AdminServerDiscoveryAutoConfiguration#instanceDiscoveryListener(ServiceInstanceConverter,
   * DiscoveryClient, InstanceRegistry, InstanceRepository)}.
   *
   * <ul>
   *   <li>Then return Services size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * AdminServerDiscoveryAutoConfiguration#instanceDiscoveryListener(ServiceInstanceConverter,
   * DiscoveryClient, InstanceRegistry, InstanceRepository)}
   */
  @Test
  @DisplayName(
      "Test instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository); then return Services size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceDiscoveryListener AdminServerDiscoveryAutoConfiguration.instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository)"
  })
  void testInstanceDiscoveryListener_thenReturnServicesSizeIsOne() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AdminServerDiscoveryAutoConfiguration adminServerDiscoveryAutoConfiguration =
        new AdminServerDiscoveryAutoConfiguration();
    DefaultServiceInstanceConverter serviceInstanceConverter =
        new DefaultServiceInstanceConverter();
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act
    InstanceDiscoveryListener actualInstanceDiscoveryListenerResult =
        adminServerDiscoveryAutoConfiguration.instanceDiscoveryListener(
            serviceInstanceConverter,
            discoveryClient,
            registry,
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
   * Test KubernetesConverterConfiguration {@link
   * KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}.
   *
   * <p>Method under test: {@link
   * KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  @DisplayName(
      "Test KubernetesConverterConfiguration serviceInstanceConverter(KubernetesDiscoveryProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "KubernetesServiceInstanceConverter KubernetesConverterConfiguration.serviceInstanceConverter(KubernetesDiscoveryProperties)"
  })
  void testKubernetesConverterConfigurationServiceInstanceConverter() {
    // Arrange
    KubernetesConverterConfiguration kubernetesConverterConfiguration =
        new KubernetesConverterConfiguration();
    HashSet<String> namespaces = new HashSet<>();
    HashSet<Integer> knownSecurePorts = new HashSet<>();

    KubernetesDiscoveryProperties discoveryProperties =
        new KubernetesDiscoveryProperties(
            true,
            true,
            namespaces,
            true,
            1L,
            true,
            "/actuator",
            knownSecurePorts,
            new HashMap<>(),
            "/actuator",
            null,
            1,
            true);

    // Act
    KubernetesServiceInstanceConverter actualServiceInstanceConverterResult =
        kubernetesConverterConfiguration.serviceInstanceConverter(discoveryProperties);

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }

  /**
   * Test KubernetesConverterConfiguration {@link
   * KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}.
   *
   * <ul>
   *   <li>When {@link KubernetesDiscoveryProperties#DEFAULT}.
   * </ul>
   *
   * <p>Method under test: {@link
   * KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  @DisplayName(
      "Test KubernetesConverterConfiguration serviceInstanceConverter(KubernetesDiscoveryProperties); when DEFAULT")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "KubernetesServiceInstanceConverter KubernetesConverterConfiguration.serviceInstanceConverter(KubernetesDiscoveryProperties)"
  })
  void testKubernetesConverterConfigurationServiceInstanceConverter_whenDefault() {
    // Arrange and Act
    KubernetesServiceInstanceConverter actualServiceInstanceConverterResult =
        new KubernetesConverterConfiguration()
            .serviceInstanceConverter(KubernetesDiscoveryProperties.DEFAULT);

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }

  /**
   * Test {@link AdminServerDiscoveryAutoConfiguration#serviceInstanceConverter()}.
   *
   * <p>Method under test: {@link AdminServerDiscoveryAutoConfiguration#serviceInstanceConverter()}
   */
  @Test
  @DisplayName("Test serviceInstanceConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultServiceInstanceConverter AdminServerDiscoveryAutoConfiguration.serviceInstanceConverter()"
  })
  void testServiceInstanceConverter() {
    // Arrange and Act
    DefaultServiceInstanceConverter actualServiceInstanceConverterResult =
        new AdminServerDiscoveryAutoConfiguration().serviceInstanceConverter();

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }
}
