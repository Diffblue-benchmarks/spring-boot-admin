package de.codecentric.boot.admin.server.web.client;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

class CompositeHttpHeadersProviderDiffblueTest {
  /**
   * Test {@link CompositeHttpHeadersProvider#getHeaders(Instance)}.
   *
   * <p>Method under test: {@link CompositeHttpHeadersProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders CompositeHttpHeadersProvider.getHeaders(Instance)"})
  void testGetHeaders() {
    // Arrange, Act and Assert
    assertTrue(
        new CompositeHttpHeadersProvider(new ArrayList<>())
            .getHeaders(mock(Instance.class))
            .isEmpty());
  }

  /**
   * Test {@link CompositeHttpHeadersProvider#getHeaders(Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link HttpHeadersProvider#getHeaders(Instance)}.
   * </ul>
   *
   * <p>Method under test: {@link CompositeHttpHeadersProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance); then calls getHeaders(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders CompositeHttpHeadersProvider.getHeaders(Instance)"})
  void testGetHeaders_thenCallsGetHeaders() {
    // Arrange
    HttpHeadersProvider httpHeadersProvider = mock(HttpHeadersProvider.class);
    when(httpHeadersProvider.getHeaders(Mockito.<Instance>any())).thenReturn(new HttpHeaders());

    ArrayList<HttpHeadersProvider> delegates = new ArrayList<>();
    delegates.add(httpHeadersProvider);

    // Act
    HttpHeaders actualHeaders =
        new CompositeHttpHeadersProvider(delegates).getHeaders(mock(Instance.class));

    // Assert
    verify(httpHeadersProvider).getHeaders(isA(Instance.class));
    assertTrue(actualHeaders.isEmpty());
  }

  /**
   * Test {@link CompositeHttpHeadersProvider#getHeaders(Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link HttpHeadersProvider#getHeaders(Instance)}.
   * </ul>
   *
   * <p>Method under test: {@link CompositeHttpHeadersProvider#getHeaders(Instance)}
   */
  @Test
  @DisplayName("Test getHeaders(Instance); then calls getHeaders(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpHeaders CompositeHttpHeadersProvider.getHeaders(Instance)"})
  void testGetHeaders_thenCallsGetHeaders2() {
    // Arrange
    HttpHeadersProvider httpHeadersProvider = mock(HttpHeadersProvider.class);
    when(httpHeadersProvider.getHeaders(Mockito.<Instance>any())).thenReturn(new HttpHeaders());

    HttpHeadersProvider httpHeadersProvider2 = mock(HttpHeadersProvider.class);
    when(httpHeadersProvider2.getHeaders(Mockito.<Instance>any())).thenReturn(new HttpHeaders());

    ArrayList<HttpHeadersProvider> delegates = new ArrayList<>();
    delegates.add(httpHeadersProvider2);
    delegates.add(httpHeadersProvider);

    // Act
    HttpHeaders actualHeaders =
        new CompositeHttpHeadersProvider(delegates).getHeaders(mock(Instance.class));

    // Assert
    verify(httpHeadersProvider2).getHeaders(isA(Instance.class));
    verify(httpHeadersProvider).getHeaders(isA(Instance.class));
    assertTrue(actualHeaders.isEmpty());
  }
}
