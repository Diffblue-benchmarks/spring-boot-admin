package de.codecentric.boot.admin.server.eventstore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.Test;
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

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class ConcurrentMapEventStoreDiffblueTest {
  @Autowired
  private ConcurrentMapEventStore concurrentMapEventStore;

  @MockBean
  private HazelcastEventStore hazelcastEventStore;

  /**
   * Method under test: {@link ConcurrentMapEventStore#append(List)}
   */
  @Test
  public void testAppend() {
    // Arrange
    Flux<?> source = Flux.fromIterable(new ArrayList<>());
    ChannelSendOperator<Object> channelSendOperator = new ChannelSendOperator<>(source, mock(Function.class));

    when(hazelcastEventStore.append(Mockito.<List<InstanceEvent>>any())).thenReturn(channelSendOperator);

    // Act
    Mono<Void> actualAppendResult = concurrentMapEventStore.append(new ArrayList<>());

    // Assert
    verify(hazelcastEventStore).append(isA(List.class));
    assertSame(channelSendOperator, actualAppendResult);
  }

  /**
   * Method under test: {@link ConcurrentMapEventStore#getLastVersion(List)}
   */
  @Test
  public void testGetLastVersion() {
    // Arrange, Act and Assert
    assertEquals(-1L, ConcurrentMapEventStore.getLastVersion(new ArrayList<>()));
  }

  /**
   * Method under test: {@link ConcurrentMapEventStore#getLastVersion(List)}
   */
  @Test
  public void testGetLastVersion2() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertEquals(1L, ConcurrentMapEventStore.getLastVersion(events));
  }

  /**
   * Method under test: {@link ConcurrentMapEventStore#getLastVersion(List)}
   */
  @Test
  public void testGetLastVersion3() {
    // Arrange
    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act and Assert
    assertEquals(1L, ConcurrentMapEventStore.getLastVersion(events));
  }
}
