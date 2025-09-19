package de.codecentric.boot.admin.server.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceEndpointsDetectedEventDiffblueTest {
  /**
   * Test {@link InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long,
   * Endpoints)}.
   *
   * <ul>
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long, Endpoints)}
   */
  @Test
  @DisplayName(
      "Test new InstanceEndpointsDetectedEvent(InstanceId, long, Endpoints); then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InstanceEndpointsDetectedEvent.<init>(InstanceId, long, Endpoints)"})
  void testNewInstanceEndpointsDetectedEvent_thenReturnVersionIsOne() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Endpoints endpoints = Endpoints.empty();

    // Act
    InstanceEndpointsDetectedEvent actualInstanceEndpointsDetectedEvent =
        new InstanceEndpointsDetectedEvent(instance, 1L, endpoints);

    // Assert
    assertEquals(1L, actualInstanceEndpointsDetectedEvent.getVersion());
    assertEquals(
        InstanceEndpointsDetectedEvent.TYPE, actualInstanceEndpointsDetectedEvent.getType());
    assertSame(endpoints, actualInstanceEndpointsDetectedEvent.getEndpoints());
    assertSame(instance, actualInstanceEndpointsDetectedEvent.getInstance());
  }

  /**
   * Test {@link InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long,
   * Instant, Endpoints)}.
   *
   * <ul>
   *   <li>Then return Version is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long, Instant,
   * Endpoints)}
   */
  @Test
  @DisplayName(
      "Test new InstanceEndpointsDetectedEvent(InstanceId, long, Instant, Endpoints); then return Version is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InstanceEndpointsDetectedEvent.<init>(InstanceId, long, Instant, Endpoints)"
  })
  void testNewInstanceEndpointsDetectedEvent_thenReturnVersionIsOne2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Endpoints endpoints = Endpoints.empty();

    // Act
    InstanceEndpointsDetectedEvent actualInstanceEndpointsDetectedEvent =
        new InstanceEndpointsDetectedEvent(instance, 1L, timestamp, endpoints);

    // Assert
    assertEquals(1L, actualInstanceEndpointsDetectedEvent.getVersion());
    assertEquals(
        InstanceEndpointsDetectedEvent.TYPE, actualInstanceEndpointsDetectedEvent.getType());
    assertSame(endpoints, actualInstanceEndpointsDetectedEvent.getEndpoints());
    assertSame(instance, actualInstanceEndpointsDetectedEvent.getInstance());
    assertSame(Instant.EPOCH, actualInstanceEndpointsDetectedEvent.getTimestamp());
  }
}
