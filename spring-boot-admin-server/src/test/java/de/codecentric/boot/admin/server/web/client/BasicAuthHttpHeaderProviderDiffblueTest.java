package de.codecentric.boot.admin.server.web.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {BasicAuthHttpHeaderProvider.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BasicAuthHttpHeaderProviderDiffblueTest {
  @Autowired
  private BasicAuthHttpHeaderProvider basicAuthHttpHeaderProvider;

  /**
   * Method under test: {@link BasicAuthHttpHeaderProvider#encode(String, String)}
   */
  @Test
  public void testEncode() {
    // Arrange, Act and Assert
    assertEquals("Basic amFuZWRvZTpodHRwczovL2V4YW1wbGUub3JnL2V4YW1wbGU=",
        basicAuthHttpHeaderProvider.encode("janedoe", "https://example.org/example"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BasicAuthHttpHeaderProvider.InstanceCredentials#equals(Object)}
   *   <li>{@link BasicAuthHttpHeaderProvider.InstanceCredentials#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceCredentialsEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    BasicAuthHttpHeaderProvider.InstanceCredentials instanceCredentials = new BasicAuthHttpHeaderProvider.InstanceCredentials(
        "janedoe", "https://example.org/example");
    BasicAuthHttpHeaderProvider.InstanceCredentials instanceCredentials2 = new BasicAuthHttpHeaderProvider.InstanceCredentials(
        "janedoe", "https://example.org/example");

    // Act and Assert
    assertEquals(instanceCredentials, instanceCredentials2);
    int expectedHashCodeResult = instanceCredentials.hashCode();
    assertEquals(expectedHashCodeResult, instanceCredentials2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BasicAuthHttpHeaderProvider.InstanceCredentials#equals(Object)}
   *   <li>{@link BasicAuthHttpHeaderProvider.InstanceCredentials#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceCredentialsEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    BasicAuthHttpHeaderProvider.InstanceCredentials instanceCredentials = new BasicAuthHttpHeaderProvider.InstanceCredentials();
    BasicAuthHttpHeaderProvider.InstanceCredentials instanceCredentials2 = new BasicAuthHttpHeaderProvider.InstanceCredentials();

    // Act and Assert
    assertEquals(instanceCredentials, instanceCredentials2);
    int expectedHashCodeResult = instanceCredentials.hashCode();
    assertEquals(expectedHashCodeResult, instanceCredentials2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BasicAuthHttpHeaderProvider.InstanceCredentials#equals(Object)}
   *   <li>{@link BasicAuthHttpHeaderProvider.InstanceCredentials#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceCredentialsEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    BasicAuthHttpHeaderProvider.InstanceCredentials instanceCredentials = new BasicAuthHttpHeaderProvider.InstanceCredentials(
        "janedoe", "https://example.org/example");

    // Act and Assert
    assertEquals(instanceCredentials, instanceCredentials);
    int expectedHashCodeResult = instanceCredentials.hashCode();
    assertEquals(expectedHashCodeResult, instanceCredentials.hashCode());
  }

  /**
   * Method under test:
   * {@link BasicAuthHttpHeaderProvider.InstanceCredentials#equals(Object)}
   */
  @Test
  public void testInstanceCredentialsEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    BasicAuthHttpHeaderProvider.InstanceCredentials instanceCredentials = new BasicAuthHttpHeaderProvider.InstanceCredentials(
        "https://example.org/example", "https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceCredentials,
        new BasicAuthHttpHeaderProvider.InstanceCredentials("janedoe", "https://example.org/example"));
  }

  /**
   * Method under test:
   * {@link BasicAuthHttpHeaderProvider.InstanceCredentials#equals(Object)}
   */
  @Test
  public void testInstanceCredentialsEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    BasicAuthHttpHeaderProvider.InstanceCredentials instanceCredentials = new BasicAuthHttpHeaderProvider.InstanceCredentials(
        "janedoe", "iloveyou");

    // Act and Assert
    assertNotEquals(instanceCredentials,
        new BasicAuthHttpHeaderProvider.InstanceCredentials("janedoe", "https://example.org/example"));
  }

  /**
   * Method under test:
   * {@link BasicAuthHttpHeaderProvider.InstanceCredentials#equals(Object)}
   */
  @Test
  public void testInstanceCredentialsEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    BasicAuthHttpHeaderProvider.InstanceCredentials instanceCredentials = new BasicAuthHttpHeaderProvider.InstanceCredentials();

    // Act and Assert
    assertNotEquals(instanceCredentials,
        new BasicAuthHttpHeaderProvider.InstanceCredentials("janedoe", "https://example.org/example"));
  }

  /**
   * Method under test:
   * {@link BasicAuthHttpHeaderProvider.InstanceCredentials#equals(Object)}
   */
  @Test
  public void testInstanceCredentialsEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    BasicAuthHttpHeaderProvider.InstanceCredentials instanceCredentials = new BasicAuthHttpHeaderProvider.InstanceCredentials();
    instanceCredentials.setUserName("janedoe");

    // Act and Assert
    assertNotEquals(instanceCredentials,
        new BasicAuthHttpHeaderProvider.InstanceCredentials("janedoe", "https://example.org/example"));
  }

  /**
   * Method under test:
   * {@link BasicAuthHttpHeaderProvider.InstanceCredentials#equals(Object)}
   */
  @Test
  public void testInstanceCredentialsEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BasicAuthHttpHeaderProvider.InstanceCredentials("janedoe", "https://example.org/example"),
        null);
  }

  /**
   * Method under test:
   * {@link BasicAuthHttpHeaderProvider.InstanceCredentials#equals(Object)}
   */
  @Test
  public void testInstanceCredentialsEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new BasicAuthHttpHeaderProvider.InstanceCredentials("janedoe", "https://example.org/example"),
        "Different type to InstanceCredentials");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link BasicAuthHttpHeaderProvider.InstanceCredentials#InstanceCredentials()}
   *   <li>{@link BasicAuthHttpHeaderProvider.InstanceCredentials#toString()}
   *   <li>{@link BasicAuthHttpHeaderProvider.InstanceCredentials#getUserName()}
   *   <li>{@link BasicAuthHttpHeaderProvider.InstanceCredentials#getUserPassword()}
   * </ul>
   */
  @Test
  public void testInstanceCredentialsGettersAndSetters() {
    // Arrange and Act
    BasicAuthHttpHeaderProvider.InstanceCredentials actualInstanceCredentials = new BasicAuthHttpHeaderProvider.InstanceCredentials();
    String actualToStringResult = actualInstanceCredentials.toString();
    String actualUserName = actualInstanceCredentials.getUserName();

    // Assert
    assertEquals("BasicAuthHttpHeaderProvider.InstanceCredentials(userName=null, userPassword=null)",
        actualToStringResult);
    assertNull(actualUserName);
    assertNull(actualInstanceCredentials.getUserPassword());
  }

  /**
   * Method under test:
   * {@link BasicAuthHttpHeaderProvider.InstanceCredentials#InstanceCredentials(String, String)}
   */
  @Test
  public void testInstanceCredentialsNewInstanceCredentials() {
    // Arrange and Act
    BasicAuthHttpHeaderProvider.InstanceCredentials actualInstanceCredentials = new BasicAuthHttpHeaderProvider.InstanceCredentials(
        "janedoe", "https://example.org/example");

    // Assert
    assertEquals("https://example.org/example", actualInstanceCredentials.getUserPassword());
    assertEquals("janedoe", actualInstanceCredentials.getUserName());
  }
}
