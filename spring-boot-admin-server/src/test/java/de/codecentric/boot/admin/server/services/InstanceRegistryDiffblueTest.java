package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.util.ArrayList;
import java.util.function.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {InstanceRegistry.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class InstanceRegistryDiffblueTest {
  @MockBean
  private InstanceFilter instanceFilter;

  @MockBean
  private InstanceIdGenerator instanceIdGenerator;

  @Autowired
  private InstanceRegistry instanceRegistry;

  @MockBean
  private InstanceRepository instanceRepository;

  /**
   * Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  public void testGetInstances() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances());
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
  }

  /**
   * Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  public void testGetInstances2() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findAll()).thenReturn(createResult);

    // Act
    instanceRegistry.getInstances();

    // Assert
    verify(instanceRepository).findAll();
  }

  /**
   * Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  public void testGetInstances3() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(flux);

    // Act
    Flux<Instance> actualPublisher = instanceRegistry.getInstances();

    // Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
    verify(flux).filter(isA(Predicate.class));
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  public void testGetInstances4() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances("Name"));
    createResult.expectComplete().verify();
    verify(instanceRepository).findByName(eq("Name"));
  }

  /**
   * Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  public void testGetInstances5() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(createResult);

    // Act
    instanceRegistry.getInstances("Name");

    // Assert
    verify(instanceRepository).findByName(eq("Name"));
  }

  /**
   * Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  public void testGetInstances6() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(flux);

    // Act
    Flux<Instance> actualPublisher = instanceRegistry.getInstances("Name");

    // Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
    verify(instanceRepository).findByName(eq("Name"));
    verify(flux).filter(isA(Predicate.class));
    assertSame(fromIterableResult, actualPublisher);
  }

  /**
   * Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  public void testGetInstance() {
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
   * Method under test:
   * {@link InstanceRegistry#InstanceRegistry(InstanceRepository, InstanceIdGenerator, InstanceFilter)}
   */
  @Test
  public void testNewInstanceRegistry() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class))).getInstances());
    createResult.expectComplete().verify();
  }
}
