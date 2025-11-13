package de.codecentric.boot.admin.server.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Info;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceInfoChangedEventDiffblueTest {
  /**
   * Test {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId, long, Info)}.
   *
   * <ul>
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId,
   * long, Info)}
   */
  @Test
  @DisplayName(
      "Test new InstanceInfoChangedEvent(InstanceId, long, Info); then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceInfoChangedEvent.<init>(InstanceId, long, Info)"})
  void testNewInstanceInfoChangedEvent_thenReturnVersionIsOne() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Info info = Info.empty();

    // Act
    InstanceInfoChangedEvent actualInstanceInfoChangedEvent =
        new InstanceInfoChangedEvent(instance, 1L, info);

    // Assert
    assertEquals(1L, actualInstanceInfoChangedEvent.getVersion());
    Info info2 = actualInstanceInfoChangedEvent.getInfo();
    assertTrue(info2.getValues().isEmpty());
    assertEquals(InstanceInfoChangedEvent.TYPE, actualInstanceInfoChangedEvent.getType());
    assertSame(info, info2);
    assertSame(instance, actualInstanceInfoChangedEvent.getInstance());
  }

  /**
   * Test {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId, long, Instant,
   * Info)}.
   *
   * <ul>
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link InstanceInfoChangedEvent#InstanceInfoChangedEvent(InstanceId,
   * long, Instant, Info)}
   */
  @Test
  @DisplayName(
      "Test new InstanceInfoChangedEvent(InstanceId, long, Instant, Info); then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceInfoChangedEvent.<init>(InstanceId, long, Instant, Info)"})
  void testNewInstanceInfoChangedEvent_thenReturnVersionIsOne2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Info info = Info.empty();

    // Act
    InstanceInfoChangedEvent actualInstanceInfoChangedEvent =
        new InstanceInfoChangedEvent(instance, 1L, timestamp, info);

    // Assert
    assertEquals(1L, actualInstanceInfoChangedEvent.getVersion());
    Info info2 = actualInstanceInfoChangedEvent.getInfo();
    assertTrue(info2.getValues().isEmpty());
    assertEquals(InstanceInfoChangedEvent.TYPE, actualInstanceInfoChangedEvent.getType());
    assertSame(info, info2);
    assertSame(instance, actualInstanceInfoChangedEvent.getInstance());
    assertSame(Instant.EPOCH, actualInstanceInfoChangedEvent.getTimestamp());
  }
}
