package de.codecentric.boot.admin.server.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.Registration.Builder;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InstancesController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class InstancesControllerDiffblueTest {
  @MockitoBean private InstanceEventStore instanceEventStore;

  @MockitoBean private InstanceRegistry instanceRegistry;

  @Autowired private InstancesController instancesController;

  /**
   * Test {@link InstancesController#InstancesController(InstanceRegistry, InstanceEventStore)}.
   *
   * <p>Method under test: {@link InstancesController#InstancesController(InstanceRegistry,
   * InstanceEventStore)}
   */
  @Test
  @DisplayName("Test new InstancesController(InstanceRegistry, InstanceEventStore)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstancesController.<init>(InstanceRegistry, InstanceEventStore)"})
  void testNewInstancesController() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act
    InstancesController actualInstancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Assert
    FirstStep<InstanceEvent> createResult = StepVerifier.create(actualInstancesController.events());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstancesController#register(Registration, UriComponentsBuilder)}.
   *
   * <p>Method under test: {@link InstancesController#register(Registration, UriComponentsBuilder)}
   */
  @Test
  @DisplayName("Test register(Registration, UriComponentsBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.register(Registration, UriComponentsBuilder)"})
  void testRegister() throws AssertionError {
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
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act and Assert
    FirstStep<ResponseEntity<Map<String, InstanceId>>> createResult =
        StepVerifier.create(
            instancesController.register(registration, UriComponentsBuilder.newInstance()));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Map<String, InstanceId>> responseEntity = r;
              Map<String, InstanceId> body = responseEntity.getBody();
              assertEquals(1, body.size());
              assertSame(ofResult, body.get("id"));
              HttpHeaders headers = responseEntity.getHeaders();
              assertEquals(1, headers.size());
              List<String> getResult = headers.get("Location");
              assertEquals(1, getResult.size());
              assertEquals("/instances/42", getResult.get(0));
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.CREATED, statusCode);
              assertEquals(201, responseEntity.getStatusCodeValue());
              assertTrue(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstancesController#register(Registration, UriComponentsBuilder)}.
   *
   * <p>Method under test: {@link InstancesController#register(Registration, UriComponentsBuilder)}
   */
  @Test
  @DisplayName("Test register(Registration, UriComponentsBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.register(Registration, UriComponentsBuilder)"})
  void testRegister2() throws AssertionError {
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
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act and Assert
    FirstStep<ResponseEntity<Map<String, InstanceId>>> createResult =
        StepVerifier.create(
            instancesController.register(registration, UriComponentsBuilder.newInstance()));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Map<String, InstanceId>> responseEntity = r;
              Map<String, InstanceId> body = responseEntity.getBody();
              assertEquals(1, body.size());
              assertSame(ofResult, body.get("id"));
              HttpHeaders headers = responseEntity.getHeaders();
              assertEquals(1, headers.size());
              List<String> getResult = headers.get("Location");
              assertEquals(1, getResult.size());
              assertEquals("/instances/42", getResult.get(0));
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.CREATED, statusCode);
              assertEquals(201, responseEntity.getStatusCodeValue());
              assertTrue(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstancesController#register(Registration, UriComponentsBuilder)}.
   *
   * <p>Method under test: {@link InstancesController#register(Registration, UriComponentsBuilder)}
   */
  @Test
  @DisplayName("Test register(Registration, UriComponentsBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.register(Registration, UriComponentsBuilder)"})
  void testRegister3() throws AssertionError {
    // Arrange
    InstanceRegistry registry = mock(InstanceRegistry.class);
    String value = "42";
    InstanceId ofResult = InstanceId.of(value);
    Mono<InstanceId> justResult = Mono.just(ofResult);
    when(registry.register(Mockito.<Registration>any())).thenReturn(justResult);
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act and Assert
    FirstStep<ResponseEntity<Map<String, InstanceId>>> createResult =
        StepVerifier.create(
            instancesController.register(registration, UriComponentsBuilder.newInstance()));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Map<String, InstanceId>> responseEntity = r;
              Map<String, InstanceId> body = responseEntity.getBody();
              assertEquals(1, body.size());
              assertSame(ofResult, body.get("id"));
              HttpHeaders headers = responseEntity.getHeaders();
              assertEquals(1, headers.size());
              List<String> getResult = headers.get("Location");
              assertEquals(1, getResult.size());
              assertEquals("/instances/42", getResult.get(0));
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.CREATED, statusCode);
              assertEquals(201, responseEntity.getStatusCodeValue());
              assertTrue(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(registry).register(isA(Registration.class));
  }

  /**
   * Test {@link InstancesController#register(Registration, UriComponentsBuilder)}.
   *
   * <p>Method under test: {@link InstancesController#register(Registration, UriComponentsBuilder)}
   */
  @Test
  @DisplayName("Test register(Registration, UriComponentsBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.register(Registration, UriComponentsBuilder)"})
  void testRegister4() throws AssertionError {
    // Arrange
    InstanceRegistry registry = mock(InstanceRegistry.class);
    String value = "42";
    InstanceId ofResult = InstanceId.of(value);
    Mono<InstanceId> justResult = Mono.just(ofResult);
    when(registry.register(Mockito.<Registration>any())).thenReturn(justResult);
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("")
            .source("Source")
            .build();

    // Act and Assert
    FirstStep<ResponseEntity<Map<String, InstanceId>>> createResult =
        StepVerifier.create(
            instancesController.register(registration, UriComponentsBuilder.newInstance()));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Map<String, InstanceId>> responseEntity = r;
              Map<String, InstanceId> body = responseEntity.getBody();
              assertEquals(1, body.size());
              assertSame(ofResult, body.get("id"));
              HttpHeaders headers = responseEntity.getHeaders();
              assertEquals(1, headers.size());
              List<String> getResult = headers.get("Location");
              assertEquals(1, getResult.size());
              assertEquals("/instances/42", getResult.get(0));
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.CREATED, statusCode);
              assertEquals(201, responseEntity.getStatusCodeValue());
              assertTrue(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(registry).register(isA(Registration.class));
  }

  /**
   * Test {@link InstancesController#register(Registration, UriComponentsBuilder)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#register(Registration)} return
   *       just {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#register(Registration, UriComponentsBuilder)}
   */
  @Test
  @DisplayName(
      "Test register(Registration, UriComponentsBuilder); given InstanceRegistry register(Registration) return just InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.register(Registration, UriComponentsBuilder)"})
  void testRegister_givenInstanceRegistryRegisterReturnJustInstanceIdWithValueIs42()
      throws AssertionError {
    // Arrange
    String value = "42";
    InstanceId ofResult = InstanceId.of(value);
    Mono<InstanceId> justResult = Mono.just(ofResult);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act and Assert
    FirstStep<ResponseEntity<Map<String, InstanceId>>> createResult =
        StepVerifier.create(
            instancesController.register(registration, UriComponentsBuilder.newInstance()));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Map<String, InstanceId>> responseEntity = r;
              Map<String, InstanceId> body = responseEntity.getBody();
              assertEquals(1, body.size());
              assertSame(ofResult, body.get("id"));
              HttpHeaders headers = responseEntity.getHeaders();
              assertEquals(1, headers.size());
              List<String> getResult = headers.get("Location");
              assertEquals(1, getResult.size());
              assertEquals("/instances/42", getResult.get(0));
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.CREATED, statusCode);
              assertEquals(201, responseEntity.getStatusCodeValue());
              assertTrue(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(instanceRegistry).register(isA(Registration.class));
  }

  /**
   * Test {@link InstancesController#register(Registration, UriComponentsBuilder)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#register(Registration, UriComponentsBuilder)}
   */
  @Test
  @DisplayName("Test register(Registration, UriComponentsBuilder); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.register(Registration, UriComponentsBuilder)"})
  void testRegister_thenCallsFind() throws AssertionError {
    // Arrange
    InstanceEventStore eventStore = mock(InstanceEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);

    InstanceIdGenerator generator = mock(InstanceIdGenerator.class);
    when(generator.generateId(Mockito.<Registration>any())).thenReturn(InstanceId.of("42"));

    InstanceRegistry registry =
        new InstanceRegistry(repository, generator, mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act and Assert
    FirstStep<ResponseEntity<Map<String, InstanceId>>> createResult =
        StepVerifier.create(
            instancesController.register(registration, UriComponentsBuilder.newInstance()));
    createResult.expectError().verify();
    verify(eventStore).find(isA(InstanceId.class));
    verify(generator).generateId(isA(Registration.class));
  }

  /**
   * Test {@link InstancesController#register(Registration, UriComponentsBuilder)}.
   *
   * <ul>
   *   <li>Then calls {@link Registration#toBuilder()}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#register(Registration, UriComponentsBuilder)}
   */
  @Test
  @DisplayName("Test register(Registration, UriComponentsBuilder); then calls toBuilder()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.register(Registration, UriComponentsBuilder)"})
  void testRegister_thenCallsToBuilder() throws AssertionError {
    // Arrange
    String value = "42";
    InstanceId ofResult = InstanceId.of(value);
    Mono<InstanceId> justResult = Mono.just(ofResult);
    when(instanceRegistry.register(Mockito.<Registration>any())).thenReturn(justResult);

    Builder builder = mock(Builder.class);
    when(builder.build())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    Builder builder2 = mock(Builder.class);
    when(builder2.name(Mockito.<String>any())).thenReturn(Registration.builder());
    when(builder2.source(Mockito.<String>any())).thenReturn(builder);
    builder2.name("not blank");

    Registration registration = mock(Registration.class);
    when(registration.toBuilder()).thenReturn(builder2);

    // Act and Assert
    FirstStep<ResponseEntity<Map<String, InstanceId>>> createResult =
        StepVerifier.create(
            instancesController.register(registration, UriComponentsBuilder.newInstance()));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Map<String, InstanceId>> responseEntity = r;
              Map<String, InstanceId> body = responseEntity.getBody();
              assertEquals(1, body.size());
              assertSame(ofResult, body.get("id"));
              HttpHeaders headers = responseEntity.getHeaders();
              assertEquals(1, headers.size());
              List<String> getResult = headers.get("Location");
              assertEquals(1, getResult.size());
              assertEquals("/instances/42", getResult.get(0));
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.CREATED, statusCode);
              assertEquals(201, responseEntity.getStatusCodeValue());
              assertTrue(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(registration).toBuilder();
    verify(builder).build();
    verify(builder2).name("not blank");
    verify(builder2).source("http-api");
    verify(instanceRegistry).register(isA(Registration.class));
  }

  /**
   * Test {@link InstancesController#instances()}.
   *
   * <p>Method under test: {@link InstancesController#instances()}
   */
  @Test
  @DisplayName("Test instances()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances()"})
  void testInstances() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstancesController#instances()}.
   *
   * <p>Method under test: {@link InstancesController#instances()}
   */
  @Test
  @DisplayName("Test instances()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances()"})
  void testInstances2() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstancesController#instances()}.
   *
   * <p>Method under test: {@link InstancesController#instances()}
   */
  @Test
  @DisplayName("Test instances()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances()"})
  void testInstances3() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findAll()).thenReturn(fromIterableResult);
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances());
    createResult.expectComplete().verify();
    verify(repository).findAll();
  }

  /**
   * Test {@link InstancesController#instances(String)} with {@code String}.
   *
   * <p>Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  @DisplayName("Test instances(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances(String)"})
  void testInstancesWithString() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Name");
  }

  /**
   * Test {@link InstancesController#instances(String)} with {@code String}.
   *
   * <p>Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  @DisplayName("Test instances(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances(String)"})
  void testInstancesWithString2() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstancesController#instances(String)} with {@code String}.
   *
   * <p>Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  @DisplayName("Test instances(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances(String)"})
  void testInstancesWithString3() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstancesController#instances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances(String)} return
   *       create.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  @DisplayName(
      "Test instances(String) with 'String'; given InstanceRegistry getInstances(String) return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances(String)"})
  void testInstancesWithString_givenInstanceRegistryGetInstancesReturnCreate() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(createResult);

    // Act
    instancesController.instances("Name");

    // Assert
    verify(instanceRegistry).getInstances("Name");
  }

  /**
   * Test {@link InstancesController#instances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link DirectProcessor#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  @DisplayName("Test instances(String) with 'String'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances(String)"})
  void testInstancesWithString_thenCallsFilter() throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances("Name");
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstancesController#instances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  @DisplayName("Test instances(String) with 'String'; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances(String)"})
  void testInstancesWithString_thenCallsFindAll() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances("Name"));
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstancesController#instances(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link EventsourcingInstanceRepository#findByName(String)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  @DisplayName("Test instances(String) with 'String'; then calls findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances(String)"})
  void testInstancesWithString_thenCallsFindByName() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(repository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances("Name"));
    createResult.expectComplete().verify();
    verify(repository).findByName("Name");
  }

  /**
   * Test {@link InstancesController#instances()}.
   *
   * <ul>
   *   <li>Given {@link EventsourcingInstanceRepository} {@link
   *       EventsourcingInstanceRepository#findAll()} return {@link DirectProcessor}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instances()}
   */
  @Test
  @DisplayName(
      "Test instances(); given EventsourcingInstanceRepository findAll() return DirectProcessor")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances()"})
  void testInstances_givenEventsourcingInstanceRepositoryFindAllReturnDirectProcessor()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);

    EventsourcingInstanceRepository repository = mock(EventsourcingInstanceRepository.class);
    when(repository.findAll()).thenReturn(directProcessor);
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances());
    createResult.expectComplete().verify();
    verify(repository).findAll();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstancesController#instances()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances()} return create.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instances()}
   */
  @Test
  @DisplayName("Test instances(); given InstanceRegistry getInstances() return create")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances()"})
  void testInstances_givenInstanceRegistryGetInstancesReturnCreate() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRegistry.getInstances()).thenReturn(createResult);

    // Act
    instancesController.instances();

    // Assert
    verify(instanceRegistry).getInstances();
  }

  /**
   * Test {@link InstancesController#instances()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances()} return {@link
   *       DirectProcessor}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instances()}
   */
  @Test
  @DisplayName("Test instances(); given InstanceRegistry getInstances() return DirectProcessor")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances()"})
  void testInstances_givenInstanceRegistryGetInstancesReturnDirectProcessor()
      throws AssertionError {
    // Arrange
    DirectProcessor<Instance> directProcessor = mock(DirectProcessor.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(directProcessor.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances()).thenReturn(directProcessor);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(directProcessor).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstancesController#instances()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstances()} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instances()}
   */
  @Test
  @DisplayName(
      "Test instances(); given InstanceRegistry getInstances() return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances()"})
  void testInstances_givenInstanceRegistryGetInstancesReturnFromIterableArrayList()
      throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances()).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
  }

  /**
   * Test {@link InstancesController#instances()}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instances()}
   */
  @Test
  @DisplayName("Test instances(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.instances()"})
  void testInstances_thenCallsFindAll() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.findAll()).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances());
    createResult.expectComplete().verify();
    verify(eventStore).findAll();
  }

  /**
   * Test {@link InstancesController#instance(String)}.
   *
   * <p>Method under test: {@link InstancesController#instance(String)}
   */
  @Test
  @DisplayName("Test instance(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.instance(String)"})
  void testInstance() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<ResponseEntity<Instance>> createResult =
        StepVerifier.create(instancesController.instance("42"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Instance> responseEntity = r;
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
   * Test {@link InstancesController#instance(String)}.
   *
   * <p>Method under test: {@link InstancesController#instance(String)}
   */
  @Test
  @DisplayName("Test instance(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.instance(String)"})
  void testInstance2() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<ResponseEntity<Instance>> createResult =
        StepVerifier.create(instancesController.instance("42"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Instance> responseEntity = r;
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
   * Test {@link InstancesController#instance(String)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#getInstance(InstanceId)} return
   *       just {@link Instance}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instance(String)}
   */
  @Test
  @DisplayName(
      "Test instance(String); given InstanceRegistry getInstance(InstanceId) return just Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.instance(String)"})
  void testInstance_givenInstanceRegistryGetInstanceReturnJustInstance() throws AssertionError {
    // Arrange
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(instanceRegistry.getInstance(Mockito.<InstanceId>any())).thenReturn(justResult);

    // Act and Assert
    FirstStep<ResponseEntity<Instance>> createResult =
        StepVerifier.create(instancesController.instance("42"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Instance> responseEntity = r;
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
    verify(instanceRegistry).getInstance(isA(InstanceId.class));
  }

  /**
   * Test {@link InstancesController#instance(String)}.
   *
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#filter(Predicate)} return just {@link Instance}.
   *   <li>When {@code 42}.
   *   <li>Then calls {@link Mono#filter(Predicate)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instance(String)}
   */
  @Test
  @DisplayName(
      "Test instance(String); given Mono filter(Predicate) return just Instance; when '42'; then calls filter(Predicate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.instance(String)"})
  void testInstance_givenMonoFilterReturnJustInstance_when42_thenCallsFilter()
      throws AssertionError {
    // Arrange
    Mono<Instance> mono = mock(Mono.class);
    Mono<Instance> justResult = Mono.just(mock(Instance.class));
    when(mono.filter(Mockito.<Predicate<Instance>>any())).thenReturn(justResult);
    when(instanceRegistry.getInstance(Mockito.<InstanceId>any())).thenReturn(mono);

    // Act and Assert
    FirstStep<ResponseEntity<Instance>> createResult =
        StepVerifier.create(instancesController.instance("42"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Instance> responseEntity = r;
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
    verify(instanceRegistry).getInstance(isA(InstanceId.class));
    verify(mono).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstancesController#instance(String)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#instance(String)}
   */
  @Test
  @DisplayName("Test instance(String); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.instance(String)"})
  void testInstance_thenCallsFind() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<ResponseEntity<Instance>> createResult =
        StepVerifier.create(instancesController.instance("42"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Instance> responseEntity = r;
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
    verify(eventStore).find(isA(InstanceId.class));
  }

  /**
   * Test {@link InstancesController#unregister(String)}.
   *
   * <p>Method under test: {@link InstancesController#unregister(String)}
   */
  @Test
  @DisplayName("Test unregister(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.unregister(String)"})
  void testUnregister() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(instancesController.unregister("42"));
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
   * Test {@link InstancesController#unregister(String)}.
   *
   * <p>Method under test: {@link InstancesController#unregister(String)}
   */
  @Test
  @DisplayName("Test unregister(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.unregister(String)"})
  void testUnregister2() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new SnapshottingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(instancesController.unregister("42"));
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
   * Test {@link InstancesController#unregister(String)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry} {@link InstanceRegistry#deregister(InstanceId)} return
   *       just {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#unregister(String)}
   */
  @Test
  @DisplayName(
      "Test unregister(String); given InstanceRegistry deregister(InstanceId) return just InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.unregister(String)"})
  void testUnregister_givenInstanceRegistryDeregisterReturnJustInstanceIdWithValueIs42()
      throws AssertionError {
    // Arrange
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.deregister(Mockito.<InstanceId>any())).thenReturn(justResult);

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(instancesController.unregister("42"));
    createResult
        .assertNext(
            r -> {
              ResponseEntity<Void> responseEntity = r;
              assertNull(responseEntity.getBody());
              assertTrue(responseEntity.getHeaders().isEmpty());
              HttpStatusCode statusCode = responseEntity.getStatusCode();
              assertTrue(statusCode instanceof HttpStatus);
              assertEquals(HttpStatus.NO_CONTENT, statusCode);
              assertEquals(204, responseEntity.getStatusCodeValue());
              assertFalse(responseEntity.hasBody());
              return;
            })
        .expectComplete()
        .verify();
    verify(instanceRegistry).deregister(isA(InstanceId.class));
  }

  /**
   * Test {@link InstancesController#unregister(String)}.
   *
   * <ul>
   *   <li>Then calls {@link HazelcastEventStore#find(InstanceId)}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#unregister(String)}
   */
  @Test
  @DisplayName("Test unregister(String); then calls find(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono InstancesController.unregister(String)"})
  void testUnregister_thenCallsFind() throws AssertionError {
    // Arrange
    HazelcastEventStore eventStore = mock(HazelcastEventStore.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(eventStore.find(Mockito.<InstanceId>any())).thenReturn(fromIterableResult);
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(eventStore);
    InstanceRegistry registry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<ResponseEntity<Void>> createResult =
        StepVerifier.create(instancesController.unregister("42"));
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
    verify(eventStore).find(isA(InstanceId.class));
  }

  /**
   * Test {@link InstancesController#events()}.
   *
   * <p>Method under test: {@link InstancesController#events()}
   */
  @Test
  @DisplayName("Test events()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.events()"})
  void testEvents() throws AssertionError {
    // Arrange
    InstanceRegistry registry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    InstancesController instancesController =
        new InstancesController(registry, new InMemoryEventStore());

    // Act and Assert
    FirstStep<InstanceEvent> createResult = StepVerifier.create(instancesController.events());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstancesController#events()}.
   *
   * <ul>
   *   <li>Given {@link InstanceRegistry}.
   *   <li>Then calls {@link InstanceEventStore#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link InstancesController#events()}
   */
  @Test
  @DisplayName("Test events(); given InstanceRegistry; then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Flux InstancesController.events()"})
  void testEvents_givenInstanceRegistry_thenCallsFindAll() throws AssertionError {
    // Arrange
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceEventStore.findAll()).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<InstanceEvent> createResult = StepVerifier.create(instancesController.events());
    createResult.expectComplete().verify();
    verify(instanceEventStore).findAll();
  }
}
