package de.codecentric.boot.admin.server.notify.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.Notifier;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.server.reactive.ChannelSendOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class FilteringNotifierDiffblueTest {
  /**
   * Test {@link FilteringNotifier#FilteringNotifier(Notifier, InstanceRepository)}.
   *
   * <ul>
   *   <li>When {@link Notifier}.
   *   <li>Then return Enabled.
   * </ul>
   *
   * <p>Method under test: {@link FilteringNotifier#FilteringNotifier(Notifier, InstanceRepository)}
   */
  @Test
  @DisplayName(
      "Test new FilteringNotifier(Notifier, InstanceRepository); when Notifier; then return Enabled")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FilteringNotifier.<init>(Notifier, InstanceRepository)"})
  void testNewFilteringNotifier_whenNotifier_thenReturnEnabled() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act
    FilteringNotifier actualFilteringNotifier = new FilteringNotifier(delegate, repository);

    // Assert
    assertTrue(actualFilteringNotifier.isEnabled());
    assertTrue(actualFilteringNotifier.getNotificationFilters().isEmpty());
  }

  /**
   * Test {@link FilteringNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link FilteringNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test shouldNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean FilteringNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate, repository);

    // Act and Assert
    assertTrue(filteringNotifier.shouldNotify(mock(InstanceEvent.class), mock(Instance.class)));
  }

  /**
   * Test {@link FilteringNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link FilteringNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Mono FilteringNotifier.doNotify(InstanceEvent, Instance)"})
  void testDoNotify() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));
    when(delegate.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate, repository);

    // Act
    Mono<Void> actualDoNotifyResult =
        filteringNotifier.doNotify(mock(InstanceEvent.class), mock(Instance.class));

    // Assert
    verify(delegate).notify(isA(InstanceEvent.class));
    assertSame(channelSendOperator, actualDoNotifyResult);
  }

  /**
   * Test {@link FilteringNotifier#addFilter(NotificationFilter)}.
   *
   * <p>Method under test: {@link FilteringNotifier#addFilter(NotificationFilter)}
   */
  @Test
  @DisplayName("Test addFilter(NotificationFilter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FilteringNotifier.addFilter(NotificationFilter)"})
  void testAddFilter() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate, repository);
    ApplicationNameNotificationFilter filter =
        new ApplicationNameNotificationFilter(
            "Application Name",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    filteringNotifier.addFilter(filter);

    // Assert
    assertEquals(1, filteringNotifier.getNotificationFilters().size());
  }

  /**
   * Test {@link FilteringNotifier#removeFilter(String)}.
   *
   * <p>Method under test: {@link FilteringNotifier#removeFilter(String)}
   */
  @Test
  @DisplayName("Test removeFilter(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"NotificationFilter FilteringNotifier.removeFilter(String)"})
  void testRemoveFilter() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate, repository);

    // Act and Assert
    assertNull(filteringNotifier.removeFilter("42"));
  }

  /**
   * Test {@link FilteringNotifier#getNotificationFilters()}.
   *
   * <p>Method under test: {@link FilteringNotifier#getNotificationFilters()}
   */
  @Test
  @DisplayName("Test getNotificationFilters()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Map FilteringNotifier.getNotificationFilters()"})
  void testGetNotificationFilters() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate, repository);

    // Act and Assert
    assertTrue(filteringNotifier.getNotificationFilters().isEmpty());
  }
}
