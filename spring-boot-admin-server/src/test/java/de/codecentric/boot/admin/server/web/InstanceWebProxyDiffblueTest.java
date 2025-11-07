package de.codecentric.boot.admin.server.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.ForwardRequest;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.ForwardRequest.Builder;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.InstanceResponse;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.net.URI;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {Builder.class, InstanceResponse.Builder.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class InstanceWebProxyDiffblueTest {
  @Autowired
  private Builder builder;

  /**
   * Test ForwardRequest {@link ForwardRequest#equals(Object)}, and {@link ForwardRequest#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ForwardRequest#equals(Object)}
   *   <li>{@link ForwardRequest#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ForwardRequest.equals(Object)", "int ForwardRequest.hashCode()"})
  public void testForwardRequestEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Builder bodyResult = ForwardRequest.builder().body(mock(BodyInserter.class));
    Builder headersResult = bodyResult.headers(new HttpHeaders());
    ForwardRequest buildResult = headersResult.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test ForwardRequest {@link ForwardRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ForwardRequest#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ForwardRequest.equals(Object)", "int ForwardRequest.hashCode()"})
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Builder bodyResult = ForwardRequest.builder().body(mock(BodyInserter.class));
    Builder headersResult = bodyResult.headers(new HttpHeaders());
    ForwardRequest buildResult = headersResult.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();
    Builder bodyResult2 = ForwardRequest.builder().body(mock(BodyInserter.class));
    Builder headersResult2 = bodyResult2.headers(new HttpHeaders());
    ForwardRequest buildResult2 = headersResult2.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test ForwardRequest {@link ForwardRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ForwardRequest#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ForwardRequest.equals(Object)", "int ForwardRequest.hashCode()"})
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.body(Mockito.<BodyInserter<Object, ClientHttpRequest>>any())).thenReturn(ForwardRequest.builder());
    Builder bodyResult = builder.body(mock(BodyInserter.class));
    Builder headersResult = bodyResult.headers(new HttpHeaders());
    ForwardRequest buildResult = headersResult.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();
    Builder bodyResult2 = ForwardRequest.builder().body(mock(BodyInserter.class));
    Builder headersResult2 = bodyResult2.headers(new HttpHeaders());
    ForwardRequest buildResult2 = headersResult2.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test ForwardRequest {@link ForwardRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ForwardRequest#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ForwardRequest.equals(Object)", "int ForwardRequest.hashCode()"})
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.headers(Mockito.<HttpHeaders>any())).thenReturn(ForwardRequest.builder());
    Builder builder2 = mock(Builder.class);
    when(builder2.body(Mockito.<BodyInserter<Object, ClientHttpRequest>>any())).thenReturn(builder);
    Builder bodyResult = builder2.body(mock(BodyInserter.class));
    Builder headersResult = bodyResult.headers(new HttpHeaders());
    ForwardRequest buildResult = headersResult.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();
    Builder bodyResult2 = ForwardRequest.builder().body(mock(BodyInserter.class));
    Builder headersResult2 = bodyResult2.headers(new HttpHeaders());
    ForwardRequest buildResult2 = headersResult2.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test ForwardRequest {@link ForwardRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ForwardRequest#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ForwardRequest.equals(Object)", "int ForwardRequest.hashCode()"})
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.method(Mockito.<HttpMethod>any())).thenReturn(ForwardRequest.builder());
    Builder builder2 = mock(Builder.class);
    when(builder2.headers(Mockito.<HttpHeaders>any())).thenReturn(builder);
    Builder builder3 = mock(Builder.class);
    when(builder3.body(Mockito.<BodyInserter<Object, ClientHttpRequest>>any())).thenReturn(builder2);
    Builder bodyResult = builder3.body(mock(BodyInserter.class));
    Builder headersResult = bodyResult.headers(new HttpHeaders());
    ForwardRequest buildResult = headersResult.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();
    Builder bodyResult2 = ForwardRequest.builder().body(mock(BodyInserter.class));
    Builder headersResult2 = bodyResult2.headers(new HttpHeaders());
    ForwardRequest buildResult2 = headersResult2.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test ForwardRequest {@link ForwardRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ForwardRequest#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ForwardRequest.equals(Object)", "int ForwardRequest.hashCode()"})
  public void testForwardRequestEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.uri(Mockito.<URI>any())).thenReturn(ForwardRequest.builder());
    Builder builder2 = mock(Builder.class);
    when(builder2.method(Mockito.<HttpMethod>any())).thenReturn(builder);
    Builder builder3 = mock(Builder.class);
    when(builder3.headers(Mockito.<HttpHeaders>any())).thenReturn(builder2);
    Builder builder4 = mock(Builder.class);
    when(builder4.body(Mockito.<BodyInserter<Object, ClientHttpRequest>>any())).thenReturn(builder3);
    Builder bodyResult = builder4.body(mock(BodyInserter.class));
    Builder headersResult = bodyResult.headers(new HttpHeaders());
    ForwardRequest buildResult = headersResult.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();
    Builder bodyResult2 = ForwardRequest.builder().body(mock(BodyInserter.class));
    Builder headersResult2 = bodyResult2.headers(new HttpHeaders());
    ForwardRequest buildResult2 = headersResult2.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test ForwardRequest {@link ForwardRequest#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ForwardRequest#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ForwardRequest.equals(Object)", "int ForwardRequest.hashCode()"})
  public void testForwardRequestEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Builder bodyResult = ForwardRequest.builder().body(mock(BodyInserter.class));
    Builder headersResult = bodyResult.headers(new HttpHeaders());
    ForwardRequest buildResult = headersResult.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test ForwardRequest {@link ForwardRequest#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ForwardRequest#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ForwardRequest.equals(Object)", "int ForwardRequest.hashCode()"})
  public void testForwardRequestEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Builder bodyResult = ForwardRequest.builder().body(mock(BodyInserter.class));
    Builder headersResult = bodyResult.headers(new HttpHeaders());
    ForwardRequest buildResult = headersResult.method(HttpMethod.valueOf("https://example.org/example"))
        .uri(PagerdutyNotifier.DEFAULT_URI)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to ForwardRequest");
  }

  /**
   * Test ForwardRequest getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ForwardRequest#ForwardRequest(URI, HttpMethod, HttpHeaders, BodyInserter)}
   *   <li>{@link ForwardRequest#toString()}
   *   <li>{@link ForwardRequest#getBody()}
   *   <li>{@link ForwardRequest#getHeaders()}
   *   <li>{@link ForwardRequest#getMethod()}
   *   <li>{@link ForwardRequest#getUri()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ForwardRequest.<init>(URI, HttpMethod, HttpHeaders, BodyInserter)",
      "BodyInserter ForwardRequest.getBody()", "HttpHeaders ForwardRequest.getHeaders()",
      "HttpMethod ForwardRequest.getMethod()", "URI ForwardRequest.getUri()", "String ForwardRequest.toString()"})
  public void testForwardRequestGettersAndSetters() {
    // Arrange
    URI uri = PagerdutyNotifier.DEFAULT_URI;
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    HttpHeaders headers = new HttpHeaders();
    BodyInserter<Object, ClientHttpRequest> body = mock(BodyInserter.class);

    // Act
    ForwardRequest actualForwardRequest = new ForwardRequest(uri, method, headers, body);
    actualForwardRequest.toString();
    BodyInserter<?, ? super ClientHttpRequest> actualBody = actualForwardRequest.getBody();
    HttpHeaders actualHeaders = actualForwardRequest.getHeaders();
    HttpMethod actualMethod = actualForwardRequest.getMethod();
    URI actualUri = actualForwardRequest.getUri();

    // Assert
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualUri.toString());
    assertSame(headers, actualHeaders);
    assertSame(method, actualMethod);
    assertSame(uri, actualUri);
    assertSame(body, actualBody);
  }

  /**
   * Test ForwardRequest_Builder {@link ForwardRequest.Builder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ForwardRequest.Builder#build()}
   *   <li>{@link ForwardRequest.Builder#body(BodyInserter)}
   *   <li>{@link ForwardRequest.Builder#headers(HttpHeaders)}
   *   <li>{@link ForwardRequest.Builder#method(HttpMethod)}
   *   <li>{@link ForwardRequest.Builder#uri(URI)}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ForwardRequest.Builder.<init>()",
      "ForwardRequest.Builder ForwardRequest.Builder.body(BodyInserter)",
      "ForwardRequest ForwardRequest.Builder.build()",
      "ForwardRequest.Builder ForwardRequest.Builder.headers(HttpHeaders)",
      "ForwardRequest.Builder ForwardRequest.Builder.method(HttpMethod)", "String ForwardRequest.Builder.toString()",
      "ForwardRequest.Builder ForwardRequest.Builder.uri(URI)"})
  public void testForwardRequest_BuilderBuild() {
    // Arrange
    Builder bodyResult = ForwardRequest.builder().body(mock(BodyInserter.class));
    HttpHeaders headers = new HttpHeaders();
    Builder headersResult = bodyResult.headers(headers);
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");

    // Act
    ForwardRequest actualBuildResult = headersResult.method(method).uri(PagerdutyNotifier.DEFAULT_URI).build();

    // Assert
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json",
        actualBuildResult.getUri().toString());
    assertSame(headers, actualBuildResult.getHeaders());
    assertSame(method, actualBuildResult.getMethod());
  }

  /**
   * Test {@link InstanceWebProxy#forward(Flux, ForwardRequest)} with {@code instances}, {@code forwardRequest}.
   * <p>
   * Method under test: {@link InstanceWebProxy#forward(Flux, ForwardRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstanceWebProxy.forward(Flux, ForwardRequest)"})
  public void testForwardWithInstancesForwardRequest() throws AssertionError {
    // Arrange
    WebClient.Builder builder = mock(WebClient.Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    InstanceWebProxy instanceWebProxy = new InstanceWebProxy(instanceWebClient);
    Flux<Instance> instances = Flux.fromIterable(new ArrayList<>());
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");

    // Act and Assert
    FirstStep<InstanceResponse> createResult = StepVerifier.create(instanceWebProxy.forward(instances,
        new ForwardRequest(PagerdutyNotifier.DEFAULT_URI, method, new HttpHeaders(), mock(BodyInserter.class))));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}, and {@link InstanceResponse#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceResponse#equals(Object)}
   *   <li>{@link InstanceResponse#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InstanceResponse.Builder contentTypeResult = InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("42")).status(1).build();
    InstanceResponse.Builder contentTypeResult2 = InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult2 = contentTypeResult2.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}, and {@link InstanceResponse#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceResponse#equals(Object)}
   *   <li>{@link InstanceResponse#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    InstanceResponse.Builder builder = mock(InstanceResponse.Builder.class);
    when(builder.body(Mockito.<String>any())).thenReturn(InstanceResponse.builder());
    InstanceResponse.Builder contentTypeResult = builder.body("Not all who wander are lost").contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("42")).status(1).build();
    InstanceResponse.Builder builder2 = mock(InstanceResponse.Builder.class);
    when(builder2.body(Mockito.<String>any())).thenReturn(InstanceResponse.builder());
    InstanceResponse.Builder contentTypeResult2 = builder2.body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult2 = contentTypeResult2.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}, and {@link InstanceResponse#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceResponse#equals(Object)}
   *   <li>{@link InstanceResponse#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceResponse.Builder contentTypeResult = InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceResponse#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceResponse.Builder builder = mock(InstanceResponse.Builder.class);
    when(builder.body(Mockito.<String>any())).thenReturn(InstanceResponse.builder());
    InstanceResponse.Builder contentTypeResult = builder.body("Not all who wander are lost").contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("42")).status(1).build();
    InstanceResponse.Builder contentTypeResult2 = InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult2 = contentTypeResult2.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceResponse#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InstanceResponse.Builder builder = mock(InstanceResponse.Builder.class);
    when(builder.body(Mockito.<String>any())).thenReturn(InstanceResponse.builder());
    InstanceResponse.Builder contentTypeResult = builder.body("Not all who wander are lost").contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("Value")).status(1).build();
    InstanceResponse.Builder contentTypeResult2 = InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult2 = contentTypeResult2.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceResponse#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    InstanceResponse.Builder builder = mock(InstanceResponse.Builder.class);
    when(builder.body(Mockito.<String>any())).thenReturn(InstanceResponse.builder());
    InstanceResponse.Builder contentTypeResult = builder.body("Not all who wander are lost").contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("42")).status(3).build();
    InstanceResponse.Builder contentTypeResult2 = InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult2 = contentTypeResult2.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceResponse#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    InstanceResponse.Builder builder = mock(InstanceResponse.Builder.class);
    when(builder.contentType(Mockito.<String>any())).thenReturn(InstanceResponse.builder());
    InstanceResponse.Builder builder2 = mock(InstanceResponse.Builder.class);
    when(builder2.body(Mockito.<String>any())).thenReturn(builder);
    InstanceResponse.Builder contentTypeResult = builder2.body("Not all who wander are lost").contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("42")).status(1).build();
    InstanceResponse.Builder builder3 = mock(InstanceResponse.Builder.class);
    when(builder3.body(Mockito.<String>any())).thenReturn(InstanceResponse.builder());
    InstanceResponse.Builder contentTypeResult2 = builder3.body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult2 = contentTypeResult2.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceResponse#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    InstanceResponse.Builder builder = mock(InstanceResponse.Builder.class);
    when(builder.instanceId(Mockito.<InstanceId>any())).thenReturn(InstanceResponse.builder());
    InstanceResponse.Builder builder2 = mock(InstanceResponse.Builder.class);
    when(builder2.contentType(Mockito.<String>any())).thenReturn(builder);
    InstanceResponse.Builder builder3 = mock(InstanceResponse.Builder.class);
    when(builder3.body(Mockito.<String>any())).thenReturn(builder2);
    InstanceResponse.Builder contentTypeResult = builder3.body("Not all who wander are lost").contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("42")).status(1).build();
    InstanceResponse.Builder builder4 = mock(InstanceResponse.Builder.class);
    when(builder4.body(Mockito.<String>any())).thenReturn(InstanceResponse.builder());
    InstanceResponse.Builder contentTypeResult2 = builder4.body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult2 = contentTypeResult2.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceResponse#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InstanceResponse.Builder contentTypeResult = InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test InstanceResponse {@link InstanceResponse#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceResponse#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceResponse.equals(Object)", "int InstanceResponse.hashCode()"})
  public void testInstanceResponseEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InstanceResponse.Builder contentTypeResult = InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceResponse buildResult = contentTypeResult.instanceId(InstanceId.of("42")).status(1).build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to InstanceResponse");
  }

  /**
   * Test InstanceResponse getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceResponse#InstanceResponse(InstanceId, int, String, String)}
   *   <li>{@link InstanceResponse#toString()}
   *   <li>{@link InstanceResponse#getBody()}
   *   <li>{@link InstanceResponse#getContentType()}
   *   <li>{@link InstanceResponse#getInstanceId()}
   *   <li>{@link InstanceResponse#getStatus()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceResponse.<init>(InstanceId, int, String, String)",
      "String InstanceResponse.getBody()", "String InstanceResponse.getContentType()",
      "InstanceId InstanceResponse.getInstanceId()", "int InstanceResponse.getStatus()",
      "String InstanceResponse.toString()"})
  public void testInstanceResponseGettersAndSetters() {
    // Arrange
    InstanceId instanceId = InstanceId.of("42");

    // Act
    InstanceResponse actualInstanceResponse = new InstanceResponse(instanceId, 1, "Not all who wander are lost",
        "text/plain");
    String actualToStringResult = actualInstanceResponse.toString();
    String actualBody = actualInstanceResponse.getBody();
    String actualContentType = actualInstanceResponse.getContentType();
    InstanceId actualInstanceId = actualInstanceResponse.getInstanceId();

    // Assert
    assertEquals("InstanceWebProxy.InstanceResponse(instanceId=42, status=1, body=Not all who wander are lost,"
        + " contentType=text/plain)", actualToStringResult);
    assertEquals("Not all who wander are lost", actualBody);
    assertEquals("text/plain", actualContentType);
    assertEquals(1, actualInstanceResponse.getStatus());
    assertSame(instanceId, actualInstanceId);
  }

  /**
   * Test InstanceResponse_Builder {@link InstanceResponse.Builder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceResponse.Builder#build()}
   *   <li>{@link InstanceResponse.Builder#body(String)}
   *   <li>{@link InstanceResponse.Builder#contentType(String)}
   *   <li>{@link InstanceResponse.Builder#instanceId(InstanceId)}
   *   <li>{@link InstanceResponse.Builder#status(int)}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceResponse.Builder.<init>()",
      "InstanceResponse.Builder InstanceResponse.Builder.body(String)",
      "InstanceResponse InstanceResponse.Builder.build()",
      "InstanceResponse.Builder InstanceResponse.Builder.contentType(String)",
      "InstanceResponse.Builder InstanceResponse.Builder.instanceId(InstanceId)",
      "InstanceResponse.Builder InstanceResponse.Builder.status(int)", "String InstanceResponse.Builder.toString()"})
  public void testInstanceResponse_BuilderBuild() {
    // Arrange
    InstanceResponse.Builder contentTypeResult = InstanceResponse.builder()
        .body("Not all who wander are lost")
        .contentType("text/plain");
    InstanceId instanceId = InstanceId.of("42");

    // Act
    InstanceResponse actualBuildResult = contentTypeResult.instanceId(instanceId).status(1).build();

    // Assert
    assertEquals("Not all who wander are lost", actualBuildResult.getBody());
    assertEquals("text/plain", actualBuildResult.getContentType());
    assertEquals(1, actualBuildResult.getStatus());
    assertSame(instanceId, actualBuildResult.getInstanceId());
  }
}
