package de.codecentric.boot.admin.server.ui.web.servlet;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.ui.web.HomepageForwardingFilterConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

class HomepageForwardingFilterDiffblueTest {
  /**
   * Test {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}.
   * <ul>
   *   <li>When {@link MockHttpServletRequest#MockHttpServletRequest()}.</li>
   *   <li>Then calls {@link FilterChain#doFilter(ServletRequest, ServletResponse)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
   */
  @Test
  @DisplayName("Test doFilter(ServletRequest, ServletResponse, FilterChain); when MockHttpServletRequest(); then calls doFilter(ServletRequest, ServletResponse)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HomepageForwardingFilter.doFilter(ServletRequest, ServletResponse, FilterChain)"})
  void testDoFilter_whenMockHttpServletRequest_thenCallsDoFilter() throws ServletException, IOException {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilter homepageForwardingFilter = new HomepageForwardingFilter(
        new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>()));
    MockHttpServletRequest request = new MockHttpServletRequest();
    Response response = new Response();
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    homepageForwardingFilter.doFilter(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }

  /**
   * Test {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}.
   * <ul>
   *   <li>When {@link ServletRequest}.</li>
   *   <li>Then calls {@link FilterChain#doFilter(ServletRequest, ServletResponse)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
   */
  @Test
  @DisplayName("Test doFilter(ServletRequest, ServletResponse, FilterChain); when ServletRequest; then calls doFilter(ServletRequest, ServletResponse)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HomepageForwardingFilter.doFilter(ServletRequest, ServletResponse, FilterChain)"})
  void testDoFilter_whenServletRequest_thenCallsDoFilter() throws ServletException, IOException {
    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilter homepageForwardingFilter = new HomepageForwardingFilter(
        new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>()));
    ServletRequest request = mock(ServletRequest.class);
    Response response = new Response();
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    homepageForwardingFilter.doFilter(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }
}
