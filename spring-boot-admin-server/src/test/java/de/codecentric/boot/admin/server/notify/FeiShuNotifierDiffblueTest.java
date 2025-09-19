package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.FeiShuNotifier.Card;
import de.codecentric.boot.admin.server.notify.FeiShuNotifier.MessageType;
import java.net.URI;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {FeiShuNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class FeiShuNotifierDiffblueTest {
  @Autowired private FeiShuNotifier feiShuNotifier;

  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private RestTemplate restTemplate;

  /**
   * Test Card getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Card#Card(FeiShuNotifier)}
   *   <li>{@link Card#setThemeColor(String)}
   *   <li>{@link Card#setTitle(String)}
   *   <li>{@link Card#getThemeColor()}
   *   <li>{@link Card#getTitle()}
   * </ul>
   */
  @Test
  @DisplayName("Test Card getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Card.<init>(FeiShuNotifier)",
    "String Card.getThemeColor()",
    "String Card.getTitle()",
    "void Card.setThemeColor(String)",
    "void Card.setTitle(String)"
  })
  void testCardGettersAndSetters() {
    // Arrange
    FeiShuNotifier feiShuNotifier =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));

    // Act
    Card actualCard = feiShuNotifier.new Card();
    actualCard.setThemeColor("Theme Color");
    actualCard.setTitle("Dr");
    String actualThemeColor = actualCard.getThemeColor();

    // Assert
    assertEquals("Dr", actualCard.getTitle());
    assertEquals("Theme Color", actualThemeColor);
  }

  /**
   * Test {@link FeiShuNotifier#FeiShuNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link FeiShuNotifier#FeiShuNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @DisplayName("Test new FeiShuNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FeiShuNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewFeiShuNotifier() {
    // Arrange and Act
    FeiShuNotifier actualFeiShuNotifier =
        new FeiShuNotifier(instanceRepository, mock(RestTemplate.class));

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
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualFeiShuNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link FeiShuNotifier} WebhookUrl is {@link PagerdutyNotifier#DEFAULT_URI}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test doNotify(InstanceEvent, Instance); given FeiShuNotifier WebhookUrl is DEFAULT_URI; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono FeiShuNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify_givenFeiShuNotifierWebhookUrlIsDefault_uri_whenNull() throws AssertionError {
    // Arrange
    feiShuNotifier.setWebhookUrl(PagerdutyNotifier.DEFAULT_URI);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            feiShuNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link FeiShuNotifier}.
   *   <li>When {@link Instance}.
   * </ul>
   *
   * <p>Method under test: {@link FeiShuNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance); given FeiShuNotifier; when Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono FeiShuNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify_givenFeiShuNotifier_whenInstance() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            feiShuNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createNotification(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  void testCreateNotification() {
    // Arrange
    FeiShuNotifier feiShuNotifier =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));

    Card card = feiShuNotifier.new Card();
    card.setThemeColor("");

    FeiShuNotifier feiShuNotifier2 =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    feiShuNotifier2.setMessage("Not all who wander are lost");
    feiShuNotifier2.setSecret("not blank");
    feiShuNotifier2.setMessageType(MessageType.interactive);
    feiShuNotifier2.setCard(card);
    feiShuNotifier2.setAtAll(false);

    // Act and Assert
    Map<String, Object> body =
        feiShuNotifier2
            .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null)
            .getBody();
    assertEquals(5, body.size());
    assertEquals(
        "{\"elements\":[{\"tag\":\"div\",\"text\":{\"tag\":\"plain_text\",\"content\":\"Not all who wander are"
            + " lost\"}}],\"header\":{\"template\":\"\",\"title\":{\"tag\":\"plain_text\",\"content\":\"Codecentric's Spring"
            + " Boot Admin notice\"}}}",
        body.get("card"));
    assertTrue(body.containsKey("msg_type"));
    assertTrue(body.containsKey("receive_id"));
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException()}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createNotification(InstanceEvent, Instance); given IllegalStateException(); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  void testCreateNotification_givenIllegalStateException_thenThrowIllegalStateException() {
    // Arrange
    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> feiShuNotifier.createNotification(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body containsKey {@code msg_type}.
   * </ul>
   *
   * <p>Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createNotification(InstanceEvent, Instance); then return Body containsKey 'msg_type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  void testCreateNotification_thenReturnBodyContainsKeyMsgType() {
    // Arrange
    FeiShuNotifier feiShuNotifier =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));

    Card card = feiShuNotifier.new Card();
    card.setThemeColor("not blank");

    FeiShuNotifier feiShuNotifier2 =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    feiShuNotifier2.setMessage("Not all who wander are lost");
    feiShuNotifier2.setSecret("not blank");
    feiShuNotifier2.setMessageType(MessageType.interactive);
    feiShuNotifier2.setCard(card);
    feiShuNotifier2.setAtAll(false);

    // Act and Assert
    Map<String, Object> body =
        feiShuNotifier2
            .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null)
            .getBody();
    assertEquals(5, body.size());
    assertEquals(
        "{\"elements\":[{\"tag\":\"div\",\"text\":{\"tag\":\"plain_text\",\"content\":\"Not all who wander are lost\"}}],"
            + "\"header\":{\"template\":\"red\",\"title\":{\"tag\":\"plain_text\",\"content\":\"Codecentric's Spring Boot Admin"
            + " notice\"}}}",
        body.get("card"));
    assertTrue(body.containsKey("msg_type"));
    assertTrue(body.containsKey("receive_id"));
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body {@code content} is a string.
   * </ul>
   *
   * <p>Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createNotification(InstanceEvent, Instance); then return Body 'content' is a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  void testCreateNotification_thenReturnBodyContentIsAString() {
    // Arrange
    FeiShuNotifier feiShuNotifier =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    feiShuNotifier.setMessageType(MessageType.text);

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");
    InstanceStatusChangedEvent event =
        new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo);

    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act
    HttpEntity<Map<String, Object>> actualCreateNotificationResult =
        feiShuNotifier.createNotification(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance, atLeast(1)).getRegistration();
    verify(statusInfo).getStatus();
    Map<String, Object> body = actualCreateNotificationResult.getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof MessageType);
    assertEquals(
        "{\"text\":\"ServiceName: Name(42) \\nServiceUrl: https://example.org/example \\nStatus: changed status from"
            + " [UNKNOWN] to [Status]\\n<at user_id=\\\"all\\\">@all</at>\"}",
        body.get("content"));
    assertEquals(MessageType.text, getResult);
    assertTrue(body.containsKey("receive_id"));
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body {@code content} is {@code {"text":"Not all who wander are lost"}}.
   * </ul>
   *
   * <p>Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createNotification(InstanceEvent, Instance); then return Body 'content' is '{\"text\":\"Not all who wander are lost\"}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  void testCreateNotification_thenReturnBodyContentIsTextNotAllWhoWanderAreLost() {
    // Arrange
    FeiShuNotifier feiShuNotifier =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));

    Card card = feiShuNotifier.new Card();
    card.setThemeColor("not blank");

    FeiShuNotifier feiShuNotifier2 =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    feiShuNotifier2.setMessage("Not all who wander are lost");
    feiShuNotifier2.setSecret("not blank");
    feiShuNotifier2.setMessageType(MessageType.text);
    feiShuNotifier2.setCard(card);
    feiShuNotifier2.setAtAll(false);

    // Act and Assert
    Map<String, Object> body =
        feiShuNotifier2
            .createNotification(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null)
            .getBody();
    assertEquals(5, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof MessageType);
    assertEquals("{\"text\":\"Not all who wander are lost\"}", body.get("content"));
    assertEquals(MessageType.text, getResult);
    assertTrue(body.containsKey("receive_id"));
    assertTrue(body.containsKey("timestamp"));
  }

  /**
   * Test {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>When {@link Instance}.
   *   <li>Then return Body {@code msg_type} is {@code interactive}.
   * </ul>
   *
   * <p>Method under test: {@link FeiShuNotifier#createNotification(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createNotification(InstanceEvent, Instance); when Instance; then return Body 'msg_type' is 'interactive'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity FeiShuNotifier.createNotification(InstanceEvent, Instance)"})
  void testCreateNotification_whenInstance_thenReturnBodyMsgTypeIsInteractive() {
    // Arrange
    FeiShuNotifier feiShuNotifier =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    feiShuNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    Map<String, Object> body =
        feiShuNotifier
            .createNotification(
                new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, mock(StatusInfo.class)),
                mock(Instance.class))
            .getBody();
    assertEquals(3, body.size());
    Object getResult = body.get("msg_type");
    assertTrue(getResult instanceof MessageType);
    assertEquals(
        "{\"elements\":[{\"tag\":\"div\",\"text\":{\"tag\":\"plain_text\",\"content\":\"Not all who wander are lost\"}},{\"tag"
            + "\":\"div\",\"text\":{\"tag\":\"lark_md\",\"content\":\"<at id=all></at>\"}}],\"header\":{\"template\":\"red\",\"title\":{"
            + "\"tag\":\"plain_text\",\"content\":\"Codecentric's Spring Boot Admin notice\"}}}",
        body.get("card"));
    assertEquals(MessageType.interactive, getResult);
    assertTrue(body.containsKey("receive_id"));
  }

  /**
   * Test {@link FeiShuNotifier#getMessage()}.
   *
   * <p>Method under test: {@link FeiShuNotifier#getMessage()}
   */
  @Test
  @DisplayName("Test getMessage()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String FeiShuNotifier.getMessage()"})
  void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        feiShuNotifier.getMessage());
  }

  /**
   * Test {@link FeiShuNotifier#setMessage(String)}.
   *
   * <p>Method under test: {@link FeiShuNotifier#setMessage(String)}
   */
  @Test
  @DisplayName("Test setMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FeiShuNotifier.setMessage(String)"})
  void testSetMessage() {
    // Arrange and Act
    feiShuNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", feiShuNotifier.getMessage());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Card FeiShuNotifier.getCard()",
    "MessageType FeiShuNotifier.getMessageType()",
    "String FeiShuNotifier.getSecret()",
    "URI FeiShuNotifier.getWebhookUrl()",
    "boolean FeiShuNotifier.isAtAll()",
    "void FeiShuNotifier.setAtAll(boolean)",
    "void FeiShuNotifier.setCard(Card)",
    "void FeiShuNotifier.setMessageType(MessageType)",
    "void FeiShuNotifier.setRestTemplate(RestTemplate)",
    "void FeiShuNotifier.setSecret(String)",
    "void FeiShuNotifier.setWebhookUrl(URI)"
  })
  void testGettersAndSetters() {
    // Arrange
    FeiShuNotifier feiShuNotifier =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));

    // Act
    feiShuNotifier.setAtAll(true);
    FeiShuNotifier feiShuNotifier2 =
        new FeiShuNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    Card card = feiShuNotifier2.new Card();
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

    // Assert
    assertEquals("Secret", actualSecret);
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json",
        actualWebhookUrl.toString());
    assertEquals(MessageType.text, actualMessageType);
    assertTrue(feiShuNotifier.isAtAll());
    assertSame(card, actualCard);
    assertSame(webhookUrl, actualWebhookUrl);
  }
}
