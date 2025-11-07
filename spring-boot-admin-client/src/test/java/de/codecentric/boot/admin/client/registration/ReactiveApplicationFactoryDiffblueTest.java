package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;

class ReactiveApplicationFactoryDiffblueTest {
  /**
   * Test {@link ReactiveApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); then return 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ReactiveApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_thenReturnHttpsExampleOrgExample() {
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
   * Test {@link ReactiveApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example/}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); then return 'https://example.org/example/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ReactiveApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_thenReturnHttpsExampleOrgExample2() {
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
   * Test {@link ReactiveApplicationFactory#getManagementBaseUrl()}.
   * <p>
   * Method under test: {@link ReactiveApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ReactiveApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl() {
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
   * Test {@link ReactiveApplicationFactory#getManagementBaseUrl()}.
   * <p>
   * Method under test: {@link ReactiveApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ReactiveApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl2() {
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
   * Test {@link ReactiveApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example/}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); then return 'https://example.org/example/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ReactiveApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_thenReturnHttpsExampleOrgExample() {
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
   * Test {@link ReactiveApplicationFactory#getManagementContextPath()}.
   * <p>
   * Method under test: {@link ReactiveApplicationFactory#getManagementContextPath()}
   */
  @Test
  @DisplayName("Test getManagementContextPath()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ReactiveApplicationFactory.getManagementContextPath()"})
  void testGetManagementContextPath() {
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
   * Test {@link ReactiveApplicationFactory#getWebfluxBasePath()}.
   * <p>
   * Method under test: {@link ReactiveApplicationFactory#getWebfluxBasePath()}
   */
  @Test
  @DisplayName("Test getWebfluxBasePath()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ReactiveApplicationFactory.getWebfluxBasePath()"})
  void testGetWebfluxBasePath() {
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
