package de.codecentric.boot.admin.server.notify.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;

public class InstanceIdNotificationFilterDiffblueTest {
  /**
   * Method under test:
   * {@link InstanceIdNotificationFilter#doFilter(InstanceEvent, Instance)}
   */
  @Test
  public void testDoFilter() {
    // Arrange
    InstanceId instanceId = InstanceId.of("42");
    InstanceIdNotificationFilter instanceIdNotificationFilter = new InstanceIdNotificationFilter(instanceId,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(instanceIdNotificationFilter.doFilter(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test:
   * {@link InstanceIdNotificationFilter#doFilter(InstanceEvent, Instance)}
   */
  @Test
  public void testDoFilter2() {
    // Arrange
    InstanceId instanceId = InstanceId.of("Value");
    InstanceIdNotificationFilter instanceIdNotificationFilter = new InstanceIdNotificationFilter(instanceId,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertFalse(instanceIdNotificationFilter.doFilter(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceIdNotificationFilter#toString()}
   *   <li>{@link InstanceIdNotificationFilter#getInstanceId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    InstanceId instanceId = InstanceId.of("42");
    InstanceIdNotificationFilter instanceIdNotificationFilter = new InstanceIdNotificationFilter(instanceId,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    String actualToStringResult = instanceIdNotificationFilter.toString();

    // Assert
    assertEquals("NotificationFilter [instanceId=42, expiry=1970-01-01T00:00:00Z]", actualToStringResult);
    assertSame(instanceId, instanceIdNotificationFilter.getInstanceId());
  }

  /**
   * Method under test:
   * {@link InstanceIdNotificationFilter#InstanceIdNotificationFilter(InstanceId, Instant)}
   */
  @Test
  public void testNewInstanceIdNotificationFilter() {
    // Arrange
    InstanceId instanceId = InstanceId.of("42");
    Instant expiry = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    InstanceIdNotificationFilter actualInstanceIdNotificationFilter = new InstanceIdNotificationFilter(instanceId,
        expiry);

    // Assert
    assertSame(instanceId, actualInstanceIdNotificationFilter.getInstanceId());
    Instant expectedExpiry = expiry.EPOCH;
    assertSame(expectedExpiry, actualInstanceIdNotificationFilter.getExpiry());
  }
}
