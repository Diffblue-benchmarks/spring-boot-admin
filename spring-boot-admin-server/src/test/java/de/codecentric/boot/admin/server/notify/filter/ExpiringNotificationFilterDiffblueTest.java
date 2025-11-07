package de.codecentric.boot.admin.server.notify.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;

public class ExpiringNotificationFilterDiffblueTest {
  /**
   * Method under test:
   * {@link ExpiringNotificationFilter#filter(InstanceEvent, Instance)}
   */
  @Test
  public void testFilter() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter = new ApplicationNameNotificationFilter(
        "Application Name", LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertFalse(applicationNameNotificationFilter.filter(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test: {@link ExpiringNotificationFilter#getExpiry()}
   */
  @Test
  public void testGetExpiry() {
    // Arrange and Act
    Instant actualExpiry = (new ApplicationNameNotificationFilter("Application Name",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())).getExpiry();

    // Assert
    assertSame(actualExpiry.EPOCH, actualExpiry);
  }
}
