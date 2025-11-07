package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class StatusInfoDiffblueTest {
  /**
   * Method under test: {@link StatusInfo#valueOf(String)}
   */
  @Test
  public void testValueOf() {
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
   * Method under test: {@link StatusInfo#valueOf(String, Map)}
   */
  @Test
  public void testValueOf2() {
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
   * Method under test: {@link StatusInfo#valueOf(String, Map)}
   */
  @Test
  public void testValueOf3() {
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
   * Method under test: {@link StatusInfo#valueOf(String, Map)}
   */
  @Test
  public void testValueOf4() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent("'status' must not be empty.", mock(BiFunction.class));

    // Act
    StatusInfo actualValueOfResult = StatusInfo.valueOf("Status Code", details);

    // Assert
    assertEquals("STATUS CODE", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }

  /**
   * Method under test: {@link StatusInfo#ofUnknown()}
   */
  @Test
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
   * Method under test: {@link StatusInfo#ofUp()}
   */
  @Test
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
   * Method under test: {@link StatusInfo#ofUp(Map)}
   */
  @Test
  public void testOfUp2() {
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
   * Method under test: {@link StatusInfo#ofUp(Map)}
   */
  @Test
  public void testOfUp3() {
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
   * Method under test: {@link StatusInfo#ofUp(Map)}
   */
  @Test
  public void testOfUp4() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent(StatusInfo.STATUS_UP, mock(BiFunction.class));

    // Act
    StatusInfo actualOfUpResult = StatusInfo.ofUp(details);

    // Assert
    assertFalse(actualOfUpResult.isDown());
    assertFalse(actualOfUpResult.isOffline());
    assertFalse(actualOfUpResult.isUnknown());
    assertTrue(actualOfUpResult.isUp());
    assertTrue(actualOfUpResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_UP, actualOfUpResult.getStatus());
  }

  /**
   * Method under test: {@link StatusInfo#ofDown()}
   */
  @Test
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
   * Method under test: {@link StatusInfo#ofDown(Map)}
   */
  @Test
  public void testOfDown2() {
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
   * Method under test: {@link StatusInfo#ofDown(Map)}
   */
  @Test
  public void testOfDown3() {
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
   * Method under test: {@link StatusInfo#ofDown(Map)}
   */
  @Test
  public void testOfDown4() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent(StatusInfo.STATUS_DOWN, mock(BiFunction.class));

    // Act
    StatusInfo actualOfDownResult = StatusInfo.ofDown(details);

    // Assert
    assertFalse(actualOfDownResult.isOffline());
    assertFalse(actualOfDownResult.isUnknown());
    assertFalse(actualOfDownResult.isUp());
    assertTrue(actualOfDownResult.isDown());
    assertTrue(actualOfDownResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_DOWN, actualOfDownResult.getStatus());
  }

  /**
   * Method under test: {@link StatusInfo#ofOffline()}
   */
  @Test
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
   * Method under test: {@link StatusInfo#ofOffline(Map)}
   */
  @Test
  public void testOfOffline2() {
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
   * Method under test: {@link StatusInfo#ofOffline(Map)}
   */
  @Test
  public void testOfOffline3() {
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
   * Method under test: {@link StatusInfo#ofOffline(Map)}
   */
  @Test
  public void testOfOffline4() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent(StatusInfo.STATUS_OFFLINE, mock(BiFunction.class));

    // Act
    StatusInfo actualOfOfflineResult = StatusInfo.ofOffline(details);

    // Assert
    assertFalse(actualOfOfflineResult.isDown());
    assertFalse(actualOfOfflineResult.isUnknown());
    assertFalse(actualOfOfflineResult.isUp());
    assertTrue(actualOfOfflineResult.isOffline());
    assertTrue(actualOfOfflineResult.getDetails().isEmpty());
    assertEquals(StatusInfo.STATUS_OFFLINE, actualOfOfflineResult.getStatus());
  }

  /**
   * Method under test: {@link StatusInfo#getDetails()}
   */
  @Test
  public void testGetDetails() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf("Status Code", new HashMap<>()).getDetails().isEmpty());
  }

  /**
   * Method under test: {@link StatusInfo#getDetails()}
   */
  @Test
  public void testGetDetails2() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertTrue(StatusInfo.valueOf("Status Code", details).getDetails().isEmpty());
  }

  /**
   * Method under test: {@link StatusInfo#isUp()}
   */
  @Test
  public void testIsUp() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isUp());
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_UP, new HashMap<>()).isUp());
  }

  /**
   * Method under test: {@link StatusInfo#isUp()}
   */
  @Test
  public void testIsUp2() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent(StatusInfo.STATUS_UP, mock(BiFunction.class));

    // Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", details).isUp());
  }

  /**
   * Method under test: {@link StatusInfo#isOffline()}
   */
  @Test
  public void testIsOffline() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isOffline());
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_OFFLINE, new HashMap<>()).isOffline());
  }

  /**
   * Method under test: {@link StatusInfo#isOffline()}
   */
  @Test
  public void testIsOffline2() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent(StatusInfo.STATUS_OFFLINE, mock(BiFunction.class));

    // Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", details).isOffline());
  }

  /**
   * Method under test: {@link StatusInfo#isDown()}
   */
  @Test
  public void testIsDown() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isDown());
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_DOWN, new HashMap<>()).isDown());
  }

  /**
   * Method under test: {@link StatusInfo#isDown()}
   */
  @Test
  public void testIsDown2() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent(StatusInfo.STATUS_DOWN, mock(BiFunction.class));

    // Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", details).isDown());
  }

  /**
   * Method under test: {@link StatusInfo#isUnknown()}
   */
  @Test
  public void testIsUnknown() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isUnknown());
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_UNKNOWN, new HashMap<>()).isUnknown());
  }

  /**
   * Method under test: {@link StatusInfo#isUnknown()}
   */
  @Test
  public void testIsUnknown2() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent(StatusInfo.STATUS_UNKNOWN, mock(BiFunction.class));

    // Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", details).isUnknown());
  }

  /**
   * Method under test: {@link StatusInfo#severity()}
   */
  @Test
  public void testSeverity() {
    // Arrange and Act
    Comparator<String> actualSeverityResult = StatusInfo.severity();

    // Assert
    assertEquals(0, actualSeverityResult.compare("foo", "foo"));
  }

  /**
   * Method under test: {@link StatusInfo#from(Map)}
   */
  @Test
  public void testFrom() {
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
   * Method under test: {@link StatusInfo#from(Map)}
   */
  @Test
  public void testFrom2() {
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
   * Methods under test:
   * <ul>
   *   <li>{@link StatusInfo#equals(Object)}
   *   <li>{@link StatusInfo#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link StatusInfo#equals(Object)}
   *   <li>{@link StatusInfo#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StatusInfo valueOfResult = StatusInfo.valueOf("Status Code", new HashMap<>());

    // Act and Assert
    assertEquals(valueOfResult, valueOfResult);
    int expectedHashCodeResult = valueOfResult.hashCode();
    assertEquals(expectedHashCodeResult, valueOfResult.hashCode());
  }

  /**
   * Method under test: {@link StatusInfo#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StatusInfo valueOfResult = StatusInfo.valueOf(StatusInfo.STATUS_DOWN, new HashMap<>());

    // Act and Assert
    assertNotEquals(valueOfResult, StatusInfo.valueOf("Status Code", new HashMap<>()));
  }

  /**
   * Method under test: {@link StatusInfo#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.put("STATUS CODE", "42");
    StatusInfo valueOfResult = StatusInfo.valueOf("Status Code", details);

    // Act and Assert
    assertNotEquals(valueOfResult, StatusInfo.valueOf("Status Code", new HashMap<>()));
  }

  /**
   * Method under test: {@link StatusInfo#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    HashMap<String, Object> details = new HashMap<>();
    details.computeIfPresent("STATUS CODE", mock(BiFunction.class));
    details.put("STATUS CODE", "42");
    StatusInfo valueOfResult = StatusInfo.valueOf("Status Code", details);

    // Act and Assert
    assertNotEquals(valueOfResult, StatusInfo.valueOf("Status Code", new HashMap<>()));
  }

  /**
   * Method under test: {@link StatusInfo#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(StatusInfo.valueOf("Status Code", new HashMap<>()), null);
  }

  /**
   * Method under test: {@link StatusInfo#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(StatusInfo.valueOf("Status Code", new HashMap<>()), "Different type to StatusInfo");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StatusInfo#toString()}
   *   <li>{@link StatusInfo#getStatus()}
   * </ul>
   */
  @Test
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
