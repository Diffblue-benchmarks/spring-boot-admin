package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

class SpringBootAdminClientEnabledConditionDiffblueTest {
  /**
   * Method under test:
   * {@link SpringBootAdminClientEnabledCondition#getMatchOutcome(ConditionContext, AnnotatedTypeMetadata)}
   */
  @Test
  void testGetMatchOutcome() {
    // Arrange
    SpringBootAdminClientEnabledCondition springBootAdminClientEnabledCondition = new SpringBootAdminClientEnabledCondition();
    ConditionContext context = mock(ConditionContext.class);
    when(context.getEnvironment()).thenReturn(new StandardReactiveWebEnvironment());

    // Act
    ConditionOutcome actualMatchOutcome = springBootAdminClientEnabledCondition.getMatchOutcome(context,
        mock(AnnotatedTypeMetadata.class));

    // Assert
    verify(context).getEnvironment();
    assertEquals("Spring Boot Client is disabled, because 'spring.boot.admin.client.url' is empty.",
        actualMatchOutcome.getMessage());
    assertFalse(actualMatchOutcome.getConditionMessage().isEmpty());
    assertFalse(actualMatchOutcome.isMatch());
  }
}
