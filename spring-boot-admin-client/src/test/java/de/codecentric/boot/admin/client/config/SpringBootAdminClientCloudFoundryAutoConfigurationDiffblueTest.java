package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SpringBootAdminClientCloudFoundryAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link SpringBootAdminClientCloudFoundryAutoConfiguration#cloudFoundryMetadataContributor(CloudFoundryApplicationProperties)}
   */
  @Test
  void testCloudFoundryMetadataContributor() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SpringBootAdminClientCloudFoundryAutoConfiguration springBootAdminClientCloudFoundryAutoConfiguration = new SpringBootAdminClientCloudFoundryAutoConfiguration();

    CloudFoundryApplicationProperties cloudFoundryApplicationProperties = new CloudFoundryApplicationProperties();
    cloudFoundryApplicationProperties.setApplicationId("42");
    cloudFoundryApplicationProperties.setInstanceIndex("Instance Index");
    cloudFoundryApplicationProperties.setUris(new ArrayList<>());

    // Act
    Map<String, String> actualMetadata = springBootAdminClientCloudFoundryAutoConfiguration
        .cloudFoundryMetadataContributor(cloudFoundryApplicationProperties)
        .getMetadata();

    // Assert
    assertEquals(2, actualMetadata.size());
    assertEquals("42", actualMetadata.get("applicationId"));
    assertEquals("Instance Index", actualMetadata.get("instanceId"));
  }
}
