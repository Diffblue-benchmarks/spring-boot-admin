package de.codecentric.boot.admin.server.web.servlet;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.web.InstanceWebProxy;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.InstanceResponse;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
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
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
   * Test {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}.
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link Flux#flatMap(Function)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, HttpServletRequest)"})
  public void testEndpointProxy_givenFluxFlatMapReturnFromIterableArrayList_thenCallsFlatMap() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);
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
    FirstStep<InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Application Name"));
    verify(servletRequest).getAttribute(eq("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
    verify(servletRequest).getCharacterEncoding();
    verify(servletRequest).getContentLength();
    verify(servletRequest).getContentType();
    verify(servletRequest).getHeaderNames();
    verify(servletRequest).getMethod();
    verify(servletRequest).getQueryString();
    verify(servletRequest).getRequestURL();
    verify(flux).flatMap(isA(Function.class));
  }

  /**
   * Test {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}.
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances(String)} return fromIterable {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstancesProxyController#endpointProxy(String, HttpServletRequest)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstancesProxyController.endpointProxy(String, HttpServletRequest)"})
  public void testEndpointProxy_givenInstanceRegistryGetInstancesReturnFromIterableArrayList() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);
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
    FirstStep<InstanceResponse> createResult = StepVerifier
        .create(instancesProxyController.endpointProxy("Application Name", servletRequest));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Application Name"));
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
