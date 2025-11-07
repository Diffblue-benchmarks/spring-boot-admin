package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InstanceRegistrationUpdatedEventDiffblueTest {
  /**
   * Test {@link InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long, Registration)}.
   * <ul>
   *   <li>Then return Registration is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long, Registration)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceRegistrationUpdatedEvent.<init>(InstanceId, long, Registration)"})
  public void testNewInstanceRegistrationUpdatedEvent_thenReturnRegistrationIsNull() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act
    InstanceRegistrationUpdatedEvent actualInstanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(
        instance, 1L, null);

    // Assert
    assertNull(actualInstanceRegistrationUpdatedEvent.getRegistration());
    assertEquals(1L, actualInstanceRegistrationUpdatedEvent.getVersion());
    assertEquals(InstanceRegistrationUpdatedEvent.TYPE, actualInstanceRegistrationUpdatedEvent.getType());
    assertSame(instance, actualInstanceRegistrationUpdatedEvent.getInstance());
  }

  /**
   * Test {@link InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long, Instant, Registration)}.
   * <ul>
   *   <li>Then return Registration is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistrationUpdatedEvent#InstanceRegistrationUpdatedEvent(InstanceId, long, Instant, Registration)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceRegistrationUpdatedEvent.<init>(InstanceId, long, Instant, Registration)"})
  public void testNewInstanceRegistrationUpdatedEvent_thenReturnRegistrationIsNull2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    InstanceRegistrationUpdatedEvent actualInstanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(
        instance, 1L, timestamp, null);

    // Assert
    assertNull(actualInstanceRegistrationUpdatedEvent.getRegistration());
    assertEquals(1L, actualInstanceRegistrationUpdatedEvent.getVersion());
    assertEquals(InstanceRegistrationUpdatedEvent.TYPE, actualInstanceRegistrationUpdatedEvent.getType());
    assertSame(instance, actualInstanceRegistrationUpdatedEvent.getInstance());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualInstanceRegistrationUpdatedEvent.getTimestamp());
  }

  /**
   * Test {@link InstanceRegistrationUpdatedEvent#equals(Object)}, and {@link InstanceRegistrationUpdatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceRegistrationUpdatedEvent#equals(Object)}
   *   <li>{@link InstanceRegistrationUpdatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceRegistrationUpdatedEvent.equals(Object)",
      "int InstanceRegistrationUpdatedEvent.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Registration registration = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    InstanceRegistrationUpdatedEvent instanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(instance,
        1L, registration);

    // Act and Assert
    assertEquals(instanceRegistrationUpdatedEvent, instanceRegistrationUpdatedEvent);
    int expectedHashCodeResult = instanceRegistrationUpdatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceRegistrationUpdatedEvent.hashCode());
  }

  /**
   * Test {@link InstanceRegistrationUpdatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistrationUpdatedEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceRegistrationUpdatedEvent.equals(Object)",
      "int InstanceRegistrationUpdatedEvent.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Registration registration = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    InstanceRegistrationUpdatedEvent instanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(instance,
        1L, registration);
    InstanceId instance2 = InstanceId.of("42");
    Registration registration2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(instanceRegistrationUpdatedEvent,
        new InstanceRegistrationUpdatedEvent(instance2, 1L, registration2));
  }

  /**
   * Test {@link InstanceRegistrationUpdatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistrationUpdatedEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceRegistrationUpdatedEvent.equals(Object)",
      "int InstanceRegistrationUpdatedEvent.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Registration registration = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(new InstanceRegistrationUpdatedEvent(instance, 1L, registration), null);
  }

  /**
   * Test {@link InstanceRegistrationUpdatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegistrationUpdatedEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceRegistrationUpdatedEvent.equals(Object)",
      "int InstanceRegistrationUpdatedEvent.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Registration registration = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(new InstanceRegistrationUpdatedEvent(instance, 1L, registration),
        "Different type to InstanceRegistrationUpdatedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceRegistrationUpdatedEvent#toString()}
   *   <li>{@link InstanceRegistrationUpdatedEvent#getRegistration()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Registration InstanceRegistrationUpdatedEvent.getRegistration()",
      "java.lang.String InstanceRegistrationUpdatedEvent.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Registration registration = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();
    InstanceRegistrationUpdatedEvent instanceRegistrationUpdatedEvent = new InstanceRegistrationUpdatedEvent(instance,
        1L, registration);

    // Act
    instanceRegistrationUpdatedEvent.toString();
    Registration actualRegistration = instanceRegistrationUpdatedEvent.getRegistration();

    // Assert
    assertEquals("Name", actualRegistration.getName());
    assertEquals("Source", actualRegistration.getSource());
    assertEquals("https://example.org/example", actualRegistration.getHealthUrl());
    assertEquals("https://example.org/example", actualRegistration.getManagementUrl());
    assertEquals("https://example.org/example", actualRegistration.getServiceUrl());
    assertTrue(actualRegistration.getMetadata().isEmpty());
  }
}
