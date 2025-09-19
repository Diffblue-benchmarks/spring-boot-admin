package de.codecentric.boot.admin.server.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration.NoSingleNotifierCandidateCondition;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.FilteringNotifierWebConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.MailNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration;
import de.codecentric.boot.admin.server.config.AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.CompositeNotifier;
import de.codecentric.boot.admin.server.notify.DingTalkNotifier;
import de.codecentric.boot.admin.server.notify.DiscordNotifier;
import de.codecentric.boot.admin.server.notify.FeiShuNotifier;
import de.codecentric.boot.admin.server.notify.FeiShuNotifier.Card;
import de.codecentric.boot.admin.server.notify.FeiShuNotifier.MessageType;
import de.codecentric.boot.admin.server.notify.HipchatNotifier;
import de.codecentric.boot.admin.server.notify.LetsChatNotifier;
import de.codecentric.boot.admin.server.notify.MicrosoftTeamsNotifier;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.NotifierProxyProperties;
import de.codecentric.boot.admin.server.notify.OpsGenieNotifier;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import de.codecentric.boot.admin.server.notify.RocketChatNotifier;
import de.codecentric.boot.admin.server.notify.SlackNotifier;
import de.codecentric.boot.admin.server.notify.TelegramNotifier;
import de.codecentric.boot.admin.server.notify.WebexNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.common.CompositeStringExpression;
import org.thymeleaf.EngineConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.cache.StandardCacheManager;
import org.thymeleaf.context.StandardEngineContextFactory;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateparser.markup.decoupled.StandardDecoupledTemplateLogicResolver;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ExtendWith(MockitoExtension.class)
class AdminServerNotifierAutoConfigurationDiffblueTest {
  @Mock private FilteringNotifier filteringNotifier;

  @InjectMocks private FilteringNotifierWebConfiguration filteringNotifierWebConfiguration;

  @InjectMocks private MailNotifierConfiguration mailNotifierConfiguration;

