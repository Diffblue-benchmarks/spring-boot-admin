package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEndpointsDetectedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class InfoUpdateTriggerDiffblueTest {
  /**
   * Test {@link InfoUpdateTrigger#InfoUpdateTrigger(InfoUpdater, Publisher, Duration, Duration,
   * Duration)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#InfoUpdateTrigger(InfoUpdater, Publisher,
   * Duration, Duration, Duration)}
   */
  @Test
  @DisplayName("Test new InfoUpdateTrigger(InfoUpdater, Publisher, Duration, Duration, Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InfoUpdateTrigger.<init>(InfoUpdater, Publisher, Duration, Duration, Duration)"
  })
  void testNewInfoUpdateTrigger() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    // Act
    new InfoUpdateTrigger(
        infoUpdater,
        publisher,
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L),
        Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given {@link Builder} {@link Builder#build()} return {@link WebClient}.
   *   <li>When create.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName(
      "Test handle(Flux); given Builder build() return WebClient; when create; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher InfoUpdateTrigger.handle(Flux)"})
  void testHandle_givenBuilderBuildReturnWebClient_whenCreate_thenCallsBuild() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    DirectProcessor<InstanceEvent> publisher2 = DirectProcessor.create();

    // Act
    infoUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Given fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); given fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher InfoUpdateTrigger.handle(Flux)"})
  void testHandle_givenFromIterableArrayList() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(fromIterableResult);

    // Act
    infoUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
    verify(publisher2).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InfoUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>Then return fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); then return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher InfoUpdateTrigger.handle(Flux)"})
  void testHandle_thenReturnFromIterableArrayList() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> publisher2 = mock(DirectProcessor.class);
    when(publisher2.filter(Mockito.<Predicate<InstanceEvent>>any())).thenReturn(directProcessor);

    // Act
    Publisher<Void> actualHandleResult = infoUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
    verify(publisher2).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#handle(Flux)}.
   *
   * <ul>
   *   <li>When fromIterable {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdateTrigger#handle(Flux)}
   */
  @Test
  @DisplayName("Test handle(Flux); when fromIterable ArrayList(); then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Publisher InfoUpdateTrigger.handle(Flux)"})
  void testHandle_whenFromIterableArrayList_thenCallsBuild() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    Flux<InstanceEvent> publisher2 = Flux.fromIterable(new ArrayList<>());

    // Act
    infoUpdateTrigger.handle(publisher2);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo2() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo3() throws AssertionError {
    // Arrange
    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo4() throws AssertionError {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectError().verify();
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo5() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo6() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo7() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    infoUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo8() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            mock(HazelcastEventStore.class),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    infoUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo9() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo10() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Mono<?> source = Mono.just("Data");
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo11() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    Mono<InstanceEvent> publisher = Mono.just(mock(InstanceEndpointsDetectedEvent.class));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo12() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, mock(StatusInfo.class)));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo13() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            mock(HazelcastEventStore.class),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));
    infoUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo14() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Mono<?> source = Mono.just("Data");
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            mock(Publisher.class),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo15() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Mono<?> source = Mono.just("Data");
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            mock(Publisher.class),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo16() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Mono<?> source = Mono.just("Data");
    ChannelSendOperator<Object> channelSendOperator2 =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator2);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            mock(Publisher.class),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId); given ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo_givenArrayListAdd42() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.doFinally(Mockito.<Consumer<SignalType>>any()))
        .thenReturn(channelSendOperator);

    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator3);
    Mono<InstanceEvent> publisher = Mono.just(mock(InstanceEndpointsDetectedEvent.class));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator2).doFinally(isA(Consumer.class));
    verify(channelSendOperator3).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link ChannelSendOperator} {@link ChannelSendOperator#doFinally(Consumer)} return
   *       {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateInfo(InstanceId); given ChannelSendOperator doFinally(Consumer) return 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo_givenChannelSendOperatorDoFinallyReturnNull_thenReturnNull() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    when(channelSendOperator.doFinally(Mockito.<Consumer<SignalType>>any())).thenReturn(null);

    ChannelSendOperator<Object> channelSendOperator2 = mock(ChannelSendOperator.class);
    when(channelSendOperator2.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator);

    InfoUpdater infoUpdater = mock(InfoUpdater.class);
    when(infoUpdater.updateInfo(Mockito.<InstanceId>any())).thenReturn(channelSendOperator2);
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdateTrigger.updateInfo(InstanceId.of("42"));

    // Assert
    verify(infoUpdater).updateInfo(isA(InstanceId.class));
    verify(channelSendOperator).doFinally(isA(Consumer.class));
    verify(channelSendOperator2).onErrorResume(isA(Function.class));
    assertNull(actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdateTrigger#updateInfo(InstanceId)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdateTrigger#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdateTrigger.updateInfo(InstanceId)"})
  void testUpdateInfo_thenCallsFind() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(infoUpdateTrigger.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.start()"})
  void testStart() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.start();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.start()"})
  void testStart2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));
    Flux<InstanceEvent> publisher = Flux.fromIterable(it);

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.start();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.start()"})
  void testStart3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.start();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.start()"})
  void testStart4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            new InMemoryEventStore(),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.start();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.start()"})
  void testStart5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));
    infoUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
    infoUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.start();

    // Assert
    verify(publisher).subscribe(isA(Subscriber.class));
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#start()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.start()"})
  void testStart6() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Publisher<InstanceEvent> publisher = mock(Publisher.class);
    doNothing().when(publisher).subscribe(Mockito.<Subscriber<InstanceEvent>>any());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(Long.MAX_VALUE));
    infoUpdateTrigger.setLifetime(Duration.ofSeconds(1L));
    infoUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.start();

    // Assert
    verify(publisher).subscribe(isA(Subscriber.class));
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L));
    infoUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop6() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(Long.MAX_VALUE));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop7() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(1L));
    infoUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop8() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(
            new InstanceStatusChangedEvent(
                InstanceId.of("42"),
                1L,
                LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
                mock(StatusInfo.class)));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(-1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop9() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        new InstanceWebClient.Builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(Long.MAX_VALUE));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop10() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(Long.MAX_VALUE));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop11() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L));
    infoUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop12() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(
            new InstanceStatusChangedEvent(
                InstanceId.of("42"),
                1L,
                LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
                mock(StatusInfo.class)));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(0L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName("Test stop()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop13() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder(mock(Builder.class)).webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MIN_VALUE),
            Duration.ofSeconds(Long.MAX_VALUE));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#stop()}.
   *
   * <ul>
   *   <li>Given {@link InstanceWebClient.Builder} {@link
   *       InstanceWebClient.Builder#webClient(Builder)} return builder.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdateTrigger#stop()}
   */
  @Test
  @DisplayName(
      "Test stop(); given Builder webClient(Builder) return builder; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.stop()"})
  void testStop_givenBuilderWebClientReturnBuilder_thenCallsWebClient() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Mono<InstanceEvent> publisher =
        Mono.just(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(Long.MAX_VALUE),
            Duration.ofSeconds(1L));
    infoUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.stop();

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link InfoUpdateTrigger#setInterval(Duration)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#setInterval(Duration)}
   */
  @Test
  @DisplayName("Test setInterval(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.setInterval(Duration)"})
  void testSetInterval() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.setInterval(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdateTrigger#setLifetime(Duration)}.
   *
   * <p>Method under test: {@link InfoUpdateTrigger#setLifetime(Duration)}
   */
  @Test
  @DisplayName("Test setLifetime(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InfoUpdateTrigger.setLifetime(Duration)"})
  void testSetLifetime() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> publisher = Flux.fromIterable(new ArrayList<>());

    InfoUpdateTrigger infoUpdateTrigger =
        new InfoUpdateTrigger(
            infoUpdater,
            publisher,
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L),
            Duration.ofSeconds(1L));

    // Act
    infoUpdateTrigger.setLifetime(Duration.ofSeconds(1L));

    // Assert
    verify(builder).build();
  }
}
