package de.codecentric.boot.admin.client.registration.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.client.config.CloudFoundryApplicationProperties;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link CloudFoundryMetadataContributor#CloudFoundryMetadataContributor(CloudFoundryApplicationProperties)}.
   * <p>
   * Method under test: {@link CloudFoundryMetadataContributor#CloudFoundryMetadataContributor(CloudFoundryApplicationProperties)}
   */
  @Test
  @DisplayName("Test new CloudFoundryMetadataContributor(CloudFoundryApplicationProperties)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CloudFoundryMetadataContributor.<init>(CloudFoundryApplicationProperties)"})
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

  /**
   * Test {@link CloudFoundryMetadataContributor#getMetadata()}.
   * <p>
   * Method under test: {@link CloudFoundryMetadataContributor#getMetadata()}
   */
  @Test
  @DisplayName("Test getMetadata()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map CloudFoundryMetadataContributor.getMetadata()"})
  void testGetMetadata() {
    // Arrange, Act and Assert
    assertTrue(cloudFoundryMetadataContributor.getMetadata().isEmpty());
  }
}
