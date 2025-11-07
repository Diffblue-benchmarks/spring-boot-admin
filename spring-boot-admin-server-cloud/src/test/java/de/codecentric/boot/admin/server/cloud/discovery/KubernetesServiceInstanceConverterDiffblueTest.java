package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties.Metadata;
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
   * Test {@link KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}.
   * <p>
   * Method under test: {@link KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  @DisplayName("Test new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void KubernetesServiceInstanceConverter.<init>(KubernetesDiscoveryProperties)"})
  void testNewKubernetesServiceInstanceConverter() {
    // Arrange
    when(kubernetesDiscoveryProperties.metadata())
        .thenReturn(new Metadata(true, "Labels Prefix", true, "Annotations Prefix", true, "Ports Prefix"));

    // Act
    KubernetesServiceInstanceConverter actualKubernetesServiceInstanceConverter = new KubernetesServiceInstanceConverter(
        kubernetesDiscoveryProperties);

    // Assert
    verify(kubernetesDiscoveryProperties, atLeast(1)).metadata();
    assertEquals("/actuator", actualKubernetesServiceInstanceConverter.getManagementContextPath());
    assertEquals("health", actualKubernetesServiceInstanceConverter.getHealthEndpointPath());
  }

  /**
   * Test {@link KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}.
   * <p>
   * Method under test: {@link KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  @DisplayName("Test new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void KubernetesServiceInstanceConverter.<init>(KubernetesDiscoveryProperties)"})
  void testNewKubernetesServiceInstanceConverter2() {
    // Arrange
    when(kubernetesDiscoveryProperties.metadata())
        .thenReturn(new Metadata(true, "Labels Prefix", true, "Annotations Prefix", true, null));

    // Act
    KubernetesServiceInstanceConverter actualKubernetesServiceInstanceConverter = new KubernetesServiceInstanceConverter(
        kubernetesDiscoveryProperties);

    // Assert
    verify(kubernetesDiscoveryProperties, atLeast(1)).metadata();
    assertEquals("/actuator", actualKubernetesServiceInstanceConverter.getManagementContextPath());
    assertEquals("health", actualKubernetesServiceInstanceConverter.getHealthEndpointPath());
  }

  /**
   * Test {@link KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  @DisplayName("Test new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties); given 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void KubernetesServiceInstanceConverter.<init>(KubernetesDiscoveryProperties)"})
  void testNewKubernetesServiceInstanceConverter_givenNull() {
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

  /**
   * Test {@link KubernetesServiceInstanceConverter#getManagementPort(ServiceInstance)}.
   * <p>
   * Method under test: {@link KubernetesServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementPort(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"int KubernetesServiceInstanceConverter.getManagementPort(ServiceInstance)"})
  void testGetManagementPort() {
    // Arrange
    HashSet<String> namespaces = new HashSet<>();
    HashSet<Integer> knownSecurePorts = new HashSet<>();
    HashMap<String, String> serviceLabels = new HashMap<>();
    KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter = new KubernetesServiceInstanceConverter(
        new KubernetesDiscoveryProperties(true, true, namespaces, true, 1L, true, "Filter", knownSecurePorts,
            serviceLabels, "Primary Port Name",
            new Metadata(true, "Labels Prefix", true, "Annotations Prefix", true, "Ports Prefix"), 1, true));

    // Act and Assert
    assertEquals(8080, kubernetesServiceInstanceConverter
        .getManagementPort(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }
}
