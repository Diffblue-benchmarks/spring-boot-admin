package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.mock.env.MockEnvironment;

class ClientPropertiesDiffblueTest {
  /**
   * Test {@link ClientProperties#getAdminUrl()}.
   * <ul>
   *   <li>Given {@link ClientProperties} (default constructor).</li>
   *   <li>Then return array length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#getAdminUrl()}
   */
  @Test
  @DisplayName("Test getAdminUrl(); given ClientProperties (default constructor); then return array length is zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String[] ClientProperties.getAdminUrl()"})
  void testGetAdminUrl_givenClientProperties_thenReturnArrayLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new ClientProperties()).getAdminUrl().length);
  }

  /**
   * Test {@link ClientProperties#getAdminUrl()}.
   * <ul>
   *   <li>Then return array of {@link String} with {@code https://example.org/example/Api Path}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#getAdminUrl()}
   */
  @Test
  @DisplayName("Test getAdminUrl(); then return array of String with 'https://example.org/example/Api Path'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String[] ClientProperties.getAdminUrl()"})
  void testGetAdminUrl_thenReturnArrayOfStringWithHttpsExampleOrgExampleApiPath() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    // Act and Assert
    assertArrayEquals(new String[]{"https://example.org/example/Api Path"}, clientProperties.getAdminUrl());
  }

  /**
   * Test {@link ClientProperties#isAutoDeregistration(Environment)}.
   * <ul>
   *   <li>When {@link MockEnvironment} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#isAutoDeregistration(Environment)}
   */
  @Test
  @DisplayName("Test isAutoDeregistration(Environment); when MockEnvironment (default constructor); then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.isAutoDeregistration(Environment)"})
  void testIsAutoDeregistration_whenMockEnvironment_thenReturnFalse() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();

    // Act and Assert
    assertFalse(clientProperties.isAutoDeregistration(new MockEnvironment()));
  }

  /**
   * Test {@link ClientProperties#isAutoDeregistration(Environment)}.
   * <ul>
   *   <li>When {@link StandardReactiveWebEnvironment#StandardReactiveWebEnvironment()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#isAutoDeregistration(Environment)}
   */
  @Test
  @DisplayName("Test isAutoDeregistration(Environment); when StandardReactiveWebEnvironment(); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.isAutoDeregistration(Environment)"})
  void testIsAutoDeregistration_whenStandardReactiveWebEnvironment_thenReturnTrue() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();

    // Act and Assert
    assertTrue(clientProperties.isAutoDeregistration(new StandardReactiveWebEnvironment()));
  }

  /**
   * Test {@link ClientProperties#equals(Object)}, and {@link ClientProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClientProperties#equals(Object)}
   *   <li>{@link ClientProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertEquals(clientProperties, clientProperties2);
    int expectedHashCodeResult = clientProperties.hashCode();
    assertEquals(expectedHashCodeResult, clientProperties2.hashCode());
  }

  /**
   * Test {@link ClientProperties#equals(Object)}, and {@link ClientProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClientProperties#equals(Object)}
   *   <li>{@link ClientProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword(null);
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword(null);
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertEquals(clientProperties, clientProperties2);
    int expectedHashCodeResult = clientProperties.hashCode();
    assertEquals(expectedHashCodeResult, clientProperties2.hashCode());
  }

  /**
   * Test {@link ClientProperties#equals(Object)}, and {@link ClientProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClientProperties#equals(Object)}
   *   <li>{@link ClientProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername(null);

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername(null);

    // Act and Assert
    assertEquals(clientProperties, clientProperties2);
    int expectedHashCodeResult = clientProperties.hashCode();
    assertEquals(expectedHashCodeResult, clientProperties2.hashCode());
  }

  /**
   * Test {@link ClientProperties#equals(Object)}, and {@link ClientProperties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClientProperties#equals(Object)}
   *   <li>{@link ClientProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    // Act and Assert
    assertEquals(clientProperties, clientProperties);
    int expectedHashCodeResult = clientProperties.hashCode();
    assertEquals(expectedHashCodeResult, clientProperties.hashCode());
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("janedoe");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(false);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("Api Path");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword(null);
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(false);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"Api Path"});
    clientProperties.setUsername("janedoe");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("Api Path");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername(null);

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath("Api Path");
    clientProperties2.setEnabled(true);
    clientProperties2.setPassword("iloveyou");
    clientProperties2.setRegisterOnce(true);
    clientProperties2.setUrl(new String[]{"https://example.org/example"});
    clientProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, null);
  }

  /**
   * Test {@link ClientProperties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ClientProperties.equals(Object)", "int ClientProperties.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(clientProperties, "Different type to ClientProperties");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClientProperties#setApiPath(String)}
   *   <li>{@link ClientProperties#setConnectTimeout(Duration)}
   *   <li>{@link ClientProperties#setEnabled(boolean)}
   *   <li>{@link ClientProperties#setPassword(String)}
   *   <li>{@link ClientProperties#setPeriod(Duration)}
   *   <li>{@link ClientProperties#setReadTimeout(Duration)}
   *   <li>{@link ClientProperties#setRegisterOnce(boolean)}
   *   <li>{@link ClientProperties#setUrl(String[])}
   *   <li>{@link ClientProperties#setUsername(String)}
   *   <li>{@link ClientProperties#setAutoDeregistration(Boolean)}
   *   <li>{@link ClientProperties#setAutoRegistration(boolean)}
   *   <li>{@link ClientProperties#toString()}
   *   <li>{@link ClientProperties#getApiPath()}
   *   <li>{@link ClientProperties#getAutoDeregistration()}
   *   <li>{@link ClientProperties#getConnectTimeout()}
   *   <li>{@link ClientProperties#getPassword()}
   *   <li>{@link ClientProperties#getPeriod()}
   *   <li>{@link ClientProperties#getReadTimeout()}
   *   <li>{@link ClientProperties#getUrl()}
   *   <li>{@link ClientProperties#getUsername()}
   *   <li>{@link ClientProperties#isAutoRegistration()}
   *   <li>{@link ClientProperties#isEnabled()}
   *   <li>{@link ClientProperties#isRegisterOnce()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ClientProperties.getApiPath()", "Boolean ClientProperties.getAutoDeregistration()",
      "Duration ClientProperties.getConnectTimeout()", "String ClientProperties.getPassword()",
      "Duration ClientProperties.getPeriod()", "Duration ClientProperties.getReadTimeout()",
      "String[] ClientProperties.getUrl()", "String ClientProperties.getUsername()",
      "boolean ClientProperties.isAutoRegistration()", "boolean ClientProperties.isEnabled()",
      "boolean ClientProperties.isRegisterOnce()", "void ClientProperties.setApiPath(String)",
      "void ClientProperties.setAutoDeregistration(Boolean)", "void ClientProperties.setAutoRegistration(boolean)",
      "void ClientProperties.setConnectTimeout(Duration)", "void ClientProperties.setEnabled(boolean)",
      "void ClientProperties.setPassword(String)", "void ClientProperties.setPeriod(Duration)",
      "void ClientProperties.setReadTimeout(Duration)", "void ClientProperties.setRegisterOnce(boolean)",
      "void ClientProperties.setUrl(String[])", "void ClientProperties.setUsername(String)",
      "String ClientProperties.toString()"})
  void testGettersAndSetters() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();

    // Act
    clientProperties.setApiPath("Api Path");
    clientProperties.setConnectTimeout(null);
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setPeriod(null);
    clientProperties.setReadTimeout(null);
    clientProperties.setRegisterOnce(true);
    String[] url = new String[]{"https://example.org/example"};
    clientProperties.setUrl(url);
    clientProperties.setUsername("janedoe");
    clientProperties.setAutoDeregistration(true);
    clientProperties.setAutoRegistration(true);
    String actualToStringResult = clientProperties.toString();
    String actualApiPath = clientProperties.getApiPath();
    Boolean actualAutoDeregistration = clientProperties.getAutoDeregistration();
    Duration actualConnectTimeout = clientProperties.getConnectTimeout();
    String actualPassword = clientProperties.getPassword();
    Duration actualPeriod = clientProperties.getPeriod();
    Duration actualReadTimeout = clientProperties.getReadTimeout();
    String[] actualUrl = clientProperties.getUrl();
    String actualUsername = clientProperties.getUsername();
    boolean actualIsAutoRegistrationResult = clientProperties.isAutoRegistration();
    boolean actualIsEnabledResult = clientProperties.isEnabled();

    // Assert
    assertEquals("Api Path", actualApiPath);
    assertEquals(
        "ClientProperties(url=[https://example.org/example], apiPath=Api Path, period=null, connectTimeout=null,"
            + " readTimeout=null, username=janedoe, password=iloveyou, autoDeregistration=true, autoRegistration=true,"
            + " registerOnce=true, enabled=true)",
        actualToStringResult);
    assertEquals("iloveyou", actualPassword);
    assertEquals("janedoe", actualUsername);
    assertNull(actualConnectTimeout);
    assertNull(actualPeriod);
    assertNull(actualReadTimeout);
    assertTrue(actualAutoDeregistration);
    assertTrue(actualIsAutoRegistrationResult);
    assertTrue(actualIsEnabledResult);
    assertTrue(clientProperties.isRegisterOnce());
    assertSame(url, actualUrl);
    assertArrayEquals(new String[]{"https://example.org/example"}, actualUrl);
  }

  /**
   * Test new {@link ClientProperties} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ClientProperties}
   */
  @Test
  @DisplayName("Test new ClientProperties (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ClientProperties.<init>()"})
  void testNewClientProperties() {
    // Arrange and Act
    ClientProperties actualClientProperties = new ClientProperties();

    // Assert
    assertEquals("instances", actualClientProperties.getApiPath());
    assertNull(actualClientProperties.getAutoDeregistration());
    assertNull(actualClientProperties.getPassword());
    assertNull(actualClientProperties.getUsername());
    assertEquals(0, actualClientProperties.getAdminUrl().length);
    assertEquals(0, actualClientProperties.getUrl().length);
    assertEquals(10000000000L, actualClientProperties.getPeriod().toNanos());
    assertEquals(5000000000L, actualClientProperties.getConnectTimeout().toNanos());
    assertEquals(5000000000L, actualClientProperties.getReadTimeout().toNanos());
    assertTrue(actualClientProperties.isAutoRegistration());
    assertTrue(actualClientProperties.isEnabled());
    assertTrue(actualClientProperties.isRegisterOnce());
  }
}
