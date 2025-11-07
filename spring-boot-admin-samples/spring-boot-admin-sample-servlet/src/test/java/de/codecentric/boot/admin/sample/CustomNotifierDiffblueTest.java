package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {CustomNotifier.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomNotifierDiffblueTest {
  @Autowired
  private CustomNotifier customNotifier;

  @MockBean
  private InstanceRepository instanceRepository;

  /**
   * Method under test: {@link CustomNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(customNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link CustomNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  void testDoNotify2() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(customNotifier.doNotify(mock(InstanceDeregisteredEvent.class), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link CustomNotifier#CustomNotifier(InstanceRepository)}
   */
  @Test
  void testNewCustomNotifier() {
    // Arrange, Act and Assert
    assertTrue((new CustomNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()))).isEnabled());
  }
}
