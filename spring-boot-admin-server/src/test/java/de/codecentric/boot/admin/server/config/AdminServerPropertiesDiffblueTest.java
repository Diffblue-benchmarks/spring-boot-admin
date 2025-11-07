package de.codecentric.boot.admin.server.config;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.web.client.BasicAuthHttpHeaderProvider;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import org.junit.Test;

public class AdminServerPropertiesDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceAuthPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
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
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceAuthPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword(null);
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword(null);
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertEquals(instanceAuthProperties, instanceAuthProperties2);
    int expectedHashCodeResult = instanceAuthProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceAuthProperties2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceAuthPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName(null);
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName(null);
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertEquals(instanceAuthProperties, instanceAuthProperties2);
    int expectedHashCodeResult = instanceAuthProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceAuthProperties2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceAuthPropertiesEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
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
   * Method under test:
   * {@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   */
  @Test
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("janedoe");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   */
  @Test
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword(null);
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   */
  @Test
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("iloveyou");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   */
  @Test
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName(null);
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   */
  @Test
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(false);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   */
  @Test
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    HashMap<String, BasicAuthHttpHeaderProvider.InstanceCredentials> serviceMap = new HashMap<>();
    serviceMap.put("janedoe",
        new BasicAuthHttpHeaderProvider.InstanceCredentials("janedoe", "https://example.org/example"));

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(serviceMap);

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   */
  @Test
  public void testInstanceAuthPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    HashMap<String, BasicAuthHttpHeaderProvider.InstanceCredentials> serviceMap = new HashMap<>();
    serviceMap.computeIfPresent("janedoe", mock(BiFunction.class));
    serviceMap.put("janedoe",
        new BasicAuthHttpHeaderProvider.InstanceCredentials("janedoe", "https://example.org/example"));

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(serviceMap);

    AdminServerProperties.InstanceAuthProperties instanceAuthProperties2 = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties2.setDefaultPassword("iloveyou");
    instanceAuthProperties2.setDefaultUserName("janedoe");
    instanceAuthProperties2.setEnabled(true);
    instanceAuthProperties2.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, instanceAuthProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   */
  @Test
  public void testInstanceAuthPropertiesEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, null);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceAuthProperties#equals(Object)}
   */
  @Test
  public void testInstanceAuthPropertiesEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    instanceAuthProperties.setDefaultPassword("iloveyou");
    instanceAuthProperties.setDefaultUserName("janedoe");
    instanceAuthProperties.setEnabled(true);
    instanceAuthProperties.setServiceMap(new HashMap<>());

    // Act and Assert
    assertNotEquals(instanceAuthProperties, "Different type to InstanceAuthProperties");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link AdminServerProperties.InstanceAuthProperties}
   *   <li>
   * {@link AdminServerProperties.InstanceAuthProperties#setDefaultPassword(String)}
   *   <li>
   * {@link AdminServerProperties.InstanceAuthProperties#setDefaultUserName(String)}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#setEnabled(boolean)}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#setServiceMap(Map)}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#toString()}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#getDefaultPassword()}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#getDefaultUserName()}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#getServiceMap()}
   *   <li>{@link AdminServerProperties.InstanceAuthProperties#isEnabled()}
   * </ul>
   */
  @Test
  public void testInstanceAuthPropertiesGettersAndSetters() {
    // Arrange and Act
    AdminServerProperties.InstanceAuthProperties actualInstanceAuthProperties = new AdminServerProperties.InstanceAuthProperties();
    actualInstanceAuthProperties.setDefaultPassword("iloveyou");
    actualInstanceAuthProperties.setDefaultUserName("janedoe");
    actualInstanceAuthProperties.setEnabled(true);
    HashMap<String, BasicAuthHttpHeaderProvider.InstanceCredentials> serviceMap = new HashMap<>();
    actualInstanceAuthProperties.setServiceMap(serviceMap);
    String actualToStringResult = actualInstanceAuthProperties.toString();
    String actualDefaultPassword = actualInstanceAuthProperties.getDefaultPassword();
    String actualDefaultUserName = actualInstanceAuthProperties.getDefaultUserName();
    Map<String, BasicAuthHttpHeaderProvider.InstanceCredentials> actualServiceMap = actualInstanceAuthProperties
        .getServiceMap();

    // Assert that nothing has changed
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
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.InstanceProxyProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.InstanceProxyProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceProxyPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AdminServerProperties.InstanceProxyProperties instanceProxyProperties = new AdminServerProperties.InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(new HashSet<>());

    AdminServerProperties.InstanceProxyProperties instanceProxyProperties2 = new AdminServerProperties.InstanceProxyProperties();
    instanceProxyProperties2.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertEquals(instanceProxyProperties, instanceProxyProperties2);
    int expectedHashCodeResult = instanceProxyProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceProxyProperties2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.InstanceProxyProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.InstanceProxyProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testInstanceProxyPropertiesEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerProperties.InstanceProxyProperties instanceProxyProperties = new AdminServerProperties.InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertEquals(instanceProxyProperties, instanceProxyProperties);
    int expectedHashCodeResult = instanceProxyProperties.hashCode();
    assertEquals(expectedHashCodeResult, instanceProxyProperties.hashCode());
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceProxyProperties#equals(Object)}
   */
  @Test
  public void testInstanceProxyPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HashSet<String> ignoredHeaders = new HashSet<>();
    ignoredHeaders.add("foo");

    AdminServerProperties.InstanceProxyProperties instanceProxyProperties = new AdminServerProperties.InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(ignoredHeaders);

    AdminServerProperties.InstanceProxyProperties instanceProxyProperties2 = new AdminServerProperties.InstanceProxyProperties();
    instanceProxyProperties2.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertNotEquals(instanceProxyProperties, instanceProxyProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceProxyProperties#equals(Object)}
   */
  @Test
  public void testInstanceProxyPropertiesEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.InstanceProxyProperties instanceProxyProperties = new AdminServerProperties.InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertNotEquals(instanceProxyProperties, null);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.InstanceProxyProperties#equals(Object)}
   */
  @Test
  public void testInstanceProxyPropertiesEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.InstanceProxyProperties instanceProxyProperties = new AdminServerProperties.InstanceProxyProperties();
    instanceProxyProperties.setIgnoredHeaders(new HashSet<>());

    // Act and Assert
    assertNotEquals(instanceProxyProperties, "Different type to InstanceProxyProperties");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link AdminServerProperties.InstanceProxyProperties#setIgnoredHeaders(Set)}
   *   <li>{@link AdminServerProperties.InstanceProxyProperties#toString()}
   *   <li>{@link AdminServerProperties.InstanceProxyProperties#getIgnoredHeaders()}
   * </ul>
   */
  @Test
  public void testInstanceProxyPropertiesGettersAndSetters() {
    // Arrange
    AdminServerProperties.InstanceProxyProperties instanceProxyProperties = new AdminServerProperties.InstanceProxyProperties();
    HashSet<String> ignoredHeaders = new HashSet<>();

    // Act
    instanceProxyProperties.setIgnoredHeaders(ignoredHeaders);
    String actualToStringResult = instanceProxyProperties.toString();
    Set<String> actualIgnoredHeaders = instanceProxyProperties.getIgnoredHeaders();

    // Assert that nothing has changed
    assertEquals("AdminServerProperties.InstanceProxyProperties(ignoredHeaders=[])", actualToStringResult);
    assertTrue(actualIgnoredHeaders.isEmpty());
    assertSame(ignoredHeaders, actualIgnoredHeaders);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AdminServerProperties.InstanceProxyProperties}
   */
  @Test
  public void testInstanceProxyPropertiesNewInstanceProxyProperties() {
    // Arrange, Act and Assert
    Set<String> ignoredHeaders = (new AdminServerProperties.InstanceProxyProperties()).getIgnoredHeaders();
    assertEquals(3, ignoredHeaders.size());
    assertTrue(ignoredHeaders.contains("Authorization"));
    assertTrue(ignoredHeaders.contains("Cookie"));
    assertTrue(ignoredHeaders.contains("Set-Cookie"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.MonitorProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.MonitorProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testMonitorPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertEquals(monitorProperties, monitorProperties2);
    int expectedHashCodeResult = monitorProperties.hashCode();
    assertEquals(expectedHashCodeResult, monitorProperties2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.MonitorProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.MonitorProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testMonitorPropertiesEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    // Act and Assert
    assertEquals(monitorProperties, monitorProperties);
    int expectedHashCodeResult = monitorProperties.hashCode();
    assertEquals(expectedHashCodeResult, monitorProperties.hashCode());
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(3);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Integer> retries = new HashMap<>();
    retries.put("foo", 1);

    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(retries);
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    HashMap<String, Integer> retries = new HashMap<>();
    retries.computeIfPresent("foo", mock(BiFunction.class));
    retries.put("foo", 1);

    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(retries);
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setStatusInterval(null);
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setStatusLifetime(null);
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setStatusMaxBackoff(null);
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setInfoInterval(null);
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setInfoMaxBackoff(null);
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setInfoLifetime(null);
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultTimeout(null);
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setStatusInterval(null);
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setStatusLifetime(null);
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setStatusMaxBackoff(null);
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setInfoInterval(null);
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setInfoMaxBackoff(null);
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setInfoLifetime(null);
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    AdminServerProperties.MonitorProperties monitorProperties2 = new AdminServerProperties.MonitorProperties();
    monitorProperties2.setDefaultTimeout(null);
    monitorProperties2.setDefaultRetries(1);
    monitorProperties2.setRetries(new HashMap<>());
    monitorProperties2.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, monitorProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, null);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.MonitorProperties#equals(Object)}
   */
  @Test
  public void testMonitorPropertiesEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();
    monitorProperties.setDefaultRetries(1);
    monitorProperties.setRetries(new HashMap<>());
    monitorProperties.setTimeout(new HashMap<>());

    // Act and Assert
    assertNotEquals(monitorProperties, "Different type to MonitorProperties");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.MonitorProperties#setDefaultRetries(int)}
   *   <li>
   * {@link AdminServerProperties.MonitorProperties#setDefaultTimeout(Duration)}
   *   <li>{@link AdminServerProperties.MonitorProperties#setInfoInterval(Duration)}
   *   <li>{@link AdminServerProperties.MonitorProperties#setInfoLifetime(Duration)}
   *   <li>
   * {@link AdminServerProperties.MonitorProperties#setInfoMaxBackoff(Duration)}
   *   <li>{@link AdminServerProperties.MonitorProperties#setRetries(Map)}
   *   <li>
   * {@link AdminServerProperties.MonitorProperties#setStatusInterval(Duration)}
   *   <li>
   * {@link AdminServerProperties.MonitorProperties#setStatusLifetime(Duration)}
   *   <li>
   * {@link AdminServerProperties.MonitorProperties#setStatusMaxBackoff(Duration)}
   *   <li>{@link AdminServerProperties.MonitorProperties#setTimeout(Map)}
   *   <li>{@link AdminServerProperties.MonitorProperties#toString()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getDefaultRetries()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getDefaultTimeout()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getInfoInterval()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getInfoLifetime()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getInfoMaxBackoff()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getRetries()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getStatusInterval()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getStatusLifetime()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getStatusMaxBackoff()}
   *   <li>{@link AdminServerProperties.MonitorProperties#getTimeout()}
   * </ul>
   */
  @Test
  public void testMonitorPropertiesGettersAndSetters() {
    // Arrange
    AdminServerProperties.MonitorProperties monitorProperties = new AdminServerProperties.MonitorProperties();

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
    monitorProperties.getDefaultTimeout();
    monitorProperties.getInfoInterval();
    monitorProperties.getInfoLifetime();
    monitorProperties.getInfoMaxBackoff();
    Map<String, Integer> actualRetries = monitorProperties.getRetries();
    monitorProperties.getStatusInterval();
    monitorProperties.getStatusLifetime();
    monitorProperties.getStatusMaxBackoff();
    Map<String, Duration> actualTimeout = monitorProperties.getTimeout();

    // Assert that nothing has changed
    assertEquals(
        "AdminServerProperties.MonitorProperties(statusInterval=null, statusLifetime=null, statusMaxBackoff=null,"
            + " infoInterval=null, infoMaxBackoff=null, infoLifetime=null, defaultRetries=1, retries={}, defaultTimeout"
            + "=null, timeout={})",
        actualToStringResult);
    assertEquals(1, actualDefaultRetries);
    assertTrue(actualRetries.isEmpty());
    assertTrue(actualTimeout.isEmpty());
    assertSame(retries, actualRetries);
    assertSame(timeout, actualTimeout);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AdminServerProperties.MonitorProperties}
   */
  @Test
  public void testMonitorPropertiesNewMonitorProperties() {
    // Arrange and Act
    AdminServerProperties.MonitorProperties actualMonitorProperties = new AdminServerProperties.MonitorProperties();

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
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.ServerProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.ServerProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testServerPropertiesEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AdminServerProperties.ServerProperties serverProperties = new AdminServerProperties.ServerProperties();
    serverProperties.setEnabled(true);

    AdminServerProperties.ServerProperties serverProperties2 = new AdminServerProperties.ServerProperties();
    serverProperties2.setEnabled(true);

    // Act and Assert
    assertEquals(serverProperties, serverProperties2);
    int expectedHashCodeResult = serverProperties.hashCode();
    assertEquals(expectedHashCodeResult, serverProperties2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties.ServerProperties#equals(Object)}
   *   <li>{@link AdminServerProperties.ServerProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testServerPropertiesEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerProperties.ServerProperties serverProperties = new AdminServerProperties.ServerProperties();
    serverProperties.setEnabled(true);

    // Act and Assert
    assertEquals(serverProperties, serverProperties);
    int expectedHashCodeResult = serverProperties.hashCode();
    assertEquals(expectedHashCodeResult, serverProperties.hashCode());
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.ServerProperties#equals(Object)}
   */
  @Test
  public void testServerPropertiesEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.ServerProperties serverProperties = new AdminServerProperties.ServerProperties();
    serverProperties.setEnabled(false);

    AdminServerProperties.ServerProperties serverProperties2 = new AdminServerProperties.ServerProperties();
    serverProperties2.setEnabled(true);

    // Act and Assert
    assertNotEquals(serverProperties, serverProperties2);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.ServerProperties#equals(Object)}
   */
  @Test
  public void testServerPropertiesEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.ServerProperties serverProperties = new AdminServerProperties.ServerProperties();
    serverProperties.setEnabled(true);

    // Act and Assert
    assertNotEquals(serverProperties, null);
  }

  /**
   * Method under test:
   * {@link AdminServerProperties.ServerProperties#equals(Object)}
   */
  @Test
  public void testServerPropertiesEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties.ServerProperties serverProperties = new AdminServerProperties.ServerProperties();
    serverProperties.setEnabled(true);

    // Act and Assert
    assertNotEquals(serverProperties, "Different type to ServerProperties");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link AdminServerProperties.ServerProperties}
   *   <li>{@link AdminServerProperties.ServerProperties#setEnabled(boolean)}
   *   <li>{@link AdminServerProperties.ServerProperties#toString()}
   *   <li>{@link AdminServerProperties.ServerProperties#isEnabled()}
   * </ul>
   */
  @Test
  public void testServerPropertiesGettersAndSetters() {
    // Arrange and Act
    AdminServerProperties.ServerProperties actualServerProperties = new AdminServerProperties.ServerProperties();
    actualServerProperties.setEnabled(true);
    String actualToStringResult = actualServerProperties.toString();

    // Assert that nothing has changed
    assertEquals("AdminServerProperties.ServerProperties(enabled=true)", actualToStringResult);
    assertTrue(actualServerProperties.isEnabled());
  }

  /**
   * Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  public void testSetContextPath() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath("Context Path");

    // Assert
    assertEquals("/Context Path", adminServerProperties.getContextPath());
  }

  /**
   * Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  public void testSetContextPath2() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath("/");

    // Assert
    assertEquals("", adminServerProperties.getContextPath());
  }

  /**
   * Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  public void testSetContextPath3() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath(null);

    // Assert
    assertNull(adminServerProperties.getContextPath());
  }

  /**
   * Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  public void testSetContextPath4() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act
    adminServerProperties.setContextPath("///");

    // Assert
    assertEquals("/", adminServerProperties.getContextPath());
  }

  /**
   * Method under test: {@link AdminServerProperties#setContextPath(String)}
   */
  @Test
  public void testSetContextPath5() {
    // Arrange
    HashMap<String, Integer> retries = new HashMap<>();
    retries.computeIfPresent("foo", mock(BiFunction.class));

    AdminServerProperties.MonitorProperties monitor = new AdminServerProperties.MonitorProperties();
    monitor.setDefaultRetries(1);
    monitor.setRetries(retries);
    monitor.setTimeout(new HashMap<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setMonitor(monitor);

    // Act
    adminServerProperties.setContextPath("/");

    // Assert
    assertEquals("", adminServerProperties.getContextPath());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties#equals(Object)}
   *   <li>{@link AdminServerProperties#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link AdminServerProperties#path(String)}
   */
  @Test
  public void testPath() {
    // Arrange, Act and Assert
    assertEquals("Path", (new AdminServerProperties()).path("Path"));
  }

  /**
   * Method under test: {@link AdminServerProperties#path(String)}
   */
  @Test
  public void testPath2() {
    // Arrange
    HashMap<String, Integer> retries = new HashMap<>();
    retries.computeIfPresent("foo", mock(BiFunction.class));

    AdminServerProperties.MonitorProperties monitor = new AdminServerProperties.MonitorProperties();
    monitor.setDefaultRetries(1);
    monitor.setRetries(retries);
    monitor.setTimeout(new HashMap<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setMonitor(monitor);

    // Act and Assert
    assertEquals("Path", adminServerProperties.path("Path"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerProperties#equals(Object)}
   *   <li>{@link AdminServerProperties#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    // Act and Assert
    assertEquals(adminServerProperties, adminServerProperties);
    int expectedHashCodeResult = adminServerProperties.hashCode();
    assertEquals(expectedHashCodeResult, adminServerProperties.hashCode());
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    AdminServerProperties.ServerProperties serverProperties = new AdminServerProperties.ServerProperties();
    serverProperties.setEnabled(true);

    // Act and Assert
    assertNotEquals(adminServerProperties, serverProperties);
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(new AdminServerProperties(), mock(AdminServerProperties.ServerProperties.class));
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setContextPath("Context Path");

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AdminServerProperties.MonitorProperties monitor = new AdminServerProperties.MonitorProperties();
    monitor.setDefaultRetries(1);
    monitor.setRetries(new HashMap<>());
    monitor.setTimeout(new HashMap<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setMonitor(monitor);

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    AdminServerProperties.InstanceAuthProperties instanceAuth = new AdminServerProperties.InstanceAuthProperties();
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
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    AdminServerProperties.InstanceProxyProperties instanceProxy = new AdminServerProperties.InstanceProxyProperties();
    instanceProxy.setIgnoredHeaders(new HashSet<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setInstanceProxy(instanceProxy);

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setMetadataKeysToSanitize(new String[]{"Metadata Keys To Sanitize"});

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setProbedEndpoints(new String[]{"https://config.us-east-2.amazonaws.com"});

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setContextPath(null);

    // Act and Assert
    assertNotEquals(adminServerProperties, new AdminServerProperties());
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AdminServerProperties(), null);
  }

  /**
   * Method under test: {@link AdminServerProperties#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AdminServerProperties(), "Different type to AdminServerProperties");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link AdminServerProperties#setInstanceAuth(AdminServerProperties.InstanceAuthProperties)}
   *   <li>
   * {@link AdminServerProperties#setInstanceProxy(AdminServerProperties.InstanceProxyProperties)}
   *   <li>{@link AdminServerProperties#setMetadataKeysToSanitize(String[])}
   *   <li>
   * {@link AdminServerProperties#setMonitor(AdminServerProperties.MonitorProperties)}
   *   <li>{@link AdminServerProperties#setProbedEndpoints(String[])}
   *   <li>
   * {@link AdminServerProperties#setServer(AdminServerProperties.ServerProperties)}
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
  public void testGettersAndSetters() {
    // Arrange
    AdminServerProperties adminServerProperties = new AdminServerProperties();

    AdminServerProperties.InstanceAuthProperties instanceAuth = new AdminServerProperties.InstanceAuthProperties();
    instanceAuth.setDefaultPassword("iloveyou");
    instanceAuth.setDefaultUserName("janedoe");
    instanceAuth.setEnabled(true);
    instanceAuth.setServiceMap(new HashMap<>());

    // Act
    adminServerProperties.setInstanceAuth(instanceAuth);
    AdminServerProperties.InstanceProxyProperties instanceProxy = new AdminServerProperties.InstanceProxyProperties();
    instanceProxy.setIgnoredHeaders(new HashSet<>());
    adminServerProperties.setInstanceProxy(instanceProxy);
    String[] metadataKeysToSanitize = new String[]{"Metadata Keys To Sanitize"};
    adminServerProperties.setMetadataKeysToSanitize(metadataKeysToSanitize);
    AdminServerProperties.MonitorProperties monitor = new AdminServerProperties.MonitorProperties();
    monitor.setDefaultRetries(1);
    monitor.setRetries(new HashMap<>());
    monitor.setTimeout(new HashMap<>());
    adminServerProperties.setMonitor(monitor);
    String[] probedEndpoints = new String[]{"https://config.us-east-2.amazonaws.com"};
    adminServerProperties.setProbedEndpoints(probedEndpoints);
    AdminServerProperties.ServerProperties server = new AdminServerProperties.ServerProperties();
    server.setEnabled(true);
    adminServerProperties.setServer(server);
    String actualToStringResult = adminServerProperties.toString();
    String actualContextPath = adminServerProperties.getContextPath();
    AdminServerProperties.InstanceAuthProperties actualInstanceAuth = adminServerProperties.getInstanceAuth();
    AdminServerProperties.InstanceProxyProperties actualInstanceProxy = adminServerProperties.getInstanceProxy();
    String[] actualMetadataKeysToSanitize = adminServerProperties.getMetadataKeysToSanitize();
    AdminServerProperties.MonitorProperties actualMonitor = adminServerProperties.getMonitor();
    String[] actualProbedEndpoints = adminServerProperties.getProbedEndpoints();
    AdminServerProperties.ServerProperties actualServer = adminServerProperties.getServer();

    // Assert that nothing has changed
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
   * Method under test: default or parameterless constructor of
   * {@link AdminServerProperties}
   */
  @Test
  public void testNewAdminServerProperties() {
    // Arrange and Act
    AdminServerProperties actualAdminServerProperties = new AdminServerProperties();

    // Assert
    assertEquals("", actualAdminServerProperties.getContextPath());
    AdminServerProperties.InstanceAuthProperties instanceAuth = actualAdminServerProperties.getInstanceAuth();
    assertNull(instanceAuth.getDefaultPassword());
    assertNull(instanceAuth.getDefaultUserName());
    AdminServerProperties.MonitorProperties monitor = actualAdminServerProperties.getMonitor();
    assertEquals(0, monitor.getDefaultRetries());
    assertEquals(10000000000L, monitor.getDefaultTimeout().toNanos());
    assertEquals(10000000000L, monitor.getStatusInterval().toNanos());
    assertEquals(10000000000L, monitor.getStatusLifetime().toNanos());
    Set<String> ignoredHeaders = actualAdminServerProperties.getInstanceProxy().getIgnoredHeaders();
    assertEquals(3, ignoredHeaders.size());
    assertEquals(600000000000L, monitor.getInfoMaxBackoff().toNanos());
    assertEquals(60000000000L, monitor.getInfoInterval().toNanos());
    assertEquals(60000000000L, monitor.getInfoLifetime().toNanos());
    assertEquals(60000000000L, monitor.getStatusMaxBackoff().toNanos());
    assertTrue(instanceAuth.isEnabled());
    assertTrue(actualAdminServerProperties.getServer().isEnabled());
    assertTrue(instanceAuth.getServiceMap().isEmpty());
    assertTrue(monitor.getRetries().isEmpty());
    assertTrue(monitor.getTimeout().isEmpty());
    assertTrue(ignoredHeaders.contains("Authorization"));
    assertTrue(ignoredHeaders.contains("Cookie"));
    assertTrue(ignoredHeaders.contains("Set-Cookie"));
    assertArrayEquals(
        new String[]{".*password$", ".*secret$", ".*key$", ".*token$", ".*credentials.*", ".*vcap_services$"},
        actualAdminServerProperties.getMetadataKeysToSanitize());
    assertArrayEquals(
        new String[]{"health", "env", "metrics", "httptrace:trace", "httptrace", "threaddump:dump", "threaddump",
            "jolokia", "info", "logfile", "refresh", "flyway", "liquibase", "heapdump", "loggers", "auditevents",
            "mappings", "scheduledtasks", "configprops", "caches", "beans"},
        actualAdminServerProperties.getProbedEndpoints());
  }
}
