package de.codecentric.boot.admin.server.notify.filter.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.filter.ApplicationNameNotificationFilter;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import de.codecentric.boot.admin.server.notify.filter.InstanceIdNotificationFilter;
import de.codecentric.boot.admin.server.notify.filter.NotificationFilter;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NotificationFilterController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class NotificationFilterControllerDiffblueTest {
  @MockitoBean private FilteringNotifier filteringNotifier;

  @Autowired private NotificationFilterController notificationFilterController;

  /**
   * Test {@link NotificationFilterController#NotificationFilterController(FilteringNotifier)}.
   *
   * <p>Method under test: {@link
   * NotificationFilterController#NotificationFilterController(FilteringNotifier)}
   */
  @Test
  @DisplayName("Test new NotificationFilterController(FilteringNotifier)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NotificationFilterController.<init>(FilteringNotifier)"})
  void testNewNotificationFilterController() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate, repository);

    // Act and Assert
    assertTrue(new NotificationFilterController(filteringNotifier).getFilters().isEmpty());
  }

  /**
   * Test {@link NotificationFilterController#getFilters()}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#getFilters()}
   */
  @Test
  @DisplayName(
      "Test getFilters(); given InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection NotificationFilterController.getFilters()"})
  void testGetFilters_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate, repository);

    // Act and Assert
    assertTrue(new NotificationFilterController(filteringNotifier).getFilters().isEmpty());
  }

  /**
   * Test {@link NotificationFilterController#getFilters()}.
   *
   * <ul>
   *   <li>Then calls {@link FilteringNotifier#getNotificationFilters()}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#getFilters()}
   */
  @Test
  @DisplayName("Test getFilters(); then calls getNotificationFilters()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Collection NotificationFilterController.getFilters()"})
  void testGetFilters_thenCallsGetNotificationFilters() {
    // Arrange
    when(filteringNotifier.getNotificationFilters()).thenReturn(new HashMap<>());

    // Act
    Collection<NotificationFilter> actualFilters = notificationFilterController.getFilters();

    // Assert
    verify(filteringNotifier).getNotificationFilters();
    assertTrue(actualFilters.isEmpty());
  }

  /**
   * Test {@link NotificationFilterController#addFilter(String, String, Long)}.
   *
   * <ul>
   *   <li>Given {@link FilteringNotifier}.
   *   <li>When zero.
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  @DisplayName(
      "Test addFilter(String, String, Long); given FilteringNotifier; when zero; then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity NotificationFilterController.addFilter(String, String, Long)"})
  void testAddFilter_givenFilteringNotifier_whenZero_thenStatusCodeReturnHttpStatus() {
    // Arrange and Act
    ResponseEntity<?> actualAddFilterResult = notificationFilterController.addFilter(" ", " ", 0L);

    // Assert
    HttpStatusCode statusCode = actualAddFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(
        "Either 'instanceId' or 'applicationName' must be set", actualAddFilterResult.getBody());
    assertEquals(400, actualAddFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
  }

  /**
   * Test {@link NotificationFilterController#addFilter(String, String, Long)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  @DisplayName(
      "Test addFilter(String, String, Long); given InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity NotificationFilterController.addFilter(String, String, Long)"})
  void testAddFilter_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate, repository);

    // Act and Assert
    Object body =
        new NotificationFilterController(filteringNotifier).addFilter("42", "Name", 1L).getBody();
    assertTrue(body instanceof InstanceIdNotificationFilter);
    InstanceId instanceId = ((InstanceIdNotificationFilter) body).getInstanceId();
    assertEquals("42", instanceId.getValue());
    assertEquals("42", instanceId.toString());
  }

  /**
   * Test {@link NotificationFilterController#addFilter(String, String, Long)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return Body InstanceId Value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  @DisplayName(
      "Test addFilter(String, String, Long); when '42'; then return Body InstanceId Value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity NotificationFilterController.addFilter(String, String, Long)"})
  void testAddFilter_when42_thenReturnBodyInstanceIdValueIs42() {
    // Arrange
    doNothing().when(filteringNotifier).addFilter(Mockito.<NotificationFilter>any());

    // Act
    ResponseEntity<?> actualAddFilterResult =
        notificationFilterController.addFilter("42", "Name", 1L);

    // Assert
    verify(filteringNotifier).addFilter(isA(NotificationFilter.class));
    Object body = actualAddFilterResult.getBody();
    assertTrue(body instanceof InstanceIdNotificationFilter);
    InstanceId instanceId = ((InstanceIdNotificationFilter) body).getInstanceId();
    assertEquals("42", instanceId.getValue());
    assertEquals("42", instanceId.toString());
  }

  /**
   * Test {@link NotificationFilterController#addFilter(String, String, Long)}.
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then return Body Expiry is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  @DisplayName(
      "Test addFilter(String, String, Long); when minus one; then return Body Expiry is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity NotificationFilterController.addFilter(String, String, Long)"})
  void testAddFilter_whenMinusOne_thenReturnBodyExpiryIsNull() {
    // Arrange
    doNothing().when(filteringNotifier).addFilter(Mockito.<NotificationFilter>any());

    // Act
    ResponseEntity<?> actualAddFilterResult =
        notificationFilterController.addFilter("42", "Name", -1L);

    // Assert
    verify(filteringNotifier).addFilter(isA(NotificationFilter.class));
    Object body = actualAddFilterResult.getBody();
    assertTrue(body instanceof InstanceIdNotificationFilter);
    InstanceId instanceId = ((InstanceIdNotificationFilter) body).getInstanceId();
    assertEquals("42", instanceId.getValue());
    assertEquals("42", instanceId.toString());
    assertNull(((InstanceIdNotificationFilter) body).getExpiry());
  }

  /**
   * Test {@link NotificationFilterController#addFilter(String, String, Long)}.
   *
   * <ul>
   *   <li>When {@code not blank}.
   *   <li>Then return Body InstanceId Value is {@code not blank}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  @DisplayName(
      "Test addFilter(String, String, Long); when 'not blank'; then return Body InstanceId Value is 'not blank'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity NotificationFilterController.addFilter(String, String, Long)"})
  void testAddFilter_whenNotBlank_thenReturnBodyInstanceIdValueIsNotBlank() {
    // Arrange
    doNothing().when(filteringNotifier).addFilter(Mockito.<NotificationFilter>any());

    // Act
    ResponseEntity<?> actualAddFilterResult =
        notificationFilterController.addFilter("not blank", "not blank", null);

    // Assert
    verify(filteringNotifier).addFilter(isA(NotificationFilter.class));
    Object body = actualAddFilterResult.getBody();
    assertTrue(body instanceof InstanceIdNotificationFilter);
    InstanceId instanceId = ((InstanceIdNotificationFilter) body).getInstanceId();
    assertEquals("not blank", instanceId.getValue());
    assertEquals("not blank", instanceId.toString());
    assertNull(((InstanceIdNotificationFilter) body).getExpiry());
  }

  /**
   * Test {@link NotificationFilterController#addFilter(String, String, Long)}.
   *
   * <ul>
   *   <li>When space.
   *   <li>Then Body return {@link ApplicationNameNotificationFilter}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  @DisplayName(
      "Test addFilter(String, String, Long); when space; then Body return ApplicationNameNotificationFilter")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity NotificationFilterController.addFilter(String, String, Long)"})
  void testAddFilter_whenSpace_thenBodyReturnApplicationNameNotificationFilter() {
    // Arrange
    doNothing().when(filteringNotifier).addFilter(Mockito.<NotificationFilter>any());

    // Act
    ResponseEntity<?> actualAddFilterResult =
        notificationFilterController.addFilter(" ", "Name", 1L);

    // Assert
    verify(filteringNotifier).addFilter(isA(NotificationFilter.class));
    Object body = actualAddFilterResult.getBody();
    assertTrue(body instanceof ApplicationNameNotificationFilter);
    assertEquals("Name", ((ApplicationNameNotificationFilter) body).getApplicationName());
  }

  /**
   * Test {@link NotificationFilterController#deleteFilter(String)}.
   *
   * <ul>
   *   <li>Given {@link FilteringNotifier} {@link FilteringNotifier#removeFilter(String)} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#deleteFilter(String)}
   */
  @Test
  @DisplayName(
      "Test deleteFilter(String); given FilteringNotifier removeFilter(String) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity NotificationFilterController.deleteFilter(String)"})
  void testDeleteFilter_givenFilteringNotifierRemoveFilterReturnNull() {
    // Arrange
    when(filteringNotifier.removeFilter(Mockito.<String>any())).thenReturn(null);

    // Act
    ResponseEntity<Void> actualDeleteFilterResult = notificationFilterController.deleteFilter("42");

    // Assert
    verify(filteringNotifier).removeFilter("42");
    HttpStatusCode statusCode = actualDeleteFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteFilterResult.getBody());
    assertEquals(404, actualDeleteFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.NOT_FOUND, statusCode);
    assertFalse(actualDeleteFilterResult.hasBody());
    assertTrue(actualDeleteFilterResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link NotificationFilterController#deleteFilter(String)}.
   *
   * <ul>
   *   <li>Given {@link InMemoryEventStore#InMemoryEventStore(int)} with maxLogSizePerAggregate is
   *       three.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#deleteFilter(String)}
   */
  @Test
  @DisplayName(
      "Test deleteFilter(String); given InMemoryEventStore(int) with maxLogSizePerAggregate is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity NotificationFilterController.deleteFilter(String)"})
  void testDeleteFilter_givenInMemoryEventStoreWithMaxLogSizePerAggregateIsThree() {
    // Arrange
    Notifier delegate = mock(Notifier.class);
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    FilteringNotifier filteringNotifier = new FilteringNotifier(delegate, repository);

    // Act
    ResponseEntity<Void> actualDeleteFilterResult =
        new NotificationFilterController(filteringNotifier).deleteFilter("42");

    // Assert
    HttpStatusCode statusCode = actualDeleteFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteFilterResult.getBody());
    assertEquals(404, actualDeleteFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.NOT_FOUND, statusCode);
    assertFalse(actualDeleteFilterResult.hasBody());
    assertTrue(actualDeleteFilterResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link NotificationFilterController#deleteFilter(String)}.
   *
   * <ul>
   *   <li>Then return StatusCodeValue is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link NotificationFilterController#deleteFilter(String)}
   */
  @Test
  @DisplayName("Test deleteFilter(String); then return StatusCodeValue is two hundred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity NotificationFilterController.deleteFilter(String)"})
  void testDeleteFilter_thenReturnStatusCodeValueIsTwoHundred() {
    // Arrange
    ApplicationNameNotificationFilter applicationNameNotificationFilter =
        new ApplicationNameNotificationFilter(
            "Application Name",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(filteringNotifier.removeFilter(Mockito.<String>any()))
        .thenReturn(applicationNameNotificationFilter);

    // Act
    ResponseEntity<Void> actualDeleteFilterResult = notificationFilterController.deleteFilter("42");

    // Assert
    verify(filteringNotifier).removeFilter("42");
    HttpStatusCode statusCode = actualDeleteFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteFilterResult.getBody());
    assertEquals(200, actualDeleteFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertFalse(actualDeleteFilterResult.hasBody());
    assertTrue(actualDeleteFilterResult.getHeaders().isEmpty());
  }
}
