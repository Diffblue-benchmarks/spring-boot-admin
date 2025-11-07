package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.util.Collection;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

class SpringBootAdminServletApplicationDiffblueTest {
  /**
   * Method under test: {@link SpringBootAdminServletApplication#cacheManager()}
   */
  @Test
  void testCacheManager() {
    // Arrange and Act
    CacheManager actualCacheManagerResult = (new SpringBootAdminServletApplication()).cacheManager();

    // Assert
    Collection<String> cacheNames = actualCacheManagerResult.getCacheNames();
    assertEquals(1, cacheNames.size());
    assertTrue(cacheNames instanceof Set);
    assertTrue(actualCacheManagerResult instanceof ConcurrentMapCacheManager);
    assertFalse(((ConcurrentMapCacheManager) actualCacheManagerResult).isStoreByValue());
    assertTrue(cacheNames.contains("books"));
    assertTrue(((ConcurrentMapCacheManager) actualCacheManagerResult).isAllowNullValues());
  }

  /**
   * Method under test:
   * {@link SpringBootAdminServletApplication#customNotifier(InstanceRepository)}
   */
  @Test
  void testCustomNotifier() {
    // Arrange
    SpringBootAdminServletApplication springBootAdminServletApplication = new SpringBootAdminServletApplication();

    // Act and Assert
    assertTrue(
        springBootAdminServletApplication.customNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()))
            .isEnabled());
  }

  /**
   * Method under test: {@link SpringBootAdminServletApplication#customEndpoint()}
   */
  @Test
  void testCustomEndpoint() {
    // Arrange, Act and Assert
    assertEquals("Hello World!", (new SpringBootAdminServletApplication()).customEndpoint().invoke());
  }

  /**
   * Method under test:
   * {@link SpringBootAdminServletApplication#httpTraceRepository()}
   */
  @Test
  void testHttpTraceRepository() {
    // Arrange and Act
    HttpExchangeRepository actualHttpTraceRepositoryResult = (new SpringBootAdminServletApplication())
        .httpTraceRepository();

    // Assert
    assertTrue(actualHttpTraceRepositoryResult instanceof InMemoryHttpExchangeRepository);
    assertTrue(actualHttpTraceRepositoryResult.findAll().isEmpty());
  }

  /**
   * Method under test:
   * {@link SpringBootAdminServletApplication#auditEventRepository()}
   */
  @Test
  void testAuditEventRepository() {
    // Arrange and Act
    AuditEventRepository actualAuditEventRepositoryResult = (new SpringBootAdminServletApplication())
        .auditEventRepository();

    // Assert
    assertTrue(actualAuditEventRepositoryResult instanceof InMemoryAuditEventRepository);
    assertTrue(actualAuditEventRepositoryResult.find("Principal", null, "Type").isEmpty());
  }
}
