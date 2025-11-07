package de.codecentric.boot.admin.server.ui.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import de.codecentric.boot.admin.server.notify.filter.web.NotificationFilterController;
import de.codecentric.boot.admin.server.ui.extensions.UiExtensions;
import de.codecentric.boot.admin.server.ui.web.HomepageForwardingFilterConfig;
import de.codecentric.boot.admin.server.ui.web.UiController;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.util.PatternSpec;

@ContextConfiguration(classes = {AdminServerUiAutoConfiguration.class, AdminServerUiProperties.class,
    AdminServerProperties.class, AdminServerUiAutoConfiguration.ReactiveUiConfiguration.AdminUiWebfluxConfig.class,
    WebFluxProperties.class, AdminServerUiAutoConfiguration.ServletUiConfiguration.AdminUiWebMvcConfig.class})
@ExtendWith(SpringExtension.class)
class AdminServerUiAutoConfigurationDiffblueTest {
  @Autowired
  private AdminServerUiAutoConfiguration.ServletUiConfiguration.AdminUiWebMvcConfig adminUiWebMvcConfig;

  @Autowired
  private AdminServerUiAutoConfiguration.ReactiveUiConfiguration.AdminUiWebfluxConfig adminUiWebfluxConfig;

  @Autowired
  private WebFluxProperties webFluxProperties;

  @Autowired
  private AdminServerProperties adminServerProperties;

