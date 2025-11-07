package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class InstanceIdDiffblueTest {
  /**
   * Method under test: {@link InstanceId#of(String)}
   */
  @Test
  public void testOf() {
    // Arrange and Act
    InstanceId actualOfResult = InstanceId.of("42");

    // Assert
    assertEquals("42", actualOfResult.getValue());
    assertEquals("42", actualOfResult.toString());
  }

  /**
   * Method under test: {@link InstanceId#compareTo(InstanceId)}
   */
  @Test
  public void testCompareTo() {
    // Arrange
    InstanceId ofResult = InstanceId.of("42");

    // Act and Assert
    assertEquals(0, ofResult.compareTo(InstanceId.of("42")));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceId#equals(Object)}
   *   <li>{@link InstanceId#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceId#equals(Object)}
   *   <li>{@link InstanceId#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceId ofResult = InstanceId.of("42");

    // Act and Assert
    assertEquals(ofResult, ofResult);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult.hashCode());
  }

  /**
   * Method under test: {@link InstanceId#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceId ofResult = InstanceId.of("Value");

    // Act and Assert
    assertNotEquals(ofResult, InstanceId.of("42"));
  }

  /**
   * Method under test: {@link InstanceId#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(InstanceId.of("42"), null);
  }

  /**
   * Method under test: {@link InstanceId#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(InstanceId.of("42"), "Different type to InstanceId");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceId#getValue()}
   *   <li>{@link InstanceId#toString()}
   * </ul>
   */
  @Test
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
