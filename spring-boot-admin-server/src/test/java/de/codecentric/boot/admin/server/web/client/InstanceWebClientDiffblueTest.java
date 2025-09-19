package de.codecentric.boot.admin.server.web.client;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.web.client.exception.ResolveInstanceException;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {InstanceWebClient.Builder.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class InstanceWebClientDiffblueTest {
  @Autowired private InstanceWebClient.Builder builder;

  /**
   * Test Builder {@link InstanceWebClient.Builder#filter(InstanceExchangeFilterFunction)}.
   *
   * <p>Method under test: {@link InstanceWebClient.Builder#filter(InstanceExchangeFilterFunction)}
   */
  @Test
  @DisplayName("Test Builder filter(InstanceExchangeFilterFunction)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceWebClient.Builder InstanceWebClient.Builder.filter(InstanceExchangeFilterFunction)"
  })
  void testBuilderFilter() {
    // Arrange and Act
    InstanceWebClient.Builder actualFilterResult =
        builder.filter(mock(InstanceExchangeFilterFunction.class));

    // Assert
    assertSame(builder, actualFilterResult);
  }

  /**
   * Test Builder {@link InstanceWebClient.Builder#filters(Consumer)}.
   *
   * <p>Method under test: {@link InstanceWebClient.Builder#filters(Consumer)}
   */
  @Test
  @DisplayName("Test Builder filters(Consumer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceWebClient.Builder InstanceWebClient.Builder.filters(Consumer)"})
  void testBuilderFilters() {
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
   * Test {@link InstanceWebClient#instance(Instance)} with {@code Instance}.
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  @DisplayName("Test instance(Instance) with 'Instance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Instance)"})
  void testInstanceWithInstance() {
    // Arrange
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenThrow(new ResolveInstanceException("An error occurred"));

    // Act and Assert
    assertThrows(
        ResolveInstanceException.class,
        () -> new InstanceWebClient(webClient).instance(mock(Instance.class)));
    verify(webClient).mutate();
  }

  /**
   * Test {@link InstanceWebClient#instance(Instance)} with {@code Instance}.
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  @DisplayName("Test instance(Instance) with 'Instance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Instance)"})
  void testInstanceWithInstance2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any()))
        .thenThrow(new ResolveInstanceException("An error occurred"));

    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder);

    // Act and Assert
    assertThrows(
        ResolveInstanceException.class,
        () -> new InstanceWebClient(webClient).instance(mock(Instance.class)));
    verify(webClient).mutate();
    verify(builder).filters(isA(Consumer.class));
  }

  /**
   * Test {@link InstanceWebClient#instance(Instance)} with {@code Instance}.
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  @DisplayName("Test instance(Instance) with 'Instance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Instance)"})
  void testInstanceWithInstance3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenThrow(new ResolveInstanceException("An error occurred"));

    Builder builder2 = mock(Builder.class);
    when(builder2.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any()))
        .thenReturn(builder);

    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder2);

    // Act and Assert
    assertThrows(
        ResolveInstanceException.class,
        () -> new InstanceWebClient(webClient).instance(mock(Instance.class)));
    verify(webClient).mutate();
    verify(builder).build();
    verify(builder2).filters(isA(Consumer.class));
  }

  /**
   * Test {@link InstanceWebClient#instance(Instance)} with {@code Instance}.
   *
   * <ul>
   *   <li>Given {@link Builder} {@link Builder#build()} return {@link WebClient}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  @DisplayName(
      "Test instance(Instance) with 'Instance'; given Builder build() return WebClient; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Instance)"})
  void testInstanceWithInstance_givenBuilderBuildReturnWebClient_thenCallsBuild() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));

    Builder builder2 = mock(Builder.class);
    when(builder2.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any()))
        .thenReturn(builder);

    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder2);

    // Act
    new InstanceWebClient(webClient).instance(mock(Instance.class));

    // Assert
    verify(webClient).mutate();
    verify(builder).build();
    verify(builder2).filters(isA(Consumer.class));
  }

  /**
   * Test {@link InstanceWebClient#instance(Instance)} with {@code Instance}.
   *
   * <ul>
   *   <li>Given {@link InstanceWebClient.Builder} {@link
   *       InstanceWebClient.Builder#webClient(Builder)} return builder.
   *   <li>Then calls {@link InstanceWebClient.Builder#webClient(Builder)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  @DisplayName(
      "Test instance(Instance) with 'Instance'; given Builder webClient(Builder) return builder; then calls webClient(Builder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Instance)"})
  void testInstanceWithInstance_givenBuilderWebClientReturnBuilder_thenCallsWebClient() {
    // Arrange
    InstanceWebClient.Builder builder = mock(InstanceWebClient.Builder.class);
    when(builder.webClient(Mockito.<Builder>any())).thenReturn(InstanceWebClient.builder());

    // Act
    builder.webClient(mock(Builder.class)).build().instance(mock(Instance.class));

    // Assert
    verify(builder).webClient(isA(Builder.class));
  }

  /**
   * Test {@link InstanceWebClient#instance(Instance)} with {@code Instance}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Instance)}
   */
  @Test
  @DisplayName("Test instance(Instance) with 'Instance'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Instance)"})
  void testInstanceWithInstance_whenNull() {
    // Arrange
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenThrow(new ResolveInstanceException("An error occurred"));

    // Act and Assert
    assertThrows(
        ResolveInstanceException.class,
        () -> new InstanceWebClient(webClient).instance((Instance) null));
    verify(webClient).mutate();
  }

  /**
   * Test {@link InstanceWebClient#instance(Mono)} with {@code Mono}.
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Mono)}
   */
  @Test
  @DisplayName("Test instance(Mono) with 'Mono'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Mono)"})
  void testInstanceWithMono() {
    // Arrange
    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenThrow(new ResolveInstanceException("An error occurred"));
    InstanceWebClient instanceWebClient = new InstanceWebClient(webClient);
    Mono<Instance> instance = Mono.just(mock(Instance.class));

    // Act and Assert
    assertThrows(ResolveInstanceException.class, () -> instanceWebClient.instance(instance));
    verify(webClient).mutate();
  }

  /**
   * Test {@link InstanceWebClient#instance(Mono)} with {@code Mono}.
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Mono)}
   */
  @Test
  @DisplayName("Test instance(Mono) with 'Mono'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Mono)"})
  void testInstanceWithMono2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any()))
        .thenThrow(new ResolveInstanceException("An error occurred"));

    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder);
    InstanceWebClient instanceWebClient = new InstanceWebClient(webClient);
    Mono<Instance> instance = Mono.just(mock(Instance.class));

    // Act and Assert
    assertThrows(ResolveInstanceException.class, () -> instanceWebClient.instance(instance));
    verify(webClient).mutate();
    verify(builder).filters(isA(Consumer.class));
  }

  /**
   * Test {@link InstanceWebClient#instance(Mono)} with {@code Mono}.
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Mono)}
   */
  @Test
  @DisplayName("Test instance(Mono) with 'Mono'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Mono)"})
  void testInstanceWithMono3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenThrow(new ResolveInstanceException("An error occurred"));

    Builder builder2 = mock(Builder.class);
    when(builder2.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any()))
        .thenReturn(builder);

    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder2);
    InstanceWebClient instanceWebClient = new InstanceWebClient(webClient);
    Mono<Instance> instance = Mono.just(mock(Instance.class));

    // Act and Assert
    assertThrows(ResolveInstanceException.class, () -> instanceWebClient.instance(instance));
    verify(webClient).mutate();
    verify(builder).build();
    verify(builder2).filters(isA(Consumer.class));
  }

  /**
   * Test {@link InstanceWebClient#instance(Mono)} with {@code Mono}.
   *
   * <ul>
   *   <li>Given {@link Builder} {@link Builder#build()} return {@link WebClient}.
   *   <li>Then calls {@link Builder#build()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceWebClient#instance(Mono)}
   */
  @Test
  @DisplayName(
      "Test instance(Mono) with 'Mono'; given Builder build() return WebClient; then calls build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebClient InstanceWebClient.instance(Mono)"})
  void testInstanceWithMono_givenBuilderBuildReturnWebClient_thenCallsBuild() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));

    Builder builder2 = mock(Builder.class);
    when(builder2.filters(Mockito.<Consumer<List<ExchangeFilterFunction>>>any()))
        .thenReturn(builder);

    WebClient webClient = mock(WebClient.class);
    when(webClient.mutate()).thenReturn(builder2);
    InstanceWebClient instanceWebClient = new InstanceWebClient(webClient);
    Mono<Instance> instance = Mono.just(mock(Instance.class));

    // Act
    instanceWebClient.instance(instance);

    // Assert
    verify(webClient).mutate();
    verify(builder).build();
    verify(builder2).filters(isA(Consumer.class));
  }
}
