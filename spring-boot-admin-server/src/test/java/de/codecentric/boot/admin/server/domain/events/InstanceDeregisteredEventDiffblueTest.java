package de.codecentric.boot.admin.server.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceDeregisteredEventDiffblueTest {
  /**
   * Test {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId, long)}.
   *
   * <ul>
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId,
   * long)}
   */
  @Test
  @DisplayName("Test new InstanceDeregisteredEvent(InstanceId, long); then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDeregisteredEvent.<init>(InstanceId, long)"})
  void testNewInstanceDeregisteredEvent_thenReturnVersionIsOne() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act
    InstanceDeregisteredEvent actualInstanceDeregisteredEvent =
        new InstanceDeregisteredEvent(instance, 1L);

    // Assert
    assertEquals(1L, actualInstanceDeregisteredEvent.getVersion());
    assertEquals(InstanceDeregisteredEvent.TYPE, actualInstanceDeregisteredEvent.getType());
    assertSame(instance, actualInstanceDeregisteredEvent.getInstance());
  }

  /**
   * Test {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId, long, Instant)}.
   *
   * <ul>
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link InstanceDeregisteredEvent#InstanceDeregisteredEvent(InstanceId,
   * long, Instant)}
   */
  @Test
  @DisplayName(
      "Test new InstanceDeregisteredEvent(InstanceId, long, Instant); then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceDeregisteredEvent.<init>(InstanceId, long, Instant)"})
  void testNewInstanceDeregisteredEvent_thenReturnVersionIsOne2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act
    InstanceDeregisteredEvent actualInstanceDeregisteredEvent =
        new InstanceDeregisteredEvent(
            instance,
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    assertEquals(1L, actualInstanceDeregisteredEvent.getVersion());
    assertEquals(InstanceDeregisteredEvent.TYPE, actualInstanceDeregisteredEvent.getType());
    assertSame(instance, actualInstanceDeregisteredEvent.getInstance());
    assertSame(Instant.EPOCH, actualInstanceDeregisteredEvent.getTimestamp());
  }
}
