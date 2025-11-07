package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.client.config.InstanceProperties;
import de.codecentric.boot.admin.client.registration.metadata.MetadataContributor;
import jakarta.servlet.ServletContext;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link ServletApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServletApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); then return 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ServletApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_thenReturnHttpsExampleOrgExample() {
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
   * Test {@link ServletApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example/}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServletApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); then return 'https://example.org/example/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ServletApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_thenReturnHttpsExampleOrgExample2() {
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
   * Test {@link ServletApplicationFactory#getManagementBaseUrl()}.
   * <p>
   * Method under test: {@link ServletApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ServletApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl() {
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
   * Test {@link ServletApplicationFactory#getManagementBaseUrl()}.
   * <p>
   * Method under test: {@link ServletApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ServletApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl2() {
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
   * Test {@link ServletApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServletApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); then return 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ServletApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_thenReturnHttpsExampleOrgExample() {
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
   * Test {@link ServletApplicationFactory#getManagementContextPath()}.
   * <p>
   * Method under test: {@link ServletApplicationFactory#getManagementContextPath()}
   */
  @Test
  @DisplayName("Test getManagementContextPath()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ServletApplicationFactory.getManagementContextPath()"})
  void testGetManagementContextPath() {
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
   * Test {@link ServletApplicationFactory#getServerContextPath()}.
   * <p>
   * Method under test: {@link ServletApplicationFactory#getServerContextPath()}
   */
  @Test
  @DisplayName("Test getServerContextPath()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ServletApplicationFactory.getServerContextPath()"})
  void testGetServerContextPath() {
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
   * Test {@link ServletApplicationFactory#getDispatcherServletPrefix()}.
   * <p>
   * Method under test: {@link ServletApplicationFactory#getDispatcherServletPrefix()}
   */
  @Test
  @DisplayName("Test getDispatcherServletPrefix()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ServletApplicationFactory.getDispatcherServletPrefix()"})
  void testGetDispatcherServletPrefix() {
    // Arrange, Act and Assert
    assertNull(servletApplicationFactory.getDispatcherServletPrefix());
  }
}
