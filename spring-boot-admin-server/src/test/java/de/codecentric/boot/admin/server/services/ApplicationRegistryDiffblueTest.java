package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventPublisher;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {ApplicationRegistry.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class ApplicationRegistryDiffblueTest {
  @Autowired
  private ApplicationRegistry applicationRegistry;

  @MockBean
  private InstanceEventPublisher instanceEventPublisher;

  @MockBean
  private InstanceRegistry instanceRegistry;

  /**
   * Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  public void testGetApplications() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances()).thenReturn(fromIterableResult);

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
  }

  /**
   * Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  public void testGetApplications2() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRegistry.getInstances()).thenReturn(createResult);

    // Act
    applicationRegistry.getApplications();

    // Assert
    verify(instanceRegistry).getInstances();
  }

  /**
   * Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  public void testGetApplications3() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances()).thenReturn(flux);

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(flux).filter(isA(Predicate.class));
  }

  /**
   * Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  public void testGetApplications4() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<GroupedFlux<Object, Instance>> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.groupBy(Mockito.<Function<Instance, Object>>any())).thenReturn(fromIterableResult);
    Flux<Instance> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux);
    when(instanceRegistry.getInstances()).thenReturn(flux2);

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(applicationRegistry.getApplications());
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(flux2).filter(isA(Predicate.class));
    verify(flux).groupBy(isA(Function.class));
  }

  /**
   * Method under test: {@link ApplicationRegistry#getApplications()}
   */
  @Test
  public void testGetApplications5() throws AssertionError {
    // Arrange
    Flux<GroupedFlux<Object, Instance>> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<GroupedFlux<Object, Instance>, Publisher<Object>>>any(), anyInt()))
        .thenReturn(fromIterableResult);
    Flux<Instance> flux2 = mock(Flux.class);
    when(flux2.groupBy(Mockito.<Function<Instance, Object>>any())).thenReturn(flux);
    Flux<Instance> flux3 = mock(Flux.class);
    when(flux3.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux2);
    when(instanceRegistry.getInstances()).thenReturn(flux3);

    // Act
    Flux<Application> actualPublisher = applicationRegistry.getApplications();

    // Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances();
    verify(flux3).filter(isA(Predicate.class));
    verify(flux).flatMap(isA(Function.class), eq(2147483647));
    verify(flux2).groupBy(isA(Function.class));
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  public void testGetApplication() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Name"));
  }

  /**
   * Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  public void testGetApplication2() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(createResult);

    // Act
    applicationRegistry.getApplication("Name");

    // Assert
    verify(instanceRegistry).getInstances(eq("Name"));
  }

  /**
   * Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  public void testGetApplication3() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Name"));
    verify(flux).filter(isA(Predicate.class));
  }

  /**
   * Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  public void testGetApplication4() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Mono<List<Instance>> justResult = Mono.just(new ArrayList<>());
    when(flux.collectList()).thenReturn(justResult);
    Flux<Instance> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux2);

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Name"));
    verify(flux).collectList();
    verify(flux2).filter(isA(Predicate.class));
  }

  /**
   * Method under test: {@link ApplicationRegistry#getApplication(String)}
   */
  @Test
  public void testGetApplication5() throws AssertionError {
    // Arrange
    Mono<List<Instance>> mono = mock(Mono.class);
    Mono<Object> justResult = Mono.just("Data");
    when(mono.map(Mockito.<Function<List<Instance>, Object>>any())).thenReturn(justResult);
    Flux<Instance> flux = mock(Flux.class);
    when(flux.collectList()).thenReturn(mono);
    Flux<Instance> flux2 = mock(Flux.class);
    when(flux2.filter(Mockito.<Predicate<Instance>>any())).thenReturn(flux);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux2);

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(applicationRegistry.getApplication("Name"));
    createResult.expectError().verify();
    verify(instanceRegistry).getInstances(eq("Name"));
    verify(flux).collectList();
    verify(flux2).filter(isA(Predicate.class));
    verify(mono).map(isA(Function.class));
  }

  /**
   * Method under test: {@link ApplicationRegistry#deregister(String)}
   */
  @Test
  public void testDeregister() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(applicationRegistry.deregister("Name"));
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Name"));
  }

  /**
   * Method under test: {@link ApplicationRegistry#deregister(String)}
   */
  @Test
  public void testDeregister2() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.flatMap(Mockito.<Function<Instance, Publisher<Object>>>any())).thenReturn(fromIterableResult);
    when(instanceRegistry.getInstances(Mockito.<String>any())).thenReturn(flux);

    // Act
    Flux<InstanceId> actualPublisher = applicationRegistry.deregister("Name");

    // Assert
    StepVerifier.FirstStep<InstanceId> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceRegistry).getInstances(eq("Name"));
    verify(flux).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test: {@link ApplicationRegistry#toApplication(String, Flux)}
   */
  @Test
  public void testToApplication() throws AssertionError {
    // Arrange
    Flux<Instance> instances = Flux.fromIterable(new ArrayList<>());

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier
        .create(applicationRegistry.toApplication("Name", instances));
    createResult.assertNext(a -> {
      Application application = a;
      assertNull(application.getBuildVersion());
      assertTrue(application.getInstances().isEmpty());
      assertEquals("Name", application.getName());
      assertEquals("UNKNOWN", application.getStatus());
      Instant statusTimestamp = application.getStatusTimestamp();
      assertEquals(0L, statusTimestamp.getEpochSecond());
      assertEquals(0, statusTimestamp.getNano());
      return;
    }).expectComplete().verify();
  }

  /**
   * Method under test: {@link ApplicationRegistry#toApplication(String, Flux)}
   */
  @Test
  public void testToApplication2() throws AssertionError {
    // Arrange
    Flux<Instance> instances = mock(Flux.class);
    Mono<List<Instance>> justResult = Mono.just(new ArrayList<>());
    when(instances.collectList()).thenReturn(justResult);

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier
        .create(applicationRegistry.toApplication("Name", instances));
    createResult.assertNext(a -> {
      Application application = a;
      assertNull(application.getBuildVersion());
      assertTrue(application.getInstances().isEmpty());
      assertEquals("Name", application.getName());
      assertEquals("UNKNOWN", application.getStatus());
      Instant statusTimestamp = application.getStatusTimestamp();
      assertEquals(0L, statusTimestamp.getEpochSecond());
      assertEquals(0, statusTimestamp.getNano());
      return;
    }).expectComplete().verify();
    verify(instances).collectList();
  }

  /**
   * Method under test: {@link ApplicationRegistry#getBuildVersion(List)}
   */
  @Test
  public void testGetBuildVersion() {
    // Arrange, Act and Assert
    assertNull(applicationRegistry.getBuildVersion(new ArrayList<>()));
  }

  /**
   * Method under test: {@link ApplicationRegistry#getStatus(List)}
   */
  @Test
  public void testGetStatus() {
    // Arrange, Act and Assert
    List<Object> toListResult = applicationRegistry.getStatus(new ArrayList<>()).toList();
    assertEquals(2, toListResult.size());
    Object getResult = toListResult.get(1);
    assertTrue(getResult instanceof Instant);
    assertEquals("UNKNOWN", toListResult.get(0));
    assertEquals(0, ((Instant) getResult).getNano());
    assertEquals(0L, ((Instant) getResult).getEpochSecond());
  }

  /**
   * Method under test: {@link ApplicationRegistry#getMax(Instant, Instant)}
   */
  @Test
  public void testGetMax() {
    // Arrange
    Instant t1 = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    Instant actualMax = applicationRegistry.getMax(t1,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    assertSame(actualMax.EPOCH, actualMax);
  }

  /**
   * Method under test: {@link ApplicationRegistry#getMax(Instant, Instant)}
   */
  @Test
  public void testGetMax2() {
    // Arrange
    Instant t1 = LocalDate.ofYearDay(1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    Instant actualMax = applicationRegistry.getMax(t1,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    assertSame(actualMax.EPOCH, actualMax);
  }

  /**
   * Method under test:
   * {@link ApplicationRegistry#ApplicationRegistry(InstanceRegistry, InstanceEventPublisher)}
   */
  @Test
  public void testNewApplicationRegistry() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(
        (new ApplicationRegistry(new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)), null)).getApplications());
    createResult.expectComplete().verify();
  }
}
