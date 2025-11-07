package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {SlackNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SlackNotifierDiffblueTest {
  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private SlackNotifier slackNotifier;

  /**
   * Test {@link SlackNotifier#SlackNotifier(InstanceRepository, RestTemplate)}.
   * <p>
   * Method under test: {@link SlackNotifier#SlackNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SlackNotifier.<init>(InstanceRepository, RestTemplate)"})
  public void testNewSlackNotifier() {
    // Arrange and Act
    SlackNotifier actualSlackNotifier = new SlackNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualSlackNotifier.getMessage());
    assertEquals("Spring Boot Admin", actualSlackNotifier.getUsername());
    assertNull(actualSlackNotifier.getChannel());
    assertNull(actualSlackNotifier.getIcon());
    assertNull(actualSlackNotifier.getWebhookUrl());
    assertTrue(actualSlackNotifier.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualSlackNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link SlackNotifier#doNotify(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Given {@link SlackNotifier} WebhookUrl is {@link PagerdutyNotifier#DEFAULT_URI}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SlackNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono SlackNotifier.doNotify(InstanceEvent, Instance)"})
  public void testDoNotify_givenSlackNotifierWebhookUrlIsDefault_uri() throws AssertionError {
    // Arrange
    slackNotifier.setWebhookUrl(PagerdutyNotifier.DEFAULT_URI);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(slackNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link SlackNotifier#doNotify(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Given {@link SlackNotifier} WebhookUrl is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SlackNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono SlackNotifier.doNotify(InstanceEvent, Instance)"})
  public void testDoNotify_givenSlackNotifierWebhookUrlIsNull() throws AssertionError {
    // Arrange
    slackNotifier.setWebhookUrl(null);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(slackNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link SlackNotifier#getText(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Then return {@code Message}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SlackNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SlackNotifier.getText(InstanceEvent, Instance)"})
  public void testGetText_thenReturnMessage() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    SlackNotifier slackNotifier = new SlackNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)), mock(RestTemplate.class));
    slackNotifier.setMessage("Message");

    // Act
    String actualText = slackNotifier.getText(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
    assertEquals("Message", actualText);
  }

  /**
   * Test {@link SlackNotifier#getText(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SlackNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SlackNotifier.getText(InstanceEvent, Instance)"})
  public void testGetText_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    IMap<InstanceId, List<InstanceEvent>> eventLogs = mock(IMap.class);
    when(eventLogs.addEntryListener(Mockito.<MapListener>any(), anyBoolean())).thenReturn(UUID.randomUUID());

    SlackNotifier slackNotifier = new SlackNotifier(
        new EventsourcingInstanceRepository(new HazelcastEventStore(eventLogs)), mock(RestTemplate.class));
    slackNotifier.setMessage("Not all who wander are lost");

    // Act
    String actualText = slackNotifier.getText(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    verify(eventLogs).addEntryListener(isA(MapListener.class), eq(true));
    assertEquals("Not all who wander are lost", actualText);
  }

  /**
   * Test {@link SlackNotifier#getColor(InstanceEvent)}.
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException(String)} with {@code UP}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SlackNotifier.getColor(InstanceEvent)"})
  public void testGetColor_givenIllegalStateExceptionWithUp_thenThrowIllegalStateException() {
    // Arrange
    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getStatusInfo()).thenThrow(new IllegalStateException("UP"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> slackNotifier.getColor(event));
    verify(event).getStatusInfo();
  }

  /**
   * Test {@link SlackNotifier#getColor(InstanceEvent)}.
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code 42}.</li>
   *   <li>Then return {@code #439FE0}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SlackNotifier.getColor(InstanceEvent)"})
  public void testGetColor_whenInstanceIdWithValueIs42_thenReturn439fe0() {
    // Arrange, Act and Assert
    assertEquals("#439FE0", slackNotifier.getColor(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SlackNotifier#setChannel(String)}
   *   <li>{@link SlackNotifier#setIcon(String)}
   *   <li>{@link SlackNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link SlackNotifier#setUsername(String)}
   *   <li>{@link SlackNotifier#setWebhookUrl(URI)}
   *   <li>{@link SlackNotifier#getChannel()}
   *   <li>{@link SlackNotifier#getIcon()}
   *   <li>{@link SlackNotifier#getUsername()}
   *   <li>{@link SlackNotifier#getWebhookUrl()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SlackNotifier.getChannel()", "String SlackNotifier.getIcon()",
      "String SlackNotifier.getUsername()", "URI SlackNotifier.getWebhookUrl()",
      "void SlackNotifier.setChannel(String)", "void SlackNotifier.setIcon(String)",
      "void SlackNotifier.setRestTemplate(RestTemplate)", "void SlackNotifier.setUsername(String)",
      "void SlackNotifier.setWebhookUrl(URI)"})
  public void testGettersAndSetters() {
    // Arrange
    SlackNotifier slackNotifier = new SlackNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));

    // Act
    slackNotifier.setChannel("Channel");
    slackNotifier.setIcon("Icon");
    slackNotifier.setRestTemplate(mock(RestTemplate.class));
    slackNotifier.setUsername("janedoe");
    URI webhookUrl = PagerdutyNotifier.DEFAULT_URI;
    slackNotifier.setWebhookUrl(webhookUrl);
    String actualChannel = slackNotifier.getChannel();
    String actualIcon = slackNotifier.getIcon();
    String actualUsername = slackNotifier.getUsername();
    URI actualWebhookUrl = slackNotifier.getWebhookUrl();

    // Assert
    assertEquals("Channel", actualChannel);
    assertEquals("Icon", actualIcon);
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualWebhookUrl.toString());
    assertEquals("janedoe", actualUsername);
    assertSame(webhookUrl, actualWebhookUrl);
  }

  /**
   * Test {@link SlackNotifier#getMessage()}.
   * <p>
   * Method under test: {@link SlackNotifier#getMessage()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String SlackNotifier.getMessage()"})
  public void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        slackNotifier.getMessage());
  }

  /**
   * Test {@link SlackNotifier#setMessage(String)}.
   * <ul>
   *   <li>Then {@link SlackNotifier} Message is {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SlackNotifier#setMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SlackNotifier.setMessage(String)"})
  public void testSetMessage_thenSlackNotifierMessageIsNotAllWhoWanderAreLost() {
    // Arrange and Act
    slackNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", slackNotifier.getMessage());
  }

  /**
   * Test {@link SlackNotifier#setMessage(String)}.
   * <ul>
   *   <li>When {@code Message42UP}.</li>
   *   <li>Then {@link SlackNotifier} Message is {@code Message42UP}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SlackNotifier#setMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SlackNotifier.setMessage(String)"})
  public void testSetMessage_whenMessage42UP_thenSlackNotifierMessageIsMessage42UP() {
    // Arrange and Act
    slackNotifier.setMessage("Message42UP");

    // Assert
    assertEquals("Message42UP", slackNotifier.getMessage());
  }
}
