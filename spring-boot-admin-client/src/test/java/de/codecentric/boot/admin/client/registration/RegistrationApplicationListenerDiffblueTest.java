package de.codecentric.boot.admin.client.registration;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.support.AbstractApplicationContext;

@ExtendWith(MockitoExtension.class)
class RegistrationApplicationListenerDiffblueTest {
  @InjectMocks
  private RegistrationApplicationListener registrationApplicationListener;

  /**
   * Test {@link RegistrationApplicationListener#onClosedContext(ContextClosedEvent)}.
   * <ul>
   *   <li>Then calls {@link AbstractApplicationContext#getParent()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RegistrationApplicationListener#onClosedContext(ContextClosedEvent)}
   */
  @Test
  @DisplayName("Test onClosedContext(ContextClosedEvent); then calls getParent()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RegistrationApplicationListener.onClosedContext(ContextClosedEvent)"})
  void testOnClosedContext_thenCallsGetParent() {
    // Arrange
    AnnotationConfigApplicationContext source = mock(AnnotationConfigApplicationContext.class);
    when(source.getParent()).thenReturn(new AnnotationConfigReactiveWebApplicationContext());

    // Act
    registrationApplicationListener.onClosedContext(new ContextClosedEvent(source));

    // Assert
    verify(source, atLeast(1)).getParent();
  }
}
