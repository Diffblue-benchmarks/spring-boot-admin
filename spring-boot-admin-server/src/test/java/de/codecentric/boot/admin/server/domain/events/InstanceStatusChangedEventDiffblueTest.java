package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;

public class InstanceStatusChangedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceStatusChangedEvent#equals(Object)}
   *   <li>{@link InstanceStatusChangedEvent#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceStatusChangedEvent instanceStatusChangedEvent = new InstanceStatusChangedEvent(InstanceId.of("42"), 1L,
        null);

    // Act and Assert
    assertEquals(instanceStatusChangedEvent, instanceStatusChangedEvent);
    int expectedHashCodeResult = instanceStatusChangedEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceStatusChangedEvent.hashCode());
  }

  /**
   * Method under test: {@link InstanceStatusChangedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceStatusChangedEvent instanceStatusChangedEvent = new InstanceStatusChangedEvent(InstanceId.of("42"), 1L,
        null);

    // Act and Assert
    assertNotEquals(instanceStatusChangedEvent, new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, null));
  }

  /**
   * Method under test: {@link InstanceStatusChangedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, null),
        mock(InstanceDeregisteredEvent.class));
  }

  /**
   * Method under test: {@link InstanceStatusChangedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, null), null);
  }

  /**
   * Method under test: {@link InstanceStatusChangedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, null),
        "Different type to InstanceStatusChangedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceStatusChangedEvent#toString()}
   *   <li>{@link InstanceStatusChangedEvent#getStatusInfo()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    InstanceStatusChangedEvent instanceStatusChangedEvent = new InstanceStatusChangedEvent(InstanceId.of("42"), 1L,
        null);

    // Act
    instanceStatusChangedEvent.toString();

    // Assert
    assertNull(instanceStatusChangedEvent.getStatusInfo());
  }

  /**
   * Method under test:
   * {@link InstanceStatusChangedEvent#InstanceStatusChangedEvent(InstanceId, long, StatusInfo)}
   */
  @Test
  public void testNewInstanceStatusChangedEvent() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act
    InstanceStatusChangedEvent actualInstanceStatusChangedEvent = new InstanceStatusChangedEvent(instance, 1L, null);

    // Assert
    assertNull(actualInstanceStatusChangedEvent.getStatusInfo());
    assertEquals(1L, actualInstanceStatusChangedEvent.getVersion());
    assertEquals(InstanceStatusChangedEvent.TYPE, actualInstanceStatusChangedEvent.getType());
    assertSame(instance, actualInstanceStatusChangedEvent.getInstance());
  }

  /**
   * Method under test:
   * {@link InstanceStatusChangedEvent#InstanceStatusChangedEvent(InstanceId, long, Instant, StatusInfo)}
   */
  @Test
  public void testNewInstanceStatusChangedEvent2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    InstanceStatusChangedEvent actualInstanceStatusChangedEvent = new InstanceStatusChangedEvent(instance, 1L,
        timestamp, null);

    // Assert
    assertNull(actualInstanceStatusChangedEvent.getStatusInfo());
    assertEquals(1L, actualInstanceStatusChangedEvent.getVersion());
    assertEquals(InstanceStatusChangedEvent.TYPE, actualInstanceStatusChangedEvent.getType());
    assertSame(instance, actualInstanceStatusChangedEvent.getInstance());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualInstanceStatusChangedEvent.getTimestamp());
  }
}
