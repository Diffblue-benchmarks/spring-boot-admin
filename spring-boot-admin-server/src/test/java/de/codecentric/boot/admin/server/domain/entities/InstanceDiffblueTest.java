package de.codecentric.boot.admin.server.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEndpointsDetectedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceInfoChangedEvent;
import de.codecentric.boot.admin.server.domain.values.BuildVersion;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.Info;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.domain.values.Tags;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class InstanceDiffblueTest {
  /**
   * Method under test: {@link Instance#create(InstanceId)}
   */
  @Test
  public void testCreate() {
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
   * Method under test: {@link Instance#deregister()}
   */
  @Test
  public void testDeregister() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act and Assert
    assertSame(createResult, createResult.deregister());
  }

  /**
   * Method under test: {@link Instance#withInfo(Info)}
   */
  @Test
  public void testWithInfo() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act and Assert
    assertSame(createResult, createResult.withInfo(Info.empty()));
  }

  /**
   * Method under test: {@link Instance#withEndpoints(Endpoints)}
   */
  @Test
  public void testWithEndpoints() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act and Assert
    assertSame(createResult, createResult.withEndpoints(Endpoints.empty()));
  }

  /**
   * Method under test: {@link Instance#withEndpoints(Endpoints)}
   */
  @Test
  public void testWithEndpoints2() {
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
    StatusInfo statusInfo = actualWithEndpointsResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertNull(actualWithEndpointsResult.getBuildVersion());
    Instant statusTimestamp = actualWithEndpointsResult.getStatusTimestamp();
    assertEquals(0, statusTimestamp.getNano());
    assertEquals(0L, actualWithEndpointsResult.getVersion());
    assertEquals(0L, getResult.getVersion());
    assertEquals(0L, statusTimestamp.getEpochSecond());
    assertFalse(actualWithEndpointsResult.isRegistered());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    Map<String, Object> values = actualWithEndpointsResult.getInfo().getValues();
    assertTrue(values.isEmpty());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(values, actualWithEndpointsResult.getTags().getValues());
    assertSame(endpoints, actualWithEndpointsResult.getEndpoints());
    assertSame(endpoints, ((InstanceEndpointsDetectedEvent) getResult).getEndpoints());
    assertSame(id, actualWithEndpointsResult.getId());
    assertSame(id, getResult.getInstance());
  }

  /**
   * Method under test: {@link Instance#getRegistration()}
   */
  @Test
  public void testGetRegistration() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> Instance.create(InstanceId.of("42")).getRegistration());
  }

  /**
   * Method under test: {@link Instance#getUnsavedEvents()}
   */
  @Test
  public void testGetUnsavedEvents() {
    // Arrange, Act and Assert
    assertTrue(Instance.create(InstanceId.of("42")).getUnsavedEvents().isEmpty());
  }

  /**
   * Method under test: {@link Instance#clearUnsavedEvents()}
   */
  @Test
  public void testClearUnsavedEvents() {
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
   * Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  public void testApply() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);

    // Act
    Instance actualApplyResult = createResult.apply(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertNull(actualApplyResult.getBuildVersion());
    assertEquals(1L, actualApplyResult.getVersion());
    assertFalse(actualApplyResult.isRegistered());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(actualApplyResult.getUnsavedEvents().isEmpty());
    Map<String, Object> values = actualApplyResult.getInfo().getValues();
    assertTrue(values.isEmpty());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(values, actualApplyResult.getTags().getValues());
    assertSame(id, actualApplyResult.getId());
  }

  /**
   * Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  public void testApply2() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);
    InstanceId instance = InstanceId.of("42");
    Endpoints endpoints = Endpoints.empty();

    // Act
    Instance actualApplyResult = createResult.apply(new InstanceEndpointsDetectedEvent(instance, 1L, endpoints));

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertNull(actualApplyResult.getBuildVersion());
    Instant statusTimestamp = actualApplyResult.getStatusTimestamp();
    assertEquals(0, statusTimestamp.getNano());
    assertEquals(0L, statusTimestamp.getEpochSecond());
    assertEquals(1L, actualApplyResult.getVersion());
    assertFalse(actualApplyResult.isRegistered());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(actualApplyResult.getUnsavedEvents().isEmpty());
    Map<String, Object> values = actualApplyResult.getInfo().getValues();
    assertTrue(values.isEmpty());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(values, actualApplyResult.getTags().getValues());
    assertSame(endpoints, actualApplyResult.getEndpoints());
    assertSame(id, actualApplyResult.getId());
  }

  /**
   * Method under test: {@link Instance#apply(InstanceEvent)}
   */
  @Test
  public void testApply3() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);
    InstanceId instance = InstanceId.of("42");
    Info info = Info.empty();

    // Act
    Instance actualApplyResult = createResult.apply(new InstanceInfoChangedEvent(instance, 1L, info));

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertNull(actualApplyResult.getBuildVersion());
    Instant statusTimestamp = actualApplyResult.getStatusTimestamp();
    assertEquals(0, statusTimestamp.getNano());
    assertEquals(0L, statusTimestamp.getEpochSecond());
    assertEquals(1L, actualApplyResult.getVersion());
    assertFalse(actualApplyResult.isRegistered());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(actualApplyResult.getUnsavedEvents().isEmpty());
    Info info2 = actualApplyResult.getInfo();
    Map<String, Object> values = info2.getValues();
    assertTrue(values.isEmpty());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(values, actualApplyResult.getTags().getValues());
    assertSame(info, info2);
    assertSame(id, actualApplyResult.getId());
  }

  /**
   * Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  public void testApply4() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act and Assert
    assertSame(createResult, createResult.apply(new ArrayList<>()));
  }

  /**
   * Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  public void testApply5() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);

    ArrayList<InstanceEvent> events = new ArrayList<>();
    events.add(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L));

    // Act
    Instance actualApplyResult = createResult.apply(events);

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertNull(actualApplyResult.getBuildVersion());
    assertEquals(1L, actualApplyResult.getVersion());
    assertFalse(actualApplyResult.isRegistered());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(actualApplyResult.getUnsavedEvents().isEmpty());
    Map<String, Object> values = actualApplyResult.getInfo().getValues();
    assertTrue(values.isEmpty());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(values, actualApplyResult.getTags().getValues());
    assertSame(id, actualApplyResult.getId());
  }

  /**
   * Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  public void testApply6() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);

    ArrayList<InstanceEvent> events = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Endpoints endpoints = Endpoints.empty();
    events.add(new InstanceEndpointsDetectedEvent(instance, 1L, endpoints));

    // Act
    Instance actualApplyResult = createResult.apply(events);

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertNull(actualApplyResult.getBuildVersion());
    Instant statusTimestamp = actualApplyResult.getStatusTimestamp();
    assertEquals(0, statusTimestamp.getNano());
    assertEquals(0L, statusTimestamp.getEpochSecond());
    assertEquals(1L, actualApplyResult.getVersion());
    assertFalse(actualApplyResult.isRegistered());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(actualApplyResult.getUnsavedEvents().isEmpty());
    Map<String, Object> values = actualApplyResult.getInfo().getValues();
    assertTrue(values.isEmpty());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(values, actualApplyResult.getTags().getValues());
    assertSame(endpoints, actualApplyResult.getEndpoints());
    assertSame(id, actualApplyResult.getId());
  }

  /**
   * Method under test: {@link Instance#apply(Collection)}
   */
  @Test
  public void testApply7() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);

    ArrayList<InstanceEvent> events = new ArrayList<>();
    InstanceId instance = InstanceId.of("42");
    Info info = Info.empty();
    events.add(new InstanceInfoChangedEvent(instance, 1L, info));

    // Act
    Instance actualApplyResult = createResult.apply(events);

    // Assert
    StatusInfo statusInfo = actualApplyResult.getStatusInfo();
    assertEquals("UNKNOWN", statusInfo.getStatus());
    assertNull(actualApplyResult.getBuildVersion());
    Instant statusTimestamp = actualApplyResult.getStatusTimestamp();
    assertEquals(0, statusTimestamp.getNano());
    assertEquals(0L, statusTimestamp.getEpochSecond());
    assertEquals(1L, actualApplyResult.getVersion());
    assertFalse(actualApplyResult.isRegistered());
    assertFalse(statusInfo.isDown());
    assertFalse(statusInfo.isOffline());
    assertFalse(statusInfo.isUp());
    assertTrue(statusInfo.isUnknown());
    assertTrue(actualApplyResult.getUnsavedEvents().isEmpty());
    Info info2 = actualApplyResult.getInfo();
    Map<String, Object> values = info2.getValues();
    assertTrue(values.isEmpty());
    assertTrue(statusInfo.getDetails().isEmpty());
    assertSame(values, actualApplyResult.getTags().getValues());
    assertSame(info, info2);
    assertSame(id, actualApplyResult.getId());
  }

  /**
   * Method under test: {@link Instance#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("Value"));

    // Act and Assert
    assertNotEquals(createResult, Instance.create(InstanceId.of("42")));
  }

  /**
   * Method under test: {@link Instance#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenThrowException() {
    // Arrange
    Instance createResult = Instance.create(InstanceId.of("42"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> createResult.equals(Instance.create(InstanceId.of("42"))));
  }

  /**
   * Method under test: {@link Instance#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Instance.create(InstanceId.of("42")), null);
  }

  /**
   * Method under test: {@link Instance#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Instance.create(InstanceId.of("42")), "Different type to Instance");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Instance#getBuildVersion()}
   *   <li>{@link Instance#getEndpoints()}
   *   <li>{@link Instance#getId()}
   *   <li>{@link Instance#getInfo()}
   *   <li>{@link Instance#getStatusInfo()}
   *   <li>{@link Instance#getStatusTimestamp()}
   *   <li>{@link Instance#getTags()}
   *   <li>{@link Instance#getVersion()}
   *   <li>{@link Instance#isRegistered()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    InstanceId id = InstanceId.of("42");
    Instance createResult = Instance.create(id);

    // Act
    BuildVersion actualBuildVersion = createResult.getBuildVersion();
    createResult.getEndpoints();
    InstanceId actualId = createResult.getId();
    Info actualInfo = createResult.getInfo();
    StatusInfo actualStatusInfo = createResult.getStatusInfo();
    Instant actualStatusTimestamp = createResult.getStatusTimestamp();
    Tags actualTags = createResult.getTags();
    long actualVersion = createResult.getVersion();
    boolean actualIsRegisteredResult = createResult.isRegistered();

    // Assert
    assertEquals("UNKNOWN", actualStatusInfo.getStatus());
    assertNull(actualBuildVersion);
    assertEquals(-1L, actualVersion);
    assertFalse(actualIsRegisteredResult);
    assertFalse(actualStatusInfo.isDown());
    assertFalse(actualStatusInfo.isOffline());
    assertFalse(actualStatusInfo.isUp());
    assertTrue(actualStatusInfo.isUnknown());
    Map<String, Object> values = actualInfo.getValues();
    assertTrue(values.isEmpty());
    assertTrue(actualStatusInfo.getDetails().isEmpty());
    assertSame(values, actualTags.getValues());
    assertSame(id, actualId);
    assertSame(actualStatusTimestamp.EPOCH, actualStatusTimestamp);
  }
}
