package de.codecentric.boot.admin;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
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
import reactor.test.StepVerifier.FirstStep;
import reactor.test.StepVerifier.Step;

class SpringBootAdminEurekaApplicationDiffblueTest {
  /**
   * Test {@link
   * SpringBootAdminEurekaApplication#securityWebFilterChainPermitAll(ServerHttpSecurity)}.
   *
   * <ul>
   *   <li>Then return {@link MatcherSecurityWebFilterChain}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SpringBootAdminEurekaApplication#securityWebFilterChainPermitAll(ServerHttpSecurity)}
   */
  @Test
  @DisplayName(
      "Test securityWebFilterChainPermitAll(ServerHttpSecurity); then return MatcherSecurityWebFilterChain")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SecurityWebFilterChain SpringBootAdminEurekaApplication.securityWebFilterChainPermitAll(ServerHttpSecurity)"
  })
  void testSecurityWebFilterChainPermitAll_thenReturnMatcherSecurityWebFilterChain()
      throws AssertionError {
    // Arrange
    SpringBootAdminEurekaApplication springBootAdminEurekaApplication =
        new SpringBootAdminEurekaApplication(new AdminServerProperties());

    // Act
    SecurityWebFilterChain actualSecurityWebFilterChainPermitAllResult =
        springBootAdminEurekaApplication.securityWebFilterChainPermitAll(ServerHttpSecurity.http());

    // Assert
    assertTrue(
        actualSecurityWebFilterChainPermitAllResult instanceof MatcherSecurityWebFilterChain);
    FirstStep<WebFilter> createResult =
        StepVerifier.create(actualSecurityWebFilterChainPermitAllResult.getWebFilters());
    Step<WebFilter> assertNextResult = createResult.assertNext(w -> {});
    Step<WebFilter> assertNextResult2 =
        assertNextResult.assertNext(
            w2 -> {
              assertTrue(w2 instanceof HttpHeaderWriterWebFilter);
              return;
            });
    Step<WebFilter> assertNextResult3 =
        assertNextResult2.assertNext(
            w3 -> {
              assertTrue(w3 instanceof ReactorContextWebFilter);
              return;
            });
    Step<WebFilter> assertNextResult4 =
        assertNextResult3.assertNext(
            w4 -> {
              assertTrue(w4 instanceof SecurityContextServerWebExchangeWebFilter);
              return;
            });
    Step<WebFilter> assertNextResult5 =
        assertNextResult4.assertNext(
            w5 -> {
              assertTrue(w5 instanceof ServerRequestCacheWebFilter);
              return;
            });
    Step<WebFilter> assertNextResult6 =
        assertNextResult5.assertNext(
            w6 -> {
              assertTrue(w6 instanceof LogoutWebFilter);
              return;
            });
    Step<WebFilter> assertNextResult7 =
        assertNextResult6.assertNext(
            w7 -> {
              assertTrue(w7 instanceof ExceptionTranslationWebFilter);
              return;
            });
    assertNextResult7
        .assertNext(
            w8 -> {
              assertTrue(w8 instanceof AuthorizationWebFilter);
              return;
            })
        .expectComplete()
        .verify();
  }
}
