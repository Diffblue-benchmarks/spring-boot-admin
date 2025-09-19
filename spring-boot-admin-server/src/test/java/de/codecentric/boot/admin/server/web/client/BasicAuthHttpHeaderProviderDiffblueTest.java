package de.codecentric.boot.admin.server.web.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BasicAuthHttpHeaderProvider.class})
@ExtendWith(SpringExtension.class)
class BasicAuthHttpHeaderProviderDiffblueTest {
  @Autowired private BasicAuthHttpHeaderProvider basicAuthHttpHeaderProvider;

  /**
   * Test {@link BasicAuthHttpHeaderProvider#BasicAuthHttpHeaderProvider()}.
   *
   * <ul>
   *   <li>Then return Headers is {@link Instance} Empty.
   * </ul>
   *
   * <p>Method under test: {@link BasicAuthHttpHeaderProvider#BasicAuthHttpHeaderProvider()}
   */
  @Test
  @DisplayName("Test new BasicAuthHttpHeaderProvider(); then return Headers is Instance Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BasicAuthHttpHeaderProvider.<init>()"})
  void testNewBasicAuthHttpHeaderProvider_thenReturnHeadersIsInstanceEmpty() {
    // Arrange and Act
    BasicAuthHttpHeaderProvider actualBasicAuthHttpHeaderProvider =
        new BasicAuthHttpHeaderProvider();
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
    HttpHeaders actualHeaders = actualBasicAuthHttpHeaderProvider.getHeaders(instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    assertTrue(actualHeaders.isEmpty());
  }

  /**
   * Test {@link BasicAuthHttpHeaderProvider#getHeaders(Instance)}.
   *
   * <p>Method under test: {@link BasicAuthHttpHeaderProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders BasicAuthHttpHeaderProvider.getHeaders(Instance)"})
  void testGetHeaders() {
    // Arrange
    BasicAuthHttpHeaderProvider basicAuthHttpHeaderProvider =
        new BasicAuthHttpHeaderProvider("janedoe", null, new HashMap<>());

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
    HttpHeaders actualHeaders = basicAuthHttpHeaderProvider.getHeaders(instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    assertTrue(actualHeaders.isEmpty());
  }

  /**
   * Test {@link BasicAuthHttpHeaderProvider#getHeaders(Instance)}.
   *
   * <ul>
   *   <li>Given {@link BasicAuthHttpHeaderProvider}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BasicAuthHttpHeaderProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance); given BasicAuthHttpHeaderProvider; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders BasicAuthHttpHeaderProvider.getHeaders(Instance)"})
  void testGetHeaders_givenBasicAuthHttpHeaderProvider_thenReturnEmpty() {
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
    HttpHeaders actualHeaders = basicAuthHttpHeaderProvider.getHeaders(instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    assertTrue(actualHeaders.isEmpty());
  }

  /**
   * Test {@link BasicAuthHttpHeaderProvider#getHeaders(Instance)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link BasicAuthHttpHeaderProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders BasicAuthHttpHeaderProvider.getHeaders(Instance)"})
  void testGetHeaders_thenReturnSizeIsOne() {
    // Arrange
    BasicAuthHttpHeaderProvider basicAuthHttpHeaderProvider =
        new BasicAuthHttpHeaderProvider("janedoe", "https://example.org/example", new HashMap<>());

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
    HttpHeaders actualHeaders = basicAuthHttpHeaderProvider.getHeaders(instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    assertEquals(1, actualHeaders.size());
    List<String> getResult = actualHeaders.get(HttpHeaders.AUTHORIZATION);
    assertEquals(1, getResult.size());
    assertEquals("Basic amFuZWRvZTpodHRwczovL2V4YW1wbGUub3JnL2V4YW1wbGU=", getResult.get(0));
  }

  /**
   * Test {@link BasicAuthHttpHeaderProvider#encode(String, String)}.
   *
   * <p>Method under test: {@link BasicAuthHttpHeaderProvider#encode(String, String)}
   */
  @Test
  @DisplayName("Test encode(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BasicAuthHttpHeaderProvider.encode(String, String)"})
  void testEncode() {
    // Arrange, Act and Assert
    assertEquals(
        "Basic amFuZWRvZTpodHRwczovL2V4YW1wbGUub3JnL2V4YW1wbGU=",
        basicAuthHttpHeaderProvider.encode("janedoe", "https://example.org/example"));
  }
}
