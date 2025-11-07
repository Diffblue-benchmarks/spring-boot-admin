package de.codecentric.boot.admin.server.ui.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class HomepageForwardingFilterConfigDiffblueTest {
  /**
   * Test {@link HomepageForwardingFilterConfig#equals(Object)}, and {@link HomepageForwardingFilterConfig#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HomepageForwardingFilterConfig#equals(Object)}
   *   <li>{@link HomepageForwardingFilterConfig#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean HomepageForwardingFilterConfig.equals(Object)",
      "int HomepageForwardingFilterConfig.hashCode()"})
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
   * Test {@link HomepageForwardingFilterConfig#equals(Object)}, and {@link HomepageForwardingFilterConfig#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HomepageForwardingFilterConfig#equals(Object)}
   *   <li>{@link HomepageForwardingFilterConfig#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean HomepageForwardingFilterConfig.equals(Object)",
      "int HomepageForwardingFilterConfig.hashCode()"})
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
   * Test {@link HomepageForwardingFilterConfig#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean HomepageForwardingFilterConfig.equals(Object)",
      "int HomepageForwardingFilterConfig.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
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
   * Test {@link HomepageForwardingFilterConfig#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean HomepageForwardingFilterConfig.equals(Object)",
      "int HomepageForwardingFilterConfig.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
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
   * Test {@link HomepageForwardingFilterConfig#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean HomepageForwardingFilterConfig.equals(Object)",
      "int HomepageForwardingFilterConfig.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
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
   * Test {@link HomepageForwardingFilterConfig#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean HomepageForwardingFilterConfig.equals(Object)",
      "int HomepageForwardingFilterConfig.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();

    // Act and Assert
    assertNotEquals(new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>()), null);
  }

  /**
   * Test {@link HomepageForwardingFilterConfig#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingFilterConfig#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean HomepageForwardingFilterConfig.equals(Object)",
      "int HomepageForwardingFilterConfig.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();

    // Act and Assert
    assertNotEquals(new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>()),
        "Different type to HomepageForwardingFilterConfig");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HomepageForwardingFilterConfig#HomepageForwardingFilterConfig(String, List, List)}
   *   <li>{@link HomepageForwardingFilterConfig#toString()}
   *   <li>{@link HomepageForwardingFilterConfig#getHomepage()}
   *   <li>{@link HomepageForwardingFilterConfig#getRoutesExcludes()}
   *   <li>{@link HomepageForwardingFilterConfig#getRoutesIncludes()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HomepageForwardingFilterConfig.<init>(String, List, List)",
      "String HomepageForwardingFilterConfig.getHomepage()", "List HomepageForwardingFilterConfig.getRoutesExcludes()",
      "List HomepageForwardingFilterConfig.getRoutesIncludes()", "String HomepageForwardingFilterConfig.toString()"})
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
