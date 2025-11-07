package de.codecentric.boot.admin.server.config;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.config.AdminServerProperties.InstanceAuthProperties;
import de.codecentric.boot.admin.server.config.AdminServerProperties.InstanceProxyProperties;
import de.codecentric.boot.admin.server.config.AdminServerProperties.MonitorProperties;
import de.codecentric.boot.admin.server.config.AdminServerProperties.ServerProperties;
import de.codecentric.boot.admin.server.web.client.BasicAuthHttpHeaderProvider;
import de.codecentric.boot.admin.server.web.client.BasicAuthHttpHeaderProvider.InstanceCredentials;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AdminServerPropertiesDiffblueTest {
  /**
   * Test InstanceAuthProperties {@link InstanceAuthProperties#equals(Object)}, and {@link InstanceAuthProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceAuthProperties#equals(Object)}
   *   <li>{@link InstanceAuthProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceAuthProperties.equals(Object)", "int InstanceAuthProperties.hashCode()"})
  public void testInstanceAuthPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InstanceAuthProperties instanceAuthProperties = new InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    InstanceAuthProperties instanceAuthProperties2 = new InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertEquals(instanceAuthProperties, instanceAuthProperties2);
    int expectedHashCodeResult = instanceAuthProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceAuthProperties2.hashCode());
  }

  /**
   * Test InstanceAuthProperties {@link InstanceAuthProperties#equals(Object)}, and {@link InstanceAuthProperties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceAuthProperties#equals(Object)}
   *   <li>{@link InstanceAuthProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceAuthProperties.equals(Object)", "int InstanceAuthProperties.hashCode()"})
  public void testInstanceAuthPropertiesEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceAuthProperties instanceAuthProperties = new InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    // Act and Assert
    assertEquals(instanceAuthProperties, instanceAuthProperties);
    int expectedHashCodeResult = instanceAuthProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceAuthProperties.hashCode());
  }

  /**
   * Test InstanceAuthProperties {@link InstanceAuthProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceAuthProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceAuthProperties.equals(Object)", "int InstanceAuthProperties.hashCode()"})
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    InstanceAuthProperties instanceAuthProperties = new InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("janedoe");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    InstanceAuthProperties instanceAuthProperties2 = new InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Test InstanceAuthProperties {@link InstanceAuthProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceAuthProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceAuthProperties.equals(Object)", "int InstanceAuthProperties.hashCode()"})
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InstanceAuthProperties instanceAuthProperties = new InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("iloveyou");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    InstanceAuthProperties instanceAuthProperties2 = new InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Test InstanceAuthProperties {@link InstanceAuthProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceAuthProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceAuthProperties.equals(Object)", "int InstanceAuthProperties.hashCode()"})
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    InstanceAuthProperties instanceAuthProperties = new InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(false);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    InstanceAuthProperties instanceAuthProperties2 = new InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Test InstanceAuthProperties {@link InstanceAuthProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceAuthProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceAuthProperties.equals(Object)", "int InstanceAuthProperties.hashCode()"})
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    HashMap<String, InstanceCredentials> serviceMap = new HashMap<>();
    serviceMap.put("janedoe", new InstanceCredentials("janedoe", "https://example.org/example"));

    InstanceAuthProperties instanceAuthProperties = new InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(serviceMap);

    InstanceAuthProperties instanceAuthProperties2 = new InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Test InstanceAuthProperties {@link InstanceAuthProperties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceAuthProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceAuthProperties.equals(Object)", "int InstanceAuthProperties.hashCode()"})
  public void testInstanceAuthPropertiesEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InstanceAuthProperties instanceAuthProperties = new InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, null);
  }

  /**
   * Test InstanceAuthProperties {@link InstanceAuthProperties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceAuthProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceAuthProperties.equals(Object)", "int InstanceAuthProperties.hashCode()"})
  public void testInstanceAuthPropertiesEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InstanceAuthProperties instanceAuthProperties = new InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, "Different type to InstanceAuthProperties");
  }

  /**
   * Test InstanceAuthProperties getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link InstanceAuthProperties}
   *   <li>{@link InstanceAuthProperties#setDefaultPassword(String)}
   *   <li>{@link InstanceAuthProperties#setDefaultUserName(String)}
   *   <li>{@link InstanceAuthProperties#setEnabled(boolean)}
   *   <li>{@link InstanceAuthProperties#setServiceMap(Map)}
   *   <li>{@link InstanceAuthProperties#toString()}
   *   <li>{@link InstanceAuthProperties#getDefaultPassword()}
   *   <li>{@link InstanceAuthProperties#getDefaultUserName()}
   *   <li>{@link InstanceAuthProperties#getServiceMap()}
   *   <li>{@link InstanceAuthProperties#isEnabled()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceAuthProperties.<init>()", "String InstanceAuthProperties.getDefaultPassword()",
      "String InstanceAuthProperties.getDefaultUserName()", "Map InstanceAuthProperties.getServiceMap()",
      "boolean InstanceAuthProperties.isEnabled()", "void InstanceAuthProperties.setDefaultPassword(String)",
      "void InstanceAuthProperties.setDefaultUserName(String)", "void InstanceAuthProperties.setEnabled(boolean)",
      "void InstanceAuthProperties.setServiceMap(Map)", "String InstanceAuthProperties.toString()"})
  public void testInstanceAuthPropertiesGettersAndSetters() {
    // Arrange and Act
    InstanceAuthProperties actualInstanceAuthProperties = new InstanceAuthProperties();
    actualInstanceAuthProperties.setDefaultPassword("iloveyou");
    actualInstanceAuthProperties.setDefaultUserName("janedoe");
    actualInstanceAuthProperties.setEnabled(true);
    HashMap<String, InstanceCredentials> serviceMap = new HashMap<>();
    actualInstanceAuthProperties.setServiceMap(serviceMap);
    String actualToStringResult = actualInstanceAuthProperties.toString();
    String actualDefaultPassword = actualInstanceAuthProperties.getDefaultPassword();
    String actualDefaultUserName = actualInstanceAuthProperties.getDefaultUserName();
    Map<String, InstanceCredentials> actualServiceMap = actualInstanceAuthProperties.getServiceMap();

    // Assert
    assertEquals(
        "AdminServerProperties.InstanceAuthProperties(enabled=true, defaultUserName=janedoe, defaultPassword=iloveyou,"
            + " serviceMap={})",
        actualToStringResult);
    assertEquals("iloveyou", actualDefaultPassword);
    assertEquals("janedoe", actualDefaultUserName);
    assertTrue(actualInstanceAuthProperties.isEnabled());
    assertTrue(actualServiceMap.isEmpty());
    assertSame(serviceMap, actualServiceMap);
  }

  /**
   * Test InstanceProxyProperties {@link InstanceProxyProperties#equals(Object)}, and {@link InstanceProxyProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceProxyProperties#equals(Object)}
   *   <li>{@link InstanceProxyProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceProxyProperties.equals(Object)", "int InstanceProxyProperties.hashCode()"})
  public void testInstanceProxyPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InstanceProxyProperties instanceProxyProperties = new InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(new HashSet<>());

    InstanceProxyProperties instanceProxyProperties2 = new InstanceProxyProperties();
    instanceProxyProperties2.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertEquals(instanceProxyProperties, instanceProxyProperties2);
    int expectedHashCodeResult = instanceProxyProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceProxyProperties2.hashCode());
  }

  /**
   * Test InstanceProxyProperties {@link InstanceProxyProperties#equals(Object)}, and {@link InstanceProxyProperties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceProxyProperties#equals(Object)}
   *   <li>{@link InstanceProxyProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceProxyProperties.equals(Object)", "int InstanceProxyProperties.hashCode()"})
  public void testInstanceProxyPropertiesEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InstanceProxyProperties instanceProxyProperties = new InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertEquals(instanceProxyProperties, instanceProxyProperties);
    int expectedHashCodeResult = instanceProxyProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceProxyProperties.hashCode());
  }

  /**
   * Test InstanceProxyProperties {@link InstanceProxyProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProxyProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceProxyProperties.equals(Object)", "int InstanceProxyProperties.hashCode()"})
  public void testInstanceProxyPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HashSet<String> ignoredHeaders = new HashSet<>();
    ignoredHeaders.add("foo");

    InstanceProxyProperties instanceProxyProperties = new InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(ignoredHeaders);

    InstanceProxyProperties instanceProxyProperties2 = new InstanceProxyProperties();
    instanceProxyProperties2.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertNotEquals(instanceProxyProperties, instanceProxyProperties2);
  }

  /**
   * Test InstanceProxyProperties {@link InstanceProxyProperties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProxyProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceProxyProperties.equals(Object)", "int InstanceProxyProperties.hashCode()"})
  public void testInstanceProxyPropertiesEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InstanceProxyProperties instanceProxyProperties = new InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertNotEquals(instanceProxyProperties, null);
  }

  /**
   * Test InstanceProxyProperties {@link InstanceProxyProperties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InstanceProxyProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InstanceProxyProperties.equals(Object)", "int InstanceProxyProperties.hashCode()"})
  public void testInstanceProxyPropertiesEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InstanceProxyProperties instanceProxyProperties = new InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertNotEquals(instanceProxyProperties, "Different type to InstanceProxyProperties");
  }

  /**
   * Test InstanceProxyProperties getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InstanceProxyProperties#setIgnoredHeaders(Set)}
   *   <li>{@link InstanceProxyProperties#toString()}
   *   <li>{@link InstanceProxyProperties#getIgnoredHeaders()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set InstanceProxyProperties.getIgnoredHeaders()",
      "void InstanceProxyProperties.setIgnoredHeaders(Set)", "String InstanceProxyProperties.toString()"})
  public void testInstanceProxyPropertiesGettersAndSetters() {
    // Arrange
    InstanceProxyProperties instanceProxyProperties = new InstanceProxyProperties();
    HashSet<String> ignoredHeaders = new HashSet<>();

    // Act
    instanceProxyProperties.setIgnoredHeaders(ignoredHeaders);
    String actualToStringResult = instanceProxyProperties.toString();
    Set<String> actualIgnoredHeaders = instanceProxyProperties.getIgnoredHeaders();

    // Assert
    assertEquals("AdminServerProperties.InstanceProxyProperties(ignoredHeaders=[])", actualToStringResult);
    assertTrue(actualIgnoredHeaders.isEmpty());
    assertSame(ignoredHeaders, actualIgnoredHeaders);
  }

  /**
   * Test InstanceProxyProperties new {@link InstanceProxyProperties} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link InstanceProxyProperties}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InstanceProxyProperties.<init>()"})
  public void testInstanceProxyPropertiesNewInstanceProxyProperties() {
    // Arrange, Act and Assert
    Set<String> ignoredHeaders = (new InstanceProxyProperties()).getIgnoredHeaders();
    assertEquals(3, ignoredHeaders.size());
    assertTrue(ignoredHeaders.contains("Authorization"));
    assertTrue(ignoredHeaders.contains("Cookie"));
    assertTrue(ignoredHeaders.contains("Set-Cookie"));
  }

  /**
   * Test MonitorProperties {@link MonitorProperties#equals(Object)}, and {@link MonitorProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MonitorProperties#equals(Object)}
   *   <li>{@link MonitorProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean MonitorProperties.equals(Object)", "int MonitorProperties.hashCode()"})
  public void testMonitorPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MonitorProperties monitorProperties = new MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    MonitorProperties monitorProperties2 = new MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertEquals(monitorProperties, monitorProperties2);
    int expectedHashCodeResult = monitorProperties.hashCode();
    assertEquals(expectedHashCodeResult, monitorProperties2.hashCode());
  }

  /**
   * Test MonitorProperties {@link MonitorProperties#equals(Object)}, and {@link MonitorProperties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MonitorProperties#equals(Object)}
   *   <li>{@link MonitorProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean MonitorProperties.equals(Object)", "int MonitorProperties.hashCode()"})
  public void testMonitorPropertiesEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MonitorProperties monitorProperties = new MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    // Act and Assert
    assertEquals(monitorProperties, monitorProperties);
    int expectedHashCodeResult = monitorProperties.hashCode();
    assertEquals(expectedHashCodeResult, monitorProperties.hashCode());
  }

  /**
   * Test MonitorProperties {@link MonitorProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitorProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean MonitorProperties.equals(Object)", "int MonitorProperties.hashCode()"})
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MonitorProperties monitorProperties = new MonitorProperties();
    monitorProperties.setDefaultRetries(3);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    MonitorProperties monitorProperties2 = new MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Test MonitorProperties {@link MonitorProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitorProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean MonitorProperties.equals(Object)", "int MonitorProperties.hashCode()"})
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Integer> retries = new HashMap<>();
    retries.put("foo", 1);

    MonitorProperties monitorProperties = new MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(retries);
    monitorProperties.setTimeout(new HashMap<>());

    MonitorProperties monitorProperties2 = new MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Test MonitorProperties {@link MonitorProperties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitorProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean MonitorProperties.equals(Object)", "int MonitorProperties.hashCode()"})
  public void testMonitorPropertiesEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    MonitorProperties monitorProperties = new MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, null);
  }

  /**
   * Test MonitorProperties {@link MonitorProperties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitorProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean MonitorProperties.equals(Object)", "int MonitorProperties.hashCode()"})
  public void testMonitorPropertiesEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    MonitorProperties monitorProperties = new MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, "Different type to MonitorProperties");
  }

  /**
   * Test MonitorProperties getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MonitorProperties#setDefaultRetries(int)}
   *   <li>{@link MonitorProperties#setDefaultTimeout(Duration)}
   *   <li>{@link MonitorProperties#setInfoInterval(Duration)}
   *   <li>{@link MonitorProperties#setInfoLifetime(Duration)}
   *   <li>{@link MonitorProperties#setInfoMaxBackoff(Duration)}
   *   <li>{@link MonitorProperties#setRetries(Map)}
   *   <li>{@link MonitorProperties#setStatusInterval(Duration)}
   *   <li>{@link MonitorProperties#setStatusLifetime(Duration)}
   *   <li>{@link MonitorProperties#setStatusMaxBackoff(Duration)}
   *   <li>{@link MonitorProperties#setTimeout(Map)}
   *   <li>{@link MonitorProperties#toString()}
   *   <li>{@link MonitorProperties#getDefaultRetries()}
   *   <li>{@link MonitorProperties#getDefaultTimeout()}
   *   <li>{@link MonitorProperties#getInfoInterval()}
   *   <li>{@link MonitorProperties#getInfoLifetime()}
   *   <li>{@link MonitorProperties#getInfoMaxBackoff()}
   *   <li>{@link MonitorProperties#getRetries()}
   *   <li>{@link MonitorProperties#getStatusInterval()}
   *   <li>{@link MonitorProperties#getStatusLifetime()}
   *   <li>{@link MonitorProperties#getStatusMaxBackoff()}
   *   <li>{@link MonitorProperties#getTimeout()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int MonitorProperties.getDefaultRetries()", "Duration MonitorProperties.getDefaultTimeout()",
      "Duration MonitorProperties.getInfoInterval()", "Duration MonitorProperties.getInfoLifetime()",
      "Duration MonitorProperties.getInfoMaxBackoff()", "Map MonitorProperties.getRetries()",
      "Duration MonitorProperties.getStatusInterval()", "Duration MonitorProperties.getStatusLifetime()",
      "Duration MonitorProperties.getStatusMaxBackoff()", "Map MonitorProperties.getTimeout()",
      "void MonitorProperties.setDefaultRetries(int)", "void MonitorProperties.setDefaultTimeout(Duration)",
      "void MonitorProperties.setInfoInterval(Duration)", "void MonitorProperties.setInfoLifetime(Duration)",
      "void MonitorProperties.setInfoMaxBackoff(Duration)", "void MonitorProperties.setRetries(Map)",
      "void MonitorProperties.setStatusInterval(Duration)", "void MonitorProperties.setStatusLifetime(Duration)",
      "void MonitorProperties.setStatusMaxBackoff(Duration)", "void MonitorProperties.setTimeout(Map)",
      "String MonitorProperties.toString()"})
  public void testMonitorPropertiesGettersAndSetters() {
    // Arrange
    MonitorProperties monitorProperties = new MonitorProperties();

    // Act
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setDefaultTimeout(null);
    monitorProperties.setInfoInterval(null);
    monitorProperties.setInfoLifetime(null);
    monitorProperties.setInfoMaxBackoff(null);
    HashMap<String, Integer> retries = new HashMap<>();
    monitorProperties.setRetries(retries);
    monitorProperties.setStatusInterval(null);
    monitorProperties.setStatusLifetime(null);
    monitorProperties.setStatusMaxBackoff(null);
    HashMap<String, Duration> timeout = new HashMap<>();
    monitorProperties.setTimeout(timeout);
    String actualToStringResult = monitorProperties.toString();
    int actualDefaultRetries = monitorProperties.getDefaultRetries();
    Duration actualDefaultTimeout = monitorProperties.getDefaultTimeout();
    Duration actualInfoInterval = monitorProperties.getInfoInterval();
    Duration actualInfoLifetime = monitorProperties.getInfoLifetime();
    Duration actualInfoMaxBackoff = monitorProperties.getInfoMaxBackoff();
    Map<String, Integer> actualRetries = monitorProperties.getRetries();
    Duration actualStatusInterval = monitorProperties.getStatusInterval();
    Duration actualStatusLifetime = monitorProperties.getStatusLifetime();
    Duration actualStatusMaxBackoff = monitorProperties.getStatusMaxBackoff();
    Map<String, Duration> actualTimeout = monitorProperties.getTimeout();

    // Assert
    assertEquals(
        "AdminServerProperties.MonitorProperties(statusInterval=null, statusLifetime=null, statusMaxBackoff=null,"
            + " infoInterval=null, infoMaxBackoff=null, infoLifetime=null, defaultRetries=1, retries={}, defaultTimeout"
            + "=null, timeout={})",
        actualToStringResult);
    assertNull(actualDefaultTimeout);
    assertNull(actualInfoInterval);
    assertNull(actualInfoLifetime);
    assertNull(actualInfoMaxBackoff);
    assertNull(actualStatusInterval);
    assertNull(actualStatusLifetime);
    assertNull(actualStatusMaxBackoff);
    assertEquals(1, actualDefaultRetries);
    assertTrue(actualRetries.isEmpty());
    assertTrue(actualTimeout.isEmpty());
    assertSame(retries, actualRetries);
    assertSame(timeout, actualTimeout);
  }

  /**
   * Test MonitorProperties new {@link MonitorProperties} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link MonitorProperties}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MonitorProperties.<init>()"})
  public void testMonitorPropertiesNewMonitorProperties() {
    // Arrange and Act
    MonitorProperties actualMonitorProperties = new MonitorProperties();

    // Assert
    assertEquals(0, actualMonitorProperties.getDefaultRetries());
    assertEquals(10000000000L, actualMonitorProperties.getDefaultTimeout().toNanos());
    assertEquals(10000000000L, actualMonitorProperties.getStatusInterval().toNanos());
    assertEquals(10000000000L, actualMonitorProperties.getStatusLifetime().toNanos());
    assertEquals(600000000000L, actualMonitorProperties.getInfoMaxBackoff().toNanos());
    assertEquals(60000000000L, actualMonitorProperties.getInfoInterval().toNanos());
    assertEquals(60000000000L, actualMonitorProperties.getInfoLifetime().toNanos());
    assertEquals(60000000000L, actualMonitorProperties.getStatusMaxBackoff().toNanos());
    assertTrue(actualMonitorProperties.getRetries().isEmpty());
    assertTrue(actualMonitorProperties.getTimeout().isEmpty());
  }

  /**
   * Test ServerProperties {@link ServerProperties#equals(Object)}, and {@link ServerProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ServerProperties#equals(Object)}
   *   <li>{@link ServerProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ServerProperties.equals(Object)", "int ServerProperties.hashCode()"})
  public void testServerPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ServerProperties serverProperties = new ServerProperties();
    serverProperties.setEnabled(true);

    ServerProperties serverProperties2 = new ServerProperties();
    serverProperties2.setEnabled(true);

    // Act and Assert
    assertEquals(serverProperties, serverProperties2);
    int expectedHashCodeResult = serverProperties.hashCode();
    assertEquals(expectedHashCodeResult, serverProperties2.hashCode());
  }

  /**
   * Test ServerProperties {@link ServerProperties#equals(Object)}, and {@link ServerProperties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ServerProperties#equals(Object)}
   *   <li>{@link ServerProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ServerProperties.equals(Object)", "int ServerProperties.hashCode()"})
  public void testServerPropertiesEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ServerProperties serverProperties = new ServerProperties();
    serverProperties.setEnabled(true);

    // Act and Assert
    assertEquals(serverProperties, serverProperties);
    int expectedHashCodeResult = serverProperties.hashCode();
    assertEquals(expectedHashCodeResult, serverProperties.hashCode());
  }

  /**
   * Test ServerProperties {@link ServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ServerProperties.equals(Object)", "int ServerProperties.hashCode()"})
  public void testServerPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ServerProperties serverProperties = new ServerProperties();
    serverProperties.setEnabled(false);

    ServerProperties serverProperties2 = new ServerProperties();
    serverProperties2.setEnabled(true);

    // Act and Assert
    assertNotEquals(serverProperties, serverProperties2);
  }

  /**
   * Test ServerProperties {@link ServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ServerProperties.equals(Object)", "int ServerProperties.hashCode()"})
  public void testServerPropertiesEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ServerProperties serverProperties = new ServerProperties();
    serverProperties.setEnabled(true);

    // Act and Assert
    assertNotEquals(serverProperties, null);
  }

  /**
   * Test ServerProperties {@link ServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ServerProperties.equals(Object)", "int ServerProperties.hashCode()"})
  public void testServerPropertiesEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ServerProperties serverProperties = new ServerProperties();
    serverProperties.setEnabled(true);

    // Act and Assert
    assertNotEquals(serverProperties, "Different type to ServerProperties");
  }

  /**
   * Test ServerProperties getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ServerProperties}
   *   <li>{@link ServerProperties#setEnabled(boolean)}
   *   <li>{@link ServerProperties#toString()}
   *   <li>{@link ServerProperties#isEnabled()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ServerProperties.<init>()", "boolean ServerProperties.isEnabled()",
      "void ServerProperties.setEnabled(boolean)", "String ServerProperties.toString()"})
  public void testServerPropertiesGettersAndSetters() {
    // Arrange and Act
    ServerProperties actualServerProperties = new ServerProperties();
    actualServerProperties.setEnabled(true);
    String actualToStringResult = actualServerProperties.toString();

    // Assert
    assertEquals("AdminServerProperties.ServerProperties(enabled=true)", actualToStringResult);
    assertTrue(actualServerProperties.isEnabled());
  }

  /**
   * Test {@link AdminServerProperties#setContextPath(String)}.
   * <ul>
   *   <li>Then {@link AdminServerProperties} (default constructor) ContextPath is {@code /Context Path}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AdminServerProperties.setContextPath(String)"})
  public void testSetContextPath_thenAdminServerPropertiesContextPathIsContextPath() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath("Context Path");

    // Assert
    assertEquals("/Context Path", adminServerProperties.getContextPath());
  }

  /**
   * Test {@link AdminServerProperties#setContextPath(String)}.
   * <ul>
   *   <li>Then {@link AdminServerProperties} (default constructor) ContextPath is {@code /}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AdminServerProperties.setContextPath(String)"})
  public void testSetContextPath_thenAdminServerPropertiesContextPathIsSlash() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath("///");

    // Assert
    assertEquals("/", adminServerProperties.getContextPath());
  }

  /**
   * Test {@link AdminServerProperties#setContextPath(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link AdminServerProperties} (default constructor) ContextPath is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AdminServerProperties.setContextPath(String)"})
  public void testSetContextPath_whenNull_thenAdminServerPropertiesContextPathIsNull() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath(null);

    // Assert
    assertNull(adminServerProperties.getContextPath());
  }

  /**
   * Test {@link AdminServerProperties#setContextPath(String)}.
   * <ul>
   *   <li>When {@code /}.</li>
   *   <li>Then {@link AdminServerProperties} (default constructor) ContextPath is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AdminServerProperties.setContextPath(String)"})
  public void testSetContextPath_whenSlash_thenAdminServerPropertiesContextPathIsEmptyString() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath("/");

    // Assert that nothing has changed
    assertEquals("", adminServerProperties.getContextPath());
  }

  /**
   * Test {@link AdminServerProperties#path(String)}.
   * <p>
   * Method under test: {@link AdminServerProperties#path(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AdminServerProperties.path(String)"})
  public void testPath() {
    // Arrange, Act and Assert
    assertEquals("Path", (new AdminServerProperties()).path("Path"));
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}, and {@link AdminServerProperties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties#equals(Object)}
   *   <li>{@link AdminServerProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();
    AdminServerProperties adminServerProperties2 = new AdminServerProperties();

    // Act and Assert
    assertEquals(adminServerProperties, adminServerProperties2);
    int expectedHashCodeResult = adminServerProperties.hashCode();
    assertEquals(expectedHashCodeResult, adminServerProperties2.hashCode());
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}, and {@link AdminServerProperties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties#equals(Object)}
   *   <li>{@link AdminServerProperties#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act and Assert
    assertEquals(adminServerProperties, adminServerProperties);
    int expectedHashCodeResult = adminServerProperties.hashCode();
    assertEquals(expectedHashCodeResult, adminServerProperties.hashCode());
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    ServerProperties serverProperties = new ServerProperties();
    serverProperties.setEnabled(true);

    // Act and Assert
    assertNotEquals(adminServerProperties, serverProperties);
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setContextPath("Context Path");

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MonitorProperties monitor = new MonitorProperties();
    monitor.setDefaultRetries(1);
    monitor.setRetries(new HashMap<>());
    monitor.setTimeout(new HashMap<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setMonitor(monitor);

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    InstanceAuthProperties instanceAuth = new InstanceAuthProperties();
    instanceAuth.setDefaultPassword("iloveyou");
    instanceAuth.setDefaultUserName("janedoe");
    instanceAuth.setEnabled(true);
    instanceAuth.setServiceMap(new HashMap<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setInstanceAuth(instanceAuth);

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    InstanceProxyProperties instanceProxy = new InstanceProxyProperties();
    instanceProxy.setIgnoredHeaders(new HashSet<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setInstanceProxy(instanceProxy);

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setMetadataKeysToSanitize(new String[]{"Metadata Keys To Sanitize"});

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setProbedEndpoints(new String[]{"https://config.us-east-2.amazonaws.com"});

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AdminServerProperties(), null);
  }

  /**
   * Test {@link AdminServerProperties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AdminServerProperties.equals(Object)", "int AdminServerProperties.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AdminServerProperties(), "Different type to AdminServerProperties");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties#setInstanceAuth(InstanceAuthProperties)}
   *   <li>{@link AdminServerProperties#setInstanceProxy(InstanceProxyProperties)}
   *   <li>{@link AdminServerProperties#setMetadataKeysToSanitize(String[])}
   *   <li>{@link AdminServerProperties#setMonitor(MonitorProperties)}
   *   <li>{@link AdminServerProperties#setProbedEndpoints(String[])}
   *   <li>{@link AdminServerProperties#setServer(ServerProperties)}
   *   <li>{@link AdminServerProperties#toString()}
   *   <li>{@link AdminServerProperties#getContextPath()}
   *   <li>{@link AdminServerProperties#getInstanceAuth()}
   *   <li>{@link AdminServerProperties#getInstanceProxy()}
   *   <li>{@link AdminServerProperties#getMetadataKeysToSanitize()}
   *   <li>{@link AdminServerProperties#getMonitor()}
   *   <li>{@link AdminServerProperties#getProbedEndpoints()}
   *   <li>{@link AdminServerProperties#getServer()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String AdminServerProperties.getContextPath()",
      "InstanceAuthProperties AdminServerProperties.getInstanceAuth()",
      "InstanceProxyProperties AdminServerProperties.getInstanceProxy()",
      "String[] AdminServerProperties.getMetadataKeysToSanitize()",
      "MonitorProperties AdminServerProperties.getMonitor()", "String[] AdminServerProperties.getProbedEndpoints()",
      "ServerProperties AdminServerProperties.getServer()",
      "void AdminServerProperties.setInstanceAuth(InstanceAuthProperties)",
      "void AdminServerProperties.setInstanceProxy(InstanceProxyProperties)",
      "void AdminServerProperties.setMetadataKeysToSanitize(String[])",
      "void AdminServerProperties.setMonitor(MonitorProperties)",
      "void AdminServerProperties.setProbedEndpoints(String[])",
      "void AdminServerProperties.setServer(ServerProperties)", "String AdminServerProperties.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    InstanceAuthProperties instanceAuth = new InstanceAuthProperties();
    instanceAuth.setDefaultPassword("iloveyou");
    instanceAuth.setDefaultUserName("janedoe");
    instanceAuth.setEnabled(true);
    instanceAuth.setServiceMap(new HashMap<>());

    // Act
    adminServerProperties.setInstanceAuth(instanceAuth);
    InstanceProxyProperties instanceProxy = new InstanceProxyProperties();
    instanceProxy.setIgnoredHeaders(new HashSet<>());
    adminServerProperties.setInstanceProxy(instanceProxy);
    String[] metadataKeysToSanitize = new String[]{"Metadata Keys To Sanitize"};
    adminServerProperties.setMetadataKeysToSanitize(metadataKeysToSanitize);
    MonitorProperties monitor = new MonitorProperties();
    monitor.setDefaultRetries(1);
    monitor.setRetries(new HashMap<>());
    monitor.setTimeout(new HashMap<>());
    adminServerProperties.setMonitor(monitor);
    String[] probedEndpoints = new String[]{"https://config.us-east-2.amazonaws.com"};
    adminServerProperties.setProbedEndpoints(probedEndpoints);
    ServerProperties server = new ServerProperties();
    server.setEnabled(true);
    adminServerProperties.setServer(server);
    String actualToStringResult = adminServerProperties.toString();
    String actualContextPath = adminServerProperties.getContextPath();
    InstanceAuthProperties actualInstanceAuth = adminServerProperties.getInstanceAuth();
    InstanceProxyProperties actualInstanceProxy = adminServerProperties.getInstanceProxy();
    String[] actualMetadataKeysToSanitize = adminServerProperties.getMetadataKeysToSanitize();
    MonitorProperties actualMonitor = adminServerProperties.getMonitor();
    String[] actualProbedEndpoints = adminServerProperties.getProbedEndpoints();
    ServerProperties actualServer = adminServerProperties.getServer();

    // Assert
    assertEquals("", actualContextPath);
    assertEquals("AdminServerProperties(contextPath=, server=AdminServerProperties.ServerProperties(enabled=true),"
        + " monitor=AdminServerProperties.MonitorProperties(statusInterval=PT10S, statusLifetime=PT10S,"
        + " statusMaxBackoff=PT1M, infoInterval=PT1M, infoMaxBackoff=PT10M, infoLifetime=PT1M, defaultRetries=1,"
        + " retries={}, defaultTimeout=PT10S, timeout={}), instanceAuth=AdminServerProperties.InstanceAuthProperties"
        + "(enabled=true, defaultUserName=janedoe, defaultPassword=iloveyou, serviceMap={}), instanceProxy"
        + "=AdminServerProperties.InstanceProxyProperties(ignoredHeaders=[]), metadataKeysToSanitize=[Metadata"
        + " Keys To Sanitize], probedEndpoints=[https://config.us-east-2.amazonaws.com])", actualToStringResult);
    assertTrue(actualServer.isEnabled());
    assertSame(instanceAuth, actualInstanceAuth);
    assertSame(instanceProxy, actualInstanceProxy);
    assertSame(monitor, actualMonitor);
    assertSame(server, actualServer);
    assertSame(metadataKeysToSanitize, actualMetadataKeysToSanitize);
    assertSame(probedEndpoints, actualProbedEndpoints);
    assertArrayEquals(new String[]{"Metadata Keys To Sanitize"}, actualMetadataKeysToSanitize);
    assertArrayEquals(new String[]{"https://config.us-east-2.amazonaws.com"}, actualProbedEndpoints);
  }

  /**
   * Test new {@link AdminServerProperties} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link AdminServerProperties}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AdminServerProperties.<init>()"})
  public void testNewAdminServerProperties() {
    // Arrange and Act
    AdminServerProperties actualAdminServerProperties = new AdminServerProperties();

    // Assert
    assertEquals("", actualAdminServerProperties.getContextPath());
    assertEquals(21, actualAdminServerProperties.getProbedEndpoints().length);
    assertEquals(6, actualAdminServerProperties.getMetadataKeysToSanitize().length);
  }
}
