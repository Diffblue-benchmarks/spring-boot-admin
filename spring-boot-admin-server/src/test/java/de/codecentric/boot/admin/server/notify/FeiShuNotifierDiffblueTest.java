package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
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
@ContextConfiguration(classes = {FeiShuNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class FeiShuNotifierDiffblueTest {
  @Autowired
  private FeiShuNotifier feiShuNotifier;

  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link FeiShuNotifier.Card#Card(FeiShuNotifier)}
   *   <li>{@link FeiShuNotifier.Card#setThemeColor(String)}
   *   <li>{@link FeiShuNotifier.Card#setTitle(String)}
   *   <li>{@link FeiShuNotifier.Card#getThemeColor()}
   *   <li>{@link FeiShuNotifier.Card#getTitle()}
   * </ul>
   */
  @Test
  public void testCardGettersAndSetters() {
    // Arrange and Act
    FeiShuNotifier.Card actualCard = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    actualCard.setThemeColor("Theme Color");
    actualCard.setTitle("Dr");
    String actualThemeColor = actualCard.getThemeColor();

    // Assert that nothing has changed
    assertEquals("Dr", actualCard.getTitle());
    assertEquals("Theme Color", actualThemeColor);
  }

  /**
   * Method under test: {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(feiShuNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify2() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(feiShuNotifier.doNotify(mock(InstanceDeregisteredEvent.class), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateNotification() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof FeiShuNotifier.MessageType);
    HttpHeaders headers = actualCreateNotificationResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult2 = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult2.size());
    assertEquals("Codecentric's Spring Boot Admin", getResult2.get(0));
    List<String> getResult3 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult3.size());
    assertEquals("application/json", getResult3.get(0));
    assertEquals(
        "{\"elements\":[{\"tag\":\"div\",\"text\":{\"tag\":\"plain_text\",\"content\":\"Not all who wander are lost\"}},{\"tag"
            + "\":\"div\",\"text\":{\"tag\":\"lark_md\",\"content\":\"<at id=all></at>\"}}],\"header\":{\"template\":\"red\",\"title\":{"
            + "\"tag\":\"plain_text\",\"content\":\"Codecentric's Spring Boot Admin notice\"}}}",
        body.get("card"));
    assertEquals(FeiShuNotifier.MessageType.interactive, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Method under test:
   * {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateNotification2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore(3)),
        mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof FeiShuNotifier.MessageType);
    HttpHeaders headers = actualCreateNotificationResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult2 = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult2.size());
    assertEquals("Codecentric's Spring Boot Admin", getResult2.get(0));
    List<String> getResult3 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult3.size());
    assertEquals("application/json", getResult3.get(0));
    assertEquals(
        "{\"elements\":[{\"tag\":\"div\",\"text\":{\"tag\":\"plain_text\",\"content\":\"Not all who wander are lost\"}},{\"tag"
            + "\":\"div\",\"text\":{\"tag\":\"lark_md\",\"content\":\"<at id=all></at>\"}}],\"header\":{\"template\":\"red\",\"title\":{"
            + "\"tag\":\"plain_text\",\"content\":\"Codecentric's Spring Boot Admin notice\"}}}",
        body.get("card"));
    assertEquals(FeiShuNotifier.MessageType.interactive, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Method under test:
   * {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateNotification3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore(3)),
        mock(RestTemplate.class));
    feiShuNotifier.setMessageType(FeiShuNotifier.MessageType.text);
    feiShuNotifier.setMessage("Not all who wander are lost");

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof FeiShuNotifier.MessageType);
    HttpHeaders headers = actualCreateNotificationResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult2 = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult2.size());
    assertEquals("Codecentric's Spring Boot Admin", getResult2.get(0));
    List<String> getResult3 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult3.size());
    assertEquals("application/json", getResult3.get(0));
    assertEquals("{\"text\":\"Not all who wander are lost\\n<at user_id=\\\"all\\\">@all</at>\"}", body.get("content"));
    assertEquals(FeiShuNotifier.MessageType.text, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Method under test:
   * {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateNotification4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    FeiShuNotifier.Card card = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    card.setThemeColor(null);

    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");
    feiShuNotifier.setMessageType(FeiShuNotifier.MessageType.interactive);
    feiShuNotifier.setAtAll(false);
    feiShuNotifier.setSecret("foo");
    feiShuNotifier.setCard(card);

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(5, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof FeiShuNotifier.MessageType);
    HttpHeaders headers = actualCreateNotificationResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult2 = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult2.size());
    assertEquals("Codecentric's Spring Boot Admin", getResult2.get(0));
    List<String> getResult3 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult3.size());
    assertEquals("application/json", getResult3.get(0));
    assertEquals(
        "{\"elements\":[{\"tag\":\"div\",\"text\":{\"tag\":\"plain_text\",\"content\":\"Not all who wander are lost\"}}],"
            + "\"header\":{\"template\":null,\"title\":{\"tag\":\"plain_text\",\"content\":\"Codecentric's Spring Boot Admin"
            + " notice\"}}}",
        body.get("card"));
    assertEquals(FeiShuNotifier.MessageType.interactive, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(body.containsKey("timestamp"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Method under test:
   * {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateNotification5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    FeiShuNotifier.Card card = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    card.setThemeColor(null);

    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");
    feiShuNotifier.setMessageType(FeiShuNotifier.MessageType.text);
    feiShuNotifier.setAtAll(false);
    feiShuNotifier.setSecret("foo");
    feiShuNotifier.setCard(card);

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(5, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof FeiShuNotifier.MessageType);
    HttpHeaders headers = actualCreateNotificationResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult2 = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult2.size());
    assertEquals("Codecentric's Spring Boot Admin", getResult2.get(0));
    List<String> getResult3 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult3.size());
    assertEquals("application/json", getResult3.get(0));
    assertEquals("{\"text\":\"Not all who wander are lost\"}", body.get("content"));
    assertEquals(FeiShuNotifier.MessageType.text, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(body.containsKey("timestamp"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Method under test: {@link FeiShuNotifier#getMessage()}
   */
  @Test
  public void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        feiShuNotifier.getMessage());
  }

  /**
   * Method under test: {@link FeiShuNotifier#setMessage(String)}
   */
  @Test
  public void testSetMessage() {
    // Arrange and Act
    feiShuNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", feiShuNotifier.getMessage());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link FeiShuNotifier#setAtAll(boolean)}
   *   <li>{@link FeiShuNotifier#setCard(FeiShuNotifier.Card)}
   *   <li>{@link FeiShuNotifier#setMessageType(FeiShuNotifier.MessageType)}
   *   <li>{@link FeiShuNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link FeiShuNotifier#setSecret(String)}
   *   <li>{@link FeiShuNotifier#setWebhookUrl(URI)}
   *   <li>{@link FeiShuNotifier#getCard()}
   *   <li>{@link FeiShuNotifier#getMessageType()}
   *   <li>{@link FeiShuNotifier#getSecret()}
   *   <li>{@link FeiShuNotifier#getWebhookUrl()}
   *   <li>{@link FeiShuNotifier#isAtAll()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));

    // Act
    feiShuNotifier.setAtAll(true);
    FeiShuNotifier.Card card = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    feiShuNotifier.setCard(card);
    feiShuNotifier.setMessageType(FeiShuNotifier.MessageType.text);
    feiShuNotifier.setRestTemplate(mock(RestTemplate.class));
    feiShuNotifier.setSecret("Secret");
    URI webhookUrl = PagerdutyNotifier.DEFAULT_URI;
    feiShuNotifier.setWebhookUrl(webhookUrl);
    FeiShuNotifier.Card actualCard = feiShuNotifier.getCard();
    FeiShuNotifier.MessageType actualMessageType = feiShuNotifier.getMessageType();
    String actualSecret = feiShuNotifier.getSecret();
    URI actualWebhookUrl = feiShuNotifier.getWebhookUrl();
    boolean actualIsAtAllResult = feiShuNotifier.isAtAll();

    // Assert that nothing has changed
    assertEquals("Secret", actualSecret);
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualWebhookUrl.toString());
    assertEquals(FeiShuNotifier.MessageType.text, actualMessageType);
    assertTrue(actualIsAtAllResult);
    assertSame(card, actualCard);
    assertSame(webhookUrl, actualWebhookUrl);
  }

  /**
   * Method under test:
   * {@link FeiShuNotifier#FeiShuNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  public void testNewFeiShuNotifier() {
    // Arrange and Act
    FeiShuNotifier actualFeiShuNotifier = new FeiShuNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    FeiShuNotifier.Card card = actualFeiShuNotifier.getCard();
    assertEquals("Codecentric's Spring Boot Admin notice", card.getTitle());
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        actualFeiShuNotifier.getMessage());
    assertEquals("red", card.getThemeColor());
    assertNull(actualFeiShuNotifier.getSecret());
    assertNull(actualFeiShuNotifier.getWebhookUrl());
    assertEquals(FeiShuNotifier.MessageType.interactive, actualFeiShuNotifier.getMessageType());
    assertTrue(actualFeiShuNotifier.isEnabled());
    assertTrue(actualFeiShuNotifier.isAtAll());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualFeiShuNotifier.getIgnoreChanges());
  }
}
