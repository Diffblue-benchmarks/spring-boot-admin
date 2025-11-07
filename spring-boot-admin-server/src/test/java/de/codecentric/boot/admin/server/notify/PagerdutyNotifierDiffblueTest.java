package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.net.URI;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {PagerdutyNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class PagerdutyNotifierDiffblueTest {
  @MockBean
  private InstanceRepository instanceRepository;

  @Autowired
  private PagerdutyNotifier pagerdutyNotifier;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Method under test:
   * {@link PagerdutyNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(pagerdutyNotifier.doNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link PagerdutyNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify2() throws AssertionError {
    // Arrange, Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(pagerdutyNotifier.doNotify(mock(InstanceDeregisteredEvent.class), null));
    createResult.expectError().verify();
  }

  /**
   * Method under test: {@link PagerdutyNotifier#getDescription()}
   */
  @Test
  public void testGetDescription() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        pagerdutyNotifier.getDescription());
  }

  /**
   * Method under test:
   * {@link PagerdutyNotifier#getDescription(InstanceEvent, Instance)}
   */
  @Test
  public void testGetDescription2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    PagerdutyNotifier pagerdutyNotifier = new PagerdutyNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));
    pagerdutyNotifier.setDescription("The characteristics of someone or something");

    // Act and Assert
    assertEquals("The characteristics of someone or something",
        pagerdutyNotifier.getDescription(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Method under test: {@link PagerdutyNotifier#setDescription(String)}
   */
  @Test
  public void testSetDescription() {
    // Arrange and Act
    pagerdutyNotifier.setDescription("The characteristics of someone or something");

    // Assert
    assertEquals("The characteristics of someone or something", pagerdutyNotifier.getDescription());
  }

  /**
   * Method under test: {@link PagerdutyNotifier#getDetails(InstanceEvent)}
   */
  @Test
  public void testGetDetails() {
    // Arrange, Act and Assert
    assertTrue(pagerdutyNotifier.getDetails(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)).isEmpty());
  }

  /**
   * Method under test: {@link PagerdutyNotifier#getDetails(InstanceEvent)}
   */
  @Test
  public void testGetDetails2() {
    // Arrange and Act
    Map<String, Object> actualDetails = pagerdutyNotifier
        .getDetails(new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, null));

    // Assert
    assertEquals(2, actualDetails.size());
    assertEquals("UNKNOWN", actualDetails.get("from"));
    assertNull(actualDetails.get("to"));
  }

  /**
   * Method under test: {@link PagerdutyNotifier#getDetails(InstanceEvent)}
   */
  @Test
  public void testGetDetails3() {
    // Arrange
    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));
    when(event.getStatusInfo()).thenReturn(null);

    // Act
    Map<String, Object> actualDetails = pagerdutyNotifier.getDetails(event);

    // Assert
    verify(event).getInstance();
    verify(event).getStatusInfo();
    assertEquals(2, actualDetails.size());
    assertEquals("UNKNOWN", actualDetails.get("from"));
    assertNull(actualDetails.get("to"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PagerdutyNotifier#setClient(String)}
   *   <li>{@link PagerdutyNotifier#setClientUrl(URI)}
   *   <li>{@link PagerdutyNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link PagerdutyNotifier#setServiceKey(String)}
   *   <li>{@link PagerdutyNotifier#setUrl(URI)}
   *   <li>{@link PagerdutyNotifier#getClient()}
   *   <li>{@link PagerdutyNotifier#getClientUrl()}
   *   <li>{@link PagerdutyNotifier#getServiceKey()}
   *   <li>{@link PagerdutyNotifier#getUrl()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    PagerdutyNotifier pagerdutyNotifier = new PagerdutyNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));

    // Act
    pagerdutyNotifier.setClient("Client");
    pagerdutyNotifier.setClientUrl(PagerdutyNotifier.DEFAULT_URI);
    pagerdutyNotifier.setRestTemplate(mock(RestTemplate.class));
    pagerdutyNotifier.setServiceKey("Service Key");
    URI url = PagerdutyNotifier.DEFAULT_URI;
    pagerdutyNotifier.setUrl(url);
    String actualClient = pagerdutyNotifier.getClient();
    URI actualClientUrl = pagerdutyNotifier.getClientUrl();
    String actualServiceKey = pagerdutyNotifier.getServiceKey();
    URI actualUrl = pagerdutyNotifier.getUrl();

    // Assert that nothing has changed
    assertEquals("Client", actualClient);
    assertEquals("Service Key", actualServiceKey);
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualClientUrl.toString());
    assertSame(url, actualClientUrl);
    assertSame(url, actualUrl);
  }

  /**
   * Method under test:
   * {@link PagerdutyNotifier#PagerdutyNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  public void testNewPagerdutyNotifier() {
    // Arrange and Act
    PagerdutyNotifier actualPagerdutyNotifier = new PagerdutyNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals("#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualPagerdutyNotifier.getDescription());
    URI url = actualPagerdutyNotifier.getUrl();
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", url.toString());
    assertNull(actualPagerdutyNotifier.getClient());
    assertNull(actualPagerdutyNotifier.getServiceKey());
    assertNull(actualPagerdutyNotifier.getClientUrl());
    assertTrue(actualPagerdutyNotifier.isEnabled());
    assertSame(actualPagerdutyNotifier.DEFAULT_URI, url);
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualPagerdutyNotifier.getIgnoreChanges());
  }
}
