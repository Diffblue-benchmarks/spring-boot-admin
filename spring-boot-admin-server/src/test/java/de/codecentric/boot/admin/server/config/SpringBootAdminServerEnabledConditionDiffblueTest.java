package de.codecentric.boot.admin.server.config;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

class SpringBootAdminServerEnabledConditionDiffblueTest {
  /**
   * Test {@link SpringBootAdminServerEnabledCondition#getMatchOutcome(ConditionContext,
   * AnnotatedTypeMetadata)}.
   *
   * <ul>
   *   <li>Given {@link StandardReactiveWebEnvironment#StandardReactiveWebEnvironment()}.
   *   <li>Then return Message is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringBootAdminServerEnabledCondition#getMatchOutcome(ConditionContext, AnnotatedTypeMetadata)}
   */
  @Test
  @DisplayName(
      "Test getMatchOutcome(ConditionContext, AnnotatedTypeMetadata); given StandardReactiveWebEnvironment(); then return Message is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ConditionOutcome SpringBootAdminServerEnabledCondition.getMatchOutcome(ConditionContext, AnnotatedTypeMetadata)"
  })
  void testGetMatchOutcome_givenStandardReactiveWebEnvironment_thenReturnMessageIsNull() {
    // Arrange
    SpringBootAdminServerEnabledCondition springBootAdminServerEnabledCondition =
        new SpringBootAdminServerEnabledCondition();

    ConditionContext context = mock(ConditionContext.class);
    when(context.getEnvironment()).thenReturn(new StandardReactiveWebEnvironment());

    // Act
    ConditionOutcome actualMatchOutcome =
        springBootAdminServerEnabledCondition.getMatchOutcome(
            context, mock(AnnotatedTypeMetadata.class));

    // Assert
    verify(context).getEnvironment();
    assertNull(actualMatchOutcome.getMessage());
    assertTrue(actualMatchOutcome.getConditionMessage().isEmpty());
    assertTrue(actualMatchOutcome.isMatch());
  }
}
