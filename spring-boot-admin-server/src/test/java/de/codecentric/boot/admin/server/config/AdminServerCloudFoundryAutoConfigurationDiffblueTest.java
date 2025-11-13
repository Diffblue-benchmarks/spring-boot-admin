package de.codecentric.boot.admin.server.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.services.CloudFoundryInstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AdminServerCloudFoundryAutoConfigurationDiffblueTest {
  /**
   * Test {@link AdminServerCloudFoundryAutoConfiguration#instanceIdGenerator()}.
   *
   * <p>Method under test: {@link AdminServerCloudFoundryAutoConfiguration#instanceIdGenerator()}
   */
  @Test
  @DisplayName("Test instanceIdGenerator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceIdGenerator AdminServerCloudFoundryAutoConfiguration.instanceIdGenerator()"
  })
  void testInstanceIdGenerator() {
    // Arrange and Act
    InstanceIdGenerator actualInstanceIdGeneratorResult =
        new AdminServerCloudFoundryAutoConfiguration().instanceIdGenerator();
    InstanceId actualGenerateIdResult =
        actualInstanceIdGeneratorResult.generateId(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Assert
    assertTrue(actualInstanceIdGeneratorResult instanceof CloudFoundryInstanceIdGenerator);
    assertEquals("504149e8a3fa", actualGenerateIdResult.getValue());
    assertEquals("504149e8a3fa", actualGenerateIdResult.toString());
  }
}
