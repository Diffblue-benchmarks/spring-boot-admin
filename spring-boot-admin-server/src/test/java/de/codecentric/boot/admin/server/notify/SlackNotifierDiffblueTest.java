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
import de.codecentric.boot.admin.server.domain.events.InstanceEndpointsDetectedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
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
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createMessage(InstanceEvent, Instance); given InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object SlackNotifier.createMessage(InstanceEvent, Instance)"})
  void testCreateMessage_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    SlackNotifier slackNotifier = new SlackNotifier(repository, mock(RestTemplate.class));
    slackNotifier.setMessage("Not all who wander are lost");
    slackNotifier.setIcon("foo");
    slackNotifier.setChannel("foo");

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
   * <p>Method under test: {@link SlackNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getText(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getText(InstanceEvent, Instance)"})
  void testGetText() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    SlackNotifier slackNotifier = new SlackNotifier(repository, mock(RestTemplate.class));
    slackNotifier.setIcon("Icon");

    InstanceEndpointsDetectedEvent event = mock(InstanceEndpointsDetectedEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> slackNotifier.getText(event, null));
    verify(event).getInstance();
  }

  /**
   * Test {@link SlackNotifier#getText(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link SlackNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getText(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getText(InstanceEvent, Instance)"})
  void testGetText2() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(mock(InMemoryEventStore.class));

    SlackNotifier slackNotifier = new SlackNotifier(repository, mock(RestTemplate.class));
    slackNotifier.setIcon("UP");

    InstanceEndpointsDetectedEvent event = mock(InstanceEndpointsDetectedEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> slackNotifier.getText(event, null));
    verify(event).getInstance();
  }

  /**
   * Test {@link SlackNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getText(InstanceEvent, Instance); then return 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    SlackNotifier slackNotifier = new SlackNotifier(repository, mock(RestTemplate.class));
    slackNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    assertEquals(
        "Not all who wander are lost",
        slackNotifier.getText(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test {@link SlackNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getText(InstanceEvent, Instance); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_thenThrowIllegalStateException() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    SlackNotifier slackNotifier = new SlackNotifier(repository, mock(RestTemplate.class));

    InstanceEndpointsDetectedEvent event = mock(InstanceEndpointsDetectedEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> slackNotifier.getText(event, null));
    verify(event).getInstance();
  }

  /**
   * Test {@link SlackNotifier#getColor(InstanceEvent)}.
   *
   * <p>Method under test: {@link SlackNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName("Test getColor(InstanceEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SlackNotifier.getColor(InstanceEvent)"})
  void testGetColor() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");
    InstanceStatusChangedEvent event =
        new InstanceStatusChangedEvent(InstanceId.of("42"), Long.MIN_VALUE, statusInfo);

    // Act
    String actualColor = slackNotifier.getColor(event);

    // Assert
    verify(statusInfo).getStatus();
    assertEquals("danger", actualColor);
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
  void testGetColor_givenStatus_whenStatusInfoGetStatusReturnStatus_thenReturnDanger2() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    String actualColor =
        slackNotifier.getColor(
            new InstanceStatusChangedEvent(mock(InstanceId.class), 1L, statusInfo));

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    SlackNotifier slackNotifier = new SlackNotifier(repository, mock(RestTemplate.class));

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
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    SlackNotifier slackNotifier = new SlackNotifier(repository, mock(RestTemplate.class));

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
    slackNotifier.setMessage(
        "Not all who wander are lostjava.lang.VoidNot all who wander are lost");

    // Assert
    assertEquals(
        "Not all who wander are lostjava.lang.VoidNot all who wander are lost",
        slackNotifier.getMessage());
  }

  /**
   * Test {@link SlackNotifier#setMessage(String)}.
   *
   * <ul>
   *   <li>Then {@link SlackNotifier} Message is {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifier#setMessage(String)}
   */
  @Test
  @DisplayName(
      "Test setMessage(String); then SlackNotifier Message is 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SlackNotifier.setMessage(String)"})
  void testSetMessage_thenSlackNotifierMessageIsNotAllWhoWanderAreLost() {
    // Arrange and Act
    slackNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", slackNotifier.getMessage());
  }
}
