package de.codecentric.boot.admin.server.ui.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UiExtensions.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class UiExtensionsDiffblueTest {
  @Autowired private List<UiExtension> list;

  @MockitoBean private UiExtension uiExtension;

  @Autowired private UiExtensions uiExtensions;

  /**
   * Test {@link UiExtensions#UiExtensions(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return CssExtensions Empty.
   * </ul>
   *
   * <p>Method under test: {@link UiExtensions#UiExtensions(List)}
   */
  @Test
  @DisplayName("Test new UiExtensions(List); when ArrayList(); then return CssExtensions Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   * Test {@link UiExtensions#getCssExtensions()}.
   *
   * <p>Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  @DisplayName("Test getCssExtensions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UiExtensions.getCssExtensions()"})
  void testGetCssExtensions() {
    // Arrange
    ArrayList<UiExtension> extensions = new ArrayList<>();
    UiExtension uiExtension = new UiExtension(".css", ".css");
    extensions.add(uiExtension);
    extensions.add(new UiExtension("Resource Path", "Resource Location"));

    // Act
    List<UiExtension> actualCssExtensions = new UiExtensions(extensions).getCssExtensions();

    // Assert
    assertEquals(1, actualCssExtensions.size());
    assertSame(uiExtension, actualCssExtensions.get(0));
  }

  /**
   * Test {@link UiExtensions#getCssExtensions()}.
   *
   * <ul>
   *   <li>Given {@link UiExtensions#EMPTY}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  @DisplayName("Test getCssExtensions(); given EMPTY; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UiExtensions.getCssExtensions()"})
  void testGetCssExtensions_givenEmpty_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(UiExtensions.EMPTY.getCssExtensions().isEmpty());
  }

  /**
   * Test {@link UiExtensions#getCssExtensions()}.
   *
   * <ul>
   *   <li>Given {@link UiExtension} {@link UiExtension#getResourcePath()} return {@code .css}.
   * </ul>
   *
   * <p>Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  @DisplayName("Test getCssExtensions(); given UiExtension getResourcePath() return '.css'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UiExtensions.getCssExtensions()"})
  void testGetCssExtensions_givenUiExtensionGetResourcePathReturnCss() {
    // Arrange
    when(uiExtension.getResourcePath()).thenReturn(".css");

    // Act
    List<UiExtension> actualCssExtensions = uiExtensions.getCssExtensions();

    // Assert
    verify(uiExtension).getResourcePath();
    assertEquals(1, actualCssExtensions.size());
  }

  /**
   * Test {@link UiExtensions#getCssExtensions()}.
   *
   * <ul>
   *   <li>Given {@link UiExtension} {@link UiExtension#getResourcePath()} return {@code Resource
   *       Path}.
   * </ul>
   *
   * <p>Method under test: {@link UiExtensions#getCssExtensions()}
   */
  @Test
  @DisplayName(
      "Test getCssExtensions(); given UiExtension getResourcePath() return 'Resource Path'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   * Test {@link UiExtensions#getJsExtensions()}.
   *
   * <p>Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  @DisplayName("Test getJsExtensions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UiExtensions.getJsExtensions()"})
  void testGetJsExtensions() {
    // Arrange
    ArrayList<UiExtension> extensions = new ArrayList<>();
    UiExtension uiExtension = new UiExtension(".js", ".js");
    extensions.add(uiExtension);
    extensions.add(new UiExtension("Resource Path", "Resource Location"));

    // Act
    List<UiExtension> actualJsExtensions = new UiExtensions(extensions).getJsExtensions();

    // Assert
    assertEquals(1, actualJsExtensions.size());
    assertSame(uiExtension, actualJsExtensions.get(0));
  }

  /**
   * Test {@link UiExtensions#getJsExtensions()}.
   *
   * <ul>
   *   <li>Given {@link UiExtensions#EMPTY}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  @DisplayName("Test getJsExtensions(); given EMPTY; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List UiExtensions.getJsExtensions()"})
  void testGetJsExtensions_givenEmpty_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(UiExtensions.EMPTY.getJsExtensions().isEmpty());
  }

  /**
   * Test {@link UiExtensions#getJsExtensions()}.
   *
   * <ul>
   *   <li>Given {@link UiExtension} {@link UiExtension#getResourcePath()} return {@code .js}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  @DisplayName(
      "Test getJsExtensions(); given UiExtension getResourcePath() return '.js'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>Given {@link UiExtension} {@link UiExtension#getResourcePath()} return {@code Resource
   *       Path}.
   * </ul>
   *
   * <p>Method under test: {@link UiExtensions#getJsExtensions()}
   */
  @Test
  @DisplayName("Test getJsExtensions(); given UiExtension getResourcePath() return 'Resource Path'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
}
