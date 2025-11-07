package de.codecentric.boot.admin.server.web.reactive;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.web.InstanceWebProxy;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.InstanceResponse;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InstancesProxyController.class, String.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class InstancesProxyControllerDiffblueTest {
  @MockBean
  private InstanceRegistry instanceRegistry;

  @MockBean
  private InstanceWebClient instanceWebClient;

  @Autowired
  private InstancesProxyController instancesProxyController;

  @Autowired
  private Set<String> set;

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code applicationName}, {@code request}.
   * <p>
   * Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  public void testEndpointProxyWithApplicationNameRequest() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);
    PathContainer pathContainer = mock(PathContainer.class);
    when(pathContainer.value()).thenReturn("https://example.org/example");
    RequestPath requestPath = mock(RequestPath.class);
    when(requestPath.pathWithinApplication()).thenReturn(pathContainer);
    ServerHttpRequestDecorator delegate = mock(ServerHttpRequestDecorator.class);
    when(delegate.getHeaders()).thenReturn(new HttpHeaders());
    when(delegate.getMethod()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(delegate.getURI()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(delegate.getPath()).thenReturn(requestPath);
    Flux<DataBuffer> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(delegate.getBody()).thenReturn(fromIterableResult2);

    // Act and Assert
    FirstStep<InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Application Name"));
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code applicationName}, {@code request}.
   * <p>
   * Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  public void testEndpointProxyWithApplicationNameRequest2() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);
    PathContainer pathContainer = mock(PathContainer.class);
    when(pathContainer.value()).thenReturn("https://example.org/example");
    RequestPath requestPath = mock(RequestPath.class);
    when(requestPath.pathWithinApplication()).thenReturn(pathContainer);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("https://example.org/example", "https://example.org/example");
    ServerHttpRequestDecorator delegate = mock(ServerHttpRequestDecorator.class);
    when(delegate.getHeaders()).thenReturn(httpHeaders);
    when(delegate.getMethod()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(delegate.getURI()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(delegate.getPath()).thenReturn(requestPath);
    Flux<DataBuffer> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(delegate.getBody()).thenReturn(fromIterableResult2);

    // Act and Assert
    FirstStep<InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Application Name"));
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(flux).flatMap(isA(Function.class));
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code applicationName}, {@code request}.
   * <p>
   * Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  public void testEndpointProxyWithApplicationNameRequest3() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);
    Flux<DataBuffer> flux2 = mock(Flux.class);
    Flux<Object> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(flux2.map(Mockito.<Function<DataBuffer, Object>>any())).thenReturn(fromIterableResult2);
    PathContainer pathContainer = mock(PathContainer.class);
    when(pathContainer.value()).thenReturn("https://example.org/example");
    RequestPath requestPath = mock(RequestPath.class);
    when(requestPath.pathWithinApplication()).thenReturn(pathContainer);
    ServerHttpRequestDecorator delegate = mock(ServerHttpRequestDecorator.class);
    when(delegate.getHeaders()).thenReturn(new HttpHeaders());
    when(delegate.getMethod()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(delegate.getURI()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(delegate.getPath()).thenReturn(requestPath);
    when(delegate.getBody()).thenReturn(flux2);

    // Act and Assert
    FirstStep<InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Application Name"));
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code applicationName}, {@code request}.
   * <ul>
   *   <li>Given create.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  public void testEndpointProxyWithApplicationNameRequest_givenCreate() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);
    PathContainer pathContainer = mock(PathContainer.class);
    when(pathContainer.value()).thenReturn("https://example.org/example");
    RequestPath requestPath = mock(RequestPath.class);
    when(requestPath.pathWithinApplication()).thenReturn(pathContainer);
    ServerHttpRequestDecorator delegate = mock(ServerHttpRequestDecorator.class);
    when(delegate.getHeaders()).thenReturn(new HttpHeaders());
    when(delegate.getMethod()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(delegate.getURI()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(delegate.getPath()).thenReturn(requestPath);
    DirectProcessor<DataBuffer> createResult = DirectProcessor.create();
    when(delegate.getBody()).thenReturn(createResult);

    // Act and Assert
    FirstStep<InstanceResponse> createResult2 = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult2.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Application Name"));
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(flux).flatMap(isA(Function.class));
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code applicationName}, {@code request}.
   * <ul>
   *   <li>Given {@link HttpHeaders#HttpHeaders()} add {@code Header Name} and {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  public void testEndpointProxyWithApplicationNameRequest_givenHttpHeadersAddHeaderNameAnd42() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);
    PathContainer pathContainer = mock(PathContainer.class);
    when(pathContainer.value()).thenReturn("https://example.org/example");
    RequestPath requestPath = mock(RequestPath.class);
    when(requestPath.pathWithinApplication()).thenReturn(pathContainer);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Header Name", "42");
    httpHeaders.add("https://example.org/example", "https://example.org/example");
    ServerHttpRequestDecorator delegate = mock(ServerHttpRequestDecorator.class);
    when(delegate.getHeaders()).thenReturn(httpHeaders);
    when(delegate.getMethod()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(delegate.getURI()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(delegate.getPath()).thenReturn(requestPath);
    Flux<DataBuffer> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(delegate.getBody()).thenReturn(fromIterableResult2);

    // Act and Assert
    FirstStep<InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Application Name"));
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(flux).flatMap(isA(Function.class));
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code applicationName}, {@code request}.
   * <ul>
   *   <li>Then calls {@link Flux#cache()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  public void testEndpointProxyWithApplicationNameRequest_thenCallsCache() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);
    Flux<Object> flux2 = mock(Flux.class);
    Flux<Object> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(flux2.cache()).thenReturn(fromIterableResult2);
    Flux<DataBuffer> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<DataBuffer, Object>>any())).thenReturn(flux2);
    PathContainer pathContainer = mock(PathContainer.class);
    when(pathContainer.value()).thenReturn("https://example.org/example");
    RequestPath requestPath = mock(RequestPath.class);
    when(requestPath.pathWithinApplication()).thenReturn(pathContainer);
    ServerHttpRequestDecorator delegate = mock(ServerHttpRequestDecorator.class);
    when(delegate.getHeaders()).thenReturn(new HttpHeaders());
    when(delegate.getMethod()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(delegate.getURI()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(delegate.getPath()).thenReturn(requestPath);
    when(delegate.getBody()).thenReturn(flux3);

    // Act and Assert
    FirstStep<InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Application Name"));
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(flux2).cache();
    verify(flux).flatMap(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code applicationName}, {@code request}.
   * <ul>
   *   <li>Then calls {@link Flux#flatMap(Function)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  public void testEndpointProxyWithApplicationNameRequest_thenCallsFlatMap() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);
    PathContainer pathContainer = mock(PathContainer.class);
    when(pathContainer.value()).thenReturn("https://example.org/example");
    RequestPath requestPath = mock(RequestPath.class);
    when(requestPath.pathWithinApplication()).thenReturn(pathContainer);
    ServerHttpRequestDecorator delegate = mock(ServerHttpRequestDecorator.class);
    when(delegate.getHeaders()).thenReturn(new HttpHeaders());
    when(delegate.getMethod()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(delegate.getURI()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(delegate.getPath()).thenReturn(requestPath);
    Flux<DataBuffer> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(delegate.getBody()).thenReturn(fromIterableResult2);

    // Act and Assert
    FirstStep<InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Application Name"));
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(flux).flatMap(isA(Function.class));
  }
}
