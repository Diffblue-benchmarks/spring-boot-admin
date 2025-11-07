package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.common.CompositeStringExpression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {RocketChatNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RocketChatNotifierDiffblueTest {
  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private RocketChatNotifier rocketChatNotifier;

  /**
   * Test {@link RocketChatNotifier#RocketChatNotifier(InstanceRepository, RestTemplate)}.
   * <p>
   * Method under test: {@link RocketChatNotifier#RocketChatNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RocketChatNotifier.<init>(InstanceRepository, RestTemplate)"})
  public void testNewRocketChatNotifier() throws EvaluationException {
    // Arrange and Act
    RocketChatNotifier actualRocketChatNotifier = new RocketChatNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    Expression message = actualRocketChatNotifier.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    assertEquals("*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}*",
        message.getExpressionString());
    assertNull(actualRocketChatNotifier.getRoomId());
    assertNull(actualRocketChatNotifier.getToken());
    assertNull(actualRocketChatNotifier.getUrl());
    assertNull(actualRocketChatNotifier.getUserId());
    assertEquals(7, ((CompositeStringExpression) message).getExpressions().length);
    assertTrue(actualRocketChatNotifier.isEnabled());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualRocketChatNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link RocketChatNotifier#doNotify(InstanceEvent, Instance)}.
   * <p>
   * Method under test: {@link RocketChatNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono RocketChatNotifier.doNotify(InstanceEvent, Instance)"})
  public void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(rocketChatNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link RocketChatNotifier#createMessage(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Then return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RocketChatNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object RocketChatNotifier.createMessage(InstanceEvent, Instance)"})
  public void testCreateMessage_thenReturnMap() {
    // Arrange
    RocketChatNotifier rocketChatNotifier = new RocketChatNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));
    rocketChatNotifier.setMessage("Not all who wander are lost");

    // Act
    Object actualCreateMessageResult = rocketChatNotifier
        .createMessage(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    assertTrue(actualCreateMessageResult instanceof Map);
    assertEquals(1, ((Map<String, HashMap>) actualCreateMessageResult).size());
    HashMap getResult = ((Map<String, HashMap>) actualCreateMessageResult).get("message");
    assertEquals(2, getResult.size());
    assertEquals("Not all who wander are lost", getResult.get("msg"));
    assertNull(getResult.get("rid"));
  }

  /**
   * Test {@link RocketChatNotifier#getText(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException(String)} with {@code event42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RocketChatNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String RocketChatNotifier.getText(InstanceEvent, Instance)"})
  public void testGetText_givenIllegalStateExceptionWithEvent42() {
    // Arrange
    RocketChatNotifier rocketChatNotifier = new RocketChatNotifier(
        new EventsourcingInstanceRepository(mock(InstanceEventStore.class)), mock(RestTemplate.class));
    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException("event42"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> rocketChatNotifier.getText(event, null));
    verify(event).getInstance();
  }

  /**
   * Test {@link RocketChatNotifier#getText(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException(String)} with {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RocketChatNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String RocketChatNotifier.getText(InstanceEvent, Instance)"})
  public void testGetText_givenIllegalStateExceptionWithFoo() {
    // Arrange
    RocketChatNotifier rocketChatNotifier = new RocketChatNotifier(
        new EventsourcingInstanceRepository(mock(InstanceEventStore.class)), mock(RestTemplate.class));
    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> rocketChatNotifier.getText(event, null));
    verify(event).getInstance();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Expression RocketChatNotifier.getMessage()", "String RocketChatNotifier.getRoomId()",
      "String RocketChatNotifier.getToken()", "String RocketChatNotifier.getUrl()",
      "String RocketChatNotifier.getUserId()", "void RocketChatNotifier.setRestTemplate(RestTemplate)",
      "void RocketChatNotifier.setRoomId(String)", "void RocketChatNotifier.setToken(String)",
      "void RocketChatNotifier.setUrl(String)", "void RocketChatNotifier.setUserId(String)"})
  public void testGettersAndSetters() {
    // Arrange
    RocketChatNotifier rocketChatNotifier = new RocketChatNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));

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
   * <p>
   * Method under test: {@link RocketChatNotifier#setMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RocketChatNotifier.setMessage(String)"})
  public void testSetMessage() throws EvaluationException {
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
    Class<String> expectedValueType = String.class;
    Class<?> valueType = message.getValueType();
    assertEquals(expectedValueType, valueType);
    assertSame(valueType, valueTypeDescriptor.getObjectType());
    assertSame(valueType, valueTypeDescriptor.getSource());
    assertSame(valueType, valueTypeDescriptor.getType());
  }
}
