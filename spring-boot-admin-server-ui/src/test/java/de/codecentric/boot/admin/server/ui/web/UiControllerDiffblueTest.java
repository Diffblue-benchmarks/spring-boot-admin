package de.codecentric.boot.admin.server.ui.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.sun.security.auth.UserPrincipal;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties;
import de.codecentric.boot.admin.server.ui.extensions.UiExtension;
import de.codecentric.boot.admin.server.ui.extensions.UiExtensions;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.util.UriComponentsBuilder;

class UiControllerDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiController.ExternalView#equals(Object)}
   *   <li>{@link UiController.ExternalView#hashCode()}
   * </ul>
   */
  @Test
  void testExternalViewEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UiController.ExternalView externalView = new UiController.ExternalView("Label", "https://example.org/example", 1,
        true, new ArrayList<>());
    UiController.ExternalView externalView2 = new UiController.ExternalView("Label", "https://example.org/example", 1,
        true, new ArrayList<>());

    // Act and Assert
    assertEquals(externalView, externalView2);
    int expectedHashCodeResult = externalView.hashCode();
    assertEquals(expectedHashCodeResult, externalView2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiController.ExternalView#equals(Object)}
   *   <li>{@link UiController.ExternalView#hashCode()}
   * </ul>
   */
  @Test
  void testExternalViewEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UiController.ExternalView externalView = new UiController.ExternalView("Label", "https://example.org/example", 1,
        true, new ArrayList<>());

    // Act and Assert
    assertEquals(externalView, externalView);
    int expectedHashCodeResult = externalView.hashCode();
    assertEquals(expectedHashCodeResult, externalView.hashCode());
  }

  /**
   * Method under test: {@link UiController.ExternalView#equals(Object)}
   */
  @Test
  void testExternalViewEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UiController.ExternalView externalView = new UiController.ExternalView("https://example.org/example",
        "https://example.org/example", 1, true, new ArrayList<>());

    // Act and Assert
    assertNotEquals(externalView,
        new UiController.ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>()));
  }

  /**
   * Method under test: {@link UiController.ExternalView#equals(Object)}
   */
  @Test
  void testExternalViewEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UiController.ExternalView externalView = new UiController.ExternalView("Label", "Url", 1, true, new ArrayList<>());

    // Act and Assert
    assertNotEquals(externalView,
        new UiController.ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>()));
  }

  /**
   * Method under test: {@link UiController.ExternalView#equals(Object)}
   */
  @Test
  void testExternalViewEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UiController.ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>()),
        null);
  }

  /**
   * Method under test: {@link UiController.ExternalView#equals(Object)}
   */
  @Test
  void testExternalViewEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UiController.ExternalView("Label", "https://example.org/example", 1, true, new ArrayList<>()),
        "Different type to ExternalView");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiController.ExternalView#toString()}
   *   <li>{@link UiController.ExternalView#getChildren()}
   *   <li>{@link UiController.ExternalView#getLabel()}
   *   <li>{@link UiController.ExternalView#getOrder()}
   *   <li>{@link UiController.ExternalView#getUrl()}
   *   <li>{@link UiController.ExternalView#isIframe()}
   * </ul>
   */
  @Test
  void testExternalViewGettersAndSetters() {
    // Arrange
    ArrayList<UiController.ExternalView> children = new ArrayList<>();
    UiController.ExternalView externalView = new UiController.ExternalView("Label", "https://example.org/example", 1,
        true, children);

    // Act
    String actualToStringResult = externalView.toString();
    List<UiController.ExternalView> actualChildren = externalView.getChildren();
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
   * Method under test:
   * {@link UiController.ExternalView#ExternalView(String, String, Integer, boolean, List)}
   */
  @Test
  void testExternalViewNewExternalView() {
    // Arrange
    ArrayList<UiController.ExternalView> children = new ArrayList<>();

    // Act
    UiController.ExternalView actualExternalView = new UiController.ExternalView("Label", "https://example.org/example",
        1, true, children);

    // Assert
    assertEquals("Label", actualExternalView.getLabel());
    assertEquals("https://example.org/example", actualExternalView.getUrl());
    assertEquals(1, actualExternalView.getOrder().intValue());
    assertTrue(actualExternalView.isIframe());
    List<UiController.ExternalView> children2 = actualExternalView.getChildren();
    assertTrue(children2.isEmpty());
    assertSame(children, children2);
  }

  /**
   * Method under test:
   * {@link UiController.ExternalView#ExternalView(String, String, Integer, boolean, List)}
   */
  @Test
  void testExternalViewNewExternalView2() {
    // Arrange
    ArrayList<UiController.ExternalView> children = new ArrayList<>();
    children.add(new UiController.ExternalView("'label' must not be empty", "https://example.org/example", 1, true,
        new ArrayList<>()));

    // Act
    UiController.ExternalView actualExternalView = new UiController.ExternalView("Label", "https://example.org/example",
        1, true, children);

    // Assert
    assertEquals("Label", actualExternalView.getLabel());
    assertEquals("https://example.org/example", actualExternalView.getUrl());
    assertEquals(1, actualExternalView.getOrder().intValue());
    assertTrue(actualExternalView.isIframe());
    assertSame(children, actualExternalView.getChildren());
  }

  /**
   * Method under test:
   * {@link UiController.ExternalView#ExternalView(String, String, Integer, boolean, List)}
   */
  @Test
  void testExternalViewNewExternalView3() {
    // Arrange
    ArrayList<UiController.ExternalView> children = new ArrayList<>();
    children.add(new UiController.ExternalView("'label' must not be empty", "https://example.org/example", 1, true,
        new ArrayList<>()));
    children.add(new UiController.ExternalView("'label' must not be empty", "https://example.org/example", 1, true,
        new ArrayList<>()));

    // Act
    UiController.ExternalView actualExternalView = new UiController.ExternalView("Label", "https://example.org/example",
        1, true, children);

    // Assert
    assertEquals("Label", actualExternalView.getLabel());
    assertEquals("https://example.org/example", actualExternalView.getUrl());
    assertEquals(1, actualExternalView.getOrder().intValue());
    assertTrue(actualExternalView.isIframe());
    assertSame(children, actualExternalView.getChildren());
  }

  /**
   * Method under test: {@link UiController#getBaseUrl(UriComponentsBuilder)}
   */
  @Test
  void testGetBaseUrl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();
    UiController uiController = new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings);
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    // Act
    String actualBaseUrl = uiController.getBaseUrl(uriBuilder);

    // Assert
    assertEquals("https", uriBuilder.build().getScheme());
    assertEquals("https://example.org/example/", actualBaseUrl);
    assertEquals("https://example.org/example/", uriBuilder.toUriString());
  }

  /**
   * Method under test: {@link UiController#getBaseUrl(UriComponentsBuilder)}
   */
  @Test
  void testGetBaseUrl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();
    UiController uiController = new UiController("/", UiExtensions.EMPTY, uiSettings);
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    // Act and Assert
    assertEquals("/", uiController.getBaseUrl(uriBuilder));
    assertEquals("/", uriBuilder.toUriString());
    assertNull(uriBuilder.build().getScheme());
  }

  /**
   * Method under test: {@link UiController#getBaseUrl(UriComponentsBuilder)}
   */
  @Test
  void testGetBaseUrl3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();
    UiController uiController = new UiController("https://example.org/example", mock(UiExtensions.class), uiSettings);
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    // Act
    String actualBaseUrl = uiController.getBaseUrl(uriBuilder);

    // Assert
    assertEquals("https", uriBuilder.build().getScheme());
    assertEquals("https://example.org/example/", actualBaseUrl);
    assertEquals("https://example.org/example/", uriBuilder.toUriString());
  }

  /**
   * Method under test: {@link UiController#getCssExtensions()}
   */
  @Test
  void testGetCssExtensions() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertTrue(
        (new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings)).getCssExtensions().isEmpty());
  }

  /**
   * Method under test: {@link UiController#getCssExtensions()}
   */
  @Test
  void testGetCssExtensions2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    UiExtensions uiExtensions = mock(UiExtensions.class);
    ArrayList<UiExtension> uiExtensionList = new ArrayList<>();
    when(uiExtensions.getCssExtensions()).thenReturn(uiExtensionList);

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act
    List<UiExtension> actualCssExtensions = (new UiController("https://example.org/example", uiExtensions, uiSettings))
        .getCssExtensions();

    // Assert
    verify(uiExtensions).getCssExtensions();
    assertTrue(actualCssExtensions.isEmpty());
    assertSame(uiExtensionList, actualCssExtensions);
  }

  /**
   * Method under test: {@link UiController#getJsExtensions()}
   */
  @Test
  void testGetJsExtensions() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertTrue(
        (new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings)).getJsExtensions().isEmpty());
  }

  /**
   * Method under test: {@link UiController#getJsExtensions()}
   */
  @Test
  void testGetJsExtensions2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    UiExtensions uiExtensions = mock(UiExtensions.class);
    ArrayList<UiExtension> uiExtensionList = new ArrayList<>();
    when(uiExtensions.getJsExtensions()).thenReturn(uiExtensionList);

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act
    List<UiExtension> actualJsExtensions = (new UiController("https://example.org/example", uiExtensions, uiSettings))
        .getJsExtensions();

    // Assert
    verify(uiExtensions).getJsExtensions();
    assertTrue(actualJsExtensions.isEmpty());
    assertSame(uiExtensionList, actualJsExtensions);
  }

  /**
   * Method under test: {@link UiController#getUser(Principal)}
   */
  @Test
  void testGetUser() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();
    UiController uiController = new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings);

    // Act
    Map<String, Object> actualUser = uiController.getUser(new UserPrincipal("principal"));

    // Assert
    assertEquals(1, actualUser.size());
    assertEquals("principal", actualUser.get("name"));
  }

  /**
   * Method under test: {@link UiController#getUser(Principal)}
   */
  @Test
  void testGetUser2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertTrue(
        (new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings)).getUser(null).isEmpty());
  }

  /**
   * Method under test: {@link UiController#getUser(Principal)}
   */
  @Test
  void testGetUser3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();
    UiController uiController = new UiController("https://example.org/example", mock(UiExtensions.class), uiSettings);

    // Act
    Map<String, Object> actualUser = uiController.getUser(new UserPrincipal("principal"));

    // Assert
    assertEquals(1, actualUser.size());
    assertEquals("principal", actualUser.get("name"));
  }

  /**
   * Method under test: {@link UiController#index()}
   */
  @Test
  void testIndex() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals("index", (new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings)).index());
  }

  /**
   * Method under test: {@link UiController#index()}
   */
  @Test
  void testIndex2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals("index",
        (new UiController("https://example.org/example", mock(UiExtensions.class), uiSettings)).index());
  }

  /**
   * Method under test: {@link UiController#sbaSettings()}
   */
  @Test
  void testSbaSettings() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals("sba-settings.js",
        (new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings)).sbaSettings());
  }

  /**
   * Method under test: {@link UiController#sbaSettings()}
   */
  @Test
  void testSbaSettings2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals("sba-settings.js",
        (new UiController("https://example.org/example", mock(UiExtensions.class), uiSettings)).sbaSettings());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiController.Settings#equals(Object)}
   *   <li>{@link UiController.Settings#hashCode()}
   * </ul>
   */
  @Test
  void testSettingsEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link UiController.Settings#equals(Object)}
   */
  @Test
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult2 = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult2 = builderResult2.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    UiController.Settings.SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    UiController.Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link UiController.Settings#equals(Object)}
   */
  @Test
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UiController.Settings.SettingsBuilder settingsBuilder = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder.availableLanguages(Mockito.<List<String>>any())).thenReturn(UiController.Settings.builder());
    UiController.Settings.SettingsBuilder enableToastsResult = settingsBuilder.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder notificationFilterEnabledResult = enableToastsResult
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true);

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = notificationFilterEnabledResult.pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult2 = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    UiController.Settings.SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    UiController.Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link UiController.Settings#equals(Object)}
   */
  @Test
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UiController.Settings.SettingsBuilder settingsBuilder = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder.brand(Mockito.<String>any())).thenReturn(UiController.Settings.builder());
    UiController.Settings.SettingsBuilder settingsBuilder2 = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder2.availableLanguages(Mockito.<List<String>>any())).thenReturn(settingsBuilder);
    UiController.Settings.SettingsBuilder enableToastsResult = settingsBuilder2.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder notificationFilterEnabledResult = enableToastsResult
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true);

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = notificationFilterEnabledResult.pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult2 = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    UiController.Settings.SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    UiController.Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link UiController.Settings#equals(Object)}
   */
  @Test
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    UiController.Settings.SettingsBuilder settingsBuilder = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder.enableToasts(Mockito.<Boolean>any())).thenReturn(UiController.Settings.builder());
    UiController.Settings.SettingsBuilder settingsBuilder2 = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder2.brand(Mockito.<String>any())).thenReturn(settingsBuilder);
    UiController.Settings.SettingsBuilder settingsBuilder3 = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder3.availableLanguages(Mockito.<List<String>>any())).thenReturn(settingsBuilder2);
    UiController.Settings.SettingsBuilder enableToastsResult = settingsBuilder3.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder notificationFilterEnabledResult = enableToastsResult
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true);

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = notificationFilterEnabledResult.pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult2 = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    UiController.Settings.SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    UiController.Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link UiController.Settings#equals(Object)}
   */
  @Test
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    UiController.Settings.SettingsBuilder settingsBuilder = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder.enableToasts(Mockito.<Boolean>any())).thenReturn(UiController.Settings.builder());
    UiController.Settings.SettingsBuilder settingsBuilder2 = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder2.brand(Mockito.<String>any())).thenReturn(settingsBuilder);
    UiController.Settings.SettingsBuilder settingsBuilder3 = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder3.availableLanguages(Mockito.<List<String>>any())).thenReturn(settingsBuilder2);
    UiController.Settings.SettingsBuilder enableToastsResult = settingsBuilder3.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder notificationFilterEnabledResult = enableToastsResult
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(false);

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = notificationFilterEnabledResult.pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult2 = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    UiController.Settings.SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    UiController.Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link UiController.Settings#equals(Object)}
   */
  @Test
  void testSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    UiController.Settings.SettingsBuilder settingsBuilder = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder.enableToasts(Mockito.<Boolean>any())).thenReturn(UiController.Settings.builder());
    UiController.Settings.SettingsBuilder settingsBuilder2 = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder2.brand(Mockito.<String>any())).thenReturn(settingsBuilder);
    UiController.Settings.SettingsBuilder settingsBuilder3 = mock(UiController.Settings.SettingsBuilder.class);
    when(settingsBuilder3.availableLanguages(Mockito.<List<String>>any())).thenReturn(settingsBuilder2);
    UiController.Settings.SettingsBuilder enableToastsResult = settingsBuilder3.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder notificationFilterEnabledResult = enableToastsResult
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true);

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = notificationFilterEnabledResult.pollTimer(pollTimer)
        .rememberMeEnabled(false);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult2 = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult2 = enableToastsResult2
        .externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer2)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult2 = rememberMeEnabledResult2.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);
    UiController.Settings.SettingsBuilder titleResult2 = routesResult2.theme(theme2).title("Dr");
    UiController.Settings buildResult2 = titleResult2.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link UiController.Settings#equals(Object)}
   */
  @Test
  void testSettingsEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link UiController.Settings#equals(Object)}
   */
  @Test
  void testSettingsEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings buildResult = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to Settings");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UiController.Settings#Settings(String, String, String, String, String, AdminServerUiProperties.PollTimer, AdminServerUiProperties.UiTheme, boolean, boolean, List, List, List, List, Boolean, Boolean)}
   *   <li>{@link UiController.Settings#toString()}
   *   <li>{@link UiController.Settings#getAvailableLanguages()}
   *   <li>{@link UiController.Settings#getBrand()}
   *   <li>{@link UiController.Settings#getEnableToasts()}
   *   <li>{@link UiController.Settings#getExternalViews()}
   *   <li>{@link UiController.Settings#getFavicon()}
   *   <li>{@link UiController.Settings#getFaviconDanger()}
   *   <li>{@link UiController.Settings#getHideInstanceUrl()}
   *   <li>{@link UiController.Settings#getLoginIcon()}
   *   <li>{@link UiController.Settings#getPollTimer()}
   *   <li>{@link UiController.Settings#getRoutes()}
   *   <li>{@link UiController.Settings#getTheme()}
   *   <li>{@link UiController.Settings#getTitle()}
   *   <li>{@link UiController.Settings#getViewSettings()}
   *   <li>{@link UiController.Settings#isNotificationFilterEnabled()}
   *   <li>{@link UiController.Settings#isRememberMeEnabled()}
   * </ul>
   */
  @Test
  void testSettingsGettersAndSetters() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    ArrayList<String> availableLanguages = new ArrayList<>();
    ArrayList<String> routes = new ArrayList<>();
    ArrayList<UiController.ExternalView> externalViews = new ArrayList<>();
    ArrayList<UiController.ViewSettings> viewSettings = new ArrayList<>();

    // Act
    UiController.Settings actualSettings = new UiController.Settings("Dr", "Brand", "Login Icon", "Favicon",
        "Favicon Danger", pollTimer, theme, true, true, availableLanguages, routes, externalViews, viewSettings, true,
        true);
    actualSettings.toString();
    List<String> actualAvailableLanguages = actualSettings.getAvailableLanguages();
    String actualBrand = actualSettings.getBrand();
    Boolean actualEnableToasts = actualSettings.getEnableToasts();
    List<UiController.ExternalView> actualExternalViews = actualSettings.getExternalViews();
    String actualFavicon = actualSettings.getFavicon();
    String actualFaviconDanger = actualSettings.getFaviconDanger();
    Boolean actualHideInstanceUrl = actualSettings.getHideInstanceUrl();
    String actualLoginIcon = actualSettings.getLoginIcon();
    AdminServerUiProperties.PollTimer actualPollTimer = actualSettings.getPollTimer();
    List<String> actualRoutes = actualSettings.getRoutes();
    AdminServerUiProperties.UiTheme actualTheme = actualSettings.getTheme();
    String actualTitle = actualSettings.getTitle();
    List<UiController.ViewSettings> actualViewSettings = actualSettings.getViewSettings();
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
   * Methods under test:
   * <ul>
   *   <li>{@link UiController.Settings.SettingsBuilder#build()}
   *   <li>{@link UiController.Settings.SettingsBuilder#availableLanguages(List)}
   *   <li>{@link UiController.Settings.SettingsBuilder#brand(String)}
   *   <li>{@link UiController.Settings.SettingsBuilder#enableToasts(Boolean)}
   *   <li>{@link UiController.Settings.SettingsBuilder#externalViews(List)}
   *   <li>{@link UiController.Settings.SettingsBuilder#favicon(String)}
   *   <li>{@link UiController.Settings.SettingsBuilder#faviconDanger(String)}
   *   <li>{@link UiController.Settings.SettingsBuilder#hideInstanceUrl(Boolean)}
   *   <li>{@link UiController.Settings.SettingsBuilder#loginIcon(String)}
   *   <li>
   * {@link UiController.Settings.SettingsBuilder#notificationFilterEnabled(boolean)}
   *   <li>
   * {@link UiController.Settings.SettingsBuilder#pollTimer(AdminServerUiProperties.PollTimer)}
   *   <li>{@link UiController.Settings.SettingsBuilder#rememberMeEnabled(boolean)}
   *   <li>{@link UiController.Settings.SettingsBuilder#routes(List)}
   *   <li>
   * {@link UiController.Settings.SettingsBuilder#theme(AdminServerUiProperties.UiTheme)}
   *   <li>{@link UiController.Settings.SettingsBuilder#title(String)}
   *   <li>{@link UiController.Settings.SettingsBuilder#viewSettings(List)}
   * </ul>
   */
  @Test
  void testSettings_SettingsBuilderBuild() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    ArrayList<String> availableLanguages = new ArrayList<>();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(availableLanguages)
        .brand("Brand")
        .enableToasts(true);
    ArrayList<UiController.ExternalView> externalViews = new ArrayList<>();
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(externalViews)
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    ArrayList<String> routes = new ArrayList<>();
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(routes);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    ArrayList<UiController.ViewSettings> viewSettings = new ArrayList<>();

    // Act
    UiController.Settings actualBuildResult = titleResult.viewSettings(viewSettings).build();

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
    List<UiController.ExternalView> externalViews2 = actualBuildResult.getExternalViews();
    assertTrue(externalViews2.isEmpty());
    List<String> routes2 = actualBuildResult.getRoutes();
    assertTrue(routes2.isEmpty());
    List<UiController.ViewSettings> viewSettings2 = actualBuildResult.getViewSettings();
    assertTrue(viewSettings2.isEmpty());
    assertSame(pollTimer, actualBuildResult.getPollTimer());
    assertSame(theme, actualBuildResult.getTheme());
    assertSame(availableLanguages, availableLanguages2);
    assertSame(externalViews, externalViews2);
    assertSame(routes, routes2);
    assertSame(viewSettings, viewSettings2);
  }

  /**
   * Method under test: {@link UiController#variablesCss()}
   */
  @Test
  void testVariablesCss() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals("variables.css",
        (new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings)).variablesCss());
  }

  /**
   * Method under test: {@link UiController#variablesCss()}
   */
  @Test
  void testVariablesCss2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals("variables.css",
        (new UiController("https://example.org/example", mock(UiExtensions.class), uiSettings)).variablesCss());
  }

  /**
   * Method under test: {@link UiController#login()}
   */
  @Test
  void testLogin() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals("login", (new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings)).login());
  }

  /**
   * Method under test: {@link UiController#login()}
   */
  @Test
  void testLogin2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertEquals("login",
        (new UiController("https://example.org/example", mock(UiExtensions.class), uiSettings)).login());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UiController#UiController(String, UiExtensions, UiController.Settings)}
   *   <li>{@link UiController#getUiSettings()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    UiController.Settings.SettingsBuilder builderResult = UiController.Settings.builder();
    UiController.Settings.SettingsBuilder enableToastsResult = builderResult.availableLanguages(new ArrayList<>())
        .brand("Brand")
        .enableToasts(true);
    UiController.Settings.SettingsBuilder rememberMeEnabledResult = enableToastsResult.externalViews(new ArrayList<>())
        .favicon("Favicon")
        .faviconDanger("Favicon Danger")
        .hideInstanceUrl(true)
        .loginIcon("Login Icon")
        .notificationFilterEnabled(true)
        .pollTimer(pollTimer)
        .rememberMeEnabled(true);
    UiController.Settings.SettingsBuilder routesResult = rememberMeEnabledResult.routes(new ArrayList<>());

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    UiController.Settings.SettingsBuilder titleResult = routesResult.theme(theme).title("Dr");
    UiController.Settings uiSettings = titleResult.viewSettings(new ArrayList<>()).build();

    // Act and Assert
    assertSame(uiSettings,
        (new UiController("https://example.org/example", UiExtensions.EMPTY, uiSettings)).getUiSettings());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiController.ViewSettings#equals(Object)}
   *   <li>{@link UiController.ViewSettings#hashCode()}
   * </ul>
   */
  @Test
  void testViewSettingsEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UiController.ViewSettings viewSettings = new UiController.ViewSettings("Name", true);
    UiController.ViewSettings viewSettings2 = new UiController.ViewSettings("Name", true);

    // Act and Assert
    assertEquals(viewSettings, viewSettings2);
    int expectedHashCodeResult = viewSettings.hashCode();
    assertEquals(expectedHashCodeResult, viewSettings2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiController.ViewSettings#equals(Object)}
   *   <li>{@link UiController.ViewSettings#hashCode()}
   * </ul>
   */
  @Test
  void testViewSettingsEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UiController.ViewSettings viewSettings = new UiController.ViewSettings("Name", true);

    // Act and Assert
    assertEquals(viewSettings, viewSettings);
    int expectedHashCodeResult = viewSettings.hashCode();
    assertEquals(expectedHashCodeResult, viewSettings.hashCode());
  }

  /**
   * Method under test: {@link UiController.ViewSettings#equals(Object)}
   */
  @Test
  void testViewSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UiController.ViewSettings viewSettings = new UiController.ViewSettings("Name", false);

    // Act and Assert
    assertNotEquals(viewSettings, new UiController.ViewSettings("Name", true));
  }

  /**
   * Method under test: {@link UiController.ViewSettings#equals(Object)}
   */
  @Test
  void testViewSettingsEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UiController.ViewSettings viewSettings = new UiController.ViewSettings("Name", true);

    // Act and Assert
    assertNotEquals(viewSettings,
        new UiController.ViewSettings("de.codecentric.boot.admin.server.ui.web.UiController$ViewSettings", true));
  }

  /**
   * Method under test: {@link UiController.ViewSettings#equals(Object)}
   */
  @Test
  void testViewSettingsEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UiController.ViewSettings("Name", true), null);
  }

  /**
   * Method under test: {@link UiController.ViewSettings#equals(Object)}
   */
  @Test
  void testViewSettingsEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UiController.ViewSettings("Name", true), "Different type to ViewSettings");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UiController.ViewSettings#setEnabled(boolean)}
   *   <li>{@link UiController.ViewSettings#toString()}
   *   <li>{@link UiController.ViewSettings#getName()}
   *   <li>{@link UiController.ViewSettings#isEnabled()}
   * </ul>
   */
  @Test
  void testViewSettingsGettersAndSetters() {
    // Arrange
    UiController.ViewSettings viewSettings = new UiController.ViewSettings("Name", true);

    // Act
    viewSettings.setEnabled(true);
    String actualToStringResult = viewSettings.toString();
    String actualName = viewSettings.getName();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
    assertEquals("UiController.ViewSettings(name=Name, enabled=true)", actualToStringResult);
    assertTrue(viewSettings.isEnabled());
  }

  /**
   * Method under test:
   * {@link UiController.ViewSettings#ViewSettings(String, boolean)}
   */
  @Test
  void testViewSettingsNewViewSettings() {
    // Arrange and Act
    UiController.ViewSettings actualViewSettings = new UiController.ViewSettings("Name", true);

    // Assert
    assertEquals("Name", actualViewSettings.getName());
    assertTrue(actualViewSettings.isEnabled());
  }
}
