package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.client.config.InstanceProperties;
import de.codecentric.boot.admin.client.registration.metadata.MetadataContributor;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoints;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;

class ReactiveApplicationFactoryDiffblueTest {
  /**
   * Method under test: {@link ReactiveApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("https://example.org/example", (new ReactiveApplicationFactory(instance, management, server,
        pathMappedEndpoints, webEndpoint, metadataContributor, new WebFluxProperties())).getServiceUrl());
  }

  /**
   * Method under test: {@link ReactiveApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl2() {
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
    assertEquals("https://example.org/example/", (new ReactiveApplicationFactory(instance, management, server,
        pathMappedEndpoints, webEndpoint, metadataContributor, new WebFluxProperties())).getServiceUrl());
  }

  /**
   * Method under test: {@link ReactiveApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setManagementBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("https://example.org/example", (new ReactiveApplicationFactory(instance, management, server,
        pathMappedEndpoints, webEndpoint, metadataContributor, new WebFluxProperties())).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link ReactiveApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("https://example.org/example", (new ReactiveApplicationFactory(instance, management, server,
        pathMappedEndpoints, webEndpoint, metadataContributor, new WebFluxProperties())).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link ReactiveApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl3() {
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
    assertEquals("https://example.org/example/", (new ReactiveApplicationFactory(instance, management, server,
        pathMappedEndpoints, webEndpoint, metadataContributor, new WebFluxProperties())).getManagementBaseUrl());
  }

  /**
   * Method under test:
   * {@link ReactiveApplicationFactory#getManagementContextPath()}
   */
  @Test
  void testGetManagementContextPath() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("", (new ReactiveApplicationFactory(instance, management, server, pathMappedEndpoints, webEndpoint,
        metadataContributor, new WebFluxProperties())).getManagementContextPath());
  }

  /**
   * Method under test: {@link ReactiveApplicationFactory#getWebfluxBasePath()}
   */
  @Test
  void testGetWebfluxBasePath() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertNull((new ReactiveApplicationFactory(instance, management, server, pathMappedEndpoints, webEndpoint,
        metadataContributor, new WebFluxProperties())).getWebfluxBasePath());
  }
}
