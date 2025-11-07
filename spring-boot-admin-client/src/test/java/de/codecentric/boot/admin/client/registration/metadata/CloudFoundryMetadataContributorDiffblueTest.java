package de.codecentric.boot.admin.client.registration.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import de.codecentric.boot.admin.client.config.CloudFoundryApplicationProperties;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CloudFoundryMetadataContributor.class, CloudFoundryApplicationProperties.class})
@ExtendWith(SpringExtension.class)
class CloudFoundryMetadataContributorDiffblueTest {
  @Autowired
  private CloudFoundryApplicationProperties cloudFoundryApplicationProperties;

  @Autowired
  private CloudFoundryMetadataContributor cloudFoundryMetadataContributor;

  /**
   * Method under test: {@link CloudFoundryMetadataContributor#getMetadata()}
   */
  @Test
  void testGetMetadata() {
    // Arrange, Act and Assert
    assertTrue(cloudFoundryMetadataContributor.getMetadata().isEmpty());
  }

  /**
   * Method under test:
   * {@link CloudFoundryMetadataContributor#CloudFoundryMetadataContributor(CloudFoundryApplicationProperties)}
   */
  @Test
  void testNewCloudFoundryMetadataContributor() {
    // Arrange
    CloudFoundryApplicationProperties cfApplicationProperties = new CloudFoundryApplicationProperties();
    cfApplicationProperties.setApplicationId("42");
    cfApplicationProperties.setInstanceIndex("Instance Index");
    cfApplicationProperties.setUris(new ArrayList<>());

    // Act and Assert
    Map<String, String> metadata = (new CloudFoundryMetadataContributor(cfApplicationProperties)).getMetadata();
    assertEquals(2, metadata.size());
    assertEquals("42", metadata.get("applicationId"));
    assertEquals("Instance Index", metadata.get("instanceId"));
  }
}
