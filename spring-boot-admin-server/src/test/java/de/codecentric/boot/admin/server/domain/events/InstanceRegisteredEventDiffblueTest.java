package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;

public class InstanceRegisteredEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceRegisteredEvent#equals(Object)}
   *   <li>{@link InstanceRegisteredEvent#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceRegisteredEvent instanceRegisteredEvent = new InstanceRegisteredEvent(InstanceId.of("42"), 1L, null);

    // Act and Assert
    assertEquals(instanceRegisteredEvent, instanceRegisteredEvent);
    int expectedHashCodeResult = instanceRegisteredEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceRegisteredEvent.hashCode());
  }

  /**
   * Method under test: {@link InstanceRegisteredEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceRegisteredEvent instanceRegisteredEvent = new InstanceRegisteredEvent(InstanceId.of("42"), 1L, null);

    // Act and Assert
    assertNotEquals(instanceRegisteredEvent, new InstanceRegisteredEvent(InstanceId.of("42"), 1L, null));
  }

  /**
   * Method under test: {@link InstanceRegisteredEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceRegisteredEvent(InstanceId.of("42"), 1L, null), mock(InstanceDeregisteredEvent.class));
  }

  /**
   * Method under test: {@link InstanceRegisteredEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceRegisteredEvent(InstanceId.of("42"), 1L, null), null);
  }

  /**
   * Method under test: {@link InstanceRegisteredEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceRegisteredEvent(InstanceId.of("42"), 1L, null),
        "Different type to InstanceRegisteredEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceRegisteredEvent#toString()}
   *   <li>{@link InstanceRegisteredEvent#getRegistration()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    InstanceRegisteredEvent instanceRegisteredEvent = new InstanceRegisteredEvent(InstanceId.of("42"), 1L, null);

    // Act
    instanceRegisteredEvent.toString();

    // Assert
    assertNull(instanceRegisteredEvent.getRegistration());
  }

  /**
   * Method under test:
   * {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long, Registration)}
   */
  @Test
  public void testNewInstanceRegisteredEvent() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act
    InstanceRegisteredEvent actualInstanceRegisteredEvent = new InstanceRegisteredEvent(instance, 1L, null);

    // Assert
    assertNull(actualInstanceRegisteredEvent.getRegistration());
    assertEquals(1L, actualInstanceRegisteredEvent.getVersion());
    assertEquals(InstanceRegisteredEvent.TYPE, actualInstanceRegisteredEvent.getType());
    assertSame(instance, actualInstanceRegisteredEvent.getInstance());
  }

  /**
   * Method under test:
   * {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long, Instant, Registration)}
   */
  @Test
  public void testNewInstanceRegisteredEvent2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    InstanceRegisteredEvent actualInstanceRegisteredEvent = new InstanceRegisteredEvent(instance, 1L, timestamp, null);

    // Assert
    assertNull(actualInstanceRegisteredEvent.getRegistration());
    assertEquals(1L, actualInstanceRegisteredEvent.getVersion());
    assertEquals(InstanceRegisteredEvent.TYPE, actualInstanceRegisteredEvent.getType());
    assertSame(instance, actualInstanceRegisteredEvent.getInstance());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualInstanceRegisteredEvent.getTimestamp());
  }
}
