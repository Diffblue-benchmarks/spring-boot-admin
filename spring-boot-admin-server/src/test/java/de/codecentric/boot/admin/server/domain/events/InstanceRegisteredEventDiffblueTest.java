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

public class InstanceRegisteredEventDiffblueTest {
  /**
   * Test {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long, Registration)}.
   * <ul>
   *   <li>Then return Registration is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long, Registration)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceRegisteredEvent.<init>(InstanceId, long, Registration)"})
  public void testNewInstanceRegisteredEvent_thenReturnRegistrationIsNull() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act
    InstanceRegisteredEvent actualInstanceRegisteredEvent = new InstanceRegisteredEvent(instance, 1L, null);

    // Assert
    assertNull(actualInstanceRegisteredEvent.getRegistration());
    assertEquals(1L, actualInstanceRegisteredEvent.getVersion());
    assertEquals(InstanceRegisteredEvent.TYPE, actualInstanceRegisteredEvent.getType());
    assertSame(instance, actualInstanceRegisteredEvent.getInstance());
  }

  /**
   * Test {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long, Instant, Registration)}.
   * <ul>
   *   <li>Then return Registration is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegisteredEvent#InstanceRegisteredEvent(InstanceId, long, Instant, Registration)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceRegisteredEvent.<init>(InstanceId, long, Instant, Registration)"})
  public void testNewInstanceRegisteredEvent_thenReturnRegistrationIsNull2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    InstanceRegisteredEvent actualInstanceRegisteredEvent = new InstanceRegisteredEvent(instance, 1L, timestamp, null);

    // Assert
    assertNull(actualInstanceRegisteredEvent.getRegistration());
    assertEquals(1L, actualInstanceRegisteredEvent.getVersion());
    assertEquals(InstanceRegisteredEvent.TYPE, actualInstanceRegisteredEvent.getType());
    assertSame(instance, actualInstanceRegisteredEvent.getInstance());
    Instant expectedTimestamp = timestamp.EPOCH;
    assertSame(expectedTimestamp, actualInstanceRegisteredEvent.getTimestamp());
  }

  /**
   * Test {@link InstanceRegisteredEvent#equals(Object)}, and {@link InstanceRegisteredEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceRegisteredEvent#equals(Object)}
   *   <li>{@link InstanceRegisteredEvent#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceRegisteredEvent.equals(Object)", "int InstanceRegisteredEvent.hashCode()"})
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
    InstanceRegisteredEvent instanceRegisteredEvent = new InstanceRegisteredEvent(instance, 1L, registration);

    // Act and Assert
    assertEquals(instanceRegisteredEvent, instanceRegisteredEvent);
    int expectedHashCodeResult = instanceRegisteredEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceRegisteredEvent.hashCode());
  }

  /**
   * Test {@link InstanceRegisteredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegisteredEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceRegisteredEvent.equals(Object)", "int InstanceRegisteredEvent.hashCode()"})
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
    InstanceRegisteredEvent instanceRegisteredEvent = new InstanceRegisteredEvent(instance, 1L, registration);
    InstanceId instance2 = InstanceId.of("42");
    Registration registration2 = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act and Assert
    assertNotEquals(instanceRegisteredEvent, new InstanceRegisteredEvent(instance2, 1L, registration2));
  }

  /**
   * Test {@link InstanceRegisteredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegisteredEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceRegisteredEvent.equals(Object)", "int InstanceRegisteredEvent.hashCode()"})
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
    assertNotEquals(new InstanceRegisteredEvent(instance, 1L, registration), null);
  }

  /**
   * Test {@link InstanceRegisteredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceRegisteredEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceRegisteredEvent.equals(Object)", "int InstanceRegisteredEvent.hashCode()"})
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
    assertNotEquals(new InstanceRegisteredEvent(instance, 1L, registration),
        "Different type to InstanceRegisteredEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceRegisteredEvent#toString()}
   *   <li>{@link InstanceRegisteredEvent#getRegistration()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Registration InstanceRegisteredEvent.getRegistration()",
      "java.lang.String InstanceRegisteredEvent.toString()"})
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
    InstanceRegisteredEvent instanceRegisteredEvent = new InstanceRegisteredEvent(instance, 1L, registration);

    // Act
    instanceRegisteredEvent.toString();
    Registration actualRegistration = instanceRegisteredEvent.getRegistration();

    // Assert
    assertEquals("Name", actualRegistration.getName());
    assertEquals("Source", actualRegistration.getSource());
    assertEquals("https://example.org/example", actualRegistration.getHealthUrl());
    assertEquals("https://example.org/example", actualRegistration.getManagementUrl());
    assertEquals("https://example.org/example", actualRegistration.getServiceUrl());
    assertTrue(actualRegistration.getMetadata().isEmpty());
  }
}
