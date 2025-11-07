package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.server.reactive.ChannelSendOperator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {NotificationTrigger.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class NotificationTriggerDiffblueTest {
  @Autowired
  private NotificationTrigger notificationTrigger;

  @MockBean
  private Notifier notifier;

  @MockBean
  private Publisher<InstanceEvent> publisher;

  /**
   * Test {@link NotificationTrigger#handle(Flux)}.
   * <ul>
   *   <li>Given fromIterable {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return fromIterable {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NotificationTrigger#handle(Flux)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Publisher NotificationTrigger.handle(Flux)"})
  public void testHandle_givenFromIterableArrayList_thenReturnFromIterableArrayList() {
    // Arrange
    Flux<InstanceEvent> publisher2 = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(publisher2.flatMap(Mockito.<Function<InstanceEvent, Publisher<Object>>>any())).thenReturn(fromIterableResult);

    // Act
    Publisher<Void> actualHandleResult = notificationTrigger.handle(publisher2);

    // Assert
    verify(publisher2).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }

  /**
   * Test {@link NotificationTrigger#sendNotifications(InstanceEvent)}.
   * <p>
   * Method under test: {@link NotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono NotificationTrigger.sendNotifications(InstanceEvent)"})
  public void testSendNotifications() {
    // Arrange
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(notifier.notify(Mockito.<InstanceEvent>any()))
        .thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));

    // Act
    notificationTrigger.sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
  }

  /**
   * Test {@link NotificationTrigger#sendNotifications(InstanceEvent)}.
   * <p>
   * Method under test: {@link NotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono NotificationTrigger.sendNotifications(InstanceEvent)"})
  public void testSendNotifications2() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    when(channelSendOperator.doOnError(Mockito.<Consumer<Throwable>>any()))
        .thenReturn(new ChannelSendOperator<>(source, mock(Function.class)));
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator);

    // Act
    notificationTrigger.sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(channelSendOperator).doOnError(isA(Consumer.class));
  }

  /**
   * Test {@link NotificationTrigger#sendNotifications(InstanceEvent)}.
   * <p>
   * Method under test: {@link NotificationTrigger#sendNotifications(InstanceEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Mono NotificationTrigger.sendNotifications(InstanceEvent)"})
  public void testSendNotifications3() {
    // Arrange
    ChannelSendOperator<Object> channelSendOperator = mock(ChannelSendOperator.class);
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator2 = new ChannelSendOperator<>(source, mock(Function.class));

    when(channelSendOperator.onErrorResume(Mockito.<Function<Throwable, Mono<Void>>>any()))
        .thenReturn(channelSendOperator2);
    ChannelSendOperator<Object> channelSendOperator3 = mock(ChannelSendOperator.class);
    when(channelSendOperator3.doOnError(Mockito.<Consumer<Throwable>>any())).thenReturn(channelSendOperator);
    when(notifier.notify(Mockito.<InstanceEvent>any())).thenReturn(channelSendOperator3);

    // Act
    Mono<Void> actualSendNotificationsResult = notificationTrigger
        .sendNotifications(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    verify(notifier).notify(isA(InstanceEvent.class));
    verify(channelSendOperator3).doOnError(isA(Consumer.class));
    verify(channelSendOperator).onErrorResume(isA(Function.class));
    assertSame(channelSendOperator2, actualSendNotificationsResult);
  }
}
