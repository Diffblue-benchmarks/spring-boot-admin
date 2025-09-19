package de.codecentric.boot.admin.server.notify.filter;

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
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExpiringNotificationFilterDiffblueTest {
  /**
   * Test {@link ExpiringNotificationFilter#isExpired()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExpiringNotificationFilter#isExpired()}
   */
  @Test
  @DisplayName("Test isExpired(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpiringNotificationFilter.isExpired()"})
  void testIsExpired_thenReturnFalse() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter =
        new ApplicationNameNotificationFilter("Application Name", null);

    // Act and Assert
    assertFalse(applicationNameNotificationFilter.isExpired());
  }

  /**
   * Test {@link ExpiringNotificationFilter#filter(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay atZone {@link
   *       ZoneOffset#UTC} toInstant.
   * </ul>
   *
   * <p>Method under test: {@link ExpiringNotificationFilter#filter(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test filter(InstanceEvent, Instance); given LocalDate with '1970' and one and one atStartOfDay atZone UTC toInstant")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpiringNotificationFilter.filter(InstanceEvent, Instance)"})
  void testFilter_givenLocalDateWith1970AndOneAndOneAtStartOfDayAtZoneUtcToInstant() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter =
        new ApplicationNameNotificationFilter(
            "Application Name",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertFalse(
        applicationNameNotificationFilter.filter(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
  }

  /**
   * Test {@link ExpiringNotificationFilter#filter(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExpiringNotificationFilter#filter(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test filter(InstanceEvent, Instance); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpiringNotificationFilter.filter(InstanceEvent, Instance)"})
  void testFilter_thenReturnFalse() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter =
        new ApplicationNameNotificationFilter("Application Name", null);
    InstanceDeregisteredEvent event = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

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
    boolean actualFilterResult = applicationNameNotificationFilter.filter(event, instance);

    // Assert
    verify(instance).getRegistration();
    assertFalse(actualFilterResult);
  }

  /**
   * Test {@link ExpiringNotificationFilter#filter(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExpiringNotificationFilter#filter(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test filter(InstanceEvent, Instance); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpiringNotificationFilter.filter(InstanceEvent, Instance)"})
  void testFilter_thenReturnTrue() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter =
        new ApplicationNameNotificationFilter("Name", null);
    InstanceDeregisteredEvent event = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

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
    boolean actualFilterResult = applicationNameNotificationFilter.filter(event, instance);

    // Assert
    verify(instance).getRegistration();
    assertTrue(actualFilterResult);
  }

  /**
   * Test {@link ExpiringNotificationFilter#getExpiry()}.
   *
   * <p>Method under test: {@link ExpiringNotificationFilter#getExpiry()}
   */
  @Test
  @DisplayName("Test getExpiry()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instant ExpiringNotificationFilter.getExpiry()"})
  void testGetExpiry() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter =
        new ApplicationNameNotificationFilter(
            "Application Name",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(Instant.EPOCH, applicationNameNotificationFilter.getExpiry());
  }
}
