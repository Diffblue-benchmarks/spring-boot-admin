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

class InstanceRegisteredEventDiffblueTest {
  /**
   * Test {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long, Registration)}.
   *
   * <ul>
   *   <li>When {@link InstanceId}.
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long,
   * Registration)}
   */
  @Test
  @DisplayName(
      "Test new InstanceRegisteredEvent(InstanceId, long, Registration); when InstanceId; then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceRegisteredEvent.<init>(InstanceId, long, Registration)"})
  void testNewInstanceRegisteredEvent_whenInstanceId_thenReturnVersionIsOne() {
    // Arrange
    InstanceId instance = mock(InstanceId.class);
    Registration registration = mock(Registration.class);

    // Act
    InstanceRegisteredEvent actualInstanceRegisteredEvent =
        new InstanceRegisteredEvent(instance, 1L, registration);

    // Assert
    assertEquals(1L, actualInstanceRegisteredEvent.getVersion());
    assertEquals(InstanceRegisteredEvent.TYPE, actualInstanceRegisteredEvent.getType());
    assertSame(instance, actualInstanceRegisteredEvent.getInstance());
    assertSame(registration, actualInstanceRegisteredEvent.getRegistration());
  }

  /**
   * Test {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long, Instant,
   * Registration)}.
   *
   * <ul>
   *   <li>When {@link InstanceId}.
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long,
   * Instant, Registration)}
   */
  @Test
  @DisplayName(
      "Test new InstanceRegisteredEvent(InstanceId, long, Instant, Registration); when InstanceId; then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceRegisteredEvent.<init>(InstanceId, long, Instant, Registration)"
  })
  void testNewInstanceRegisteredEvent_whenInstanceId_thenReturnVersionIsOne2() {
    // Arrange
    InstanceId instance = mock(InstanceId.class);
    Registration registration = mock(Registration.class);

    // Act
    InstanceRegisteredEvent actualInstanceRegisteredEvent =
        new InstanceRegisteredEvent(
            instance,
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            registration);

    // Assert
    assertEquals(1L, actualInstanceRegisteredEvent.getVersion());
    assertEquals(InstanceRegisteredEvent.TYPE, actualInstanceRegisteredEvent.getType());
    assertSame(Instant.EPOCH, actualInstanceRegisteredEvent.getTimestamp());
    assertSame(instance, actualInstanceRegisteredEvent.getInstance());
    assertSame(registration, actualInstanceRegisteredEvent.getRegistration());
  }
}
