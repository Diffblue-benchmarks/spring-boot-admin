package de.codecentric.boot.admin.server.web.servlet;

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
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.web.InstanceWebProxy;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.InstanceResponse;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class InstancesProxyControllerDiffblueTest {
  /**
   * Test {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}.
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String,
   * HttpServletRequest)}
   */
  @Test
  @DisplayName("Test endpointProxy(String, HttpServletRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, HttpServletRequest)"})
  void testEndpointProxy() throws AssertionError {
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

    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("Str"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(
            instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(servletRequest)
        .getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(builder).build();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}.
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String,
   * HttpServletRequest)}
   */
  @Test
  @DisplayName("Test endpointProxy(String, HttpServletRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, HttpServletRequest)"})
  void testEndpointProxy2() throws AssertionError {
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

    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("Str"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(
            instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(servletRequest)
        .getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(builder).build();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String,
   * HttpServletRequest)}
   */
  @Test
  @DisplayName("Test endpointProxy(String, HttpServletRequest); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, HttpServletRequest)"})
  void testEndpointProxy_thenCallsFindAll() throws AssertionError {
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

    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("Str"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(
            instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(servletRequest)
        .getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(builder).build();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findByName(String)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String,
   * HttpServletRequest)}
   */
  @Test
  @DisplayName("Test endpointProxy(String, HttpServletRequest); then calls findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, HttpServletRequest)"})
  void testEndpointProxy_thenCallsFindByName() throws AssertionError {
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

    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("Str"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(
            instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(repository).findByName("Application Name");
    verify(servletRequest)
        .getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(builder).build();
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceRegistry#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesProxyController#endpointProxy(String,
   * HttpServletRequest)}
   */
  @Test
  @DisplayName("Test endpointProxy(String, HttpServletRequest); then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, HttpServletRequest)"})
  void testEndpointProxy_thenCallsGetInstances() throws AssertionError {
    // Arrange
    InstanceRegistry registry = mock(InstanceRegistry.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(registry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    InstancesProxyController instancesProxyController =
        new InstancesProxyController(
            "Admin Context Path", new HashSet<>(), registry, instanceWebClient);

    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("Str"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(
            instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(registry).getInstances("Application Name");
    verify(servletRequest)
        .getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(builder).build();
  }
}
