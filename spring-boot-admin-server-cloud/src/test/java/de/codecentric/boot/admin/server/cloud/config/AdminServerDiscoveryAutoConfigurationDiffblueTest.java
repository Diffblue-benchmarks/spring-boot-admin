package de.codecentric.boot.admin.server.cloud.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties.Metadata;

@ExtendWith(MockitoExtension.class)
class AdminServerDiscoveryAutoConfigurationDiffblueTest {
  @InjectMocks
  private AdminServerDiscoveryAutoConfiguration adminServerDiscoveryAutoConfiguration;

  @InjectMocks
  private EurekaConverterConfiguration eurekaConverterConfiguration;

  @InjectMocks
  private KubernetesConverterConfiguration kubernetesConverterConfiguration;

  /**
   * Test EurekaConverterConfiguration {@link EurekaConverterConfiguration#serviceInstanceConverter()}.
   * <p>
   * Method under test: {@link EurekaConverterConfiguration#serviceInstanceConverter()}
   */
  @Test
  @DisplayName("Test EurekaConverterConfiguration serviceInstanceConverter()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"EurekaServiceInstanceConverter EurekaConverterConfiguration.serviceInstanceConverter()"})
  void testEurekaConverterConfigurationServiceInstanceConverter() {
    // Arrange and Act
    EurekaServiceInstanceConverter actualServiceInstanceConverterResult = eurekaConverterConfiguration
        .serviceInstanceConverter();

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }

  /**
   * Test {@link AdminServerDiscoveryAutoConfiguration#instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository)}.
   * <ul>
   *   <li>When {@link DefaultServiceInstanceConverter} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerDiscoveryAutoConfiguration#instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository)}
   */
  @Test
  @DisplayName("Test instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository); when DefaultServiceInstanceConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "InstanceDiscoveryListener AdminServerDiscoveryAutoConfiguration.instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository)"})
  void testInstanceDiscoveryListener_whenDefaultServiceInstanceConverter() {
    // Arrange
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
   * Test {@link AdminServerDiscoveryAutoConfiguration#instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository)}.
   * <ul>
   *   <li>When {@link ServiceInstanceConverter}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerDiscoveryAutoConfiguration#instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository)}
   */
  @Test
  @DisplayName("Test instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository); when ServiceInstanceConverter")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "InstanceDiscoveryListener AdminServerDiscoveryAutoConfiguration.instanceDiscoveryListener(ServiceInstanceConverter, DiscoveryClient, InstanceRegistry, InstanceRepository)"})
  void testInstanceDiscoveryListener_whenServiceInstanceConverter() {
    // Arrange
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
   * Test KubernetesConverterConfiguration {@link KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}.
   * <p>
   * Method under test: {@link KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  @DisplayName("Test KubernetesConverterConfiguration serviceInstanceConverter(KubernetesDiscoveryProperties)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "KubernetesServiceInstanceConverter KubernetesConverterConfiguration.serviceInstanceConverter(KubernetesDiscoveryProperties)"})
  void testKubernetesConverterConfigurationServiceInstanceConverter() {
    // Arrange
    HashSet<String> namespaces = new HashSet<>();
    HashSet<Integer> knownSecurePorts = new HashSet<>();
    HashMap<String, String> serviceLabels = new HashMap<>();

    // Act
    KubernetesServiceInstanceConverter actualServiceInstanceConverterResult = kubernetesConverterConfiguration
        .serviceInstanceConverter(new KubernetesDiscoveryProperties(true, true, namespaces, true, 1L, true, "Filter",
            knownSecurePorts, serviceLabels, "Primary Port Name",
            new Metadata(true, "Labels Prefix", true, "Annotations Prefix", true, "Ports Prefix"), 1, true));

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }

  /**
   * Test KubernetesConverterConfiguration {@link KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}.
   * <p>
   * Method under test: {@link KubernetesConverterConfiguration#serviceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  @DisplayName("Test KubernetesConverterConfiguration serviceInstanceConverter(KubernetesDiscoveryProperties)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "KubernetesServiceInstanceConverter KubernetesConverterConfiguration.serviceInstanceConverter(KubernetesDiscoveryProperties)"})
  void testKubernetesConverterConfigurationServiceInstanceConverter2() {
    // Arrange
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
   * Test {@link AdminServerDiscoveryAutoConfiguration#serviceInstanceConverter()}.
   * <p>
   * Method under test: {@link AdminServerDiscoveryAutoConfiguration#serviceInstanceConverter()}
   */
  @Test
  @DisplayName("Test serviceInstanceConverter()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "DefaultServiceInstanceConverter AdminServerDiscoveryAutoConfiguration.serviceInstanceConverter()"})
  void testServiceInstanceConverter() {
    // Arrange and Act
    DefaultServiceInstanceConverter actualServiceInstanceConverterResult = adminServerDiscoveryAutoConfiguration
        .serviceInstanceConverter();

    // Assert
    assertEquals("/actuator", actualServiceInstanceConverterResult.getManagementContextPath());
    assertEquals("health", actualServiceInstanceConverterResult.getHealthEndpointPath());
  }
}
