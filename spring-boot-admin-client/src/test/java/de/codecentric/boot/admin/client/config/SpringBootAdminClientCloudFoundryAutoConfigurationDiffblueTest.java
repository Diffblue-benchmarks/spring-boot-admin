package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpringBootAdminClientCloudFoundryAutoConfigurationDiffblueTest {
  @InjectMocks
  private SpringBootAdminClientCloudFoundryAutoConfiguration springBootAdminClientCloudFoundryAutoConfiguration;

  /**
   * Test {@link SpringBootAdminClientCloudFoundryAutoConfiguration#cloudFoundryMetadataContributor(CloudFoundryApplicationProperties)}.
   * <p>
   * Method under test: {@link SpringBootAdminClientCloudFoundryAutoConfiguration#cloudFoundryMetadataContributor(CloudFoundryApplicationProperties)}
   */
  @Test
  @DisplayName("Test cloudFoundryMetadataContributor(CloudFoundryApplicationProperties)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "de.codecentric.boot.admin.client.registration.metadata.CloudFoundryMetadataContributor SpringBootAdminClientCloudFoundryAutoConfiguration.cloudFoundryMetadataContributor(CloudFoundryApplicationProperties)"})
  void testCloudFoundryMetadataContributor() {
    // Arrange
    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    // Act and Assert
    Map<String, String> metadata = springBootAdminClientCloudFoundryAutoConfiguration
        .cloudFoundryMetadataContributor(cloudFoundryApplicationProperties)
        .getMetadata();
    assertEquals(2, metadata.size());
    assertEquals("42", metadata.get("applicationId"));
    assertEquals("Instance Index", metadata.get("instanceId"));
  }
}
