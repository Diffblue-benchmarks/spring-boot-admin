package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.client.config.SpringBootAdminClientAutoConfiguration.BlockingRegistrationClientConfig;
import de.codecentric.boot.admin.client.config.SpringBootAdminClientAutoConfiguration.ReactiveRegistrationClientConfig;
import de.codecentric.boot.admin.client.registration.ApplicationFactory;
import de.codecentric.boot.admin.client.registration.ApplicationRegistrator;
import de.codecentric.boot.admin.client.registration.BlockingRegistrationClient;
import de.codecentric.boot.admin.client.registration.DefaultApplicationRegistrator;
import de.codecentric.boot.admin.client.registration.ReactiveRegistrationClient;
import de.codecentric.boot.admin.client.registration.RegistrationClient;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

class SpringBootAdminClientAutoConfigurationDiffblueTest {
  /**
   * Test BlockingRegistrationClientConfig {@link
   * BlockingRegistrationClientConfig#registrationClient(ClientProperties)}.
   *
   * <p>Method under test: {@link
   * BlockingRegistrationClientConfig#registrationClient(ClientProperties)}
   */
  @Test
  @DisplayName("Test BlockingRegistrationClientConfig registrationClient(ClientProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RegistrationClient BlockingRegistrationClientConfig.registrationClient(ClientProperties)"
  })
  void testBlockingRegistrationClientConfigRegistrationClient() {
    // Arrange
    BlockingRegistrationClientConfig blockingRegistrationClientConfig =
        new BlockingRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setConnectTimeout(Duration.ofSeconds(1L));
    client.setEnabled(true);
    client.setPeriod(Duration.ofSeconds(1L));
    client.setReadTimeout(Duration.ofSeconds(1L));
    client.setRegisterOnce(true);
    client.setUrl(new String[] {"https://example.org/example"});
    client.setUsername("Client");
    client.setPassword("Client");

    // Act and Assert
    assertTrue(
        blockingRegistrationClientConfig.registrationClient(client)
            instanceof BlockingRegistrationClient);
  }

  /**
   * Test BlockingRegistrationClientConfig {@link
   * BlockingRegistrationClientConfig#registrationClient(ClientProperties)}.
   *
   * <p>Method under test: {@link
   * BlockingRegistrationClientConfig#registrationClient(ClientProperties)}
   */
  @Test
  @DisplayName("Test BlockingRegistrationClientConfig registrationClient(ClientProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RegistrationClient BlockingRegistrationClientConfig.registrationClient(ClientProperties)"
  })
  void testBlockingRegistrationClientConfigRegistrationClient2() {
    // Arrange
    BlockingRegistrationClientConfig blockingRegistrationClientConfig =
        new BlockingRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setConnectTimeout(Duration.ofSeconds(1L));
    client.setEnabled(true);
    client.setPeriod(Duration.ofSeconds(1L));
    client.setReadTimeout(Duration.ofSeconds(1L));
    client.setRegisterOnce(true);
    client.setUrl(new String[] {"https://example.org/example"});
    client.setUsername("Client");
    client.setPassword(null);

    // Act and Assert
    assertTrue(
        blockingRegistrationClientConfig.registrationClient(client)
            instanceof BlockingRegistrationClient);
  }

  /**
   * Test BlockingRegistrationClientConfig {@link
   * BlockingRegistrationClientConfig#registrationClient(ClientProperties)}.
   *
   * <p>Method under test: {@link
   * BlockingRegistrationClientConfig#registrationClient(ClientProperties)}
   */
  @Test
  @DisplayName("Test BlockingRegistrationClientConfig registrationClient(ClientProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RegistrationClient BlockingRegistrationClientConfig.registrationClient(ClientProperties)"
  })
  void testBlockingRegistrationClientConfigRegistrationClient3() {
    // Arrange
    BlockingRegistrationClientConfig blockingRegistrationClientConfig =
        new BlockingRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setConnectTimeout(Duration.ofSeconds(1L));
    client.setEnabled(true);
    client.setPeriod(Duration.ofSeconds(1L));
    client.setReadTimeout(Duration.ofSeconds(1L));
    client.setRegisterOnce(true);
    client.setUrl(new String[] {"https://example.org/example"});
    client.setUsername(null);
    client.setPassword("Client");

    // Act and Assert
    assertTrue(
        blockingRegistrationClientConfig.registrationClient(client)
            instanceof BlockingRegistrationClient);
  }

  /**
   * Test ReactiveRegistrationClientConfig {@link
   * ReactiveRegistrationClientConfig#registrationClient(ClientProperties, Builder)}.
   *
   * <p>Method under test: {@link
   * ReactiveRegistrationClientConfig#registrationClient(ClientProperties, Builder)}
   */
  @Test
  @DisplayName(
      "Test ReactiveRegistrationClientConfig registrationClient(ClientProperties, Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RegistrationClient ReactiveRegistrationClientConfig.registrationClient(ClientProperties, Builder)"
  })
  void testReactiveRegistrationClientConfigRegistrationClient() {
    // Arrange
    ReactiveRegistrationClientConfig reactiveRegistrationClientConfig =
        new ReactiveRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setConnectTimeout(Duration.ofSeconds(1L));
    client.setEnabled(true);
    client.setPassword(null);
    client.setPeriod(Duration.ofSeconds(1L));
    client.setReadTimeout(Duration.ofSeconds(1L));
    client.setRegisterOnce(true);
    client.setUrl(new String[] {"https://example.org/example"});
    client.setUsername(null);

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    // Act
    RegistrationClient actualRegistrationClientResult =
        reactiveRegistrationClientConfig.registrationClient(client, webClient);

    // Assert
    verify(webClient).build();
    assertTrue(actualRegistrationClientResult instanceof ReactiveRegistrationClient);
  }

  /**
   * Test ReactiveRegistrationClientConfig {@link
   * ReactiveRegistrationClientConfig#registrationClient(ClientProperties, Builder)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ReactiveRegistrationClientConfig#registrationClient(ClientProperties, Builder)}
   */
  @Test
  @DisplayName(
      "Test ReactiveRegistrationClientConfig registrationClient(ClientProperties, Builder); given 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RegistrationClient ReactiveRegistrationClientConfig.registrationClient(ClientProperties, Builder)"
  })
  void testReactiveRegistrationClientConfigRegistrationClient_givenNull() {
    // Arrange
    ReactiveRegistrationClientConfig reactiveRegistrationClientConfig =
        new ReactiveRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setConnectTimeout(Duration.ofSeconds(1L));
    client.setEnabled(true);
    client.setPassword(null);
    client.setPeriod(Duration.ofSeconds(1L));
    client.setReadTimeout(Duration.ofSeconds(1L));
    client.setRegisterOnce(true);
    client.setUrl(new String[] {"https://example.org/example"});
    client.setUsername("janedoe");

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    // Act
    RegistrationClient actualRegistrationClientResult =
        reactiveRegistrationClientConfig.registrationClient(client, webClient);

    // Assert
    verify(webClient).build();
    assertTrue(actualRegistrationClientResult instanceof ReactiveRegistrationClient);
  }

  /**
   * Test ReactiveRegistrationClientConfig {@link
   * ReactiveRegistrationClientConfig#registrationClient(ClientProperties, Builder)}.
   *
   * <ul>
   *   <li>Then calls {@link Builder#filter(ExchangeFilterFunction)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ReactiveRegistrationClientConfig#registrationClient(ClientProperties, Builder)}
   */
  @Test
  @DisplayName(
      "Test ReactiveRegistrationClientConfig registrationClient(ClientProperties, Builder); then calls filter(ExchangeFilterFunction)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RegistrationClient ReactiveRegistrationClientConfig.registrationClient(ClientProperties, Builder)"
  })
  void testReactiveRegistrationClientConfigRegistrationClient_thenCallsFilter() {
    // Arrange
    ReactiveRegistrationClientConfig reactiveRegistrationClientConfig =
        new ReactiveRegistrationClientConfig();

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setConnectTimeout(Duration.ofSeconds(1L));
    client.setEnabled(true);
    client.setPassword("iloveyou");
    client.setPeriod(Duration.ofSeconds(1L));
    client.setReadTimeout(Duration.ofSeconds(1L));
    client.setRegisterOnce(true);
    client.setUrl(new String[] {"https://example.org/example"});
    client.setUsername("janedoe");

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));

    Builder webClient = mock(Builder.class);
    when(webClient.filter(Mockito.<ExchangeFilterFunction>any())).thenReturn(builder);

    // Act
    RegistrationClient actualRegistrationClientResult =
        reactiveRegistrationClientConfig.registrationClient(client, webClient);

    // Assert
    verify(builder).build();
    verify(webClient).filter(isA(ExchangeFilterFunction.class));
    assertTrue(actualRegistrationClientResult instanceof ReactiveRegistrationClient);
  }

  /**
   * Test {@link SpringBootAdminClientAutoConfiguration#registrator(RegistrationClient,
   * ClientProperties, ApplicationFactory)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When {@link ClientProperties} (default constructor) RegisterOnce is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringBootAdminClientAutoConfiguration#registrator(RegistrationClient, ClientProperties,
   * ApplicationFactory)}
   */
  @Test
  @DisplayName(
      "Test registrator(RegistrationClient, ClientProperties, ApplicationFactory); given 'false'; when ClientProperties (default constructor) RegisterOnce is 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ApplicationRegistrator SpringBootAdminClientAutoConfiguration.registrator(RegistrationClient, ClientProperties, ApplicationFactory)"
  })
  void testRegistrator_givenFalse_whenClientPropertiesRegisterOnceIsFalse() {
    // Arrange
    SpringBootAdminClientAutoConfiguration springBootAdminClientAutoConfiguration =
        new SpringBootAdminClientAutoConfiguration();
    BlockingRegistrationClient registrationClient =
        new BlockingRegistrationClient(mock(RestTemplate.class));

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setConnectTimeout(Duration.ofSeconds(1L));
    client.setEnabled(true);
    client.setPassword("iloveyou");
    client.setPeriod(Duration.ofSeconds(1L));
    client.setReadTimeout(Duration.ofSeconds(1L));
    client.setRegisterOnce(false);
    client.setUrl(new String[] {"https://example.org/example"});
    client.setUsername("janedoe");

    // Act
    ApplicationRegistrator actualRegistratorResult =
        springBootAdminClientAutoConfiguration.registrator(
            registrationClient, client, mock(ApplicationFactory.class));

    // Assert
    assertTrue(actualRegistratorResult instanceof DefaultApplicationRegistrator);
    assertNull(actualRegistratorResult.getRegisteredId());
  }

  /**
   * Test {@link SpringBootAdminClientAutoConfiguration#registrator(RegistrationClient,
   * ClientProperties, ApplicationFactory)}.
   *
   * <ul>
   *   <li>When {@link ClientProperties} (default constructor) RegisterOnce is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringBootAdminClientAutoConfiguration#registrator(RegistrationClient, ClientProperties,
   * ApplicationFactory)}
   */
  @Test
  @DisplayName(
      "Test registrator(RegistrationClient, ClientProperties, ApplicationFactory); when ClientProperties (default constructor) RegisterOnce is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ApplicationRegistrator SpringBootAdminClientAutoConfiguration.registrator(RegistrationClient, ClientProperties, ApplicationFactory)"
  })
  void testRegistrator_whenClientPropertiesRegisterOnceIsTrue() {
    // Arrange
    SpringBootAdminClientAutoConfiguration springBootAdminClientAutoConfiguration =
        new SpringBootAdminClientAutoConfiguration();
    BlockingRegistrationClient registrationClient =
        new BlockingRegistrationClient(mock(RestTemplate.class));

    ClientProperties client = new ClientProperties();
    client.setApiPath("Api Path");
    client.setConnectTimeout(Duration.ofSeconds(1L));
    client.setEnabled(true);
    client.setPassword("iloveyou");
    client.setPeriod(Duration.ofSeconds(1L));
    client.setReadTimeout(Duration.ofSeconds(1L));
    client.setRegisterOnce(true);
    client.setUrl(new String[] {"https://example.org/example"});
    client.setUsername("janedoe");

    // Act
    ApplicationRegistrator actualRegistratorResult =
        springBootAdminClientAutoConfiguration.registrator(
            registrationClient, client, mock(ApplicationFactory.class));

    // Assert
    assertTrue(actualRegistratorResult instanceof DefaultApplicationRegistrator);
    assertNull(actualRegistratorResult.getRegisteredId());
  }

  /**
   * Test {@link SpringBootAdminClientAutoConfiguration#startupDateMetadataContributor()}.
   *
   * <p>Method under test: {@link
   * SpringBootAdminClientAutoConfiguration#startupDateMetadataContributor()}
   */
  @Test
  @DisplayName("Test startupDateMetadataContributor()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.client.registration.metadata.StartupDateMetadataContributor SpringBootAdminClientAutoConfiguration.startupDateMetadataContributor()"
  })
  void testStartupDateMetadataContributor() {
    // Arrange, Act and Assert
    assertEquals(
        1,
        new SpringBootAdminClientAutoConfiguration()
            .startupDateMetadataContributor()
            .getMetadata()
            .size());
  }
}
