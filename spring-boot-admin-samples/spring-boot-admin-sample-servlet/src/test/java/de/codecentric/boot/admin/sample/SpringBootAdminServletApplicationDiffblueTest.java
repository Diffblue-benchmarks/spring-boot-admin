package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.util.Collection;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

class SpringBootAdminServletApplicationDiffblueTest {
  /**
   * Test {@link SpringBootAdminServletApplication#cacheManager()}.
   * <p>
   * Method under test: {@link SpringBootAdminServletApplication#cacheManager()}
   */
  @Test
  @DisplayName("Test cacheManager()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CacheManager SpringBootAdminServletApplication.cacheManager()"})
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
   * Test {@link SpringBootAdminServletApplication#customNotifier(InstanceRepository)}.
   * <p>
   * Method under test: {@link SpringBootAdminServletApplication#customNotifier(InstanceRepository)}
   */
  @Test
  @DisplayName("Test customNotifier(InstanceRepository)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "de.codecentric.boot.admin.sample.CustomNotifier SpringBootAdminServletApplication.customNotifier(InstanceRepository)"})
  void testCustomNotifier() {
    // Arrange
    SpringBootAdminServletApplication springBootAdminServletApplication = new SpringBootAdminServletApplication();

    // Act and Assert
    assertTrue(
        springBootAdminServletApplication.customNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()))
            .isEnabled());
  }

  /**
   * Test {@link SpringBootAdminServletApplication#customEndpoint()}.
   * <p>
   * Method under test: {@link SpringBootAdminServletApplication#customEndpoint()}
   */
  @Test
  @DisplayName("Test customEndpoint()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "de.codecentric.boot.admin.sample.CustomEndpoint SpringBootAdminServletApplication.customEndpoint()"})
  void testCustomEndpoint() {
    // Arrange, Act and Assert
    assertEquals("Hello World!", (new SpringBootAdminServletApplication()).customEndpoint().invoke());
  }

  /**
   * Test {@link SpringBootAdminServletApplication#httpTraceRepository()}.
   * <p>
   * Method under test: {@link SpringBootAdminServletApplication#httpTraceRepository()}
   */
  @Test
  @DisplayName("Test httpTraceRepository()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"HttpExchangeRepository SpringBootAdminServletApplication.httpTraceRepository()"})
  void testHttpTraceRepository() {
    // Arrange and Act
    HttpExchangeRepository actualHttpTraceRepositoryResult = (new SpringBootAdminServletApplication())
        .httpTraceRepository();

    // Assert
    assertTrue(actualHttpTraceRepositoryResult instanceof InMemoryHttpExchangeRepository);
    assertTrue(actualHttpTraceRepositoryResult.findAll().isEmpty());
  }

  /**
   * Test {@link SpringBootAdminServletApplication#auditEventRepository()}.
   * <p>
   * Method under test: {@link SpringBootAdminServletApplication#auditEventRepository()}
   */
  @Test
  @DisplayName("Test auditEventRepository()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"AuditEventRepository SpringBootAdminServletApplication.auditEventRepository()"})
  void testAuditEventRepository() {
    // Arrange and Act
    AuditEventRepository actualAuditEventRepositoryResult = (new SpringBootAdminServletApplication())
        .auditEventRepository();

    // Assert
    assertTrue(actualAuditEventRepositoryResult instanceof InMemoryAuditEventRepository);
    assertTrue(actualAuditEventRepositoryResult.find("Principal", null, "Type").isEmpty());
  }
}
