package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecuritySecureConfigDiffblueTest {
  /**
   * Test {@link SecuritySecureConfig#userDetailsService(PasswordEncoder)}.
   *
   * <ul>
   *   <li>When {@link BCryptPasswordEncoder#BCryptPasswordEncoder()}.
   *   <li>Then return not userExists {@code janedoe}.
   * </ul>
   *
   * <p>Method under test: {@link SecuritySecureConfig#userDetailsService(PasswordEncoder)}
   */
  @Test
  @DisplayName(
      "Test userDetailsService(PasswordEncoder); when BCryptPasswordEncoder(); then return not userExists 'janedoe'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.security.provisioning.InMemoryUserDetailsManager SecuritySecureConfig.userDetailsService(PasswordEncoder)"
  })
  void testUserDetailsService_whenBCryptPasswordEncoder_thenReturnNotUserExistsJanedoe() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AdminServerProperties adminServer = new AdminServerProperties();
    SecuritySecureConfig securitySecureConfig =
        new SecuritySecureConfig(adminServer, new SecurityProperties());

    // Act and Assert
    assertFalse(
        securitySecureConfig.userDetailsService(new BCryptPasswordEncoder()).userExists("janedoe"));
  }

  /**
   * Test {@link SecuritySecureConfig#passwordEncoder()}.
   *
   * <p>Method under test: {@link SecuritySecureConfig#passwordEncoder()}
   */
  @Test
  @DisplayName("Test passwordEncoder()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"PasswordEncoder SecuritySecureConfig.passwordEncoder()"})
  void testPasswordEncoder() {
    // Arrange
    AdminServerProperties adminServer = new AdminServerProperties();
    SecuritySecureConfig securitySecureConfig =
        new SecuritySecureConfig(adminServer, new SecurityProperties());

    // Act and Assert
    assertTrue(securitySecureConfig.passwordEncoder() instanceof BCryptPasswordEncoder);
  }
}
