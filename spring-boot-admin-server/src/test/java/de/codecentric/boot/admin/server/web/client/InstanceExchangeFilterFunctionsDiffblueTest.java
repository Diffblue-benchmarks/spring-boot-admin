package de.codecentric.boot.admin.server.web.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.web.client.cookies.JdkPerInstanceCookieStore;
import de.codecentric.boot.admin.server.web.client.cookies.PerInstanceCookieStore;
import de.codecentric.boot.admin.server.web.client.exception.ResolveEndpointException;
import de.codecentric.boot.admin.server.web.client.reactive.ReactiveHttpHeadersProvider;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class InstanceExchangeFilterFunctionsDiffblueTest {
  /**
   * Test {@link InstanceExchangeFilterFunctions#addHeaders(HttpHeadersProvider)}.
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#addHeaders(HttpHeadersProvider)}
   */
  @Test
  @DisplayName("Test addHeaders(HttpHeadersProvider)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.addHeaders(HttpHeadersProvider)"
  })
  void testAddHeaders() {
    // Arrange
    HttpHeadersProvider httpHeadersProvider = mock(HttpHeadersProvider.class);
    when(httpHeadersProvider.getHeaders(Mockito.<Instance>any())).thenReturn(new HttpHeaders());

    // Act
    InstanceExchangeFilterFunction actualAddHeadersResult =
        InstanceExchangeFilterFunctions.addHeaders(httpHeadersProvider);
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
        actualAddHeadersResult.filter(instance, request, next);

    // Assert
    verify(httpHeadersProvider).getHeaders(isA(Instance.class));
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
    verify(next).exchange(isA(ClientRequest.class));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#addHeaders(HttpHeadersProvider)}.
   *
   * <ul>
   *   <li>Given {@link HttpHeaders#HttpHeaders()}.
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#addHeaders(HttpHeadersProvider)}
   */
  @Test
  @DisplayName(
      "Test addHeaders(HttpHeadersProvider); given HttpHeaders(); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.addHeaders(HttpHeadersProvider)"
  })
  void testAddHeaders_givenHttpHeaders_thenThrowResolveEndpointException() {
    // Arrange
    HttpHeadersProvider httpHeadersProvider = mock(HttpHeadersProvider.class);
    when(httpHeadersProvider.getHeaders(Mockito.<Instance>any())).thenReturn(new HttpHeaders());

    // Act
    InstanceExchangeFilterFunction actualAddHeadersResult =
        InstanceExchangeFilterFunctions.addHeaders(httpHeadersProvider);
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
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualAddHeadersResult.filter(instance, request, next));
    verify(httpHeadersProvider).getHeaders(isA(Instance.class));
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#addHeaders(HttpHeadersProvider)}.
   *
   * <ul>
   *   <li>Given {@link ResolveEndpointException#ResolveEndpointException(String)} with message is
   *       {@code An error occurred}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#addHeaders(HttpHeadersProvider)}
   */
  @Test
  @DisplayName(
      "Test addHeaders(HttpHeadersProvider); given ResolveEndpointException(String) with message is 'An error occurred'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.addHeaders(HttpHeadersProvider)"
  })
  void testAddHeaders_givenResolveEndpointExceptionWithMessageIsAnErrorOccurred() {
    // Arrange
    HttpHeadersProvider httpHeadersProvider = mock(HttpHeadersProvider.class);
    when(httpHeadersProvider.getHeaders(Mockito.<Instance>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Act
    InstanceExchangeFilterFunction actualAddHeadersResult =
        InstanceExchangeFilterFunctions.addHeaders(httpHeadersProvider);
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

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualAddHeadersResult.filter(instance, request, mock(ExchangeFunction.class)));
    verify(httpHeadersProvider).getHeaders(isA(Instance.class));
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#addHeaders(HttpHeadersProvider)}.
   *
   * <ul>
   *   <li>When {@link HttpHeadersProvider}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#addHeaders(HttpHeadersProvider)}
   */
  @Test
  @DisplayName(
      "Test addHeaders(HttpHeadersProvider); when HttpHeadersProvider; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.addHeaders(HttpHeadersProvider)"
  })
  void testAddHeaders_whenHttpHeadersProvider_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(
        () -> InstanceExchangeFilterFunctions.addHeaders(mock(HttpHeadersProvider.class)));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#addHeadersReactive(ReactiveHttpHeadersProvider)}.
   *
   * <ul>
   *   <li>Given just {@link HttpHeaders#HttpHeaders()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceExchangeFilterFunctions#addHeadersReactive(ReactiveHttpHeadersProvider)}
   */
  @Test
  @DisplayName("Test addHeadersReactive(ReactiveHttpHeadersProvider); given just HttpHeaders()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.addHeadersReactive(ReactiveHttpHeadersProvider)"
  })
  void testAddHeadersReactive_givenJustHttpHeaders() throws AssertionError {
    // Arrange
    ReactiveHttpHeadersProvider httpHeadersProvider = mock(ReactiveHttpHeadersProvider.class);
    Mono<HttpHeaders> justResult = Mono.just(new HttpHeaders());
    when(httpHeadersProvider.getHeaders(Mockito.<Instance>any())).thenReturn(justResult);

    // Act
    Mono<ClientResponse> actualPublisher =
        InstanceExchangeFilterFunctions.addHeadersReactive(httpHeadersProvider)
            .filter(mock(Instance.class), mock(ClientRequest.class), mock(ExchangeFunction.class));

    // Assert
    verify(httpHeadersProvider).getHeaders(isA(Instance.class));
    FirstStep<ClientResponse> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#addHeadersReactive(ReactiveHttpHeadersProvider)}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceExchangeFilterFunctions#addHeadersReactive(ReactiveHttpHeadersProvider)}
   */
  @Test
  @DisplayName(
      "Test addHeadersReactive(ReactiveHttpHeadersProvider); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.addHeadersReactive(ReactiveHttpHeadersProvider)"
  })
  void testAddHeadersReactive_thenThrowResolveEndpointException() {
    // Arrange
    ReactiveHttpHeadersProvider httpHeadersProvider = mock(ReactiveHttpHeadersProvider.class);
    when(httpHeadersProvider.getHeaders(Mockito.<Instance>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Act and Assert
    assertThrows(
        ResolveEndpointException.class,
        () ->
            InstanceExchangeFilterFunctions.addHeadersReactive(httpHeadersProvider)
                .filter(
                    mock(Instance.class), mock(ClientRequest.class), mock(ExchangeFunction.class)));
    verify(httpHeadersProvider).getHeaders(isA(Instance.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#addHeadersReactive(ReactiveHttpHeadersProvider)}.
   *
   * <ul>
   *   <li>When {@link ReactiveHttpHeadersProvider}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceExchangeFilterFunctions#addHeadersReactive(ReactiveHttpHeadersProvider)}
   */
  @Test
  @DisplayName(
      "Test addHeadersReactive(ReactiveHttpHeadersProvider); when ReactiveHttpHeadersProvider; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.addHeadersReactive(ReactiveHttpHeadersProvider)"
  })
  void testAddHeadersReactive_whenReactiveHttpHeadersProvider_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(
        () ->
            InstanceExchangeFilterFunctions.addHeadersReactive(
                mock(ReactiveHttpHeadersProvider.class)));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}.
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}
   */
  @Test
  @DisplayName("Test rewriteEndpointUrl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.rewriteEndpointUrl()"
  })
  void testRewriteEndpointUrl() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRewriteEndpointUrlResult =
        InstanceExchangeFilterFunctions.rewriteEndpointUrl();
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
        actualRewriteEndpointUrlResult.filter(instance, request, next);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(request, atLeast(1)).url();
    verify(next).exchange(isA(ClientRequest.class));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}.
   *
   * <ul>
   *   <li>Then calls {@link ClientRequest#attributes()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}
   */
  @Test
  @DisplayName("Test rewriteEndpointUrl(); then calls attributes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.rewriteEndpointUrl()"
  })
  void testRewriteEndpointUrl_thenCallsAttributes() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRewriteEndpointUrlResult =
        InstanceExchangeFilterFunctions.rewriteEndpointUrl();
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
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualRewriteEndpointUrlResult.filter(instance, request, next));
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
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getRegistration()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}
   */
  @Test
  @DisplayName("Test rewriteEndpointUrl(); then calls getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.rewriteEndpointUrl()"
  })
  void testRewriteEndpointUrl_thenCallsGetRegistration() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRewriteEndpointUrlResult =
        InstanceExchangeFilterFunctions.rewriteEndpointUrl();
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
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualRewriteEndpointUrlResult.filter(instance, request, next));
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(request, atLeast(1)).url();
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}
   */
  @Test
  @DisplayName("Test rewriteEndpointUrl(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.rewriteEndpointUrl()"
  })
  void testRewriteEndpointUrl_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> InstanceExchangeFilterFunctions.rewriteEndpointUrl());
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}
   */
  @Test
  @DisplayName("Test rewriteEndpointUrl(); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.rewriteEndpointUrl()"
  })
  void testRewriteEndpointUrl_thenThrowResolveEndpointException() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRewriteEndpointUrlResult =
        InstanceExchangeFilterFunctions.rewriteEndpointUrl();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.url()).thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () ->
            actualRewriteEndpointUrlResult.filter(instance, request, mock(ExchangeFunction.class)));
    verify(request).url();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#rewriteEndpointUrl()}
   */
  @Test
  @DisplayName("Test rewriteEndpointUrl(); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.rewriteEndpointUrl()"
  })
  void testRewriteEndpointUrl_thenThrowResolveEndpointException2() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRewriteEndpointUrlResult =
        InstanceExchangeFilterFunctions.rewriteEndpointUrl();
    Instance instance = mock(Instance.class);
    when(instance.getId()).thenThrow(new ResolveEndpointException("An error occurred"));
    ClientRequest request = mock(ClientRequest.class);
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () ->
            actualRewriteEndpointUrlResult.filter(instance, request, mock(ExchangeFunction.class)));
    verify(instance).getId();
    verify(request, atLeast(1)).url();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}.
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}
   */
  @Test
  @DisplayName("Test convertLegacyEndpoints(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.convertLegacyEndpoints(List)"
  })
  void testConvertLegacyEndpoints() {
    // Arrange
    LegacyEndpointConverter legacyEndpointConverter = mock(LegacyEndpointConverter.class);
    when(legacyEndpointConverter.canConvert(Mockito.<Object>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();
    converters.add(legacyEndpointConverter);

    // Act
    InstanceExchangeFilterFunction actualConvertLegacyEndpointsResult =
        InstanceExchangeFilterFunctions.convertLegacyEndpoints(converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualConvertLegacyEndpointsResult.filter(instance, request, next));
    verify(legacyEndpointConverter).canConvert(isA(Object.class));
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}.
   *
   * <ul>
   *   <li>Given beans.
   *   <li>Then {@link ArrayList#ArrayList()} first is beans.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}
   */
  @Test
  @DisplayName("Test convertLegacyEndpoints(List); given beans; then ArrayList() first is beans")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.convertLegacyEndpoints(List)"
  })
  void testConvertLegacyEndpoints_givenBeans_thenArrayListFirstIsBeans() {
    // Arrange
    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();
    LegacyEndpointConverter beansResult = LegacyEndpointConverters.beans();
    converters.add(beansResult);

    // Act
    InstanceExchangeFilterFunction actualConvertLegacyEndpointsResult =
        InstanceExchangeFilterFunctions.convertLegacyEndpoints(converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualConvertLegacyEndpointsResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertEquals(1, converters.size());
    assertSame(beansResult, converters.get(0));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}.
   *
   * <ul>
   *   <li>Given beans.
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}
   */
  @Test
  @DisplayName("Test convertLegacyEndpoints(List); given beans; then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.convertLegacyEndpoints(List)"
  })
  void testConvertLegacyEndpoints_givenBeans_thenArrayListSizeIsTwo() {
    // Arrange
    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();
    LegacyEndpointConverter beansResult = LegacyEndpointConverters.beans();
    converters.add(beansResult);
    LegacyEndpointConverter beansResult2 = LegacyEndpointConverters.beans();
    converters.add(beansResult2);

    // Act
    InstanceExchangeFilterFunction actualConvertLegacyEndpointsResult =
        InstanceExchangeFilterFunctions.convertLegacyEndpoints(converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualConvertLegacyEndpointsResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertEquals(2, converters.size());
    assertSame(beansResult, converters.get(0));
    assertSame(beansResult2, converters.get(1));
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}
   */
  @Test
  @DisplayName("Test convertLegacyEndpoints(List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.convertLegacyEndpoints(List)"
  })
  void testConvertLegacyEndpoints_thenArrayListSizeIsOne() throws AssertionError {
    // Arrange
    LegacyEndpointConverter legacyEndpointConverter = mock(LegacyEndpointConverter.class);
    when(legacyEndpointConverter.canConvert(Mockito.<Object>any())).thenReturn(true);

    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();
    converters.add(legacyEndpointConverter);

    // Act
    InstanceExchangeFilterFunction actualConvertLegacyEndpointsResult =
        InstanceExchangeFilterFunctions.convertLegacyEndpoints(converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualPublisher =
        actualConvertLegacyEndpointsResult.filter(instance, request, next);

    // Assert
    verify(legacyEndpointConverter).canConvert(isA(Object.class));
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertEquals(1, converters.size());
    FirstStep<ClientResponse> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}
   */
  @Test
  @DisplayName("Test convertLegacyEndpoints(List); when ArrayList(); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.convertLegacyEndpoints(List)"
  })
  void testConvertLegacyEndpoints_whenArrayList_thenArrayListEmpty() {
    // Arrange
    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();

    // Act
    InstanceExchangeFilterFunctions.convertLegacyEndpoints(converters);

    // Assert that nothing has changed
    assertTrue(converters.isEmpty());
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}
   */
  @Test
  @DisplayName("Test convertLegacyEndpoints(List); when ArrayList(); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.convertLegacyEndpoints(List)"
  })
  void testConvertLegacyEndpoints_whenArrayList_thenArrayListEmpty2() {
    // Arrange
    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();

    // Act
    InstanceExchangeFilterFunction actualConvertLegacyEndpointsResult =
        InstanceExchangeFilterFunctions.convertLegacyEndpoints(converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualConvertLegacyEndpointsResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertTrue(converters.isEmpty());
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}
   */
  @Test
  @DisplayName("Test convertLegacyEndpoints(List); when ArrayList(); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.convertLegacyEndpoints(List)"
  })
  void testConvertLegacyEndpoints_whenArrayList_thenArrayListEmpty3() {
    // Arrange
    ArrayList<LegacyEndpointConverter> converters = new ArrayList<>();

    // Act
    InstanceExchangeFilterFunction actualConvertLegacyEndpointsResult =
        InstanceExchangeFilterFunctions.convertLegacyEndpoints(converters);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> emptyResult = Optional.empty();
    when(request.attribute(Mockito.<String>any())).thenReturn(emptyResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualConvertLegacyEndpointsResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
    assertTrue(converters.isEmpty());
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}
   */
  @Test
  @DisplayName(
      "Test convertLegacyEndpoints(List); when ArrayList(); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.convertLegacyEndpoints(List)"
  })
  void testConvertLegacyEndpoints_whenArrayList_thenThrowResolveEndpointException() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualConvertLegacyEndpointsResult =
        InstanceExchangeFilterFunctions.convertLegacyEndpoints(new ArrayList<>());
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualConvertLegacyEndpointsResult.filter(instance, request, next));
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#convertLegacyEndpoints(List)}
   */
  @Test
  @DisplayName(
      "Test convertLegacyEndpoints(List); when ArrayList(); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.convertLegacyEndpoints(List)"
  })
  void testConvertLegacyEndpoints_whenArrayList_thenThrowResolveEndpointException2() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualConvertLegacyEndpointsResult =
        InstanceExchangeFilterFunctions.convertLegacyEndpoints(new ArrayList<>());
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attribute(Mockito.<String>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualConvertLegacyEndpointsResult.filter(instance, request, next));
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}.
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}
   */
  @Test
  @DisplayName("Test setDefaultAcceptHeader()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.setDefaultAcceptHeader()"
  })
  void testSetDefaultAcceptHeader() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualSetDefaultAcceptHeaderResult =
        InstanceExchangeFilterFunctions.setDefaultAcceptHeader();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attributes()).thenReturn(new HashMap<>());
    when(request.httpRequest()).thenReturn(mock(Consumer.class));
    Mockito.<BodyInserter<?, ? super ClientHttpRequest>>when(request.body())
        .thenReturn(mock(BodyInserter.class));
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(request.cookies()).thenReturn(new HttpHeaders());
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    when(request.headers()).thenReturn(new HttpHeaders());
    ExchangeFunction next = mock(ExchangeFunction.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualFilterResult =
        actualSetDefaultAcceptHeaderResult.filter(instance, request, next);

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
   * Test {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}.
   *
   * <ul>
   *   <li>Then calls {@link ClientRequest#attributes()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}
   */
  @Test
  @DisplayName("Test setDefaultAcceptHeader(); then calls attributes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.setDefaultAcceptHeader()"
  })
  void testSetDefaultAcceptHeader_thenCallsAttributes() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualSetDefaultAcceptHeaderResult =
        InstanceExchangeFilterFunctions.setDefaultAcceptHeader();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attributes()).thenReturn(new HashMap<>());
    when(request.httpRequest()).thenReturn(mock(Consumer.class));
    Mockito.<BodyInserter<?, ? super ClientHttpRequest>>when(request.body())
        .thenReturn(mock(BodyInserter.class));
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(request.cookies()).thenReturn(new HttpHeaders());
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    when(request.headers()).thenReturn(new HttpHeaders());
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualSetDefaultAcceptHeaderResult.filter(instance, request, next));
    verify(request).attribute("endpointId");
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request, atLeast(1)).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}.
   *
   * <ul>
   *   <li>Then calls {@link ClientRequest#attributes()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}
   */
  @Test
  @DisplayName("Test setDefaultAcceptHeader(); then calls attributes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.setDefaultAcceptHeader()"
  })
  void testSetDefaultAcceptHeader_thenCallsAttributes2() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualSetDefaultAcceptHeaderResult =
        InstanceExchangeFilterFunctions.setDefaultAcceptHeader();
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
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualSetDefaultAcceptHeaderResult.filter(instance, request, next));
    verify(request).attribute("endpointId");
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request, atLeast(1)).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}
   */
  @Test
  @DisplayName("Test setDefaultAcceptHeader(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.setDefaultAcceptHeader()"
  })
  void testSetDefaultAcceptHeader_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> InstanceExchangeFilterFunctions.setDefaultAcceptHeader());
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}
   */
  @Test
  @DisplayName("Test setDefaultAcceptHeader(); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.setDefaultAcceptHeader()"
  })
  void testSetDefaultAcceptHeader_thenThrowResolveEndpointException() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualSetDefaultAcceptHeaderResult =
        InstanceExchangeFilterFunctions.setDefaultAcceptHeader();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.headers()).thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () ->
            actualSetDefaultAcceptHeaderResult.filter(
                instance, request, mock(ExchangeFunction.class)));
    verify(request).headers();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#setDefaultAcceptHeader()}
   */
  @Test
  @DisplayName("Test setDefaultAcceptHeader(); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.setDefaultAcceptHeader()"
  })
  void testSetDefaultAcceptHeader_thenThrowResolveEndpointException2() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualSetDefaultAcceptHeaderResult =
        InstanceExchangeFilterFunctions.setDefaultAcceptHeader();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attribute(Mockito.<String>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));
    when(request.headers()).thenReturn(new HttpHeaders());

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () ->
            actualSetDefaultAcceptHeaderResult.filter(
                instance, request, mock(ExchangeFunction.class)));
    verify(request).attribute("endpointId");
    verify(request).headers();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#retry(int, Map)}.
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#retry(int, Map)}
   */
  @Test
  @DisplayName("Test retry(int, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.retry(int, Map)"
  })
  void testRetry() {
    // Arrange
    HashMap<String, Integer> retriesPerEndpoint = new HashMap<>();

    // Act
    InstanceExchangeFilterFunction actualRetryResult =
        InstanceExchangeFilterFunctions.retry(1, retriesPerEndpoint);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    Mono<ClientResponse> mono = mock(Mono.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(mono.retry(anyLong())).thenReturn(justResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(mono);
    Mono<ClientResponse> actualFilterResult = actualRetryResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(request, atLeast(1)).method();
    verify(next).exchange(isA(ClientRequest.class));
    verify(mono).retry(1L);
    assertTrue(retriesPerEndpoint.isEmpty());
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#retry(int, Map)}.
   *
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#retry(int, Map)}
   */
  @Test
  @DisplayName("Test retry(int, Map); then HashMap() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.retry(int, Map)"
  })
  void testRetry_thenHashMapEmpty() {
    // Arrange
    HashMap<String, Integer> retriesPerEndpoint = new HashMap<>();

    // Act
    InstanceExchangeFilterFunctions.retry(1, retriesPerEndpoint);

    // Assert that nothing has changed
    assertTrue(retriesPerEndpoint.isEmpty());
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#retry(int, Map)}.
   *
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#retry(int, Map)}
   */
  @Test
  @DisplayName("Test retry(int, Map); then HashMap() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.retry(int, Map)"
  })
  void testRetry_thenHashMapEmpty2() throws AssertionError {
    // Arrange
    HashMap<String, Integer> retriesPerEndpoint = new HashMap<>();

    // Act
    InstanceExchangeFilterFunction actualRetryResult =
        InstanceExchangeFilterFunctions.retry(1, retriesPerEndpoint);
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    ExchangeFunction next = mock(ExchangeFunction.class);
    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    ClientResponseWrapper clientResponseWrapper = new ClientResponseWrapper(delegate);
    Mono<ClientResponse> justResult = Mono.just(clientResponseWrapper);
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualPublisher = actualRetryResult.filter(instance, request, next);

    // Assert
    verify(request).attribute("endpointId");
    verify(request, atLeast(1)).method();
    verify(next).exchange(isA(ClientRequest.class));
    assertTrue(retriesPerEndpoint.isEmpty());
    FirstStep<ClientResponse> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            c -> {
              assertSame(clientResponseWrapper, c);
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#retry(int, Map)}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#retry(int, Map)}
   */
  @Test
  @DisplayName("Test retry(int, Map); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.retry(int, Map)"
  })
  void testRetry_thenThrowResolveEndpointException() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRetryResult =
        InstanceExchangeFilterFunctions.retry(1, new HashMap<>());
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.method()).thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualRetryResult.filter(instance, request, mock(ExchangeFunction.class)));
    verify(request).method();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#retry(int, Map)}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#retry(int, Map)}
   */
  @Test
  @DisplayName("Test retry(int, Map); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.retry(int, Map)"
  })
  void testRetry_thenThrowResolveEndpointException2() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRetryResult =
        InstanceExchangeFilterFunctions.retry(1, new HashMap<>());
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attribute(Mockito.<String>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualRetryResult.filter(instance, request, mock(ExchangeFunction.class)));
    verify(request).attribute("endpointId");
    verify(request, atLeast(1)).method();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#retry(int, Map)}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#retry(int, Map)}
   */
  @Test
  @DisplayName("Test retry(int, Map); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.retry(int, Map)"
  })
  void testRetry_thenThrowResolveEndpointException3() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualRetryResult =
        InstanceExchangeFilterFunctions.retry(1, new HashMap<>());
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    when(request.method()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class, () -> actualRetryResult.filter(instance, request, next));
    verify(request).attribute("endpointId");
    verify(request, atLeast(1)).method();
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#timeout(Duration, Map)}.
   *
   * <ul>
   *   <li>Then calls {@link ExchangeFunction#exchange(ClientRequest)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#timeout(Duration, Map)}
   */
  @Test
  @DisplayName("Test timeout(Duration, Map); then calls exchange(ClientRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.timeout(Duration, Map)"
  })
  void testTimeout_thenCallsExchange() {
    // Arrange
    Duration defaultTimeout = Duration.ofSeconds(1L);

    // Act
    InstanceExchangeFilterFunction actualTimeoutResult =
        InstanceExchangeFilterFunctions.timeout(defaultTimeout, new HashMap<>());
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class, () -> actualTimeoutResult.filter(instance, request, next));
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#timeout(Duration, Map)}.
   *
   * <ul>
   *   <li>Then ofSeconds one toNanos is {@code 1000000000}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#timeout(Duration, Map)}
   */
  @Test
  @DisplayName("Test timeout(Duration, Map); then ofSeconds one toNanos is '1000000000'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.timeout(Duration, Map)"
  })
  void testTimeout_thenOfSecondsOneToNanosIs1000000000() {
    // Arrange
    Duration defaultTimeout = Duration.ofSeconds(1L);
    HashMap<String, Duration> timeoutPerEndpoint = new HashMap<>();

    // Act
    InstanceExchangeFilterFunctions.timeout(defaultTimeout, timeoutPerEndpoint);

    // Assert that nothing has changed
    assertEquals(1000000000L, defaultTimeout.toNanos());
    assertTrue(timeoutPerEndpoint.isEmpty());
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#timeout(Duration, Map)}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#timeout(Duration, Map)}
   */
  @Test
  @DisplayName("Test timeout(Duration, Map); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.timeout(Duration, Map)"
  })
  void testTimeout_thenThrowResolveEndpointException() {
    // Arrange
    Duration defaultTimeout = Duration.ofSeconds(1L);

    // Act
    InstanceExchangeFilterFunction actualTimeoutResult =
        InstanceExchangeFilterFunctions.timeout(defaultTimeout, new HashMap<>());
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attribute(Mockito.<String>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualTimeoutResult.filter(instance, request, mock(ExchangeFunction.class)));
    verify(request).attribute("endpointId");
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}.
   *
   * <ul>
   *   <li>Then calls {@link ClientRequest#attributes()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}
   */
  @Test
  @DisplayName("Test logfileAcceptWorkaround(); then calls attributes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.logfileAcceptWorkaround()"
  })
  void testLogfileAcceptWorkaround_thenCallsAttributes() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualLogfileAcceptWorkaroundResult =
        InstanceExchangeFilterFunctions.logfileAcceptWorkaround();
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
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualLogfileAcceptWorkaroundResult.filter(instance, request, next));
    verify(request).attribute("endpointId");
    verify(request).attributes();
    verify(request).body();
    verify(request).cookies();
    verify(request, atLeast(1)).headers();
    verify(request).httpRequest();
    verify(request).method();
    verify(request).url();
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}.
   *
   * <ul>
   *   <li>Then calls {@link ExchangeFunction#exchange(ClientRequest)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}
   */
  @Test
  @DisplayName("Test logfileAcceptWorkaround(); then calls exchange(ClientRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.logfileAcceptWorkaround()"
  })
  void testLogfileAcceptWorkaround_thenCallsExchange() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualLogfileAcceptWorkaroundResult =
        InstanceExchangeFilterFunctions.logfileAcceptWorkaround();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Optional<Object> ofResult = Optional.of("Value");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualLogfileAcceptWorkaroundResult.filter(instance, request, next));
    verify(request).attribute("endpointId");
    verify(next).exchange(isA(ClientRequest.class));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}.
   *
   * <ul>
   *   <li>Then calls {@link ClientRequest#headers()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}
   */
  @Test
  @DisplayName("Test logfileAcceptWorkaround(); then calls headers()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.logfileAcceptWorkaround()"
  })
  void testLogfileAcceptWorkaround_thenCallsHeaders() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualLogfileAcceptWorkaroundResult =
        InstanceExchangeFilterFunctions.logfileAcceptWorkaround();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.headers()).thenThrow(new ResolveEndpointException("An error occurred"));
    Optional<Object> ofResult = Optional.of("logfile");
    when(request.attribute(Mockito.<String>any())).thenReturn(ofResult);

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () ->
            actualLogfileAcceptWorkaroundResult.filter(
                instance, request, mock(ExchangeFunction.class)));
    verify(request).attribute("endpointId");
    verify(request).headers();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}
   */
  @Test
  @DisplayName("Test logfileAcceptWorkaround(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.logfileAcceptWorkaround()"
  })
  void testLogfileAcceptWorkaround_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> InstanceExchangeFilterFunctions.logfileAcceptWorkaround());
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceExchangeFilterFunctions#logfileAcceptWorkaround()}
   */
  @Test
  @DisplayName("Test logfileAcceptWorkaround(); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.logfileAcceptWorkaround()"
  })
  void testLogfileAcceptWorkaround_thenThrowResolveEndpointException() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualLogfileAcceptWorkaroundResult =
        InstanceExchangeFilterFunctions.logfileAcceptWorkaround();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.attribute(Mockito.<String>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () ->
            actualLogfileAcceptWorkaroundResult.filter(
                instance, request, mock(ExchangeFunction.class)));
    verify(request).attribute("endpointId");
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}.
   *
   * <ul>
   *   <li>Then calls {@link ClientRequest#headers()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}
   */
  @Test
  @DisplayName("Test handleCookies(PerInstanceCookieStore); then calls headers()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.handleCookies(PerInstanceCookieStore)"
  })
  void testHandleCookies_thenCallsHeaders() throws AssertionError {
    // Arrange and Act
    InstanceExchangeFilterFunction actualHandleCookiesResult =
        InstanceExchangeFilterFunctions.handleCookies(new JdkPerInstanceCookieStore());
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
        actualHandleCookiesResult.filter(instance, request, next);

    // Assert
    verify(instance).getId();
    verify(request).headers();
    verify(request, atLeast(1)).url();
    verify(next).exchange(isA(ClientRequest.class));
    FirstStep<ClientResponse> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}
   */
  @Test
  @DisplayName("Test handleCookies(PerInstanceCookieStore); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.handleCookies(PerInstanceCookieStore)"
  })
  void testHandleCookies_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(
        () -> InstanceExchangeFilterFunctions.handleCookies(new JdkPerInstanceCookieStore()));
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}
   */
  @Test
  @DisplayName("Test handleCookies(PerInstanceCookieStore); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.handleCookies(PerInstanceCookieStore)"
  })
  void testHandleCookies_thenThrowResolveEndpointException() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualHandleCookiesResult =
        InstanceExchangeFilterFunctions.handleCookies(new JdkPerInstanceCookieStore());
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    when(request.url()).thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualHandleCookiesResult.filter(instance, request, mock(ExchangeFunction.class)));
    verify(request).url();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}
   */
  @Test
  @DisplayName("Test handleCookies(PerInstanceCookieStore); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.handleCookies(PerInstanceCookieStore)"
  })
  void testHandleCookies_thenThrowResolveEndpointException2() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualHandleCookiesResult =
        InstanceExchangeFilterFunctions.handleCookies(new JdkPerInstanceCookieStore());
    Instance instance = mock(Instance.class);
    when(instance.getId()).thenThrow(new ResolveEndpointException("An error occurred"));
    ClientRequest request = mock(ClientRequest.class);
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualHandleCookiesResult.filter(instance, request, mock(ExchangeFunction.class)));
    verify(instance).getId();
    verify(request).url();
  }

  /**
   * Test {@link InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}.
   *
   * <ul>
   *   <li>Then throw {@link ResolveEndpointException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceExchangeFilterFunctions#handleCookies(PerInstanceCookieStore)}
   */
  @Test
  @DisplayName("Test handleCookies(PerInstanceCookieStore); then throw ResolveEndpointException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceExchangeFilterFunction InstanceExchangeFilterFunctions.handleCookies(PerInstanceCookieStore)"
  })
  void testHandleCookies_thenThrowResolveEndpointException3() {
    // Arrange and Act
    InstanceExchangeFilterFunction actualHandleCookiesResult =
        InstanceExchangeFilterFunctions.handleCookies(new JdkPerInstanceCookieStore());
    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    ClientRequest request = mock(ClientRequest.class);
    when(request.headers()).thenReturn(new HttpHeaders());
    when(request.url()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any()))
        .thenThrow(new ResolveEndpointException("An error occurred"));

    // Assert
    assertThrows(
        ResolveEndpointException.class,
        () -> actualHandleCookiesResult.filter(instance, request, next));
    verify(instance).getId();
    verify(request).headers();
    verify(request, atLeast(1)).url();
    verify(next).exchange(isA(ClientRequest.class));
  }
}
