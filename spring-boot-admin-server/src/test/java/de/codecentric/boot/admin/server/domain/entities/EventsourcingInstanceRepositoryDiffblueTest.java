package de.codecentric.boot.admin.server.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class EventsourcingInstanceRepositoryDiffblueTest {
  /**
   * Test {@link
   * EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)}.
   *
   * <p>Method under test: {@link
   * EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)}
   */
  @Test
  @DisplayName("Test new EventsourcingInstanceRepository(InstanceEventStore)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventsourcingInstanceRepository.<init>(InstanceEventStore)"})
  void testNewEventsourcingInstanceRepository() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            new EventsourcingInstanceRepository(new InMemoryEventStore(3)).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#save(Instance)}.
   *
   * <ul>
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore#InMemoryEventStore(int)}.
   * </ul>
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#save(Instance)}
   */
  @Test
  @DisplayName(
      "Test save(Instance); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono EventsourcingInstanceRepository.save(Instance)"})
  void testSave_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore()
      throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(eventsourcingInstanceRepository.save(Instance.create(id)));
    createResult
        .assertNext(
            i -> {
              Instance instance = i;
              assertNull(instance.getBuildVersion());
              assertSame(id, instance.getId());
              assertTrue(instance.getUnsavedEvents().isEmpty());
              assertEquals(-1L, instance.getVersion());
              assertFalse(instance.isRegistered());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#save(Instance)}.
   *
   * <ul>
   *   <li>Given {@link
   *       SnapshottingInstanceRepository#SnapshottingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore#InMemoryEventStore(int)}.
   * </ul>
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#save(Instance)}
   */
  @Test
  @DisplayName(
      "Test save(Instance); given SnapshottingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono EventsourcingInstanceRepository.save(Instance)"})
  void testSave_givenSnapshottingInstanceRepositoryWithEventStoreIsInMemoryEventStore()
      throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));
    String value = "42";
    InstanceId id = InstanceId.of(value);

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(snapshottingInstanceRepository.save(Instance.create(id)));
    createResult
        .assertNext(
            i -> {
              Instance instance = i;
              assertNull(instance.getBuildVersion());
              assertSame(id, instance.getId());
              assertTrue(instance.getUnsavedEvents().isEmpty());
              assertEquals(-1L, instance.getVersion());
              assertFalse(instance.isRegistered());
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#findAll()}.
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#findAll()}
   */
  @Test
  @DisplayName("Test findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Flux EventsourcingInstanceRepository.findAll()"})
  void testFindAll() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            new EventsourcingInstanceRepository(new InMemoryEventStore(3)).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#find(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link
   *       EventsourcingInstanceRepository#EventsourcingInstanceRepository(InstanceEventStore)} with
   *       eventStore is {@link InMemoryEventStore#InMemoryEventStore(int)}.
   * </ul>
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#find(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test find(InstanceId); given EventsourcingInstanceRepository(InstanceEventStore) with eventStore is InMemoryEventStore(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono EventsourcingInstanceRepository.find(InstanceId)"
  })
  void testFind_givenEventsourcingInstanceRepositoryWithEventStoreIsInMemoryEventStore()
      throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(eventsourcingInstanceRepository.find(InstanceId.of("42")));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#findByName(String)}.
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#findByName(String)}
   */
  @Test
  @DisplayName("Test findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Flux EventsourcingInstanceRepository.findByName(String)"
  })
  void testFindByName() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            new EventsourcingInstanceRepository(new InMemoryEventStore(3)).findByName("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#findByName(String)}.
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#findByName(String)}
   */
  @Test
  @DisplayName("Test findByName(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Flux EventsourcingInstanceRepository.findByName(String)"
  })
  void testFindByName2() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            new SnapshottingInstanceRepository(new InMemoryEventStore(3)).findByName("Name"));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}.
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}
   */
  @Test
  @DisplayName("Test compute(InstanceId, BiFunction)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono EventsourcingInstanceRepository.compute(InstanceId, BiFunction)"
  })
  void testCompute() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            eventsourcingInstanceRepository.compute(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}.
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#compute(InstanceId, BiFunction)}
   */
  @Test
  @DisplayName("Test compute(InstanceId, BiFunction)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono EventsourcingInstanceRepository.compute(InstanceId, BiFunction)"
  })
  void testCompute2() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            snapshottingInstanceRepository.compute(InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId, BiFunction)}.
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId,
   * BiFunction)}
   */
  @Test
  @DisplayName("Test computeIfPresent(InstanceId, BiFunction)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono EventsourcingInstanceRepository.computeIfPresent(InstanceId, BiFunction)"
  })
  void testComputeIfPresent() throws AssertionError {
    // Arrange
    EventsourcingInstanceRepository eventsourcingInstanceRepository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            eventsourcingInstanceRepository.computeIfPresent(
                InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId, BiFunction)}.
   *
   * <p>Method under test: {@link EventsourcingInstanceRepository#computeIfPresent(InstanceId,
   * BiFunction)}
   */
  @Test
  @DisplayName("Test computeIfPresent(InstanceId, BiFunction)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono EventsourcingInstanceRepository.computeIfPresent(InstanceId, BiFunction)"
  })
  void testComputeIfPresent2() throws AssertionError {
    // Arrange
    SnapshottingInstanceRepository snapshottingInstanceRepository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(3));

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            snapshottingInstanceRepository.computeIfPresent(
                InstanceId.of("42"), mock(BiFunction.class)));
    createResult.expectComplete().verify();
  }
}
