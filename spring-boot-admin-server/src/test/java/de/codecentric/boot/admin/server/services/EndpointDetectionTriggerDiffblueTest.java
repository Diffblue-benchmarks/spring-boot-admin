package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.services.endpoints.EndpointDetectionStrategy;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
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

@ContextConfiguration(classes = {EndpointDetectionTrigger.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class EndpointDetectionTriggerDiffblueTest {
  @Autowired
  private EndpointDetectionTrigger endpointDetectionTrigger;

  @MockBean
  private EndpointDetector endpointDetector;

  @MockBean
  private Publisher<InstanceEvent> publisher;

  /**
   * Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  public void testHandle() {
    // Arrange
    Flux<InstanceEvent> publisher2 = mock(Flux.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(fromIterableResult);

    // Act
    endpointDetectionTrigger.handle(publisher2);

    // Assert
    verify(publisher2).filter(isA(Predicate.class));
  }

  /**
   * Method under test: {@link EndpointDetectionTrigger#handle(Flux)}
   */
  @Test
  public void testHandle2() {
    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<InstanceEvent> publisher2 = mock(Flux.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(flux);

    // Act
    Publisher<Void> actualHandleResult = endpointDetectionTrigger.handle(publisher2);

    // Assert
    verify(publisher2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Method under test:
   * {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  public void testDetectEndpoints() {
    // Arrange
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(endpointDetector.detectEndpoints(Mockito.<InstanceId>any()))
        .thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));

    // Act
    endpointDetectionTrigger.detectEndpoints(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(endpointDetector).detectEndpoints(isA(InstanceId.class));
  }

  /**
   * Method under test:
   * {@link EndpointDetectionTrigger#detectEndpoints(InstanceEvent)}
   */
  @Test
  public void testDetectEndpoints2() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 = new ChannelSendOperator<>(source, mock(Function.class));

    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);
    when(endpointDetector.detectEndpoints(Mockito.<InstanceId>any())).thenReturn(channelSendOperator);

    // Act
    Mono<Void> actualDetectEndpointsResult = endpointDetectionTrigger
        .detectEndpoints(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(endpointDetector).detectEndpoints(isA(InstanceId.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualDetectEndpointsResult);
  }

  /**
   * Method under test:
   * {@link EndpointDetectionTrigger#EndpointDetectionTrigger(EndpointDetector, Publisher)}
   */
  @Test
  public void testNewEndpointDetectionTrigger() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EndpointDetector endpointDetector = new EndpointDetector(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(EndpointDetectionStrategy.class));

    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act and Assert
    assertFalse((new EndpointDetectionTrigger(endpointDetector, publisher)).createScheduler().isDisposed());
  }
}
