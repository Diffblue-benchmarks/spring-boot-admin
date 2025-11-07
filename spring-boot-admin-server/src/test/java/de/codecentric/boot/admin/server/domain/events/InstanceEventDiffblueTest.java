package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;
import org.mockito.Mockito;

public class InstanceEventDiffblueTest {
  /**
   * Method under test: {@link InstanceEvent#canEqual(Object)}
   */
  @Test
  public void testCanEqual() {
    // Arrange, Act and Assert
    assertFalse((new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)).canEqual("Other"));
  }

  /**
   * Method under test: {@link InstanceEvent#canEqual(Object)}
   */
  @Test
  public void testCanEqual2() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertTrue(instanceDeregisteredEvent.canEqual(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
  }

  /**
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(instance, 1L,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    InstanceDeregisteredEvent instanceDeregisteredEvent2 = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent2.getType()).thenReturn(InstanceDeregisteredEvent.TYPE);
    when(instanceDeregisteredEvent2.getTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instanceDeregisteredEvent2.getInstance()).thenReturn(InstanceId.of("42"));
    when(instanceDeregisteredEvent2.getVersion()).thenReturn(1L);
    when(instanceDeregisteredEvent2.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertEquals(instanceDeregisteredEvent, instanceDeregisteredEvent2);
    int notExpectedHashCodeResult = instanceDeregisteredEvent.hashCode();
    assertNotEquals(notExpectedHashCodeResult, instanceDeregisteredEvent2.hashCode());
  }

  /**
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertEquals(instanceDeregisteredEvent, instanceDeregisteredEvent);
    int expectedHashCodeResult = instanceDeregisteredEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceDeregisteredEvent.hashCode());
  }

  /**
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
  }

  /**
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("Value"), 1L);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
  }

  /**
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 3L);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
  }

  /**
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);
    InstanceDeregisteredEvent instanceDeregisteredEvent2 = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent2.getTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instanceDeregisteredEvent2.getInstance()).thenReturn(InstanceId.of("42"));
    when(instanceDeregisteredEvent2.getVersion()).thenReturn(1L);
    when(instanceDeregisteredEvent2.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, instanceDeregisteredEvent2);
  }

  /**
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(instance, 1L,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    InstanceDeregisteredEvent instanceDeregisteredEvent2 = mock(InstanceDeregisteredEvent.class);
    when(instanceDeregisteredEvent2.getType()).thenReturn("Type");
    when(instanceDeregisteredEvent2.getTimestamp())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(instanceDeregisteredEvent2.getInstance()).thenReturn(InstanceId.of("42"));
    when(instanceDeregisteredEvent2.getVersion()).thenReturn(1L);
    when(instanceDeregisteredEvent2.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, instanceDeregisteredEvent2);
  }

  /**
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);
  }

  /**
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), "Different type to InstanceEvent");
  }

  /**
   * Method under test: {@link InstanceEvent#getInstance()}
   */
  @Test
  public void testGetInstance() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertSame(instance, (new InstanceDeregisteredEvent(instance, 1L)).getInstance());
  }

  /**
   * Method under test: {@link InstanceEvent#getType()}
   */
  @Test
  public void testGetType() {
    // Arrange, Act and Assert
    assertEquals(InstanceDeregisteredEvent.TYPE, (new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)).getType());
  }

  /**
   * Method under test: {@link InstanceEvent#getVersion()}
   */
  @Test
  public void testGetVersion() {
    // Arrange, Act and Assert
    assertEquals(1L, (new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)).getVersion());
  }
}
