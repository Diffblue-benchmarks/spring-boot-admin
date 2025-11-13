package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.SimpleObjectProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NotifierConfig.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class NotifierConfigDiffblueTest {
  @MockitoBean private FilteringNotifier filteringNotifier;

  @MockitoBean private InstanceRepository instanceRepository;

  @Autowired private NotifierConfig notifierConfig;

  @MockitoBean private RemindingNotifier remindingNotifier;

  /**
   * Test {@link NotifierConfig#filteringNotifier()}.
   *
   * <ul>
   *   <li>Then return Enabled.
   * </ul>
   *
   * <p>Method under test: {@link NotifierConfig#filteringNotifier()}
   */
  @Test
  @DisplayName("Test filteringNotifier(); then return Enabled")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"FilteringNotifier NotifierConfig.filteringNotifier()"})
  void testFilteringNotifier_thenReturnEnabled() {
    // Arrange and Act
    FilteringNotifier actualFilteringNotifierResult = notifierConfig.filteringNotifier();

    // Assert
    assertTrue(actualFilteringNotifierResult.isEnabled());
    assertTrue(actualFilteringNotifierResult.getNotificationFilters().isEmpty());
  }

  /**
   * Test {@link NotifierConfig#remindingNotifier()}.
   *
   * <ul>
   *   <li>Then return Enabled.
   * </ul>
   *
   * <p>Method under test: {@link NotifierConfig#remindingNotifier()}
   */
  @Test
  @DisplayName("Test remindingNotifier(); then return Enabled")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"RemindingNotifier NotifierConfig.remindingNotifier()"})
  void testRemindingNotifier_thenReturnEnabled() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());
    NotifierConfig notifierConfig =
        new NotifierConfig(repository, new SimpleObjectProvider<>(new ArrayList<>()));

    // Act and Assert
    assertTrue(notifierConfig.remindingNotifier().isEnabled());
  }
}
