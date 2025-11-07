package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.client.config.InstanceProperties;
import de.codecentric.boot.admin.client.config.ServiceHostType;
import de.codecentric.boot.admin.client.registration.metadata.MetadataContributor;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoints;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.Ssl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultApplicationFactory.class, InstanceProperties.class,
    ManagementServerProperties.class, ServerProperties.class, WebEndpointProperties.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DefaultApplicationFactoryDiffblueTest {
  @Autowired
  private DefaultApplicationFactory defaultApplicationFactory;

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
  private WebEndpointProperties webEndpointProperties;

  /**
   * Method under test: {@link DefaultApplicationFactory#createApplication()}
   */
  @Test
  void testCreateApplication() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> defaultApplicationFactory.createApplication());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getName()}
   */
  @Test
  void testGetName() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("spring-boot-application", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getName());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(true);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("http", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new ReactiveApplicationFactory(instance, management, server,
        pathMappedEndpoints, webEndpoint, metadataContributor, new WebFluxProperties())).getServiceUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example/", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceHostType(ServiceHostType.IP);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InetAddress address = mock(InetAddress.class);
    when(address.getCanonicalHostName()).thenReturn("Canonical Host Name");

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceUrl());
    verify(address).getCanonicalHostName();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  void testGetServiceUrl10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(false);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  void testGetServiceBaseUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  void testGetServiceBaseUrl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(true);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  void testGetServiceBaseUrl3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  void testGetServiceBaseUrl4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  void testGetServiceBaseUrl5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceHostType(ServiceHostType.IP);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  void testGetServiceBaseUrl6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InetAddress address = mock(InetAddress.class);
    when(address.getCanonicalHostName()).thenReturn("Canonical Host Name");

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceBaseUrl());
    verify(address).getCanonicalHostName();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  void testGetServiceBaseUrl7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceBaseUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  void testGetServiceBaseUrl8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(false);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceBaseUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServicePath()}
   */
  @Test
  void testGetServicePath() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("/", (new DefaultApplicationFactory(instance, management, server, pathMappedEndpoints,
        new WebEndpointProperties(), mock(MetadataContributor.class))).getServicePath());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServicePath()}
   */
  @Test
  void testGetServicePath2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServicePath("/");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("/", (new DefaultApplicationFactory(instance, management, server, pathMappedEndpoints,
        new WebEndpointProperties(), mock(MetadataContributor.class))).getServicePath());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(true);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("http", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new ReactiveApplicationFactory(instance, management, server,
        pathMappedEndpoints, webEndpoint, metadataContributor, new WebFluxProperties())).getManagementUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setManagementUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setManagementBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example/actuator", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example/actuator", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example/actuator", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceHostType(ServiceHostType.IP);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InetAddress address = mock(InetAddress.class);
    when(address.getCanonicalHostName()).thenReturn("Canonical Host Name");

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
    verify(address).getCanonicalHostName();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  void testGetManagementUrl12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(false);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(true);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("http", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new ReactiveApplicationFactory(instance, management, server,
        pathMappedEndpoints, webEndpoint, metadataContributor, new WebFluxProperties())).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setManagementBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example/", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceHostType(ServiceHostType.IP);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InetAddress address = mock(InetAddress.class);
    when(address.getCanonicalHostName()).thenReturn("Canonical Host Name");

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
    verify(address).getCanonicalHostName();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  void testGetManagementBaseUrl11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(false);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementBaseUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#isManagementPortEqual()}
   */
  @Test
  void testIsManagementPortEqual() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertTrue((new DefaultApplicationFactory(instance, management, server, pathMappedEndpoints,
        new WebEndpointProperties(), mock(MetadataContributor.class))).isManagementPortEqual());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getEndpointsWebPath()}
   */
  @Test
  void testGetEndpointsWebPath() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("/actuator", (new DefaultApplicationFactory(instance, management, server, pathMappedEndpoints,
        new WebEndpointProperties(), mock(MetadataContributor.class))).getEndpointsWebPath());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(true);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("http", new ArrayList<>());

    WebEndpointProperties webEndpoint = new WebEndpointProperties();
    MetadataContributor metadataContributor = mock(MetadataContributor.class);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new ReactiveApplicationFactory(instance, management, server,
        pathMappedEndpoints, webEndpoint, metadataContributor, new WebFluxProperties())).getHealthUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setManagementBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceBaseUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setHealthUrl("https://example.org/example");
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("https://example.org/example", (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setServiceHostType(ServiceHostType.IP);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InetAddress address = mock(InetAddress.class);
    when(address.getCanonicalHostName()).thenReturn("Canonical Host Name");

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
    verify(address).getCanonicalHostName();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  void testGetHealthUrl12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(false);
    ssl.setEnabledProtocols(new String[]{"http"});
    ssl.setKeyAlias("http");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("http");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("http");
    ssl.setKeyStoreType("http");
    ssl.setProtocol("http");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("http");
    ssl.setTrustCertificatePrivateKey("http");
    ssl.setTrustStore("http");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("http");
    ssl.setTrustStoreType("http");

    ServerProperties server = new ServerProperties();
    server.setSsl(ssl);
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthUrl());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getMetadata()}
   */
  @Test
  void testGetMetadata() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    MetadataContributor metadataContributor = mock(MetadataContributor.class);
    when(metadataContributor.getMetadata()).thenReturn(new HashMap<>());
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act
    Map<String, String> actualMetadata = (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), metadataContributor)).getMetadata();

    // Assert
    verify(metadataContributor).getMetadata();
    assertTrue(actualMetadata.isEmpty());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getMetadata()}
   */
  @Test
  void testGetMetadata2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    MetadataContributor metadataContributor = mock(MetadataContributor.class);
    when(metadataContributor.getMetadata()).thenThrow(new IllegalArgumentException("foo"));
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), metadataContributor)).getMetadata());
    verify(metadataContributor).getMetadata();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceHost()}
   */
  @Test
  void testGetServiceHost() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InetAddress address = mock(InetAddress.class);
    when(address.getCanonicalHostName()).thenReturn("Canonical Host Name");

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act
    String actualServiceHost = (new DefaultApplicationFactory(instance, management, server, pathMappedEndpoints,
        new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceHost();

    // Assert
    verify(address).getCanonicalHostName();
    assertEquals("Canonical Host Name", actualServiceHost);
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getServiceHost()}
   */
  @Test
  void testGetServiceHost2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    ServerProperties server = new ServerProperties();
    server.setAddress(address);
    ManagementServerProperties management = new ManagementServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getServiceHost());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementHost()}
   */
  @Test
  void testGetManagementHost() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InetAddress address = mock(InetAddress.class);
    when(address.getCanonicalHostName()).thenReturn("Canonical Host Name");

    ManagementServerProperties management = new ManagementServerProperties();
    management.setAddress(address);
    InstanceProperties instance = new InstanceProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act
    String actualManagementHost = (new DefaultApplicationFactory(instance, management, server, pathMappedEndpoints,
        new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementHost();

    // Assert
    verify(address).getCanonicalHostName();
    assertEquals("Canonical Host Name", actualManagementHost);
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getManagementHost()}
   */
  @Test
  void testGetManagementHost2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    ManagementServerProperties management = new ManagementServerProperties();
    management.setAddress(address);
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getManagementHost());
    verify(address).getHostAddress();
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getLocalServerPort()}
   */
  @Test
  void testGetLocalServerPort() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getLocalServerPort());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getLocalManagementPort()}
   */
  @Test
  void testGetLocalManagementPort() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getLocalManagementPort());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHealthEndpointPath()}
   */
  @Test
  void testGetHealthEndpointPath() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> (new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class))).getHealthEndpointPath());
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getScheme(Ssl)}
   */
  @Test
  void testGetScheme() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    DefaultApplicationFactory defaultApplicationFactory = new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class));

    Ssl ssl = new Ssl();
    ssl.setBundle("Bundle");
    ssl.setCertificate("Certificate");
    ssl.setCertificatePrivateKey("Certificate Private Key");
    ssl.setCiphers(new String[]{"Ciphers"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabled(true);
    ssl.setEnabledProtocols(new String[]{"Enabled Protocols"});
    ssl.setKeyAlias("Key Alias");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("Key Store");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("Key Store Provider");
    ssl.setKeyStoreType("Key Store Type");
    ssl.setProtocol("Protocol");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("Trust Certificate");
    ssl.setTrustCertificatePrivateKey("Trust Certificate Private Key");
    ssl.setTrustStore("Trust Store");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("Trust Store Provider");
    ssl.setTrustStoreType("Trust Store Type");

    // Act and Assert
    assertEquals("https", defaultApplicationFactory.getScheme(ssl));
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getScheme(Ssl)}
   */
  @Test
  void testGetScheme2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    // Act and Assert
    assertEquals("http", (new DefaultApplicationFactory(instance, management, server, pathMappedEndpoints,
        new WebEndpointProperties(), mock(MetadataContributor.class))).getScheme(null));
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getScheme(Ssl)}
   */
  @Test
  void testGetScheme3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    DefaultApplicationFactory defaultApplicationFactory = new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class));

    Ssl ssl = new Ssl();
    ssl.setBundle("Bundle");
    ssl.setCertificate("Certificate");
    ssl.setCertificatePrivateKey("Certificate Private Key");
    ssl.setCiphers(new String[]{"Ciphers"});
    ssl.setClientAuth(Ssl.ClientAuth.NONE);
    ssl.setEnabledProtocols(new String[]{"Enabled Protocols"});
    ssl.setKeyAlias("Key Alias");
    ssl.setKeyPassword("iloveyou");
    ssl.setKeyStore("Key Store");
    ssl.setKeyStorePassword("iloveyou");
    ssl.setKeyStoreProvider("Key Store Provider");
    ssl.setKeyStoreType("Key Store Type");
    ssl.setProtocol("Protocol");
    ssl.setServerNameBundles(new ArrayList<>());
    ssl.setTrustCertificate("Trust Certificate");
    ssl.setTrustCertificatePrivateKey("Trust Certificate Private Key");
    ssl.setTrustStore("Trust Store");
    ssl.setTrustStorePassword("iloveyou");
    ssl.setTrustStoreProvider("Trust Store Provider");
    ssl.setTrustStoreType("Trust Store Type");
    ssl.setEnabled(false);

    // Act and Assert
    assertEquals("http", defaultApplicationFactory.getScheme(ssl));
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHost(InetAddress)}
   */
  @Test
  void testGetHost() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    DefaultApplicationFactory defaultApplicationFactory = new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class));
    InetAddress address = mock(InetAddress.class);
    when(address.getCanonicalHostName()).thenReturn("Canonical Host Name");

    // Act
    String actualHost = defaultApplicationFactory.getHost(address);

    // Assert
    verify(address).getCanonicalHostName();
    assertEquals("Canonical Host Name", actualHost);
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHost(InetAddress)}
   */
  @Test
  void testGetHost2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    DefaultApplicationFactory defaultApplicationFactory = new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class));
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenReturn("42 Main St");

    // Act
    String actualHost = defaultApplicationFactory.getHost(address);

    // Assert
    verify(address).getHostAddress();
    assertEquals("42 Main St", actualHost);
  }

  /**
   * Method under test: {@link DefaultApplicationFactory#getHost(InetAddress)}
   */
  @Test
  void testGetHost3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceProperties instance = new InstanceProperties();
    instance.setPreferIp(true);
    ManagementServerProperties management = new ManagementServerProperties();
    ServerProperties server = new ServerProperties();
    PathMappedEndpoints pathMappedEndpoints = new PathMappedEndpoints("Base Path", new ArrayList<>());

    DefaultApplicationFactory defaultApplicationFactory = new DefaultApplicationFactory(instance, management, server,
        pathMappedEndpoints, new WebEndpointProperties(), mock(MetadataContributor.class));
    InetAddress address = mock(InetAddress.class);
    when(address.getHostAddress()).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultApplicationFactory.getHost(address));
    verify(address).getHostAddress();
  }
}
