package de.codecentric.boot.admin.server.web.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class LegacyEndpointConvertersDiffblueTest {
  /**
   * Test {@link LegacyEndpointConverters#health()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#health()}
   */
  @Test
  @DisplayName("Test health()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.health()"})
  void testHealth() throws AssertionError {
    // Arrange and Act
    LegacyEndpointConverter actualHealthResult = LegacyEndpointConverters.health();

    // Assert
    assertFalse(actualHealthResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualHealthResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LegacyEndpointConverters#env()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#env()}
   */
  @Test
  @DisplayName("Test env()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.env()"})
  void testEnv() throws AssertionError {
    // Arrange and Act
    LegacyEndpointConverter actualEnvResult = LegacyEndpointConverters.env();

    // Assert
    assertFalse(actualEnvResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualEnvResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LegacyEndpointConverters#httptrace()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#httptrace()}
   */
  @Test
  @DisplayName("Test httptrace()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.httptrace()"})
  void testHttptrace() throws AssertionError {
    // Arrange and Act
    LegacyEndpointConverter actualHttptraceResult = LegacyEndpointConverters.httptrace();

    // Assert
    assertFalse(actualHttptraceResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualHttptraceResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LegacyEndpointConverters#threaddump()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#threaddump()}
   */
  @Test
  @DisplayName("Test threaddump()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.threaddump()"})
  void testThreaddump() throws AssertionError {
    // Arrange and Act
    LegacyEndpointConverter actualThreaddumpResult = LegacyEndpointConverters.threaddump();

    // Assert
    assertFalse(actualThreaddumpResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualThreaddumpResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LegacyEndpointConverters#liquibase()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#liquibase()}
   */
  @Test
  @DisplayName("Test liquibase()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.liquibase()"})
  void testLiquibase() throws AssertionError {
    // Arrange and Act
    LegacyEndpointConverter actualLiquibaseResult = LegacyEndpointConverters.liquibase();

    // Assert
    assertFalse(actualLiquibaseResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualLiquibaseResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LegacyEndpointConverters#flyway()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#flyway()}
   */
  @Test
  @DisplayName("Test flyway()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.flyway()"})
  void testFlyway() throws AssertionError {
    // Arrange and Act
    LegacyEndpointConverter actualFlywayResult = LegacyEndpointConverters.flyway();

    // Assert
    assertFalse(actualFlywayResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualFlywayResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LegacyEndpointConverters#info()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#info()}
   */
  @Test
  @DisplayName("Test info()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.info()"})
  void testInfo() {
    // Arrange and Act
    LegacyEndpointConverter actualInfoResult = LegacyEndpointConverters.info();

    // Assert
    assertNull(actualInfoResult.convert(null));
    assertFalse(actualInfoResult.canConvert("Endpoint Id"));
  }

  /**
   * Test {@link LegacyEndpointConverters#beans()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#beans()}
   */
  @Test
  @DisplayName("Test beans()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.beans()"})
  void testBeans() throws AssertionError {
    // Arrange and Act
    LegacyEndpointConverter actualBeansResult = LegacyEndpointConverters.beans();

    // Assert
    assertFalse(actualBeansResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualBeansResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LegacyEndpointConverters#configprops()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#configprops()}
   */
  @Test
  @DisplayName("Test configprops()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.configprops()"})
  void testConfigprops() throws AssertionError {
    // Arrange and Act
    LegacyEndpointConverter actualConfigpropsResult = LegacyEndpointConverters.configprops();

    // Assert
    assertFalse(actualConfigpropsResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualConfigpropsResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LegacyEndpointConverters#mappings()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#mappings()}
   */
  @Test
  @DisplayName("Test mappings()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.mappings()"})
  void testMappings() throws AssertionError {
    // Arrange and Act
    LegacyEndpointConverter actualMappingsResult = LegacyEndpointConverters.mappings();

    // Assert
    assertFalse(actualMappingsResult.canConvert("Endpoint Id"));
    FirstStep<DataBuffer> createResult = StepVerifier.create(actualMappingsResult.convert(null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LegacyEndpointConverters#startup()}.
   *
   * <p>Method under test: {@link LegacyEndpointConverters#startup()}
   */
  @Test
  @DisplayName("Test startup()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LegacyEndpointConverter LegacyEndpointConverters.startup()"})
  void testStartup() {
    // Arrange and Act
    LegacyEndpointConverter actualStartupResult = LegacyEndpointConverters.startup();

    // Assert
    assertNull(actualStartupResult.convert(null));
    assertFalse(actualStartupResult.canConvert("Endpoint Id"));
  }
}