  /**
   * Test CompositeNotifierConfiguration {@link
   * CompositeNotifierConfiguration#compositeNotifier(List)}.
   *
   * <ul>
   *   <li>Given {@link Notifier}.
   * </ul>
   *
   * <p>Method under test: {@link CompositeNotifierConfiguration#compositeNotifier(List)}
   */
  @Test
  @DisplayName("Test CompositeNotifierConfiguration compositeNotifier(List); given Notifier")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CompositeNotifier CompositeNotifierConfiguration.compositeNotifier(List)"})
  void testCompositeNotifierConfigurationCompositeNotifier_givenNotifier() throws AssertionError {
    // Arrange
    CompositeNotifierConfiguration compositeNotifierConfiguration =
        new CompositeNotifierConfiguration();

    ArrayList<Notifier> notifiers = new ArrayList<>();
    notifiers.add(mock(Notifier.class));

    // Act
    CompositeNotifier actualCompositeNotifierResult =
        compositeNotifierConfiguration.compositeNotifier(notifiers);
    Mono<Void> actualPublisher =
        actualCompositeNotifierResult.notify(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test CompositeNotifierConfiguration {@link
   * CompositeNotifierConfiguration#compositeNotifier(List)}.
   *
   * <ul>
   *   <li>Given {@link Notifier}.
   * </ul>
   *
   * <p>Method under test: {@link CompositeNotifierConfiguration#compositeNotifier(List)}
   */
  @Test
  @DisplayName("Test CompositeNotifierConfiguration compositeNotifier(List); given Notifier")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CompositeNotifier CompositeNotifierConfiguration.compositeNotifier(List)"})
  void testCompositeNotifierConfigurationCompositeNotifier_givenNotifier2() throws AssertionError {
    // Arrange
    CompositeNotifierConfiguration compositeNotifierConfiguration =
        new CompositeNotifierConfiguration();

    ArrayList<Notifier> notifiers = new ArrayList<>();
    notifiers.add(mock(Notifier.class));
    notifiers.add(mock(Notifier.class));

    // Act
    CompositeNotifier actualCompositeNotifierResult =
        compositeNotifierConfiguration.compositeNotifier(notifiers);
    Mono<Void> actualPublisher =
        actualCompositeNotifierResult.notify(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectError().verify();
  }

  /**
   * Test CompositeNotifierConfiguration {@link
   * CompositeNotifierConfiguration#compositeNotifier(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CompositeNotifierConfiguration#compositeNotifier(List)}
   */
  @Test
  @DisplayName("Test CompositeNotifierConfiguration compositeNotifier(List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CompositeNotifier CompositeNotifierConfiguration.compositeNotifier(List)"})
  void testCompositeNotifierConfigurationCompositeNotifier_whenArrayList() throws AssertionError {
    // Arrange
    CompositeNotifierConfiguration compositeNotifierConfiguration =
        new CompositeNotifierConfiguration();

    // Act
    CompositeNotifier actualCompositeNotifierResult =
        compositeNotifierConfiguration.compositeNotifier(new ArrayList<>());
    Mono<Void> actualPublisher =
        actualCompositeNotifierResult.notify(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    FirstStep<Void> createResult = StepVerifier.create(actualPublisher);
    createResult.expectComplete().verify();
  }

  /**
   * Test CompositeNotifierConfiguration_NoSingleNotifierCandidateCondition new {@link
   * CompositeNotifierConfiguration.NoSingleNotifierCandidateCondition} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * CompositeNotifierConfiguration.NoSingleNotifierCandidateCondition}
   */
  @Test
  @DisplayName(
      "Test CompositeNotifierConfiguration_NoSingleNotifierCandidateCondition new NoSingleNotifierCandidateCondition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CompositeNotifierConfiguration.NoSingleNotifierCandidateCondition.<init>()"
  })
  void
      testCompositeNotifierConfiguration_NoSingleNotifierCandidateConditionNewNoSingleNotifierCandidateCondition() {
    // Arrange, Act and Assert
    assertEquals(
        ConfigurationPhase.REGISTER_BEAN,
        new NoSingleNotifierCandidateCondition().getConfigurationPhase());
  }

  /**
   * Test DingTalkNotifierConfiguration {@link
   * DingTalkNotifierConfiguration#dingTalkNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link DingTalkNotifierConfiguration#dingTalkNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test DingTalkNotifierConfiguration dingTalkNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DingTalkNotifier DingTalkNotifierConfiguration.dingTalkNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testDingTalkNotifierConfigurationDingTalkNotifier() {
    // Arrange
    DingTalkNotifierConfiguration dingTalkNotifierConfiguration =
        new DingTalkNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    DingTalkNotifier actualDingTalkNotifierResult =
        dingTalkNotifierConfiguration.dingTalkNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "#{instance.registration.name} #{instance.id} is #{event.statusInfo.status}",
        actualDingTalkNotifierResult.getMessage());
    assertNull(actualDingTalkNotifierResult.getSecret());
    assertNull(actualDingTalkNotifierResult.getWebhookUrl());
    assertTrue(actualDingTalkNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualDingTalkNotifierResult.getIgnoreChanges());
  }

  /**
   * Test DingTalkNotifierConfiguration {@link
   * DingTalkNotifierConfiguration#dingTalkNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link DingTalkNotifierConfiguration#dingTalkNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test DingTalkNotifierConfiguration dingTalkNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DingTalkNotifier DingTalkNotifierConfiguration.dingTalkNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testDingTalkNotifierConfigurationDingTalkNotifier_givenMinusOne() {
    // Arrange
    DingTalkNotifierConfiguration dingTalkNotifierConfiguration =
        new DingTalkNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    DingTalkNotifier actualDingTalkNotifierResult =
        dingTalkNotifierConfiguration.dingTalkNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "#{instance.registration.name} #{instance.id} is #{event.statusInfo.status}",
        actualDingTalkNotifierResult.getMessage());
    assertNull(actualDingTalkNotifierResult.getSecret());
    assertNull(actualDingTalkNotifierResult.getWebhookUrl());
    assertTrue(actualDingTalkNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualDingTalkNotifierResult.getIgnoreChanges());
  }

  /**
   * Test DiscordNotifierConfiguration {@link
   * DiscordNotifierConfiguration#discordNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link DiscordNotifierConfiguration#discordNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test DiscordNotifierConfiguration discordNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DiscordNotifier DiscordNotifierConfiguration.discordNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testDiscordNotifierConfigurationDiscordNotifier() {
    // Arrange
    DiscordNotifierConfiguration discordNotifierConfiguration = new DiscordNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    DiscordNotifier actualDiscordNotifierResult =
        discordNotifierConfiguration.discordNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualDiscordNotifierResult.getMessage());
    assertNull(actualDiscordNotifierResult.getAvatarUrl());
    assertNull(actualDiscordNotifierResult.getUsername());
    assertNull(actualDiscordNotifierResult.getWebhookUrl());
    assertFalse(actualDiscordNotifierResult.isTts());
    assertTrue(actualDiscordNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualDiscordNotifierResult.getIgnoreChanges());
  }

  /**
   * Test DiscordNotifierConfiguration {@link
   * DiscordNotifierConfiguration#discordNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link DiscordNotifierConfiguration#discordNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test DiscordNotifierConfiguration discordNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DiscordNotifier DiscordNotifierConfiguration.discordNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testDiscordNotifierConfigurationDiscordNotifier_givenMinusOne() {
    // Arrange
    DiscordNotifierConfiguration discordNotifierConfiguration = new DiscordNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    DiscordNotifier actualDiscordNotifierResult =
        discordNotifierConfiguration.discordNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualDiscordNotifierResult.getMessage());
    assertNull(actualDiscordNotifierResult.getAvatarUrl());
    assertNull(actualDiscordNotifierResult.getUsername());
    assertNull(actualDiscordNotifierResult.getWebhookUrl());
    assertFalse(actualDiscordNotifierResult.isTts());
    assertTrue(actualDiscordNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualDiscordNotifierResult.getIgnoreChanges());
  }

  /**
   * Test FeiShuNotifierConfiguration {@link
   * FeiShuNotifierConfiguration#feiShuNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link FeiShuNotifierConfiguration#feiShuNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test FeiShuNotifierConfiguration feiShuNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FeiShuNotifier FeiShuNotifierConfiguration.feiShuNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testFeiShuNotifierConfigurationFeiShuNotifier() {
    // Arrange
    FeiShuNotifierConfiguration feiShuNotifierConfiguration = new FeiShuNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    FeiShuNotifier actualFeiShuNotifierResult =
        feiShuNotifierConfiguration.feiShuNotifier(repository, proxyProperties);

    // Assert
    Card card = actualFeiShuNotifierResult.getCard();
    assertEquals("Codecentric's Spring Boot Admin notice", card.getTitle());
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        actualFeiShuNotifierResult.getMessage());
    assertEquals("red", card.getThemeColor());
    assertNull(actualFeiShuNotifierResult.getSecret());
    assertNull(actualFeiShuNotifierResult.getWebhookUrl());
    assertEquals(MessageType.interactive, actualFeiShuNotifierResult.getMessageType());
    assertTrue(actualFeiShuNotifierResult.isEnabled());
    assertTrue(actualFeiShuNotifierResult.isAtAll());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualFeiShuNotifierResult.getIgnoreChanges());
  }

  /**
   * Test FeiShuNotifierConfiguration {@link
   * FeiShuNotifierConfiguration#feiShuNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link FeiShuNotifierConfiguration#feiShuNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test FeiShuNotifierConfiguration feiShuNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FeiShuNotifier FeiShuNotifierConfiguration.feiShuNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testFeiShuNotifierConfigurationFeiShuNotifier_givenMinusOne() {
    // Arrange
    FeiShuNotifierConfiguration feiShuNotifierConfiguration = new FeiShuNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    FeiShuNotifier actualFeiShuNotifierResult =
        feiShuNotifierConfiguration.feiShuNotifier(repository, proxyProperties);

    // Assert
    Card card = actualFeiShuNotifierResult.getCard();
    assertEquals("Codecentric's Spring Boot Admin notice", card.getTitle());
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        actualFeiShuNotifierResult.getMessage());
    assertEquals("red", card.getThemeColor());
    assertNull(actualFeiShuNotifierResult.getSecret());
    assertNull(actualFeiShuNotifierResult.getWebhookUrl());
    assertEquals(MessageType.interactive, actualFeiShuNotifierResult.getMessageType());
    assertTrue(actualFeiShuNotifierResult.isEnabled());
    assertTrue(actualFeiShuNotifierResult.isAtAll());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualFeiShuNotifierResult.getIgnoreChanges());
  }

  /**
   * Test FilteringNotifierWebConfiguration {@link
   * FilteringNotifierWebConfiguration#notificationFilterController()}.
   *
   * <p>Method under test: {@link FilteringNotifierWebConfiguration#notificationFilterController()}
   */
  @Test
  @DisplayName("Test FilteringNotifierWebConfiguration notificationFilterController()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.notify.filter.web.NotificationFilterController FilteringNotifierWebConfiguration.notificationFilterController()"
  })
  void testFilteringNotifierWebConfigurationNotificationFilterController() {
    // Arrange, Act and Assert
    assertTrue(
        filteringNotifierWebConfiguration.notificationFilterController().getFilters().isEmpty());
  }

  /**
   * Test HipchatNotifierConfiguration {@link
   * HipchatNotifierConfiguration#hipchatNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifierConfiguration#hipchatNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test HipchatNotifierConfiguration hipchatNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HipchatNotifier HipchatNotifierConfiguration.hipchatNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testHipchatNotifierConfigurationHipchatNotifier_givenMinusOne() {
    // Arrange
    HipchatNotifierConfiguration hipchatNotifierConfiguration = new HipchatNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    HipchatNotifier actualHipchatNotifierResult =
        hipchatNotifierConfiguration.hipchatNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
            + "}</strong>",
        actualHipchatNotifierResult.getDescription());
    assertNull(actualHipchatNotifierResult.getAuthToken());
    assertNull(actualHipchatNotifierResult.getRoomId());
    assertNull(actualHipchatNotifierResult.getUrl());
    assertFalse(actualHipchatNotifierResult.isNotify());
    assertTrue(actualHipchatNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualHipchatNotifierResult.getIgnoreChanges());
  }

  /**
   * Test HipchatNotifierConfiguration {@link
   * HipchatNotifierConfiguration#hipchatNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Then return Description is a string.
   * </ul>
   *
   * <p>Method under test: {@link HipchatNotifierConfiguration#hipchatNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test HipchatNotifierConfiguration hipchatNotifier(InstanceRepository, NotifierProxyProperties); then return Description is a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HipchatNotifier HipchatNotifierConfiguration.hipchatNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testHipchatNotifierConfigurationHipchatNotifier_thenReturnDescriptionIsAString() {
    // Arrange
    HipchatNotifierConfiguration hipchatNotifierConfiguration = new HipchatNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    HipchatNotifier actualHipchatNotifierResult =
        hipchatNotifierConfiguration.hipchatNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
            + "}</strong>",
        actualHipchatNotifierResult.getDescription());
    assertNull(actualHipchatNotifierResult.getAuthToken());
    assertNull(actualHipchatNotifierResult.getRoomId());
    assertNull(actualHipchatNotifierResult.getUrl());
    assertFalse(actualHipchatNotifierResult.isNotify());
    assertTrue(actualHipchatNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualHipchatNotifierResult.getIgnoreChanges());
  }

  /**
   * Test LetsChatNotifierConfiguration {@link
   * LetsChatNotifierConfiguration#letsChatNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link LetsChatNotifierConfiguration#letsChatNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test LetsChatNotifierConfiguration letsChatNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LetsChatNotifier LetsChatNotifierConfiguration.letsChatNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testLetsChatNotifierConfigurationLetsChatNotifier() {
    // Arrange
    LetsChatNotifierConfiguration letsChatNotifierConfiguration =
        new LetsChatNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    LetsChatNotifier actualLetsChatNotifierResult =
        letsChatNotifierConfiguration.letsChatNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualLetsChatNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualLetsChatNotifierResult.getUsername());
    assertNull(actualLetsChatNotifierResult.getRoom());
    assertNull(actualLetsChatNotifierResult.getToken());
    assertNull(actualLetsChatNotifierResult.getUrl());
    assertTrue(actualLetsChatNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualLetsChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Test LetsChatNotifierConfiguration {@link
   * LetsChatNotifierConfiguration#letsChatNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link LetsChatNotifierConfiguration#letsChatNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test LetsChatNotifierConfiguration letsChatNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "LetsChatNotifier LetsChatNotifierConfiguration.letsChatNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testLetsChatNotifierConfigurationLetsChatNotifier_givenMinusOne() {
    // Arrange
    LetsChatNotifierConfiguration letsChatNotifierConfiguration =
        new LetsChatNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    LetsChatNotifier actualLetsChatNotifierResult =
        letsChatNotifierConfiguration.letsChatNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualLetsChatNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualLetsChatNotifierResult.getUsername());
    assertNull(actualLetsChatNotifierResult.getRoom());
    assertNull(actualLetsChatNotifierResult.getToken());
    assertNull(actualLetsChatNotifierResult.getUrl());
    assertTrue(actualLetsChatNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualLetsChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Test MailNotifierConfiguration {@link MailNotifierConfiguration#mailNotifierTemplateEngine()}.
   *
   * <p>Method under test: {@link MailNotifierConfiguration#mailNotifierTemplateEngine()}
   */
  @Test
  @DisplayName("Test MailNotifierConfiguration mailNotifierTemplateEngine()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TemplateEngine MailNotifierConfiguration.mailNotifierTemplateEngine()"})
  void testMailNotifierConfigurationMailNotifierTemplateEngine() {
    // Arrange and Act
    TemplateEngine actualMailNotifierTemplateEngineResult =
        mailNotifierConfiguration.mailNotifierTemplateEngine();

    // Assert
    assertTrue(
        actualMailNotifierTemplateEngineResult.getConfiguration() instanceof EngineConfiguration);
    assertTrue(
        actualMailNotifierTemplateEngineResult.getCacheManager() instanceof StandardCacheManager);
    assertTrue(
        actualMailNotifierTemplateEngineResult.getEngineContextFactory()
            instanceof StandardEngineContextFactory);
    assertTrue(actualMailNotifierTemplateEngineResult instanceof SpringTemplateEngine);
    assertTrue(
        actualMailNotifierTemplateEngineResult.getDecoupledTemplateLogicResolver()
            instanceof StandardDecoupledTemplateLogicResolver);
    Map<String, Set<IDialect>> dialectsByPrefix =
        actualMailNotifierTemplateEngineResult.getDialectsByPrefix();
    assertEquals(1, dialectsByPrefix.size());
    assertEquals(1, actualMailNotifierTemplateEngineResult.getDialects().size());
    assertEquals(1, actualMailNotifierTemplateEngineResult.getLinkBuilders().size());
    assertEquals(1, actualMailNotifierTemplateEngineResult.getMessageResolvers().size());
    assertEquals(1, actualMailNotifierTemplateEngineResult.getTemplateResolvers().size());
    assertFalse(
        ((SpringTemplateEngine) actualMailNotifierTemplateEngineResult)
            .getEnableSpringELCompiler());
    assertFalse(
        ((SpringTemplateEngine) actualMailNotifierTemplateEngineResult)
            .getRenderHiddenMarkersBeforeCheckboxes());
    assertTrue(dialectsByPrefix.containsKey(null));
    assertTrue(actualMailNotifierTemplateEngineResult.isInitialized());
  }

  /**
   * Test MicrosoftTeamsNotifierConfiguration {@link
   * MicrosoftTeamsNotifierConfiguration#microsoftTeamsNotifier(InstanceRepository,
   * NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link
   * MicrosoftTeamsNotifierConfiguration#microsoftTeamsNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test MicrosoftTeamsNotifierConfiguration microsoftTeamsNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MicrosoftTeamsNotifier MicrosoftTeamsNotifierConfiguration.microsoftTeamsNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testMicrosoftTeamsNotifierConfigurationMicrosoftTeamsNotifier() {
    // Arrange
    MicrosoftTeamsNotifierConfiguration microsoftTeamsNotifierConfiguration =
        new MicrosoftTeamsNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    MicrosoftTeamsNotifier actualMicrosoftTeamsNotifierResult =
        microsoftTeamsNotifierConfiguration.microsoftTeamsNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
            + ".statusInfo.status}",
        actualMicrosoftTeamsNotifierResult.getStatusActivitySubtitle());
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getDeregisterActivitySubtitle());
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getRegisterActivitySubtitle());
    assertEquals("De-Registered", actualMicrosoftTeamsNotifierResult.getDeRegisteredTitle());
    assertEquals("Registered", actualMicrosoftTeamsNotifierResult.getRegisteredTitle());
    assertEquals(
        "Spring Boot Admin Notification", actualMicrosoftTeamsNotifierResult.getMessageSummary());
    assertEquals("Status Changed", actualMicrosoftTeamsNotifierResult.getStatusChangedTitle());
    assertEquals(
        "event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        actualMicrosoftTeamsNotifierResult.getThemeColor());
    assertNull(actualMicrosoftTeamsNotifierResult.getWebhookUrl());
    assertTrue(actualMicrosoftTeamsNotifierResult.isEnabled());
    assertArrayEquals(
        new String[] {"UNKNOWN:UP"}, actualMicrosoftTeamsNotifierResult.getIgnoreChanges());
  }

  /**
   * Test MicrosoftTeamsNotifierConfiguration {@link
   * MicrosoftTeamsNotifierConfiguration#microsoftTeamsNotifier(InstanceRepository,
   * NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link
   * MicrosoftTeamsNotifierConfiguration#microsoftTeamsNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test MicrosoftTeamsNotifierConfiguration microsoftTeamsNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MicrosoftTeamsNotifier MicrosoftTeamsNotifierConfiguration.microsoftTeamsNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testMicrosoftTeamsNotifierConfigurationMicrosoftTeamsNotifier_givenMinusOne() {
    // Arrange
    MicrosoftTeamsNotifierConfiguration microsoftTeamsNotifierConfiguration =
        new MicrosoftTeamsNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    MicrosoftTeamsNotifier actualMicrosoftTeamsNotifierResult =
        microsoftTeamsNotifierConfiguration.microsoftTeamsNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
            + ".statusInfo.status}",
        actualMicrosoftTeamsNotifierResult.getStatusActivitySubtitle());
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getDeregisterActivitySubtitle());
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getRegisterActivitySubtitle());
    assertEquals("De-Registered", actualMicrosoftTeamsNotifierResult.getDeRegisteredTitle());
    assertEquals("Registered", actualMicrosoftTeamsNotifierResult.getRegisteredTitle());
    assertEquals(
        "Spring Boot Admin Notification", actualMicrosoftTeamsNotifierResult.getMessageSummary());
    assertEquals("Status Changed", actualMicrosoftTeamsNotifierResult.getStatusChangedTitle());
    assertEquals(
        "event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        actualMicrosoftTeamsNotifierResult.getThemeColor());
    assertNull(actualMicrosoftTeamsNotifierResult.getWebhookUrl());
    assertTrue(actualMicrosoftTeamsNotifierResult.isEnabled());
    assertArrayEquals(
        new String[] {"UNKNOWN:UP"}, actualMicrosoftTeamsNotifierResult.getIgnoreChanges());
  }

  /**
   * Test OpsGenieNotifierConfiguration {@link
   * OpsGenieNotifierConfiguration#opsgenieNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link OpsGenieNotifierConfiguration#opsgenieNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test OpsGenieNotifierConfiguration opsgenieNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "OpsGenieNotifier OpsGenieNotifierConfiguration.opsgenieNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testOpsGenieNotifierConfigurationOpsgenieNotifier() {
    // Arrange
    OpsGenieNotifierConfiguration opsGenieNotifierConfiguration =
        new OpsGenieNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    OpsGenieNotifier actualOpsgenieNotifierResult =
        opsGenieNotifierConfiguration.opsgenieNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualOpsgenieNotifierResult.getMessage());
    assertEquals(
        "https://api.opsgenie.com/v2/alerts", actualOpsgenieNotifierResult.getUrl().toString());
    assertNull(actualOpsgenieNotifierResult.getActions());
    assertNull(actualOpsgenieNotifierResult.getApiKey());
    assertNull(actualOpsgenieNotifierResult.getEntity());
    assertNull(actualOpsgenieNotifierResult.getSource());
    assertNull(actualOpsgenieNotifierResult.getTags());
    assertNull(actualOpsgenieNotifierResult.getUser());
    assertTrue(actualOpsgenieNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualOpsgenieNotifierResult.getIgnoreChanges());
  }

  /**
   * Test OpsGenieNotifierConfiguration {@link
   * OpsGenieNotifierConfiguration#opsgenieNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifierConfiguration#opsgenieNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test OpsGenieNotifierConfiguration opsgenieNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "OpsGenieNotifier OpsGenieNotifierConfiguration.opsgenieNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testOpsGenieNotifierConfigurationOpsgenieNotifier_givenMinusOne() {
    // Arrange
    OpsGenieNotifierConfiguration opsGenieNotifierConfiguration =
        new OpsGenieNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    OpsGenieNotifier actualOpsgenieNotifierResult =
        opsGenieNotifierConfiguration.opsgenieNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualOpsgenieNotifierResult.getMessage());
    assertEquals(
        "https://api.opsgenie.com/v2/alerts", actualOpsgenieNotifierResult.getUrl().toString());
    assertNull(actualOpsgenieNotifierResult.getActions());
    assertNull(actualOpsgenieNotifierResult.getApiKey());
    assertNull(actualOpsgenieNotifierResult.getEntity());
    assertNull(actualOpsgenieNotifierResult.getSource());
    assertNull(actualOpsgenieNotifierResult.getTags());
    assertNull(actualOpsgenieNotifierResult.getUser());
    assertTrue(actualOpsgenieNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualOpsgenieNotifierResult.getIgnoreChanges());
  }

  /**
   * Test PagerdutyNotifierConfiguration {@link
   * PagerdutyNotifierConfiguration#pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link
   * PagerdutyNotifierConfiguration#pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test PagerdutyNotifierConfiguration pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PagerdutyNotifier PagerdutyNotifierConfiguration.pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testPagerdutyNotifierConfigurationPagerdutyNotifier() {
    // Arrange
    PagerdutyNotifierConfiguration pagerdutyNotifierConfiguration =
        new PagerdutyNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    PagerdutyNotifier actualPagerdutyNotifierResult =
        pagerdutyNotifierConfiguration.pagerdutyNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualPagerdutyNotifierResult.getDescription());
    URI url = actualPagerdutyNotifierResult.getUrl();
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json", url.toString());
    assertNull(actualPagerdutyNotifierResult.getClient());
    assertNull(actualPagerdutyNotifierResult.getServiceKey());
    assertNull(actualPagerdutyNotifierResult.getClientUrl());
    assertTrue(actualPagerdutyNotifierResult.isEnabled());
    assertSame(PagerdutyNotifier.DEFAULT_URI, url);
    assertArrayEquals(
        new String[] {"UNKNOWN:UP"}, actualPagerdutyNotifierResult.getIgnoreChanges());
  }

  /**
   * Test PagerdutyNotifierConfiguration {@link
   * PagerdutyNotifierConfiguration#pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link
   * PagerdutyNotifierConfiguration#pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test PagerdutyNotifierConfiguration pagerdutyNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PagerdutyNotifier PagerdutyNotifierConfiguration.pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testPagerdutyNotifierConfigurationPagerdutyNotifier_givenMinusOne() {
    // Arrange
    PagerdutyNotifierConfiguration pagerdutyNotifierConfiguration =
        new PagerdutyNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    PagerdutyNotifier actualPagerdutyNotifierResult =
        pagerdutyNotifierConfiguration.pagerdutyNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualPagerdutyNotifierResult.getDescription());
    URI url = actualPagerdutyNotifierResult.getUrl();
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json", url.toString());
    assertNull(actualPagerdutyNotifierResult.getClient());
    assertNull(actualPagerdutyNotifierResult.getServiceKey());
    assertNull(actualPagerdutyNotifierResult.getClientUrl());
    assertTrue(actualPagerdutyNotifierResult.isEnabled());
    assertSame(PagerdutyNotifier.DEFAULT_URI, url);
    assertArrayEquals(
        new String[] {"UNKNOWN:UP"}, actualPagerdutyNotifierResult.getIgnoreChanges());
  }

  /**
   * Test RocketChatNotifierConfiguration {@link
   * RocketChatNotifierConfiguration#rocketChatNotifier(InstanceRepository,
   * NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link
   * RocketChatNotifierConfiguration#rocketChatNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test RocketChatNotifierConfiguration rocketChatNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RocketChatNotifier RocketChatNotifierConfiguration.rocketChatNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testRocketChatNotifierConfigurationRocketChatNotifier() throws EvaluationException {
    // Arrange
    RocketChatNotifierConfiguration rocketChatNotifierConfiguration =
        new RocketChatNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    RocketChatNotifier actualRocketChatNotifierResult =
        rocketChatNotifierConfiguration.rocketChatNotifier(repository, proxyProperties);

    // Assert
    Expression message = actualRocketChatNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        message.getExpressionString());
    assertNull(actualRocketChatNotifierResult.getRoomId());
    assertNull(actualRocketChatNotifierResult.getToken());
    assertNull(actualRocketChatNotifierResult.getUrl());
    assertNull(actualRocketChatNotifierResult.getUserId());
    assertEquals(7, ((CompositeStringExpression) message).getExpressions().length);
    assertTrue(actualRocketChatNotifierResult.isEnabled());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
    assertArrayEquals(
        new String[] {"UNKNOWN:UP"}, actualRocketChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Test RocketChatNotifierConfiguration {@link
   * RocketChatNotifierConfiguration#rocketChatNotifier(InstanceRepository,
   * NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link
   * RocketChatNotifierConfiguration#rocketChatNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test RocketChatNotifierConfiguration rocketChatNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "RocketChatNotifier RocketChatNotifierConfiguration.rocketChatNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testRocketChatNotifierConfigurationRocketChatNotifier_givenMinusOne()
      throws EvaluationException {
    // Arrange
    RocketChatNotifierConfiguration rocketChatNotifierConfiguration =
        new RocketChatNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    RocketChatNotifier actualRocketChatNotifierResult =
        rocketChatNotifierConfiguration.rocketChatNotifier(repository, proxyProperties);

    // Assert
    Expression message = actualRocketChatNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        message.getExpressionString());
    assertNull(actualRocketChatNotifierResult.getRoomId());
    assertNull(actualRocketChatNotifierResult.getToken());
    assertNull(actualRocketChatNotifierResult.getUrl());
    assertNull(actualRocketChatNotifierResult.getUserId());
    assertEquals(7, ((CompositeStringExpression) message).getExpressions().length);
    assertTrue(actualRocketChatNotifierResult.isEnabled());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
    assertArrayEquals(
        new String[] {"UNKNOWN:UP"}, actualRocketChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Test SlackNotifierConfiguration {@link
   * SlackNotifierConfiguration#slackNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link SlackNotifierConfiguration#slackNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test SlackNotifierConfiguration slackNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SlackNotifier SlackNotifierConfiguration.slackNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testSlackNotifierConfigurationSlackNotifier() {
    // Arrange
    SlackNotifierConfiguration slackNotifierConfiguration = new SlackNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    SlackNotifier actualSlackNotifierResult =
        slackNotifierConfiguration.slackNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualSlackNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualSlackNotifierResult.getUsername());
    assertNull(actualSlackNotifierResult.getChannel());
    assertNull(actualSlackNotifierResult.getIcon());
    assertNull(actualSlackNotifierResult.getWebhookUrl());
    assertTrue(actualSlackNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualSlackNotifierResult.getIgnoreChanges());
  }

  /**
   * Test SlackNotifierConfiguration {@link
   * SlackNotifierConfiguration#slackNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link SlackNotifierConfiguration#slackNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test SlackNotifierConfiguration slackNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SlackNotifier SlackNotifierConfiguration.slackNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testSlackNotifierConfigurationSlackNotifier_givenMinusOne() {
    // Arrange
    SlackNotifierConfiguration slackNotifierConfiguration = new SlackNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    SlackNotifier actualSlackNotifierResult =
        slackNotifierConfiguration.slackNotifier(repository, proxyProperties);

    // Assert
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualSlackNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualSlackNotifierResult.getUsername());
    assertNull(actualSlackNotifierResult.getChannel());
    assertNull(actualSlackNotifierResult.getIcon());
    assertNull(actualSlackNotifierResult.getWebhookUrl());
    assertTrue(actualSlackNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualSlackNotifierResult.getIgnoreChanges());
  }

  /**
   * Test TelegramNotifierConfiguration {@link
   * TelegramNotifierConfiguration#telegramNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link TelegramNotifierConfiguration#telegramNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test TelegramNotifierConfiguration telegramNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TelegramNotifier TelegramNotifierConfiguration.telegramNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testTelegramNotifierConfigurationTelegramNotifier_givenMinusOne() {
    // Arrange
    TelegramNotifierConfiguration telegramNotifierConfiguration =
        new TelegramNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    TelegramNotifier actualTelegramNotifierResult =
        telegramNotifierConfiguration.telegramNotifier(repository, proxyProperties);

    // Assert
    assertEquals("HTML", actualTelegramNotifierResult.getParseMode());
    assertEquals("https://api.telegram.org", actualTelegramNotifierResult.getApiUrl());
    assertNull(actualTelegramNotifierResult.getAuthToken());
    assertNull(actualTelegramNotifierResult.getChatId());
    assertFalse(actualTelegramNotifierResult.isDisableNotify());
    assertTrue(actualTelegramNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualTelegramNotifierResult.getIgnoreChanges());
  }

  /**
   * Test TelegramNotifierConfiguration {@link
   * TelegramNotifierConfiguration#telegramNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Then return ParseMode is {@code HTML}.
   * </ul>
   *
   * <p>Method under test: {@link TelegramNotifierConfiguration#telegramNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test TelegramNotifierConfiguration telegramNotifier(InstanceRepository, NotifierProxyProperties); then return ParseMode is 'HTML'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TelegramNotifier TelegramNotifierConfiguration.telegramNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testTelegramNotifierConfigurationTelegramNotifier_thenReturnParseModeIsHtml() {
    // Arrange
    TelegramNotifierConfiguration telegramNotifierConfiguration =
        new TelegramNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    TelegramNotifier actualTelegramNotifierResult =
        telegramNotifierConfiguration.telegramNotifier(repository, proxyProperties);

    // Assert
    assertEquals("HTML", actualTelegramNotifierResult.getParseMode());
    assertEquals("https://api.telegram.org", actualTelegramNotifierResult.getApiUrl());
    assertNull(actualTelegramNotifierResult.getAuthToken());
    assertNull(actualTelegramNotifierResult.getChatId());
    assertFalse(actualTelegramNotifierResult.isDisableNotify());
    assertTrue(actualTelegramNotifierResult.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualTelegramNotifierResult.getIgnoreChanges());
  }

  /**
   * Test WebexNotifierConfiguration {@link
   * WebexNotifierConfiguration#webexNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <p>Method under test: {@link WebexNotifierConfiguration#webexNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test WebexNotifierConfiguration webexNotifier(InstanceRepository, NotifierProxyProperties)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WebexNotifier WebexNotifierConfiguration.webexNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testWebexNotifierConfigurationWebexNotifier() throws EvaluationException {
    // Arrange
    WebexNotifierConfiguration webexNotifierConfiguration = new WebexNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    WebexNotifier actualWebexNotifierResult =
        webexNotifierConfiguration.webexNotifier(repository, proxyProperties);

    // Assert
    Expression message = actualWebexNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    assertEquals(
        "<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
            + "}</strong>",
        message.getExpressionString());
    assertEquals(
        "https://webexapis.com/v1/messages", actualWebexNotifierResult.getUrl().toString());
    assertNull(actualWebexNotifierResult.getAuthToken());
    assertNull(actualWebexNotifierResult.getRoomId());
    assertEquals(7, ((CompositeStringExpression) message).getExpressions().length);
    assertTrue(actualWebexNotifierResult.isEnabled());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualWebexNotifierResult.getIgnoreChanges());
  }

  /**
   * Test WebexNotifierConfiguration {@link
   * WebexNotifierConfiguration#webexNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given minus one.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifierConfiguration#webexNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test WebexNotifierConfiguration webexNotifier(InstanceRepository, NotifierProxyProperties); given minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WebexNotifier WebexNotifierConfiguration.webexNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testWebexNotifierConfigurationWebexNotifier_givenMinusOne() throws EvaluationException {
    // Arrange
    WebexNotifierConfiguration webexNotifierConfiguration = new WebexNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    WebexNotifier actualWebexNotifierResult =
        webexNotifierConfiguration.webexNotifier(repository, proxyProperties);

    // Assert
    Expression message = actualWebexNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    assertEquals(
        "<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
            + "}</strong>",
        message.getExpressionString());
    assertEquals(
        "https://webexapis.com/v1/messages", actualWebexNotifierResult.getUrl().toString());
    assertNull(actualWebexNotifierResult.getAuthToken());
    assertNull(actualWebexNotifierResult.getRoomId());
    assertEquals(7, ((CompositeStringExpression) message).getExpressions().length);
    assertTrue(actualWebexNotifierResult.isEnabled());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualWebexNotifierResult.getIgnoreChanges());
  }

  /**
   * Test WebexNotifierConfiguration {@link
   * WebexNotifierConfiguration#webexNotifier(InstanceRepository, NotifierProxyProperties)}.
   *
   * <ul>
   *   <li>Given {@code UNKNOWN:UP}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifierConfiguration#webexNotifier(InstanceRepository,
   * NotifierProxyProperties)}
   */
  @Test
  @DisplayName(
      "Test WebexNotifierConfiguration webexNotifier(InstanceRepository, NotifierProxyProperties); given 'UNKNOWN:UP'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WebexNotifier WebexNotifierConfiguration.webexNotifier(InstanceRepository, NotifierProxyProperties)"
  })
  void testWebexNotifierConfigurationWebexNotifier_givenUnknownUp() throws EvaluationException {
    // Arrange
    WebexNotifierConfiguration webexNotifierConfiguration = new WebexNotifierConfiguration();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("UNKNOWN:UP");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    WebexNotifier actualWebexNotifierResult =
        webexNotifierConfiguration.webexNotifier(repository, proxyProperties);

    // Assert
    Expression message = actualWebexNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    assertEquals(
        "<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
            + "}</strong>",
        message.getExpressionString());
    assertEquals(
        "https://webexapis.com/v1/messages", actualWebexNotifierResult.getUrl().toString());
    assertNull(actualWebexNotifierResult.getAuthToken());
    assertNull(actualWebexNotifierResult.getRoomId());
    assertEquals(7, ((CompositeStringExpression) message).getExpressions().length);
    assertTrue(actualWebexNotifierResult.isEnabled());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualWebexNotifierResult.getIgnoreChanges());
  }
}
