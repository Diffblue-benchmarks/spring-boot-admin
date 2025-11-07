package de.codecentric.boot.admin.server.services;

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
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.Info;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
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
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper.HeadersWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InfoUpdater.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class InfoUpdaterDiffblueTest {
  @MockBean
  private ApiMediaTypeHandler apiMediaTypeHandler;

  @Autowired
  private InfoUpdater infoUpdater;

  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private InstanceWebClient instanceWebClient;

  /**
   * Test {@link InfoUpdater#updateInfo(InstanceId)}.
   * <p>
   * Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono InfoUpdater.updateInfo(InstanceId)"})
  public void testUpdateInfo() {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator = new ChannelSendOperator<>(source, mock(Function.class));

    when(mono.then()).thenReturn(channelSendOperator);
    when(instanceRepository.computeIfPresent(Mockito.<InstanceId>any(),
        Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any())).thenReturn(mono);

    // Act
    Mono<Void> actualUpdateInfoResult = infoUpdater.updateInfo(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).then();
    assertSame(channelSendOperator, actualUpdateInfoResult);
  }

  /**
   * Test {@link InfoUpdater#convertInfo(Instance, Throwable)} with {@code instance}, {@code ex}.
   * <p>
   * Method under test: {@link InfoUpdater#convertInfo(Instance, Throwable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Info InfoUpdater.convertInfo(Instance, Throwable)"})
  public void testConvertInfoWithInstanceEx() {
    // Arrange, Act and Assert
    assertTrue(infoUpdater.convertInfo(null, new Throwable()).getValues().isEmpty());
  }

  /**
   * Test {@link InfoUpdater#convertInfo(Instance, ClientResponse)} with {@code instance}, {@code response}.
   * <ul>
   *   <li>Given just {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InfoUpdater#convertInfo(Instance, ClientResponse)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono InfoUpdater.convertInfo(Instance, ClientResponse)"})
  public void testConvertInfoWithInstanceResponse_givenJustHashMap() throws AssertionError {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLog = mock(IMap.class);
    when(eventLog.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());
    new ChannelSendOperator<>(new HazelcastEventStore(3, eventLog), mock(Function.class));

    MediaType mediaType = mock(MediaType.class);
    when(mediaType.isCompatibleWith(Mockito.<MediaType>any())).thenReturn(true);
    Optional<MediaType> ofResult = Optional.of(mediaType);
    HeadersWrapper headersWrapper = mock(HeadersWrapper.class);
    when(headersWrapper.contentType()).thenReturn(ofResult);
    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    Mono<Map<String, Object>> justResult = Mono.just(new HashMap<>());
    when(delegate.bodyToMono(Mockito.<ParameterizedTypeReference<Map<String, Object>>>any())).thenReturn(justResult);
    when(delegate.headers()).thenReturn(headersWrapper);
    when(delegate.statusCode()).thenReturn(HttpStatusCode.valueOf(200));

    // Act and Assert
    FirstStep<Info> createResult = StepVerifier
        .create(infoUpdater.convertInfo(null, new ClientResponseWrapper(delegate)));
    createResult.assertNext(i -> {
      assertTrue(i.getValues().isEmpty());
      return;
    }).expectComplete().verify();
    verify(eventLog).addEntryListener(isA(MapListener.class), eq(true));
    verify(mediaType).isCompatibleWith(isA(MediaType.class));
    verify(delegate).bodyToMono(isA(ParameterizedTypeReference.class));
    verify(delegate).headers();
    verify(delegate).statusCode();
    verify(headersWrapper).contentType();
  }
}
