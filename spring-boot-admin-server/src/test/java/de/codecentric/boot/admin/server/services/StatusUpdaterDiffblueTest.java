package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper.HeadersWrapper;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {StatusUpdater.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class StatusUpdaterDiffblueTest {
  @MockitoBean private ApiMediaTypeHandler apiMediaTypeHandler;

  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private InstanceWebClient instanceWebClient;

  @Autowired private StatusUpdater statusUpdater;

  /**
   * Test {@link StatusUpdater#timeout(Duration)}.
   *
   * <p>Method under test: {@link StatusUpdater#timeout(Duration)}
   */
  @Test
  @DisplayName("Test timeout(Duration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusUpdater StatusUpdater.timeout(Duration)"})
  void testTimeout() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());
    InstanceWebClient instanceWebClient =
        InstanceWebClient.builder().webClient(mock(Builder.class)).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act
    StatusUpdater actualTimeoutResult = statusUpdater.timeout(Duration.ofSeconds(1L));

    // Assert
    assertSame(statusUpdater, actualTimeoutResult);
  }

  /**
   * Test {@link StatusUpdater#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdater#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.updateStatus(InstanceId)"})
  void testUpdateStatus() {
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
    Mono<Void> actualUpdateStatusResult = statusUpdater.updateStatus(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdater#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdater#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.updateStatus(InstanceId)"})
  void testUpdateStatus2() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdater.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdater#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdater#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.updateStatus(InstanceId)"})
  void testUpdateStatus3() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdater.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdater#updateStatus(InstanceId)}.
   *
   * <p>Method under test: {@link StatusUpdater#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.updateStatus(InstanceId)"})
  void testUpdateStatus4() {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(mock(EmitterProcessor.class), mock(Function.class));
    when(mono.then()).thenReturn(channelSendOperator);

    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(mono);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdater.updateStatus(InstanceId.of("42"));

    // Assert
    verify(repository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(builder).build();
    verify(mono).then();
    assertSame(channelSendOperator, actualUpdateStatusResult);
  }

  /**
   * Test {@link StatusUpdater#updateStatus(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#computeIfPresent(InstanceId,
   *       BiFunction)} return just {@link Instance}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateStatus(InstanceId); given InstanceRepository computeIfPresent(InstanceId, BiFunction) return just Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.updateStatus(InstanceId)"})
  void testUpdateStatus_givenInstanceRepositoryComputeIfPresentReturnJustInstance()
      throws AssertionError {
    // Arrange
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(justResult);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdater.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
  }

  /**
   * Test {@link StatusUpdater#updateStatus(InstanceId)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#updateStatus(InstanceId)}
   */
  @Test
  @DisplayName("Test updateStatus(InstanceId); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.updateStatus(InstanceId)"})
  void testUpdateStatus_thenCallsFind() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(statusUpdater.updateStatus(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(builder).build();
  }

  /**
   * Test {@link StatusUpdater#doUpdateStatus(Instance)}.
   *
   * <p>Method under test: {@link StatusUpdater#doUpdateStatus(Instance)}
   */
  @Test
  @DisplayName("Test doUpdateStatus(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.doUpdateStatus(Instance)"})
  void testDoUpdateStatus() throws AssertionError {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    Instance instance = mock(Instance.class);
    when(instance.isRegistered()).thenReturn(true);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(statusUpdater.doUpdateStatus(instance));
    createResult.expectError().verify();
    verify(instance).isRegistered();
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdater#doUpdateStatus(Instance)}.
   *
   * <p>Method under test: {@link StatusUpdater#doUpdateStatus(Instance)}
   */
  @Test
  @DisplayName("Test doUpdateStatus(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.doUpdateStatus(Instance)"})
  void testDoUpdateStatus2() throws AssertionError {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());
    InstanceWebClient instanceWebClient = builder.webClient(mock(Builder.class)).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    statusUpdater.timeout(Duration.ofSeconds(1L));

    Instance instance = mock(Instance.class);
    when(instance.isRegistered()).thenReturn(true);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(statusUpdater.doUpdateStatus(instance));
    createResult.expectError().verify();
    verify(instance).isRegistered();
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link StatusUpdater#doUpdateStatus(Instance)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When {@link Instance} {@link Instance#isRegistered()} return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#doUpdateStatus(Instance)}
   */
  @Test
  @DisplayName(
      "Test doUpdateStatus(Instance); given 'false'; when Instance isRegistered() return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.doUpdateStatus(Instance)"})
  void testDoUpdateStatus_givenFalse_whenInstanceIsRegisteredReturnFalse() throws AssertionError {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.isRegistered()).thenReturn(false);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(statusUpdater.doUpdateStatus(instance));
    createResult.expectComplete().verify();
    verify(instance).isRegistered();
  }

  /**
   * Test {@link StatusUpdater#convertStatusInfo(ClientResponse)}.
   *
   * <ul>
   *   <li>Given {@link HttpStatus#OK}.
   *   <li>Then calls {@link ApiMediaTypeHandler#isApiMediaType(MediaType)}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#convertStatusInfo(ClientResponse)}
   */
  @Test
  @DisplayName(
      "Test convertStatusInfo(ClientResponse); given OK; then calls isApiMediaType(MediaType)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.convertStatusInfo(ClientResponse)"})
  void testConvertStatusInfo_givenOk_thenCallsIsApiMediaType() throws AssertionError {
    // Arrange
    when(apiMediaTypeHandler.isApiMediaType(Mockito.<MediaType>any())).thenReturn(true);
    Mono<Map<String, Object>> justResult = Mono.just(new HashMap<>());

    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    when(delegate.bodyToMono(Mockito.<ParameterizedTypeReference<Map<String, Object>>>any()))
        .thenReturn(justResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(delegate.releaseBody()).thenReturn(channelSendOperator);
    when(delegate.statusCode()).thenReturn(HttpStatus.OK);
    Optional<MediaType> ofResult =
        Optional.of(MediaType.parseMediaType(MediaType.TEXT_PLAIN_VALUE));

    HeadersWrapper headers = mock(HeadersWrapper.class);
    when(headers.contentType()).thenReturn(ofResult);
    when(delegate.headers()).thenReturn(new HeadersWrapper(headers));

    // Act and Assert
    FirstStep<StatusInfo> createResult =
        StepVerifier.create(statusUpdater.convertStatusInfo(new ClientResponseWrapper(delegate)));
    StepVerifier expectCompleteResult =
        createResult
            .assertNext(
                s -> {
                  StatusInfo statusInfo = s;
                  assertTrue(statusInfo.getDetails().isEmpty());
                  assertEquals("UP", statusInfo.getStatus());
                  assertFalse(statusInfo.isDown());
                  assertFalse(statusInfo.isOffline());
                  assertFalse(statusInfo.isUnknown());
                  assertTrue(statusInfo.isUp());
                  return;
                })
            .expectComplete();
    verify(apiMediaTypeHandler).isApiMediaType(isA(MediaType.class));
    verify(delegate).bodyToMono(isA(ParameterizedTypeReference.class));
    verify(delegate).headers();
    verify(delegate).statusCode();
    verify(headers).contentType();
    expectCompleteResult.verify();
  }

  /**
   * Test {@link StatusUpdater#handleError(Throwable)}.
   *
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.
   *   <li>When {@link IOException#IOException()} addSuppressed {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#handleError(Throwable)}
   */
  @Test
  @DisplayName(
      "Test handleError(Throwable); given Throwable(); when IOException() addSuppressed Throwable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.handleError(Throwable)"})
  void testHandleError_givenThrowable_whenIOExceptionAddSuppressedThrowable()
      throws AssertionError {
    // Arrange
    IOException ex = new IOException();
    ex.addSuppressed(new Throwable());

    // Act and Assert
    FirstStep<StatusInfo> createResult = StepVerifier.create(statusUpdater.handleError(ex));
    createResult
        .assertNext(
            s -> {
              StatusInfo statusInfo = s;
              Map<String, Object> details = statusInfo.getDetails();
              assertEquals(2, details.size());
              Object getResult = details.get("exception");
              assertEquals("java.io.IOException", getResult);
              assertNull(details.get("message"));
              assertEquals("OFFLINE", statusInfo.getStatus());
              assertFalse(statusInfo.isDown());
              assertTrue(statusInfo.isOffline());
              assertFalse(statusInfo.isUnknown());
              assertFalse(statusInfo.isUp());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link StatusUpdater#handleError(Throwable)}.
   *
   * <ul>
   *   <li>When {@link AbstractMethodError#AbstractMethodError()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#handleError(Throwable)}
   */
  @Test
  @DisplayName("Test handleError(Throwable); when AbstractMethodError()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.handleError(Throwable)"})
  void testHandleError_whenAbstractMethodError() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<StatusInfo> createResult =
        StepVerifier.create(statusUpdater.handleError(new AbstractMethodError()));
    createResult
        .assertNext(
            s -> {
              StatusInfo statusInfo = s;
              Map<String, Object> details = statusInfo.getDetails();
              assertEquals(2, details.size());
              Object getResult = details.get("exception");
              assertEquals("java.lang.AbstractMethodError", getResult);
              assertNull(details.get("message"));
              assertEquals("OFFLINE", statusInfo.getStatus());
              assertFalse(statusInfo.isDown());
              assertTrue(statusInfo.isOffline());
              assertFalse(statusInfo.isUnknown());
              assertFalse(statusInfo.isUp());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link StatusUpdater#handleError(Throwable)}.
   *
   * <ul>
   *   <li>When {@link ArrayIndexOutOfBoundsException#ArrayIndexOutOfBoundsException(int)} with one.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#handleError(Throwable)}
   */
  @Test
  @DisplayName("Test handleError(Throwable); when ArrayIndexOutOfBoundsException(int) with one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.handleError(Throwable)"})
  void testHandleError_whenArrayIndexOutOfBoundsExceptionWithOne() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<StatusInfo> createResult =
        StepVerifier.create(statusUpdater.handleError(new ArrayIndexOutOfBoundsException(1)));
    createResult
        .assertNext(
            s -> {
              StatusInfo statusInfo = s;
              Map<String, Object> details = statusInfo.getDetails();
              assertEquals(2, details.size());
              Object getResult = details.get("exception");
              assertEquals("java.lang.ArrayIndexOutOfBoundsException", getResult);
              assertEquals("Array index out of range: 1", details.get("message"));
              assertEquals("OFFLINE", statusInfo.getStatus());
              assertFalse(statusInfo.isDown());
              assertTrue(statusInfo.isOffline());
              assertFalse(statusInfo.isUnknown());
              assertFalse(statusInfo.isUp());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link StatusUpdater#handleError(Throwable)}.
   *
   * <ul>
   *   <li>When {@link IOException#IOException()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#handleError(Throwable)}
   */
  @Test
  @DisplayName("Test handleError(Throwable); when IOException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.handleError(Throwable)"})
  void testHandleError_whenIOException() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<StatusInfo> createResult =
        StepVerifier.create(statusUpdater.handleError(new IOException()));
    createResult
        .assertNext(
            s -> {
              StatusInfo statusInfo = s;
              Map<String, Object> details = statusInfo.getDetails();
              assertEquals(2, details.size());
              Object getResult = details.get("exception");
              assertEquals("java.io.IOException", getResult);
              assertNull(details.get("message"));
              assertEquals("OFFLINE", statusInfo.getStatus());
              assertFalse(statusInfo.isDown());
              assertTrue(statusInfo.isOffline());
              assertFalse(statusInfo.isUnknown());
              assertFalse(statusInfo.isUp());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link StatusUpdater#handleError(Throwable)}.
   *
   * <ul>
   *   <li>When {@link OutOfMemoryError#OutOfMemoryError(String)} with {@code message}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#handleError(Throwable)}
   */
  @Test
  @DisplayName("Test handleError(Throwable); when OutOfMemoryError(String) with 'message'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.handleError(Throwable)"})
  void testHandleError_whenOutOfMemoryErrorWithMessage() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<StatusInfo> createResult =
        StepVerifier.create(statusUpdater.handleError(new OutOfMemoryError("message")));
    createResult
        .assertNext(
            s -> {
              StatusInfo statusInfo = s;
              Map<String, Object> details = statusInfo.getDetails();
              assertEquals(2, details.size());
              Object getResult = details.get("exception");
              assertEquals("java.lang.OutOfMemoryError", getResult);
              assertEquals("message", details.get("message"));
              assertEquals("OFFLINE", statusInfo.getStatus());
              assertFalse(statusInfo.isDown());
              assertTrue(statusInfo.isOffline());
              assertFalse(statusInfo.isUnknown());
              assertFalse(statusInfo.isUp());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link StatusUpdater#handleError(Throwable)}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#handleError(Throwable)}
   */
  @Test
  @DisplayName("Test handleError(Throwable); when Throwable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono StatusUpdater.handleError(Throwable)"})
  void testHandleError_whenThrowable() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<StatusInfo> createResult =
        StepVerifier.create(statusUpdater.handleError(new Throwable()));
    createResult
        .assertNext(
            s -> {
              StatusInfo statusInfo = s;
              Map<String, Object> details = statusInfo.getDetails();
              assertEquals(2, details.size());
              Object getResult = details.get("exception");
              assertEquals("java.lang.Throwable", getResult);
              assertNull(details.get("message"));
              assertEquals("OFFLINE", statusInfo.getStatus());
              assertFalse(statusInfo.isDown());
              assertTrue(statusInfo.isOffline());
              assertFalse(statusInfo.isUnknown());
              assertFalse(statusInfo.isUp());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link StatusUpdater#logError(Instance, Throwable)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#isOffline()} return {@code false}.
   *   <li>Then calls {@link Instance#getStatusInfo()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#logError(Instance, Throwable)}
   */
  @Test
  @DisplayName(
      "Test logError(Instance, Throwable); given StatusInfo isOffline() return 'false'; then calls getStatusInfo()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdater.logError(Instance, Throwable)"})
  void testLogError_givenStatusInfoIsOfflineReturnFalse_thenCallsGetStatusInfo() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.isOffline()).thenReturn(false);

    Instance instance = mock(Instance.class);
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act
    statusUpdater.logError(instance, new Throwable());

    // Assert
    verify(instance).getStatusInfo();
    verify(statusInfo).isOffline();
  }

  /**
   * Test {@link StatusUpdater#logError(Instance, Throwable)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#isOffline()} return {@code true}.
   *   <li>Then calls {@link Instance#getStatusInfo()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusUpdater#logError(Instance, Throwable)}
   */
  @Test
  @DisplayName(
      "Test logError(Instance, Throwable); given StatusInfo isOffline() return 'true'; then calls getStatusInfo()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StatusUpdater.logError(Instance, Throwable)"})
  void testLogError_givenStatusInfoIsOfflineReturnTrue_thenCallsGetStatusInfo() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.isOffline()).thenReturn(true);

    Instance instance = mock(Instance.class);
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act
    statusUpdater.logError(instance, new Throwable());

    // Assert
    verify(instance).getStatusInfo();
    verify(statusInfo).isOffline();
  }
}
