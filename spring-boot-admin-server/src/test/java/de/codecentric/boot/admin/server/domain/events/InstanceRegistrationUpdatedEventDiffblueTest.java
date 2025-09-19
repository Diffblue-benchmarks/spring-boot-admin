package de.codecentric.boot.admin.server.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceRegistrationUpdatedEventDiffblueTest {
  /**
   * Test {@link InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long,
   * Registration)}.
   *
   * <ul>
   *   <li>When {@link InstanceId}.
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long,
   * Registration)}
   */
  @Test
  @DisplayName(
      "Test new InstanceRegistrationUpdatedEvent(InstanceId, long, Registration); when InstanceId; then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceRegistrationUpdatedEvent.<init>(InstanceId, long, Registration)"
  })
  void testNewInstanceRegistrationUpdatedEvent_whenInstanceId_thenReturnVersionIsOne() {
    // Arrange
    InstanceId instance = mock(InstanceId.class);
    Registration registration = mock(Registration.class);

    // Act
    InstanceRegistrationUpdatedEvent actualInstanceRegistrationUpdatedEvent =
        new InstanceRegistrationUpdatedEvent(instance, 1L, registration);

    // Assert
    assertEquals(1L, actualInstanceRegistrationUpdatedEvent.getVersion());
    assertEquals(
        InstanceRegistrationUpdatedEvent.TYPE, actualInstanceRegistrationUpdatedEvent.getType());
    assertSame(instance, actualInstanceRegistrationUpdatedEvent.getInstance());
    assertSame(registration, actualInstanceRegistrationUpdatedEvent.getRegistration());
  }

  /**
   * Test {@link InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long,
   * Instant, Registration)}.
   *
   * <ul>
   *   <li>When {@link InstanceId}.
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long, Instant,
   * Registration)}
   */
  @Test
  @DisplayName(
      "Test new InstanceRegistrationUpdatedEvent(InstanceId, long, Instant, Registration); when InstanceId; then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceRegistrationUpdatedEvent.<init>(InstanceId, long, Instant, Registration)"
  })
  void testNewInstanceRegistrationUpdatedEvent_whenInstanceId_thenReturnVersionIsOne2() {
    // Arrange
    InstanceId instance = mock(InstanceId.class);
    Registration registration = mock(Registration.class);

    // Act
    InstanceRegistrationUpdatedEvent actualInstanceRegistrationUpdatedEvent =
        new InstanceRegistrationUpdatedEvent(
            instance,
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            registration);

    // Assert
    assertEquals(1L, actualInstanceRegistrationUpdatedEvent.getVersion());
    assertEquals(
        InstanceRegistrationUpdatedEvent.TYPE, actualInstanceRegistrationUpdatedEvent.getType());
    assertSame(Instant.EPOCH, actualInstanceRegistrationUpdatedEvent.getTimestamp());
    assertSame(instance, actualInstanceRegistrationUpdatedEvent.getInstance());
    assertSame(registration, actualInstanceRegistrationUpdatedEvent.getRegistration());
  }
}
