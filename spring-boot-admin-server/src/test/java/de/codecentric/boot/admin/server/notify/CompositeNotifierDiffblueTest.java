package de.codecentric.boot.admin.server.notify;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {CompositeNotifier.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class CompositeNotifierDiffblueTest {
  @Autowired private CompositeNotifier compositeNotifier;

  @MockitoBean private Iterable<Notifier> iterable;

  /**
   * Test {@link CompositeNotifier#CompositeNotifier(Iterable)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CompositeNotifier#CompositeNotifier(Iterable)}
   */
  @Test
  @DisplayName("Test new CompositeNotifier(Iterable); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CompositeNotifier.<init>(Iterable)"})
  void testNewCompositeNotifier_whenArrayList() throws AssertionError {
    // Arrange and Act
    CompositeNotifier actualCompositeNotifier = new CompositeNotifier(new ArrayList<>());
    Mono<Void> actualPublisher =
        actualCompositeNotifier.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }
}
