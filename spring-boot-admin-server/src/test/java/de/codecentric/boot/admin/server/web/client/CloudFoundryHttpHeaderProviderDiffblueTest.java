package de.codecentric.boot.admin.server.web.client;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.Registration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CloudFoundryHttpHeaderProvider.class})
@ExtendWith(SpringExtension.class)
class CloudFoundryHttpHeaderProviderDiffblueTest {
  @Autowired private CloudFoundryHttpHeaderProvider cloudFoundryHttpHeaderProvider;

  /**
   * Test {@link CloudFoundryHttpHeaderProvider#getHeaders(Instance)}.
   *
   * <p>Method under test: {@link CloudFoundryHttpHeaderProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders CloudFoundryHttpHeaderProvider.getHeaders(Instance)"})
  void testGetHeaders() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act
    HttpHeaders actualHeaders = cloudFoundryHttpHeaderProvider.getHeaders(instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    assertTrue(actualHeaders.isEmpty());
    assertSame(HttpHeaders.EMPTY, actualHeaders);
  }
}
