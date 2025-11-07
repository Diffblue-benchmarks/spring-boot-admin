package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InstanceDeregisteredEventDiffblueTest {
  /**
   * Test {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId, long)}.
   * <ul>
   *   <li>Then return Version is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId, long)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceDeregisteredEvent.<init>(InstanceId, long)"})
  public void testNewInstanceDeregisteredEvent_thenReturnVersionIsOne() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act
    InstanceDeregisteredEvent actualInstanceDeregisteredEvent = new InstanceDeregisteredEvent(instance, 1L);

    // Assert
    assertEquals(1L, actualInstanceDeregisteredEvent.getVersion());
    assertEquals(InstanceDeregisteredEvent.TYPE, actualInstanceDeregisteredEvent.getType());
    assertSame(instance, actualInstanceDeregisteredEvent.getInstance());
  }

  /**
   * Test {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId, long, Instant)}.
   * <ul>
   *   <li>Then return Version is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId, long, Instant)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceDeregisteredEvent.<init>(InstanceId, long, Instant)"})
  public void testNewInstanceDeregisteredEvent_thenReturnVersionIsOne2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    InstanceDeregisteredEvent actualInstanceDeregisteredEvent = new InstanceDeregisteredEvent(instance, 1L, timestamp);

    // Assert
    assertEquals(1L, actualInstanceDeregisteredEvent.getVersion());
    assertEquals(InstanceDeregisteredEvent.TYPE, actualInstanceDeregisteredEvent.getType());
    assertSame(instance, actualInstanceDeregisteredEvent.getInstance());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualInstanceDeregisteredEvent.getTimestamp());
  }

  /**
   * Test {@link InstanceDeregisteredEvent#equals(Object)}, and {@link InstanceDeregisteredEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceDeregisteredEvent#equals(Object)}
   *   <li>{@link InstanceDeregisteredEvent#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceDeregisteredEvent.equals(Object)", "int InstanceDeregisteredEvent.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertEquals(instanceDeregisteredEvent, instanceDeregisteredEvent);
    int expectedHashCodeResult = instanceDeregisteredEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceDeregisteredEvent.hashCode());
  }

  /**
   * Test {@link InstanceDeregisteredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDeregisteredEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceDeregisteredEvent.equals(Object)", "int InstanceDeregisteredEvent.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
  }

  /**
   * Test {@link InstanceDeregisteredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDeregisteredEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceDeregisteredEvent.equals(Object)", "int InstanceDeregisteredEvent.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);
  }

  /**
   * Test {@link InstanceDeregisteredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceDeregisteredEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceDeregisteredEvent.equals(Object)", "int InstanceDeregisteredEvent.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L),
        "Different type to InstanceDeregisteredEvent");
  }
}
