package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertNull;
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
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ChannelSendOperator;
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

@ContextConfiguration(classes = {InstanceRegistry.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class InstanceRegistryDiffblueTest {
  @MockitoBean private InstanceFilter instanceFilter;

  @MockitoBean private InstanceIdGenerator instanceIdGenerator;

  @Autowired private InstanceRegistry instanceRegistry;

  @MockitoBean private InstanceRepository instanceRepository;

  /**
   * Test {@link InstanceRegistry#InstanceRegistry(InstanceRepository, InstanceIdGenerator,
   * InstanceFilter)}.
   *
   * <p>Method under test: {@link InstanceRegistry#InstanceRegistry(InstanceRepository,
   * InstanceIdGenerator, InstanceFilter)}
   */
  @Test
  @DisplayName("Test new InstanceRegistry(InstanceRepository, InstanceIdGenerator, InstanceFilter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceRegistry.<init>(InstanceRepository, InstanceIdGenerator, InstanceFilter)"
  })
  void testNewInstanceRegistry() throws AssertionError {
    // Arrange and Act
    InstanceRegistry actualInstanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Assert
    FirstStep<Instance> createResult = StepVerifier.create(actualInstanceRegistry.getInstances());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#register(Registration)}.
   *
   * <p>Method under test: {@link InstanceRegistry#register(Registration)}
   */
  @Test
  @DisplayName("Test register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.register(Registration)"})
  void testRegister() throws AssertionError {
    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            generator,
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceRegistry.register(mock(Registration.class)));
    createResult.expectError().verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstanceRegistry#register(Registration)}.
   *
   * <p>Method under test: {@link InstanceRegistry#register(Registration)}
   */
  @Test
  @DisplayName("Test register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.register(Registration)"})
  void testRegister2() throws AssertionError {
    // Arrange
    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            generator,
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceRegistry.register(mock(Registration.class)));
    createResult.expectError().verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstanceRegistry#register(Registration)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#compute(InstanceId,
   *       BiFunction)} return just {@link Instance}.
   *   <li>Then calls {@link InstanceRepository#compute(InstanceId, BiFunction)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#register(Registration)}
   */
  @Test
  @DisplayName(
      "Test register(Registration); given InstanceRepository compute(InstanceId, BiFunction) return just Instance; then calls compute(InstanceId, BiFunction)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.register(Registration)"})
  void testRegister_givenInstanceRepositoryComputeReturnJustInstance_thenCallsCompute()
      throws AssertionError {
    // Arrange
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(instanceRepository.compute(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(justResult);
    when(instanceIdGenerator.generateId(Mockito.<Registration>any()))
        .thenReturn(InstanceId.of("42"));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceRegistry.register(mock(Registration.class)));
    createResult.expectError().verify();
    verify(instanceRepository).compute(isA(InstanceId.class), isA(BiFunction.class));
    verify(instanceIdGenerator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstanceRegistry#register(Registration)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#register(Registration)}
   */
  @Test
  @DisplayName("Test register(Registration); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.register(Registration)"})
  void testRegister_thenCallsFind() throws AssertionError {
    // Arrange
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));

    InstanceRegistry instanceRegistry =
        new InstanceRegistry(repository, generator, mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceRegistry.register(mock(Registration.class)));
    createResult.expectError().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @DisplayName("Test getInstances()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  void testGetInstances() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @DisplayName("Test getInstances()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  void testGetInstances2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @DisplayName("Test getInstances(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  void testGetInstancesWithString() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances("Name"));
    createResult.expectComplete().verify();
    verify(instanceRepository).findByName("Name");
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @DisplayName("Test getInstances(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  void testGetInstancesWithString2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @DisplayName("Test getInstances(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  void testGetInstancesWithString3() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} addAll {@link ArrayList#ArrayList()}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @DisplayName(
      "Test getInstances(String) with 'String'; given ArrayList() addAll ArrayList(); when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  void testGetInstancesWithString_givenArrayListAddAllArrayList_whenEmptyString()
      throws AssertionError {
    // Arrange
    ArrayList<Instance> it = new ArrayList<>();
    it.addAll(new ArrayList<>());
    Flux<Instance> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances(""));
    createResult.expectComplete().verify();
    verify(instanceRepository).findByName("");
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@link
   *       Flux}.
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @DisplayName(
      "Test getInstances(String) with 'String'; given DirectProcessor filter(Predicate) return Flux; when '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  void testGetInstancesWithString_givenDirectProcessorFilterReturnFlux_when42() {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(mock(Flux.class));
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(directProcessor);

    // Act
    instanceRegistry.getInstances("42");

    // Assert
    verify(instanceRepository).findByName("42");
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @DisplayName(
      "Test getInstances(String) with 'String'; given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  void testGetInstancesWithString_givenDirectProcessorFilterReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances("Name"));
    createResult.expectComplete().verify();
    verify(instanceRepository).findByName("Name");
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return {@code
   *       null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @DisplayName(
      "Test getInstances(String) with 'String'; given DirectProcessor filter(Predicate) return 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  void testGetInstancesWithString_givenDirectProcessorFilterReturnNull_thenReturnNull() {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(null);
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(directProcessor);

    // Act
    Flux<Instance> actualInstances = instanceRegistry.getInstances("NameName");

    // Assert
    verify(instanceRepository).findByName("NameName");
    verify(directProcessor).filter(isA(Predicate.class));
    assertNull(actualInstances);
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findByName(String)} return
   *       create.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @DisplayName(
      "Test getInstances(String) with 'String'; given InstanceRepository findByName(String) return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  void testGetInstancesWithString_givenInstanceRepositoryFindByNameReturnCreate() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(createResult);

    // Act
    instanceRegistry.getInstances("Name");

    // Assert
    verify(instanceRepository).findByName("Name");
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @DisplayName("Test getInstances(String) with 'String'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  void testGetInstancesWithString_thenCallsFindAll() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances("Name"));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @DisplayName(
      "Test getInstances(); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  void testGetInstances_givenDirectProcessorFilterReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances());
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   *
   * <ul>
   *   <li>Given {@link HazelcastEventStore} {@link HazelcastEventStore#findAll()} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @DisplayName(
      "Test getInstances(); given HazelcastEventStore findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  void testGetInstances_givenHazelcastEventStoreFindAllReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances());
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create.
   *   <li>Then calls {@link InstanceRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @DisplayName(
      "Test getInstances(); given InstanceRepository findAll() return create; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  void testGetInstances_givenInstanceRepositoryFindAllReturnCreate_thenCallsFindAll() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceRegistry.getInstances();

    // Assert
    verify(instanceRepository).findAll();
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @DisplayName(
      "Test getInstances(); given InstanceRepository findAll() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  void testGetInstances_givenInstanceRepositoryFindAllReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances());
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#groupBy(Function)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @DisplayName("Test getInstances(); then calls groupBy(Function)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  void testGetInstances_thenCallsGroupBy() throws AssertionError {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Flux<GroupedFlux<Object, InstanceEvent>> fromIterableResult =
        Flux.fromIterable(new ArrayList<>());
    when(directProcessor.groupBy(Mockito.<Function<InstanceEvent, Object>>any()))
        .thenReturn(fromIterableResult);

    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.findAll()).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances());
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   *
   * <ul>
   *   <li>Then return Error is {@link ArrayStoreException#ArrayStoreException()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @DisplayName("Test getInstances(); then return Error is ArrayStoreException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  void testGetInstances_thenReturnErrorIsArrayStoreException() {
    // Arrange
    ArrayStoreException t = new ArrayStoreException();
    t.addSuppressed(new Throwable());

    EmitterProcessor<Instance> createResult = EmitterProcessor.create(3, true);
    createResult.tryEmitError(t);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(createResult);
    when(instanceRepository.findAll()).thenReturn(directProcessor);

    // Act
    Flux<Instance> actualInstances = instanceRegistry.getInstances();

    // Assert
    verify(instanceRepository).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    assertTrue(actualInstances instanceof EmitterProcessor);
    assertSame(t, ((EmitterProcessor<Instance>) actualInstances).getError());
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   *
   * <ul>
   *   <li>Then return Error is {@link Throwable#Throwable()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @DisplayName("Test getInstances(); then return Error is Throwable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  void testGetInstances_thenReturnErrorIsThrowable() {
    // Arrange
    EmitterProcessor<Instance> createResult = EmitterProcessor.create(3, true);
    Throwable t = new Throwable();
    createResult.tryEmitError(t);

    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(createResult);
    when(instanceRepository.findAll()).thenReturn(directProcessor);

    // Act
    Flux<Instance> actualInstances = instanceRegistry.getInstances();

    // Assert
    verify(instanceRepository).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
    assertTrue(actualInstances instanceof EmitterProcessor);
    assertSame(t, ((EmitterProcessor<Instance>) actualInstances).getError());
  }

  /**
   * Test {@link InstanceRegistry#getInstance(InstanceId)}.
   *
   * <p>Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  @DisplayName("Test getInstance(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.getInstance(InstanceId)"})
  void testGetInstance() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(instanceRegistry.getInstance(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#getInstance(InstanceId)}.
   *
   * <p>Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  @DisplayName("Test getInstance(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.getInstance(InstanceId)"})
  void testGetInstance2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(instanceRegistry.getInstance(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#getInstance(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#collectList()} return just {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test getInstance(InstanceId); given DirectProcessor collectList() return just ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.getInstance(InstanceId)"})
  void testGetInstance_givenDirectProcessorCollectListReturnJustArrayList() throws AssertionError {
    // Arrange
    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(directProcessor.collectList()).thenReturn(justResult);

    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(instanceRegistry.getInstance(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(directProcessor).collectList();
  }

  /**
   * Test {@link InstanceRegistry#getInstance(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link HazelcastEventStore} {@link HazelcastEventStore#find(InstanceId)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test getInstance(InstanceId); given HazelcastEventStore find(InstanceId) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.getInstance(InstanceId)"})
  void testGetInstance_givenHazelcastEventStoreFindReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(instanceRegistry.getInstance(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstance(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#find(InstanceId)} return just
   *       {@link Instance}.
   *   <li>Then calls {@link InstanceRepository#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test getInstance(InstanceId); given InstanceRepository find(InstanceId) return just Instance; then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.getInstance(InstanceId)"})
  void testGetInstance_givenInstanceRepositoryFindReturnJustInstance_thenCallsFind()
      throws AssertionError {
    // Arrange
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(instanceRepository.find(Mockito.<InstanceId>any())).thenReturn(justResult);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(instanceRegistry.getInstance(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(instanceRepository).find(isA(InstanceId.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstance(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#filter(Predicate)} return just {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link DirectProcessor#collectList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test getInstance(InstanceId); given Mono filter(Predicate) return just ArrayList(); then calls collectList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.getInstance(InstanceId)"})
  void testGetInstance_givenMonoFilterReturnJustArrayList_thenCallsCollectList()
      throws AssertionError {
    // Arrange
    Mono<List<InstanceEvent>> mono = mock(Mono.class);
    Mono<List<InstanceEvent>> justResult = Mono.just(new ArrayList<>());
    when(mono.filter(Mockito.<Predicate<List<InstanceEvent>>>any())).thenReturn(justResult);

    DirectProcessor<InstanceEvent> directProcessor = mock(DirectProcessor.class);
    when(directProcessor.collectList()).thenReturn(mono);

    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(directProcessor);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(instanceRegistry.getInstance(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(directProcessor).collectList();
    verify(mono).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstance(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#filter(Predicate)} return {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test getInstance(InstanceId); given Mono filter(Predicate) return 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.getInstance(InstanceId)"})
  void testGetInstance_givenMonoFilterReturnNull_thenReturnNull() {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    when(mono.filter(Mockito.<Predicate<Instance>>any())).thenReturn(null);
    when(instanceRepository.find(Mockito.<InstanceId>any())).thenReturn(mono);

    // Act
    Mono<Instance> actualInstance = instanceRegistry.getInstance(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).find(isA(InstanceId.class));
    verify(mono).filter(isA(Predicate.class));
    assertNull(actualInstance);
  }

  /**
   * Test {@link InstanceRegistry#getInstance(InstanceId)}.
   *
   * <ul>
   *   <li>When {@link InstanceId} with {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  @DisplayName("Test getInstance(InstanceId); when InstanceId with 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.getInstance(InstanceId)"})
  void testGetInstance_whenInstanceIdWithValue() throws AssertionError {
    // Arrange
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(instanceRepository.find(Mockito.<InstanceId>any())).thenReturn(justResult);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(instanceRegistry.getInstance(InstanceId.of("Value")));
    createResult.expectComplete().verify();
    verify(instanceRepository).find(isA(InstanceId.class));
  }

  /**
   * Test {@link InstanceRegistry#deregister(InstanceId)}.
   *
   * <p>Method under test: {@link InstanceRegistry#deregister(InstanceId)}
   */
  @Test
  @DisplayName("Test deregister(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.deregister(InstanceId)"})
  void testDeregister() {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    Mono<Object> justResult = Mono.just(channelSendOperator);
    when(mono.map(Mockito.<Function<Instance, Object>>any())).thenReturn(justResult);
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(mono);

    // Act
    Mono<InstanceId> actualDeregisterResult = instanceRegistry.deregister(InstanceId.of("42"));

    // Assert
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
    verify(mono).map(isA(Function.class));
    assertSame(justResult, actualDeregisterResult);
  }

  /**
   * Test {@link InstanceRegistry#deregister(InstanceId)}.
   *
   * <p>Method under test: {@link InstanceRegistry#deregister(InstanceId)}
   */
  @Test
  @DisplayName("Test deregister(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.deregister(InstanceId)"})
  void testDeregister2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceRegistry.deregister(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#deregister(InstanceId)}.
   *
   * <p>Method under test: {@link InstanceRegistry#deregister(InstanceId)}
   */
  @Test
  @DisplayName("Test deregister(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.deregister(InstanceId)"})
  void testDeregister3() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceRegistry.deregister(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#deregister(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#computeIfPresent(InstanceId,
   *       BiFunction)} return just {@link Instance}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#deregister(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test deregister(InstanceId); given InstanceRepository computeIfPresent(InstanceId, BiFunction) return just Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.deregister(InstanceId)"})
  void testDeregister_givenInstanceRepositoryComputeIfPresentReturnJustInstance()
      throws AssertionError {
    // Arrange
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(instanceRepository.computeIfPresent(
            Mockito.<InstanceId>any(),
            Mockito.<BiFunction<InstanceId, Instance, Mono<Instance>>>any()))
        .thenReturn(justResult);

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceRegistry.deregister(InstanceId.of("42")));
    createResult.expectError().verify();
    verify(instanceRepository).computeIfPresent(isA(InstanceId.class), isA(BiFunction.class));
  }

  /**
   * Test {@link InstanceRegistry#deregister(InstanceId)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegistry#deregister(InstanceId)}
   */
  @Test
  @DisplayName("Test deregister(InstanceId); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstanceRegistry.deregister(InstanceId)"})
  void testDeregister_thenCallsFind() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<InstanceId> createResult =
        StepVerifier.create(instanceRegistry.deregister(InstanceId.of("42")));
    createResult.expectComplete().verify();
    verify(eventStore).find(isA(InstanceId.class));
  }
}
