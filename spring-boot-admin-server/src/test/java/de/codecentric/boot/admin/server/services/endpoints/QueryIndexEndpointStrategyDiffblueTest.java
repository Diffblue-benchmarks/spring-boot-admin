package de.codecentric.boot.admin.server.services.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.services.ApiMediaTypeHandler;
import de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy.Response;
import de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy.Response.EndpointRef;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

public class QueryIndexEndpointStrategyDiffblueTest {
  /**
   * Test {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}.
   * <p>
   * Method under test: {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Function QueryIndexEndpointStrategy.alignWithManagementUrl(InstanceId, String)"})
  public void testAlignWithManagementUrl() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult = queryIndexEndpointStrategy
        .alignWithManagementUrl(InstanceId.of("42"), "https://example.org/example");
    Endpoints singleResult = Endpoints.single("42", "https://example.org/example");
    Endpoints actualApplyResult = actualAlignWithManagementUrlResult.apply(singleResult);

    // Assert
    verify(builder).build();
    assertSame(singleResult, actualApplyResult);
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}.
   * <ul>
   *   <li>Then return apply empty is empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Function QueryIndexEndpointStrategy.alignWithManagementUrl(InstanceId, String)"})
  public void testAlignWithManagementUrl_thenReturnApplyEmptyIsEmpty() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult = queryIndexEndpointStrategy
        .alignWithManagementUrl(InstanceId.of("42"), "https://example.org/example");
    Endpoints emptyResult = Endpoints.empty();
    Endpoints actualApplyResult = actualAlignWithManagementUrlResult.apply(emptyResult);

    // Assert
    verify(builder).build();
    assertSame(emptyResult, actualApplyResult);
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}.
   * <ul>
   *   <li>When {@code Management Url}.</li>
   *   <li>Then return apply empty is empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Function QueryIndexEndpointStrategy.alignWithManagementUrl(InstanceId, String)"})
  public void testAlignWithManagementUrl_whenManagementUrl_thenReturnApplyEmptyIsEmpty() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult = queryIndexEndpointStrategy
        .alignWithManagementUrl(InstanceId.of("42"), "Management Url");
    Endpoints emptyResult = Endpoints.empty();
    Endpoints actualApplyResult = actualAlignWithManagementUrlResult.apply(emptyResult);

    // Assert
    verify(builder).build();
    assertSame(emptyResult, actualApplyResult);
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   * <p>
   * Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  public void testConvertResponse() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("42", new EndpointRef("Href", true));
    links.putIfAbsent("foo", new EndpointRef("Href", true));

    Response response = new Response();
    response.setLinks(links);

    // Act and Assert
    FirstStep<Endpoints> createResult = StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   * <p>
   * Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  public void testConvertResponse2() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("42", new EndpointRef("Href", true));
    links.putIfAbsent("self", new EndpointRef("Href", true));

    Response response = new Response();
    response.setLinks(links);

    // Act and Assert
    FirstStep<Endpoints> createResult = StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   * <p>
   * Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  public void testConvertResponse3() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("42", new EndpointRef("Href", true));
    links.putIfAbsent("foo", new EndpointRef("Href", false));

    Response response = new Response();
    response.setLinks(links);

    // Act and Assert
    FirstStep<Endpoints> createResult = StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.assertNext(e -> {
      Endpoints endpoints = e;
      Iterator<Endpoint> iteratorResult = endpoints.iterator();
      Endpoint nextResult = iteratorResult.next();
      assertFalse(iteratorResult.hasNext());
      assertEquals("foo", nextResult.getId());
      assertEquals("Href", nextResult.getUrl());
      Stream<Endpoint> streamResult = endpoints.stream();
      assertEquals(1, streamResult.limit(5).collect(Collectors.toList()).size());
      return;
    }).expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link EndpointRef#EndpointRef(String, boolean)} with {@code Href} and templated is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  public void testConvertResponse_givenHashMapFooIsEndpointRefWithHrefAndTemplatedIsTrue() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("foo", new EndpointRef("Href", true));

    Response response = new Response();
    response.setLinks(links);

    // Act and Assert
    FirstStep<Endpoints> createResult = StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link QueryIndexEndpointStrategy#convertResponse(Response)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link Builder#build()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link QueryIndexEndpointStrategy#convertResponse(Response)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono QueryIndexEndpointStrategy.convertResponse(Response)"})
  public void testConvertResponse_givenHashMap_thenCallsBuild() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    Response response = new Response();
    response.setLinks(new HashMap<>());

    // Act and Assert
    FirstStep<Endpoints> createResult = StepVerifier.create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test Response {@link Response#equals(Object)}, and {@link Response#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Response#equals(Object)}
   *   <li>{@link Response#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Response.equals(Object)", "int Response.hashCode()"})
  public void testResponseEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Response response = new Response();
    response.setLinks(new HashMap<>());

    Response response2 = new Response();
    response2.setLinks(new HashMap<>());

    // Act and Assert
    assertEquals(response, response2);
    int expectedHashCodeResult = response.hashCode();
    assertEquals(expectedHashCodeResult, response2.hashCode());
  }

  /**
   * Test Response {@link Response#equals(Object)}, and {@link Response#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Response#equals(Object)}
   *   <li>{@link Response#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Response.equals(Object)", "int Response.hashCode()"})
  public void testResponseEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Response response = new Response();
    response.setLinks(new HashMap<>());

    // Act and Assert
    assertEquals(response, response);
    int expectedHashCodeResult = response.hashCode();
    assertEquals(expectedHashCodeResult, response.hashCode());
  }

  /**
   * Test Response {@link Response#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Response#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Response.equals(Object)", "int Response.hashCode()"})
  public void testResponseEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HashMap<String, EndpointRef> links = new HashMap<>();
    links.put("foo", new EndpointRef("Href", true));

    Response response = new Response();
    response.setLinks(links);

    Response response2 = new Response();
    response2.setLinks(new HashMap<>());

    // Act and Assert
    assertNotEquals(response, response2);
  }

  /**
   * Test Response {@link Response#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Response#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Response.equals(Object)", "int Response.hashCode()"})
  public void testResponseEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Response response = new Response();
    response.setLinks(new HashMap<>());

    // Act and Assert
    assertNotEquals(response, null);
  }

  /**
   * Test Response {@link Response#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Response#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Response.equals(Object)", "int Response.hashCode()"})
  public void testResponseEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Response response = new Response();
    response.setLinks(new HashMap<>());

    // Act and Assert
    assertNotEquals(response, "Different type to Response");
  }

  /**
   * Test Response getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Response}
   *   <li>{@link Response#setLinks(Map)}
   *   <li>{@link Response#toString()}
   *   <li>{@link Response#getLinks()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Response.<init>()", "Map Response.getLinks()", "void Response.setLinks(Map)",
      "String Response.toString()"})
  public void testResponseGettersAndSetters() {
    // Arrange and Act
    Response actualResponse = new Response();
    HashMap<String, EndpointRef> links = new HashMap<>();
    actualResponse.setLinks(links);
    String actualToStringResult = actualResponse.toString();
    Map<String, EndpointRef> actualLinks = actualResponse.getLinks();

    // Assert
    assertEquals("QueryIndexEndpointStrategy.Response(links={})", actualToStringResult);
    assertTrue(actualLinks.isEmpty());
    assertSame(links, actualLinks);
  }

  /**
   * Test Response_EndpointRef {@link EndpointRef#equals(Object)}, and {@link EndpointRef#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EndpointRef#equals(Object)}
   *   <li>{@link EndpointRef#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointRef.equals(Object)", "int EndpointRef.hashCode()"})
  public void testResponse_EndpointRefEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    EndpointRef endpointRef = new EndpointRef("Href", true);
    EndpointRef endpointRef2 = new EndpointRef("Href", true);

    // Act and Assert
    assertEquals(endpointRef, endpointRef2);
    int expectedHashCodeResult = endpointRef.hashCode();
    assertEquals(expectedHashCodeResult, endpointRef2.hashCode());
  }

  /**
   * Test Response_EndpointRef {@link EndpointRef#equals(Object)}, and {@link EndpointRef#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EndpointRef#equals(Object)}
   *   <li>{@link EndpointRef#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointRef.equals(Object)", "int EndpointRef.hashCode()"})
  public void testResponse_EndpointRefEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    EndpointRef endpointRef = new EndpointRef("Href", true);

    // Act and Assert
    assertEquals(endpointRef, endpointRef);
    int expectedHashCodeResult = endpointRef.hashCode();
    assertEquals(expectedHashCodeResult, endpointRef.hashCode());
  }

  /**
   * Test Response_EndpointRef {@link EndpointRef#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointRef#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointRef.equals(Object)", "int EndpointRef.hashCode()"})
  public void testResponse_EndpointRefEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    EndpointRef endpointRef = new EndpointRef(
        "de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy$Response$EndpointRef", true);

    // Act and Assert
    assertNotEquals(endpointRef, new EndpointRef("Href", true));
  }

  /**
   * Test Response_EndpointRef {@link EndpointRef#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointRef#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointRef.equals(Object)", "int EndpointRef.hashCode()"})
  public void testResponse_EndpointRefEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    EndpointRef endpointRef = new EndpointRef("Href", false);

    // Act and Assert
    assertNotEquals(endpointRef, new EndpointRef("Href", true));
  }

  /**
   * Test Response_EndpointRef {@link EndpointRef#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointRef#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointRef.equals(Object)", "int EndpointRef.hashCode()"})
  public void testResponse_EndpointRefEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new EndpointRef("Href", true), null);
  }

  /**
   * Test Response_EndpointRef {@link EndpointRef#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointRef#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean EndpointRef.equals(Object)", "int EndpointRef.hashCode()"})
  public void testResponse_EndpointRefEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new EndpointRef("Href", true), "Different type to EndpointRef");
  }

  /**
   * Test Response_EndpointRef getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EndpointRef#EndpointRef(String, boolean)}
   *   <li>{@link EndpointRef#toString()}
   *   <li>{@link EndpointRef#getHref()}
   *   <li>{@link EndpointRef#isTemplated()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EndpointRef.<init>(String, boolean)", "String EndpointRef.getHref()",
      "boolean EndpointRef.isTemplated()", "String EndpointRef.toString()"})
  public void testResponse_EndpointRefGettersAndSetters() {
    // Arrange and Act
    EndpointRef actualEndpointRef = new EndpointRef("Href", true);
    String actualToStringResult = actualEndpointRef.toString();
    String actualHref = actualEndpointRef.getHref();

    // Assert
    assertEquals("Href", actualHref);
    assertEquals("QueryIndexEndpointStrategy.Response.EndpointRef(href=Href, templated=true)", actualToStringResult);
    assertTrue(actualEndpointRef.isTemplated());
  }
}
