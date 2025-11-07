package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DingTalkNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class DingTalkNotifierDiffblueTest {
  @Autowired
  private DingTalkNotifier dingTalkNotifier;

  @MockBean
  private InstanceRepository instanceRepository;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Method under test: {@link DingTalkNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(dingTalkNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link DingTalkNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify2() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(dingTalkNotifier.doNotify(mock(InstanceDeregisteredEvent.class), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link DingTalkNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateMessage() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    DingTalkNotifier dingTalkNotifier = new DingTalkNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));
    dingTalkNotifier.setMessage("Not all who wander are lost");

    // Act
    Object actualCreateMessageResult = dingTalkNotifier
        .createMessage(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Object body = ((HttpEntity<Object>) actualCreateMessageResult).getBody();
    assertEquals(2, ((Map<String, Object>) body).size());
    Object getResult = ((Map<String, Object>) body).get("text");
    assertTrue(getResult instanceof Map);
    assertTrue(body instanceof Map);
    assertTrue(actualCreateMessageResult instanceof HttpEntity);
    assertEquals(1, ((Map<String, String>) getResult).size());
    assertEquals("Not all who wander are lost", ((Map<String, String>) getResult).get("content"));
    HttpHeaders headers = ((HttpEntity<Object>) actualCreateMessageResult).getHeaders();
    assertEquals(1, headers.size());
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertEquals("text", ((Map<String, Object>) body).get("msgtype"));
    assertTrue(((HttpEntity<Object>) actualCreateMessageResult).hasBody());
  }

  /**
   * Method under test: {@link DingTalkNotifier#getMessage()}
   */
  @Test
  public void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name} #{instance.id} is #{event.statusInfo.status}",
        dingTalkNotifier.getMessage());
  }

  /**
   * Method under test: {@link DingTalkNotifier#setMessage(String)}
   */
  @Test
  public void testSetMessage() {
    // Arrange and Act
    dingTalkNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", dingTalkNotifier.getMessage());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DingTalkNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link DingTalkNotifier#setSecret(String)}
   *   <li>{@link DingTalkNotifier#setWebhookUrl(String)}
   *   <li>{@link DingTalkNotifier#getSecret()}
   *   <li>{@link DingTalkNotifier#getWebhookUrl()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DingTalkNotifier dingTalkNotifier = new DingTalkNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));

    // Act
    dingTalkNotifier.setRestTemplate(mock(RestTemplate.class));
    dingTalkNotifier.setSecret("Secret");
    dingTalkNotifier.setWebhookUrl("https://example.org/example");
    String actualSecret = dingTalkNotifier.getSecret();

    // Assert that nothing has changed
    assertEquals("Secret", actualSecret);
    assertEquals("https://example.org/example", dingTalkNotifier.getWebhookUrl());
  }

  /**
   * Method under test:
   * {@link DingTalkNotifier#DingTalkNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  public void testNewDingTalkNotifier() {
    // Arrange and Act
    DingTalkNotifier actualDingTalkNotifier = new DingTalkNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals("#{instance.registration.name} #{instance.id} is #{event.statusInfo.status}",
        actualDingTalkNotifier.getMessage());
    assertNull(actualDingTalkNotifier.getSecret());
    assertNull(actualDingTalkNotifier.getWebhookUrl());
    assertTrue(actualDingTalkNotifier.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualDingTalkNotifier.getIgnoreChanges());
  }
}
