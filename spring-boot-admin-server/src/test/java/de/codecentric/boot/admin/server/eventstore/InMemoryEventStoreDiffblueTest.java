package de.codecentric.boot.admin.server.eventstore;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {InMemoryEventStore.class})
@ExtendWith(SpringExtension.class)
class InMemoryEventStoreDiffblueTest {
  @Autowired private InMemoryEventStore inMemoryEventStore;

  /**
   * Test {@link InMemoryEventStore#InMemoryEventStore()}.
   *
   * <p>Method under test: {@link InMemoryEventStore#InMemoryEventStore()}
   */
  @Test
  @DisplayName("Test new InMemoryEventStore()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InMemoryEventStore.<init>()"})
  void testNewInMemoryEventStore() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<InstanceEvent> createResult = StepVerifier.create(new InMemoryEventStore().findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InMemoryEventStore#InMemoryEventStore(int)}.
   *
   * <p>Method under test: {@link InMemoryEventStore#InMemoryEventStore(int)}
   */
  @Test
  @DisplayName("Test new InMemoryEventStore(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void InMemoryEventStore.<init>(int)"})
  void testNewInMemoryEventStore2() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<InstanceEvent> createResult =
        StepVerifier.create(new InMemoryEventStore(3).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link InMemoryEventStore#append(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link InMemoryEventStore#append(List)}
   */
  @Test
  @DisplayName("Test append(List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono InMemoryEventStore.append(List)"})
  void testAppend_whenArrayList() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(inMemoryEventStore.append(new ArrayList<>()));
    createResult.expectComplete().verify();
  }
}
