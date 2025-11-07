package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.FeiShuNotifier.Card;
import de.codecentric.boot.admin.server.notify.FeiShuNotifier.MessageType;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {FeiShuNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class FeiShuNotifierDiffblueTest {
  @Autowired
  private FeiShuNotifier feiShuNotifier;

  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Test Card getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Card#Card(FeiShuNotifier)}
   *   <li>{@link Card#setThemeColor(String)}
   *   <li>{@link Card#setTitle(String)}
   *   <li>{@link Card#getThemeColor()}
   *   <li>{@link Card#getTitle()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Card.<init>(FeiShuNotifier)", "String Card.getThemeColor()", "String Card.getTitle()",
      "void Card.setThemeColor(String)", "void Card.setTitle(String)"})
  public void testCardGettersAndSetters() {
    // Arrange and Act
    Card actualCard = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    actualCard.setThemeColor("Theme Color");
    actualCard.setTitle("Dr");
    String actualThemeColor = actualCard.getThemeColor();

    // Assert
    assertEquals("Dr", actualCard.getTitle());
    assertEquals("Theme Color", actualThemeColor);
  }

  /**
   * Test {@link FeiShuNotifier#FeiShuNotifier(InstanceRepository, RestTemplate)}.
   * <p>
   * Method under test: {@link FeiShuNotifier#FeiShuNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FeiShuNotifier.<init>(InstanceRepository, RestTemplate)"})
  public void testNewFeiShuNotifier() {
    // Arrange and Act
    FeiShuNotifier actualFeiShuNotifier = new FeiShuNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    Card card = actualFeiShuNotifier.getCard();
    assertEquals("Codecentric's Spring Boot Admin notice", card.getTitle());
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        actualFeiShuNotifier.getMessage());
    assertEquals("red", card.getThemeColor());
    assertNull(actualFeiShuNotifier.getSecret());
    assertNull(actualFeiShuNotifier.getWebhookUrl());
    assertEquals(MessageType.interactive, actualFeiShuNotifier.getMessageType());
    assertTrue(actualFeiShuNotifier.isEnabled());
    assertTrue(actualFeiShuNotifier.isAtAll());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualFeiShuNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Given {@link FeiShuNotifier} WebhookUrl is {@link PagerdutyNotifier#DEFAULT_URI}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono FeiShuNotifier.doNotify(InstanceEvent, Instance)"})
  public void testDoNotify_givenFeiShuNotifierWebhookUrlIsDefault_uri() throws AssertionError {
    // Arrange
    feiShuNotifier.setWebhookUrl(PagerdutyNotifier.DEFAULT_URI);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(feiShuNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Given {@link FeiShuNotifier} WebhookUrl is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono FeiShuNotifier.doNotify(InstanceEvent, Instance)"})
  public void testDoNotify_givenFeiShuNotifierWebhookUrlIsNull() throws AssertionError {
    // Arrange
    feiShuNotifier.setWebhookUrl(null);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(feiShuNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   * <p>
   * Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  public void testCreateNotification() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Card card = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    card.setThemeColor(null);

    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");
    feiShuNotifier.setMessageType(MessageType.interactive);
    feiShuNotifier.setAtAll(true);
    feiShuNotifier.setSecret(null);
    feiShuNotifier.setCard(card);

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof MessageType);
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
            + "\":\"div\",\"text\":{\"tag\":\"lark_md\",\"content\":\"<at id=all></at>\"}}],\"header\":{\"template\":null,\"title\":{"
            + "\"tag\":\"plain_text\",\"content\":\"Codecentric's Spring Boot Admin notice\"}}}",
        body.get("card"));
    assertEquals(MessageType.interactive, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  public void testCreateNotification_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Card card = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    card.setThemeColor(null);

    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore(3)),
        mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");
    feiShuNotifier.setMessageType(MessageType.interactive);
    feiShuNotifier.setAtAll(false);
    feiShuNotifier.setSecret(null);
    feiShuNotifier.setCard(card);

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof MessageType);
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
    assertEquals(MessageType.interactive, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Then calls {@link Card#getThemeColor()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  public void testCreateNotification_thenCallsGetThemeColor() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Card card = mock(Card.class);
    when(card.getThemeColor()).thenReturn("Theme Color");
    when(card.getTitle()).thenReturn("Dr");
    doNothing().when(card).setThemeColor(Mockito.<String>any());
    card.setThemeColor(null);

    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");
    feiShuNotifier.setMessageType(MessageType.interactive);
    feiShuNotifier.setAtAll(false);
    feiShuNotifier.setSecret(null);
    feiShuNotifier.setCard(card);

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    verify(card).getThemeColor();
    verify(card).getTitle();
    verify(card).setThemeColor(isNull());
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof MessageType);
    HttpHeaders headers = actualCreateNotificationResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult2 = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult2.size());
    assertEquals("Codecentric's Spring Boot Admin", getResult2.get(0));
    List<String> getResult3 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult3.size());
    assertEquals("application/json", getResult3.get(0));
    assertEquals(
        "{\"elements\":[{\"tag\":\"div\",\"text\":{\"tag\":\"plain_text\",\"content\":\"Not all who wander are lost\"}}],\"header"
            + "\":{\"template\":\"red\",\"title\":{\"tag\":\"plain_text\",\"content\":\"Dr\"}}}",
        body.get("card"));
    assertEquals(MessageType.interactive, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Then return Body {@code card} is a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  public void testCreateNotification_thenReturnBodyCardIsAString() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Card card = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    card.setThemeColor(null);

    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");
    feiShuNotifier.setMessageType(MessageType.interactive);
    feiShuNotifier.setAtAll(false);
    feiShuNotifier.setSecret(null);
    feiShuNotifier.setCard(card);

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof MessageType);
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
    assertEquals(MessageType.interactive, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Then return Body {@code content} is {@code {"text":"Not all who wander are lost"}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  public void testCreateNotification_thenReturnBodyContentIsTextNotAllWhoWanderAreLost() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    Card card = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    card.setThemeColor(null);

    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");
    feiShuNotifier.setMessageType(MessageType.text);
    feiShuNotifier.setAtAll(false);
    feiShuNotifier.setSecret(null);
    feiShuNotifier.setCard(card);

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult = feiShuNotifier
        .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof MessageType);
    HttpHeaders headers = actualCreateNotificationResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult2 = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult2.size());
    assertEquals("Codecentric's Spring Boot Admin", getResult2.get(0));
    List<String> getResult3 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult3.size());
    assertEquals("application/json", getResult3.get(0));
    assertEquals("{\"text\":\"Not all who wander are lost\"}", body.get("content"));
    assertEquals(MessageType.text, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(actualCreateNotificationResult.hasBody());
  }

  /**
   * Test {@link FeiShuNotifier#getMessage()}.
   * <p>
   * Method under test: {@link FeiShuNotifier#getMessage()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String FeiShuNotifier.getMessage()"})
  public void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        feiShuNotifier.getMessage());
  }

  /**
   * Test {@link FeiShuNotifier#setMessage(String)}.
   * <p>
   * Method under test: {@link FeiShuNotifier#setMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FeiShuNotifier.setMessage(String)"})
  public void testSetMessage() {
    // Arrange and Act
    feiShuNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", feiShuNotifier.getMessage());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FeiShuNotifier#setAtAll(boolean)}
   *   <li>{@link FeiShuNotifier#setCard(Card)}
   *   <li>{@link FeiShuNotifier#setMessageType(MessageType)}
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Card FeiShuNotifier.getCard()", "MessageType FeiShuNotifier.getMessageType()",
      "String FeiShuNotifier.getSecret()", "URI FeiShuNotifier.getWebhookUrl()", "boolean FeiShuNotifier.isAtAll()",
      "void FeiShuNotifier.setAtAll(boolean)", "void FeiShuNotifier.setCard(Card)",
      "void FeiShuNotifier.setMessageType(MessageType)", "void FeiShuNotifier.setRestTemplate(RestTemplate)",
      "void FeiShuNotifier.setSecret(String)", "void FeiShuNotifier.setWebhookUrl(URI)"})
  public void testGettersAndSetters() {
    // Arrange
    FeiShuNotifier feiShuNotifier = new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));

    // Act
    feiShuNotifier.setAtAll(true);
    Card card = (new FeiShuNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class))).new Card();
    feiShuNotifier.setCard(card);
    feiShuNotifier.setMessageType(MessageType.text);
    feiShuNotifier.setRestTemplate(mock(RestTemplate.class));
    feiShuNotifier.setSecret("Secret");
    URI webhookUrl = PagerdutyNotifier.DEFAULT_URI;
    feiShuNotifier.setWebhookUrl(webhookUrl);
    Card actualCard = feiShuNotifier.getCard();
    MessageType actualMessageType = feiShuNotifier.getMessageType();
    String actualSecret = feiShuNotifier.getSecret();
    URI actualWebhookUrl = feiShuNotifier.getWebhookUrl();
    boolean actualIsAtAllResult = feiShuNotifier.isAtAll();

    // Assert
    assertEquals("Secret", actualSecret);
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualWebhookUrl.toString());
    assertEquals(MessageType.text, actualMessageType);
    assertTrue(actualIsAtAllResult);
    assertSame(card, actualCard);
    assertSame(webhookUrl, actualWebhookUrl);
  }
}
