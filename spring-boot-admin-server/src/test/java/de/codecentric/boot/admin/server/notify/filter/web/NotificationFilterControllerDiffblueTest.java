package de.codecentric.boot.admin.server.notify.filter.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {NotificationFilterController.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class NotificationFilterControllerDiffblueTest {
  @MockBean
  private FilteringNotifier filteringNotifier;

  @Autowired
  private NotificationFilterController notificationFilterController;

  /**
   * Method under test: {@link NotificationFilterController#getFilters()}
   */
  @Test
  public void testGetFilters() {
    // Arrange
    when(filteringNotifier.getNotificationFilters()).thenReturn(new HashMap<>());

    // Act
    Collection<NotificationFilter> actualFilters = notificationFilterController.getFilters();

    // Assert
    verify(filteringNotifier).getNotificationFilters();
    assertTrue(actualFilters.isEmpty());
  }

  /**
   * Method under test:
   * {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  public void testAddFilter() {
    // Arrange
    doNothing().when(filteringNotifier).addFilter(Mockito.<NotificationFilter>any());

    // Act
    ResponseEntity<?> actualAddFilterResult = notificationFilterController.addFilter("42", "Name", 1L);

    // Assert
    verify(filteringNotifier).addFilter(isA(NotificationFilter.class));
    Object body = actualAddFilterResult.getBody();
    assertTrue(body instanceof InstanceIdNotificationFilter);
    HttpStatusCode statusCode = actualAddFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    InstanceId instanceId = ((InstanceIdNotificationFilter) body).getInstanceId();
    assertEquals("42", instanceId.getValue());
    assertEquals("42", instanceId.toString());
    assertEquals(200, actualAddFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualAddFilterResult.hasBody());
    assertTrue(actualAddFilterResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  public void testAddFilter2() {
    // Arrange
    doNothing().when(filteringNotifier).addFilter(Mockito.<NotificationFilter>any());

    // Act
    ResponseEntity<?> actualAddFilterResult = notificationFilterController.addFilter(null, "Name", 1L);

    // Assert
    verify(filteringNotifier).addFilter(isA(NotificationFilter.class));
    Object body = actualAddFilterResult.getBody();
    assertTrue(body instanceof ApplicationNameNotificationFilter);
    HttpStatusCode statusCode = actualAddFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Name", ((ApplicationNameNotificationFilter) body).getApplicationName());
    assertEquals(200, actualAddFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualAddFilterResult.hasBody());
    assertTrue(actualAddFilterResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  public void testAddFilter3() {
    // Arrange
    doNothing().when(filteringNotifier).addFilter(Mockito.<NotificationFilter>any());

    // Act
    ResponseEntity<?> actualAddFilterResult = notificationFilterController.addFilter("42", "Name", null);

    // Assert
    verify(filteringNotifier).addFilter(isA(NotificationFilter.class));
    Object body = actualAddFilterResult.getBody();
    assertTrue(body instanceof InstanceIdNotificationFilter);
    HttpStatusCode statusCode = actualAddFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    InstanceId instanceId = ((InstanceIdNotificationFilter) body).getInstanceId();
    assertEquals("42", instanceId.getValue());
    assertEquals("42", instanceId.toString());
    assertNull(((InstanceIdNotificationFilter) body).getExpiry());
    assertEquals(200, actualAddFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertFalse(((InstanceIdNotificationFilter) body).isExpired());
    assertTrue(actualAddFilterResult.hasBody());
    assertTrue(actualAddFilterResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  public void testAddFilter4() {
    // Arrange
    doNothing().when(filteringNotifier).addFilter(Mockito.<NotificationFilter>any());

    // Act
    ResponseEntity<?> actualAddFilterResult = notificationFilterController.addFilter("42", "Name", -1L);

    // Assert
    verify(filteringNotifier).addFilter(isA(NotificationFilter.class));
    Object body = actualAddFilterResult.getBody();
    assertTrue(body instanceof InstanceIdNotificationFilter);
    HttpStatusCode statusCode = actualAddFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    InstanceId instanceId = ((InstanceIdNotificationFilter) body).getInstanceId();
    assertEquals("42", instanceId.getValue());
    assertEquals("42", instanceId.toString());
    assertNull(((InstanceIdNotificationFilter) body).getExpiry());
    assertEquals(200, actualAddFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertFalse(((InstanceIdNotificationFilter) body).isExpired());
    assertTrue(actualAddFilterResult.hasBody());
    assertTrue(actualAddFilterResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link NotificationFilterController#addFilter(String, String, Long)}
   */
  @Test
  public void testAddFilter5() {
    // Arrange and Act
    ResponseEntity<?> actualAddFilterResult = notificationFilterController.addFilter(null, null, 1L);

    // Assert
    HttpStatusCode statusCode = actualAddFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Either 'instanceId' or 'applicationName' must be set", actualAddFilterResult.getBody());
    assertEquals(400, actualAddFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    assertTrue(actualAddFilterResult.hasBody());
    assertTrue(actualAddFilterResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link NotificationFilterController#deleteFilter(String)}
   */
  @Test
  public void testDeleteFilter() {
    // Arrange
    when(filteringNotifier.removeFilter(Mockito.<String>any())).thenReturn(new ApplicationNameNotificationFilter(
        "Application Name", LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    ResponseEntity<Void> actualDeleteFilterResult = notificationFilterController.deleteFilter("42");

    // Assert
    verify(filteringNotifier).removeFilter(eq("42"));
    HttpStatusCode statusCode = actualDeleteFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteFilterResult.getBody());
    assertEquals(200, actualDeleteFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertFalse(actualDeleteFilterResult.hasBody());
    assertTrue(actualDeleteFilterResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link NotificationFilterController#deleteFilter(String)}
   */
  @Test
  public void testDeleteFilter2() {
    // Arrange
    when(filteringNotifier.removeFilter(Mockito.<String>any())).thenReturn(null);

    // Act
    ResponseEntity<Void> actualDeleteFilterResult = notificationFilterController.deleteFilter("42");

    // Assert
    verify(filteringNotifier).removeFilter(eq("42"));
    HttpStatusCode statusCode = actualDeleteFilterResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteFilterResult.getBody());
    assertEquals(404, actualDeleteFilterResult.getStatusCodeValue());
    assertEquals(HttpStatus.NOT_FOUND, statusCode);
    assertFalse(actualDeleteFilterResult.hasBody());
    assertTrue(actualDeleteFilterResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link NotificationFilterController#NotificationFilterController(FilteringNotifier)}
   */
  @Test
  public void testNewNotificationFilterController() {
    // Arrange
    Notifier delegate = mock(Notifier.class);

    // Act and Assert
    assertTrue((new NotificationFilterController(
        new FilteringNotifier(delegate, new EventsourcingInstanceRepository(new InMemoryEventStore())))).getFilters()
        .isEmpty());
  }
}
