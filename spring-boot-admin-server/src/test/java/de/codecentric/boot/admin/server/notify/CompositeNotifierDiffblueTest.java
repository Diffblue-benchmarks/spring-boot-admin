package de.codecentric.boot.admin.server.notify;

import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {CompositeNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class CompositeNotifierDiffblueTest {
  @Autowired
  private CompositeNotifier compositeNotifier;

  @MockBean
  private Iterable<Notifier> iterable;

  /**
   * Method under test: {@link CompositeNotifier#CompositeNotifier(Iterable)}
   */
  @Test
  public void testNewCompositeNotifier() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    CompositeNotifier actualCompositeNotifier = new CompositeNotifier(new ArrayList<>());

    // Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(actualCompositeNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link CompositeNotifier#notify(InstanceEvent)}
   */
  @Test
  public void testNotify() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(compositeNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link CompositeNotifier#notify(InstanceEvent)}
   */
  @Test
  public void testNotify2() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(compositeNotifier.notify(mock(InstanceDeregisteredEvent.class)));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link CompositeNotifier#CompositeNotifier(Iterable)}
   */
  @Test
  public void testNewCompositeNotifier2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<Notifier> delegates = new ArrayList<>();
    delegates.add(mock(Notifier.class));

    // Act
    CompositeNotifier actualCompositeNotifier = new CompositeNotifier(delegates);

    // Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(actualCompositeNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectError().verify();
  }
}
