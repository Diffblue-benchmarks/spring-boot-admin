package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.client.config.CloudFoundryApplicationProperties;
import de.codecentric.boot.admin.client.config.InstanceProperties;
import de.codecentric.boot.admin.client.registration.metadata.MetadataContributor;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoints;
import org.springframework.boot.autoconfigure.web.ServerProperties;

class CloudFoundryApplicationFactoryDiffblueTest {
  /**
   * Method under test: {@link CloudFoundryApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  void testGetServiceBaseUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
