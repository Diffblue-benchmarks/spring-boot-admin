package de.codecentric.boot.admin.server.services.endpoints;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class ChainingStrategyDiffblueTest {
  /**
   * Test {@link ChainingStrategy#ChainingStrategy(EndpointDetectionStrategy[])}.
   *
   * <ul>
   *   <li>Given just empty.
   *   <li>Then calls {@link EndpointDetectionStrategy#detectEndpoints(Instance)}.
   * </ul>
   *
   * <p>Method under test: {@link ChainingStrategy#ChainingStrategy(EndpointDetectionStrategy[])}
   */
  @Test
  @DisplayName(
      "Test new ChainingStrategy(EndpointDetectionStrategy[]); given just empty; then calls detectEndpoints(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ChainingStrategy.<init>(EndpointDetectionStrategy[])"})
  void testNewChainingStrategy_givenJustEmpty_thenCallsDetectEndpoints() throws AssertionError {
    // Arrange
    EndpointDetectionStrategy endpointDetectionStrategy = mock(EndpointDetectionStrategy.class);
    Endpoints emptyResult = Endpoints.empty();
    Mono<Endpoints> justResult = Mono.just(emptyResult);
    when(endpointDetectionStrategy.detectEndpoints(Mockito.<Instance>any())).thenReturn(justResult);
    EndpointDetectionStrategy[] delegates =
        new EndpointDetectionStrategy[] {endpointDetectionStrategy};

    // Act
    ChainingStrategy actualChainingStrategy = new ChainingStrategy(delegates);
    Mono<Endpoints> actualPublisher = actualChainingStrategy.detectEndpoints(mock(Instance.class));

    // Assert
    verify(endpointDetectionStrategy).detectEndpoints(isA(Instance.class));
    assertEquals(1, delegates.length);
    FirstStep<Endpoints> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            e -> {
              assertSame(emptyResult, e);
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link ChainingStrategy#ChainingStrategy(EndpointDetectionStrategy[])}.
   *
   * <ul>
   *   <li>When {@link EndpointDetectionStrategy}.
   *   <li>Then array length is one.
   * </ul>
   *
   * <p>Method under test: {@link ChainingStrategy#ChainingStrategy(EndpointDetectionStrategy[])}
   */
  @Test
  @DisplayName(
      "Test new ChainingStrategy(EndpointDetectionStrategy[]); when EndpointDetectionStrategy; then array length is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ChainingStrategy.<init>(EndpointDetectionStrategy[])"})
  void testNewChainingStrategy_whenEndpointDetectionStrategy_thenArrayLengthIsOne() {
    // Arrange
    EndpointDetectionStrategy[] delegates =
        new EndpointDetectionStrategy[] {mock(EndpointDetectionStrategy.class)};

    // Act
    new ChainingStrategy(delegates);

    // Assert that nothing has changed
    assertEquals(1, delegates.length);
  }

  /**
   * Test {@link ChainingStrategy#detectEndpoints(Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link EndpointDetectionStrategy#detectEndpoints(Instance)}.
   * </ul>
   *
   * <p>Method under test: {@link ChainingStrategy#detectEndpoints(Instance)}
   */
  @Test
  @DisplayName("Test detectEndpoints(Instance); then calls detectEndpoints(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ChainingStrategy.detectEndpoints(Instance)"})
  void testDetectEndpoints_thenCallsDetectEndpoints() throws AssertionError {
    // Arrange
    EndpointDetectionStrategy endpointDetectionStrategy = mock(EndpointDetectionStrategy.class);
    Endpoints emptyResult = Endpoints.empty();
    Mono<Endpoints> justResult = Mono.just(emptyResult);
    when(endpointDetectionStrategy.detectEndpoints(Mockito.<Instance>any())).thenReturn(justResult);

    // Act and Assert
    FirstStep<Endpoints> createResult =
        StepVerifier.create(
            new ChainingStrategy(endpointDetectionStrategy).detectEndpoints(mock(Instance.class)));
    createResult
        .assertNext(
            e -> {
              assertSame(emptyResult, e);
              return;
            })
        .expectComplete()
        .verify();
    verify(endpointDetectionStrategy).detectEndpoints(isA(Instance.class));
  }
}
