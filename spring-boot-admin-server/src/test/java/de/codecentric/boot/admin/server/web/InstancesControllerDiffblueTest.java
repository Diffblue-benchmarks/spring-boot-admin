package de.codecentric.boot.admin.server.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import java.util.ArrayList;
import java.util.function.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {InstancesController.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class InstancesControllerDiffblueTest {
  @MockBean
  private InstanceEventStore instanceEventStore;

  @MockBean
  private InstanceRegistry instanceRegistry;

  @Autowired
  private InstancesController instancesController;

  /**
   * Method under test: {@link InstancesController#instances()}
   */
  @Test
  public void testInstances() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances()).thenReturn(fromIterableResult);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
  }

  /**
   * Method under test: {@link InstancesController#instances()}
   */
  @Test
  public void testInstances2() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRegistry.getInstances()).thenReturn(createResult);

    // Act
    instancesController.instances();

    // Assert
    verify(instanceRegistry).getInstances();
  }

  /**
   * Method under test: {@link InstancesController#instances()}
   */
  @Test
  public void testInstances3() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances()).thenReturn(flux);

    // Act
    Flux<Instance> actualPublisher = instancesController.instances();

    // Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(flux).filter(isA(Predicate.class));
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  public void testInstances4() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(instancesController.instances("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Name"));
  }

  /**
   * Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  public void testInstances5() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(createResult);

    // Act
    instancesController.instances("Name");

    // Assert
    verify(instanceRegistry).getInstances(eq("Name"));
  }

  /**
   * Method under test: {@link InstancesController#instances(String)}
   */
  @Test
  public void testInstances6() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);

    // Act
    Flux<Instance> actualPublisher = instancesController.instances("Name");

    // Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Name"));
    verify(flux).filter(isA(Predicate.class));
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test: {@link InstancesController#unregister(String)}
   */
  @Test
  public void testUnregister() throws AssertionError {
    // Arrange
    Mono<InstanceId> justResult = Mono.just(InstanceId.of("42"));
    when(instanceRegistry.deregister(Mockito.<InstanceId>any())).thenReturn(justResult);

    // Act and Assert
    StepVerifier.FirstStep<ResponseEntity<Void>> createResult = StepVerifier
        .create(instancesController.unregister("42"));
    createResult.assertNext(r -> {
      ResponseEntity<Void> responseEntity = r;
      assertNull(responseEntity.getBody());
      assertTrue(responseEntity.getHeaders().isEmpty());
      HttpStatusCode statusCode = responseEntity.getStatusCode();
      assertTrue(statusCode instanceof HttpStatus);
      assertEquals(HttpStatus.NO_CONTENT, statusCode);
      assertEquals(204, responseEntity.getStatusCodeValue());
      assertFalse(responseEntity.hasBody());
      return;
    }).expectComplete().verify();
    verify(instanceRegistry).deregister(isA(InstanceId.class));
  }

  /**
   * Method under test: {@link InstancesController#events()}
   */
  @Test
  public void testEvents() throws AssertionError {
    // Arrange
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceEventStore.findAll()).thenReturn(fromIterableResult);

    // Act
    Flux<InstanceEvent> actualPublisher = instancesController.events();

    // Assert
    StepVerifier.FirstStep<InstanceEvent> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceEventStore).findAll();
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test:
   * {@link InstancesController#InstancesController(InstanceRegistry, InstanceEventStore)}
   */
  @Test
  public void testNewInstancesController() throws AssertionError {
    // Arrange
    InstanceRegistry registry = new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    StepVerifier.FirstStep<InstanceEvent> createResult = StepVerifier
        .create((new InstancesController(registry, new InMemoryEventStore())).events());
    createResult.expectComplete().verify();
  }
}
