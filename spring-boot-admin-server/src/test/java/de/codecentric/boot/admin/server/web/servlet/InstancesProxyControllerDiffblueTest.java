package de.codecentric.boot.admin.server.web.servlet;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.web.InstanceWebProxy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.security.web.savedrequest.Enumerator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.test.StepVerifier;

public class InstancesProxyControllerDiffblueTest {
  /**
   * Method under test:
   * {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  public void testEndpointProxy() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<String> ignoredHeaders = new HashSet<>();
    InstancesProxyController instancesProxyController = new InstancesProxyController("Admin Context Path",
        ignoredHeaders, new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        null);
    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("foo"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
  }

  /**
   * Method under test:
   * {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  public void testEndpointProxy2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(eventStore),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstancesProxyController instancesProxyController = new InstancesProxyController("Admin Context Path",
        new HashSet<>(), registry, null);
    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("foo"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
  }

  /**
   * Method under test:
   * {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  public void testEndpointProxy3() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(eventStore),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstancesProxyController instancesProxyController = new InstancesProxyController("Admin Context Path",
        new HashSet<>(), registry, null);
    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("foo"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(flux).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  public void testEndpointProxy4() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(eventStore),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstancesProxyController instancesProxyController = new InstancesProxyController("Admin Context Path",
        new HashSet<>(), registry, null);
    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("foo"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  public void testEndpointProxy5() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(eventStore),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstancesProxyController instancesProxyController = new InstancesProxyController("Admin Context Path",
        new HashSet<>(), registry, null);
    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("foo"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  public void testEndpointProxy6() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(eventStore),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstancesProxyController instancesProxyController = new InstancesProxyController("Admin Context Path",
        new HashSet<>(), registry, null);
    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("foo"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  public void testEndpointProxy7() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(eventStore),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstancesProxyController instancesProxyController = new InstancesProxyController("Admin Context Path",
        new HashSet<>(), registry, null);
    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("foo"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act
    Flux<InstanceWebProxy.InstanceResponse> actualPublisher = instancesProxyController.endpointProxy("Application Name",
        servletRequest);

    // Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test:
   * {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  public void testEndpointProxy8() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<String> ignoredHeaders = new HashSet<>();
    InstancesProxyController instancesProxyController = new InstancesProxyController("Admin Context Path",
        ignoredHeaders, new InstanceRegistry(new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        null);
    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("foo"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
  }

  /**
   * Method under test:
   * {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  public void testEndpointProxy9() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceRegistry registry = mock(InstanceRegistry.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(registry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);
    InstancesProxyController instancesProxyController = new InstancesProxyController("Admin Context Path",
        new HashSet<>(), registry, null);
    HttpServletRequestWrapper servletRequest = mock(HttpServletRequestWrapper.class);
    when(servletRequest.getCharacterEncoding()).thenReturn("UTF-8");
    when(servletRequest.getContentLength()).thenReturn(3);
    when(servletRequest.getContentType()).thenReturn("text/plain");
    when(servletRequest.getHeaderNames()).thenReturn(new Enumerator<>(new ArrayList<>()));
    when(servletRequest.getMethod()).thenReturn("https://example.org/example");
    when(servletRequest.getQueryString()).thenReturn("https://example.org/example");
    when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("foo"));
    when(servletRequest.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

    // Act and Assert
    StepVerifier.FirstStep<InstanceWebProxy.InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(registry).getInstances(eq("Application Name"));
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
  }
}
