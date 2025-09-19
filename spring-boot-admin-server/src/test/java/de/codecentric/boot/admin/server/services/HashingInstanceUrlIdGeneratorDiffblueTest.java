package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {HashingInstanceUrlIdGenerator.class})
@ExtendWith(SpringExtension.class)
class HashingInstanceUrlIdGeneratorDiffblueTest {
  @Autowired private HashingInstanceUrlIdGenerator hashingInstanceUrlIdGenerator;

  /**
   * Test {@link HashingInstanceUrlIdGenerator#generateId(Registration)}.
   *
   * <ul>
   *   <li>Then return Value is {@code 504149e8a3fa}.
   * </ul>
   *
   * <p>Method under test: {@link HashingInstanceUrlIdGenerator#generateId(Registration)}
   */
  @Test
  @DisplayName("Test generateId(Registration); then return Value is '504149e8a3fa'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceId HashingInstanceUrlIdGenerator.generateId(Registration)"})
  void testGenerateId_thenReturnValueIs504149e8a3fa() {
    // Arrange and Act
    InstanceId actualGenerateIdResult =
        hashingInstanceUrlIdGenerator.generateId(
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
