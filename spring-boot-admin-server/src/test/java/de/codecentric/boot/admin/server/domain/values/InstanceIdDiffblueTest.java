package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InstanceIdDiffblueTest {
  /**
   * Test {@link InstanceId#of(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return Value is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceId#of(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"InstanceId InstanceId.of(String)"})
  public void testOf_when42_thenReturnValueIs42() {
    // Arrange and Act
    InstanceId actualOfResult = InstanceId.of("42");

    // Assert
    assertEquals("42", actualOfResult.getValue());
    assertEquals("42", actualOfResult.toString());
  }

  /**
   * Test {@link InstanceId#compareTo(InstanceId)} with {@code InstanceId}.
   * <p>
   * Method under test: {@link InstanceId#compareTo(InstanceId)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int InstanceId.compareTo(InstanceId)"})
  public void testCompareToWithInstanceId() {
    // Arrange
    InstanceId ofResult = InstanceId.of("42");

    // Act and Assert
    assertEquals(0, ofResult.compareTo(InstanceId.of("42")));
  }

  /**
   * Test {@link InstanceId#equals(Object)}, and {@link InstanceId#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceId#equals(Object)}
   *   <li>{@link InstanceId#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceId.equals(Object)", "int InstanceId.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InstanceId ofResult = InstanceId.of("42");
    InstanceId ofResult2 = InstanceId.of("42");

    // Act and Assert
    assertEquals(ofResult, ofResult2);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult2.hashCode());
  }

  /**
   * Test {@link InstanceId#equals(Object)}, and {@link InstanceId#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceId#equals(Object)}
   *   <li>{@link InstanceId#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceId.equals(Object)", "int InstanceId.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceId ofResult = InstanceId.of("42");

    // Act and Assert
    assertEquals(ofResult, ofResult);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult.hashCode());
  }

  /**
   * Test {@link InstanceId#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceId#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceId.equals(Object)", "int InstanceId.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceId ofResult = InstanceId.of("Value");

    // Act and Assert
    assertNotEquals(ofResult, InstanceId.of("42"));
  }

  /**
   * Test {@link InstanceId#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceId#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceId.equals(Object)", "int InstanceId.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(InstanceId.of("42"), null);
  }

  /**
   * Test {@link InstanceId#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceId#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceId.equals(Object)", "int InstanceId.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(InstanceId.of("42"), "Different type to InstanceId");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceId#getValue()}
   *   <li>{@link InstanceId#toString()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String InstanceId.getValue()", "String InstanceId.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    InstanceId ofResult = InstanceId.of("42");

    // Act
    String actualValue = ofResult.getValue();

    // Assert
    assertEquals("42", actualValue);
    assertEquals("42", ofResult.toString());
  }
}
