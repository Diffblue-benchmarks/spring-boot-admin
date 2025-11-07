package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class InfoDiffblueTest {
  /**
   * Method under test: {@link Info#from(Map)}
   */
  @Test
  public void testFrom() {
    // Arrange, Act and Assert
    assertTrue(Info.from(new HashMap<>()).getValues().isEmpty());
    assertTrue(Info.from(null).getValues().isEmpty());
  }

  /**
   * Method under test: {@link Info#from(Map)}
   */
  @Test
  public void testFrom2() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.put("foo", "42");

    // Act and Assert
    Map<String, Object> values2 = Info.from(values).getValues();
    assertEquals(1, values2.size());
    assertEquals("42", values2.get("foo"));
  }

  /**
   * Method under test: {@link Info#from(Map)}
   */
  @Test
  public void testFrom3() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.computeIfPresent("foo", mock(BiFunction.class));
    values.put("foo", "42");

    // Act and Assert
    Map<String, Object> values2 = Info.from(values).getValues();
    assertEquals(1, values2.size());
    assertEquals("42", values2.get("foo"));
  }

  /**
   * Method under test: {@link Info#empty()}
   */
  @Test
  public void testEmpty() {
    // Arrange, Act and Assert
    assertTrue(Info.empty().getValues().isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Info#equals(Object)}
   *   <li>{@link Info#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Info emptyResult = Info.empty();
    Info emptyResult2 = Info.empty();

    // Act and Assert
    assertEquals(emptyResult, emptyResult2);
    int expectedHashCodeResult = emptyResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Info#equals(Object)}
   *   <li>{@link Info#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Info emptyResult = Info.empty();

    // Act and Assert
    assertEquals(emptyResult, emptyResult);
    int expectedHashCodeResult = emptyResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult.hashCode());
  }

  /**
   * Method under test: {@link Info#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Info.empty(), 1);
  }

  /**
   * Method under test: {@link Info#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.put("foo", "42");
    Info fromResult = Info.from(values);

    // Act and Assert
    assertNotEquals(fromResult, Info.empty());
  }

  /**
   * Method under test: {@link Info#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.computeIfPresent("foo", mock(BiFunction.class));
    values.put("foo", "42");
    Info fromResult = Info.from(values);

    // Act and Assert
    assertNotEquals(fromResult, Info.empty());
  }

  /**
   * Method under test: {@link Info#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Info.empty(), null);
  }

  /**
   * Method under test: {@link Info#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Info.empty(), "Different type to Info");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Info#toString()}
   *   <li>{@link Info#getValues()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Info emptyResult = Info.empty();

    // Act
    String actualToStringResult = emptyResult.toString();

    // Assert
    assertEquals("Info(values={})", actualToStringResult);
    assertTrue(emptyResult.getValues().isEmpty());
  }
}
