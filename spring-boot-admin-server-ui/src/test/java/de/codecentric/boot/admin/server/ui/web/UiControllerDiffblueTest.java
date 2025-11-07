package de.codecentric.boot.admin.server.ui.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.sun.security.auth.UserPrincipal;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.Palette;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.PollTimer;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.UiTheme;
import de.codecentric.boot.admin.server.ui.extensions.UiExtension;
import de.codecentric.boot.admin.server.ui.extensions.UiExtensions;
import de.codecentric.boot.admin.server.ui.web.UiController.ExternalView;
import de.codecentric.boot.admin.server.ui.web.UiController.Settings;
import de.codecentric.boot.admin.server.ui.web.UiController.Settings.SettingsBuilder;
import de.codecentric.boot.admin.server.ui.web.UiController.ViewSettings;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

@ContextConfiguration(classes = {UiController.class, String.class, SettingsBuilder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UiControllerDiffblueTest {
  @MockBean
  private Settings settings;

  @Autowired
  private UiController uiController;

  @MockBean
  private UiExtensions uiExtensions;

  @Autowired
  private SettingsBuilder settingsBuilder;

  /**
   * Test ExternalView {@link ExternalView#equals(Object)}, and {@link ExternalView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExternalView#equals(Object)}
   *   <li>{@link ExternalView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ExternalView equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExternalView.equals(Object)", "int ExternalView.hashCode()"})
  void testExternalViewEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ExternalView externalView = new ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>());
    ExternalView externalView2 = new ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>());

    // Act and Assert
    assertEquals(externalView, externalView2);
    int expectedHashCodeResult = externalView.hashCode();
    assertEquals(expectedHashCodeResult, externalView2.hashCode());
  }

  /**
   * Test ExternalView {@link ExternalView#equals(Object)}, and {@link ExternalView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExternalView#equals(Object)}
   *   <li>{@link ExternalView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ExternalView equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExternalView.equals(Object)", "int ExternalView.hashCode()"})
  void testExternalViewEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ExternalView externalView = new ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>());

    // Act and Assert
    assertEquals(externalView, externalView);
    int expectedHashCodeResult = externalView.hashCode();
    assertEquals(expectedHashCodeResult, externalView.hashCode());
  }

  /**
   * Test ExternalView {@link ExternalView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExternalView#equals(Object)}
   */
  @Test
  @DisplayName("Test ExternalView equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExternalView.equals(Object)", "int ExternalView.hashCode()"})
  void testExternalViewEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ExternalView externalView = new ExternalView("https://example.org/example", "https://example.org/example", 1, true,
        new ArrayList<>());

    // Act and Assert
    assertNotEquals(externalView, new ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>()));
  }

  /**
   * Test ExternalView {@link ExternalView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExternalView#equals(Object)}
   */
  @Test
  @DisplayName("Test ExternalView equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExternalView.equals(Object)", "int ExternalView.hashCode()"})
  void testExternalViewEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ExternalView externalView = new ExternalView("Label", "Url", 1, true, new ArrayList<>());

    // Act and Assert
    assertNotEquals(externalView, new ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>()));
  }

  /**
   * Test ExternalView {@link ExternalView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExternalView#equals(Object)}
   */
  @Test
  @DisplayName("Test ExternalView equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExternalView.equals(Object)", "int ExternalView.hashCode()"})
  void testExternalViewEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>()), null);
  }

  /**
   * Test ExternalView {@link ExternalView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExternalView#equals(Object)}
   */
  @Test
  @DisplayName("Test ExternalView equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExternalView.equals(Object)", "int ExternalView.hashCode()"})
  void testExternalViewEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>()),
        "Different type to ExternalView");
  }

  /**
   * Test ExternalView getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExternalView#toString()}
   *   <li>{@link ExternalView#getChildren()}
   *   <li>{@link ExternalView#getLabel()}
   *   <li>{@link ExternalView#getOrder()}
   *   <li>{@link ExternalView#getUrl()}
   *   <li>{@link ExternalView#isIframe()}
   * </ul>
   */
  @Test
  @DisplayName("Test ExternalView getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ExternalView.getChildren()", "String ExternalView.getLabel()",
      "Integer ExternalView.getOrder()", "String ExternalView.getUrl()", "boolean ExternalView.isIframe()",
      "String ExternalView.toString()"})
  void testExternalViewGettersAndSetters() {
    // Arrange
    ArrayList<ExternalView> children = new ArrayList<>();
    ExternalView externalView = new ExternalView("Label", "https://example.org/example", 1, true, children);

    // Act
    String actualToStringResult = externalView.toString();
    List<ExternalView> actualChildren = externalView.getChildren();
    String actualLabel = externalView.getLabel();
    Integer actualOrder = externalView.getOrder();
    String actualUrl = externalView.getUrl();
    boolean actualIsIframeResult = externalView.isIframe();

    // Assert
    assertEquals("Label", actualLabel);
    assertEquals("UiController.ExternalView(label=Label, url=https://example.org/example, order=1, iframe=true,"
        + " children=[])", actualToStringResult);
    assertEquals("https://example.org/example", actualUrl);
    assertEquals(1, actualOrder.intValue());
    assertTrue(actualIsIframeResult);
    assertTrue(actualChildren.isEmpty());
    assertSame(children, actualChildren);
  }

  /**
   * Test ExternalView {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}.
   * <ul>
   *   <li>Then return Children is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}
   */
  @Test
  @DisplayName("Test ExternalView new ExternalView(String, String, Integer, boolean, List); then return Children is ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExternalView.<init>(String, String, Integer, boolean, List)"})
  void testExternalViewNewExternalView_thenReturnChildrenIsArrayList() {
    // Arrange
    ArrayList<ExternalView> children = new ArrayList<>();
    children
        .add(new ExternalView("'label' must not be empty", "https://example.org/example", 1, true, new ArrayList<>()));

    // Act and Assert
    assertSame(children, (new ExternalView("Label", "https://example.org/example", 1, true, children)).getChildren());
  }

  /**
   * Test ExternalView {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}.
   * <ul>
   *   <li>Then return Children size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}
   */
  @Test
  @DisplayName("Test ExternalView new ExternalView(String, String, Integer, boolean, List); then return Children size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExternalView.<init>(String, String, Integer, boolean, List)"})
  void testExternalViewNewExternalView_thenReturnChildrenSizeIsTwo() {
    // Arrange
    ArrayList<ExternalView> children = new ArrayList<>();
    children
        .add(new ExternalView("'label' must not be empty", "https://example.org/example", 1, true, new ArrayList<>()));
    ExternalView externalView = new ExternalView("'label' must not be empty", "https://example.org/example", 1, true,
        new ArrayList<>());

    children.add(externalView);

    // Act and Assert
    List<ExternalView> children2 = (new ExternalView("Label", "https://example.org/example", 1, true, children))
        .getChildren();
    assertEquals(2, children2.size());
    assertSame(externalView, children2.get(1));
  }

  /**
   * Test ExternalView {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code Label}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}
   */
  @Test
  @DisplayName("Test ExternalView new ExternalView(String, String, Integer, boolean, List); when ArrayList(); then return 'Label'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExternalView.<init>(String, String, Integer, boolean, List)"})
  void testExternalViewNewExternalView_whenArrayList_thenReturnLabel() {
    // Arrange and Act
    ExternalView actualExternalView = new ExternalView("Label", "https://example.org/example", 1, true,
        new ArrayList<>());

    // Assert
    assertEquals("Label", actualExternalView.getLabel());
    assertEquals("https://example.org/example", actualExternalView.getUrl());
    assertEquals(1, actualExternalView.getOrder().intValue());
    assertTrue(actualExternalView.isIframe());
    assertTrue(actualExternalView.getChildren().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiController#UiController(String, UiExtensions, Settings)}
   *   <li>{@link UiController#getUiSettings()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UiController.<init>(String, UiExtensions, Settings)",
      "Settings UiController.getUiSettings()"})
  void testGettersAndSetters() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    SettingsBuilder builderResult = Settings.builder();
    SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertSame(uiSettings,
        (new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings)).getUiSettings());
  }

  /**
   * Test {@link UiController#getBaseUrl(UriComponentsBuilder)}.
   * <ul>
   *   <li>When newInstance.</li>
   *   <li>Then return {@code /}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiController#getBaseUrl(UriComponentsBuilder)}
   */
  @Test
  @DisplayName("Test getBaseUrl(UriComponentsBuilder); when newInstance; then return '/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String UiController.getBaseUrl(UriComponentsBuilder)"})
  void testGetBaseUrl_whenNewInstance_thenReturnSlash() {
    // Arrange, Act and Assert
    assertEquals("/", uiController.getBaseUrl(UriComponentsBuilder.newInstance()));
  }

  /**
   * Test {@link UiController#getCssExtensions()}.
   * <p>
   * Method under test: {@link UiController#getCssExtensions()}
   */
  @Test
  @DisplayName("Test getCssExtensions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiController.getCssExtensions()"})
  void testGetCssExtensions() {
    // Arrange
    when(uiExtensions.getCssExtensions()).thenReturn(new ArrayList<>());

    // Act
    List<UiExtension> actualCssExtensions = uiController.getCssExtensions();

    // Assert
    verify(uiExtensions).getCssExtensions();
    assertTrue(actualCssExtensions.isEmpty());
  }

  /**
   * Test {@link UiController#getJsExtensions()}.
   * <p>
   * Method under test: {@link UiController#getJsExtensions()}
   */
  @Test
  @DisplayName("Test getJsExtensions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List UiController.getJsExtensions()"})
  void testGetJsExtensions() {
    // Arrange
    when(uiExtensions.getJsExtensions()).thenReturn(new ArrayList<>());

    // Act
    List<UiExtension> actualJsExtensions = uiController.getJsExtensions();

    // Assert
    verify(uiExtensions).getJsExtensions();
    assertTrue(actualJsExtensions.isEmpty());
  }

  /**
   * Test {@link UiController#getUser(Principal)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiController#getUser(Principal)}
   */
  @Test
  @DisplayName("Test getUser(Principal); when 'null'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map UiController.getUser(Principal)"})
  void testGetUser_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(uiController.getUser(null).isEmpty());
  }

  /**
   * Test {@link UiController#getUser(Principal)}.
   * <ul>
   *   <li>When {@link UserPrincipal#UserPrincipal(String)} with name is {@code principal}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiController#getUser(Principal)}
   */
  @Test
  @DisplayName("Test getUser(Principal); when UserPrincipal(String) with name is 'principal'; then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map UiController.getUser(Principal)"})
  void testGetUser_whenUserPrincipalWithNameIsPrincipal_thenReturnSizeIsOne() {
    // Arrange and Act
    Map<String, Object> actualUser = uiController.getUser(new UserPrincipal("principal"));

    // Assert
    assertEquals(1, actualUser.size());
    assertEquals("principal", actualUser.get("name"));
  }

  /**
   * Test {@link UiController#index()}.
   * <p>
   * Method under test: {@link UiController#index()}
   */
  @Test
  @DisplayName("Test index()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String UiController.index()"})
  void testIndex() {
    // Arrange, Act and Assert
    assertEquals("index", uiController.index());
  }

  /**
   * Test {@link UiController#sbaSettings()}.
   * <p>
   * Method under test: {@link UiController#sbaSettings()}
   */
  @Test
  @DisplayName("Test sbaSettings()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String UiController.sbaSettings()"})
  void testSbaSettings() {
    // Arrange, Act and Assert
    assertEquals("sba-settings.js", uiController.sbaSettings());
  }

  /**
   * Test Settings {@link Settings#equals(Object)}, and {@link Settings#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Settings#equals(Object)}
   *   <li>{@link Settings#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Settings equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Settings.equals(Object)", "int Settings.hashCode()"})
  void testSettingsEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    SettingsBuilder builderResult = Settings.builder();
    SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test Settings {@link Settings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Settings#equals(Object)}
   */
  @Test
  @DisplayName("Test Settings equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Settings.equals(Object)", "int Settings.hashCode()"})
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    SettingsBuilder builderResult = Settings.builder();
    SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    SettingsBuilder builderResult2 = Settings.builder();
    SettingsBuilder enableToastsResult2 = builderResult2.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test Settings {@link Settings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Settings#equals(Object)}
   */
  @Test
  @DisplayName("Test Settings equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Settings.equals(Object)", "int Settings.hashCode()"})
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    SettingsBuilder builderResult = Settings.builder();
    SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Dr")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    SettingsBuilder builderResult2 = Settings.builder();
    SettingsBuilder enableToastsResult2 = builderResult2.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test Settings {@link Settings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Settings#equals(Object)}
   */
  @Test
  @DisplayName("Test Settings equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Settings.equals(Object)", "int Settings.hashCode()"})
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    SettingsBuilder builderResult = Settings.builder();
    SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(false);
    SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    SettingsBuilder builderResult2 = Settings.builder();
    SettingsBuilder enableToastsResult2 = builderResult2.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test Settings {@link Settings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Settings#equals(Object)}
   */
  @Test
  @DisplayName("Test Settings equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Settings.equals(Object)", "int Settings.hashCode()"})
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SettingsBuilder settingsBuilder = mock(SettingsBuilder.class);
    when(settingsBuilder.enableToasts(Mockito.<Boolean>any())).thenReturn(Settings.builder());
    SettingsBuilder settingsBuilder2 = mock(SettingsBuilder.class);
    when(settingsBuilder2.brand(Mockito.<String>any())).thenReturn(settingsBuilder);
    SettingsBuilder settingsBuilder3 = mock(SettingsBuilder.class);
    when(settingsBuilder3.availableLanguages(Mockito.<List<String>>any())).thenReturn(settingsBuilder2);
    SettingsBuilder enableToastsResult = settingsBuilder3.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);

    ArrayList<ExternalView> externalViews = new ArrayList<>();
    externalViews.add(new ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>()));
    SettingsBuilder notificationFilterEnabledResult = enableToastsResult.externalViews(externalViews)
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true);

    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    SettingsBuilder rememberMeEnabledResult = notificationFilterEnabledResult.pollTimer(pollTimer)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    SettingsBuilder builderResult = Settings.builder();
    SettingsBuilder enableToastsResult2 = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test Settings {@link Settings#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Settings#equals(Object)}
   */
  @Test
  @DisplayName("Test Settings equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Settings.equals(Object)", "int Settings.hashCode()"})
  void testSettingsEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    SettingsBuilder builderResult = Settings.builder();
    SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test Settings {@link Settings#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Settings#equals(Object)}
   */
  @Test
  @DisplayName("Test Settings equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Settings.equals(Object)", "int Settings.hashCode()"})
  void testSettingsEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    SettingsBuilder builderResult = Settings.builder();
    SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to Settings");
  }

  /**
   * Test Settings getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Settings#Settings(String, String, String, String, String, PollTimer, UiTheme, boolean, boolean, List, List, List, List, Boolean, Boolean)}
   *   <li>{@link Settings#toString()}
   *   <li>{@link Settings#getAvailableLanguages()}
   *   <li>{@link Settings#getBrand()}
   *   <li>{@link Settings#getEnableToasts()}
   *   <li>{@link Settings#getExternalViews()}
   *   <li>{@link Settings#getFavicon()}
   *   <li>{@link Settings#getFaviconDanger()}
   *   <li>{@link Settings#getHideInstanceUrl()}
   *   <li>{@link Settings#getLoginIcon()}
   *   <li>{@link Settings#getPollTimer()}
   *   <li>{@link Settings#getRoutes()}
   *   <li>{@link Settings#getTheme()}
   *   <li>{@link Settings#getTitle()}
   *   <li>{@link Settings#getViewSettings()}
   *   <li>{@link Settings#isNotificationFilterEnabled()}
   *   <li>{@link Settings#isRememberMeEnabled()}
   * </ul>
   */
  @Test
  @DisplayName("Test Settings getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void Settings.<init>(String, String, String, String, String, PollTimer, UiTheme, boolean, boolean, List, List, List, List, Boolean, Boolean)",
      "List Settings.getAvailableLanguages()", "String Settings.getBrand()", "Boolean Settings.getEnableToasts()",
      "List Settings.getExternalViews()", "String Settings.getFavicon()", "String Settings.getFaviconDanger()",
      "Boolean Settings.getHideInstanceUrl()", "String Settings.getLoginIcon()", "PollTimer Settings.getPollTimer()",
      "List Settings.getRoutes()", "UiTheme Settings.getTheme()", "String Settings.getTitle()",
      "List Settings.getViewSettings()", "boolean Settings.isNotificationFilterEnabled()",
      "boolean Settings.isRememberMeEnabled()", "String Settings.toString()"})
  void testSettingsGettersAndSetters() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    ArrayList<String> availableLanguages = new ArrayList<>();
    ArrayList<String> routes = new ArrayList<>();
    ArrayList<ExternalView> externalViews = new ArrayList<>();
    ArrayList<ViewSettings> viewSettings = new ArrayList<>();

    // Act
    Settings actualSettings = new Settings("Dr", "Brand", "Login Icon", "Favicon", "Favicon Danger", pollTimer, theme,
        true, true, availableLanguages, routes, externalViews, viewSettings, true, true);
    actualSettings.toString();
    List<String> actualAvailableLanguages = actualSettings.getAvailableLanguages();
    String actualBrand = actualSettings.getBrand();
    Boolean actualEnableToasts = actualSettings.getEnableToasts();
    List<ExternalView> actualExternalViews = actualSettings.getExternalViews();
    String actualFavicon = actualSettings.getFavicon();
    String actualFaviconDanger = actualSettings.getFaviconDanger();
    Boolean actualHideInstanceUrl = actualSettings.getHideInstanceUrl();
    String actualLoginIcon = actualSettings.getLoginIcon();
    PollTimer actualPollTimer = actualSettings.getPollTimer();
    List<String> actualRoutes = actualSettings.getRoutes();
    UiTheme actualTheme = actualSettings.getTheme();
    String actualTitle = actualSettings.getTitle();
    List<ViewSettings> actualViewSettings = actualSettings.getViewSettings();
    boolean actualIsNotificationFilterEnabledResult = actualSettings.isNotificationFilterEnabled();

    // Assert
    assertEquals("Brand", actualBrand);
    assertEquals("Dr", actualTitle);
    assertEquals("Favicon Danger", actualFaviconDanger);
    assertEquals("Favicon", actualFavicon);
    assertEquals("Login Icon", actualLoginIcon);
    assertTrue(actualEnableToasts);
    assertTrue(actualHideInstanceUrl);
    assertTrue(actualIsNotificationFilterEnabledResult);
    assertTrue(actualSettings.isRememberMeEnabled());
    assertTrue(actualAvailableLanguages.isEmpty());
    assertTrue(actualExternalViews.isEmpty());
    assertTrue(actualRoutes.isEmpty());
    assertTrue(actualViewSettings.isEmpty());
    assertSame(pollTimer, actualPollTimer);
    assertSame(theme, actualTheme);
    assertSame(availableLanguages, actualAvailableLanguages);
    assertSame(externalViews, actualExternalViews);
    assertSame(routes, actualRoutes);
    assertSame(viewSettings, actualViewSettings);
  }

  /**
   * Test Settings_SettingsBuilder {@link SettingsBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SettingsBuilder#build()}
   *   <li>{@link SettingsBuilder#availableLanguages(List)}
   *   <li>{@link SettingsBuilder#brand(String)}
   *   <li>{@link SettingsBuilder#enableToasts(Boolean)}
   *   <li>{@link SettingsBuilder#externalViews(List)}
   *   <li>{@link SettingsBuilder#favicon(String)}
   *   <li>{@link SettingsBuilder#faviconDanger(String)}
   *   <li>{@link SettingsBuilder#hideInstanceUrl(Boolean)}
   *   <li>{@link SettingsBuilder#loginIcon(String)}
   *   <li>{@link SettingsBuilder#notificationFilterEnabled(boolean)}
   *   <li>{@link SettingsBuilder#pollTimer(PollTimer)}
   *   <li>{@link SettingsBuilder#rememberMeEnabled(boolean)}
   *   <li>{@link SettingsBuilder#routes(List)}
   *   <li>{@link SettingsBuilder#theme(UiTheme)}
   *   <li>{@link SettingsBuilder#title(String)}
   *   <li>{@link SettingsBuilder#viewSettings(List)}
   * </ul>
   */
  @Test
  @DisplayName("Test Settings_SettingsBuilder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SettingsBuilder.<init>()", "SettingsBuilder SettingsBuilder.availableLanguages(List)",
      "SettingsBuilder SettingsBuilder.brand(String)", "Settings SettingsBuilder.build()",
      "SettingsBuilder SettingsBuilder.enableToasts(Boolean)", "SettingsBuilder SettingsBuilder.externalViews(List)",
      "SettingsBuilder SettingsBuilder.favicon(String)", "SettingsBuilder SettingsBuilder.faviconDanger(String)",
      "SettingsBuilder SettingsBuilder.hideInstanceUrl(Boolean)", "SettingsBuilder SettingsBuilder.loginIcon(String)",
      "SettingsBuilder SettingsBuilder.notificationFilterEnabled(boolean)",
      "SettingsBuilder SettingsBuilder.pollTimer(PollTimer)",
      "SettingsBuilder SettingsBuilder.rememberMeEnabled(boolean)", "SettingsBuilder SettingsBuilder.routes(List)",
      "SettingsBuilder SettingsBuilder.theme(UiTheme)", "SettingsBuilder SettingsBuilder.title(String)",
      "String SettingsBuilder.toString()", "SettingsBuilder SettingsBuilder.viewSettings(List)"})
  void testSettings_SettingsBuilderBuild() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    SettingsBuilder builderResult = Settings.builder();
    ArrayList<String> availableLanguages = new ArrayList<>();
    SettingsBuilder enableToastsResult = builderResult.availableLanguages(availableLanguages)
        .brand("Brand")
        .enableToasts(true);
    ArrayList<ExternalView> externalViews = new ArrayList<>();
    SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(externalViews)
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    ArrayList<String> routes = new ArrayList<>();
    SettingsBuilder routesResult = rememberMeEnabledResult.routes(routes);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    ArrayList<ViewSettings> viewSettings = new ArrayList<>();

    // Act
    Settings actualBuildResult = titleResult.viewSettings(viewSettings).build();

    // Assert
    assertEquals("Brand", actualBuildResult.getBrand());
    assertEquals("Dr", actualBuildResult.getTitle());
    assertEquals("Favicon Danger", actualBuildResult.getFaviconDanger());
    assertEquals("Favicon", actualBuildResult.getFavicon());
    assertEquals("Login Icon", actualBuildResult.getLoginIcon());
    assertTrue(actualBuildResult.getEnableToasts());
    assertTrue(actualBuildResult.getHideInstanceUrl());
    assertTrue(actualBuildResult.isNotificationFilterEnabled());
    assertTrue(actualBuildResult.isRememberMeEnabled());
    List<String> availableLanguages2 = actualBuildResult.getAvailableLanguages();
    assertTrue(availableLanguages2.isEmpty());
    List<ExternalView> externalViews2 = actualBuildResult.getExternalViews();
    assertTrue(externalViews2.isEmpty());
    List<String> routes2 = actualBuildResult.getRoutes();
    assertTrue(routes2.isEmpty());
    List<ViewSettings> viewSettings2 = actualBuildResult.getViewSettings();
    assertTrue(viewSettings2.isEmpty());
    assertSame(pollTimer, actualBuildResult.getPollTimer());
    assertSame(theme, actualBuildResult.getTheme());
    assertSame(availableLanguages, availableLanguages2);
    assertSame(externalViews, externalViews2);
    assertSame(routes, routes2);
    assertSame(viewSettings, viewSettings2);
  }

  /**
   * Test {@link UiController#variablesCss()}.
   * <p>
   * Method under test: {@link UiController#variablesCss()}
   */
  @Test
  @DisplayName("Test variablesCss()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String UiController.variablesCss()"})
  void testVariablesCss() {
    // Arrange, Act and Assert
    assertEquals("variables.css", uiController.variablesCss());
  }

  /**
   * Test {@link UiController#login()}.
   * <p>
   * Method under test: {@link UiController#login()}
   */
  @Test
  @DisplayName("Test login()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String UiController.login()"})
  void testLogin() {
    // Arrange, Act and Assert
    assertEquals("login", uiController.login());
  }

  /**
   * Test ViewSettings {@link ViewSettings#equals(Object)}, and {@link ViewSettings#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ViewSettings#equals(Object)}
   *   <li>{@link ViewSettings#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ViewSettings equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ViewSettings.equals(Object)", "int ViewSettings.hashCode()"})
  void testViewSettingsEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ViewSettings viewSettings = new ViewSettings("Name", true);
    ViewSettings viewSettings2 = new ViewSettings("Name", true);

    // Act and Assert
    assertEquals(viewSettings, viewSettings2);
    int expectedHashCodeResult = viewSettings.hashCode();
    assertEquals(expectedHashCodeResult, viewSettings2.hashCode());
  }

  /**
   * Test ViewSettings {@link ViewSettings#equals(Object)}, and {@link ViewSettings#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ViewSettings#equals(Object)}
   *   <li>{@link ViewSettings#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ViewSettings equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ViewSettings.equals(Object)", "int ViewSettings.hashCode()"})
  void testViewSettingsEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ViewSettings viewSettings = new ViewSettings("Name", true);

    // Act and Assert
    assertEquals(viewSettings, viewSettings);
    int expectedHashCodeResult = viewSettings.hashCode();
    assertEquals(expectedHashCodeResult, viewSettings.hashCode());
  }

  /**
   * Test ViewSettings {@link ViewSettings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ViewSettings#equals(Object)}
   */
  @Test
  @DisplayName("Test ViewSettings equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ViewSettings.equals(Object)", "int ViewSettings.hashCode()"})
  void testViewSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ViewSettings viewSettings = new ViewSettings("de.codecentric.boot.admin.server.ui.web.UiController$ViewSettings",
        true);

    // Act and Assert
    assertNotEquals(viewSettings, new ViewSettings("Name", true));
  }

  /**
   * Test ViewSettings {@link ViewSettings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ViewSettings#equals(Object)}
   */
  @Test
  @DisplayName("Test ViewSettings equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ViewSettings.equals(Object)", "int ViewSettings.hashCode()"})
  void testViewSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ViewSettings viewSettings = new ViewSettings("Name", false);

    // Act and Assert
    assertNotEquals(viewSettings, new ViewSettings("Name", true));
  }

  /**
   * Test ViewSettings {@link ViewSettings#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ViewSettings#equals(Object)}
   */
  @Test
  @DisplayName("Test ViewSettings equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ViewSettings.equals(Object)", "int ViewSettings.hashCode()"})
  void testViewSettingsEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ViewSettings("Name", true), null);
  }

  /**
   * Test ViewSettings {@link ViewSettings#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ViewSettings#equals(Object)}
   */
  @Test
  @DisplayName("Test ViewSettings equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ViewSettings.equals(Object)", "int ViewSettings.hashCode()"})
  void testViewSettingsEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ViewSettings("Name", true), "Different type to ViewSettings");
  }

  /**
   * Test ViewSettings getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ViewSettings#setEnabled(boolean)}
   *   <li>{@link ViewSettings#toString()}
   *   <li>{@link ViewSettings#getName()}
   *   <li>{@link ViewSettings#isEnabled()}
   * </ul>
   */
  @Test
  @DisplayName("Test ViewSettings getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ViewSettings.getName()", "boolean ViewSettings.isEnabled()",
      "void ViewSettings.setEnabled(boolean)", "String ViewSettings.toString()"})
  void testViewSettingsGettersAndSetters() {
    // Arrange
    ViewSettings viewSettings = new ViewSettings("Name", true);

    // Act
    viewSettings.setEnabled(true);
    String actualToStringResult = viewSettings.toString();
    String actualName = viewSettings.getName();

    // Assert
    assertEquals("Name", actualName);
    assertEquals("UiController.ViewSettings(name=Name, enabled=true)", actualToStringResult);
    assertTrue(viewSettings.isEnabled());
  }

  /**
   * Test ViewSettings {@link ViewSettings#ViewSettings(String, boolean)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ViewSettings#ViewSettings(String, boolean)}
   */
  @Test
  @DisplayName("Test ViewSettings new ViewSettings(String, boolean); when 'Name'; then return 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ViewSettings.<init>(String, boolean)"})
  void testViewSettingsNewViewSettings_whenName_thenReturnName() {
    // Arrange and Act
    ViewSettings actualViewSettings = new ViewSettings("Name", true);

    // Assert
    assertEquals("Name", actualViewSettings.getName());
    assertTrue(actualViewSettings.isEnabled());
  }
}
