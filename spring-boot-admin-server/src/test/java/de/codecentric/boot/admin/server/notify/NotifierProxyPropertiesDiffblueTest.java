package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class NotifierProxyPropertiesDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NotifierProxyProperties#equals(Object)}
   *   <li>{@link NotifierProxyProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertEquals(notifierProxyProperties, notifierProxyProperties2);
    int expectedHashCodeResult = notifierProxyProperties.hashCode();
    assertEquals(expectedHashCodeResult, notifierProxyProperties2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NotifierProxyProperties#equals(Object)}
   *   <li>{@link NotifierProxyProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost(null);
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost(null);
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertEquals(notifierProxyProperties, notifierProxyProperties2);
    int expectedHashCodeResult = notifierProxyProperties.hashCode();
    assertEquals(expectedHashCodeResult, notifierProxyProperties2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NotifierProxyProperties#equals(Object)}
   *   <li>{@link NotifierProxyProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword(null);
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword(null);
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertEquals(notifierProxyProperties, notifierProxyProperties2);
    int expectedHashCodeResult = notifierProxyProperties.hashCode();
    assertEquals(expectedHashCodeResult, notifierProxyProperties2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NotifierProxyProperties#equals(Object)}
   *   <li>{@link NotifierProxyProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername(null);

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername(null);

    // Act and Assert
    assertEquals(notifierProxyProperties, notifierProxyProperties2);
    int expectedHashCodeResult = notifierProxyProperties.hashCode();
    assertEquals(expectedHashCodeResult, notifierProxyProperties2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NotifierProxyProperties#equals(Object)}
   *   <li>{@link NotifierProxyProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    // Act and Assert
    assertEquals(notifierProxyProperties, notifierProxyProperties);
    int expectedHashCodeResult = notifierProxyProperties.hashCode();
    assertEquals(expectedHashCodeResult, notifierProxyProperties.hashCode());
  }

  /**
   * Method under test: {@link NotifierProxyProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("janedoe");
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(notifierProxyProperties, notifierProxyProperties2);
  }

  /**
   * Method under test: {@link NotifierProxyProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost(null);
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(notifierProxyProperties, notifierProxyProperties2);
  }

  /**
   * Method under test: {@link NotifierProxyProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword("localhost");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(notifierProxyProperties, notifierProxyProperties2);
  }

  /**
   * Method under test: {@link NotifierProxyProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword(null);
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(notifierProxyProperties, notifierProxyProperties2);
  }

  /**
   * Method under test: {@link NotifierProxyProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(1);
    notifierProxyProperties.setUsername("janedoe");

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(notifierProxyProperties, notifierProxyProperties2);
  }

  /**
   * Method under test: {@link NotifierProxyProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("localhost");

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(notifierProxyProperties, notifierProxyProperties2);
  }

  /**
   * Method under test: {@link NotifierProxyProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername(null);

    NotifierProxyProperties notifierProxyProperties2 = new NotifierProxyProperties();
    notifierProxyProperties2.setHost("localhost");
    notifierProxyProperties2.setPassword("iloveyou");
    notifierProxyProperties2.setPort(8080);
    notifierProxyProperties2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(notifierProxyProperties, notifierProxyProperties2);
  }

  /**
   * Method under test: {@link NotifierProxyProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(notifierProxyProperties, null);
  }

  /**
   * Method under test: {@link NotifierProxyProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    NotifierProxyProperties notifierProxyProperties = new NotifierProxyProperties();
    notifierProxyProperties.setHost("localhost");
    notifierProxyProperties.setPassword("iloveyou");
    notifierProxyProperties.setPort(8080);
    notifierProxyProperties.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(notifierProxyProperties, "Different type to NotifierProxyProperties");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link NotifierProxyProperties}
   *   <li>{@link NotifierProxyProperties#setHost(String)}
   *   <li>{@link NotifierProxyProperties#setPassword(String)}
   *   <li>{@link NotifierProxyProperties#setPort(int)}
   *   <li>{@link NotifierProxyProperties#setUsername(String)}
   *   <li>{@link NotifierProxyProperties#toString()}
   *   <li>{@link NotifierProxyProperties#getHost()}
   *   <li>{@link NotifierProxyProperties#getPassword()}
   *   <li>{@link NotifierProxyProperties#getPort()}
   *   <li>{@link NotifierProxyProperties#getUsername()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    NotifierProxyProperties actualNotifierProxyProperties = new NotifierProxyProperties();
    actualNotifierProxyProperties.setHost("localhost");
    actualNotifierProxyProperties.setPassword("iloveyou");
    actualNotifierProxyProperties.setPort(8080);
    actualNotifierProxyProperties.setUsername("janedoe");
    String actualToStringResult = actualNotifierProxyProperties.toString();
    String actualHost = actualNotifierProxyProperties.getHost();
    String actualPassword = actualNotifierProxyProperties.getPassword();
    int actualPort = actualNotifierProxyProperties.getPort();

    // Assert that nothing has changed
    assertEquals("NotifierProxyProperties(host=localhost, port=8080, username=janedoe, password=iloveyou)",
        actualToStringResult);
    assertEquals("iloveyou", actualPassword);
    assertEquals("janedoe", actualNotifierProxyProperties.getUsername());
    assertEquals("localhost", actualHost);
    assertEquals(8080, actualPort);
  }
}
