package de.codecentric.boot.admin.server.ui.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link UiExtensions#UiExtensions(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return CssExtensions Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#UiExtensions(List)}
   */
  @Test
  @DisplayName("Test new UiExtensions(List); when ArrayList(); then return CssExtensions Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UiExtensions.<init>(List)"})
  void testNewUiExtensions_whenArrayList_thenReturnCssExtensionsEmpty() {
    // Arrange and Act
    UiExtensions actualUiExtensions = new UiExtensions(new ArrayList<>());

    // Assert
    assertFalse(actualUiExtensions.iterator().hasNext());
    assertTrue(actualUiExtensions.getCssExtensions().isEmpty());
    assertTrue(actualUiExtensions.getExtensions().isEmpty());
    assertTrue(actualUiExtensions.getJsExtensions().isEmpty());
  }

  /**
   * Test {@link UiExtensions#iterator()}.
   * <p>
   * Method under test: {@link UiExtensions#iterator()}
   */
  @Test
  @DisplayName("Test iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Iterator UiExtensions.iterator()"})
  void testIterator() {
    // Arrange, Act and Assert
    assertFalse(UiExtensions.EMPTY.iterator().hasNext());
  }

  /**
   * Test {@link UiExtensions#getCssExtensions()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link UiExtension}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  @DisplayName("Test getCssExtensions(); given ArrayList() add UiExtension; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiExtensions.getCssExtensions()"})
  void testGetCssExtensions_givenArrayListAddUiExtension_thenReturnEmpty() {
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
   * Test {@link UiExtensions#getCssExtensions()}.
   * <ul>
   *   <li>Given {@link UiExtension} {@link UiExtension#getResourcePath()} return {@code Resource Path}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  @DisplayName("Test getCssExtensions(); given UiExtension getResourcePath() return 'Resource Path'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiExtensions.getCssExtensions()"})
  void testGetCssExtensions_givenUiExtensionGetResourcePathReturnResourcePath() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn("Resource Path");

    // Act
    List<UiExtension> actualCssExtensions = uiExtensions.getCssExtensions();

    // Assert
    verify(uiExtension).getResourcePath();
    assertTrue(actualCssExtensions.isEmpty());
  }

  /**
   * Test {@link UiExtensions#getCssExtensions()}.
   * <ul>
   *   <li>Given {@link UiExtension}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  @DisplayName("Test getCssExtensions(); given UiExtension; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiExtensions.getCssExtensions()"})
  void testGetCssExtensions_givenUiExtension_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(UiExtensions.EMPTY.getCssExtensions().isEmpty());
  }

  /**
   * Test {@link UiExtensions#getCssExtensions()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  @DisplayName("Test getCssExtensions(); then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiExtensions.getCssExtensions()"})
  void testGetCssExtensions_thenReturnSizeIsOne() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn(".css");

    // Act
    List<UiExtension> actualCssExtensions = uiExtensions.getCssExtensions();

    // Assert
    verify(uiExtension).getResourcePath();
    assertEquals(1, actualCssExtensions.size());
  }

  /**
   * Test {@link UiExtensions#getJsExtensions()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link UiExtension}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  @DisplayName("Test getJsExtensions(); given ArrayList() add UiExtension; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiExtensions.getJsExtensions()"})
  void testGetJsExtensions_givenArrayListAddUiExtension_thenReturnEmpty() {
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
   * Test {@link UiExtensions#getJsExtensions()}.
   * <ul>
   *   <li>Given {@link UiExtension} {@link UiExtension#getResourcePath()} return {@code .js}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  @DisplayName("Test getJsExtensions(); given UiExtension getResourcePath() return '.js'; then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiExtensions.getJsExtensions()"})
  void testGetJsExtensions_givenUiExtensionGetResourcePathReturnJs_thenReturnSizeIsOne() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn(".js");

    // Act
    List<UiExtension> actualJsExtensions = uiExtensions.getJsExtensions();

    // Assert
    verify(uiExtension).getResourcePath();
    assertEquals(1, actualJsExtensions.size());
  }

  /**
   * Test {@link UiExtensions#getJsExtensions()}.
   * <ul>
   *   <li>Given {@link UiExtension} {@link UiExtension#getResourcePath()} return {@code Resource Path}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  @DisplayName("Test getJsExtensions(); given UiExtension getResourcePath() return 'Resource Path'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiExtensions.getJsExtensions()"})
  void testGetJsExtensions_givenUiExtensionGetResourcePathReturnResourcePath() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn("Resource Path");

    // Act
    List<UiExtension> actualJsExtensions = uiExtensions.getJsExtensions();

    // Assert
    verify(uiExtension).getResourcePath();
    assertTrue(actualJsExtensions.isEmpty());
  }

  /**
   * Test {@link UiExtensions#getJsExtensions()}.
   * <ul>
   *   <li>Given {@link UiExtension}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  @DisplayName("Test getJsExtensions(); given UiExtension; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiExtensions.getJsExtensions()"})
  void testGetJsExtensions_givenUiExtension_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(UiExtensions.EMPTY.getJsExtensions().isEmpty());
  }

  /**
   * Test {@link UiExtensions#equals(Object)}, and {@link UiExtensions#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtensions#equals(Object)}
   *   <li>{@link UiExtensions#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtensions.equals(Object)", "int UiExtensions.hashCode()"})
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
   * Test {@link UiExtensions#equals(Object)}, and {@link UiExtensions#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtensions#equals(Object)}
   *   <li>{@link UiExtensions#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtensions.equals(Object)", "int UiExtensions.hashCode()"})
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
   * Test {@link UiExtensions#equals(Object)}, and {@link UiExtensions#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtensions#equals(Object)}
   *   <li>{@link UiExtensions#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtensions.equals(Object)", "int UiExtensions.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UiExtensions uiExtensions = UiExtensions.EMPTY;

    // Act and Assert
    assertEquals(uiExtensions, uiExtensions);
    int expectedHashCodeResult = uiExtensions.hashCode();
    assertEquals(expectedHashCodeResult, uiExtensions.hashCode());
  }

  /**
   * Test {@link UiExtensions#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtensions.equals(Object)", "int UiExtensions.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(UiExtensions.EMPTY, 1);
  }

  /**
   * Test {@link UiExtensions#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtensions.equals(Object)", "int UiExtensions.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<UiExtension> extensions = new ArrayList<>();
    extensions.add(new UiExtension("Resource Path", "Resource Location"));

    // Act and Assert
    assertNotEquals(new UiExtensions(extensions), UiExtensions.EMPTY);
  }

  /**
   * Test {@link UiExtensions#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtensions.equals(Object)", "int UiExtensions.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(UiExtensions.EMPTY, null);
  }

  /**
   * Test {@link UiExtensions#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiExtensions#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiExtensions.equals(Object)", "int UiExtensions.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(UiExtensions.EMPTY, "Different type to UiExtensions");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiExtensions#toString()}
   *   <li>{@link UiExtensions#getExtensions()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiExtensions.getExtensions()", "String UiExtensions.toString()"})
  void testGettersAndSetters() {
    // Arrange
    UiExtensions uiExtensions = new UiExtensions(new ArrayList<>());

    // Act
    String actualToStringResult = uiExtensions.toString();

    // Assert
    assertEquals("UiExtensions(extensions=[])", actualToStringResult);
    assertTrue(uiExtensions.getExtensions().isEmpty());
  }
}
