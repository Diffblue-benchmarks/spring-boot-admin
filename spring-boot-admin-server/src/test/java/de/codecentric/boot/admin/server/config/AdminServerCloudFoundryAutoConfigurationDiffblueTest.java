package de.codecentric.boot.admin.server.config;

import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.services.CloudFoundryInstanceIdGenerator;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdminServerCloudFoundryAutoConfigurationDiffblueTest {
  @InjectMocks
  private AdminServerCloudFoundryAutoConfiguration adminServerCloudFoundryAutoConfiguration;

  /**
   * Test {@link AdminServerCloudFoundryAutoConfiguration#instanceIdGenerator()}.
   * <p>
   * Method under test: {@link AdminServerCloudFoundryAutoConfiguration#instanceIdGenerator()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "de.codecentric.boot.admin.server.services.InstanceIdGenerator AdminServerCloudFoundryAutoConfiguration.instanceIdGenerator()"})
  public void testInstanceIdGenerator() {
    // Arrange, Act and Assert
    assertTrue(
        adminServerCloudFoundryAutoConfiguration.instanceIdGenerator() instanceof CloudFoundryInstanceIdGenerator);
  }
}
