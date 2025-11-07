package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.net.URI;
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
@ContextConfiguration(classes = {OpsGenieNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class OpsGenieNotifierDiffblueTest {
  @MockBean
  private InstanceRepository instanceRepository;

  @Autowired
  private OpsGenieNotifier opsGenieNotifier;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Method under test: {@link OpsGenieNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(opsGenieNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link OpsGenieNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify2() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(opsGenieNotifier.doNotify(mock(InstanceDeregisteredEvent.class), null));
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link OpsGenieNotifier#buildUrl(InstanceEvent, Instance)}
   */
  @Test
  public void testBuildUrl() {
    // Arrange, Act and Assert
    assertEquals("https://api.opsgenie.com/v2/alerts",
        opsGenieNotifier.buildUrl(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test:
   * {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateRequest() {
    // Arrange and Act
    HttpEntity<?> actualCreateRequestResult = opsGenieNotifier
        .createRequest(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Object body = actualCreateRequestResult.getBody();
    assertTrue(body instanceof Map);
    HttpHeaders headers = actualCreateRequestResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.AUTHORIZATION);
    assertEquals(1, getResult.size());
    assertEquals("GenieKey null", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertTrue(((Map<Object, Object>) body).isEmpty());
    assertTrue(actualCreateRequestResult.hasBody());
  }

  /**
   * Method under test: {@link OpsGenieNotifier#getMessage()}
   */
  @Test
  public void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        opsGenieNotifier.getMessage());
  }

  /**
   * Method under test:
   * {@link OpsGenieNotifier#getMessage(InstanceEvent, Instance)}
   */
  @Test
  public void testGetMessage2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    OpsGenieNotifier opsGenieNotifier = new OpsGenieNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));
    opsGenieNotifier.setDescription("The characteristics of someone or something");

    // Act and Assert
    assertEquals("The characteristics of someone or something",
        opsGenieNotifier.getMessage(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test: {@link OpsGenieNotifier#setDescription(String)}
   */
  @Test
  public void testSetDescription() {
    // Arrange and Act
    opsGenieNotifier.setDescription("The characteristics of someone or something");

    // Assert
    assertEquals("The characteristics of someone or something", opsGenieNotifier.getMessage());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link OpsGenieNotifier#setActions(String)}
   *   <li>{@link OpsGenieNotifier#setApiKey(String)}
   *   <li>{@link OpsGenieNotifier#setEntity(String)}
   *   <li>{@link OpsGenieNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link OpsGenieNotifier#setSource(String)}
   *   <li>{@link OpsGenieNotifier#setTags(String)}
   *   <li>{@link OpsGenieNotifier#setUrl(URI)}
   *   <li>{@link OpsGenieNotifier#setUser(String)}
   *   <li>{@link OpsGenieNotifier#getActions()}
   *   <li>{@link OpsGenieNotifier#getApiKey()}
   *   <li>{@link OpsGenieNotifier#getEntity()}
   *   <li>{@link OpsGenieNotifier#getSource()}
   *   <li>{@link OpsGenieNotifier#getTags()}
   *   <li>{@link OpsGenieNotifier#getUrl()}
   *   <li>{@link OpsGenieNotifier#getUser()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    OpsGenieNotifier opsGenieNotifier = new OpsGenieNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));

    // Act
    opsGenieNotifier.setActions("Actions");
    opsGenieNotifier.setApiKey("Api Key");
    opsGenieNotifier.setEntity("Entity");
    opsGenieNotifier.setRestTemplate(mock(RestTemplate.class));
    opsGenieNotifier.setSource("Source");
    opsGenieNotifier.setTags("Tags");
    URI url = PagerdutyNotifier.DEFAULT_URI;
    opsGenieNotifier.setUrl(url);
    opsGenieNotifier.setUser("User");
    String actualActions = opsGenieNotifier.getActions();
    String actualApiKey = opsGenieNotifier.getApiKey();
    String actualEntity = opsGenieNotifier.getEntity();
    String actualSource = opsGenieNotifier.getSource();
    String actualTags = opsGenieNotifier.getTags();
    URI actualUrl = opsGenieNotifier.getUrl();

    // Assert that nothing has changed
    assertEquals("Actions", actualActions);
    assertEquals("Api Key", actualApiKey);
    assertEquals("Entity", actualEntity);
    assertEquals("Source", actualSource);
    assertEquals("Tags", actualTags);
    assertEquals("User", opsGenieNotifier.getUser());
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualUrl.toString());
    assertSame(url, actualUrl);
  }

  /**
   * Method under test:
   * {@link OpsGenieNotifier#OpsGenieNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  public void testNewOpsGenieNotifier() {
    // Arrange and Act
    OpsGenieNotifier actualOpsGenieNotifier = new OpsGenieNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualOpsGenieNotifier.getMessage());
    assertEquals("https://api.opsgenie.com/v2/alerts", actualOpsGenieNotifier.getUrl().toString());
    assertNull(actualOpsGenieNotifier.getActions());
    assertNull(actualOpsGenieNotifier.getApiKey());
    assertNull(actualOpsGenieNotifier.getEntity());
    assertNull(actualOpsGenieNotifier.getSource());
    assertNull(actualOpsGenieNotifier.getTags());
    assertNull(actualOpsGenieNotifier.getUser());
    assertTrue(actualOpsGenieNotifier.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualOpsGenieNotifier.getIgnoreChanges());
  }
}
