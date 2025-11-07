package de.codecentric.boot.admin.client.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.client.config.InstanceProperties;
import de.codecentric.boot.admin.client.config.ServiceHostType;
import de.codecentric.boot.admin.client.registration.metadata.MetadataContributor;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
import org.springframework.boot.web.server.Ssl.ClientAuth;
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
   * Test {@link DefaultApplicationFactory#createApplication()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#createApplication()}
   */
  @Test
  @DisplayName("Test createApplication()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "de.codecentric.boot.admin.client.registration.Application DefaultApplicationFactory.createApplication()"})
  void testCreateApplication() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> defaultApplicationFactory.createApplication());
  }

  /**
   * Test {@link DefaultApplicationFactory#getName()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getName()}
   */
  @Test
  @DisplayName("Test getName()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getName()"})
  void testGetName() {
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl() {
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) PreferIp is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); given InstanceProperties (default constructor) PreferIp is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_givenInstancePropertiesPreferIpIsTrue() {
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) ServiceHostType is {@code IP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); given InstanceProperties (default constructor) ServiceHostType is 'IP'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_givenInstancePropertiesServiceHostTypeIsIp() {
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code false}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); given Ssl (default constructor) Enabled is 'false'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_givenSslEnabledIsFalse_thenThrowIllegalArgumentException() {
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
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code true}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); given Ssl (default constructor) Enabled is 'true'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_givenSslEnabledIsTrue_thenThrowIllegalStateException() {
    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Then calls {@link InetAddress#getCanonicalHostName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); then calls getCanonicalHostName()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_thenCallsGetCanonicalHostName() {
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); then return 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_thenReturnHttpsExampleOrgExample() {
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example/}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); then return 'https://example.org/example/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_thenReturnHttpsExampleOrgExample2() {
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_thenThrowIllegalArgumentException() {
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
   * Test {@link DefaultApplicationFactory#getServiceUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceUrl()}
   */
  @Test
  @DisplayName("Test getServiceUrl(); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceUrl()"})
  void testGetServiceUrl_thenThrowIllegalStateException() {
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
   * Test {@link DefaultApplicationFactory#getServiceBaseUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) PreferIp is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  @DisplayName("Test getServiceBaseUrl(); given InstanceProperties (default constructor) PreferIp is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceBaseUrl()"})
  void testGetServiceBaseUrl_givenInstancePropertiesPreferIpIsTrue() {
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
   * Test {@link DefaultApplicationFactory#getServiceBaseUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) ServiceHostType is {@code IP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  @DisplayName("Test getServiceBaseUrl(); given InstanceProperties (default constructor) ServiceHostType is 'IP'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceBaseUrl()"})
  void testGetServiceBaseUrl_givenInstancePropertiesServiceHostTypeIsIp() {
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
   * Test {@link DefaultApplicationFactory#getServiceBaseUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code false}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  @DisplayName("Test getServiceBaseUrl(); given Ssl (default constructor) Enabled is 'false'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceBaseUrl()"})
  void testGetServiceBaseUrl_givenSslEnabledIsFalse_thenThrowIllegalArgumentException() {
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
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getServiceBaseUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code true}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  @DisplayName("Test getServiceBaseUrl(); given Ssl (default constructor) Enabled is 'true'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceBaseUrl()"})
  void testGetServiceBaseUrl_givenSslEnabledIsTrue_thenThrowIllegalStateException() {
    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getServiceBaseUrl()}.
   * <ul>
   *   <li>Then calls {@link InetAddress#getCanonicalHostName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  @DisplayName("Test getServiceBaseUrl(); then calls getCanonicalHostName()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceBaseUrl()"})
  void testGetServiceBaseUrl_thenCallsGetCanonicalHostName() {
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
   * Test {@link DefaultApplicationFactory#getServiceBaseUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  @DisplayName("Test getServiceBaseUrl(); then return 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceBaseUrl()"})
  void testGetServiceBaseUrl_thenReturnHttpsExampleOrgExample() {
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
   * Test {@link DefaultApplicationFactory#getServiceBaseUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  @DisplayName("Test getServiceBaseUrl(); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceBaseUrl()"})
  void testGetServiceBaseUrl_thenThrowIllegalArgumentException() {
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
   * Test {@link DefaultApplicationFactory#getServiceBaseUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceBaseUrl()}
   */
  @Test
  @DisplayName("Test getServiceBaseUrl(); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceBaseUrl()"})
  void testGetServiceBaseUrl_thenThrowIllegalStateException() {
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
   * Test {@link DefaultApplicationFactory#getServicePath()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServicePath()}
   */
  @Test
  @DisplayName("Test getServicePath()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServicePath()"})
  void testGetServicePath() {
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
   * Test {@link DefaultApplicationFactory#getServicePath()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) ServicePath is {@code /}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServicePath()}
   */
  @Test
  @DisplayName("Test getServicePath(); given InstanceProperties (default constructor) ServicePath is '/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServicePath()"})
  void testGetServicePath_givenInstancePropertiesServicePathIsSlash() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl2() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl3() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) PreferIp is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl(); given InstanceProperties (default constructor) PreferIp is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl_givenInstancePropertiesPreferIpIsTrue() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) ServiceHostType is {@code IP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl(); given InstanceProperties (default constructor) ServiceHostType is 'IP'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl_givenInstancePropertiesServiceHostTypeIsIp() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) ServiceUrl is {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl(); given InstanceProperties (default constructor) ServiceUrl is 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl_givenInstancePropertiesServiceUrlIsHttpsExampleOrgExample() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code false}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl(); given Ssl (default constructor) Enabled is 'false'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl_givenSslEnabledIsFalse_thenThrowIllegalArgumentException() {
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
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code true}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl(); given Ssl (default constructor) Enabled is 'true'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl_givenSslEnabledIsTrue_thenThrowIllegalStateException() {
    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <ul>
   *   <li>Then calls {@link InetAddress#getCanonicalHostName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl(); then calls getCanonicalHostName()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl_thenCallsGetCanonicalHostName() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl(); then return 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl_thenReturnHttpsExampleOrgExample() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl(); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl_thenThrowIllegalArgumentException() {
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
   * Test {@link DefaultApplicationFactory#getManagementUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementUrl()}
   */
  @Test
  @DisplayName("Test getManagementUrl(); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementUrl()"})
  void testGetManagementUrl_thenThrowIllegalStateException() {
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl() {
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl2() {
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl3() {
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) PreferIp is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); given InstanceProperties (default constructor) PreferIp is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_givenInstancePropertiesPreferIpIsTrue() {
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) ServiceHostType is {@code IP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); given InstanceProperties (default constructor) ServiceHostType is 'IP'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_givenInstancePropertiesServiceHostTypeIsIp() {
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); given Ssl (default constructor) Enabled is 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_givenSslEnabledIsFalse() {
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
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code true}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); given Ssl (default constructor) Enabled is 'true'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_givenSslEnabledIsTrue_thenThrowIllegalStateException() {
    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Then calls {@link InetAddress#getCanonicalHostName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); then calls getCanonicalHostName()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_thenCallsGetCanonicalHostName() {
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example/}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); then return 'https://example.org/example/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_thenReturnHttpsExampleOrgExample() {
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_thenThrowIllegalArgumentException() {
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
   * Test {@link DefaultApplicationFactory#getManagementBaseUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementBaseUrl()}
   */
  @Test
  @DisplayName("Test getManagementBaseUrl(); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementBaseUrl()"})
  void testGetManagementBaseUrl_thenThrowIllegalStateException() {
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
   * Test {@link DefaultApplicationFactory#isManagementPortEqual()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#isManagementPortEqual()}
   */
  @Test
  @DisplayName("Test isManagementPortEqual()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean DefaultApplicationFactory.isManagementPortEqual()"})
  void testIsManagementPortEqual() {
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
   * Test {@link DefaultApplicationFactory#getEndpointsWebPath()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getEndpointsWebPath()}
   */
  @Test
  @DisplayName("Test getEndpointsWebPath()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getEndpointsWebPath()"})
  void testGetEndpointsWebPath() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl2() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) PreferIp is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); given InstanceProperties (default constructor) PreferIp is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_givenInstancePropertiesPreferIpIsTrue() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) ServiceBaseUrl is {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); given InstanceProperties (default constructor) ServiceBaseUrl is 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_givenInstancePropertiesServiceBaseUrlIsHttpsExampleOrgExample() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) ServiceHostType is {@code IP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); given InstanceProperties (default constructor) ServiceHostType is 'IP'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_givenInstancePropertiesServiceHostTypeIsIp() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Given {@link InstanceProperties} (default constructor) ServiceUrl is {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); given InstanceProperties (default constructor) ServiceUrl is 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_givenInstancePropertiesServiceUrlIsHttpsExampleOrgExample() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code false}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); given Ssl (default constructor) Enabled is 'false'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_givenSslEnabledIsFalse_thenThrowIllegalArgumentException() {
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
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Given {@link Ssl} (default constructor) Enabled is {@code true}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); given Ssl (default constructor) Enabled is 'true'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_givenSslEnabledIsTrue_thenThrowIllegalStateException() {
    // Arrange
    Ssl ssl = new Ssl();
    ssl.setBundle("http");
    ssl.setCertificate("http");
    ssl.setCertificatePrivateKey("http");
    ssl.setCiphers(new String[]{"http"});
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Then calls {@link InetAddress#getCanonicalHostName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); then calls getCanonicalHostName()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_thenCallsGetCanonicalHostName() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Then return {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); then return 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_thenReturnHttpsExampleOrgExample() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_thenThrowIllegalArgumentException() {
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
   * Test {@link DefaultApplicationFactory#getHealthUrl()}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthUrl()}
   */
  @Test
  @DisplayName("Test getHealthUrl(); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthUrl()"})
  void testGetHealthUrl_thenThrowIllegalStateException() {
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
   * Test {@link DefaultApplicationFactory#getMetadata()}.
   * <ul>
   *   <li>Given {@link MetadataContributor} {@link MetadataContributor#getMetadata()} return {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getMetadata()}
   */
  @Test
  @DisplayName("Test getMetadata(); given MetadataContributor getMetadata() return HashMap(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map DefaultApplicationFactory.getMetadata()"})
  void testGetMetadata_givenMetadataContributorGetMetadataReturnHashMap_thenReturnEmpty() {
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
   * Test {@link DefaultApplicationFactory#getMetadata()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getMetadata()}
   */
  @Test
  @DisplayName("Test getMetadata(); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map DefaultApplicationFactory.getMetadata()"})
  void testGetMetadata_thenThrowIllegalArgumentException() {
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
   * Test {@link DefaultApplicationFactory#getServiceHost()}.
   * <ul>
   *   <li>Then return {@code Canonical Host Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceHost()}
   */
  @Test
  @DisplayName("Test getServiceHost(); then return 'Canonical Host Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceHost()"})
  void testGetServiceHost_thenReturnCanonicalHostName() {
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
   * Test {@link DefaultApplicationFactory#getServiceHost()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getServiceHost()}
   */
  @Test
  @DisplayName("Test getServiceHost(); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getServiceHost()"})
  void testGetServiceHost_thenThrowIllegalArgumentException() {
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
   * Test {@link DefaultApplicationFactory#getManagementHost()}.
   * <ul>
   *   <li>Then return {@code Canonical Host Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementHost()}
   */
  @Test
  @DisplayName("Test getManagementHost(); then return 'Canonical Host Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementHost()"})
  void testGetManagementHost_thenReturnCanonicalHostName() {
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
   * Test {@link DefaultApplicationFactory#getManagementHost()}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getManagementHost()}
   */
  @Test
  @DisplayName("Test getManagementHost(); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getManagementHost()"})
  void testGetManagementHost_thenThrowIllegalArgumentException() {
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
   * Test {@link DefaultApplicationFactory#getLocalServerPort()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getLocalServerPort()}
   */
  @Test
  @DisplayName("Test getLocalServerPort()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.Integer DefaultApplicationFactory.getLocalServerPort()"})
  void testGetLocalServerPort() {
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
   * Test {@link DefaultApplicationFactory#getLocalManagementPort()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getLocalManagementPort()}
   */
  @Test
  @DisplayName("Test getLocalManagementPort()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.Integer DefaultApplicationFactory.getLocalManagementPort()"})
  void testGetLocalManagementPort() {
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
   * Test {@link DefaultApplicationFactory#getHealthEndpointPath()}.
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHealthEndpointPath()}
   */
  @Test
  @DisplayName("Test getHealthEndpointPath()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHealthEndpointPath()"})
  void testGetHealthEndpointPath() {
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
   * Test {@link DefaultApplicationFactory#getScheme(Ssl)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>When {@link Ssl} (default constructor) Enabled is {@code false}.</li>
   *   <li>Then return {@code http}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getScheme(Ssl)}
   */
  @Test
  @DisplayName("Test getScheme(Ssl); given 'false'; when Ssl (default constructor) Enabled is 'false'; then return 'http'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getScheme(Ssl)"})
  void testGetScheme_givenFalse_whenSslEnabledIsFalse_thenReturnHttp() {
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
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getScheme(Ssl)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link Ssl} (default constructor) Enabled is {@code true}.</li>
   *   <li>Then return {@code https}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getScheme(Ssl)}
   */
  @Test
  @DisplayName("Test getScheme(Ssl); given 'true'; when Ssl (default constructor) Enabled is 'true'; then return 'https'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getScheme(Ssl)"})
  void testGetScheme_givenTrue_whenSslEnabledIsTrue_thenReturnHttps() {
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
    ssl.setClientAuth(ClientAuth.NONE);
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
   * Test {@link DefaultApplicationFactory#getScheme(Ssl)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code http}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getScheme(Ssl)}
   */
  @Test
  @DisplayName("Test getScheme(Ssl); when 'null'; then return 'http'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getScheme(Ssl)"})
  void testGetScheme_whenNull_thenReturnHttp() {
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
   * Test {@link DefaultApplicationFactory#getHost(InetAddress)}.
   * <ul>
   *   <li>Given {@code 42 Main St}.</li>
   *   <li>Then return {@code 42 Main St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHost(InetAddress)}
   */
  @Test
  @DisplayName("Test getHost(InetAddress); given '42 Main St'; then return '42 Main St'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHost(InetAddress)"})
  void testGetHost_given42MainSt_thenReturn42MainSt() {
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
   * Test {@link DefaultApplicationFactory#getHost(InetAddress)}.
   * <ul>
   *   <li>Given {@code Canonical Host Name}.</li>
   *   <li>Then return {@code Canonical Host Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHost(InetAddress)}
   */
  @Test
  @DisplayName("Test getHost(InetAddress); given 'Canonical Host Name'; then return 'Canonical Host Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHost(InetAddress)"})
  void testGetHost_givenCanonicalHostName_thenReturnCanonicalHostName() {
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
   * Test {@link DefaultApplicationFactory#getHost(InetAddress)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultApplicationFactory#getHost(InetAddress)}
   */
  @Test
  @DisplayName("Test getHost(InetAddress); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultApplicationFactory.getHost(InetAddress)"})
  void testGetHost_thenThrowIllegalArgumentException() {
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
