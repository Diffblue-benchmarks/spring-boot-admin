package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {SlackNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class SlackNotifierDiffblueTest {
  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private SlackNotifier slackNotifier;

  /**
   * Method under test: {@link SlackNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(slackNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link SlackNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify2() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(slackNotifier.doNotify(mock(InstanceDeregisteredEvent.class), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link SlackNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateMessage() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SlackNotifier slackNotifier = new SlackNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    slackNotifier.setMessage("Not all who wander are lost");

    // Act
    Object actualCreateMessageResult = slackNotifier
        .createMessage(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Object body = ((HttpEntity<Object>) actualCreateMessageResult).getBody();
    assertEquals(2, ((Map<String, Object>) body).size());
    Object getResult = ((Map<String, Object>) body).get("attachments");
    assertEquals(1, ((List<HashMap>) getResult).size());
    HashMap getResult2 = ((List<HashMap>) getResult).get(0);
    assertEquals(3, getResult2.size());
    Object getResult3 = getResult2.get("mrkdwn_in");
    assertTrue(getResult3 instanceof List);
    assertTrue(getResult instanceof List);
    assertTrue(body instanceof Map);
    assertTrue(actualCreateMessageResult instanceof HttpEntity);
    assertEquals("#439FE0", getResult2.get("color"));
    assertEquals("Not all who wander are lost", getResult2.get("text"));
    assertEquals("Spring Boot Admin", ((Map<String, Object>) body).get("username"));
    HttpHeaders headers = ((HttpEntity<Object>) actualCreateMessageResult).getHeaders();
    assertEquals(1, headers.size());
    List<String> getResult4 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult4.size());
    assertEquals("application/json", getResult4.get(0));
    assertEquals(1, ((List<String>) getResult3).size());
    assertEquals("text", ((List<String>) getResult3).get(0));
    assertTrue(((HttpEntity<Object>) actualCreateMessageResult).hasBody());
  }

  /**
   * Method under test: {@link SlackNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  public void testGetText() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SlackNotifier slackNotifier = new SlackNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    slackNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    assertEquals("Not all who wander are lost",
        slackNotifier.getText(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  public void testGetColor() {
    // Arrange, Act and Assert
    assertEquals("#439FE0", slackNotifier.getColor(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
  }

  /**
   * Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  public void testGetColor2() {
    // Arrange
    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getStatusInfo()).thenThrow(new IllegalStateException("UP"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> slackNotifier.getColor(event));
    verify(event).getStatusInfo();
  }

  /**
   * Method under test: {@link SlackNotifier#getMessage()}
   */
  @Test
  public void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        slackNotifier.getMessage());
  }

  /**
   * Method under test: {@link SlackNotifier#setMessage(String)}
   */
  @Test
  public void testSetMessage() {
    // Arrange and Act
    slackNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", slackNotifier.getMessage());
  }

  /**
   * Method under test: {@link SlackNotifier#setMessage(String)}
   */
  @Test
  public void testSetMessage2() {
    // Arrange and Act
    slackNotifier.setMessage("de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent");

    // Assert
    assertEquals("de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent",
        slackNotifier.getMessage());
  }

  /**
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

    // Assert that nothing has changed
    assertEquals("Channel", actualChannel);
    assertEquals("Icon", actualIcon);
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualWebhookUrl.toString());
    assertEquals("janedoe", actualUsername);
    assertSame(webhookUrl, actualWebhookUrl);
  }

  /**
   * Method under test:
   * {@link SlackNotifier#SlackNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
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
}
