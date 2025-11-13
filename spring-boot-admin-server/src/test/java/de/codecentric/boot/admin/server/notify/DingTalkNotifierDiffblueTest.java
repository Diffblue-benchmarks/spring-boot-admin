package de.codecentric.boot.admin.server.notify;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

@ContextConfiguration(classes = {DingTalkNotifier.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class DingTalkNotifierDiffblueTest {
  @Autowired private DingTalkNotifier dingTalkNotifier;

  @MockitoBean private InstanceRepository instanceRepository;

  @MockitoBean private RestTemplate restTemplate;

  /**
   * Test {@link DingTalkNotifier#DingTalkNotifier(InstanceRepository, RestTemplate)}.
   *
   * <p>Method under test: {@link DingTalkNotifier#DingTalkNotifier(InstanceRepository,
   * RestTemplate)}
   */
  @Test
  @DisplayName("Test new DingTalkNotifier(InstanceRepository, RestTemplate)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DingTalkNotifier.<init>(InstanceRepository, RestTemplate)"})
  void testNewDingTalkNotifier() {
    // Arrange and Act
    DingTalkNotifier actualDingTalkNotifier =
        new DingTalkNotifier(instanceRepository, mock(RestTemplate.class));

    // Assert
    assertEquals(
        "#{instance.registration.name} #{instance.id} is #{event.statusInfo.status}",
        actualDingTalkNotifier.getMessage());
    assertNull(actualDingTalkNotifier.getSecret());
    assertNull(actualDingTalkNotifier.getWebhookUrl());
    assertTrue(actualDingTalkNotifier.isEnabled());
    assertArrayEquals(new String[] {"UNKNOWN:UP"}, actualDingTalkNotifier.getIgnoreChanges());
  }

  /**
   * Test {@link DingTalkNotifier#doNotify(InstanceEvent, Instance)}.
   *
   * <p>Method under test: {@link DingTalkNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName("Test doNotify(InstanceEvent, Instance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "reactor.core.publisher.Mono DingTalkNotifier.doNotify(InstanceEvent, Instance)"
  })
  void testDoNotify() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Void> createResult =
        StepVerifier.create(
            dingTalkNotifier.doNotify(
                new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), mock(Instance.class)));
    createResult.expectError().verify();
  }

  /**
   * Test {@link DingTalkNotifier#createMessage(InstanceEvent, Instance)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>Then return Body size is two.
   * </ul>
   *
   * <p>Method under test: {@link DingTalkNotifier#createMessage(InstanceEvent, Instance)}
   */
  @Test
  @DisplayName(
      "Test createMessage(InstanceEvent, Instance); given 'Status'; then return Body size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DingTalkNotifier.createMessage(InstanceEvent, Instance)"})
  void testCreateMessage_givenStatus_thenReturnBodySizeIsTwo() {
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
    Object actualCreateMessageResult = dingTalkNotifier.createMessage(event, instance);

    // Assert
    verify(instance).getId();
    verify(instance).getRegistration();
    verify(statusInfo).getStatus();
    Object body = ((HttpEntity<Object>) actualCreateMessageResult).getBody();
    assertEquals(2, ((Map<String, Object>) body).size());
    Object getResult = ((Map<String, Object>) body).get("text");
    assertTrue(getResult instanceof Map);
    assertTrue(body instanceof Map);
    assertTrue(actualCreateMessageResult instanceof HttpEntity);
    assertEquals(1, ((Map<String, String>) getResult).size());
    assertEquals("Name 42 is Status", ((Map<String, String>) getResult).get("content"));
    HttpHeaders headers = ((HttpEntity<Object>) actualCreateMessageResult).getHeaders();
    assertEquals(1, headers.size());
    List<String> getResult2 = headers.get(HttpHeaders.CONTENT_TYPE);
    assertEquals(1, getResult2.size());
    assertEquals("application/json", getResult2.get(0));
    assertEquals("text", ((Map<String, Object>) body).get("msgtype"));
    assertTrue(((HttpEntity<Object>) actualCreateMessageResult).hasBody());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DingTalkNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link DingTalkNotifier#setSecret(String)}
   *   <li>{@link DingTalkNotifier#setWebhookUrl(String)}
   *   <li>{@link DingTalkNotifier#getSecret()}
   *   <li>{@link DingTalkNotifier#getWebhookUrl()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String DingTalkNotifier.getSecret()",
    "String DingTalkNotifier.getWebhookUrl()",
    "void DingTalkNotifier.setRestTemplate(RestTemplate)",
    "void DingTalkNotifier.setSecret(String)",
    "void DingTalkNotifier.setWebhookUrl(String)"
  })
  void testGettersAndSetters() {
    // Arrange
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    DingTalkNotifier dingTalkNotifier = new DingTalkNotifier(repository, mock(RestTemplate.class));

    // Act
    dingTalkNotifier.setRestTemplate(mock(RestTemplate.class));
    dingTalkNotifier.setSecret("Secret");
    dingTalkNotifier.setWebhookUrl("https://example.org/example");
    String actualSecret = dingTalkNotifier.getSecret();

    // Assert
    assertEquals("Secret", actualSecret);
    assertEquals("https://example.org/example", dingTalkNotifier.getWebhookUrl());
  }

  /**
   * Test {@link DingTalkNotifier#getMessage()}.
   *
   * <p>Method under test: {@link DingTalkNotifier#getMessage()}
   */
  @Test
  @DisplayName("Test getMessage()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DingTalkNotifier.getMessage()"})
  void testGetMessage() {
    // Arrange, Act and Assert
    assertEquals(
        "#{instance.registration.name} #{instance.id} is #{event.statusInfo.status}",
        dingTalkNotifier.getMessage());
  }

  /**
   * Test {@link DingTalkNotifier#setMessage(String)}.
   *
   * <p>Method under test: {@link DingTalkNotifier#setMessage(String)}
   */
  @Test
  @DisplayName("Test setMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DingTalkNotifier.setMessage(String)"})
  void testSetMessage() {
    // Arrange and Act
    dingTalkNotifier.setMessage("Not all who wander are lost");

    // Assert
    assertEquals("Not all who wander are lost", dingTalkNotifier.getMessage());
  }
}
