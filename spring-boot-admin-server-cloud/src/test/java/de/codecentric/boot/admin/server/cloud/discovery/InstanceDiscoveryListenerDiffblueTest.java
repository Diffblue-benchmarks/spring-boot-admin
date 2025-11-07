package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {InstanceDiscoveryListener.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class InstanceDiscoveryListenerDiffblueTest {
  @MockBean
  private DiscoveryClient discoveryClient;

  @Autowired
  private InstanceDiscoveryListener instanceDiscoveryListener;

  @MockBean
  private InstanceRegistry instanceRegistry;

  @MockBean
  private InstanceRepository instanceRepository;

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any())).thenReturn(null);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady13() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady14() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady15() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady16() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady17() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady18() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady19() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady20() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady21() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady22() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady23() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  void testOnApplicationReady24() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class)));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any())).thenReturn(null);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered13() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered14() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered15() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered16() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered17() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered18() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered19() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered20() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered21() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered22() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered23() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  void testOnInstanceRegistered24() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class)));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any())).thenReturn(null);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances13() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances14() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances15() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances16() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances17() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances18() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances19() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances20() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances21() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances22() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances23() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  void testOnRefreshInstances24() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class)));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any())).thenReturn(null);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat13() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat14() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat15() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat16() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat17() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat18() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat19() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat20() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat21() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat22() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  void testOnParentHeartbeat23() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class)));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any())).thenReturn(null);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent13() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent14() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent15() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent16() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent17() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent18() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent19() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent20() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent21() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent22() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  void testOnApplicationEvent23() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class)));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(eventStore.findAll()).thenReturn(createResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GroupedFlux<Object, InstanceEvent> groupedFlux = mock(GroupedFlux.class);
    when(groupedFlux.key()).thenReturn("Key");

    ArrayList<GroupedFlux<Object, InstanceEvent>> it = new ArrayList<>();
    it.add(groupedFlux);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(it);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
    verify(groupedFlux).key();
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any())).thenReturn(null);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover13() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover14() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover15() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover16() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover17() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover18() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover19() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover20() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(null);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover21() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover22() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover23() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(source, writeFunction));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(writeFunction).apply(isA(Publisher.class));
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  void testDiscover24() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    when(flux.then()).thenReturn(new ChannelSendOperator<>(new InMemoryEventStore(), mock(Function.class)));
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());

    // Act
    (new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository)).discover();

    // Assert
    verify(eventStore).findAll();
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances3() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(fromIterableResult);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(flux).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances4() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<GroupedFlux<Object, InstanceEvent>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    Flux<InstanceEvent> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux2);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(flux).flatMap(isA(Function.class));
    verify(flux2).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances5() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<GroupedFlux<Object, InstanceEvent>> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux);
    Flux<InstanceEvent> flux3 = mock(Flux.class);
    when(flux3.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux2);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux3);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux3).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances6() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<GroupedFlux<Object, InstanceEvent>> flux3 = mock(Flux.class);
    when(flux3.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux2);
    Flux<InstanceEvent> flux4 = mock(Flux.class);
    when(flux4.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux3);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux4);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux3).flatMap(isA(Function.class));
    verify(flux4).groupBy(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances7() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.map(Mockito.<Function<Object, Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<GroupedFlux<Object, InstanceEvent>> flux4 = mock(Flux.class);
    when(flux4.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux3);
    Flux<InstanceEvent> flux5 = mock(Flux.class);
    when(flux5.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux4);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux5);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux4).flatMap(isA(Function.class));
    verify(flux5).groupBy(isA(Function.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances8() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<GroupedFlux<Object, InstanceEvent>> flux5 = mock(Flux.class);
    when(flux5.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux4);
    Flux<InstanceEvent> flux6 = mock(Flux.class);
    when(flux6.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux5);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux6);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux5).flatMap(isA(Function.class));
    verify(flux6).groupBy(isA(Function.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances9() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<GroupedFlux<Object, InstanceEvent>> flux6 = mock(Flux.class);
    when(flux6.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux5);
    Flux<InstanceEvent> flux7 = mock(Flux.class);
    when(flux7.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux6);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux7);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux6).flatMap(isA(Function.class));
    verify(flux7).groupBy(isA(Function.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances10() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<GroupedFlux<Object, InstanceEvent>> flux7 = mock(Flux.class);
    when(flux7.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux6);
    Flux<InstanceEvent> flux8 = mock(Flux.class);
    when(flux8.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux7);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux8);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux7).flatMap(isA(Function.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux8).groupBy(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator = new ChannelSendOperator<>(source, mock(Function.class));

    when(flux.then()).thenReturn(channelSendOperator);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux2);
    Flux<Object> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux3);
    Flux<Object> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Object, Object>>any())).thenReturn(flux4);
    Flux<Object> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux5);
    Flux<Object> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux6);
    Flux<GroupedFlux<Object, InstanceEvent>> flux8 = mock(Flux.class);
    when(flux8.flatMap(Mockito.<Function<GroupedFlux<Object, InstanceEvent>, Publisher<Object>>>any()))
        .thenReturn(flux7);
    Flux<InstanceEvent> flux9 = mock(Flux.class);
    when(flux9.groupBy(Mockito.<Function<InstanceEvent, Object>>any())).thenReturn(flux8);
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(flux9);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
        repository);

    // Act
    Mono<Void> actualRemoveStaleInstancesResult = instanceDiscoveryListener.removeStaleInstances(new HashSet<>());

    // Assert
    verify(eventStore).findAll();
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux8).flatMap(isA(Function.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux9).groupBy(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
    assertSame(channelSendOperator, actualRemoveStaleInstancesResult);
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  void testRemoveStaleInstances12() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new SnapshottingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#shouldRegisterService(String)}
   */
  @Test
  void testShouldRegisterService() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    assertTrue((new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()))).shouldRegisterService("42"));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#shouldRegisterService(String)}
   */
  @Test
  void testShouldRegisterService2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setServices(new HashSet<>());

    // Act and Assert
    assertFalse(instanceDiscoveryListener.shouldRegisterService("42"));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#shouldRegisterService(String)}
   */
  @Test
  void testShouldRegisterService3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<String> services = new HashSet<>();
    services.add("42");
    services.add("Ignoring service '{}' from discovery.");
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setServices(services);

    // Act and Assert
    assertTrue(instanceDiscoveryListener.shouldRegisterService("42"));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#matchesPattern(String, Set)}
   */
  @Test
  void testMatchesPattern() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertFalse(instanceDiscoveryListener.matchesPattern("42", new HashSet<>()));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#matchesPattern(String, Set)}
   */
  @Test
  void testMatchesPattern2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    HashSet<String> patterns = new HashSet<>();
    patterns.add("foo");

    // Act and Assert
    assertFalse(instanceDiscoveryListener.matchesPattern("42", patterns));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#matchesPattern(String, Set)}
   */
  @Test
  void testMatchesPattern3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    HashSet<String> patterns = new HashSet<>();
    patterns.add("42");
    patterns.add("foo");

    // Act and Assert
    assertTrue(instanceDiscoveryListener.matchesPattern("42", patterns));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}
   */
  @Test
  void testShouldRegisterInstanceBasedOnMetadata() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertTrue(instanceDiscoveryListener
        .shouldRegisterInstanceBasedOnMetadata(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    String value = "42";
    InstanceId ofResult = InstanceId.of(value);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        generator, mock(InstanceFilter.class));

    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.assertNext(i -> {
      assertSame(ofResult, i);
      return;
    }).expectComplete().verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(null), generator,
        mock(InstanceFilter.class));

    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectComplete().verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance3() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry registry = new InstanceRegistry(repository, generator, mock(InstanceFilter.class));

    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectError().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance4() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Flux<InstanceEvent> flux = mock(Flux.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(flux.collectList()).thenReturn(justResult);
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry registry = new InstanceRegistry(repository, generator, mock(InstanceFilter.class));

    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectError().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(generator).generateId(isA(Registration.class));
    verify(flux).collectList();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance5() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Mono<List<InstanceEvent>> mono = mock(Mono.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(mono.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(justResult);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.collectList()).thenReturn(mono);
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry registry = new InstanceRegistry(repository, generator, mock(InstanceFilter.class));

    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectError().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(generator).generateId(isA(Registration.class));
    verify(flux).collectList();
    verify(mono).filter(isA(Predicate.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance6() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Mono<List<InstanceEvent>> mono = mock(Mono.class);
    Mono<Object> justResult = Mono.just("Data");
    when(mono.map(Mockito.<Function<List<InstanceEvent>, Object>>any())).thenReturn(justResult);
    Mono<List<InstanceEvent>> mono2 = mock(Mono.class);
    when(mono2.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(mono);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.collectList()).thenReturn(mono2);
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry registry = new InstanceRegistry(repository, generator, mock(InstanceFilter.class));

    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectError().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(generator).generateId(isA(Registration.class));
    verify(flux).collectList();
    verify(mono2).filter(isA(Predicate.class));
    verify(mono).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance7() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Mono<Object> mono = mock(Mono.class);
    Mono<Object> justResult = Mono.just("Data");
    when(mono.flatMap(Mockito.<Function<Object, Mono<Object>>>any())).thenReturn(justResult);
    Mono<List<InstanceEvent>> mono2 = mock(Mono.class);
    when(mono2.map(Mockito.<Function<List<InstanceEvent>, Object>>any())).thenReturn(mono);
    Mono<List<InstanceEvent>> mono3 = mock(Mono.class);
    when(mono3.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(mono2);
    Flux<InstanceEvent> flux = mock(Flux.class);
    when(flux.collectList()).thenReturn(mono3);
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(flux);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry registry = new InstanceRegistry(repository, generator, mock(InstanceFilter.class));

    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectError().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(generator).generateId(isA(Registration.class));
    verify(flux).collectList();
    verify(mono3).filter(isA(Predicate.class));
    verify(mono).flatMap(isA(Function.class));
    verify(mono2).map(isA(Function.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance8() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceRepository repository = mock(InstanceRepository.class);
    when(repository.compute(Mockito.<InstanceId>any(), Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(null);
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry registry = new InstanceRegistry(repository, generator, mock(InstanceFilter.class));

    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectComplete().verify();
    verify(repository).compute(isA(InstanceId.class), isA(BiFunction.class));
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance9() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(null);
    InstanceRegistry registry = new InstanceRegistry(mock(InstanceRepository.class), generator,
        mock(InstanceFilter.class));

    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectComplete().verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance10() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, null,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    InstanceRegistry registry = mock(InstanceRegistry.class);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(registry.register(Mockito.<Registration>any())).thenReturn(justResult);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act
    Mono<InstanceId> actualRegisterInstanceResult = instanceDiscoveryListener
        .registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true));

    // Assert
    verify(registry).register(isA(Registration.class));
    assertSame(justResult, actualRegisterInstanceResult);
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance12() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = mock(InstanceRegistry.class);
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "", "localhost", 8080, true)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance13() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = mock(InstanceRegistry.class);
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier
        .create(instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42",
            "Converting service '{}' running at '{}' with metadata {}", 8080, true)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance14() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = mock(InstanceRegistry.class);
    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier
        .create(instanceDiscoveryListener.registerInstance(new DefaultServiceInstance()));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance15() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ServiceInstanceConverter converter = mock(ServiceInstanceConverter.class);
    when(converter.convert(Mockito.<ServiceInstance>any())).thenReturn(null);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = mock(InstanceRegistry.class);

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setConverter(converter);

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
    createResult.expectComplete().verify();
    verify(converter).convert(isA(ServiceInstance.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  void testRegisterInstance16() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<DiscoveryClient> discoveryClients = new ArrayList<>();
    discoveryClients.add(new CompositeDiscoveryClient(new ArrayList<>()));
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(discoveryClients);
    ServiceInstanceConverter converter = mock(ServiceInstanceConverter.class);
    when(converter.convert(Mockito.<ServiceInstance>any())).thenReturn(null);
    InstanceRegistry registry = mock(InstanceRegistry.class);

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));
    instanceDiscoveryListener.setConverter(converter);
    HashMap<String, String> metadata = new HashMap<>();

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier
        .create(instanceDiscoveryListener.registerInstance(new DefaultKubernetesServiceInstance("42", "42", "localhost",
            8080, metadata, true, "Namespace", "Cluster", new HashMap<>())));
    createResult.expectComplete().verify();
    verify(converter).convert(isA(ServiceInstance.class));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#toString(ServiceInstance)}
   */
  @Test
  void testToString() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertEquals("serviceId=42, instanceId=42, url= https://localhost:8080",
        instanceDiscoveryListener.toString(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#toString(ServiceInstance)}
   */
  @Test
  void testToString2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    DefaultServiceInstance instance = new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    instance.setSecure(false);

    // Act and Assert
    assertEquals("serviceId=42, instanceId=42, url= http://localhost:8080",
        instanceDiscoveryListener.toString(instance));
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#toString(ServiceInstance)}
   */
  @Test
  void testToString3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));
    HashMap<String, String> metadata = new HashMap<>();

    // Act and Assert
    assertEquals("serviceId=42, instanceId=42, url= https://localhost:8080",
        instanceDiscoveryListener.toString(new DefaultKubernetesServiceInstance("42", "42", "localhost", 8080, metadata,
            true, "https", "https", new HashMap<>())));
  }

  /**
   * Methods under test:
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
  void testGettersAndSetters() {
    // Arrange
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient, registry,
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
    Map<String, String> actualIgnoredInstancesMetadata = instanceDiscoveryListener.getIgnoredInstancesMetadata();
    Set<String> actualIgnoredServices = instanceDiscoveryListener.getIgnoredServices();
    Map<String, String> actualInstancesMetadata = instanceDiscoveryListener.getInstancesMetadata();
    Set<String> actualServices = instanceDiscoveryListener.getServices();

    // Assert that nothing has changed
    assertTrue(actualIgnoredInstancesMetadata.isEmpty());
    assertTrue(actualInstancesMetadata.isEmpty());
    assertTrue(actualIgnoredServices.isEmpty());
    assertTrue(actualServices.isEmpty());
    assertSame(ignoredInstancesMetadata, actualIgnoredInstancesMetadata);
    assertSame(instancesMetadata, actualInstancesMetadata);
    assertSame(ignoredServices, actualIgnoredServices);
    assertSame(services, actualServices);
  }

  /**
   * Method under test:
   * {@link InstanceDiscoveryListener#InstanceDiscoveryListener(DiscoveryClient, InstanceRegistry, InstanceRepository)}
   */
  @Test
  void testNewInstanceDiscoveryListener() {
    // Arrange and Act
    InstanceDiscoveryListener actualInstanceDiscoveryListener = new InstanceDiscoveryListener(discoveryClient,
        instanceRegistry, instanceRepository);

    // Assert
    Set<String> services = actualInstanceDiscoveryListener.getServices();
    assertEquals(1, services.size());
    assertTrue(actualInstanceDiscoveryListener.getIgnoredInstancesMetadata().isEmpty());
    assertTrue(actualInstanceDiscoveryListener.getInstancesMetadata().isEmpty());
    assertTrue(services.contains("*"));
    assertTrue(actualInstanceDiscoveryListener.getIgnoredServices().isEmpty());
  }
}
