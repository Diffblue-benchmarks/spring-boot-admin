package de.codecentric.boot.admin.server.ui.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

@ContextConfiguration(classes = {UiController.class, String.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class UiControllerDiffblueTest {
  @MockitoBean private Settings settings;

  @Autowired private UiController uiController;

  @MockitoBean private UiExtensions uiExtensions;

  /**
   * Test ExternalView {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}.
   *
   * <ul>
   *   <li>Then return Children is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}
   */
  @Test
  @DisplayName(
      "Test ExternalView new ExternalView(String, String, Integer, boolean, List); then return Children is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExternalView.<init>(String, String, Integer, boolean, List)"})
  void testExternalViewNewExternalView_thenReturnChildrenIsArrayList() {
    // Arrange
    ArrayList<ExternalView> children = new ArrayList<>();
    ExternalView externalView =
        new ExternalView(
            "'label' must not be empty", "https://example.org/example", 1, true, new ArrayList<>());
    children.add(externalView);

    // Act
    ExternalView actualExternalView =
        new ExternalView("Label", "https://example.org/example", 1, true, children);

    // Assert
    assertSame(children, actualExternalView.getChildren());
  }

  /**
   * Test ExternalView {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}.
   *
   * <ul>
   *   <li>Then return Children size is two.
   * </ul>
   *
   * <p>Method under test: {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}
   */
  @Test
  @DisplayName(
      "Test ExternalView new ExternalView(String, String, Integer, boolean, List); then return Children size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExternalView.<init>(String, String, Integer, boolean, List)"})
  void testExternalViewNewExternalView_thenReturnChildrenSizeIsTwo() {
    // Arrange
    ArrayList<ExternalView> children = new ArrayList<>();
    ExternalView externalView =
        new ExternalView(
            "'label' must not be empty", "https://example.org/example", 1, true, new ArrayList<>());
    children.add(externalView);
    ExternalView externalView2 =
        new ExternalView(
            "'label' must not be empty", "https://example.org/example", 1, true, new ArrayList<>());
    children.add(externalView2);

    // Act
    ExternalView actualExternalView =
        new ExternalView("Label", "https://example.org/example", 1, true, children);

    // Assert
    List<ExternalView> children2 = actualExternalView.getChildren();
    assertEquals(2, children2.size());
    assertSame(externalView2, children2.get(1));
  }

  /**
   * Test ExternalView {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}.
   *
   * <ul>
   *   <li>When {@code Label}.
   *   <li>Then return {@code Label}.
   * </ul>
   *
   * <p>Method under test: {@link ExternalView#ExternalView(String, String, Integer, boolean, List)}
   */
  @Test
  @DisplayName(
      "Test ExternalView new ExternalView(String, String, Integer, boolean, List); when 'Label'; then return 'Label'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExternalView.<init>(String, String, Integer, boolean, List)"})
  void testExternalViewNewExternalView_whenLabel_thenReturnLabel() {
    // Arrange and Act
    ExternalView actualExternalView =
        new ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>());

    // Assert
    assertEquals("Label", actualExternalView.getLabel());
    assertEquals("https://example.org/example", actualExternalView.getUrl());
    assertEquals(1, actualExternalView.getOrder().intValue());
    assertTrue(actualExternalView.isIframe());
    assertTrue(actualExternalView.getChildren().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link UiController#UiController(String, UiExtensions, Settings)}
   *   <li>{@link UiController#getUiSettings()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UiController.<init>(String, UiExtensions, Settings)",
    "Settings UiController.getUiSettings()"
  })
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

    SettingsBuilder enableToastsResult =
        builderResult.availableLanguages(new ArrayList<>()).brand("Brand").enableToasts(true);

    SettingsBuilder rememberMeEnabledResult =
        enableToastsResult
            .externalViews(new ArrayList<>())
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

    // Act
    UiController actualUiController =
        new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings);

    // Assert
    assertSame(uiSettings, actualUiController.getUiSettings());
  }

  /**
   * Test {@link UiController#getBaseUrl(UriComponentsBuilder)}.
   *
   * <ul>
   *   <li>Given {@link PollTimer} (default constructor) Cache is one.
   *   <li>Then return {@code https://example.org/example/}.
   * </ul>
   *
   * <p>Method under test: {@link UiController#getBaseUrl(UriComponentsBuilder)}
   */
  @Test
  @DisplayName(
      "Test getBaseUrl(UriComponentsBuilder); given PollTimer (default constructor) Cache is one; then return 'https://example.org/example/'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UiController.getBaseUrl(UriComponentsBuilder)"})
  void testGetBaseUrl_givenPollTimerCacheIsOne_thenReturnHttpsExampleOrgExample() {
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

    SettingsBuilder enableToastsResult =
        builderResult.availableLanguages(new ArrayList<>()).brand("Brand").enableToasts(true);

    SettingsBuilder rememberMeEnabledResult =
        enableToastsResult
            .externalViews(new ArrayList<>())
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
    UiController uiController =
        new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings);

    // Act and Assert
    assertEquals(
        "https://example.org/example/",
        uiController.getBaseUrl(UriComponentsBuilder.newInstance()));
  }

  /**
   * Test {@link UiController#getBaseUrl(UriComponentsBuilder)}.
   *
   * <ul>
   *   <li>Given {@link UiExtensions}.
   *   <li>When newInstance.
   *   <li>Then return {@code /}.
   * </ul>
   *
   * <p>Method under test: {@link UiController#getBaseUrl(UriComponentsBuilder)}
   */
  @Test
  @DisplayName(
      "Test getBaseUrl(UriComponentsBuilder); given UiExtensions; when newInstance; then return '/'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UiController.getBaseUrl(UriComponentsBuilder)"})
  void testGetBaseUrl_givenUiExtensions_whenNewInstance_thenReturnSlash() {
    // Arrange, Act and Assert
    assertEquals("/", uiController.getBaseUrl(UriComponentsBuilder.newInstance()));
  }

  /**
   * Test {@link UiController#getCssExtensions()}.
   *
   * <p>Method under test: {@link UiController#getCssExtensions()}
   */
  @Test
  @DisplayName("Test getCssExtensions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link UiController#getJsExtensions()}
   */
  @Test
  @DisplayName("Test getJsExtensions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link UiController#getUser(Principal)}
   */
  @Test
  @DisplayName("Test getUser(Principal); when 'null'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map UiController.getUser(Principal)"})
  void testGetUser_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(uiController.getUser(null).isEmpty());
  }

  /**
   * Test {@link UiController#getUser(Principal)}.
   *
   * <ul>
   *   <li>When {@link UserPrincipal#UserPrincipal(String)} with name is {@code principal}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link UiController#getUser(Principal)}
   */
  @Test
  @DisplayName(
      "Test getUser(Principal); when UserPrincipal(String) with name is 'principal'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link UiController#index()}
   */
  @Test
  @DisplayName("Test index()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UiController.index()"})
  void testIndex() {
    // Arrange, Act and Assert
    assertEquals("index", uiController.index());
  }

  /**
   * Test {@link UiController#sbaSettings()}.
   *
   * <p>Method under test: {@link UiController#sbaSettings()}
   */
  @Test
  @DisplayName("Test sbaSettings()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UiController.sbaSettings()"})
  void testSbaSettings() {
    // Arrange, Act and Assert
    assertEquals("sba-settings.js", uiController.sbaSettings());
  }

  /**
   * Test {@link UiController#variablesCss()}.
   *
   * <p>Method under test: {@link UiController#variablesCss()}
   */
  @Test
  @DisplayName("Test variablesCss()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UiController.variablesCss()"})
  void testVariablesCss() {
    // Arrange, Act and Assert
    assertEquals("variables.css", uiController.variablesCss());
  }

  /**
   * Test {@link UiController#login()}.
   *
   * <p>Method under test: {@link UiController#login()}
   */
  @Test
  @DisplayName("Test login()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String UiController.login()"})
  void testLogin() {
    // Arrange, Act and Assert
    assertEquals("login", uiController.login());
  }

  /**
   * Test ViewSettings {@link ViewSettings#ViewSettings(String, boolean)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then return {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link ViewSettings#ViewSettings(String, boolean)}
   */
  @Test
  @DisplayName(
      "Test ViewSettings new ViewSettings(String, boolean); when 'Name'; then return 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ViewSettings.<init>(String, boolean)"})
  void testViewSettingsNewViewSettings_whenName_thenReturnName() {
    // Arrange and Act
    ViewSettings actualViewSettings = new ViewSettings("Name", true);

    // Assert
    assertEquals("Name", actualViewSettings.getName());
    assertTrue(actualViewSettings.isEnabled());
  }
}
