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
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.common.CompositeStringExpression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.CompoundExpression;
import org.springframework.expression.spel.standard.SpelExpression;
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
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class WebexNotifierDiffblueTest {
  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private RestTemplate restTemplate;

  @Autowired private WebexNotifier webexNotifier;

  @InjectMocks private WebexNotifier webexNotifier2;

  /**
   * Test {@link WebexNotifier#WebexNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link WebexNotifier#WebexNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @DisplayName("Test new WebexNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebexNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewWebexNotifier() throws EvaluationException {
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
   * Test {@link WebexNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException()}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createMessage(InstanceEvent, Instance); given IllegalStateException(); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object WebexNotifier.createMessage(InstanceEvent, Instance)"})
  void testCreateMessage_givenIllegalStateException_thenThrowIllegalStateException() {
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
   * Test {@link WebexNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>Then return {@code <strong>Name</strong>/ is <strong>Status</strong>}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test getText(InstanceEvent, Instance); given 'Status'; then return '<strong>Name</strong>/ is <strong>Status</strong>'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String WebexNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_givenStatus_thenReturnStrongNameStrongIsStrongStatusStrong() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");
    InstanceStatusChangedEvent event =
        new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo);

    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(null);
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
    String actualText = webexNotifier2.getText(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    Expression message = webexNotifier2.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    Expression expression = expressions[1];
    SpelNode aST = ((SpelExpression) expression).getAST();
    assertTrue(aST instanceof CompoundExpression);
    Expression expression2 = expressions[3];
    SpelNode aST2 = ((SpelExpression) expression2).getAST();
    assertTrue(aST2 instanceof CompoundExpression);
    Expression expression3 = expressions[5];
    SpelNode aST3 = ((SpelExpression) expression3).getAST();
    assertTrue(aST3 instanceof CompoundExpression);
    assertTrue(expression instanceof SpelExpression);
    assertTrue(expression2 instanceof SpelExpression);
    assertTrue(expression3 instanceof SpelExpression);
    assertEquals("<strong>Name</strong>/ is <strong>Status</strong>", actualText);
    assertEquals(
        "Lde/codecentric/boot/admin/server/domain/values/InstanceId",
        ((CompoundExpression) aST2).getExitDescriptor());
    assertEquals("Ljava/lang/String", ((CompoundExpression) aST).getExitDescriptor());
    assertEquals("Ljava/lang/String", ((CompoundExpression) aST3).getExitDescriptor());
    assertEquals(7, expressions.length);
  }

  /**
   * Test {@link WebexNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code <strong>event</strong>/ is <strong>Status</strong>}.
   * </ul>
   *
   * <p>Method under test: {@link WebexNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test getText(InstanceEvent, Instance); then return '<strong>event</strong>/ is <strong>Status</strong>'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String WebexNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_thenReturnStrongEventStrongIsStrongStatusStrong() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");
    InstanceStatusChangedEvent event =
        new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo);

    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(null);
    when(instance.getRegistration())
        .thenReturn(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("event")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Act
    String actualText = webexNotifier2.getText(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    Expression message = webexNotifier2.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    Expression[] expressions = ((CompositeStringExpression) message).getExpressions();
    Expression expression = expressions[1];
    SpelNode aST = ((SpelExpression) expression).getAST();
    assertTrue(aST instanceof CompoundExpression);
    Expression expression2 = expressions[3];
    SpelNode aST2 = ((SpelExpression) expression2).getAST();
    assertTrue(aST2 instanceof CompoundExpression);
    Expression expression3 = expressions[5];
    SpelNode aST3 = ((SpelExpression) expression3).getAST();
    assertTrue(aST3 instanceof CompoundExpression);
    assertTrue(expression instanceof SpelExpression);
    assertTrue(expression2 instanceof SpelExpression);
    assertTrue(expression3 instanceof SpelExpression);
    assertEquals("<strong>event</strong>/ is <strong>Status</strong>", actualText);
    assertEquals(
        "Lde/codecentric/boot/admin/server/domain/values/InstanceId",
        ((CompoundExpression) aST2).getExitDescriptor());
    assertEquals("Ljava/lang/String", ((CompoundExpression) aST).getExitDescriptor());
    assertEquals("Ljava/lang/String", ((CompoundExpression) aST3).getExitDescriptor());
    assertEquals(7, expressions.length);
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
    WebexNotifier webexNotifier =
        new WebexNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));

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
    Class<String> expectedValueType = String.class;
    Class<?> valueType = message.getValueType();
    assertEquals(expectedValueType, valueType);
    assertSame(valueType, valueTypeDescriptor.getObjectType());
    assertSame(valueType, valueTypeDescriptor.getSource());
    assertSame(valueType, valueTypeDescriptor.getType());
  }
}
