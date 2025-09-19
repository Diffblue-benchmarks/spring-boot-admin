package de.codecentric.boot.admin.server.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.ForwardRequest;
import de.codecentric.boot.admin.server.web.InstanceWebProxy.InstanceResponse;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.util.ArrayList;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class InstanceWebProxyDiffblueTest {
  /**
   * Test {@link InstanceWebProxy#forward(Mono, ForwardRequest, Function)} with {@code
   * instanceMono}, {@code forwardRequest}, {@code responseHandler}.
   *
   * <p>Method under test: {@link InstanceWebProxy#forward(Mono, ForwardRequest, Function)}
   */
  @Test
  @DisplayName(
      "Test forward(Mono, ForwardRequest, Function) with 'instanceMono', 'forwardRequest', 'responseHandler'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceWebProxy.forward(Mono, ForwardRequest, Function)"})
  void testForwardWithInstanceMonoForwardRequestResponseHandler() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    InstanceWebProxy instanceWebProxy = new InstanceWebProxy(instanceWebClient);
    Mono<Instance> instanceMono = Mono.just(mock(Instance.class));
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    ForwardRequest forwardRequest =
        new ForwardRequest(
            PagerdutyNotifier.DEFAULT_URI, method, new HttpHeaders(), mock(BodyInserter.class));

    // Act and Assert
    FirstStep<Object> createResult =
        StepVerifier.create(
            instanceWebProxy.forward(instanceMono, forwardRequest, mock(Function.class)));
    createResult.expectError().verify();
    verify(builder).build();
  }

  /**
   * Test {@link InstanceWebProxy#forward(Flux, ForwardRequest)} with {@code instances}, {@code
   * forwardRequest}.
   *
   * <p>Method under test: {@link InstanceWebProxy#forward(Flux, ForwardRequest)}
   */
  @Test
  @DisplayName("Test forward(Flux, ForwardRequest) with 'instances', 'forwardRequest'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceWebProxy.forward(Flux, ForwardRequest)"})
  void testForwardWithInstancesForwardRequest() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    InstanceWebProxy instanceWebProxy = new InstanceWebProxy(instanceWebClient);
    Flux<Instance> instances = Flux.fromIterable(new ArrayList<>());
    HttpMethod method = HttpMethod.valueOf("https://example.org/example");
    ForwardRequest forwardRequest =
        new ForwardRequest(
            PagerdutyNotifier.DEFAULT_URI, method, new HttpHeaders(), mock(BodyInserter.class));

    // Act and Assert
    FirstStep<InstanceResponse> createResult =
        StepVerifier.create(instanceWebProxy.forward(instances, forwardRequest));
    createResult.expectComplete().verify();
    verify(builder).build();
  }
}
