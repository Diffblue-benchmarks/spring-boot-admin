package de.codecentric.boot.admin.server.cloud.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.web.client.RefreshInstancesEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
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
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InstanceDiscoveryListener.class, DiscoveryClient.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
@ExtendWith(MockitoExtension.class)
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
   * Test {@link InstanceDiscoveryListener#InstanceDiscoveryListener(DiscoveryClient, InstanceRegistry, InstanceRepository)}.
   * <ul>
   *   <li>Then return Services size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#InstanceDiscoveryListener(DiscoveryClient, InstanceRegistry, InstanceRepository)}
   */
  @Test
  @DisplayName("Test new InstanceDiscoveryListener(DiscoveryClient, InstanceRegistry, InstanceRepository); then return Services size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.<init>(DiscoveryClient, InstanceRegistry, InstanceRepository)"})
  void testNewInstanceDiscoveryListener_thenReturnServicesSizeIsOne() {
    // Arrange and Act
    InstanceDiscoveryListener actualInstanceDiscoveryListener = new InstanceDiscoveryListener(
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
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady3() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList
        .add(new DefaultServiceInstance("42", "42", "Discovering new instances from DiscoveryClient", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DefaultServiceInstance#DefaultServiceInstance()}.</li>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent); given ArrayList() add DefaultServiceInstance(); then calls findAll()")
  @Tag("MaintainedByDiffblue")
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

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("MaintainedByDiffblue")
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

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return {@code null}.</li>
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#subscribe(Subscriber)} does nothing.</li>
   *   <li>Then calls {@link Mono#subscribe(Subscriber)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent); given Mono subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_givenMonoSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> mono = mock(Mono.class);
    doNothing().when(mono).subscribe(Mockito.<Subscriber<InstanceId>>any());
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(mono);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
    verify(mono).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_thenCallsFindAll() {
    // Arrange
    when(discoveryClient.getServices()).thenReturn(new ArrayList<>());
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}.
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationReady(ApplicationReadyEvent)}
   */
  @Test
  @DisplayName("Test onApplicationReady(ApplicationReadyEvent); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationReady(ApplicationReadyEvent)"})
  void testOnApplicationReady_thenCallsFindAll2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);
    Class<Object> forNameResult = Object.class;
    SpringApplication application = new SpringApplication(forNameResult);

    // Act
    instanceDiscoveryListener.onApplicationReady(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext(), null));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"})
  void testOnInstanceRegistered() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"})
  void testOnInstanceRegistered2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"})
  void testOnInstanceRegistered3() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList
        .add(new DefaultServiceInstance("42", "42", "Discovering new instances from DiscoveryClient", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DefaultServiceInstance#DefaultServiceInstance()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent); given ArrayList() add DefaultServiceInstance()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"})
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
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"})
  void testOnInstanceRegistered_givenArrayListAddNull_thenCallsGetInstances() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(null);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent); given InstanceRegistry register(Registration) return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"})
  void testOnInstanceRegistered_givenInstanceRegistryRegisterReturnNull() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#subscribe(Subscriber)} does nothing.</li>
   *   <li>Then calls {@link Mono#subscribe(Subscriber)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent); given Mono subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"})
  void testOnInstanceRegistered_givenMonoSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> mono = mock(Mono.class);
    doNothing().when(mono).subscribe(Mockito.<Subscriber<InstanceId>>any());
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(mono);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
    verify(mono).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"})
  void testOnInstanceRegistered_thenCallsFindAll() {
    // Arrange
    when(discoveryClient.getServices()).thenReturn(new ArrayList<>());
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}.
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onInstanceRegistered(InstanceRegisteredEvent)}
   */
  @Test
  @DisplayName("Test onInstanceRegistered(InstanceRegisteredEvent); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onInstanceRegistered(InstanceRegisteredEvent)"})
  void testOnInstanceRegistered_thenCallsFindAll2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onInstanceRegistered(new InstanceRegisteredEvent<>("Source", "Config"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances3() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList
        .add(new DefaultServiceInstance("42", "42", "Discovering new instances from DiscoveryClient", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DefaultServiceInstance#DefaultServiceInstance()}.</li>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); given ArrayList() add DefaultServiceInstance(); then calls findAll()")
  @Tag("MaintainedByDiffblue")
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("MaintainedByDiffblue")
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link Mono#subscribe(Subscriber)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); given ArrayList() add 'null'; then calls subscribe(Subscriber)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenArrayListAddNull_thenCallsSubscribe() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    serviceInstanceList.add(null);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> mono = mock(Mono.class);
    doNothing().when(mono).subscribe(Mockito.<Subscriber<InstanceId>>any());
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(mono);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
    verify(mono).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return {@code null}.</li>
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#subscribe(Subscriber)} does nothing.</li>
   *   <li>Then calls {@link Mono#subscribe(Subscriber)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); given Mono subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_givenMonoSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> mono = mock(Mono.class);
    doNothing().when(mono).subscribe(Mockito.<Subscriber<InstanceId>>any());
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(mono);

    // Act
    instanceDiscoveryListener.onRefreshInstances(new RefreshInstancesEvent("Source"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
    verify(mono).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}.
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_thenCallsFindAll() {
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
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onRefreshInstances(RefreshInstancesEvent)}
   */
  @Test
  @DisplayName("Test onRefreshInstances(RefreshInstancesEvent); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onRefreshInstances(RefreshInstancesEvent)"})
  void testOnRefreshInstances_thenCallsFindAll2() {
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat3() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList
        .add(new DefaultServiceInstance("42", "42", "Discovering new instances from DiscoveryClient", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return {@code null}.</li>
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.onParentHeartbeat(new ParentHeartbeatEvent("Source", "Value"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}.
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_thenCallsFindAll() {
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
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onParentHeartbeat(ParentHeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onParentHeartbeat(ParentHeartbeatEvent); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onParentHeartbeat(ParentHeartbeatEvent)"})
  void testOnParentHeartbeat_thenCallsFindAll2() {
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("MaintainedByDiffblue")
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return {@code null}.</li>
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#subscribe(Subscriber)} does nothing.</li>
   *   <li>Then calls {@link Mono#subscribe(Subscriber)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent); given Mono subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_givenMonoSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> mono = mock(Mono.class);
    doNothing().when(mono).subscribe(Mockito.<Subscriber<InstanceId>>any());
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(mono);

    // Act
    instanceDiscoveryListener.onApplicationEvent(new HeartbeatEvent("Source", "State"));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
    verify(mono).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}.
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#onApplicationEvent(HeartbeatEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(HeartbeatEvent); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.onApplicationEvent(HeartbeatEvent)"})
  void testOnApplicationEvent_thenCallsFindAll() {
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
   * Test {@link InstanceDiscoveryListener#discover()}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList
        .add(new DefaultServiceInstance("42", "42", "Discovering new instances from DiscoveryClient", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRepository).findAll();
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DefaultServiceInstance#DefaultServiceInstance()}.</li>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given ArrayList() add DefaultServiceInstance(); then calls findAll()")
  @Tag("MaintainedByDiffblue")
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link DiscoveryClient#getInstances(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given ArrayList() add 'null'; then calls getInstances(String)")
  @Tag("MaintainedByDiffblue")
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then calls {@link Mono#subscribe(Subscriber)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given ArrayList() add 'null'; then calls subscribe(Subscriber)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenArrayListAddNull_thenCallsSubscribe() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    serviceInstanceList.add(null);
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> mono = mock(Mono.class);
    doNothing().when(mono).subscribe(Mockito.<Subscriber<InstanceId>>any());
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(mono);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
    verify(mono).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return just {@link InstanceId} with value is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given InstanceRegistry register(Registration) return just InstanceId with value is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRegistryRegisterReturnJustInstanceIdWithValueIs42() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return {@code null}.</li>
   *   <li>Then calls {@link InstanceRegistry#register(Registration)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given InstanceRegistry register(Registration) return 'null'; then calls register(Registration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenInstanceRegistryRegisterReturnNull_thenCallsRegister() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(null);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#subscribe(Subscriber)} does nothing.</li>
   *   <li>Then calls {@link Mono#subscribe(Subscriber)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); given Mono subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_givenMonoSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Discovering new instances from DiscoveryClient");

    ArrayList<ServiceInstance> serviceInstanceList = new ArrayList<>();
    serviceInstanceList.add(new DefaultServiceInstance("42", "42", "localhost", 8080, true));
    when(discoveryClient.getInstances(Mockito.<String>any())).thenReturn(serviceInstanceList);
    when(discoveryClient.getServices()).thenReturn(stringList);
    Mono<InstanceId> mono = mock(Mono.class);
    doNothing().when(mono).subscribe(Mockito.<Subscriber<InstanceId>>any());
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(mono);

    // Act
    instanceDiscoveryListener.discover();

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
    verify(mono).subscribe(isA(Subscriber.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#discover()}.
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_thenCallsFindAll() {
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
   * <ul>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#discover()}
   */
  @Test
  @DisplayName("Test discover(); then calls findAll()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InstanceDiscoveryListener.discover()"})
  void testDiscover_thenCallsFindAll2() {
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
    verify(discoveryClient).getInstances(eq("Discovering new instances from DiscoveryClient"));
    verify(discoveryClient).getServices();
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances2() {
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
    Flux<Instance> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Instance, Object>>any())).thenReturn(flux4);
    Flux<Instance> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux5);
    Flux<Instance> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux6);
    when(instanceRepository.findAll()).thenReturn(flux7);

    // Act
    Mono<Void> actualRemoveStaleInstancesResult = instanceDiscoveryListener.removeStaleInstances(new HashSet<>());

    // Assert
    verify(instanceRepository).findAll();
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
    assertSame(channelSendOperator, actualRemoveStaleInstancesResult);
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#doOnNext(Consumer)} return fromIterable {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set); given Flux doOnNext(Consumer) return fromIterable ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenFluxDoOnNextReturnFromIterableArrayList() throws AssertionError {
    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux);
    Flux<Instance> flux3 = mock(Flux.class);
    when(flux3.map(Mockito.<Function<Instance, Object>>any())).thenReturn(flux2);
    Flux<Instance> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux3);
    Flux<Instance> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux4);
    when(instanceRepository.findAll()).thenReturn(flux5);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
    verify(flux).doOnNext(isA(Consumer.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux3).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#filter(Predicate)} return fromIterable {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set); given Flux filter(Predicate) return fromIterable ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenFluxFilterReturnFromIterableArrayList() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(flux);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
    verify(flux).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#filter(Predicate)} return fromIterable {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set); given Flux filter(Predicate) return fromIterable ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenFluxFilterReturnFromIterableArrayList2() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    Flux<Instance> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux);
    when(instanceRepository.findAll()).thenReturn(flux2);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#filter(Predicate)} return fromIterable {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link Flux#map(Function)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set); given Flux filter(Predicate) return fromIterable ArrayList(); then calls map(Function)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenFluxFilterReturnFromIterableArrayList_thenCallsMap() throws AssertionError {
    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Object>>any())).thenReturn(fromIterableResult);
    Flux<Instance> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Instance, Object>>any())).thenReturn(flux);
    Flux<Instance> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux2);
    Flux<Instance> flux4 = mock(Flux.class);
    when(flux4.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux3);
    when(instanceRepository.findAll()).thenReturn(flux4);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
    verify(flux4).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).filter(isA(Predicate.class));
    verify(flux2).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#flatMap(Function)} return fromIterable {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set); given Flux flatMap(Function) return fromIterable ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenFluxFlatMapReturnFromIterableArrayList() throws AssertionError {
    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Object, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    Flux<Object> flux2 = mock(Flux.class);
    when(flux2.doOnNext(Mockito.<Consumer<Object>>any())).thenReturn(flux);
    Flux<Object> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Object>>any())).thenReturn(flux2);
    Flux<Instance> flux4 = mock(Flux.class);
    when(flux4.map(Mockito.<Function<Instance, Object>>any())).thenReturn(flux3);
    Flux<Instance> flux5 = mock(Flux.class);
    when(flux5.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux4);
    Flux<Instance> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux5);
    when(instanceRepository.findAll()).thenReturn(flux6);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
    verify(flux2).doOnNext(isA(Consumer.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux5).filter(isA(Predicate.class));
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class));
    verify(flux4).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#map(Function)} return fromIterable {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link Flux#map(Function)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set); given Flux map(Function) return fromIterable ArrayList(); then calls map(Function)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenFluxMapReturnFromIterableArrayList_thenCallsMap() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.map(Mockito.<Function<Instance, Object>>any())).thenReturn(fromIterableResult);
    Flux<Instance> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux);
    Flux<Instance> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux2);
    when(instanceRepository.findAll()).thenReturn(flux3);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(instanceDiscoveryListener.removeStaleInstances(new HashSet<>()));
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).map(isA(Function.class));
  }

  /**
   * Test {@link InstanceDiscoveryListener#removeStaleInstances(Set)}.
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#removeStaleInstances(Set)}
   */
  @Test
  @DisplayName("Test removeStaleInstances(Set); given InstanceId with value is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.removeStaleInstances(Set)"})
  void testRemoveStaleInstances_givenInstanceIdWithValueIs42() {
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
    Flux<Instance> flux5 = mock(Flux.class);
    when(flux5.map(Mockito.<Function<Instance, Object>>any())).thenReturn(flux4);
    Flux<Instance> flux6 = mock(Flux.class);
    when(flux6.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux5);
    Flux<Instance> flux7 = mock(Flux.class);
    when(flux7.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux6);
    when(instanceRepository.findAll()).thenReturn(flux7);

    HashSet<InstanceId> registeredInstanceIds = new HashSet<>();
    registeredInstanceIds.add(InstanceId.of("42"));

    // Act
    Mono<Void> actualRemoveStaleInstancesResult = instanceDiscoveryListener.removeStaleInstances(registeredInstanceIds);

    // Assert
    verify(instanceRepository).findAll();
    verify(flux3).doOnNext(isA(Consumer.class));
    verify(flux7).filter(isA(Predicate.class));
    verify(flux6).filter(isA(Predicate.class));
    verify(flux4).filter(isA(Predicate.class));
    verify(flux2).flatMap(isA(Function.class));
    verify(flux5).map(isA(Function.class));
    verify(flux).then();
    assertSame(channelSendOperator, actualRemoveStaleInstancesResult);
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterService(String)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#shouldRegisterService(String)}
   */
  @Test
  @DisplayName("Test shouldRegisterService(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.shouldRegisterService(String)"})
  void testShouldRegisterService() {
    // Arrange, Act and Assert
    assertTrue(instanceDiscoveryListener.shouldRegisterService("42"));
  }

  /**
   * Test {@link InstanceDiscoveryListener#matchesPattern(String, Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#matchesPattern(String, Set)}
   */
  @Test
  @DisplayName("Test matchesPattern(String, Set); given '42'; when HashSet() add '42'; then return 'true'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#matchesPattern(String, Set)}
   */
  @Test
  @DisplayName("Test matchesPattern(String, Set); given 'foo'; when HashSet() add 'foo'; then return 'false'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#matchesPattern(String, Set)}
   */
  @Test
  @DisplayName("Test matchesPattern(String, Set); when HashSet(); then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.matchesPattern(String, Set)"})
  void testMatchesPattern_whenHashSet_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(instanceDiscoveryListener.matchesPattern("42", new HashSet<>()));
  }

  /**
   * Test {@link InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#shouldRegisterInstanceBasedOnMetadata(ServiceInstance)}
   */
  @Test
  @DisplayName("Test shouldRegisterInstanceBasedOnMetadata(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InstanceDiscoveryListener.shouldRegisterInstanceBasedOnMetadata(ServiceInstance)"})
  void testShouldRegisterInstanceBasedOnMetadata() {
    // Arrange, Act and Assert
    assertTrue(instanceDiscoveryListener
        .shouldRegisterInstanceBasedOnMetadata(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<InstanceId> createResult = StepVerifier.create(
        instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "", "localhost", 8080, true)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance2() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<InstanceId> createResult = StepVerifier
        .create(instanceDiscoveryListener.registerInstance(new DefaultServiceInstance("42", "42",
            "Converting service '{}' running at '{}' with metadata {}", 8080, true)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   * <ul>
   *   <li>Then return just {@link InstanceId} with value is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance); then return just InstanceId with value is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_thenReturnJustInstanceIdWithValueIs42() {
    // Arrange
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    // Act
    Mono<InstanceId> actualRegisterInstanceResult = instanceDiscoveryListener
        .registerInstance(new DefaultServiceInstance("42", "42", "localhost", 8080, true));

    // Assert
    verify(instanceRegistry).register(isA(Registration.class));
    assertSame(justResult, actualRegisterInstanceResult);
  }

  /**
   * Test {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}.
   * <ul>
   *   <li>When {@link DefaultServiceInstance#DefaultServiceInstance()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#registerInstance(ServiceInstance)}
   */
  @Test
  @DisplayName("Test registerInstance(ServiceInstance); when DefaultServiceInstance()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mono InstanceDiscoveryListener.registerInstance(ServiceInstance)"})
  void testRegisterInstance_whenDefaultServiceInstance() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<InstanceId> createResult = StepVerifier
        .create(instanceDiscoveryListener.registerInstance(new DefaultServiceInstance()));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceDiscoveryListener#toString(ServiceInstance)} with {@code ServiceInstance}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#toString(ServiceInstance)}
   */
  @Test
  @DisplayName("Test toString(ServiceInstance) with 'ServiceInstance'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String InstanceDiscoveryListener.toString(ServiceInstance)"})
  void testToStringWithServiceInstance() {
    // Arrange, Act and Assert
    assertEquals("serviceId=42, instanceId=42, url= https://localhost:8080",
        instanceDiscoveryListener.toString(new DefaultServiceInstance("42", "42", "localhost", 8080, true)));
  }

  /**
   * Test {@link InstanceDiscoveryListener#toString(ServiceInstance)} with {@code ServiceInstance}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#toString(ServiceInstance)}
   */
  @Test
  @DisplayName("Test toString(ServiceInstance) with 'ServiceInstance'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String InstanceDiscoveryListener.toString(ServiceInstance)"})
  void testToStringWithServiceInstance2() {
    // Arrange
    DefaultServiceInstance instance = new DefaultServiceInstance("42", "42", "localhost", 8080, true);
    instance.setSecure(false);

    // Act and Assert
    assertEquals("serviceId=42, instanceId=42, url= http://localhost:8080",
        instanceDiscoveryListener.toString(instance));
  }

  /**
   * Test {@link InstanceDiscoveryListener#toString(ServiceInstance)} with {@code ServiceInstance}.
   * <p>
   * Method under test: {@link InstanceDiscoveryListener#toString(ServiceInstance)}
   */
  @Test
  @DisplayName("Test toString(ServiceInstance) with 'ServiceInstance'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String InstanceDiscoveryListener.toString(ServiceInstance)"})
  void testToStringWithServiceInstance3() {
    // Arrange
    HashMap<String, String> metadata = new HashMap<>();

    // Act and Assert
    assertEquals("serviceId=42, instanceId=42, url= https://localhost:8080",
        instanceDiscoveryListener.toString(new DefaultKubernetesServiceInstance("42", "42", "localhost", 8080, metadata,
            true, "https", "https", new HashMap<>())));
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map InstanceDiscoveryListener.getIgnoredInstancesMetadata()",
      "Set InstanceDiscoveryListener.getIgnoredServices()", "Map InstanceDiscoveryListener.getInstancesMetadata()",
      "Set InstanceDiscoveryListener.getServices()",
      "void InstanceDiscoveryListener.setConverter(ServiceInstanceConverter)",
      "void InstanceDiscoveryListener.setIgnoredInstancesMetadata(Map)",
      "void InstanceDiscoveryListener.setIgnoredServices(Set)",
      "void InstanceDiscoveryListener.setInstancesMetadata(Map)", "void InstanceDiscoveryListener.setServices(Set)"})
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
