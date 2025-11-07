package de.codecentric.boot.admin.server.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.web.client.BasicAuthHttpHeaderProvider;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import de.codecentric.boot.admin.server.web.client.InstanceWebClientCustomizer;
import de.codecentric.boot.admin.server.web.client.LegacyEndpointConverter;
import de.codecentric.boot.admin.server.web.client.cookies.JdkPerInstanceCookieStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration.class,
    AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration.class, AdminServerProperties.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class AdminServerInstanceWebClientConfigurationDiffblueTest {
  @Autowired
  private AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration httpHeadersProviderConfiguration;

  @Autowired
  private AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration cookieStoreConfiguration;

  @MockBean
  private Publisher publisher;

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration#cookieStore()}
   */
  @Test
  public void testCookieStoreConfigurationCookieStore() {
    // Arrange, Act and Assert
    assertTrue(cookieStoreConfiguration.cookieStore() instanceof JdkPerInstanceCookieStore);
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}
   */
  @Test
  public void testHttpHeadersProviderConfigurationBasicAuthHttpHeadersProvider() {
    // Arrange
    AdminServerProperties.ServerProperties server = mock(AdminServerProperties.ServerProperties.class);
    doNothing().when(server).setEnabled(anyBoolean());
    server.setEnabled(true);

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setServer(server);

    // Act
    httpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(adminServerProperties);

    // Assert
    verify(server).setEnabled(eq(true));
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}
   */
  @Test
  public void testHttpHeadersProviderConfigurationBasicAuthHttpHeadersProvider2() {
    // Arrange
    AdminServerProperties.ServerProperties server = mock(AdminServerProperties.ServerProperties.class);
    doNothing().when(server).setEnabled(anyBoolean());
    server.setEnabled(true);
    AdminServerProperties.InstanceAuthProperties instanceAuth = mock(
        AdminServerProperties.InstanceAuthProperties.class);
    when(instanceAuth.isEnabled()).thenReturn(false);
    doNothing().when(instanceAuth).setDefaultPassword(Mockito.<String>any());
    doNothing().when(instanceAuth).setDefaultUserName(Mockito.<String>any());
    doNothing().when(instanceAuth).setEnabled(anyBoolean());
    doNothing().when(instanceAuth)
        .setServiceMap(Mockito.<Map<String, BasicAuthHttpHeaderProvider.InstanceCredentials>>any());
    instanceAuth.setDefaultPassword("iloveyou");
    instanceAuth.setDefaultUserName("janedoe");
    instanceAuth.setEnabled(true);
    instanceAuth.setServiceMap(new HashMap<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setInstanceAuth(instanceAuth);
    adminServerProperties.setServer(server);

    // Act
    httpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(adminServerProperties);

    // Assert
    verify(instanceAuth).isEnabled();
    verify(instanceAuth).setDefaultPassword(eq("iloveyou"));
    verify(instanceAuth).setDefaultUserName(eq("janedoe"));
    verify(instanceAuth).setEnabled(eq(true));
    verify(instanceAuth).setServiceMap(isA(Map.class));
    verify(server).setEnabled(eq(true));
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration#filterInstanceWebClientCustomizer(List)}
   */
  @Test
  public void testInstanceExchangeFiltersConfigurationFilterInstanceWebClientCustomizer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration instanceExchangeFiltersConfiguration = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration();

    // Act
    InstanceWebClientCustomizer actualFilterInstanceWebClientCustomizerResult = instanceExchangeFiltersConfiguration
        .filterInstanceWebClientCustomizer(new ArrayList<>());
    InstanceWebClient.Builder instanceWebClientBuilder = mock(InstanceWebClient.Builder.class);
    when(instanceWebClientBuilder.filters(Mockito.<Consumer<List<InstanceExchangeFilterFunction>>>any()))
        .thenReturn(InstanceWebClient.builder());
    actualFilterInstanceWebClientCustomizerResult.customize(instanceWebClientBuilder);

    // Assert that nothing has changed
    verify(instanceWebClientBuilder).filters(isA(Consumer.class));
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#beansLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationBeansLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualBeansLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .beansLegacyEndpointConverter();

    // Assert
    assertFalse(actualBeansLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualBeansLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#configpropsLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationConfigpropsLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualConfigpropsLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .configpropsLegacyEndpointConverter();

    // Assert
    assertFalse(actualConfigpropsLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualConfigpropsLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#envLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationEnvLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualEnvLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .envLegacyEndpointConverter();

    // Assert
    assertFalse(actualEnvLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualEnvLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#flywayLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationFlywayLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualFlywayLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .flywayLegacyEndpointConverter();

    // Assert
    assertFalse(actualFlywayLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualFlywayLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#healthLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationHealthLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualHealthLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .healthLegacyEndpointConverter();

    // Assert
    assertFalse(actualHealthLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualHealthLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#httptraceLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationHttptraceLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualHttptraceLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .httptraceLegacyEndpointConverter();

    // Assert
    assertFalse(actualHttptraceLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualHttptraceLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#infoLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationInfoLegacyEndpointConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualInfoLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .infoLegacyEndpointConverter();

    // Assert
    assertNull(actualInfoLegacyEndpointConverterResult.convert(null));
    assertFalse(actualInfoLegacyEndpointConverterResult.canConvert("Endpoint Id"));
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#liquibaseLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationLiquibaseLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualLiquibaseLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .liquibaseLegacyEndpointConverter();

    // Assert
    assertFalse(actualLiquibaseLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualLiquibaseLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#mappingsLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationMappingsLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualMappingsLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .mappingsLegacyEndpointConverter();

    // Assert
    assertFalse(actualMappingsLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualMappingsLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#startupLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationStartupLegacyEndpointConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualStartupLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .startupLegacyEndpointConverter();

    // Assert
    assertNull(actualStartupLegacyEndpointConverterResult.convert(null));
    assertFalse(actualStartupLegacyEndpointConverterResult.canConvert("Endpoint Id"));
  }

  /**
   * Method under test:
   * {@link AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration#threaddumpLegacyEndpointConverter()}
   */
  @Test
  public void testLegaycEndpointConvertersConfigurationThreaddumpLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LegacyEndpointConverter actualThreaddumpLegacyEndpointConverterResult = (new AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration())
        .threaddumpLegacyEndpointConverter();

    // Assert
    assertFalse(actualThreaddumpLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    StepVerifier.FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualThreaddumpLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }
}
