package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertFalse;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.NotificationTrigger;
import de.codecentric.boot.admin.server.notify.Notifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {NotificationTrigger.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class AbstractEventHandlerDiffblueTest {
  @Autowired
  private AbstractEventHandler<InstanceEvent> abstractEventHandler;

  @MockBean
  private Notifier notifier;

  @MockBean
  private Publisher<InstanceEvent> publisher;

  /**
   * Method under test: {@link AbstractEventHandler#createScheduler()}
   */
  @Test
  public void testCreateScheduler() {
    // Arrange, Act and Assert
    assertFalse(abstractEventHandler.createScheduler().isDisposed());
  }
}
