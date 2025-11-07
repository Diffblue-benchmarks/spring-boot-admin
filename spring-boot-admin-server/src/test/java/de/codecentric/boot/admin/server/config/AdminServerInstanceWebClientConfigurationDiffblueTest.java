package de.codecentric.boot.admin.server.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.config.AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient.Builder;
import de.codecentric.boot.admin.server.web.client.InstanceWebClientCustomizer;
import de.codecentric.boot.admin.server.web.client.LegacyEndpointConverter;
import de.codecentric.boot.admin.server.web.client.cookies.JdkPerInstanceCookieStore;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {CookieStoreConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class AdminServerInstanceWebClientConfigurationDiffblueTest {
  @Autowired
  private CookieStoreConfiguration cookieStoreConfiguration;

  @MockBean
  private Publisher publisher;

  /**
   * Test CookieStoreConfiguration {@link CookieStoreConfiguration#cookieStore()}.
   * <ul>
   *   <li>Then return {@link JdkPerInstanceCookieStore}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieStoreConfiguration#cookieStore()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "de.codecentric.boot.admin.server.web.client.cookies.PerInstanceCookieStore CookieStoreConfiguration.cookieStore()"})
  public void testCookieStoreConfigurationCookieStore_thenReturnJdkPerInstanceCookieStore() {
    // Arrange, Act and Assert
    assertTrue(cookieStoreConfiguration.cookieStore() instanceof JdkPerInstanceCookieStore);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration {@link InstanceExchangeFiltersConfiguration#filterInstanceWebClientCustomizer(List)}.
   * <p>
   * Method under test: {@link InstanceExchangeFiltersConfiguration#filterInstanceWebClientCustomizer(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "InstanceWebClientCustomizer InstanceExchangeFiltersConfiguration.filterInstanceWebClientCustomizer(List)"})
  public void testInstanceExchangeFiltersConfigurationFilterInstanceWebClientCustomizer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceExchangeFiltersConfiguration instanceExchangeFiltersConfiguration = new InstanceExchangeFiltersConfiguration();

    // Act
    InstanceWebClientCustomizer actualFilterInstanceWebClientCustomizerResult = instanceExchangeFiltersConfiguration
        .filterInstanceWebClientCustomizer(new ArrayList<>());
    Builder instanceWebClientBuilder = mock(Builder.class);
    when(instanceWebClientBuilder.filters(Mockito.<Consumer<List<InstanceExchangeFilterFunction>>>any()))
        .thenReturn(InstanceWebClient.builder());
    actualFilterInstanceWebClientCustomizerResult.customize(instanceWebClientBuilder);

    // Assert
    verify(instanceWebClientBuilder).filters(isA(Consumer.class));
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#beansLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#beansLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LegacyEndpointConverter LegaycEndpointConvertersConfiguration.beansLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationBeansLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualBeansLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .beansLegacyEndpointConverter();

    // Assert
    assertFalse(actualBeansLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualBeansLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#configpropsLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#configpropsLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.configpropsLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationConfigpropsLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualConfigpropsLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .configpropsLegacyEndpointConverter();

    // Assert
    assertFalse(actualConfigpropsLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualConfigpropsLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#envLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#envLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LegacyEndpointConverter LegaycEndpointConvertersConfiguration.envLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationEnvLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualEnvLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .envLegacyEndpointConverter();

    // Assert
    assertFalse(actualEnvLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualEnvLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#flywayLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#flywayLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LegacyEndpointConverter LegaycEndpointConvertersConfiguration.flywayLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationFlywayLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualFlywayLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .flywayLegacyEndpointConverter();

    // Assert
    assertFalse(actualFlywayLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualFlywayLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#healthLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#healthLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LegacyEndpointConverter LegaycEndpointConvertersConfiguration.healthLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationHealthLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualHealthLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .healthLegacyEndpointConverter();

    // Assert
    assertFalse(actualHealthLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualHealthLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#httptraceLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#httptraceLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.httptraceLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationHttptraceLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualHttptraceLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .httptraceLegacyEndpointConverter();

    // Assert
    assertFalse(actualHttptraceLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualHttptraceLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#infoLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#infoLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LegacyEndpointConverter LegaycEndpointConvertersConfiguration.infoLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationInfoLegacyEndpointConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualInfoLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .infoLegacyEndpointConverter();

    // Assert
    assertNull(actualInfoLegacyEndpointConverterResult.convert(null));
    assertFalse(actualInfoLegacyEndpointConverterResult.canConvert("Endpoint Id"));
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#liquibaseLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#liquibaseLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.liquibaseLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationLiquibaseLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualLiquibaseLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .liquibaseLegacyEndpointConverter();

    // Assert
    assertFalse(actualLiquibaseLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualLiquibaseLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#mappingsLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#mappingsLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LegacyEndpointConverter LegaycEndpointConvertersConfiguration.mappingsLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationMappingsLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualMappingsLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .mappingsLegacyEndpointConverter();

    // Assert
    assertFalse(actualMappingsLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualMappingsLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#startupLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#startupLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"LegacyEndpointConverter LegaycEndpointConvertersConfiguration.startupLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationStartupLegacyEndpointConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualStartupLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .startupLegacyEndpointConverter();

    // Assert
    assertNull(actualStartupLegacyEndpointConverterResult.convert(null));
    assertFalse(actualStartupLegacyEndpointConverterResult.canConvert("Endpoint Id"));
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link LegaycEndpointConvertersConfiguration#threaddumpLegacyEndpointConverter()}.
   * <p>
   * Method under test: {@link LegaycEndpointConvertersConfiguration#threaddumpLegacyEndpointConverter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.threaddumpLegacyEndpointConverter()"})
  public void testLegaycEndpointConvertersConfigurationThreaddumpLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualThreaddumpLegacyEndpointConverterResult = (new LegaycEndpointConvertersConfiguration())
        .threaddumpLegacyEndpointConverter();

    // Assert
    assertFalse(actualThreaddumpLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier
        .create(actualThreaddumpLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }
}
