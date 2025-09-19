package de.codecentric.boot.admin.client.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.core.env.Environment;

class ClientPropertiesDiffblueTest {
  /**
   * Test {@link ClientProperties#getAdminUrl()}.
   *
   * <ul>
   *   <li>Given {@link ClientProperties} (default constructor).
   *   <li>Then return array length is zero.
   * </ul>
   *
   * <p>Method under test: {@link ClientProperties#getAdminUrl()}
   */
  @Test
  @DisplayName(
      "Test getAdminUrl(); given ClientProperties (default constructor); then return array length is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] ClientProperties.getAdminUrl()"})
  void testGetAdminUrl_givenClientProperties_thenReturnArrayLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, new ClientProperties().getAdminUrl().length);
  }

  /**
   * Test {@link ClientProperties#getAdminUrl()}.
   *
   * <ul>
   *   <li>Then return array of {@link String} with {@code https://example.org/example/Api Path}.
   * </ul>
   *
   * <p>Method under test: {@link ClientProperties#getAdminUrl()}
   */
  @Test
  @DisplayName(
      "Test getAdminUrl(); then return array of String with 'https://example.org/example/Api Path'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] ClientProperties.getAdminUrl()"})
  void testGetAdminUrl_thenReturnArrayOfStringWithHttpsExampleOrgExampleApiPath() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setConnectTimeout(Duration.ofSeconds(1L));
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setPeriod(Duration.ofSeconds(1L));
    clientProperties.setReadTimeout(Duration.ofSeconds(1L));
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[] {"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    // Act and Assert
    assertArrayEquals(
        new String[] {"https://example.org/example/Api Path"}, clientProperties.getAdminUrl());
  }

  /**
   * Test {@link ClientProperties#isAutoDeregistration(Environment)}.
   *
   * <ul>
   *   <li>Given {@link ClientProperties} (default constructor) ApiPath is {@code Api Path}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ClientProperties#isAutoDeregistration(Environment)}
   */
  @Test
  @DisplayName(
      "Test isAutoDeregistration(Environment); given ClientProperties (default constructor) ApiPath is 'Api Path'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ClientProperties.isAutoDeregistration(Environment)"})
  void testIsAutoDeregistration_givenClientPropertiesApiPathIsApiPath_thenReturnFalse() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();
    clientProperties.setApiPath("Api Path");
    clientProperties.setConnectTimeout(Duration.ofSeconds(1L));
    clientProperties.setEnabled(true);
    clientProperties.setPassword("iloveyou");
    clientProperties.setPeriod(Duration.ofSeconds(1L));
    clientProperties.setReadTimeout(Duration.ofSeconds(1L));
    clientProperties.setRegisterOnce(true);
    clientProperties.setUrl(new String[] {"https://example.org/example"});
    clientProperties.setUsername("janedoe");

    // Act and Assert
    assertFalse(clientProperties.isAutoDeregistration(null));
  }

  /**
   * Test {@link ClientProperties#isAutoDeregistration(Environment)}.
   *
   * <ul>
   *   <li>Given {@link ClientProperties} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ClientProperties#isAutoDeregistration(Environment)}
   */
  @Test
  @DisplayName(
      "Test isAutoDeregistration(Environment); given ClientProperties (default constructor); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ClientProperties.isAutoDeregistration(Environment)"})
  void testIsAutoDeregistration_givenClientProperties_thenReturnTrue() {
    // Arrange
    ClientProperties clientProperties = new ClientProperties();

    // Act and Assert
    assertTrue(clientProperties.isAutoDeregistration(new StandardReactiveWebEnvironment()));
  }
}
