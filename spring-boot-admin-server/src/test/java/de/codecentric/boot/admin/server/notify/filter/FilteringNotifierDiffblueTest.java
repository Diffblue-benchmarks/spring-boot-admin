package de.codecentric.boot.admin.server.notify.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.Notifier;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.function.Function;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.http.server.reactive.ChannelSendOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FilteringNotifierDiffblueTest {
  /**
   * Method under test:
   * {@link FilteringNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testShouldNotify() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act and Assert
    assertTrue(filteringNotifier.shouldNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    assertTrue(filteringNotifier.getNotificationFilters().isEmpty());
  }

  /**
   * Method under test:
   * {@link FilteringNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testShouldNotify2() {
    // Arrange
    Notifier delegate = mock(Notifier.class);

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));
    filteringNotifier.addFilter(new ApplicationNameNotificationFilter("Application Name",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertTrue(filteringNotifier.shouldNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test:
   * {@link FilteringNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator = new ChannelSendOperator<>(source, mock(Function.class));

    when(delegate.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);
    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act
    Mono<Void> actualDoNotifyResult = filteringNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L),
        null);

    // Assert
    verify(delegate).notify(isA(InstanceEvent.class));
    assertSame(channelSendOperator, actualDoNotifyResult);
  }

  /**
   * Method under test: {@link FilteringNotifier#addFilter(NotificationFilter)}
   */
  @Test
  public void testAddFilter() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Act
    filteringNotifier.addFilter(new ApplicationNameNotificationFilter("Application Name",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    assertEquals(1, filteringNotifier.getNotificationFilters().size());
  }

  /**
   * Method under test: {@link FilteringNotifier#removeFilter(String)}
   */
  @Test
  public void testRemoveFilter() {
    // Arrange
    Notifier delegate = mock(Notifier.class);

    // Act and Assert
    assertNull((new FilteringNotifier(delegate, new EventsourcingInstanceRepository(new InMemoryEventStore())))
        .removeFilter("42"));
  }

  /**
   * Method under test: {@link FilteringNotifier#getNotificationFilters()}
   */
  @Test
  public void testGetNotificationFilters() {
    // Arrange
    Notifier delegate = mock(Notifier.class);

    // Act and Assert
    assertTrue((new FilteringNotifier(delegate, new EventsourcingInstanceRepository(new InMemoryEventStore())))
        .getNotificationFilters()
        .isEmpty());
  }

  /**
   * Method under test:
   * {@link FilteringNotifier#FilteringNotifier(Notifier, InstanceRepository)}
   */
  @Test
  public void testNewFilteringNotifier() {
    // Arrange
    Notifier delegate = mock(Notifier.class);

    // Act
    FilteringNotifier actualFilteringNotifier = new FilteringNotifier(delegate,
        new EventsourcingInstanceRepository(new InMemoryEventStore()));

    // Assert
    assertTrue(actualFilteringNotifier.isEnabled());
    assertTrue(actualFilteringNotifier.getNotificationFilters().isEmpty());
  }
}
