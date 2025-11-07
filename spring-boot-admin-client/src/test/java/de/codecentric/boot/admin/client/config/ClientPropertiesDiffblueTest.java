package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.util.StringValueResolver;

class ClientPropertiesDiffblueTest {
  /**
   * Method under test: {@link ClientProperties#getAdminUrl()}
   */
  @Test
  void testGetAdminUrl() {
    // Arrange, Act and Assert
    assertEquals(0, (new ClientProperties()).getAdminUrl().length);
  }

  /**
   * Method under test: {@link ClientProperties#getAdminUrl()}
   */
  @Test
  void testGetAdminUrl2() {
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
   * Method under test: {@link ClientProperties#isAutoDeregistration(Environment)}
   */
  @Test
  void testIsAutoDeregistration() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();

    // Act and Assert
    assertTrue(clientProperties.isAutoDeregistration(new StandardReactiveWebEnvironment()));
  }

  /**
   * Method under test: {@link ClientProperties#isAutoDeregistration(Environment)}
   */
  @Test
  void testIsAutoDeregistration2() {
    // Arrange, Act and Assert
    assertFalse((new ClientProperties()).isAutoDeregistration(null));
  }

  /**
   * Method under test: {@link ClientProperties#isAutoDeregistration(Environment)}
   */
  @Test
  void testIsAutoDeregistration3() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();

    StandardReactiveWebEnvironment environment = new StandardReactiveWebEnvironment();
    environment.setConversionService(new DefaultFormattingConversionService(mock(StringValueResolver.class), true));

    // Act and Assert
    assertTrue(clientProperties.isAutoDeregistration(environment));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ClientProperties#equals(Object)}
   *   <li>{@link ClientProperties#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link ClientProperties#equals(Object)}
   *   <li>{@link ClientProperties#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath(null);
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[]{"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    ClientProperties clientProperties2 = new ClientProperties();
    clientProperties2.setApiPath(null);
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
   * Methods under test:
   * <ul>
   *   <li>{@link ClientProperties#equals(Object)}
   *   <li>{@link ClientProperties#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
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
   * Methods under test:
   * <ul>
   *   <li>{@link ClientProperties#equals(Object)}
   *   <li>{@link ClientProperties#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath(null);
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setPeriod(null);
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
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setConnectTimeout(null);
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
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setReadTimeout(null);
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
    assertNotEquals(clientProperties, clientProperties2);
  }

  /**
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link ClientProperties#equals(Object)}
   */
  @Test
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
    clientProperties.getConnectTimeout();
    String actualPassword = clientProperties.getPassword();
    clientProperties.getPeriod();
    clientProperties.getReadTimeout();
    String[] actualUrl = clientProperties.getUrl();
    String actualUsername = clientProperties.getUsername();
    boolean actualIsAutoRegistrationResult = clientProperties.isAutoRegistration();
    boolean actualIsEnabledResult = clientProperties.isEnabled();

    // Assert that nothing has changed
    assertEquals("Api Path", actualApiPath);
    assertEquals(
        "ClientProperties(url=[https://example.org/example], apiPath=Api Path, period=null, connectTimeout=null,"
            + " readTimeout=null, username=janedoe, password=iloveyou, autoDeregistration=true, autoRegistration=true,"
            + " registerOnce=true, enabled=true)",
        actualToStringResult);
    assertEquals("iloveyou", actualPassword);
    assertEquals("janedoe", actualUsername);
    assertTrue(actualAutoDeregistration);
    assertTrue(actualIsAutoRegistrationResult);
    assertTrue(actualIsEnabledResult);
    assertTrue(clientProperties.isRegisterOnce());
    assertSame(url, actualUrl);
    assertArrayEquals(new String[]{"https://example.org/example"}, actualUrl);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ClientProperties}
   */
  @Test
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
