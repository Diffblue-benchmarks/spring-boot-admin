package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.client.config.CloudFoundryApplicationProperties;
import de.codecentric.boot.admin.client.config.InstanceProperties;
import de.codecentric.boot.admin.client.registration.metadata.MetadataContributor;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoints;
import org.springframework.boot.autoconfigure.web.ServerProperties;

class CloudFoundryApplicationFactoryDiffblueTest {
  /**
   * Test {@link CloudFoundryApplicationFactory#getServiceBaseUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CloudFoundryApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  @DisplayName("Test getServiceBaseUrl(); then return 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String CloudFoundryApplicationFactory.getServiceBaseUrl()"})
  void testGetServiceBaseUrl_thenReturnHttpsExampleOrgExample() {
    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("https://example.org/example",
        (new CloudFoundryApplicationFactory(instance, management, server, pathMappedEndpoints, webEndpoint,
            metadataContributor, new CloudFoundryApplicationProperties())).getServiceBaseUrl());
  }
}
