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
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ContextConfiguration(classes = {OpsGenieNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class OpsGenieNotifierDiffblueTest {
  @MockitoBean private InstanceRepository instanceRepository;

  @Autowired private OpsGenieNotifier opsGenieNotifier;

  @MockitoBean private RestTemplate restTemplate;

  /**
   * Test {@link OpsGenieNotifier#OpsGenieNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link OpsGenieNotifier#OpsGenieNotifier(InstanceRepository,
   * RestTemplate)}
   */
  @Test
  @DisplayName("Test new OpsGenieNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OpsGenieNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewOpsGenieNotifier() {
    // Arrange and Act
    OpsGenieNotifier actualOpsGenieNotifier =
        new OpsGenieNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals(
        "#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        actualOpsGenieNotifier.getMessage());
    assertEquals("https://api.opsgenie.com/v2/alerts", actualOpsGenieNotifier.getUrl().toString());
    assertNull(actualOpsGenieNotifier.getActions());
    assertNull(actualOpsGenieNotifier.getApiKey());
    assertNull(actualOpsGenieNotifier.getEntity());
    assertNull(actualOpsGenieNotifier.getSource());
    assertNull(actualOpsGenieNotifier.getTags());
    assertNull(actualOpsGenieNotifier.getUser());
    assertTrue(actualOpsGenieNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualOpsGenieNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link OpsGenieNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link OpsGenieNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono OpsGenieNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            opsGenieNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link OpsGenieNotifier#buildUrl(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link OpsGenieNotifier#buildUrl(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test buildUrl(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String OpsGenieNotifier.buildUrl(InstanceEvent, Instance)"})
  void testBuildUrl() {
    // Arrange, Act and Assert
    assertEquals(
        "https://api.opsgenie.com/v2/alerts",
        opsGenieNotifier.buildUrl(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
  }

  /**
   * Test {@link OpsGenieNotifier#buildUrl(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#buildUrl(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test buildUrl(InstanceEvent, Instance); given 'Status'; when StatusInfo getStatus() return 'Status'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String OpsGenieNotifier.buildUrl(InstanceEvent, Instance)"})
  void testBuildUrl_givenStatus_whenStatusInfoGetStatusReturnStatus() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    String actualBuildUrlResult =
        opsGenieNotifier.buildUrl(
            new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo),
            mock(Instance.class));

    // Assert
    verify(statusInfo).getStatus();
    assertEquals("https://api.opsgenie.com/v2/alerts", actualBuildUrlResult);
  }

  /**
   * Test {@link OpsGenieNotifier#buildUrl(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code UP}.
   *   <li>Then return {@code https://api.opsgenie.com/v2/alerts/Name_42/close}.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#buildUrl(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test buildUrl(InstanceEvent, Instance); given 'UP'; then return 'https://api.opsgenie.com/v2/alerts/Name_42/close'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String OpsGenieNotifier.buildUrl(InstanceEvent, Instance)"})
  void testBuildUrl_givenUp_thenReturnHttpsApiOpsgenieComV2AlertsName42Close() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");
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
    String actualBuildUrlResult = opsGenieNotifier.buildUrl(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    assertEquals("https://api.opsgenie.com/v2/alerts/Name_42/close", actualBuildUrlResult);
  }

  /**
   * Test {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code Status}.
   *   <li>Then return Body size is four.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createRequest(InstanceEvent, Instance); given StatusInfo getStatus() return 'Status'; then return Body size is four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity OpsGenieNotifier.createRequest(InstanceEvent, Instance)"})
  void testCreateRequest_givenStatusInfoGetStatusReturnStatus_thenReturnBodySizeIsFour() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));
    when(event.getStatusInfo()).thenReturn(statusInfo);

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
    HttpEntity<?> actualCreateRequestResult = opsGenieNotifier.createRequest(event, instance);

    // Assert
    verify(instance, atLeast(1)).getId();
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(event).getInstance();
    verify(event, atLeast(1)).getStatusInfo();
    verify(statusInfo2).getStatus();
    verify(statusInfo, atLeast(1)).getStatus();
    Object body = actualCreateRequestResult.getBody();
    assertEquals(4, ((Map<String, Object>) body).size());
    Object getResult = ((Map<String, Object>) body).get("details");
    assertTrue(getResult instanceof Map);
    assertTrue(body instanceof Map);
    assertEquals(
        "Instance Name (42) went from UNKNOWN to Status",
        ((Map<String, Object>) body).get("description"));
    assertEquals(3, ((Map<String, String>) getResult).size());
    assertEquals("Instance health-endpoint", ((Map<String, String>) getResult).get("text"));
    assertEquals("Name/42 is Status", ((Map<String, Object>) body).get("message"));
    assertEquals("Name_42", ((Map<String, Object>) body).get("alias"));
    assertEquals("https://example.org/example", ((Map<String, String>) getResult).get("href"));
    assertEquals("link", ((Map<String, String>) getResult).get("type"));
  }

  /**
   * Test {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code UP}.
   *   <li>When {@link StatusInfo} {@link StatusInfo#getStatus()} return {@code UP}.
   *   <li>Then return Body Empty.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createRequest(InstanceEvent, Instance); given 'UP'; when StatusInfo getStatus() return 'UP'; then return Body Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity OpsGenieNotifier.createRequest(InstanceEvent, Instance)"})
  void testCreateRequest_givenUp_whenStatusInfoGetStatusReturnUp_thenReturnBodyEmpty() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UP");

    // Act
    HttpEntity<?> actualCreateRequestResult =
        opsGenieNotifier.createRequest(
            new InstanceStatusChangedEvent(InstanceId.of("42"), 1L, statusInfo),
            mock(Instance.class));

    // Assert
    verify(statusInfo).getStatus();
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
   * Test {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body {@code actions} is {@code UP}.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createRequest(InstanceEvent, Instance); then return Body 'actions' is 'UP'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity OpsGenieNotifier.createRequest(InstanceEvent, Instance)"})
  void testCreateRequest_thenReturnBodyActionsIsUp() {
    // Arrange
    OpsGenieNotifier opsGenieNotifier =
        new OpsGenieNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    opsGenieNotifier.setActions("UP");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));
    when(event.getStatusInfo()).thenReturn(statusInfo);

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
    HttpEntity<?> actualCreateRequestResult = opsGenieNotifier.createRequest(event, instance);

    // Assert
    verify(instance, atLeast(1)).getId();
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(event).getInstance();
    verify(event, atLeast(1)).getStatusInfo();
    verify(statusInfo2).getStatus();
    verify(statusInfo, atLeast(1)).getStatus();
    Object body = actualCreateRequestResult.getBody();
    assertEquals(5, ((Map<String, Object>) body).size());
    Object getResult = ((Map<String, Object>) body).get("details");
    assertTrue(getResult instanceof Map);
    assertTrue(body instanceof Map);
    assertEquals(
        "Instance Name (42) went from UNKNOWN to Status",
        ((Map<String, Object>) body).get("description"));
    assertEquals(3, ((Map<String, String>) getResult).size());
    assertEquals("Instance health-endpoint", ((Map<String, String>) getResult).get("text"));
    assertEquals("Name/42 is Status", ((Map<String, Object>) body).get("message"));
    assertEquals("Name_42", ((Map<String, Object>) body).get("alias"));
    assertEquals("UP", ((Map<String, Object>) body).get("actions"));
    assertEquals("https://example.org/example", ((Map<String, String>) getResult).get("href"));
    assertEquals("link", ((Map<String, String>) getResult).get("type"));
  }

  /**
   * Test {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body Empty.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createRequest(InstanceEvent, Instance); then return Body Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity OpsGenieNotifier.createRequest(InstanceEvent, Instance)"})
  void testCreateRequest_thenReturnBodyEmpty() {
    // Arrange and Act
    HttpEntity<?> actualCreateRequestResult =
        opsGenieNotifier.createRequest(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class));

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
   * Test {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body {@code entity} is {@code UP}.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createRequest(InstanceEvent, Instance); then return Body 'entity' is 'UP'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity OpsGenieNotifier.createRequest(InstanceEvent, Instance)"})
  void testCreateRequest_thenReturnBodyEntityIsUp() {
    // Arrange
    OpsGenieNotifier opsGenieNotifier =
        new OpsGenieNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    opsGenieNotifier.setEntity("UP");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));
    when(event.getStatusInfo()).thenReturn(statusInfo);

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
    HttpEntity<?> actualCreateRequestResult = opsGenieNotifier.createRequest(event, instance);

    // Assert
    verify(instance, atLeast(1)).getId();
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(event).getInstance();
    verify(event, atLeast(1)).getStatusInfo();
    verify(statusInfo2).getStatus();
    verify(statusInfo, atLeast(1)).getStatus();
    Object body = actualCreateRequestResult.getBody();
    assertEquals(5, ((Map<String, Object>) body).size());
    Object getResult = ((Map<String, Object>) body).get("details");
    assertTrue(getResult instanceof Map);
    assertTrue(body instanceof Map);
    assertEquals(
        "Instance Name (42) went from UNKNOWN to Status",
        ((Map<String, Object>) body).get("description"));
    assertEquals(3, ((Map<String, String>) getResult).size());
    assertEquals("Instance health-endpoint", ((Map<String, String>) getResult).get("text"));
    assertEquals("Name/42 is Status", ((Map<String, Object>) body).get("message"));
    assertEquals("Name_42", ((Map<String, Object>) body).get("alias"));
    assertEquals("UP", ((Map<String, Object>) body).get("entity"));
    assertEquals("https://example.org/example", ((Map<String, String>) getResult).get("href"));
    assertEquals("link", ((Map<String, String>) getResult).get("type"));
  }

  /**
   * Test {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body {@code source} is {@code Authorization}.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createRequest(InstanceEvent, Instance); then return Body 'source' is 'Authorization'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity OpsGenieNotifier.createRequest(InstanceEvent, Instance)"})
  void testCreateRequest_thenReturnBodySourceIsAuthorization() {
    // Arrange
    OpsGenieNotifier opsGenieNotifier =
        new OpsGenieNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    opsGenieNotifier.setSource("Authorization");

    // Act
    HttpEntity<?> actualCreateRequestResult =
        opsGenieNotifier.createRequest(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class));

    // Assert
    Object body = actualCreateRequestResult.getBody();
    assertTrue(body instanceof Map);
    assertEquals(1, ((Map<String, String>) body).size());
    assertEquals("Authorization", ((Map<String, String>) body).get("source"));
    HttpHeaders headers = actualCreateRequestResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.AUTHORIZATION);
    assertEquals(1, getResult.size());
    assertEquals("GenieKey null", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertTrue(actualCreateRequestResult.hasBody());
  }

  /**
   * Test {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body {@code tags} is {@code UP}.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test createRequest(InstanceEvent, Instance); then return Body 'tags' is 'UP'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity OpsGenieNotifier.createRequest(InstanceEvent, Instance)"})
  void testCreateRequest_thenReturnBodyTagsIsUp() {
    // Arrange
    OpsGenieNotifier opsGenieNotifier =
        new OpsGenieNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    opsGenieNotifier.setTags("UP");

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    InstanceStatusChangedEvent event = mock(InstanceStatusChangedEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));
    when(event.getStatusInfo()).thenReturn(statusInfo);

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
    HttpEntity<?> actualCreateRequestResult = opsGenieNotifier.createRequest(event, instance);

    // Assert
    verify(instance, atLeast(1)).getId();
    verify(instance, atLeast(1)).getRegistration();
    verify(instance).getStatusInfo();
    verify(event).getInstance();
    verify(event, atLeast(1)).getStatusInfo();
    verify(statusInfo2).getStatus();
    verify(statusInfo, atLeast(1)).getStatus();
    Object body = actualCreateRequestResult.getBody();
    assertEquals(5, ((Map<String, Object>) body).size());
    Object getResult = ((Map<String, Object>) body).get("details");
    assertTrue(getResult instanceof Map);
    assertTrue(body instanceof Map);
    assertEquals(
        "Instance Name (42) went from UNKNOWN to Status",
        ((Map<String, Object>) body).get("description"));
    assertEquals(3, ((Map<String, String>) getResult).size());
    assertEquals("Instance health-endpoint", ((Map<String, String>) getResult).get("text"));
    assertEquals("Name/42 is Status", ((Map<String, Object>) body).get("message"));
    assertEquals("Name_42", ((Map<String, Object>) body).get("alias"));
    assertEquals("UP", ((Map<String, Object>) body).get("tags"));
    assertEquals("https://example.org/example", ((Map<String, String>) getResult).get("href"));
    assertEquals("link", ((Map<String, String>) getResult).get("type"));
  }

  /**
   * Test {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Then return Body {@code user} is {@code Authorization}.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#createRequest(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createRequest(InstanceEvent, Instance); then return Body 'user' is 'Authorization'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"HttpEntity OpsGenieNotifier.createRequest(InstanceEvent, Instance)"})
  void testCreateRequest_thenReturnBodyUserIsAuthorization() {
    // Arrange
    OpsGenieNotifier opsGenieNotifier =
        new OpsGenieNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    opsGenieNotifier.setUser("Authorization");

    // Act
    HttpEntity<?> actualCreateRequestResult =
        opsGenieNotifier.createRequest(
            new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class));

    // Assert
    Object body = actualCreateRequestResult.getBody();
    assertTrue(body instanceof Map);
    assertEquals(1, ((Map<String, String>) body).size());
    assertEquals("Authorization", ((Map<String, String>) body).get("user"));
    HttpHeaders headers = actualCreateRequestResult.getHeaders();
    assertEquals(2, headers.size());
    List<String> getResult = headers.get(HttpHeaders.AUTHORIZATION);
    assertEquals(1, getResult.size());
    assertEquals("GenieKey null", getResult.get(0));
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertTrue(actualCreateRequestResult.hasBody());
  }

  /**
   * Test {@link OpsGenieNotifier#generateAlias(Instance)}.
   *
   * <p>Method under test: {@link OpsGenieNotifier#generateAlias(Instance)}
   */
  @Test
  @DisplayName("Test generateAlias(Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String OpsGenieNotifier.generateAlias(Instance)"})
  void testGenerateAlias() {
    // Arrange
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
    String actualGenerateAliasResult = opsGenieNotifier.generateAlias(instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    assertEquals("Name_42", actualGenerateAliasResult);
  }

  /**
   * Test {@link OpsGenieNotifier#getMessage()}.
   *
   * <p>Method under test: {@link OpsGenieNotifier#getMessage()}
   */
  @Test
  @DisplayName("Test getMessage()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String OpsGenieNotifier.getMessage()"})
  void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals(
        "#{instance.registration.name}/#{instance.id} is #{instance.statusInfo.status}",
        opsGenieNotifier.getMessage());
  }

  /**
   * Test {@link OpsGenieNotifier#getMessage(InstanceEvent, Instance)} with {@code InstanceEvent},
   * {@code Instance}.
   *
   * <p>Method under test: {@link OpsGenieNotifier#getMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test getMessage(InstanceEvent, Instance) with 'InstanceEvent', 'Instance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String OpsGenieNotifier.getMessage(InstanceEvent, Instance)"})
  void testGetMessageWithInstanceEventInstance() {
    // Arrange
    OpsGenieNotifier opsGenieNotifier =
        new OpsGenieNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));
    opsGenieNotifier.setDescription("The characteristics of someone or something");

    // Act and Assert
    assertEquals(
        "The characteristics of someone or something",
        opsGenieNotifier.getMessage(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
  }

  /**
   * Test {@link OpsGenieNotifier#getDescription(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>Then return {@code Instance Name (42) went from UNKNOWN to Status}.
   * </ul>
   *
   * <p>Method under test: {@link OpsGenieNotifier#getDescription(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test getDescription(InstanceEvent, Instance); given 'Status'; then return 'Instance Name (42) went from UNKNOWN to Status'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String OpsGenieNotifier.getDescription(InstanceEvent, Instance)"})
  void testGetDescription_givenStatus_thenReturnInstanceName42WentFromUnknownToStatus() {
    // Arrange
    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");
    InstanceStatusChangedEvent event =
        new InstanceStatusChangedEvent(InstanceId.of("42"), 4L, statusInfo);

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
    String actualDescription = opsGenieNotifier.getDescription(event, instance);

    // Assert
    verify(instance, atLeast(1)).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    assertEquals("Instance Name (42) went from UNKNOWN to Status", actualDescription);
  }

  /**
   * Test {@link OpsGenieNotifier#setDescription(String)}.
   *
   * <p>Method under test: {@link OpsGenieNotifier#setDescription(String)}
   */
  @Test
  @DisplayName("Test setDescription(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OpsGenieNotifier.setDescription(String)"})
  void testSetDescription() {
    // Arrange and Act
    opsGenieNotifier.setDescription("The characteristics of someone or something");

    // Assert
    assertEquals("The characteristics of someone or something", opsGenieNotifier.getMessage());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String OpsGenieNotifier.getActions()",
    "String OpsGenieNotifier.getApiKey()",
    "String OpsGenieNotifier.getEntity()",
    "String OpsGenieNotifier.getSource()",
    "String OpsGenieNotifier.getTags()",
    "URI OpsGenieNotifier.getUrl()",
    "String OpsGenieNotifier.getUser()",
    "void OpsGenieNotifier.setActions(String)",
    "void OpsGenieNotifier.setApiKey(String)",
    "void OpsGenieNotifier.setEntity(String)",
    "void OpsGenieNotifier.setRestTemplate(RestTemplate)",
    "void OpsGenieNotifier.setSource(String)",
    "void OpsGenieNotifier.setTags(String)",
    "void OpsGenieNotifier.setUrl(URI)",
    "void OpsGenieNotifier.setUser(String)"
  })
  void testGettersAndSetters() {
    // Arrange
    OpsGenieNotifier opsGenieNotifier =
        new OpsGenieNotifier(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(RestTemplate.class));

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
    assertEquals(
        "https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualUrl.toString());
    assertSame(url, actualUrl);
  }
}
