package de.codecentric.boot.admin.server.config;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.CompositeNotifier;
import de.codecentric.boot.admin.server.notify.DingTalkNotifier;
import de.codecentric.boot.admin.server.notify.DiscordNotifier;
import de.codecentric.boot.admin.server.notify.FeiShuNotifier;
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
import java.io.Serializable;
import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.ConstructorResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.OperatorOverloader;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypeComparator;
import org.springframework.expression.TypeLocator;
import org.springframework.expression.TypedValue;
import org.springframework.expression.common.CompositeStringExpression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.CompoundExpression;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.support.ReflectiveConstructorResolver;
import org.springframework.expression.spel.support.ReflectiveMethodResolver;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardOperatorOverloader;
import org.springframework.expression.spel.support.StandardTypeComparator;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.expression.spel.support.StandardTypeLocator;
import reactor.test.StepVerifier;

public class AdminServerNotifierAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration#compositeNotifier(List)}
   */
  @Test
  public void testCompositeNotifierConfigurationCompositeNotifier() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration compositeNotifierConfiguration = new AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration();

    // Act
    CompositeNotifier actualCompositeNotifierResult = compositeNotifierConfiguration
        .compositeNotifier(new ArrayList<>());

    // Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(actualCompositeNotifierResult.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration#compositeNotifier(List)}
   */
  @Test
  public void testCompositeNotifierConfigurationCompositeNotifier2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration compositeNotifierConfiguration = new AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration();

    ArrayList<Notifier> notifiers = new ArrayList<>();
    notifiers.add(mock(Notifier.class));

    // Act
    CompositeNotifier actualCompositeNotifierResult = compositeNotifierConfiguration.compositeNotifier(notifiers);

    // Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(actualCompositeNotifierResult.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration#compositeNotifier(List)}
   */
  @Test
  public void testCompositeNotifierConfigurationCompositeNotifier3() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration compositeNotifierConfiguration = new AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration();

    ArrayList<Notifier> notifiers = new ArrayList<>();
    notifiers.add(mock(Notifier.class));
    notifiers.add(mock(Notifier.class));

    // Act
    CompositeNotifier actualCompositeNotifierResult = compositeNotifierConfiguration.compositeNotifier(notifiers);

    // Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(actualCompositeNotifierResult.notify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)));
    createResult.expectError().verify();
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration.NoSingleNotifierCandidateCondition}
   */
  @Test
  public void testCompositeNotifierConfiguration_NoSingleNotifierCandidateConditionNewNoSingleNotifierCandidateCondition() {
    // Arrange, Act and Assert
    assertEquals(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN,
        (new AdminServerNotifierAutoConfiguration.CompositeNotifierConfiguration.NoSingleNotifierCandidateCondition())
            .getConfigurationPhase());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration#dingTalkNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testDingTalkNotifierConfigurationDingTalkNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration dingTalkNotifierConfiguration = new AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    DingTalkNotifier actualDingTalkNotifierResult = dingTalkNotifierConfiguration.dingTalkNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name} #{instance.id} is #{event.statusInfo.status}",
        actualDingTalkNotifierResult.getMessage());
    assertNull(actualDingTalkNotifierResult.getSecret());
    assertNull(actualDingTalkNotifierResult.getWebhookUrl());
    assertTrue(actualDingTalkNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualDingTalkNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration#dingTalkNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testDingTalkNotifierConfigurationDingTalkNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration dingTalkNotifierConfiguration = new AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    DingTalkNotifier actualDingTalkNotifierResult = dingTalkNotifierConfiguration.dingTalkNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name} #{instance.id} is #{event.statusInfo.status}",
        actualDingTalkNotifierResult.getMessage());
    assertNull(actualDingTalkNotifierResult.getSecret());
    assertNull(actualDingTalkNotifierResult.getWebhookUrl());
    assertTrue(actualDingTalkNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualDingTalkNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration#dingTalkNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testDingTalkNotifierConfigurationDingTalkNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration dingTalkNotifierConfiguration = new AdminServerNotifierAutoConfiguration.DingTalkNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    DingTalkNotifier actualDingTalkNotifierResult = dingTalkNotifierConfiguration.dingTalkNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name} #{instance.id} is #{event.statusInfo.status}",
        actualDingTalkNotifierResult.getMessage());
    assertNull(actualDingTalkNotifierResult.getSecret());
    assertNull(actualDingTalkNotifierResult.getWebhookUrl());
    assertTrue(actualDingTalkNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualDingTalkNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration#discordNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testDiscordNotifierConfigurationDiscordNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration discordNotifierConfiguration = new AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    DiscordNotifier actualDiscordNotifierResult = discordNotifierConfiguration.discordNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualDiscordNotifierResult.getMessage());
    assertNull(actualDiscordNotifierResult.getAvatarUrl());
    assertNull(actualDiscordNotifierResult.getUsername());
    assertNull(actualDiscordNotifierResult.getWebhookUrl());
    assertFalse(actualDiscordNotifierResult.isTts());
    assertTrue(actualDiscordNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualDiscordNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration#discordNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testDiscordNotifierConfigurationDiscordNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration discordNotifierConfiguration = new AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    DiscordNotifier actualDiscordNotifierResult = discordNotifierConfiguration.discordNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualDiscordNotifierResult.getMessage());
    assertNull(actualDiscordNotifierResult.getAvatarUrl());
    assertNull(actualDiscordNotifierResult.getUsername());
    assertNull(actualDiscordNotifierResult.getWebhookUrl());
    assertFalse(actualDiscordNotifierResult.isTts());
    assertTrue(actualDiscordNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualDiscordNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration#discordNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testDiscordNotifierConfigurationDiscordNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration discordNotifierConfiguration = new AdminServerNotifierAutoConfiguration.DiscordNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    DiscordNotifier actualDiscordNotifierResult = discordNotifierConfiguration.discordNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualDiscordNotifierResult.getMessage());
    assertNull(actualDiscordNotifierResult.getAvatarUrl());
    assertNull(actualDiscordNotifierResult.getUsername());
    assertNull(actualDiscordNotifierResult.getWebhookUrl());
    assertFalse(actualDiscordNotifierResult.isTts());
    assertTrue(actualDiscordNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualDiscordNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration#feiShuNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testFeiShuNotifierConfigurationFeiShuNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration feiShuNotifierConfiguration = new AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    FeiShuNotifier actualFeiShuNotifierResult = feiShuNotifierConfiguration.feiShuNotifier(repository, proxyProperties);

    // Assert
    FeiShuNotifier.Card card = actualFeiShuNotifierResult.getCard();
    assertEquals("Codecentric's Spring Boot Admin notice", card.getTitle());
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        actualFeiShuNotifierResult.getMessage());
    assertEquals("red", card.getThemeColor());
    assertNull(actualFeiShuNotifierResult.getSecret());
    assertNull(actualFeiShuNotifierResult.getWebhookUrl());
    assertEquals(FeiShuNotifier.MessageType.interactive, actualFeiShuNotifierResult.getMessageType());
    assertTrue(actualFeiShuNotifierResult.isEnabled());
    assertTrue(actualFeiShuNotifierResult.isAtAll());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualFeiShuNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration#feiShuNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testFeiShuNotifierConfigurationFeiShuNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration feiShuNotifierConfiguration = new AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    FeiShuNotifier actualFeiShuNotifierResult = feiShuNotifierConfiguration.feiShuNotifier(repository, proxyProperties);

    // Assert
    FeiShuNotifier.Card card = actualFeiShuNotifierResult.getCard();
    assertEquals("Codecentric's Spring Boot Admin notice", card.getTitle());
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        actualFeiShuNotifierResult.getMessage());
    assertEquals("red", card.getThemeColor());
    assertNull(actualFeiShuNotifierResult.getSecret());
    assertNull(actualFeiShuNotifierResult.getWebhookUrl());
    assertEquals(FeiShuNotifier.MessageType.interactive, actualFeiShuNotifierResult.getMessageType());
    assertTrue(actualFeiShuNotifierResult.isEnabled());
    assertTrue(actualFeiShuNotifierResult.isAtAll());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualFeiShuNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration#feiShuNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testFeiShuNotifierConfigurationFeiShuNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration feiShuNotifierConfiguration = new AdminServerNotifierAutoConfiguration.FeiShuNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    FeiShuNotifier actualFeiShuNotifierResult = feiShuNotifierConfiguration.feiShuNotifier(repository, proxyProperties);

    // Assert
    FeiShuNotifier.Card card = actualFeiShuNotifierResult.getCard();
    assertEquals("Codecentric's Spring Boot Admin notice", card.getTitle());
    assertEquals(
        "ServiceName: #{instance.registration.name}(#{instance.id}) \n"
            + "ServiceUrl: #{instance.registration.serviceUrl} \n"
            + "Status: changed status from [#{lastStatus}] to [#{event.statusInfo.status}]",
        actualFeiShuNotifierResult.getMessage());
    assertEquals("red", card.getThemeColor());
    assertNull(actualFeiShuNotifierResult.getSecret());
    assertNull(actualFeiShuNotifierResult.getWebhookUrl());
    assertEquals(FeiShuNotifier.MessageType.interactive, actualFeiShuNotifierResult.getMessageType());
    assertTrue(actualFeiShuNotifierResult.isEnabled());
    assertTrue(actualFeiShuNotifierResult.isAtAll());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualFeiShuNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.FilteringNotifierWebConfiguration#notificationFilterController()}
   */
  @Test
  public void testFilteringNotifierWebConfigurationNotificationFilterController() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    Notifier delegate = mock(Notifier.class);

    // Act and Assert
    assertTrue((new AdminServerNotifierAutoConfiguration.FilteringNotifierWebConfiguration(
        new FilteringNotifier(delegate, new EventsourcingInstanceRepository(new InMemoryEventStore()))))
        .notificationFilterController()
        .getFilters()
        .isEmpty());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration#hipchatNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testHipchatNotifierConfigurationHipchatNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration hipchatNotifierConfiguration = new AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    HipchatNotifier actualHipchatNotifierResult = hipchatNotifierConfiguration.hipchatNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
        + "}</strong>", actualHipchatNotifierResult.getDescription());
    assertNull(actualHipchatNotifierResult.getAuthToken());
    assertNull(actualHipchatNotifierResult.getRoomId());
    assertNull(actualHipchatNotifierResult.getUrl());
    assertFalse(actualHipchatNotifierResult.isNotify());
    assertTrue(actualHipchatNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualHipchatNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration#hipchatNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testHipchatNotifierConfigurationHipchatNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration hipchatNotifierConfiguration = new AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    HipchatNotifier actualHipchatNotifierResult = hipchatNotifierConfiguration.hipchatNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
        + "}</strong>", actualHipchatNotifierResult.getDescription());
    assertNull(actualHipchatNotifierResult.getAuthToken());
    assertNull(actualHipchatNotifierResult.getRoomId());
    assertNull(actualHipchatNotifierResult.getUrl());
    assertFalse(actualHipchatNotifierResult.isNotify());
    assertTrue(actualHipchatNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualHipchatNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration#hipchatNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testHipchatNotifierConfigurationHipchatNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration hipchatNotifierConfiguration = new AdminServerNotifierAutoConfiguration.HipchatNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    HipchatNotifier actualHipchatNotifierResult = hipchatNotifierConfiguration.hipchatNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
        + "}</strong>", actualHipchatNotifierResult.getDescription());
    assertNull(actualHipchatNotifierResult.getAuthToken());
    assertNull(actualHipchatNotifierResult.getRoomId());
    assertNull(actualHipchatNotifierResult.getUrl());
    assertFalse(actualHipchatNotifierResult.isNotify());
    assertTrue(actualHipchatNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualHipchatNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration#letsChatNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testLetsChatNotifierConfigurationLetsChatNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration letsChatNotifierConfiguration = new AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    LetsChatNotifier actualLetsChatNotifierResult = letsChatNotifierConfiguration.letsChatNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualLetsChatNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualLetsChatNotifierResult.getUsername());
    assertNull(actualLetsChatNotifierResult.getRoom());
    assertNull(actualLetsChatNotifierResult.getToken());
    assertNull(actualLetsChatNotifierResult.getUrl());
    assertTrue(actualLetsChatNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualLetsChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration#letsChatNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testLetsChatNotifierConfigurationLetsChatNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration letsChatNotifierConfiguration = new AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    LetsChatNotifier actualLetsChatNotifierResult = letsChatNotifierConfiguration.letsChatNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualLetsChatNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualLetsChatNotifierResult.getUsername());
    assertNull(actualLetsChatNotifierResult.getRoom());
    assertNull(actualLetsChatNotifierResult.getToken());
    assertNull(actualLetsChatNotifierResult.getUrl());
    assertTrue(actualLetsChatNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualLetsChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration#letsChatNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testLetsChatNotifierConfigurationLetsChatNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration letsChatNotifierConfiguration = new AdminServerNotifierAutoConfiguration.LetsChatNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    LetsChatNotifier actualLetsChatNotifierResult = letsChatNotifierConfiguration.letsChatNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualLetsChatNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualLetsChatNotifierResult.getUsername());
    assertNull(actualLetsChatNotifierResult.getRoom());
    assertNull(actualLetsChatNotifierResult.getToken());
    assertNull(actualLetsChatNotifierResult.getUrl());
    assertTrue(actualLetsChatNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualLetsChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration#microsoftTeamsNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testMicrosoftTeamsNotifierConfigurationMicrosoftTeamsNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration microsoftTeamsNotifierConfiguration = new AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    MicrosoftTeamsNotifier actualMicrosoftTeamsNotifierResult = microsoftTeamsNotifierConfiguration
        .microsoftTeamsNotifier(repository, proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
        + ".statusInfo.status}", actualMicrosoftTeamsNotifierResult.getStatusActivitySubtitle());
    assertEquals("#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getDeregisterActivitySubtitle());
    assertEquals("#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getRegisterActivitySubtitle());
    assertEquals("De-Registered", actualMicrosoftTeamsNotifierResult.getDeRegisteredTitle());
    assertEquals("Registered", actualMicrosoftTeamsNotifierResult.getRegisteredTitle());
    assertEquals("Spring Boot Admin Notification", actualMicrosoftTeamsNotifierResult.getMessageSummary());
    assertEquals("Status Changed", actualMicrosoftTeamsNotifierResult.getStatusChangedTitle());
    assertEquals("event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        actualMicrosoftTeamsNotifierResult.getThemeColor());
    assertNull(actualMicrosoftTeamsNotifierResult.getWebhookUrl());
    assertTrue(actualMicrosoftTeamsNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualMicrosoftTeamsNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration#microsoftTeamsNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testMicrosoftTeamsNotifierConfigurationMicrosoftTeamsNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration microsoftTeamsNotifierConfiguration = new AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    MicrosoftTeamsNotifier actualMicrosoftTeamsNotifierResult = microsoftTeamsNotifierConfiguration
        .microsoftTeamsNotifier(repository, proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
        + ".statusInfo.status}", actualMicrosoftTeamsNotifierResult.getStatusActivitySubtitle());
    assertEquals("#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getDeregisterActivitySubtitle());
    assertEquals("#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getRegisterActivitySubtitle());
    assertEquals("De-Registered", actualMicrosoftTeamsNotifierResult.getDeRegisteredTitle());
    assertEquals("Registered", actualMicrosoftTeamsNotifierResult.getRegisteredTitle());
    assertEquals("Spring Boot Admin Notification", actualMicrosoftTeamsNotifierResult.getMessageSummary());
    assertEquals("Status Changed", actualMicrosoftTeamsNotifierResult.getStatusChangedTitle());
    assertEquals("event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        actualMicrosoftTeamsNotifierResult.getThemeColor());
    assertNull(actualMicrosoftTeamsNotifierResult.getWebhookUrl());
    assertTrue(actualMicrosoftTeamsNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualMicrosoftTeamsNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration#microsoftTeamsNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testMicrosoftTeamsNotifierConfigurationMicrosoftTeamsNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration microsoftTeamsNotifierConfiguration = new AdminServerNotifierAutoConfiguration.MicrosoftTeamsNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    MicrosoftTeamsNotifier actualMicrosoftTeamsNotifierResult = microsoftTeamsNotifierConfiguration
        .microsoftTeamsNotifier(repository, proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
        + ".statusInfo.status}", actualMicrosoftTeamsNotifierResult.getStatusActivitySubtitle());
    assertEquals("#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getDeregisterActivitySubtitle());
    assertEquals("#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        actualMicrosoftTeamsNotifierResult.getRegisterActivitySubtitle());
    assertEquals("De-Registered", actualMicrosoftTeamsNotifierResult.getDeRegisteredTitle());
    assertEquals("Registered", actualMicrosoftTeamsNotifierResult.getRegisteredTitle());
    assertEquals("Spring Boot Admin Notification", actualMicrosoftTeamsNotifierResult.getMessageSummary());
    assertEquals("Status Changed", actualMicrosoftTeamsNotifierResult.getStatusChangedTitle());
    assertEquals("event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        actualMicrosoftTeamsNotifierResult.getThemeColor());
    assertNull(actualMicrosoftTeamsNotifierResult.getWebhookUrl());
    assertTrue(actualMicrosoftTeamsNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualMicrosoftTeamsNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration#opsgenieNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testOpsGenieNotifierConfigurationOpsgenieNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration opsGenieNotifierConfiguration = new AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    OpsGenieNotifier actualOpsgenieNotifierResult = opsGenieNotifierConfiguration.opsgenieNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualOpsgenieNotifierResult.getMessage());
    assertEquals("https://api.opsgenie.com/v2/alerts", actualOpsgenieNotifierResult.getUrl().toString());
    assertNull(actualOpsgenieNotifierResult.getActions());
    assertNull(actualOpsgenieNotifierResult.getApiKey());
    assertNull(actualOpsgenieNotifierResult.getEntity());
    assertNull(actualOpsgenieNotifierResult.getSource());
    assertNull(actualOpsgenieNotifierResult.getTags());
    assertNull(actualOpsgenieNotifierResult.getUser());
    assertTrue(actualOpsgenieNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualOpsgenieNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration#opsgenieNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testOpsGenieNotifierConfigurationOpsgenieNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration opsGenieNotifierConfiguration = new AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    OpsGenieNotifier actualOpsgenieNotifierResult = opsGenieNotifierConfiguration.opsgenieNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualOpsgenieNotifierResult.getMessage());
    assertEquals("https://api.opsgenie.com/v2/alerts", actualOpsgenieNotifierResult.getUrl().toString());
    assertNull(actualOpsgenieNotifierResult.getActions());
    assertNull(actualOpsgenieNotifierResult.getApiKey());
    assertNull(actualOpsgenieNotifierResult.getEntity());
    assertNull(actualOpsgenieNotifierResult.getSource());
    assertNull(actualOpsgenieNotifierResult.getTags());
    assertNull(actualOpsgenieNotifierResult.getUser());
    assertTrue(actualOpsgenieNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualOpsgenieNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration#opsgenieNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testOpsGenieNotifierConfigurationOpsgenieNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration opsGenieNotifierConfiguration = new AdminServerNotifierAutoConfiguration.OpsGenieNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    OpsGenieNotifier actualOpsgenieNotifierResult = opsGenieNotifierConfiguration.opsgenieNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualOpsgenieNotifierResult.getMessage());
    assertEquals("https://api.opsgenie.com/v2/alerts", actualOpsgenieNotifierResult.getUrl().toString());
    assertNull(actualOpsgenieNotifierResult.getActions());
    assertNull(actualOpsgenieNotifierResult.getApiKey());
    assertNull(actualOpsgenieNotifierResult.getEntity());
    assertNull(actualOpsgenieNotifierResult.getSource());
    assertNull(actualOpsgenieNotifierResult.getTags());
    assertNull(actualOpsgenieNotifierResult.getUser());
    assertTrue(actualOpsgenieNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualOpsgenieNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration#pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testPagerdutyNotifierConfigurationPagerdutyNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration pagerdutyNotifierConfiguration = new AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    PagerdutyNotifier actualPagerdutyNotifierResult = pagerdutyNotifierConfiguration.pagerdutyNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualPagerdutyNotifierResult.getDescription());
    URI url = actualPagerdutyNotifierResult.getUrl();
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", url.toString());
    assertNull(actualPagerdutyNotifierResult.getClient());
    assertNull(actualPagerdutyNotifierResult.getServiceKey());
    assertNull(actualPagerdutyNotifierResult.getClientUrl());
    assertTrue(actualPagerdutyNotifierResult.isEnabled());
    assertSame(actualPagerdutyNotifierResult.DEFAULT_URI, url);
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualPagerdutyNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration#pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testPagerdutyNotifierConfigurationPagerdutyNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration pagerdutyNotifierConfiguration = new AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    PagerdutyNotifier actualPagerdutyNotifierResult = pagerdutyNotifierConfiguration.pagerdutyNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualPagerdutyNotifierResult.getDescription());
    URI url = actualPagerdutyNotifierResult.getUrl();
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", url.toString());
    assertNull(actualPagerdutyNotifierResult.getClient());
    assertNull(actualPagerdutyNotifierResult.getServiceKey());
    assertNull(actualPagerdutyNotifierResult.getClientUrl());
    assertTrue(actualPagerdutyNotifierResult.isEnabled());
    assertSame(actualPagerdutyNotifierResult.DEFAULT_URI, url);
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualPagerdutyNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration#pagerdutyNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testPagerdutyNotifierConfigurationPagerdutyNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration pagerdutyNotifierConfiguration = new AdminServerNotifierAutoConfiguration.PagerdutyNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    PagerdutyNotifier actualPagerdutyNotifierResult = pagerdutyNotifierConfiguration.pagerdutyNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualPagerdutyNotifierResult.getDescription());
    URI url = actualPagerdutyNotifierResult.getUrl();
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", url.toString());
    assertNull(actualPagerdutyNotifierResult.getClient());
    assertNull(actualPagerdutyNotifierResult.getServiceKey());
    assertNull(actualPagerdutyNotifierResult.getClientUrl());
    assertTrue(actualPagerdutyNotifierResult.isEnabled());
    assertSame(actualPagerdutyNotifierResult.DEFAULT_URI, url);
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualPagerdutyNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration#rocketChatNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testRocketChatNotifierConfigurationRocketChatNotifier() throws EvaluationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration rocketChatNotifierConfiguration = new AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    RocketChatNotifier actualRocketChatNotifierResult = rocketChatNotifierConfiguration.rocketChatNotifier(repository,
        proxyProperties);

    // Assert
    Expression message = actualRocketChatNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    Expression expression = expressions[0];
    assertTrue(expression instanceof LiteralExpression);
    Expression expression2 = expressions[2];
    assertTrue(expression2 instanceof LiteralExpression);
    Expression expression3 = expressions[4];
    assertTrue(expression3 instanceof LiteralExpression);
    Expression expression4 = expressions[6];
    assertTrue(expression4 instanceof LiteralExpression);
    Expression expression5 = expressions[1];
    SpelNode aST = ((SpelExpression) expression5).getAST();
    assertTrue(aST instanceof CompoundExpression);
    Expression expression6 = expressions[3];
    SpelNode aST2 = ((SpelExpression) expression6).getAST();
    assertTrue(aST2 instanceof CompoundExpression);
    Expression expression7 = expressions[5];
    SpelNode aST3 = ((SpelExpression) expression7).getAST();
    assertTrue(aST3 instanceof CompoundExpression);
    assertTrue(expression5 instanceof SpelExpression);
    assertTrue(expression6 instanceof SpelExpression);
    assertTrue(expression7 instanceof SpelExpression);
    EvaluationContext evaluationContext = ((SpelExpression) expression5).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers = evaluationContext.getConstructorResolvers();
    assertEquals(1, constructorResolvers.size());
    assertTrue(constructorResolvers.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext2 = ((SpelExpression) expression6).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers2 = evaluationContext2.getConstructorResolvers();
    assertEquals(1, constructorResolvers2.size());
    assertTrue(constructorResolvers2.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext3 = ((SpelExpression) expression7).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers3 = evaluationContext3.getConstructorResolvers();
    assertEquals(1, constructorResolvers3.size());
    assertTrue(constructorResolvers3.get(0) instanceof ReflectiveConstructorResolver);
    List<MethodResolver> methodResolvers = evaluationContext.getMethodResolvers();
    assertEquals(1, methodResolvers.size());
    assertTrue(methodResolvers.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers2 = evaluationContext2.getMethodResolvers();
    assertEquals(1, methodResolvers2.size());
    assertTrue(methodResolvers2.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers3 = evaluationContext3.getMethodResolvers();
    assertEquals(1, methodResolvers3.size());
    assertTrue(methodResolvers3.get(0) instanceof ReflectiveMethodResolver);
    List<PropertyAccessor> propertyAccessors = evaluationContext.getPropertyAccessors();
    assertEquals(1, propertyAccessors.size());
    PropertyAccessor getResult = propertyAccessors.get(0);
    assertTrue(getResult instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors2 = evaluationContext2.getPropertyAccessors();
    assertEquals(1, propertyAccessors2.size());
    PropertyAccessor getResult2 = propertyAccessors2.get(0);
    assertTrue(getResult2 instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors3 = evaluationContext3.getPropertyAccessors();
    assertEquals(1, propertyAccessors3.size());
    PropertyAccessor getResult3 = propertyAccessors3.get(0);
    assertTrue(getResult3 instanceof ReflectivePropertyAccessor);
    assertTrue(evaluationContext instanceof StandardEvaluationContext);
    assertTrue(evaluationContext2 instanceof StandardEvaluationContext);
    assertTrue(evaluationContext3 instanceof StandardEvaluationContext);
    OperatorOverloader operatorOverloader = evaluationContext.getOperatorOverloader();
    assertTrue(operatorOverloader instanceof StandardOperatorOverloader);
    TypeComparator typeComparator = evaluationContext.getTypeComparator();
    assertTrue(typeComparator instanceof StandardTypeComparator);
    assertTrue(evaluationContext.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext2.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext3.getTypeConverter() instanceof StandardTypeConverter);
    TypeLocator typeLocator = evaluationContext.getTypeLocator();
    assertTrue(typeLocator instanceof StandardTypeLocator);
    TypeLocator typeLocator2 = evaluationContext2.getTypeLocator();
    assertTrue(typeLocator2 instanceof StandardTypeLocator);
    TypeLocator typeLocator3 = evaluationContext3.getTypeLocator();
    assertTrue(typeLocator3 instanceof StandardTypeLocator);
    assertEquals(") is *", expression3.getExpressionString());
    assertEquals(") is *", expression3.getValue());
    assertEquals("* (", expression2.getExpressionString());
    assertEquals("* (", expression2.getValue());
    assertEquals("*", expression.getExpressionString());
    assertEquals("*", expression4.getExpressionString());
    assertEquals("*", expression.getValue());
    assertEquals("*", expression4.getValue());
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        message.getExpressionString());
    assertEquals("event.statusInfo.status", expression7.getExpressionString());
    assertEquals("event.statusInfo.status", aST3.toStringAST());
    assertEquals("event.statusInfo.status", ((SpelExpression) expression7).toStringAST());
    assertEquals("instance.id", expression6.getExpressionString());
    assertEquals("instance.id", aST2.toStringAST());
    assertEquals("instance.id", ((SpelExpression) expression6).toStringAST());
    assertEquals("instance.registration.name", expression5.getExpressionString());
    assertEquals("instance.registration.name", aST.toStringAST());
    assertEquals("instance.registration.name", ((SpelExpression) expression5).toStringAST());
    List<String> importPrefixes = ((StandardTypeLocator) typeLocator).getImportPrefixes();
    assertEquals(1, importPrefixes.size());
    assertEquals("java.lang", importPrefixes.get(0));
    List<String> importPrefixes2 = ((StandardTypeLocator) typeLocator2).getImportPrefixes();
    assertEquals(1, importPrefixes2.size());
    assertEquals("java.lang", importPrefixes2.get(0));
    List<String> importPrefixes3 = ((StandardTypeLocator) typeLocator3).getImportPrefixes();
    assertEquals(1, importPrefixes3.size());
    assertEquals("java.lang", importPrefixes3.get(0));
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(getResult.getSpecificTargetClasses());
    assertNull(getResult2.getSpecificTargetClasses());
    assertNull(getResult3.getSpecificTargetClasses());
    ResolvableType resolvableType = valueTypeDescriptor.getResolvableType();
    ResolvableType componentType = resolvableType.getComponentType();
    assertNull(componentType.getRawClass());
    TypedValue rootObject = evaluationContext.getRootObject();
    assertNull(rootObject.getValue());
    assertNull(actualRocketChatNotifierResult.getRoomId());
    assertNull(actualRocketChatNotifierResult.getToken());
    assertNull(actualRocketChatNotifierResult.getUrl());
    assertNull(actualRocketChatNotifierResult.getUserId());
    assertNull(((CompoundExpression) aST).getExitDescriptor());
    assertNull(((CompoundExpression) aST2).getExitDescriptor());
    assertNull(((CompoundExpression) aST3).getExitDescriptor());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertNull(rootObject.getTypeDescriptor());
    assertNull(evaluationContext.getBeanResolver());
    assertNull(evaluationContext2.getBeanResolver());
    assertNull(evaluationContext3.getBeanResolver());
    assertEquals(0, aST.getStartPosition());
    assertEquals(0, aST2.getStartPosition());
    assertEquals(0, aST3.getStartPosition());
    ResolvableType[] generics = resolvableType.getGenerics();
    assertEquals(0, generics.length);
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    ResolvableType[] interfaces = resolvableType.getInterfaces();
    ResolvableType resolvableType2 = interfaces[1];
    ResolvableType[] generics2 = resolvableType2.getGenerics();
    assertEquals(1, generics2.length);
    ResolvableType resolvableType3 = generics2[0];
    ResolvableType[] interfaces2 = resolvableType3.getInterfaces();
    ResolvableType resolvableType4 = interfaces2[1];
    ResolvableType[] generics3 = resolvableType4.getGenerics();
    assertEquals(1, generics3.length);
    ResolvableType resolvableType5 = generics3[0];
    ResolvableType[] interfaces3 = resolvableType5.getInterfaces();
    ResolvableType resolvableType6 = interfaces3[1];
    assertEquals(1, resolvableType6.getGenerics().length);
    assertEquals(11, aST2.getEndPosition());
    assertEquals(2, aST2.getChildCount());
    assertEquals(23, aST3.getEndPosition());
    assertEquals(26, aST.getEndPosition());
    assertEquals(3, aST.getChildCount());
    assertEquals(3, aST3.getChildCount());
    assertEquals(5, interfaces.length);
    assertEquals(5, interfaces2.length);
    assertEquals(5, interfaces3.length);
    assertEquals(7, expressions.length);
    assertFalse(componentType.hasGenerics());
    ResolvableType superType = resolvableType.getSuperType();
    assertFalse(superType.hasGenerics());
    ResolvableType superType2 = resolvableType3.getSuperType();
    assertFalse(superType2.hasGenerics());
    ResolvableType superType3 = resolvableType5.getSuperType();
    assertFalse(superType3.hasGenerics());
    assertFalse(resolvableType.hasGenerics());
    assertFalse(resolvableType3.hasGenerics());
    assertFalse(resolvableType5.hasGenerics());
    ResolvableType resolvableType7 = interfaces[0];
    assertFalse(resolvableType7.hasGenerics());
    ResolvableType resolvableType8 = interfaces[2];
    assertFalse(resolvableType8.hasGenerics());
    ResolvableType resolvableType9 = interfaces[3];
    assertFalse(resolvableType9.hasGenerics());
    ResolvableType resolvableType10 = interfaces[4];
    assertFalse(resolvableType10.hasGenerics());
    ResolvableType resolvableType11 = interfaces2[0];
    assertFalse(resolvableType11.hasGenerics());
    ResolvableType resolvableType12 = interfaces2[2];
    assertFalse(resolvableType12.hasGenerics());
    ResolvableType resolvableType13 = interfaces2[3];
    assertFalse(resolvableType13.hasGenerics());
    ResolvableType resolvableType14 = interfaces2[4];
    assertFalse(resolvableType14.hasGenerics());
    ResolvableType resolvableType15 = interfaces3[0];
    assertFalse(resolvableType15.hasGenerics());
    ResolvableType resolvableType16 = interfaces3[2];
    assertFalse(resolvableType16.hasGenerics());
    ResolvableType resolvableType17 = interfaces3[3];
    assertFalse(resolvableType17.hasGenerics());
    ResolvableType resolvableType18 = interfaces3[4];
    assertFalse(resolvableType18.hasGenerics());
    assertFalse(componentType.hasResolvableGenerics());
    assertFalse(superType.hasResolvableGenerics());
    assertFalse(superType2.hasResolvableGenerics());
    assertFalse(superType3.hasResolvableGenerics());
    assertFalse(resolvableType.hasResolvableGenerics());
    assertFalse(resolvableType3.hasResolvableGenerics());
    assertFalse(resolvableType5.hasResolvableGenerics());
    assertFalse(resolvableType7.hasResolvableGenerics());
    assertFalse(resolvableType8.hasResolvableGenerics());
    assertFalse(resolvableType9.hasResolvableGenerics());
    assertFalse(resolvableType10.hasResolvableGenerics());
    assertFalse(resolvableType11.hasResolvableGenerics());
    assertFalse(resolvableType12.hasResolvableGenerics());
    assertFalse(resolvableType13.hasResolvableGenerics());
    assertFalse(resolvableType14.hasResolvableGenerics());
    assertFalse(resolvableType15.hasResolvableGenerics());
    assertFalse(resolvableType16.hasResolvableGenerics());
    assertFalse(resolvableType17.hasResolvableGenerics());
    assertFalse(resolvableType18.hasResolvableGenerics());
    assertFalse(componentType.hasUnresolvableGenerics());
    assertFalse(superType.hasUnresolvableGenerics());
    assertFalse(superType2.hasUnresolvableGenerics());
    assertFalse(superType3.hasUnresolvableGenerics());
    assertFalse(resolvableType.hasUnresolvableGenerics());
    assertFalse(resolvableType3.hasUnresolvableGenerics());
    assertFalse(resolvableType5.hasUnresolvableGenerics());
    assertFalse(resolvableType7.hasUnresolvableGenerics());
    assertFalse(resolvableType2.hasUnresolvableGenerics());
    assertFalse(resolvableType8.hasUnresolvableGenerics());
    assertFalse(resolvableType9.hasUnresolvableGenerics());
    assertFalse(resolvableType10.hasUnresolvableGenerics());
    assertFalse(resolvableType11.hasUnresolvableGenerics());
    assertFalse(resolvableType4.hasUnresolvableGenerics());
    assertFalse(resolvableType12.hasUnresolvableGenerics());
    assertFalse(resolvableType13.hasUnresolvableGenerics());
    assertFalse(resolvableType14.hasUnresolvableGenerics());
    assertFalse(resolvableType15.hasUnresolvableGenerics());
    assertFalse(resolvableType6.hasUnresolvableGenerics());
    assertFalse(resolvableType16.hasUnresolvableGenerics());
    assertFalse(resolvableType17.hasUnresolvableGenerics());
    assertFalse(resolvableType18.hasUnresolvableGenerics());
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    assertFalse(aST.isCompilable());
    assertFalse(aST2.isCompilable());
    assertFalse(aST3.isCompilable());
    assertFalse(((CompoundExpression) aST).isNullSafe());
    assertFalse(((CompoundExpression) aST2).isNullSafe());
    assertFalse(((CompoundExpression) aST3).isNullSafe());
    assertTrue(actualRocketChatNotifierResult.isEnabled());
    assertTrue(evaluationContext.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext2.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext3.getIndexAccessors().isEmpty());
    assertTrue(resolvableType2.hasGenerics());
    assertTrue(resolvableType4.hasGenerics());
    assertTrue(resolvableType6.hasGenerics());
    assertTrue(resolvableType2.hasResolvableGenerics());
    assertTrue(resolvableType4.hasResolvableGenerics());
    assertTrue(resolvableType6.hasResolvableGenerics());
    assertTrue(evaluationContext.isAssignmentEnabled());
    assertTrue(evaluationContext2.isAssignmentEnabled());
    assertTrue(evaluationContext3.isAssignmentEnabled());
    Class<Serializable> expectedRawClass = Serializable.class;
    Class<?> rawClass = resolvableType7.getRawClass();
    assertEquals(expectedRawClass, rawClass);
    Class<CharSequence> expectedRawClass2 = CharSequence.class;
    Class<?> rawClass2 = resolvableType8.getRawClass();
    assertEquals(expectedRawClass2, rawClass2);
    Class<Comparable> expectedRawClass3 = Comparable.class;
    Class<?> rawClass3 = resolvableType2.getRawClass();
    assertEquals(expectedRawClass3, rawClass3);
    Class<Object> expectedRawClass4 = Object.class;
    Class<?> rawClass4 = superType.getRawClass();
    assertEquals(expectedRawClass4, rawClass4);
    Class<String> expectedValueType = String.class;
    Class<?> valueType = message.getValueType();
    assertEquals(expectedValueType, valueType);
    Class<Constable> expectedRawClass5 = Constable.class;
    Class<?> rawClass5 = resolvableType9.getRawClass();
    assertEquals(expectedRawClass5, rawClass5);
    Class<ConstantDesc> expectedRawClass6 = ConstantDesc.class;
    Class<?> rawClass6 = resolvableType10.getRawClass();
    assertEquals(expectedRawClass6, rawClass6);
    ResolvableType componentType2 = componentType.getComponentType();
    assertSame(componentType2, componentType2);
    assertSame(componentType2, superType.getComponentType());
    assertSame(componentType2, superType2.getComponentType());
    assertSame(componentType2, superType3.getComponentType());
    assertSame(componentType2, resolvableType3.getComponentType());
    assertSame(componentType2, resolvableType5.getComponentType());
    assertSame(componentType2, resolvableType7.getComponentType());
    assertSame(componentType2, resolvableType2.getComponentType());
    assertSame(componentType2, resolvableType8.getComponentType());
    assertSame(componentType2, resolvableType9.getComponentType());
    assertSame(componentType2, resolvableType10.getComponentType());
    assertSame(componentType2, resolvableType11.getComponentType());
    assertSame(componentType2, resolvableType4.getComponentType());
    assertSame(componentType2, resolvableType12.getComponentType());
    assertSame(componentType2, resolvableType13.getComponentType());
    assertSame(componentType2, resolvableType14.getComponentType());
    assertSame(componentType2, resolvableType15.getComponentType());
    assertSame(componentType2, resolvableType6.getComponentType());
    assertSame(componentType2, resolvableType16.getComponentType());
    assertSame(componentType2, resolvableType17.getComponentType());
    assertSame(componentType2, resolvableType18.getComponentType());
    assertSame(componentType2, componentType.getSuperType());
    assertSame(componentType2, superType.getSuperType());
    assertSame(componentType2, superType2.getSuperType());
    assertSame(componentType2, superType3.getSuperType());
    assertSame(componentType2, resolvableType7.getSuperType());
    assertSame(componentType2, resolvableType2.getSuperType());
    assertSame(componentType2, resolvableType8.getSuperType());
    assertSame(componentType2, resolvableType9.getSuperType());
    assertSame(componentType2, resolvableType10.getSuperType());
    assertSame(componentType2, resolvableType11.getSuperType());
    assertSame(componentType2, resolvableType4.getSuperType());
    assertSame(componentType2, resolvableType12.getSuperType());
    assertSame(componentType2, resolvableType13.getSuperType());
    assertSame(componentType2, resolvableType14.getSuperType());
    assertSame(componentType2, resolvableType15.getSuperType());
    assertSame(componentType2, resolvableType6.getSuperType());
    assertSame(componentType2, resolvableType16.getSuperType());
    assertSame(componentType2, resolvableType17.getSuperType());
    assertSame(componentType2, resolvableType18.getSuperType());
    assertSame(generics, componentType.getGenerics());
    assertSame(generics, superType.getGenerics());
    assertSame(generics, superType2.getGenerics());
    assertSame(generics, superType3.getGenerics());
    assertSame(generics, resolvableType3.getGenerics());
    assertSame(generics, resolvableType5.getGenerics());
    assertSame(generics, resolvableType7.getGenerics());
    assertSame(generics, resolvableType8.getGenerics());
    assertSame(generics, resolvableType9.getGenerics());
    assertSame(generics, resolvableType10.getGenerics());
    assertSame(generics, resolvableType11.getGenerics());
    assertSame(generics, resolvableType12.getGenerics());
    assertSame(generics, resolvableType13.getGenerics());
    assertSame(generics, resolvableType14.getGenerics());
    assertSame(generics, resolvableType15.getGenerics());
    assertSame(generics, resolvableType16.getGenerics());
    assertSame(generics, resolvableType17.getGenerics());
    assertSame(generics, resolvableType18.getGenerics());
    assertSame(generics, componentType.getInterfaces());
    assertSame(generics, superType.getInterfaces());
    assertSame(generics, superType2.getInterfaces());
    assertSame(generics, superType3.getInterfaces());
    assertSame(generics, resolvableType7.getInterfaces());
    assertSame(generics, resolvableType2.getInterfaces());
    assertSame(generics, resolvableType8.getInterfaces());
    assertSame(generics, resolvableType9.getInterfaces());
    assertSame(generics, resolvableType10.getInterfaces());
    assertSame(generics, resolvableType11.getInterfaces());
    assertSame(generics, resolvableType4.getInterfaces());
    assertSame(generics, resolvableType12.getInterfaces());
    assertSame(generics, resolvableType13.getInterfaces());
    assertSame(generics, resolvableType14.getInterfaces());
    assertSame(generics, resolvableType15.getInterfaces());
    assertSame(generics, resolvableType6.getInterfaces());
    assertSame(generics, resolvableType16.getInterfaces());
    assertSame(generics, resolvableType17.getInterfaces());
    assertSame(generics, resolvableType18.getInterfaces());
    assertSame(rawClass4, superType2.getRawClass());
    assertSame(rawClass4, superType3.getRawClass());
    assertSame(rawClass4, superType.getSource());
    assertSame(rawClass4, superType2.getSource());
    assertSame(rawClass4, superType3.getSource());
    assertSame(rawClass4, superType.getType());
    assertSame(rawClass4, superType2.getType());
    assertSame(rawClass4, superType3.getType());
    assertSame(rawClass, resolvableType11.getRawClass());
    assertSame(rawClass, resolvableType15.getRawClass());
    assertSame(rawClass, resolvableType7.getSource());
    assertSame(rawClass, resolvableType11.getSource());
    assertSame(rawClass, resolvableType15.getSource());
    assertSame(rawClass, resolvableType7.getType());
    assertSame(rawClass, resolvableType11.getType());
    assertSame(rawClass, resolvableType15.getType());
    assertSame(rawClass3, resolvableType4.getRawClass());
    assertSame(rawClass3, resolvableType6.getRawClass());
    assertSame(rawClass2, resolvableType12.getRawClass());
    assertSame(rawClass2, resolvableType16.getRawClass());
    assertSame(rawClass2, resolvableType8.getSource());
    assertSame(rawClass2, resolvableType12.getSource());
    assertSame(rawClass2, resolvableType16.getSource());
    assertSame(rawClass2, resolvableType8.getType());
    assertSame(rawClass2, resolvableType12.getType());
    assertSame(rawClass2, resolvableType16.getType());
    assertSame(rawClass5, resolvableType13.getRawClass());
    assertSame(rawClass5, resolvableType17.getRawClass());
    assertSame(rawClass5, resolvableType9.getSource());
    assertSame(rawClass5, resolvableType13.getSource());
    assertSame(rawClass5, resolvableType17.getSource());
    assertSame(rawClass5, resolvableType9.getType());
    assertSame(rawClass5, resolvableType13.getType());
    assertSame(rawClass5, resolvableType17.getType());
    assertSame(rawClass6, resolvableType14.getRawClass());
    assertSame(rawClass6, resolvableType18.getRawClass());
    assertSame(rawClass6, resolvableType10.getSource());
    assertSame(rawClass6, resolvableType14.getSource());
    assertSame(rawClass6, resolvableType18.getSource());
    assertSame(rawClass6, resolvableType10.getType());
    assertSame(rawClass6, resolvableType14.getType());
    assertSame(rawClass6, resolvableType18.getType());
    assertSame(rootObject, evaluationContext2.getRootObject());
    assertSame(rootObject, evaluationContext3.getRootObject());
    assertSame(valueType, resolvableType.getRawClass());
    assertSame(valueType, resolvableType3.getRawClass());
    assertSame(valueType, resolvableType5.getRawClass());
    assertSame(valueType, resolvableType.getSource());
    assertSame(valueType, resolvableType3.getSource());
    assertSame(valueType, resolvableType5.getSource());
    assertSame(valueType, resolvableType.getType());
    assertSame(valueType, resolvableType3.getType());
    assertSame(valueType, resolvableType5.getType());
    assertSame(valueType, valueTypeDescriptor.getObjectType());
    assertSame(valueType, valueTypeDescriptor.getSource());
    assertSame(valueType, valueTypeDescriptor.getType());
    assertSame(valueType, expression.getValueType());
    assertSame(valueType, expression2.getValueType());
    assertSame(valueType, expression3.getValueType());
    assertSame(valueType, expression4.getValueType());
    assertSame(valueTypeDescriptor, expression.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression2.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression3.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression4.getValueTypeDescriptor());
    assertSame(operatorOverloader, evaluationContext2.getOperatorOverloader());
    assertSame(operatorOverloader, evaluationContext3.getOperatorOverloader());
    assertSame(typeComparator, evaluationContext2.getTypeComparator());
    assertSame(typeComparator, evaluationContext3.getTypeComparator());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualRocketChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration#rocketChatNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testRocketChatNotifierConfigurationRocketChatNotifier2() throws EvaluationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration rocketChatNotifierConfiguration = new AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    RocketChatNotifier actualRocketChatNotifierResult = rocketChatNotifierConfiguration.rocketChatNotifier(repository,
        proxyProperties);

    // Assert
    Expression message = actualRocketChatNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    Expression expression = expressions[0];
    assertTrue(expression instanceof LiteralExpression);
    Expression expression2 = expressions[2];
    assertTrue(expression2 instanceof LiteralExpression);
    Expression expression3 = expressions[4];
    assertTrue(expression3 instanceof LiteralExpression);
    Expression expression4 = expressions[6];
    assertTrue(expression4 instanceof LiteralExpression);
    Expression expression5 = expressions[1];
    SpelNode aST = ((SpelExpression) expression5).getAST();
    assertTrue(aST instanceof CompoundExpression);
    Expression expression6 = expressions[3];
    SpelNode aST2 = ((SpelExpression) expression6).getAST();
    assertTrue(aST2 instanceof CompoundExpression);
    Expression expression7 = expressions[5];
    SpelNode aST3 = ((SpelExpression) expression7).getAST();
    assertTrue(aST3 instanceof CompoundExpression);
    assertTrue(expression5 instanceof SpelExpression);
    assertTrue(expression6 instanceof SpelExpression);
    assertTrue(expression7 instanceof SpelExpression);
    EvaluationContext evaluationContext = ((SpelExpression) expression5).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers = evaluationContext.getConstructorResolvers();
    assertEquals(1, constructorResolvers.size());
    assertTrue(constructorResolvers.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext2 = ((SpelExpression) expression6).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers2 = evaluationContext2.getConstructorResolvers();
    assertEquals(1, constructorResolvers2.size());
    assertTrue(constructorResolvers2.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext3 = ((SpelExpression) expression7).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers3 = evaluationContext3.getConstructorResolvers();
    assertEquals(1, constructorResolvers3.size());
    assertTrue(constructorResolvers3.get(0) instanceof ReflectiveConstructorResolver);
    List<MethodResolver> methodResolvers = evaluationContext.getMethodResolvers();
    assertEquals(1, methodResolvers.size());
    assertTrue(methodResolvers.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers2 = evaluationContext2.getMethodResolvers();
    assertEquals(1, methodResolvers2.size());
    assertTrue(methodResolvers2.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers3 = evaluationContext3.getMethodResolvers();
    assertEquals(1, methodResolvers3.size());
    assertTrue(methodResolvers3.get(0) instanceof ReflectiveMethodResolver);
    List<PropertyAccessor> propertyAccessors = evaluationContext.getPropertyAccessors();
    assertEquals(1, propertyAccessors.size());
    PropertyAccessor getResult = propertyAccessors.get(0);
    assertTrue(getResult instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors2 = evaluationContext2.getPropertyAccessors();
    assertEquals(1, propertyAccessors2.size());
    PropertyAccessor getResult2 = propertyAccessors2.get(0);
    assertTrue(getResult2 instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors3 = evaluationContext3.getPropertyAccessors();
    assertEquals(1, propertyAccessors3.size());
    PropertyAccessor getResult3 = propertyAccessors3.get(0);
    assertTrue(getResult3 instanceof ReflectivePropertyAccessor);
    assertTrue(evaluationContext instanceof StandardEvaluationContext);
    assertTrue(evaluationContext2 instanceof StandardEvaluationContext);
    assertTrue(evaluationContext3 instanceof StandardEvaluationContext);
    OperatorOverloader operatorOverloader = evaluationContext.getOperatorOverloader();
    assertTrue(operatorOverloader instanceof StandardOperatorOverloader);
    TypeComparator typeComparator = evaluationContext.getTypeComparator();
    assertTrue(typeComparator instanceof StandardTypeComparator);
    assertTrue(evaluationContext.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext2.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext3.getTypeConverter() instanceof StandardTypeConverter);
    TypeLocator typeLocator = evaluationContext.getTypeLocator();
    assertTrue(typeLocator instanceof StandardTypeLocator);
    TypeLocator typeLocator2 = evaluationContext2.getTypeLocator();
    assertTrue(typeLocator2 instanceof StandardTypeLocator);
    TypeLocator typeLocator3 = evaluationContext3.getTypeLocator();
    assertTrue(typeLocator3 instanceof StandardTypeLocator);
    assertEquals(") is *", expression3.getExpressionString());
    assertEquals(") is *", expression3.getValue());
    assertEquals("* (", expression2.getExpressionString());
    assertEquals("* (", expression2.getValue());
    assertEquals("*", expression.getExpressionString());
    assertEquals("*", expression4.getExpressionString());
    assertEquals("*", expression.getValue());
    assertEquals("*", expression4.getValue());
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        message.getExpressionString());
    assertEquals("event.statusInfo.status", expression7.getExpressionString());
    assertEquals("event.statusInfo.status", aST3.toStringAST());
    assertEquals("event.statusInfo.status", ((SpelExpression) expression7).toStringAST());
    assertEquals("instance.id", expression6.getExpressionString());
    assertEquals("instance.id", aST2.toStringAST());
    assertEquals("instance.id", ((SpelExpression) expression6).toStringAST());
    assertEquals("instance.registration.name", expression5.getExpressionString());
    assertEquals("instance.registration.name", aST.toStringAST());
    assertEquals("instance.registration.name", ((SpelExpression) expression5).toStringAST());
    List<String> importPrefixes = ((StandardTypeLocator) typeLocator).getImportPrefixes();
    assertEquals(1, importPrefixes.size());
    assertEquals("java.lang", importPrefixes.get(0));
    List<String> importPrefixes2 = ((StandardTypeLocator) typeLocator2).getImportPrefixes();
    assertEquals(1, importPrefixes2.size());
    assertEquals("java.lang", importPrefixes2.get(0));
    List<String> importPrefixes3 = ((StandardTypeLocator) typeLocator3).getImportPrefixes();
    assertEquals(1, importPrefixes3.size());
    assertEquals("java.lang", importPrefixes3.get(0));
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(getResult.getSpecificTargetClasses());
    assertNull(getResult2.getSpecificTargetClasses());
    assertNull(getResult3.getSpecificTargetClasses());
    ResolvableType resolvableType = valueTypeDescriptor.getResolvableType();
    ResolvableType componentType = resolvableType.getComponentType();
    assertNull(componentType.getRawClass());
    TypedValue rootObject = evaluationContext.getRootObject();
    assertNull(rootObject.getValue());
    assertNull(actualRocketChatNotifierResult.getRoomId());
    assertNull(actualRocketChatNotifierResult.getToken());
    assertNull(actualRocketChatNotifierResult.getUrl());
    assertNull(actualRocketChatNotifierResult.getUserId());
    assertNull(((CompoundExpression) aST).getExitDescriptor());
    assertNull(((CompoundExpression) aST2).getExitDescriptor());
    assertNull(((CompoundExpression) aST3).getExitDescriptor());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertNull(rootObject.getTypeDescriptor());
    assertNull(evaluationContext.getBeanResolver());
    assertNull(evaluationContext2.getBeanResolver());
    assertNull(evaluationContext3.getBeanResolver());
    assertEquals(0, aST.getStartPosition());
    assertEquals(0, aST2.getStartPosition());
    assertEquals(0, aST3.getStartPosition());
    ResolvableType[] generics = resolvableType.getGenerics();
    assertEquals(0, generics.length);
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    ResolvableType[] interfaces = resolvableType.getInterfaces();
    ResolvableType resolvableType2 = interfaces[1];
    ResolvableType[] generics2 = resolvableType2.getGenerics();
    assertEquals(1, generics2.length);
    ResolvableType resolvableType3 = generics2[0];
    ResolvableType[] interfaces2 = resolvableType3.getInterfaces();
    ResolvableType resolvableType4 = interfaces2[1];
    ResolvableType[] generics3 = resolvableType4.getGenerics();
    assertEquals(1, generics3.length);
    ResolvableType resolvableType5 = generics3[0];
    ResolvableType[] interfaces3 = resolvableType5.getInterfaces();
    ResolvableType resolvableType6 = interfaces3[1];
    assertEquals(1, resolvableType6.getGenerics().length);
    assertEquals(11, aST2.getEndPosition());
    assertEquals(2, aST2.getChildCount());
    assertEquals(23, aST3.getEndPosition());
    assertEquals(26, aST.getEndPosition());
    assertEquals(3, aST.getChildCount());
    assertEquals(3, aST3.getChildCount());
    assertEquals(5, interfaces.length);
    assertEquals(5, interfaces2.length);
    assertEquals(5, interfaces3.length);
    assertEquals(7, expressions.length);
    assertFalse(componentType.hasGenerics());
    ResolvableType superType = resolvableType.getSuperType();
    assertFalse(superType.hasGenerics());
    ResolvableType superType2 = resolvableType3.getSuperType();
    assertFalse(superType2.hasGenerics());
    ResolvableType superType3 = resolvableType5.getSuperType();
    assertFalse(superType3.hasGenerics());
    assertFalse(resolvableType.hasGenerics());
    assertFalse(resolvableType3.hasGenerics());
    assertFalse(resolvableType5.hasGenerics());
    ResolvableType resolvableType7 = interfaces[0];
    assertFalse(resolvableType7.hasGenerics());
    ResolvableType resolvableType8 = interfaces[2];
    assertFalse(resolvableType8.hasGenerics());
    ResolvableType resolvableType9 = interfaces[3];
    assertFalse(resolvableType9.hasGenerics());
    ResolvableType resolvableType10 = interfaces[4];
    assertFalse(resolvableType10.hasGenerics());
    ResolvableType resolvableType11 = interfaces2[0];
    assertFalse(resolvableType11.hasGenerics());
    ResolvableType resolvableType12 = interfaces2[2];
    assertFalse(resolvableType12.hasGenerics());
    ResolvableType resolvableType13 = interfaces2[3];
    assertFalse(resolvableType13.hasGenerics());
    ResolvableType resolvableType14 = interfaces2[4];
    assertFalse(resolvableType14.hasGenerics());
    ResolvableType resolvableType15 = interfaces3[0];
    assertFalse(resolvableType15.hasGenerics());
    ResolvableType resolvableType16 = interfaces3[2];
    assertFalse(resolvableType16.hasGenerics());
    ResolvableType resolvableType17 = interfaces3[3];
    assertFalse(resolvableType17.hasGenerics());
    ResolvableType resolvableType18 = interfaces3[4];
    assertFalse(resolvableType18.hasGenerics());
    assertFalse(componentType.hasResolvableGenerics());
    assertFalse(superType.hasResolvableGenerics());
    assertFalse(superType2.hasResolvableGenerics());
    assertFalse(superType3.hasResolvableGenerics());
    assertFalse(resolvableType.hasResolvableGenerics());
    assertFalse(resolvableType3.hasResolvableGenerics());
    assertFalse(resolvableType5.hasResolvableGenerics());
    assertFalse(resolvableType7.hasResolvableGenerics());
    assertFalse(resolvableType8.hasResolvableGenerics());
    assertFalse(resolvableType9.hasResolvableGenerics());
    assertFalse(resolvableType10.hasResolvableGenerics());
    assertFalse(resolvableType11.hasResolvableGenerics());
    assertFalse(resolvableType12.hasResolvableGenerics());
    assertFalse(resolvableType13.hasResolvableGenerics());
    assertFalse(resolvableType14.hasResolvableGenerics());
    assertFalse(resolvableType15.hasResolvableGenerics());
    assertFalse(resolvableType16.hasResolvableGenerics());
    assertFalse(resolvableType17.hasResolvableGenerics());
    assertFalse(resolvableType18.hasResolvableGenerics());
    assertFalse(componentType.hasUnresolvableGenerics());
    assertFalse(superType.hasUnresolvableGenerics());
    assertFalse(superType2.hasUnresolvableGenerics());
    assertFalse(superType3.hasUnresolvableGenerics());
    assertFalse(resolvableType.hasUnresolvableGenerics());
    assertFalse(resolvableType3.hasUnresolvableGenerics());
    assertFalse(resolvableType5.hasUnresolvableGenerics());
    assertFalse(resolvableType7.hasUnresolvableGenerics());
    assertFalse(resolvableType2.hasUnresolvableGenerics());
    assertFalse(resolvableType8.hasUnresolvableGenerics());
    assertFalse(resolvableType9.hasUnresolvableGenerics());
    assertFalse(resolvableType10.hasUnresolvableGenerics());
    assertFalse(resolvableType11.hasUnresolvableGenerics());
    assertFalse(resolvableType4.hasUnresolvableGenerics());
    assertFalse(resolvableType12.hasUnresolvableGenerics());
    assertFalse(resolvableType13.hasUnresolvableGenerics());
    assertFalse(resolvableType14.hasUnresolvableGenerics());
    assertFalse(resolvableType15.hasUnresolvableGenerics());
    assertFalse(resolvableType6.hasUnresolvableGenerics());
    assertFalse(resolvableType16.hasUnresolvableGenerics());
    assertFalse(resolvableType17.hasUnresolvableGenerics());
    assertFalse(resolvableType18.hasUnresolvableGenerics());
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    assertFalse(aST.isCompilable());
    assertFalse(aST2.isCompilable());
    assertFalse(aST3.isCompilable());
    assertFalse(((CompoundExpression) aST).isNullSafe());
    assertFalse(((CompoundExpression) aST2).isNullSafe());
    assertFalse(((CompoundExpression) aST3).isNullSafe());
    assertTrue(actualRocketChatNotifierResult.isEnabled());
    assertTrue(evaluationContext.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext2.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext3.getIndexAccessors().isEmpty());
    assertTrue(resolvableType2.hasGenerics());
    assertTrue(resolvableType4.hasGenerics());
    assertTrue(resolvableType6.hasGenerics());
    assertTrue(resolvableType2.hasResolvableGenerics());
    assertTrue(resolvableType4.hasResolvableGenerics());
    assertTrue(resolvableType6.hasResolvableGenerics());
    assertTrue(evaluationContext.isAssignmentEnabled());
    assertTrue(evaluationContext2.isAssignmentEnabled());
    assertTrue(evaluationContext3.isAssignmentEnabled());
    Class<Serializable> expectedRawClass = Serializable.class;
    Class<?> rawClass = resolvableType7.getRawClass();
    assertEquals(expectedRawClass, rawClass);
    Class<CharSequence> expectedRawClass2 = CharSequence.class;
    Class<?> rawClass2 = resolvableType8.getRawClass();
    assertEquals(expectedRawClass2, rawClass2);
    Class<Comparable> expectedRawClass3 = Comparable.class;
    Class<?> rawClass3 = resolvableType2.getRawClass();
    assertEquals(expectedRawClass3, rawClass3);
    Class<Object> expectedRawClass4 = Object.class;
    Class<?> rawClass4 = superType.getRawClass();
    assertEquals(expectedRawClass4, rawClass4);
    Class<String> expectedValueType = String.class;
    Class<?> valueType = message.getValueType();
    assertEquals(expectedValueType, valueType);
    Class<Constable> expectedRawClass5 = Constable.class;
    Class<?> rawClass5 = resolvableType9.getRawClass();
    assertEquals(expectedRawClass5, rawClass5);
    Class<ConstantDesc> expectedRawClass6 = ConstantDesc.class;
    Class<?> rawClass6 = resolvableType10.getRawClass();
    assertEquals(expectedRawClass6, rawClass6);
    ResolvableType componentType2 = componentType.getComponentType();
    assertSame(componentType2, componentType2);
    assertSame(componentType2, superType.getComponentType());
    assertSame(componentType2, superType2.getComponentType());
    assertSame(componentType2, superType3.getComponentType());
    assertSame(componentType2, resolvableType3.getComponentType());
    assertSame(componentType2, resolvableType5.getComponentType());
    assertSame(componentType2, resolvableType7.getComponentType());
    assertSame(componentType2, resolvableType2.getComponentType());
    assertSame(componentType2, resolvableType8.getComponentType());
    assertSame(componentType2, resolvableType9.getComponentType());
    assertSame(componentType2, resolvableType10.getComponentType());
    assertSame(componentType2, resolvableType11.getComponentType());
    assertSame(componentType2, resolvableType4.getComponentType());
    assertSame(componentType2, resolvableType12.getComponentType());
    assertSame(componentType2, resolvableType13.getComponentType());
    assertSame(componentType2, resolvableType14.getComponentType());
    assertSame(componentType2, resolvableType15.getComponentType());
    assertSame(componentType2, resolvableType6.getComponentType());
    assertSame(componentType2, resolvableType16.getComponentType());
    assertSame(componentType2, resolvableType17.getComponentType());
    assertSame(componentType2, resolvableType18.getComponentType());
    assertSame(componentType2, componentType.getSuperType());
    assertSame(componentType2, superType.getSuperType());
    assertSame(componentType2, superType2.getSuperType());
    assertSame(componentType2, superType3.getSuperType());
    assertSame(componentType2, resolvableType7.getSuperType());
    assertSame(componentType2, resolvableType2.getSuperType());
    assertSame(componentType2, resolvableType8.getSuperType());
    assertSame(componentType2, resolvableType9.getSuperType());
    assertSame(componentType2, resolvableType10.getSuperType());
    assertSame(componentType2, resolvableType11.getSuperType());
    assertSame(componentType2, resolvableType4.getSuperType());
    assertSame(componentType2, resolvableType12.getSuperType());
    assertSame(componentType2, resolvableType13.getSuperType());
    assertSame(componentType2, resolvableType14.getSuperType());
    assertSame(componentType2, resolvableType15.getSuperType());
    assertSame(componentType2, resolvableType6.getSuperType());
    assertSame(componentType2, resolvableType16.getSuperType());
    assertSame(componentType2, resolvableType17.getSuperType());
    assertSame(componentType2, resolvableType18.getSuperType());
    assertSame(generics, componentType.getGenerics());
    assertSame(generics, superType.getGenerics());
    assertSame(generics, superType2.getGenerics());
    assertSame(generics, superType3.getGenerics());
    assertSame(generics, resolvableType3.getGenerics());
    assertSame(generics, resolvableType5.getGenerics());
    assertSame(generics, resolvableType7.getGenerics());
    assertSame(generics, resolvableType8.getGenerics());
    assertSame(generics, resolvableType9.getGenerics());
    assertSame(generics, resolvableType10.getGenerics());
    assertSame(generics, resolvableType11.getGenerics());
    assertSame(generics, resolvableType12.getGenerics());
    assertSame(generics, resolvableType13.getGenerics());
    assertSame(generics, resolvableType14.getGenerics());
    assertSame(generics, resolvableType15.getGenerics());
    assertSame(generics, resolvableType16.getGenerics());
    assertSame(generics, resolvableType17.getGenerics());
    assertSame(generics, resolvableType18.getGenerics());
    assertSame(generics, componentType.getInterfaces());
    assertSame(generics, superType.getInterfaces());
    assertSame(generics, superType2.getInterfaces());
    assertSame(generics, superType3.getInterfaces());
    assertSame(generics, resolvableType7.getInterfaces());
    assertSame(generics, resolvableType2.getInterfaces());
    assertSame(generics, resolvableType8.getInterfaces());
    assertSame(generics, resolvableType9.getInterfaces());
    assertSame(generics, resolvableType10.getInterfaces());
    assertSame(generics, resolvableType11.getInterfaces());
    assertSame(generics, resolvableType4.getInterfaces());
    assertSame(generics, resolvableType12.getInterfaces());
    assertSame(generics, resolvableType13.getInterfaces());
    assertSame(generics, resolvableType14.getInterfaces());
    assertSame(generics, resolvableType15.getInterfaces());
    assertSame(generics, resolvableType6.getInterfaces());
    assertSame(generics, resolvableType16.getInterfaces());
    assertSame(generics, resolvableType17.getInterfaces());
    assertSame(generics, resolvableType18.getInterfaces());
    assertSame(rawClass4, superType2.getRawClass());
    assertSame(rawClass4, superType3.getRawClass());
    assertSame(rawClass4, superType.getSource());
    assertSame(rawClass4, superType2.getSource());
    assertSame(rawClass4, superType3.getSource());
    assertSame(rawClass4, superType.getType());
    assertSame(rawClass4, superType2.getType());
    assertSame(rawClass4, superType3.getType());
    assertSame(rawClass, resolvableType11.getRawClass());
    assertSame(rawClass, resolvableType15.getRawClass());
    assertSame(rawClass, resolvableType7.getSource());
    assertSame(rawClass, resolvableType11.getSource());
    assertSame(rawClass, resolvableType15.getSource());
    assertSame(rawClass, resolvableType7.getType());
    assertSame(rawClass, resolvableType11.getType());
    assertSame(rawClass, resolvableType15.getType());
    assertSame(rawClass3, resolvableType4.getRawClass());
    assertSame(rawClass3, resolvableType6.getRawClass());
    assertSame(rawClass2, resolvableType12.getRawClass());
    assertSame(rawClass2, resolvableType16.getRawClass());
    assertSame(rawClass2, resolvableType8.getSource());
    assertSame(rawClass2, resolvableType12.getSource());
    assertSame(rawClass2, resolvableType16.getSource());
    assertSame(rawClass2, resolvableType8.getType());
    assertSame(rawClass2, resolvableType12.getType());
    assertSame(rawClass2, resolvableType16.getType());
    assertSame(rawClass5, resolvableType13.getRawClass());
    assertSame(rawClass5, resolvableType17.getRawClass());
    assertSame(rawClass5, resolvableType9.getSource());
    assertSame(rawClass5, resolvableType13.getSource());
    assertSame(rawClass5, resolvableType17.getSource());
    assertSame(rawClass5, resolvableType9.getType());
    assertSame(rawClass5, resolvableType13.getType());
    assertSame(rawClass5, resolvableType17.getType());
    assertSame(rawClass6, resolvableType14.getRawClass());
    assertSame(rawClass6, resolvableType18.getRawClass());
    assertSame(rawClass6, resolvableType10.getSource());
    assertSame(rawClass6, resolvableType14.getSource());
    assertSame(rawClass6, resolvableType18.getSource());
    assertSame(rawClass6, resolvableType10.getType());
    assertSame(rawClass6, resolvableType14.getType());
    assertSame(rawClass6, resolvableType18.getType());
    assertSame(rootObject, evaluationContext2.getRootObject());
    assertSame(rootObject, evaluationContext3.getRootObject());
    assertSame(valueType, resolvableType.getRawClass());
    assertSame(valueType, resolvableType3.getRawClass());
    assertSame(valueType, resolvableType5.getRawClass());
    assertSame(valueType, resolvableType.getSource());
    assertSame(valueType, resolvableType3.getSource());
    assertSame(valueType, resolvableType5.getSource());
    assertSame(valueType, resolvableType.getType());
    assertSame(valueType, resolvableType3.getType());
    assertSame(valueType, resolvableType5.getType());
    assertSame(valueType, valueTypeDescriptor.getObjectType());
    assertSame(valueType, valueTypeDescriptor.getSource());
    assertSame(valueType, valueTypeDescriptor.getType());
    assertSame(valueType, expression.getValueType());
    assertSame(valueType, expression2.getValueType());
    assertSame(valueType, expression3.getValueType());
    assertSame(valueType, expression4.getValueType());
    assertSame(valueTypeDescriptor, expression.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression2.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression3.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression4.getValueTypeDescriptor());
    assertSame(operatorOverloader, evaluationContext2.getOperatorOverloader());
    assertSame(operatorOverloader, evaluationContext3.getOperatorOverloader());
    assertSame(typeComparator, evaluationContext2.getTypeComparator());
    assertSame(typeComparator, evaluationContext3.getTypeComparator());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualRocketChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration#rocketChatNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testRocketChatNotifierConfigurationRocketChatNotifier3() throws EvaluationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration rocketChatNotifierConfiguration = new AdminServerNotifierAutoConfiguration.RocketChatNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    RocketChatNotifier actualRocketChatNotifierResult = rocketChatNotifierConfiguration.rocketChatNotifier(repository,
        proxyProperties);

    // Assert
    Expression message = actualRocketChatNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    Expression expression = expressions[0];
    assertTrue(expression instanceof LiteralExpression);
    Expression expression2 = expressions[2];
    assertTrue(expression2 instanceof LiteralExpression);
    Expression expression3 = expressions[4];
    assertTrue(expression3 instanceof LiteralExpression);
    Expression expression4 = expressions[6];
    assertTrue(expression4 instanceof LiteralExpression);
    Expression expression5 = expressions[1];
    SpelNode aST = ((SpelExpression) expression5).getAST();
    assertTrue(aST instanceof CompoundExpression);
    Expression expression6 = expressions[3];
    SpelNode aST2 = ((SpelExpression) expression6).getAST();
    assertTrue(aST2 instanceof CompoundExpression);
    Expression expression7 = expressions[5];
    SpelNode aST3 = ((SpelExpression) expression7).getAST();
    assertTrue(aST3 instanceof CompoundExpression);
    assertTrue(expression5 instanceof SpelExpression);
    assertTrue(expression6 instanceof SpelExpression);
    assertTrue(expression7 instanceof SpelExpression);
    EvaluationContext evaluationContext = ((SpelExpression) expression5).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers = evaluationContext.getConstructorResolvers();
    assertEquals(1, constructorResolvers.size());
    assertTrue(constructorResolvers.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext2 = ((SpelExpression) expression6).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers2 = evaluationContext2.getConstructorResolvers();
    assertEquals(1, constructorResolvers2.size());
    assertTrue(constructorResolvers2.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext3 = ((SpelExpression) expression7).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers3 = evaluationContext3.getConstructorResolvers();
    assertEquals(1, constructorResolvers3.size());
    assertTrue(constructorResolvers3.get(0) instanceof ReflectiveConstructorResolver);
    List<MethodResolver> methodResolvers = evaluationContext.getMethodResolvers();
    assertEquals(1, methodResolvers.size());
    assertTrue(methodResolvers.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers2 = evaluationContext2.getMethodResolvers();
    assertEquals(1, methodResolvers2.size());
    assertTrue(methodResolvers2.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers3 = evaluationContext3.getMethodResolvers();
    assertEquals(1, methodResolvers3.size());
    assertTrue(methodResolvers3.get(0) instanceof ReflectiveMethodResolver);
    List<PropertyAccessor> propertyAccessors = evaluationContext.getPropertyAccessors();
    assertEquals(1, propertyAccessors.size());
    PropertyAccessor getResult = propertyAccessors.get(0);
    assertTrue(getResult instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors2 = evaluationContext2.getPropertyAccessors();
    assertEquals(1, propertyAccessors2.size());
    PropertyAccessor getResult2 = propertyAccessors2.get(0);
    assertTrue(getResult2 instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors3 = evaluationContext3.getPropertyAccessors();
    assertEquals(1, propertyAccessors3.size());
    PropertyAccessor getResult3 = propertyAccessors3.get(0);
    assertTrue(getResult3 instanceof ReflectivePropertyAccessor);
    assertTrue(evaluationContext instanceof StandardEvaluationContext);
    assertTrue(evaluationContext2 instanceof StandardEvaluationContext);
    assertTrue(evaluationContext3 instanceof StandardEvaluationContext);
    OperatorOverloader operatorOverloader = evaluationContext.getOperatorOverloader();
    assertTrue(operatorOverloader instanceof StandardOperatorOverloader);
    TypeComparator typeComparator = evaluationContext.getTypeComparator();
    assertTrue(typeComparator instanceof StandardTypeComparator);
    assertTrue(evaluationContext.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext2.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext3.getTypeConverter() instanceof StandardTypeConverter);
    TypeLocator typeLocator = evaluationContext.getTypeLocator();
    assertTrue(typeLocator instanceof StandardTypeLocator);
    TypeLocator typeLocator2 = evaluationContext2.getTypeLocator();
    assertTrue(typeLocator2 instanceof StandardTypeLocator);
    TypeLocator typeLocator3 = evaluationContext3.getTypeLocator();
    assertTrue(typeLocator3 instanceof StandardTypeLocator);
    assertEquals(") is *", expression3.getExpressionString());
    assertEquals(") is *", expression3.getValue());
    assertEquals("* (", expression2.getExpressionString());
    assertEquals("* (", expression2.getValue());
    assertEquals("*", expression.getExpressionString());
    assertEquals("*", expression4.getExpressionString());
    assertEquals("*", expression.getValue());
    assertEquals("*", expression4.getValue());
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        message.getExpressionString());
    assertEquals("event.statusInfo.status", expression7.getExpressionString());
    assertEquals("event.statusInfo.status", aST3.toStringAST());
    assertEquals("event.statusInfo.status", ((SpelExpression) expression7).toStringAST());
    assertEquals("instance.id", expression6.getExpressionString());
    assertEquals("instance.id", aST2.toStringAST());
    assertEquals("instance.id", ((SpelExpression) expression6).toStringAST());
    assertEquals("instance.registration.name", expression5.getExpressionString());
    assertEquals("instance.registration.name", aST.toStringAST());
    assertEquals("instance.registration.name", ((SpelExpression) expression5).toStringAST());
    List<String> importPrefixes = ((StandardTypeLocator) typeLocator).getImportPrefixes();
    assertEquals(1, importPrefixes.size());
    assertEquals("java.lang", importPrefixes.get(0));
    List<String> importPrefixes2 = ((StandardTypeLocator) typeLocator2).getImportPrefixes();
    assertEquals(1, importPrefixes2.size());
    assertEquals("java.lang", importPrefixes2.get(0));
    List<String> importPrefixes3 = ((StandardTypeLocator) typeLocator3).getImportPrefixes();
    assertEquals(1, importPrefixes3.size());
    assertEquals("java.lang", importPrefixes3.get(0));
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(getResult.getSpecificTargetClasses());
    assertNull(getResult2.getSpecificTargetClasses());
    assertNull(getResult3.getSpecificTargetClasses());
    ResolvableType resolvableType = valueTypeDescriptor.getResolvableType();
    ResolvableType componentType = resolvableType.getComponentType();
    assertNull(componentType.getRawClass());
    TypedValue rootObject = evaluationContext.getRootObject();
    assertNull(rootObject.getValue());
    assertNull(actualRocketChatNotifierResult.getRoomId());
    assertNull(actualRocketChatNotifierResult.getToken());
    assertNull(actualRocketChatNotifierResult.getUrl());
    assertNull(actualRocketChatNotifierResult.getUserId());
    assertNull(((CompoundExpression) aST).getExitDescriptor());
    assertNull(((CompoundExpression) aST2).getExitDescriptor());
    assertNull(((CompoundExpression) aST3).getExitDescriptor());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertNull(rootObject.getTypeDescriptor());
    assertNull(evaluationContext.getBeanResolver());
    assertNull(evaluationContext2.getBeanResolver());
    assertNull(evaluationContext3.getBeanResolver());
    assertEquals(0, aST.getStartPosition());
    assertEquals(0, aST2.getStartPosition());
    assertEquals(0, aST3.getStartPosition());
    ResolvableType[] generics = resolvableType.getGenerics();
    assertEquals(0, generics.length);
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    ResolvableType[] interfaces = resolvableType.getInterfaces();
    ResolvableType resolvableType2 = interfaces[1];
    ResolvableType[] generics2 = resolvableType2.getGenerics();
    assertEquals(1, generics2.length);
    ResolvableType resolvableType3 = generics2[0];
    ResolvableType[] interfaces2 = resolvableType3.getInterfaces();
    ResolvableType resolvableType4 = interfaces2[1];
    ResolvableType[] generics3 = resolvableType4.getGenerics();
    assertEquals(1, generics3.length);
    ResolvableType resolvableType5 = generics3[0];
    ResolvableType[] interfaces3 = resolvableType5.getInterfaces();
    ResolvableType resolvableType6 = interfaces3[1];
    assertEquals(1, resolvableType6.getGenerics().length);
    assertEquals(11, aST2.getEndPosition());
    assertEquals(2, aST2.getChildCount());
    assertEquals(23, aST3.getEndPosition());
    assertEquals(26, aST.getEndPosition());
    assertEquals(3, aST.getChildCount());
    assertEquals(3, aST3.getChildCount());
    assertEquals(5, interfaces.length);
    assertEquals(5, interfaces2.length);
    assertEquals(5, interfaces3.length);
    assertEquals(7, expressions.length);
    assertFalse(componentType.hasGenerics());
    ResolvableType superType = resolvableType.getSuperType();
    assertFalse(superType.hasGenerics());
    ResolvableType superType2 = resolvableType3.getSuperType();
    assertFalse(superType2.hasGenerics());
    ResolvableType superType3 = resolvableType5.getSuperType();
    assertFalse(superType3.hasGenerics());
    assertFalse(resolvableType.hasGenerics());
    assertFalse(resolvableType3.hasGenerics());
    assertFalse(resolvableType5.hasGenerics());
    ResolvableType resolvableType7 = interfaces[0];
    assertFalse(resolvableType7.hasGenerics());
    ResolvableType resolvableType8 = interfaces[2];
    assertFalse(resolvableType8.hasGenerics());
    ResolvableType resolvableType9 = interfaces[3];
    assertFalse(resolvableType9.hasGenerics());
    ResolvableType resolvableType10 = interfaces[4];
    assertFalse(resolvableType10.hasGenerics());
    ResolvableType resolvableType11 = interfaces2[0];
    assertFalse(resolvableType11.hasGenerics());
    ResolvableType resolvableType12 = interfaces2[2];
    assertFalse(resolvableType12.hasGenerics());
    ResolvableType resolvableType13 = interfaces2[3];
    assertFalse(resolvableType13.hasGenerics());
    ResolvableType resolvableType14 = interfaces2[4];
    assertFalse(resolvableType14.hasGenerics());
    ResolvableType resolvableType15 = interfaces3[0];
    assertFalse(resolvableType15.hasGenerics());
    ResolvableType resolvableType16 = interfaces3[2];
    assertFalse(resolvableType16.hasGenerics());
    ResolvableType resolvableType17 = interfaces3[3];
    assertFalse(resolvableType17.hasGenerics());
    ResolvableType resolvableType18 = interfaces3[4];
    assertFalse(resolvableType18.hasGenerics());
    assertFalse(componentType.hasResolvableGenerics());
    assertFalse(superType.hasResolvableGenerics());
    assertFalse(superType2.hasResolvableGenerics());
    assertFalse(superType3.hasResolvableGenerics());
    assertFalse(resolvableType.hasResolvableGenerics());
    assertFalse(resolvableType3.hasResolvableGenerics());
    assertFalse(resolvableType5.hasResolvableGenerics());
    assertFalse(resolvableType7.hasResolvableGenerics());
    assertFalse(resolvableType8.hasResolvableGenerics());
    assertFalse(resolvableType9.hasResolvableGenerics());
    assertFalse(resolvableType10.hasResolvableGenerics());
    assertFalse(resolvableType11.hasResolvableGenerics());
    assertFalse(resolvableType12.hasResolvableGenerics());
    assertFalse(resolvableType13.hasResolvableGenerics());
    assertFalse(resolvableType14.hasResolvableGenerics());
    assertFalse(resolvableType15.hasResolvableGenerics());
    assertFalse(resolvableType16.hasResolvableGenerics());
    assertFalse(resolvableType17.hasResolvableGenerics());
    assertFalse(resolvableType18.hasResolvableGenerics());
    assertFalse(componentType.hasUnresolvableGenerics());
    assertFalse(superType.hasUnresolvableGenerics());
    assertFalse(superType2.hasUnresolvableGenerics());
    assertFalse(superType3.hasUnresolvableGenerics());
    assertFalse(resolvableType.hasUnresolvableGenerics());
    assertFalse(resolvableType3.hasUnresolvableGenerics());
    assertFalse(resolvableType5.hasUnresolvableGenerics());
    assertFalse(resolvableType7.hasUnresolvableGenerics());
    assertFalse(resolvableType2.hasUnresolvableGenerics());
    assertFalse(resolvableType8.hasUnresolvableGenerics());
    assertFalse(resolvableType9.hasUnresolvableGenerics());
    assertFalse(resolvableType10.hasUnresolvableGenerics());
    assertFalse(resolvableType11.hasUnresolvableGenerics());
    assertFalse(resolvableType4.hasUnresolvableGenerics());
    assertFalse(resolvableType12.hasUnresolvableGenerics());
    assertFalse(resolvableType13.hasUnresolvableGenerics());
    assertFalse(resolvableType14.hasUnresolvableGenerics());
    assertFalse(resolvableType15.hasUnresolvableGenerics());
    assertFalse(resolvableType6.hasUnresolvableGenerics());
    assertFalse(resolvableType16.hasUnresolvableGenerics());
    assertFalse(resolvableType17.hasUnresolvableGenerics());
    assertFalse(resolvableType18.hasUnresolvableGenerics());
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    assertFalse(aST.isCompilable());
    assertFalse(aST2.isCompilable());
    assertFalse(aST3.isCompilable());
    assertFalse(((CompoundExpression) aST).isNullSafe());
    assertFalse(((CompoundExpression) aST2).isNullSafe());
    assertFalse(((CompoundExpression) aST3).isNullSafe());
    assertTrue(actualRocketChatNotifierResult.isEnabled());
    assertTrue(evaluationContext.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext2.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext3.getIndexAccessors().isEmpty());
    assertTrue(resolvableType2.hasGenerics());
    assertTrue(resolvableType4.hasGenerics());
    assertTrue(resolvableType6.hasGenerics());
    assertTrue(resolvableType2.hasResolvableGenerics());
    assertTrue(resolvableType4.hasResolvableGenerics());
    assertTrue(resolvableType6.hasResolvableGenerics());
    assertTrue(evaluationContext.isAssignmentEnabled());
    assertTrue(evaluationContext2.isAssignmentEnabled());
    assertTrue(evaluationContext3.isAssignmentEnabled());
    Class<Serializable> expectedRawClass = Serializable.class;
    Class<?> rawClass = resolvableType7.getRawClass();
    assertEquals(expectedRawClass, rawClass);
    Class<CharSequence> expectedRawClass2 = CharSequence.class;
    Class<?> rawClass2 = resolvableType8.getRawClass();
    assertEquals(expectedRawClass2, rawClass2);
    Class<Comparable> expectedRawClass3 = Comparable.class;
    Class<?> rawClass3 = resolvableType2.getRawClass();
    assertEquals(expectedRawClass3, rawClass3);
    Class<Object> expectedRawClass4 = Object.class;
    Class<?> rawClass4 = superType.getRawClass();
    assertEquals(expectedRawClass4, rawClass4);
    Class<String> expectedValueType = String.class;
    Class<?> valueType = message.getValueType();
    assertEquals(expectedValueType, valueType);
    Class<Constable> expectedRawClass5 = Constable.class;
    Class<?> rawClass5 = resolvableType9.getRawClass();
    assertEquals(expectedRawClass5, rawClass5);
    Class<ConstantDesc> expectedRawClass6 = ConstantDesc.class;
    Class<?> rawClass6 = resolvableType10.getRawClass();
    assertEquals(expectedRawClass6, rawClass6);
    ResolvableType componentType2 = componentType.getComponentType();
    assertSame(componentType2, componentType2);
    assertSame(componentType2, superType.getComponentType());
    assertSame(componentType2, superType2.getComponentType());
    assertSame(componentType2, superType3.getComponentType());
    assertSame(componentType2, resolvableType3.getComponentType());
    assertSame(componentType2, resolvableType5.getComponentType());
    assertSame(componentType2, resolvableType7.getComponentType());
    assertSame(componentType2, resolvableType2.getComponentType());
    assertSame(componentType2, resolvableType8.getComponentType());
    assertSame(componentType2, resolvableType9.getComponentType());
    assertSame(componentType2, resolvableType10.getComponentType());
    assertSame(componentType2, resolvableType11.getComponentType());
    assertSame(componentType2, resolvableType4.getComponentType());
    assertSame(componentType2, resolvableType12.getComponentType());
    assertSame(componentType2, resolvableType13.getComponentType());
    assertSame(componentType2, resolvableType14.getComponentType());
    assertSame(componentType2, resolvableType15.getComponentType());
    assertSame(componentType2, resolvableType6.getComponentType());
    assertSame(componentType2, resolvableType16.getComponentType());
    assertSame(componentType2, resolvableType17.getComponentType());
    assertSame(componentType2, resolvableType18.getComponentType());
    assertSame(componentType2, componentType.getSuperType());
    assertSame(componentType2, superType.getSuperType());
    assertSame(componentType2, superType2.getSuperType());
    assertSame(componentType2, superType3.getSuperType());
    assertSame(componentType2, resolvableType7.getSuperType());
    assertSame(componentType2, resolvableType2.getSuperType());
    assertSame(componentType2, resolvableType8.getSuperType());
    assertSame(componentType2, resolvableType9.getSuperType());
    assertSame(componentType2, resolvableType10.getSuperType());
    assertSame(componentType2, resolvableType11.getSuperType());
    assertSame(componentType2, resolvableType4.getSuperType());
    assertSame(componentType2, resolvableType12.getSuperType());
    assertSame(componentType2, resolvableType13.getSuperType());
    assertSame(componentType2, resolvableType14.getSuperType());
    assertSame(componentType2, resolvableType15.getSuperType());
    assertSame(componentType2, resolvableType6.getSuperType());
    assertSame(componentType2, resolvableType16.getSuperType());
    assertSame(componentType2, resolvableType17.getSuperType());
    assertSame(componentType2, resolvableType18.getSuperType());
    assertSame(generics, componentType.getGenerics());
    assertSame(generics, superType.getGenerics());
    assertSame(generics, superType2.getGenerics());
    assertSame(generics, superType3.getGenerics());
    assertSame(generics, resolvableType3.getGenerics());
    assertSame(generics, resolvableType5.getGenerics());
    assertSame(generics, resolvableType7.getGenerics());
    assertSame(generics, resolvableType8.getGenerics());
    assertSame(generics, resolvableType9.getGenerics());
    assertSame(generics, resolvableType10.getGenerics());
    assertSame(generics, resolvableType11.getGenerics());
    assertSame(generics, resolvableType12.getGenerics());
    assertSame(generics, resolvableType13.getGenerics());
    assertSame(generics, resolvableType14.getGenerics());
    assertSame(generics, resolvableType15.getGenerics());
    assertSame(generics, resolvableType16.getGenerics());
    assertSame(generics, resolvableType17.getGenerics());
    assertSame(generics, resolvableType18.getGenerics());
    assertSame(generics, componentType.getInterfaces());
    assertSame(generics, superType.getInterfaces());
    assertSame(generics, superType2.getInterfaces());
    assertSame(generics, superType3.getInterfaces());
    assertSame(generics, resolvableType7.getInterfaces());
    assertSame(generics, resolvableType2.getInterfaces());
    assertSame(generics, resolvableType8.getInterfaces());
    assertSame(generics, resolvableType9.getInterfaces());
    assertSame(generics, resolvableType10.getInterfaces());
    assertSame(generics, resolvableType11.getInterfaces());
    assertSame(generics, resolvableType4.getInterfaces());
    assertSame(generics, resolvableType12.getInterfaces());
    assertSame(generics, resolvableType13.getInterfaces());
    assertSame(generics, resolvableType14.getInterfaces());
    assertSame(generics, resolvableType15.getInterfaces());
    assertSame(generics, resolvableType6.getInterfaces());
    assertSame(generics, resolvableType16.getInterfaces());
    assertSame(generics, resolvableType17.getInterfaces());
    assertSame(generics, resolvableType18.getInterfaces());
    assertSame(rawClass4, superType2.getRawClass());
    assertSame(rawClass4, superType3.getRawClass());
    assertSame(rawClass4, superType.getSource());
    assertSame(rawClass4, superType2.getSource());
    assertSame(rawClass4, superType3.getSource());
    assertSame(rawClass4, superType.getType());
    assertSame(rawClass4, superType2.getType());
    assertSame(rawClass4, superType3.getType());
    assertSame(rawClass, resolvableType11.getRawClass());
    assertSame(rawClass, resolvableType15.getRawClass());
    assertSame(rawClass, resolvableType7.getSource());
    assertSame(rawClass, resolvableType11.getSource());
    assertSame(rawClass, resolvableType15.getSource());
    assertSame(rawClass, resolvableType7.getType());
    assertSame(rawClass, resolvableType11.getType());
    assertSame(rawClass, resolvableType15.getType());
    assertSame(rawClass3, resolvableType4.getRawClass());
    assertSame(rawClass3, resolvableType6.getRawClass());
    assertSame(rawClass2, resolvableType12.getRawClass());
    assertSame(rawClass2, resolvableType16.getRawClass());
    assertSame(rawClass2, resolvableType8.getSource());
    assertSame(rawClass2, resolvableType12.getSource());
    assertSame(rawClass2, resolvableType16.getSource());
    assertSame(rawClass2, resolvableType8.getType());
    assertSame(rawClass2, resolvableType12.getType());
    assertSame(rawClass2, resolvableType16.getType());
    assertSame(rawClass5, resolvableType13.getRawClass());
    assertSame(rawClass5, resolvableType17.getRawClass());
    assertSame(rawClass5, resolvableType9.getSource());
    assertSame(rawClass5, resolvableType13.getSource());
    assertSame(rawClass5, resolvableType17.getSource());
    assertSame(rawClass5, resolvableType9.getType());
    assertSame(rawClass5, resolvableType13.getType());
    assertSame(rawClass5, resolvableType17.getType());
    assertSame(rawClass6, resolvableType14.getRawClass());
    assertSame(rawClass6, resolvableType18.getRawClass());
    assertSame(rawClass6, resolvableType10.getSource());
    assertSame(rawClass6, resolvableType14.getSource());
    assertSame(rawClass6, resolvableType18.getSource());
    assertSame(rawClass6, resolvableType10.getType());
    assertSame(rawClass6, resolvableType14.getType());
    assertSame(rawClass6, resolvableType18.getType());
    assertSame(rootObject, evaluationContext2.getRootObject());
    assertSame(rootObject, evaluationContext3.getRootObject());
    assertSame(valueType, resolvableType.getRawClass());
    assertSame(valueType, resolvableType3.getRawClass());
    assertSame(valueType, resolvableType5.getRawClass());
    assertSame(valueType, resolvableType.getSource());
    assertSame(valueType, resolvableType3.getSource());
    assertSame(valueType, resolvableType5.getSource());
    assertSame(valueType, resolvableType.getType());
    assertSame(valueType, resolvableType3.getType());
    assertSame(valueType, resolvableType5.getType());
    assertSame(valueType, valueTypeDescriptor.getObjectType());
    assertSame(valueType, valueTypeDescriptor.getSource());
    assertSame(valueType, valueTypeDescriptor.getType());
    assertSame(valueType, expression.getValueType());
    assertSame(valueType, expression2.getValueType());
    assertSame(valueType, expression3.getValueType());
    assertSame(valueType, expression4.getValueType());
    assertSame(valueTypeDescriptor, expression.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression2.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression3.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression4.getValueTypeDescriptor());
    assertSame(operatorOverloader, evaluationContext2.getOperatorOverloader());
    assertSame(operatorOverloader, evaluationContext3.getOperatorOverloader());
    assertSame(typeComparator, evaluationContext2.getTypeComparator());
    assertSame(typeComparator, evaluationContext3.getTypeComparator());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualRocketChatNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration#slackNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testSlackNotifierConfigurationSlackNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration slackNotifierConfiguration = new AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    SlackNotifier actualSlackNotifierResult = slackNotifierConfiguration.slackNotifier(repository, proxyProperties);

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualSlackNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualSlackNotifierResult.getUsername());
    assertNull(actualSlackNotifierResult.getChannel());
    assertNull(actualSlackNotifierResult.getIcon());
    assertNull(actualSlackNotifierResult.getWebhookUrl());
    assertTrue(actualSlackNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualSlackNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration#slackNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testSlackNotifierConfigurationSlackNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration slackNotifierConfiguration = new AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    SlackNotifier actualSlackNotifierResult = slackNotifierConfiguration.slackNotifier(repository, proxyProperties);

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualSlackNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualSlackNotifierResult.getUsername());
    assertNull(actualSlackNotifierResult.getChannel());
    assertNull(actualSlackNotifierResult.getIcon());
    assertNull(actualSlackNotifierResult.getWebhookUrl());
    assertTrue(actualSlackNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualSlackNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration#slackNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testSlackNotifierConfigurationSlackNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration slackNotifierConfiguration = new AdminServerNotifierAutoConfiguration.SlackNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    SlackNotifier actualSlackNotifierResult = slackNotifierConfiguration.slackNotifier(repository, proxyProperties);

    // Assert
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        actualSlackNotifierResult.getMessage());
    assertEquals("Spring Boot Admin", actualSlackNotifierResult.getUsername());
    assertNull(actualSlackNotifierResult.getChannel());
    assertNull(actualSlackNotifierResult.getIcon());
    assertNull(actualSlackNotifierResult.getWebhookUrl());
    assertTrue(actualSlackNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualSlackNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration#telegramNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testTelegramNotifierConfigurationTelegramNotifier() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration telegramNotifierConfiguration = new AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    TelegramNotifier actualTelegramNotifierResult = telegramNotifierConfiguration.telegramNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("HTML", actualTelegramNotifierResult.getParseMode());
    assertEquals("https://api.telegram.org", actualTelegramNotifierResult.getApiUrl());
    assertNull(actualTelegramNotifierResult.getAuthToken());
    assertNull(actualTelegramNotifierResult.getChatId());
    assertFalse(actualTelegramNotifierResult.isDisableNotify());
    assertTrue(actualTelegramNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualTelegramNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration#telegramNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testTelegramNotifierConfigurationTelegramNotifier2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration telegramNotifierConfiguration = new AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    TelegramNotifier actualTelegramNotifierResult = telegramNotifierConfiguration.telegramNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("HTML", actualTelegramNotifierResult.getParseMode());
    assertEquals("https://api.telegram.org", actualTelegramNotifierResult.getApiUrl());
    assertNull(actualTelegramNotifierResult.getAuthToken());
    assertNull(actualTelegramNotifierResult.getChatId());
    assertFalse(actualTelegramNotifierResult.isDisableNotify());
    assertTrue(actualTelegramNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualTelegramNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration#telegramNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testTelegramNotifierConfigurationTelegramNotifier3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration telegramNotifierConfiguration = new AdminServerNotifierAutoConfiguration.TelegramNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    TelegramNotifier actualTelegramNotifierResult = telegramNotifierConfiguration.telegramNotifier(repository,
        proxyProperties);

    // Assert
    assertEquals("HTML", actualTelegramNotifierResult.getParseMode());
    assertEquals("https://api.telegram.org", actualTelegramNotifierResult.getApiUrl());
    assertNull(actualTelegramNotifierResult.getAuthToken());
    assertNull(actualTelegramNotifierResult.getChatId());
    assertFalse(actualTelegramNotifierResult.isDisableNotify());
    assertTrue(actualTelegramNotifierResult.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualTelegramNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration#webexNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testWebexNotifierConfigurationWebexNotifier() throws EvaluationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration webexNotifierConfiguration = new AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    WebexNotifier actualWebexNotifierResult = webexNotifierConfiguration.webexNotifier(repository, proxyProperties);

    // Assert
    Expression message = actualWebexNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    Expression expression = expressions[0];
    assertTrue(expression instanceof LiteralExpression);
    Expression expression2 = expressions[2];
    assertTrue(expression2 instanceof LiteralExpression);
    Expression expression3 = expressions[4];
    assertTrue(expression3 instanceof LiteralExpression);
    Expression expression4 = expressions[6];
    assertTrue(expression4 instanceof LiteralExpression);
    Expression expression5 = expressions[1];
    SpelNode aST = ((SpelExpression) expression5).getAST();
    assertTrue(aST instanceof CompoundExpression);
    Expression expression6 = expressions[3];
    SpelNode aST2 = ((SpelExpression) expression6).getAST();
    assertTrue(aST2 instanceof CompoundExpression);
    Expression expression7 = expressions[5];
    SpelNode aST3 = ((SpelExpression) expression7).getAST();
    assertTrue(aST3 instanceof CompoundExpression);
    assertTrue(expression5 instanceof SpelExpression);
    assertTrue(expression6 instanceof SpelExpression);
    assertTrue(expression7 instanceof SpelExpression);
    EvaluationContext evaluationContext = ((SpelExpression) expression5).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers = evaluationContext.getConstructorResolvers();
    assertEquals(1, constructorResolvers.size());
    assertTrue(constructorResolvers.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext2 = ((SpelExpression) expression6).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers2 = evaluationContext2.getConstructorResolvers();
    assertEquals(1, constructorResolvers2.size());
    assertTrue(constructorResolvers2.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext3 = ((SpelExpression) expression7).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers3 = evaluationContext3.getConstructorResolvers();
    assertEquals(1, constructorResolvers3.size());
    assertTrue(constructorResolvers3.get(0) instanceof ReflectiveConstructorResolver);
    List<MethodResolver> methodResolvers = evaluationContext.getMethodResolvers();
    assertEquals(1, methodResolvers.size());
    assertTrue(methodResolvers.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers2 = evaluationContext2.getMethodResolvers();
    assertEquals(1, methodResolvers2.size());
    assertTrue(methodResolvers2.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers3 = evaluationContext3.getMethodResolvers();
    assertEquals(1, methodResolvers3.size());
    assertTrue(methodResolvers3.get(0) instanceof ReflectiveMethodResolver);
    List<PropertyAccessor> propertyAccessors = evaluationContext.getPropertyAccessors();
    assertEquals(1, propertyAccessors.size());
    PropertyAccessor getResult = propertyAccessors.get(0);
    assertTrue(getResult instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors2 = evaluationContext2.getPropertyAccessors();
    assertEquals(1, propertyAccessors2.size());
    PropertyAccessor getResult2 = propertyAccessors2.get(0);
    assertTrue(getResult2 instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors3 = evaluationContext3.getPropertyAccessors();
    assertEquals(1, propertyAccessors3.size());
    PropertyAccessor getResult3 = propertyAccessors3.get(0);
    assertTrue(getResult3 instanceof ReflectivePropertyAccessor);
    assertTrue(evaluationContext instanceof StandardEvaluationContext);
    assertTrue(evaluationContext2 instanceof StandardEvaluationContext);
    assertTrue(evaluationContext3 instanceof StandardEvaluationContext);
    OperatorOverloader operatorOverloader = evaluationContext.getOperatorOverloader();
    assertTrue(operatorOverloader instanceof StandardOperatorOverloader);
    TypeComparator typeComparator = evaluationContext.getTypeComparator();
    assertTrue(typeComparator instanceof StandardTypeComparator);
    assertTrue(evaluationContext.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext2.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext3.getTypeConverter() instanceof StandardTypeConverter);
    TypeLocator typeLocator = evaluationContext.getTypeLocator();
    assertTrue(typeLocator instanceof StandardTypeLocator);
    TypeLocator typeLocator2 = evaluationContext2.getTypeLocator();
    assertTrue(typeLocator2 instanceof StandardTypeLocator);
    TypeLocator typeLocator3 = evaluationContext3.getTypeLocator();
    assertTrue(typeLocator3 instanceof StandardTypeLocator);
    assertEquals(" is <strong>", expression3.getExpressionString());
    assertEquals(" is <strong>", expression3.getValue());
    assertEquals("</strong>", expression4.getExpressionString());
    assertEquals("</strong>", expression4.getValue());
    assertEquals("</strong>/", expression2.getExpressionString());
    assertEquals("</strong>/", expression2.getValue());
    assertEquals("<strong>", expression.getExpressionString());
    assertEquals("<strong>", expression.getValue());
    assertEquals("<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
        + "}</strong>", message.getExpressionString());
    assertEquals("event.statusInfo.status", expression7.getExpressionString());
    assertEquals("event.statusInfo.status", aST3.toStringAST());
    assertEquals("event.statusInfo.status", ((SpelExpression) expression7).toStringAST());
    assertEquals("https://webexapis.com/v1/messages", actualWebexNotifierResult.getUrl().toString());
    assertEquals("instance.id", expression6.getExpressionString());
    assertEquals("instance.id", aST2.toStringAST());
    assertEquals("instance.id", ((SpelExpression) expression6).toStringAST());
    assertEquals("instance.registration.name", expression5.getExpressionString());
    assertEquals("instance.registration.name", aST.toStringAST());
    assertEquals("instance.registration.name", ((SpelExpression) expression5).toStringAST());
    List<String> importPrefixes = ((StandardTypeLocator) typeLocator).getImportPrefixes();
    assertEquals(1, importPrefixes.size());
    assertEquals("java.lang", importPrefixes.get(0));
    List<String> importPrefixes2 = ((StandardTypeLocator) typeLocator2).getImportPrefixes();
    assertEquals(1, importPrefixes2.size());
    assertEquals("java.lang", importPrefixes2.get(0));
    List<String> importPrefixes3 = ((StandardTypeLocator) typeLocator3).getImportPrefixes();
    assertEquals(1, importPrefixes3.size());
    assertEquals("java.lang", importPrefixes3.get(0));
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(getResult.getSpecificTargetClasses());
    assertNull(getResult2.getSpecificTargetClasses());
    assertNull(getResult3.getSpecificTargetClasses());
    ResolvableType resolvableType = valueTypeDescriptor.getResolvableType();
    ResolvableType componentType = resolvableType.getComponentType();
    assertNull(componentType.getRawClass());
    TypedValue rootObject = evaluationContext.getRootObject();
    assertNull(rootObject.getValue());
    assertNull(actualWebexNotifierResult.getAuthToken());
    assertNull(actualWebexNotifierResult.getRoomId());
    assertNull(((CompoundExpression) aST).getExitDescriptor());
    assertNull(((CompoundExpression) aST2).getExitDescriptor());
    assertNull(((CompoundExpression) aST3).getExitDescriptor());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertNull(rootObject.getTypeDescriptor());
    assertNull(evaluationContext.getBeanResolver());
    assertNull(evaluationContext2.getBeanResolver());
    assertNull(evaluationContext3.getBeanResolver());
    assertEquals(0, aST.getStartPosition());
    assertEquals(0, aST2.getStartPosition());
    assertEquals(0, aST3.getStartPosition());
    ResolvableType[] generics = resolvableType.getGenerics();
    assertEquals(0, generics.length);
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    ResolvableType[] interfaces = resolvableType.getInterfaces();
    ResolvableType resolvableType2 = interfaces[1];
    ResolvableType[] generics2 = resolvableType2.getGenerics();
    assertEquals(1, generics2.length);
    ResolvableType resolvableType3 = generics2[0];
    ResolvableType[] interfaces2 = resolvableType3.getInterfaces();
    ResolvableType resolvableType4 = interfaces2[1];
    ResolvableType[] generics3 = resolvableType4.getGenerics();
    assertEquals(1, generics3.length);
    ResolvableType resolvableType5 = generics3[0];
    ResolvableType[] interfaces3 = resolvableType5.getInterfaces();
    ResolvableType resolvableType6 = interfaces3[1];
    assertEquals(1, resolvableType6.getGenerics().length);
    assertEquals(11, aST2.getEndPosition());
    assertEquals(2, aST2.getChildCount());
    assertEquals(23, aST3.getEndPosition());
    assertEquals(26, aST.getEndPosition());
    assertEquals(3, aST.getChildCount());
    assertEquals(3, aST3.getChildCount());
    assertEquals(5, interfaces.length);
    assertEquals(5, interfaces2.length);
    assertEquals(5, interfaces3.length);
    assertEquals(7, expressions.length);
    assertFalse(componentType.hasGenerics());
    ResolvableType superType = resolvableType.getSuperType();
    assertFalse(superType.hasGenerics());
    ResolvableType superType2 = resolvableType3.getSuperType();
    assertFalse(superType2.hasGenerics());
    ResolvableType superType3 = resolvableType5.getSuperType();
    assertFalse(superType3.hasGenerics());
    assertFalse(resolvableType.hasGenerics());
    assertFalse(resolvableType3.hasGenerics());
    assertFalse(resolvableType5.hasGenerics());
    ResolvableType resolvableType7 = interfaces[0];
    assertFalse(resolvableType7.hasGenerics());
    ResolvableType resolvableType8 = interfaces[2];
    assertFalse(resolvableType8.hasGenerics());
    ResolvableType resolvableType9 = interfaces[3];
    assertFalse(resolvableType9.hasGenerics());
    ResolvableType resolvableType10 = interfaces[4];
    assertFalse(resolvableType10.hasGenerics());
    ResolvableType resolvableType11 = interfaces2[0];
    assertFalse(resolvableType11.hasGenerics());
    ResolvableType resolvableType12 = interfaces2[2];
    assertFalse(resolvableType12.hasGenerics());
    ResolvableType resolvableType13 = interfaces2[3];
    assertFalse(resolvableType13.hasGenerics());
    ResolvableType resolvableType14 = interfaces2[4];
    assertFalse(resolvableType14.hasGenerics());
    ResolvableType resolvableType15 = interfaces3[0];
    assertFalse(resolvableType15.hasGenerics());
    ResolvableType resolvableType16 = interfaces3[2];
    assertFalse(resolvableType16.hasGenerics());
    ResolvableType resolvableType17 = interfaces3[3];
    assertFalse(resolvableType17.hasGenerics());
    ResolvableType resolvableType18 = interfaces3[4];
    assertFalse(resolvableType18.hasGenerics());
    assertFalse(componentType.hasResolvableGenerics());
    assertFalse(superType.hasResolvableGenerics());
    assertFalse(superType2.hasResolvableGenerics());
    assertFalse(superType3.hasResolvableGenerics());
    assertFalse(resolvableType.hasResolvableGenerics());
    assertFalse(resolvableType3.hasResolvableGenerics());
    assertFalse(resolvableType5.hasResolvableGenerics());
    assertFalse(resolvableType7.hasResolvableGenerics());
    assertFalse(resolvableType8.hasResolvableGenerics());
    assertFalse(resolvableType9.hasResolvableGenerics());
    assertFalse(resolvableType10.hasResolvableGenerics());
    assertFalse(resolvableType11.hasResolvableGenerics());
    assertFalse(resolvableType12.hasResolvableGenerics());
    assertFalse(resolvableType13.hasResolvableGenerics());
    assertFalse(resolvableType14.hasResolvableGenerics());
    assertFalse(resolvableType15.hasResolvableGenerics());
    assertFalse(resolvableType16.hasResolvableGenerics());
    assertFalse(resolvableType17.hasResolvableGenerics());
    assertFalse(resolvableType18.hasResolvableGenerics());
    assertFalse(componentType.hasUnresolvableGenerics());
    assertFalse(superType.hasUnresolvableGenerics());
    assertFalse(superType2.hasUnresolvableGenerics());
    assertFalse(superType3.hasUnresolvableGenerics());
    assertFalse(resolvableType.hasUnresolvableGenerics());
    assertFalse(resolvableType3.hasUnresolvableGenerics());
    assertFalse(resolvableType5.hasUnresolvableGenerics());
    assertFalse(resolvableType7.hasUnresolvableGenerics());
    assertFalse(resolvableType2.hasUnresolvableGenerics());
    assertFalse(resolvableType8.hasUnresolvableGenerics());
    assertFalse(resolvableType9.hasUnresolvableGenerics());
    assertFalse(resolvableType10.hasUnresolvableGenerics());
    assertFalse(resolvableType11.hasUnresolvableGenerics());
    assertFalse(resolvableType4.hasUnresolvableGenerics());
    assertFalse(resolvableType12.hasUnresolvableGenerics());
    assertFalse(resolvableType13.hasUnresolvableGenerics());
    assertFalse(resolvableType14.hasUnresolvableGenerics());
    assertFalse(resolvableType15.hasUnresolvableGenerics());
    assertFalse(resolvableType6.hasUnresolvableGenerics());
    assertFalse(resolvableType16.hasUnresolvableGenerics());
    assertFalse(resolvableType17.hasUnresolvableGenerics());
    assertFalse(resolvableType18.hasUnresolvableGenerics());
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    assertFalse(aST.isCompilable());
    assertFalse(aST2.isCompilable());
    assertFalse(aST3.isCompilable());
    assertFalse(((CompoundExpression) aST).isNullSafe());
    assertFalse(((CompoundExpression) aST2).isNullSafe());
    assertFalse(((CompoundExpression) aST3).isNullSafe());
    assertTrue(actualWebexNotifierResult.isEnabled());
    assertTrue(evaluationContext.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext2.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext3.getIndexAccessors().isEmpty());
    assertTrue(resolvableType2.hasGenerics());
    assertTrue(resolvableType4.hasGenerics());
    assertTrue(resolvableType6.hasGenerics());
    assertTrue(resolvableType2.hasResolvableGenerics());
    assertTrue(resolvableType4.hasResolvableGenerics());
    assertTrue(resolvableType6.hasResolvableGenerics());
    assertTrue(evaluationContext.isAssignmentEnabled());
    assertTrue(evaluationContext2.isAssignmentEnabled());
    assertTrue(evaluationContext3.isAssignmentEnabled());
    Class<Serializable> expectedRawClass = Serializable.class;
    Class<?> rawClass = resolvableType7.getRawClass();
    assertEquals(expectedRawClass, rawClass);
    Class<CharSequence> expectedRawClass2 = CharSequence.class;
    Class<?> rawClass2 = resolvableType8.getRawClass();
    assertEquals(expectedRawClass2, rawClass2);
    Class<Comparable> expectedRawClass3 = Comparable.class;
    Class<?> rawClass3 = resolvableType2.getRawClass();
    assertEquals(expectedRawClass3, rawClass3);
    Class<Object> expectedRawClass4 = Object.class;
    Class<?> rawClass4 = superType.getRawClass();
    assertEquals(expectedRawClass4, rawClass4);
    Class<String> expectedValueType = String.class;
    Class<?> valueType = message.getValueType();
    assertEquals(expectedValueType, valueType);
    Class<Constable> expectedRawClass5 = Constable.class;
    Class<?> rawClass5 = resolvableType9.getRawClass();
    assertEquals(expectedRawClass5, rawClass5);
    Class<ConstantDesc> expectedRawClass6 = ConstantDesc.class;
    Class<?> rawClass6 = resolvableType10.getRawClass();
    assertEquals(expectedRawClass6, rawClass6);
    ResolvableType componentType2 = componentType.getComponentType();
    assertSame(componentType2, componentType2);
    assertSame(componentType2, superType.getComponentType());
    assertSame(componentType2, superType2.getComponentType());
    assertSame(componentType2, superType3.getComponentType());
    assertSame(componentType2, resolvableType3.getComponentType());
    assertSame(componentType2, resolvableType5.getComponentType());
    assertSame(componentType2, resolvableType7.getComponentType());
    assertSame(componentType2, resolvableType2.getComponentType());
    assertSame(componentType2, resolvableType8.getComponentType());
    assertSame(componentType2, resolvableType9.getComponentType());
    assertSame(componentType2, resolvableType10.getComponentType());
    assertSame(componentType2, resolvableType11.getComponentType());
    assertSame(componentType2, resolvableType4.getComponentType());
    assertSame(componentType2, resolvableType12.getComponentType());
    assertSame(componentType2, resolvableType13.getComponentType());
    assertSame(componentType2, resolvableType14.getComponentType());
    assertSame(componentType2, resolvableType15.getComponentType());
    assertSame(componentType2, resolvableType6.getComponentType());
    assertSame(componentType2, resolvableType16.getComponentType());
    assertSame(componentType2, resolvableType17.getComponentType());
    assertSame(componentType2, resolvableType18.getComponentType());
    assertSame(componentType2, componentType.getSuperType());
    assertSame(componentType2, superType.getSuperType());
    assertSame(componentType2, superType2.getSuperType());
    assertSame(componentType2, superType3.getSuperType());
    assertSame(componentType2, resolvableType7.getSuperType());
    assertSame(componentType2, resolvableType2.getSuperType());
    assertSame(componentType2, resolvableType8.getSuperType());
    assertSame(componentType2, resolvableType9.getSuperType());
    assertSame(componentType2, resolvableType10.getSuperType());
    assertSame(componentType2, resolvableType11.getSuperType());
    assertSame(componentType2, resolvableType4.getSuperType());
    assertSame(componentType2, resolvableType12.getSuperType());
    assertSame(componentType2, resolvableType13.getSuperType());
    assertSame(componentType2, resolvableType14.getSuperType());
    assertSame(componentType2, resolvableType15.getSuperType());
    assertSame(componentType2, resolvableType6.getSuperType());
    assertSame(componentType2, resolvableType16.getSuperType());
    assertSame(componentType2, resolvableType17.getSuperType());
    assertSame(componentType2, resolvableType18.getSuperType());
    assertSame(generics, componentType.getGenerics());
    assertSame(generics, superType.getGenerics());
    assertSame(generics, superType2.getGenerics());
    assertSame(generics, superType3.getGenerics());
    assertSame(generics, resolvableType3.getGenerics());
    assertSame(generics, resolvableType5.getGenerics());
    assertSame(generics, resolvableType7.getGenerics());
    assertSame(generics, resolvableType8.getGenerics());
    assertSame(generics, resolvableType9.getGenerics());
    assertSame(generics, resolvableType10.getGenerics());
    assertSame(generics, resolvableType11.getGenerics());
    assertSame(generics, resolvableType12.getGenerics());
    assertSame(generics, resolvableType13.getGenerics());
    assertSame(generics, resolvableType14.getGenerics());
    assertSame(generics, resolvableType15.getGenerics());
    assertSame(generics, resolvableType16.getGenerics());
    assertSame(generics, resolvableType17.getGenerics());
    assertSame(generics, resolvableType18.getGenerics());
    assertSame(generics, componentType.getInterfaces());
    assertSame(generics, superType.getInterfaces());
    assertSame(generics, superType2.getInterfaces());
    assertSame(generics, superType3.getInterfaces());
    assertSame(generics, resolvableType7.getInterfaces());
    assertSame(generics, resolvableType2.getInterfaces());
    assertSame(generics, resolvableType8.getInterfaces());
    assertSame(generics, resolvableType9.getInterfaces());
    assertSame(generics, resolvableType10.getInterfaces());
    assertSame(generics, resolvableType11.getInterfaces());
    assertSame(generics, resolvableType4.getInterfaces());
    assertSame(generics, resolvableType12.getInterfaces());
    assertSame(generics, resolvableType13.getInterfaces());
    assertSame(generics, resolvableType14.getInterfaces());
    assertSame(generics, resolvableType15.getInterfaces());
    assertSame(generics, resolvableType6.getInterfaces());
    assertSame(generics, resolvableType16.getInterfaces());
    assertSame(generics, resolvableType17.getInterfaces());
    assertSame(generics, resolvableType18.getInterfaces());
    assertSame(rawClass4, superType2.getRawClass());
    assertSame(rawClass4, superType3.getRawClass());
    assertSame(rawClass4, superType.getSource());
    assertSame(rawClass4, superType2.getSource());
    assertSame(rawClass4, superType3.getSource());
    assertSame(rawClass4, superType.getType());
    assertSame(rawClass4, superType2.getType());
    assertSame(rawClass4, superType3.getType());
    assertSame(rawClass, resolvableType11.getRawClass());
    assertSame(rawClass, resolvableType15.getRawClass());
    assertSame(rawClass, resolvableType7.getSource());
    assertSame(rawClass, resolvableType11.getSource());
    assertSame(rawClass, resolvableType15.getSource());
    assertSame(rawClass, resolvableType7.getType());
    assertSame(rawClass, resolvableType11.getType());
    assertSame(rawClass, resolvableType15.getType());
    assertSame(rawClass3, resolvableType4.getRawClass());
    assertSame(rawClass3, resolvableType6.getRawClass());
    assertSame(rawClass2, resolvableType12.getRawClass());
    assertSame(rawClass2, resolvableType16.getRawClass());
    assertSame(rawClass2, resolvableType8.getSource());
    assertSame(rawClass2, resolvableType12.getSource());
    assertSame(rawClass2, resolvableType16.getSource());
    assertSame(rawClass2, resolvableType8.getType());
    assertSame(rawClass2, resolvableType12.getType());
    assertSame(rawClass2, resolvableType16.getType());
    assertSame(rawClass5, resolvableType13.getRawClass());
    assertSame(rawClass5, resolvableType17.getRawClass());
    assertSame(rawClass5, resolvableType9.getSource());
    assertSame(rawClass5, resolvableType13.getSource());
    assertSame(rawClass5, resolvableType17.getSource());
    assertSame(rawClass5, resolvableType9.getType());
    assertSame(rawClass5, resolvableType13.getType());
    assertSame(rawClass5, resolvableType17.getType());
    assertSame(rawClass6, resolvableType14.getRawClass());
    assertSame(rawClass6, resolvableType18.getRawClass());
    assertSame(rawClass6, resolvableType10.getSource());
    assertSame(rawClass6, resolvableType14.getSource());
    assertSame(rawClass6, resolvableType18.getSource());
    assertSame(rawClass6, resolvableType10.getType());
    assertSame(rawClass6, resolvableType14.getType());
    assertSame(rawClass6, resolvableType18.getType());
    assertSame(rootObject, evaluationContext2.getRootObject());
    assertSame(rootObject, evaluationContext3.getRootObject());
    assertSame(valueType, resolvableType.getRawClass());
    assertSame(valueType, resolvableType3.getRawClass());
    assertSame(valueType, resolvableType5.getRawClass());
    assertSame(valueType, resolvableType.getSource());
    assertSame(valueType, resolvableType3.getSource());
    assertSame(valueType, resolvableType5.getSource());
    assertSame(valueType, resolvableType.getType());
    assertSame(valueType, resolvableType3.getType());
    assertSame(valueType, resolvableType5.getType());
    assertSame(valueType, valueTypeDescriptor.getObjectType());
    assertSame(valueType, valueTypeDescriptor.getSource());
    assertSame(valueType, valueTypeDescriptor.getType());
    assertSame(valueType, expression.getValueType());
    assertSame(valueType, expression2.getValueType());
    assertSame(valueType, expression3.getValueType());
    assertSame(valueType, expression4.getValueType());
    assertSame(valueTypeDescriptor, expression.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression2.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression3.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression4.getValueTypeDescriptor());
    assertSame(operatorOverloader, evaluationContext2.getOperatorOverloader());
    assertSame(operatorOverloader, evaluationContext3.getOperatorOverloader());
    assertSame(typeComparator, evaluationContext2.getTypeComparator());
    assertSame(typeComparator, evaluationContext3.getTypeComparator());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualWebexNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration#webexNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testWebexNotifierConfigurationWebexNotifier2() throws EvaluationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration webexNotifierConfiguration = new AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(null);

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    WebexNotifier actualWebexNotifierResult = webexNotifierConfiguration.webexNotifier(repository, proxyProperties);

    // Assert
    Expression message = actualWebexNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    Expression expression = expressions[0];
    assertTrue(expression instanceof LiteralExpression);
    Expression expression2 = expressions[2];
    assertTrue(expression2 instanceof LiteralExpression);
    Expression expression3 = expressions[4];
    assertTrue(expression3 instanceof LiteralExpression);
    Expression expression4 = expressions[6];
    assertTrue(expression4 instanceof LiteralExpression);
    Expression expression5 = expressions[1];
    SpelNode aST = ((SpelExpression) expression5).getAST();
    assertTrue(aST instanceof CompoundExpression);
    Expression expression6 = expressions[3];
    SpelNode aST2 = ((SpelExpression) expression6).getAST();
    assertTrue(aST2 instanceof CompoundExpression);
    Expression expression7 = expressions[5];
    SpelNode aST3 = ((SpelExpression) expression7).getAST();
    assertTrue(aST3 instanceof CompoundExpression);
    assertTrue(expression5 instanceof SpelExpression);
    assertTrue(expression6 instanceof SpelExpression);
    assertTrue(expression7 instanceof SpelExpression);
    EvaluationContext evaluationContext = ((SpelExpression) expression5).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers = evaluationContext.getConstructorResolvers();
    assertEquals(1, constructorResolvers.size());
    assertTrue(constructorResolvers.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext2 = ((SpelExpression) expression6).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers2 = evaluationContext2.getConstructorResolvers();
    assertEquals(1, constructorResolvers2.size());
    assertTrue(constructorResolvers2.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext3 = ((SpelExpression) expression7).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers3 = evaluationContext3.getConstructorResolvers();
    assertEquals(1, constructorResolvers3.size());
    assertTrue(constructorResolvers3.get(0) instanceof ReflectiveConstructorResolver);
    List<MethodResolver> methodResolvers = evaluationContext.getMethodResolvers();
    assertEquals(1, methodResolvers.size());
    assertTrue(methodResolvers.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers2 = evaluationContext2.getMethodResolvers();
    assertEquals(1, methodResolvers2.size());
    assertTrue(methodResolvers2.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers3 = evaluationContext3.getMethodResolvers();
    assertEquals(1, methodResolvers3.size());
    assertTrue(methodResolvers3.get(0) instanceof ReflectiveMethodResolver);
    List<PropertyAccessor> propertyAccessors = evaluationContext.getPropertyAccessors();
    assertEquals(1, propertyAccessors.size());
    PropertyAccessor getResult = propertyAccessors.get(0);
    assertTrue(getResult instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors2 = evaluationContext2.getPropertyAccessors();
    assertEquals(1, propertyAccessors2.size());
    PropertyAccessor getResult2 = propertyAccessors2.get(0);
    assertTrue(getResult2 instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors3 = evaluationContext3.getPropertyAccessors();
    assertEquals(1, propertyAccessors3.size());
    PropertyAccessor getResult3 = propertyAccessors3.get(0);
    assertTrue(getResult3 instanceof ReflectivePropertyAccessor);
    assertTrue(evaluationContext instanceof StandardEvaluationContext);
    assertTrue(evaluationContext2 instanceof StandardEvaluationContext);
    assertTrue(evaluationContext3 instanceof StandardEvaluationContext);
    OperatorOverloader operatorOverloader = evaluationContext.getOperatorOverloader();
    assertTrue(operatorOverloader instanceof StandardOperatorOverloader);
    TypeComparator typeComparator = evaluationContext.getTypeComparator();
    assertTrue(typeComparator instanceof StandardTypeComparator);
    assertTrue(evaluationContext.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext2.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext3.getTypeConverter() instanceof StandardTypeConverter);
    TypeLocator typeLocator = evaluationContext.getTypeLocator();
    assertTrue(typeLocator instanceof StandardTypeLocator);
    TypeLocator typeLocator2 = evaluationContext2.getTypeLocator();
    assertTrue(typeLocator2 instanceof StandardTypeLocator);
    TypeLocator typeLocator3 = evaluationContext3.getTypeLocator();
    assertTrue(typeLocator3 instanceof StandardTypeLocator);
    assertEquals(" is <strong>", expression3.getExpressionString());
    assertEquals(" is <strong>", expression3.getValue());
    assertEquals("</strong>", expression4.getExpressionString());
    assertEquals("</strong>", expression4.getValue());
    assertEquals("</strong>/", expression2.getExpressionString());
    assertEquals("</strong>/", expression2.getValue());
    assertEquals("<strong>", expression.getExpressionString());
    assertEquals("<strong>", expression.getValue());
    assertEquals("<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
        + "}</strong>", message.getExpressionString());
    assertEquals("event.statusInfo.status", expression7.getExpressionString());
    assertEquals("event.statusInfo.status", aST3.toStringAST());
    assertEquals("event.statusInfo.status", ((SpelExpression) expression7).toStringAST());
    assertEquals("https://webexapis.com/v1/messages", actualWebexNotifierResult.getUrl().toString());
    assertEquals("instance.id", expression6.getExpressionString());
    assertEquals("instance.id", aST2.toStringAST());
    assertEquals("instance.id", ((SpelExpression) expression6).toStringAST());
    assertEquals("instance.registration.name", expression5.getExpressionString());
    assertEquals("instance.registration.name", aST.toStringAST());
    assertEquals("instance.registration.name", ((SpelExpression) expression5).toStringAST());
    List<String> importPrefixes = ((StandardTypeLocator) typeLocator).getImportPrefixes();
    assertEquals(1, importPrefixes.size());
    assertEquals("java.lang", importPrefixes.get(0));
    List<String> importPrefixes2 = ((StandardTypeLocator) typeLocator2).getImportPrefixes();
    assertEquals(1, importPrefixes2.size());
    assertEquals("java.lang", importPrefixes2.get(0));
    List<String> importPrefixes3 = ((StandardTypeLocator) typeLocator3).getImportPrefixes();
    assertEquals(1, importPrefixes3.size());
    assertEquals("java.lang", importPrefixes3.get(0));
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(getResult.getSpecificTargetClasses());
    assertNull(getResult2.getSpecificTargetClasses());
    assertNull(getResult3.getSpecificTargetClasses());
    ResolvableType resolvableType = valueTypeDescriptor.getResolvableType();
    ResolvableType componentType = resolvableType.getComponentType();
    assertNull(componentType.getRawClass());
    TypedValue rootObject = evaluationContext.getRootObject();
    assertNull(rootObject.getValue());
    assertNull(actualWebexNotifierResult.getAuthToken());
    assertNull(actualWebexNotifierResult.getRoomId());
    assertNull(((CompoundExpression) aST).getExitDescriptor());
    assertNull(((CompoundExpression) aST2).getExitDescriptor());
    assertNull(((CompoundExpression) aST3).getExitDescriptor());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertNull(rootObject.getTypeDescriptor());
    assertNull(evaluationContext.getBeanResolver());
    assertNull(evaluationContext2.getBeanResolver());
    assertNull(evaluationContext3.getBeanResolver());
    assertEquals(0, aST.getStartPosition());
    assertEquals(0, aST2.getStartPosition());
    assertEquals(0, aST3.getStartPosition());
    ResolvableType[] generics = resolvableType.getGenerics();
    assertEquals(0, generics.length);
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    ResolvableType[] interfaces = resolvableType.getInterfaces();
    ResolvableType resolvableType2 = interfaces[1];
    ResolvableType[] generics2 = resolvableType2.getGenerics();
    assertEquals(1, generics2.length);
    ResolvableType resolvableType3 = generics2[0];
    ResolvableType[] interfaces2 = resolvableType3.getInterfaces();
    ResolvableType resolvableType4 = interfaces2[1];
    ResolvableType[] generics3 = resolvableType4.getGenerics();
    assertEquals(1, generics3.length);
    ResolvableType resolvableType5 = generics3[0];
    ResolvableType[] interfaces3 = resolvableType5.getInterfaces();
    ResolvableType resolvableType6 = interfaces3[1];
    assertEquals(1, resolvableType6.getGenerics().length);
    assertEquals(11, aST2.getEndPosition());
    assertEquals(2, aST2.getChildCount());
    assertEquals(23, aST3.getEndPosition());
    assertEquals(26, aST.getEndPosition());
    assertEquals(3, aST.getChildCount());
    assertEquals(3, aST3.getChildCount());
    assertEquals(5, interfaces.length);
    assertEquals(5, interfaces2.length);
    assertEquals(5, interfaces3.length);
    assertEquals(7, expressions.length);
    assertFalse(componentType.hasGenerics());
    ResolvableType superType = resolvableType.getSuperType();
    assertFalse(superType.hasGenerics());
    ResolvableType superType2 = resolvableType3.getSuperType();
    assertFalse(superType2.hasGenerics());
    ResolvableType superType3 = resolvableType5.getSuperType();
    assertFalse(superType3.hasGenerics());
    assertFalse(resolvableType.hasGenerics());
    assertFalse(resolvableType3.hasGenerics());
    assertFalse(resolvableType5.hasGenerics());
    ResolvableType resolvableType7 = interfaces[0];
    assertFalse(resolvableType7.hasGenerics());
    ResolvableType resolvableType8 = interfaces[2];
    assertFalse(resolvableType8.hasGenerics());
    ResolvableType resolvableType9 = interfaces[3];
    assertFalse(resolvableType9.hasGenerics());
    ResolvableType resolvableType10 = interfaces[4];
    assertFalse(resolvableType10.hasGenerics());
    ResolvableType resolvableType11 = interfaces2[0];
    assertFalse(resolvableType11.hasGenerics());
    ResolvableType resolvableType12 = interfaces2[2];
    assertFalse(resolvableType12.hasGenerics());
    ResolvableType resolvableType13 = interfaces2[3];
    assertFalse(resolvableType13.hasGenerics());
    ResolvableType resolvableType14 = interfaces2[4];
    assertFalse(resolvableType14.hasGenerics());
    ResolvableType resolvableType15 = interfaces3[0];
    assertFalse(resolvableType15.hasGenerics());
    ResolvableType resolvableType16 = interfaces3[2];
    assertFalse(resolvableType16.hasGenerics());
    ResolvableType resolvableType17 = interfaces3[3];
    assertFalse(resolvableType17.hasGenerics());
    ResolvableType resolvableType18 = interfaces3[4];
    assertFalse(resolvableType18.hasGenerics());
    assertFalse(componentType.hasResolvableGenerics());
    assertFalse(superType.hasResolvableGenerics());
    assertFalse(superType2.hasResolvableGenerics());
    assertFalse(superType3.hasResolvableGenerics());
    assertFalse(resolvableType.hasResolvableGenerics());
    assertFalse(resolvableType3.hasResolvableGenerics());
    assertFalse(resolvableType5.hasResolvableGenerics());
    assertFalse(resolvableType7.hasResolvableGenerics());
    assertFalse(resolvableType8.hasResolvableGenerics());
    assertFalse(resolvableType9.hasResolvableGenerics());
    assertFalse(resolvableType10.hasResolvableGenerics());
    assertFalse(resolvableType11.hasResolvableGenerics());
    assertFalse(resolvableType12.hasResolvableGenerics());
    assertFalse(resolvableType13.hasResolvableGenerics());
    assertFalse(resolvableType14.hasResolvableGenerics());
    assertFalse(resolvableType15.hasResolvableGenerics());
    assertFalse(resolvableType16.hasResolvableGenerics());
    assertFalse(resolvableType17.hasResolvableGenerics());
    assertFalse(resolvableType18.hasResolvableGenerics());
    assertFalse(componentType.hasUnresolvableGenerics());
    assertFalse(superType.hasUnresolvableGenerics());
    assertFalse(superType2.hasUnresolvableGenerics());
    assertFalse(superType3.hasUnresolvableGenerics());
    assertFalse(resolvableType.hasUnresolvableGenerics());
    assertFalse(resolvableType3.hasUnresolvableGenerics());
    assertFalse(resolvableType5.hasUnresolvableGenerics());
    assertFalse(resolvableType7.hasUnresolvableGenerics());
    assertFalse(resolvableType2.hasUnresolvableGenerics());
    assertFalse(resolvableType8.hasUnresolvableGenerics());
    assertFalse(resolvableType9.hasUnresolvableGenerics());
    assertFalse(resolvableType10.hasUnresolvableGenerics());
    assertFalse(resolvableType11.hasUnresolvableGenerics());
    assertFalse(resolvableType4.hasUnresolvableGenerics());
    assertFalse(resolvableType12.hasUnresolvableGenerics());
    assertFalse(resolvableType13.hasUnresolvableGenerics());
    assertFalse(resolvableType14.hasUnresolvableGenerics());
    assertFalse(resolvableType15.hasUnresolvableGenerics());
    assertFalse(resolvableType6.hasUnresolvableGenerics());
    assertFalse(resolvableType16.hasUnresolvableGenerics());
    assertFalse(resolvableType17.hasUnresolvableGenerics());
    assertFalse(resolvableType18.hasUnresolvableGenerics());
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    assertFalse(aST.isCompilable());
    assertFalse(aST2.isCompilable());
    assertFalse(aST3.isCompilable());
    assertFalse(((CompoundExpression) aST).isNullSafe());
    assertFalse(((CompoundExpression) aST2).isNullSafe());
    assertFalse(((CompoundExpression) aST3).isNullSafe());
    assertTrue(actualWebexNotifierResult.isEnabled());
    assertTrue(evaluationContext.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext2.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext3.getIndexAccessors().isEmpty());
    assertTrue(resolvableType2.hasGenerics());
    assertTrue(resolvableType4.hasGenerics());
    assertTrue(resolvableType6.hasGenerics());
    assertTrue(resolvableType2.hasResolvableGenerics());
    assertTrue(resolvableType4.hasResolvableGenerics());
    assertTrue(resolvableType6.hasResolvableGenerics());
    assertTrue(evaluationContext.isAssignmentEnabled());
    assertTrue(evaluationContext2.isAssignmentEnabled());
    assertTrue(evaluationContext3.isAssignmentEnabled());
    Class<Serializable> expectedRawClass = Serializable.class;
    Class<?> rawClass = resolvableType7.getRawClass();
    assertEquals(expectedRawClass, rawClass);
    Class<CharSequence> expectedRawClass2 = CharSequence.class;
    Class<?> rawClass2 = resolvableType8.getRawClass();
    assertEquals(expectedRawClass2, rawClass2);
    Class<Comparable> expectedRawClass3 = Comparable.class;
    Class<?> rawClass3 = resolvableType2.getRawClass();
    assertEquals(expectedRawClass3, rawClass3);
    Class<Object> expectedRawClass4 = Object.class;
    Class<?> rawClass4 = superType.getRawClass();
    assertEquals(expectedRawClass4, rawClass4);
    Class<String> expectedValueType = String.class;
    Class<?> valueType = message.getValueType();
    assertEquals(expectedValueType, valueType);
    Class<Constable> expectedRawClass5 = Constable.class;
    Class<?> rawClass5 = resolvableType9.getRawClass();
    assertEquals(expectedRawClass5, rawClass5);
    Class<ConstantDesc> expectedRawClass6 = ConstantDesc.class;
    Class<?> rawClass6 = resolvableType10.getRawClass();
    assertEquals(expectedRawClass6, rawClass6);
    ResolvableType componentType2 = componentType.getComponentType();
    assertSame(componentType2, componentType2);
    assertSame(componentType2, superType.getComponentType());
    assertSame(componentType2, superType2.getComponentType());
    assertSame(componentType2, superType3.getComponentType());
    assertSame(componentType2, resolvableType3.getComponentType());
    assertSame(componentType2, resolvableType5.getComponentType());
    assertSame(componentType2, resolvableType7.getComponentType());
    assertSame(componentType2, resolvableType2.getComponentType());
    assertSame(componentType2, resolvableType8.getComponentType());
    assertSame(componentType2, resolvableType9.getComponentType());
    assertSame(componentType2, resolvableType10.getComponentType());
    assertSame(componentType2, resolvableType11.getComponentType());
    assertSame(componentType2, resolvableType4.getComponentType());
    assertSame(componentType2, resolvableType12.getComponentType());
    assertSame(componentType2, resolvableType13.getComponentType());
    assertSame(componentType2, resolvableType14.getComponentType());
    assertSame(componentType2, resolvableType15.getComponentType());
    assertSame(componentType2, resolvableType6.getComponentType());
    assertSame(componentType2, resolvableType16.getComponentType());
    assertSame(componentType2, resolvableType17.getComponentType());
    assertSame(componentType2, resolvableType18.getComponentType());
    assertSame(componentType2, componentType.getSuperType());
    assertSame(componentType2, superType.getSuperType());
    assertSame(componentType2, superType2.getSuperType());
    assertSame(componentType2, superType3.getSuperType());
    assertSame(componentType2, resolvableType7.getSuperType());
    assertSame(componentType2, resolvableType2.getSuperType());
    assertSame(componentType2, resolvableType8.getSuperType());
    assertSame(componentType2, resolvableType9.getSuperType());
    assertSame(componentType2, resolvableType10.getSuperType());
    assertSame(componentType2, resolvableType11.getSuperType());
    assertSame(componentType2, resolvableType4.getSuperType());
    assertSame(componentType2, resolvableType12.getSuperType());
    assertSame(componentType2, resolvableType13.getSuperType());
    assertSame(componentType2, resolvableType14.getSuperType());
    assertSame(componentType2, resolvableType15.getSuperType());
    assertSame(componentType2, resolvableType6.getSuperType());
    assertSame(componentType2, resolvableType16.getSuperType());
    assertSame(componentType2, resolvableType17.getSuperType());
    assertSame(componentType2, resolvableType18.getSuperType());
    assertSame(generics, componentType.getGenerics());
    assertSame(generics, superType.getGenerics());
    assertSame(generics, superType2.getGenerics());
    assertSame(generics, superType3.getGenerics());
    assertSame(generics, resolvableType3.getGenerics());
    assertSame(generics, resolvableType5.getGenerics());
    assertSame(generics, resolvableType7.getGenerics());
    assertSame(generics, resolvableType8.getGenerics());
    assertSame(generics, resolvableType9.getGenerics());
    assertSame(generics, resolvableType10.getGenerics());
    assertSame(generics, resolvableType11.getGenerics());
    assertSame(generics, resolvableType12.getGenerics());
    assertSame(generics, resolvableType13.getGenerics());
    assertSame(generics, resolvableType14.getGenerics());
    assertSame(generics, resolvableType15.getGenerics());
    assertSame(generics, resolvableType16.getGenerics());
    assertSame(generics, resolvableType17.getGenerics());
    assertSame(generics, resolvableType18.getGenerics());
    assertSame(generics, componentType.getInterfaces());
    assertSame(generics, superType.getInterfaces());
    assertSame(generics, superType2.getInterfaces());
    assertSame(generics, superType3.getInterfaces());
    assertSame(generics, resolvableType7.getInterfaces());
    assertSame(generics, resolvableType2.getInterfaces());
    assertSame(generics, resolvableType8.getInterfaces());
    assertSame(generics, resolvableType9.getInterfaces());
    assertSame(generics, resolvableType10.getInterfaces());
    assertSame(generics, resolvableType11.getInterfaces());
    assertSame(generics, resolvableType4.getInterfaces());
    assertSame(generics, resolvableType12.getInterfaces());
    assertSame(generics, resolvableType13.getInterfaces());
    assertSame(generics, resolvableType14.getInterfaces());
    assertSame(generics, resolvableType15.getInterfaces());
    assertSame(generics, resolvableType6.getInterfaces());
    assertSame(generics, resolvableType16.getInterfaces());
    assertSame(generics, resolvableType17.getInterfaces());
    assertSame(generics, resolvableType18.getInterfaces());
    assertSame(rawClass4, superType2.getRawClass());
    assertSame(rawClass4, superType3.getRawClass());
    assertSame(rawClass4, superType.getSource());
    assertSame(rawClass4, superType2.getSource());
    assertSame(rawClass4, superType3.getSource());
    assertSame(rawClass4, superType.getType());
    assertSame(rawClass4, superType2.getType());
    assertSame(rawClass4, superType3.getType());
    assertSame(rawClass, resolvableType11.getRawClass());
    assertSame(rawClass, resolvableType15.getRawClass());
    assertSame(rawClass, resolvableType7.getSource());
    assertSame(rawClass, resolvableType11.getSource());
    assertSame(rawClass, resolvableType15.getSource());
    assertSame(rawClass, resolvableType7.getType());
    assertSame(rawClass, resolvableType11.getType());
    assertSame(rawClass, resolvableType15.getType());
    assertSame(rawClass3, resolvableType4.getRawClass());
    assertSame(rawClass3, resolvableType6.getRawClass());
    assertSame(rawClass2, resolvableType12.getRawClass());
    assertSame(rawClass2, resolvableType16.getRawClass());
    assertSame(rawClass2, resolvableType8.getSource());
    assertSame(rawClass2, resolvableType12.getSource());
    assertSame(rawClass2, resolvableType16.getSource());
    assertSame(rawClass2, resolvableType8.getType());
    assertSame(rawClass2, resolvableType12.getType());
    assertSame(rawClass2, resolvableType16.getType());
    assertSame(rawClass5, resolvableType13.getRawClass());
    assertSame(rawClass5, resolvableType17.getRawClass());
    assertSame(rawClass5, resolvableType9.getSource());
    assertSame(rawClass5, resolvableType13.getSource());
    assertSame(rawClass5, resolvableType17.getSource());
    assertSame(rawClass5, resolvableType9.getType());
    assertSame(rawClass5, resolvableType13.getType());
    assertSame(rawClass5, resolvableType17.getType());
    assertSame(rawClass6, resolvableType14.getRawClass());
    assertSame(rawClass6, resolvableType18.getRawClass());
    assertSame(rawClass6, resolvableType10.getSource());
    assertSame(rawClass6, resolvableType14.getSource());
    assertSame(rawClass6, resolvableType18.getSource());
    assertSame(rawClass6, resolvableType10.getType());
    assertSame(rawClass6, resolvableType14.getType());
    assertSame(rawClass6, resolvableType18.getType());
    assertSame(rootObject, evaluationContext2.getRootObject());
    assertSame(rootObject, evaluationContext3.getRootObject());
    assertSame(valueType, resolvableType.getRawClass());
    assertSame(valueType, resolvableType3.getRawClass());
    assertSame(valueType, resolvableType5.getRawClass());
    assertSame(valueType, resolvableType.getSource());
    assertSame(valueType, resolvableType3.getSource());
    assertSame(valueType, resolvableType5.getSource());
    assertSame(valueType, resolvableType.getType());
    assertSame(valueType, resolvableType3.getType());
    assertSame(valueType, resolvableType5.getType());
    assertSame(valueType, valueTypeDescriptor.getObjectType());
    assertSame(valueType, valueTypeDescriptor.getSource());
    assertSame(valueType, valueTypeDescriptor.getType());
    assertSame(valueType, expression.getValueType());
    assertSame(valueType, expression2.getValueType());
    assertSame(valueType, expression3.getValueType());
    assertSame(valueType, expression4.getValueType());
    assertSame(valueTypeDescriptor, expression.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression2.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression3.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression4.getValueTypeDescriptor());
    assertSame(operatorOverloader, evaluationContext2.getOperatorOverloader());
    assertSame(operatorOverloader, evaluationContext3.getOperatorOverloader());
    assertSame(typeComparator, evaluationContext2.getTypeComparator());
    assertSame(typeComparator, evaluationContext3.getTypeComparator());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualWebexNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration#webexNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testWebexNotifierConfigurationWebexNotifier3() throws EvaluationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration webexNotifierConfiguration = new AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(mock(HazelcastEventStore.class));

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(8080);
    proxyProperties.setUsername("janedoe");

    // Act
    WebexNotifier actualWebexNotifierResult = webexNotifierConfiguration.webexNotifier(repository, proxyProperties);

    // Assert
    Expression message = actualWebexNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    Expression expression = expressions[0];
    assertTrue(expression instanceof LiteralExpression);
    Expression expression2 = expressions[2];
    assertTrue(expression2 instanceof LiteralExpression);
    Expression expression3 = expressions[4];
    assertTrue(expression3 instanceof LiteralExpression);
    Expression expression4 = expressions[6];
    assertTrue(expression4 instanceof LiteralExpression);
    Expression expression5 = expressions[1];
    SpelNode aST = ((SpelExpression) expression5).getAST();
    assertTrue(aST instanceof CompoundExpression);
    Expression expression6 = expressions[3];
    SpelNode aST2 = ((SpelExpression) expression6).getAST();
    assertTrue(aST2 instanceof CompoundExpression);
    Expression expression7 = expressions[5];
    SpelNode aST3 = ((SpelExpression) expression7).getAST();
    assertTrue(aST3 instanceof CompoundExpression);
    assertTrue(expression5 instanceof SpelExpression);
    assertTrue(expression6 instanceof SpelExpression);
    assertTrue(expression7 instanceof SpelExpression);
    EvaluationContext evaluationContext = ((SpelExpression) expression5).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers = evaluationContext.getConstructorResolvers();
    assertEquals(1, constructorResolvers.size());
    assertTrue(constructorResolvers.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext2 = ((SpelExpression) expression6).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers2 = evaluationContext2.getConstructorResolvers();
    assertEquals(1, constructorResolvers2.size());
    assertTrue(constructorResolvers2.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext3 = ((SpelExpression) expression7).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers3 = evaluationContext3.getConstructorResolvers();
    assertEquals(1, constructorResolvers3.size());
    assertTrue(constructorResolvers3.get(0) instanceof ReflectiveConstructorResolver);
    List<MethodResolver> methodResolvers = evaluationContext.getMethodResolvers();
    assertEquals(1, methodResolvers.size());
    assertTrue(methodResolvers.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers2 = evaluationContext2.getMethodResolvers();
    assertEquals(1, methodResolvers2.size());
    assertTrue(methodResolvers2.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers3 = evaluationContext3.getMethodResolvers();
    assertEquals(1, methodResolvers3.size());
    assertTrue(methodResolvers3.get(0) instanceof ReflectiveMethodResolver);
    List<PropertyAccessor> propertyAccessors = evaluationContext.getPropertyAccessors();
    assertEquals(1, propertyAccessors.size());
    PropertyAccessor getResult = propertyAccessors.get(0);
    assertTrue(getResult instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors2 = evaluationContext2.getPropertyAccessors();
    assertEquals(1, propertyAccessors2.size());
    PropertyAccessor getResult2 = propertyAccessors2.get(0);
    assertTrue(getResult2 instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors3 = evaluationContext3.getPropertyAccessors();
    assertEquals(1, propertyAccessors3.size());
    PropertyAccessor getResult3 = propertyAccessors3.get(0);
    assertTrue(getResult3 instanceof ReflectivePropertyAccessor);
    assertTrue(evaluationContext instanceof StandardEvaluationContext);
    assertTrue(evaluationContext2 instanceof StandardEvaluationContext);
    assertTrue(evaluationContext3 instanceof StandardEvaluationContext);
    OperatorOverloader operatorOverloader = evaluationContext.getOperatorOverloader();
    assertTrue(operatorOverloader instanceof StandardOperatorOverloader);
    TypeComparator typeComparator = evaluationContext.getTypeComparator();
    assertTrue(typeComparator instanceof StandardTypeComparator);
    assertTrue(evaluationContext.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext2.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext3.getTypeConverter() instanceof StandardTypeConverter);
    TypeLocator typeLocator = evaluationContext.getTypeLocator();
    assertTrue(typeLocator instanceof StandardTypeLocator);
    TypeLocator typeLocator2 = evaluationContext2.getTypeLocator();
    assertTrue(typeLocator2 instanceof StandardTypeLocator);
    TypeLocator typeLocator3 = evaluationContext3.getTypeLocator();
    assertTrue(typeLocator3 instanceof StandardTypeLocator);
    assertEquals(" is <strong>", expression3.getExpressionString());
    assertEquals(" is <strong>", expression3.getValue());
    assertEquals("</strong>", expression4.getExpressionString());
    assertEquals("</strong>", expression4.getValue());
    assertEquals("</strong>/", expression2.getExpressionString());
    assertEquals("</strong>/", expression2.getValue());
    assertEquals("<strong>", expression.getExpressionString());
    assertEquals("<strong>", expression.getValue());
    assertEquals("<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
        + "}</strong>", message.getExpressionString());
    assertEquals("event.statusInfo.status", expression7.getExpressionString());
    assertEquals("event.statusInfo.status", aST3.toStringAST());
    assertEquals("event.statusInfo.status", ((SpelExpression) expression7).toStringAST());
    assertEquals("https://webexapis.com/v1/messages", actualWebexNotifierResult.getUrl().toString());
    assertEquals("instance.id", expression6.getExpressionString());
    assertEquals("instance.id", aST2.toStringAST());
    assertEquals("instance.id", ((SpelExpression) expression6).toStringAST());
    assertEquals("instance.registration.name", expression5.getExpressionString());
    assertEquals("instance.registration.name", aST.toStringAST());
    assertEquals("instance.registration.name", ((SpelExpression) expression5).toStringAST());
    List<String> importPrefixes = ((StandardTypeLocator) typeLocator).getImportPrefixes();
    assertEquals(1, importPrefixes.size());
    assertEquals("java.lang", importPrefixes.get(0));
    List<String> importPrefixes2 = ((StandardTypeLocator) typeLocator2).getImportPrefixes();
    assertEquals(1, importPrefixes2.size());
    assertEquals("java.lang", importPrefixes2.get(0));
    List<String> importPrefixes3 = ((StandardTypeLocator) typeLocator3).getImportPrefixes();
    assertEquals(1, importPrefixes3.size());
    assertEquals("java.lang", importPrefixes3.get(0));
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(getResult.getSpecificTargetClasses());
    assertNull(getResult2.getSpecificTargetClasses());
    assertNull(getResult3.getSpecificTargetClasses());
    ResolvableType resolvableType = valueTypeDescriptor.getResolvableType();
    ResolvableType componentType = resolvableType.getComponentType();
    assertNull(componentType.getRawClass());
    TypedValue rootObject = evaluationContext.getRootObject();
    assertNull(rootObject.getValue());
    assertNull(actualWebexNotifierResult.getAuthToken());
    assertNull(actualWebexNotifierResult.getRoomId());
    assertNull(((CompoundExpression) aST).getExitDescriptor());
    assertNull(((CompoundExpression) aST2).getExitDescriptor());
    assertNull(((CompoundExpression) aST3).getExitDescriptor());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertNull(rootObject.getTypeDescriptor());
    assertNull(evaluationContext.getBeanResolver());
    assertNull(evaluationContext2.getBeanResolver());
    assertNull(evaluationContext3.getBeanResolver());
    assertEquals(0, aST.getStartPosition());
    assertEquals(0, aST2.getStartPosition());
    assertEquals(0, aST3.getStartPosition());
    ResolvableType[] generics = resolvableType.getGenerics();
    assertEquals(0, generics.length);
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    ResolvableType[] interfaces = resolvableType.getInterfaces();
    ResolvableType resolvableType2 = interfaces[1];
    ResolvableType[] generics2 = resolvableType2.getGenerics();
    assertEquals(1, generics2.length);
    ResolvableType resolvableType3 = generics2[0];
    ResolvableType[] interfaces2 = resolvableType3.getInterfaces();
    ResolvableType resolvableType4 = interfaces2[1];
    ResolvableType[] generics3 = resolvableType4.getGenerics();
    assertEquals(1, generics3.length);
    ResolvableType resolvableType5 = generics3[0];
    ResolvableType[] interfaces3 = resolvableType5.getInterfaces();
    ResolvableType resolvableType6 = interfaces3[1];
    assertEquals(1, resolvableType6.getGenerics().length);
    assertEquals(11, aST2.getEndPosition());
    assertEquals(2, aST2.getChildCount());
    assertEquals(23, aST3.getEndPosition());
    assertEquals(26, aST.getEndPosition());
    assertEquals(3, aST.getChildCount());
    assertEquals(3, aST3.getChildCount());
    assertEquals(5, interfaces.length);
    assertEquals(5, interfaces2.length);
    assertEquals(5, interfaces3.length);
    assertEquals(7, expressions.length);
    assertFalse(componentType.hasGenerics());
    ResolvableType superType = resolvableType.getSuperType();
    assertFalse(superType.hasGenerics());
    ResolvableType superType2 = resolvableType3.getSuperType();
    assertFalse(superType2.hasGenerics());
    ResolvableType superType3 = resolvableType5.getSuperType();
    assertFalse(superType3.hasGenerics());
    assertFalse(resolvableType.hasGenerics());
    assertFalse(resolvableType3.hasGenerics());
    assertFalse(resolvableType5.hasGenerics());
    ResolvableType resolvableType7 = interfaces[0];
    assertFalse(resolvableType7.hasGenerics());
    ResolvableType resolvableType8 = interfaces[2];
    assertFalse(resolvableType8.hasGenerics());
    ResolvableType resolvableType9 = interfaces[3];
    assertFalse(resolvableType9.hasGenerics());
    ResolvableType resolvableType10 = interfaces[4];
    assertFalse(resolvableType10.hasGenerics());
    ResolvableType resolvableType11 = interfaces2[0];
    assertFalse(resolvableType11.hasGenerics());
    ResolvableType resolvableType12 = interfaces2[2];
    assertFalse(resolvableType12.hasGenerics());
    ResolvableType resolvableType13 = interfaces2[3];
    assertFalse(resolvableType13.hasGenerics());
    ResolvableType resolvableType14 = interfaces2[4];
    assertFalse(resolvableType14.hasGenerics());
    ResolvableType resolvableType15 = interfaces3[0];
    assertFalse(resolvableType15.hasGenerics());
    ResolvableType resolvableType16 = interfaces3[2];
    assertFalse(resolvableType16.hasGenerics());
    ResolvableType resolvableType17 = interfaces3[3];
    assertFalse(resolvableType17.hasGenerics());
    ResolvableType resolvableType18 = interfaces3[4];
    assertFalse(resolvableType18.hasGenerics());
    assertFalse(componentType.hasResolvableGenerics());
    assertFalse(superType.hasResolvableGenerics());
    assertFalse(superType2.hasResolvableGenerics());
    assertFalse(superType3.hasResolvableGenerics());
    assertFalse(resolvableType.hasResolvableGenerics());
    assertFalse(resolvableType3.hasResolvableGenerics());
    assertFalse(resolvableType5.hasResolvableGenerics());
    assertFalse(resolvableType7.hasResolvableGenerics());
    assertFalse(resolvableType8.hasResolvableGenerics());
    assertFalse(resolvableType9.hasResolvableGenerics());
    assertFalse(resolvableType10.hasResolvableGenerics());
    assertFalse(resolvableType11.hasResolvableGenerics());
    assertFalse(resolvableType12.hasResolvableGenerics());
    assertFalse(resolvableType13.hasResolvableGenerics());
    assertFalse(resolvableType14.hasResolvableGenerics());
    assertFalse(resolvableType15.hasResolvableGenerics());
    assertFalse(resolvableType16.hasResolvableGenerics());
    assertFalse(resolvableType17.hasResolvableGenerics());
    assertFalse(resolvableType18.hasResolvableGenerics());
    assertFalse(componentType.hasUnresolvableGenerics());
    assertFalse(superType.hasUnresolvableGenerics());
    assertFalse(superType2.hasUnresolvableGenerics());
    assertFalse(superType3.hasUnresolvableGenerics());
    assertFalse(resolvableType.hasUnresolvableGenerics());
    assertFalse(resolvableType3.hasUnresolvableGenerics());
    assertFalse(resolvableType5.hasUnresolvableGenerics());
    assertFalse(resolvableType7.hasUnresolvableGenerics());
    assertFalse(resolvableType2.hasUnresolvableGenerics());
    assertFalse(resolvableType8.hasUnresolvableGenerics());
    assertFalse(resolvableType9.hasUnresolvableGenerics());
    assertFalse(resolvableType10.hasUnresolvableGenerics());
    assertFalse(resolvableType11.hasUnresolvableGenerics());
    assertFalse(resolvableType4.hasUnresolvableGenerics());
    assertFalse(resolvableType12.hasUnresolvableGenerics());
    assertFalse(resolvableType13.hasUnresolvableGenerics());
    assertFalse(resolvableType14.hasUnresolvableGenerics());
    assertFalse(resolvableType15.hasUnresolvableGenerics());
    assertFalse(resolvableType6.hasUnresolvableGenerics());
    assertFalse(resolvableType16.hasUnresolvableGenerics());
    assertFalse(resolvableType17.hasUnresolvableGenerics());
    assertFalse(resolvableType18.hasUnresolvableGenerics());
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    assertFalse(aST.isCompilable());
    assertFalse(aST2.isCompilable());
    assertFalse(aST3.isCompilable());
    assertFalse(((CompoundExpression) aST).isNullSafe());
    assertFalse(((CompoundExpression) aST2).isNullSafe());
    assertFalse(((CompoundExpression) aST3).isNullSafe());
    assertTrue(actualWebexNotifierResult.isEnabled());
    assertTrue(evaluationContext.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext2.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext3.getIndexAccessors().isEmpty());
    assertTrue(resolvableType2.hasGenerics());
    assertTrue(resolvableType4.hasGenerics());
    assertTrue(resolvableType6.hasGenerics());
    assertTrue(resolvableType2.hasResolvableGenerics());
    assertTrue(resolvableType4.hasResolvableGenerics());
    assertTrue(resolvableType6.hasResolvableGenerics());
    assertTrue(evaluationContext.isAssignmentEnabled());
    assertTrue(evaluationContext2.isAssignmentEnabled());
    assertTrue(evaluationContext3.isAssignmentEnabled());
    Class<Serializable> expectedRawClass = Serializable.class;
    Class<?> rawClass = resolvableType7.getRawClass();
    assertEquals(expectedRawClass, rawClass);
    Class<CharSequence> expectedRawClass2 = CharSequence.class;
    Class<?> rawClass2 = resolvableType8.getRawClass();
    assertEquals(expectedRawClass2, rawClass2);
    Class<Comparable> expectedRawClass3 = Comparable.class;
    Class<?> rawClass3 = resolvableType2.getRawClass();
    assertEquals(expectedRawClass3, rawClass3);
    Class<Object> expectedRawClass4 = Object.class;
    Class<?> rawClass4 = superType.getRawClass();
    assertEquals(expectedRawClass4, rawClass4);
    Class<String> expectedValueType = String.class;
    Class<?> valueType = message.getValueType();
    assertEquals(expectedValueType, valueType);
    Class<Constable> expectedRawClass5 = Constable.class;
    Class<?> rawClass5 = resolvableType9.getRawClass();
    assertEquals(expectedRawClass5, rawClass5);
    Class<ConstantDesc> expectedRawClass6 = ConstantDesc.class;
    Class<?> rawClass6 = resolvableType10.getRawClass();
    assertEquals(expectedRawClass6, rawClass6);
    ResolvableType componentType2 = componentType.getComponentType();
    assertSame(componentType2, componentType2);
    assertSame(componentType2, superType.getComponentType());
    assertSame(componentType2, superType2.getComponentType());
    assertSame(componentType2, superType3.getComponentType());
    assertSame(componentType2, resolvableType3.getComponentType());
    assertSame(componentType2, resolvableType5.getComponentType());
    assertSame(componentType2, resolvableType7.getComponentType());
    assertSame(componentType2, resolvableType2.getComponentType());
    assertSame(componentType2, resolvableType8.getComponentType());
    assertSame(componentType2, resolvableType9.getComponentType());
    assertSame(componentType2, resolvableType10.getComponentType());
    assertSame(componentType2, resolvableType11.getComponentType());
    assertSame(componentType2, resolvableType4.getComponentType());
    assertSame(componentType2, resolvableType12.getComponentType());
    assertSame(componentType2, resolvableType13.getComponentType());
    assertSame(componentType2, resolvableType14.getComponentType());
    assertSame(componentType2, resolvableType15.getComponentType());
    assertSame(componentType2, resolvableType6.getComponentType());
    assertSame(componentType2, resolvableType16.getComponentType());
    assertSame(componentType2, resolvableType17.getComponentType());
    assertSame(componentType2, resolvableType18.getComponentType());
    assertSame(componentType2, componentType.getSuperType());
    assertSame(componentType2, superType.getSuperType());
    assertSame(componentType2, superType2.getSuperType());
    assertSame(componentType2, superType3.getSuperType());
    assertSame(componentType2, resolvableType7.getSuperType());
    assertSame(componentType2, resolvableType2.getSuperType());
    assertSame(componentType2, resolvableType8.getSuperType());
    assertSame(componentType2, resolvableType9.getSuperType());
    assertSame(componentType2, resolvableType10.getSuperType());
    assertSame(componentType2, resolvableType11.getSuperType());
    assertSame(componentType2, resolvableType4.getSuperType());
    assertSame(componentType2, resolvableType12.getSuperType());
    assertSame(componentType2, resolvableType13.getSuperType());
    assertSame(componentType2, resolvableType14.getSuperType());
    assertSame(componentType2, resolvableType15.getSuperType());
    assertSame(componentType2, resolvableType6.getSuperType());
    assertSame(componentType2, resolvableType16.getSuperType());
    assertSame(componentType2, resolvableType17.getSuperType());
    assertSame(componentType2, resolvableType18.getSuperType());
    assertSame(generics, componentType.getGenerics());
    assertSame(generics, superType.getGenerics());
    assertSame(generics, superType2.getGenerics());
    assertSame(generics, superType3.getGenerics());
    assertSame(generics, resolvableType3.getGenerics());
    assertSame(generics, resolvableType5.getGenerics());
    assertSame(generics, resolvableType7.getGenerics());
    assertSame(generics, resolvableType8.getGenerics());
    assertSame(generics, resolvableType9.getGenerics());
    assertSame(generics, resolvableType10.getGenerics());
    assertSame(generics, resolvableType11.getGenerics());
    assertSame(generics, resolvableType12.getGenerics());
    assertSame(generics, resolvableType13.getGenerics());
    assertSame(generics, resolvableType14.getGenerics());
    assertSame(generics, resolvableType15.getGenerics());
    assertSame(generics, resolvableType16.getGenerics());
    assertSame(generics, resolvableType17.getGenerics());
    assertSame(generics, resolvableType18.getGenerics());
    assertSame(generics, componentType.getInterfaces());
    assertSame(generics, superType.getInterfaces());
    assertSame(generics, superType2.getInterfaces());
    assertSame(generics, superType3.getInterfaces());
    assertSame(generics, resolvableType7.getInterfaces());
    assertSame(generics, resolvableType2.getInterfaces());
    assertSame(generics, resolvableType8.getInterfaces());
    assertSame(generics, resolvableType9.getInterfaces());
    assertSame(generics, resolvableType10.getInterfaces());
    assertSame(generics, resolvableType11.getInterfaces());
    assertSame(generics, resolvableType4.getInterfaces());
    assertSame(generics, resolvableType12.getInterfaces());
    assertSame(generics, resolvableType13.getInterfaces());
    assertSame(generics, resolvableType14.getInterfaces());
    assertSame(generics, resolvableType15.getInterfaces());
    assertSame(generics, resolvableType6.getInterfaces());
    assertSame(generics, resolvableType16.getInterfaces());
    assertSame(generics, resolvableType17.getInterfaces());
    assertSame(generics, resolvableType18.getInterfaces());
    assertSame(rawClass4, superType2.getRawClass());
    assertSame(rawClass4, superType3.getRawClass());
    assertSame(rawClass4, superType.getSource());
    assertSame(rawClass4, superType2.getSource());
    assertSame(rawClass4, superType3.getSource());
    assertSame(rawClass4, superType.getType());
    assertSame(rawClass4, superType2.getType());
    assertSame(rawClass4, superType3.getType());
    assertSame(rawClass, resolvableType11.getRawClass());
    assertSame(rawClass, resolvableType15.getRawClass());
    assertSame(rawClass, resolvableType7.getSource());
    assertSame(rawClass, resolvableType11.getSource());
    assertSame(rawClass, resolvableType15.getSource());
    assertSame(rawClass, resolvableType7.getType());
    assertSame(rawClass, resolvableType11.getType());
    assertSame(rawClass, resolvableType15.getType());
    assertSame(rawClass3, resolvableType4.getRawClass());
    assertSame(rawClass3, resolvableType6.getRawClass());
    assertSame(rawClass2, resolvableType12.getRawClass());
    assertSame(rawClass2, resolvableType16.getRawClass());
    assertSame(rawClass2, resolvableType8.getSource());
    assertSame(rawClass2, resolvableType12.getSource());
    assertSame(rawClass2, resolvableType16.getSource());
    assertSame(rawClass2, resolvableType8.getType());
    assertSame(rawClass2, resolvableType12.getType());
    assertSame(rawClass2, resolvableType16.getType());
    assertSame(rawClass5, resolvableType13.getRawClass());
    assertSame(rawClass5, resolvableType17.getRawClass());
    assertSame(rawClass5, resolvableType9.getSource());
    assertSame(rawClass5, resolvableType13.getSource());
    assertSame(rawClass5, resolvableType17.getSource());
    assertSame(rawClass5, resolvableType9.getType());
    assertSame(rawClass5, resolvableType13.getType());
    assertSame(rawClass5, resolvableType17.getType());
    assertSame(rawClass6, resolvableType14.getRawClass());
    assertSame(rawClass6, resolvableType18.getRawClass());
    assertSame(rawClass6, resolvableType10.getSource());
    assertSame(rawClass6, resolvableType14.getSource());
    assertSame(rawClass6, resolvableType18.getSource());
    assertSame(rawClass6, resolvableType10.getType());
    assertSame(rawClass6, resolvableType14.getType());
    assertSame(rawClass6, resolvableType18.getType());
    assertSame(rootObject, evaluationContext2.getRootObject());
    assertSame(rootObject, evaluationContext3.getRootObject());
    assertSame(valueType, resolvableType.getRawClass());
    assertSame(valueType, resolvableType3.getRawClass());
    assertSame(valueType, resolvableType5.getRawClass());
    assertSame(valueType, resolvableType.getSource());
    assertSame(valueType, resolvableType3.getSource());
    assertSame(valueType, resolvableType5.getSource());
    assertSame(valueType, resolvableType.getType());
    assertSame(valueType, resolvableType3.getType());
    assertSame(valueType, resolvableType5.getType());
    assertSame(valueType, valueTypeDescriptor.getObjectType());
    assertSame(valueType, valueTypeDescriptor.getSource());
    assertSame(valueType, valueTypeDescriptor.getType());
    assertSame(valueType, expression.getValueType());
    assertSame(valueType, expression2.getValueType());
    assertSame(valueType, expression3.getValueType());
    assertSame(valueType, expression4.getValueType());
    assertSame(valueTypeDescriptor, expression.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression2.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression3.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression4.getValueTypeDescriptor());
    assertSame(operatorOverloader, evaluationContext2.getOperatorOverloader());
    assertSame(operatorOverloader, evaluationContext3.getOperatorOverloader());
    assertSame(typeComparator, evaluationContext2.getTypeComparator());
    assertSame(typeComparator, evaluationContext3.getTypeComparator());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualWebexNotifierResult.getIgnoreChanges());
  }

  /**
   * Method under test:
   * {@link AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration#webexNotifier(InstanceRepository, NotifierProxyProperties)}
   */
  @Test
  public void testWebexNotifierConfigurationWebexNotifier4() throws EvaluationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration webexNotifierConfiguration = new AdminServerNotifierAutoConfiguration.WebexNotifierConfiguration();
    EventsourcingInstanceRepository repository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    NotifierProxyProperties proxyProperties = new NotifierProxyProperties();
    proxyProperties.setHost("localhost");
    proxyProperties.setPassword("iloveyou");
    proxyProperties.setPort(-1);
    proxyProperties.setUsername("janedoe");

    // Act
    WebexNotifier actualWebexNotifierResult = webexNotifierConfiguration.webexNotifier(repository, proxyProperties);

    // Assert
    Expression message = actualWebexNotifierResult.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    Expression expression = expressions[0];
    assertTrue(expression instanceof LiteralExpression);
    Expression expression2 = expressions[2];
    assertTrue(expression2 instanceof LiteralExpression);
    Expression expression3 = expressions[4];
    assertTrue(expression3 instanceof LiteralExpression);
    Expression expression4 = expressions[6];
    assertTrue(expression4 instanceof LiteralExpression);
    Expression expression5 = expressions[1];
    SpelNode aST = ((SpelExpression) expression5).getAST();
    assertTrue(aST instanceof CompoundExpression);
    Expression expression6 = expressions[3];
    SpelNode aST2 = ((SpelExpression) expression6).getAST();
    assertTrue(aST2 instanceof CompoundExpression);
    Expression expression7 = expressions[5];
    SpelNode aST3 = ((SpelExpression) expression7).getAST();
    assertTrue(aST3 instanceof CompoundExpression);
    assertTrue(expression5 instanceof SpelExpression);
    assertTrue(expression6 instanceof SpelExpression);
    assertTrue(expression7 instanceof SpelExpression);
    EvaluationContext evaluationContext = ((SpelExpression) expression5).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers = evaluationContext.getConstructorResolvers();
    assertEquals(1, constructorResolvers.size());
    assertTrue(constructorResolvers.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext2 = ((SpelExpression) expression6).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers2 = evaluationContext2.getConstructorResolvers();
    assertEquals(1, constructorResolvers2.size());
    assertTrue(constructorResolvers2.get(0) instanceof ReflectiveConstructorResolver);
    EvaluationContext evaluationContext3 = ((SpelExpression) expression7).getEvaluationContext();
    List<ConstructorResolver> constructorResolvers3 = evaluationContext3.getConstructorResolvers();
    assertEquals(1, constructorResolvers3.size());
    assertTrue(constructorResolvers3.get(0) instanceof ReflectiveConstructorResolver);
    List<MethodResolver> methodResolvers = evaluationContext.getMethodResolvers();
    assertEquals(1, methodResolvers.size());
    assertTrue(methodResolvers.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers2 = evaluationContext2.getMethodResolvers();
    assertEquals(1, methodResolvers2.size());
    assertTrue(methodResolvers2.get(0) instanceof ReflectiveMethodResolver);
    List<MethodResolver> methodResolvers3 = evaluationContext3.getMethodResolvers();
    assertEquals(1, methodResolvers3.size());
    assertTrue(methodResolvers3.get(0) instanceof ReflectiveMethodResolver);
    List<PropertyAccessor> propertyAccessors = evaluationContext.getPropertyAccessors();
    assertEquals(1, propertyAccessors.size());
    PropertyAccessor getResult = propertyAccessors.get(0);
    assertTrue(getResult instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors2 = evaluationContext2.getPropertyAccessors();
    assertEquals(1, propertyAccessors2.size());
    PropertyAccessor getResult2 = propertyAccessors2.get(0);
    assertTrue(getResult2 instanceof ReflectivePropertyAccessor);
    List<PropertyAccessor> propertyAccessors3 = evaluationContext3.getPropertyAccessors();
    assertEquals(1, propertyAccessors3.size());
    PropertyAccessor getResult3 = propertyAccessors3.get(0);
    assertTrue(getResult3 instanceof ReflectivePropertyAccessor);
    assertTrue(evaluationContext instanceof StandardEvaluationContext);
    assertTrue(evaluationContext2 instanceof StandardEvaluationContext);
    assertTrue(evaluationContext3 instanceof StandardEvaluationContext);
    OperatorOverloader operatorOverloader = evaluationContext.getOperatorOverloader();
    assertTrue(operatorOverloader instanceof StandardOperatorOverloader);
    TypeComparator typeComparator = evaluationContext.getTypeComparator();
    assertTrue(typeComparator instanceof StandardTypeComparator);
    assertTrue(evaluationContext.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext2.getTypeConverter() instanceof StandardTypeConverter);
    assertTrue(evaluationContext3.getTypeConverter() instanceof StandardTypeConverter);
    TypeLocator typeLocator = evaluationContext.getTypeLocator();
    assertTrue(typeLocator instanceof StandardTypeLocator);
    TypeLocator typeLocator2 = evaluationContext2.getTypeLocator();
    assertTrue(typeLocator2 instanceof StandardTypeLocator);
    TypeLocator typeLocator3 = evaluationContext3.getTypeLocator();
    assertTrue(typeLocator3 instanceof StandardTypeLocator);
    assertEquals(" is <strong>", expression3.getExpressionString());
    assertEquals(" is <strong>", expression3.getValue());
    assertEquals("</strong>", expression4.getExpressionString());
    assertEquals("</strong>", expression4.getValue());
    assertEquals("</strong>/", expression2.getExpressionString());
    assertEquals("</strong>/", expression2.getValue());
    assertEquals("<strong>", expression.getExpressionString());
    assertEquals("<strong>", expression.getValue());
    assertEquals("<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
        + "}</strong>", message.getExpressionString());
    assertEquals("event.statusInfo.status", expression7.getExpressionString());
    assertEquals("event.statusInfo.status", aST3.toStringAST());
    assertEquals("event.statusInfo.status", ((SpelExpression) expression7).toStringAST());
    assertEquals("https://webexapis.com/v1/messages", actualWebexNotifierResult.getUrl().toString());
    assertEquals("instance.id", expression6.getExpressionString());
    assertEquals("instance.id", aST2.toStringAST());
    assertEquals("instance.id", ((SpelExpression) expression6).toStringAST());
    assertEquals("instance.registration.name", expression5.getExpressionString());
    assertEquals("instance.registration.name", aST.toStringAST());
    assertEquals("instance.registration.name", ((SpelExpression) expression5).toStringAST());
    List<String> importPrefixes = ((StandardTypeLocator) typeLocator).getImportPrefixes();
    assertEquals(1, importPrefixes.size());
    assertEquals("java.lang", importPrefixes.get(0));
    List<String> importPrefixes2 = ((StandardTypeLocator) typeLocator2).getImportPrefixes();
    assertEquals(1, importPrefixes2.size());
    assertEquals("java.lang", importPrefixes2.get(0));
    List<String> importPrefixes3 = ((StandardTypeLocator) typeLocator3).getImportPrefixes();
    assertEquals(1, importPrefixes3.size());
    assertEquals("java.lang", importPrefixes3.get(0));
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(getResult.getSpecificTargetClasses());
    assertNull(getResult2.getSpecificTargetClasses());
    assertNull(getResult3.getSpecificTargetClasses());
    ResolvableType resolvableType = valueTypeDescriptor.getResolvableType();
    ResolvableType componentType = resolvableType.getComponentType();
    assertNull(componentType.getRawClass());
    TypedValue rootObject = evaluationContext.getRootObject();
    assertNull(rootObject.getValue());
    assertNull(actualWebexNotifierResult.getAuthToken());
    assertNull(actualWebexNotifierResult.getRoomId());
    assertNull(((CompoundExpression) aST).getExitDescriptor());
    assertNull(((CompoundExpression) aST2).getExitDescriptor());
    assertNull(((CompoundExpression) aST3).getExitDescriptor());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertNull(rootObject.getTypeDescriptor());
    assertNull(evaluationContext.getBeanResolver());
    assertNull(evaluationContext2.getBeanResolver());
    assertNull(evaluationContext3.getBeanResolver());
    assertEquals(0, aST.getStartPosition());
    assertEquals(0, aST2.getStartPosition());
    assertEquals(0, aST3.getStartPosition());
    ResolvableType[] generics = resolvableType.getGenerics();
    assertEquals(0, generics.length);
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    ResolvableType[] interfaces = resolvableType.getInterfaces();
    ResolvableType resolvableType2 = interfaces[1];
    ResolvableType[] generics2 = resolvableType2.getGenerics();
    assertEquals(1, generics2.length);
    ResolvableType resolvableType3 = generics2[0];
    ResolvableType[] interfaces2 = resolvableType3.getInterfaces();
    ResolvableType resolvableType4 = interfaces2[1];
    ResolvableType[] generics3 = resolvableType4.getGenerics();
    assertEquals(1, generics3.length);
    ResolvableType resolvableType5 = generics3[0];
    ResolvableType[] interfaces3 = resolvableType5.getInterfaces();
    ResolvableType resolvableType6 = interfaces3[1];
    assertEquals(1, resolvableType6.getGenerics().length);
    assertEquals(11, aST2.getEndPosition());
    assertEquals(2, aST2.getChildCount());
    assertEquals(23, aST3.getEndPosition());
    assertEquals(26, aST.getEndPosition());
    assertEquals(3, aST.getChildCount());
    assertEquals(3, aST3.getChildCount());
    assertEquals(5, interfaces.length);
    assertEquals(5, interfaces2.length);
    assertEquals(5, interfaces3.length);
    assertEquals(7, expressions.length);
    assertFalse(componentType.hasGenerics());
    ResolvableType superType = resolvableType.getSuperType();
    assertFalse(superType.hasGenerics());
    ResolvableType superType2 = resolvableType3.getSuperType();
    assertFalse(superType2.hasGenerics());
    ResolvableType superType3 = resolvableType5.getSuperType();
    assertFalse(superType3.hasGenerics());
    assertFalse(resolvableType.hasGenerics());
    assertFalse(resolvableType3.hasGenerics());
    assertFalse(resolvableType5.hasGenerics());
    ResolvableType resolvableType7 = interfaces[0];
    assertFalse(resolvableType7.hasGenerics());
    ResolvableType resolvableType8 = interfaces[2];
    assertFalse(resolvableType8.hasGenerics());
    ResolvableType resolvableType9 = interfaces[3];
    assertFalse(resolvableType9.hasGenerics());
    ResolvableType resolvableType10 = interfaces[4];
    assertFalse(resolvableType10.hasGenerics());
    ResolvableType resolvableType11 = interfaces2[0];
    assertFalse(resolvableType11.hasGenerics());
    ResolvableType resolvableType12 = interfaces2[2];
    assertFalse(resolvableType12.hasGenerics());
    ResolvableType resolvableType13 = interfaces2[3];
    assertFalse(resolvableType13.hasGenerics());
    ResolvableType resolvableType14 = interfaces2[4];
    assertFalse(resolvableType14.hasGenerics());
    ResolvableType resolvableType15 = interfaces3[0];
    assertFalse(resolvableType15.hasGenerics());
    ResolvableType resolvableType16 = interfaces3[2];
    assertFalse(resolvableType16.hasGenerics());
    ResolvableType resolvableType17 = interfaces3[3];
    assertFalse(resolvableType17.hasGenerics());
    ResolvableType resolvableType18 = interfaces3[4];
    assertFalse(resolvableType18.hasGenerics());
    assertFalse(componentType.hasResolvableGenerics());
    assertFalse(superType.hasResolvableGenerics());
    assertFalse(superType2.hasResolvableGenerics());
    assertFalse(superType3.hasResolvableGenerics());
    assertFalse(resolvableType.hasResolvableGenerics());
    assertFalse(resolvableType3.hasResolvableGenerics());
    assertFalse(resolvableType5.hasResolvableGenerics());
    assertFalse(resolvableType7.hasResolvableGenerics());
    assertFalse(resolvableType8.hasResolvableGenerics());
    assertFalse(resolvableType9.hasResolvableGenerics());
    assertFalse(resolvableType10.hasResolvableGenerics());
    assertFalse(resolvableType11.hasResolvableGenerics());
    assertFalse(resolvableType12.hasResolvableGenerics());
    assertFalse(resolvableType13.hasResolvableGenerics());
    assertFalse(resolvableType14.hasResolvableGenerics());
    assertFalse(resolvableType15.hasResolvableGenerics());
    assertFalse(resolvableType16.hasResolvableGenerics());
    assertFalse(resolvableType17.hasResolvableGenerics());
    assertFalse(resolvableType18.hasResolvableGenerics());
    assertFalse(componentType.hasUnresolvableGenerics());
    assertFalse(superType.hasUnresolvableGenerics());
    assertFalse(superType2.hasUnresolvableGenerics());
    assertFalse(superType3.hasUnresolvableGenerics());
    assertFalse(resolvableType.hasUnresolvableGenerics());
    assertFalse(resolvableType3.hasUnresolvableGenerics());
    assertFalse(resolvableType5.hasUnresolvableGenerics());
    assertFalse(resolvableType7.hasUnresolvableGenerics());
    assertFalse(resolvableType2.hasUnresolvableGenerics());
    assertFalse(resolvableType8.hasUnresolvableGenerics());
    assertFalse(resolvableType9.hasUnresolvableGenerics());
    assertFalse(resolvableType10.hasUnresolvableGenerics());
    assertFalse(resolvableType11.hasUnresolvableGenerics());
    assertFalse(resolvableType4.hasUnresolvableGenerics());
    assertFalse(resolvableType12.hasUnresolvableGenerics());
    assertFalse(resolvableType13.hasUnresolvableGenerics());
    assertFalse(resolvableType14.hasUnresolvableGenerics());
    assertFalse(resolvableType15.hasUnresolvableGenerics());
    assertFalse(resolvableType6.hasUnresolvableGenerics());
    assertFalse(resolvableType16.hasUnresolvableGenerics());
    assertFalse(resolvableType17.hasUnresolvableGenerics());
    assertFalse(resolvableType18.hasUnresolvableGenerics());
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    assertFalse(aST.isCompilable());
    assertFalse(aST2.isCompilable());
    assertFalse(aST3.isCompilable());
    assertFalse(((CompoundExpression) aST).isNullSafe());
    assertFalse(((CompoundExpression) aST2).isNullSafe());
    assertFalse(((CompoundExpression) aST3).isNullSafe());
    assertTrue(actualWebexNotifierResult.isEnabled());
    assertTrue(evaluationContext.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext2.getIndexAccessors().isEmpty());
    assertTrue(evaluationContext3.getIndexAccessors().isEmpty());
    assertTrue(resolvableType2.hasGenerics());
    assertTrue(resolvableType4.hasGenerics());
    assertTrue(resolvableType6.hasGenerics());
    assertTrue(resolvableType2.hasResolvableGenerics());
    assertTrue(resolvableType4.hasResolvableGenerics());
    assertTrue(resolvableType6.hasResolvableGenerics());
    assertTrue(evaluationContext.isAssignmentEnabled());
    assertTrue(evaluationContext2.isAssignmentEnabled());
    assertTrue(evaluationContext3.isAssignmentEnabled());
    Class<Serializable> expectedRawClass = Serializable.class;
    Class<?> rawClass = resolvableType7.getRawClass();
    assertEquals(expectedRawClass, rawClass);
    Class<CharSequence> expectedRawClass2 = CharSequence.class;
    Class<?> rawClass2 = resolvableType8.getRawClass();
    assertEquals(expectedRawClass2, rawClass2);
    Class<Comparable> expectedRawClass3 = Comparable.class;
    Class<?> rawClass3 = resolvableType2.getRawClass();
    assertEquals(expectedRawClass3, rawClass3);
    Class<Object> expectedRawClass4 = Object.class;
    Class<?> rawClass4 = superType.getRawClass();
    assertEquals(expectedRawClass4, rawClass4);
    Class<String> expectedValueType = String.class;
    Class<?> valueType = message.getValueType();
    assertEquals(expectedValueType, valueType);
    Class<Constable> expectedRawClass5 = Constable.class;
    Class<?> rawClass5 = resolvableType9.getRawClass();
    assertEquals(expectedRawClass5, rawClass5);
    Class<ConstantDesc> expectedRawClass6 = ConstantDesc.class;
    Class<?> rawClass6 = resolvableType10.getRawClass();
    assertEquals(expectedRawClass6, rawClass6);
    ResolvableType componentType2 = componentType.getComponentType();
    assertSame(componentType2, componentType2);
    assertSame(componentType2, superType.getComponentType());
    assertSame(componentType2, superType2.getComponentType());
    assertSame(componentType2, superType3.getComponentType());
    assertSame(componentType2, resolvableType3.getComponentType());
    assertSame(componentType2, resolvableType5.getComponentType());
    assertSame(componentType2, resolvableType7.getComponentType());
    assertSame(componentType2, resolvableType2.getComponentType());
    assertSame(componentType2, resolvableType8.getComponentType());
    assertSame(componentType2, resolvableType9.getComponentType());
    assertSame(componentType2, resolvableType10.getComponentType());
    assertSame(componentType2, resolvableType11.getComponentType());
    assertSame(componentType2, resolvableType4.getComponentType());
    assertSame(componentType2, resolvableType12.getComponentType());
    assertSame(componentType2, resolvableType13.getComponentType());
    assertSame(componentType2, resolvableType14.getComponentType());
    assertSame(componentType2, resolvableType15.getComponentType());
    assertSame(componentType2, resolvableType6.getComponentType());
    assertSame(componentType2, resolvableType16.getComponentType());
    assertSame(componentType2, resolvableType17.getComponentType());
    assertSame(componentType2, resolvableType18.getComponentType());
    assertSame(componentType2, componentType.getSuperType());
    assertSame(componentType2, superType.getSuperType());
    assertSame(componentType2, superType2.getSuperType());
    assertSame(componentType2, superType3.getSuperType());
    assertSame(componentType2, resolvableType7.getSuperType());
    assertSame(componentType2, resolvableType2.getSuperType());
    assertSame(componentType2, resolvableType8.getSuperType());
    assertSame(componentType2, resolvableType9.getSuperType());
    assertSame(componentType2, resolvableType10.getSuperType());
    assertSame(componentType2, resolvableType11.getSuperType());
    assertSame(componentType2, resolvableType4.getSuperType());
    assertSame(componentType2, resolvableType12.getSuperType());
    assertSame(componentType2, resolvableType13.getSuperType());
    assertSame(componentType2, resolvableType14.getSuperType());
    assertSame(componentType2, resolvableType15.getSuperType());
    assertSame(componentType2, resolvableType6.getSuperType());
    assertSame(componentType2, resolvableType16.getSuperType());
    assertSame(componentType2, resolvableType17.getSuperType());
    assertSame(componentType2, resolvableType18.getSuperType());
    assertSame(generics, componentType.getGenerics());
    assertSame(generics, superType.getGenerics());
    assertSame(generics, superType2.getGenerics());
    assertSame(generics, superType3.getGenerics());
    assertSame(generics, resolvableType3.getGenerics());
    assertSame(generics, resolvableType5.getGenerics());
    assertSame(generics, resolvableType7.getGenerics());
    assertSame(generics, resolvableType8.getGenerics());
    assertSame(generics, resolvableType9.getGenerics());
    assertSame(generics, resolvableType10.getGenerics());
    assertSame(generics, resolvableType11.getGenerics());
    assertSame(generics, resolvableType12.getGenerics());
    assertSame(generics, resolvableType13.getGenerics());
    assertSame(generics, resolvableType14.getGenerics());
    assertSame(generics, resolvableType15.getGenerics());
    assertSame(generics, resolvableType16.getGenerics());
    assertSame(generics, resolvableType17.getGenerics());
    assertSame(generics, resolvableType18.getGenerics());
    assertSame(generics, componentType.getInterfaces());
    assertSame(generics, superType.getInterfaces());
    assertSame(generics, superType2.getInterfaces());
    assertSame(generics, superType3.getInterfaces());
    assertSame(generics, resolvableType7.getInterfaces());
    assertSame(generics, resolvableType2.getInterfaces());
    assertSame(generics, resolvableType8.getInterfaces());
    assertSame(generics, resolvableType9.getInterfaces());
    assertSame(generics, resolvableType10.getInterfaces());
    assertSame(generics, resolvableType11.getInterfaces());
    assertSame(generics, resolvableType4.getInterfaces());
    assertSame(generics, resolvableType12.getInterfaces());
    assertSame(generics, resolvableType13.getInterfaces());
    assertSame(generics, resolvableType14.getInterfaces());
    assertSame(generics, resolvableType15.getInterfaces());
    assertSame(generics, resolvableType6.getInterfaces());
    assertSame(generics, resolvableType16.getInterfaces());
    assertSame(generics, resolvableType17.getInterfaces());
    assertSame(generics, resolvableType18.getInterfaces());
    assertSame(rawClass4, superType2.getRawClass());
    assertSame(rawClass4, superType3.getRawClass());
    assertSame(rawClass4, superType.getSource());
    assertSame(rawClass4, superType2.getSource());
    assertSame(rawClass4, superType3.getSource());
    assertSame(rawClass4, superType.getType());
    assertSame(rawClass4, superType2.getType());
    assertSame(rawClass4, superType3.getType());
    assertSame(rawClass, resolvableType11.getRawClass());
    assertSame(rawClass, resolvableType15.getRawClass());
    assertSame(rawClass, resolvableType7.getSource());
    assertSame(rawClass, resolvableType11.getSource());
    assertSame(rawClass, resolvableType15.getSource());
    assertSame(rawClass, resolvableType7.getType());
    assertSame(rawClass, resolvableType11.getType());
    assertSame(rawClass, resolvableType15.getType());
    assertSame(rawClass3, resolvableType4.getRawClass());
    assertSame(rawClass3, resolvableType6.getRawClass());
    assertSame(rawClass2, resolvableType12.getRawClass());
    assertSame(rawClass2, resolvableType16.getRawClass());
    assertSame(rawClass2, resolvableType8.getSource());
    assertSame(rawClass2, resolvableType12.getSource());
    assertSame(rawClass2, resolvableType16.getSource());
    assertSame(rawClass2, resolvableType8.getType());
    assertSame(rawClass2, resolvableType12.getType());
    assertSame(rawClass2, resolvableType16.getType());
    assertSame(rawClass5, resolvableType13.getRawClass());
    assertSame(rawClass5, resolvableType17.getRawClass());
    assertSame(rawClass5, resolvableType9.getSource());
    assertSame(rawClass5, resolvableType13.getSource());
    assertSame(rawClass5, resolvableType17.getSource());
    assertSame(rawClass5, resolvableType9.getType());
    assertSame(rawClass5, resolvableType13.getType());
    assertSame(rawClass5, resolvableType17.getType());
    assertSame(rawClass6, resolvableType14.getRawClass());
    assertSame(rawClass6, resolvableType18.getRawClass());
    assertSame(rawClass6, resolvableType10.getSource());
    assertSame(rawClass6, resolvableType14.getSource());
    assertSame(rawClass6, resolvableType18.getSource());
    assertSame(rawClass6, resolvableType10.getType());
    assertSame(rawClass6, resolvableType14.getType());
    assertSame(rawClass6, resolvableType18.getType());
    assertSame(rootObject, evaluationContext2.getRootObject());
    assertSame(rootObject, evaluationContext3.getRootObject());
    assertSame(valueType, resolvableType.getRawClass());
    assertSame(valueType, resolvableType3.getRawClass());
    assertSame(valueType, resolvableType5.getRawClass());
    assertSame(valueType, resolvableType.getSource());
    assertSame(valueType, resolvableType3.getSource());
    assertSame(valueType, resolvableType5.getSource());
    assertSame(valueType, resolvableType.getType());
    assertSame(valueType, resolvableType3.getType());
    assertSame(valueType, resolvableType5.getType());
    assertSame(valueType, valueTypeDescriptor.getObjectType());
    assertSame(valueType, valueTypeDescriptor.getSource());
    assertSame(valueType, valueTypeDescriptor.getType());
    assertSame(valueType, expression.getValueType());
    assertSame(valueType, expression2.getValueType());
    assertSame(valueType, expression3.getValueType());
    assertSame(valueType, expression4.getValueType());
    assertSame(valueTypeDescriptor, expression.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression2.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression3.getValueTypeDescriptor());
    assertSame(valueTypeDescriptor, expression4.getValueTypeDescriptor());
    assertSame(operatorOverloader, evaluationContext2.getOperatorOverloader());
    assertSame(operatorOverloader, evaluationContext3.getOperatorOverloader());
    assertSame(typeComparator, evaluationContext2.getTypeComparator());
    assertSame(typeComparator, evaluationContext3.getTypeComparator());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualWebexNotifierResult.getIgnoreChanges());
  }
}
