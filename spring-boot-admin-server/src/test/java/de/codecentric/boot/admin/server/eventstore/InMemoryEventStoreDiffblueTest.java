package de.codecentric.boot.admin.server.eventstore;

import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {InMemoryEventStore.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class InMemoryEventStoreDiffblueTest {
  @Autowired
  private InMemoryEventStore inMemoryEventStore;

  /**
   * Method under test: {@link InMemoryEventStore#InMemoryEventStore()}
   */
  @Test
  public void testNewInMemoryEventStore() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<InstanceEvent> createResult = StepVerifier.create((new InMemoryEventStore()).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link InMemoryEventStore#InMemoryEventStore(int)}
   */
  @Test
  public void testNewInMemoryEventStore2() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<InstanceEvent> createResult = StepVerifier.create((new InMemoryEventStore(3)).findAll());
    createResult.expectComplete().verify();
  }
}
