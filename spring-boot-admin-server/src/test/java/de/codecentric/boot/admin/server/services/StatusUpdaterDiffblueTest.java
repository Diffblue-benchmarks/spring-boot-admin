package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper.HeadersWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

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
   * Test {@link StatusUpdater#StatusUpdater(InstanceRepository, InstanceWebClient, ApiMediaTypeHandler)}.
   * <p>
   * Method under test: {@link StatusUpdater#StatusUpdater(InstanceRepository, InstanceWebClient, ApiMediaTypeHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void StatusUpdater.<init>(InstanceRepository, InstanceWebClient, ApiMediaTypeHandler)"})
  public void testNewStatusUpdater() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InstanceWebClient instanceWebClient = mock(InstanceWebClient.class);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create((new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler())).updateStatus(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link StatusUpdater#timeout(Duration)}.
   * <p>
   * Method under test: {@link StatusUpdater#timeout(Duration)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusUpdater StatusUpdater.timeout(Duration)"})
  public void testTimeout() {
    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(mock(Builder.class)).build();
    StatusUpdater statusUpdater = new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());

    // Act and Assert
    assertSame(statusUpdater, statusUpdater.timeout(null));
  }

  /**
   * Test {@link StatusUpdater#updateStatus(InstanceId)}.
   * <p>
   * Method under test: {@link StatusUpdater#updateStatus(InstanceId)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono StatusUpdater.updateStatus(InstanceId)"})
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
   * Test {@link StatusUpdater#convertStatusInfo(ClientResponse)}.
   * <ul>
   *   <li>Given just {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusUpdater#convertStatusInfo(ClientResponse)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono StatusUpdater.convertStatusInfo(ClientResponse)"})
  public void testConvertStatusInfo_givenJustHashMap() throws AssertionError {
    // Arrange
    Mono<Map<String, Object>> justResult = Mono.just(new HashMap<>());
    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    when(delegate.bodyToMono(Mockito.<ParameterizedTypeReference<Map<String, Object>>>any())).thenReturn(justResult);
    IMap<InstanceId, List<InstanceEvent>> eventLog = mock(IMap.class);
    when(eventLog.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    new ChannelSendOperator<>(new HazelcastEventStore(3, eventLog), mock(Function.class));

    when(delegate.statusCode()).thenReturn(HttpStatusCode.valueOf(200));
    HeadersWrapper headersWrapper = mock(HeadersWrapper.class);
    MediaType mediaType = mock(MediaType.class);
    when(mediaType.isCompatibleWith(Mockito.<MediaType>any())).thenReturn(true);
    Optional<MediaType> ofResult = Optional.of(mediaType);
    when(headersWrapper.contentType()).thenReturn(ofResult);
    when(delegate.headers()).thenReturn(headersWrapper);

    // Act and Assert
    FirstStep<StatusInfo> createResult = StepVerifier
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
    verify(eventLog).addEntryListener(isA(MapListener.class), eq(true));
    verify(mediaType).isCompatibleWith(isA(MediaType.class));
    verify(delegate).bodyToMono(isA(ParameterizedTypeReference.class));
    verify(delegate).headers();
    verify(delegate).statusCode();
    verify(headersWrapper).contentType();
    expectCompleteResult.verify();
  }

  /**
   * Test {@link StatusUpdater#handleError(Throwable)}.
   * <p>
   * Method under test: {@link StatusUpdater#handleError(Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono StatusUpdater.handleError(Throwable)"})
  public void testHandleError() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<StatusInfo> createResult = StepVerifier.create(statusUpdater.handleError(new Throwable()));
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
