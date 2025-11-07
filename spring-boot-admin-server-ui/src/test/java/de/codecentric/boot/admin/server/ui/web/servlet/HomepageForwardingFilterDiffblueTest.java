package de.codecentric.boot.admin.server.ui.web.servlet;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import de.codecentric.boot.admin.server.ui.web.HomepageForwardingFilterConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

class HomepageForwardingFilterDiffblueTest {
  /**
   * Method under test:
   * {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
   */
  @Test
  void testDoFilter() throws ServletException, IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link HomepageForwardingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
   */
  @Test
  void testDoFilter2() throws ServletException, IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<String> routesIncludes = new ArrayList<>();
    HomepageForwardingFilter homepageForwardingFilter = new HomepageForwardingFilter(
        new HomepageForwardingFilterConfig("Homepage", routesIncludes, new ArrayList<>()));
    Response response = new Response();
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    homepageForwardingFilter.doFilter(null, response, chain);

    // Assert
    verify(chain).doFilter(isNull(), isA(ServletResponse.class));
  }
}
