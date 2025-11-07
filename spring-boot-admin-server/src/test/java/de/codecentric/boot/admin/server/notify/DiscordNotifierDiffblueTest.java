package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.net.URI;
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
@ContextConfiguration(classes = {DiscordNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class DiscordNotifierDiffblueTest {
  @Autowired
  private DiscordNotifier discordNotifier;

  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Method under test: {@link DiscordNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(discordNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link DiscordNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify2() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(discordNotifier.doNotify(mock(InstanceDeregisteredEvent.class), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link DiscordNotifier#createDiscordNotification(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateDiscordNotification() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    DiscordNotifier discordNotifier = new DiscordNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    discordNotifier.setMessage("Not all who wander are lost");

    // Act
    Object actualCreateDiscordNotificationResult = discordNotifier
        .createDiscordNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Object body = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getBody();
    assertTrue(body instanceof Map);
    assertTrue(actualCreateDiscordNotificationResult instanceof HttpEntity);
    assertEquals(2, ((Map<String, Object>) body).size());
    assertEquals("Not all who wander are lost", ((Map<String, Object>) body).get("content"));
    HttpHeaders headers = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult.size());
    assertEquals("RestTemplate", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertTrue(((Map<String, Object>) body).containsKey("tts"));
    assertTrue(((HttpEntity<Object>) actualCreateDiscordNotificationResult).hasBody());
  }

  /**
   * Method under test:
   * {@link DiscordNotifier#createDiscordNotification(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateDiscordNotification2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    DiscordNotifier discordNotifier = new DiscordNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    discordNotifier.setUsername("janedoe");
    discordNotifier.setMessage("Not all who wander are lost");

    // Act
    Object actualCreateDiscordNotificationResult = discordNotifier
        .createDiscordNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Object body = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getBody();
    assertTrue(body instanceof Map);
    assertTrue(actualCreateDiscordNotificationResult instanceof HttpEntity);
    assertEquals(3, ((Map<String, Object>) body).size());
    assertEquals("Not all who wander are lost", ((Map<String, Object>) body).get("content"));
    HttpHeaders headers = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult.size());
    assertEquals("RestTemplate", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertEquals("janedoe", ((Map<String, Object>) body).get("username"));
    assertTrue(((Map<String, Object>) body).containsKey("tts"));
    assertTrue(((HttpEntity<Object>) actualCreateDiscordNotificationResult).hasBody());
  }

  /**
   * Method under test:
   * {@link DiscordNotifier#createDiscordNotification(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateDiscordNotification3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    DiscordNotifier discordNotifier = new DiscordNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    discordNotifier.setAvatarUrl("https://example.org/example");
    discordNotifier.setMessage("Not all who wander are lost");

    // Act
    Object actualCreateDiscordNotificationResult = discordNotifier
        .createDiscordNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Object body = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getBody();
    assertTrue(body instanceof Map);
    assertTrue(actualCreateDiscordNotificationResult instanceof HttpEntity);
    assertEquals(3, ((Map<String, Object>) body).size());
    assertEquals("Not all who wander are lost", ((Map<String, Object>) body).get("content"));
    HttpHeaders headers = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult.size());
    assertEquals("RestTemplate", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertEquals("https://example.org/example", ((Map<String, Object>) body).get("avatar_url"));
    assertTrue(((Map<String, Object>) body).containsKey("tts"));
    assertTrue(((HttpEntity<Object>) actualCreateDiscordNotificationResult).hasBody());
  }

  /**
   * Method under test:
   * {@link DiscordNotifier#createContent(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateContent() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    DiscordNotifier discordNotifier = new DiscordNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    discordNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    assertEquals("Not all who wander are lost",
        discordNotifier.createContent(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test: {@link DiscordNotifier#getMessage()}
   */
  @Test
  public void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        discordNotifier.getMessage());
  }

  /**
   * Method under test: {@link DiscordNotifier#setMessage(String)}
   */
  @Test
  public void testSetMessage() {
    // Arrange and Act
    discordNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", discordNotifier.getMessage());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DiscordNotifier#setAvatarUrl(String)}
   *   <li>{@link DiscordNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link DiscordNotifier#setTts(boolean)}
   *   <li>{@link DiscordNotifier#setUsername(String)}
   *   <li>{@link DiscordNotifier#setWebhookUrl(URI)}
   *   <li>{@link DiscordNotifier#getAvatarUrl()}
   *   <li>{@link DiscordNotifier#getUsername()}
   *   <li>{@link DiscordNotifier#getWebhookUrl()}
   *   <li>{@link DiscordNotifier#isTts()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DiscordNotifier discordNotifier = new DiscordNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));

    // Act
    discordNotifier.setAvatarUrl("https://example.org/example");
    discordNotifier.setRestTemplate(mock(RestTemplate.class));
    discordNotifier.setTts(true);
    discordNotifier.setUsername("janedoe");
    URI webhookUrl = PagerdutyNotifier.DEFAULT_URI;
    discordNotifier.setWebhookUrl(webhookUrl);
    String actualAvatarUrl = discordNotifier.getAvatarUrl();
    String actualUsername = discordNotifier.getUsername();
    URI actualWebhookUrl = discordNotifier.getWebhookUrl();
    boolean actualIsTtsResult = discordNotifier.isTts();

    // Assert that nothing has changed
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualWebhookUrl.toString());
    assertEquals("https://example.org/example", actualAvatarUrl);
    assertEquals("janedoe", actualUsername);
    assertTrue(actualIsTtsResult);
    assertSame(webhookUrl, actualWebhookUrl);
  }

  /**
   * Method under test:
   * {@link DiscordNotifier#DiscordNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  public void testNewDiscordNotifier() {
    // Arrange and Act
    DiscordNotifier actualDiscordNotifier = new DiscordNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualDiscordNotifier.getMessage());
    assertNull(actualDiscordNotifier.getAvatarUrl());
    assertNull(actualDiscordNotifier.getUsername());
    assertNull(actualDiscordNotifier.getWebhookUrl());
    assertFalse(actualDiscordNotifier.isTts());
    assertTrue(actualDiscordNotifier.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualDiscordNotifier.getIgnoreChanges());
  }
}
