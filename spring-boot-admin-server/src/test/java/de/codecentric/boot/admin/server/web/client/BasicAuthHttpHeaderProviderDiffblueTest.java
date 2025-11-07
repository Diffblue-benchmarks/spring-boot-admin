package de.codecentric.boot.admin.server.web.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.web.client.BasicAuthHttpHeaderProvider.InstanceCredentials;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
   * Test {@link BasicAuthHttpHeaderProvider#encode(String, String)}.
   * <p>
   * Method under test: {@link BasicAuthHttpHeaderProvider#encode(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String BasicAuthHttpHeaderProvider.encode(String, String)"})
  public void testEncode() {
    // Arrange, Act and Assert
    assertEquals("Basic amFuZWRvZTpodHRwczovL2V4YW1wbGUub3JnL2V4YW1wbGU=",
        basicAuthHttpHeaderProvider.encode("janedoe", "https://example.org/example"));
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#equals(Object)}, and {@link InstanceCredentials#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceCredentials#equals(Object)}
   *   <li>{@link InstanceCredentials#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceCredentials.equals(Object)", "int InstanceCredentials.hashCode()"})
  public void testInstanceCredentialsEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InstanceCredentials instanceCredentials = new InstanceCredentials("janedoe", "https://example.org/example");
    InstanceCredentials instanceCredentials2 = new InstanceCredentials("janedoe", "https://example.org/example");

    // Act and Assert
    assertEquals(instanceCredentials, instanceCredentials2);
    int expectedHashCodeResult = instanceCredentials.hashCode();
    assertEquals(expectedHashCodeResult, instanceCredentials2.hashCode());
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#equals(Object)}, and {@link InstanceCredentials#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceCredentials#equals(Object)}
   *   <li>{@link InstanceCredentials#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceCredentials.equals(Object)", "int InstanceCredentials.hashCode()"})
  public void testInstanceCredentialsEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    InstanceCredentials instanceCredentials = new InstanceCredentials();
    InstanceCredentials instanceCredentials2 = new InstanceCredentials();

    // Act and Assert
    assertEquals(instanceCredentials, instanceCredentials2);
    int expectedHashCodeResult = instanceCredentials.hashCode();
    assertEquals(expectedHashCodeResult, instanceCredentials2.hashCode());
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#equals(Object)}, and {@link InstanceCredentials#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceCredentials#equals(Object)}
   *   <li>{@link InstanceCredentials#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceCredentials.equals(Object)", "int InstanceCredentials.hashCode()"})
  public void testInstanceCredentialsEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceCredentials instanceCredentials = new InstanceCredentials("janedoe", "https://example.org/example");

    // Act and Assert
    assertEquals(instanceCredentials, instanceCredentials);
    int expectedHashCodeResult = instanceCredentials.hashCode();
    assertEquals(expectedHashCodeResult, instanceCredentials.hashCode());
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceCredentials#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceCredentials.equals(Object)", "int InstanceCredentials.hashCode()"})
  public void testInstanceCredentialsEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceCredentials instanceCredentials = new InstanceCredentials("https://example.org/example",
        "https://example.org/example");

    // Act and Assert
    assertNotEquals(instanceCredentials, new InstanceCredentials("janedoe", "https://example.org/example"));
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceCredentials#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceCredentials.equals(Object)", "int InstanceCredentials.hashCode()"})
  public void testInstanceCredentialsEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InstanceCredentials instanceCredentials = new InstanceCredentials("janedoe", "iloveyou");

    // Act and Assert
    assertNotEquals(instanceCredentials, new InstanceCredentials("janedoe", "https://example.org/example"));
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceCredentials#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceCredentials.equals(Object)", "int InstanceCredentials.hashCode()"})
  public void testInstanceCredentialsEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    InstanceCredentials instanceCredentials = new InstanceCredentials();

    // Act and Assert
    assertNotEquals(instanceCredentials, new InstanceCredentials("janedoe", "https://example.org/example"));
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceCredentials#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceCredentials.equals(Object)", "int InstanceCredentials.hashCode()"})
  public void testInstanceCredentialsEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    InstanceCredentials instanceCredentials = new InstanceCredentials();
    instanceCredentials.setUserName("janedoe");

    // Act and Assert
    assertNotEquals(instanceCredentials, new InstanceCredentials("janedoe", "https://example.org/example"));
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceCredentials#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceCredentials.equals(Object)", "int InstanceCredentials.hashCode()"})
  public void testInstanceCredentialsEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceCredentials("janedoe", "https://example.org/example"), null);
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceCredentials#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceCredentials.equals(Object)", "int InstanceCredentials.hashCode()"})
  public void testInstanceCredentialsEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new InstanceCredentials("janedoe", "https://example.org/example"),
        "Different type to InstanceCredentials");
  }

  /**
   * Test InstanceCredentials getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceCredentials#InstanceCredentials()}
   *   <li>{@link InstanceCredentials#toString()}
   *   <li>{@link InstanceCredentials#getUserName()}
   *   <li>{@link InstanceCredentials#getUserPassword()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceCredentials.<init>()", "String InstanceCredentials.getUserName()",
      "String InstanceCredentials.getUserPassword()", "String InstanceCredentials.toString()"})
  public void testInstanceCredentialsGettersAndSetters() {
    // Arrange and Act
    InstanceCredentials actualInstanceCredentials = new InstanceCredentials();
    String actualToStringResult = actualInstanceCredentials.toString();
    String actualUserName = actualInstanceCredentials.getUserName();

    // Assert
    assertEquals("BasicAuthHttpHeaderProvider.InstanceCredentials(userName=null, userPassword=null)",
        actualToStringResult);
    assertNull(actualUserName);
    assertNull(actualInstanceCredentials.getUserPassword());
  }

  /**
   * Test InstanceCredentials {@link InstanceCredentials#InstanceCredentials(String, String)}.
   * <p>
   * Method under test: {@link InstanceCredentials#InstanceCredentials(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceCredentials.<init>(String, String)"})
  public void testInstanceCredentialsNewInstanceCredentials() {
    // Arrange and Act
    InstanceCredentials actualInstanceCredentials = new InstanceCredentials("janedoe", "https://example.org/example");

    // Assert
    assertEquals("https://example.org/example", actualInstanceCredentials.getUserPassword());
    assertEquals("janedoe", actualInstanceCredentials.getUserName());
  }
}
