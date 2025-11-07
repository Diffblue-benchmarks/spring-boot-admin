package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
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
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {OpsGenieNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class OpsGenieNotifierDiffblueTest {
  @MockBean
  private InstanceRepository instanceRepository;

  @Autowired
  private OpsGenieNotifier opsGenieNotifier;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Test {@link OpsGenieNotifier#OpsGenieNotifier(InstanceRepository, RestTemplate)}.
   * <p>
   * Method under test: {@link OpsGenieNotifier#OpsGenieNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void OpsGenieNotifier.<init>(InstanceRepository, RestTemplate)"})
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

  /**
   * Test {@link OpsGenieNotifier#doNotify(InstanceEvent, Instance)}.
   * <p>
   * Method under test: {@link OpsGenieNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"reactor.core.publisher.Mono OpsGenieNotifier.doNotify(InstanceEvent, Instance)"})
  public void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult = StepVerifier
        .create(opsGenieNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link OpsGenieNotifier#getMessage()}.
   * <p>
   * Method under test: {@link OpsGenieNotifier#getMessage()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String OpsGenieNotifier.getMessage()"})
  public void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        opsGenieNotifier.getMessage());
  }

  /**
   * Test {@link OpsGenieNotifier#getMessage(InstanceEvent, Instance)} with {@code InstanceEvent}, {@code Instance}.
   * <p>
   * Method under test: {@link OpsGenieNotifier#getMessage(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String OpsGenieNotifier.getMessage(InstanceEvent, Instance)"})
  public void testGetMessageWithInstanceEventInstance() {
    // Arrange
    OpsGenieNotifier opsGenieNotifier = new OpsGenieNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));
    opsGenieNotifier.setDescription("The characteristics of someone or something");

    // Act and Assert
    assertEquals("The characteristics of someone or something",
        opsGenieNotifier.getMessage(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test {@link OpsGenieNotifier#setDescription(String)}.
   * <p>
   * Method under test: {@link OpsGenieNotifier#setDescription(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void OpsGenieNotifier.setDescription(String)"})
  public void testSetDescription() {
    // Arrange and Act
    opsGenieNotifier.setDescription("The characteristics of someone or something");

    // Assert
    assertEquals("The characteristics of someone or something", opsGenieNotifier.getMessage());
  }

  /**
   * Test getters and setters.
   * <p>
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String OpsGenieNotifier.getActions()", "String OpsGenieNotifier.getApiKey()",
      "String OpsGenieNotifier.getEntity()", "String OpsGenieNotifier.getSource()", "String OpsGenieNotifier.getTags()",
      "URI OpsGenieNotifier.getUrl()", "String OpsGenieNotifier.getUser()", "void OpsGenieNotifier.setActions(String)",
      "void OpsGenieNotifier.setApiKey(String)", "void OpsGenieNotifier.setEntity(String)",
      "void OpsGenieNotifier.setRestTemplate(RestTemplate)", "void OpsGenieNotifier.setSource(String)",
      "void OpsGenieNotifier.setTags(String)", "void OpsGenieNotifier.setUrl(URI)",
      "void OpsGenieNotifier.setUser(String)"})
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

    // Assert
    assertEquals("Actions", actualActions);
    assertEquals("Api Key", actualApiKey);
    assertEquals("Entity", actualEntity);
    assertEquals("Source", actualSource);
    assertEquals("Tags", actualTags);
    assertEquals("User", opsGenieNotifier.getUser());
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualUrl.toString());
    assertSame(url, actualUrl);
  }
}
