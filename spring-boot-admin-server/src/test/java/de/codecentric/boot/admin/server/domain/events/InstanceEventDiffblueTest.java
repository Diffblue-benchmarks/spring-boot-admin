package de.codecentric.boot.admin.server.domain.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class InstanceEventDiffblueTest {
  /**
   * Test {@link InstanceEvent#canEqual(Object)}.
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#canEqual(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.canEqual(Object)"})
  public void testCanEqual_whenInstanceIdWithValueIs42_thenReturnTrue() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertTrue(instanceDeregisteredEvent.canEqual(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
  }

  /**
   * Test {@link InstanceEvent#canEqual(Object)}.
   * <ul>
   *   <li>When {@code Other}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#canEqual(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.canEqual(Object)"})
  public void testCanEqual_whenOther_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)).canEqual("Other"));
  }

  /**
   * Test {@link InstanceEvent#equals(Object)}, and {@link InstanceEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.equals(Object)", "int InstanceEvent.hashCode()"})
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
   * Test {@link InstanceEvent#equals(Object)}, and {@link InstanceEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.equals(Object)", "int InstanceEvent.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertEquals(instanceDeregisteredEvent, instanceDeregisteredEvent);
    int expectedHashCodeResult = instanceDeregisteredEvent.hashCode();
    assertEquals(expectedHashCodeResult, instanceDeregisteredEvent.hashCode());
  }

  /**
   * Test {@link InstanceEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.equals(Object)", "int InstanceEvent.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
  }

  /**
   * Test {@link InstanceEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.equals(Object)", "int InstanceEvent.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("Value"), 1L);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
  }

  /**
   * Test {@link InstanceEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.equals(Object)", "int InstanceEvent.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    InstanceDeregisteredEvent instanceDeregisteredEvent = new InstanceDeregisteredEvent(InstanceId.of("42"), 3L);

    // Act and Assert
    assertNotEquals(instanceDeregisteredEvent, new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
  }

  /**
   * Test {@link InstanceEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.equals(Object)", "int InstanceEvent.hashCode()"})
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
   * Test {@link InstanceEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.equals(Object)", "int InstanceEvent.hashCode()"})
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
   * Test {@link InstanceEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.equals(Object)", "int InstanceEvent.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);
  }

  /**
   * Test {@link InstanceEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceEvent#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceEvent.equals(Object)", "int InstanceEvent.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), "Different type to InstanceEvent");
  }

  /**
   * Test {@link InstanceEvent#getInstance()}.
   * <p>
   * Method under test: {@link InstanceEvent#getInstance()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"InstanceId InstanceEvent.getInstance()"})
  public void testGetInstance() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertSame(instance, (new InstanceDeregisteredEvent(instance, 1L)).getInstance());
  }

  /**
   * Test {@link InstanceEvent#getType()}.
   * <p>
   * Method under test: {@link InstanceEvent#getType()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String InstanceEvent.getType()"})
  public void testGetType() {
    // Arrange, Act and Assert
    assertEquals(InstanceDeregisteredEvent.TYPE, (new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)).getType());
  }

  /**
   * Test {@link InstanceEvent#getVersion()}.
   * <p>
   * Method under test: {@link InstanceEvent#getVersion()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long InstanceEvent.getVersion()"})
  public void testGetVersion() {
    // Arrange, Act and Assert
    assertEquals(1L, (new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)).getVersion());
  }
}
