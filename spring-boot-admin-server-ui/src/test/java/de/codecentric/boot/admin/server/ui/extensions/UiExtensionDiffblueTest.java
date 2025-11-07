package de.codecentric.boot.admin.server.ui.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class UiExtensionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtension#equals(Object)}
   *   <li>{@link UiExtension#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UiExtension uiExtension = new UiExtension("Resource Path", "Resource Location");
    UiExtension uiExtension2 = new UiExtension("Resource Path", "Resource Location");

    // Act and Assert
    assertEquals(uiExtension, uiExtension2);
    int expectedHashCodeResult = uiExtension.hashCode();
    assertEquals(expectedHashCodeResult, uiExtension2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtension#equals(Object)}
   *   <li>{@link UiExtension#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    UiExtension uiExtension = new UiExtension(null, "Resource Location");
    UiExtension uiExtension2 = new UiExtension(null, "Resource Location");

    // Act and Assert
    assertEquals(uiExtension, uiExtension2);
    int expectedHashCodeResult = uiExtension.hashCode();
    assertEquals(expectedHashCodeResult, uiExtension2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtension#equals(Object)}
   *   <li>{@link UiExtension#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    UiExtension uiExtension = new UiExtension("Resource Path", null);
    UiExtension uiExtension2 = new UiExtension("Resource Path", null);

    // Act and Assert
    assertEquals(uiExtension, uiExtension2);
    int expectedHashCodeResult = uiExtension.hashCode();
    assertEquals(expectedHashCodeResult, uiExtension2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtension#equals(Object)}
   *   <li>{@link UiExtension#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UiExtension uiExtension = new UiExtension("Resource Path", "Resource Location");

    // Act and Assert
    assertEquals(uiExtension, uiExtension);
    int expectedHashCodeResult = uiExtension.hashCode();
    assertEquals(expectedHashCodeResult, uiExtension.hashCode());
  }

  /**
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UiExtension uiExtension = new UiExtension("Resource Location", "Resource Location");

    // Act and Assert
    assertNotEquals(uiExtension, new UiExtension("Resource Path", "Resource Location"));
  }

  /**
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UiExtension uiExtension = new UiExtension(null, "Resource Location");

    // Act and Assert
    assertNotEquals(uiExtension, new UiExtension("Resource Path", "Resource Location"));
  }

  /**
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UiExtension uiExtension = new UiExtension("Resource Path", "Resource Path");

    // Act and Assert
    assertNotEquals(uiExtension, new UiExtension("Resource Path", "Resource Location"));
  }

  /**
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    UiExtension uiExtension = new UiExtension("Resource Path", null);

    // Act and Assert
    assertNotEquals(uiExtension, new UiExtension("Resource Path", "Resource Location"));
  }

  /**
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UiExtension("Resource Path", "Resource Location"), null);
  }

  /**
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UiExtension("Resource Path", "Resource Location"), "Different type to UiExtension");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtension#UiExtension(String, String)}
   *   <li>{@link UiExtension#toString()}
   *   <li>{@link UiExtension#getResourceLocation()}
   *   <li>{@link UiExtension#getResourcePath()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    UiExtension actualUiExtension = new UiExtension("Resource Path", "Resource Location");
    String actualToStringResult = actualUiExtension.toString();
    String actualResourceLocation = actualUiExtension.getResourceLocation();

    // Assert
    assertEquals("Resource Location", actualResourceLocation);
    assertEquals("Resource Path", actualUiExtension.getResourcePath());
    assertEquals("UiExtension(resourcePath=Resource Path, resourceLocation=Resource Location)", actualToStringResult);
  }
}
