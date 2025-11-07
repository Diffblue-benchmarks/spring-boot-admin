package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.util.ArrayList;
import java.util.function.Predicate;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
import reactor.test.StepVerifier.FirstStep;

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
   * Test {@link InstanceRegistry#InstanceRegistry(InstanceRepository, InstanceIdGenerator, InstanceFilter)}.
   * <p>
   * Method under test: {@link InstanceRegistry#InstanceRegistry(InstanceRepository, InstanceIdGenerator, InstanceFilter)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceRegistry.<init>(InstanceRepository, InstanceIdGenerator, InstanceFilter)"})
  public void testNewInstanceRegistry() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create((new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class))).getInstances());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   * <p>
   * Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  public void testGetInstancesWithString() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances("Name"));
    createResult.expectComplete().verify();
    verify(instanceRepository).findByName(eq("Name"));
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findByName(String)} return create.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  public void testGetInstancesWithString_givenInstanceRepositoryFindByNameReturnCreate() {
    // Arrange
    DirectProcessor<Instance> createResult = DirectProcessor.create();
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(createResult);

    // Act
    instanceRegistry.getInstances("Name");

    // Assert
    verify(instanceRepository).findByName(eq("Name"));
  }

  /**
   * Test {@link InstanceRegistry#getInstances(String)} with {@code String}.
   * <ul>
   *   <li>Then calls {@link Flux#filter(Predicate)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistry#getInstances(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances(String)"})
  public void testGetInstancesWithString_thenCallsFilter() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findByName(Mockito.<String>any())).thenReturn(flux);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances("Name"));
    createResult.expectComplete().verify();
    verify(instanceRepository).findByName(eq("Name"));
    verify(flux).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   * <ul>
   *   <li>Given {@link Flux} {@link Flux#filter(Predicate)} return fromIterable {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link Flux#filter(Predicate)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  public void testGetInstances_givenFluxFilterReturnFromIterableArrayList_thenCallsFilter() throws AssertionError {
    // Arrange
    Flux<Instance> flux = mock(Flux.class);
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(flux.filter(Mockito.<Predicate<Instance>>any())).thenReturn(fromIterableResult);
    when(instanceRepository.findAll()).thenReturn(flux);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances());
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
    verify(flux).filter(isA(Predicate.class));
  }

  /**
   * Test {@link InstanceRegistry#getInstances()}.
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return create.</li>
   *   <li>Then calls {@link InstanceRepository#findAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  public void testGetInstances_givenInstanceRepositoryFindAllReturnCreate_thenCallsFindAll() {
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
   * <ul>
   *   <li>Given {@link InstanceRepository} {@link InstanceRepository#findAll()} return fromIterable {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistry#getInstances()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Flux InstanceRegistry.getInstances()"})
  public void testGetInstances_givenInstanceRepositoryFindAllReturnFromIterableArrayList() throws AssertionError {
    // Arrange
    Flux<Instance> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(instanceRepository.findAll()).thenReturn(fromIterableResult);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(instanceRegistry.getInstances());
    createResult.expectComplete().verify();
    verify(instanceRepository).findAll();
  }

  /**
   * Test {@link InstanceRegistry#getInstance(InstanceId)}.
   * <ul>
   *   <li>Given {@link Mono} {@link Mono#filter(Predicate)} return {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistry#getInstance(InstanceId)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono InstanceRegistry.getInstance(InstanceId)"})
  public void testGetInstance_givenMonoFilterReturnNull_thenReturnNull() {
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
}
