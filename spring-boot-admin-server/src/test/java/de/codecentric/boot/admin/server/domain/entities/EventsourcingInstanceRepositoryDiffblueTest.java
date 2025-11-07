package de.codecentric.boot.admin.server.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import java.util.function.BiFunction;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

public class EventsourcingInstanceRepositoryDiffblueTest {
  /**
   * Test {@link EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)}.
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventsourcingInstanceRepository.<init>(InstanceEventStore)"})
  public void testNewEventsourcingInstanceRepository() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create((new EventsourcingInstanceRepository(new InMemoryEventStore())).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#save(Instance)}.
   * <ul>
   *   <li>Given {@link EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with eventStore is {@link InMemoryEventStore#InMemoryEventStore()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#save(Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono EventsourcingInstanceRepository.save(Instance)"})
  public void testSave_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new InMemoryEventStore());
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(eventsourcingInstanceRepository.save(Instance.create(id)));
    createResult.assertNext(i -> {
      Instance instance = i;
      assertNull(instance.getBuildVersion());
      assertSame(id, instance.getId());
      assertTrue(instance.getUnsavedEvents().isEmpty());
      assertEquals(-1L, instance.getVersion());
      assertFalse(instance.isRegistered());
      return;
    }).expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#save(Instance)}.
   * <ul>
   *   <li>Given {@link SnapshottingInstanceRepository#SnapshottingInstanceRepository(InstanceEventStore)} with eventStore is {@link InMemoryEventStore#InMemoryEventStore()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#save(Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono EventsourcingInstanceRepository.save(Instance)"})
  public void testSave_givenSnapshottingInstanceRepositoryWithEventStoreIsInMemoryEventStore() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository = new SnapshottingInstanceRepository(
        new InMemoryEventStore());
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(snapshottingInstanceRepository.save(Instance.create(id)));
    createResult.assertNext(i -> {
      Instance instance = i;
      assertNull(instance.getBuildVersion());
      assertSame(id, instance.getId());
      assertTrue(instance.getUnsavedEvents().isEmpty());
      assertEquals(-1L, instance.getVersion());
      assertFalse(instance.isRegistered());
      return;
    }).expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#findAll()}.
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#findAll()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Flux EventsourcingInstanceRepository.findAll()"})
  public void testFindAll() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create((new EventsourcingInstanceRepository(new InMemoryEventStore())).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#findAll()}.
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#findAll()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Flux EventsourcingInstanceRepository.findAll()"})
  public void testFindAll2() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create((new SnapshottingInstanceRepository(new InMemoryEventStore())).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#find(InstanceId)}.
   * <ul>
   *   <li>Given {@link EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with eventStore is {@link InMemoryEventStore#InMemoryEventStore()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#find(InstanceId)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono EventsourcingInstanceRepository.find(InstanceId)"})
  public void testFind_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(eventsourcingInstanceRepository.find(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#find(InstanceId)}.
   * <ul>
   *   <li>Given {@link SnapshottingInstanceRepository#SnapshottingInstanceRepository(InstanceEventStore)} with eventStore is {@link InMemoryEventStore#InMemoryEventStore()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#find(InstanceId)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono EventsourcingInstanceRepository.find(InstanceId)"})
  public void testFind_givenSnapshottingInstanceRepositoryWithEventStoreIsInMemoryEventStore() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository = new SnapshottingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier.create(snapshottingInstanceRepository.find(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#findByName(String)}.
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#findByName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Flux EventsourcingInstanceRepository.findByName(String)"})
  public void testFindByName() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create((new EventsourcingInstanceRepository(new InMemoryEventStore())).findByName("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#findByName(String)}.
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#findByName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Flux EventsourcingInstanceRepository.findByName(String)"})
  public void testFindByName2() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create((new SnapshottingInstanceRepository(new InMemoryEventStore())).findByName("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}.
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono EventsourcingInstanceRepository.compute(InstanceId, BiFunction)"})
  public void testCompute() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create(eventsourcingInstanceRepository.compute(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}.
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono EventsourcingInstanceRepository.compute(InstanceId, BiFunction)"})
  public void testCompute2() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository = new SnapshottingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.compute(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId, BiFunction)}.
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId, BiFunction)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "reactor.core.publisher.Mono EventsourcingInstanceRepository.computeIfPresent(InstanceId, BiFunction)"})
  public void testComputeIfPresent() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository = new EventsourcingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create(eventsourcingInstanceRepository.computeIfPresent(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId, BiFunction)}.
   * <p>
   * Method under test: {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId, BiFunction)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "reactor.core.publisher.Mono EventsourcingInstanceRepository.computeIfPresent(InstanceId, BiFunction)"})
  public void testComputeIfPresent2() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository = new SnapshottingInstanceRepository(
        new InMemoryEventStore());

    // Act and Assert
    FirstStep<Instance> createResult = StepVerifier
        .create(snapshottingInstanceRepository.computeIfPresent(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectComplete().verify();
  }
}
