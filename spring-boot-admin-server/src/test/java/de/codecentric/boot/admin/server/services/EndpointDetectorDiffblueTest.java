package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.services.endpoints.EndpointDetectionStrategy;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {EndpointDetector.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class EndpointDetectorDiffblueTest {
  @MockBean
  private EndpointDetectionStrategy endpointDetectionStrategy;

  @Autowired
  private EndpointDetector endpointDetector;

  @MockBean
  private InstanceRepository instanceRepository;

  /**
   * Method under test: {@link EndpointDetector#detectEndpoints(InstanceId)}
   */
  @Test
  public void testDetectEndpoints() {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator = new ChannelSendOperator<>(source, mock(Function.class));

    when(mono.then()).thenReturn(channelSendOperator);
    when(instanceRepository.computeIfPresent(Mockito.<InstanceId>any(),
        Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any())).thenReturn(mono);

    // Act
    Mono<Void> actualDetectEndpointsResult = endpointDetector.detectEndpoints(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualDetectEndpointsResult);
  }
}
