package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {DiscordNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class DiscordNotifierDiffblueTest {
  @Autowired private DiscordNotifier discordNotifier;

  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private RestTemplate restTemplate;

  /**
   * Test {@link DiscordNotifier#DiscordNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link DiscordNotifier#DiscordNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @DisplayName("Test new DiscordNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscordNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewDiscordNotifier() {
    // Arrange and Act
    DiscordNotifier actualDiscordNotifier =
        new DiscordNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualDiscordNotifier.getMessage());
    assertNull(actualDiscordNotifier.getAvatarUrl());
    assertNull(actualDiscordNotifier.getUsername());
    assertNull(actualDiscordNotifier.getWebhookUrl());
    assertFalse(actualDiscordNotifier.isTts());
    assertTrue(actualDiscordNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualDiscordNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link DiscordNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link DiscordNotifier} WebhookUrl is {@link PagerdutyNotifier#DEFAULT_URI}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DiscordNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test doNotify(InstanceEvent, Instance); given DiscordNotifier WebhookUrl is DEFAULT_URI; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono DiscordNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify_givenDiscordNotifierWebhookUrlIsDefault_uri_whenNull() throws AssertionError {
    // Arrange
    discordNotifier.setWebhookUrl(PagerdutyNotifier.DEFAULT_URI);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            discordNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link DiscordNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link DiscordNotifier}.
   *   <li>When {@link Instance}.
   * </ul>
   *
   * <p>Method under test: {@link DiscordNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance); given DiscordNotifier; when Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono DiscordNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify_givenDiscordNotifier_whenInstance() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            discordNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link DiscordNotifier#createDiscordNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body {@code avatar_url} is {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link DiscordNotifier#createDiscordNotification(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test createDiscordNotification(InstanceEvent, Instance); then return Body 'avatar_url' is 'https://example.org/example'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DiscordNotifier.createDiscordNotification(InstanceEvent, Instance)"})
  void testCreateDiscordNotification_thenReturnBodyAvatarUrlIsHttpsExampleOrgExample() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    DiscordNotifier discordNotifier = new DiscordNotifier(repository, mock(RestTemplate.class));
    discordNotifier.setAvatarUrl("https://example.org/example");

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
    Object actualCreateDiscordNotificationResult =
        discordNotifier.createDiscordNotification(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    Object body = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getBody();
    assertTrue(body instanceof Map);
    assertTrue(actualCreateDiscordNotificationResult instanceof HttpEntity);
    assertEquals(3, ((Map<String, Object>) body).size());
    assertEquals("*Name* (42) is *Status*", ((Map<String, Object>) body).get("content"));
    HttpHeaders headers = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult.size());
    assertEquals("RestTemplate", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertEquals("https://example.org/example", ((Map<String, Object>) body).get("avatar_url"));
    assertFalse((Boolean) ((Map<String, Object>) body).get("tts"));
    assertTrue(((HttpEntity<Object>) actualCreateDiscordNotificationResult).hasBody());
  }

  /**
   * Test {@link DiscordNotifier#createDiscordNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body {@code username} is {@code janedoe}.
   * </ul>
   *
   * <p>Method under test: {@link DiscordNotifier#createDiscordNotification(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test createDiscordNotification(InstanceEvent, Instance); then return Body 'username' is 'janedoe'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DiscordNotifier.createDiscordNotification(InstanceEvent, Instance)"})
  void testCreateDiscordNotification_thenReturnBodyUsernameIsJanedoe() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    DiscordNotifier discordNotifier = new DiscordNotifier(repository, mock(RestTemplate.class));
    discordNotifier.setUsername("janedoe");

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
    Object actualCreateDiscordNotificationResult =
        discordNotifier.createDiscordNotification(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    Object body = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getBody();
    assertTrue(body instanceof Map);
    assertTrue(actualCreateDiscordNotificationResult instanceof HttpEntity);
    assertEquals(3, ((Map<String, Object>) body).size());
    assertEquals("*Name* (42) is *Status*", ((Map<String, Object>) body).get("content"));
    HttpHeaders headers = ((HttpEntity<Object>) actualCreateDiscordNotificationResult).getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.USER_AGENT);
    assertEquals(1, getResult.size());
    assertEquals("RestTemplate", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertEquals("janedoe", ((Map<String, Object>) body).get("username"));
    assertFalse((Boolean) ((Map<String, Object>) body).get("tts"));
    assertTrue(((HttpEntity<Object>) actualCreateDiscordNotificationResult).hasBody());
  }

  /**
   * Test {@link DiscordNotifier#createDiscordNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link DiscordNotifier#createDiscordNotification(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test createDiscordNotification(InstanceEvent, Instance); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DiscordNotifier.createDiscordNotification(InstanceEvent, Instance)"})
  void testCreateDiscordNotification_thenThrowIllegalStateException() {
    // Arrange
    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> discordNotifier.createDiscordNotification(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link DiscordNotifier#createContent(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link DiscordNotifier#createContent(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createContent(InstanceEvent, Instance); then return 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DiscordNotifier.createContent(InstanceEvent, Instance)"})
  void testCreateContent_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    DiscordNotifier discordNotifier = new DiscordNotifier(repository, mock(RestTemplate.class));
    discordNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    assertEquals(
        "Not all who wander are lost",
        discordNotifier.createContent(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String DiscordNotifier.getAvatarUrl()",
    "String DiscordNotifier.getUsername()",
    "URI DiscordNotifier.getWebhookUrl()",
    "boolean DiscordNotifier.isTts()",
    "void DiscordNotifier.setAvatarUrl(String)",
    "void DiscordNotifier.setRestTemplate(RestTemplate)",
    "void DiscordNotifier.setTts(boolean)",
    "void DiscordNotifier.setUsername(String)",
    "void DiscordNotifier.setWebhookUrl(URI)"
  })
  void testGettersAndSetters() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    DiscordNotifier discordNotifier = new DiscordNotifier(repository, mock(RestTemplate.class));

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

    // Assert
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json",
        actualWebhookUrl.toString());
    assertEquals("https://example.org/example", actualAvatarUrl);
    assertEquals("janedoe", actualUsername);
    assertTrue(discordNotifier.isTts());
    assertSame(webhookUrl, actualWebhookUrl);
  }

  /**
   * Test {@link DiscordNotifier#getMessage()}.
   *
   * <p>Method under test: {@link DiscordNotifier#getMessage()}
   */
  @Test
  @DisplayName("Test getMessage()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DiscordNotifier.getMessage()"})
  void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        discordNotifier.getMessage());
  }

  /**
   * Test {@link DiscordNotifier#setMessage(String)}.
   *
   * <p>Method under test: {@link DiscordNotifier#setMessage(String)}
   */
  @Test
  @DisplayName("Test setMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DiscordNotifier.setMessage(String)"})
  void testSetMessage() {
    // Arrange and Act
    discordNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", discordNotifier.getMessage());
  }
}
