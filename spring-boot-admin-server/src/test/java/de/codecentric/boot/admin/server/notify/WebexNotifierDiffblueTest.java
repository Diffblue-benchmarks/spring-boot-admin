package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.net.URI;
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

@ContextConfiguration(classes = {WebexNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class WebexNotifierDiffblueTest {
  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private WebexNotifier webexNotifier;

  /**
   * Test {@link WebexNotifier#WebexNotifier(InstanceRepository, RestTemplate)}.
   * <p>
   * Method under test: {@link WebexNotifier#WebexNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebexNotifier.<init>(InstanceRepository, RestTemplate)"})
  public void testNewWebexNotifier() throws EvaluationException {
    // Arrange and Act
    WebexNotifier actualWebexNotifier = new WebexNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    Expression message = actualWebexNotifier.getMessage();
    assertTrue(message instanceof CompositeStringExpression);
    assertEquals("<strong>#{instance.registration.name}</strong>/#{instance.id} is <strong>#{event.statusInfo.status"
        + "}</strong>", message.getExpressionString());
    assertEquals("https://webexapis.com/v1/messages", actualWebexNotifier.getUrl().toString());
    assertNull(actualWebexNotifier.getAuthToken());
    assertNull(actualWebexNotifier.getRoomId());
    assertEquals(7, ((CompositeStringExpression) message).getExpressions().length);
    assertTrue(actualWebexNotifier.isEnabled());
    Class<String> expectedValueType = String.class;
    assertEquals(expectedValueType, message.getValueType());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualWebexNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link WebexNotifier#doNotify(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Given {@link WebexNotifier} AuthToken is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebexNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono WebexNotifier.doNotify(InstanceEvent, Instance)"})
  public void testDoNotify_givenWebexNotifierAuthTokenIsFoo() throws AssertionError {
    // Arrange
    webexNotifier.setAuthToken("foo");

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(webexNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link WebexNotifier#doNotify(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Given {@link WebexNotifier} AuthToken is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebexNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono WebexNotifier.doNotify(InstanceEvent, Instance)"})
  public void testDoNotify_givenWebexNotifierAuthTokenIsNull() throws AssertionError {
    // Arrange
    webexNotifier.setAuthToken(null);

    // Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(webexNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Test {@link WebexNotifier#createMessage(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Then return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebexNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object WebexNotifier.createMessage(InstanceEvent, Instance)"})
  public void testCreateMessage_thenReturnMap() {
    // Arrange
    WebexNotifier webexNotifier = new WebexNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    webexNotifier.setMessage("Not all who wander are lost");

    // Act
    Object actualCreateMessageResult = webexNotifier
        .createMessage(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    assertTrue(actualCreateMessageResult instanceof Map);
    assertEquals(2, ((Map<String, String>) actualCreateMessageResult).size());
    assertEquals("Not all who wander are lost", ((Map<String, String>) actualCreateMessageResult).get("markdown"));
    assertNull(((Map<String, String>) actualCreateMessageResult).get("roomId"));
  }

  /**
   * Test {@link WebexNotifier#getText(InstanceEvent, Instance)}.
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebexNotifier#getText(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String WebexNotifier.getText(InstanceEvent, Instance)"})
  public void testGetText_thenReturnNotAllWhoWanderAreLost() {
    // Arrange
    WebexNotifier webexNotifier = new WebexNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
        mock(RestTemplate.class));
    webexNotifier.setMessage("Not all who wander are lost");

    // Act and Assert
    assertEquals("Not all who wander are lost",
        webexNotifier.getText(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String WebexNotifier.getAuthToken()", "Expression WebexNotifier.getMessage()",
      "String WebexNotifier.getRoomId()", "URI WebexNotifier.getUrl()", "void WebexNotifier.setAuthToken(String)",
      "void WebexNotifier.setRestTemplate(RestTemplate)", "void WebexNotifier.setRoomId(String)",
      "void WebexNotifier.setUrl(URI)"})
  public void testGettersAndSetters() {
    // Arrange
    WebexNotifier webexNotifier = new WebexNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()),
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
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualUrl.toString());
    assertSame(url, actualUrl);
  }

  /**
   * Test {@link WebexNotifier#setMessage(String)}.
   * <p>
   * Method under test: {@link WebexNotifier#setMessage(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void WebexNotifier.setMessage(String)"})
  public void testSetMessage() throws EvaluationException {
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
