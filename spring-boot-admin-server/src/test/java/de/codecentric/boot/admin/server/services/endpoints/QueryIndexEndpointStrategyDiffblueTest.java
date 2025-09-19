package de.codecentric.boot.admin.server.services.endpoints;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.services.ApiMediaTypeHandler;
import de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy.Response;
import de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy.Response.EndpointRef;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ClientResponse.Headers;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper.HeadersWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class QueryIndexEndpointStrategyDiffblueTest {
  /**
   * Test {@link QueryIndexEndpointStrategy#detectEndpoints(Instance)}.
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#detectEndpoints(Instance)}
   */
  @Test
  @DisplayName("Test detectEndpoints(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono QueryIndexEndpointStrategy.detectEndpoints(Instance)"})
  void testDetectEndpoints() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

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

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(queryIndexEndpointStrategy.detectEndpoints(instance));
    createResult.expectComplete().verify();
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#detectEndpoints(Instance)}.
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#detectEndpoints(Instance)}
   */
  @Test
  @DisplayName("Test detectEndpoints(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono QueryIndexEndpointStrategy.detectEndpoints(Instance)"})
  void testDetectEndpoints2() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl(null)
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(queryIndexEndpointStrategy.detectEndpoints(instance));
    createResult.expectComplete().verify();
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convert(Instance, String)}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#convert(Instance, String)}
   */
  @Test
  @DisplayName("Test convert(Instance, String); then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Function QueryIndexEndpointStrategy.convert(Instance, String)"})
  void testConvert_thenCallsGetId() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));

    // Act
    Function<ClientResponse, Mono<Endpoints>> actualConvertResult =
        queryIndexEndpointStrategy.convert(instance, "https://example.org/example");
    Headers headers = mock(Headers.class);
    Optional<MediaType> ofResult =
        Optional.of(MediaType.parseMediaType(MediaType.TEXT_PLAIN_VALUE));
    when(headers.contentType()).thenReturn(ofResult);
    HeadersWrapper headersWrapper = new HeadersWrapper(headers);
    ClientResponse delegate = mock(ClientResponse.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(delegate.releaseBody()).thenReturn(channelSendOperator);
    when(delegate.headers()).thenReturn(headersWrapper);
    when(delegate.statusCode()).thenReturn(HttpStatus.OK);
    Mono<Endpoints> actualPublisher =
        actualConvertResult.apply(new ClientResponseWrapper(delegate));

    // Assert
    verify(instance).getId();
    verify(delegate, atLeast(1)).headers();
    verify(delegate).releaseBody();
    verify(delegate).statusCode();
    verify(headers, atLeast(1)).contentType();
    verify(builder).build();
    FirstStep<Endpoints> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}.
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId,
   * String)}
   */
  @Test
  @DisplayName("Test alignWithManagementUrl(InstanceId, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Function QueryIndexEndpointStrategy.alignWithManagementUrl(InstanceId, String)"
  })
  void testAlignWithManagementUrl() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult =
        queryIndexEndpointStrategy.alignWithManagementUrl(
            InstanceId.of("42"), "https://example.org/example");
    Endpoints singleResult = Endpoints.single("42", "https://example.org/example");
    Endpoints actualApplyResult = actualAlignWithManagementUrlResult.apply(singleResult);

    // Assert
    verify(builder).build();
    assertSame(singleResult, actualApplyResult);
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}.
   *
   * <ul>
   *   <li>Then return apply empty is empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId,
   * String)}
   */
  @Test
  @DisplayName("Test alignWithManagementUrl(InstanceId, String); then return apply empty is empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Function QueryIndexEndpointStrategy.alignWithManagementUrl(InstanceId, String)"
  })
  void testAlignWithManagementUrl_thenReturnApplyEmptyIsEmpty() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult =
        queryIndexEndpointStrategy.alignWithManagementUrl(
            InstanceId.of("42"), "https://example.org/example");
    Endpoints emptyResult = Endpoints.empty();
    Endpoints actualApplyResult = actualAlignWithManagementUrlResult.apply(emptyResult);

    // Assert
    verify(builder).build();
    assertSame(emptyResult, actualApplyResult);
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}.
   *
   * <ul>
   *   <li>Then return apply single {@code 42} and {@code http:} iterator next Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId,
   * String)}
   */
  @Test
  @DisplayName(
      "Test alignWithManagementUrl(InstanceId, String); then return apply single '42' and 'http:' iterator next Id is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Function QueryIndexEndpointStrategy.alignWithManagementUrl(InstanceId, String)"
  })
  void testAlignWithManagementUrl_thenReturnApplySingle42AndHttpIteratorNextIdIs42() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult =
        queryIndexEndpointStrategy.alignWithManagementUrl(
            InstanceId.of("42"), "https://example.org/example");
    Endpoints actualApplyResult =
        actualAlignWithManagementUrlResult.apply(Endpoints.single("42", "http:"));

    // Assert
    verify(builder).build();
    Iterator<Endpoint> iteratorResult = actualApplyResult.iterator();
    Endpoint nextResult = iteratorResult.next();
    assertEquals("42", nextResult.getId());
    assertEquals("https:", nextResult.getUrl());
    Stream<Endpoint> streamResult = actualApplyResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(1, collectResult.size());
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, collectResult.get(0));
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}.
   *
   * <ul>
   *   <li>When {@code Management Url}.
   *   <li>Then return apply empty is empty.
   * </ul>
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId,
   * String)}
   */
  @Test
  @DisplayName(
      "Test alignWithManagementUrl(InstanceId, String); when 'Management Url'; then return apply empty is empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Function QueryIndexEndpointStrategy.alignWithManagementUrl(InstanceId, String)"
  })
  void testAlignWithManagementUrl_whenManagementUrl_thenReturnApplyEmptyIsEmpty() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult =
        queryIndexEndpointStrategy.alignWithManagementUrl(InstanceId.of("42"), "Management Url");
    Endpoints emptyResult = Endpoints.empty();
    Endpoints actualApplyResult = actualAlignWithManagementUrlResult.apply(emptyResult);

    // Assert
    verify(builder).build();
    assertSame(emptyResult, actualApplyResult);
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @DisplayName("Test convertResponse(Response)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  void testConvertResponse() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("self", new EndpointRef("self", true));
    links.put("42", new EndpointRef("self", true));
    links.put("foo", new EndpointRef("self", true));

    Response response = new Response();
    response.setLinks(links);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @DisplayName("Test convertResponse(Response)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  void testConvertResponse2() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("42", new EndpointRef("self", false));
    links.put("foo", new EndpointRef("self", false));

    Response response = new Response();
    response.setLinks(links);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult
        .assertNext(
            e -> {
              Endpoints endpoints = e;
              Iterator<Endpoint> iteratorResult = endpoints.iterator();
              Endpoint nextResult = iteratorResult.next();
              Endpoint nextResult2 = iteratorResult.next();
              assertFalse(iteratorResult.hasNext());
              assertEquals("foo", nextResult.getId());
              assertEquals("42", nextResult2.getId());
              assertEquals("self", nextResult2.getUrl());
              Stream<Endpoint> streamResult = endpoints.stream();
              List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
              assertEquals(2, collectResult.size());
              assertSame(nextResult2, collectResult.get(1));
              return;
            })
        .expectComplete()
        .verify();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link EndpointRef#EndpointRef(String,
   *       boolean)} with href is {@code self} and templated is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @DisplayName(
      "Test convertResponse(Response); given HashMap() '42' is EndpointRef(String, boolean) with href is 'self' and templated is 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  void testConvertResponse_givenHashMap42IsEndpointRefWithHrefIsSelfAndTemplatedIsFalse()
      throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("42", new EndpointRef("self", false));
    links.put("foo", new EndpointRef("self", true));

    Response response = new Response();
    response.setLinks(links);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult
        .assertNext(
            e -> {
              Endpoints endpoints = e;
              Iterator<Endpoint> iteratorResult = endpoints.iterator();
              Endpoint nextResult = iteratorResult.next();
              assertFalse(iteratorResult.hasNext());
              assertEquals("42", nextResult.getId());
              assertEquals("self", nextResult.getUrl());
              Stream<Endpoint> streamResult = endpoints.stream();
              assertEquals(1, streamResult.limit(5).collect(Collectors.toList()).size());
              return;
            })
        .expectComplete()
        .verify();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link EndpointRef#EndpointRef(String,
   *       boolean)} with href is {@code self} and templated is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @DisplayName(
      "Test convertResponse(Response); given HashMap() '42' is EndpointRef(String, boolean) with href is 'self' and templated is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  void testConvertResponse_givenHashMap42IsEndpointRefWithHrefIsSelfAndTemplatedIsTrue()
      throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("42", new EndpointRef("self", true));
    links.put("foo", new EndpointRef("self", true));

    Response response = new Response();
    response.setLinks(links);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link EndpointRef#EndpointRef(String,
   *       boolean)} with {@code Href} and templated is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @DisplayName(
      "Test convertResponse(Response); given HashMap() 'foo' is EndpointRef(String, boolean) with 'Href' and templated is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  void testConvertResponse_givenHashMapFooIsEndpointRefWithHrefAndTemplatedIsTrue()
      throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("foo", new EndpointRef("Href", true));

    Response response = new Response();
    response.setLinks(links);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @DisplayName("Test convertResponse(Response); given HashMap(); then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  void testConvertResponse_givenHashMap_thenCallsBuild() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy =
        new QueryIndexEndpointStrategy(instanceWebClient, new ApiMediaTypeHandler());

    Response response = new Response();
    response.setLinks(new HashMap<>());

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test Response_EndpointRef {@link EndpointRef#EndpointRef(String, boolean)}.
   *
   * <p>Method under test: {@link EndpointRef#EndpointRef(String, boolean)}
   */
  @Test
  @DisplayName("Test Response_EndpointRef new EndpointRef(String, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndpointRef.<init>(String, boolean)"})
  void testResponse_EndpointRefNewEndpointRef() {
    // Arrange and Act
    EndpointRef actualEndpointRef = new EndpointRef("Href", true);

    // Assert
    assertEquals("Href", actualEndpointRef.getHref());
    assertTrue(actualEndpointRef.isTemplated());
  }
}
