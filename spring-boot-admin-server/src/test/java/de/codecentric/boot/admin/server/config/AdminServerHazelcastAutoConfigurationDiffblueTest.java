package de.codecentric.boot.admin.server.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.hazelcast.client.impl.clientside.HazelcastClientInstanceImpl;
import com.hazelcast.core.HazelcastInstance;
import de.codecentric.boot.admin.server.config.AdminServerHazelcastAutoConfiguration.NotifierTriggerConfiguration;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.HazelcastNotificationTrigger;
import de.codecentric.boot.admin.server.notify.NotificationTrigger;
import de.codecentric.boot.admin.server.notify.Notifier;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

class AdminServerHazelcastAutoConfigurationDiffblueTest {
  /**
   * Test NotifierTriggerConfiguration {@link
   * NotifierTriggerConfiguration#notificationTrigger(HazelcastInstance, Notifier, Publisher)}.
   *
   * <p>Method under test: {@link
   * NotifierTriggerConfiguration#notificationTrigger(HazelcastInstance, Notifier, Publisher)}
   */
  @Test
  @DisplayName(
      "Test NotifierTriggerConfiguration notificationTrigger(HazelcastInstance, Notifier, Publisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "NotificationTrigger NotifierTriggerConfiguration.notificationTrigger(HazelcastInstance, Notifier, Publisher)"
  })
  void testNotifierTriggerConfigurationNotificationTrigger() {
    // Arrange
    NotifierTriggerConfiguration notifierTriggerConfiguration = new NotifierTriggerConfiguration();

    HazelcastClientInstanceImpl hazelcastInstance = mock(HazelcastClientInstanceImpl.class);
    when(hazelcastInstance.getMap(Mockito.<String>any())).thenReturn(null);
    Notifier notifier = mock(Notifier.class);
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());

    // Act
    NotificationTrigger actualNotificationTriggerResult =
        notifierTriggerConfiguration.notificationTrigger(hazelcastInstance, notifier, events);

    // Assert
    verify(hazelcastInstance).getMap("spring-boot-admin-sent-notifications");
    assertTrue(actualNotificationTriggerResult instanceof HazelcastNotificationTrigger);
  }
}
