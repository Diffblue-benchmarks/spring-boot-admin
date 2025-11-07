package de.codecentric.boot.admin.server.ui.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HomepageForwardingMatcherDiffblueTest {
  /**
   * Test {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  @DisplayName("Test new HomepageForwardingMatcher(List, List, Function, Function, Function); given '42'; when ArrayList() add '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HomepageForwardingMatcher.<init>(List, List, Function, Function, Function)"})
  void testNewHomepageForwardingMatcher_given42_whenArrayListAdd42() {
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
   * Test {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  @DisplayName("Test new HomepageForwardingMatcher(List, List, Function, Function, Function); given '42'; when ArrayList() add '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HomepageForwardingMatcher.<init>(List, List, Function, Function, Function)"})
  void testNewHomepageForwardingMatcher_given42_whenArrayListAdd422() {
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
   * Test {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  @DisplayName("Test new HomepageForwardingMatcher(List, List, Function, Function, Function); given 'foo'; when ArrayList() add 'foo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HomepageForwardingMatcher.<init>(List, List, Function, Function, Function)"})
  void testNewHomepageForwardingMatcher_givenFoo_whenArrayListAddFoo() {
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
   * Test {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  @DisplayName("Test new HomepageForwardingMatcher(List, List, Function, Function, Function); given 'foo'; when ArrayList() add 'foo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HomepageForwardingMatcher.<init>(List, List, Function, Function, Function)"})
  void testNewHomepageForwardingMatcher_givenFoo_whenArrayListAddFoo2() {
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
   * Test {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}.
   * <ul>
   *   <li>When {@link Function} {@link Function#apply(Object)} return {@code Apply}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HomepageForwardingMatcher#HomepageForwardingMatcher(List, List, Function, Function, Function)}
   */
  @Test
  @DisplayName("Test new HomepageForwardingMatcher(List, List, Function, Function, Function); when Function apply(Object) return 'Apply'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HomepageForwardingMatcher.<init>(List, List, Function, Function, Function)"})
  void testNewHomepageForwardingMatcher_whenFunctionApplyReturnApply() {
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
   * Test {@link HomepageForwardingMatcher#test(Object)}.
   * <p>
   * Method under test: {@link HomepageForwardingMatcher#test(Object)}
   */
  @Test
  @DisplayName("Test test(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean HomepageForwardingMatcher.test(Object)"})
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
