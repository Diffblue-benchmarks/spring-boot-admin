package de.codecentric.boot.admin.server.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceStatusChangedEventDiffblueTest {
  /**
   * Test {@link InstanceStatusChangedEvent#InstanceStatusChangedEvent(InstanceId, long,
   * StatusInfo)}.
   *
   * <ul>
   *   <li>When {@link InstanceId}.
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link InstanceStatusChangedEvent#InstanceStatusChangedEvent(InstanceId,
   * long, StatusInfo)}
   */
  @Test
  @DisplayName(
      "Test new InstanceStatusChangedEvent(InstanceId, long, StatusInfo); when InstanceId; then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceStatusChangedEvent.<init>(InstanceId, long, StatusInfo)"})
  void testNewInstanceStatusChangedEvent_whenInstanceId_thenReturnVersionIsOne() {
    // Arrange
    InstanceId instance = mock(InstanceId.class);
    StatusInfo statusInfo = mock(StatusInfo.class);

    // Act
    InstanceStatusChangedEvent actualInstanceStatusChangedEvent =
        new InstanceStatusChangedEvent(instance, 1L, statusInfo);

    // Assert
    assertEquals(1L, actualInstanceStatusChangedEvent.getVersion());
    assertEquals(InstanceStatusChangedEvent.TYPE, actualInstanceStatusChangedEvent.getType());
    assertSame(instance, actualInstanceStatusChangedEvent.getInstance());
    assertSame(statusInfo, actualInstanceStatusChangedEvent.getStatusInfo());
  }

  /**
   * Test {@link InstanceStatusChangedEvent#InstanceStatusChangedEvent(InstanceId, long, Instant,
   * StatusInfo)}.
   *
   * <ul>
   *   <li>When {@link InstanceId}.
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link InstanceStatusChangedEvent#InstanceStatusChangedEvent(InstanceId,
   * long, Instant, StatusInfo)}
   */
  @Test
  @DisplayName(
      "Test new InstanceStatusChangedEvent(InstanceId, long, Instant, StatusInfo); when InstanceId; then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceStatusChangedEvent.<init>(InstanceId, long, Instant, StatusInfo)"
  })
  void testNewInstanceStatusChangedEvent_whenInstanceId_thenReturnVersionIsOne2() {
    // Arrange
    InstanceId instance = mock(InstanceId.class);
    StatusInfo statusInfo = mock(StatusInfo.class);

    // Act
    InstanceStatusChangedEvent actualInstanceStatusChangedEvent =
        new InstanceStatusChangedEvent(
            instance,
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            statusInfo);

    // Assert
    assertEquals(1L, actualInstanceStatusChangedEvent.getVersion());
    assertEquals(InstanceStatusChangedEvent.TYPE, actualInstanceStatusChangedEvent.getType());
    assertSame(Instant.EPOCH, actualInstanceStatusChangedEvent.getTimestamp());
    assertSame(instance, actualInstanceStatusChangedEvent.getInstance());
    assertSame(statusInfo, actualInstanceStatusChangedEvent.getStatusInfo());
  }
}
