package de.codecentric.boot.admin.server.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.domain.entities.Application.Builder;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.BuildVersion;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventPublisher;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
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
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
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

@ContextConfiguration(classes = {ApplicationsController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ApplicationsControllerDiffblueTest {
  @MockitoBean private ApplicationRegistry applicationRegistry;

  @Autowired private ApplicationsController applicationsController;

  /**
   * Test {@link ApplicationsController#ApplicationsController(ApplicationRegistry,
   * ApplicationEventPublisher)}.
   *
   * <p>Method under test: {@link ApplicationsController#ApplicationsController(ApplicationRegistry,
   * ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new ApplicationsController(ApplicationRegistry, ApplicationEventPublisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ApplicationsController.<init>(ApplicationRegistry, ApplicationEventPublisher)"
  })
  void testNewApplicationsController() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act
    ApplicationsController actualApplicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Assert
    FirstStep<Application> createResult =
        StepVerifier.create(actualApplicationsController.applications());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationsController#applications()}.
   *
   * <p>Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  @DisplayName("Test applications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applications()"})
  void testApplications() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationsController.applications());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationsController#applications()}.
   *
   * <p>Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  @DisplayName("Test applications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applications()"})
  void testApplications2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationsController.applications());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link ApplicationsController#applications()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#filter(Predicate)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  @DisplayName(
      "Test applications(); given DirectProcessor filter(Predicate) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applications()"})
  void testApplications_givenDirectProcessorFilterReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);

    InstanceRegistry instanceRegistry = mock(InstanceRegistry.class);
    when(instanceRegistry.getInstances()).thenReturn(directProcessor);
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationsController.applications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link ApplicationsController#applications()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#groupBy(Function)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  @DisplayName(
      "Test applications(); given DirectProcessor groupBy(Function) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applications()"})
  void testApplications_givenDirectProcessorGroupByReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<GroupedFlux<Object, Instance>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.groupBy(Mockito.<Function<Instance, Object>>any()))
        .thenReturn(fromIterableResult);

    DirectProcessor<Instance> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(directProcessor);

    InstanceRegistry instanceRegistry = mock(InstanceRegistry.class);
    when(instanceRegistry.getInstances()).thenReturn(directProcessor2);
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationsController.applications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(directProcessor2).filter(isA(Predicate.class));
    verify(directProcessor).groupBy(isA(Function.class));
  }

  /**
   * Test {@link ApplicationsController#applications()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances()} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  @DisplayName(
      "Test applications(); given InstanceRegistry getInstances() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applications()"})
  void testApplications_givenInstanceRegistryGetInstancesReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry = mock(InstanceRegistry.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances()).thenReturn(fromIterableResult);
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationsController.applications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
  }

  /**
   * Test {@link ApplicationsController#applications()}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  @DisplayName("Test applications(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applications()"})
  void testApplications_thenCallsFindAll() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationsController.applications());
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link ApplicationsController#applications()}.
   *
   * <ul>
   *   <li>Then calls {@link EventsourcingInstanceRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  @DisplayName("Test applications(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applications()"})
  void testApplications_thenCallsFindAll2() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findAll()).thenReturn(fromIterableResult);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationsController.applications());
    createResult.expectComplete().verify();
    verify(repository).findAll();
  }

  /**
   * Test {@link ApplicationsController#applications()}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#flatMap(Function, int)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  @DisplayName("Test applications(); then calls flatMap(Function, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applications()"})
  void testApplications_thenCallsFlatMap() throws AssertionError {
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

    InstanceRegistry instanceRegistry = mock(InstanceRegistry.class);
    when(instanceRegistry.getInstances()).thenReturn(directProcessor3);
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationsController.applications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(directProcessor3).filter(isA(Predicate.class));
    verify(directProcessor).flatMap(isA(Function.class), eq(2147483647));
    verify(directProcessor2).groupBy(isA(Function.class));
  }

  /**
   * Test {@link ApplicationsController#applications()}.
   *
   * <ul>
   *   <li>Then calls {@link ApplicationRegistry#getApplications()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  @DisplayName("Test applications(); then calls getApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applications()"})
  void testApplications_thenCallsGetApplications() throws AssertionError {
    // Arrange
    Flux<Application> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(applicationRegistry.getApplications()).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(applicationsController.applications());
    createResult.expectComplete().verify();
    verify(applicationRegistry).getApplications();
  }

  /**
   * Test {@link ApplicationsController#refreshApplications()}.
   *
   * <p>Method under test: {@link ApplicationsController#refreshApplications()}
   */
  @Test
  @DisplayName("Test refreshApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ApplicationsController.refreshApplications()"})
  void testRefreshApplications() {
    // Arrange
    ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);
    doNothing().when(publisher).publishEvent(Mockito.<ApplicationEvent>any());
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    ApplicationsController applicationsController = new ApplicationsController(registry, publisher);

    // Act
    applicationsController.refreshApplications();

    // Assert
    verify(publisher).publishEvent(isA(ApplicationEvent.class));
  }

  /**
   * Test {@link ApplicationsController#application(String)}.
   *
   * <p>Method under test: {@link ApplicationsController#application(String)}
   */
  @Test
  @DisplayName("Test application(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.application(String)"})
  void testApplication() throws AssertionError {
    // Arrange
    String s = "foo";
    BuildVersion buildVersion = BuildVersion.valueOf(s);

    Builder buildVersionResult = Application.builder().buildVersion(buildVersion);
    Application application =
        buildVersionResult
            .instances(new ArrayList<>())
            .name("Name")
            .status("Status")
            .statusTimestamp(
                LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
            .build();
    Mono<Application> justResult = Mono.just(application);
    when(applicationRegistry.getApplication(Mockito.<String>any())).thenReturn(justResult);

    // Act and Assert
    FirstStep<ResponseEntity<Application>> createResult =
        StepVerifier.create(applicationsController.application("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Application> responseEntity = r;
              Application body = responseEntity.getBody();
              assertSame(buildVersion, body.getBuildVersion());
              assertTrue(body.getInstances().isEmpty());
              assertEquals("Name", body.getName());
              assertEquals("Status", body.getStatus());
              Instant statusTimestamp = body.getStatusTimestamp();
              assertEquals(0L, statusTimestamp.getEpochSecond());
              assertEquals(0, statusTimestamp.getNano());
              assertTrue(responseEntity.getHeaders().isEmpty());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.OK, statusCode);
              assertEquals(200, responseEntity.getStatusCodeValue());
              assertTrue(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(applicationRegistry).getApplication("Name");
  }

  /**
   * Test {@link ApplicationsController#application(String)}.
   *
   * <p>Method under test: {@link ApplicationsController#application(String)}
   */
  @Test
  @DisplayName("Test application(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.application(String)"})
  void testApplication2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Application>> createResult =
        StepVerifier.create(applicationsController.application("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Application> responseEntity = r;
              assertNull(responseEntity.getBody());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link ApplicationsController#application(String)}.
   *
   * <p>Method under test: {@link ApplicationsController#application(String)}
   */
  @Test
  @DisplayName("Test application(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.application(String)"})
  void testApplication3() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Application>> createResult =
        StepVerifier.create(applicationsController.application("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Application> responseEntity = r;
              assertNull(responseEntity.getBody());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link ApplicationsController#application(String)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#application(String)}
   */
  @Test
  @DisplayName("Test application(String); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.application(String)"})
  void testApplication_thenCallsFindAll() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Application>> createResult =
        StepVerifier.create(applicationsController.application("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Application> responseEntity = r;
              assertNull(responseEntity.getBody());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link ApplicationsController#application(String)}.
   *
   * <ul>
   *   <li>Then calls {@link EventsourcingInstanceRepository#findByName(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#application(String)}
   */
  @Test
  @DisplayName("Test application(String); then calls findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.application(String)"})
  void testApplication_thenCallsFindByName() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Application>> createResult =
        StepVerifier.create(applicationsController.application("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Application> responseEntity = r;
              assertNull(responseEntity.getBody());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(repository).findByName("Name");
  }

  /**
   * Test {@link ApplicationsController#application(String)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceRegistry#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#application(String)}
   */
  @Test
  @DisplayName("Test application(String); then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.application(String)"})
  void testApplication_thenCallsGetInstances() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry = mock(InstanceRegistry.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Application>> createResult =
        StepVerifier.create(applicationsController.application("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Application> responseEntity = r;
              assertNull(responseEntity.getBody());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(instanceRegistry).getInstances("Name");
  }

  /**
   * Test {@link ApplicationsController#applicationsStream()}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#mergeWith(Publisher)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#applicationsStream()}
   */
  @Test
  @DisplayName("Test applicationsStream(); then calls mergeWith(Publisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux ApplicationsController.applicationsStream()"})
  void testApplicationsStream_thenCallsMergeWith() throws AssertionError {
    // Arrange
    DirectProcessor<Object> directProcessor = mock(DirectProcessor.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.mergeWith(Mockito.<Publisher<?>>any())).thenReturn(fromIterableResult);

    DirectProcessor<Application> directProcessor2 = mock(DirectProcessor.class);
    when(directProcessor2.map(Mockito.<Function<Application, Object>>any()))
        .thenReturn(directProcessor);
    when(applicationRegistry.getApplicationStream()).thenReturn(directProcessor2);

    // Act and Assert
    FirstStep<ServerSentEvent<Application>> createResult =
        StepVerifier.create(applicationsController.applicationsStream());
    createResult.expectComplete().verify();
    verify(applicationRegistry).getApplicationStream();
    verify(directProcessor2).map(isA(Function.class));
    verify(directProcessor).mergeWith(isA(Publisher.class));
  }

  /**
   * Test {@link ApplicationsController#unregister(String)}.
   *
   * <p>Method under test: {@link ApplicationsController#unregister(String)}
   */
  @Test
  @DisplayName("Test unregister(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.unregister(String)"})
  void testUnregister() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(applicationsController.unregister("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Void> responseEntity = r;
              assertNull(responseEntity.getBody());
              assertTrue(responseEntity.getHeaders().isEmpty());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link ApplicationsController#unregister(String)}.
   *
   * <p>Method under test: {@link ApplicationsController#unregister(String)}
   */
  @Test
  @DisplayName("Test unregister(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.unregister(String)"})
  void testUnregister2() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(applicationsController.unregister("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Void> responseEntity = r;
              assertNull(responseEntity.getBody());
              assertTrue(responseEntity.getHeaders().isEmpty());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link ApplicationsController#unregister(String)}.
   *
   * <ul>
   *   <li>Given {@link ApplicationRegistry} {@link ApplicationRegistry#deregister(String)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#unregister(String)}
   */
  @Test
  @DisplayName(
      "Test unregister(String); given ApplicationRegistry deregister(String) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.unregister(String)"})
  void testUnregister_givenApplicationRegistryDeregisterReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    Flux<InstanceId> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(applicationRegistry.deregister(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(applicationsController.unregister("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Void> responseEntity = r;
              assertNull(responseEntity.getBody());
              assertTrue(responseEntity.getHeaders().isEmpty());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(applicationRegistry).deregister("Name");
  }

  /**
   * Test {@link ApplicationsController#unregister(String)}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#collectList()} return just {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#unregister(String)}
   */
  @Test
  @DisplayName(
      "Test unregister(String); given DirectProcessor collectList() return just ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.unregister(String)"})
  void testUnregister_givenDirectProcessorCollectListReturnJustArrayList() throws AssertionError {
    // Arrange
    DirectProcessor<InstanceId> directProcessor = mock(DirectProcessor.class);
    Mono<List<InstanceId>> justResult = Mono.just(new ArrayList<>());
    when(directProcessor.collectList()).thenReturn(justResult);
    when(applicationRegistry.deregister(Mockito.<String>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(applicationsController.unregister("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Void> responseEntity = r;
              assertNull(responseEntity.getBody());
              assertTrue(responseEntity.getHeaders().isEmpty());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(applicationRegistry).deregister("Name");
    verify(directProcessor).collectList();
  }

  /**
   * Test {@link ApplicationsController#unregister(String)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#unregister(String)}
   */
  @Test
  @DisplayName("Test unregister(String); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.unregister(String)"})
  void testUnregister_thenCallsFindAll() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(applicationsController.unregister("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Void> responseEntity = r;
              assertNull(responseEntity.getBody());
              assertTrue(responseEntity.getHeaders().isEmpty());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link ApplicationsController#unregister(String)}.
   *
   * <ul>
   *   <li>Then calls {@link EventsourcingInstanceRepository#findByName(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#unregister(String)}
   */
  @Test
  @DisplayName("Test unregister(String); then calls findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.unregister(String)"})
  void testUnregister_thenCallsFindByName() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(applicationsController.unregister("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Void> responseEntity = r;
              assertNull(responseEntity.getBody());
              assertTrue(responseEntity.getHeaders().isEmpty());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(repository).findByName("Name");
  }

  /**
   * Test {@link ApplicationsController#unregister(String)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceRegistry#getInstances(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationsController#unregister(String)}
   */
  @Test
  @DisplayName("Test unregister(String); then calls getInstances(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono ApplicationsController.unregister(String)"})
  void testUnregister_thenCallsGetInstances() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry = mock(InstanceRegistry.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);
    ApplicationRegistry registry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));
    ApplicationsController applicationsController =
        new ApplicationsController(registry, mock(ApplicationEventPublisher.class));

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(applicationsController.unregister("Name"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Void> responseEntity = r;
              assertNull(responseEntity.getBody());
              assertTrue(responseEntity.getHeaders().isEmpty());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NOT_FOUND, statusCode);
              assertEquals(404, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(instanceRegistry).getInstances("Name");
  }
}
