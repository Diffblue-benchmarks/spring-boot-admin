package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class CustomCsrfFilterDiffblueTest {
  /**
   * Test {@link CustomCsrfFilter#doFilterInternal(HttpServletRequest, HttpServletResponse,
   * FilterChain)}.
   *
   * <ul>
   *   <li>Given {@link ServletException#ServletException()}.
   *   <li>Then throw {@link ServletException}.
   * </ul>
   *
   * <p>Method under test: {@link CustomCsrfFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given ServletException(); then throw ServletException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CustomCsrfFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenServletException_thenThrowServletException()
      throws ServletException, IOException {
    // Arrange
    CustomCsrfFilter customCsrfFilter = new CustomCsrfFilter();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain filterChain = mock(FilterChain.class);
    doThrow(new ServletException())
        .when(filterChain)
        .doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act and Assert
    assertThrows(
        ServletException.class,
        () -> customCsrfFilter.doFilterInternal(request, response, filterChain));
    verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }

  /**
   * Test {@link CustomCsrfFilter#doFilterInternal(HttpServletRequest, HttpServletResponse,
   * FilterChain)}.
   *
   * <ul>
   *   <li>When {@link FilterChain} {@link FilterChain#doFilter(ServletRequest, ServletResponse)}
   *       does nothing.
   *   <li>Then calls {@link FilterChain#doFilter(ServletRequest, ServletResponse)}.
   * </ul>
   *
   * <p>Method under test: {@link CustomCsrfFilter#doFilterInternal(HttpServletRequest,
   * HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); when FilterChain doFilter(ServletRequest, ServletResponse) does nothing; then calls doFilter(ServletRequest, ServletResponse)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CustomCsrfFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_whenFilterChainDoFilterDoesNothing_thenCallsDoFilter()
      throws ServletException, IOException {
    // Arrange
    CustomCsrfFilter customCsrfFilter = new CustomCsrfFilter();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain filterChain = mock(FilterChain.class);
    doNothing()
        .when(filterChain)
        .doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    customCsrfFilter.doFilterInternal(request, response, filterChain);

    // Assert
    verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }
}