  @Autowired
  private AdminServerUiProperties adminServerUiProperties;

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}
   */
  @Test
  void testHomeUiController() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties adminUi = new AdminServerUiProperties();
    AdminServerProperties serverProperties = new AdminServerProperties();
    Class<Object> forNameResult = Object.class;

    // Act
    UiController actualHomeUiControllerResult = (new AdminServerUiAutoConfiguration(adminUi, serverProperties,
        new AnnotationConfigReactiveWebApplicationContext(forNameResult))).homeUiController(UiExtensions.EMPTY);

    // Assert
    UiController.Settings uiSettings = actualHomeUiControllerResult.getUiSettings();
    AdminServerUiProperties.UiTheme theme = uiSettings.getTheme();
    AdminServerUiProperties.Palette palette = theme.getPalette();
    assertEquals("#0A2F2B", palette.getShade900());
    assertEquals("#14615A", palette.getShade800());
    assertEquals("#14615A", theme.getColor());
    assertEquals("#1E9084", palette.getShade700());
    assertEquals("#27BEAF", palette.getShade600());
    assertEquals("#47D9CB", palette.getShade500());
    assertEquals("#6BE0D5", palette.getShade400());
    assertEquals("#91E8E0", palette.getShade300());
    assertEquals("#B7F0EA", palette.getShade200());
    assertEquals("#D9F7F4", palette.getShade100());
    assertEquals("#EEFCFA", palette.getShade50());
    List<String> routes = uiSettings.getRoutes();
    assertEquals(6, routes.size());
    assertEquals("/about/**", routes.get(0));
    assertEquals("/applications/**", routes.get(1));
    assertEquals("/external/**", routes.get(5));
    assertEquals("/wallboard/**", routes.get(4));
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        uiSettings.getBrand());
    assertEquals("Spring Boot Admin", uiSettings.getTitle());
    assertEquals("assets/img/favicon-danger.png", uiSettings.getFaviconDanger());
    assertEquals("assets/img/favicon.png", uiSettings.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", uiSettings.getLoginIcon());
    assertEquals("index", actualHomeUiControllerResult.index());
    assertEquals("login", actualHomeUiControllerResult.login());
    AdminServerUiProperties.PollTimer pollTimer = uiSettings.getPollTimer();
    assertEquals(1000, pollTimer.getLogfile());
    assertEquals(2500, pollTimer.getCache());
    assertEquals(2500, pollTimer.getDatasource());
    assertEquals(2500, pollTimer.getGc());
    assertEquals(2500, pollTimer.getMemory());
    assertEquals(2500, pollTimer.getProcess());
    assertEquals(2500, pollTimer.getThreads());
    assertFalse(uiSettings.getEnableToasts());
    assertFalse(uiSettings.getHideInstanceUrl());
    assertFalse(uiSettings.isNotificationFilterEnabled());
    assertTrue(theme.getBackgroundEnabled());
    assertTrue(uiSettings.isRememberMeEnabled());
    assertTrue(actualHomeUiControllerResult.getCssExtensions().isEmpty());
    assertTrue(actualHomeUiControllerResult.getJsExtensions().isEmpty());
    assertTrue(uiSettings.getAvailableLanguages().isEmpty());
    assertTrue(uiSettings.getExternalViews().isEmpty());
    assertTrue(uiSettings.getViewSettings().isEmpty());
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}
   */
  @Test
  void testHomeUiController2() throws IOException, BeansException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    UiController.Settings uiSettings = actualHomeUiControllerResult.getUiSettings();
    AdminServerUiProperties.UiTheme theme = uiSettings.getTheme();
    AdminServerUiProperties.Palette palette = theme.getPalette();
    assertEquals("#0A2F2B", palette.getShade900());
    assertEquals("#14615A", palette.getShade800());
    assertEquals("#14615A", theme.getColor());
    assertEquals("#1E9084", palette.getShade700());
    assertEquals("#27BEAF", palette.getShade600());
    assertEquals("#47D9CB", palette.getShade500());
    assertEquals("#6BE0D5", palette.getShade400());
    assertEquals("#91E8E0", palette.getShade300());
    assertEquals("#B7F0EA", palette.getShade200());
    assertEquals("#D9F7F4", palette.getShade100());
    assertEquals("#EEFCFA", palette.getShade50());
    List<String> routes = uiSettings.getRoutes();
    assertEquals(7, routes.size());
    assertEquals("/about/**", routes.get(0));
    assertEquals("/applications/**", routes.get(1));
    assertEquals("/external/**", routes.get(5));
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        uiSettings.getBrand());
    assertEquals("AXAXAXAX", routes.get(6));
    assertEquals("Spring Boot Admin", uiSettings.getTitle());
    assertEquals("assets/img/favicon-danger.png", uiSettings.getFaviconDanger());
    assertEquals("assets/img/favicon.png", uiSettings.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", uiSettings.getLoginIcon());
    assertEquals("index", actualHomeUiControllerResult.index());
    assertEquals("login", actualHomeUiControllerResult.login());
    AdminServerUiProperties.PollTimer pollTimer = uiSettings.getPollTimer();
    assertEquals(1000, pollTimer.getLogfile());
    assertEquals(2500, pollTimer.getCache());
    assertEquals(2500, pollTimer.getDatasource());
    assertEquals(2500, pollTimer.getGc());
    assertEquals(2500, pollTimer.getMemory());
    assertEquals(2500, pollTimer.getProcess());
    assertEquals(2500, pollTimer.getThreads());
    assertFalse(uiSettings.getEnableToasts());
    assertFalse(uiSettings.getHideInstanceUrl());
    assertFalse(uiSettings.isNotificationFilterEnabled());
    assertTrue(theme.getBackgroundEnabled());
    assertTrue(uiSettings.isRememberMeEnabled());
    assertTrue(actualHomeUiControllerResult.getCssExtensions().isEmpty());
    assertTrue(actualHomeUiControllerResult.getJsExtensions().isEmpty());
    assertTrue(uiSettings.getAvailableLanguages().isEmpty());
    assertTrue(uiSettings.getExternalViews().isEmpty());
    assertTrue(uiSettings.getViewSettings().isEmpty());
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}
   */
  @Test
  void testHomeUiController3() throws IOException, BeansException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    UiController.Settings uiSettings = actualHomeUiControllerResult.getUiSettings();
    AdminServerUiProperties.UiTheme theme = uiSettings.getTheme();
    AdminServerUiProperties.Palette palette = theme.getPalette();
    assertEquals("#0A2F2B", palette.getShade900());
    assertEquals("#14615A", palette.getShade800());
    assertEquals("#14615A", theme.getColor());
    assertEquals("#1E9084", palette.getShade700());
    assertEquals("#27BEAF", palette.getShade600());
    assertEquals("#47D9CB", palette.getShade500());
    assertEquals("#6BE0D5", palette.getShade400());
    assertEquals("#91E8E0", palette.getShade300());
    assertEquals("#B7F0EA", palette.getShade200());
    assertEquals("#D9F7F4", palette.getShade100());
    assertEquals("#EEFCFA", palette.getShade50());
    List<String> routes = uiSettings.getRoutes();
    assertEquals(7, routes.size());
    assertEquals("/about/**", routes.get(0));
    assertEquals("/applications/**", routes.get(1));
    assertEquals("/external/**", routes.get(5));
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        uiSettings.getBrand());
    assertEquals("AXAXAXAX", routes.get(6));
    assertEquals("Spring Boot Admin", uiSettings.getTitle());
    assertEquals("assets/img/favicon-danger.png", uiSettings.getFaviconDanger());
    assertEquals("assets/img/favicon.png", uiSettings.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", uiSettings.getLoginIcon());
    assertEquals("index", actualHomeUiControllerResult.index());
    assertEquals("login", actualHomeUiControllerResult.login());
    AdminServerUiProperties.PollTimer pollTimer = uiSettings.getPollTimer();
    assertEquals(1000, pollTimer.getLogfile());
    assertEquals(2500, pollTimer.getCache());
    assertEquals(2500, pollTimer.getDatasource());
    assertEquals(2500, pollTimer.getGc());
    assertEquals(2500, pollTimer.getMemory());
    assertEquals(2500, pollTimer.getProcess());
    assertEquals(2500, pollTimer.getThreads());
    assertFalse(uiSettings.getEnableToasts());
    assertFalse(uiSettings.getHideInstanceUrl());
    assertTrue(theme.getBackgroundEnabled());
    assertTrue(uiSettings.isNotificationFilterEnabled());
    assertTrue(uiSettings.isRememberMeEnabled());
    assertTrue(actualHomeUiControllerResult.getCssExtensions().isEmpty());
    assertTrue(actualHomeUiControllerResult.getJsExtensions().isEmpty());
    assertTrue(uiSettings.getAvailableLanguages().isEmpty());
    assertTrue(uiSettings.getExternalViews().isEmpty());
    assertTrue(uiSettings.getViewSettings().isEmpty());
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration#homeUiController(UiExtensions)}
   */
  @Test
  void testHomeUiController4() throws IOException, BeansException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    UiController.Settings uiSettings = actualHomeUiControllerResult.getUiSettings();
    AdminServerUiProperties.UiTheme theme = uiSettings.getTheme();
    AdminServerUiProperties.Palette palette = theme.getPalette();
    assertEquals("#0A2F2B", palette.getShade900());
    assertEquals("#14615A", palette.getShade800());
    assertEquals("#14615A", theme.getColor());
    assertEquals("#1E9084", palette.getShade700());
    assertEquals("#27BEAF", palette.getShade600());
    assertEquals("#47D9CB", palette.getShade500());
    assertEquals("#6BE0D5", palette.getShade400());
    assertEquals("#91E8E0", palette.getShade300());
    assertEquals("#B7F0EA", palette.getShade200());
    assertEquals("#D9F7F4", palette.getShade100());
    assertEquals("#EEFCFA", palette.getShade50());
    List<String> routes = uiSettings.getRoutes();
    assertEquals(6, routes.size());
    assertEquals("/about/**", routes.get(0));
    assertEquals("/applications/**", routes.get(1));
    assertEquals("/external/**", routes.get(5));
    assertEquals("/wallboard/**", routes.get(4));
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        uiSettings.getBrand());
    assertEquals("Spring Boot Admin", uiSettings.getTitle());
    assertEquals("assets/img/favicon-danger.png", uiSettings.getFaviconDanger());
    assertEquals("assets/img/favicon.png", uiSettings.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", uiSettings.getLoginIcon());
    assertEquals("index", actualHomeUiControllerResult.index());
    assertEquals("login", actualHomeUiControllerResult.login());
    AdminServerUiProperties.PollTimer pollTimer = uiSettings.getPollTimer();
    assertEquals(1000, pollTimer.getLogfile());
    assertEquals(2500, pollTimer.getCache());
    assertEquals(2500, pollTimer.getDatasource());
    assertEquals(2500, pollTimer.getGc());
    assertEquals(2500, pollTimer.getMemory());
    assertEquals(2500, pollTimer.getProcess());
    assertEquals(2500, pollTimer.getThreads());
    assertFalse(uiSettings.getEnableToasts());
    assertFalse(uiSettings.getHideInstanceUrl());
    assertFalse(uiSettings.isNotificationFilterEnabled());
    assertTrue(theme.getBackgroundEnabled());
    assertTrue(uiSettings.isRememberMeEnabled());
    assertTrue(actualHomeUiControllerResult.getCssExtensions().isEmpty());
    assertTrue(actualHomeUiControllerResult.getJsExtensions().isEmpty());
    assertTrue(uiSettings.getAvailableLanguages().isEmpty());
    assertTrue(uiSettings.getExternalViews().isEmpty());
    assertTrue(uiSettings.getViewSettings().isEmpty());
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration.ReactiveUiConfiguration.AdminUiWebfluxConfig#addResourceHandlers(ResourceHandlerRegistry)}
   */
  @Test
  void testReactiveUiConfiguration_AdminUiWebfluxConfigAddResourceHandlers() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AnnotationConfigApplicationContext applicationContext = mock(AnnotationConfigApplicationContext.class);
    doNothing().when(applicationContext).addBeanFactoryPostProcessor(Mockito.<BeanFactoryPostProcessor>any());
    applicationContext.addBeanFactoryPostProcessor(new CustomAutowireConfigurer());
    AdminServerUiProperties adminUi = new AdminServerUiProperties();
    AdminServerProperties adminServer = new AdminServerProperties();
    AdminServerUiAutoConfiguration.ReactiveUiConfiguration.AdminUiWebfluxConfig adminUiWebfluxConfig = new AdminServerUiAutoConfiguration.ReactiveUiConfiguration.AdminUiWebfluxConfig(
        adminUi, adminServer, new WebFluxProperties(), applicationContext);

    // Act
    adminUiWebfluxConfig.addResourceHandlers(new ResourceHandlerRegistry(new ApplicationResourceLoader()));

    // Assert
    verify(applicationContext).addBeanFactoryPostProcessor(isA(BeanFactoryPostProcessor.class));
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration.ReactiveUiConfiguration.AdminUiWebfluxConfig#homepageForwardingFilterConfig()}
   */
  @Test
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
   * Method under test:
   * {@link AdminServerUiAutoConfiguration.ServletUiConfiguration.AdminUiWebMvcConfig#addResourceHandlers(ResourceHandlerRegistry)}
   */
  @Test
  void testServletUiConfiguration_AdminUiWebMvcConfigAddResourceHandlers() {
    // Arrange
    StandardEngine standardEngine = mock(StandardEngine.class);
    when(standardEngine.getService()).thenReturn(new StandardService());
    StandardContext standardContext = mock(StandardContext.class);
    when(standardContext.getParent()).thenReturn(standardEngine);
    StandardContext context = mock(StandardContext.class);
    when(context.getCookies()).thenReturn(true);
    when(context.getParent()).thenReturn(standardContext);
    org.apache.catalina.core.ApplicationContext servletContext = new org.apache.catalina.core.ApplicationContext(
        context);

    // Act
    adminUiWebMvcConfig.addResourceHandlers(
        new org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry(applicationContext,
            servletContext));

    // Assert
    verify(context).getParent();
    verify(standardContext).getParent();
    verify(context).getCookies();
    verify(standardEngine).getService();
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration.ServletUiConfiguration.AdminUiWebMvcConfig#configurePathMatch(PathMatchConfigurer)}
   */
  @Test
  void testServletUiConfiguration_AdminUiWebMvcConfigConfigurePathMatch() {
    // Arrange
    PathMatchConfigurer configurer = new PathMatchConfigurer();

    // Act
    adminUiWebMvcConfig.configurePathMatch(configurer);

    // Assert
    assertTrue(configurer.isUseTrailingSlashMatch());
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration.ServletUiConfiguration.AdminUiWebMvcConfig#configurePathMatch(PathMatchConfigurer)}
   */
  @Test
  void testServletUiConfiguration_AdminUiWebMvcConfigConfigurePathMatch2() {
    // Arrange
    PathMatchConfigurer configurer = mock(PathMatchConfigurer.class);
    when(configurer.setUseTrailingSlashMatch(Mockito.<Boolean>any())).thenReturn(new PathMatchConfigurer());

    // Act
    adminUiWebMvcConfig.configurePathMatch(configurer);

    // Assert that nothing has changed
    verify(configurer).setUseTrailingSlashMatch(eq(true));
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration.ServletUiConfiguration.AdminUiWebMvcConfig#homepageForwardingFilterConfig()}
   */
  @Test
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
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  void testUiExtensions() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  void testUiExtensions2() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AnnotationConfigReactiveWebApplicationContext applicationContext = new AnnotationConfigReactiveWebApplicationContext();
    applicationContext.addApplicationListener(mock(ApplicationListener.class));
    AdminServerUiProperties adminUi = new AdminServerUiProperties();

    // Act
    UiExtensions actualUiExtensionsResult = (new AdminServerUiAutoConfiguration(adminUi, new AdminServerProperties(),
        applicationContext)).uiExtensions();

    // Assert
    assertEquals(actualUiExtensionsResult.EMPTY, actualUiExtensionsResult);
  }

  /**
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  void testUiExtensions3() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  void testUiExtensions4() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  void testUiExtensions5() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  void testUiExtensions6() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link AdminServerUiAutoConfiguration#uiExtensions()}
   */
  @Test
  void testUiExtensions7() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link AdminServerUiAutoConfiguration#adminTemplateResolver()}
   */
  @Test
  void testAdminTemplateResolver() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    PatternSpec cSSTemplateModePatternSpec = actualAdminTemplateResolverResult.getCSSTemplateModePatternSpec();
    assertTrue(cSSTemplateModePatternSpec.isEmpty());
    PatternSpec cacheablePatternSpec = actualAdminTemplateResolverResult.getCacheablePatternSpec();
    assertTrue(cacheablePatternSpec.isEmpty());
    PatternSpec htmlTemplateModePatternSpec = actualAdminTemplateResolverResult.getHtmlTemplateModePatternSpec();
    assertTrue(htmlTemplateModePatternSpec.isEmpty());
    PatternSpec javaScriptTemplateModePatternSpec = actualAdminTemplateResolverResult
        .getJavaScriptTemplateModePatternSpec();
    assertTrue(javaScriptTemplateModePatternSpec.isEmpty());
    PatternSpec nonCacheablePatternSpec = actualAdminTemplateResolverResult.getNonCacheablePatternSpec();
    assertTrue(nonCacheablePatternSpec.isEmpty());
    PatternSpec rawTemplateModePatternSpec = actualAdminTemplateResolverResult.getRawTemplateModePatternSpec();
    assertTrue(rawTemplateModePatternSpec.isEmpty());
    PatternSpec textTemplateModePatternSpec = actualAdminTemplateResolverResult.getTextTemplateModePatternSpec();
    assertTrue(textTemplateModePatternSpec.isEmpty());
    PatternSpec xmlTemplateModePatternSpec = actualAdminTemplateResolverResult.getXmlTemplateModePatternSpec();
    assertTrue(xmlTemplateModePatternSpec.isEmpty());
    PatternSpec resolvablePatternSpec = actualAdminTemplateResolverResult.getResolvablePatternSpec();
    assertTrue(resolvablePatternSpec.isEmpty());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getCacheablePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getHtmlTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getJavaScriptTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getNonCacheablePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getRawTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getTextTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getXmlTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getResolvablePatterns());
    assertSame(cSSTemplateModePatterns, cSSTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, cacheablePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, htmlTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, javaScriptTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, nonCacheablePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, rawTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, textTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, xmlTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, resolvablePatternSpec.getPatterns());
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration#adminTemplateResolver()}
   */
  @Test
  void testAdminTemplateResolver2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerUiProperties adminUi = new AdminServerUiProperties();

    // Act
    SpringResourceTemplateResolver actualAdminTemplateResolverResult = (new AdminServerUiAutoConfiguration(adminUi,
        new AdminServerProperties(), mock(AnnotationConfigApplicationContext.class))).adminTemplateResolver();

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
    PatternSpec cSSTemplateModePatternSpec = actualAdminTemplateResolverResult.getCSSTemplateModePatternSpec();
    assertTrue(cSSTemplateModePatternSpec.isEmpty());
    PatternSpec cacheablePatternSpec = actualAdminTemplateResolverResult.getCacheablePatternSpec();
    assertTrue(cacheablePatternSpec.isEmpty());
    PatternSpec htmlTemplateModePatternSpec = actualAdminTemplateResolverResult.getHtmlTemplateModePatternSpec();
    assertTrue(htmlTemplateModePatternSpec.isEmpty());
    PatternSpec javaScriptTemplateModePatternSpec = actualAdminTemplateResolverResult
        .getJavaScriptTemplateModePatternSpec();
    assertTrue(javaScriptTemplateModePatternSpec.isEmpty());
    PatternSpec nonCacheablePatternSpec = actualAdminTemplateResolverResult.getNonCacheablePatternSpec();
    assertTrue(nonCacheablePatternSpec.isEmpty());
    PatternSpec rawTemplateModePatternSpec = actualAdminTemplateResolverResult.getRawTemplateModePatternSpec();
    assertTrue(rawTemplateModePatternSpec.isEmpty());
    PatternSpec textTemplateModePatternSpec = actualAdminTemplateResolverResult.getTextTemplateModePatternSpec();
    assertTrue(textTemplateModePatternSpec.isEmpty());
    PatternSpec xmlTemplateModePatternSpec = actualAdminTemplateResolverResult.getXmlTemplateModePatternSpec();
    assertTrue(xmlTemplateModePatternSpec.isEmpty());
    PatternSpec resolvablePatternSpec = actualAdminTemplateResolverResult.getResolvablePatternSpec();
    assertTrue(resolvablePatternSpec.isEmpty());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getCacheablePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getHtmlTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getJavaScriptTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getNonCacheablePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getRawTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getTextTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getXmlTemplateModePatterns());
    assertSame(cSSTemplateModePatterns, actualAdminTemplateResolverResult.getResolvablePatterns());
    assertSame(cSSTemplateModePatterns, cSSTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, cacheablePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, htmlTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, javaScriptTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, nonCacheablePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, rawTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, textTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, xmlTemplateModePatternSpec.getPatterns());
    assertSame(cSSTemplateModePatterns, resolvablePatternSpec.getPatterns());
  }

  /**
   * Method under test:
   * {@link AdminServerUiAutoConfiguration#normalizeHomepageUrl(String)}
   */
  @Test
  void testNormalizeHomepageUrl() {
    // Arrange, Act and Assert
    assertEquals("/https://example.org/example",
        AdminServerUiAutoConfiguration.normalizeHomepageUrl("https://example.org/example"));
    assertEquals("/", AdminServerUiAutoConfiguration.normalizeHomepageUrl("/"));
    assertEquals("/", AdminServerUiAutoConfiguration.normalizeHomepageUrl("//"));
    assertEquals("", AdminServerUiAutoConfiguration.normalizeHomepageUrl(""));
    assertEquals("/", AdminServerUiAutoConfiguration.normalizeHomepageUrl("///"));
  }
}
