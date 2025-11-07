package de.codecentric.boot.admin.server.ui.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class HomepageForwardingFilterConfigDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link HomepageForwardingFilterConfig#equals(Object)}
   *   <li>{@link HomepageForwardingFilterConfig#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilterConfig homepageForwardingFilterConfig = new HomepageForwardingFilterConfig("Homepage",
        routesIncludes, new ArrayList<>());
    ArrayList<String> routesIncludes2 = new ArrayList<>();
    HomepageForwardingFilterConfig homepageForwardingFilterConfig2 = new HomepageForwardingFilterConfig("Homepage",
        routesIncludes2, new ArrayList<>());

    // Act and Assert
    assertEquals(homepageForwardingFilterConfig, homepageForwardingFilterConfig2);
    int expectedHashCodeResult = homepageForwardingFilterConfig.hashCode();
    assertEquals(expectedHashCodeResult, homepageForwardingFilterConfig2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link HomepageForwardingFilterConfig#equals(Object)}
   *   <li>{@link HomepageForwardingFilterConfig#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilterConfig homepageForwardingFilterConfig = new HomepageForwardingFilterConfig(null,
        routesIncludes, new ArrayList<>());
    ArrayList<String> routesIncludes2 = new ArrayList<>();
    HomepageForwardingFilterConfig homepageForwardingFilterConfig2 = new HomepageForwardingFilterConfig(null,
        routesIncludes2, new ArrayList<>());

    // Act and Assert
    assertEquals(homepageForwardingFilterConfig, homepageForwardingFilterConfig2);
    int expectedHashCodeResult = homepageForwardingFilterConfig.hashCode();
    assertEquals(expectedHashCodeResult, homepageForwardingFilterConfig2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link HomepageForwardingFilterConfig#equals(Object)}
   *   <li>{@link HomepageForwardingFilterConfig#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilterConfig homepageForwardingFilterConfig = new HomepageForwardingFilterConfig("Homepage",
        routesIncludes, new ArrayList<>());

    // Act and Assert
    assertEquals(homepageForwardingFilterConfig, homepageForwardingFilterConfig);
    int expectedHashCodeResult = homepageForwardingFilterConfig.hashCode();
    assertEquals(expectedHashCodeResult, homepageForwardingFilterConfig.hashCode());
  }

  /**
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilterConfig homepageForwardingFilterConfig = new HomepageForwardingFilterConfig(null,
        routesIncludes, new ArrayList<>());
    ArrayList<String> routesIncludes2 = new ArrayList<>();

    // Act and Assert
    assertNotEquals(homepageForwardingFilterConfig,
        new HomepageForwardingFilterConfig("Homepage", routesIncludes2, new ArrayList<>()));
  }

  /**
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilterConfig homepageForwardingFilterConfig = new HomepageForwardingFilterConfig("42",
        routesIncludes, new ArrayList<>());
    ArrayList<String> routesIncludes2 = new ArrayList<>();

    // Act and Assert
    assertNotEquals(homepageForwardingFilterConfig,
        new HomepageForwardingFilterConfig("Homepage", routesIncludes2, new ArrayList<>()));
  }

  /**
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    routesIncludes.add("Homepage");
    HomepageForwardingFilterConfig homepageForwardingFilterConfig = new HomepageForwardingFilterConfig("Homepage",
        routesIncludes, new ArrayList<>());
    ArrayList<String> routesIncludes2 = new ArrayList<>();

    // Act and Assert
    assertNotEquals(homepageForwardingFilterConfig,
        new HomepageForwardingFilterConfig("Homepage", routesIncludes2, new ArrayList<>()));
  }

  /**
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ArrayList<String> routesExcludes = new ArrayList<>();
    routesExcludes.add("Homepage");
    HomepageForwardingFilterConfig homepageForwardingFilterConfig = new HomepageForwardingFilterConfig("Homepage",
        new ArrayList<>(), routesExcludes);
    ArrayList<String> routesIncludes = new ArrayList<>();

    // Act and Assert
    assertNotEquals(homepageForwardingFilterConfig,
        new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>()));
  }

  /**
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();

    // Act and Assert
    assertNotEquals(new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>()), null);
  }

  /**
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();

    // Act and Assert
    assertNotEquals(new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>()),
        "Different type to HomepageForwardingFilterConfig");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HomepageForwardingFilterConfig#HomepageForwardingFilterConfig(String, List, List)}
   *   <li>{@link HomepageForwardingFilterConfig#toString()}
   *   <li>{@link HomepageForwardingFilterConfig#getHomepage()}
   *   <li>{@link HomepageForwardingFilterConfig#getRoutesExcludes()}
   *   <li>{@link HomepageForwardingFilterConfig#getRoutesIncludes()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    ArrayList<String> routesExcludes = new ArrayList<>();

    // Act
    HomepageForwardingFilterConfig actualHomepageForwardingFilterConfig = new HomepageForwardingFilterConfig("Homepage",
        routesIncludes, routesExcludes);
    String actualToStringResult = actualHomepageForwardingFilterConfig.toString();
    String actualHomepage = actualHomepageForwardingFilterConfig.getHomepage();
    List<String> actualRoutesExcludes = actualHomepageForwardingFilterConfig.getRoutesExcludes();
    List<String> actualRoutesIncludes = actualHomepageForwardingFilterConfig.getRoutesIncludes();

    // Assert
    assertEquals("Homepage", actualHomepage);
    assertEquals("HomepageForwardingFilterConfig(homepage=Homepage, routesIncludes=[], routesExcludes=[])",
        actualToStringResult);
    assertTrue(actualRoutesExcludes.isEmpty());
    assertTrue(actualRoutesIncludes.isEmpty());
    assertSame(routesExcludes, actualRoutesExcludes);
    assertSame(routesIncludes, actualRoutesIncludes);
  }
}
