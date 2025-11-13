package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {PagerdutyNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class PagerdutyNotifierDiffblueTest {
  @MockitoBean private InstanceRepository instanceRepository;

  @Autowired private PagerdutyNotifier pagerdutyNotifier;

  @MockitoBean private RestTemplate restTemplate;

  /**
   * Test {@link PagerdutyNotifier#PagerdutyNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link PagerdutyNotifier#PagerdutyNotifier(InstanceRepository,
   * RestTemplate)}
   */
  @Test
  @DisplayName("Test new PagerdutyNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void PagerdutyNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewPagerdutyNotifier() {
    // Arrange and Act
    PagerdutyNotifier actualPagerdutyNotifier =
        new PagerdutyNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals(
        "#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualPagerdutyNotifier.getDescription());
    URI url = actualPagerdutyNotifier.getUrl();
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json", url.toString());
    assertNull(actualPagerdutyNotifier.getClient());
    assertNull(actualPagerdutyNotifier.getServiceKey());
    assertNull(actualPagerdutyNotifier.getClientUrl());
    assertTrue(actualPagerdutyNotifier.isEnabled());
    assertSame(PagerdutyNotifier.DEFAULT_URI, url);
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualPagerdutyNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link PagerdutyNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link PagerdutyNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono PagerdutyNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            pagerdutyNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>Then return size is six.
   * </ul>
   *
   * <p>Method under test: {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createPagerdutyEvent(InstanceEvent, Instance); given 'Status'; then return size is six")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map PagerdutyNotifier.createPagerdutyEvent(InstanceEvent, Instance)"})
  void testCreatePagerdutyEvent_givenStatus_thenReturnSizeIsSix() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");
    InstanceStatusChangedEvent event =
        new InstanceStatusChangedEvent(InstanceId.of("42"), 2L, statusInfo);

    StatusInfo statusInfo2 = mock(StatusInfo.class);
    when(statusInfo2.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    when(instance.getStatusInfo()).thenReturn(statusInfo2);
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
    Map<String, Object> actualCreatePagerdutyEventResult =
        pagerdutyNotifier.createPagerdutyEvent(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
    verify(statusInfo2).getStatus();
    assertEquals(6, actualCreatePagerdutyEventResult.size());
    Object getResult = actualCreatePagerdutyEventResult.get("contexts");
    assertTrue(getResult instanceof List);
    assertEquals(1, ((List<HashMap>) getResult).size());
    HashMap getResult2 = ((List<HashMap>) getResult).get(0);
    assertEquals(3, getResult2.size());
    assertEquals("Application health-endpoint", getResult2.get("text"));
    assertEquals("https://example.org/example", getResult2.get("href"));
    assertEquals("link", getResult2.get("type"));
    assertEquals("trigger", actualCreatePagerdutyEventResult.get("event_type"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("description"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("details"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("incident_key"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("service_key"));
  }

  /**
   * Test {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code UP}.
   *   <li>Then return size is five.
   * </ul>
   *
   * <p>Method under test: {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createPagerdutyEvent(InstanceEvent, Instance); given 'UP'; then return size is five")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map PagerdutyNotifier.createPagerdutyEvent(InstanceEvent, Instance)"})
  void testCreatePagerdutyEvent_givenUp_thenReturnSizeIsFive() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");
    InstanceStatusChangedEvent event =
        new InstanceStatusChangedEvent(InstanceId.of("42"), 2L, statusInfo);

    StatusInfo statusInfo2 = mock(StatusInfo.class);
    when(statusInfo2.getStatus()).thenReturn("Status");

    Instance instance = mock(Instance.class);
    when(instance.getId()).thenReturn(InstanceId.of("42"));
    when(instance.getStatusInfo()).thenReturn(statusInfo2);
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
    Map<String, Object> actualCreatePagerdutyEventResult =
        pagerdutyNotifier.createPagerdutyEvent(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
    verify(statusInfo2).getStatus();
    assertEquals(5, actualCreatePagerdutyEventResult.size());
    Object getResult = actualCreatePagerdutyEventResult.get("details");
    assertTrue(getResult instanceof Map);
    assertEquals(2, ((Map<String, Object>) getResult).size());
    assertEquals("UNKNOWN", ((Map<String, Object>) getResult).get("from"));
    assertEquals("resolve", actualCreatePagerdutyEventResult.get("event_type"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("description"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("incident_key"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("service_key"));
    assertTrue(((Map<String, Object>) getResult).containsKey("to"));
  }

  /**
   * Test {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return size is eight.
   * </ul>
   *
   * <p>Method under test: {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createPagerdutyEvent(InstanceEvent, Instance); then return size is eight")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map PagerdutyNotifier.createPagerdutyEvent(InstanceEvent, Instance)"})
  void testCreatePagerdutyEvent_thenReturnSizeIsEight() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    PagerdutyNotifier pagerdutyNotifier =
        new PagerdutyNotifier(repository, mock(RestTemplate.class));
    pagerdutyNotifier.setClientUrl(PagerdutyNotifier.DEFAULT_URI);
    pagerdutyNotifier.setClient("service_key");
    pagerdutyNotifier.setDescription("The characteristics of someone or something");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));
    when(event.getStatusInfo()).thenReturn(statusInfo);

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

    // Act
    Map<String, Object> actualCreatePagerdutyEventResult =
        pagerdutyNotifier.createPagerdutyEvent(event, instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    verify(event, atLeast(1)).getInstance();
    verify(event, atLeast(1)).getStatusInfo();
    verify(statusInfo).getStatus();
    assertEquals(8, actualCreatePagerdutyEventResult.size());
    Object getResult = actualCreatePagerdutyEventResult.get("client_url");
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json", getResult.toString());
    assertEquals("service_key", actualCreatePagerdutyEventResult.get("client"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("contexts"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("description"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("details"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("event_type"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("incident_key"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("service_key"));
    assertSame(PagerdutyNotifier.DEFAULT_URI, getResult);
  }

  /**
   * Test {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return size is four.
   * </ul>
   *
   * <p>Method under test: {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createPagerdutyEvent(InstanceEvent, Instance); then return size is four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map PagerdutyNotifier.createPagerdutyEvent(InstanceEvent, Instance)"})
  void testCreatePagerdutyEvent_thenReturnSizeIsFour() {
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

    // Act
    Map<String, Object> actualCreatePagerdutyEventResult =
        pagerdutyNotifier.createPagerdutyEvent(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(statusInfo).getStatus();
    assertEquals(4, actualCreatePagerdutyEventResult.size());
    Object getResult = actualCreatePagerdutyEventResult.get("details");
    assertTrue(getResult instanceof Map);
    assertEquals("Name/42 is Status", actualCreatePagerdutyEventResult.get("description"));
    assertEquals("Name/42", actualCreatePagerdutyEventResult.get("incident_key"));
    assertNull(actualCreatePagerdutyEventResult.get("service_key"));
    assertTrue(((Map<Object, Object>) getResult).isEmpty());
  }

  /**
   * Test {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return size is seven.
   * </ul>
   *
   * <p>Method under test: {@link PagerdutyNotifier#createPagerdutyEvent(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createPagerdutyEvent(InstanceEvent, Instance); then return size is seven")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map PagerdutyNotifier.createPagerdutyEvent(InstanceEvent, Instance)"})
  void testCreatePagerdutyEvent_thenReturnSizeIsSeven() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    PagerdutyNotifier pagerdutyNotifier =
        new PagerdutyNotifier(repository, mock(RestTemplate.class));
    pagerdutyNotifier.setClient("service_key");
    pagerdutyNotifier.setDescription("The characteristics of someone or something");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));
    when(event.getStatusInfo()).thenReturn(statusInfo);

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

    // Act
    Map<String, Object> actualCreatePagerdutyEventResult =
        pagerdutyNotifier.createPagerdutyEvent(event, instance);

    // Assert
    verify(instance, atLeast(1)).getRegistration();
    verify(event, atLeast(1)).getInstance();
    verify(event, atLeast(1)).getStatusInfo();
    verify(statusInfo).getStatus();
    assertEquals(7, actualCreatePagerdutyEventResult.size());
    Object getResult = actualCreatePagerdutyEventResult.get("contexts");
    assertTrue(getResult instanceof List);
    assertEquals(1, ((List<HashMap>) getResult).size());
    HashMap getResult2 = ((List<HashMap>) getResult).get(0);
    assertEquals(3, getResult2.size());
    assertEquals("Application health-endpoint", getResult2.get("text"));
    assertEquals("https://example.org/example", getResult2.get("href"));
    assertEquals("link", getResult2.get("type"));
    assertEquals("service_key", actualCreatePagerdutyEventResult.get("client"));
    assertEquals("trigger", actualCreatePagerdutyEventResult.get("event_type"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("description"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("details"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("incident_key"));
    assertTrue(actualCreatePagerdutyEventResult.containsKey("service_key"));
  }

  /**
   * Test {@link PagerdutyNotifier#getDescription()}.
   *
   * <p>Method under test: {@link PagerdutyNotifier#getDescription()}
   */
  @Test
  @DisplayName("Test getDescription()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String PagerdutyNotifier.getDescription()"})
  void testGetDescription() {
    // Arrange, Act and Assert
    assertEquals(
        "#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        pagerdutyNotifier.getDescription());
  }

  /**
   * Test {@link PagerdutyNotifier#getDescription(InstanceEvent, Instance)} with {@code
   * InstanceEvent}, {@code Instance}.
   *
   * <p>Method under test: {@link PagerdutyNotifier#getDescription(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getDescription(InstanceEvent, Instance) with 'InstanceEvent', 'Instance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String PagerdutyNotifier.getDescription(InstanceEvent, Instance)"})
  void testGetDescriptionWithInstanceEventInstance() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    PagerdutyNotifier pagerdutyNotifier =
        new PagerdutyNotifier(repository, mock(RestTemplate.class));
    pagerdutyNotifier.setDescription("The characteristics of someone or something");

    // Act and Assert
    assertEquals(
        "The characteristics of someone or something",
        pagerdutyNotifier.getDescription(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
  }

  /**
   * Test {@link PagerdutyNotifier#setDescription(String)}.
   *
   * <p>Method under test: {@link PagerdutyNotifier#setDescription(String)}
   */
  @Test
  @DisplayName("Test setDescription(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void PagerdutyNotifier.setDescription(String)"})
  void testSetDescription() {
    // Arrange and Act
    pagerdutyNotifier.setDescription("The characteristics of someone or something");

    // Assert
    assertEquals("The characteristics of someone or something", pagerdutyNotifier.getDescription());
  }

  /**
   * Test {@link PagerdutyNotifier#getDetails(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link PagerdutyNotifier#getDetails(InstanceEvent)}
   */
  @Test
  @DisplayName("Test getDetails(InstanceEvent); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map PagerdutyNotifier.getDetails(InstanceEvent)"})
  void testGetDetails_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        pagerdutyNotifier
            .getDetails(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L))
            .isEmpty());
  }

  /**
   * Test {@link PagerdutyNotifier#getDetails(InstanceEvent)}.
   *
   * <ul>
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link PagerdutyNotifier#getDetails(InstanceEvent)}
   */
  @Test
  @DisplayName("Test getDetails(InstanceEvent); then return size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map PagerdutyNotifier.getDetails(InstanceEvent)"})
  void testGetDetails_thenReturnSizeIsTwo() {
    // Arrange and Act
    Map<String, Object> actualDetails =
        pagerdutyNotifier.getDetails(
            new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, mock(StatusInfo.class)));

    // Assert
    assertEquals(2, actualDetails.size());
    assertEquals("UNKNOWN", actualDetails.get("from"));
    assertTrue(actualDetails.containsKey("to"));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String PagerdutyNotifier.getClient()",
    "URI PagerdutyNotifier.getClientUrl()",
    "String PagerdutyNotifier.getServiceKey()",
    "URI PagerdutyNotifier.getUrl()",
    "void PagerdutyNotifier.setClient(String)",
    "void PagerdutyNotifier.setClientUrl(URI)",
    "void PagerdutyNotifier.setRestTemplate(RestTemplate)",
    "void PagerdutyNotifier.setServiceKey(String)",
    "void PagerdutyNotifier.setUrl(URI)"
  })
  void testGettersAndSetters() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    PagerdutyNotifier pagerdutyNotifier =
        new PagerdutyNotifier(repository, mock(RestTemplate.class));

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

    // Assert
    assertEquals("Client", actualClient);
    assertEquals("Service Key", actualServiceKey);
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json",
        actualClientUrl.toString());
    assertSame(url, actualClientUrl);
    assertSame(url, pagerdutyNotifier.getUrl());
  }
}
