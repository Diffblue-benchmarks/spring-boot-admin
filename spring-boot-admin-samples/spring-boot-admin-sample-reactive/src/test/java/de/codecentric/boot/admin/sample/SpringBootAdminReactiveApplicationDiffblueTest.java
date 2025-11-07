package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.Notifier;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.MatcherSecurityWebFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.LogoutWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationWebFilter;
import org.springframework.security.web.server.authorization.ExceptionTranslationWebFilter;
import org.springframework.security.web.server.context.ReactorContextWebFilter;
import org.springframework.security.web.server.context.SecurityContextServerWebExchangeWebFilter;
import org.springframework.security.web.server.header.HttpHeaderWriterWebFilter;
import org.springframework.security.web.server.savedrequest.ServerRequestCacheWebFilter;
import org.springframework.web.server.WebFilter;
import reactor.test.StepVerifier;

class SpringBootAdminReactiveApplicationDiffblueTest {
  /**
   * Method under test:
   * {@link SpringBootAdminReactiveApplication#securityWebFilterChainPermitAll(ServerHttpSecurity)}
   */
  @Test
  void testSecurityWebFilterChainPermitAll() throws AssertionError {
    // Arrange
    SpringBootAdminReactiveApplication springBootAdminReactiveApplication = new SpringBootAdminReactiveApplication(
        new AdminServerProperties());

    // Act
    SecurityWebFilterChain actualSecurityWebFilterChainPermitAllResult = springBootAdminReactiveApplication
        .securityWebFilterChainPermitAll(ServerHttpSecurity.http());

    // Assert
    assertTrue(actualSecurityWebFilterChainPermitAllResult instanceof MatcherSecurityWebFilterChain);
    StepVerifier.FirstStep<WebFilter> createResult = StepVerifier
        .create(actualSecurityWebFilterChainPermitAllResult.getWebFilters());
    StepVerifier.Step<WebFilter> assertNextResult = createResult.assertNext(w -> {});
    StepVerifier.Step<WebFilter> assertNextResult2 = assertNextResult.assertNext(w2 -> {
      assertTrue(w2 instanceof HttpHeaderWriterWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult3 = assertNextResult2.assertNext(w3 -> {
      assertTrue(w3 instanceof ReactorContextWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult4 = assertNextResult3.assertNext(w4 -> {
      assertTrue(w4 instanceof SecurityContextServerWebExchangeWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult5 = assertNextResult4.assertNext(w5 -> {
      assertTrue(w5 instanceof ServerRequestCacheWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult6 = assertNextResult5.assertNext(w6 -> {
      assertTrue(w6 instanceof LogoutWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult7 = assertNextResult6.assertNext(w7 -> {
      assertTrue(w7 instanceof ExceptionTranslationWebFilter);
      return;
    });
    assertNextResult7.assertNext(w8 -> {
      assertTrue(w8 instanceof AuthorizationWebFilter);
      return;
    }).expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link SpringBootAdminReactiveApplication#securityWebFilterChainPermitAll(ServerHttpSecurity)}
   */
  @Test
  void testSecurityWebFilterChainPermitAll2() throws AssertionError {
    // Arrange
    SpringBootAdminReactiveApplication springBootAdminReactiveApplication = new SpringBootAdminReactiveApplication(
        new AdminServerProperties());
    ServerHttpSecurity http = ServerHttpSecurity.http();
    http.addFilterAt(mock(WebFilter.class), SecurityWebFiltersOrder.FIRST);

    // Act
    SecurityWebFilterChain actualSecurityWebFilterChainPermitAllResult = springBootAdminReactiveApplication
        .securityWebFilterChainPermitAll(http);

    // Assert
    assertTrue(actualSecurityWebFilterChainPermitAllResult instanceof MatcherSecurityWebFilterChain);
    StepVerifier.FirstStep<WebFilter> createResult = StepVerifier
        .create(actualSecurityWebFilterChainPermitAllResult.getWebFilters());
    StepVerifier.Step<WebFilter> assertNextResult = createResult.assertNext(w -> {});
    StepVerifier.Step<WebFilter> assertNextResult2 = assertNextResult.assertNext(w2 -> {});
    StepVerifier.Step<WebFilter> assertNextResult3 = assertNextResult2.assertNext(w3 -> {
      assertTrue(w3 instanceof HttpHeaderWriterWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult4 = assertNextResult3.assertNext(w4 -> {
      assertTrue(w4 instanceof ReactorContextWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult5 = assertNextResult4.assertNext(w5 -> {
      assertTrue(w5 instanceof SecurityContextServerWebExchangeWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult6 = assertNextResult5.assertNext(w6 -> {
      assertTrue(w6 instanceof ServerRequestCacheWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult7 = assertNextResult6.assertNext(w7 -> {
      assertTrue(w7 instanceof LogoutWebFilter);
      return;
    });
    StepVerifier.Step<WebFilter> assertNextResult8 = assertNextResult7.assertNext(w8 -> {
      assertTrue(w8 instanceof ExceptionTranslationWebFilter);
      return;
    });
    assertNextResult8.assertNext(w9 -> {
      assertTrue(w9 instanceof AuthorizationWebFilter);
      return;
    }).expectComplete().verify();
  }

  /**
   * Method under test: {@link SpringBootAdminReactiveApplication#notifier()}
   */
  @Test
  void testNotifier() throws AssertionError {
    // Arrange and Act
    Notifier actualNotifierResult = (new SpringBootAdminReactiveApplication(new AdminServerProperties())).notifier();

    // Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(actualNotifierResult.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link SpringBootAdminReactiveApplication#notifier()}
   */
  @Test
  void testNotifier2() throws AssertionError {
    // Arrange and Act
    Notifier actualNotifierResult = (new SpringBootAdminReactiveApplication(null)).notifier();

    // Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(actualNotifierResult.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }
}
