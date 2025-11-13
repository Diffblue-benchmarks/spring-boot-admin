package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class ApiMediaTypeHandlerDiffblueTest {
  /**
   * Test {@link ApiMediaTypeHandler#isApiMediaType(MediaType)}.
   *
   * <ul>
   *   <li>When parseMediaType {@link MediaType#TEXT_PLAIN_VALUE}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ApiMediaTypeHandler#isApiMediaType(MediaType)}
   */
  @Test
  @DisplayName(
      "Test isApiMediaType(MediaType); when parseMediaType TEXT_PLAIN_VALUE; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ApiMediaTypeHandler.isApiMediaType(MediaType)"})
  void testIsApiMediaType_whenParseMediaTypeText_plain_value_thenReturnFalse() {
    // Arrange
    ApiMediaTypeHandler apiMediaTypeHandler = new ApiMediaTypeHandler();

    // Act and Assert
    assertFalse(
        apiMediaTypeHandler.isApiMediaType(MediaType.parseMediaType(MediaType.TEXT_PLAIN_VALUE)));
  }
}
