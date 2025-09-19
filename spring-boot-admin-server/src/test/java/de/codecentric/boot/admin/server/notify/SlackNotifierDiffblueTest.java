package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {SlackNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class SlackNotifierDiffblueTest {
  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private RestTemplate restTemplate;

  @Autowired private SlackNotifier slackNotifier;

  /**
   * Test {@link SlackNotifier#SlackNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link SlackNotifier#SlackNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @DisplayName("Test new SlackNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SlackNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewSlackNotifier() {
    // Arrange and Act
    SlackNotifier actualSlackNotifier =
        new SlackNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualSlackNotifier.getMessage());
    assertEquals("Spring Boot Admin", actualSlackNotifier.getUsername());
    assertNull(actualSlackNotifier.getChannel());
    assertNull(actualSlackNotifier.getIcon());
    assertNull(actualSlackNotifier.getWebhookUrl());
    assertTrue(actualSlackNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualSlackNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link SlackNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link SlackNotifier} WebhookUrl is {@link PagerdutyNotifier#DEFAULT_URI}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test doNotify(InstanceEvent, Instance); given SlackNotifier WebhookUrl is DEFAULT_URI; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono SlackNotifier.doNotify(InstanceEvent, Instance)"})
  void testDoNotify_givenSlackNotifierWebhookUrlIsDefault_uri_whenNull() throws AssertionError {
    // Arrange
    slackNotifier.setWebhookUrl(PagerdutyNotifier.DEFAULT_URI);

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            slackNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link SlackNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link SlackNotifier}.
   *   <li>When {@link Instance}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance); given SlackNotifier; when Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono SlackNotifier.doNotify(InstanceEvent, Instance)"})
  void testDoNotify_givenSlackNotifier_whenInstance() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            slackNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link SlackNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link SlackNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createMessage(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object SlackNotifier.createMessage(InstanceEvent, Instance)"})
  void testCreateMessage() {
    // Arrange
    SlackNotifier slackNotifier =
        new SlackNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    slackNotifier.setMessage("Not all who wander are lost");
    slackNotifier.setIcon(null);
    slackNotifier.setChannel(null);

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            slackNotifier.createMessage(
                new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo), null));
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link SlackNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceStatusChangedEvent#getInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createMessage(InstanceEvent, Instance); then calls getInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object SlackNotifier.createMessage(InstanceEvent, Instance)"})
  void testCreateMessage_thenCallsGetInstance() {
    // Arrange
    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> slackNotifier.createMessage(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link SlackNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>Then return {@code *Name* (42) is *Status*}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test getText(InstanceEvent, Instance); given 'Status'; then return '*Name* (42) is *Status*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_givenStatus_thenReturnName42IsStatus() {
    // Arrange
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
    String actualText = slackNotifier.getText(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    assertEquals("*Name* (42) is *Status*", actualText);
  }

  /**
   * Test {@link SlackNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return {@code danger}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test getColor(InstanceEvent); given 'Status'; when StatusInfo getStatus() return 'Status'; then return 'danger'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getColor(InstanceEvent)"})
  void testGetColor_givenStatus_whenStatusInfoGetStatusReturnStatus_thenReturnDanger() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    String actualColor =
        slackNotifier.getColor(new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo));

    // Assert
    verify(statusInfo).getStatus();
    assertEquals("danger", actualColor);
  }

  /**
   * Test {@link SlackNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code UP}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code UP}.
   *   <li>Then return {@code good}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test getColor(InstanceEvent); given 'UP'; when StatusInfo getStatus() return 'UP'; then return 'good'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getColor(InstanceEvent)"})
  void testGetColor_givenUp_whenStatusInfoGetStatusReturnUp_thenReturnGood() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");

    // Act
    String actualColor =
        slackNotifier.getColor(new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo));

    // Assert
    verify(statusInfo).getStatus();
    assertEquals("good", actualColor);
  }

  /**
   * Test {@link SlackNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceStatusChangedEvent#getStatusInfo()}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName("Test getColor(InstanceEvent); then calls getStatusInfo()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getColor(InstanceEvent)"})
  void testGetColor_thenCallsGetStatusInfo() {
    // Arrange
    SlackNotifier slackNotifier =
        new SlackNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));

    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getStatusInfo()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> slackNotifier.getColor(event));
    verify(event).getStatusInfo();
  }

  /**
   * Test {@link SlackNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then return {@code #439FE0}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName("Test getColor(InstanceEvent); then return '#439FE0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getColor(InstanceEvent)"})
  void testGetColor_thenReturn439fe0() {
    // Arrange, Act and Assert
    assertEquals(
        "#439FE0", slackNotifier.getColor(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
  }

  /**
   * Test {@link SlackNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code UP42}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test getColor(InstanceEvent); when InstanceId with value is 'UP42'; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getColor(InstanceEvent)"})
  void testGetColor_whenInstanceIdWithValueIsUp42_thenThrowIllegalStateException() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            slackNotifier.getColor(
                new InstanceStatusChangedEvent(InstanceId.of("UP42"), 1L, statusInfo)));
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link SlackNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test getColor(InstanceEvent); when StatusInfo getStatus() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getColor(InstanceEvent)"})
  void testGetColor_whenStatusInfoGetStatusThrowIllegalStateException() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            slackNotifier.getColor(
                new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo)));
    verify(statusInfo).getStatus();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String SlackNotifier.getChannel()",
    "String SlackNotifier.getIcon()",
    "String SlackNotifier.getUsername()",
    "URI SlackNotifier.getWebhookUrl()",
    "void SlackNotifier.setChannel(String)",
    "void SlackNotifier.setIcon(String)",
    "void SlackNotifier.setRestTemplate(RestTemplate)",
    "void SlackNotifier.setUsername(String)",
    "void SlackNotifier.setWebhookUrl(URI)"
  })
  void testGettersAndSetters() {
    // Arrange
    SlackNotifier slackNotifier =
        new SlackNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
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
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json",
        actualWebhookUrl.toString());
    assertEquals("janedoe", actualUsername);
    assertSame(webhookUrl, actualWebhookUrl);
  }

  /**
   * Test {@link SlackNotifier#getMessage()}.
   *
   * <p>Method under test: {@link SlackNotifier#getMessage()}
   */
  @Test
  @DisplayName("Test getMessage()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getMessage()"})
  void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        slackNotifier.getMessage());
  }

  /**
   * Test {@link SlackNotifier#setMessage(String)}.
   *
   * <p>Method under test: {@link SlackNotifier#setMessage(String)}
   */
  @Test
  @DisplayName("Test setMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SlackNotifier.setMessage(String)"})
  void testSetMessage() {
    // Arrange and Act
    slackNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", slackNotifier.getMessage());
  }
}
