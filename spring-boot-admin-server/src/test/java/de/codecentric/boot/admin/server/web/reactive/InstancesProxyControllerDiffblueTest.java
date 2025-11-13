package de.codecentric.boot.admin.server.web.reactive;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.web.InstanceWebProxy;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.InstanceResponse;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InstancesProxyController.class, String.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class InstancesProxyControllerDiffblueTest {
  @MockitoBean private InstanceRegistry instanceRegistry;

  @MockitoBean private InstanceWebClient instanceWebClient;

  @Autowired private InstancesProxyController instancesProxyController;

  @Autowired private Set<String> set;

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code
   * applicationName}, {@code request}.
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @DisplayName("Test endpointProxy(String, ServerHttpRequest) with 'applicationName', 'request'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  void testEndpointProxyWithApplicationNameRequest() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

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

    // Act
    Flux<InstanceResponse> actualPublisher =
        instancesProxyController.endpointProxy(
            "Application Name", new ServerHttpRequestDecorator(delegate));

    // Assert
    FirstStep<InstanceResponse> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Application Name");
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code
   * applicationName}, {@code request}.
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @DisplayName("Test endpointProxy(String, ServerHttpRequest) with 'applicationName', 'request'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  void testEndpointProxyWithApplicationNameRequest2() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    HashSet<String> ignoredHeaders = new HashSet<>();
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstancesProxyController instancesProxyController =
        new InstancesProxyController(
            "Admin Context Path", ignoredHeaders, registry, instanceWebClient);

    PathContainer pathContainer = mock(PathContainer.class);
    when(pathContainer.value()).thenReturn("https://example.org/example");

    RequestPath requestPath = mock(RequestPath.class);
    when(requestPath.pathWithinApplication()).thenReturn(pathContainer);

    ServerHttpRequestDecorator delegate = mock(ServerHttpRequestDecorator.class);
    when(delegate.getHeaders()).thenReturn(new HttpHeaders());
    when(delegate.getMethod()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(delegate.getURI()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(delegate.getPath()).thenReturn(requestPath);
    Flux<DataBuffer> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(delegate.getBody()).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(
            instancesProxyController.endpointProxy(
                "Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(builder).build();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code
   * applicationName}, {@code request}.
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @DisplayName("Test endpointProxy(String, ServerHttpRequest) with 'applicationName', 'request'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  void testEndpointProxyWithApplicationNameRequest3() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    HashSet<String> ignoredHeaders = new HashSet<>();
    InstanceRegistry registry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstancesProxyController instancesProxyController =
        new InstancesProxyController(
            "Admin Context Path", ignoredHeaders, registry, instanceWebClient);

    PathContainer pathContainer = mock(PathContainer.class);
    when(pathContainer.value()).thenReturn("https://example.org/example");

    RequestPath requestPath = mock(RequestPath.class);
    when(requestPath.pathWithinApplication()).thenReturn(pathContainer);

    ServerHttpRequestDecorator delegate = mock(ServerHttpRequestDecorator.class);
    when(delegate.getHeaders()).thenReturn(new HttpHeaders());
    when(delegate.getMethod()).thenReturn(HttpMethod.valueOf("https://example.org/example"));
    when(delegate.getURI()).thenReturn(PagerdutyNotifier.DEFAULT_URI);
    when(delegate.getPath()).thenReturn(requestPath);
    Flux<DataBuffer> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(delegate.getBody()).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(
            instancesProxyController.endpointProxy(
                "Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(builder).build();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code
   * applicationName}, {@code request}.
   *
   * <ul>
   *   <li>Given {@link HttpHeaders#HttpHeaders()} add {@code Header Name} and {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @DisplayName(
      "Test endpointProxy(String, ServerHttpRequest) with 'applicationName', 'request'; given HttpHeaders() add 'Header Name' and '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  void testEndpointProxyWithApplicationNameRequest_givenHttpHeadersAddHeaderNameAnd42()
      throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

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

    // Act
    Flux<InstanceResponse> actualPublisher =
        instancesProxyController.endpointProxy(
            "Application Name", new ServerHttpRequestDecorator(delegate));

    // Assert
    FirstStep<InstanceResponse> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Application Name");
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code
   * applicationName}, {@code request}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @DisplayName(
      "Test endpointProxy(String, ServerHttpRequest) with 'applicationName', 'request'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  void testEndpointProxyWithApplicationNameRequest_thenCallsFindAll() throws AssertionError {
    // Arrange
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    InstancesProxyController instancesProxyController =
        new InstancesProxyController(
            "Admin Context Path", new HashSet<>(), registry, instanceWebClient);

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
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(
            instancesProxyController.endpointProxy(
                "Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(builder).build();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code
   * applicationName}, {@code request}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findByName(String)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @DisplayName(
      "Test endpointProxy(String, ServerHttpRequest) with 'applicationName', 'request'; then calls findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  void testEndpointProxyWithApplicationNameRequest_thenCallsFindByName() throws AssertionError {
    // Arrange
    InstanceRepository repository = mock(InstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    InstancesProxyController instancesProxyController =
        new InstancesProxyController(
            "Admin Context Path", new HashSet<>(), registry, instanceWebClient);

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
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(
            instancesProxyController.endpointProxy(
                "Application Name", new ServerHttpRequestDecorator(delegate)));
    createResult.expectComplete().verify();
    verify(repository).findByName("Application Name");
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
    verify(builder).build();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)} with {@code
   * applicationName}, {@code request}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceRegistry#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String, ServerHttpRequest)}
   */
  @Test
  @DisplayName(
      "Test endpointProxy(String, ServerHttpRequest) with 'applicationName', 'request'; then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, ServerHttpRequest)"})
  void testEndpointProxyWithApplicationNameRequest_thenCallsGetInstances() throws AssertionError {
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

    // Act
    Flux<InstanceResponse> actualPublisher =
        instancesProxyController.endpointProxy(
            "Application Name", new ServerHttpRequestDecorator(delegate));

    // Assert
    FirstStep<InstanceResponse> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Application Name");
    verify(pathContainer).value();
    verify(requestPath).pathWithinApplication();
    verify(delegate).getBody();
    verify(delegate).getHeaders();
    verify(delegate).getMethod();
    verify(delegate).getPath();
    verify(delegate).getURI();
  }
}
