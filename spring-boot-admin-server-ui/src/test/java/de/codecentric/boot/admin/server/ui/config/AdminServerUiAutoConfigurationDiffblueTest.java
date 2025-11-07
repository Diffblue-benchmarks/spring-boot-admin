package de.codecentric.boot.admin.server.ui.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import de.codecentric.boot.admin.server.notify.filter.web.NotificationFilterController;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiAutoConfiguration.ReactiveUiConfiguration;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiAutoConfiguration.ReactiveUiConfiguration.AdminUiWebfluxConfig;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiAutoConfiguration.ServletUiConfiguration;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiAutoConfiguration.ServletUiConfiguration.AdminUiWebMvcConfig;
import de.codecentric.boot.admin.server.ui.extensions.UiExtensions;
import de.codecentric.boot.admin.server.ui.web.HomepageForwardingFilterConfig;
import de.codecentric.boot.admin.server.ui.web.UiController;
import de.codecentric.boot.admin.server.ui.web.UiController.Settings;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@ContextConfiguration(classes = {AdminServerUiAutoConfiguration.class, AdminServerUiProperties.class,
    AdminServerProperties.class, AdminUiWebfluxConfig.class, WebFluxProperties.class, AdminUiWebMvcConfig.class})
@ExtendWith(SpringExtension.class)
class AdminServerUiAutoConfigurationDiffblueTest {
  @Autowired
  private AdminServerProperties adminServerProperties;

  @Autowired
  private AdminServerUiProperties adminServerUiProperties;

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private AdminUiWebfluxConfig adminUiWebfluxConfig;

  @Autowired
  private WebFluxProperties webFluxProperties;

  @Autowired
  private AdminUiWebMvcConfig adminUiWebMvcConfig;

