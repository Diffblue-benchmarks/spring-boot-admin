package de.codecentric.boot.admin.server.domain.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StatusInfoDiffblueTest {
  /**
   * Test {@link StatusInfo#valueOf(String, Map)} with {@code statusCode}, {@code details}.
   *
   * <ul>
   *   <li>When {@code not blank}.
   *   <li>Then return Status is {@code NOT BLANK}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#valueOf(String, Map)}
   */
  @Test
  @DisplayName(
      "Test valueOf(String, Map) with 'statusCode', 'details'; when 'not blank'; then return Status is 'NOT BLANK'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.valueOf(String, Map)"})
  void testValueOfWithStatusCodeDetails_whenNotBlank_thenReturnStatusIsNotBlank() {
    // Arrange and Act
    StatusInfo actualValueOfResult = StatusInfo.valueOf("not blank", null);

    // Assert
    assertEquals("NOT BLANK", actualValueOfResult.getStatus());
    assertFalse(actualValueOfResult.isDown());
    assertFalse(actualValueOfResult.isOffline());
    assertFalse(actualValueOfResult.isUnknown());
    assertFalse(actualValueOfResult.isUp());
    assertTrue(actualValueOfResult.getDetails().isEmpty());
  }

  /**
   * Test {@link StatusInfo#valueOf(String, Map)} with {@code statusCode}, {@code details}.
   *
   * <ul>
   *   <li>When {@code Status Code}.
   *   <li>Then return Status is {@code STATUS CODE}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#valueOf(String, Map)}
   */
  @Test
  @DisplayName(
      "Test valueOf(String, Map) with 'statusCode', 'details'; when 'Status Code'; then return Status is 'STATUS CODE'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.valueOf(String, Map)"})
  void testValueOfWithStatusCodeDetails_whenStatusCode_thenReturnStatusIsStatusCode() {
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
   * Test {@link StatusInfo#valueOf(String)} with {@code statusCode}.
   *
   * <ul>
   *   <li>When {@code Status Code}.
   *   <li>Then return Status is {@code STATUS CODE}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#valueOf(String)}
   */
  @Test
  @DisplayName(
      "Test valueOf(String) with 'statusCode'; when 'Status Code'; then return Status is 'STATUS CODE'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.valueOf(String)"})
  void testValueOfWithStatusCode_whenStatusCode_thenReturnStatusIsStatusCode() {
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
   *
   * <p>Method under test: {@link StatusInfo#ofUnknown()}
   */
  @Test
  @DisplayName("Test ofUnknown()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofUnknown()"})
  void testOfUnknown() {
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
   *
   * <p>Method under test: {@link StatusInfo#ofUp()}
   */
  @Test
  @DisplayName("Test ofUp()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofUp()"})
  void testOfUp() {
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
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#ofUp(Map)}
   */
  @Test
  @DisplayName("Test ofUp(Map) with 'Map'; when HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofUp(Map)"})
  void testOfUpWithMap_whenHashMap() {
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
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#ofUp(Map)}
   */
  @Test
  @DisplayName("Test ofUp(Map) with 'Map'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofUp(Map)"})
  void testOfUpWithMap_whenNull() {
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
   *
   * <p>Method under test: {@link StatusInfo#ofDown()}
   */
  @Test
  @DisplayName("Test ofDown()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofDown()"})
  void testOfDown() {
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
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#ofDown(Map)}
   */
  @Test
  @DisplayName("Test ofDown(Map) with 'Map'; when HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofDown(Map)"})
  void testOfDownWithMap_whenHashMap() {
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
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#ofDown(Map)}
   */
  @Test
  @DisplayName("Test ofDown(Map) with 'Map'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofDown(Map)"})
  void testOfDownWithMap_whenNull() {
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
   *
   * <p>Method under test: {@link StatusInfo#ofOffline()}
   */
  @Test
  @DisplayName("Test ofOffline()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofOffline()"})
  void testOfOffline() {
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
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#ofOffline(Map)}
   */
  @Test
  @DisplayName("Test ofOffline(Map) with 'Map'; when HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofOffline(Map)"})
  void testOfOfflineWithMap_whenHashMap() {
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
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#ofOffline(Map)}
   */
  @Test
  @DisplayName("Test ofOffline(Map) with 'Map'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.ofOffline(Map)"})
  void testOfOfflineWithMap_whenNull() {
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
   *
   * <p>Method under test: {@link StatusInfo#getDetails()}
   */
  @Test
  @DisplayName("Test getDetails()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map StatusInfo.getDetails()"})
  void testGetDetails() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf("Status Code", new HashMap<>()).getDetails().isEmpty());
  }

  /**
   * Test {@link StatusInfo#isUp()}.
   *
   * <ul>
   *   <li>Given valueOf {@code Status Code} and {@link HashMap#HashMap()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#isUp()}
   */
  @Test
  @DisplayName("Test isUp(); given valueOf 'Status Code' and HashMap(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean StatusInfo.isUp()"})
  void testIsUp_givenValueOfStatusCodeAndHashMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isUp());
  }

  /**
   * Test {@link StatusInfo#isUp()}.
   *
   * <ul>
   *   <li>Given valueOf {@link StatusInfo#STATUS_UP} and {@link HashMap#HashMap()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#isUp()}
   */
  @Test
  @DisplayName("Test isUp(); given valueOf STATUS_UP and HashMap(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean StatusInfo.isUp()"})
  void testIsUp_givenValueOfStatus_upAndHashMap_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_UP, new HashMap<>()).isUp());
  }

  /**
   * Test {@link StatusInfo#isOffline()}.
   *
   * <ul>
   *   <li>Given valueOf {@code Status Code} and {@link HashMap#HashMap()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#isOffline()}
   */
  @Test
  @DisplayName("Test isOffline(); given valueOf 'Status Code' and HashMap(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean StatusInfo.isOffline()"})
  void testIsOffline_givenValueOfStatusCodeAndHashMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isOffline());
  }

  /**
   * Test {@link StatusInfo#isOffline()}.
   *
   * <ul>
   *   <li>Given valueOf {@link StatusInfo#STATUS_OFFLINE} and {@link HashMap#HashMap()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#isOffline()}
   */
  @Test
  @DisplayName("Test isOffline(); given valueOf STATUS_OFFLINE and HashMap(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean StatusInfo.isOffline()"})
  void testIsOffline_givenValueOfStatus_offlineAndHashMap_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_OFFLINE, new HashMap<>()).isOffline());
  }

  /**
   * Test {@link StatusInfo#isDown()}.
   *
   * <ul>
   *   <li>Given valueOf {@code Status Code} and {@link HashMap#HashMap()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#isDown()}
   */
  @Test
  @DisplayName("Test isDown(); given valueOf 'Status Code' and HashMap(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean StatusInfo.isDown()"})
  void testIsDown_givenValueOfStatusCodeAndHashMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isDown());
  }

  /**
   * Test {@link StatusInfo#isDown()}.
   *
   * <ul>
   *   <li>Given valueOf {@link StatusInfo#STATUS_DOWN} and {@link HashMap#HashMap()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#isDown()}
   */
  @Test
  @DisplayName("Test isDown(); given valueOf STATUS_DOWN and HashMap(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean StatusInfo.isDown()"})
  void testIsDown_givenValueOfStatus_downAndHashMap_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_DOWN, new HashMap<>()).isDown());
  }

  /**
   * Test {@link StatusInfo#isUnknown()}.
   *
   * <ul>
   *   <li>Given valueOf {@code Status Code} and {@link HashMap#HashMap()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#isUnknown()}
   */
  @Test
  @DisplayName("Test isUnknown(); given valueOf 'Status Code' and HashMap(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean StatusInfo.isUnknown()"})
  void testIsUnknown_givenValueOfStatusCodeAndHashMap_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(StatusInfo.valueOf("Status Code", new HashMap<>()).isUnknown());
  }

  /**
   * Test {@link StatusInfo#isUnknown()}.
   *
   * <ul>
   *   <li>Given valueOf {@link StatusInfo#STATUS_UNKNOWN} and {@link HashMap#HashMap()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#isUnknown()}
   */
  @Test
  @DisplayName("Test isUnknown(); given valueOf STATUS_UNKNOWN and HashMap(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean StatusInfo.isUnknown()"})
  void testIsUnknown_givenValueOfStatus_unknownAndHashMap_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(StatusInfo.valueOf(StatusInfo.STATUS_UNKNOWN, new HashMap<>()).isUnknown());
  }

  /**
   * Test {@link StatusInfo#severity()}.
   *
   * <p>Method under test: {@link StatusInfo#severity()}
   */
  @Test
  @DisplayName("Test severity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Comparator StatusInfo.severity()"})
  void testSeverity() {
    // Arrange and Act
    Comparator<String> actualSeverityResult = StatusInfo.severity();

    // Assert
    assertEquals(0, actualSeverityResult.compare("foo", "foo"));
  }

  /**
   * Test {@link StatusInfo#from(Map)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code status} is {@code Body}.
   *   <li>Then return Status is {@code BODY}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given 'foo'; when HashMap() 'status' is 'Body'; then return Status is 'BODY'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.from(Map)"})
  void testFrom_givenFoo_whenHashMapStatusIsBody_thenReturnStatusIsBody() {
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
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>When {@link HashMap#HashMap()} {@code details} is {@link HashMap#HashMap()}.
   *   <li>Then return Status is {@code BODY}.
   * </ul>
   *
   * <p>Method under test: {@link StatusInfo#from(Map)}
   */
  @Test
  @DisplayName(
      "Test from(Map); given HashMap(); when HashMap() 'details' is HashMap(); then return Status is 'BODY'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StatusInfo StatusInfo.from(Map)"})
  void testFrom_givenHashMap_whenHashMapDetailsIsHashMap_thenReturnStatusIsBody() {
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
}
