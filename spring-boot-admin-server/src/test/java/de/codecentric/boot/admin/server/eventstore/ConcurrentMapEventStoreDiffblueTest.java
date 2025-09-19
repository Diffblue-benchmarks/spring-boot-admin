package de.codecentric.boot.admin.server.eventstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InMemoryEventStore.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ConcurrentMapEventStoreDiffblueTest {
  @Autowired private ConcurrentMapEventStore concurrentMapEventStore;

  @MockitoBean private HazelcastEventStore hazelcastEventStore;

  /**
   * Test {@link ConcurrentMapEventStore#append(List)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore()}.
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#append(List)}
   */
  @Test
  @DisplayName("Test append(List); given InMemoryEventStore(); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono ConcurrentMapEventStore.append(List)"})
  void testAppend_givenInMemoryEventStore_whenArrayList() throws AssertionError {
    // Arrange
    InMemoryEventStore inMemoryEventStore = new InMemoryEventStore();

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(inMemoryEventStore.append(new ArrayList<>()));
    createResult.expectComplete().verify();
    FirstStep<InstanceEvent> createResult2 = StepVerifier.create(inMemoryEventStore.findAll());
    createResult2.expectComplete().verify();
  }

  /**
   * Test {@link ConcurrentMapEventStore#append(List)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#append(List)}
   */
  @Test
  @DisplayName("Test append(List); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono ConcurrentMapEventStore.append(List)"})
  void testAppend_thenThrowIllegalArgumentException() {
    // Arrange
    when(concurrentMapEventStore.append(Mockito.<List<InstanceEvent>>any()))
        .thenThrow(new IllegalArgumentException());

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> concurrentMapEventStore.append(events));
    verify(concurrentMapEventStore).append(isA(List.class));
  }

  /**
   * Test {@link ConcurrentMapEventStore#append(List)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#append(List)}
   */
  @Test
  @DisplayName("Test append(List); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono ConcurrentMapEventStore.append(List)"})
  void testAppend_thenThrowIllegalArgumentException2() {
    // Arrange
    when(concurrentMapEventStore.append(Mockito.<List<InstanceEvent>>any()))
        .thenThrow(new IllegalArgumentException());

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> concurrentMapEventStore.append(events));
    verify(concurrentMapEventStore).append(isA(List.class));
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName("Test doAppend(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), -1L));

    // Act and Assert
    assertThrows(OptimisticLockingException.class, () -> concurrentMapEventStore.doAppend(events));
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName("Test doAppend(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend2() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getVersion()).thenThrow(new IllegalArgumentException());
    when(instanceDeregisteredEvent.getInstance()).thenReturn(InstanceId.of("42"));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(instanceDeregisteredEvent);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> concurrentMapEventStore.doAppend(events));
    verify(instanceDeregisteredEvent, atLeast(1)).getInstance();
    verify(instanceDeregisteredEvent).getVersion();
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName("Test doAppend(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend3() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getVersion()).thenThrow(new IllegalArgumentException());
    when(instanceDeregisteredEvent.getInstance()).thenReturn(mock(InstanceId.class));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(instanceDeregisteredEvent);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> concurrentMapEventStore.doAppend(events));
    verify(instanceDeregisteredEvent, atLeast(1)).getInstance();
    verify(instanceDeregisteredEvent).getVersion();
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <ul>
   *   <li>Given {@link ConcurrentMapEventStore}.
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName(
      "Test doAppend(List); given ConcurrentMapEventStore; when ArrayList(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend_givenConcurrentMapEventStore_whenArrayList_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(concurrentMapEventStore.doAppend(new ArrayList<>()));
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       minus one.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName(
      "Test doAppend(List); given InMemoryEventStore(int) with maxLogSizePerAggregate is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsMinusOne()
      throws AssertionError {
    // Arrange
    InMemoryEventStore inMemoryEventStore = new InMemoryEventStore(-1);

    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getVersion()).thenReturn(0L);
    when(instanceDeregisteredEvent.getInstance()).thenReturn(mock(InstanceId.class));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(instanceDeregisteredEvent);

    // Act
    boolean actualDoAppendResult = inMemoryEventStore.doAppend(events);

    // Assert
    verify(instanceDeregisteredEvent, atLeast(1)).getInstance();
    verify(instanceDeregisteredEvent).getVersion();
    assertTrue(actualDoAppendResult);
    FirstStep<InstanceEvent> createResult = StepVerifier.create(inMemoryEventStore.findAll());
    createResult.assertNext(i -> {}).expectComplete().verify();
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName("Test doAppend(List); given InMemoryEventStore(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend_givenInMemoryEventStore_thenReturnTrue() throws AssertionError {
    // Arrange
    InMemoryEventStore inMemoryEventStore = new InMemoryEventStore();

    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getVersion()).thenReturn(0L);
    when(instanceDeregisteredEvent.getInstance()).thenReturn(mock(InstanceId.class));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(instanceDeregisteredEvent);

    // Act
    boolean actualDoAppendResult = inMemoryEventStore.doAppend(events);

    // Assert
    verify(instanceDeregisteredEvent, atLeast(1)).getInstance();
    verify(instanceDeregisteredEvent).getVersion();
    assertTrue(actualDoAppendResult);
    FirstStep<InstanceEvent> createResult = StepVerifier.create(inMemoryEventStore.findAll());
    createResult.assertNext(i -> {}).expectComplete().verify();
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceDeregisteredEvent} {@link InstanceDeregisteredEvent#getInstance()}
   *       return {@link InstanceId}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName(
      "Test doAppend(List); given InstanceDeregisteredEvent getInstance() return InstanceId")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend_givenInstanceDeregisteredEventGetInstanceReturnInstanceId() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getInstance()).thenReturn(mock(InstanceId.class));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    events.add(instanceDeregisteredEvent);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> concurrentMapEventStore.doAppend(events));
    verify(instanceDeregisteredEvent).getInstance();
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceDeregisteredEvent} {@link InstanceDeregisteredEvent#getInstance()}
   *       return {@link InstanceId} with {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName(
      "Test doAppend(List); given InstanceDeregisteredEvent getInstance() return InstanceId with 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend_givenInstanceDeregisteredEventGetInstanceReturnInstanceIdWithValue() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getInstance()).thenReturn(InstanceId.of("Value"));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    events.add(instanceDeregisteredEvent);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> concurrentMapEventStore.doAppend(events));
    verify(instanceDeregisteredEvent).getInstance();
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceDeregisteredEvent} {@link InstanceDeregisteredEvent#getVersion()}
   *       return minus one.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName("Test doAppend(List); given InstanceDeregisteredEvent getVersion() return minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend_givenInstanceDeregisteredEventGetVersionReturnMinusOne() {
    // Arrange
    InMemoryEventStore inMemoryEventStore = new InMemoryEventStore();

    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getVersion()).thenReturn(-1L);
    when(instanceDeregisteredEvent.getInstance()).thenReturn(mock(InstanceId.class));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(instanceDeregisteredEvent);

    // Act and Assert
    assertThrows(OptimisticLockingException.class, () -> inMemoryEventStore.doAppend(events));
    verify(instanceDeregisteredEvent, atLeast(1)).getInstance();
    verify(instanceDeregisteredEvent, atLeast(1)).getVersion();
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} {@link InstanceId#getValue()} return {@code 42}.
   *   <li>Then calls {@link InstanceId#getValue()}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName(
      "Test doAppend(List); given InstanceId getValue() return '42'; then calls getValue()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend_givenInstanceIdGetValueReturn42_thenCallsGetValue() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getInstance()).thenReturn(InstanceId.of("Value"));

    InstanceId instance = mock(InstanceId.class);
    when(instance.getValue()).thenReturn("42");
    InstanceDeregisteredEvent instanceDeregisteredEvent2 =
        new InstanceDeregisteredEvent(instance, 1L);

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(instanceDeregisteredEvent2);
    events.add(instanceDeregisteredEvent);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> concurrentMapEventStore.doAppend(events));
    verify(instanceDeregisteredEvent).getInstance();
    verify(instance).getValue();
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} {@link InstanceId#getValue()} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName("Test doAppend(List); given InstanceId getValue() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend_givenInstanceIdGetValueThrowIllegalArgumentException() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent.getInstance()).thenReturn(InstanceId.of("Value"));

    InstanceId instance = mock(InstanceId.class);
    when(instance.getValue()).thenThrow(new IllegalArgumentException());
    InstanceDeregisteredEvent instanceDeregisteredEvent2 =
        new InstanceDeregisteredEvent(instance, 1L);

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(instanceDeregisteredEvent2);
    events.add(instanceDeregisteredEvent);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> concurrentMapEventStore.doAppend(events));
    verify(instanceDeregisteredEvent).getInstance();
    verify(instance).getValue();
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then throw {@link OptimisticLockingException}.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @DisplayName(
      "Test doAppend(List); given InstanceId with value is '42'; then throw OptimisticLockingException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  void testDoAppend_givenInstanceIdWithValueIs42_thenThrowOptimisticLockingException() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertThrows(OptimisticLockingException.class, () -> concurrentMapEventStore.doAppend(events));
  }

  /**
   * Test {@link ConcurrentMapEventStore#getLastVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#getLastVersion(List)}
   */
  @Test
  @DisplayName("Test getLastVersion(List); given InstanceId with value is '42'; then return one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long ConcurrentMapEventStore.getLastVersion(List)"})
  void testGetLastVersion_givenInstanceIdWithValueIs42_thenReturnOne() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertEquals(1L, ConcurrentMapEventStore.getLastVersion(events));
  }

  /**
   * Test {@link ConcurrentMapEventStore#getLastVersion(List)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#getLastVersion(List)}
   */
  @Test
  @DisplayName("Test getLastVersion(List); given InstanceId with value is '42'; then return one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long ConcurrentMapEventStore.getLastVersion(List)"})
  void testGetLastVersion_givenInstanceIdWithValueIs42_thenReturnOne2() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertEquals(1L, ConcurrentMapEventStore.getLastVersion(events));
  }

  /**
   * Test {@link ConcurrentMapEventStore#getLastVersion(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link ConcurrentMapEventStore#getLastVersion(List)}
   */
  @Test
  @DisplayName("Test getLastVersion(List); when ArrayList(); then return minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long ConcurrentMapEventStore.getLastVersion(List)"})
  void testGetLastVersion_whenArrayList_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1L, ConcurrentMapEventStore.getLastVersion(new ArrayList<>()));
  }
}
