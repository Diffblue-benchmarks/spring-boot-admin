package de.codecentric.boot.admin.server.web.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.PathContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMethodMappingNamingStrategy;
import org.springframework.web.util.pattern.PathPatternParser;

@ContextConfiguration(classes = {AdminControllerHandlerMapping.class, String.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminControllerHandlerMappingDiffblueTest {
  @Autowired
  private AdminControllerHandlerMapping adminControllerHandlerMapping;

  /**
   * Method under test: {@link AdminControllerHandlerMapping#isHandler(Class)}
   */
  @Test
  public void testIsHandler() {
    // Arrange
    Class<Object> beanType = Object.class;

    // Act and Assert
    assertFalse(adminControllerHandlerMapping.isHandler(beanType));
  }

  /**
   * Method under test:
   * {@link AdminControllerHandlerMapping#AdminControllerHandlerMapping(String)}
   */
  @Test
  public void testNewAdminControllerHandlerMapping() {
    // Arrange and Act
    AdminControllerHandlerMapping actualAdminControllerHandlerMapping = new AdminControllerHandlerMapping(
        "Admin Context Path");

    // Assert
    assertTrue(actualAdminControllerHandlerMapping.getPathMatcher() instanceof AntPathMatcher);
    ContentNegotiationManager contentNegotiationManager = actualAdminControllerHandlerMapping
        .getContentNegotiationManager();
    List<ContentNegotiationStrategy> strategies = contentNegotiationManager.getStrategies();
    assertEquals(1, strategies.size());
    assertTrue(strategies.get(0) instanceof HeaderContentNegotiationStrategy);
    assertTrue(actualAdminControllerHandlerMapping.getCorsProcessor() instanceof DefaultCorsProcessor);
    assertTrue(actualAdminControllerHandlerMapping
        .getNamingStrategy() instanceof RequestMappingInfoHandlerMethodMappingNamingStrategy);
    PathPatternParser patternParser = actualAdminControllerHandlerMapping.getPatternParser();
    PathContainer.Options pathOptions = patternParser.getPathOptions();
    assertEquals('/', pathOptions.separator());
    assertNull(actualAdminControllerHandlerMapping.getAdaptedInterceptors());
    assertNull(actualAdminControllerHandlerMapping.getDefaultHandler());
    RequestMappingInfo.BuilderConfiguration builderConfiguration = actualAdminControllerHandlerMapping
        .getBuilderConfiguration();
    assertNull(builderConfiguration.getFileExtensions());
    assertNull(actualAdminControllerHandlerMapping.getFileExtensions());
    assertNull(builderConfiguration.getPathMatcher());
    assertNull(builderConfiguration.getContentNegotiationManager());
    assertNull(actualAdminControllerHandlerMapping.getCorsConfigurationSource());
    assertNull(builderConfiguration.getPatternParser());
    assertFalse(actualAdminControllerHandlerMapping.useRegisteredSuffixPatternMatch());
    assertFalse(actualAdminControllerHandlerMapping.useSuffixPatternMatch());
    assertFalse(actualAdminControllerHandlerMapping.useTrailingSlashMatch());
    assertFalse(patternParser.isMatchOptionalTrailingSeparator());
    PathPatternParser patternParserToUse = builderConfiguration.getPatternParserToUse();
    assertFalse(patternParserToUse.isMatchOptionalTrailingSeparator());
    assertTrue(contentNegotiationManager.getAllFileExtensions().isEmpty());
    assertTrue(actualAdminControllerHandlerMapping.getHandlerMethods().isEmpty());
    Map<String, Predicate<Class<?>>> pathPrefixes = actualAdminControllerHandlerMapping.getPathPrefixes();
    assertTrue(pathPrefixes.isEmpty());
    assertTrue(actualAdminControllerHandlerMapping.getUrlPathHelper().isUrlDecode());
    assertTrue(builderConfiguration.getUrlPathHelper().isUrlDecode());
    assertTrue(patternParser.isCaseSensitive());
    assertTrue(patternParserToUse.isCaseSensitive());
    assertEquals(Integer.MAX_VALUE, actualAdminControllerHandlerMapping.getOrder());
    assertSame(pathPrefixes, contentNegotiationManager.getMediaTypeMappings());
    assertSame(pathOptions, patternParserToUse.getPathOptions());
  }
}
