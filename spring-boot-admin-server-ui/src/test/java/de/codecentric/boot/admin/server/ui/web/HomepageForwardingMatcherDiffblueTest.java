package de.codecentric.boot.admin.server.ui.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

class HomepageForwardingMatcherDiffblueTest {
  /**
   * Method under test:
   * {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  void testNewHomepageForwardingMatcher() {
    // Arrange
    ArrayList<String> includeRoutes = new ArrayList<>();
    ArrayList<String> excludeRoutes = new ArrayList<>();
    Function<Object, String> methodAccessor = mock(Function.class);
    when(methodAccessor.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act
    HomepageForwardingMatcher<Object> actualHomepageForwardingMatcher = new HomepageForwardingMatcher<>(includeRoutes,
        excludeRoutes, methodAccessor, mock(Function.class), mock(Function.class));
    boolean actualTestResult = actualHomepageForwardingMatcher.test("Request");

    // Assert
    verify(methodAccessor).apply(isA(Object.class));
    assertFalse(actualTestResult);
  }

  /**
   * Method under test:
   * {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  void testNewHomepageForwardingMatcher2() {
    // Arrange
    ArrayList<String> includeRoutes = new ArrayList<>();
    includeRoutes.add("foo");
    ArrayList<String> excludeRoutes = new ArrayList<>();
    Function<Object, String> methodAccessor = mock(Function.class);
    when(methodAccessor.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act
    HomepageForwardingMatcher<Object> actualHomepageForwardingMatcher = new HomepageForwardingMatcher<>(includeRoutes,
        excludeRoutes, methodAccessor, mock(Function.class), mock(Function.class));
    boolean actualTestResult = actualHomepageForwardingMatcher.test("Request");

    // Assert
    verify(methodAccessor).apply(isA(Object.class));
    assertFalse(actualTestResult);
  }

  /**
   * Method under test:
   * {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  void testNewHomepageForwardingMatcher3() {
    // Arrange
    ArrayList<String> includeRoutes = new ArrayList<>();
    includeRoutes.add("42");
    includeRoutes.add("foo");
    ArrayList<String> excludeRoutes = new ArrayList<>();
    Function<Object, String> methodAccessor = mock(Function.class);
    when(methodAccessor.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act
    HomepageForwardingMatcher<Object> actualHomepageForwardingMatcher = new HomepageForwardingMatcher<>(includeRoutes,
        excludeRoutes, methodAccessor, mock(Function.class), mock(Function.class));
    boolean actualTestResult = actualHomepageForwardingMatcher.test("Request");

    // Assert
    verify(methodAccessor).apply(isA(Object.class));
    assertFalse(actualTestResult);
  }

  /**
   * Method under test:
   * {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  void testNewHomepageForwardingMatcher4() {
    // Arrange
    ArrayList<String> includeRoutes = new ArrayList<>();

    ArrayList<String> excludeRoutes = new ArrayList<>();
    excludeRoutes.add("foo");
    Function<Object, String> methodAccessor = mock(Function.class);
    when(methodAccessor.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act
    HomepageForwardingMatcher<Object> actualHomepageForwardingMatcher = new HomepageForwardingMatcher<>(includeRoutes,
        excludeRoutes, methodAccessor, mock(Function.class), mock(Function.class));
    boolean actualTestResult = actualHomepageForwardingMatcher.test("Request");

    // Assert
    verify(methodAccessor).apply(isA(Object.class));
    assertFalse(actualTestResult);
  }

  /**
   * Method under test:
   * {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  void testNewHomepageForwardingMatcher5() {
    // Arrange
    ArrayList<String> includeRoutes = new ArrayList<>();

    ArrayList<String> excludeRoutes = new ArrayList<>();
    excludeRoutes.add("42");
    excludeRoutes.add("foo");
    Function<Object, String> methodAccessor = mock(Function.class);
    when(methodAccessor.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act
    HomepageForwardingMatcher<Object> actualHomepageForwardingMatcher = new HomepageForwardingMatcher<>(includeRoutes,
        excludeRoutes, methodAccessor, mock(Function.class), mock(Function.class));
    boolean actualTestResult = actualHomepageForwardingMatcher.test("Request");

    // Assert
    verify(methodAccessor).apply(isA(Object.class));
    assertFalse(actualTestResult);
  }

  /**
   * Method under test: {@link HomepageForwardingMatcher#test(Object)}
   */
  @Test
  void testTest() {
    // Arrange
    Function<Object, String> methodAccessor = mock(Function.class);
    when(methodAccessor.apply(Mockito.<Object>any())).thenReturn("Apply");
    ArrayList<String> includeRoutes = new ArrayList<>();
    HomepageForwardingMatcher<Object> homepageForwardingMatcher = new HomepageForwardingMatcher<>(includeRoutes,
        new ArrayList<>(), methodAccessor, mock(Function.class), mock(Function.class));

    // Act
    boolean actualTestResult = homepageForwardingMatcher.test("Request");

    // Assert
    verify(methodAccessor).apply(isA(Object.class));
    assertFalse(actualTestResult);
  }
}
