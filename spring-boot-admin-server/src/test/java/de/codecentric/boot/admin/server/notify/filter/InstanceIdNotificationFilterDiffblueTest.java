package de.codecentric.boot.admin.server.notify.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceIdNotificationFilterDiffblueTest {
  /**
   * Test {@link InstanceIdNotificationFilter#InstanceIdNotificationFilter(InstanceId, Instant)}.
   *
   * <p>Method under test: {@link
   * InstanceIdNotificationFilter#InstanceIdNotificationFilter(InstanceId, Instant)}
   */
  @Test
  @DisplayName("Test new InstanceIdNotificationFilter(InstanceId, Instant)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceIdNotificationFilter.<init>(InstanceId, Instant)"})
  void testNewInstanceIdNotificationFilter() {
    // Arrange
    InstanceId instanceId = InstanceId.of("42");

    // Act
    InstanceIdNotificationFilter actualInstanceIdNotificationFilter =
        new InstanceIdNotificationFilter(
            instanceId, LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    assertSame(instanceId, actualInstanceIdNotificationFilter.getInstanceId());
    assertSame(Instant.EPOCH, actualInstanceIdNotificationFilter.getExpiry());
  }

  /**
   * Test {@link InstanceIdNotificationFilter#doFilter(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} with {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceIdNotificationFilter#doFilter(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doFilter(InstanceEvent, Instance); given InstanceId with 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceIdNotificationFilter.doFilter(InstanceEvent, Instance)"})
  void testDoFilter_givenInstanceIdWithValue() {
    // Arrange
    InstanceIdNotificationFilter instanceIdNotificationFilter =
        new InstanceIdNotificationFilter(
            InstanceId.of("Value"),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));

    // Act
    boolean actualDoFilterResult =
        instanceIdNotificationFilter.doFilter(event, mock(Instance.class));

    // Assert
    verify(event).getInstance();
    assertFalse(actualDoFilterResult);
  }

  /**
   * Test {@link InstanceIdNotificationFilter#doFilter(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link InstanceEvent} {@link InstanceEvent#getInstance()} return {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceIdNotificationFilter#doFilter(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test doFilter(InstanceEvent, Instance); given 'null'; when InstanceEvent getInstance() return 'null'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceIdNotificationFilter.doFilter(InstanceEvent, Instance)"})
  void testDoFilter_givenNull_whenInstanceEventGetInstanceReturnNull_thenReturnFalse() {
    // Arrange
    InstanceIdNotificationFilter instanceIdNotificationFilter =
        new InstanceIdNotificationFilter(
            InstanceId.of("42"),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenReturn(null);

    // Act
    boolean actualDoFilterResult =
        instanceIdNotificationFilter.doFilter(event, mock(Instance.class));

    // Assert
    verify(event).getInstance();
    assertFalse(actualDoFilterResult);
  }

  /**
   * Test {@link InstanceIdNotificationFilter#doFilter(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link InstanceIdNotificationFilter#doFilter(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test doFilter(InstanceEvent, Instance); when InstanceId with value is '42'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean InstanceIdNotificationFilter.doFilter(InstanceEvent, Instance)"})
  void testDoFilter_whenInstanceIdWithValueIs42_thenReturnTrue() {
    // Arrange
    InstanceIdNotificationFilter instanceIdNotificationFilter =
        new InstanceIdNotificationFilter(
            InstanceId.of("42"),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(
        instanceIdNotificationFilter.doFilter(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link InstanceIdNotificationFilter#toString()}
   *   <li>{@link InstanceIdNotificationFilter#getInstanceId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceId InstanceIdNotificationFilter.getInstanceId()",
    "String InstanceIdNotificationFilter.toString()"
  })
  void testGettersAndSetters() {
    // Arrange
    InstanceId instanceId = InstanceId.of("42");
    InstanceIdNotificationFilter instanceIdNotificationFilter =
        new InstanceIdNotificationFilter(
            instanceId, LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    String actualToStringResult = instanceIdNotificationFilter.toString();

    // Assert
    assertEquals(
        "NotificationFilter [instanceId=42, expiry=1970-01-01T00:00:00Z]", actualToStringResult);
    assertSame(instanceId, instanceIdNotificationFilter.getInstanceId());
  }
}
