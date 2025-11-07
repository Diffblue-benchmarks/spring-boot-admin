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

public class InstanceRegistrationUpdatedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceRegistrationUpdatedEvent#equals(Object)}
   *   <li>{@link InstanceRegistrationUpdatedEvent#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceRegistrationUpdatedEvent instanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(
        InstanceId.of("42"), 1L, null);

    // Act and Assert
    assertEquals(instanceRegistrationUpdatedEvent, instanceRegistrationUpdatedEvent);
    int expectedHashCodeResult = instanceRegistrationUpdatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceRegistrationUpdatedEvent.hashCode());
  }

  /**
   * Method under test: {@link InstanceRegistrationUpdatedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceRegistrationUpdatedEvent instanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(
        InstanceId.of("42"), 1L, null);

    // Act and Assert
    assertNotEquals(instanceRegistrationUpdatedEvent,
        new InstanceRegistrationUpdatedEvent(InstanceId.of("42"), 1L, null));
  }

  /**
   * Method under test: {@link InstanceRegistrationUpdatedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceRegistrationUpdatedEvent(InstanceId.of("42"), 1L, null),
        mock(InstanceDeregisteredEvent.class));
  }

  /**
   * Method under test: {@link InstanceRegistrationUpdatedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceRegistrationUpdatedEvent(InstanceId.of("42"), 1L, null), null);
  }

  /**
   * Method under test: {@link InstanceRegistrationUpdatedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceRegistrationUpdatedEvent(InstanceId.of("42"), 1L, null),
        "Different type to InstanceRegistrationUpdatedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceRegistrationUpdatedEvent#toString()}
   *   <li>{@link InstanceRegistrationUpdatedEvent#getRegistration()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    InstanceRegistrationUpdatedEvent instanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(
        InstanceId.of("42"), 1L, null);

    // Act
    instanceRegistrationUpdatedEvent.toString();

    // Assert
    assertNull(instanceRegistrationUpdatedEvent.getRegistration());
  }

  /**
   * Method under test:
   * {@link InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long, Registration)}
   */
  @Test
  public void testNewInstanceRegistrationUpdatedEvent() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act
    InstanceRegistrationUpdatedEvent actualInstanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(
        instance, 1L, null);

    // Assert
    assertNull(actualInstanceRegistrationUpdatedEvent.getRegistration());
    assertEquals(1L, actualInstanceRegistrationUpdatedEvent.getVersion());
    assertEquals(InstanceRegistrationUpdatedEvent.TYPE, actualInstanceRegistrationUpdatedEvent.getType());
    assertSame(instance, actualInstanceRegistrationUpdatedEvent.getInstance());
  }

  /**
   * Method under test:
   * {@link InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long, Instant, Registration)}
   */
  @Test
  public void testNewInstanceRegistrationUpdatedEvent2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    InstanceRegistrationUpdatedEvent actualInstanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(
        instance, 1L, timestamp, null);

    // Assert
    assertNull(actualInstanceRegistrationUpdatedEvent.getRegistration());
    assertEquals(1L, actualInstanceRegistrationUpdatedEvent.getVersion());
    assertEquals(InstanceRegistrationUpdatedEvent.TYPE, actualInstanceRegistrationUpdatedEvent.getType());
    assertSame(instance, actualInstanceRegistrationUpdatedEvent.getInstance());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualInstanceRegistrationUpdatedEvent.getTimestamp());
  }
}
