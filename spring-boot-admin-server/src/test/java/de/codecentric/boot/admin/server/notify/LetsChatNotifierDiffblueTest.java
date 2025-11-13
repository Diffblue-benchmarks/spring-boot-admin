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
import java.util.Map;
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

@ContextConfiguration(classes = {LetsChatNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class LetsChatNotifierDiffblueTest {
  @MockitoBean private InstanceRepository instanceRepository;

  @Autowired private LetsChatNotifier letsChatNotifier;

  @MockitoBean private RestTemplate restTemplate;

  /**
   * Test {@link LetsChatNotifier#LetsChatNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link LetsChatNotifier#LetsChatNotifier(InstanceRepository,
   * RestTemplate)}
   */
  @Test
  @DisplayName("Test new LetsChatNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LetsChatNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewLetsChatNotifier() {
    // Arrange and Act
    LetsChatNotifier actualLetsChatNotifier =
        new LetsChatNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualLetsChatNotifier.getMessage());
    assertEquals("Spring Boot Admin", actualLetsChatNotifier.getUsername());
    assertNull(actualLetsChatNotifier.getRoom());
    assertNull(actualLetsChatNotifier.getToken());
    assertNull(actualLetsChatNotifier.getUrl());
    assertTrue(actualLetsChatNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualLetsChatNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link LetsChatNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link LetsChatNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono LetsChatNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            letsChatNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link LetsChatNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException()}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link LetsChatNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createMessage(InstanceEvent, Instance); given IllegalStateException(); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LetsChatNotifier.createMessage(InstanceEvent, Instance)"})
  void testCreateMessage_givenIllegalStateException_thenThrowIllegalStateException() {
    // Arrange
    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> letsChatNotifier.createMessage(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link LetsChatNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link LetsChatNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createMessage(InstanceEvent, Instance); given 'Status'; when StatusInfo getStatus() return 'Status'; then return Map")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object LetsChatNotifier.createMessage(InstanceEvent, Instance)"})
  void testCreateMessage_givenStatus_whenStatusInfoGetStatusReturnStatus_thenReturnMap() {
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
    Object actualCreateMessageResult = letsChatNotifier.createMessage(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    assertTrue(actualCreateMessageResult instanceof Map);
    assertEquals(1, ((Map<String, String>) actualCreateMessageResult).size());
    assertEquals(
        "*Name* (42) is *Status*", ((Map<String, String>) actualCreateMessageResult).get("text"));
  }

  /**
   * Test {@link LetsChatNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link LetsChatNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getText(InstanceEvent, Instance); then return 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String LetsChatNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    LetsChatNotifier letsChatNotifier = new LetsChatNotifier(repository, mock(RestTemplate.class));
    letsChatNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    assertEquals(
        "Not all who wander are lost",
        letsChatNotifier.getText(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link LetsChatNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link LetsChatNotifier#setRoom(String)}
   *   <li>{@link LetsChatNotifier#setToken(String)}
   *   <li>{@link LetsChatNotifier#setUrl(URI)}
   *   <li>{@link LetsChatNotifier#setUsername(String)}
   *   <li>{@link LetsChatNotifier#getRoom()}
   *   <li>{@link LetsChatNotifier#getToken()}
   *   <li>{@link LetsChatNotifier#getUrl()}
   *   <li>{@link LetsChatNotifier#getUsername()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String LetsChatNotifier.getRoom()",
    "String LetsChatNotifier.getToken()",
    "URI LetsChatNotifier.getUrl()",
    "String LetsChatNotifier.getUsername()",
    "void LetsChatNotifier.setRestTemplate(RestTemplate)",
    "void LetsChatNotifier.setRoom(String)",
    "void LetsChatNotifier.setToken(String)",
    "void LetsChatNotifier.setUrl(URI)",
    "void LetsChatNotifier.setUsername(String)"
  })
  void testGettersAndSetters() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    LetsChatNotifier letsChatNotifier = new LetsChatNotifier(repository, mock(RestTemplate.class));

    // Act
    letsChatNotifier.setRestTemplate(mock(RestTemplate.class));
    letsChatNotifier.setRoom("Room");
    letsChatNotifier.setToken("ABC123");
    URI url = PagerdutyNotifier.DEFAULT_URI;
    letsChatNotifier.setUrl(url);
    letsChatNotifier.setUsername("janedoe");
    String actualRoom = letsChatNotifier.getRoom();
    String actualToken = letsChatNotifier.getToken();
    URI actualUrl = letsChatNotifier.getUrl();

    // Assert
    assertEquals("ABC123", actualToken);
    assertEquals("Room", actualRoom);
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualUrl.toString());
    assertEquals("janedoe", letsChatNotifier.getUsername());
    assertSame(url, actualUrl);
  }

  /**
   * Test {@link LetsChatNotifier#getMessage()}.
   *
   * <p>Method under test: {@link LetsChatNotifier#getMessage()}
   */
  @Test
  @DisplayName("Test getMessage()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String LetsChatNotifier.getMessage()"})
  void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        letsChatNotifier.getMessage());
  }

  /**
   * Test {@link LetsChatNotifier#setMessage(String)}.
   *
   * <p>Method under test: {@link LetsChatNotifier#setMessage(String)}
   */
  @Test
  @DisplayName("Test setMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LetsChatNotifier.setMessage(String)"})
  void testSetMessage() {
    // Arrange and Act
    letsChatNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", letsChatNotifier.getMessage());
  }
}
