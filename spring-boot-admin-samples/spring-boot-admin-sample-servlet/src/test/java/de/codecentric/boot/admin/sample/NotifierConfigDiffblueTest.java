package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.loadbalancer.SimpleObjectProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NotifierConfig.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class NotifierConfigDiffblueTest {
  @MockBean
  private FilteringNotifier filteringNotifier;

  @MockBean
  private InstanceRepository instanceRepository;

  @Autowired
  private NotifierConfig notifierConfig;

  @MockBean
  private ObjectProvider objectProvider;

  /**
   * Method under test: {@link NotifierConfig#filteringNotifier()}
   */
  @Test
  void testFilteringNotifier() {
    // Arrange and Act
    FilteringNotifier actualFilteringNotifierResult = notifierConfig.filteringNotifier();

    // Assert
    assertTrue(actualFilteringNotifierResult.isEnabled());
    assertTrue(actualFilteringNotifierResult.getNotificationFilters().isEmpty());
  }

  /**
   * Method under test: {@link NotifierConfig#remindingNotifier()}
   */
  @Test
  void testRemindingNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    // Act and Assert
    assertTrue((new NotifierConfig(repository, new SimpleObjectProvider<>(new ArrayList<>()))).remindingNotifier()
        .isEnabled());
  }

  /**
   * Method under test: {@link NotifierConfig#remindingNotifier()}
   */
  @Test
  void testRemindingNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(InstanceEventStore.class));

    // Act and Assert
    assertTrue((new NotifierConfig(repository, new SimpleObjectProvider<>(new ArrayList<>()))).remindingNotifier()
        .isEnabled());
  }
}
