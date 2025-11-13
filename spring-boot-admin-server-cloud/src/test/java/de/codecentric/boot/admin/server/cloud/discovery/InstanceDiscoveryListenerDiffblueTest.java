package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.web.client.RefreshInstancesEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.client.discovery.event.ParentHeartbeatEvent;
import org.springframework.cloud.kubernetes.commons.discovery.DefaultKubernetesServiceInstance;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InstanceDiscoveryListener.class, DiscoveryClient.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class InstanceDiscoveryListenerDiffblueTest {
  @MockitoBean private DiscoveryClient discoveryClient;

  @Autowired private InstanceDiscoveryListener instanceDiscoveryListener;

  @MockitoBean private InstanceRegistry instanceRegistry;

  @MockitoBean private InstanceRepository instanceRepository;

  /**
   * Test {@link InstanceDiscoveryListener#InstanceDiscoveryListener(DiscoveryClient,
   * InstanceRegistry, InstanceRepository)}.
   *
   * <ul>
   *   <li>Then return Services size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#InstanceDiscoveryListener(DiscoveryClient, InstanceRegistry,
   * InstanceRepository)}
   */
  @Test
  @DisplayName(
      "Test new InstanceDiscoveryListener(DiscoveryClient, InstanceRegistry, InstanceRepository); then return Services size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.<init>(DiscoveryClient, InstanceRegistry, InstanceRepository)"
  })
  void testNewInstanceDiscoveryListener_thenReturnServicesSizeIsOne() {
    // Arrange and Act
    InstanceDiscoveryListener actualInstanceDiscoveryListener =
        new InstanceDiscoveryListener(
            new CompositeDiscoveryClient(new ArrayList<>()), instanceRegistry, instanceRepository);

    // Assert
    Set<String> services = actualInstanceDiscoveryListener.getServices();
    assertEquals(1, services.size());
    assertTrue(actualInstanceDiscoveryListener.getIgnoredInstancesMetadata().isEmpty());
    assertTrue(actualInstanceDiscoveryListener.getInstancesMetadata().isEmpty());
    assertTrue(services.contains("*"));
    assertTrue(actualInstanceDiscoveryListener.getIgnoredServices().isEmpty());
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady2() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady3() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);

    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady4() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady5() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady6() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, writeFunction);

    Function<Publisher<Object>, Publisher<Void>> writeFunction2 = mock(Function.class);
    Flux<Void> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(writeFunction2.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult2);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source2, writeFunction2);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(writeFunction2).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given ArrayList() add '42'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenArrayListAdd42_thenCallsMap() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor doOnNext(Consumer) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorDoOnNextReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#doOnNext(Consumer)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor doOnNext(Consumer) return 'null'; then calls doOnNext(Consumer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorDoOnNextReturnNull_thenCallsDoOnNext() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorFilterReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorFilterReturnFromIterableArrayList2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorFilterReturnFromIterableArrayList3() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorFilterReturnNull_thenCallsFilter() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorFilterReturnNull_thenCallsFilter2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor filter(Predicate) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorFilterReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorFlatMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#groupBy(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor groupBy(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorGroupByReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult =
        Flux.fromIterable(new ArrayList<>());
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor map(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return {@code null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given DirectProcessor map(Function) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenDirectProcessorMapReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenFluxFlatMapReturnFromIterableArrayList() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenFluxFlatMapReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return {@code null}.
   *   <li>Then calls {@link Flux#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given Flux flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenFluxFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link GroupedFlux} {@link GroupedFlux#key()} return {@code Key}.
   *   <li>Then calls {@link GroupedFlux#key()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given GroupedFlux key() return 'Key'; then calls key()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenGroupedFluxKeyReturnKey_thenCallsKey() {
    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InMemoryEventStore findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInMemoryEventStoreFindAllReturnCreateThreeAndTrue() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InMemoryEventStore findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInMemoryEventStoreFindAllReturnFromIterableArrayList() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link InMemoryEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InstanceId with value is '42'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInstanceIdWithValueIs42_thenCallsFindAll() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link Publisher} {@link Publisher#subscribe(Subscriber)} does nothing.
   *   <li>Then calls {@link Publisher#subscribe(Subscriber)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given Publisher subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenPublisherSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    Publisher<Object> source = mock(Publisher.class);
    doNothing().when(source).subscribe(Mockito.<Subscriber<Object>>any());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);
    String[] args = new String[] {"Args"};

    ApplicationReadyEvent event =
        new ApplicationReadyEvent(
            application,
            args,
            new AnnotationConfigReactiveWebApplicationContext(),
            Duration.ofSeconds(1L));

    // Act
    instanceDiscoveryListener.onApplicationReady(event);

    // Assert
    verify(eventStore).findAll();
    verify(source).subscribe(isA(Subscriber.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered2() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered3() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered4() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);

    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered5() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered6() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered7() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, writeFunction);

    Function<Publisher<Object>, Publisher<Void>> writeFunction2 = mock(Function.class);
    Flux<Void> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(writeFunction2.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult2);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source2, writeFunction2);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(writeFunction2).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given ArrayList() add '42'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenArrayListAdd42_thenCallsMap() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor doOnNext(Consumer) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorDoOnNextReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor doOnNext(Consumer) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorDoOnNextReturnNull() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorFilterReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorFilterReturnFromIterableArrayList2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorFilterReturnFromIterableArrayList3() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorFilterReturnNull_thenCallsFilter() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorFilterReturnNull_thenCallsFilter2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor filter(Predicate) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorFilterReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorFlatMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#groupBy(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor groupBy(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorGroupByReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult =
        Flux.fromIterable(new ArrayList<>());
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor map(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return {@code null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given DirectProcessor map(Function) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenDirectProcessorMapReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenFluxFlatMapReturnFromIterableArrayList() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenFluxFlatMapReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return {@code null}.
   *   <li>Then calls {@link Flux#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given Flux flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenFluxFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link GroupedFlux} {@link GroupedFlux#key()} return {@code Key}.
   *   <li>Then calls {@link GroupedFlux#key()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given GroupedFlux key() return 'Key'; then calls key()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenGroupedFluxKeyReturnKey_thenCallsKey() {
    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given InMemoryEventStore findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenInMemoryEventStoreFindAllReturnCreateThreeAndTrue() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link InMemoryEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given InstanceId with value is '42'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenInstanceIdWithValueIs42_thenCallsFindAll() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link Publisher} {@link Publisher#subscribe(Subscriber)} does nothing.
   *   <li>Then calls {@link Publisher#subscribe(Subscriber)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given Publisher subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenPublisherSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    Publisher<Object> source = mock(Publisher.class);
    doNothing().when(source).subscribe(Mockito.<Subscriber<Object>>any());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(source).subscribe(isA(Subscriber.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances2() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances3() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);

    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances4() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances5() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances6() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, writeFunction);

    Function<Publisher<Object>, Publisher<Void>> writeFunction2 = mock(Function.class);
    Flux<Void> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(writeFunction2.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult2);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source2, writeFunction2);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(writeFunction2).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given ArrayList() add '42'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenArrayListAdd42_thenCallsMap() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor doOnNext(Consumer) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorDoOnNextReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#doOnNext(Consumer)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor doOnNext(Consumer) return 'null'; then calls doOnNext(Consumer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorDoOnNextReturnNull_thenCallsDoOnNext() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorFilterReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorFilterReturnFromIterableArrayList2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorFilterReturnFromIterableArrayList3() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorFilterReturnNull_thenCallsFilter() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorFilterReturnNull_thenCallsFilter2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor filter(Predicate) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorFilterReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorFlatMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#groupBy(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor groupBy(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorGroupByReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult =
        Flux.fromIterable(new ArrayList<>());
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor map(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return {@code null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given DirectProcessor map(Function) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenDirectProcessorMapReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenFluxFlatMapReturnFromIterableArrayList() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenFluxFlatMapReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return {@code null}.
   *   <li>Then calls {@link Flux#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given Flux flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenFluxFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link GroupedFlux} {@link GroupedFlux#key()} return {@code Key}.
   *   <li>Then calls {@link GroupedFlux#key()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given GroupedFlux key() return 'Key'; then calls key()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenGroupedFluxKeyReturnKey_thenCallsKey() {
    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InMemoryEventStore findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInMemoryEventStoreFindAllReturnCreateThreeAndTrue() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InMemoryEventStore findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInMemoryEventStoreFindAllReturnFromIterableArrayList() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link InMemoryEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InstanceId with value is '42'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInstanceIdWithValueIs42_thenCallsFindAll() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link Publisher} {@link Publisher#subscribe(Subscriber)} does nothing.
   *   <li>Then calls {@link Publisher#subscribe(Subscriber)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given Publisher subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenPublisherSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    Publisher<Object> source = mock(Publisher.class);
    doNothing().when(source).subscribe(Mockito.<Subscriber<Object>>any());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(source).subscribe(isA(Subscriber.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat2() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);

    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat3() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat4() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat5() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, writeFunction);

    Function<Publisher<Object>, Publisher<Void>> writeFunction2 = mock(Function.class);
    Flux<Void> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(writeFunction2.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult2);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source2, writeFunction2);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(writeFunction2).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given ArrayList() add '42'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenArrayListAdd42_thenCallsMap() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor doOnNext(Consumer) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorDoOnNextReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#doOnNext(Consumer)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor doOnNext(Consumer) return 'null'; then calls doOnNext(Consumer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorDoOnNextReturnNull_thenCallsDoOnNext() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorFilterReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorFilterReturnFromIterableArrayList2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorFilterReturnFromIterableArrayList3() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorFilterReturnNull_thenCallsFilter() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorFilterReturnNull_thenCallsFilter2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor filter(Predicate) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorFilterReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorFlatMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#groupBy(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor groupBy(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorGroupByReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult =
        Flux.fromIterable(new ArrayList<>());
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor map(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return {@code null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given DirectProcessor map(Function) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenDirectProcessorMapReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenFluxFlatMapReturnFromIterableArrayList() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenFluxFlatMapReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return {@code null}.
   *   <li>Then calls {@link Flux#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given Flux flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenFluxFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link GroupedFlux} {@link GroupedFlux#key()} return {@code Key}.
   *   <li>Then calls {@link GroupedFlux#key()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given GroupedFlux key() return 'Key'; then calls key()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenGroupedFluxKeyReturnKey_thenCallsKey() {
    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InMemoryEventStore findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInMemoryEventStoreFindAllReturnCreateThreeAndTrue() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InMemoryEventStore findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInMemoryEventStoreFindAllReturnFromIterableArrayList() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link InMemoryEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceId with value is '42'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceIdWithValueIs42_thenCallsFindAll() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link InMemoryEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceId with value is '42'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceIdWithValueIs42_thenCallsFindAll2() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link Publisher} {@link Publisher#subscribe(Subscriber)} does nothing.
   *   <li>Then calls {@link Publisher#subscribe(Subscriber)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given Publisher subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenPublisherSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    Publisher<Object> source = mock(Publisher.class);
    doNothing().when(source).subscribe(Mockito.<Subscriber<Object>>any());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(source).subscribe(isA(Subscriber.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent2() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);

    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent3() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent4() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent5() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, writeFunction);

    Function<Publisher<Object>, Publisher<Void>> writeFunction2 = mock(Function.class);
    Flux<Void> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(writeFunction2.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult2);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source2, writeFunction2);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(writeFunction2).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given ArrayList() add '42'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenArrayListAdd42_thenCallsMap() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor doOnNext(Consumer) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorDoOnNextReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#doOnNext(Consumer)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor doOnNext(Consumer) return 'null'; then calls doOnNext(Consumer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorDoOnNextReturnNull_thenCallsDoOnNext() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorFilterReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorFilterReturnFromIterableArrayList2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorFilterReturnFromIterableArrayList3() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorFilterReturnNull_thenCallsFilter() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorFilterReturnNull_thenCallsFilter2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor filter(Predicate) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorFilterReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorFlatMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#groupBy(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor groupBy(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorGroupByReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult =
        Flux.fromIterable(new ArrayList<>());
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor map(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return {@code null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given DirectProcessor map(Function) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenDirectProcessorMapReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenFluxFlatMapReturnFromIterableArrayList() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenFluxFlatMapReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return {@code null}.
   *   <li>Then calls {@link Flux#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given Flux flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenFluxFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link GroupedFlux} {@link GroupedFlux#key()} return {@code Key}.
   *   <li>Then calls {@link GroupedFlux#key()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given GroupedFlux key() return 'Key'; then calls key()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenGroupedFluxKeyReturnKey_thenCallsKey() {
    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InMemoryEventStore findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInMemoryEventStoreFindAllReturnCreateThreeAndTrue() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InMemoryEventStore findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInMemoryEventStoreFindAllReturnFromIterableArrayList() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link InMemoryEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InstanceId with value is '42'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceIdWithValueIs42_thenCallsFindAll() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link InMemoryEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InstanceId with value is '42'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceIdWithValueIs42_thenCallsFindAll2() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link Publisher} {@link Publisher#subscribe(Subscriber)} does nothing.
   *   <li>Then calls {@link Publisher#subscribe(Subscriber)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given Publisher subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenPublisherSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    Publisher<Object> source = mock(Publisher.class);
    doNothing().when(source).subscribe(Mockito.<Subscriber<Object>>any());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(source).subscribe(isA(Subscriber.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover2() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover3() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);

    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover4() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover5() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class));
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover6() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> source2 = new ChannelSendOperator<>(source, writeFunction);

    Function<Publisher<Object>, Publisher<Void>> writeFunction2 = mock(Function.class);
    Flux<Void> fromIterableResult2 = Flux.fromIterable(new ArrayList<>());
    when(writeFunction2.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult2);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source2, writeFunction2);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(writeFunction2).apply(isA(Publisher.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given ArrayList() add '42'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenArrayListAdd42_thenCallsMap() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor doOnNext(Consumer) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorDoOnNextReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#doOnNext(Consumer)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor doOnNext(Consumer) return 'null'; then calls doOnNext(Consumer)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorDoOnNextReturnNull_thenCallsDoOnNext() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor5);

    DirectProcessor<InstanceEvent> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor6).groupBy(isA(Function.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorFilterReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorFilterReturnFromIterableArrayList2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor filter(Predicate) return fromIterable ArrayList(); then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorFilterReturnFromIterableArrayList_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorFilterReturnNull_thenCallsFilter() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<InstanceEvent> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor filter(Predicate) return 'null'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorFilterReturnNull_thenCallsFilter2() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<InstanceEvent> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor3).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor filter(Predicate) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorFilterReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Object, Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<InstanceEvent> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor5).groupBy(isA(Function.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorFlatMapReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return {@code
   *       null}.
   *   <li>Then calls {@link DirectProcessor#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor6);

    DirectProcessor<InstanceEvent> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor7).groupBy(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#groupBy(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor groupBy(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorGroupByReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult =
        Flux.fromIterable(new ArrayList<>());
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor map(Function) return fromIterable ArrayList(); then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorMapReturnFromIterableArrayList_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return {@code null}.
   *   <li>Then calls {@link DirectProcessor#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given DirectProcessor map(Function) return 'null'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenDirectProcessorMapReturnNull_thenCallsMap() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<InstanceEvent> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor4).groupBy(isA(Function.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link Flux#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given Flux flatMap(Function) return fromIterable ArrayList(); then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenFluxFlatMapReturnFromIterableArrayList_thenCallsFlatMap() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link Flux#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given Flux flatMap(Function) return fromIterable ArrayList(); then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenFluxFlatMapReturnFromIterableArrayList_thenCallsFlatMap2() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<Object> fromIterableResult = Flux.fromIterable(it);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return {@code null}.
   *   <li>Then calls {@link Flux#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given Flux flatMap(Function) return 'null'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenFluxFlatMapReturnNull_thenCallsFlatMap() {
    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(null);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link GroupedFlux} {@link GroupedFlux#key()} return {@code Key}.
   *   <li>Then calls {@link GroupedFlux#key()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given GroupedFlux key() return 'Key'; then calls key()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenGroupedFluxKeyReturnKey_thenCallsKey() {
    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given InMemoryEventStore findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInMemoryEventStoreFindAllReturnCreateThreeAndTrue() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore} {@link InMemoryEventStore#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given InMemoryEventStore findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInMemoryEventStoreFindAllReturnFromIterableArrayList() {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then calls {@link InMemoryEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given InstanceId with value is '42'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceIdWithValueIs42_thenCallsFindAll() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link Publisher} {@link Publisher#subscribe(Subscriber)} does nothing.
   *   <li>Then calls {@link Publisher#subscribe(Subscriber)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given Publisher subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenPublisherSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    Publisher<Object> source = mock(Publisher.class);
    doNothing().when(source).subscribe(Mockito.<Subscriber<Object>>any());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Object> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Object, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Object> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor5);

    DirectProcessor<Object> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor6);

    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(
            Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(directProcessor7);

    DirectProcessor<InstanceEvent> directProcessor8 = mock(DirectProcessor.class);
    when(directProcessor8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(source).subscribe(isA(Subscriber.class));
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(directProcessor8).groupBy(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances() throws AssertionError {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances2() throws AssertionError {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new SnapshottingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances3() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findAll()).thenReturn(fromIterableResult);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(repository).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances4() {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(directProcessor.then()).thenReturn(channelSendOperator);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Object> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor3);

    DirectProcessor<Instance> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.map(Mockito.<Function<Instance, Object>>any()))
        .thenReturn(directProcessor4);

    DirectProcessor<Instance> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor5);

    DirectProcessor<Instance> directProcessor7 = mock(DirectProcessor.class);
    when(directProcessor7.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor6);

    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.findAll()).thenReturn(directProcessor7);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    Mono<Void> actualRemoveStaleInstancesResult =
        instanceDiscoveryListener.removeStaleInstances(new HashSet<>());

    // Assert
    verify(repository).findAll();
    verify(directProcessor3).doOnNext(isA(Consumer.class));
    verify(directProcessor7).filter(isA(Predicate.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).flatMap(isA(Function.class));
    verify(directProcessor5).map(isA(Function.class));
    verify(directProcessor).then();
    assertSame(channelSendOperator, actualRemoveStaleInstancesResult);
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#doOnNext(Consumer)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName(
      "Test removeStaleInstances(Set); given DirectProcessor doOnNext(Consumer) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenDirectProcessorDoOnNextReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Instance> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.map(Mockito.<Function<Instance, Object>>any()))
        .thenReturn(directProcessor2);

    DirectProcessor<Instance> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor3);

    DirectProcessor<Instance> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor4);

    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.findAll()).thenReturn(directProcessor5);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(repository).findAll();
    verify(directProcessor).doOnNext(isA(Consumer.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName(
      "Test removeStaleInstances(Set); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenDirectProcessorFilterReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);

    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.findAll()).thenReturn(directProcessor);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(repository).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName(
      "Test removeStaleInstances(Set); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenDirectProcessorFilterReturnFromIterableArrayList2()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Instance> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor);

    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.findAll()).thenReturn(directProcessor2);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(repository).findAll();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName(
      "Test removeStaleInstances(Set); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenDirectProcessorFilterReturnFromIterableArrayList3()
      throws AssertionError {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Instance> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Instance, Object>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Instance> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor2);

    DirectProcessor<Instance> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor3);

    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.findAll()).thenReturn(directProcessor4);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(repository).findAll();
    verify(directProcessor4).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).filter(isA(Predicate.class));
    verify(directProcessor2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#flatMap(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName(
      "Test removeStaleInstances(Set); given DirectProcessor flatMap(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenDirectProcessorFlatMapReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<Object, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Object> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(directProcessor);

    DirectProcessor<Object> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Object>>any())).thenReturn(directProcessor2);

    DirectProcessor<Instance> directProcessor4 = mock(DirectProcessor.class);
    when(directProcessor4.map(Mockito.<Function<Instance, Object>>any()))
        .thenReturn(directProcessor3);

    DirectProcessor<Instance> directProcessor5 = mock(DirectProcessor.class);
    when(directProcessor5.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor4);

    DirectProcessor<Instance> directProcessor6 = mock(DirectProcessor.class);
    when(directProcessor6.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor5);

    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.findAll()).thenReturn(directProcessor6);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(repository).findAll();
    verify(directProcessor2).doOnNext(isA(Consumer.class));
    verify(directProcessor6).filter(isA(Predicate.class));
    verify(directProcessor5).filter(isA(Predicate.class));
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class));
    verify(directProcessor4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#map(Function)} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName(
      "Test removeStaleInstances(Set); given DirectProcessor map(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenDirectProcessorMapReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.map(Mockito.<Function<Instance, Object>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Instance> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor);

    DirectProcessor<Instance> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor2);

    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.findAll()).thenReturn(directProcessor3);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(repository).findAll();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   *
   * <ul>
   *   <li>Then calls {@link InMemoryEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_thenCallsFindAll() throws AssertionError {
    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterService(String)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#shouldRegisterService(String)}
   */
  @Test
  @DisplayName("Test shouldRegisterService(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.shouldRegisterService(String)"})
  void testShouldRegisterService() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertTrue(instanceDiscoveryListener.shouldRegisterService("42"));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterService(String)}.
   *
   * <ul>
   *   <li>Given {@link HashSet#HashSet()} add {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#shouldRegisterService(String)}
   */
  @Test
  @DisplayName("Test shouldRegisterService(String); given HashSet() add '42'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.shouldRegisterService(String)"})
  void testShouldRegisterService_givenHashSetAdd42_thenReturnTrue() {
    // Arrange
    HashSet<String> services = new HashSet<>();
    services.add("42");
    services.add("Ignoring service '{}' from discovery.");
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setServices(services);

    // Act and Assert
    assertTrue(instanceDiscoveryListener.shouldRegisterService("42"));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterService(String)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#shouldRegisterService(String)}
   */
  @Test
  @DisplayName("Test shouldRegisterService(String); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.shouldRegisterService(String)"})
  void testShouldRegisterService_thenReturnFalse() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setServices(new HashSet<>());

    // Act and Assert
    assertFalse(instanceDiscoveryListener.shouldRegisterService("42"));
  }

  /**
   * Test {@link InstanceDiscoveryListener#matchesPattern(String, Set)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#matchesPattern(String, Set)}
   */
  @Test
  @DisplayName(
      "Test matchesPattern(String, Set); given '42'; when HashSet() add '42'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.matchesPattern(String, Set)"})
  void testMatchesPattern_given42_whenHashSetAdd42_thenReturnTrue() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    HashSet<String> patterns = new HashSet<>();
    patterns.add("42");
    patterns.add("foo");

    // Act and Assert
    assertTrue(instanceDiscoveryListener.matchesPattern("42", patterns));
  }

  /**
   * Test {@link InstanceDiscoveryListener#matchesPattern(String, Set)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#matchesPattern(String, Set)}
   */
  @Test
  @DisplayName(
      "Test matchesPattern(String, Set); given 'foo'; when HashSet() add 'foo'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.matchesPattern(String, Set)"})
  void testMatchesPattern_givenFoo_whenHashSetAddFoo_thenReturnFalse() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    HashSet<String> patterns = new HashSet<>();
    patterns.add("foo");

    // Act and Assert
    assertFalse(instanceDiscoveryListener.matchesPattern("42", patterns));
  }

  /**
   * Test {@link InstanceDiscoveryListener#matchesPattern(String, Set)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#matchesPattern(String, Set)}
   */
  @Test
  @DisplayName("Test matchesPattern(String, Set); when HashSet(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.matchesPattern(String, Set)"})
  void testMatchesPattern_whenHashSet_thenReturnFalse() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertFalse(instanceDiscoveryListener.matchesPattern("42", new HashSet<>()));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName("Test shouldRegisterInstanceBasedOnMetadata(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InstanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(ServiceInstance)"
  })
  void testShouldRegisterInstanceBasedOnMetadata() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertTrue(
        instanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(
            new DefaultServiceInstance()));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}.
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName("Test shouldRegisterInstanceBasedOnMetadata(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InstanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(ServiceInstance)"
  })
  void testShouldRegisterInstanceBasedOnMetadata2() {
    // Arrange
    HashMap<String, String> ignoredInstancesMetadata = new HashMap<>();
    ignoredInstancesMetadata.put("Key", "42");
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setIgnoredInstancesMetadata(ignoredInstancesMetadata);

    // Act and Assert
    assertTrue(
        instanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(
            new DefaultServiceInstance()));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName("Test shouldRegisterInstanceBasedOnMetadata(ServiceInstance); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InstanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(ServiceInstance)"
  })
  void testShouldRegisterInstanceBasedOnMetadata_thenReturnFalse() {
    // Arrange
    HashMap<String, String> instancesMetadata = new HashMap<>();
    instancesMetadata.put("Key", "42");
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setInstancesMetadata(instancesMetadata);

    // Act and Assert
    assertFalse(
        instanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(
            new DefaultServiceInstance()));
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance() throws AssertionError {
    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    String value = "42";
    InstanceId ofResult = InstanceId.of(value);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            generator,
            mock(InstanceFilter.class));
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualPublisher = instanceDiscoveryListener.registerInstance(instance);

    // Assert
    FirstStep<InstanceId> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            i -> {
              assertSame(ofResult, i);
              return;
            })
        .expectComplete()
        .verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance2() throws AssertionError {
    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    String value = "42";
    InstanceId ofResult = InstanceId.of(value);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);
    InstanceRegistry registry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            generator,
            mock(InstanceFilter.class));
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualPublisher = instanceDiscoveryListener.registerInstance(instance);

    // Assert
    FirstStep<InstanceId> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            i -> {
              assertSame(ofResult, i);
              return;
            })
        .expectComplete()
        .verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance3() {
    // Arrange
    InstanceRegistry registry = mock(InstanceRegistry.class);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(registry.register(Mockito.<Registration>any())).thenReturn(justResult);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualRegisterInstanceResult =
        instanceDiscoveryListener.registerInstance(instance);

    // Assert
    verify(registry).register(isA(Registration.class));
    assertSame(justResult, actualRegisterInstanceResult);
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance4() {
    // Arrange
    InstanceRegistry registry = mock(InstanceRegistry.class);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(registry.register(Mockito.<Registration>any())).thenReturn(justResult);

    ServiceInstanceConverter converter = mock(ServiceInstanceConverter.class);
    when(converter.convert(Mockito.<ServiceInstance>any()))
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setConverter(converter);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualRegisterInstanceResult =
        instanceDiscoveryListener.registerInstance(instance);

    // Assert
    verify(converter).convert(isA(ServiceInstance.class));
    verify(registry).register(isA(Registration.class));
    assertSame(justResult, actualRegisterInstanceResult);
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance5() {
    // Arrange
    InstanceRegistry registry = mock(InstanceRegistry.class);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(registry.register(Mockito.<Registration>any())).thenReturn(justResult);

    ServiceInstanceConverter converter = mock(ServiceInstanceConverter.class);
    when(converter.convert(Mockito.<ServiceInstance>any()))
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setConverter(converter);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualRegisterInstanceResult =
        instanceDiscoveryListener.registerInstance(instance);

    // Assert
    verify(converter).convert(isA(ServiceInstance.class));
    verify(registry).register(isA(Registration.class));
    assertSame(justResult, actualRegisterInstanceResult);
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance6() {
    // Arrange
    InstanceRegistry registry = mock(InstanceRegistry.class);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(registry.register(Mockito.<Registration>any())).thenReturn(justResult);

    ServiceInstanceConverter converter = mock(ServiceInstanceConverter.class);
    when(converter.convert(Mockito.<ServiceInstance>any()))
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("")
                .source("Source")
                .build());
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setConverter(converter);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualRegisterInstanceResult =
        instanceDiscoveryListener.registerInstance(instance);

    // Assert
    verify(converter).convert(isA(ServiceInstance.class));
    verify(registry).register(isA(Registration.class));
    assertSame(justResult, actualRegisterInstanceResult);
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test registerInstance(ServiceInstance); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_givenEventsourcingInstanceRepositoryWithEventStoreIsNull()
      throws AssertionError {
    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(null), generator, mock(InstanceFilter.class));
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualPublisher = instanceDiscoveryListener.registerInstance(instance);

    // Assert
    FirstStep<InstanceId> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#compute(InstanceId,
   *       BiFunction)} return {@code null}.
   *   <li>Then calls {@link InstanceRepository#compute(InstanceId, BiFunction)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test registerInstance(ServiceInstance); given InstanceRepository compute(InstanceId, BiFunction) return 'null'; then calls compute(InstanceId, BiFunction)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_givenInstanceRepositoryComputeReturnNull_thenCallsCompute()
      throws AssertionError {
    // Arrange
    InstanceRepository repository = mock(InstanceRepository.class);
    when(repository.compute(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(null);

    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));

    InstanceRegistry registry =
        new InstanceRegistry(repository, generator, mock(InstanceFilter.class));
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualPublisher = instanceDiscoveryListener.registerInstance(instance);

    // Assert
    FirstStep<InstanceId> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(repository).compute(isA(InstanceId.class), isA(BiFunction.class));
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_thenCallsFind() throws AssertionError {
    // Arrange
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));

    InstanceRegistry registry =
        new InstanceRegistry(repository, generator, mock(InstanceFilter.class));
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualPublisher = instanceDiscoveryListener.registerInstance(instance);

    // Assert
    FirstStep<InstanceId> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <ul>
   *   <li>When {@link DefaultServiceInstance#DefaultServiceInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance); when DefaultServiceInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_whenDefaultServiceInstance() throws AssertionError {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(
            instanceDiscoveryListener.registerInstance(new DefaultServiceInstance()));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceDiscoveryListener#toString(ServiceInstance)} with {@code ServiceInstance}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#toString(ServiceInstance)}
   */
  @Test
  @DisplayName("Test toString(ServiceInstance) with 'ServiceInstance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String InstanceDiscoveryListener.toString(ServiceInstance)"})
  void testToStringWithServiceInstance() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertEquals(
        "serviceId=null, instanceId=null, url= http://null:0",
        instanceDiscoveryListener.toString(new DefaultServiceInstance()));
  }

  /**
   * Test {@link InstanceDiscoveryListener#toString(ServiceInstance)} with {@code ServiceInstance}.
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#toString(ServiceInstance)}
   */
  @Test
  @DisplayName("Test toString(ServiceInstance) with 'ServiceInstance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String InstanceDiscoveryListener.toString(ServiceInstance)"})
  void testToStringWithServiceInstance2() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));
    HashMap<String, String> metadata = new HashMap<>();
    DefaultKubernetesServiceInstance instance =
        new DefaultKubernetesServiceInstance(
            "42", "42", "localhost", 8080, metadata, true, "http", "http", new HashMap<>());

    // Act
    String actualToStringResult = instanceDiscoveryListener.toString(instance);

    // Assert
    assertEquals("serviceId=42, instanceId=42, url= https://localhost:8080", actualToStringResult);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link InstanceDiscoveryListener#setConverter(ServiceInstanceConverter)}
   *   <li>{@link InstanceDiscoveryListener#setIgnoredInstancesMetadata(Map)}
   *   <li>{@link InstanceDiscoveryListener#setIgnoredServices(Set)}
   *   <li>{@link InstanceDiscoveryListener#setInstancesMetadata(Map)}
   *   <li>{@link InstanceDiscoveryListener#setServices(Set)}
   *   <li>{@link InstanceDiscoveryListener#getIgnoredInstancesMetadata()}
   *   <li>{@link InstanceDiscoveryListener#getIgnoredServices()}
   *   <li>{@link InstanceDiscoveryListener#getInstancesMetadata()}
   *   <li>{@link InstanceDiscoveryListener#getServices()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map InstanceDiscoveryListener.getIgnoredInstancesMetadata()",
    "Set InstanceDiscoveryListener.getIgnoredServices()",
    "Map InstanceDiscoveryListener.getInstancesMetadata()",
    "Set InstanceDiscoveryListener.getServices()",
    "void InstanceDiscoveryListener.setConverter(ServiceInstanceConverter)",
    "void InstanceDiscoveryListener.setIgnoredInstancesMetadata(Map)",
    "void InstanceDiscoveryListener.setIgnoredServices(Set)",
    "void InstanceDiscoveryListener.setInstancesMetadata(Map)",
    "void InstanceDiscoveryListener.setServices(Set)"
  })
  void testGettersAndSetters() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(
            discoveryClient,
            registry,
            new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act
    instanceDiscoveryListener.setConverter(mock(ServiceInstanceConverter.class));
    HashMap<String, String> ignoredInstancesMetadata = new HashMap<>();
    instanceDiscoveryListener.setIgnoredInstancesMetadata(ignoredInstancesMetadata);
    HashSet<String> ignoredServices = new HashSet<>();
    instanceDiscoveryListener.setIgnoredServices(ignoredServices);
    HashMap<String, String> instancesMetadata = new HashMap<>();
    instanceDiscoveryListener.setInstancesMetadata(instancesMetadata);
    HashSet<String> services = new HashSet<>();
    instanceDiscoveryListener.setServices(services);
    Map<String, String> actualIgnoredInstancesMetadata =
        instanceDiscoveryListener.getIgnoredInstancesMetadata();
    Set<String> actualIgnoredServices = instanceDiscoveryListener.getIgnoredServices();
    Map<String, String> actualInstancesMetadata = instanceDiscoveryListener.getInstancesMetadata();
    Set<String> actualServices = instanceDiscoveryListener.getServices();

    // Assert
    assertTrue(actualIgnoredInstancesMetadata.isEmpty());
    assertTrue(actualInstancesMetadata.isEmpty());
    assertTrue(actualIgnoredServices.isEmpty());
    assertTrue(actualServices.isEmpty());
    assertSame(ignoredInstancesMetadata, actualIgnoredInstancesMetadata);
    assertSame(instancesMetadata, actualInstancesMetadata);
    assertSame(ignoredServices, actualIgnoredServices);
    assertSame(services, actualServices);
  }
}
