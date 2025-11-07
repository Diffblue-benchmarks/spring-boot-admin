package de.codecentric.boot.admin.server.services.endpoints;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import org.junit.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ChainingStrategyDiffblueTest {
  /**
   * Method under test: {@link ChainingStrategy#detectEndpoints(Instance)}
   */
  @Test
  public void testDetectEndpoints() throws AssertionError {
    // Arrange
    EndpointDetectionStrategy endpointDetectionStrategy = mock(EndpointDetectionStrategy.class);
    Endpoints emptyResult = Endpoints.empty();
    Mono<Endpoints> justResult = Mono.just(emptyResult);
    when(endpointDetectionStrategy.detectEndpoints(Mockito.<Instance>any())).thenReturn(justResult);

    // Act and Assert
    StepVerifier.FirstStep<Endpoints> createResult = StepVerifier
        .create((new ChainingStrategy(endpointDetectionStrategy)).detectEndpoints(null));
    createResult.assertNext(e -> {
      assertSame(emptyResult, e);
      return;
    }).expectComplete().verify();
    verify(endpointDetectionStrategy).detectEndpoints(isNull());
  }
}
