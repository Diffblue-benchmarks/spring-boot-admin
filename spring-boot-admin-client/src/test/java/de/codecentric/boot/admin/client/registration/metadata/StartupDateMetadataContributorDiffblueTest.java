package de.codecentric.boot.admin.client.registration.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StartupDateMetadataContributor.class})
@ExtendWith(SpringExtension.class)
class StartupDateMetadataContributorDiffblueTest {
  @Autowired
  private StartupDateMetadataContributor startupDateMetadataContributor;

  /**
   * Method under test: default or parameterless constructor of
   * {@link StartupDateMetadataContributor}
   */
  @Test
  void testNewStartupDateMetadataContributor() {
    // Arrange, Act and Assert
    assertEquals(1, (new StartupDateMetadataContributor()).getMetadata().size());
  }
}