  /**
   * Test {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}.
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}
   */
  @Test
  @DisplayName("Test homeUiController(UiExtensions)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiController AdminServerUiAutoConfiguration.homeUiController(UiExtensions)"})
  void testHomeUiController() throws IOException, BeansException {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getBeansOfType(Mockito.<Class<Object>>any())).thenReturn(new HashMap<>());
    when(applicationContext.getBeansOfType(Mockito.<Class<NotificationFilterController>>any()))
        .thenReturn(new HashMap<>());
    when(applicationContext.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[]{new ClassPathResource("classpath:")});
    AdminServerUiProperties adminUi = new AdminServerUiProperties();

    // Act
    UiController actualHomeUiControllerResult = (new AdminServerUiAutoConfiguration(adminUi,
        new AdminServerProperties(), applicationContext)).homeUiController(UiExtensions.EMPTY);

    // Assert
    verify(applicationContext, atLeast(1)).getBeansOfType(isA(Class.class));
    verify(applicationContext)
        .getResources(eq("classpath*:/META-INF/spring-boot-admin-server-ui/extensions/**/routes.txt"));
    Settings uiSettings = actualHomeUiControllerResult.getUiSettings();
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        uiSettings.getBrand());
    assertEquals("Spring Boot Admin", uiSettings.getTitle());
    assertEquals("assets/img/favicon-danger.png", uiSettings.getFaviconDanger());
    assertEquals("assets/img/favicon.png", uiSettings.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", uiSettings.getLoginIcon());
    assertEquals("index", actualHomeUiControllerResult.index());
    assertEquals("login", actualHomeUiControllerResult.login());
    assertEquals(6, uiSettings.getRoutes().size());
    assertFalse(uiSettings.getEnableToasts());
    assertFalse(uiSettings.getHideInstanceUrl());
    assertFalse(uiSettings.isNotificationFilterEnabled());
    assertTrue(uiSettings.isRememberMeEnabled());
    assertTrue(actualHomeUiControllerResult.getCssExtensions().isEmpty());
    assertTrue(actualHomeUiControllerResult.getJsExtensions().isEmpty());
    assertTrue(uiSettings.getAvailableLanguages().isEmpty());
    assertTrue(uiSettings.getExternalViews().isEmpty());
    assertTrue(uiSettings.getViewSettings().isEmpty());
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   *   <li>Then return UiSettings Routes size is six.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}
   */
  @Test
  @DisplayName("Test homeUiController(UiExtensions); given 'java.lang.Object'; then return UiSettings Routes size is six")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiController AdminServerUiAutoConfiguration.homeUiController(UiExtensions)"})
  void testHomeUiController_givenJavaLangObject_thenReturnUiSettingsRoutesSizeIsSix() throws IOException {
    // Arrange
    AdminServerUiProperties adminUi = new AdminServerUiProperties();
    AdminServerProperties serverProperties = new AdminServerProperties();
    Class<Object> forNameResult = Object.class;

    // Act
    UiController actualHomeUiControllerResult = (new AdminServerUiAutoConfiguration(adminUi, serverProperties,
        new AnnotationConfigReactiveWebApplicationContext(forNameResult))).homeUiController(UiExtensions.EMPTY);

    // Assert
    Settings uiSettings = actualHomeUiControllerResult.getUiSettings();
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        uiSettings.getBrand());
    assertEquals("Spring Boot Admin", uiSettings.getTitle());
    assertEquals("assets/img/favicon-danger.png", uiSettings.getFaviconDanger());
    assertEquals("assets/img/favicon.png", uiSettings.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", uiSettings.getLoginIcon());
    assertEquals("index", actualHomeUiControllerResult.index());
    assertEquals("login", actualHomeUiControllerResult.login());
    assertEquals(6, uiSettings.getRoutes().size());
    assertFalse(uiSettings.getEnableToasts());
    assertFalse(uiSettings.getHideInstanceUrl());
    assertFalse(uiSettings.isNotificationFilterEnabled());
    assertTrue(uiSettings.isRememberMeEnabled());
    assertTrue(actualHomeUiControllerResult.getCssExtensions().isEmpty());
    assertTrue(actualHomeUiControllerResult.getJsExtensions().isEmpty());
    assertTrue(uiSettings.getAvailableLanguages().isEmpty());
    assertTrue(uiSettings.getExternalViews().isEmpty());
    assertTrue(uiSettings.getViewSettings().isEmpty());
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}.
   * <ul>
   *   <li>Then return UiSettings NotificationFilterEnabled.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}
   */
  @Test
  @DisplayName("Test homeUiController(UiExtensions); then return UiSettings NotificationFilterEnabled")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiController AdminServerUiAutoConfiguration.homeUiController(UiExtensions)"})
  void testHomeUiController_thenReturnUiSettingsNotificationFilterEnabled() throws IOException, BeansException {
    // Arrange
    HashMap<String, NotificationFilterController> stringNotificationFilterControllerMap = new HashMap<>();
    Notifier delegate = mock(Notifier.class);
    stringNotificationFilterControllerMap.put("classpath:", new NotificationFilterController(
        new FilteringNotifier(delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()))));
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getBeansOfType(Mockito.<Class<NotificationFilterController>>any()))
        .thenReturn(stringNotificationFilterControllerMap);
    when(applicationContext.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[]{new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))});
    AdminServerUiProperties adminUi = new AdminServerUiProperties();

    // Act
    UiController actualHomeUiControllerResult = (new AdminServerUiAutoConfiguration(adminUi,
        new AdminServerProperties(), applicationContext)).homeUiController(UiExtensions.EMPTY);

    // Assert
    verify(applicationContext).getBeansOfType(isA(Class.class));
    verify(applicationContext)
        .getResources(eq("classpath*:/META-INF/spring-boot-admin-server-ui/extensions/**/routes.txt"));
    Settings uiSettings = actualHomeUiControllerResult.getUiSettings();
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        uiSettings.getBrand());
    assertEquals("Spring Boot Admin", uiSettings.getTitle());
    assertEquals("assets/img/favicon-danger.png", uiSettings.getFaviconDanger());
    assertEquals("assets/img/favicon.png", uiSettings.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", uiSettings.getLoginIcon());
    assertEquals("index", actualHomeUiControllerResult.index());
    assertEquals("login", actualHomeUiControllerResult.login());
    assertEquals(7, uiSettings.getRoutes().size());
    assertFalse(uiSettings.getEnableToasts());
    assertFalse(uiSettings.getHideInstanceUrl());
    assertTrue(uiSettings.isNotificationFilterEnabled());
    assertTrue(uiSettings.isRememberMeEnabled());
    assertTrue(actualHomeUiControllerResult.getCssExtensions().isEmpty());
    assertTrue(actualHomeUiControllerResult.getJsExtensions().isEmpty());
    assertTrue(uiSettings.getAvailableLanguages().isEmpty());
    assertTrue(uiSettings.getExternalViews().isEmpty());
    assertTrue(uiSettings.getViewSettings().isEmpty());
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}.
   * <ul>
   *   <li>Then return UiSettings Routes size is seven.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}
   */
  @Test
  @DisplayName("Test homeUiController(UiExtensions); then return UiSettings Routes size is seven")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiController AdminServerUiAutoConfiguration.homeUiController(UiExtensions)"})
  void testHomeUiController_thenReturnUiSettingsRoutesSizeIsSeven() throws IOException, BeansException {
    // Arrange
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getBeansOfType(Mockito.<Class<NotificationFilterController>>any()))
        .thenReturn(new HashMap<>());
    when(applicationContext.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[]{new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))});
    AdminServerUiProperties adminUi = new AdminServerUiProperties();

    // Act
    UiController actualHomeUiControllerResult = (new AdminServerUiAutoConfiguration(adminUi,
        new AdminServerProperties(), applicationContext)).homeUiController(UiExtensions.EMPTY);

    // Assert
    verify(applicationContext).getBeansOfType(isA(Class.class));
    verify(applicationContext)
        .getResources(eq("classpath*:/META-INF/spring-boot-admin-server-ui/extensions/**/routes.txt"));
    Settings uiSettings = actualHomeUiControllerResult.getUiSettings();
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        uiSettings.getBrand());
    assertEquals("Spring Boot Admin", uiSettings.getTitle());
    assertEquals("assets/img/favicon-danger.png", uiSettings.getFaviconDanger());
    assertEquals("assets/img/favicon.png", uiSettings.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", uiSettings.getLoginIcon());
    assertEquals("index", actualHomeUiControllerResult.index());
    assertEquals("login", actualHomeUiControllerResult.login());
    assertEquals(7, uiSettings.getRoutes().size());
    assertFalse(uiSettings.getEnableToasts());
    assertFalse(uiSettings.getHideInstanceUrl());
    assertFalse(uiSettings.isNotificationFilterEnabled());
    assertTrue(uiSettings.isRememberMeEnabled());
    assertTrue(actualHomeUiControllerResult.getCssExtensions().isEmpty());
    assertTrue(actualHomeUiControllerResult.getJsExtensions().isEmpty());
    assertTrue(uiSettings.getAvailableLanguages().isEmpty());
    assertTrue(uiSettings.getExternalViews().isEmpty());
    assertTrue(uiSettings.getViewSettings().isEmpty());
  }

  /**
   * Test ReactiveUiConfiguration_AdminUiWebfluxConfig {@link AdminUiWebfluxConfig#homepageForwardingFilterConfig()}.
   * <p>
   * Method under test: {@link AdminUiWebfluxConfig#homepageForwardingFilterConfig()}
   */
  @Test
  @DisplayName("Test ReactiveUiConfiguration_AdminUiWebfluxConfig homepageForwardingFilterConfig()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"HomepageForwardingFilterConfig AdminUiWebfluxConfig.homepageForwardingFilterConfig()"})
  void testReactiveUiConfiguration_AdminUiWebfluxConfigHomepageForwardingFilterConfig() throws IOException {
    // Arrange and Act
    HomepageForwardingFilterConfig actualHomepageForwardingFilterConfigResult = adminUiWebfluxConfig
        .homepageForwardingFilterConfig();

    // Assert
    List<String> routesIncludes = actualHomepageForwardingFilterConfigResult.getRoutesIncludes();
    assertEquals(7, routesIncludes.size());
    assertEquals("", routesIncludes.get(6));
    assertEquals("/", actualHomepageForwardingFilterConfigResult.getHomepage());
    assertEquals("/about/**", routesIncludes.get(0));
    assertEquals("/applications/**", routesIncludes.get(1));
    List<String> routesExcludes = actualHomepageForwardingFilterConfigResult.getRoutesExcludes();
    assertEquals(2, routesExcludes.size());
    assertEquals("/extensions/**", routesExcludes.get(0));
    assertEquals("/external/**", routesIncludes.get(5));
    assertEquals("/instances/**", routesIncludes.get(2));
    assertEquals("/instances/*/actuator/**", routesExcludes.get(1));
    assertEquals("/wallboard/**", routesIncludes.get(4));
  }

  /**
   * Test ServletUiConfiguration_AdminUiWebMvcConfig {@link ServletUiConfiguration.AdminUiWebMvcConfig#configurePathMatch(PathMatchConfigurer)}.
   * <p>
   * Method under test: {@link ServletUiConfiguration.AdminUiWebMvcConfig#configurePathMatch(PathMatchConfigurer)}
   */
  @Test
  @DisplayName("Test ServletUiConfiguration_AdminUiWebMvcConfig configurePathMatch(PathMatchConfigurer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServletUiConfiguration.AdminUiWebMvcConfig.configurePathMatch(PathMatchConfigurer)"})
  void testServletUiConfiguration_AdminUiWebMvcConfigConfigurePathMatch() {
    // Arrange
    PathMatchConfigurer configurer = new PathMatchConfigurer();

    // Act
    adminUiWebMvcConfig.configurePathMatch(configurer);

    // Assert
    assertTrue(configurer.isUseTrailingSlashMatch());
  }

  /**
   * Test ServletUiConfiguration_AdminUiWebMvcConfig {@link ServletUiConfiguration.AdminUiWebMvcConfig#homepageForwardingFilterConfig()}.
   * <p>
   * Method under test: {@link ServletUiConfiguration.AdminUiWebMvcConfig#homepageForwardingFilterConfig()}
   */
  @Test
  @DisplayName("Test ServletUiConfiguration_AdminUiWebMvcConfig homepageForwardingFilterConfig()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "HomepageForwardingFilterConfig ServletUiConfiguration.AdminUiWebMvcConfig.homepageForwardingFilterConfig()"})
  void testServletUiConfiguration_AdminUiWebMvcConfigHomepageForwardingFilterConfig() throws IOException {
    // Arrange and Act
    HomepageForwardingFilterConfig actualHomepageForwardingFilterConfigResult = adminUiWebMvcConfig
        .homepageForwardingFilterConfig();

    // Assert
    assertEquals("/", actualHomepageForwardingFilterConfigResult.getHomepage());
    List<String> routesIncludes = actualHomepageForwardingFilterConfigResult.getRoutesIncludes();
    assertEquals(7, routesIncludes.size());
    assertEquals("/", routesIncludes.get(6));
    assertEquals("/about/**", routesIncludes.get(0));
    assertEquals("/applications/**", routesIncludes.get(1));
    List<String> routesExcludes = actualHomepageForwardingFilterConfigResult.getRoutesExcludes();
    assertEquals(2, routesExcludes.size());
    assertEquals("/extensions/**", routesExcludes.get(0));
    assertEquals("/external/**", routesIncludes.get(5));
    assertEquals("/instances/**", routesIncludes.get(2));
    assertEquals("/instances/*/actuator/**", routesExcludes.get(1));
    assertEquals("/wallboard/**", routesIncludes.get(4));
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#uiExtensions()}.
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  @DisplayName("Test uiExtensions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiExtensions AdminServerUiAutoConfiguration.uiExtensions()"})
  void testUiExtensions() throws IOException {
    // Arrange
    AdminServerUiProperties adminUi = new AdminServerUiProperties();
    AdminServerProperties serverProperties = new AdminServerProperties();

    // Act
    UiExtensions actualUiExtensionsResult = (new AdminServerUiAutoConfiguration(adminUi, serverProperties,
        new AnnotationConfigReactiveWebApplicationContext())).uiExtensions();

    // Assert
    assertEquals(actualUiExtensionsResult.EMPTY, actualUiExtensionsResult);
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#uiExtensions()}.
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  @DisplayName("Test uiExtensions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiExtensions AdminServerUiAutoConfiguration.uiExtensions()"})
  void testUiExtensions2() throws IOException {
    // Arrange
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.getURI()).thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getResources(Mockito.<String>any())).thenReturn(new Resource[]{byteArrayResource});
    AdminServerUiProperties adminUi = new AdminServerUiProperties();

    // Act
    UiExtensions actualUiExtensionsResult = (new AdminServerUiAutoConfiguration(adminUi, new AdminServerProperties(),
        applicationContext)).uiExtensions();

    // Assert
    verify(byteArrayResource, atLeast(1)).getURI();
    verify(applicationContext, atLeast(1)).getResources(Mockito.<String>any());
    assertEquals(actualUiExtensionsResult.EMPTY, actualUiExtensionsResult);
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#uiExtensions()}.
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  @DisplayName("Test uiExtensions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiExtensions AdminServerUiAutoConfiguration.uiExtensions()"})
  void testUiExtensions3() throws IOException {
    // Arrange
    AdminServerUiProperties adminUi = new AdminServerUiProperties();
    adminUi.setExtensionResourceLocations(new String[]{"U"});
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.getURI()).thenReturn(
        Paths.get(System.getProperty("java.io.tmpdir"), "/META-INF/spring-boot-admin-server-ui/extensions/U").toUri());
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getResources(Mockito.<String>any())).thenReturn(new Resource[]{byteArrayResource});

    // Act
    UiExtensions actualUiExtensionsResult = (new AdminServerUiAutoConfiguration(adminUi, new AdminServerProperties(),
        applicationContext)).uiExtensions();

    // Assert
    verify(byteArrayResource, atLeast(1)).getURI();
    verify(applicationContext, atLeast(1)).getResources(Mockito.<String>any());
    assertEquals(actualUiExtensionsResult.EMPTY, actualUiExtensionsResult);
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#uiExtensions()}.
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  @DisplayName("Test uiExtensions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiExtensions AdminServerUiAutoConfiguration.uiExtensions()"})
  void testUiExtensions4() throws IOException {
    // Arrange
    AdminServerUiProperties adminUi = new AdminServerUiProperties();
    adminUi.setExtensionResourceLocations(new String[]{"^[^:]+:"});
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.getURI()).thenReturn(
        Paths.get(System.getProperty("java.io.tmpdir"), "/META-INF/spring-boot-admin-server-ui/extensions/U").toUri());
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getResources(Mockito.<String>any())).thenReturn(new Resource[]{byteArrayResource});

    // Act
    UiExtensions actualUiExtensionsResult = (new AdminServerUiAutoConfiguration(adminUi, new AdminServerProperties(),
        applicationContext)).uiExtensions();

    // Assert
    verify(byteArrayResource, atLeast(1)).getURI();
    verify(applicationContext, atLeast(1)).getResources(Mockito.<String>any());
    assertEquals(actualUiExtensionsResult.EMPTY, actualUiExtensionsResult);
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#uiExtensions()}.
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  @DisplayName("Test uiExtensions()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiExtensions AdminServerUiAutoConfiguration.uiExtensions()"})
  void testUiExtensions5() throws IOException {
    // Arrange
    AdminServerUiProperties adminUi = new AdminServerUiProperties();
    adminUi.setExtensionResourceLocations(new String[]{"Loaded Spring Boot Admin UI Extension: {}"});
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.getURI()).thenReturn(
        Paths.get(System.getProperty("java.io.tmpdir"), "/META-INF/spring-boot-admin-server-ui/extensions/U").toUri());
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getResources(Mockito.<String>any())).thenReturn(new Resource[]{byteArrayResource});

    // Act
    UiExtensions actualUiExtensionsResult = (new AdminServerUiAutoConfiguration(adminUi, new AdminServerProperties(),
        applicationContext)).uiExtensions();

    // Assert
    verify(byteArrayResource, atLeast(1)).getURI();
    verify(applicationContext, atLeast(1)).getResources(Mockito.<String>any());
    assertEquals(actualUiExtensionsResult.EMPTY, actualUiExtensionsResult);
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#uiExtensions()}.
   * <ul>
   *   <li>Given {@link ByteArrayResource} {@link AbstractResource#isReadable()} return {@code false}.</li>
   *   <li>Then calls {@link AbstractResource#isReadable()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  @DisplayName("Test uiExtensions(); given ByteArrayResource isReadable() return 'false'; then calls isReadable()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"UiExtensions AdminServerUiAutoConfiguration.uiExtensions()"})
  void testUiExtensions_givenByteArrayResourceIsReadableReturnFalse_thenCallsIsReadable() throws IOException {
    // Arrange
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.isReadable()).thenReturn(false);
    when(byteArrayResource.getURI()).thenReturn(
        Paths.get(System.getProperty("java.io.tmpdir"), "/META-INF/spring-boot-admin-server-ui/extensions/U").toUri());
    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getResources(Mockito.<String>any())).thenReturn(new Resource[]{byteArrayResource});
    AdminServerUiProperties adminUi = new AdminServerUiProperties();

    // Act
    UiExtensions actualUiExtensionsResult = (new AdminServerUiAutoConfiguration(adminUi, new AdminServerProperties(),
        applicationContext)).uiExtensions();

    // Assert
    verify(byteArrayResource, atLeast(1)).getURI();
    verify(byteArrayResource, atLeast(1)).isReadable();
    verify(applicationContext, atLeast(1)).getResources(Mockito.<String>any());
    assertEquals(actualUiExtensionsResult.EMPTY, actualUiExtensionsResult);
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#adminTemplateResolver()}.
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#adminTemplateResolver()}
   */
  @Test
  @DisplayName("Test adminTemplateResolver()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"SpringResourceTemplateResolver AdminServerUiAutoConfiguration.adminTemplateResolver()"})
  void testAdminTemplateResolver() {
    // Arrange
    AdminServerUiProperties adminUi = new AdminServerUiProperties();
    AdminServerProperties serverProperties = new AdminServerProperties();

    // Act
    SpringResourceTemplateResolver actualAdminTemplateResolverResult = (new AdminServerUiAutoConfiguration(adminUi,
        serverProperties, new AnnotationConfigReactiveWebApplicationContext())).adminTemplateResolver();

    // Assert
    assertEquals(".html", actualAdminTemplateResolverResult.getSuffix());
    assertEquals("UTF-8", actualAdminTemplateResolverResult.getCharacterEncoding());
    assertEquals("classpath:/META-INF/spring-boot-admin-server-ui/", actualAdminTemplateResolverResult.getPrefix());
    assertEquals("org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver",
        actualAdminTemplateResolverResult.getName());
    assertNull(actualAdminTemplateResolverResult.getCacheTTLMs());
    assertEquals(10, actualAdminTemplateResolverResult.getOrder().intValue());
    assertEquals(TemplateMode.HTML, actualAdminTemplateResolverResult.getTemplateMode());
    assertFalse(actualAdminTemplateResolverResult.getForceSuffix());
    assertFalse(actualAdminTemplateResolverResult.getForceTemplateMode());
    assertFalse(actualAdminTemplateResolverResult.getUseDecoupledLogic());
    assertTrue(actualAdminTemplateResolverResult.getTemplateAliases().isEmpty());
    Set<String> cSSTemplateModePatterns = actualAdminTemplateResolverResult.getCSSTemplateModePatterns();
    assertTrue(cSSTemplateModePatterns.isEmpty());
    assertTrue(actualAdminTemplateResolverResult.isCacheable());
    assertTrue(actualAdminTemplateResolverResult.getCheckExistence());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getCacheablePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getHtmlTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getJavaScriptTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getNonCacheablePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getRawTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getTextTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getXmlTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getResolvablePatterns());
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}.
   * <ul>
   *   <li>Then return {@code /https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}
   */
  @Test
  @DisplayName("Test normalizeHomepageUrl(String); then return '/https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AdminServerUiAutoConfiguration.normalizeHomepageUrl(String)"})
  void testNormalizeHomepageUrl_thenReturnHttpsExampleOrgExample() {
    // Arrange, Act and Assert
    assertEquals("/https://example.org/example",
        AdminServerUiAutoConfiguration.normalizeHomepageUrl("https://example.org/example"));
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}
   */
  @Test
  @DisplayName("Test normalizeHomepageUrl(String); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AdminServerUiAutoConfiguration.normalizeHomepageUrl(String)"})
  void testNormalizeHomepageUrl_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(AdminServerUiAutoConfiguration.normalizeHomepageUrl(null));
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}.
   * <ul>
   *   <li>When {@code ///}.</li>
   *   <li>Then return {@code /}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}
   */
  @Test
  @DisplayName("Test normalizeHomepageUrl(String); when '///'; then return '/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AdminServerUiAutoConfiguration.normalizeHomepageUrl(String)"})
  void testNormalizeHomepageUrl_whenSlashSlashSlash_thenReturnSlash() {
    // Arrange, Act and Assert
    assertEquals("/", AdminServerUiAutoConfiguration.normalizeHomepageUrl("///"));
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}.
   * <ul>
   *   <li>When {@code //}.</li>
   *   <li>Then return {@code /}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}
   */
  @Test
  @DisplayName("Test normalizeHomepageUrl(String); when '//'; then return '/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AdminServerUiAutoConfiguration.normalizeHomepageUrl(String)"})
  void testNormalizeHomepageUrl_whenSlashSlash_thenReturnSlash() {
    // Arrange, Act and Assert
    assertEquals("/", AdminServerUiAutoConfiguration.normalizeHomepageUrl("//"));
  }

  /**
   * Test {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}.
   * <ul>
   *   <li>When {@code /}.</li>
   *   <li>Then return {@code /}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}
   */
  @Test
  @DisplayName("Test normalizeHomepageUrl(String); when '/'; then return '/'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AdminServerUiAutoConfiguration.normalizeHomepageUrl(String)"})
  void testNormalizeHomepageUrl_whenSlash_thenReturnSlash() {
    // Arrange, Act and Assert
    assertEquals("/", AdminServerUiAutoConfiguration.normalizeHomepageUrl("/"));
  }
}
