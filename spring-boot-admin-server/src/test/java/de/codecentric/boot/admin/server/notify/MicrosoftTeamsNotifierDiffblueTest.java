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
import de.codecentric.boot.admin.server.domain.events.InstanceEndpointsDetectedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.MicrosoftTeamsNotifier.Fact;
import de.codecentric.boot.admin.server.notify.MicrosoftTeamsNotifier.Message;
import de.codecentric.boot.admin.server.notify.MicrosoftTeamsNotifier.Section;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.ConstructorResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.support.DataBindingPropertyAccessor;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardOperatorOverloader;
import org.springframework.expression.spel.support.StandardTypeComparator;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {MicrosoftTeamsNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class MicrosoftTeamsNotifierDiffblueTest {
  @MockitoBean private InstanceRepository instanceRepository;

  @Autowired private MicrosoftTeamsNotifier microsoftTeamsNotifier;

  @MockitoBean private RestTemplate restTemplate;

  /**
   * Test {@link MicrosoftTeamsNotifier#MicrosoftTeamsNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#MicrosoftTeamsNotifier(InstanceRepository,
   * RestTemplate)}
   */
  @Test
  @DisplayName("Test new MicrosoftTeamsNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewMicrosoftTeamsNotifier() {
    // Arrange and Act
    MicrosoftTeamsNotifier actualMicrosoftTeamsNotifier =
        new MicrosoftTeamsNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
            + ".statusInfo.status}",
        actualMicrosoftTeamsNotifier.getStatusActivitySubtitle());
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        actualMicrosoftTeamsNotifier.getDeregisterActivitySubtitle());
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        actualMicrosoftTeamsNotifier.getRegisterActivitySubtitle());
    assertEquals("De-Registered", actualMicrosoftTeamsNotifier.getDeRegisteredTitle());
    assertEquals("Registered", actualMicrosoftTeamsNotifier.getRegisteredTitle());
    assertEquals(
        "Spring Boot Admin Notification", actualMicrosoftTeamsNotifier.getMessageSummary());
    assertEquals("Status Changed", actualMicrosoftTeamsNotifier.getStatusChangedTitle());
    assertEquals(
        "event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        actualMicrosoftTeamsNotifier.getThemeColor());
    assertNull(actualMicrosoftTeamsNotifier.getWebhookUrl());
    assertTrue(actualMicrosoftTeamsNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualMicrosoftTeamsNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono MicrosoftTeamsNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify() throws AssertionError {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    InstanceRegisteredEvent event = new InstanceRegisteredEvent(instance, 2L, registration);

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance2 = mock(Instance.class);
    when(instance2.getId()).thenReturn(InstanceId.of("42"));
    when(instance2.getStatusInfo()).thenReturn(statusInfo);
    when(instance2.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(microsoftTeamsNotifier.doNotify(event, instance2));
    createResult.expectError().verify();
    verify(instance2).getId();
    verify(instance2, atLeast(1)).getRegistration();
    verify(instance2).getStatusInfo();
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then calls {@link Instance#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test doNotify(InstanceEvent, Instance); given StatusInfo getStatus() return 'Status'; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono MicrosoftTeamsNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify_givenStatusInfoGetStatusReturnStatus_thenCallsGetId() throws AssertionError {
    // Arrange
    InstanceDeregisteredEvent event = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    when(instance.getStatusInfo()).thenReturn(statusInfo);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(microsoftTeamsNotifier.doNotify(event, instance));
    createResult.expectError().verify();
    verify(instance).getId();
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono MicrosoftTeamsNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify_thenThrowIllegalStateException() {
    // Arrange
    InstanceRegisteredEvent event = mock(InstanceRegisteredEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> microsoftTeamsNotifier.doNotify(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test shouldNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MicrosoftTeamsNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify() {
    // Arrange, Act and Assert
    assertTrue(
        microsoftTeamsNotifier.shouldNotify(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test shouldNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MicrosoftTeamsNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify2() {
    // Arrange
    InstanceId instance = InstanceId.of("42");
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act and Assert
    assertTrue(
        microsoftTeamsNotifier.shouldNotify(
            new InstanceRegisteredEvent(instance, 1L, registration), null));
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test shouldNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MicrosoftTeamsNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify3() {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    assertFalse(
        microsoftTeamsNotifier.shouldNotify(
            new InstanceEndpointsDetectedEvent(instance, 1L, Endpoints.empty()),
            mock(Instance.class)));
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test shouldNotify(InstanceEvent, Instance); given 'Status'; when StatusInfo getStatus() return 'Status'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MicrosoftTeamsNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify_givenStatus_whenStatusInfoGetStatusReturnStatus_thenReturnTrue() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    boolean actualShouldNotifyResult =
        microsoftTeamsNotifier.shouldNotify(
            new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo), null);

    // Assert
    verify(statusInfo).getStatus();
    assertTrue(actualShouldNotifyResult);
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code UP}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code UP}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test shouldNotify(InstanceEvent, Instance); given 'UP'; when StatusInfo getStatus() return 'UP'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MicrosoftTeamsNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify_givenUp_whenStatusInfoGetStatusReturnUp_thenReturnFalse() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");

    // Act
    boolean actualShouldNotifyResult =
        microsoftTeamsNotifier.shouldNotify(
            new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo), null);

    // Assert
    verify(statusInfo).getStatus();
    assertFalse(actualShouldNotifyResult);
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then calls {@link InstanceStatusChangedEvent#getInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test shouldNotify(InstanceEvent, Instance); then calls getInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MicrosoftTeamsNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify_thenCallsGetInstance() {
    // Arrange
    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> microsoftTeamsNotifier.shouldNotify(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test shouldNotify(InstanceEvent, Instance); when StatusInfo getStatus() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MicrosoftTeamsNotifier.shouldNotify(InstanceEvent, Instance)"})
  void testShouldNotify_whenStatusInfoGetStatusThrowIllegalStateException() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            microsoftTeamsNotifier.shouldNotify(
                new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo), null));
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getDeregisteredMessage(Instance, EvaluationContext)}.
   *
   * <ul>
   *   <li>Then return Title is {@code De-Registered}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getDeregisteredMessage(Instance,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test getDeregisteredMessage(Instance, EvaluationContext); then return Title is 'De-Registered'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.getDeregisteredMessage(Instance, EvaluationContext)"
  })
  void testGetDeregisteredMessage_thenReturnTitleIsDeRegistered() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setThemeColor("Status");
    microsoftTeamsNotifier.setDeregisterActivitySubtitle("Dr");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act
    Message actualDeregisteredMessage =
        microsoftTeamsNotifier.getDeregisteredMessage(instance, new StandardEvaluationContext());

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
    assertEquals("De-Registered", actualDeregisteredMessage.getTitle());
    List<Section> sections = actualDeregisteredMessage.getSections();
    assertEquals(1, sections.size());
    Section getResult = sections.get(0);
    assertEquals("Dr", getResult.getActivitySubtitle());
    List<Fact> facts = getResult.getFacts();
    assertEquals(5, facts.size());
    Fact getResult2 = facts.get(3);
    assertEquals("Management URL", getResult2.getName());
    assertEquals("Name", getResult.getActivityTitle());
    Fact getResult3 = facts.get(1);
    assertEquals("Service URL", getResult3.getName());
    Fact getResult4 = facts.get(4);
    assertEquals("Source", getResult4.getName());
    assertEquals("Source", getResult4.getValue());
    assertEquals("Spring Boot Admin Notification", actualDeregisteredMessage.getSummary());
    Fact getResult5 = facts.get(0);
    assertEquals("Status", getResult5.getName());
    assertEquals("Status", getResult5.getValue());
    assertEquals("Status", actualDeregisteredMessage.getThemeColor());
    assertEquals("https://example.org/example", getResult3.getValue());
    assertEquals("https://example.org/example", getResult2.getValue());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getDeregisteredMessage(Instance, EvaluationContext)}.
   *
   * <ul>
   *   <li>When {@link Instance} {@link Instance#getRegistration()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getDeregisteredMessage(Instance,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test getDeregisteredMessage(Instance, EvaluationContext); when Instance getRegistration() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.getDeregisteredMessage(Instance, EvaluationContext)"
  })
  void testGetDeregisteredMessage_whenInstanceGetRegistrationThrowIllegalStateException() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setDeregisterActivitySubtitle("Dr");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getRegistration()).thenThrow(new IllegalStateException());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            microsoftTeamsNotifier.getDeregisteredMessage(
                instance, new StandardEvaluationContext()));
    verify(instance).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getDeregisteredMessage(Instance, EvaluationContext)}.
   *
   * <ul>
   *   <li>When {@link Instance} {@link Instance#getStatusInfo()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getDeregisteredMessage(Instance,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test getDeregisteredMessage(Instance, EvaluationContext); when Instance getStatusInfo() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.getDeregisteredMessage(Instance, EvaluationContext)"
  })
  void testGetDeregisteredMessage_whenInstanceGetStatusInfoThrowIllegalStateException() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setDeregisterActivitySubtitle("Dr");

    Instance instance = mock(Instance.class);
    when(instance.getStatusInfo()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            microsoftTeamsNotifier.getDeregisteredMessage(
                instance, new StandardEvaluationContext()));
    verify(instance).getStatusInfo();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getRegisteredMessage(Instance, EvaluationContext)}.
   *
   * <ul>
   *   <li>Then return Sections size is one.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getRegisteredMessage(Instance,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test getRegisteredMessage(Instance, EvaluationContext); then return Sections size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.getRegisteredMessage(Instance, EvaluationContext)"
  })
  void testGetRegisteredMessage_thenReturnSectionsSizeIsOne() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setThemeColor("Status");
    microsoftTeamsNotifier.setRegisterActivitySubtitle("Dr");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act
    Message actualRegisteredMessage =
        microsoftTeamsNotifier.getRegisteredMessage(instance, new StandardEvaluationContext());

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
    List<Section> sections = actualRegisteredMessage.getSections();
    assertEquals(1, sections.size());
    Section getResult = sections.get(0);
    assertEquals("Dr", getResult.getActivitySubtitle());
    List<Fact> facts = getResult.getFacts();
    assertEquals(5, facts.size());
    Fact getResult2 = facts.get(3);
    assertEquals("Management URL", getResult2.getName());
    assertEquals("Name", getResult.getActivityTitle());
    assertEquals("Registered", actualRegisteredMessage.getTitle());
    Fact getResult3 = facts.get(1);
    assertEquals("Service URL", getResult3.getName());
    Fact getResult4 = facts.get(4);
    assertEquals("Source", getResult4.getName());
    assertEquals("Source", getResult4.getValue());
    assertEquals("Spring Boot Admin Notification", actualRegisteredMessage.getSummary());
    Fact getResult5 = facts.get(0);
    assertEquals("Status", getResult5.getName());
    assertEquals("Status", getResult5.getValue());
    assertEquals("Status", actualRegisteredMessage.getThemeColor());
    assertEquals("https://example.org/example", getResult3.getValue());
    assertEquals("https://example.org/example", getResult2.getValue());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getRegisteredMessage(Instance, EvaluationContext)}.
   *
   * <ul>
   *   <li>When {@link Instance} {@link Instance#getRegistration()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getRegisteredMessage(Instance,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test getRegisteredMessage(Instance, EvaluationContext); when Instance getRegistration() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.getRegisteredMessage(Instance, EvaluationContext)"
  })
  void testGetRegisteredMessage_whenInstanceGetRegistrationThrowIllegalStateException() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setRegisterActivitySubtitle("Dr");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getRegistration()).thenThrow(new IllegalStateException());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            microsoftTeamsNotifier.getRegisteredMessage(instance, new StandardEvaluationContext()));
    verify(instance).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getRegisteredMessage(Instance, EvaluationContext)}.
   *
   * <ul>
   *   <li>When {@link Instance} {@link Instance#getStatusInfo()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getRegisteredMessage(Instance,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test getRegisteredMessage(Instance, EvaluationContext); when Instance getStatusInfo() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.getRegisteredMessage(Instance, EvaluationContext)"
  })
  void testGetRegisteredMessage_whenInstanceGetStatusInfoThrowIllegalStateException() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setRegisterActivitySubtitle("Dr");

    Instance instance = mock(Instance.class);
    when(instance.getStatusInfo()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            microsoftTeamsNotifier.getRegisteredMessage(instance, new StandardEvaluationContext()));
    verify(instance).getStatusInfo();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getStatusChangedMessage(Instance, EvaluationContext)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getStatusChangedMessage(Instance,
   * EvaluationContext)}
   */
  @Test
  @DisplayName("Test getStatusChangedMessage(Instance, EvaluationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.getStatusChangedMessage(Instance, EvaluationContext)"
  })
  void testGetStatusChangedMessage() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setStatusActivitySubtitle("Dr");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getRegistration()).thenThrow(new IllegalStateException());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            microsoftTeamsNotifier.getStatusChangedMessage(
                instance, new StandardEvaluationContext()));
    verify(instance).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getStatusChangedMessage(Instance, EvaluationContext)}.
   *
   * <ul>
   *   <li>Then return Sections size is one.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getStatusChangedMessage(Instance,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test getStatusChangedMessage(Instance, EvaluationContext); then return Sections size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.getStatusChangedMessage(Instance, EvaluationContext)"
  })
  void testGetStatusChangedMessage_thenReturnSectionsSizeIsOne() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setThemeColor("Status");
    microsoftTeamsNotifier.setStatusActivitySubtitle("Dr");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act
    Message actualStatusChangedMessage =
        microsoftTeamsNotifier.getStatusChangedMessage(instance, new StandardEvaluationContext());

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
    List<Section> sections = actualStatusChangedMessage.getSections();
    assertEquals(1, sections.size());
    Section getResult = sections.get(0);
    assertEquals("Dr", getResult.getActivitySubtitle());
    List<Fact> facts = getResult.getFacts();
    assertEquals(5, facts.size());
    Fact getResult2 = facts.get(3);
    assertEquals("Management URL", getResult2.getName());
    assertEquals("Name", getResult.getActivityTitle());
    Fact getResult3 = facts.get(1);
    assertEquals("Service URL", getResult3.getName());
    Fact getResult4 = facts.get(4);
    assertEquals("Source", getResult4.getName());
    assertEquals("Source", getResult4.getValue());
    assertEquals("Spring Boot Admin Notification", actualStatusChangedMessage.getSummary());
    assertEquals("Status Changed", actualStatusChangedMessage.getTitle());
    Fact getResult5 = facts.get(0);
    assertEquals("Status", getResult5.getName());
    assertEquals("Status", getResult5.getValue());
    assertEquals("Status", actualStatusChangedMessage.getThemeColor());
    assertEquals("https://example.org/example", getResult3.getValue());
    assertEquals("https://example.org/example", getResult2.getValue());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getStatusChangedMessage(Instance, EvaluationContext)}.
   *
   * <ul>
   *   <li>When {@link Instance} {@link Instance#getStatusInfo()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getStatusChangedMessage(Instance,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test getStatusChangedMessage(Instance, EvaluationContext); when Instance getStatusInfo() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.getStatusChangedMessage(Instance, EvaluationContext)"
  })
  void testGetStatusChangedMessage_whenInstanceGetStatusInfoThrowIllegalStateException() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setStatusActivitySubtitle("Dr");

    Instance instance = mock(Instance.class);
    when(instance.getStatusInfo()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            microsoftTeamsNotifier.getStatusChangedMessage(
                instance, new StandardEvaluationContext()));
    verify(instance).getStatusInfo();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#createMessage(Instance, String, String, EvaluationContext)}.
   *
   * <ul>
   *   <li>Then return Title is {@code Dr}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#createMessage(Instance, String, String,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test createMessage(Instance, String, String, EvaluationContext); then return Title is 'Dr'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.createMessage(Instance, String, String, EvaluationContext)"
  })
  void testCreateMessage_thenReturnTitleIsDr() {
    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    microsoftTeamsNotifier.setThemeColor("Status");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act
    Message actualCreateMessageResult =
        microsoftTeamsNotifier.createMessage(instance, "Dr", "Dr", new StandardEvaluationContext());

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
    assertEquals("Dr", actualCreateMessageResult.getTitle());
    List<Section> sections = actualCreateMessageResult.getSections();
    assertEquals(1, sections.size());
    Section getResult = sections.get(0);
    assertEquals("Dr", getResult.getActivitySubtitle());
    List<Fact> facts = getResult.getFacts();
    assertEquals(5, facts.size());
    Fact getResult2 = facts.get(3);
    assertEquals("Management URL", getResult2.getName());
    assertEquals("Name", getResult.getActivityTitle());
    Fact getResult3 = facts.get(1);
    assertEquals("Service URL", getResult3.getName());
    Fact getResult4 = facts.get(4);
    assertEquals("Source", getResult4.getName());
    assertEquals("Source", getResult4.getValue());
    assertEquals("Spring Boot Admin Notification", actualCreateMessageResult.getSummary());
    Fact getResult5 = facts.get(0);
    assertEquals("Status", getResult5.getName());
    assertEquals("Status", getResult5.getValue());
    assertEquals("Status", actualCreateMessageResult.getThemeColor());
    assertEquals("https://example.org/example", getResult3.getValue());
    assertEquals("https://example.org/example", getResult2.getValue());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#createMessage(Instance, String, String, EvaluationContext)}.
   *
   * <ul>
   *   <li>When {@link Instance} {@link Instance#getRegistration()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#createMessage(Instance, String, String,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test createMessage(Instance, String, String, EvaluationContext); when Instance getRegistration() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.createMessage(Instance, String, String, EvaluationContext)"
  })
  void testCreateMessage_whenInstanceGetRegistrationThrowIllegalStateException() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getRegistration()).thenThrow(new IllegalStateException());
    when(instance.getStatusInfo()).thenReturn(statusInfo);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            microsoftTeamsNotifier.createMessage(
                instance, "Dr", "Dr", new StandardEvaluationContext()));
    verify(instance).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#createMessage(Instance, String, String, EvaluationContext)}.
   *
   * <ul>
   *   <li>When {@link Instance} {@link Instance#getStatusInfo()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#createMessage(Instance, String, String,
   * EvaluationContext)}
   */
  @Test
  @DisplayName(
      "Test createMessage(Instance, String, String, EvaluationContext); when Instance getStatusInfo() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Message MicrosoftTeamsNotifier.createMessage(Instance, String, String, EvaluationContext)"
  })
  void testCreateMessage_whenInstanceGetStatusInfoThrowIllegalStateException() {
    // Arrange
    Instance instance = mock(Instance.class);
    when(instance.getStatusInfo()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            microsoftTeamsNotifier.createMessage(
                instance, "Dr", "Dr", new StandardEvaluationContext()));
    verify(instance).getStatusInfo();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#evaluateExpression(EvaluationContext, Expression)}.
   *
   * <ul>
   *   <li>When {@link StandardEvaluationContext#StandardEvaluationContext()}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#evaluateExpression(EvaluationContext,
   * Expression)}
   */
  @Test
  @DisplayName(
      "Test evaluateExpression(EvaluationContext, Expression); when StandardEvaluationContext(); then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String MicrosoftTeamsNotifier.evaluateExpression(EvaluationContext, Expression)"
  })
  void testEvaluateExpression_whenStandardEvaluationContext_thenReturn42() {
    // Arrange
    StandardEvaluationContext context = new StandardEvaluationContext();

    // Act
    String actualEvaluateExpressionResult =
        microsoftTeamsNotifier.evaluateExpression(context, new LiteralExpression("42"));

    // Assert
    assertEquals("42", actualEvaluateExpressionResult);
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#createEvaluationContext(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then RootObject Value return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#createEvaluationContext(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test createEvaluationContext(InstanceEvent, Instance); then RootObject Value return Map")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "EvaluationContext MicrosoftTeamsNotifier.createEvaluationContext(InstanceEvent, Instance)"
  })
  void testCreateEvaluationContext_thenRootObjectValueReturnMap() {
    // Arrange and Act
    EvaluationContext actualCreateEvaluationContextResult =
        microsoftTeamsNotifier.createEvaluationContext(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class));

    // Assert
    Object value = actualCreateEvaluationContextResult.getRootObject().getValue();
    assertTrue(value instanceof Map);
    List<PropertyAccessor> propertyAccessors =
        actualCreateEvaluationContextResult.getPropertyAccessors();
    assertEquals(2, propertyAccessors.size());
    assertTrue(propertyAccessors.get(1) instanceof MapAccessor);
    assertTrue(propertyAccessors.get(0) instanceof DataBindingPropertyAccessor);
    assertTrue(actualCreateEvaluationContextResult instanceof SimpleEvaluationContext);
    assertTrue(
        actualCreateEvaluationContextResult.getOperatorOverloader()
            instanceof StandardOperatorOverloader);
    assertTrue(
        actualCreateEvaluationContextResult.getTypeComparator() instanceof StandardTypeComparator);
    assertTrue(
        actualCreateEvaluationContextResult.getTypeConverter() instanceof StandardTypeConverter);
    assertNull(actualCreateEvaluationContextResult.getBeanResolver());
    assertEquals(3, ((Map<String, Object>) value).size());
    List<ConstructorResolver> constructorResolvers =
        actualCreateEvaluationContextResult.getConstructorResolvers();
    assertTrue(constructorResolvers.isEmpty());
    assertTrue(((Map<String, Object>) value).containsKey("event"));
    assertTrue(((Map<String, Object>) value).containsKey("instance"));
    assertTrue(((Map<String, Object>) value).containsKey("lastStatus"));
    assertTrue(actualCreateEvaluationContextResult.isAssignmentEnabled());
    assertSame(constructorResolvers, actualCreateEvaluationContextResult.getIndexAccessors());
    assertSame(constructorResolvers, actualCreateEvaluationContextResult.getMethodResolvers());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#createEvaluationContext(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#createEvaluationContext(InstanceEvent,
   * Instance)}
   */
  @Test
  @DisplayName(
      "Test createEvaluationContext(InstanceEvent, Instance); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "EvaluationContext MicrosoftTeamsNotifier.createEvaluationContext(InstanceEvent, Instance)"
  })
  void testCreateEvaluationContext_thenThrowIllegalStateException() {
    // Arrange
    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> microsoftTeamsNotifier.createEvaluationContext(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getThemeColor()}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getThemeColor()}
   */
  @Test
  @DisplayName("Test getThemeColor()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.getThemeColor()"})
  void testGetThemeColor() {
    // Arrange, Act and Assert
    assertEquals(
        "event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        microsoftTeamsNotifier.getThemeColor());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#setThemeColor(String)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#setThemeColor(String)}
   */
  @Test
  @DisplayName("Test setThemeColor(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.setThemeColor(String)"})
  void testSetThemeColor() {
    // Arrange and Act
    microsoftTeamsNotifier.setThemeColor("Theme Color");

    // Assert
    assertEquals("Theme Color", microsoftTeamsNotifier.getThemeColor());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getDeregisterActivitySubtitle()}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getDeregisterActivitySubtitle()}
   */
  @Test
  @DisplayName("Test getDeregisterActivitySubtitle()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.getDeregisterActivitySubtitle()"})
  void testGetDeregisterActivitySubtitle() {
    // Arrange, Act and Assert
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        microsoftTeamsNotifier.getDeregisterActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#setDeregisterActivitySubtitle(String)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#setDeregisterActivitySubtitle(String)}
   */
  @Test
  @DisplayName("Test setDeregisterActivitySubtitle(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.setDeregisterActivitySubtitle(String)"})
  void testSetDeregisterActivitySubtitle() {
    // Arrange and Act
    microsoftTeamsNotifier.setDeregisterActivitySubtitle("Dr");

    // Assert
    assertEquals("Dr", microsoftTeamsNotifier.getDeregisterActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getRegisterActivitySubtitle()}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getRegisterActivitySubtitle()}
   */
  @Test
  @DisplayName("Test getRegisterActivitySubtitle()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.getRegisterActivitySubtitle()"})
  void testGetRegisterActivitySubtitle() {
    // Arrange, Act and Assert
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        microsoftTeamsNotifier.getRegisterActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#setRegisterActivitySubtitle(String)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#setRegisterActivitySubtitle(String)}
   */
  @Test
  @DisplayName("Test setRegisterActivitySubtitle(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.setRegisterActivitySubtitle(String)"})
  void testSetRegisterActivitySubtitle() {
    // Arrange and Act
    microsoftTeamsNotifier.setRegisterActivitySubtitle("Dr");

    // Assert
    assertEquals("Dr", microsoftTeamsNotifier.getRegisterActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getStatusActivitySubtitle()}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#getStatusActivitySubtitle()}
   */
  @Test
  @DisplayName("Test getStatusActivitySubtitle()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.getStatusActivitySubtitle()"})
  void testGetStatusActivitySubtitle() {
    // Arrange, Act and Assert
    assertEquals(
        "#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
            + ".statusInfo.status}",
        microsoftTeamsNotifier.getStatusActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#setStatusActivitySubtitle(String)}.
   *
   * <p>Method under test: {@link MicrosoftTeamsNotifier#setStatusActivitySubtitle(String)}
   */
  @Test
  @DisplayName("Test setStatusActivitySubtitle(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.setStatusActivitySubtitle(String)"})
  void testSetStatusActivitySubtitle() {
    // Arrange and Act
    microsoftTeamsNotifier.setStatusActivitySubtitle("Dr");

    // Assert
    assertEquals("Dr", microsoftTeamsNotifier.getStatusActivitySubtitle());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier#setDeRegisteredTitle(String)}
   *   <li>{@link MicrosoftTeamsNotifier#setMessageSummary(String)}
   *   <li>{@link MicrosoftTeamsNotifier#setRegisteredTitle(String)}
   *   <li>{@link MicrosoftTeamsNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link MicrosoftTeamsNotifier#setStatusChangedTitle(String)}
   *   <li>{@link MicrosoftTeamsNotifier#setWebhookUrl(URI)}
   *   <li>{@link MicrosoftTeamsNotifier#getDeRegisteredTitle()}
   *   <li>{@link MicrosoftTeamsNotifier#getMessageSummary()}
   *   <li>{@link MicrosoftTeamsNotifier#getRegisteredTitle()}
   *   <li>{@link MicrosoftTeamsNotifier#getStatusChangedTitle()}
   *   <li>{@link MicrosoftTeamsNotifier#getWebhookUrl()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String MicrosoftTeamsNotifier.getDeRegisteredTitle()",
    "String MicrosoftTeamsNotifier.getMessageSummary()",
    "String MicrosoftTeamsNotifier.getRegisteredTitle()",
    "String MicrosoftTeamsNotifier.getStatusChangedTitle()",
    "URI MicrosoftTeamsNotifier.getWebhookUrl()",
    "void MicrosoftTeamsNotifier.setDeRegisteredTitle(String)",
    "void MicrosoftTeamsNotifier.setMessageSummary(String)",
    "void MicrosoftTeamsNotifier.setRegisteredTitle(String)",
    "void MicrosoftTeamsNotifier.setRestTemplate(RestTemplate)",
    "void MicrosoftTeamsNotifier.setStatusChangedTitle(String)",
    "void MicrosoftTeamsNotifier.setWebhookUrl(URI)"
  })
  void testGettersAndSetters() {
    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier =
        new MicrosoftTeamsNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));

    // Act
    microsoftTeamsNotifier.setDeRegisteredTitle("Dr");
    microsoftTeamsNotifier.setMessageSummary("Message Summary");
    microsoftTeamsNotifier.setRegisteredTitle("Dr");
    microsoftTeamsNotifier.setRestTemplate(mock(RestTemplate.class));
    microsoftTeamsNotifier.setStatusChangedTitle("Dr");
    URI webhookUrl = PagerdutyNotifier.DEFAULT_URI;
    microsoftTeamsNotifier.setWebhookUrl(webhookUrl);
    String actualDeRegisteredTitle = microsoftTeamsNotifier.getDeRegisteredTitle();
    String actualMessageSummary = microsoftTeamsNotifier.getMessageSummary();
    String actualRegisteredTitle = microsoftTeamsNotifier.getRegisteredTitle();
    String actualStatusChangedTitle = microsoftTeamsNotifier.getStatusChangedTitle();
    URI actualWebhookUrl = microsoftTeamsNotifier.getWebhookUrl();

    // Assert
    assertEquals("Dr", actualDeRegisteredTitle);
    assertEquals("Dr", actualRegisteredTitle);
    assertEquals("Dr", actualStatusChangedTitle);
    assertEquals("Message Summary", actualMessageSummary);
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json",
        actualWebhookUrl.toString());
    assertSame(webhookUrl, actualWebhookUrl);
  }
}
