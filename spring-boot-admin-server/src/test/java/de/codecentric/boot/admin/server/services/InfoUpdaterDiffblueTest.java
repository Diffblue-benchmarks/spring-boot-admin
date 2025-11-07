package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.values.Info;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
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
   * Method under test: {@link InfoUpdater#updateInfo(InstanceId)}
   */
  @Test
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
   * Method under test: {@link InfoUpdater#convertInfo(Instance, Throwable)}
   */
  @Test
  public void testConvertInfo() {
    // Arrange, Act and Assert
    assertTrue(infoUpdater.convertInfo(null, new Throwable()).getValues().isEmpty());
  }

  /**
   * Method under test: {@link InfoUpdater#convertInfo(Instance, ClientResponse)}
   */
  @Test
  public void testConvertInfo2() throws AssertionError {
    // Arrange
    MediaType mediaType = mock(MediaType.class);
    when(mediaType.isCompatibleWith(Mockito.<MediaType>any())).thenReturn(true);
    Optional<MediaType> ofResult = Optional.of(mediaType);
    ClientResponseWrapper.HeadersWrapper headersWrapper = mock(ClientResponseWrapper.HeadersWrapper.class);
    when(headersWrapper.contentType()).thenReturn(ofResult);
    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    Mono<Map<String, Object>> justResult = Mono.just(new HashMap<>());
    when(delegate.bodyToMono(Mockito.<ParameterizedTypeReference<Map<String, Object>>>any())).thenReturn(justResult);
    when(delegate.headers()).thenReturn(headersWrapper);
    when(delegate.statusCode()).thenReturn(HttpStatusCode.valueOf(200));

    // Act and Assert
    StepVerifier.FirstStep<Info> createResult = StepVerifier
        .create(infoUpdater.convertInfo(null, new ClientResponseWrapper(delegate)));
    createResult.assertNext(i -> {
      assertTrue(i.getValues().isEmpty());
      return;
    }).expectComplete().verify();
    verify(mediaType).isCompatibleWith(isA(MediaType.class));
    verify(delegate).bodyToMono(isA(ParameterizedTypeReference.class));
    verify(delegate).headers();
    verify(delegate).statusCode();
    verify(headersWrapper).contentType();
  }
}
