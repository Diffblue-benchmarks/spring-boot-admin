package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.client.config.InstanceProperties;
import de.codecentric.boot.admin.client.registration.metadata.MetadataContributor;
import jakarta.servlet.ServletContext;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoints;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.DispatcherServlet;

@ContextConfiguration(classes = {ServletApplicationFactory.class, InstanceProperties.class,
    ManagementServerProperties.class, ServerProperties.class, WebEndpointProperties.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ServletApplicationFactoryDiffblueTest {
  @MockBean
  private DispatcherServletPath dispatcherServletPath;

  @Autowired
  private InstanceProperties instanceProperties;

  @Autowired
  private ManagementServerProperties managementServerProperties;

  @MockBean
  private MetadataContributor metadataContributor;

  @MockBean
  private PathMappedEndpoints pathMappedEndpoints;

  @Autowired
  private ServerProperties serverProperties;

  @Autowired
  private ServletApplicationFactory servletApplicationFactory;

  @MockBean
  private ServletContext servletContext;

  @Autowired
  private WebEndpointProperties webEndpointProperties;

  /**
   * Method under test: {@link ServletApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    MockServletContext servletContext = new MockServletContext();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("https://example.org/example",
        (new ServletApplicationFactory(instance, management, server, servletContext, pathMappedEndpoints, webEndpoint,
            metadataContributor, new DispatcherServletRegistrationBean(new DispatcherServlet(), "Path")))
            .getServiceUrl());
  }

  /**
   * Method under test: {@link ServletApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    MockServletContext servletContext = new MockServletContext();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("https://example.org/example/",
        (new ServletApplicationFactory(instance, management, server, servletContext, pathMappedEndpoints, webEndpoint,
            metadataContributor, new DispatcherServletRegistrationBean(new DispatcherServlet(), "Path")))
            .getServiceUrl());
  }

  /**
   * Method under test: {@link ServletApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setManagementBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    MockServletContext servletContext = new MockServletContext();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("https://example.org/example",
        (new ServletApplicationFactory(instance, management, server, servletContext, pathMappedEndpoints, webEndpoint,
            metadataContributor, new DispatcherServletRegistrationBean(new DispatcherServlet(), "Path")))
            .getManagementBaseUrl());
  }

  /**
   * Method under test: {@link ServletApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    MockServletContext servletContext = new MockServletContext();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("https://example.org/example/Path",
        (new ServletApplicationFactory(instance, management, server, servletContext, pathMappedEndpoints, webEndpoint,
            metadataContributor, new DispatcherServletRegistrationBean(new DispatcherServlet(), "Path")))
            .getManagementBaseUrl());
  }

  /**
   * Method under test: {@link ServletApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    MockServletContext servletContext = new MockServletContext();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("https://example.org/example/Path",
        (new ServletApplicationFactory(instance, management, server, servletContext, pathMappedEndpoints, webEndpoint,
            metadataContributor, new DispatcherServletRegistrationBean(new DispatcherServlet(), "Path")))
            .getManagementBaseUrl());
  }

  /**
   * Method under test:
   * {@link ServletApplicationFactory#getManagementContextPath()}
   */
  @Test
  void testGetManagementContextPath() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    MockServletContext servletContext = new MockServletContext();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("",
        (new ServletApplicationFactory(instance, management, server, servletContext, pathMappedEndpoints, webEndpoint,
            metadataContributor, new DispatcherServletRegistrationBean(new DispatcherServlet(), "Path")))
            .getManagementContextPath());
  }

  /**
   * Method under test: {@link ServletApplicationFactory#getServerContextPath()}
   */
  @Test
  void testGetServerContextPath() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    MockServletContext servletContext = new MockServletContext();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertEquals("",
        (new ServletApplicationFactory(instance, management, server, servletContext, pathMappedEndpoints, webEndpoint,
            metadataContributor, new DispatcherServletRegistrationBean(new DispatcherServlet(), "Path")))
            .getServerContextPath());
  }

  /**
   * Method under test:
   * {@link ServletApplicationFactory#getDispatcherServletPrefix()}
   */
  @Test
  void testGetDispatcherServletPrefix() {
    // Arrange
    when(dispatcherServletPath.getPrefix()).thenReturn("Prefix");

    // Act
    String actualDispatcherServletPrefix = servletApplicationFactory.getDispatcherServletPrefix();

    // Assert
    verify(dispatcherServletPath).getPrefix();
    assertEquals("Prefix", actualDispatcherServletPrefix);
  }
}
