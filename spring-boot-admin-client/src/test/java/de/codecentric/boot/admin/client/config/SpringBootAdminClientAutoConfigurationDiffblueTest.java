package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.client.registration.ApplicationFactory;
import de.codecentric.boot.admin.client.registration.ApplicationRegistrator;
import de.codecentric.boot.admin.client.registration.BlockingRegistrationClient;
import de.codecentric.boot.admin.client.registration.DefaultApplicationRegistrator;
import de.codecentric.boot.admin.client.registration.ReactiveRegistrationClient;
import de.codecentric.boot.admin.client.registration.RegistrationClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

class SpringBootAdminClientAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig#registrationClient(ClientProperties)}
   */
  @Test
  void testBlockingRegistrationClientConfigRegistrationClient() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig blockingRegistrationClientConfig = new SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setEnabled(true);
    client.setPassword("iloveyou");
    client.setRegisterOnce(true);
    client.setUrl(new String[]{"https://example.org/example"});
    client.setUsername("janedoe");

    // Act and Assert
    assertTrue(blockingRegistrationClientConfig.registrationClient(client) instanceof BlockingRegistrationClient);
  }

  /**
   * Method under test:
   * {@link SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig#registrationClient(ClientProperties)}
   */
  @Test
  void testBlockingRegistrationClientConfigRegistrationClient2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig blockingRegistrationClientConfig = new SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setEnabled(true);
    client.setRegisterOnce(true);
    client.setUrl(new String[]{"https://example.org/example"});
    client.setUsername(null);
    client.setPassword(null);

    // Act and Assert
    assertTrue(blockingRegistrationClientConfig.registrationClient(client) instanceof BlockingRegistrationClient);
  }

  /**
   * Method under test:
   * {@link SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig#registrationClient(ClientProperties)}
   */
  @Test
  void testBlockingRegistrationClientConfigRegistrationClient3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig blockingRegistrationClientConfig = new SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setEnabled(true);
    client.setRegisterOnce(true);
    client.setUrl(new String[]{"https://example.org/example"});
    client.setUsername("Client");
    client.setPassword(null);

    // Act and Assert
    assertTrue(blockingRegistrationClientConfig.registrationClient(client) instanceof BlockingRegistrationClient);
  }

  /**
   * Method under test:
   * {@link SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig#registrationClient(ClientProperties, WebClient.Builder)}
   */
  @Test
  void testReactiveRegistrationClientConfigRegistrationClient() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig reactiveRegistrationClientConfig = new SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setEnabled(true);
    client.setPassword("iloveyou");
    client.setRegisterOnce(true);
    client.setUrl(new String[]{"https://example.org/example"});
    client.setUsername("janedoe");
    WebClient.Builder builder = mock(WebClient.Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    WebClient.Builder webClient = mock(WebClient.Builder.class);
    when(webClient.filter(Mockito.<ExchangeFilterFunction>any())).thenReturn(builder);

    // Act
    RegistrationClient actualRegistrationClientResult = reactiveRegistrationClientConfig.registrationClient(client,
        webClient);

    // Assert
    verify(builder).build();
    verify(webClient).filter(isA(ExchangeFilterFunction.class));
    assertTrue(actualRegistrationClientResult instanceof ReactiveRegistrationClient);
  }

  /**
   * Method under test:
   * {@link SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig#registrationClient(ClientProperties, WebClient.Builder)}
   */
  @Test
  void testReactiveRegistrationClientConfigRegistrationClient2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig reactiveRegistrationClientConfig = new SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setEnabled(true);
    client.setPassword(null);
    client.setRegisterOnce(true);
    client.setUrl(new String[]{"https://example.org/example"});
    client.setUsername("janedoe");
    WebClient.Builder webClient = mock(WebClient.Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    // Act
    RegistrationClient actualRegistrationClientResult = reactiveRegistrationClientConfig.registrationClient(client,
        webClient);

    // Assert
    verify(webClient).build();
    assertTrue(actualRegistrationClientResult instanceof ReactiveRegistrationClient);
  }

  /**
   * Method under test:
   * {@link SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig#registrationClient(ClientProperties, WebClient.Builder)}
   */
  @Test
  void testReactiveRegistrationClientConfigRegistrationClient3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig reactiveRegistrationClientConfig = new SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setEnabled(true);
    client.setPassword(null);
    client.setRegisterOnce(true);
    client.setUrl(new String[]{"https://example.org/example"});
    client.setUsername(null);
    WebClient.Builder webClient = mock(WebClient.Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    // Act
    RegistrationClient actualRegistrationClientResult = reactiveRegistrationClientConfig.registrationClient(client,
        webClient);

    // Assert
    verify(webClient).build();
    assertTrue(actualRegistrationClientResult instanceof ReactiveRegistrationClient);
  }

  /**
   * Method under test:
   * {@link SpringBootAdminClientAutoConfiguration#registrator(RegistrationClient, ClientProperties, ApplicationFactory)}
   */
  @Test
  void testRegistrator() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SpringBootAdminClientAutoConfiguration springBootAdminClientAutoConfiguration = new SpringBootAdminClientAutoConfiguration();
    BlockingRegistrationClient registrationClient = new BlockingRegistrationClient(mock(RestTemplate.class));

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setEnabled(true);
    client.setPassword("iloveyou");
    client.setRegisterOnce(true);
    client.setUrl(new String[]{"https://example.org/example"});
    client.setUsername("janedoe");

    // Act
    ApplicationRegistrator actualRegistratorResult = springBootAdminClientAutoConfiguration
        .registrator(registrationClient, client, mock(ApplicationFactory.class));

    // Assert
    assertTrue(actualRegistratorResult instanceof DefaultApplicationRegistrator);
    assertNull(actualRegistratorResult.getRegisteredId());
  }

  /**
   * Method under test:
   * {@link SpringBootAdminClientAutoConfiguration#registrator(RegistrationClient, ClientProperties, ApplicationFactory)}
   */
  @Test
  void testRegistrator2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SpringBootAdminClientAutoConfiguration springBootAdminClientAutoConfiguration = new SpringBootAdminClientAutoConfiguration();
    BlockingRegistrationClient registrationClient = new BlockingRegistrationClient(mock(RestTemplate.class));

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setEnabled(true);
    client.setPassword("iloveyou");
    client.setRegisterOnce(false);
    client.setUrl(new String[]{"https://example.org/example"});
    client.setUsername("janedoe");

    // Act
    ApplicationRegistrator actualRegistratorResult = springBootAdminClientAutoConfiguration
        .registrator(registrationClient, client, mock(ApplicationFactory.class));

    // Assert
    assertTrue(actualRegistratorResult instanceof DefaultApplicationRegistrator);
    assertNull(actualRegistratorResult.getRegisteredId());
  }

  /**
   * Method under test:
   * {@link SpringBootAdminClientAutoConfiguration#startupDateMetadataContributor()}
   */
  @Test
  void testStartupDateMetadataContributor() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertEquals(1,
        (new SpringBootAdminClientAutoConfiguration()).startupDateMetadataContributor().getMetadata().size());
  }
}
