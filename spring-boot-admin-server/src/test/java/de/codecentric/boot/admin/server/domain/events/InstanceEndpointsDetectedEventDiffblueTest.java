package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;

public class InstanceEndpointsDetectedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceEndpointsDetectedEvent#equals(Object)}
   *   <li>{@link InstanceEndpointsDetectedEvent#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    InstanceEndpointsDetectedEvent instanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance, 1L,
        Endpoints.empty());

    // Act and Assert
    assertEquals(instanceEndpointsDetectedEvent, instanceEndpointsDetectedEvent);
    int expectedHashCodeResult = instanceEndpointsDetectedEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceEndpointsDetectedEvent.hashCode());
  }

  /**
   * Method under test: {@link InstanceEndpointsDetectedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    InstanceEndpointsDetectedEvent instanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance, 1L,
        Endpoints.empty());
    InstanceId instance2 = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(instanceEndpointsDetectedEvent,
        new InstanceEndpointsDetectedEvent(instance2, 1L, Endpoints.empty()));
  }

  /**
   * Method under test: {@link InstanceEndpointsDetectedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceEndpointsDetectedEvent(instance, 1L, Endpoints.empty()),
        mock(InstanceDeregisteredEvent.class));
  }

  /**
   * Method under test: {@link InstanceEndpointsDetectedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceEndpointsDetectedEvent(instance, 1L, Endpoints.empty()), null);
  }

  /**
   * Method under test: {@link InstanceEndpointsDetectedEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceEndpointsDetectedEvent(instance, 1L, Endpoints.empty()),
        "Different type to InstanceEndpointsDetectedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceEndpointsDetectedEvent#toString()}
   *   <li>{@link InstanceEndpointsDetectedEvent#getEndpoints()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Endpoints endpoints = Endpoints.empty();
    InstanceEndpointsDetectedEvent instanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance, 1L,
        endpoints);

    // Act
    instanceEndpointsDetectedEvent.toString();

    // Assert
    assertSame(endpoints, instanceEndpointsDetectedEvent.getEndpoints());
  }

  /**
   * Method under test:
   * {@link InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long, Endpoints)}
   */
  @Test
  public void testNewInstanceEndpointsDetectedEvent() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Endpoints endpoints = Endpoints.empty();

    // Act
    InstanceEndpointsDetectedEvent actualInstanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance,
        1L, endpoints);

    // Assert
    assertEquals(1L, actualInstanceEndpointsDetectedEvent.getVersion());
    assertEquals(InstanceEndpointsDetectedEvent.TYPE, actualInstanceEndpointsDetectedEvent.getType());
    assertSame(endpoints, actualInstanceEndpointsDetectedEvent.getEndpoints());
    assertSame(instance, actualInstanceEndpointsDetectedEvent.getInstance());
  }

  /**
   * Method under test:
   * {@link InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long, Instant, Endpoints)}
   */
  @Test
  public void testNewInstanceEndpointsDetectedEvent2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Endpoints endpoints = Endpoints.empty();

    // Act
    InstanceEndpointsDetectedEvent actualInstanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance,
        1L, timestamp, endpoints);

    // Assert
    assertEquals(1L, actualInstanceEndpointsDetectedEvent.getVersion());
    assertEquals(InstanceEndpointsDetectedEvent.TYPE, actualInstanceEndpointsDetectedEvent.getType());
    assertSame(endpoints, actualInstanceEndpointsDetectedEvent.getEndpoints());
    assertSame(instance, actualInstanceEndpointsDetectedEvent.getInstance());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualInstanceEndpointsDetectedEvent.getTimestamp());
  }
}
