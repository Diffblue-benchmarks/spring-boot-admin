package de.codecentric.boot.admin.server.ui.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.Cache;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AdminServerUiPropertiesDiffblueTest {
  /**
   * Test Cache {@link Cache#toCacheControl()}.
   *
   * <ul>
   *   <li>Given {@link Cache} (default constructor) MaxAge is {@code null}.
   *   <li>Then return HeaderValue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Cache#toCacheControl()}
   */
  @Test
  @DisplayName(
      "Test Cache toCacheControl(); given Cache (default constructor) MaxAge is 'null'; then return HeaderValue is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.springframework.http.CacheControl Cache.toCacheControl()"})
  void testCacheToCacheControl_givenCacheMaxAgeIsNull_thenReturnHeaderValueIsNull() {
    // Arrange
    Cache cache = new Cache();
    cache.setMaxAge(null);

    // Act and Assert
    assertNull(cache.toCacheControl().getHeaderValue());
  }

  /**
   * Test Cache {@link Cache#toCacheControl()}.
   *
   * <ul>
   *   <li>Given {@link Cache} (default constructor) NoCache is {@code true}.
   *   <li>Then return HeaderValue is {@code no-cache}.
   * </ul>
   *
   * <p>Method under test: {@link Cache#toCacheControl()}
   */
  @Test
  @DisplayName(
      "Test Cache toCacheControl(); given Cache (default constructor) NoCache is 'true'; then return HeaderValue is 'no-cache'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.springframework.http.CacheControl Cache.toCacheControl()"})
  void testCacheToCacheControl_givenCacheNoCacheIsTrue_thenReturnHeaderValueIsNoCache() {
    // Arrange
    Cache cache = new Cache();
    cache.setNoCache(true);

    // Act and Assert
    assertEquals("no-cache", cache.toCacheControl().getHeaderValue());
  }

  /**
   * Test Cache {@link Cache#toCacheControl()}.
   *
   * <ul>
   *   <li>Given {@link Cache} (default constructor) NoStore is {@code true}.
   *   <li>Then return HeaderValue is {@code no-store}.
   * </ul>
   *
   * <p>Method under test: {@link Cache#toCacheControl()}
   */
  @Test
  @DisplayName(
      "Test Cache toCacheControl(); given Cache (default constructor) NoStore is 'true'; then return HeaderValue is 'no-store'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.springframework.http.CacheControl Cache.toCacheControl()"})
  void testCacheToCacheControl_givenCacheNoStoreIsTrue_thenReturnHeaderValueIsNoStore() {
    // Arrange
    Cache cache = new Cache();
    cache.setNoStore(true);

    // Act and Assert
    assertEquals("no-store", cache.toCacheControl().getHeaderValue());
  }

  /**
   * Test Cache {@link Cache#toCacheControl()}.
   *
   * <ul>
   *   <li>Given {@link Cache} (default constructor).
   *   <li>Then return HeaderValue is {@code max-age=3600}.
   * </ul>
   *
   * <p>Method under test: {@link Cache#toCacheControl()}
   */
  @Test
  @DisplayName(
      "Test Cache toCacheControl(); given Cache (default constructor); then return HeaderValue is 'max-age=3600'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.springframework.http.CacheControl Cache.toCacheControl()"})
  void testCacheToCacheControl_givenCache_thenReturnHeaderValueIsMaxAge3600() {
    // Arrange, Act and Assert
    assertEquals("max-age=3600", new Cache().toCacheControl().getHeaderValue());
  }
}
