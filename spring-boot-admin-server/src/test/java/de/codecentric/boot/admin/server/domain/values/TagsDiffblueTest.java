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

public class TagsDiffblueTest {
  /**
   * Test {@link Tags#equals(Object)}, and {@link Tags#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Tags#equals(Object)}
   *   <li>{@link Tags#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Tags.equals(Object)", "int Tags.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Tags emptyResult = Tags.empty();
    Tags emptyResult2 = Tags.empty();

    // Act and Assert
    assertEquals(emptyResult, emptyResult2);
    int expectedHashCodeResult = emptyResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult2.hashCode());
  }

  /**
   * Test {@link Tags#equals(Object)}, and {@link Tags#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Tags#equals(Object)}
   *   <li>{@link Tags#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Tags.equals(Object)", "int Tags.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Tags emptyResult = Tags.empty();

    // Act and Assert
    assertEquals(emptyResult, emptyResult);
    int expectedHashCodeResult = emptyResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult.hashCode());
  }

  /**
   * Test {@link Tags#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Tags.equals(Object)", "int Tags.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Tags.empty(), 1);
  }

  /**
   * Test {@link Tags#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Tags.equals(Object)", "int Tags.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Tags.empty(), null);
  }

  /**
   * Test {@link Tags#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Tags.equals(Object)", "int Tags.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Tags.empty(), "Different type to Tags");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Tags#toString()}
   *   <li>{@link Tags#getValues()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map Tags.getValues()", "String Tags.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    Tags emptyResult = Tags.empty();

    // Act
    String actualToStringResult = emptyResult.toString();

    // Assert
    assertEquals("Tags(values={})", actualToStringResult);
    assertTrue(emptyResult.getValues().isEmpty());
  }

  /**
   * Test {@link Tags#append(Tags)}.
   * <p>
   * Method under test: {@link Tags#append(Tags)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.append(Tags)"})
  public void testAppend() {
    // Arrange
    Tags emptyResult = Tags.empty();

    // Act and Assert
    assertTrue(emptyResult.append(Tags.empty()).getValues().isEmpty());
  }

  /**
   * Test {@link Tags#empty()}.
   * <p>
   * Method under test: {@link Tags#empty()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.empty()"})
  public void testEmpty() {
    // Arrange, Act and Assert
    assertTrue(Tags.empty().getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.</li>
   *   <li>Then return Values Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  public void testFromWithMapPrefix_givenFoo_whenHashMap42Is42_thenReturnValuesEmpty() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "42");
    map.put("foo", "42");

    // Act and Assert
    assertTrue(Tags.from(map, "Prefix").getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.</li>
   *   <li>Then return Values is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  public void testFromWithMapPrefix_givenFoo_whenHashMap42Is42_thenReturnValuesIsHashMap() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "42");
    map.put("foo", "42");

    // Act and Assert
    assertEquals(map, Tags.from(map, null).getValues());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return Values Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  public void testFromWithMapPrefix_givenFoo_whenHashMapFooIs42_thenReturnValuesEmpty() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act and Assert
    assertTrue(Tags.from(map, "Prefix").getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return Values size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  public void testFromWithMapPrefix_givenFoo_whenNull_thenReturnValuesSizeIsOne() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act and Assert
    Map<String, String> values = Tags.from(map, null).getValues();
    assertEquals(1, values.size());
    assertEquals("42", values.get("foo"));
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code 42}.</li>
   *   <li>Then return Values Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  public void testFromWithMapPrefix_givenNull_whenHashMapNullIs42_thenReturnValuesEmpty() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put(null, "42");

    // Act and Assert
    assertTrue(Tags.from(map, "Prefix").getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@code 42}.</li>
   *   <li>Then return Values Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  public void testFromWithMapPrefix_givenNull_whenHashMapNullIs42_thenReturnValuesEmpty2() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put(null, "42");

    // Act and Assert
    assertTrue(Tags.from(map, null).getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map, String)} with {@code map}, {@code prefix}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return Values Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map, String)"})
  public void testFromWithMapPrefix_whenHashMap_thenReturnValuesEmpty() {
    // Arrange, Act and Assert
    assertTrue(Tags.from(new HashMap<>(), "Prefix").getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map)} with {@code map}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code 42} is {@code 42}.</li>
   *   <li>Then return Values is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map)"})
  public void testFromWithMap_given42_whenHashMap42Is42_thenReturnValuesIsHashMap() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("42", "42");
    map.put("foo", "42");

    // Act and Assert
    assertEquals(map, Tags.from(map).getValues());
  }

  /**
   * Test {@link Tags#from(Map)} with {@code map}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return Values size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map)"})
  public void testFromWithMap_givenFoo_whenHashMapFooIs42_thenReturnValuesSizeIsOne() {
    // Arrange
    HashMap<String, Object> map = new HashMap<>();
    map.put("foo", "42");

    // Act and Assert
    Map<String, String> values = Tags.from(map).getValues();
    assertEquals(1, values.size());
    assertEquals("42", values.get("foo"));
  }

  /**
   * Test {@link Tags#from(Map)} with {@code map}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code null} is {@link HashMap#HashMap()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map)"})
  public void testFromWithMap_givenHashMapNullIsHashMap_whenHashMapNullIsHashMap() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(null, new HashMap<>());

    HashMap<Object, Object> objectObjectMap2 = new HashMap<>();
    objectObjectMap2.put(null, objectObjectMap);

    HashMap<String, Object> map = new HashMap<>();
    map.put(null, objectObjectMap2);

    // Act and Assert
    assertTrue(Tags.from(map).getValues().isEmpty());
  }

  /**
   * Test {@link Tags#from(Map)} with {@code map}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return Values Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tags#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Tags Tags.from(Map)"})
  public void testFromWithMap_whenHashMap_thenReturnValuesEmpty() {
    // Arrange, Act and Assert
    assertTrue(Tags.from(new HashMap<>()).getValues().isEmpty());
  }
}
