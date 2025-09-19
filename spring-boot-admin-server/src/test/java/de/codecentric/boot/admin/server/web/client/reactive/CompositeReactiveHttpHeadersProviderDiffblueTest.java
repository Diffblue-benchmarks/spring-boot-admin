package de.codecentric.boot.admin.server.web.client.reactive;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class CompositeReactiveHttpHeadersProviderDiffblueTest {
  /**
   * Test {@link CompositeReactiveHttpHeadersProvider#getHeaders(Instance)}.
   *
   * <p>Method under test: {@link CompositeReactiveHttpHeadersProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono CompositeReactiveHttpHeadersProvider.getHeaders(Instance)"})
  void testGetHeaders() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<HttpHeaders> createResult =
        StepVerifier.create(
            new CompositeReactiveHttpHeadersProvider(new ArrayList<>())
                .getHeaders(mock(Instance.class)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link CompositeReactiveHttpHeadersProvider#getHeaders(Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link ReactiveHttpHeadersProvider#getHeaders(Instance)}.
   * </ul>
   *
   * <p>Method under test: {@link CompositeReactiveHttpHeadersProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance); then calls getHeaders(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono CompositeReactiveHttpHeadersProvider.getHeaders(Instance)"})
  void testGetHeaders_thenCallsGetHeaders() throws AssertionError {
    // Arrange
    ReactiveHttpHeadersProvider reactiveHttpHeadersProvider =
        mock(ReactiveHttpHeadersProvider.class);
    Mono<HttpHeaders> justResult = Mono.just(new HttpHeaders());
    when(reactiveHttpHeadersProvider.getHeaders(Mockito.<Instance>any())).thenReturn(justResult);

    ArrayList<ReactiveHttpHeadersProvider> delegates = new ArrayList<>();
    delegates.add(reactiveHttpHeadersProvider);

    // Act and Assert
    FirstStep<HttpHeaders> createResult =
        StepVerifier.create(
            new CompositeReactiveHttpHeadersProvider(delegates).getHeaders(mock(Instance.class)));
    createResult
        .assertNext(
            h -> {
              assertTrue(h.isEmpty());
              return;
            })
        .expectComplete()
        .verify();
    verify(reactiveHttpHeadersProvider).getHeaders(isA(Instance.class));
  }

  /**
   * Test {@link CompositeReactiveHttpHeadersProvider#getHeaders(Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link ReactiveHttpHeadersProvider#getHeaders(Instance)}.
   * </ul>
   *
   * <p>Method under test: {@link CompositeReactiveHttpHeadersProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance); then calls getHeaders(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono CompositeReactiveHttpHeadersProvider.getHeaders(Instance)"})
  void testGetHeaders_thenCallsGetHeaders2() throws AssertionError {
    // Arrange
    ReactiveHttpHeadersProvider reactiveHttpHeadersProvider =
        mock(ReactiveHttpHeadersProvider.class);
    Mono<HttpHeaders> justResult = Mono.just(new HttpHeaders());
    when(reactiveHttpHeadersProvider.getHeaders(Mockito.<Instance>any())).thenReturn(justResult);

    ReactiveHttpHeadersProvider reactiveHttpHeadersProvider2 =
        mock(ReactiveHttpHeadersProvider.class);
    Mono<HttpHeaders> justResult2 = Mono.just(new HttpHeaders());
    when(reactiveHttpHeadersProvider2.getHeaders(Mockito.<Instance>any())).thenReturn(justResult2);

    ArrayList<ReactiveHttpHeadersProvider> delegates = new ArrayList<>();
    delegates.add(reactiveHttpHeadersProvider2);
    delegates.add(reactiveHttpHeadersProvider);

    // Act and Assert
    FirstStep<HttpHeaders> createResult =
        StepVerifier.create(
            new CompositeReactiveHttpHeadersProvider(delegates).getHeaders(mock(Instance.class)));
    createResult
        .assertNext(
            h -> {
              assertTrue(h.isEmpty());
              return;
            })
        .expectComplete()
        .verify();
    verify(reactiveHttpHeadersProvider2).getHeaders(isA(Instance.class));
    verify(reactiveHttpHeadersProvider).getHeaders(isA(Instance.class));
  }
}
