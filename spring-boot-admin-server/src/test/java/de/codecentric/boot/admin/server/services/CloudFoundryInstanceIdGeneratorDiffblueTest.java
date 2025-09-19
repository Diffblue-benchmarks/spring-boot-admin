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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CloudFoundryInstanceIdGeneratorDiffblueTest {
  @InjectMocks private CloudFoundryInstanceIdGenerator cloudFoundryInstanceIdGenerator;

  @Mock private InstanceIdGenerator instanceIdGenerator;

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
    InstanceId ofResult = InstanceId.of("42");
    when(instanceIdGenerator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("applicationId", "Metadata");

    Registration registration = mock(Registration.class);
    when(registration.getMetadata()).thenReturn(stringStringMap);

    // Act
    InstanceId actualGenerateIdResult = cloudFoundryInstanceIdGenerator.generateId(registration);

    // Assert
    verify(registration, atLeast(1)).getMetadata();
    verify(instanceIdGenerator).generateId(isA(Registration.class));
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
    InstanceId ofResult = InstanceId.of("42");
    when(instanceIdGenerator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("instanceId", "Metadata");

    Registration registration = mock(Registration.class);
    when(registration.getMetadata()).thenReturn(stringStringMap);

    // Act
    InstanceId actualGenerateIdResult = cloudFoundryInstanceIdGenerator.generateId(registration);

    // Assert
    verify(registration, atLeast(1)).getMetadata();
    verify(instanceIdGenerator).generateId(isA(Registration.class));
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

  /**
   * Test {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}.
   *
   * <ul>
   *   <li>Then return Value is {@code applicationId:Metadata}.
   * </ul>
   *
   * <p>Method under test: {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}
   */
  @Test
  @DisplayName("Test generateId(Registration); then return Value is 'applicationId:Metadata'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceId CloudFoundryInstanceIdGenerator.generateId(Registration)"})
  void testGenerateId_thenReturnValueIsApplicationIdMetadata() {
    // Arrange
    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("applicationId", "applicationId");
    stringStringMap.put("instanceId", "Metadata");

    Registration registration = mock(Registration.class);
    when(registration.getMetadata()).thenReturn(stringStringMap);

    // Act
    InstanceId actualGenerateIdResult = cloudFoundryInstanceIdGenerator.generateId(registration);

    // Assert
    verify(registration, atLeast(1)).getMetadata();
    assertEquals("applicationId:Metadata", actualGenerateIdResult.getValue());
    assertEquals("applicationId:Metadata", actualGenerateIdResult.toString());
  }
}
