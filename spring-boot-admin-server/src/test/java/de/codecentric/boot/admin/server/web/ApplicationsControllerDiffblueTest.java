package de.codecentric.boot.admin.server.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {ApplicationsController.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class ApplicationsControllerDiffblueTest {
  @MockBean
  private ApplicationEventPublisher applicationEventPublisher;

  @MockBean
  private ApplicationRegistry applicationRegistry;

  @Autowired
  private ApplicationsController applicationsController;

  /**
   * Method under test: {@link ApplicationsController#applications()}
   */
  @Test
  public void testApplications() throws AssertionError {
    // Arrange
    Flux<Application> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(applicationRegistry.getApplications()).thenReturn(fromIterableResult);

    // Act
    Flux<Application> actualPublisher = applicationsController.applications();

    // Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(applicationRegistry).getApplications();
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test:
   * {@link ApplicationsController#ApplicationsController(ApplicationRegistry, ApplicationEventPublisher)}
   */
  @Test
  public void testNewApplicationsController() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier
        .create((new ApplicationsController(
            new ApplicationRegistry(new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
                mock(InstanceIdGenerator.class), mock(InstanceFilter.class)), null),
            mock(ApplicationEventPublisher.class))).applications());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link ApplicationsController#refreshApplications()}
   */
  @Test
  public void testRefreshApplications() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);
    doNothing().when(publisher).publishEvent(Mockito.<ApplicationEvent>any());

    // Act
    (new ApplicationsController(
        new ApplicationRegistry(new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)), null),
        publisher)).refreshApplications();

    // Assert that nothing has changed
    verify(publisher).publishEvent(isA(ApplicationEvent.class));
  }

  /**
   * Method under test: {@link ApplicationsController#applicationsStream()}
   */
  @Test
  public void testApplicationsStream() throws AssertionError {
    // Arrange
    Flux<Object> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.mergeWith(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<Application> flux2 = mock(Flux.class);
    when(flux2.map(Mockito.<Function<Application, Object>>any())).thenReturn(flux);
    when(applicationRegistry.getApplicationStream()).thenReturn(flux2);

    // Act
    Flux<ServerSentEvent<Application>> actualPublisher = applicationsController.applicationsStream();

    // Assert
    StepVerifier.FirstStep<ServerSentEvent<Application>> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(applicationRegistry).getApplicationStream();
    verify(flux2).map(isA(Function.class));
    verify(flux).mergeWith(isA(Publisher.class));
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test: {@link ApplicationsController#unregister(String)}
   */
  @Test
  public void testUnregister() throws AssertionError {
    // Arrange
    Flux<InstanceId> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(applicationRegistry.deregister(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    StepVerifier.FirstStep<ResponseEntity<Void>> createResult = StepVerifier
        .create(applicationsController.unregister("Name"));
    createResult.assertNext(r -> {
      ResponseEntity<Void> responseEntity = r;
      assertNull(responseEntity.getBody());
      assertTrue(responseEntity.getHeaders().isEmpty());
      HttpStatusCode statusCode = responseEntity.getStatusCode();
      assertTrue(statusCode instanceof HttpStatus);
      assertEquals(HttpStatus.NOT_FOUND, statusCode);
      assertEquals(404, responseEntity.getStatusCodeValue());
      assertFalse(responseEntity.hasBody());
      return;
    }).expectComplete().verify();
    verify(applicationRegistry).deregister(eq("Name"));
  }

  /**
   * Method under test: {@link ApplicationsController#unregister(String)}
   */
  @Test
  public void testUnregister2() throws AssertionError {
    // Arrange
    Flux<InstanceId> flux = mock(Flux.class);
    Mono<List<InstanceId>> justResult = Mono.just(new ArrayList<>());
    when(flux.collectList()).thenReturn(justResult);
    when(applicationRegistry.deregister(Mockito.<String>any())).thenReturn(flux);

    // Act and Assert
    StepVerifier.FirstStep<ResponseEntity<Void>> createResult = StepVerifier
        .create(applicationsController.unregister("Name"));
    createResult.assertNext(r -> {
      ResponseEntity<Void> responseEntity = r;
      assertNull(responseEntity.getBody());
      assertTrue(responseEntity.getHeaders().isEmpty());
      HttpStatusCode statusCode = responseEntity.getStatusCode();
      assertTrue(statusCode instanceof HttpStatus);
      assertEquals(HttpStatus.NOT_FOUND, statusCode);
      assertEquals(404, responseEntity.getStatusCodeValue());
      assertFalse(responseEntity.hasBody());
      return;
    }).expectComplete().verify();
    verify(applicationRegistry).deregister(eq("Name"));
    verify(flux).collectList();
  }
}
