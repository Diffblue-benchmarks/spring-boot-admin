package de.codecentric.boot.admin.server.ui.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UiExtensionDiffblueTest {
  /**
   * Test {@link UiExtension#equals(Object)}, and {@link UiExtension#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtension#equals(Object)}
   *   <li>{@link UiExtension#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtension.equals(Object)", "int UiExtension.hashCode()"})
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
   * Test {@link UiExtension#equals(Object)}, and {@link UiExtension#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtension#equals(Object)}
   *   <li>{@link UiExtension#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtension.equals(Object)", "int UiExtension.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UiExtension uiExtension = new UiExtension("Resource Path", "Resource Location");

    // Act and Assert
    assertEquals(uiExtension, uiExtension);
    int expectedHashCodeResult = uiExtension.hashCode();
    assertEquals(expectedHashCodeResult, uiExtension.hashCode());
  }

  /**
   * Test {@link UiExtension#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtension.equals(Object)", "int UiExtension.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UiExtension uiExtension = new UiExtension("Resource Location", "Resource Location");

    // Act and Assert
    assertNotEquals(uiExtension, new UiExtension("Resource Path", "Resource Location"));
  }

  /**
   * Test {@link UiExtension#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtension.equals(Object)", "int UiExtension.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UiExtension uiExtension = new UiExtension("Resource Path", "Resource Path");

    // Act and Assert
    assertNotEquals(uiExtension, new UiExtension("Resource Path", "Resource Location"));
  }

  /**
   * Test {@link UiExtension#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtension.equals(Object)", "int UiExtension.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UiExtension("Resource Path", "Resource Location"), null);
  }

  /**
   * Test {@link UiExtension#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtension#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtension.equals(Object)", "int UiExtension.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UiExtension("Resource Path", "Resource Location"), "Different type to UiExtension");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtension#UiExtension(String, String)}
   *   <li>{@link UiExtension#toString()}
   *   <li>{@link UiExtension#getResourceLocation()}
   *   <li>{@link UiExtension#getResourcePath()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UiExtension.<init>(String, String)", "String UiExtension.getResourceLocation()",
      "String UiExtension.getResourcePath()", "String UiExtension.toString()"})
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
