package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InfoDiffblueTest {
  /**
   * Test {@link Info#from(Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return Values is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Info#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Info Info.from(Map)"})
  public void testFrom_givenFoo_whenHashMapFooIs42_thenReturnValuesIsHashMap() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.put("foo", "42");

    // Act and Assert
    assertEquals(values, Info.from(values).getValues());
  }

  /**
   * Test {@link Info#from(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return Values Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Info#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Info Info.from(Map)"})
  public void testFrom_whenHashMap_thenReturnValuesEmpty() {
    // Arrange, Act and Assert
    assertTrue(Info.from(new HashMap<>()).getValues().isEmpty());
  }

  /**
   * Test {@link Info#from(Map)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Values Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Info#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Info Info.from(Map)"})
  public void testFrom_whenNull_thenReturnValuesEmpty() {
    // Arrange, Act and Assert
    assertTrue(Info.from(null).getValues().isEmpty());
  }

  /**
   * Test {@link Info#empty()}.
   * <p>
   * Method under test: {@link Info#empty()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Info Info.empty()"})
  public void testEmpty() {
    // Arrange, Act and Assert
    assertTrue(Info.empty().getValues().isEmpty());
  }

  /**
   * Test {@link Info#equals(Object)}, and {@link Info#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Info#equals(Object)}
   *   <li>{@link Info#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Info.equals(Object)", "int Info.hashCode()"})
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
   * Test {@link Info#equals(Object)}, and {@link Info#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Info#equals(Object)}
   *   <li>{@link Info#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Info.equals(Object)", "int Info.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Info emptyResult = Info.empty();

    // Act and Assert
    assertEquals(emptyResult, emptyResult);
    int expectedHashCodeResult = emptyResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult.hashCode());
  }

  /**
   * Test {@link Info#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Info#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Info.equals(Object)", "int Info.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Info.empty(), 1);
  }

  /**
   * Test {@link Info#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Info#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Info.equals(Object)", "int Info.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Object> values = new HashMap<>();
    values.put("foo", "42");
    Info fromResult = Info.from(values);

    // Act and Assert
    assertNotEquals(fromResult, Info.empty());
  }

  /**
   * Test {@link Info#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Info#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Info.equals(Object)", "int Info.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Info.empty(), null);
  }

  /**
   * Test {@link Info#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Info#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Info.equals(Object)", "int Info.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Info.empty(), "Different type to Info");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Info#toString()}
   *   <li>{@link Info#getValues()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map Info.getValues()", "String Info.toString()"})
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
