package de.codecentric.boot.admin.server.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.HazelcastNotificationTrigger;
import de.codecentric.boot.admin.server.notify.NotificationTrigger;
import de.codecentric.boot.admin.server.notify.Notifier;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.scheduler.Scheduler;

@ContextConfiguration(classes = {NotificationTrigger.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class AbstractEventHandlerDiffblueTest {
  @Autowired private AbstractEventHandler<InstanceEvent> abstractEventHandler;

  @MockitoBean private Notifier notifier;

  @MockitoBean private Publisher<InstanceEvent> publisher;

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    Notifier notifier = mock(Notifier.class);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult2 = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(fromIterableResult2);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(writeFunction).apply(isA(Publisher.class));
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart2() {
    // Arrange
    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);
    Mono<?> source = Mono.just("Data");

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    Notifier notifier = mock(Notifier.class);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult2 = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(fromIterableResult2);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(writeFunction).apply(isA(Publisher.class));
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link Function#apply(Object)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName("Test start(); given ArrayList() add '42'; then calls apply(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart_givenArrayListAdd42_thenCallsApply() {
    // Arrange
    ArrayList<Object> it = new ArrayList<>();
    it.add("42");
    Flux<?> source = Flux.fromIterable(it);

    Function<Publisher<Object>, Publisher<Void>> writeFunction = mock(Function.class);
    Flux<Void> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(writeFunction.apply(Mockito.<Publisher<Object>>any())).thenReturn(fromIterableResult);

    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, writeFunction);

    Notifier notifier = mock(Notifier.class);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);

    ArrayList<InstanceEvent> it2 = new ArrayList<>();
    it2.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult2 = Flux.fromIterable(it2);

    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(fromIterableResult2);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(writeFunction).apply(isA(Publisher.class));
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link DirectProcessor#subscribeOn(Scheduler)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName("Test start(); given ArrayList() add 'null'; then calls subscribeOn(Scheduler)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart_givenArrayListAddNull_thenCallsSubscribeOn() {
    // Arrange
    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(null);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(fromIterableResult);
    Notifier notifier = mock(Notifier.class);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#subscribeOn(Scheduler)} return
   *       create three and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given DirectProcessor subscribeOn(Scheduler) return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart_givenDirectProcessorSubscribeOnReturnCreateThreeAndTrue() {
    // Arrange
    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(createResult);
    Notifier notifier = mock(Notifier.class);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#subscribeOn(Scheduler)} return
   *       create three and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given DirectProcessor subscribeOn(Scheduler) return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart_givenDirectProcessorSubscribeOnReturnCreateThreeAndTrue2() {
    // Arrange
    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    ReplayProcessor<InstanceEvent> createResult = ReplayProcessor.create(3, true);
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(createResult);
    Notifier notifier = mock(Notifier.class);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#subscribeOn(Scheduler)} return
   *       create three and {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given DirectProcessor subscribeOn(Scheduler) return create three and 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart_givenDirectProcessorSubscribeOnReturnCreateThreeAndTrue3() {
    // Arrange
    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    EmitterProcessor<InstanceEvent> createResult = EmitterProcessor.create(3, true);
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(createResult);
    Notifier notifier = mock(Notifier.class);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#subscribeOn(Scheduler)} return
   *       create.
   *   <li>Then calls {@link DirectProcessor#subscribeOn(Scheduler)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given DirectProcessor subscribeOn(Scheduler) return create; then calls subscribeOn(Scheduler)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart_givenDirectProcessorSubscribeOnReturnCreate_thenCallsSubscribeOn() {
    // Arrange
    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    DirectProcessor<InstanceEvent> createResult = DirectProcessor.create();
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(createResult);
    Notifier notifier = mock(Notifier.class);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <ul>
   *   <li>Given {@link DirectProcessor} {@link DirectProcessor#subscribeOn(Scheduler)} return
   *       fromIterable {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given DirectProcessor subscribeOn(Scheduler) return fromIterable ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart_givenDirectProcessorSubscribeOnReturnFromIterableArrayList() {
    // Arrange
    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(fromIterableResult);
    Notifier notifier = mock(Notifier.class);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <ul>
   *   <li>Given {@link Notifier} {@link Notifier#notify(InstanceEvent)} return {@code null}.
   *   <li>Then calls {@link Notifier#notify(InstanceEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given Notifier notify(InstanceEvent) return 'null'; then calls notify(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart_givenNotifierNotifyReturnNull_thenCallsNotify() {
    // Arrange
    Notifier notifier = mock(Notifier.class);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(null);

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(fromIterableResult);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#start()}.
   *
   * <ul>
   *   <li>Given {@link Publisher} {@link Publisher#subscribe(Subscriber)} does nothing.
   *   <li>Then calls {@link Publisher#subscribe(Subscriber)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEventHandler#start()}
   */
  @Test
  @DisplayName(
      "Test start(); given Publisher subscribe(Subscriber) does nothing; then calls subscribe(Subscriber)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEventHandler.start()"})
  void testStart_givenPublisherSubscribeDoesNothing_thenCallsSubscribe() {
    // Arrange
    Publisher<Object> source = mock(Publisher.class);
    doNothing().when(source).subscribe(Mockito.<Subscriber<Object>>any());
    ChannelSendOperator<Object> channelSendOperator =
        new ChannelSendOperator<>(source, mock(Function.class));

    Notifier notifier = mock(Notifier.class);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);

    ArrayList<InstanceEvent> it = new ArrayList<>();
    it.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    Flux<InstanceEvent> fromIterableResult = Flux.fromIterable(it);

    DirectProcessor<InstanceEvent> events = mock(DirectProcessor.class);
    when(events.subscribeOn(Mockito.<Scheduler>any())).thenReturn(fromIterableResult);

    HazelcastNotificationTrigger hazelcastNotificationTrigger =
        new HazelcastNotificationTrigger(notifier, events, new ConcurrentHashMap<>());

    // Act
    hazelcastNotificationTrigger.start();

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(source).subscribe(isA(Subscriber.class));
    verify(events).subscribeOn(isA(Scheduler.class));
  }

  /**
   * Test {@link AbstractEventHandler#createScheduler()}.
   *
   * <p>Method under test: {@link AbstractEventHandler#createScheduler()}
   */
  @Test
  @DisplayName("Test createScheduler()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Scheduler AbstractEventHandler.createScheduler()"})
  void testCreateScheduler() {
    // Arrange, Act and Assert
    assertFalse(abstractEventHandler.createScheduler().isDisposed());
  }
}
