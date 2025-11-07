package de.codecentric.boot.admin.client.registration;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.client.RestTemplate;

class RegistrationApplicationListenerDiffblueTest {
  /**
   * Method under test:
   * {@link RegistrationApplicationListener#onClosedContext(ContextClosedEvent)}
   */
  @Test
  void testOnClosedContext() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    RegistrationApplicationListener registrationApplicationListener = new RegistrationApplicationListener(
        new DefaultApplicationRegistrator(applicationFactory, new BlockingRegistrationClient(mock(RestTemplate.class)),
            new String[]{"https://example.org/example"}, true));
    AnnotationConfigApplicationContext source = mock(AnnotationConfigApplicationContext.class);
    when(source.getParent()).thenReturn(new AnnotationConfigReactiveWebApplicationContext());

    // Act
    registrationApplicationListener.onClosedContext(new ContextClosedEvent(source));

    // Assert that nothing has changed
    verify(source, atLeast(1)).getParent();
  }
}
