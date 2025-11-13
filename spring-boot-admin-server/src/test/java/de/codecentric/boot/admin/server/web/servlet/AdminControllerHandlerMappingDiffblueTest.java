package de.codecentric.boot.admin.server.web.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMethodMappingNamingStrategy;

@ContextConfiguration(classes = {AdminControllerHandlerMapping.class, String.class})
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class AdminControllerHandlerMappingDiffblueTest {
  @Autowired private AdminControllerHandlerMapping adminControllerHandlerMapping;

  /**
   * Test {@link AdminControllerHandlerMapping#AdminControllerHandlerMapping(String)}.
   *
   * <p>Method under test: {@link
   * AdminControllerHandlerMapping#AdminControllerHandlerMapping(String)}
   */
  @Test
  @DisplayName("Test new AdminControllerHandlerMapping(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AdminControllerHandlerMapping.<init>(String)"})
  void testNewAdminControllerHandlerMapping() {
    // Arrange and Act
    AdminControllerHandlerMapping actualAdminControllerHandlerMapping =
        new AdminControllerHandlerMapping("Admin Context Path");

    // Assert
    assertTrue(actualAdminControllerHandlerMapping.getPathMatcher() instanceof AntPathMatcher);
    assertTrue(
        actualAdminControllerHandlerMapping.getCorsProcessor() instanceof DefaultCorsProcessor);
    assertTrue(
        actualAdminControllerHandlerMapping.getNamingStrategy()
            instanceof RequestMappingInfoHandlerMethodMappingNamingStrategy);
    assertNull(actualAdminControllerHandlerMapping.getDefaultHandler());
    assertNull(actualAdminControllerHandlerMapping.getFileExtensions());
    assertNull(actualAdminControllerHandlerMapping.getCorsConfigurationSource());
    assertNull(actualAdminControllerHandlerMapping.getAdaptedInterceptors());
    assertFalse(actualAdminControllerHandlerMapping.useRegisteredSuffixPatternMatch());
    assertFalse(actualAdminControllerHandlerMapping.useSuffixPatternMatch());
    assertFalse(actualAdminControllerHandlerMapping.useTrailingSlashMatch());
    assertTrue(actualAdminControllerHandlerMapping.getHandlerMethods().isEmpty());
    assertTrue(actualAdminControllerHandlerMapping.getPathPrefixes().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualAdminControllerHandlerMapping.getOrder());
  }

  /**
   * Test {@link AdminControllerHandlerMapping#isHandler(Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AdminControllerHandlerMapping#isHandler(Class)}
   */
  @Test
  @DisplayName("Test isHandler(Class); when 'java.lang.Object'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AdminControllerHandlerMapping.isHandler(Class)"})
  void testIsHandler_whenJavaLangObject_thenReturnFalse() {
    // Arrange
    Class<Object> beanType = Object.class;

    // Act and Assert
    assertFalse(adminControllerHandlerMapping.isHandler(beanType));
  }
}
