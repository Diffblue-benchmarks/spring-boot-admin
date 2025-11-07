package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {StatusUpdater.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class StatusUpdaterDiffblueTest {
  @MockBean
  private ApiMediaTypeHandler apiMediaTypeHandler;

  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private InstanceWebClient instanceWebClient;

  @Autowired
  private StatusUpdater statusUpdater;

  /**
   * Method under test:
   * {@link StatusUpdater#StatusUpdater(InstanceRepository, InstanceWebClient, ApiMediaTypeHandler)}
   */
  @Test
  public void testNewStatusUpdater() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create((new StatusUpdater(repository, null, new ApiMediaTypeHandler())).updateStatus(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link StatusUpdater#timeout(Duration)}
   */
  @Test
  public void testTimeout() {
    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    StatusUpdater statusUpdater = new StatusUpdater(repository, null, new ApiMediaTypeHandler());

    // Act and Assert
    assertSame(statusUpdater, statusUpdater.timeout(null));
  }

  /**
   * Method under test: {@link StatusUpdater#updateStatus(InstanceId)}
   */
  @Test
  public void testUpdateStatus() {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator = new ChannelSendOperator<>(source, mock(Function.class));

    when(mono.then()).thenReturn(channelSendOperator);
    when(instanceRepository.computeIfPresent(Mockito.<InstanceId>any(),
        Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any())).thenReturn(mono);

    // Act
    Mono<Void> actualUpdateStatusResult = statusUpdater.updateStatus(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualUpdateStatusResult);
  }

  /**
   * Method under test: {@link StatusUpdater#convertStatusInfo(ClientResponse)}
   */
  @Test
  public void testConvertStatusInfo() throws AssertionError {
    // Arrange
    Mono<Map<String, Object>> justResult = Mono.just(new HashMap<>());
    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    when(delegate.bodyToMono(Mockito.<ParameterizedTypeReference<Map<String, Object>>>any())).thenReturn(justResult);
    when(delegate.statusCode()).thenReturn(HttpStatusCode.valueOf(200));
    ClientResponseWrapper.HeadersWrapper headersWrapper = mock(ClientResponseWrapper.HeadersWrapper.class);
    MediaType mediaType = mock(MediaType.class);
    when(mediaType.isCompatibleWith(Mockito.<MediaType>any())).thenReturn(true);
    Optional<MediaType> ofResult = Optional.of(mediaType);
    when(headersWrapper.contentType()).thenReturn(ofResult);
    when(delegate.headers()).thenReturn(headersWrapper);

    // Act and Assert
    StepVerifier.FirstStep<StatusInfo> createResult = StepVerifier
        .create(statusUpdater.convertStatusInfo(new ClientResponseWrapper(delegate)));
    StepVerifier expectCompleteResult = createResult.assertNext(s -> {
      StatusInfo statusInfo = s;
      assertTrue(statusInfo.getDetails().isEmpty());
      assertEquals("UP", statusInfo.getStatus());
      assertFalse(statusInfo.isDown());
      assertFalse(statusInfo.isOffline());
      assertFalse(statusInfo.isUnknown());
      assertTrue(statusInfo.isUp());
      return;
    }).expectComplete();
    verify(mediaType).isCompatibleWith(isA(MediaType.class));
    verify(delegate).bodyToMono(isA(ParameterizedTypeReference.class));
    verify(delegate).headers();
    verify(delegate).statusCode();
    verify(headersWrapper).contentType();
    expectCompleteResult.verify();
  }

  /**
   * Method under test: {@link StatusUpdater#handleError(Throwable)}
   */
  @Test
  public void testHandleError() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<StatusInfo> createResult = StepVerifier.create(statusUpdater.handleError(new Throwable()));
    createResult.assertNext(s -> {
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
    }).expectComplete().verify();
  }
}
