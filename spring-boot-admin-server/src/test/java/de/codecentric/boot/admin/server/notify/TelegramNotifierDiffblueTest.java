package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
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

@ContextConfiguration(classes = {TelegramNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class TelegramNotifierDiffblueTest {
  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private RestTemplate restTemplate;

  @Autowired private TelegramNotifier telegramNotifier;

  /**
   * Test {@link TelegramNotifier#TelegramNotifier(InstanceRepository, RestTemplate)}.
   *
   * <ul>
   *   <li>Given {@link InstanceRepository}.
   *   <li>When {@link InstanceRepository}.
   * </ul>
   *
   * <p>Method under test: {@link TelegramNotifier#TelegramNotifier(InstanceRepository,
   * RestTemplate)}
   */
  @Test
  @DisplayName(
      "Test new TelegramNotifier(InstanceRepository, RestTemplate); given InstanceRepository; when InstanceRepository")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TelegramNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewTelegramNotifier_givenInstanceRepository_whenInstanceRepository() {
    // Arrange and Act
    TelegramNotifier actualTelegramNotifier =
        new TelegramNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals("HTML", actualTelegramNotifier.getParseMode());
    assertEquals("https://api.telegram.org", actualTelegramNotifier.getApiUrl());
    assertNull(actualTelegramNotifier.getAuthToken());
    assertNull(actualTelegramNotifier.getChatId());
    assertFalse(actualTelegramNotifier.isDisableNotify());
    assertTrue(actualTelegramNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualTelegramNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link TelegramNotifier#TelegramNotifier(InstanceRepository, RestTemplate)}.
   *
   * <ul>
   *   <li>When {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link TelegramNotifier#TelegramNotifier(InstanceRepository,
   * RestTemplate)}
   */
  @Test
  @DisplayName(
      "Test new TelegramNotifier(InstanceRepository, RestTemplate); when InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TelegramNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewTelegramNotifier_whenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    // Act
    TelegramNotifier actualTelegramNotifier =
        new TelegramNotifier(repository, mock(RestTemplate.class));

    // Assert
    assertEquals("HTML", actualTelegramNotifier.getParseMode());
    assertEquals("https://api.telegram.org", actualTelegramNotifier.getApiUrl());
    assertNull(actualTelegramNotifier.getAuthToken());
    assertNull(actualTelegramNotifier.getChatId());
    assertFalse(actualTelegramNotifier.isDisableNotify());
    assertTrue(actualTelegramNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualTelegramNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link TelegramNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link TelegramNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono TelegramNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            telegramNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link TelegramNotifier#buildUrl()}.
   *
   * <p>Method under test: {@link TelegramNotifier#buildUrl()}
   */
  @Test
  @DisplayName("Test buildUrl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String TelegramNotifier.buildUrl()"})
  void testBuildUrl() {
    // Arrange, Act and Assert
    assertEquals(
        "https://api.telegram.org/botnull/sendmessage?chat_id={chat_id}&text={text}&parse_mode={parse_mode}"
            + "&disable_notification={disable_notification}",
        telegramNotifier.buildUrl());
  }

  /**
   * Test {@link TelegramNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link TelegramNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getText(InstanceEvent, Instance); then return 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String TelegramNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    TelegramNotifier telegramNotifier = new TelegramNotifier(repository, mock(RestTemplate.class));
    telegramNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    assertEquals(
        "Not all who wander are lost",
        telegramNotifier.getText(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TelegramNotifier#setApiUrl(String)}
   *   <li>{@link TelegramNotifier#setAuthToken(String)}
   *   <li>{@link TelegramNotifier#setChatId(String)}
   *   <li>{@link TelegramNotifier#setDisableNotify(boolean)}
   *   <li>{@link TelegramNotifier#setParseMode(String)}
   *   <li>{@link TelegramNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link TelegramNotifier#getApiUrl()}
   *   <li>{@link TelegramNotifier#getAuthToken()}
   *   <li>{@link TelegramNotifier#getChatId()}
   *   <li>{@link TelegramNotifier#getParseMode()}
   *   <li>{@link TelegramNotifier#isDisableNotify()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String TelegramNotifier.getApiUrl()",
    "String TelegramNotifier.getAuthToken()",
    "String TelegramNotifier.getChatId()",
    "String TelegramNotifier.getParseMode()",
    "boolean TelegramNotifier.isDisableNotify()",
    "void TelegramNotifier.setApiUrl(String)",
    "void TelegramNotifier.setAuthToken(String)",
    "void TelegramNotifier.setChatId(String)",
    "void TelegramNotifier.setDisableNotify(boolean)",
    "void TelegramNotifier.setParseMode(String)",
    "void TelegramNotifier.setRestTemplate(RestTemplate)"
  })
  void testGettersAndSetters() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    TelegramNotifier telegramNotifier = new TelegramNotifier(repository, mock(RestTemplate.class));

    // Act
    telegramNotifier.setApiUrl("https://example.org/example");
    telegramNotifier.setAuthToken("ABC123");
    telegramNotifier.setChatId("42");
    telegramNotifier.setDisableNotify(true);
    telegramNotifier.setParseMode("Parse Mode");
    telegramNotifier.setRestTemplate(mock(RestTemplate.class));
    String actualApiUrl = telegramNotifier.getApiUrl();
    String actualAuthToken = telegramNotifier.getAuthToken();
    String actualChatId = telegramNotifier.getChatId();
    String actualParseMode = telegramNotifier.getParseMode();

    // Assert
    assertEquals("42", actualChatId);
    assertEquals("ABC123", actualAuthToken);
    assertEquals("Parse Mode", actualParseMode);
    assertEquals("https://example.org/example", actualApiUrl);
    assertTrue(telegramNotifier.isDisableNotify());
  }
}
