package de.codecentric.boot.admin.server.notify;

import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {CompositeNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class CompositeNotifierDiffblueTest {
  @Autowired
  private CompositeNotifier compositeNotifier;

  @MockBean
  private Iterable<Notifier> iterable;

  /**
   * Test {@link CompositeNotifier#CompositeNotifier(Iterable)}.
   * <p>
   * Method under test: {@link CompositeNotifier#CompositeNotifier(Iterable)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CompositeNotifier.<init>(Iterable)"})
  public void testNewCompositeNotifier() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    CompositeNotifier actualCompositeNotifier = new CompositeNotifier(new ArrayList<>());

    // Assert
    FirstStep<Void> createResult = StepVerifier
        .create(actualCompositeNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link CompositeNotifier#notify(InstanceEvent)} with {@code InstanceEvent}.
   * <p>
   * Method under test: {@link CompositeNotifier#notify(InstanceEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono CompositeNotifier.notify(InstanceEvent)"})
  public void testNotifyWithInstanceEvent() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(compositeNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectError().verify();
  }
}
