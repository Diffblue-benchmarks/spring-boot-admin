package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CloudFoundryInstanceIdGeneratorDiffblueTest {
  /**
   * Test {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}.
   *
   * <p>Method under test: {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}
   */
  @Test
  @DisplayName("Test generateId(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceId CloudFoundryInstanceIdGenerator.generateId(Registration)"})
  void testGenerateId() {
    // Arrange
    InstanceIdGenerator fallbackIdGenerator = mock(InstanceIdGenerator.class);
    InstanceId ofResult = InstanceId.of("42");
    when(fallbackIdGenerator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);
    CloudFoundryInstanceIdGenerator fallbackIdGenerator2 =
        new CloudFoundryInstanceIdGenerator(fallbackIdGenerator);
    CloudFoundryInstanceIdGenerator cloudFoundryInstanceIdGenerator =
        new CloudFoundryInstanceIdGenerator(fallbackIdGenerator2);

    // Act
    InstanceId actualGenerateIdResult =
        cloudFoundryInstanceIdGenerator.generateId(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Assert
    verify(fallbackIdGenerator).generateId(isA(Registration.class));
    assertSame(ofResult, actualGenerateIdResult);
  }

  /**
   * Test {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code applicationId} is {@code 42}.
   *   <li>Then return Value is {@code 42:Metadata}.
   * </ul>
   *
   * <p>Method under test: {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}
   */
  @Test
  @DisplayName(
      "Test generateId(Registration); given HashMap() 'applicationId' is '42'; then return Value is '42:Metadata'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceId CloudFoundryInstanceIdGenerator.generateId(Registration)"})
  void testGenerateId_givenHashMapApplicationIdIs42_thenReturnValueIs42Metadata() {
    // Arrange
    CloudFoundryInstanceIdGenerator cloudFoundryInstanceIdGenerator =
        new CloudFoundryInstanceIdGenerator(mock(InstanceIdGenerator.class));

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("applicationId", "42");
    stringStringMap.put("instanceId", "Metadata");

    Registration registration = mock(Registration.class);
    when(registration.getMetadata()).thenReturn(stringStringMap);

    // Act
    InstanceId actualGenerateIdResult = cloudFoundryInstanceIdGenerator.generateId(registration);

    // Assert
    verify(registration, atLeast(1)).getMetadata();
    assertEquals("42:Metadata", actualGenerateIdResult.getValue());
    assertEquals("42:Metadata", actualGenerateIdResult.toString());
  }

  /**
   * Test {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code applicationId} is {@code Metadata}.
   * </ul>
   *
   * <p>Method under test: {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}
   */
  @Test
  @DisplayName("Test generateId(Registration); given HashMap() 'applicationId' is 'Metadata'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceId CloudFoundryInstanceIdGenerator.generateId(Registration)"})
  void testGenerateId_givenHashMapApplicationIdIsMetadata() {
    // Arrange
    InstanceIdGenerator fallbackIdGenerator = mock(InstanceIdGenerator.class);
    InstanceId ofResult = InstanceId.of("42");
    when(fallbackIdGenerator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);
    CloudFoundryInstanceIdGenerator cloudFoundryInstanceIdGenerator =
        new CloudFoundryInstanceIdGenerator(fallbackIdGenerator);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("applicationId", "Metadata");

    Registration registration = mock(Registration.class);
    when(registration.getMetadata()).thenReturn(stringStringMap);

    // Act
    InstanceId actualGenerateIdResult = cloudFoundryInstanceIdGenerator.generateId(registration);

    // Assert
    verify(registration, atLeast(1)).getMetadata();
    verify(fallbackIdGenerator).generateId(isA(Registration.class));
    assertSame(ofResult, actualGenerateIdResult);
  }

  /**
   * Test {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code instanceId} is {@code Metadata}.
   * </ul>
   *
   * <p>Method under test: {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}
   */
  @Test
  @DisplayName("Test generateId(Registration); given HashMap() 'instanceId' is 'Metadata'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceId CloudFoundryInstanceIdGenerator.generateId(Registration)"})
  void testGenerateId_givenHashMapInstanceIdIsMetadata() {
    // Arrange
    InstanceIdGenerator fallbackIdGenerator = mock(InstanceIdGenerator.class);
    InstanceId ofResult = InstanceId.of("42");
    when(fallbackIdGenerator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);
    CloudFoundryInstanceIdGenerator cloudFoundryInstanceIdGenerator =
        new CloudFoundryInstanceIdGenerator(fallbackIdGenerator);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("instanceId", "Metadata");

    Registration registration = mock(Registration.class);
    when(registration.getMetadata()).thenReturn(stringStringMap);

    // Act
    InstanceId actualGenerateIdResult = cloudFoundryInstanceIdGenerator.generateId(registration);

    // Assert
    verify(registration, atLeast(1)).getMetadata();
    verify(fallbackIdGenerator).generateId(isA(Registration.class));
    assertSame(ofResult, actualGenerateIdResult);
  }

  /**
   * Test {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}.
   *
   * <ul>
   *   <li>Then return Value is {@code 504149e8a3fa}.
   * </ul>
   *
   * <p>Method under test: {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}
   */
  @Test
  @DisplayName("Test generateId(Registration); then return Value is '504149e8a3fa'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceId CloudFoundryInstanceIdGenerator.generateId(Registration)"})
  void testGenerateId_thenReturnValueIs504149e8a3fa() {
    // Arrange
    CloudFoundryInstanceIdGenerator cloudFoundryInstanceIdGenerator =
        new CloudFoundryInstanceIdGenerator(new HashingInstanceUrlIdGenerator());

    // Act
    InstanceId actualGenerateIdResult =
        cloudFoundryInstanceIdGenerator.generateId(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Assert
    assertEquals("504149e8a3fa", actualGenerateIdResult.getValue());
    assertEquals("504149e8a3fa", actualGenerateIdResult.toString());
  }
}
