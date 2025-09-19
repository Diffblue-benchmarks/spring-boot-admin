package de.codecentric.boot.admin.server.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.codecentric.boot.admin.server.config.AdminServerWebConfiguration.ReactiveRestApiConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerWebConfiguration.ServletRestApiConfiguration;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventPublisher;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.utils.jackson.AdminServerModule;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import de.codecentric.boot.admin.server.web.reactive.AdminControllerHandlerMapping;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.cors.reactive.DefaultCorsProcessor;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMethodMappingNamingStrategy;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(
    classes = {
      AdminServerWebConfiguration.class,
      AdminServerProperties.class,
      InstanceRegistry.class,
      ApplicationRegistry.class,
      InstanceEventPublisher.class
    })
@DisabledInAotMode
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AdminServerWebConfigurationDiffblueTest {
  @Autowired private AdminServerProperties adminServerProperties;

  @InjectMocks private AdminServerProperties adminServerProperties2;

  @Autowired private AdminServerWebConfiguration adminServerWebConfiguration;

  @InjectMocks private AdminServerWebConfiguration adminServerWebConfiguration2;

  @MockitoBean private InstanceEventStore instanceEventStore;

  @MockitoBean private InstanceFilter instanceFilter;

  @MockitoBean private InstanceIdGenerator instanceIdGenerator;

  @MockitoBean private InstanceRepository instanceRepository;

  /**
   * Test {@link AdminServerWebConfiguration#adminJacksonModule()}.
   *
   * <p>Method under test: {@link AdminServerWebConfiguration#adminJacksonModule()}
   */
  @Test
  @DisplayName("Test adminJacksonModule()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"SimpleModule AdminServerWebConfiguration.adminJacksonModule()"})
  void testAdminJacksonModule() {
    // Arrange and Act
    SimpleModule actualAdminJacksonModuleResult = adminServerWebConfiguration.adminJacksonModule();

    // Assert
    assertTrue(actualAdminJacksonModuleResult instanceof AdminServerModule);
    Iterable<? extends Module> dependencies = actualAdminJacksonModuleResult.getDependencies();
    assertTrue(dependencies instanceof List);
    Version versionResult = actualAdminJacksonModuleResult.version();
    assertEquals("", versionResult.getArtifactId());
    assertEquals("", versionResult.getGroupId());
    assertEquals("//0.0.0", versionResult.toFullString());
    assertEquals(
        "de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
        actualAdminJacksonModuleResult.getModuleName());
    assertEquals(
        "de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
        actualAdminJacksonModuleResult.getTypeId());
    assertEquals(0, versionResult.getMajorVersion());
    assertEquals(0, versionResult.getMinorVersion());
    assertEquals(0, versionResult.getPatchLevel());
    assertFalse(versionResult.isSnapshot());
    assertTrue(versionResult.isUknownVersion());
    assertTrue(versionResult.isUnknownVersion());
    assertTrue(((List<? extends Module>) dependencies).isEmpty());
  }

  /**
   * Test {@link AdminServerWebConfiguration#instancesController(InstanceRegistry,
   * InstanceEventStore)}.
   *
   * <p>Method under test: {@link AdminServerWebConfiguration#instancesController(InstanceRegistry,
   * InstanceEventStore)}
   */
  @Test
  @DisplayName("Test instancesController(InstanceRegistry, InstanceEventStore)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.web.InstancesController AdminServerWebConfiguration.instancesController(InstanceRegistry, InstanceEventStore)"
  })
  void testInstancesController() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<InstanceEvent> createResult =
        StepVerifier.create(
            adminServerWebConfiguration2
                .instancesController(instanceRegistry, new InMemoryEventStore())
                .events());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AdminServerWebConfiguration#applicationsController(ApplicationRegistry,
   * ApplicationEventPublisher)}.
   *
   * <p>Method under test: {@link
   * AdminServerWebConfiguration#applicationsController(ApplicationRegistry,
   * ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test applicationsController(ApplicationRegistry, ApplicationEventPublisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.web.ApplicationsController AdminServerWebConfiguration.applicationsController(ApplicationRegistry, ApplicationEventPublisher)"
  })
  void testApplicationsController() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));
    ApplicationRegistry applicationRegistry =
        new ApplicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(
            adminServerWebConfiguration2
                .applicationsController(applicationRegistry, mock(ApplicationEventPublisher.class))
                .applications());
    createResult.expectComplete().verify();
  }

  /**
   * Test ReactiveRestApiConfiguration {@link
   * ReactiveRestApiConfiguration#adminHandlerMapping(RequestedContentTypeResolver)}.
   *
   * <p>Method under test: {@link
   * ReactiveRestApiConfiguration#adminHandlerMapping(RequestedContentTypeResolver)}
   */
  @Test
  @DisplayName(
      "Test ReactiveRestApiConfiguration adminHandlerMapping(RequestedContentTypeResolver)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RequestMappingHandlerMapping ReactiveRestApiConfiguration.adminHandlerMapping(RequestedContentTypeResolver)"
  })
  void testReactiveRestApiConfigurationAdminHandlerMapping() throws IllegalStateException {
    // Arrange
    RequestedContentTypeResolver webFluxContentTypeResolver =
        mock(RequestedContentTypeResolver.class);

    // Act
    RequestMappingHandlerMapping actualAdminHandlerMappingResult =
        new ReactiveRestApiConfiguration(new AdminServerProperties())
            .adminHandlerMapping(webFluxContentTypeResolver);

    // Assert
    assertTrue(actualAdminHandlerMappingResult instanceof AdminControllerHandlerMapping);
    assertTrue(actualAdminHandlerMappingResult.getCorsProcessor() instanceof DefaultCorsProcessor);
    PathPatternParser pathPatternParser = actualAdminHandlerMappingResult.getPathPatternParser();
    assertEquals('/', pathPatternParser.getPathOptions().separator());
    assertNull(actualAdminHandlerMappingResult.getApplicationContext());
    assertEquals(0, actualAdminHandlerMappingResult.getOrder());
    assertFalse(pathPatternParser.isMatchOptionalTrailingSeparator());
    assertTrue(actualAdminHandlerMappingResult.getHandlerMethods().isEmpty());
    assertTrue(actualAdminHandlerMappingResult.getPathPrefixes().isEmpty());
    assertTrue(pathPatternParser.isCaseSensitive());
    assertSame(
        webFluxContentTypeResolver, actualAdminHandlerMappingResult.getContentTypeResolver());
  }

  /**
   * Test ReactiveRestApiConfiguration {@link
   * ReactiveRestApiConfiguration#instancesProxyController(InstanceRegistry, Builder)}.
   *
   * <ul>
   *   <li>Given {@link WebClient}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ReactiveRestApiConfiguration#instancesProxyController(InstanceRegistry,
   * InstanceWebClient.Builder)}
   */
  @Test
  @DisplayName(
      "Test ReactiveRestApiConfiguration instancesProxyController(InstanceRegistry, Builder); given WebClient")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.web.reactive.InstancesProxyController ReactiveRestApiConfiguration.instancesProxyController(InstanceRegistry, InstanceWebClient.Builder)"
  })
  void testReactiveRestApiConfigurationInstancesProxyController_givenWebClient() {
    // Arrange
    ReactiveRestApiConfiguration reactiveRestApiConfiguration =
        new ReactiveRestApiConfiguration(new AdminServerProperties());
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    Builder webClient = mock(Builder.class);
    when(webClient.build()).thenReturn(mock(WebClient.class));

    // Act
    reactiveRestApiConfiguration.instancesProxyController(
        instanceRegistry, InstanceWebClient.builder(webClient));

    // Assert
    verify(webClient).build();
  }

  /**
   * Test ServletRestApiConfiguration {@link
   * ServletRestApiConfiguration#adminHandlerMapping(ContentNegotiationManager)}.
   *
   * <p>Method under test: {@link
   * ServletRestApiConfiguration#adminHandlerMapping(ContentNegotiationManager)}
   */
  @Test
  @DisplayName("Test ServletRestApiConfiguration adminHandlerMapping(ContentNegotiationManager)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping ServletRestApiConfiguration.adminHandlerMapping(ContentNegotiationManager)"
  })
  void testServletRestApiConfigurationAdminHandlerMapping() {
    // Arrange
    ServletRestApiConfiguration servletRestApiConfiguration =
        new ServletRestApiConfiguration(new AdminServerProperties());
    ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();

    // Act
    org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
        actualAdminHandlerMappingResult =
            servletRestApiConfiguration.adminHandlerMapping(contentNegotiationManager);

    // Assert
    assertTrue(
        actualAdminHandlerMappingResult
            instanceof de.codecentric.boot.admin.server.web.servlet.AdminControllerHandlerMapping);
    assertTrue(actualAdminHandlerMappingResult.getPathMatcher() instanceof AntPathMatcher);
    assertTrue(
        actualAdminHandlerMappingResult.getCorsProcessor()
            instanceof org.springframework.web.cors.DefaultCorsProcessor);
    assertTrue(
        actualAdminHandlerMappingResult.getNamingStrategy()
            instanceof RequestMappingInfoHandlerMethodMappingNamingStrategy);
    assertNull(actualAdminHandlerMappingResult.getDefaultHandler());
    assertNull(actualAdminHandlerMappingResult.getFileExtensions());
    assertNull(actualAdminHandlerMappingResult.getCorsConfigurationSource());
    assertNull(actualAdminHandlerMappingResult.getAdaptedInterceptors());
    assertEquals(0, actualAdminHandlerMappingResult.getOrder());
    assertFalse(actualAdminHandlerMappingResult.useRegisteredSuffixPatternMatch());
    assertFalse(actualAdminHandlerMappingResult.useSuffixPatternMatch());
    assertFalse(actualAdminHandlerMappingResult.useTrailingSlashMatch());
    assertTrue(actualAdminHandlerMappingResult.getHandlerMethods().isEmpty());
    assertTrue(actualAdminHandlerMappingResult.getPathPrefixes().isEmpty());
    assertSame(
        contentNegotiationManager, actualAdminHandlerMappingResult.getContentNegotiationManager());
  }
}
