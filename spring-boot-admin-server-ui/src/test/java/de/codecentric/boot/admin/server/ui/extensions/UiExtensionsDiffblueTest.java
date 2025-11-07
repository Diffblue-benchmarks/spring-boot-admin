package de.codecentric.boot.admin.server.ui.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UiExtensions.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UiExtensionsDiffblueTest {
  @Autowired
  private List<UiExtension> list;

  @MockBean
  private UiExtension uiExtension;

  @Autowired
  private UiExtensions uiExtensions;

  /**
   * Method under test: {@link UiExtensions#iterator()}
   */
  @Test
  void testIterator() {
    // Arrange, Act and Assert
    assertFalse(UiExtensions.EMPTY.iterator().hasNext());
  }

  /**
   * Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  void testGetCssExtensions() {
    // Arrange, Act and Assert
    assertTrue(UiExtensions.EMPTY.getCssExtensions().isEmpty());
  }

  /**
   * Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  void testGetCssExtensions2() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn("Resource Path");

    // Act
    List<UiExtension> actualCssExtensions = uiExtensions.getCssExtensions();

    // Assert
    verify(uiExtension).getResourcePath();
    assertTrue(actualCssExtensions.isEmpty());
  }

  /**
   * Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  void testGetCssExtensions3() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn(".css");

    // Act
    List<UiExtension> actualCssExtensions = uiExtensions.getCssExtensions();

    // Assert
    verify(uiExtension).getResourcePath();
    assertEquals(1, actualCssExtensions.size());
  }

  /**
   * Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  void testGetCssExtensions4() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn("Resource Path");

    ArrayList<UiExtension> extensions = new ArrayList<>();
    extensions.add(uiExtension);
    extensions.add(uiExtension);

    // Act
    List<UiExtension> actualCssExtensions = (new UiExtensions(extensions)).getCssExtensions();

    // Assert
    verify(uiExtension, atLeast(1)).getResourcePath();
    assertTrue(actualCssExtensions.isEmpty());
  }

  /**
   * Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  void testGetJsExtensions() {
    // Arrange, Act and Assert
    assertTrue(UiExtensions.EMPTY.getJsExtensions().isEmpty());
  }

  /**
   * Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  void testGetJsExtensions2() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn("Resource Path");

    // Act
    List<UiExtension> actualJsExtensions = uiExtensions.getJsExtensions();

    // Assert
    verify(uiExtension).getResourcePath();
    assertTrue(actualJsExtensions.isEmpty());
  }

  /**
   * Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  void testGetJsExtensions3() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn(".js");

    // Act
    List<UiExtension> actualJsExtensions = uiExtensions.getJsExtensions();

    // Assert
    verify(uiExtension).getResourcePath();
    assertEquals(1, actualJsExtensions.size());
  }

  /**
   * Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  void testGetJsExtensions4() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn("Resource Path");

    ArrayList<UiExtension> extensions = new ArrayList<>();
    extensions.add(uiExtension);
    extensions.add(uiExtension);

    // Act
    List<UiExtension> actualJsExtensions = (new UiExtensions(extensions)).getJsExtensions();

    // Assert
    verify(uiExtension, atLeast(1)).getResourcePath();
    assertTrue(actualJsExtensions.isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtensions#equals(Object)}
   *   <li>{@link UiExtensions#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UiExtensions uiExtensions = UiExtensions.EMPTY;
    UiExtensions uiExtensions2 = UiExtensions.EMPTY;

    // Act and Assert
    assertEquals(uiExtensions, uiExtensions2);
    int expectedHashCodeResult = uiExtensions.hashCode();
    assertEquals(expectedHashCodeResult, uiExtensions2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtensions#equals(Object)}
   *   <li>{@link UiExtensions#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    UiExtensions uiExtensions = new UiExtensions(new ArrayList<>());
    UiExtensions uiExtensions2 = UiExtensions.EMPTY;

    // Act and Assert
    assertEquals(uiExtensions, uiExtensions2);
    int expectedHashCodeResult = uiExtensions.hashCode();
    assertEquals(expectedHashCodeResult, uiExtensions2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtensions#equals(Object)}
   *   <li>{@link UiExtensions#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UiExtensions uiExtensions = UiExtensions.EMPTY;

    // Act and Assert
    assertEquals(uiExtensions, uiExtensions);
    int expectedHashCodeResult = uiExtensions.hashCode();
    assertEquals(expectedHashCodeResult, uiExtensions.hashCode());
  }

  /**
   * Method under test: {@link UiExtensions#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(UiExtensions.EMPTY, 1);
  }

  /**
   * Method under test: {@link UiExtensions#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<UiExtension> extensions = new ArrayList<>();
    extensions.add(new UiExtension("Resource Path", "Resource Location"));

    // Act and Assert
    assertNotEquals(new UiExtensions(extensions), UiExtensions.EMPTY);
  }

  /**
   * Method under test: {@link UiExtensions#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ArrayList<UiExtension> extensions = new ArrayList<>();
    extensions.add(mock(UiExtension.class));

    // Act and Assert
    assertNotEquals(new UiExtensions(extensions), UiExtensions.EMPTY);
  }

  /**
   * Method under test: {@link UiExtensions#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(UiExtensions.EMPTY, null);
  }

  /**
   * Method under test: {@link UiExtensions#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(UiExtensions.EMPTY, "Different type to UiExtensions");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtensions#toString()}
   *   <li>{@link UiExtensions#getExtensions()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    UiExtensions uiExtensions = new UiExtensions(new ArrayList<>());

    // Act
    String actualToStringResult = uiExtensions.toString();

    // Assert
    assertEquals("UiExtensions(extensions=[])", actualToStringResult);
    assertTrue(uiExtensions.getExtensions().isEmpty());
  }

  /**
   * Method under test: {@link UiExtensions#UiExtensions(List)}
   */
  @Test
  void testNewUiExtensions() {
    // Arrange and Act
    UiExtensions actualUiExtensions = new UiExtensions(new ArrayList<>());

    // Assert
    assertFalse(actualUiExtensions.iterator().hasNext());
    assertTrue(actualUiExtensions.getCssExtensions().isEmpty());
    assertTrue(actualUiExtensions.getExtensions().isEmpty());
    assertTrue(actualUiExtensions.getJsExtensions().isEmpty());
  }
}
