package de.codecentric.boot.admin.server.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEndpointsDetectedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceInfoChangedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegistrationUpdatedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.Info;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InstanceDiffblueTest {
  /**
   * Test {@link Instance#create(InstanceId)}.
   *
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code 42}.
   *   <li>Then return StatusInfo Status is {@code UNKNOWN}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#create(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test create(InstanceId); when InstanceId with value is '42'; then return StatusInfo Status is 'UNKNOWN'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.create(InstanceId)"})
  void testCreate_whenInstanceIdWithValueIs42_thenReturnStatusInfoStatusIsUnknown() {
    // Arrange
    InstanceId id = InstanceId.of("42");

    // Act
    Instance actualCreateResult = Instance.create(id);

    // Assert
    StatusInfo statusInfo = actualCreateResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertNull(actualCreateResult.getBuildVersion());
    assertEquals(-1L, actualCreateResult.getVersion());
    Instant statusTimestamp = actualCreateResult.getStatusTimestamp();
    assertEquals(0, statusTimestamp.getNano());
    assertEquals(0L, statusTimestamp.getEpochSecond());
    assertFalse(actualCreateResult.isRegistered());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(actualCreateResult.getUnsavedEvents().isEmpty());
    Map<String, Object> values = actualCreateResult.getInfo().getValues();
    assertTrue(values.isEmpty());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(values, actualCreateResult.getTags().getValues());
    assertSame(id, actualCreateResult.getId());
  }

  /**
   * Test {@link Instance#register(Registration)}.
   *
   * <p>Method under test: {@link Instance#register(Registration)}
   */
  @Test
  @DisplayName("Test register(Registration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.register(Registration)"})
  void testRegister() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act
    Instance actualRegisterResult = createResult.register(registration);

    // Assert
    List<InstanceEvent> unsavedEvents = actualRegisterResult.getUnsavedEvents();
    assertEquals(1, unsavedEvents.size());
    InstanceEvent getResult = unsavedEvents.get(0);
    assertTrue(getResult instanceof InstanceRegisteredEvent);
    assertSame(registration, actualRegisterResult.getRegistration());
    assertSame(registration, ((InstanceRegisteredEvent) getResult).getRegistration());
  }

  /**
   * Test {@link Instance#register(Registration)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@code 42}.
   *   <li>Then return {@link Registration}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#register(Registration)}
   */
  @Test
  @DisplayName(
      "Test register(Registration); given HashMap() 'Key' is '42'; then return Registration")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.register(Registration)"})
  void testRegister_givenHashMapKeyIs42_thenReturnRegistration() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("Key", "42");

    Registration registration = mock(Registration.class);
    when(registration.getHealthUrl()).thenReturn("https://example.org/example");
    when(registration.getMetadata()).thenReturn(stringStringMap);

    // Act
    Instance actualRegisterResult = createResult.register(registration);

    // Assert
    verify(registration).getHealthUrl();
    verify(registration, atLeast(1)).getMetadata();
    List<InstanceEvent> unsavedEvents = actualRegisterResult.getUnsavedEvents();
    assertEquals(1, unsavedEvents.size());
    InstanceEvent getResult = unsavedEvents.get(0);
    assertTrue(getResult instanceof InstanceRegisteredEvent);
    assertSame(registration, actualRegisterResult.getRegistration());
    assertSame(registration, ((InstanceRegisteredEvent) getResult).getRegistration());
  }

  /**
   * Test {@link Instance#register(Registration)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code null} is {@code 42}.
   *   <li>Then return {@link Registration}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#register(Registration)}
   */
  @Test
  @DisplayName(
      "Test register(Registration); given HashMap() 'null' is '42'; then return Registration")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.register(Registration)"})
  void testRegister_givenHashMapNullIs42_thenReturnRegistration() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("REGISTERED", "'registration' must not be null");
    stringStringMap.put(null, "42");

    Registration registration = mock(Registration.class);
    when(registration.getHealthUrl()).thenReturn("https://example.org/example");
    when(registration.getMetadata()).thenReturn(stringStringMap);

    // Act
    Instance actualRegisterResult = createResult.register(registration);

    // Assert
    verify(registration).getHealthUrl();
    verify(registration, atLeast(1)).getMetadata();
    List<InstanceEvent> unsavedEvents = actualRegisterResult.getUnsavedEvents();
    assertEquals(1, unsavedEvents.size());
    InstanceEvent getResult = unsavedEvents.get(0);
    assertTrue(getResult instanceof InstanceRegisteredEvent);
    assertSame(registration, actualRegisterResult.getRegistration());
    assertSame(registration, ((InstanceRegisteredEvent) getResult).getRegistration());
  }

  /**
   * Test {@link Instance#register(Registration)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code REGISTERED} is {@code 'registration' must not be
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#register(Registration)}
   */
  @Test
  @DisplayName(
      "Test register(Registration); given HashMap() 'REGISTERED' is ''registration' must not be null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.register(Registration)"})
  void testRegister_givenHashMapRegisteredIsRegistrationMustNotBeNull() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("REGISTERED", "'registration' must not be null");
    stringStringMap.put("Key", "42");

    Registration registration = mock(Registration.class);
    when(registration.getHealthUrl()).thenReturn("https://example.org/example");
    when(registration.getMetadata()).thenReturn(stringStringMap);

    // Act
    Instance actualRegisterResult = createResult.register(registration);

    // Assert
    verify(registration).getHealthUrl();
    verify(registration, atLeast(1)).getMetadata();
    List<InstanceEvent> unsavedEvents = actualRegisterResult.getUnsavedEvents();
    assertEquals(1, unsavedEvents.size());
    InstanceEvent getResult = unsavedEvents.get(0);
    assertTrue(getResult instanceof InstanceRegisteredEvent);
    assertSame(registration, actualRegisterResult.getRegistration());
    assertSame(registration, ((InstanceRegisteredEvent) getResult).getRegistration());
  }

  /**
   * Test {@link Instance#register(Registration)}.
   *
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException()}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#register(Registration)}
   */
  @Test
  @DisplayName(
      "Test register(Registration); given IllegalStateException(); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.register(Registration)"})
  void testRegister_givenIllegalStateException_thenThrowIllegalStateException() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    Registration registration = mock(Registration.class);
    when(registration.getMetadata()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> createResult.register(registration));
    verify(registration).getMetadata();
  }

  /**
   * Test {@link Instance#deregister()}.
   *
   * <p>Method under test: {@link Instance#deregister()}
   */
  @Test
  @DisplayName("Test deregister()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.deregister()"})
  void testDeregister() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act
    Instance actualDeregisterResult = createResult.deregister();

    // Assert
    assertSame(createResult, actualDeregisterResult);
  }

  /**
   * Test {@link Instance#withInfo(Info)}.
   *
   * <ul>
   *   <li>When empty.
   *   <li>Then return create {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#withInfo(Info)}
   */
  @Test
  @DisplayName("Test withInfo(Info); when empty; then return create InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.withInfo(Info)"})
  void testWithInfo_whenEmpty_thenReturnCreateInstanceIdWithValueIs42() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act
    Instance actualWithInfoResult = createResult.withInfo(Info.empty());

    // Assert
    assertSame(createResult, actualWithInfoResult);
  }

  /**
   * Test {@link Instance#withStatusInfo(StatusInfo)}.
   *
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException()}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#withStatusInfo(StatusInfo)}
   */
  @Test
  @DisplayName(
      "Test withStatusInfo(StatusInfo); given IllegalStateException(); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.withStatusInfo(StatusInfo)"})
  void testWithStatusInfo_givenIllegalStateException_thenThrowIllegalStateException() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> createResult.withStatusInfo(statusInfo));
    verify(statusInfo).getStatus();
  }

  /**
   * Test {@link Instance#withStatusInfo(StatusInfo)}.
   *
   * <ul>
   *   <li>Given {@code Status}.
   *   <li>Then return UnsavedEvents size is one.
   * </ul>
   *
   * <p>Method under test: {@link Instance#withStatusInfo(StatusInfo)}
   */
  @Test
  @DisplayName(
      "Test withStatusInfo(StatusInfo); given 'Status'; then return UnsavedEvents size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.withStatusInfo(StatusInfo)"})
  void testWithStatusInfo_givenStatus_thenReturnUnsavedEventsSizeIsOne() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("Status");

    // Act
    Instance actualWithStatusInfoResult = createResult.withStatusInfo(statusInfo);

    // Assert
    verify(statusInfo).getStatus();
    List<InstanceEvent> unsavedEvents = actualWithStatusInfoResult.getUnsavedEvents();
    assertEquals(1, unsavedEvents.size());
    InstanceEvent getResult = unsavedEvents.get(0);
    assertTrue(getResult instanceof InstanceStatusChangedEvent);
    assertEquals("STATUS_CHANGED", getResult.getType());
    assertEquals(0L, actualWithStatusInfoResult.getVersion());
    assertEquals(0L, getResult.getVersion());
    assertSame(id, getResult.getInstance());
    assertSame(statusInfo, actualWithStatusInfoResult.getStatusInfo());
    assertSame(statusInfo, ((InstanceStatusChangedEvent) getResult).getStatusInfo());
  }

  /**
   * Test {@link Instance#withStatusInfo(StatusInfo)}.
   *
   * <ul>
   *   <li>Given {@code UNKNOWN}.
   *   <li>Then return create {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#withStatusInfo(StatusInfo)}
   */
  @Test
  @DisplayName(
      "Test withStatusInfo(StatusInfo); given 'UNKNOWN'; then return create InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.withStatusInfo(StatusInfo)"})
  void testWithStatusInfo_givenUnknown_thenReturnCreateInstanceIdWithValueIs42() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    StatusInfo statusInfo = mock(StatusInfo.class);
    when(statusInfo.getStatus()).thenReturn("UNKNOWN");

    // Act
    Instance actualWithStatusInfoResult = createResult.withStatusInfo(statusInfo);

    // Assert
    verify(statusInfo).getStatus();
    assertSame(createResult, actualWithStatusInfoResult);
  }

  /**
   * Test {@link Instance#withEndpoints(Endpoints)}.
   *
   * <ul>
   *   <li>Then return UnsavedEvents size is one.
   * </ul>
   *
   * <p>Method under test: {@link Instance#withEndpoints(Endpoints)}
   */
  @Test
  @DisplayName("Test withEndpoints(Endpoints); then return UnsavedEvents size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.withEndpoints(Endpoints)"})
  void testWithEndpoints_thenReturnUnsavedEventsSizeIsOne() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);
    Endpoints endpoints = Endpoints.single("42", "https://example.org/example");

    // Act
    Instance actualWithEndpointsResult = createResult.withEndpoints(endpoints);

    // Assert
    List<InstanceEvent> unsavedEvents = actualWithEndpointsResult.getUnsavedEvents();
    assertEquals(1, unsavedEvents.size());
    InstanceEvent getResult = unsavedEvents.get(0);
    assertTrue(getResult instanceof InstanceEndpointsDetectedEvent);
    assertEquals("ENDPOINTS_DETECTED", getResult.getType());
    assertEquals(0L, actualWithEndpointsResult.getVersion());
    assertEquals(0L, getResult.getVersion());
    assertSame(endpoints, actualWithEndpointsResult.getEndpoints());
    assertSame(endpoints, ((InstanceEndpointsDetectedEvent) getResult).getEndpoints());
    assertSame(id, getResult.getInstance());
  }

  /**
   * Test {@link Instance#withEndpoints(Endpoints)}.
   *
   * <ul>
   *   <li>When empty.
   *   <li>Then return create {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#withEndpoints(Endpoints)}
   */
  @Test
  @DisplayName(
      "Test withEndpoints(Endpoints); when empty; then return create InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.withEndpoints(Endpoints)"})
  void testWithEndpoints_whenEmpty_thenReturnCreateInstanceIdWithValueIs42() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act
    Instance actualWithEndpointsResult = createResult.withEndpoints(Endpoints.empty());

    // Assert
    assertSame(createResult, actualWithEndpointsResult);
  }

  /**
   * Test {@link Instance#isRegistered()}.
   *
   * <p>Method under test: {@link Instance#isRegistered()}
   */
  @Test
  @DisplayName("Test isRegistered()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Instance.isRegistered()"})
  void testIsRegistered() {
    // Arrange, Act and Assert
    assertFalse(Instance.create(InstanceId.of("42")).isRegistered());
  }

  /**
   * Test {@link Instance#getRegistration()}.
   *
   * <p>Method under test: {@link Instance#getRegistration()}
   */
  @Test
  @DisplayName("Test getRegistration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Registration Instance.getRegistration()"})
  void testGetRegistration() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalStateException.class, () -> Instance.create(InstanceId.of("42")).getRegistration());
  }

  /**
   * Test {@link Instance#getUnsavedEvents()}.
   *
   * <p>Method under test: {@link Instance#getUnsavedEvents()}
   */
  @Test
  @DisplayName("Test getUnsavedEvents()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List Instance.getUnsavedEvents()"})
  void testGetUnsavedEvents() {
    // Arrange, Act and Assert
    assertTrue(Instance.create(InstanceId.of("42")).getUnsavedEvents().isEmpty());
  }

  /**
   * Test {@link Instance#clearUnsavedEvents()}.
   *
   * <p>Method under test: {@link Instance#clearUnsavedEvents()}
   */
  @Test
  @DisplayName("Test clearUnsavedEvents()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.clearUnsavedEvents()"})
  void testClearUnsavedEvents() {
    // Arrange
    InstanceId id = InstanceId.of("42");

    // Act
    Instance actualClearUnsavedEventsResult = Instance.create(id).clearUnsavedEvents();

    // Assert
    StatusInfo statusInfo = actualClearUnsavedEventsResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertNull(actualClearUnsavedEventsResult.getBuildVersion());
    assertEquals(-1L, actualClearUnsavedEventsResult.getVersion());
    Instant statusTimestamp = actualClearUnsavedEventsResult.getStatusTimestamp();
    assertEquals(0, statusTimestamp.getNano());
    assertEquals(0L, statusTimestamp.getEpochSecond());
    assertFalse(actualClearUnsavedEventsResult.isRegistered());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(actualClearUnsavedEventsResult.getUnsavedEvents().isEmpty());
    Map<String, Object> values = actualClearUnsavedEventsResult.getInfo().getValues();
    assertTrue(values.isEmpty());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(values, actualClearUnsavedEventsResult.getTags().getValues());
    assertSame(id, actualClearUnsavedEventsResult.getId());
  }

  /**
   * Test {@link Instance#apply(InstanceEvent)} with {@code event}.
   *
   * <p>Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  @DisplayName("Test apply(InstanceEvent) with 'event'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(InstanceEvent)"})
  void testApplyWithEvent() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act and Assert
    StatusInfo statusInfo =
        createResult.apply(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L)).getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(statusInfo.getDetails().isEmpty());
  }

  /**
   * Test {@link Instance#apply(InstanceEvent)} with {@code event}.
   *
   * <p>Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  @DisplayName("Test apply(InstanceEvent) with 'event'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(InstanceEvent)"})
  void testApplyWithEvent2() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));
    InstanceId instance = InstanceId.of("42");
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act and Assert
    Registration registration2 =
        createResult
            .apply(new InstanceRegistrationUpdatedEvent(instance, 1L, registration))
            .getRegistration();
    assertEquals("Name", registration2.getName());
    assertEquals("Source", registration2.getSource());
    assertEquals("https://example.org/example", registration2.getHealthUrl());
    assertEquals("https://example.org/example", registration2.getManagementUrl());
    assertEquals("https://example.org/example", registration2.getServiceUrl());
    assertTrue(registration2.getMetadata().isEmpty());
  }

  /**
   * Test {@link Instance#apply(InstanceEvent)} with {@code event}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then return BuildVersion is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test apply(InstanceEvent) with 'event'; given '42'; then return BuildVersion is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(InstanceEvent)"})
  void testApplyWithEvent_given42_thenReturnBuildVersionIsNull() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);

    InstanceId instance = mock(InstanceId.class);
    when(instance.getValue()).thenReturn("42");

    // Act
    Instance actualApplyResult =
        createResult.apply(new InstanceStatusChangedEvent(instance, 1L, mock(StatusInfo.class)));

    // Assert
    verify(instance).getValue();
    assertNull(actualApplyResult.getBuildVersion());
    assertEquals(1L, actualApplyResult.getVersion());
    assertTrue(actualApplyResult.getUnsavedEvents().isEmpty());
    Map<String, Object> values = actualApplyResult.getInfo().getValues();
    assertTrue(values.isEmpty());
    assertSame(values, actualApplyResult.getTags().getValues());
    assertSame(id, actualApplyResult.getId());
  }

  /**
   * Test {@link Instance#apply(InstanceEvent)} with {@code event}.
   *
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException()}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  @DisplayName(
      "Test apply(InstanceEvent) with 'event'; given IllegalStateException(); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(InstanceEvent)"})
  void testApplyWithEvent_givenIllegalStateException_thenThrowIllegalStateException() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    InstanceId instance = mock(InstanceId.class);
    when(instance.getValue()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            createResult.apply(
                new InstanceStatusChangedEvent(instance, 1L, mock(StatusInfo.class))));
    verify(instance).getValue();
  }

  /**
   * Test {@link Instance#apply(InstanceEvent)} with {@code event}.
   *
   * <ul>
   *   <li>Then return Endpoints is empty.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  @DisplayName("Test apply(InstanceEvent) with 'event'; then return Endpoints is empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(InstanceEvent)"})
  void testApplyWithEvent_thenReturnEndpointsIsEmpty() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));
    InstanceId instance = InstanceId.of("42");
    Endpoints endpoints = Endpoints.empty();

    // Act
    Instance actualApplyResult =
        createResult.apply(new InstanceEndpointsDetectedEvent(instance, 1L, endpoints));

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(endpoints, actualApplyResult.getEndpoints());
  }

  /**
   * Test {@link Instance#apply(InstanceEvent)} with {@code event}.
   *
   * <ul>
   *   <li>Then return Info is empty.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  @DisplayName("Test apply(InstanceEvent) with 'event'; then return Info is empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(InstanceEvent)"})
  void testApplyWithEvent_thenReturnInfoIsEmpty() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));
    InstanceId instance = InstanceId.of("42");
    Info info = Info.empty();

    // Act
    Instance actualApplyResult =
        createResult.apply(new InstanceInfoChangedEvent(instance, 1L, info));

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(info, actualApplyResult.getInfo());
  }

  /**
   * Test {@link Instance#apply(InstanceEvent)} with {@code event}.
   *
   * <ul>
   *   <li>Then return Registered.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  @DisplayName("Test apply(InstanceEvent) with 'event'; then return Registered")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(InstanceEvent)"})
  void testApplyWithEvent_thenReturnRegistered() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));
    InstanceId instance = InstanceId.of("42");
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();

    // Act
    Instance actualApplyResult =
        createResult.apply(new InstanceRegisteredEvent(instance, 1L, registration));

    // Assert
    Registration registration2 = actualApplyResult.getRegistration();
    assertEquals("Name", registration2.getName());
    assertEquals("Source", registration2.getSource());
    assertEquals("https://example.org/example", registration2.getHealthUrl());
    assertEquals("https://example.org/example", registration2.getManagementUrl());
    assertEquals("https://example.org/example", registration2.getServiceUrl());
    assertTrue(actualApplyResult.isRegistered());
    assertTrue(registration2.getMetadata().isEmpty());
  }

  /**
   * Test {@link Instance#apply(Collection)} with {@code events}.
   *
   * <p>Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  @DisplayName("Test apply(Collection) with 'events'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(Collection)"})
  void testApplyWithEvents() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();
    events.add(new InstanceRegistrationUpdatedEvent(instance, 1L, registration));

    // Act and Assert
    Registration registration2 = createResult.apply(events).getRegistration();
    assertEquals("Name", registration2.getName());
    assertEquals("Source", registration2.getSource());
    assertEquals("https://example.org/example", registration2.getHealthUrl());
    assertEquals("https://example.org/example", registration2.getManagementUrl());
    assertEquals("https://example.org/example", registration2.getServiceUrl());
    assertTrue(registration2.getMetadata().isEmpty());
  }

  /**
   * Test {@link Instance#apply(Collection)} with {@code events}.
   *
   * <ul>
   *   <li>Given {@link InstanceId} {@link InstanceId#getValue()} return {@code 42}.
   *   <li>Then return BuildVersion is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  @DisplayName(
      "Test apply(Collection) with 'events'; given InstanceId getValue() return '42'; then return BuildVersion is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(Collection)"})
  void testApplyWithEvents_givenInstanceIdGetValueReturn42_thenReturnBuildVersionIsNull() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);

    InstanceId instance = mock(InstanceId.class);
    when(instance.getValue()).thenReturn("42");
    InstanceStatusChangedEvent instanceStatusChangedEvent =
        new InstanceStatusChangedEvent(instance, 1L, mock(StatusInfo.class));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(instanceStatusChangedEvent);

    // Act
    Instance actualApplyResult = createResult.apply(events);

    // Assert
    verify(instance).getValue();
    assertNull(actualApplyResult.getBuildVersion());
    assertTrue(actualApplyResult.getUnsavedEvents().isEmpty());
    Map<String, Object> values = actualApplyResult.getInfo().getValues();
    assertTrue(values.isEmpty());
    assertSame(values, actualApplyResult.getTags().getValues());
    assertSame(id, actualApplyResult.getId());
  }

  /**
   * Test {@link Instance#apply(Collection)} with {@code events}.
   *
   * <ul>
   *   <li>Then return Endpoints is empty.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  @DisplayName("Test apply(Collection) with 'events'; then return Endpoints is empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(Collection)"})
  void testApplyWithEvents_thenReturnEndpointsIsEmpty() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Endpoints endpoints = Endpoints.empty();
    events.add(new InstanceEndpointsDetectedEvent(instance, 1L, endpoints));

    // Act
    Instance actualApplyResult = createResult.apply(events);

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertEquals(1L, actualApplyResult.getVersion());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(endpoints, actualApplyResult.getEndpoints());
  }

  /**
   * Test {@link Instance#apply(Collection)} with {@code events}.
   *
   * <ul>
   *   <li>Then return Info is empty.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  @DisplayName("Test apply(Collection) with 'events'; then return Info is empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(Collection)"})
  void testApplyWithEvents_thenReturnInfoIsEmpty() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Info info = Info.empty();
    events.add(new InstanceInfoChangedEvent(instance, 1L, info));

    // Act
    Instance actualApplyResult = createResult.apply(events);

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertEquals(1L, actualApplyResult.getVersion());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(info, actualApplyResult.getInfo());
  }

  /**
   * Test {@link Instance#apply(Collection)} with {@code events}.
   *
   * <ul>
   *   <li>Then return Registered.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  @DisplayName("Test apply(Collection) with 'events'; then return Registered")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(Collection)"})
  void testApplyWithEvents_thenReturnRegistered() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Registration registration =
        Registration.builder()
            .healthUrl("https://example.org/example")
            .managementUrl("https://example.org/example")
            .name("Name")
            .serviceUrl("https://example.org/example")
            .source("Source")
            .build();
    events.add(new InstanceRegisteredEvent(instance, 1L, registration));

    // Act
    Instance actualApplyResult = createResult.apply(events);

    // Assert
    Registration registration2 = actualApplyResult.getRegistration();
    assertEquals("Name", registration2.getName());
    assertEquals("Source", registration2.getSource());
    assertEquals("https://example.org/example", registration2.getHealthUrl());
    assertEquals("https://example.org/example", registration2.getManagementUrl());
    assertEquals("https://example.org/example", registration2.getServiceUrl());
    assertTrue(actualApplyResult.isRegistered());
    assertTrue(registration2.getMetadata().isEmpty());
  }

  /**
   * Test {@link Instance#apply(Collection)} with {@code events}.
   *
   * <ul>
   *   <li>Then return StatusInfo Status is {@code UNKNOWN}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  @DisplayName("Test apply(Collection) with 'events'; then return StatusInfo Status is 'UNKNOWN'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(Collection)"})
  void testApplyWithEvents_thenReturnStatusInfoStatusIsUnknown() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act
    Instance actualApplyResult = createResult.apply(events);

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertEquals(1L, actualApplyResult.getVersion());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(statusInfo.getDetails().isEmpty());
  }

  /**
   * Test {@link Instance#apply(Collection)} with {@code events}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  @DisplayName("Test apply(Collection) with 'events'; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(Collection)"})
  void testApplyWithEvents_thenThrowIllegalStateException() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    InstanceId instance = mock(InstanceId.class);
    when(instance.getValue()).thenThrow(new IllegalStateException());
    InstanceStatusChangedEvent instanceStatusChangedEvent =
        new InstanceStatusChangedEvent(instance, 1L, mock(StatusInfo.class));

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(instanceStatusChangedEvent);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> createResult.apply(events));
    verify(instance).getValue();
  }

  /**
   * Test {@link Instance#apply(Collection)} with {@code events}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return create {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  @DisplayName(
      "Test apply(Collection) with 'events'; when ArrayList(); then return create InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Instance Instance.apply(Collection)"})
  void testApplyWithEvents_whenArrayList_thenReturnCreateInstanceIdWithValueIs42() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act
    Instance actualApplyResult = createResult.apply(new ArrayList<>());

    // Assert
    assertSame(createResult, actualApplyResult);
  }
}
