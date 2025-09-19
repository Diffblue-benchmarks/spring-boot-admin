package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Registration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RegistrationDeserializerDiffblueTest {
  /**
   * Test new {@link RegistrationDeserializer} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link RegistrationDeserializer}
   */
  @Test
  @DisplayName("Test new RegistrationDeserializer (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RegistrationDeserializer.<init>()"})
  void testNewRegistrationDeserializer() {
    // Arrange and Act
    RegistrationDeserializer actualRegistrationDeserializer = new RegistrationDeserializer();

    // Assert
    assertNull(actualRegistrationDeserializer.getValueType());
    Class<Registration> expectedValueClass = Registration.class;
    assertEquals(expectedValueClass, actualRegistrationDeserializer.getValueClass());
  }
}
