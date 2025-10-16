package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
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
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.Info;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper.HeadersWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InfoUpdater.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class InfoUpdaterDiffblueTest {
  @MockitoBean private ApiMediaTypeHandler apiMediaTypeHandler;

  @Autowired private InfoUpdater infoUpdater;

  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private InstanceWebClient instanceWebClient;

  /**
   * Test {@link InfoUpdater#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.updateInfo(InstanceId)"})
  void testUpdateInfo() {
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
    Mono<Void> actualUpdateInfoResult = infoUpdater.updateInfo(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdater#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.updateInfo(InstanceId)"})
  void testUpdateInfo2() {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(mono.then()).thenReturn(channelSendOperator);
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(mono);

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdater.updateInfo(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdater#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.updateInfo(InstanceId)"})
  void testUpdateInfo3() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(infoUpdater.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdater#updateInfo(InstanceId)}.
   *
   * <p>Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.updateInfo(InstanceId)"})
  void testUpdateInfo4() throws AssertionError {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(infoUpdater.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdater#updateInfo(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} addAll {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId); given ArrayList() addAll ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.updateInfo(InstanceId)"})
  void testUpdateInfo_givenArrayListAddAllArrayList() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
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
    Mono<Void> actualUpdateInfoResult = infoUpdater.updateInfo(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdater#updateInfo(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} addAll {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId); given ArrayList() addAll ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.updateInfo(InstanceId)"})
  void testUpdateInfo_givenArrayListAddAllArrayList2() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.addAll(new ArrayList<>());
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
    Mono<Void> actualUpdateInfoResult = infoUpdater.updateInfo(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdater#updateInfo(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#computeIfPresent(InstanceId,
   *       BiFunction)} return just {@link Instance}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test updateInfo(InstanceId); given InstanceRepository computeIfPresent(InstanceId, BiFunction) return just Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.updateInfo(InstanceId)"})
  void testUpdateInfo_givenInstanceRepositoryComputeIfPresentReturnJustInstance()
      throws AssertionError {
    // Arrange
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(justResult);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(infoUpdater.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
  }

  /**
   * Test {@link InfoUpdater#updateInfo(InstanceId)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
  @DisplayName("Test updateInfo(InstanceId); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.updateInfo(InstanceId)"})
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

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(infoUpdater.updateInfo(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(builder).build();
  }

  /**
   * Test {@link InfoUpdater#doUpdateInfo(Instance)}.
   *
   * <ul>
   *   <li>Given empty.
   *   <li>When {@link Instance} {@link Instance#getEndpoints()} return empty.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#doUpdateInfo(Instance)}
   */
  @Test
  @DisplayName(
      "Test doUpdateInfo(Instance); given empty; when Instance getEndpoints() return empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.doUpdateInfo(Instance)"})
  void testDoUpdateInfo_givenEmpty_whenInstanceGetEndpointsReturnEmpty() throws AssertionError {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.isOffline()).thenReturn(false);
    when(statusInfo.isUnknown()).thenReturn(false);

    Instance instance = mock(Instance.class);
    when(instance.getEndpoints()).thenReturn(Endpoints.empty());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(infoUpdater.doUpdateInfo(instance));
    createResult.expectComplete().verify();
    verify(instance).getEndpoints();
    verify(instance, atLeast(1)).getStatusInfo();
    verify(statusInfo).isOffline();
    verify(statusInfo).isUnknown();
  }

  /**
   * Test {@link InfoUpdater#doUpdateInfo(Instance)}.
   *
   * <ul>
   *   <li>Given {@link Endpoints} {@link Endpoints#isPresent(String)} return {@code false}.
   *   <li>Then calls {@link Endpoints#isPresent(String)}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#doUpdateInfo(Instance)}
   */
  @Test
  @DisplayName(
      "Test doUpdateInfo(Instance); given Endpoints isPresent(String) return 'false'; then calls isPresent(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.doUpdateInfo(Instance)"})
  void testDoUpdateInfo_givenEndpointsIsPresentReturnFalse_thenCallsIsPresent()
      throws AssertionError {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.isOffline()).thenReturn(false);
    when(statusInfo.isUnknown()).thenReturn(false);

    Endpoints endpoints = mock(Endpoints.class);
    when(endpoints.isPresent(Mockito.<String>any())).thenReturn(false);

    Instance instance = mock(Instance.class);
    when(instance.getEndpoints()).thenReturn(endpoints);
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(infoUpdater.doUpdateInfo(instance));
    createResult.expectComplete().verify();
    verify(instance).getEndpoints();
    verify(instance, atLeast(1)).getStatusInfo();
    verify(endpoints).isPresent("info");
    verify(statusInfo).isOffline();
    verify(statusInfo).isUnknown();
  }

  /**
   * Test {@link InfoUpdater#doUpdateInfo(Instance)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#isOffline()} return {@code true}.
   *   <li>Then calls {@link Instance#getStatusInfo()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#doUpdateInfo(Instance)}
   */
  @Test
  @DisplayName(
      "Test doUpdateInfo(Instance); given StatusInfo isOffline() return 'true'; then calls getStatusInfo()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.doUpdateInfo(Instance)"})
  void testDoUpdateInfo_givenStatusInfoIsOfflineReturnTrue_thenCallsGetStatusInfo()
      throws AssertionError {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.isOffline()).thenReturn(true);

    Instance instance = mock(Instance.class);
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(infoUpdater.doUpdateInfo(instance));
    createResult.expectComplete().verify();
    verify(instance).getStatusInfo();
    verify(statusInfo).isOffline();
  }

  /**
   * Test {@link InfoUpdater#doUpdateInfo(Instance)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#isUnknown()} return {@code true}.
   *   <li>Then calls {@link StatusInfo#isUnknown()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#doUpdateInfo(Instance)}
   */
  @Test
  @DisplayName(
      "Test doUpdateInfo(Instance); given StatusInfo isUnknown() return 'true'; then calls isUnknown()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.doUpdateInfo(Instance)"})
  void testDoUpdateInfo_givenStatusInfoIsUnknownReturnTrue_thenCallsIsUnknown()
      throws AssertionError {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.isOffline()).thenReturn(false);
    when(statusInfo.isUnknown()).thenReturn(true);

    Instance instance = mock(Instance.class);
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(infoUpdater.doUpdateInfo(instance));
    createResult.expectComplete().verify();
    verify(instance, atLeast(1)).getStatusInfo();
    verify(statusInfo).isOffline();
    verify(statusInfo).isUnknown();
  }

  /**
   * Test {@link InfoUpdater#convertInfo(Instance, Throwable)} with {@code instance}, {@code ex}.
   *
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#convertInfo(Instance, Throwable)}
   */
  @Test
  @DisplayName("Test convertInfo(Instance, Throwable) with 'instance', 'ex'; given Throwable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Info InfoUpdater.convertInfo(Instance, Throwable)"})
  void testConvertInfoWithInstanceEx_givenThrowable() {
    // Arrange
    Instance instance = mock(Instance.class);

    ArrayStoreException ex = new ArrayStoreException();
    ex.addSuppressed(new Throwable());

    // Act and Assert
    assertTrue(infoUpdater.convertInfo(instance, ex).getValues().isEmpty());
  }

  /**
   * Test {@link InfoUpdater#convertInfo(Instance, Throwable)} with {@code instance}, {@code ex}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#convertInfo(Instance, Throwable)}
   */
  @Test
  @DisplayName("Test convertInfo(Instance, Throwable) with 'instance', 'ex'; when Throwable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Info InfoUpdater.convertInfo(Instance, Throwable)"})
  void testConvertInfoWithInstanceEx_whenThrowable() {
    // Arrange
    Instance instance = mock(Instance.class);

    // Act and Assert
    assertTrue(infoUpdater.convertInfo(instance, new Throwable()).getValues().isEmpty());
  }

  /**
   * Test {@link InfoUpdater#convertInfo(Instance, ClientResponse)} with {@code instance}, {@code
   * response}.
   *
   * <p>Method under test: {@link InfoUpdater#convertInfo(Instance, ClientResponse)}
   */
  @Test
  @DisplayName("Test convertInfo(Instance, ClientResponse) with 'instance', 'response'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.convertInfo(Instance, ClientResponse)"})
  void testConvertInfoWithInstanceResponse() throws AssertionError {
    // Arrange
    when(apiMediaTypeHandler.isApiMediaType(Mockito.<MediaType>any())).thenReturn(true);
    Instance instance = mock(Instance.class);

    HeadersWrapper headers = mock(HeadersWrapper.class);
    Optional<MediaType> ofResult =
        Optional.of(MediaType.parseMediaType(MediaType.TEXT_PLAIN_VALUE));
    when(headers.contentType()).thenReturn(ofResult);
    HeadersWrapper headersWrapper = new HeadersWrapper(headers);

    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    Mono<Map<String, Object>> justResult = Mono.just(new HashMap<>());
    when(delegate.bodyToMono(Mockito.<ParameterizedTypeReference<Map<String, Object>>>any()))
        .thenReturn(justResult);
    when(delegate.headers()).thenReturn(headersWrapper);
    when(delegate.statusCode()).thenReturn(HttpStatus.OK);

    // Act
    Mono<Info> actualPublisher =
        infoUpdater.convertInfo(instance, new ClientResponseWrapper(delegate));

    // Assert
    FirstStep<Info> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            i -> {
              assertTrue(i.getValues().isEmpty());
              return;
            })
        .expectComplete()
        .verify();
    verify(apiMediaTypeHandler).isApiMediaType(isA(MediaType.class));
    verify(delegate).bodyToMono(isA(ParameterizedTypeReference.class));
    verify(delegate).headers();
    verify(delegate).statusCode();
    verify(headers).contentType();
  }

  /**
   * Test {@link InfoUpdater#convertInfo(Instance, ClientResponse)} with {@code instance}, {@code
   * response}.
   *
   * <p>Method under test: {@link InfoUpdater#convertInfo(Instance, ClientResponse)}
   */
  @Test
  @DisplayName("Test convertInfo(Instance, ClientResponse) with 'instance', 'response'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.convertInfo(Instance, ClientResponse)"})
  void testConvertInfoWithInstanceResponse2() throws AssertionError {
    // Arrange
    when(apiMediaTypeHandler.isApiMediaType(Mockito.<MediaType>any())).thenReturn(true);
    Instance instance = mock(Instance.class);

    HeadersWrapper headersWrapper = mock(HeadersWrapper.class);
    Optional<MediaType> ofResult =
        Optional.of(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE));
    when(headersWrapper.contentType()).thenReturn(ofResult);

    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    Mono<Object> justResult = Mono.just("Data");
    when(delegate.bodyToMono(Mockito.<ParameterizedTypeReference<Object>>any()))
        .thenReturn(justResult);
    Mono<Map<String, Object>> justResult2 = Mono.just(new HashMap<>());
    when(delegate.bodyToMono(Mockito.<ParameterizedTypeReference<Map<String, Object>>>any()))
        .thenReturn(justResult2);
    when(delegate.headers()).thenReturn(headersWrapper);
    when(delegate.statusCode()).thenReturn(HttpStatus.OK);

    // Act
    Mono<Info> actualPublisher =
        infoUpdater.convertInfo(instance, new ClientResponseWrapper(delegate));

    // Assert
    FirstStep<Info> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            i -> {
              assertTrue(i.getValues().isEmpty());
              return;
            })
        .expectComplete()
        .verify();
    verify(apiMediaTypeHandler).isApiMediaType(isA(MediaType.class));
    verify(delegate, atLeast(1)).bodyToMono(isA(ParameterizedTypeReference.class));
    verify(delegate).headers();
    verify(delegate).statusCode();
    verify(headersWrapper).contentType();
  }

  /**
   * Test {@link InfoUpdater#convertInfo(Instance, ClientResponse)} with {@code instance}, {@code
   * response}.
   *
   * <ul>
   *   <li>Then calls {@link MediaType#isCompatibleWith(MediaType)}.
   * </ul>
   *
   * <p>Method under test: {@link InfoUpdater#convertInfo(Instance, ClientResponse)}
   */
  @Test
  @DisplayName(
      "Test convertInfo(Instance, ClientResponse) with 'instance', 'response'; then calls isCompatibleWith(MediaType)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InfoUpdater.convertInfo(Instance, ClientResponse)"})
  void testConvertInfoWithInstanceResponse_thenCallsIsCompatibleWith() throws AssertionError {
    // Arrange
    Instance instance = mock(Instance.class);

    MediaType mediaType = mock(MediaType.class);
    when(mediaType.isCompatibleWith(Mockito.<MediaType>any())).thenReturn(true);
    Optional<MediaType> ofResult = Optional.of(mediaType);

    HeadersWrapper headers = mock(HeadersWrapper.class);
    when(headers.contentType()).thenReturn(ofResult);
    HeadersWrapper headersWrapper = new HeadersWrapper(headers);

    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    Mono<Map<String, Object>> justResult = Mono.just(new HashMap<>());
    when(delegate.bodyToMono(Mockito.<ParameterizedTypeReference<Map<String, Object>>>any()))
        .thenReturn(justResult);
    when(delegate.headers()).thenReturn(headersWrapper);
    when(delegate.statusCode()).thenReturn(HttpStatus.OK);

    // Act
    Mono<Info> actualPublisher =
        infoUpdater.convertInfo(instance, new ClientResponseWrapper(delegate));

    // Assert
    FirstStep<Info> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            i -> {
              assertTrue(i.getValues().isEmpty());
              return;
            })
        .expectComplete()
        .verify();
    verify(mediaType).isCompatibleWith(isA(MediaType.class));
    verify(delegate).bodyToMono(isA(ParameterizedTypeReference.class));
    verify(delegate).headers();
    verify(delegate).statusCode();
    verify(headers).contentType();
  }
}
