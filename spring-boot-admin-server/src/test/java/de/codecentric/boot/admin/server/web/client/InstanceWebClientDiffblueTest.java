package de.codecentric.boot.admin.server.web.client;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.web.client.exception.ResolveInstanceException;
import java.util.List;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {InstanceWebClient.Builder.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InstanceWebClientDiffblueTest {
  @Autowired
  private InstanceWebClient.Builder builder;

  /**
   * Method under test:
   * {@link InstanceWebClient.Builder#filter(InstanceExchangeFilterFunction)}
   */
  @Test
  public void testBuilderFilter() {
    // Arrange, Act and Assert
    assertSame(builder, builder.filter(mock(InstanceExchangeFilterFunction.class)));
  }

  /**
   * Method under test: {@link InstanceWebClient.Builder#filters(Consumer)}
   */
  @Test
  public void testBuilderFilters() {
    // Arrange
    Consumer<List<InstanceExchangeFilterFunction>> filtersConsumer = mock(Consumer.class);
    doNothing().when(filtersConsumer).accept(Mockito.<List<InstanceExchangeFilterFunction>>any());

    // Act
    InstanceWebClient.Builder actualFiltersResult = builder.filters(filtersConsumer);

    // Assert
    verify(filtersConsumer).accept(isA(List.class));
    assertSame(builder, actualFiltersResult);
  }

  /**
   * Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  public void testInstance() {
    // Arrange
    WebClient.Builder builder = mock(WebClient.Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    WebClient.Builder builder2 = mock(WebClient.Builder.class);
    when(builder2.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any())).thenReturn(builder);
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder2);

    // Act
    (new InstanceWebClient(webClient)).instance((Instance) null);

    // Assert
    verify(webClient).mutate();
    verify(builder).build();
    verify(builder2).filters(isA(Consumer.class));
  }

  /**
   * Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  public void testInstance2() {
    // Arrange
    WebClient.Builder builder = mock(WebClient.Builder.class);
    when(builder.build()).thenThrow(new ResolveInstanceException("An error occurred"));
    WebClient.Builder builder2 = mock(WebClient.Builder.class);
    when(builder2.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any())).thenReturn(builder);
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder2);

    // Act and Assert
    assertThrows(ResolveInstanceException.class, () -> (new InstanceWebClient(webClient)).instance((Instance) null));
    verify(webClient).mutate();
    verify(builder).build();
    verify(builder2).filters(isA(Consumer.class));
  }

  /**
   * Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  public void testInstance3() {
    // Arrange
    WebClient.Builder builder = mock(WebClient.Builder.class);
    when(builder.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any()))
        .thenThrow(new ResolveInstanceException("An error occurred"));
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder);

    // Act and Assert
    assertThrows(ResolveInstanceException.class, () -> (new InstanceWebClient(webClient)).instance((Instance) null));
    verify(webClient).mutate();
    verify(builder).filters(isA(Consumer.class));
  }

  /**
   * Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  public void testInstance4() {
    // Arrange
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenThrow(new ResolveInstanceException("An error occurred"));

    // Act and Assert
    assertThrows(ResolveInstanceException.class, () -> (new InstanceWebClient(webClient)).instance((Instance) null));
    verify(webClient).mutate();
  }

  /**
   * Method under test: {@link InstanceWebClient#instance(Mono)}
   */
  @Test
  public void testInstance5() {
    // Arrange
    WebClient.Builder builder = mock(WebClient.Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    WebClient.Builder builder2 = mock(WebClient.Builder.class);
    when(builder2.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any())).thenReturn(builder);
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder2);

    // Act
    (new InstanceWebClient(webClient)).instance((Mono<Instance>) null);

    // Assert
    verify(webClient).mutate();
    verify(builder).build();
    verify(builder2).filters(isA(Consumer.class));
  }

  /**
   * Method under test: {@link InstanceWebClient#instance(Mono)}
   */
  @Test
  public void testInstance6() {
    // Arrange
    WebClient.Builder builder = mock(WebClient.Builder.class);
    when(builder.build()).thenThrow(new ResolveInstanceException("An error occurred"));
    WebClient.Builder builder2 = mock(WebClient.Builder.class);
    when(builder2.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any())).thenReturn(builder);
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder2);

    // Act and Assert
    assertThrows(ResolveInstanceException.class,
        () -> (new InstanceWebClient(webClient)).instance((Mono<Instance>) null));
    verify(webClient).mutate();
    verify(builder).build();
    verify(builder2).filters(isA(Consumer.class));
  }

  /**
   * Method under test: {@link InstanceWebClient#instance(Mono)}
   */
  @Test
  public void testInstance7() {
    // Arrange
    WebClient.Builder builder = mock(WebClient.Builder.class);
    when(builder.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any()))
        .thenThrow(new ResolveInstanceException("An error occurred"));
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder);

    // Act and Assert
    assertThrows(ResolveInstanceException.class,
        () -> (new InstanceWebClient(webClient)).instance((Mono<Instance>) null));
    verify(webClient).mutate();
    verify(builder).filters(isA(Consumer.class));
  }

  /**
   * Method under test: {@link InstanceWebClient#instance(Mono)}
   */
  @Test
  public void testInstance8() {
    // Arrange
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenThrow(new ResolveInstanceException("An error occurred"));

    // Act and Assert
    assertThrows(ResolveInstanceException.class,
        () -> (new InstanceWebClient(webClient)).instance((Mono<Instance>) null));
    verify(webClient).mutate();
  }
}
