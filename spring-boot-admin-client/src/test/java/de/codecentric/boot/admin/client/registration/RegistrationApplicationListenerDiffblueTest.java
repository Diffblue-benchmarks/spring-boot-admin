package de.codecentric.boot.admin.client.registration;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.client.RestTemplate;

class RegistrationApplicationListenerDiffblueTest {
  /**
   * Test {@link RegistrationApplicationListener#onClosedContext(ContextClosedEvent)}.
   *
   * <p>Method under test: {@link
   * RegistrationApplicationListener#onClosedContext(ContextClosedEvent)}
   */
  @Test
  @DisplayName("Test onClosedContext(ContextClosedEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RegistrationApplicationListener.onClosedContext(ContextClosedEvent)"})
  void testOnClosedContext() {
    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    BlockingRegistrationClient registrationClient =
        new BlockingRegistrationClient(mock(RestTemplate.class));
    String[] adminUrls = new String[] {"https://example.org/example"};

    DefaultApplicationRegistrator registrator =
        new DefaultApplicationRegistrator(applicationFactory, registrationClient, adminUrls, true);
    RegistrationApplicationListener registrationApplicationListener =
        new RegistrationApplicationListener(registrator);

    AnnotationConfigReactiveWebApplicationContext annotationConfigReactiveWebApplicationContext =
        new AnnotationConfigReactiveWebApplicationContext(new DefaultListableBeanFactory());
    annotationConfigReactiveWebApplicationContext.setId("bootstrap");

    ApplicationContext source = mock(ApplicationContext.class);
    when(source.getParent()).thenReturn(annotationConfigReactiveWebApplicationContext);

    // Act
    registrationApplicationListener.onClosedContext(new ContextClosedEvent(source));

    // Assert
    verify(source, atLeast(1)).getParent();
  }

  /**
   * Test {@link RegistrationApplicationListener#onClosedContext(ContextClosedEvent)}.
   *
   * <ul>
   *   <li>Given {@link ApplicationContext} {@link ApplicationContext#getId()} return {@code 42}.
   *   <li>Then calls {@link ApplicationContext#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RegistrationApplicationListener#onClosedContext(ContextClosedEvent)}
   */
  @Test
  @DisplayName(
      "Test onClosedContext(ContextClosedEvent); given ApplicationContext getId() return '42'; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RegistrationApplicationListener.onClosedContext(ContextClosedEvent)"})
  void testOnClosedContext_givenApplicationContextGetIdReturn42_thenCallsGetId() {
    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    BlockingRegistrationClient registrationClient =
        new BlockingRegistrationClient(mock(RestTemplate.class));
    String[] adminUrls = new String[] {"https://example.org/example"};

    DefaultApplicationRegistrator registrator =
        new DefaultApplicationRegistrator(applicationFactory, registrationClient, adminUrls, true);
    RegistrationApplicationListener registrationApplicationListener =
        new RegistrationApplicationListener(registrator);

    ApplicationContext applicationContext = mock(ApplicationContext.class);
    when(applicationContext.getId()).thenReturn("42");

    ApplicationContext source = mock(ApplicationContext.class);
    when(source.getParent()).thenReturn(applicationContext);

    // Act
    registrationApplicationListener.onClosedContext(new ContextClosedEvent(source));

    // Assert
    verify(applicationContext).getId();
    verify(source, atLeast(1)).getParent();
  }

  /**
   * Test {@link RegistrationApplicationListener#onClosedContext(ContextClosedEvent)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ApplicationContext} {@link ApplicationContext#getParent()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RegistrationApplicationListener#onClosedContext(ContextClosedEvent)}
   */
  @Test
  @DisplayName(
      "Test onClosedContext(ContextClosedEvent); given 'null'; when ApplicationContext getParent() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RegistrationApplicationListener.onClosedContext(ContextClosedEvent)"})
  void testOnClosedContext_givenNull_whenApplicationContextGetParentReturnNull() {
    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    BlockingRegistrationClient registrationClient =
        new BlockingRegistrationClient(mock(RestTemplate.class));
    String[] adminUrls = new String[] {"https://example.org/example"};

    DefaultApplicationRegistrator registrator =
        new DefaultApplicationRegistrator(applicationFactory, registrationClient, adminUrls, true);
    RegistrationApplicationListener registrationApplicationListener =
        new RegistrationApplicationListener(registrator);

    ApplicationContext source = mock(ApplicationContext.class);
    when(source.getParent()).thenReturn(null);

    // Act
    registrationApplicationListener.onClosedContext(new ContextClosedEvent(source));

    // Assert
    verify(source).getParent();
  }
}
