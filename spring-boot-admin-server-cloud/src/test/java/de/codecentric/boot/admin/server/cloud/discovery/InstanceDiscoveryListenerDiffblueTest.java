package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class InstanceDiscoveryListenerDiffblueTest {
  @Mock private DiscoveryClient discoveryClient;

  @InjectMocks private InstanceDiscoveryListener instanceDiscoveryListener;

  @Mock private InstanceRegistry instanceRegistry;

  @Mock private InstanceRepository instanceRepository;

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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       DefaultServiceInstance#DefaultServiceInstance()}.
   *   <li>Then calls {@link InstanceRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given ArrayList() add DefaultServiceInstance(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenArrayListAddDefaultServiceInstance_thenCallsFindAll() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance());
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
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
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenArrayListAddNull_thenCallsGetInstances() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(null);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
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
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return
   *       {@code null}.
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);
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
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InstanceRepository findAll() return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInstanceRepositoryFindAllReturnCreate() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findAll()).thenReturn(createResult);
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
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InstanceRepository findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInstanceRepositoryFindAllReturnCreateThreeAndTrue() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    EmitterProcessor<Instance> createResult = EmitterProcessor.create(3, true);
    when(instanceRepository.findAll()).thenReturn(createResult);
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
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInstanceRepositoryFindAllReturnFromIterableArrayList() {
    // Arrange
    when(discoveryClient.getServices()).thenReturn(new ArrayList<>());
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
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
    verify(instanceRepository).findAll();
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInstanceRepositoryFindAllReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
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
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInstanceRepositoryFindAllReturnFromIterableArrayList3() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
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
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationReady(ApplicationReadyEvent); given InstanceRepository findAll() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInstanceRepositoryFindAllReturnNull() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    when(instanceRepository.findAll()).thenReturn(null);
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
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent); then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_thenCallsFilter() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(directProcessor);
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
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getRegistration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent); then calls getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_thenCallsGetRegistration() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.isRegistered()).thenReturn(true);

    ArrayList<Instance> it = new ArrayList<>();
    it.add(instance);
    Flux<Instance> fromIterableResult = Flux.fromIterable(it);
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
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
    verify(instance).getRegistration();
    verify(instance).isRegistered();
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#groupBy(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent); then calls groupBy(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_thenCallsGroupBy() {
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
    when(discoveryClient.getServices()).thenReturn(new ArrayList<>());
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getServices();
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
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
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
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
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
    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
  void testOnInstanceRegistered5() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 59L));
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    InMemoryEventStore eventStore = mock(InMemoryEventStore.class);
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       DefaultServiceInstance#DefaultServiceInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given ArrayList() add DefaultServiceInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenArrayListAddDefaultServiceInstance() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance());
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenArrayListAddNull_thenCallsGetInstances() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(null);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given InstanceRegistry register(Registration) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenInstanceRegistryRegisterReturnNull() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given InstanceRepository findAll() return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenInstanceRepositoryFindAllReturnCreate() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given InstanceRepository findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenInstanceRepositoryFindAllReturnCreateThreeAndTrue() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    EmitterProcessor<Instance> createResult = EmitterProcessor.create(3, true);
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName(
      "Test onInstanceRegistered(InstanceRegisteredEvent); given InstanceRepository findAll() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_givenInstanceRepositoryFindAllReturnNull() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    when(instanceRepository.findAll()).thenReturn(null);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent); then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_thenCallsFilter() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(directProcessor);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getRegistration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent); then calls getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_thenCallsGetRegistration() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.isRegistered()).thenReturn(true);

    ArrayList<Instance> it = new ArrayList<>();
    it.add(instance);
    Flux<Instance> fromIterableResult = Flux.fromIterable(it);
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(
        new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instance).getRegistration();
    verify(instance).isRegistered();
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#groupBy(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent); then calls groupBy(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"
  })
  void testOnInstanceRegistered_thenCallsGroupBy() {
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       DefaultServiceInstance#DefaultServiceInstance()}.
   *   <li>Then calls {@link InstanceRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given ArrayList() add DefaultServiceInstance(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenArrayListAddDefaultServiceInstance_thenCallsFindAll() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance());
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenArrayListAddNull_thenCallsGetInstances() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(null);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return
   *       {@code null}.
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InstanceRepository findAll() return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInstanceRepositoryFindAllReturnCreate() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InstanceRepository findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInstanceRepositoryFindAllReturnCreateThreeAndTrue() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    EmitterProcessor<Instance> createResult = EmitterProcessor.create(3, true);
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInstanceRepositoryFindAllReturnFromIterableArrayList() {
    // Arrange
    when(discoveryClient.getServices()).thenReturn(new ArrayList<>());
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInstanceRepositoryFindAllReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInstanceRepositoryFindAllReturnFromIterableArrayList3() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName(
      "Test onRefreshInstances(RefreshInstancesEvent); given InstanceRepository findAll() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInstanceRepositoryFindAllReturnNull() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    when(instanceRepository.findAll()).thenReturn(null);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_thenCallsFilter() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(directProcessor);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getRegistration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); then calls getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_thenCallsGetRegistration() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.isRegistered()).thenReturn(true);

    ArrayList<Instance> it = new ArrayList<>();
    it.add(instance);
    Flux<Instance> fromIterableResult = Flux.fromIterable(it);
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instance).getRegistration();
    verify(instance).isRegistered();
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#groupBy(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); then calls groupBy(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_thenCallsGroupBy() {
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       DefaultServiceInstance#DefaultServiceInstance()}.
   *   <li>Then calls {@link InstanceRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given ArrayList() add DefaultServiceInstance(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenArrayListAddDefaultServiceInstance_thenCallsFindAll() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance());
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenArrayListAddNull_thenCallsGetInstances() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(null);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return
   *       {@code null}.
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceRepository findAll() return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceRepositoryFindAllReturnCreate() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceRepository findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceRepositoryFindAllReturnCreateThreeAndTrue() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    EmitterProcessor<Instance> createResult = EmitterProcessor.create(3, true);
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceRepositoryFindAllReturnFromIterableArrayList() {
    // Arrange
    when(discoveryClient.getServices()).thenReturn(new ArrayList<>());
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceRepositoryFindAllReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceRepositoryFindAllReturnFromIterableArrayList3() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return {@code
   *       null}.
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceRepository findAll() return 'null'; then calls register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceRepositoryFindAllReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    when(instanceRepository.findAll()).thenReturn(null);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent); then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_thenCallsFilter() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(directProcessor);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getRegistration()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent); then calls getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_thenCallsGetRegistration() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.isRegistered()).thenReturn(true);

    ArrayList<Instance> it = new ArrayList<>();
    it.add(instance);
    Flux<Instance> fromIterableResult = Flux.fromIterable(it);
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instance).getRegistration();
    verify(instance).isRegistered();
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#groupBy(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent); then calls groupBy(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_thenCallsGroupBy() {
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       DefaultServiceInstance#DefaultServiceInstance()}.
   *   <li>Then calls {@link InstanceRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given ArrayList() add DefaultServiceInstance(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenArrayListAddDefaultServiceInstance_thenCallsFindAll() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance());
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenArrayListAddNull_thenCallsGetInstances() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(null);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return
   *       {@code null}.
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InstanceRepository findAll() return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceRepositoryFindAllReturnCreate() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InstanceRepository findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceRepositoryFindAllReturnCreateThreeAndTrue() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    EmitterProcessor<Instance> createResult = EmitterProcessor.create(3, true);
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceRepositoryFindAllReturnFromIterableArrayList() {
    // Arrange
    when(discoveryClient.getServices()).thenReturn(new ArrayList<>());
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceRepositoryFindAllReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceRepositoryFindAllReturnFromIterableArrayList3() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(HeartbeatEvent); given InstanceRepository findAll() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceRepositoryFindAllReturnNull() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    when(instanceRepository.findAll()).thenReturn(null);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent); then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_thenCallsFilter() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(directProcessor);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getRegistration()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent); then calls getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_thenCallsGetRegistration() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.isRegistered()).thenReturn(true);

    ArrayList<Instance> it = new ArrayList<>();
    it.add(instance);
    Flux<Instance> fromIterableResult = Flux.fromIterable(it);
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instance).getRegistration();
    verify(instance).isRegistered();
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#groupBy(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent); then calls groupBy(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_thenCallsGroupBy() {
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       DefaultServiceInstance#DefaultServiceInstance()}.
   *   <li>Then calls {@link InstanceRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given ArrayList() add DefaultServiceInstance(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenArrayListAddDefaultServiceInstance_thenCallsFindAll() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance());
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenArrayListAddNull_thenCallsGetInstances() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(null);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

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
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return
   *       just {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given InstanceRegistry register(Registration) return just InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRegistryRegisterReturnJustInstanceIdWithValueIs42() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return
   *       {@code null}.
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry}.
   *   <li>Then calls {@link Instance#getRegistration()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given InstanceRegistry; then calls getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRegistry_thenCallsGetRegistration() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance());
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.isRegistered()).thenReturn(true);

    ArrayList<Instance> it = new ArrayList<>();
    it.add(instance);
    Flux<Instance> fromIterableResult = Flux.fromIterable(it);
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instance).getRegistration();
    verify(instance).isRegistered();
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create three
   *       and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given InstanceRepository findAll() return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRepositoryFindAllReturnCreateThreeAndTrue() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    EmitterProcessor<Instance> createResult = EmitterProcessor.create(3, true);
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create.
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given InstanceRepository findAll() return create; then calls register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRepositoryFindAllReturnCreate_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRepositoryFindAllReturnFromIterableArrayList() {
    // Arrange
    when(discoveryClient.getServices()).thenReturn(new ArrayList<>());
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRepositoryFindAllReturnFromIterableArrayList2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return {@code
   *       null}.
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName(
      "Test discover(); given InstanceRepository findAll() return 'null'; then calls register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRepositoryFindAllReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    when(instanceRepository.findAll()).thenReturn(null);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_thenCallsFilter() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(directProcessor);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Then calls {@link Instance#getRegistration()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); then calls getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_thenCallsGetRegistration() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    DefaultServiceInstance defaultServiceInstance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    serviceInstanceList.add(defaultServiceInstance);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.isRegistered()).thenReturn(true);

    ArrayList<Instance> it = new ArrayList<>();
    it.add(instance);
    Flux<Instance> fromIterableResult = Flux.fromIterable(it);
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instance).getRegistration();
    verify(instance).isRegistered();
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances("Discovering new instances from DiscoveryClient");
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#groupBy(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); then calls groupBy(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_thenCallsGroupBy() {
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository2, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository2);
    instanceDiscoveryListener.setServices(services);

    // Act and Assert
    assertTrue(instanceDiscoveryListener.shouldRegisterService("42"));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterService(String)}.
   *
   * <ul>
   *   <li>Given {@link InstanceDiscoveryListener}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#shouldRegisterService(String)}
   */
  @Test
  @DisplayName(
      "Test shouldRegisterService(String); given InstanceDiscoveryListener; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.shouldRegisterService(String)"})
  void testShouldRegisterService_givenInstanceDiscoveryListener_thenReturnTrue() {
    // Arrange, Act and Assert
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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository2);
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
    // Arrange, Act and Assert
    assertFalse(instanceDiscoveryListener.matchesPattern("42", new HashSet<>()));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test shouldRegisterInstanceBasedOnMetadata(ServiceInstance); given HashMap() '42' is '42'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InstanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(ServiceInstance)"
  })
  void testShouldRegisterInstanceBasedOnMetadata_givenHashMap42Is42_thenReturnTrue() {
    // Arrange
    HashMap<String, String> ignoredInstancesMetadata = new HashMap<>();
    ignoredInstancesMetadata.put("42", "42");
    instanceDiscoveryListener.setInstancesMetadata(new HashMap<>());
    instanceDiscoveryListener.setIgnoredInstancesMetadata(ignoredInstancesMetadata);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertTrue(instanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(instance));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@code foo}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test shouldRegisterInstanceBasedOnMetadata(ServiceInstance); given HashMap() 'foo' is 'foo'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InstanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(ServiceInstance)"
  })
  void testShouldRegisterInstanceBasedOnMetadata_givenHashMapFooIsFoo_thenReturnFalse() {
    // Arrange
    HashMap<String, String> instancesMetadata = new HashMap<>();
    instancesMetadata.put("foo", "foo");
    instanceDiscoveryListener.setInstancesMetadata(instancesMetadata);
    instanceDiscoveryListener.setIgnoredInstancesMetadata(new HashMap<>());
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertFalse(instanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(instance));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@link InstanceDiscoveryListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test shouldRegisterInstanceBasedOnMetadata(ServiceInstance); given InstanceDiscoveryListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean InstanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(ServiceInstance)"
  })
  void testShouldRegisterInstanceBasedOnMetadata_givenInstanceDiscoveryListener() {
    // Arrange
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertTrue(instanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(instance));
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
    DefaultServiceInstance instance = new DefaultServiceInstance("42", "", "localhost", 8080, true);

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceDiscoveryListener.registerInstance(instance));
    createResult.expectComplete().verify();
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
    DefaultServiceInstance instance =
        new DefaultServiceInstance(
            "42", "42", "Converting service '{}' running at '{}' with metadata {}", 8080, true);

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceDiscoveryListener.registerInstance(instance));
    createResult.expectComplete().verify();
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
  void testRegisterInstance3() throws AssertionError {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    DefaultKubernetesServiceInstance instance =
        new DefaultKubernetesServiceInstance(
            "42",
            "",
            "localhost",
            8080,
            metadata,
            true,
            "Converting service '{}' running at '{}' with metadata {}",
            "Converting service '{}' running at '{}' with metadata {}",
            new HashMap<>());

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceDiscoveryListener.registerInstance(instance));
    createResult.expectComplete().verify();
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
  void testRegisterInstance4() throws AssertionError {
    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    String value = "42";
    InstanceId ofResult = InstanceId.of(value);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceRegistry registry =
        new InstanceRegistry(repository, generator, mock(InstanceFilter.class));
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository2);
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
  void testRegisterInstance5() throws AssertionError {
    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    String value = "42";
    InstanceId ofResult = InstanceId.of(value);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));

    InstanceRegistry registry =
        new InstanceRegistry(repository, generator, mock(InstanceFilter.class));
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository2);
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
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    CompositeDiscoveryClient discoveryClient = new CompositeDiscoveryClient(new ArrayList<>());
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
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
  void testRegisterInstance7() {
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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
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
  void testRegisterInstance8() {
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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository);
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
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance); given 'foo'; when HashMap() 'foo' is 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_givenFoo_whenHashMapFooIsFoo() {
    // Arrange
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("foo", "foo");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Mono<InstanceId> actualRegisterInstanceResult =
        instanceDiscoveryListener.registerInstance(instance);

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    assertSame(justResult, actualRegisterInstanceResult);
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   *
   * <ul>
   *   <li>Given {@link InstanceDiscoveryListener}.
   *   <li>When {@link DefaultServiceInstance#DefaultServiceInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test registerInstance(ServiceInstance); given InstanceDiscoveryListener; when DefaultServiceInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_givenInstanceDiscoveryListener_whenDefaultServiceInstance()
      throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(
            instanceDiscoveryListener.registerInstance(new DefaultServiceInstance()));
    createResult.expectComplete().verify();
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository2);
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
   *   <li>Given {@code localhost}.
   *   <li>When {@link HashMap#HashMap()} {@code localhost} is {@code localhost}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test registerInstance(ServiceInstance); given 'localhost'; when HashMap() 'localhost' is 'localhost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_givenLocalhost_whenHashMapLocalhostIsLocalhost() {
    // Arrange
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    HashMap<String, String> metadata = new HashMap<>();
    metadata.put("localhost", "localhost");
    metadata.put("foo", "foo");
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true, metadata);

    // Act
    Mono<InstanceId> actualRegisterInstanceResult =
        instanceDiscoveryListener.registerInstance(instance);

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    assertSame(justResult, actualRegisterInstanceResult);
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
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository2);
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
   *   <li>Then return just {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName(
      "Test registerInstance(ServiceInstance); then return just InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_thenReturnJustInstanceIdWithValueIs42() {
    // Arrange
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act
    Mono<InstanceId> actualRegisterInstanceResult =
        instanceDiscoveryListener.registerInstance(instance);

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    assertSame(justResult, actualRegisterInstanceResult);
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
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);

    // Act and Assert
    assertEquals(
        "serviceId=42, instanceId=42, url= https://localhost:8080",
        instanceDiscoveryListener.toString(instance));
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
    DefaultServiceInstance instance =
        new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    instance.setSecure(false);

    // Act and Assert
    assertEquals(
        "serviceId=42, instanceId=42, url= http://localhost:8080",
        instanceDiscoveryListener.toString(instance));
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
  void testToStringWithServiceInstance3() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();
    DefaultKubernetesServiceInstance instance =
        new DefaultKubernetesServiceInstance(
            "42", "42", "localhost", 8080, metadata, true, "https", "https", new HashMap<>());

    // Act and Assert
    assertEquals(
        "serviceId=42, instanceId=42, url= https://localhost:8080",
        instanceDiscoveryListener.toString(instance));
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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    EventsourcingInstanceRepository repository2 =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    InstanceDiscoveryListener instanceDiscoveryListener =
        new InstanceDiscoveryListener(discoveryClient, registry, repository2);

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
