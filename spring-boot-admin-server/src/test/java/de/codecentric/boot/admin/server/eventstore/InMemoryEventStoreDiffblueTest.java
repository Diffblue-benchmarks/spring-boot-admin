package de.codecentric.boot.admin.server.eventstore;

import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InMemoryEventStore.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class InMemoryEventStoreDiffblueTest {
  @Autowired
  private InMemoryEventStore inMemoryEventStore;

  /**
   * Test {@link InMemoryEventStore#InMemoryEventStore()}.
   * <p>
   * Method under test: {@link InMemoryEventStore#InMemoryEventStore()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InMemoryEventStore.<init>()"})
  public void testNewInMemoryEventStore() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<InstanceEvent> createResult = StepVerifier.create((new InMemoryEventStore()).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InMemoryEventStore#InMemoryEventStore(int)}.
   * <p>
   * Method under test: {@link InMemoryEventStore#InMemoryEventStore(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void InMemoryEventStore.<init>(int)"})
  public void testNewInMemoryEventStore2() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<InstanceEvent> createResult = StepVerifier.create((new InMemoryEventStore(3)).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InMemoryEventStore#append(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InMemoryEventStore#append(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono InMemoryEventStore.append(List)"})
  public void testAppend_whenArrayList() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult = StepVerifier.create(inMemoryEventStore.append(new ArrayList<>()));
    createResult.expectComplete().verify();
  }
}
