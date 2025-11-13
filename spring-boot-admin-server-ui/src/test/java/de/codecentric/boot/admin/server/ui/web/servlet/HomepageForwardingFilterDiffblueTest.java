package de.codecentric.boot.admin.server.ui.web.servlet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.ui.web.HomepageForwardingFilterConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class HomepageForwardingFilterDiffblueTest {
  /**
   * Test {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given {@link IOException#IOException()}.
   *   <li>Then throw {@link IOException}.
   * </ul>
   *
   * <p>Method under test: {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse,
   * FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilter(ServletRequest, ServletResponse, FilterChain); given IOException(); then throw IOException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HomepageForwardingFilter.doFilter(ServletRequest, ServletResponse, FilterChain)"
  })
  void testDoFilter_givenIOException_thenThrowIOException() throws ServletException, IOException {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilterConfig filterConfig =
        new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>());
    HomepageForwardingFilter homepageForwardingFilter = new HomepageForwardingFilter(filterConfig);
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain chain = mock(FilterChain.class);
    doThrow(new IOException())
        .when(chain)
        .doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act and Assert
    assertThrows(
        IOException.class, () -> homepageForwardingFilter.doFilter(request, response, chain));
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }

  /**
   * Test {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>When {@link FilterChain} {@link FilterChain#doFilter(ServletRequest, ServletResponse)}
   *       does nothing.
   * </ul>
   *
   * <p>Method under test: {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse,
   * FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilter(ServletRequest, ServletResponse, FilterChain); when FilterChain doFilter(ServletRequest, ServletResponse) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HomepageForwardingFilter.doFilter(ServletRequest, ServletResponse, FilterChain)"
  })
  void testDoFilter_whenFilterChainDoFilterDoesNothing() throws ServletException, IOException {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilterConfig filterConfig =
        new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>());
    HomepageForwardingFilter homepageForwardingFilter = new HomepageForwardingFilter(filterConfig);
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    homepageForwardingFilter.doFilter(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }
}
