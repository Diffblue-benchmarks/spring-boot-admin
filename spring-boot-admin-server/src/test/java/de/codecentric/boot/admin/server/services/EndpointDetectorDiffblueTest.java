package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
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
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.services.endpoints.EndpointDetectionStrategy;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {EndpointDetector.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class EndpointDetectorDiffblueTest {
  @MockitoBean private EndpointDetectionStrategy endpointDetectionStrategy;

  @Autowired private EndpointDetector endpointDetector;

  @MockitoBean private InstanceRepository instanceRepository;

  /**
   * Test {@link EndpointDetector#detectEndpoints(InstanceId)}.
   *
   * <p>Method under test: {@link EndpointDetector#detectEndpoints(InstanceId)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetector.detectEndpoints(InstanceId)"})
  void testDetectEndpoints() {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(mono.then()).thenReturn(channelSendOperator);
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(mono);

    // Act
    Mono<Void> actualDetectEndpointsResult = endpointDetector.detectEndpoints(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualDetectEndpointsResult);
  }

  /**
   * Test {@link EndpointDetector#detectEndpoints(InstanceId)}.
   *
   * <p>Method under test: {@link EndpointDetector#detectEndpoints(InstanceId)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetector.detectEndpoints(InstanceId)"})
  void testDetectEndpoints2() {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(mock(ChannelSendOperator.class), mock(Function.class));
    when(mono.then()).thenReturn(channelSendOperator);
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(mono);

    // Act
    Mono<Void> actualDetectEndpointsResult = endpointDetector.detectEndpoints(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualDetectEndpointsResult);
  }

  /**
   * Test {@link EndpointDetector#detectEndpoints(InstanceId)}.
   *
   * <p>Method under test: {@link EndpointDetector#detectEndpoints(InstanceId)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetector.detectEndpoints(InstanceId)"})
  void testDetectEndpoints3() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(endpointDetector.detectEndpoints(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EndpointDetector#detectEndpoints(InstanceId)}.
   *
   * <p>Method under test: {@link EndpointDetector#detectEndpoints(InstanceId)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetector.detectEndpoints(InstanceId)"})
  void testDetectEndpoints4() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(endpointDetector.detectEndpoints(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EndpointDetector#detectEndpoints(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetector#detectEndpoints(InstanceId)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceId); given ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetector.detectEndpoints(InstanceId)"})
  void testDetectEndpoints_givenArrayListAdd42() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    it.addAll(new ArrayList<>());
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    Mono<Instance> mono = mock(Mono.class);
    when(mono.then()).thenReturn(channelSendOperator);
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(mono);

    // Act
    Mono<Void> actualDetectEndpointsResult = endpointDetector.detectEndpoints(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualDetectEndpointsResult);
  }

  /**
   * Test {@link EndpointDetector#detectEndpoints(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>When {@link InstanceId} with value is {@code Value42}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetector#detectEndpoints(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test detectEndpoints(InstanceId); given ArrayList() add '42'; when InstanceId with value is 'Value42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetector.detectEndpoints(InstanceId)"})
  void testDetectEndpoints_givenArrayListAdd42_whenInstanceIdWithValueIsValue42() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    it.addAll(new ArrayList<>());
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    Mono<Instance> mono = mock(Mono.class);
    when(mono.then()).thenReturn(channelSendOperator);
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(mono);

    // Act
    Mono<Void> actualDetectEndpointsResult =
        endpointDetector.detectEndpoints(InstanceId.of("Value42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualDetectEndpointsResult);
  }

  /**
   * Test {@link EndpointDetector#detectEndpoints(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#computeIfPresent(InstanceId,
   *       BiFunction)} return just {@link Instance}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetector#detectEndpoints(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test detectEndpoints(InstanceId); given InstanceRepository computeIfPresent(InstanceId, BiFunction) return just Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetector.detectEndpoints(InstanceId)"})
  void testDetectEndpoints_givenInstanceRepositoryComputeIfPresentReturnJustInstance()
      throws AssertionError {
    // Arrange
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(justResult);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(endpointDetector.detectEndpoints(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
  }

  /**
   * Test {@link EndpointDetector#detectEndpoints(InstanceId)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointDetector#detectEndpoints(InstanceId)}
   */
  @Test
  @DisplayName("Test detectEndpoints(InstanceId); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono EndpointDetector.detectEndpoints(InstanceId)"})
  void testDetectEndpoints_thenCallsFind() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    EndpointDetector endpointDetector =
        new EndpointDetector(repository, mock(EndpointDetectionStrategy.class));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(endpointDetector.detectEndpoints(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
  }
}
