package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InstanceEndpointsDetectedEventDiffblueTest {
  /**
   * Test {@link InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long, Endpoints)}.
   * <ul>
   *   <li>Then return Version is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long, Endpoints)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceEndpointsDetectedEvent.<init>(InstanceId, long, Endpoints)"})
  public void testNewInstanceEndpointsDetectedEvent_thenReturnVersionIsOne() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Endpoints endpoints = Endpoints.empty();

    // Act
    InstanceEndpointsDetectedEvent actualInstanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance,
        1L, endpoints);

    // Assert
    assertEquals(1L, actualInstanceEndpointsDetectedEvent.getVersion());
    assertEquals(InstanceEndpointsDetectedEvent.TYPE, actualInstanceEndpointsDetectedEvent.getType());
    assertSame(endpoints, actualInstanceEndpointsDetectedEvent.getEndpoints());
    assertSame(instance, actualInstanceEndpointsDetectedEvent.getInstance());
  }

  /**
   * Test {@link InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long, Instant, Endpoints)}.
   * <ul>
   *   <li>Then return Version is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEndpointsDetectedEvent#InstanceEndpointsDetectedEvent(InstanceId, long, Instant, Endpoints)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceEndpointsDetectedEvent.<init>(InstanceId, long, Instant, Endpoints)"})
  public void testNewInstanceEndpointsDetectedEvent_thenReturnVersionIsOne2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Endpoints endpoints = Endpoints.empty();

    // Act
    InstanceEndpointsDetectedEvent actualInstanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance,
        1L, timestamp, endpoints);

    // Assert
    assertEquals(1L, actualInstanceEndpointsDetectedEvent.getVersion());
    assertEquals(InstanceEndpointsDetectedEvent.TYPE, actualInstanceEndpointsDetectedEvent.getType());
    assertSame(endpoints, actualInstanceEndpointsDetectedEvent.getEndpoints());
    assertSame(instance, actualInstanceEndpointsDetectedEvent.getInstance());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualInstanceEndpointsDetectedEvent.getTimestamp());
  }

  /**
   * Test {@link InstanceEndpointsDetectedEvent#equals(Object)}, and {@link InstanceEndpointsDetectedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceEndpointsDetectedEvent#equals(Object)}
   *   <li>{@link InstanceEndpointsDetectedEvent#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEndpointsDetectedEvent.equals(Object)",
      "int InstanceEndpointsDetectedEvent.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    InstanceEndpointsDetectedEvent instanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance, 1L,
        Endpoints.empty());

    // Act and Assert
    assertEquals(instanceEndpointsDetectedEvent, instanceEndpointsDetectedEvent);
    int expectedHashCodeResult = instanceEndpointsDetectedEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceEndpointsDetectedEvent.hashCode());
  }

  /**
   * Test {@link InstanceEndpointsDetectedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEndpointsDetectedEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEndpointsDetectedEvent.equals(Object)",
      "int InstanceEndpointsDetectedEvent.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    InstanceEndpointsDetectedEvent instanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance, 1L,
        Endpoints.empty());
    InstanceId instance2 = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(instanceEndpointsDetectedEvent,
        new InstanceEndpointsDetectedEvent(instance2, 1L, Endpoints.empty()));
  }

  /**
   * Test {@link InstanceEndpointsDetectedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEndpointsDetectedEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEndpointsDetectedEvent.equals(Object)",
      "int InstanceEndpointsDetectedEvent.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceEndpointsDetectedEvent(instance, 1L, Endpoints.empty()), null);
  }

  /**
   * Test {@link InstanceEndpointsDetectedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEndpointsDetectedEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEndpointsDetectedEvent.equals(Object)",
      "int InstanceEndpointsDetectedEvent.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertNotEquals(new InstanceEndpointsDetectedEvent(instance, 1L, Endpoints.empty()),
        "Different type to InstanceEndpointsDetectedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceEndpointsDetectedEvent#toString()}
   *   <li>{@link InstanceEndpointsDetectedEvent#getEndpoints()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Endpoints InstanceEndpointsDetectedEvent.getEndpoints()",
      "java.lang.String InstanceEndpointsDetectedEvent.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Endpoints endpoints = Endpoints.empty();
    InstanceEndpointsDetectedEvent instanceEndpointsDetectedEvent = new InstanceEndpointsDetectedEvent(instance, 1L,
        endpoints);

    // Act
    instanceEndpointsDetectedEvent.toString();

    // Assert
    assertSame(endpoints, instanceEndpointsDetectedEvent.getEndpoints());
  }
}
