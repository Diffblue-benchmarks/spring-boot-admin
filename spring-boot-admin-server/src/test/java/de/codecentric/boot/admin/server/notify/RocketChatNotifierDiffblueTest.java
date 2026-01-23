package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.util.HashMap;
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

@ContextConfiguration(classes = {RocketChatNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class RocketChatNotifierDiffblueTest {
  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private RestTemplate restTemplate;

  @Autowired private RocketChatNotifier rocketChatNotifier;

  /**
   * Test {@link RocketChatNotifier#RocketChatNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link RocketChatNotifier#RocketChatNotifier(InstanceRepository,
   * RestTemplate)}
   */
  @Test
  @DisplayName("Test new RocketChatNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RocketChatNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewRocketChatNotifier() throws EvaluationException {
    // Arrange and Act
    RocketChatNotifier actualRocketChatNotifier =
        new RocketChatNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    Expression message = actualRocketChatNotifier.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    assertEquals(
        "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        message.getExpressionString());
    assertNull(actualRocketChatNotifier.getRoomId());
    assertNull(actualRocketChatNotifier.getToken());
    assertNull(actualRocketChatNotifier.getUrl());
    assertNull(actualRocketChatNotifier.getUserId());
    assertEquals(7, ((CompositeStringExpression) message).getExpressions().length);
    assertTrue(actualRocketChatNotifier.isEnabled());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualRocketChatNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link RocketChatNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link RocketChatNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono RocketChatNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            rocketChatNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link RocketChatNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link RocketChatNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createMessage(InstanceEvent, Instance); given 'Status'; when StatusInfo getStatus() return 'Status'; then return Map")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RocketChatNotifier.createMessage(InstanceEvent, Instance)"})
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
    Object actualCreateMessageResult = rocketChatNotifier.createMessage(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    assertTrue(actualCreateMessageResult instanceof Map);
    assertEquals(1, ((Map<String, HashMap>) actualCreateMessageResult).size());
    HashMap getResult = ((Map<String, HashMap>) actualCreateMessageResult).get("message");
    assertEquals(2, getResult.size());
    assertEquals("*Name* (42) is *Status*", getResult.get("msg"));
    assertNull(getResult.get("rid"));
  }

  /**
   * Test {@link RocketChatNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link RocketChatNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createMessage(InstanceEvent, Instance); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RocketChatNotifier.createMessage(InstanceEvent, Instance)"})
  void testCreateMessage_thenThrowIllegalStateException() {
    // Arrange
    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> rocketChatNotifier.createMessage(event, mock(Instance.class)));
    verify(event).getInstance();
  }

  /**
   * Test {@link RocketChatNotifier#getText(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link RocketChatNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getText(InstanceEvent, Instance); then return 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String RocketChatNotifier.getText(InstanceEvent, Instance)"})
  void testGetText_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    RocketChatNotifier rocketChatNotifier =
        new RocketChatNotifier(repository, mock(RestTemplate.class));
    rocketChatNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    assertEquals(
        "Not all who wander are lost",
        rocketChatNotifier.getText(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link RocketChatNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link RocketChatNotifier#setRoomId(String)}
   *   <li>{@link RocketChatNotifier#setToken(String)}
   *   <li>{@link RocketChatNotifier#setUrl(String)}
   *   <li>{@link RocketChatNotifier#setUserId(String)}
   *   <li>{@link RocketChatNotifier#getMessage()}
   *   <li>{@link RocketChatNotifier#getRoomId()}
   *   <li>{@link RocketChatNotifier#getToken()}
   *   <li>{@link RocketChatNotifier#getUrl()}
   *   <li>{@link RocketChatNotifier#getUserId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Expression RocketChatNotifier.getMessage()",
    "String RocketChatNotifier.getRoomId()",
    "String RocketChatNotifier.getToken()",
    "String RocketChatNotifier.getUrl()",
    "String RocketChatNotifier.getUserId()",
    "void RocketChatNotifier.setRestTemplate(RestTemplate)",
    "void RocketChatNotifier.setRoomId(String)",
    "void RocketChatNotifier.setToken(String)",
    "void RocketChatNotifier.setUrl(String)",
    "void RocketChatNotifier.setUserId(String)"
  })
  void testGettersAndSetters() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    RocketChatNotifier rocketChatNotifier =
        new RocketChatNotifier(repository, mock(RestTemplate.class));

    // Act
    rocketChatNotifier.setRestTemplate(mock(RestTemplate.class));
    rocketChatNotifier.setRoomId("42");
    rocketChatNotifier.setToken("ABC123");
    rocketChatNotifier.setUrl("https://example.org/example");
    rocketChatNotifier.setUserId("42");
    Expression actualMessage = rocketChatNotifier.getMessage();
    String actualRoomId = rocketChatNotifier.getRoomId();
    String actualToken = rocketChatNotifier.getToken();
    String actualUrl = rocketChatNotifier.getUrl();

    // Assert
    assertTrue(actualMessage instanceof CompositeStringExpression);
    assertEquals("42", actualRoomId);
    assertEquals("42", rocketChatNotifier.getUserId());
    assertEquals("ABC123", actualToken);
    assertEquals("https://example.org/example", actualUrl);
  }

  /**
   * Test {@link RocketChatNotifier#setMessage(String)}.
   *
   * <p>Method under test: {@link RocketChatNotifier#setMessage(String)}
   */
  @Test
  @DisplayName("Test setMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RocketChatNotifier.setMessage(String)"})
  void testSetMessage() throws EvaluationException {
    // Arrange and Act
    rocketChatNotifier.setMessage("Not all who wander are lost");

    // Assert
    Expression message = rocketChatNotifier.getMessage();
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

  /**
   * Test {@link RocketChatNotifier#setMessage(String)}.
   *
   * <p>Method under test: {@link RocketChatNotifier#setMessage(String)}
   */
  @Test
  @DisplayName("Test setMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RocketChatNotifier.setMessage(String)"})
  void testSetMessage2() throws EvaluationException {
    // Arrange and Act
    rocketChatNotifier.setMessage("java.lang.VoidNot all who wander are lostmsg");

    // Assert
    Expression message = rocketChatNotifier.getMessage();
    assertTrue(message instanceof LiteralExpression);
    TypeDescriptor valueTypeDescriptor = message.getValueTypeDescriptor();
    assertEquals("java.lang.String", valueTypeDescriptor.getName());
    assertEquals("java.lang.VoidNot all who wander are lostmsg", message.getExpressionString());
    assertEquals("java.lang.VoidNot all who wander are lostmsg", message.getValue());
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
   * Test {@link RocketChatNotifier#setMessage(String)}.
   *
   * <ul>
   *   <li>When {@code 4242}.
   *   <li>Then {@link RocketChatNotifier} Message ExpressionString is {@code 4242}.
   * </ul>
   *
   * <p>Method under test: {@link RocketChatNotifier#setMessage(String)}
   */
  @Test
  @DisplayName(
      "Test setMessage(String); when '4242'; then RocketChatNotifier Message ExpressionString is '4242'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RocketChatNotifier.setMessage(String)"})
  void testSetMessage_when4242_thenRocketChatNotifierMessageExpressionStringIs4242()
      throws EvaluationException {
    // Arrange and Act
    rocketChatNotifier.setMessage("4242");

    // Assert
    Expression message = rocketChatNotifier.getMessage();
    assertTrue(message instanceof LiteralExpression);
    assertEquals("4242", message.getExpressionString());
    assertEquals("4242", message.getValue());
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
