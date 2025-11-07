package de.codecentric.boot.admin.server.notify.filter;

import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExpiringNotificationFilterDiffblueTest {
  /**
   * Test {@link ExpiringNotificationFilter#getExpiry()}.
   * <p>
   * Method under test: {@link ExpiringNotificationFilter#getExpiry()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Instant ExpiringNotificationFilter.getExpiry()"})
  public void testGetExpiry() {
    // Arrange and Act
    Instant actualExpiry = (new ApplicationNameNotificationFilter("Application Name",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())).getExpiry();

    // Assert
    assertSame(actualExpiry.EPOCH, actualExpiry);
  }
}
