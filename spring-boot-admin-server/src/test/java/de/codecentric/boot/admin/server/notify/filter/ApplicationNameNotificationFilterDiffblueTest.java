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
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ApplicationNameNotificationFilterDiffblueTest {
  /**
   * Test {@link ApplicationNameNotificationFilter#ApplicationNameNotificationFilter(String,
   * Instant)}.
   *
   * <p>Method under test: {@link
   * ApplicationNameNotificationFilter#ApplicationNameNotificationFilter(String, Instant)}
   */
  @Test
  @DisplayName("Test new ApplicationNameNotificationFilter(String, Instant)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ApplicationNameNotificationFilter.<init>(String, Instant)"})
  void testNewApplicationNameNotificationFilter() {
    // Arrange and Act
    ApplicationNameNotificationFilter actualApplicationNameNotificationFilter =
        new ApplicationNameNotificationFilter(
            "Application Name",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    assertEquals("Application Name", actualApplicationNameNotificationFilter.getApplicationName());
    assertSame(Instant.EPOCH, actualApplicationNameNotificationFilter.getExpiry());
  }

  /**
   * Test {@link ApplicationNameNotificationFilter#doFilter(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationNameNotificationFilter#doFilter(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName("Test doFilter(InstanceEvent, Instance); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ApplicationNameNotificationFilter.doFilter(InstanceEvent, Instance)"})
  void testDoFilter_thenReturnFalse() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter =
        new ApplicationNameNotificationFilter(
            "Application Name",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    InstanceEvent event = mock(InstanceEvent.class);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act
    boolean actualDoFilterResult = applicationNameNotificationFilter.doFilter(event, instance);

    // Assert
    verify(instance).getRegistration();
    assertFalse(actualDoFilterResult);
  }

  /**
   * Test {@link ApplicationNameNotificationFilter#doFilter(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationNameNotificationFilter#doFilter(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName("Test doFilter(InstanceEvent, Instance); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ApplicationNameNotificationFilter.doFilter(InstanceEvent, Instance)"})
  void testDoFilter_thenReturnTrue() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter =
        new ApplicationNameNotificationFilter(
            "Name", LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    InstanceEvent event = mock(InstanceEvent.class);

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act
    boolean actualDoFilterResult = applicationNameNotificationFilter.doFilter(event, instance);

    // Assert
    verify(instance).getRegistration();
    assertTrue(actualDoFilterResult);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ApplicationNameNotificationFilter#toString()}
   *   <li>{@link ApplicationNameNotificationFilter#getApplicationName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ApplicationNameNotificationFilter.getApplicationName()",
    "String ApplicationNameNotificationFilter.toString()"
  })
  void testGettersAndSetters() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter =
        new ApplicationNameNotificationFilter(
            "Application Name",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    String actualToStringResult = applicationNameNotificationFilter.toString();

    // Assert
    assertEquals("Application Name", applicationNameNotificationFilter.getApplicationName());
    assertEquals(
        "NotificationFilter [applicationName=Application Name, expiry=1970-01-01T00:00:00Z]",
        actualToStringResult);
  }
}
