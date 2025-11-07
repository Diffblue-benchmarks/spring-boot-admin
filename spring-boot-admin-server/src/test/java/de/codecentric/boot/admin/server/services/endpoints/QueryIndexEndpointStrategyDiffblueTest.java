package de.codecentric.boot.admin.server.services.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.services.ApiMediaTypeHandler;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import reactor.test.StepVerifier;

public class QueryIndexEndpointStrategyDiffblueTest {
  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}
   */
  @Test
  public void testAlignWithManagementUrl() {
    // Arrange
    InstanceWebClient instanceWebClient = mock(InstanceWebClient.class);
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult = queryIndexEndpointStrategy
        .alignWithManagementUrl(InstanceId.of("42"), "https://example.org/example");
    Endpoints emptyResult = Endpoints.empty();

    // Assert
    assertSame(emptyResult, actualAlignWithManagementUrlResult.apply(emptyResult));
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}
   */
  @Test
  public void testAlignWithManagementUrl2() {
    // Arrange
    InstanceWebClient instanceWebClient = mock(InstanceWebClient.class);
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult = queryIndexEndpointStrategy
        .alignWithManagementUrl(InstanceId.of("42"), "Management Url");
    Endpoints emptyResult = Endpoints.empty();

    // Assert
    assertSame(emptyResult, actualAlignWithManagementUrlResult.apply(emptyResult));
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy#alignWithManagementUrl(InstanceId, String)}
   */
  @Test
  public void testAlignWithManagementUrl3() {
    // Arrange
    InstanceWebClient instanceWebClient = mock(InstanceWebClient.class);
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    // Act
    Function<Endpoints, Endpoints> actualAlignWithManagementUrlResult = queryIndexEndpointStrategy
        .alignWithManagementUrl(InstanceId.of("42"), "https://example.org/example");
    Endpoints singleResult = Endpoints.single("42", "https://example.org/example");

    // Assert
    assertSame(singleResult, actualAlignWithManagementUrlResult.apply(singleResult));
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy#convertResponse(QueryIndexEndpointStrategy.Response)}
   */
  @Test
  public void testConvertResponse() throws AssertionError {
    // Arrange
    InstanceWebClient instanceWebClient = mock(InstanceWebClient.class);
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(new HashMap<>());

    // Act and Assert
    StepVerifier.FirstStep<Endpoints> createResult = StepVerifier
        .create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy#convertResponse(QueryIndexEndpointStrategy.Response)}
   */
  @Test
  public void testConvertResponse2() throws AssertionError {
    // Arrange
    InstanceWebClient instanceWebClient = mock(InstanceWebClient.class);
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    HashMap<String, QueryIndexEndpointStrategy.Response.EndpointRef> links = new HashMap<>();
    links.put("foo", new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true));

    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(links);

    // Act and Assert
    StepVerifier.FirstStep<Endpoints> createResult = StepVerifier
        .create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy#convertResponse(QueryIndexEndpointStrategy.Response)}
   */
  @Test
  public void testConvertResponse3() throws AssertionError {
    // Arrange
    InstanceWebClient instanceWebClient = mock(InstanceWebClient.class);
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    HashMap<String, QueryIndexEndpointStrategy.Response.EndpointRef> links = new HashMap<>();
    links.put("self", new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true));

    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(links);

    // Act and Assert
    StepVerifier.FirstStep<Endpoints> createResult = StepVerifier
        .create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy#convertResponse(QueryIndexEndpointStrategy.Response)}
   */
  @Test
  public void testConvertResponse4() throws AssertionError {
    // Arrange
    InstanceWebClient instanceWebClient = mock(InstanceWebClient.class);
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    HashMap<String, QueryIndexEndpointStrategy.Response.EndpointRef> links = new HashMap<>();
    links.put("foo", new QueryIndexEndpointStrategy.Response.EndpointRef("Href", false));

    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(links);

    // Act and Assert
    StepVerifier.FirstStep<Endpoints> createResult = StepVerifier
        .create(queryIndexEndpointStrategy.convertResponse(response));
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
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy#convertResponse(QueryIndexEndpointStrategy.Response)}
   */
  @Test
  public void testConvertResponse5() throws AssertionError {
    // Arrange
    InstanceWebClient instanceWebClient = mock(InstanceWebClient.class);
    QueryIndexEndpointStrategy queryIndexEndpointStrategy = new QueryIndexEndpointStrategy(instanceWebClient,
        new ApiMediaTypeHandler());

    HashMap<String, QueryIndexEndpointStrategy.Response.EndpointRef> links = new HashMap<>();
    links.put("42", new QueryIndexEndpointStrategy.Response.EndpointRef("self", true));
    links.put("foo", new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true));

    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(links);

    // Act and Assert
    StepVerifier.FirstStep<Endpoints> createResult = StepVerifier
        .create(queryIndexEndpointStrategy.convertResponse(response));
    createResult.expectComplete().verify();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link QueryIndexEndpointStrategy.Response#equals(Object)}
   *   <li>{@link QueryIndexEndpointStrategy.Response#hashCode()}
   * </ul>
   */
  @Test
  public void testResponseEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(new HashMap<>());

    QueryIndexEndpointStrategy.Response response2 = new QueryIndexEndpointStrategy.Response();
    response2.setLinks(new HashMap<>());

    // Act and Assert
    assertEquals(response, response2);
    int expectedHashCodeResult = response.hashCode();
    assertEquals(expectedHashCodeResult, response2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link QueryIndexEndpointStrategy.Response#equals(Object)}
   *   <li>{@link QueryIndexEndpointStrategy.Response#hashCode()}
   * </ul>
   */
  @Test
  public void testResponseEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(new HashMap<>());

    // Act and Assert
    assertEquals(response, response);
    int expectedHashCodeResult = response.hashCode();
    assertEquals(expectedHashCodeResult, response.hashCode());
  }

  /**
   * Method under test: {@link QueryIndexEndpointStrategy.Response#equals(Object)}
   */
  @Test
  public void testResponseEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HashMap<String, QueryIndexEndpointStrategy.Response.EndpointRef> links = new HashMap<>();
    links.put("foo", new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true));

    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(links);

    QueryIndexEndpointStrategy.Response response2 = new QueryIndexEndpointStrategy.Response();
    response2.setLinks(new HashMap<>());

    // Act and Assert
    assertNotEquals(response, response2);
  }

  /**
   * Method under test: {@link QueryIndexEndpointStrategy.Response#equals(Object)}
   */
  @Test
  public void testResponseEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, QueryIndexEndpointStrategy.Response.EndpointRef> links = new HashMap<>();
    links.computeIfPresent("foo", mock(BiFunction.class));
    links.put("foo", new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true));

    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(links);

    QueryIndexEndpointStrategy.Response response2 = new QueryIndexEndpointStrategy.Response();
    response2.setLinks(new HashMap<>());

    // Act and Assert
    assertNotEquals(response, response2);
  }

  /**
   * Method under test: {@link QueryIndexEndpointStrategy.Response#equals(Object)}
   */
  @Test
  public void testResponseEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(new HashMap<>());

    // Act and Assert
    assertNotEquals(response, null);
  }

  /**
   * Method under test: {@link QueryIndexEndpointStrategy.Response#equals(Object)}
   */
  @Test
  public void testResponseEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    QueryIndexEndpointStrategy.Response response = new QueryIndexEndpointStrategy.Response();
    response.setLinks(new HashMap<>());

    // Act and Assert
    assertNotEquals(response, "Different type to Response");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link QueryIndexEndpointStrategy.Response}
   *   <li>{@link QueryIndexEndpointStrategy.Response#setLinks(Map)}
   *   <li>{@link QueryIndexEndpointStrategy.Response#toString()}
   *   <li>{@link QueryIndexEndpointStrategy.Response#getLinks()}
   * </ul>
   */
  @Test
  public void testResponseGettersAndSetters() {
    // Arrange and Act
    QueryIndexEndpointStrategy.Response actualResponse = new QueryIndexEndpointStrategy.Response();
    HashMap<String, QueryIndexEndpointStrategy.Response.EndpointRef> links = new HashMap<>();
    actualResponse.setLinks(links);
    String actualToStringResult = actualResponse.toString();
    Map<String, QueryIndexEndpointStrategy.Response.EndpointRef> actualLinks = actualResponse.getLinks();

    // Assert that nothing has changed
    assertEquals("QueryIndexEndpointStrategy.Response(links={})", actualToStringResult);
    assertTrue(actualLinks.isEmpty());
    assertSame(links, actualLinks);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link QueryIndexEndpointStrategy.Response.EndpointRef#equals(Object)}
   *   <li>{@link QueryIndexEndpointStrategy.Response.EndpointRef#hashCode()}
   * </ul>
   */
  @Test
  public void testResponse_EndpointRefEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    QueryIndexEndpointStrategy.Response.EndpointRef endpointRef = new QueryIndexEndpointStrategy.Response.EndpointRef(
        "Href", true);
    QueryIndexEndpointStrategy.Response.EndpointRef endpointRef2 = new QueryIndexEndpointStrategy.Response.EndpointRef(
        "Href", true);

    // Act and Assert
    assertEquals(endpointRef, endpointRef2);
    int expectedHashCodeResult = endpointRef.hashCode();
    assertEquals(expectedHashCodeResult, endpointRef2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link QueryIndexEndpointStrategy.Response.EndpointRef#equals(Object)}
   *   <li>{@link QueryIndexEndpointStrategy.Response.EndpointRef#hashCode()}
   * </ul>
   */
  @Test
  public void testResponse_EndpointRefEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    QueryIndexEndpointStrategy.Response.EndpointRef endpointRef = new QueryIndexEndpointStrategy.Response.EndpointRef(
        null, true);
    QueryIndexEndpointStrategy.Response.EndpointRef endpointRef2 = new QueryIndexEndpointStrategy.Response.EndpointRef(
        null, true);

    // Act and Assert
    assertEquals(endpointRef, endpointRef2);
    int expectedHashCodeResult = endpointRef.hashCode();
    assertEquals(expectedHashCodeResult, endpointRef2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link QueryIndexEndpointStrategy.Response.EndpointRef#equals(Object)}
   *   <li>{@link QueryIndexEndpointStrategy.Response.EndpointRef#hashCode()}
   * </ul>
   */
  @Test
  public void testResponse_EndpointRefEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    QueryIndexEndpointStrategy.Response.EndpointRef endpointRef = new QueryIndexEndpointStrategy.Response.EndpointRef(
        "Href", true);

    // Act and Assert
    assertEquals(endpointRef, endpointRef);
    int expectedHashCodeResult = endpointRef.hashCode();
    assertEquals(expectedHashCodeResult, endpointRef.hashCode());
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy.Response.EndpointRef#equals(Object)}
   */
  @Test
  public void testResponse_EndpointRefEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    QueryIndexEndpointStrategy.Response.EndpointRef endpointRef = new QueryIndexEndpointStrategy.Response.EndpointRef(
        null, true);

    // Act and Assert
    assertNotEquals(endpointRef, new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true));
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy.Response.EndpointRef#equals(Object)}
   */
  @Test
  public void testResponse_EndpointRefEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    QueryIndexEndpointStrategy.Response.EndpointRef endpointRef = new QueryIndexEndpointStrategy.Response.EndpointRef(
        "de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy$Response$EndpointRef", true);

    // Act and Assert
    assertNotEquals(endpointRef, new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true));
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy.Response.EndpointRef#equals(Object)}
   */
  @Test
  public void testResponse_EndpointRefEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    QueryIndexEndpointStrategy.Response.EndpointRef endpointRef = new QueryIndexEndpointStrategy.Response.EndpointRef(
        "Href", false);

    // Act and Assert
    assertNotEquals(endpointRef, new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true));
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy.Response.EndpointRef#equals(Object)}
   */
  @Test
  public void testResponse_EndpointRefEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true), null);
  }

  /**
   * Method under test:
   * {@link QueryIndexEndpointStrategy.Response.EndpointRef#equals(Object)}
   */
  @Test
  public void testResponse_EndpointRefEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new QueryIndexEndpointStrategy.Response.EndpointRef("Href", true), "Different type to EndpointRef");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link QueryIndexEndpointStrategy.Response.EndpointRef#EndpointRef(String, boolean)}
   *   <li>{@link QueryIndexEndpointStrategy.Response.EndpointRef#toString()}
   *   <li>{@link QueryIndexEndpointStrategy.Response.EndpointRef#getHref()}
   *   <li>{@link QueryIndexEndpointStrategy.Response.EndpointRef#isTemplated()}
   * </ul>
   */
  @Test
  public void testResponse_EndpointRefGettersAndSetters() {
    // Arrange and Act
    QueryIndexEndpointStrategy.Response.EndpointRef actualEndpointRef = new QueryIndexEndpointStrategy.Response.EndpointRef(
        "Href", true);
    String actualToStringResult = actualEndpointRef.toString();
    String actualHref = actualEndpointRef.getHref();

    // Assert
    assertEquals("Href", actualHref);
    assertEquals("QueryIndexEndpointStrategy.Response.EndpointRef(href=Href, templated=true)", actualToStringResult);
    assertTrue(actualEndpointRef.isTemplated());
  }
}
