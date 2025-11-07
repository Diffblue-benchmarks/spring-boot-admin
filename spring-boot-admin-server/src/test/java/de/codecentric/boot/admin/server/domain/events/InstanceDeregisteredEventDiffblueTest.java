package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;

public class InstanceDeregisteredEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceDeregisteredEvent#equals(Object)}
   *   <li>{@link InstanceDeregisteredEvent#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertEquals(instanceDeregisteredEvent, instanceDeregisteredEvent);
    int expectedHashCodeResult = instanceDeregisteredEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceDeregisteredEvent.hashCode());
  }

  /**
   * Method under test: {@link InstanceDeregisteredEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
  }

  /**
   * Method under test: {@link InstanceDeregisteredEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(InstanceEndpointsDetectedEvent.class));
  }

  /**
   * Method under test: {@link InstanceDeregisteredEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);
  }

  /**
   * Method under test: {@link InstanceDeregisteredEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L),
        "Different type to InstanceDeregisteredEvent");
  }

  /**
   * Method under test:
   * {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId, long)}
   */
  @Test
  public void testNewInstanceDeregisteredEvent() {
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
   * Method under test:
   * {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId, long, Instant)}
   */
  @Test
  public void testNewInstanceDeregisteredEvent2() {
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
}
