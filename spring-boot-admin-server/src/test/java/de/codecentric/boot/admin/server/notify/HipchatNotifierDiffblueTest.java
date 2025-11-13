package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

@ContextConfiguration(classes = {HipchatNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class HipchatNotifierDiffblueTest {
  @Autowired private HipchatNotifier hipchatNotifier;

  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private RestTemplate restTemplate;

  /**
   * Test {@link HipchatNotifier#HipchatNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link HipchatNotifier#HipchatNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @DisplayName("Test new HipchatNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void HipchatNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewHipchatNotifier() {
    // Arrange and Act
    HipchatNotifier actualHipchatNotifier =
        new HipchatNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals(
        "<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
            + "}</strong>",
        actualHipchatNotifier.getDescription());
    assertNull(actualHipchatNotifier.getAuthToken());
    assertNull(actualHipchatNotifier.getRoomId());
    assertNull(actualHipchatNotifier.getUrl());
    assertFalse(actualHipchatNotifier.getNotify());
    assertFalse(actualHipchatNotifier.isNotify());
    assertTrue(actualHipchatNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualHipchatNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link HipchatNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link HipchatNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono HipchatNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            hipchatNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link HipchatNotifier#buildUrl()}.
   *
   * <ul>
   *   <li>Given {@link HipchatNotifier} Url is {@link PagerdutyNotifier#DEFAULT_URI}.
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#buildUrl()}
   */
  @Test
  @DisplayName("Test buildUrl(); given HipchatNotifier Url is DEFAULT_URI; then return a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String HipchatNotifier.buildUrl()"})
  void testBuildUrl_givenHipchatNotifierUrlIsDefault_uri_thenReturnAString() {
    // Arrange
    hipchatNotifier.setUrl(PagerdutyNotifier.DEFAULT_URI);

    // Act and Assert
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json/room/null/notification?auth"
            + "_token=null",
        hipchatNotifier.buildUrl());
  }

  /**
   * Test {@link HipchatNotifier#buildUrl()}.
   *
   * <ul>
   *   <li>Given {@link HipchatNotifier}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#buildUrl()}
   */
  @Test
  @DisplayName("Test buildUrl(); given HipchatNotifier; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String HipchatNotifier.buildUrl()"})
  void testBuildUrl_givenHipchatNotifier_thenThrowIllegalStateException() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> hipchatNotifier.buildUrl());
  }

  /**
   * Test {@link HipchatNotifier#createHipChatNotification(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link HipchatNotifier#createHipChatNotification(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName("Test createHipChatNotification(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpEntity HipchatNotifier.createHipChatNotification(InstanceEvent, Instance)"
  })
  void testCreateHipChatNotification() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");
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
    HttpEntity<Map<String, Object>> actualCreateHipChatNotificationResult =
        hipchatNotifier.createHipChatNotification(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo, atLeast(1)).getStatus();
    Map<String, Object> body = actualCreateHipChatNotificationResult.getBody();
    assertEquals(4, body.size());
    assertEquals("<strong>Name</strong>/42 is <strong>UP</strong>", body.get("message"));
    HttpHeaders headers = actualCreateHipChatNotificationResult.getHeaders();
    assertEquals(1, headers.size());
    List<String> getResult = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult.size());
    assertEquals("application/json", getResult.get(0));
    assertEquals("green", body.get("color"));
    assertEquals("html", body.get("message_format"));
    assertFalse((Boolean) body.get("notify"));
    assertTrue(actualCreateHipChatNotificationResult.hasBody());
  }

  /**
   * Test {@link HipchatNotifier#createHipChatNotification(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link HipchatNotifier#createHipChatNotification(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName("Test createHipChatNotification(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpEntity HipchatNotifier.createHipChatNotification(InstanceEvent, Instance)"
  })
  void testCreateHipChatNotification2() {
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
    HttpEntity<Map<String, Object>> actualCreateHipChatNotificationResult =
        hipchatNotifier.createHipChatNotification(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo, atLeast(1)).getStatus();
    Map<String, Object> body = actualCreateHipChatNotificationResult.getBody();
    assertEquals(4, body.size());
    assertEquals("<strong>Name</strong>/42 is <strong>Status</strong>", body.get("message"));
    HttpHeaders headers = actualCreateHipChatNotificationResult.getHeaders();
    assertEquals(1, headers.size());
    List<String> getResult = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult.size());
    assertEquals("application/json", getResult.get(0));
    assertEquals("html", body.get("message_format"));
    assertEquals("red", body.get("color"));
    assertFalse((Boolean) body.get("notify"));
    assertTrue(actualCreateHipChatNotificationResult.hasBody());
  }

  /**
   * Test {@link HipchatNotifier#createHipChatNotification(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link HipchatNotifier#createHipChatNotification(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName("Test createHipChatNotification(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpEntity HipchatNotifier.createHipChatNotification(InstanceEvent, Instance)"
  })
  void testCreateHipChatNotification3() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    HipchatNotifier hipchatNotifier = new HipchatNotifier(repository, mock(RestTemplate.class));
    hipchatNotifier.setNotify(true);
    hipchatNotifier.setDescription("The characteristics of someone or something");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");

    // Act
    HttpEntity<Map<String, Object>> actualCreateHipChatNotificationResult =
        hipchatNotifier.createHipChatNotification(
            new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo), null);

    // Assert
    verify(statusInfo).getStatus();
    Map<String, Object> body = actualCreateHipChatNotificationResult.getBody();
    assertEquals(4, body.size());
    assertEquals("The characteristics of someone or something", body.get("message"));
    HttpHeaders headers = actualCreateHipChatNotificationResult.getHeaders();
    assertEquals(1, headers.size());
    List<String> getResult = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult.size());
    assertEquals("application/json", getResult.get(0));
    assertEquals("green", body.get("color"));
    assertEquals("html", body.get("message_format"));
    assertTrue(actualCreateHipChatNotificationResult.hasBody());
    assertTrue((Boolean) body.get("notify"));
  }

  /**
   * Test {@link HipchatNotifier#createHipChatNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceStatusChangedEvent#getStatusInfo()}.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#createHipChatNotification(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test createHipChatNotification(InstanceEvent, Instance); then calls getStatusInfo()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpEntity HipchatNotifier.createHipChatNotification(InstanceEvent, Instance)"
  })
  void testCreateHipChatNotification_thenCallsGetStatusInfo() {
    // Arrange
    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getStatusInfo()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> hipchatNotifier.createHipChatNotification(event, mock(Instance.class)));
    verify(event).getStatusInfo();
  }

  /**
   * Test {@link HipchatNotifier#createHipChatNotification(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#createHipChatNotification(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test createHipChatNotification(InstanceEvent, Instance); when StatusInfo getStatus() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpEntity HipchatNotifier.createHipChatNotification(InstanceEvent, Instance)"
  })
  void testCreateHipChatNotification_whenStatusInfoGetStatusThrowIllegalStateException() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            hipchatNotifier.createHipChatNotification(
                new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo), null));
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link HipchatNotifier#getMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code The characteristics of someone or something}.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#getMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test getMessage(InstanceEvent, Instance); then return 'The characteristics of someone or something'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String HipchatNotifier.getMessage(InstanceEvent, Instance)"})
  void testGetMessage_thenReturnTheCharacteristicsOfSomeoneOrSomething() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    HipchatNotifier hipchatNotifier = new HipchatNotifier(repository, mock(RestTemplate.class));
    hipchatNotifier.setDescription("The characteristics of someone or something");

    // Act and Assert
    assertEquals(
        "The characteristics of someone or something",
        hipchatNotifier.getMessage(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test {@link HipchatNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return {@code red}.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test getColor(InstanceEvent); given 'Status'; when StatusInfo getStatus() return 'Status'; then return 'red'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String HipchatNotifier.getColor(InstanceEvent)"})
  void testGetColor_givenStatus_whenStatusInfoGetStatusReturnStatus_thenReturnRed() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    String actualColor =
        hipchatNotifier.getColor(
            new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo));

    // Assert
    verify(statusInfo).getStatus();
    assertEquals("red", actualColor);
  }

  /**
   * Test {@link HipchatNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>Given {@code UP}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code UP}.
   *   <li>Then return {@code green}.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test getColor(InstanceEvent); given 'UP'; when StatusInfo getStatus() return 'UP'; then return 'green'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String HipchatNotifier.getColor(InstanceEvent)"})
  void testGetColor_givenUp_whenStatusInfoGetStatusReturnUp_thenReturnGreen() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");

    // Act
    String actualColor =
        hipchatNotifier.getColor(
            new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo));

    // Assert
    verify(statusInfo).getStatus();
    assertEquals("green", actualColor);
  }

  /**
   * Test {@link HipchatNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceStatusChangedEvent#getStatusInfo()}.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName("Test getColor(InstanceEvent); then calls getStatusInfo()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String HipchatNotifier.getColor(InstanceEvent)"})
  void testGetColor_thenCallsGetStatusInfo() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    HipchatNotifier hipchatNotifier = new HipchatNotifier(repository, mock(RestTemplate.class));

    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getStatusInfo()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> hipchatNotifier.getColor(event));
    verify(event).getStatusInfo();
  }

  /**
   * Test {@link HipchatNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then return {@code gray}.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName("Test getColor(InstanceEvent); then return 'gray'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String HipchatNotifier.getColor(InstanceEvent)"})
  void testGetColor_thenReturnGray() {
    // Arrange, Act and Assert
    assertEquals(
        "gray", hipchatNotifier.getColor(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
  }

  /**
   * Test {@link HipchatNotifier#getColor(InstanceEvent)}.
   *
   * <ul>
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifier#getColor(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test getColor(InstanceEvent); when StatusInfo getStatus() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String HipchatNotifier.getColor(InstanceEvent)"})
  void testGetColor_whenStatusInfoGetStatusThrowIllegalStateException() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            hipchatNotifier.getColor(
                new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo)));
    verify(statusInfo).getStatus();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link HipchatNotifier#setAuthToken(String)}
   *   <li>{@link HipchatNotifier#setNotify(boolean)}
   *   <li>{@link HipchatNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link HipchatNotifier#setRoomId(String)}
   *   <li>{@link HipchatNotifier#setUrl(URI)}
   *   <li>{@link HipchatNotifier#getAuthToken()}
   *   <li>{@link HipchatNotifier#getNotify()}
   *   <li>{@link HipchatNotifier#getRoomId()}
   *   <li>{@link HipchatNotifier#getUrl()}
   *   <li>{@link HipchatNotifier#isNotify()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String HipchatNotifier.getAuthToken()",
    "boolean HipchatNotifier.getNotify()",
    "String HipchatNotifier.getRoomId()",
    "URI HipchatNotifier.getUrl()",
    "boolean HipchatNotifier.isNotify()",
    "void HipchatNotifier.setAuthToken(String)",
    "void HipchatNotifier.setNotify(boolean)",
    "void HipchatNotifier.setRestTemplate(RestTemplate)",
    "void HipchatNotifier.setRoomId(String)",
    "void HipchatNotifier.setUrl(URI)"
  })
  void testGettersAndSetters() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    HipchatNotifier hipchatNotifier = new HipchatNotifier(repository, mock(RestTemplate.class));

    // Act
    hipchatNotifier.setAuthToken("ABC123");
    hipchatNotifier.setNotify(true);
    hipchatNotifier.setRestTemplate(mock(RestTemplate.class));
    hipchatNotifier.setRoomId("42");
    URI url = PagerdutyNotifier.DEFAULT_URI;
    hipchatNotifier.setUrl(url);
    String actualAuthToken = hipchatNotifier.getAuthToken();
    boolean actualNotify = hipchatNotifier.getNotify();
    String actualRoomId = hipchatNotifier.getRoomId();
    URI actualUrl = hipchatNotifier.getUrl();

    // Assert
    assertEquals("42", actualRoomId);
    assertEquals("ABC123", actualAuthToken);
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualUrl.toString());
    assertTrue(actualNotify);
    assertTrue(hipchatNotifier.isNotify());
    assertSame(url, actualUrl);
  }

  /**
   * Test {@link HipchatNotifier#getDescription()}.
   *
   * <p>Method under test: {@link HipchatNotifier#getDescription()}
   */
  @Test
  @DisplayName("Test getDescription()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String HipchatNotifier.getDescription()"})
  void testGetDescription() {
    // Arrange, Act and Assert
    assertEquals(
        "<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
            + "}</strong>",
        hipchatNotifier.getDescription());
  }

  /**
   * Test {@link HipchatNotifier#setDescription(String)}.
   *
   * <p>Method under test: {@link HipchatNotifier#setDescription(String)}
   */
  @Test
  @DisplayName("Test setDescription(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void HipchatNotifier.setDescription(String)"})
  void testSetDescription() {
    // Arrange and Act
    hipchatNotifier.setDescription("The characteristics of someone or something");

    // Assert
    assertEquals("The characteristics of someone or something", hipchatNotifier.getDescription());
  }
}
