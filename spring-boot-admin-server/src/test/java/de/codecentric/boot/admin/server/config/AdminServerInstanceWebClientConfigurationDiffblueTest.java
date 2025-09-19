package de.codecentric.boot.admin.server.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.config.AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerInstanceWebClientConfiguration.LegaycEndpointConvertersConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerProperties.InstanceAuthProperties;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.web.client.BasicAuthHttpHeaderProvider;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient.Builder;
import de.codecentric.boot.admin.server.web.client.InstanceWebClientCustomizer;
import de.codecentric.boot.admin.server.web.client.LegacyEndpointConverter;
import de.codecentric.boot.admin.server.web.client.LegacyEndpointConverters;
import de.codecentric.boot.admin.server.web.client.cookies.JdkPerInstanceCookieStore;
import de.codecentric.boot.admin.server.web.client.cookies.PerInstanceCookieStore;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(
    classes = {
      DefaultInstanceExchangeFiltersConfiguration.class,
      AdminServerProperties.class,
      InstanceExchangeFiltersConfiguration.class,
      HttpHeadersProviderConfiguration.class,
      CookieStoreConfiguration.class
    })
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class AdminServerInstanceWebClientConfigurationDiffblueTest {
  @Autowired private CookieStoreConfiguration cookieStoreConfiguration;

  @Autowired
  private DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration;

  @Autowired private HttpHeadersProviderConfiguration httpHeadersProviderConfiguration;

  @Autowired private InstanceExchangeFiltersConfiguration instanceExchangeFiltersConfiguration;

  @MockitoBean private PerInstanceCookieStore perInstanceCookieStore;

  @MockitoBean private Publisher publisher;

  /**
   * Test CookieStoreConfiguration {@link CookieStoreConfiguration#cookieStore()}.
   *
   * <ul>
   *   <li>Then return {@link JdkPerInstanceCookieStore}.
   * </ul>
   *
   * <p>Method under test: {@link CookieStoreConfiguration#cookieStore()}
   */
  @Test
  @DisplayName("Test CookieStoreConfiguration cookieStore(); then return JdkPerInstanceCookieStore")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"PerInstanceCookieStore CookieStoreConfiguration.cookieStore()"})
  void testCookieStoreConfigurationCookieStore_thenReturnJdkPerInstanceCookieStore() {
    // Arrange, Act and Assert
    assertTrue(cookieStoreConfiguration.cookieStore() instanceof JdkPerInstanceCookieStore);
  }

  /**
   * Test HttpHeadersProviderConfiguration {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test HttpHeadersProviderConfiguration basicAuthHttpHeadersProvider(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BasicAuthHttpHeaderProvider HttpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(AdminServerProperties)"
  })
  void testHttpHeadersProviderConfigurationBasicAuthHttpHeadersProvider() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    httpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(adminServerProperties);

    // Assert that nothing has changed
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
  }

  /**
   * Test HttpHeadersProviderConfiguration {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test HttpHeadersProviderConfiguration basicAuthHttpHeadersProvider(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BasicAuthHttpHeaderProvider HttpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(AdminServerProperties)"
  })
  void testHttpHeadersProviderConfigurationBasicAuthHttpHeadersProvider2() {
    // Arrange
    InstanceAuthProperties instanceAuth = new InstanceAuthProperties();
    instanceAuth.setDefaultPassword("iloveyou");
    instanceAuth.setDefaultUserName("janedoe");
    instanceAuth.setServiceMap(new HashMap<>());
    instanceAuth.setEnabled(false);

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setInstanceAuth(instanceAuth);

    // Act
    httpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(adminServerProperties);

    // Assert that nothing has changed
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
    assertSame(instanceAuth, adminServerProperties.getInstanceAuth());
  }

  /**
   * Test HttpHeadersProviderConfiguration {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test HttpHeadersProviderConfiguration basicAuthHttpHeadersProvider(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BasicAuthHttpHeaderProvider HttpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(AdminServerProperties)"
  })
  void testHttpHeadersProviderConfigurationBasicAuthHttpHeadersProvider3() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    BasicAuthHttpHeaderProvider actualBasicAuthHttpHeadersProviderResult =
        httpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(adminServerProperties);
    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    HttpHeaders actualHeaders = actualBasicAuthHttpHeadersProviderResult.getHeaders(instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
    assertTrue(actualHeaders.isEmpty());
  }

  /**
   * Test HttpHeadersProviderConfiguration {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test HttpHeadersProviderConfiguration basicAuthHttpHeadersProvider(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BasicAuthHttpHeaderProvider HttpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(AdminServerProperties)"
  })
  void testHttpHeadersProviderConfigurationBasicAuthHttpHeadersProvider4() {
    // Arrange
    InstanceAuthProperties instanceAuth = new InstanceAuthProperties();
    instanceAuth.setDefaultPassword("iloveyou");
    instanceAuth.setDefaultUserName("janedoe");
    instanceAuth.setEnabled(true);
    instanceAuth.setServiceMap(new HashMap<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setInstanceAuth(instanceAuth);

    // Act
    BasicAuthHttpHeaderProvider actualBasicAuthHttpHeadersProviderResult =
        httpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(adminServerProperties);
    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    HttpHeaders actualHeaders = actualBasicAuthHttpHeadersProviderResult.getHeaders(instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(1, actualHeaders.size());
    assertEquals(1, actualHeaders.get(HttpHeaders.AUTHORIZATION).size());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
    assertSame(instanceAuth, adminServerProperties.getInstanceAuth());
  }

  /**
   * Test HttpHeadersProviderConfiguration {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * HttpHeadersProviderConfiguration#basicAuthHttpHeadersProvider(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test HttpHeadersProviderConfiguration basicAuthHttpHeadersProvider(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BasicAuthHttpHeaderProvider HttpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(AdminServerProperties)"
  })
  void testHttpHeadersProviderConfigurationBasicAuthHttpHeadersProvider5() {
    // Arrange
    InstanceAuthProperties instanceAuth = new InstanceAuthProperties();
    instanceAuth.setDefaultPassword("");
    instanceAuth.setDefaultUserName("janedoe");
    instanceAuth.setEnabled(true);
    instanceAuth.setServiceMap(new HashMap<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setInstanceAuth(instanceAuth);

    // Act
    BasicAuthHttpHeaderProvider actualBasicAuthHttpHeadersProviderResult =
        httpHeadersProviderConfiguration.basicAuthHttpHeadersProvider(adminServerProperties);
    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    HttpHeaders actualHeaders = actualBasicAuthHttpHeadersProviderResult.getHeaders(instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
    assertTrue(actualHeaders.isEmpty());
    assertSame(instanceAuth, adminServerProperties.getInstanceAuth());
  }

  /**
   * Test InstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration#filterInstanceWebClientCustomizer(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration#filterInstanceWebClientCustomizer(List)}
   */
  @Test
  @DisplayName("Test InstanceExchangeFiltersConfiguration filterInstanceWebClientCustomizer(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceWebClientCustomizer InstanceExchangeFiltersConfiguration.filterInstanceWebClientCustomizer(List)"
  })
  void testInstanceExchangeFiltersConfigurationFilterInstanceWebClientCustomizer() {
    // Arrange and Act
    InstanceWebClientCustomizer actualFilterInstanceWebClientCustomizerResult =
        instanceExchangeFiltersConfiguration.filterInstanceWebClientCustomizer(new ArrayList<>());
    Builder instanceWebClientBuilder = mock(Builder.class);
    when(instanceWebClientBuilder.filters(
            Mockito.<Consumer<List<InstanceExchangeFilterFunction>>>any()))
        .thenReturn(InstanceWebClient.builder());
    actualFilterInstanceWebClientCustomizerResult.customize(instanceWebClientBuilder);

    // Assert
    verify(instanceWebClientBuilder).filters(isA(Consumer.class));
  }

  /**
   * Test InstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration#filterInstanceWebClientCustomizer(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration#filterInstanceWebClientCustomizer(List)}
   */
  @Test
  @DisplayName("Test InstanceExchangeFiltersConfiguration filterInstanceWebClientCustomizer(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceWebClientCustomizer InstanceExchangeFiltersConfiguration.filterInstanceWebClientCustomizer(List)"
  })
  void testInstanceExchangeFiltersConfigurationFilterInstanceWebClientCustomizer2() {
    // Arrange
    ArrayList<InstanceExchangeFilterFunction> filters = new ArrayList<>();
    filters.add(mock(InstanceExchangeFilterFunction.class));

    // Act
    InstanceWebClientCustomizer actualFilterInstanceWebClientCustomizerResult =
        instanceExchangeFiltersConfiguration.filterInstanceWebClientCustomizer(filters);
    Builder instanceWebClientBuilder = mock(Builder.class);
    when(instanceWebClientBuilder.filters(
            Mockito.<Consumer<List<InstanceExchangeFilterFunction>>>any()))
        .thenReturn(InstanceWebClient.builder());
    actualFilterInstanceWebClientCustomizerResult.customize(instanceWebClientBuilder);

    // Assert
    verify(instanceWebClientBuilder).filters(isA(Consumer.class));
  }

  /**
   * Test InstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration#filterInstanceWebClientCustomizer(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration#filterInstanceWebClientCustomizer(List)}
   */
  @Test
  @DisplayName("Test InstanceExchangeFiltersConfiguration filterInstanceWebClientCustomizer(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceWebClientCustomizer InstanceExchangeFiltersConfiguration.filterInstanceWebClientCustomizer(List)"
  })
  void testInstanceExchangeFiltersConfigurationFilterInstanceWebClientCustomizer3() {
    // Arrange
    ArrayList<InstanceExchangeFilterFunction> filters = new ArrayList<>();
    filters.add(mock(InstanceExchangeFilterFunction.class));
    filters.add(mock(InstanceExchangeFilterFunction.class));

    // Act
    InstanceWebClientCustomizer actualFilterInstanceWebClientCustomizerResult =
        instanceExchangeFiltersConfiguration.filterInstanceWebClientCustomizer(filters);
    Builder instanceWebClientBuilder = mock(Builder.class);
    when(instanceWebClientBuilder.filters(
            Mockito.<Consumer<List<InstanceExchangeFilterFunction>>>any()))
        .thenReturn(InstanceWebClient.builder());
    actualFilterInstanceWebClientCustomizerResult.customize(instanceWebClientBuilder);

    // Assert
    verify(instanceWebClientBuilder).filters(isA(Consumer.class));
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#addHeadersInstanceExchangeFilter(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#addHeadersInstanceExchangeFilter(List)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration addHeadersInstanceExchangeFilter(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.addHeadersInstanceExchangeFilter(List)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationAddHeadersInstanceExchangeFilter() {
    // Arrange
    ArrayList<HttpHeadersProvider> headersProviders = new ArrayList<>();

    // Act
    defaultInstanceExchangeFiltersConfiguration.addHeadersInstanceExchangeFilter(headersProviders);

    // Assert that nothing has changed
    assertTrue(headersProviders.isEmpty());
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#addHeadersInstanceExchangeFilter(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#addHeadersInstanceExchangeFilter(List)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration addHeadersInstanceExchangeFilter(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.addHeadersInstanceExchangeFilter(List)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationAddHeadersInstanceExchangeFilter2() {
    // Arrange
    ArrayList<HttpHeadersProvider> headersProviders = new ArrayList<>();

    // Act
    InstanceExchangeFilterFunction actualAddHeadersInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.addHeadersInstanceExchangeFilter(
            headersProviders);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attributes()).thenReturn(new HashMap<>());
    when(request.httpRequest()).thenReturn(mock(Consumer.class));
    Mockito.<BodyInserter<?, ? super ClientHttpRequest>>when(request.body())
        .thenReturn(mock(BodyInserter.class));
    when(request.cookies()).thenReturn(new HttpHeaders());
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(request.headers()).thenReturn(new HttpHeaders());
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualAddHeadersInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
    verify(next).exchange(isA(ClientRequest.class));
    assertTrue(headersProviders.isEmpty());
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#addReactiveHeadersInstanceExchangeFilter(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#addReactiveHeadersInstanceExchangeFilter(List)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration addReactiveHeadersInstanceExchangeFilter(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.addReactiveHeadersInstanceExchangeFilter(List)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationAddReactiveHeadersInstanceExchangeFilter()
          throws AssertionError {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();

    // Act and Assert
    FirstStep<ClientResponse> createResult =
        StepVerifier.create(
            defaultInstanceExchangeFiltersConfiguration
                .addReactiveHeadersInstanceExchangeFilter(new ArrayList<>())
                .filter(
                    mock(Instance.class), mock(ClientRequest.class), mock(ExchangeFunction.class)));
    createResult.expectError().verify();
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#cookieHandlingInstanceExchangeFilter(PerInstanceCookieStore)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#cookieHandlingInstanceExchangeFilter(PerInstanceCookieStore)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration cookieHandlingInstanceExchangeFilter(PerInstanceCookieStore)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.cookieHandlingInstanceExchangeFilter(PerInstanceCookieStore)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationCookieHandlingInstanceExchangeFilter()
          throws AssertionError {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();

    // Act
    InstanceExchangeFilterFunction actualCookieHandlingInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.cookieHandlingInstanceExchangeFilter(
            new JdkPerInstanceCookieStore());
    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    ClientRequest request = mock(ClientRequest.class);
    when(request.headers()).thenReturn(new HttpHeaders());
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualPublisher =
        actualCookieHandlingInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(instance).getId();
    verify(request).headers();
    verify(request, atLeast(1)).url();
    verify(next).exchange(isA(ClientRequest.class));
    FirstStep<ClientResponse> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration legacyEndpointConverterInstanceExchangeFilter(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(List)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationLegacyEndpointConverterInstanceExchangeFilter() {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();
    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();

    // Act
    defaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(
        converters);

    // Assert that nothing has changed
    assertTrue(converters.isEmpty());
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration legacyEndpointConverterInstanceExchangeFilter(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(List)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationLegacyEndpointConverterInstanceExchangeFilter2() {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();
    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();

    // Act
    InstanceExchangeFilterFunction actualLegacyEndpointConverterInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(
            converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualLegacyEndpointConverterInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertTrue(converters.isEmpty());
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration legacyEndpointConverterInstanceExchangeFilter(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(List)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationLegacyEndpointConverterInstanceExchangeFilter3() {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();

    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();
    LegacyEndpointConverter beansResult = LegacyEndpointConverters.beans();
    converters.add(beansResult);

    // Act
    InstanceExchangeFilterFunction actualLegacyEndpointConverterInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(
            converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualLegacyEndpointConverterInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertEquals(1, converters.size());
    assertSame(beansResult, converters.get(0));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration legacyEndpointConverterInstanceExchangeFilter(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(List)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationLegacyEndpointConverterInstanceExchangeFilter4() {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();
    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();

    // Act
    InstanceExchangeFilterFunction actualLegacyEndpointConverterInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(
            converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> emptyResult = Optional.empty();
    when(request.attribute(Mockito.<String>any())).thenReturn(emptyResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualLegacyEndpointConverterInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertTrue(converters.isEmpty());
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration legacyEndpointConverterInstanceExchangeFilter(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(List)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationLegacyEndpointConverterInstanceExchangeFilter5() {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();

    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();
    LegacyEndpointConverter beansResult = LegacyEndpointConverters.beans();
    converters.add(beansResult);
    LegacyEndpointConverter beansResult2 = LegacyEndpointConverters.beans();
    converters.add(beansResult2);

    // Act
    InstanceExchangeFilterFunction actualLegacyEndpointConverterInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(
            converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualLegacyEndpointConverterInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertEquals(2, converters.size());
    assertSame(beansResult, converters.get(0));
    assertSame(beansResult2, converters.get(1));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#legacyEndpointConverterInstanceExchangeFilter(List)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration legacyEndpointConverterInstanceExchangeFilter(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(List)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationLegacyEndpointConverterInstanceExchangeFilter6()
          throws AssertionError {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();

    LegacyEndpointConverter legacyEndpointConverter = mock(LegacyEndpointConverter.class);
    when(legacyEndpointConverter.canConvert(Mockito.<Object>any())).thenReturn(true);

    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();
    converters.add(legacyEndpointConverter);

    // Act
    InstanceExchangeFilterFunction actualLegacyEndpointConverterInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.legacyEndpointConverterInstanceExchangeFilter(
            converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualPublisher =
        actualLegacyEndpointConverterInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(legacyEndpointConverter).canConvert(isA(Object.class));
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertEquals(1, converters.size());
    FirstStep<ClientResponse> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#logfileAcceptWorkaround()}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#logfileAcceptWorkaround()}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration logfileAcceptWorkaround()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.logfileAcceptWorkaround()"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationLogfileAcceptWorkaround() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualLogfileAcceptWorkaroundResult =
        new DefaultInstanceExchangeFiltersConfiguration().logfileAcceptWorkaround();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualLogfileAcceptWorkaroundResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#logfileAcceptWorkaround()}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#logfileAcceptWorkaround()}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration logfileAcceptWorkaround()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.logfileAcceptWorkaround()"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationLogfileAcceptWorkaround2() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualLogfileAcceptWorkaroundResult =
        new DefaultInstanceExchangeFiltersConfiguration().logfileAcceptWorkaround();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attributes()).thenReturn(new HashMap<>());
    when(request.httpRequest()).thenReturn(mock(Consumer.class));
    Mockito.<BodyInserter<?, ? super ClientHttpRequest>>when(request.body())
        .thenReturn(mock(BodyInserter.class));
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(request.cookies()).thenReturn(new HttpHeaders());
    when(request.headers()).thenReturn(new HttpHeaders());
    Optional<Object> ofResult = Optional.of("logfile");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualLogfileAcceptWorkaroundResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request, atLeast(1)).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
    verify(next).exchange(isA(ClientRequest.class));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#retryInstanceExchangeFilter(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#retryInstanceExchangeFilter(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration retryInstanceExchangeFilter(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.retryInstanceExchangeFilter(AdminServerProperties)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationRetryInstanceExchangeFilter() {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    defaultInstanceExchangeFiltersConfiguration.retryInstanceExchangeFilter(adminServerProperties);

    // Assert that nothing has changed
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#retryInstanceExchangeFilter(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#retryInstanceExchangeFilter(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration retryInstanceExchangeFilter(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.retryInstanceExchangeFilter(AdminServerProperties)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationRetryInstanceExchangeFilter2()
          throws AssertionError {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    InstanceExchangeFilterFunction actualRetryInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.retryInstanceExchangeFilter(
            adminServerProperties);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    ExchangeFunction next = mock(ExchangeFunction.class);
    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    ClientResponseWrapper clientResponseWrapper = new ClientResponseWrapper(delegate);
    Mono<ClientResponse> justResult = Mono.just(clientResponseWrapper);
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualPublisher =
        actualRetryInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(request, atLeast(1)).method();
    verify(next).exchange(isA(ClientRequest.class));
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
    FirstStep<ClientResponse> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            c -> {
              ClientResponse clientResponse = c;
              assertTrue(clientResponse instanceof ClientResponseWrapper);
              assertSame(clientResponseWrapper, clientResponse);
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#retryInstanceExchangeFilter(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#retryInstanceExchangeFilter(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration retryInstanceExchangeFilter(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.retryInstanceExchangeFilter(AdminServerProperties)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationRetryInstanceExchangeFilter3() {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    InstanceExchangeFilterFunction actualRetryInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.retryInstanceExchangeFilter(
            adminServerProperties);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    Mono<ClientResponse> mono = mock(Mono.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(mono.retry(anyLong())).thenReturn(justResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(mono);
    Mono<ClientResponse> actualFilterResult =
        actualRetryInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(request, atLeast(1)).method();
    verify(next).exchange(isA(ClientRequest.class));
    verify(mono).retry(0L);
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#rewriteEndpointUrlInstanceExchangeFilter()}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#rewriteEndpointUrlInstanceExchangeFilter()}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration rewriteEndpointUrlInstanceExchangeFilter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.rewriteEndpointUrlInstanceExchangeFilter()"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationRewriteEndpointUrlInstanceExchangeFilter() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRewriteEndpointUrlInstanceExchangeFilterResult =
        new DefaultInstanceExchangeFiltersConfiguration()
            .rewriteEndpointUrlInstanceExchangeFilter();
    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    ClientRequest request = mock(ClientRequest.class);
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualRewriteEndpointUrlInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(request, atLeast(1)).url();
    verify(next).exchange(isA(ClientRequest.class));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#rewriteEndpointUrlInstanceExchangeFilter()}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#rewriteEndpointUrlInstanceExchangeFilter()}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration rewriteEndpointUrlInstanceExchangeFilter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.rewriteEndpointUrlInstanceExchangeFilter()"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationRewriteEndpointUrlInstanceExchangeFilter2() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRewriteEndpointUrlInstanceExchangeFilterResult =
        new DefaultInstanceExchangeFiltersConfiguration()
            .rewriteEndpointUrlInstanceExchangeFilter();
    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://events.pagerduty.com/generic/2010-04-15/create_event.json")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    ClientRequest request = mock(ClientRequest.class);
    when(request.attributes()).thenReturn(new HashMap<>());
    when(request.httpRequest()).thenReturn(mock(Consumer.class));
    Mockito.<BodyInserter<?, ? super ClientHttpRequest>>when(request.body())
        .thenReturn(mock(BodyInserter.class));
    when(request.cookies()).thenReturn(new HttpHeaders());
    when(request.headers()).thenReturn(new HttpHeaders());
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualRewriteEndpointUrlInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request, atLeast(1)).url();
    verify(next).exchange(isA(ClientRequest.class));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#setDefaultAcceptHeaderInstanceExchangeFilter()}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#setDefaultAcceptHeaderInstanceExchangeFilter()}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration setDefaultAcceptHeaderInstanceExchangeFilter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.setDefaultAcceptHeaderInstanceExchangeFilter()"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationSetDefaultAcceptHeaderInstanceExchangeFilter() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualSetDefaultAcceptHeaderInstanceExchangeFilterResult =
        new DefaultInstanceExchangeFiltersConfiguration()
            .setDefaultAcceptHeaderInstanceExchangeFilter();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attributes()).thenReturn(new HashMap<>());
    when(request.httpRequest()).thenReturn(mock(Consumer.class));
    Mockito.<BodyInserter<?, ? super ClientHttpRequest>>when(request.body())
        .thenReturn(mock(BodyInserter.class));
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(request.cookies()).thenReturn(new HttpHeaders());
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    when(request.headers()).thenReturn(new HttpHeaders());
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualSetDefaultAcceptHeaderInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request, atLeast(1)).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
    verify(next).exchange(isA(ClientRequest.class));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#setDefaultAcceptHeaderInstanceExchangeFilter()}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#setDefaultAcceptHeaderInstanceExchangeFilter()}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration setDefaultAcceptHeaderInstanceExchangeFilter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.setDefaultAcceptHeaderInstanceExchangeFilter()"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationSetDefaultAcceptHeaderInstanceExchangeFilter2() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualSetDefaultAcceptHeaderInstanceExchangeFilterResult =
        new DefaultInstanceExchangeFiltersConfiguration()
            .setDefaultAcceptHeaderInstanceExchangeFilter();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attributes()).thenReturn(new HashMap<>());
    when(request.httpRequest()).thenReturn(mock(Consumer.class));
    Mockito.<BodyInserter<?, ? super ClientHttpRequest>>when(request.body())
        .thenReturn(mock(BodyInserter.class));
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(request.cookies()).thenReturn(new HttpHeaders());
    Optional<Object> ofResult = Optional.of("logfile");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    when(request.headers()).thenReturn(new HttpHeaders());
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualSetDefaultAcceptHeaderInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request, atLeast(1)).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
    verify(next).exchange(isA(ClientRequest.class));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#timeoutInstanceExchangeFilter(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#timeoutInstanceExchangeFilter(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration timeoutInstanceExchangeFilter(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.timeoutInstanceExchangeFilter(AdminServerProperties)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationTimeoutInstanceExchangeFilter() {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    defaultInstanceExchangeFiltersConfiguration.timeoutInstanceExchangeFilter(
        adminServerProperties);

    // Assert that nothing has changed
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#timeoutInstanceExchangeFilter(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#timeoutInstanceExchangeFilter(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration timeoutInstanceExchangeFilter(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.timeoutInstanceExchangeFilter(AdminServerProperties)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationTimeoutInstanceExchangeFilter2()
          throws AssertionError {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    InstanceExchangeFilterFunction actualTimeoutInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.timeoutInstanceExchangeFilter(
            adminServerProperties);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    ClientResponseWrapper clientResponseWrapper = new ClientResponseWrapper(delegate);
    Mono<ClientResponse> justResult = Mono.just(clientResponseWrapper);
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualPublisher =
        actualTimeoutInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
    FirstStep<ClientResponse> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            c -> {
              ClientResponse clientResponse = c;
              assertTrue(clientResponse instanceof ClientResponseWrapper);
              assertSame(clientResponseWrapper, clientResponse);
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#timeoutInstanceExchangeFilter(AdminServerProperties)}.
   *
   * <p>Method under test: {@link
   * InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration#timeoutInstanceExchangeFilter(AdminServerProperties)}
   */
  @Test
  @DisplayName(
      "Test InstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfiguration timeoutInstanceExchangeFilter(AdminServerProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration.timeoutInstanceExchangeFilter(AdminServerProperties)"
  })
  void
      testInstanceExchangeFiltersConfiguration_DefaultInstanceExchangeFiltersConfigurationTimeoutInstanceExchangeFilter3() {
    // Arrange
    DefaultInstanceExchangeFiltersConfiguration defaultInstanceExchangeFiltersConfiguration =
        new DefaultInstanceExchangeFiltersConfiguration();
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    InstanceExchangeFilterFunction actualTimeoutInstanceExchangeFilterResult =
        defaultInstanceExchangeFiltersConfiguration.timeoutInstanceExchangeFilter(
            adminServerProperties);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("42");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    Mono<ClientResponse> mono = mock(Mono.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(mono.timeout(Mockito.<Duration>any())).thenReturn(justResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(mono);
    Mono<ClientResponse> actualFilterResult =
        actualTimeoutInstanceExchangeFilterResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    verify(mono).timeout(isA(Duration.class));
    assertEquals("", adminServerProperties.getContextPath());
    assertEquals(21, adminServerProperties.getProbedEndpoints().length);
    assertEquals(6, adminServerProperties.getMetadataKeysToSanitize().length);
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#beansLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#beansLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration beansLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.beansLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationBeansLegacyEndpointConverter()
      throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualBeansLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().beansLegacyEndpointConverter();

    // Assert
    assertFalse(actualBeansLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult =
        StepVerifier.create(actualBeansLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#configpropsLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#configpropsLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration configpropsLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.configpropsLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationConfigpropsLegacyEndpointConverter()
      throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualConfigpropsLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().configpropsLegacyEndpointConverter();

    // Assert
    assertFalse(actualConfigpropsLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult =
        StepVerifier.create(actualConfigpropsLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#envLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#envLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration envLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.envLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationEnvLegacyEndpointConverter() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualEnvLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().envLegacyEndpointConverter();

    // Assert
    assertFalse(actualEnvLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult =
        StepVerifier.create(actualEnvLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#flywayLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#flywayLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration flywayLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.flywayLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationFlywayLegacyEndpointConverter()
      throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualFlywayLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().flywayLegacyEndpointConverter();

    // Assert
    assertFalse(actualFlywayLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult =
        StepVerifier.create(actualFlywayLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#healthLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#healthLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration healthLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.healthLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationHealthLegacyEndpointConverter()
      throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualHealthLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().healthLegacyEndpointConverter();

    // Assert
    assertFalse(actualHealthLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult =
        StepVerifier.create(actualHealthLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#httptraceLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#httptraceLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration httptraceLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.httptraceLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationHttptraceLegacyEndpointConverter()
      throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualHttptraceLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().httptraceLegacyEndpointConverter();

    // Assert
    assertFalse(actualHttptraceLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult =
        StepVerifier.create(actualHttptraceLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#infoLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#infoLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration infoLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.infoLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationInfoLegacyEndpointConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualInfoLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().infoLegacyEndpointConverter();

    // Assert
    assertNull(actualInfoLegacyEndpointConverterResult.convert(null));
    assertFalse(actualInfoLegacyEndpointConverterResult.canConvert("Endpoint Id"));
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#liquibaseLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#liquibaseLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration liquibaseLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.liquibaseLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationLiquibaseLegacyEndpointConverter()
      throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualLiquibaseLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().liquibaseLegacyEndpointConverter();

    // Assert
    assertFalse(actualLiquibaseLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult =
        StepVerifier.create(actualLiquibaseLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#mappingsLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#mappingsLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration mappingsLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.mappingsLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationMappingsLegacyEndpointConverter()
      throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualMappingsLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().mappingsLegacyEndpointConverter();

    // Assert
    assertFalse(actualMappingsLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult =
        StepVerifier.create(actualMappingsLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#startupLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#startupLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration startupLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.startupLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationStartupLegacyEndpointConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualStartupLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().startupLegacyEndpointConverter();

    // Assert
    assertNull(actualStartupLegacyEndpointConverterResult.convert(null));
    assertFalse(actualStartupLegacyEndpointConverterResult.canConvert("Endpoint Id"));
  }

  /**
   * Test LegaycEndpointConvertersConfiguration {@link
   * LegaycEndpointConvertersConfiguration#threaddumpLegacyEndpointConverter()}.
   *
   * <p>Method under test: {@link
   * LegaycEndpointConvertersConfiguration#threaddumpLegacyEndpointConverter()}
   */
  @Test
  @DisplayName("Test LegaycEndpointConvertersConfiguration threaddumpLegacyEndpointConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LegacyEndpointConverter LegaycEndpointConvertersConfiguration.threaddumpLegacyEndpointConverter()"
  })
  void testLegaycEndpointConvertersConfigurationThreaddumpLegacyEndpointConverter()
      throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LegacyEndpointConverter actualThreaddumpLegacyEndpointConverterResult =
        new LegaycEndpointConvertersConfiguration().threaddumpLegacyEndpointConverter();

    // Assert
    assertFalse(actualThreaddumpLegacyEndpointConverterResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult =
        StepVerifier.create(actualThreaddumpLegacyEndpointConverterResult.convert(null));
    createResult.expectError().verify();
  }
}
