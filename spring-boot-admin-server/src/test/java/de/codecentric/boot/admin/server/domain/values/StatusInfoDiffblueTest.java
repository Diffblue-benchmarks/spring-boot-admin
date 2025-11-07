package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class StatusInfoDiffblueTest {
  /**
   * Test {@link StatusInfo#valueOf(String, Map)} with {@code statusCode}, {@code details}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return Status is {@code STATUS CODE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#valueOf(String, Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.valueOf(String, Map)"})
  public void testValueOfWithStatusCodeDetails_whenHashMap_thenReturnStatusIsStatusCode() {
    // Arrange and Act
    StatusInfo actualValueOfResult = StatusInfo.valueOf("Status Code", new HashMap<>());

    // Assert
    assertEquals("STATUS CODE", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }

  /**
   * Test {@link StatusInfo#valueOf(String, Map)} with {@code statusCode}, {@code details}.
   * <ul>
   *   <li>When {@code Status Code}.</li>
   *   <li>Then return Status is {@code STATUS CODE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#valueOf(String, Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.valueOf(String, Map)"})
  public void testValueOfWithStatusCodeDetails_whenStatusCode_thenReturnStatusIsStatusCode() {
    // Arrange and Act
    StatusInfo actualValueOfResult = StatusInfo.valueOf("Status Code", null);

    // Assert
    assertEquals("STATUS CODE", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }

  /**
   * Test {@link StatusInfo#valueOf(String)} with {@code statusCode}.
   * <ul>
   *   <li>When {@code Status Code}.</li>
   *   <li>Then return Status is {@code STATUS CODE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#valueOf(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.valueOf(String)"})
  public void testValueOfWithStatusCode_whenStatusCode_thenReturnStatusIsStatusCode() {
    // Arrange and Act
    StatusInfo actualValueOfResult = StatusInfo.valueOf("Status Code");

    // Assert
    assertEquals("STATUS CODE", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }

  /**
   * Test {@link StatusInfo#ofUnknown()}.
   * <p>
   * Method under test: {@link StatusInfo#ofUnknown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofUnknown()"})
  public void testOfUnknown() {
    // Arrange and Act
    StatusInfo actualOfUnknownResult = StatusInfo.ofUnknown();

    // Assert
    assertFalse(actualOfUnknownResult.isDown());
    assertFalse(actualOfUnknownResult.isOffline());
    assertFalse(actualOfUnknownResult.isUp());
    assertTrue(actualOfUnknownResult.isUnknown());
    assertTrue(actualOfUnknownResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_UNKNOWN, actualOfUnknownResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#ofUp()}.
   * <p>
   * Method under test: {@link StatusInfo#ofUp()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofUp()"})
  public void testOfUp() {
    // Arrange and Act
    StatusInfo actualOfUpResult = StatusInfo.ofUp();

    // Assert
    assertFalse(actualOfUpResult.isDown());
    assertFalse(actualOfUpResult.isOffline());
    assertFalse(actualOfUpResult.isUnknown());
    assertTrue(actualOfUpResult.isUp());
    assertTrue(actualOfUpResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_UP, actualOfUpResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#ofUp(Map)} with {@code Map}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#ofUp(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofUp(Map)"})
  public void testOfUpWithMap_whenHashMap() {
    // Arrange and Act
    StatusInfo actualOfUpResult = StatusInfo.ofUp(new HashMap<>());

    // Assert
    assertFalse(actualOfUpResult.isDown());
    assertFalse(actualOfUpResult.isOffline());
    assertFalse(actualOfUpResult.isUnknown());
    assertTrue(actualOfUpResult.isUp());
    assertTrue(actualOfUpResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_UP, actualOfUpResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#ofUp(Map)} with {@code Map}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#ofUp(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofUp(Map)"})
  public void testOfUpWithMap_whenNull() {
    // Arrange and Act
    StatusInfo actualOfUpResult = StatusInfo.ofUp(null);

    // Assert
    assertFalse(actualOfUpResult.isDown());
    assertFalse(actualOfUpResult.isOffline());
    assertFalse(actualOfUpResult.isUnknown());
    assertTrue(actualOfUpResult.isUp());
    assertTrue(actualOfUpResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_UP, actualOfUpResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#ofDown()}.
   * <p>
   * Method under test: {@link StatusInfo#ofDown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofDown()"})
  public void testOfDown() {
    // Arrange and Act
    StatusInfo actualOfDownResult = StatusInfo.ofDown();

    // Assert
    assertFalse(actualOfDownResult.isOffline());
    assertFalse(actualOfDownResult.isUnknown());
    assertFalse(actualOfDownResult.isUp());
    assertTrue(actualOfDownResult.isDown());
    assertTrue(actualOfDownResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_DOWN, actualOfDownResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#ofDown(Map)} with {@code Map}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#ofDown(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofDown(Map)"})
  public void testOfDownWithMap_whenHashMap() {
    // Arrange and Act
    StatusInfo actualOfDownResult = StatusInfo.ofDown(new HashMap<>());

    // Assert
    assertFalse(actualOfDownResult.isOffline());
    assertFalse(actualOfDownResult.isUnknown());
    assertFalse(actualOfDownResult.isUp());
    assertTrue(actualOfDownResult.isDown());
    assertTrue(actualOfDownResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_DOWN, actualOfDownResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#ofDown(Map)} with {@code Map}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#ofDown(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofDown(Map)"})
  public void testOfDownWithMap_whenNull() {
    // Arrange and Act
    StatusInfo actualOfDownResult = StatusInfo.ofDown(null);

    // Assert
    assertFalse(actualOfDownResult.isOffline());
    assertFalse(actualOfDownResult.isUnknown());
    assertFalse(actualOfDownResult.isUp());
    assertTrue(actualOfDownResult.isDown());
    assertTrue(actualOfDownResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_DOWN, actualOfDownResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#ofOffline()}.
   * <p>
   * Method under test: {@link StatusInfo#ofOffline()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofOffline()"})
  public void testOfOffline() {
    // Arrange and Act
    StatusInfo actualOfOfflineResult = StatusInfo.ofOffline();

    // Assert
    assertFalse(actualOfOfflineResult.isDown());
    assertFalse(actualOfOfflineResult.isUnknown());
    assertFalse(actualOfOfflineResult.isUp());
    assertTrue(actualOfOfflineResult.isOffline());
    assertTrue(actualOfOfflineResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_OFFLINE, actualOfOfflineResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#ofOffline(Map)} with {@code Map}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#ofOffline(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofOffline(Map)"})
  public void testOfOfflineWithMap_whenHashMap() {
    // Arrange and Act
    StatusInfo actualOfOfflineResult = StatusInfo.ofOffline(new HashMap<>());

    // Assert
    assertFalse(actualOfOfflineResult.isDown());
    assertFalse(actualOfOfflineResult.isUnknown());
    assertFalse(actualOfOfflineResult.isUp());
    assertTrue(actualOfOfflineResult.isOffline());
    assertTrue(actualOfOfflineResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_OFFLINE, actualOfOfflineResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#ofOffline(Map)} with {@code Map}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#ofOffline(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.ofOffline(Map)"})
  public void testOfOfflineWithMap_whenNull() {
    // Arrange and Act
    StatusInfo actualOfOfflineResult = StatusInfo.ofOffline(null);

    // Assert
    assertFalse(actualOfOfflineResult.isDown());
    assertFalse(actualOfOfflineResult.isUnknown());
    assertFalse(actualOfOfflineResult.isUp());
    assertTrue(actualOfOfflineResult.isOffline());
    assertTrue(actualOfOfflineResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_OFFLINE, actualOfOfflineResult.getStatus());
  }

  /**
   * Test {@link StatusInfo#getDetails()}.
   * <p>
   * Method under test: {@link StatusInfo#getDetails()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map StatusInfo.getDetails()"})
  public void testGetDetails() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf("Status Code", new HashMap<>()).getDetails().isEmpty());
  }

  /**
   * Test {@link StatusInfo#isUp()}.
   * <ul>
   *   <li>Given valueOf {@code Status Code} and {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#isUp()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.isUp()"})
  public void testIsUp_givenValueOfStatusCodeAndHashMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isUp());
  }

  /**
   * Test {@link StatusInfo#isUp()}.
   * <ul>
   *   <li>Given valueOf {@link StatusInfo#STATUS_UP} and {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#isUp()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.isUp()"})
  public void testIsUp_givenValueOfStatus_upAndHashMap_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_UP, new HashMap<>()).isUp());
  }

  /**
   * Test {@link StatusInfo#isOffline()}.
   * <ul>
   *   <li>Given valueOf {@code Status Code} and {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#isOffline()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.isOffline()"})
  public void testIsOffline_givenValueOfStatusCodeAndHashMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isOffline());
  }

  /**
   * Test {@link StatusInfo#isOffline()}.
   * <ul>
   *   <li>Given valueOf {@link StatusInfo#STATUS_OFFLINE} and {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#isOffline()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.isOffline()"})
  public void testIsOffline_givenValueOfStatus_offlineAndHashMap_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_OFFLINE, new HashMap<>()).isOffline());
  }

  /**
   * Test {@link StatusInfo#isDown()}.
   * <ul>
   *   <li>Given valueOf {@code Status Code} and {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#isDown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.isDown()"})
  public void testIsDown_givenValueOfStatusCodeAndHashMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isDown());
  }

  /**
   * Test {@link StatusInfo#isDown()}.
   * <ul>
   *   <li>Given valueOf {@link StatusInfo#STATUS_DOWN} and {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#isDown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.isDown()"})
  public void testIsDown_givenValueOfStatus_downAndHashMap_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_DOWN, new HashMap<>()).isDown());
  }

  /**
   * Test {@link StatusInfo#isUnknown()}.
   * <ul>
   *   <li>Given valueOf {@code Status Code} and {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#isUnknown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.isUnknown()"})
  public void testIsUnknown_givenValueOfStatusCodeAndHashMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isUnknown());
  }

  /**
   * Test {@link StatusInfo#isUnknown()}.
   * <ul>
   *   <li>Given valueOf {@link StatusInfo#STATUS_UNKNOWN} and {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#isUnknown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.isUnknown()"})
  public void testIsUnknown_givenValueOfStatus_unknownAndHashMap_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_UNKNOWN, new HashMap<>()).isUnknown());
  }

  /**
   * Test {@link StatusInfo#severity()}.
   * <p>
   * Method under test: {@link StatusInfo#severity()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Comparator StatusInfo.severity()"})
  public void testSeverity() {
    // Arrange and Act
    Comparator<String> actualSeverityResult = StatusInfo.severity();

    // Assert
    assertEquals(0, actualSeverityResult.compare("foo", "foo"));
  }

  /**
   * Test {@link StatusInfo#from(Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code status} is {@code Body}.</li>
   *   <li>Then return Status is {@code BODY}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.from(Map)"})
  public void testFrom_givenFoo_whenHashMapStatusIsBody_thenReturnStatusIsBody() {
    // Arrange
    HashMap<String, Object> body = new HashMap<>();
    body.put("details", null);
    body.put("components", null);
    body.put("status", "Body");
    body.put("foo", "Body");
    body.put("components", "Body");

    // Act
    StatusInfo actualFromResult = StatusInfo.from(body);

    // Assert
    assertEquals("BODY", actualFromResult.getStatus());
    assertFalse(actualFromResult.isDown());
    assertFalse(actualFromResult.isOffline());
    assertFalse(actualFromResult.isUnknown());
    assertFalse(actualFromResult.isUp());
    assertTrue(actualFromResult.getDetails().isEmpty());
  }

  /**
   * Test {@link StatusInfo#from(Map)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code details} is {@link HashMap#HashMap()}.</li>
   *   <li>Then return Status is {@code BODY}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#from(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"StatusInfo StatusInfo.from(Map)"})
  public void testFrom_givenHashMap_whenHashMapDetailsIsHashMap_thenReturnStatusIsBody() {
    // Arrange
    HashMap<String, Object> body = new HashMap<>();
    body.put("details", null);
    body.put("components", null);
    body.put("status", "Body");
    body.put("details", new HashMap<>());
    body.put("components", "Body");

    // Act
    StatusInfo actualFromResult = StatusInfo.from(body);

    // Assert
    assertEquals("BODY", actualFromResult.getStatus());
    assertFalse(actualFromResult.isDown());
    assertFalse(actualFromResult.isOffline());
    assertFalse(actualFromResult.isUnknown());
    assertFalse(actualFromResult.isUp());
    assertTrue(actualFromResult.getDetails().isEmpty());
  }

  /**
   * Test {@link StatusInfo#equals(Object)}, and {@link StatusInfo#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StatusInfo#equals(Object)}
   *   <li>{@link StatusInfo#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.equals(Object)", "int StatusInfo.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    StatusInfo valueOfResult = StatusInfo.valueOf("Status Code", new HashMap<>());
    StatusInfo valueOfResult2 = StatusInfo.valueOf("Status Code", new HashMap<>());

    // Act and Assert
    assertEquals(valueOfResult, valueOfResult2);
    int expectedHashCodeResult = valueOfResult.hashCode();
    assertEquals(expectedHashCodeResult, valueOfResult2.hashCode());
  }

  /**
   * Test {@link StatusInfo#equals(Object)}, and {@link StatusInfo#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StatusInfo#equals(Object)}
   *   <li>{@link StatusInfo#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.equals(Object)", "int StatusInfo.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StatusInfo valueOfResult = StatusInfo.valueOf("Status Code", new HashMap<>());

    // Act and Assert
    assertEquals(valueOfResult, valueOfResult);
    int expectedHashCodeResult = valueOfResult.hashCode();
    assertEquals(expectedHashCodeResult, valueOfResult.hashCode());
  }

  /**
   * Test {@link StatusInfo#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.equals(Object)", "int StatusInfo.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StatusInfo valueOfResult = StatusInfo.valueOf(StatusInfo.STATUS_DOWN, new HashMap<>());

    // Act and Assert
    assertNotEquals(valueOfResult, StatusInfo.valueOf("Status Code", new HashMap<>()));
  }

  /**
   * Test {@link StatusInfo#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.equals(Object)", "int StatusInfo.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.put("STATUS CODE", "42");
    StatusInfo valueOfResult = StatusInfo.valueOf("Status Code", details);

    // Act and Assert
    assertNotEquals(valueOfResult, StatusInfo.valueOf("Status Code", new HashMap<>()));
  }

  /**
   * Test {@link StatusInfo#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.equals(Object)", "int StatusInfo.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(StatusInfo.valueOf("Status Code", new HashMap<>()), null);
  }

  /**
   * Test {@link StatusInfo#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusInfo#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean StatusInfo.equals(Object)", "int StatusInfo.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(StatusInfo.valueOf("Status Code", new HashMap<>()), "Different type to StatusInfo");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StatusInfo#toString()}
   *   <li>{@link StatusInfo#getStatus()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String StatusInfo.getStatus()", "String StatusInfo.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    StatusInfo valueOfResult = StatusInfo.valueOf("Status Code", new HashMap<>());

    // Act
    String actualToStringResult = valueOfResult.toString();

    // Assert
    assertEquals("STATUS CODE", valueOfResult.getStatus());
    assertEquals("StatusInfo(status=STATUS CODE, details={})", actualToStringResult);
  }
}
