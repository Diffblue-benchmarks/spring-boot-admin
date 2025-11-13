package de.codecentric.boot.admin.server.web.reactive;

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
import org.springframework.web.cors.reactive.DefaultCorsProcessor;
import org.springframework.web.util.pattern.PathPatternParser;

@ContextConfiguration(classes = {AdminControllerHandlerMapping.class, String.class})
@ExtendWith(SpringExtension.class)
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
  void testNewAdminControllerHandlerMapping() throws IllegalStateException {
    // Arrange and Act
    AdminControllerHandlerMapping actualAdminControllerHandlerMapping =
        new AdminControllerHandlerMapping("Admin Context Path");

    // Assert
    assertTrue(
        actualAdminControllerHandlerMapping.getCorsProcessor() instanceof DefaultCorsProcessor);
    PathPatternParser pathPatternParser =
        actualAdminControllerHandlerMapping.getPathPatternParser();
    assertEquals('/', pathPatternParser.getPathOptions().separator());
    assertNull(actualAdminControllerHandlerMapping.getApplicationContext());
    assertFalse(pathPatternParser.isMatchOptionalTrailingSeparator());
    assertTrue(actualAdminControllerHandlerMapping.getHandlerMethods().isEmpty());
    assertTrue(actualAdminControllerHandlerMapping.getPathPrefixes().isEmpty());
    assertTrue(pathPatternParser.isCaseSensitive());
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
