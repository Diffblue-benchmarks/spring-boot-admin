package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecuritySecureConfigDiffblueTest {
  /**
   * Method under test:
   * {@link SecuritySecureConfig#userDetailsService(PasswordEncoder)}
   */
  @Test
  void testUserDetailsService() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerProperties adminServer = new AdminServerProperties();
    SecuritySecureConfig securitySecureConfig = new SecuritySecureConfig(adminServer, new SecurityProperties());

    // Act and Assert
    assertFalse(securitySecureConfig.userDetailsService(new BCryptPasswordEncoder()).userExists("janedoe"));
  }

  /**
   * Method under test: {@link SecuritySecureConfig#passwordEncoder()}
   */
  @Test
  void testPasswordEncoder() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerProperties adminServer = new AdminServerProperties();

    // Act and Assert
    assertTrue((new SecuritySecureConfig(adminServer, new SecurityProperties()))
        .passwordEncoder() instanceof BCryptPasswordEncoder);
  }

  /**
   * Method under test: {@link SecuritySecureConfig#passwordEncoder()}
   */
  @Test
  void testPasswordEncoder2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerProperties.ServerProperties server = mock(AdminServerProperties.ServerProperties.class);
    doNothing().when(server).setEnabled(anyBoolean());
    server.setEnabled(true);

    AdminServerProperties adminServer = new AdminServerProperties();
    adminServer.setServer(server);

    // Act
    PasswordEncoder actualPasswordEncoderResult = (new SecuritySecureConfig(adminServer, new SecurityProperties()))
        .passwordEncoder();

    // Assert
    verify(server).setEnabled(eq(true));
    assertTrue(actualPasswordEncoderResult instanceof BCryptPasswordEncoder);
  }
}
