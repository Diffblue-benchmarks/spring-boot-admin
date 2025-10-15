package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.BuildVersion;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventPublisher;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;
import reactor.util.function.Tuple2;

@ContextConfiguration(classes = {ApplicationRegistry.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ApplicationRegistryDiffblueTest {
  @Autowired private ApplicationRegistry applicationRegistry;

  @MockitoBean private InstanceEventPublisher instanceEventPublisher;

  @MockitoBean private InstanceRegistry instanceRegistry;

  /**
   * Test {@link ApplicationRegistry#ApplicationRegistry(InstanceRegistry, InstanceEventPublisher)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#ApplicationRegistry(InstanceRegistry,
   * InstanceEventPublisher)}
   */
  @Test
  @DisplayName("Test new ApplicationRegistry(InstanceRegistry, InstanceEventPublisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ApplicationRegistry.<init>(InstanceRegistry, InstanceEventPublisher)"})
  void testNewApplicationRegistry() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act
    ApplicationRegistry actualApplicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Assert
    FirstStep<Application> createResult =
        StepVerifier.create(actualApplicationRegistry.getApplications());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName("Test getApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName("Test getApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName(
      "Test getApplications(); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications_givenDirectProcessorFilterReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances()).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#groupBy(Function)} return create.
   *   <li>Then calls {@link DirectProcessor#groupBy(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName(
      "Test getApplications(); given DirectProcessor groupBy(Function) return create; then calls groupBy(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications_givenDirectProcessorGroupByReturnCreate_thenCallsGroupBy() {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    DirectProcessor<GroupedFlux<Object, Instance>> createResult = DirectProcessor.create();
    when(directProcessor.groupBy(Mockito.<Function<Instance, Object>>any()))
        .thenReturn(createResult);

    DirectProcessor<Instance> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor);
    when(instanceRegistry.getInstances()).thenReturn(directProcessor2);

    // Act
    applicationRegistry.getApplications();

    // Assert
    verify(instanceRegistry).getInstances();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#groupBy(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName(
      "Test getApplications(); given DirectProcessor groupBy(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications_givenDirectProcessorGroupByReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<GroupedFlux<Object, Instance>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.groupBy(Mockito.<Function<Instance, Object>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Instance> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor);
    when(instanceRegistry.getInstances()).thenReturn(directProcessor2);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances()} return create.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName("Test getApplications(); given InstanceRegistry getInstances() return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications_givenInstanceRegistryGetInstancesReturnCreate() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRegistry.getInstances()).thenReturn(createResult);

    // Act
    applicationRegistry.getApplications();

    // Assert
    verify(instanceRegistry).getInstances();
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances()} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName(
      "Test getApplications(); given InstanceRegistry getInstances() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications_givenInstanceRegistryGetInstancesReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances()).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName("Test getApplications(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications_thenCallsFindAll() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <ul>
   *   <li>Then calls {@link EventsourcingInstanceRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName("Test getApplications(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications_thenCallsFindAll2() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findAll()).thenReturn(fromIterableResult);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
    verify(repository).findAll();
  }

  /**
   * Test {@link ApplicationRegistry#getApplications()}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#flatMap(Function, int)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  @DisplayName("Test getApplications(); then calls flatMap(Function, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.getApplications()"})
  void testGetApplications_thenCallsFlatMap() throws AssertionError {
    // Arrange
    DirectProcessor<GroupedFlux<Object, Instance>> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(
            Mockito.<Function<GroupedFlux<Object, Instance>, Publisher<Object>>>any(), anyInt()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Instance> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.groupBy(Mockito.<Function<Instance, Object>>any()))
        .thenReturn(directProcessor);

    DirectProcessor<Instance> directProcessor3 = mock(DirectProcessor.class);
    when(directProcessor3.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor2);
    when(instanceRegistry.getInstances()).thenReturn(directProcessor3);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class), eq(2147483647));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName("Test getApplication(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName("Test getApplication(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Instance}.
   *   <li>When {@code Name}.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName(
      "Test getApplication(String); given ArrayList() add Instance; when 'Name'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication_givenArrayListAddInstance_whenName_thenCallsFilter()
      throws AssertionError {
    // Arrange
    ArrayList<Instance> it = new ArrayList<>();
    it.add(mock(Instance.class));
    Flux<Instance> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectError().verify();
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#collectList()} return just {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName(
      "Test getApplication(String); given DirectProcessor collectList() return just ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication_givenDirectProcessorCollectListReturnJustArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Mono<List<Instance>> justResult = Mono.just(new ArrayList<>());
    when(directProcessor.collectList()).thenReturn(justResult);

    DirectProcessor<Instance> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor2);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).collectList();
    verify(directProcessor2).filter(isA(Predicate.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return create.
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName(
      "Test getApplication(String); given DirectProcessor filter(Predicate) return create; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication_givenDirectProcessorFilterReturnCreate_thenCallsFilter() {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(createResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

    // Act
    applicationRegistry.getApplication("Name");

    // Assert
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName(
      "Test getApplication(String); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication_givenDirectProcessorFilterReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances(String)} return
   *       create.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName(
      "Test getApplication(String); given InstanceRegistry getInstances(String) return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication_givenInstanceRegistryGetInstancesReturnCreate() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(createResult);

    // Act
    applicationRegistry.getApplication("Name");

    // Assert
    verify(instanceRegistry).getInstances("Name");
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances(String)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName(
      "Test getApplication(String); given InstanceRegistry getInstances(String) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication_givenInstanceRegistryGetInstancesReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Name");
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#map(Function)} return just {@code Data}.
   *   <li>When {@code Name}.
   *   <li>Then calls {@link Mono#map(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName(
      "Test getApplication(String); given Mono map(Function) return just 'Data'; when 'Name'; then calls map(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication_givenMonoMapReturnJustData_whenName_thenCallsMap() throws AssertionError {
    // Arrange
    Mono<List<Instance>> mono = mock(Mono.class);
    Mono<Object> justResult = Mono.just("Data");
    when(mono.map(Mockito.<Function<List<Instance>, Object>>any())).thenReturn(justResult);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.collectList()).thenReturn(mono);

    DirectProcessor<Instance> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor2);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectError().verify();
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).collectList();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(mono).map(isA(Function.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName("Test getApplication(String); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication_thenCallsFindAll() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link ApplicationRegistry#getApplication(String)}.
   *
   * <ul>
   *   <li>Then calls {@link EventsourcingInstanceRepository#findByName(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  @DisplayName("Test getApplication(String); then calls findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.getApplication(String)"})
  void testGetApplication_thenCallsFindByName() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
    verify(repository).findByName("Name");
  }

  /**
   * Test {@link ApplicationRegistry#deregister(String)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#deregister(String)}
   */
  @Test
  @DisplayName("Test deregister(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.deregister(String)"})
  void testDeregister() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(applicationRegistry.deregister("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationRegistry#deregister(String)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#deregister(String)}
   */
  @Test
  @DisplayName("Test deregister(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.deregister(String)"})
  void testDeregister2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(applicationRegistry.deregister("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationRegistry#deregister(String)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances(String)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#deregister(String)}
   */
  @Test
  @DisplayName(
      "Test deregister(String); given InstanceRegistry getInstances(String) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.deregister(String)"})
  void testDeregister_givenInstanceRegistryGetInstancesReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(applicationRegistry.deregister("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Name");
  }

  /**
   * Test {@link ApplicationRegistry#deregister(String)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#deregister(String)}
   */
  @Test
  @DisplayName("Test deregister(String); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.deregister(String)"})
  void testDeregister_thenCallsFindAll() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(applicationRegistry.deregister("Name"));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link ApplicationRegistry#deregister(String)}.
   *
   * <ul>
   *   <li>Then calls {@link EventsourcingInstanceRepository#findByName(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#deregister(String)}
   */
  @Test
  @DisplayName("Test deregister(String); then calls findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.deregister(String)"})
  void testDeregister_thenCallsFindByName() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(applicationRegistry.deregister("Name"));
    createResult.expectComplete().verify();
    verify(repository).findByName("Name");
  }

  /**
   * Test {@link ApplicationRegistry#deregister(String)}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#deregister(String)}
   */
  @Test
  @DisplayName("Test deregister(String); then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.deregister(String)"})
  void testDeregister_thenCallsFlatMap() throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(applicationRegistry.deregister("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).flatMap(isA(Function.class));
  }

  /**
   * Test {@link ApplicationRegistry#deregister(String)}.
   *
   * <ul>
   *   <li>When {@code List}.
   *   <li>Then calls {@link DirectProcessor#flatMap(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#deregister(String)}
   */
  @Test
  @DisplayName("Test deregister(String); when 'java.util.List'; then calls flatMap(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationRegistry.deregister(String)"})
  void testDeregister_whenJavaUtilList_thenCallsFlatMap() throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(applicationRegistry.deregister("java.util.List"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("java.util.List");
    verify(directProcessor).flatMap(isA(Function.class));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName("Test getApplicationForInstance(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance() {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

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

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    verify(instanceRegistry).getInstances("Name");
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("Name", toListResult.get(0));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName("Test getApplicationForInstance(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance2() {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("java.lang.String")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    verify(instanceRegistry).getInstances("java.lang.String");
    verify(directProcessor).filter(isA(Predicate.class));
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("java.lang.String", toListResult.get(0));
    assertSame(fromIterableResult, toListResult.get(1));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName("Test getApplicationForInstance(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance3() {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl(null)
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).filter(isA(Predicate.class));
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("Name", toListResult.get(0));
    assertSame(fromIterableResult, toListResult.get(1));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName("Test getApplicationForInstance(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance4() {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("")
                .name("java.lang.String")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    verify(instanceRegistry).getInstances("java.lang.String");
    verify(directProcessor).filter(isA(Predicate.class));
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("java.lang.String", toListResult.get(0));
    assertSame(fromIterableResult, toListResult.get(1));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName("Test getApplicationForInstance(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance5() {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

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

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("Name", toListResult.get(0));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName("Test getApplicationForInstance(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance6() {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

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

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("Name", toListResult.get(0));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances(String)} return
   *       create.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName(
      "Test getApplicationForInstance(Instance); given InstanceRegistry getInstances(String) return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance_givenInstanceRegistryGetInstancesReturnCreate() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(createResult);

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

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    verify(instanceRegistry).getInstances("Name");
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("Name", toListResult.get(0));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName("Test getApplicationForInstance(Instance); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance_thenCallsFindAll() {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

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

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    verify(eventStore).findAll();
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("Name", toListResult.get(0));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link EventsourcingInstanceRepository#findByName(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName("Test getApplicationForInstance(Instance); then calls findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance_thenCallsFindByName() {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

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

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(repository).findByName("Name");
    verify(instance).getRegistration();
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("Name", toListResult.get(0));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <ul>
   *   <li>Then return toList second is create.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName("Test getApplicationForInstance(Instance); then return toList second is create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance_thenReturnToListSecondIsCreate() {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(createResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

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

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).filter(isA(Predicate.class));
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertSame(createResult, toListResult.get(1));
  }

  /**
   * Test {@link ApplicationRegistry#getApplicationForInstance(Instance)}.
   *
   * <ul>
   *   <li>Then return toList second is fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getApplicationForInstance(Instance)}
   */
  @Test
  @DisplayName(
      "Test getApplicationForInstance(Instance); then return toList second is fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getApplicationForInstance(Instance)"})
  void testGetApplicationForInstance_thenReturnToListSecondIsFromIterableArrayList() {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

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

    // Act
    Tuple2<String, Flux<Instance>> actualApplicationForInstance =
        applicationRegistry.getApplicationForInstance(instance);

    // Assert
    verify(instance).getRegistration();
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).filter(isA(Predicate.class));
    List<Object> toListResult = actualApplicationForInstance.toList();
    assertEquals(2, toListResult.size());
    assertEquals("Name", toListResult.get(0));
    assertSame(fromIterableResult, toListResult.get(1));
  }

  /**
   * Test {@link ApplicationRegistry#toApplication(String, Flux)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry}.
   *   <li>When fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#toApplication(String, Flux)}
   */
  @Test
  @DisplayName(
      "Test toApplication(String, Flux); given InstanceRegistry; when fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.toApplication(String, Flux)"})
  void testToApplication_givenInstanceRegistry_whenFromIterableArrayList() throws AssertionError {
    // Arrange
    Flux<Instance> instances = Flux.fromIterable(new ArrayList<>());

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.toApplication("Name", instances));
    createResult
        .assertNext(
            a -> {
              Application application = a;
              assertNull(application.getBuildVersion());
              assertTrue(application.getInstances().isEmpty());
              assertEquals("Name", application.getName());
              assertEquals("UNKNOWN", application.getStatus());
              Instant statusTimestamp = application.getStatusTimestamp();
              assertEquals(0L, statusTimestamp.getEpochSecond());
              assertEquals(0, statusTimestamp.getNano());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link ApplicationRegistry#toApplication(String, Flux)}.
   *
   * <ul>
   *   <li>Given just {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#toApplication(String, Flux)}
   */
  @Test
  @DisplayName("Test toApplication(String, Flux); given just ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationRegistry.toApplication(String, Flux)"})
  void testToApplication_givenJustArrayList() throws AssertionError {
    // Arrange
    DirectProcessor<Instance> instances = mock(DirectProcessor.class);
    Mono<List<Instance>> justResult = Mono.just(new ArrayList<>());
    when(instances.collectList()).thenReturn(justResult);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationRegistry.toApplication("Name", instances));
    createResult
        .assertNext(
            a -> {
              Application application = a;
              assertNull(application.getBuildVersion());
              assertTrue(application.getInstances().isEmpty());
              assertEquals("Name", application.getName());
              assertEquals("UNKNOWN", application.getStatus());
              Instant statusTimestamp = application.getStatusTimestamp();
              assertEquals(0L, statusTimestamp.getEpochSecond());
              assertEquals(0, statusTimestamp.getNano());
              return;
            })
        .expectComplete()
        .verify();
    verify(instances).collectList();
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName("Test getBuildVersion(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion() {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(1);

    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(buildVersion);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(mock(BuildVersion.class));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion).compareTo(isA(BuildVersion.class));
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName("Test getBuildVersion(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion2() {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(-1);

    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(buildVersion);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion).compareTo(isA(BuildVersion.class));
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link BuildVersion} {@link BuildVersion#compareTo(BuildVersion)} return minus one.
   *   <li>Then calls {@link BuildVersion#compareTo(BuildVersion)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName(
      "Test getBuildVersion(List); given BuildVersion compareTo(BuildVersion) return minus one; then calls compareTo(BuildVersion)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenBuildVersionCompareToReturnMinusOne_thenCallsCompareTo() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(-1);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(buildVersion);

    Instance instance3 = mock(Instance.class);
    when(instance3.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance3);
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance3).getBuildVersion();
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion).compareTo(isA(BuildVersion.class));
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link BuildVersion} {@link BuildVersion#compareTo(BuildVersion)} return one.
   *   <li>Then calls {@link BuildVersion#compareTo(BuildVersion)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName(
      "Test getBuildVersion(List); given BuildVersion compareTo(BuildVersion) return one; then calls compareTo(BuildVersion)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenBuildVersionCompareToReturnOne_thenCallsCompareTo() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(1);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(buildVersion);

    Instance instance3 = mock(Instance.class);
    when(instance3.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance3);
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance3).getBuildVersion();
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion).compareTo(isA(BuildVersion.class));
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link BuildVersion} {@link BuildVersion#compareTo(BuildVersion)} return zero.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName("Test getBuildVersion(List); given BuildVersion compareTo(BuildVersion) return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenBuildVersionCompareToReturnZero() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(0);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(buildVersion);

    Instance instance3 = mock(Instance.class);
    when(instance3.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    Instance instance4 = mock(Instance.class);
    when(instance4.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance4);
    instances.addAll(new ArrayList<>());
    instances.add(instance3);
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance4).getBuildVersion();
    verify(instance3).getBuildVersion();
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion).compareTo(isA(BuildVersion.class));
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName(
      "Test getBuildVersion(List); given InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(1);

    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(buildVersion);

    BuildVersion buildVersion2 = mock(BuildVersion.class);
    when(buildVersion2.compareTo(Mockito.<BuildVersion>any())).thenReturn(1);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(buildVersion2);

    Instance instance3 = mock(Instance.class);
    when(instance3.getBuildVersion()).thenReturn(BuildVersion.valueOf("[.\\-+]"));

    Instance instance4 = mock(Instance.class);
    when(instance4.getBuildVersion()).thenReturn(BuildVersion.valueOf("[.\\-+][.\\-+]"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance4);
    instances.add(instance3);
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance4).getBuildVersion();
    verify(instance3).getBuildVersion();
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion2, atLeast(1)).compareTo(Mockito.<BuildVersion>any());
    verify(buildVersion, atLeast(1)).compareTo(Mockito.<BuildVersion>any());
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link Instance} {@link Instance#getBuildVersion()} return valueOf {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName("Test getBuildVersion(List); given Instance getBuildVersion() return valueOf '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenInstanceGetBuildVersionReturnValueOf42() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(1);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(buildVersion);

    Instance instance3 = mock(Instance.class);
    when(instance3.getBuildVersion()).thenReturn(BuildVersion.valueOf("42"));

    Instance instance4 = mock(Instance.class);
    when(instance4.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance4);
    instances.addAll(new ArrayList<>());
    instances.add(instance3);
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance4).getBuildVersion();
    verify(instance3).getBuildVersion();
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion, atLeast(1)).compareTo(Mockito.<BuildVersion>any());
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link Instance} {@link Instance#getBuildVersion()} return valueOf {@code
   *       java.util.Map}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName(
      "Test getBuildVersion(List); given Instance getBuildVersion() return valueOf 'java.util.Map'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenInstanceGetBuildVersionReturnValueOfJavaUtilMap() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(1);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(buildVersion);

    Instance instance3 = mock(Instance.class);
    when(instance3.getBuildVersion()).thenReturn(BuildVersion.valueOf("java.util.Map"));

    Instance instance4 = mock(Instance.class);
    when(instance4.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance4);
    instances.addAll(new ArrayList<>());
    instances.add(instance3);
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance4).getBuildVersion();
    verify(instance3).getBuildVersion();
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion).compareTo(isA(BuildVersion.class));
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link Instance} {@link Instance#getBuildVersion()} return valueOf {@code UNKNOWN}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName(
      "Test getBuildVersion(List); given Instance getBuildVersion() return valueOf 'UNKNOWN'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenInstanceGetBuildVersionReturnValueOfUnknown() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(1);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(buildVersion);

    Instance instance3 = mock(Instance.class);
    when(instance3.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    Instance instance4 = mock(Instance.class);
    when(instance4.getBuildVersion()).thenReturn(BuildVersion.valueOf("UNKNOWN"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance4);
    instances.addAll(new ArrayList<>());
    instances.add(instance3);
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance4).getBuildVersion();
    verify(instance3).getBuildVersion();
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion).compareTo(isA(BuildVersion.class));
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link Instance} {@link Instance#getBuildVersion()} return valueOf {@code UNKNOWN}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName(
      "Test getBuildVersion(List); given Instance getBuildVersion() return valueOf 'UNKNOWN'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenInstanceGetBuildVersionReturnValueOfUnknown2() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    BuildVersion buildVersion = mock(BuildVersion.class);
    when(buildVersion.compareTo(Mockito.<BuildVersion>any())).thenReturn(1);

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(buildVersion);

    Instance instance3 = mock(Instance.class);
    when(instance3.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    Instance instance4 = mock(Instance.class);
    when(instance4.getBuildVersion()).thenReturn(BuildVersion.valueOf("UNKNOWN"));

    Instance instance5 = mock(Instance.class);
    when(instance5.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance5);
    instances.add(instance4);
    instances.addAll(new ArrayList<>());
    instances.add(instance3);
    instances.add(instance2);
    instances.add(instance);

    // Act
    applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance5).getBuildVersion();
    verify(instance4).getBuildVersion();
    verify(instance3).getBuildVersion();
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    verify(buildVersion, atLeast(1)).compareTo(Mockito.<BuildVersion>any());
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry}.
   *   <li>Then return valueOf {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName("Test getBuildVersion(List); given InstanceRegistry; then return valueOf 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenInstanceRegistry_thenReturnValueOfFoo() {
    // Arrange
    Instance instance = mock(Instance.class);
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");
    when(instance.getBuildVersion()).thenReturn(valueOfResult);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance);

    // Act
    BuildVersion actualBuildVersion = applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance).getBuildVersion();
    assertSame(valueOfResult, actualBuildVersion);
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry}.
   *   <li>Then return valueOf {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName("Test getBuildVersion(List); given InstanceRegistry; then return valueOf 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenInstanceRegistry_thenReturnValueOfFoo2() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    Instance instance2 = mock(Instance.class);
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");
    when(instance2.getBuildVersion()).thenReturn(valueOfResult);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    BuildVersion actualBuildVersion = applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    assertSame(valueOfResult, actualBuildVersion);
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry}.
   *   <li>Then return valueOf {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName("Test getBuildVersion(List); given InstanceRegistry; then return valueOf 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenInstanceRegistry_thenReturnValueOfFoo3() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    Instance instance2 = mock(Instance.class);
    when(instance2.getBuildVersion()).thenReturn(BuildVersion.valueOf("foo"));

    Instance instance3 = mock(Instance.class);
    BuildVersion valueOfResult = BuildVersion.valueOf("foo");
    when(instance3.getBuildVersion()).thenReturn(valueOfResult);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance3);
    instances.add(instance2);
    instances.add(instance);

    // Act
    BuildVersion actualBuildVersion = applicationRegistry.getBuildVersion(instances);

    // Assert
    verify(instance3).getBuildVersion();
    verify(instance2).getBuildVersion();
    verify(instance).getBuildVersion();
    assertSame(valueOfResult, actualBuildVersion);
  }

  /**
   * Test {@link ApplicationRegistry#getBuildVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry}.
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  @DisplayName(
      "Test getBuildVersion(List); given InstanceRegistry; when ArrayList(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BuildVersion ApplicationRegistry.getBuildVersion(List)"})
  void testGetBuildVersion_givenInstanceRegistry_whenArrayList_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(applicationRegistry.getBuildVersion(new ArrayList<>()));
  }

  /**
   * Test {@link ApplicationRegistry#getStatus(List)}.
   *
   * <p>Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  @DisplayName("Test getStatus(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getStatus(List)"})
  void testGetStatus() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InMemoryEventStore eventStore = new InMemoryEventStore(3);
    eventStore.append(new ArrayList<>());
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("components");

    Instance instance = mock(Instance.class);
    when(instance.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    StatusInfo statusInfo2 = mock(StatusInfo.class);
    when(statusInfo2.getStatus()).thenReturn("Status");

    Instance instance2 = mock(Instance.class);
    when(instance2.getStatusTimestamp())
        .thenReturn(
            LocalDate.of(1970, 1, 1)
                .atStartOfDay()
                .atZone(ZoneOffset.ofTotalSeconds(1))
                .toInstant());
    when(instance2.getStatusInfo()).thenReturn(statusInfo2);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    Tuple2<String, Instant> actualStatus = applicationRegistry.getStatus(instances);

    // Assert
    verify(instance2).getStatusInfo();
    verify(instance).getStatusInfo();
    verify(instance2).getStatusTimestamp();
    verify(instance).getStatusTimestamp();
    verify(statusInfo2).getStatus();
    verify(statusInfo).getStatus();
    List<Object> toListResult = actualStatus.toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("Status", toListResult.get(0));
    assertEquals(-1L, ((Instant) getResult).getEpochSecond());
    assertEquals(0, ((Instant) getResult).getNano());
  }

  /**
   * Test {@link ApplicationRegistry#getStatus(List)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  @DisplayName("Test getStatus(List); given StatusInfo getStatus() return 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getStatus(List)"})
  void testGetStatus_givenStatusInfoGetStatusReturnFoo() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("foo");

    Instance instance = mock(Instance.class);
    when(instance.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    StatusInfo statusInfo2 = mock(StatusInfo.class);
    when(statusInfo2.getStatus()).thenReturn("components");

    Instance instance2 = mock(Instance.class);
    when(instance2.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance2.getStatusInfo()).thenReturn(statusInfo2);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    Tuple2<String, Instant> actualStatus = applicationRegistry.getStatus(instances);

    // Assert
    verify(instance2).getStatusInfo();
    verify(instance).getStatusInfo();
    verify(instance2).getStatusTimestamp();
    verify(instance).getStatusTimestamp();
    verify(statusInfo2).getStatus();
    verify(statusInfo).getStatus();
    List<Object> toListResult = actualStatus.toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("components", toListResult.get(0));
    assertEquals(0, ((Instant) getResult).getNano());
    assertEquals(0L, ((Instant) getResult).getEpochSecond());
  }

  /**
   * Test {@link ApplicationRegistry#getStatus(List)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return toList first is {@code Status}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  @DisplayName(
      "Test getStatus(List); given StatusInfo getStatus() return 'Status'; then return toList first is 'Status'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getStatus(List)"})
  void testGetStatus_givenStatusInfoGetStatusReturnStatus_thenReturnToListFirstIsStatus() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance);

    // Act
    Tuple2<String, Instant> actualStatus = applicationRegistry.getStatus(instances);

    // Assert
    verify(instance).getStatusInfo();
    verify(instance).getStatusTimestamp();
    verify(statusInfo).getStatus();
    List<Object> toListResult = actualStatus.toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("Status", toListResult.get(0));
    assertEquals(0, ((Instant) getResult).getNano());
    assertEquals(0L, ((Instant) getResult).getEpochSecond());
  }

  /**
   * Test {@link ApplicationRegistry#getStatus(List)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return toList first is {@code Status}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  @DisplayName(
      "Test getStatus(List); given StatusInfo getStatus() return 'Status'; then return toList first is 'Status'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getStatus(List)"})
  void testGetStatus_givenStatusInfoGetStatusReturnStatus_thenReturnToListFirstIsStatus2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("components");

    Instance instance = mock(Instance.class);
    when(instance.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    StatusInfo statusInfo2 = mock(StatusInfo.class);
    when(statusInfo2.getStatus()).thenReturn("Status");

    Instance instance2 = mock(Instance.class);
    when(instance2.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance2.getStatusInfo()).thenReturn(statusInfo2);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    Tuple2<String, Instant> actualStatus = applicationRegistry.getStatus(instances);

    // Assert
    verify(instance2).getStatusInfo();
    verify(instance).getStatusInfo();
    verify(instance2).getStatusTimestamp();
    verify(instance).getStatusTimestamp();
    verify(statusInfo2).getStatus();
    verify(statusInfo).getStatus();
    List<Object> toListResult = actualStatus.toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("Status", toListResult.get(0));
    assertEquals(0, ((Instant) getResult).getNano());
    assertEquals(0L, ((Instant) getResult).getEpochSecond());
  }

  /**
   * Test {@link ApplicationRegistry#getStatus(List)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code UP}.
   *   <li>Then return toList first is {@code RESTRICTED}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  @DisplayName(
      "Test getStatus(List); given StatusInfo getStatus() return 'UP'; then return toList first is 'RESTRICTED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getStatus(List)"})
  void testGetStatus_givenStatusInfoGetStatusReturnUp_thenReturnToListFirstIsRestricted() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("components");

    Instance instance = mock(Instance.class);
    when(instance.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    StatusInfo statusInfo2 = mock(StatusInfo.class);
    when(statusInfo2.getStatus()).thenReturn("UP");

    Instance instance2 = mock(Instance.class);
    when(instance2.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance2.getStatusInfo()).thenReturn(statusInfo2);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    Tuple2<String, Instant> actualStatus = applicationRegistry.getStatus(instances);

    // Assert
    verify(instance2).getStatusInfo();
    verify(instance).getStatusInfo();
    verify(instance2).getStatusTimestamp();
    verify(instance).getStatusTimestamp();
    verify(statusInfo2).getStatus();
    verify(statusInfo).getStatus();
    List<Object> toListResult = actualStatus.toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("RESTRICTED", toListResult.get(0));
    assertEquals(0, ((Instant) getResult).getNano());
    assertEquals(0L, ((Instant) getResult).getEpochSecond());
  }

  /**
   * Test {@link ApplicationRegistry#getStatus(List)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code UP}.
   *   <li>Then return toList first is {@code RESTRICTED}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  @DisplayName(
      "Test getStatus(List); given StatusInfo getStatus() return 'UP'; then return toList first is 'RESTRICTED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getStatus(List)"})
  void testGetStatus_givenStatusInfoGetStatusReturnUp_thenReturnToListFirstIsRestricted2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");

    Instance instance = mock(Instance.class);
    when(instance.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    StatusInfo statusInfo2 = mock(StatusInfo.class);
    when(statusInfo2.getStatus()).thenReturn("Status");

    Instance instance2 = mock(Instance.class);
    when(instance2.getStatusTimestamp())
        .thenReturn(
            LocalDate.of(1970, 1, 1)
                .atStartOfDay()
                .atZone(ZoneOffset.ofTotalSeconds(1))
                .toInstant());
    when(instance2.getStatusInfo()).thenReturn(statusInfo2);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    Tuple2<String, Instant> actualStatus = applicationRegistry.getStatus(instances);

    // Assert
    verify(instance2).getStatusInfo();
    verify(instance).getStatusInfo();
    verify(instance2).getStatusTimestamp();
    verify(instance).getStatusTimestamp();
    verify(statusInfo2).getStatus();
    verify(statusInfo).getStatus();
    List<Object> toListResult = actualStatus.toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("RESTRICTED", toListResult.get(0));
    assertEquals(0, ((Instant) getResult).getNano());
    assertEquals(0L, ((Instant) getResult).getEpochSecond());
  }

  /**
   * Test {@link ApplicationRegistry#getStatus(List)}.
   *
   * <ul>
   *   <li>Then return toList first is {@code components}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  @DisplayName("Test getStatus(List); then return toList first is 'components'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getStatus(List)"})
  void testGetStatus_thenReturnToListFirstIsComponents() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("components");

    Instance instance = mock(Instance.class);
    when(instance.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    StatusInfo statusInfo2 = mock(StatusInfo.class);
    when(statusInfo2.getStatus()).thenReturn("components");

    Instance instance2 = mock(Instance.class);
    when(instance2.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance2.getStatusInfo()).thenReturn(statusInfo2);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    Tuple2<String, Instant> actualStatus = applicationRegistry.getStatus(instances);

    // Assert
    verify(instance2).getStatusInfo();
    verify(instance).getStatusInfo();
    verify(instance2).getStatusTimestamp();
    verify(instance).getStatusTimestamp();
    verify(statusInfo2).getStatus();
    verify(statusInfo).getStatus();
    List<Object> toListResult = actualStatus.toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("components", toListResult.get(0));
    assertEquals(0, ((Instant) getResult).getNano());
    assertEquals(0L, ((Instant) getResult).getEpochSecond());
  }

  /**
   * Test {@link ApplicationRegistry#getStatus(List)}.
   *
   * <ul>
   *   <li>Then return toList second EpochSecond is minus one.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  @DisplayName("Test getStatus(List); then return toList second EpochSecond is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getStatus(List)"})
  void testGetStatus_thenReturnToListSecondEpochSecondIsMinusOne() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("components");

    Instance instance = mock(Instance.class);
    when(instance.getStatusTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    StatusInfo statusInfo2 = mock(StatusInfo.class);
    when(statusInfo2.getStatus()).thenReturn("Status");

    Instance instance2 = mock(Instance.class);
    when(instance2.getStatusTimestamp())
        .thenReturn(
            LocalDate.of(1970, 1, 1)
                .atStartOfDay()
                .atZone(ZoneOffset.ofTotalSeconds(1))
                .toInstant());
    when(instance2.getStatusInfo()).thenReturn(statusInfo2);

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(instance2);
    instances.add(instance);

    // Act
    Tuple2<String, Instant> actualStatus = applicationRegistry.getStatus(instances);

    // Assert
    verify(instance2).getStatusInfo();
    verify(instance).getStatusInfo();
    verify(instance2).getStatusTimestamp();
    verify(instance).getStatusTimestamp();
    verify(statusInfo2).getStatus();
    verify(statusInfo).getStatus();
    List<Object> toListResult = actualStatus.toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("Status", toListResult.get(0));
    assertEquals(-1L, ((Instant) getResult).getEpochSecond());
    assertEquals(0, ((Instant) getResult).getNano());
  }

  /**
   * Test {@link ApplicationRegistry#getStatus(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return toList first is {@code UNKNOWN}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  @DisplayName("Test getStatus(List); when ArrayList(); then return toList first is 'UNKNOWN'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tuple2 ApplicationRegistry.getStatus(List)"})
  void testGetStatus_whenArrayList_thenReturnToListFirstIsUnknown() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    List<Object> toListResult = applicationRegistry.getStatus(new ArrayList<>()).toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("UNKNOWN", toListResult.get(0));
    assertEquals(0, ((Instant) getResult).getNano());
    assertEquals(0L, ((Instant) getResult).getEpochSecond());
  }

  /**
   * Test {@link ApplicationRegistry#getMax(Instant, Instant)}.
   *
   * <ul>
   *   <li>When {@link LocalDate} with {@code 1970} and one and one atStartOfDay atZone {@link
   *       ZoneOffset#UTC} toInstant.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getMax(Instant, Instant)}
   */
  @Test
  @DisplayName(
      "Test getMax(Instant, Instant); when LocalDate with '1970' and one and one atStartOfDay atZone UTC toInstant")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instant ApplicationRegistry.getMax(Instant, Instant)"})
  void testGetMax_whenLocalDateWith1970AndOneAndOneAtStartOfDayAtZoneUtcToInstant() {
    // Arrange and Act
    Instant actualMax =
        applicationRegistry.getMax(
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    assertSame(Instant.EPOCH, actualMax);
  }

  /**
   * Test {@link ApplicationRegistry#getMax(Instant, Instant)}.
   *
   * <ul>
   *   <li>When ofYearDay one and one atStartOfDay atZone {@link ZoneOffset#UTC} toInstant.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationRegistry#getMax(Instant, Instant)}
   */
  @Test
  @DisplayName(
      "Test getMax(Instant, Instant); when ofYearDay one and one atStartOfDay atZone UTC toInstant")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instant ApplicationRegistry.getMax(Instant, Instant)"})
  void testGetMax_whenOfYearDayOneAndOneAtStartOfDayAtZoneUtcToInstant() {
    // Arrange
    LocalDate ofYearDayResult = LocalDate.ofYearDay(1, 1);

    // Act
    Instant actualMax =
        applicationRegistry.getMax(
            ofYearDayResult.atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    assertSame(Instant.EPOCH, actualMax);
  }
}
