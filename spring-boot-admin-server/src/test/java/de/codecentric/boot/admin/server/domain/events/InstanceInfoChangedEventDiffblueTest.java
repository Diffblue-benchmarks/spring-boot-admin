package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.values.Info;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;

public class InstanceInfoChangedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceInfoChangedEvent#equals(Object)}
   *   <li>{@link InstanceInfoChangedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link InstanceInfoChangedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    InstanceInfoChangedEvent instanceInfoChangedEvent = new InstanceInfoChangedEvent(instance, 1L, Info.empty());
    InstanceId instance2 = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(instanceInfoChangedEvent, new InstanceInfoChangedEvent(instance2, 1L, Info.empty()));
  }

  /**
   * Method under test: {@link InstanceInfoChangedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceInfoChangedEvent(instance, 1L, Info.empty()), mock(InstanceDeregisteredEvent.class));
  }

  /**
   * Method under test: {@link InstanceInfoChangedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceInfoChangedEvent(instance, 1L, Info.empty()), null);
  }

  /**
   * Method under test: {@link InstanceInfoChangedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceInfoChangedEvent(instance, 1L, Info.empty()),
        "Different type to InstanceInfoChangedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceInfoChangedEvent#toString()}
   *   <li>{@link InstanceInfoChangedEvent#getInfo()}
   * </ul>
   */
  @Test
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

  /**
   * Method under test:
   * {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId, long, Info)}
   */
  @Test
  public void testNewInstanceInfoChangedEvent() {
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
   * Method under test:
   * {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId, long, Instant, Info)}
   */
  @Test
  public void testNewInstanceInfoChangedEvent2() {
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
}
