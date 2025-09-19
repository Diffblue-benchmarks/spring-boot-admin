package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties;
import org.springframework.cloud.kubernetes.commons.discovery.KubernetesDiscoveryProperties.Metadata;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {KubernetesServiceInstanceConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class KubernetesServiceInstanceConverterDiffblueTest {
  @MockitoBean private KubernetesDiscoveryProperties kubernetesDiscoveryProperties;

  @Autowired private KubernetesServiceInstanceConverter kubernetesServiceInstanceConverter;

  /**
   * Test {@link
   * KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}.
   *
   * <p>Method under test: {@link
   * KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  @DisplayName("Test new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void KubernetesServiceInstanceConverter.<init>(KubernetesDiscoveryProperties)"
  })
  void testNewKubernetesServiceInstanceConverter() {
    // Arrange
    Metadata metadata = new Metadata(true, "Labels Prefix", true, "Annotations Prefix", true, null);
    when(kubernetesDiscoveryProperties.metadata()).thenReturn(metadata);

    // Act
    KubernetesServiceInstanceConverter actualKubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(kubernetesDiscoveryProperties);

    // Assert
    verify(kubernetesDiscoveryProperties, atLeast(1)).metadata();
    assertEquals("/actuator", actualKubernetesServiceInstanceConverter.getManagementContextPath());
    assertEquals("health", actualKubernetesServiceInstanceConverter.getHealthEndpointPath());
  }

  /**
   * Test {@link
   * KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}.
   *
   * <p>Method under test: {@link
   * KubernetesServiceInstanceConverter#KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)}
   */
  @Test
  @DisplayName("Test new KubernetesServiceInstanceConverter(KubernetesDiscoveryProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void KubernetesServiceInstanceConverter.<init>(KubernetesDiscoveryProperties)"
  })
  void testNewKubernetesServiceInstanceConverter2() {
    // Arrange
    Metadata metadata =
        new Metadata(true, "Labels Prefix", true, "Annotations Prefix", true, "Ports Prefix");
    when(kubernetesDiscoveryProperties.metadata()).thenReturn(metadata);

    // Act
    KubernetesServiceInstanceConverter actualKubernetesServiceInstanceConverter =
        new KubernetesServiceInstanceConverter(kubernetesDiscoveryProperties);

    // Assert
    verify(kubernetesDiscoveryProperties, atLeast(1)).metadata();
    assertEquals("/actuator", actualKubernetesServiceInstanceConverter.getManagementContextPath());
    assertEquals("health", actualKubernetesServiceInstanceConverter.getHealthEndpointPath());
  }

  /**
   * Test {@link KubernetesServiceInstanceConverter#getManagementPort(ServiceInstance)}.
   *
   * <p>Method under test: {@link
   * KubernetesServiceInstanceConverter#getManagementPort(ServiceInstance)}
   */
  @Test
  @DisplayName("Test getManagementPort(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int KubernetesServiceInstanceConverter.getManagementPort(ServiceInstance)"})
  void testGetManagementPort() {
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
}
