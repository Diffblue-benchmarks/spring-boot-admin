package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Info;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InstanceInfoChangedEventDiffblueTest {
  /**
   * Test {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId, long, Info)}.
   * <ul>
   *   <li>Then return Version is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId, long, Info)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceInfoChangedEvent.<init>(InstanceId, long, Info)"})
  public void testNewInstanceInfoChangedEvent_thenReturnVersionIsOne() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Info info = Info.empty();

    // Act
    InstanceInfoChangedEvent actualInstanceInfoChangedEvent = new InstanceInfoChangedEvent(instance, 1L, info);

    // Assert
    assertEquals(1L, actualInstanceInfoChangedEvent.getVersion());
    Info info2 = actualInstanceInfoChangedEvent.getInfo();
    assertTrue(info2.getValues().isEmpty());
    assertEquals(InstanceInfoChangedEvent.TYPE, actualInstanceInfoChangedEvent.getType());
    assertSame(info, info2);
    assertSame(instance, actualInstanceInfoChangedEvent.getInstance());
  }

  /**
   * Test {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId, long, Instant, Info)}.
   * <ul>
   *   <li>Then return Version is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId, long, Instant, Info)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceInfoChangedEvent.<init>(InstanceId, long, Instant, Info)"})
  public void testNewInstanceInfoChangedEvent_thenReturnVersionIsOne2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Info info = Info.empty();

    // Act
    InstanceInfoChangedEvent actualInstanceInfoChangedEvent = new InstanceInfoChangedEvent(instance, 1L, timestamp,
        info);

    // Assert
    assertEquals(1L, actualInstanceInfoChangedEvent.getVersion());
    Info info2 = actualInstanceInfoChangedEvent.getInfo();
    assertTrue(info2.getValues().isEmpty());
    assertEquals(InstanceInfoChangedEvent.TYPE, actualInstanceInfoChangedEvent.getType());
    assertSame(info, info2);
    assertSame(instance, actualInstanceInfoChangedEvent.getInstance());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualInstanceInfoChangedEvent.getTimestamp());
  }

  /**
   * Test {@link InstanceInfoChangedEvent#equals(Object)}, and {@link InstanceInfoChangedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceInfoChangedEvent#equals(Object)}
   *   <li>{@link InstanceInfoChangedEvent#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceInfoChangedEvent.equals(Object)", "int InstanceInfoChangedEvent.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    InstanceInfoChangedEvent instanceInfoChangedEvent = new InstanceInfoChangedEvent(instance, 1L, Info.empty());

    // Act and Assert
    assertEquals(instanceInfoChangedEvent, instanceInfoChangedEvent);
    int expectedHashCodeResult = instanceInfoChangedEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceInfoChangedEvent.hashCode());
  }

  /**
   * Test {@link InstanceInfoChangedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceInfoChangedEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceInfoChangedEvent.equals(Object)", "int InstanceInfoChangedEvent.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    InstanceInfoChangedEvent instanceInfoChangedEvent = new InstanceInfoChangedEvent(instance, 1L, Info.empty());
    InstanceId instance2 = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(instanceInfoChangedEvent, new InstanceInfoChangedEvent(instance2, 1L, Info.empty()));
  }

  /**
   * Test {@link InstanceInfoChangedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceInfoChangedEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceInfoChangedEvent.equals(Object)", "int InstanceInfoChangedEvent.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceInfoChangedEvent(instance, 1L, Info.empty()), null);
  }

  /**
   * Test {@link InstanceInfoChangedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceInfoChangedEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceInfoChangedEvent.equals(Object)", "int InstanceInfoChangedEvent.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceInfoChangedEvent(instance, 1L, Info.empty()),
        "Different type to InstanceInfoChangedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceInfoChangedEvent#toString()}
   *   <li>{@link InstanceInfoChangedEvent#getInfo()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Info InstanceInfoChangedEvent.getInfo()", "java.lang.String InstanceInfoChangedEvent.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Info info = Info.empty();
    InstanceInfoChangedEvent instanceInfoChangedEvent = new InstanceInfoChangedEvent(instance, 1L, info);

    // Act
    instanceInfoChangedEvent.toString();
    Info actualInfo = instanceInfoChangedEvent.getInfo();

    // Assert
    assertTrue(actualInfo.getValues().isEmpty());
    assertSame(info, actualInfo);
  }
}
