package de.codecentric.boot.admin.server.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.server.PathContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.cors.reactive.DefaultCorsProcessor;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMethodMappingNamingStrategy;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {AdminServerWebConfiguration.class, AdminServerProperties.class, InstanceRegistry.class,
    ApplicationRegistry.class, InstanceEventPublisher.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class AdminServerWebConfigurationDiffblueTest {
  @Autowired
  private AdminServerProperties adminServerProperties;

  @Autowired
  private AdminServerWebConfiguration adminServerWebConfiguration;

  @MockBean
  private InstanceEventStore instanceEventStore;

  @MockBean
  private InstanceFilter instanceFilter;

  @MockBean
  private InstanceIdGenerator instanceIdGenerator;

  @MockBean
  private InstanceRepository instanceRepository;

  /**
   * Method under test: {@link AdminServerWebConfiguration#adminJacksonModule()}
   */
  @Test
  public void testAdminJacksonModule() {
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
    assertEquals("de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
        actualAdminJacksonModuleResult.getModuleName());
    assertEquals("de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
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
   * Method under test:
   * {@link AdminServerWebConfiguration#instancesController(InstanceRegistry, InstanceEventStore)}
   */
  @Test
  public void testInstancesController() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerWebConfiguration adminServerWebConfiguration = new AdminServerWebConfiguration(
        new AdminServerProperties());
    InstanceRegistry instanceRegistry = new InstanceRegistry(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(InstanceIdGenerator.class),
        mock(InstanceFilter.class));

    // Act and Assert
    StepVerifier.FirstStep<InstanceEvent> createResult = StepVerifier
        .create(adminServerWebConfiguration.instancesController(instanceRegistry, new InMemoryEventStore()).events());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerWebConfiguration#applicationsController(ApplicationRegistry, ApplicationEventPublisher)}
   */
  @Test
  public void testApplicationsController() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerWebConfiguration adminServerWebConfiguration = new AdminServerWebConfiguration(
        new AdminServerProperties());

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(
        adminServerWebConfiguration
            .applicationsController(
                new ApplicationRegistry(
                    new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
                        mock(InstanceIdGenerator.class), mock(InstanceFilter.class)),
                    null),
                mock(ApplicationEventPublisher.class))
            .applications());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerWebConfiguration.ReactiveRestApiConfiguration#adminHandlerMapping(RequestedContentTypeResolver)}
   */
  @Test
  public void testReactiveRestApiConfigurationAdminHandlerMapping() throws IllegalStateException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RequestedContentTypeResolver webFluxContentTypeResolver = mock(RequestedContentTypeResolver.class);

    // Act
    RequestMappingHandlerMapping actualAdminHandlerMappingResult = (new AdminServerWebConfiguration.ReactiveRestApiConfiguration(
        new AdminServerProperties())).adminHandlerMapping(webFluxContentTypeResolver);

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
    assertSame(webFluxContentTypeResolver, actualAdminHandlerMappingResult.getContentTypeResolver());
  }

  /**
   * Method under test:
   * {@link AdminServerWebConfiguration.ReactiveRestApiConfiguration#instancesProxyController(InstanceRegistry, InstanceWebClient.Builder)}
   */
  @Test
  public void testReactiveRestApiConfigurationInstancesProxyController() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerWebConfiguration.ReactiveRestApiConfiguration reactiveRestApiConfiguration = new AdminServerWebConfiguration.ReactiveRestApiConfiguration(
        new AdminServerProperties());
    InstanceRegistry instanceRegistry = new InstanceRegistry(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(InstanceIdGenerator.class),
        mock(InstanceFilter.class));

    InstanceWebClient.Builder instanceWebClientBuilder = mock(InstanceWebClient.Builder.class);
    when(instanceWebClientBuilder.build()).thenReturn(null);

    // Act
    reactiveRestApiConfiguration.instancesProxyController(instanceRegistry, instanceWebClientBuilder);

    // Assert
    verify(instanceWebClientBuilder).build();
  }

  /**
   * Method under test:
   * {@link AdminServerWebConfiguration.ServletRestApiConfiguration#adminHandlerMapping(ContentNegotiationManager)}
   */
  @Test
  public void testServletRestApiConfigurationAdminHandlerMapping() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerWebConfiguration.ServletRestApiConfiguration servletRestApiConfiguration = new AdminServerWebConfiguration.ServletRestApiConfiguration(
        new AdminServerProperties());
    ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();

    // Act
    org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping actualAdminHandlerMappingResult = servletRestApiConfiguration
        .adminHandlerMapping(contentNegotiationManager);

    // Assert
    assertTrue(
        actualAdminHandlerMappingResult instanceof de.codecentric.boot.admin.server.web.servlet.AdminControllerHandlerMapping);
    assertTrue(actualAdminHandlerMappingResult.getPathMatcher() instanceof AntPathMatcher);
    assertTrue(actualAdminHandlerMappingResult
        .getCorsProcessor() instanceof org.springframework.web.cors.DefaultCorsProcessor);
    assertTrue(actualAdminHandlerMappingResult
        .getNamingStrategy() instanceof RequestMappingInfoHandlerMethodMappingNamingStrategy);
    PathPatternParser patternParser = actualAdminHandlerMappingResult.getPatternParser();
    PathContainer.Options pathOptions = patternParser.getPathOptions();
    assertEquals('/', pathOptions.separator());
    assertNull(actualAdminHandlerMappingResult.getAdaptedInterceptors());
    assertNull(actualAdminHandlerMappingResult.getDefaultHandler());
    RequestMappingInfo.BuilderConfiguration builderConfiguration = actualAdminHandlerMappingResult
        .getBuilderConfiguration();
    assertNull(builderConfiguration.getFileExtensions());
    assertNull(actualAdminHandlerMappingResult.getFileExtensions());
    assertNull(builderConfiguration.getPathMatcher());
    assertNull(builderConfiguration.getContentNegotiationManager());
    assertNull(actualAdminHandlerMappingResult.getCorsConfigurationSource());
    assertNull(builderConfiguration.getPatternParser());
    assertEquals(0, actualAdminHandlerMappingResult.getOrder());
    assertFalse(actualAdminHandlerMappingResult.useRegisteredSuffixPatternMatch());
    assertFalse(actualAdminHandlerMappingResult.useSuffixPatternMatch());
    assertFalse(actualAdminHandlerMappingResult.useTrailingSlashMatch());
    assertFalse(patternParser.isMatchOptionalTrailingSeparator());
    PathPatternParser patternParserToUse = builderConfiguration.getPatternParserToUse();
    assertFalse(patternParserToUse.isMatchOptionalTrailingSeparator());
    assertTrue(actualAdminHandlerMappingResult.getHandlerMethods().isEmpty());
    assertTrue(actualAdminHandlerMappingResult.getPathPrefixes().isEmpty());
    assertTrue(actualAdminHandlerMappingResult.getUrlPathHelper().isUrlDecode());
    assertTrue(builderConfiguration.getUrlPathHelper().isUrlDecode());
    assertTrue(patternParser.isCaseSensitive());
    assertTrue(patternParserToUse.isCaseSensitive());
    assertSame(contentNegotiationManager, actualAdminHandlerMappingResult.getContentNegotiationManager());
    assertSame(pathOptions, patternParserToUse.getPathOptions());
  }

  /**
   * Method under test:
   * {@link AdminServerWebConfiguration.ServletRestApiConfiguration#adminHandlerMapping(ContentNegotiationManager)}
   */
  @Test
  public void testServletRestApiConfigurationAdminHandlerMapping2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerProperties.ServerProperties server = mock(AdminServerProperties.ServerProperties.class);
    doNothing().when(server).setEnabled(anyBoolean());
    server.setEnabled(true);

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setServer(server);
    AdminServerWebConfiguration.ServletRestApiConfiguration servletRestApiConfiguration = new AdminServerWebConfiguration.ServletRestApiConfiguration(
        adminServerProperties);
    ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();

    // Act
    org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping actualAdminHandlerMappingResult = servletRestApiConfiguration
        .adminHandlerMapping(contentNegotiationManager);

    // Assert
    verify(server).setEnabled(eq(true));
    assertTrue(
        actualAdminHandlerMappingResult instanceof de.codecentric.boot.admin.server.web.servlet.AdminControllerHandlerMapping);
    assertTrue(actualAdminHandlerMappingResult.getPathMatcher() instanceof AntPathMatcher);
    assertTrue(actualAdminHandlerMappingResult
        .getCorsProcessor() instanceof org.springframework.web.cors.DefaultCorsProcessor);
    assertTrue(actualAdminHandlerMappingResult
        .getNamingStrategy() instanceof RequestMappingInfoHandlerMethodMappingNamingStrategy);
    PathPatternParser patternParser = actualAdminHandlerMappingResult.getPatternParser();
    PathContainer.Options pathOptions = patternParser.getPathOptions();
    assertEquals('/', pathOptions.separator());
    assertNull(actualAdminHandlerMappingResult.getAdaptedInterceptors());
    assertNull(actualAdminHandlerMappingResult.getDefaultHandler());
    RequestMappingInfo.BuilderConfiguration builderConfiguration = actualAdminHandlerMappingResult
        .getBuilderConfiguration();
    assertNull(builderConfiguration.getFileExtensions());
    assertNull(actualAdminHandlerMappingResult.getFileExtensions());
    assertNull(builderConfiguration.getPathMatcher());
    assertNull(builderConfiguration.getContentNegotiationManager());
    assertNull(actualAdminHandlerMappingResult.getCorsConfigurationSource());
    assertNull(builderConfiguration.getPatternParser());
    assertEquals(0, actualAdminHandlerMappingResult.getOrder());
    assertFalse(actualAdminHandlerMappingResult.useRegisteredSuffixPatternMatch());
    assertFalse(actualAdminHandlerMappingResult.useSuffixPatternMatch());
    assertFalse(actualAdminHandlerMappingResult.useTrailingSlashMatch());
    assertFalse(patternParser.isMatchOptionalTrailingSeparator());
    PathPatternParser patternParserToUse = builderConfiguration.getPatternParserToUse();
    assertFalse(patternParserToUse.isMatchOptionalTrailingSeparator());
    assertTrue(actualAdminHandlerMappingResult.getHandlerMethods().isEmpty());
    assertTrue(actualAdminHandlerMappingResult.getPathPrefixes().isEmpty());
    assertTrue(actualAdminHandlerMappingResult.getUrlPathHelper().isUrlDecode());
    assertTrue(builderConfiguration.getUrlPathHelper().isUrlDecode());
    assertTrue(patternParser.isCaseSensitive());
    assertTrue(patternParserToUse.isCaseSensitive());
    assertSame(contentNegotiationManager, actualAdminHandlerMappingResult.getContentNegotiationManager());
    assertSame(pathOptions, patternParserToUse.getPathOptions());
  }

  /**
   * Method under test:
   * {@link AdminServerWebConfiguration.ServletRestApiConfiguration#instancesProxyController(InstanceRegistry, InstanceWebClient.Builder)}
   */
  @Test
  public void testServletRestApiConfigurationInstancesProxyController() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerWebConfiguration.ServletRestApiConfiguration servletRestApiConfiguration = new AdminServerWebConfiguration.ServletRestApiConfiguration(
        new AdminServerProperties());
    InstanceRegistry instanceRegistry = new InstanceRegistry(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(InstanceIdGenerator.class),
        mock(InstanceFilter.class));

    InstanceWebClient.Builder instanceWebClientBuilder = mock(InstanceWebClient.Builder.class);
    when(instanceWebClientBuilder.build()).thenReturn(null);

    // Act
    servletRestApiConfiguration.instancesProxyController(instanceRegistry, instanceWebClientBuilder);

    // Assert
    verify(instanceWebClientBuilder).build();
  }
}
