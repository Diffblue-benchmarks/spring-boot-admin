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
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegistrationUpdatedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.common.CompositeStringExpression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {WebexNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class WebexNotifierDiffblueTest {
  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private RestTemplate restTemplate;

  @Autowired private WebexNotifier webexNotifier;

  /**
   * Test {@link WebexNotifier#WebexNotifier(InstanceRepository, RestTemplate)}.
   *
   * <ul>
   *   <li>Then first element return {@link LiteralExpression}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#WebexNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @DisplayName(
      "Test new WebexNotifier(InstanceRepository, RestTemplate); then first element return LiteralExpression")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebexNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewWebexNotifier_thenFirstElementReturnLiteralExpression() {
    // Arrange
    SnapshottingInstanceRepository repository =
        new SnapshottingInstanceRepository(new InMemoryEventStore(100));

    // Act
    WebexNotifier actualWebexNotifier = new WebexNotifier(repository, mock(RestTemplate.class));

    // Assert
    Expression message = actualWebexNotifier.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    assertTrue(expressions[0] instanceof LiteralExpression);
    assertTrue(expressions[2] instanceof LiteralExpression);
    assertTrue(expressions[4] instanceof LiteralExpression);
    assertTrue(expressions[6] instanceof LiteralExpression);
    assertEquals(7, expressions.length);
  }

  /**
   * Test {@link WebexNotifier#WebexNotifier(InstanceRepository, RestTemplate)}.
   *
   * <ul>
   *   <li>Then return Message ExpressionString is a string.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#WebexNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @DisplayName(
      "Test new WebexNotifier(InstanceRepository, RestTemplate); then return Message ExpressionString is a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebexNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewWebexNotifier_thenReturnMessageExpressionStringIsAString()
      throws EvaluationException {
    // Arrange and Act
    WebexNotifier actualWebexNotifier =
        new WebexNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    Expression message = actualWebexNotifier.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    assertEquals(
        "<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
            + "}</strong>",
        message.getExpressionString());
    assertEquals("https://webexapis.com/v1/messages", actualWebexNotifier.getUrl().toString());
    assertNull(actualWebexNotifier.getAuthToken());
    assertNull(actualWebexNotifier.getRoomId());
    assertEquals(7, ((CompositeStringExpression) message).getExpressions().length);
    assertTrue(actualWebexNotifier.isEnabled());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualWebexNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link WebexNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link WebexNotifier} AuthToken is {@code foo}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test doNotify(InstanceEvent, Instance); given WebexNotifier AuthToken is 'foo'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono WebexNotifier.doNotify(InstanceEvent, Instance)"})
  void testDoNotify_givenWebexNotifierAuthTokenIsFoo_whenNull() throws AssertionError {
    // Arrange
    webexNotifier.setAuthToken("foo");

    // Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            webexNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link WebexNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link WebexNotifier}.
   *   <li>When {@link InstanceDeregisteredEvent}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test doNotify(InstanceEvent, Instance); given WebexNotifier; when InstanceDeregisteredEvent")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono WebexNotifier.doNotify(InstanceEvent, Instance)"})
  void testDoNotify_givenWebexNotifier_whenInstanceDeregisteredEvent() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            webexNotifier.doNotify(mock(InstanceDeregisteredEvent.class), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link WebexNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link WebexNotifier}.
   *   <li>When {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test doNotify(InstanceEvent, Instance); given WebexNotifier; when InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono WebexNotifier.doNotify(InstanceEvent, Instance)"})
  void testDoNotify_givenWebexNotifier_whenInstanceIdWithValueIs42() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            webexNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link WebexNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"reactor.core.publisher.Mono WebexNotifier.doNotify(InstanceEvent, Instance)"})
  void testDoNotify_thenThrowIllegalStateException() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    WebexNotifier webexNotifier = new WebexNotifier(repository, mock(RestTemplate.class));
    webexNotifier.setAuthToken("ABC123");

    InstanceDeregisteredEvent event = mock(InstanceDeregisteredEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> webexNotifier.doNotify(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link WebexNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createMessage(InstanceEvent, Instance); given 'Status'; when StatusInfo getStatus() return 'Status'; then return Map")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object WebexNotifier.createMessage(InstanceEvent, Instance)"})
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
    Object actualCreateMessageResult = webexNotifier.createMessage(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    assertTrue(actualCreateMessageResult instanceof Map);
    assertEquals(2, ((Map<String, String>) actualCreateMessageResult).size());
    assertEquals(
        "<strong>Name</strong>/42 is <strong>Status</strong>",
        ((Map<String, String>) actualCreateMessageResult).get("markdown"));
    assertNull(((Map<String, String>) actualCreateMessageResult).get("roomId"));
  }

  /**
   * Test {@link WebexNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createMessage(InstanceEvent, Instance); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object WebexNotifier.createMessage(InstanceEvent, Instance)"})
  void testCreateMessage_thenThrowIllegalStateException() {
    // Arrange
    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> webexNotifier.createMessage(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link WebexNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getText(InstanceEvent, Instance); then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String WebexNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_thenReturn42() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    WebexNotifier webexNotifier = new WebexNotifier(repository, mock(RestTemplate.class));
    webexNotifier.setMessage("42");
    InstanceId instance = InstanceId.of("42");
    Instant timestamp = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act and Assert
    assertEquals(
        "42",
        webexNotifier.getText(
            new InstanceRegistrationUpdatedEvent(instance, 1L, timestamp, registration), null));
  }

  /**
   * Test {@link WebexNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getText(InstanceEvent, Instance); then return 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String WebexNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    WebexNotifier webexNotifier = new WebexNotifier(repository, mock(RestTemplate.class));
    webexNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    assertEquals(
        "Not all who wander are lost",
        webexNotifier.getText(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link WebexNotifier#setAuthToken(String)}
   *   <li>{@link WebexNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link WebexNotifier#setRoomId(String)}
   *   <li>{@link WebexNotifier#setUrl(URI)}
   *   <li>{@link WebexNotifier#getAuthToken()}
   *   <li>{@link WebexNotifier#getMessage()}
   *   <li>{@link WebexNotifier#getRoomId()}
   *   <li>{@link WebexNotifier#getUrl()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String WebexNotifier.getAuthToken()",
    "Expression WebexNotifier.getMessage()",
    "String WebexNotifier.getRoomId()",
    "URI WebexNotifier.getUrl()",
    "void WebexNotifier.setAuthToken(String)",
    "void WebexNotifier.setRestTemplate(RestTemplate)",
    "void WebexNotifier.setRoomId(String)",
    "void WebexNotifier.setUrl(URI)"
  })
  void testGettersAndSetters() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    WebexNotifier webexNotifier = new WebexNotifier(repository, mock(RestTemplate.class));

    // Act
    webexNotifier.setAuthToken("ABC123");
    webexNotifier.setRestTemplate(mock(RestTemplate.class));
    webexNotifier.setRoomId("42");
    URI url = PagerdutyNotifier.DEFAULT_URI;
    webexNotifier.setUrl(url);
    String actualAuthToken = webexNotifier.getAuthToken();
    Expression actualMessage = webexNotifier.getMessage();
    String actualRoomId = webexNotifier.getRoomId();
    URI actualUrl = webexNotifier.getUrl();

    // Assert
    assertTrue(actualMessage instanceof CompositeStringExpression);
    assertEquals("42", actualRoomId);
    assertEquals("ABC123", actualAuthToken);
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualUrl.toString());
    assertSame(url, actualUrl);
  }

  /**
   * Test {@link WebexNotifier#setMessage(String)}.
   *
   * <p>Method under test: {@link WebexNotifier#setMessage(String)}
   */
  @Test
  @DisplayName("Test setMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebexNotifier.setMessage(String)"})
  void testSetMessage() throws EvaluationException {
    // Arrange and Act
    webexNotifier.setMessage(
        "de.codecentric.boot.admin.server.notify.WebexNotifierNot all who wander are lost");

    // Assert
    Expression message = webexNotifier.getMessage();
    assertTrue(message instanceof LiteralExpression);
    assertEquals(
        "de.codecentric.boot.admin.server.notify.WebexNotifierNot all who wander are lost",
        message.getExpressionString());
    assertEquals(
        "de.codecentric.boot.admin.server.notify.WebexNotifierNot all who wander are lost",
        message.getValue());
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    Class<String> expectedObjectType = String.class;
    assertEquals(expectedObjectType, valueTypeDescriptor.getObjectType());
    Class<String> expectedType = String.class;
    assertEquals(expectedType, valueTypeDescriptor.getType());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
  }

  /**
   * Test {@link WebexNotifier#setMessage(String)}.
   *
   * <p>Method under test: {@link WebexNotifier#setMessage(String)}
   */
  @Test
  @DisplayName("Test setMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebexNotifier.setMessage(String)"})
  void testSetMessage2() throws EvaluationException {
    // Arrange and Act
    webexNotifier.setMessage("de.codecentric.boot.admin.server.notify.WebexNotifier42");

    // Assert
    Expression message = webexNotifier.getMessage();
    assertTrue(message instanceof LiteralExpression);
    assertEquals(
        "de.codecentric.boot.admin.server.notify.WebexNotifier42", message.getExpressionString());
    assertEquals("de.codecentric.boot.admin.server.notify.WebexNotifier42", message.getValue());
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    Class<String> expectedObjectType = String.class;
    assertEquals(expectedObjectType, valueTypeDescriptor.getObjectType());
    Class<String> expectedType = String.class;
    assertEquals(expectedType, valueTypeDescriptor.getType());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
  }

  /**
   * Test {@link WebexNotifier#setMessage(String)}.
   *
   * <ul>
   *   <li>Then {@link WebexNotifier} Message ExpressionString is {@code Not all who wander are
   *       lost}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#setMessage(String)}
   */
  @Test
  @DisplayName(
      "Test setMessage(String); then WebexNotifier Message ExpressionString is 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebexNotifier.setMessage(String)"})
  void testSetMessage_thenWebexNotifierMessageExpressionStringIsNotAllWhoWanderAreLost()
      throws EvaluationException {
    // Arrange and Act
    webexNotifier.setMessage("Not all who wander are lost");

    // Assert
    Expression message = webexNotifier.getMessage();
    assertTrue(message instanceof LiteralExpression);
    assertEquals("Not all who wander are lost", message.getExpressionString());
    assertEquals("Not all who wander are lost", message.getValue());
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertNull(valueTypeDescriptor.getElementTypeDescriptor());
    assertEquals(0, valueTypeDescriptor.getAnnotations().length);
    assertFalse(valueTypeDescriptor.isArray());
    assertFalse(valueTypeDescriptor.isCollection());
    assertFalse(valueTypeDescriptor.isMap());
    assertFalse(valueTypeDescriptor.isPrimitive());
    Class<String> expectedObjectType = String.class;
    assertEquals(expectedObjectType, valueTypeDescriptor.getObjectType());
    Class<String> expectedType = String.class;
    assertEquals(expectedType, valueTypeDescriptor.getType());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
  }
}
