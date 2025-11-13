package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Subscription;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

class SpringBootAdminServletApplicationDiffblueTest {
  /**
   * Test {@link SpringBootAdminServletApplication#cacheManager()}.
   *
   * <p>Method under test: {@link SpringBootAdminServletApplication#cacheManager()}
   */
  @Test
  @DisplayName("Test cacheManager()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CacheManager SpringBootAdminServletApplication.cacheManager()"})
  void testCacheManager() {
    // Arrange and Act
    CacheManager actualCacheManagerResult = new SpringBootAdminServletApplication().cacheManager();

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
   * Test {@link SpringBootAdminServletApplication#auditLog()}.
   *
   * <p>Method under test: {@link SpringBootAdminServletApplication#auditLog()}
   */
  @Test
  @DisplayName("Test auditLog()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceExchangeFilterFunction SpringBootAdminServletApplication.auditLog()"})
  void testAuditLog() {
    // Arrange
    SpringBootAdminServletApplication springBootAdminServletApplication =
        new SpringBootAdminServletApplication();

    // Act
    springBootAdminServletApplication.auditLog();

    // Assert that nothing has changed
    CacheManager cacheManagerResult = springBootAdminServletApplication.cacheManager();
    Collection<String> cacheNames = cacheManagerResult.getCacheNames();
    assertEquals(1, cacheNames.size());
    assertTrue(cacheNames instanceof Set);
    assertTrue(
        springBootAdminServletApplication.auditEventRepository()
            instanceof InMemoryAuditEventRepository);
    assertTrue(cacheManagerResult instanceof ConcurrentMapCacheManager);
    assertEquals("Hello World!", springBootAdminServletApplication.customEndpoint().invoke());
    assertFalse(((ConcurrentMapCacheManager) cacheManagerResult).isStoreByValue());
    assertTrue(cacheNames.contains("books"));
    assertTrue(((ConcurrentMapCacheManager) cacheManagerResult).isAllowNullValues());
  }

  /**
   * Test {@link SpringBootAdminServletApplication#auditLog()}.
   *
   * <p>Method under test: {@link SpringBootAdminServletApplication#auditLog()}
   */
  @Test
  @DisplayName("Test auditLog()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceExchangeFilterFunction SpringBootAdminServletApplication.auditLog()"})
  void testAuditLog2() {
    // Arrange
    SpringBootAdminServletApplication springBootAdminServletApplication =
        new SpringBootAdminServletApplication();

    // Act
    InstanceExchangeFilterFunction actualAuditLogResult =
        springBootAdminServletApplication.auditLog();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    Mono<ClientResponse> mono = mock(Mono.class);
    Mono<ClientResponse> justResult =
        Mono.just(new ClientResponseWrapper(mock(ClientResponseWrapper.class)));
    when(mono.doOnSubscribe(Mockito.<Consumer<Subscription>>any())).thenReturn(justResult);
    ExchangeFunction next = mock(ExchangeFunction.class);
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(mono);
    Mono<ClientResponse> actualFilterResult = actualAuditLogResult.filter(instance, request, next);

    // Assert
    verify(next).exchange(isA(ClientRequest.class));
    verify(mono).doOnSubscribe(isA(Consumer.class));
    CacheManager cacheManagerResult = springBootAdminServletApplication.cacheManager();
    Collection<String> cacheNames = cacheManagerResult.getCacheNames();
    assertEquals(1, cacheNames.size());
    assertTrue(cacheNames instanceof Set);
    assertTrue(
        springBootAdminServletApplication.auditEventRepository()
            instanceof InMemoryAuditEventRepository);
    assertTrue(cacheManagerResult instanceof ConcurrentMapCacheManager);
    assertEquals("Hello World!", springBootAdminServletApplication.customEndpoint().invoke());
    assertFalse(((ConcurrentMapCacheManager) cacheManagerResult).isStoreByValue());
    assertTrue(((ConcurrentMapCacheManager) cacheManagerResult).isAllowNullValues());
    assertSame(justResult, actualFilterResult);
  }

  /**
   * Test {@link SpringBootAdminServletApplication#auditLog()}.
   *
   * <ul>
   *   <li>Then calls {@link ExchangeFunction#exchange(ClientRequest)}.
   * </ul>
   *
   * <p>Method under test: {@link SpringBootAdminServletApplication#auditLog()}
   */
  @Test
  @DisplayName("Test auditLog(); then calls exchange(ClientRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceExchangeFilterFunction SpringBootAdminServletApplication.auditLog()"})
  void testAuditLog_thenCallsExchange() throws AssertionError {
    // Arrange
    SpringBootAdminServletApplication springBootAdminServletApplication =
        new SpringBootAdminServletApplication();

    // Act
    InstanceExchangeFilterFunction actualAuditLogResult =
        springBootAdminServletApplication.auditLog();
    Instance instance = mock(Instance.class);
    ClientRequest request = mock(ClientRequest.class);
    ExchangeFunction next = mock(ExchangeFunction.class);
    ClientResponseWrapper delegate = mock(ClientResponseWrapper.class);
    ClientResponseWrapper clientResponseWrapper = new ClientResponseWrapper(delegate);
    Mono<ClientResponse> justResult = Mono.just(clientResponseWrapper);
    when(next.exchange(Mockito.<ClientRequest>any())).thenReturn(justResult);
    Mono<ClientResponse> actualPublisher = actualAuditLogResult.filter(instance, request, next);

    // Assert
    verify(next).exchange(isA(ClientRequest.class));
    CacheManager cacheManagerResult = springBootAdminServletApplication.cacheManager();
    Collection<String> cacheNames = cacheManagerResult.getCacheNames();
    assertEquals(1, cacheNames.size());
    assertTrue(cacheNames instanceof Set);
    assertTrue(
        springBootAdminServletApplication.auditEventRepository()
            instanceof InMemoryAuditEventRepository);
    assertTrue(cacheManagerResult instanceof ConcurrentMapCacheManager);
    assertEquals("Hello World!", springBootAdminServletApplication.customEndpoint().invoke());
    assertFalse(((ConcurrentMapCacheManager) cacheManagerResult).isStoreByValue());
    assertTrue(((ConcurrentMapCacheManager) cacheManagerResult).isAllowNullValues());
    FirstStep<ClientResponse> createResult = StepVerifier.create(actualPublisher);
    createResult
        .assertNext(
            c -> {
              assertSame(clientResponseWrapper, c);
              return;
            })
        .expectComplete()
        .verify();
  }

  /**
   * Test {@link SpringBootAdminServletApplication#customNotifier(InstanceRepository)}.
   *
   * <p>Method under test: {@link
   * SpringBootAdminServletApplication#customNotifier(InstanceRepository)}
   */
  @Test
  @DisplayName("Test customNotifier(InstanceRepository)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.sample.CustomNotifier SpringBootAdminServletApplication.customNotifier(InstanceRepository)"
  })
  void testCustomNotifier() {
    // Arrange
    SpringBootAdminServletApplication springBootAdminServletApplication =
        new SpringBootAdminServletApplication();

    // Act and Assert
    assertTrue(
        springBootAdminServletApplication
            .customNotifier(new EventsourcingInstanceRepository(new InMemoryEventStore()))
            .isEnabled());
  }

  /**
   * Test {@link SpringBootAdminServletApplication#customEndpoint()}.
   *
   * <p>Method under test: {@link SpringBootAdminServletApplication#customEndpoint()}
   */
  @Test
  @DisplayName("Test customEndpoint()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.sample.CustomEndpoint SpringBootAdminServletApplication.customEndpoint()"
  })
  void testCustomEndpoint() {
    // Arrange, Act and Assert
    assertEquals("Hello World!", new SpringBootAdminServletApplication().customEndpoint().invoke());
  }

  /**
   * Test {@link SpringBootAdminServletApplication#customHttpHeadersProvider()}.
   *
   * <p>Method under test: {@link SpringBootAdminServletApplication#customHttpHeadersProvider()}
   */
  @Test
  @DisplayName("Test customHttpHeadersProvider()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.web.client.HttpHeadersProvider SpringBootAdminServletApplication.customHttpHeadersProvider()"
  })
  void testCustomHttpHeadersProvider() {
    // Arrange and Act
    HttpHeaders actualHeaders =
        new SpringBootAdminServletApplication()
            .customHttpHeadersProvider()
            .getHeaders(mock(Instance.class));

    // Assert
    assertEquals(1, actualHeaders.size());
    List<String> getResult = actualHeaders.get("X-CUSTOM");
    assertEquals(1, getResult.size());
    assertEquals("My Custom Value", getResult.get(0));
  }

  /**
   * Test {@link SpringBootAdminServletApplication#httpTraceRepository()}.
   *
   * <p>Method under test: {@link SpringBootAdminServletApplication#httpTraceRepository()}
   */
  @Test
  @DisplayName("Test httpTraceRepository()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HttpExchangeRepository SpringBootAdminServletApplication.httpTraceRepository()"
  })
  void testHttpTraceRepository() {
    // Arrange and Act
    HttpExchangeRepository actualHttpTraceRepositoryResult =
        new SpringBootAdminServletApplication().httpTraceRepository();

    // Assert
    assertTrue(actualHttpTraceRepositoryResult instanceof InMemoryHttpExchangeRepository);
    assertTrue(actualHttpTraceRepositoryResult.findAll().isEmpty());
  }

  /**
   * Test {@link SpringBootAdminServletApplication#auditEventRepository()}.
   *
   * <p>Method under test: {@link SpringBootAdminServletApplication#auditEventRepository()}
   */
  @Test
  @DisplayName("Test auditEventRepository()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AuditEventRepository SpringBootAdminServletApplication.auditEventRepository()"
  })
  void testAuditEventRepository() {
    // Arrange and Act
    AuditEventRepository actualAuditEventRepositoryResult =
        new SpringBootAdminServletApplication().auditEventRepository();

    // Assert
    assertTrue(actualAuditEventRepositoryResult instanceof InMemoryAuditEventRepository);
    assertTrue(actualAuditEventRepositoryResult.find("Principal", null, "Type").isEmpty());
  }
}
