package de.codecentric.boot.admin.server.eventstore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {InMemoryEventStore.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConcurrentMapEventStoreDiffblueTest {
  @Autowired
  private ConcurrentMapEventStore concurrentMapEventStore;

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.</li>
   *   <li>Then throw {@link OptimisticLockingException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  public void testDoAppend_givenInstanceIdWithValueIs42_thenThrowOptimisticLockingException() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertThrows(OptimisticLockingException.class, () -> concurrentMapEventStore.doAppend(events));
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   * <ul>
   *   <li>Given {@link InstanceId} with {@code Value}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  public void testDoAppend_givenInstanceIdWithValue_thenThrowIllegalArgumentException() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("Value"), 1L));
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> concurrentMapEventStore.doAppend(events));
  }

  /**
   * Test {@link ConcurrentMapEventStore#doAppend(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConcurrentMapEventStore#doAppend(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ConcurrentMapEventStore.doAppend(List)"})
  public void testDoAppend_whenArrayList_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(concurrentMapEventStore.doAppend(new ArrayList<>()));
  }

  /**
   * Test {@link ConcurrentMapEventStore#getLastVersion(List)}.
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConcurrentMapEventStore#getLastVersion(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long ConcurrentMapEventStore.getLastVersion(List)"})
  public void testGetLastVersion_givenInstanceIdWithValueIs42_thenReturnOne() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertEquals(1L, ConcurrentMapEventStore.getLastVersion(events));
  }

  /**
   * Test {@link ConcurrentMapEventStore#getLastVersion(List)}.
   * <ul>
   *   <li>Given {@link InstanceId} with value is {@code 42}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConcurrentMapEventStore#getLastVersion(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long ConcurrentMapEventStore.getLastVersion(List)"})
  public void testGetLastVersion_givenInstanceIdWithValueIs42_thenReturnOne2() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertEquals(1L, ConcurrentMapEventStore.getLastVersion(events));
  }

  /**
   * Test {@link ConcurrentMapEventStore#getLastVersion(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConcurrentMapEventStore#getLastVersion(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long ConcurrentMapEventStore.getLastVersion(List)"})
  public void testGetLastVersion_whenArrayList_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1L, ConcurrentMapEventStore.getLastVersion(new ArrayList<>()));
  }
}
