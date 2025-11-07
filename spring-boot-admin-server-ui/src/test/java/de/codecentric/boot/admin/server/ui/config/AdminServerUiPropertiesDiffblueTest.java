package de.codecentric.boot.admin.server.ui.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.Cache;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.Palette;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.PollTimer;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties.UiTheme;
import de.codecentric.boot.admin.server.ui.web.UiController;
import de.codecentric.boot.admin.server.ui.web.UiController.ExternalView;
import de.codecentric.boot.admin.server.ui.web.UiController.ViewSettings;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AdminServerUiPropertiesDiffblueTest {
  /**
   * Test Cache {@link Cache#equals(Object)}, and {@link Cache#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Cache#equals(Object)}
   *   <li>{@link Cache#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Cache equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Cache cache = new Cache();
    Cache cache2 = new Cache();

    // Act and Assert
    assertEquals(cache, cache2);
    int expectedHashCodeResult = cache.hashCode();
    assertEquals(expectedHashCodeResult, cache2.hashCode());
  }

  /**
   * Test Cache {@link Cache#equals(Object)}, and {@link Cache#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Cache#equals(Object)}
   *   <li>{@link Cache#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Cache equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Cache cache = new Cache();
    cache.setMaxAge(null);

    Cache cache2 = new Cache();
    cache2.setMaxAge(null);

    // Act and Assert
    assertEquals(cache, cache2);
    int expectedHashCodeResult = cache.hashCode();
    assertEquals(expectedHashCodeResult, cache2.hashCode());
  }

  /**
   * Test Cache {@link Cache#equals(Object)}, and {@link Cache#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Cache#equals(Object)}
   *   <li>{@link Cache#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Cache equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Cache cache = new Cache();

    // Act and Assert
    assertEquals(cache, cache);
    int expectedHashCodeResult = cache.hashCode();
    assertEquals(expectedHashCodeResult, cache.hashCode());
  }

  /**
   * Test Cache {@link Cache#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#equals(Object)}
   */
  @Test
  @DisplayName("Test Cache equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Cache(), 1);
  }

  /**
   * Test Cache {@link Cache#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#equals(Object)}
   */
  @Test
  @DisplayName("Test Cache equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Cache cache = new Cache();
    cache.setMaxAge(null);

    // Act and Assert
    assertNotEquals(cache, new Cache());
  }

  /**
   * Test Cache {@link Cache#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#equals(Object)}
   */
  @Test
  @DisplayName("Test Cache equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Cache cache = new Cache();
    cache.setNoCache(true);

    // Act and Assert
    assertNotEquals(cache, new Cache());
  }

  /**
   * Test Cache {@link Cache#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#equals(Object)}
   */
  @Test
  @DisplayName("Test Cache equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Cache cache = new Cache();
    cache.setNoStore(true);

    // Act and Assert
    assertNotEquals(cache, new Cache());
  }

  /**
   * Test Cache {@link Cache#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#equals(Object)}
   */
  @Test
  @DisplayName("Test Cache equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Cache cache = new Cache();

    Cache cache2 = new Cache();
    cache2.setMaxAge(null);

    // Act and Assert
    assertNotEquals(cache, cache2);
  }

  /**
   * Test Cache {@link Cache#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#equals(Object)}
   */
  @Test
  @DisplayName("Test Cache equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Cache(), null);
  }

  /**
   * Test Cache {@link Cache#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#equals(Object)}
   */
  @Test
  @DisplayName("Test Cache equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Cache.equals(Object)", "int Cache.hashCode()"})
  void testCacheEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Cache(), "Different type to Cache");
  }

  /**
   * Test Cache getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Cache#setMaxAge(Duration)}
   *   <li>{@link Cache#setNoCache(Boolean)}
   *   <li>{@link Cache#setNoStore(Boolean)}
   *   <li>{@link Cache#toString()}
   *   <li>{@link Cache#getMaxAge()}
   *   <li>{@link Cache#getNoCache()}
   *   <li>{@link Cache#getNoStore()}
   * </ul>
   */
  @Test
  @DisplayName("Test Cache getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Duration Cache.getMaxAge()", "Boolean Cache.getNoCache()", "Boolean Cache.getNoStore()",
      "void Cache.setMaxAge(Duration)", "void Cache.setNoCache(Boolean)", "void Cache.setNoStore(Boolean)",
      "String Cache.toString()"})
  void testCacheGettersAndSetters() {
    // Arrange
    Cache cache = new Cache();

    // Act
    cache.setMaxAge(null);
    cache.setNoCache(true);
    cache.setNoStore(true);
    String actualToStringResult = cache.toString();
    Duration actualMaxAge = cache.getMaxAge();
    Boolean actualNoCache = cache.getNoCache();

    // Assert
    assertEquals("AdminServerUiProperties.Cache(maxAge=null, noCache=true, noStore=true)", actualToStringResult);
    assertNull(actualMaxAge);
    assertTrue(actualNoCache);
    assertTrue(cache.getNoStore());
  }

  /**
   * Test Cache new {@link Cache} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link Cache}
   */
  @Test
  @DisplayName("Test Cache new Cache (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Cache.<init>()"})
  void testCacheNewCache() {
    // Arrange and Act
    Cache actualCache = new Cache();

    // Assert
    assertEquals(3600000000000L, actualCache.getMaxAge().toNanos());
    assertFalse(actualCache.getNoCache());
    assertFalse(actualCache.getNoStore());
  }

  /**
   * Test Cache {@link Cache#toCacheControl()}.
   * <ul>
   *   <li>Given {@link Cache} (default constructor) MaxAge is {@code null}.</li>
   *   <li>Then return HeaderValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#toCacheControl()}
   */
  @Test
  @DisplayName("Test Cache toCacheControl(); given Cache (default constructor) MaxAge is 'null'; then return HeaderValue is 'null'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Cache} (default constructor) NoCache is {@code true}.</li>
   *   <li>Then return HeaderValue is {@code no-cache}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#toCacheControl()}
   */
  @Test
  @DisplayName("Test Cache toCacheControl(); given Cache (default constructor) NoCache is 'true'; then return HeaderValue is 'no-cache'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Cache} (default constructor) NoStore is {@code true}.</li>
   *   <li>Then return HeaderValue is {@code no-store}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#toCacheControl()}
   */
  @Test
  @DisplayName("Test Cache toCacheControl(); given Cache (default constructor) NoStore is 'true'; then return HeaderValue is 'no-store'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Cache} (default constructor).</li>
   *   <li>Then return HeaderValue is {@code max-age=3600}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#toCacheControl()}
   */
  @Test
  @DisplayName("Test Cache toCacheControl(); given Cache (default constructor); then return HeaderValue is 'max-age=3600'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.springframework.http.CacheControl Cache.toCacheControl()"})
  void testCacheToCacheControl_givenCache_thenReturnHeaderValueIsMaxAge3600() {
    // Arrange, Act and Assert
    assertEquals("max-age=3600", (new Cache()).toCacheControl().getHeaderValue());
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}, and {@link AdminServerUiProperties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties#equals(Object)}
   *   <li>{@link AdminServerUiProperties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertEquals(adminServerUiProperties, adminServerUiProperties);
    int expectedHashCodeResult = adminServerUiProperties.hashCode();
    assertEquals(expectedHashCodeResult, adminServerUiProperties.hashCode());
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new Cache());
    adminServerUiProperties2.setCacheTemplates(true);
    adminServerUiProperties2.setEnableToasts(true);
    adminServerUiProperties2.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties2.setExternalViews(new ArrayList<>());
    adminServerUiProperties2.setFavicon("Favicon");
    adminServerUiProperties2.setFaviconDanger("Favicon Danger");
    adminServerUiProperties2.setHideInstanceUrl(true);
    adminServerUiProperties2.setLoginIcon("Login Icon");
    adminServerUiProperties2.setPollTimer(pollTimer2);
    adminServerUiProperties2.setPublicUrl("https://example.org/example");
    adminServerUiProperties2.setRememberMeEnabled(true);
    adminServerUiProperties2.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties2.setTemplateLocation("Template Location");
    adminServerUiProperties2.setTheme(theme2);
    adminServerUiProperties2.setTitle("Dr");
    adminServerUiProperties2.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, adminServerUiProperties2);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<String> additionalRouteExcludes = new ArrayList<>();
    additionalRouteExcludes.add("Template Location");

    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(additionalRouteExcludes);
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new Cache());
    adminServerUiProperties2.setCacheTemplates(true);
    adminServerUiProperties2.setEnableToasts(true);
    adminServerUiProperties2.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties2.setExternalViews(new ArrayList<>());
    adminServerUiProperties2.setFavicon("Favicon");
    adminServerUiProperties2.setFaviconDanger("Favicon Danger");
    adminServerUiProperties2.setHideInstanceUrl(true);
    adminServerUiProperties2.setLoginIcon("Login Icon");
    adminServerUiProperties2.setPollTimer(pollTimer2);
    adminServerUiProperties2.setPublicUrl("https://example.org/example");
    adminServerUiProperties2.setRememberMeEnabled(true);
    adminServerUiProperties2.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties2.setTemplateLocation("Template Location");
    adminServerUiProperties2.setTheme(theme2);
    adminServerUiProperties2.setTitle("Dr");
    adminServerUiProperties2.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, adminServerUiProperties2);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ArrayList<String> availableLanguages = new ArrayList<>();
    availableLanguages.add("Template Location");

    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(availableLanguages);
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new Cache());
    adminServerUiProperties2.setCacheTemplates(true);
    adminServerUiProperties2.setEnableToasts(true);
    adminServerUiProperties2.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties2.setExternalViews(new ArrayList<>());
    adminServerUiProperties2.setFavicon("Favicon");
    adminServerUiProperties2.setFaviconDanger("Favicon Danger");
    adminServerUiProperties2.setHideInstanceUrl(true);
    adminServerUiProperties2.setLoginIcon("Login Icon");
    adminServerUiProperties2.setPollTimer(pollTimer2);
    adminServerUiProperties2.setPublicUrl("https://example.org/example");
    adminServerUiProperties2.setRememberMeEnabled(true);
    adminServerUiProperties2.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties2.setTemplateLocation("Template Location");
    adminServerUiProperties2.setTheme(theme2);
    adminServerUiProperties2.setTitle("Dr");
    adminServerUiProperties2.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, adminServerUiProperties2);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Template Location");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new Cache());
    adminServerUiProperties2.setCacheTemplates(true);
    adminServerUiProperties2.setEnableToasts(true);
    adminServerUiProperties2.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties2.setExternalViews(new ArrayList<>());
    adminServerUiProperties2.setFavicon("Favicon");
    adminServerUiProperties2.setFaviconDanger("Favicon Danger");
    adminServerUiProperties2.setHideInstanceUrl(true);
    adminServerUiProperties2.setLoginIcon("Login Icon");
    adminServerUiProperties2.setPollTimer(pollTimer2);
    adminServerUiProperties2.setPublicUrl("https://example.org/example");
    adminServerUiProperties2.setRememberMeEnabled(true);
    adminServerUiProperties2.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties2.setTemplateLocation("Template Location");
    adminServerUiProperties2.setTheme(theme2);
    adminServerUiProperties2.setTitle("Dr");
    adminServerUiProperties2.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, adminServerUiProperties2);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(mock(Cache.class));
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new Cache());
    adminServerUiProperties2.setCacheTemplates(true);
    adminServerUiProperties2.setEnableToasts(true);
    adminServerUiProperties2.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties2.setExternalViews(new ArrayList<>());
    adminServerUiProperties2.setFavicon("Favicon");
    adminServerUiProperties2.setFaviconDanger("Favicon Danger");
    adminServerUiProperties2.setHideInstanceUrl(true);
    adminServerUiProperties2.setLoginIcon("Login Icon");
    adminServerUiProperties2.setPollTimer(pollTimer2);
    adminServerUiProperties2.setPublicUrl("https://example.org/example");
    adminServerUiProperties2.setRememberMeEnabled(true);
    adminServerUiProperties2.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties2.setTemplateLocation("Template Location");
    adminServerUiProperties2.setTheme(theme2);
    adminServerUiProperties2.setTitle("Dr");
    adminServerUiProperties2.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, adminServerUiProperties2);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(false);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new Cache());
    adminServerUiProperties2.setCacheTemplates(true);
    adminServerUiProperties2.setEnableToasts(true);
    adminServerUiProperties2.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties2.setExternalViews(new ArrayList<>());
    adminServerUiProperties2.setFavicon("Favicon");
    adminServerUiProperties2.setFaviconDanger("Favicon Danger");
    adminServerUiProperties2.setHideInstanceUrl(true);
    adminServerUiProperties2.setLoginIcon("Login Icon");
    adminServerUiProperties2.setPollTimer(pollTimer2);
    adminServerUiProperties2.setPublicUrl("https://example.org/example");
    adminServerUiProperties2.setRememberMeEnabled(true);
    adminServerUiProperties2.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties2.setTemplateLocation("Template Location");
    adminServerUiProperties2.setTheme(theme2);
    adminServerUiProperties2.setTitle("Dr");
    adminServerUiProperties2.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, adminServerUiProperties2);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(false);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new Cache());
    adminServerUiProperties2.setCacheTemplates(true);
    adminServerUiProperties2.setEnableToasts(true);
    adminServerUiProperties2.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties2.setExternalViews(new ArrayList<>());
    adminServerUiProperties2.setFavicon("Favicon");
    adminServerUiProperties2.setFaviconDanger("Favicon Danger");
    adminServerUiProperties2.setHideInstanceUrl(true);
    adminServerUiProperties2.setLoginIcon("Login Icon");
    adminServerUiProperties2.setPollTimer(pollTimer2);
    adminServerUiProperties2.setPublicUrl("https://example.org/example");
    adminServerUiProperties2.setRememberMeEnabled(true);
    adminServerUiProperties2.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties2.setTemplateLocation("Template Location");
    adminServerUiProperties2.setTheme(theme2);
    adminServerUiProperties2.setTitle("Dr");
    adminServerUiProperties2.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, adminServerUiProperties2);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Template Location"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new Cache());
    adminServerUiProperties2.setCacheTemplates(true);
    adminServerUiProperties2.setEnableToasts(true);
    adminServerUiProperties2.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties2.setExternalViews(new ArrayList<>());
    adminServerUiProperties2.setFavicon("Favicon");
    adminServerUiProperties2.setFaviconDanger("Favicon Danger");
    adminServerUiProperties2.setHideInstanceUrl(true);
    adminServerUiProperties2.setLoginIcon("Login Icon");
    adminServerUiProperties2.setPollTimer(pollTimer2);
    adminServerUiProperties2.setPublicUrl("https://example.org/example");
    adminServerUiProperties2.setRememberMeEnabled(true);
    adminServerUiProperties2.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties2.setTemplateLocation("Template Location");
    adminServerUiProperties2.setTheme(theme2);
    adminServerUiProperties2.setTitle("Dr");
    adminServerUiProperties2.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, adminServerUiProperties2);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    ArrayList<ExternalView> externalViews = new ArrayList<>();
    externalViews.add(new ExternalView("Template Location", "https://example.org/example", 1, true, new ArrayList<>()));

    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(externalViews);
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme theme2 = new UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new Cache());
    adminServerUiProperties2.setCacheTemplates(true);
    adminServerUiProperties2.setEnableToasts(true);
    adminServerUiProperties2.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties2.setExternalViews(new ArrayList<>());
    adminServerUiProperties2.setFavicon("Favicon");
    adminServerUiProperties2.setFaviconDanger("Favicon Danger");
    adminServerUiProperties2.setHideInstanceUrl(true);
    adminServerUiProperties2.setLoginIcon("Login Icon");
    adminServerUiProperties2.setPollTimer(pollTimer2);
    adminServerUiProperties2.setPublicUrl("https://example.org/example");
    adminServerUiProperties2.setRememberMeEnabled(true);
    adminServerUiProperties2.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties2.setTemplateLocation("Template Location");
    adminServerUiProperties2.setTheme(theme2);
    adminServerUiProperties2.setTitle("Dr");
    adminServerUiProperties2.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, adminServerUiProperties2);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, null);
  }

  /**
   * Test {@link AdminServerUiProperties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AdminServerUiProperties.equals(Object)", "int AdminServerUiProperties.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    adminServerUiProperties.setExtensionResourceLocations(new String[]{"Extension Resource Locations"});
    adminServerUiProperties.setExternalViews(new ArrayList<>());
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    adminServerUiProperties.setResourceLocations(new String[]{"Resource Locations"});
    adminServerUiProperties.setTemplateLocation("Template Location");
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    adminServerUiProperties.setViewSettings(new ArrayList<>());

    // Act and Assert
    assertNotEquals(adminServerUiProperties, "Different type to AdminServerUiProperties");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties#setAdditionalRouteExcludes(List)}
   *   <li>{@link AdminServerUiProperties#setAvailableLanguages(List)}
   *   <li>{@link AdminServerUiProperties#setBrand(String)}
   *   <li>{@link AdminServerUiProperties#setCache(Cache)}
   *   <li>{@link AdminServerUiProperties#setCacheTemplates(boolean)}
   *   <li>{@link AdminServerUiProperties#setEnableToasts(Boolean)}
   *   <li>{@link AdminServerUiProperties#setExtensionResourceLocations(String[])}
   *   <li>{@link AdminServerUiProperties#setExternalViews(List)}
   *   <li>{@link AdminServerUiProperties#setFavicon(String)}
   *   <li>{@link AdminServerUiProperties#setFaviconDanger(String)}
   *   <li>{@link AdminServerUiProperties#setHideInstanceUrl(Boolean)}
   *   <li>{@link AdminServerUiProperties#setLoginIcon(String)}
   *   <li>{@link AdminServerUiProperties#setPollTimer(PollTimer)}
   *   <li>{@link AdminServerUiProperties#setPublicUrl(String)}
   *   <li>{@link AdminServerUiProperties#setRememberMeEnabled(boolean)}
   *   <li>{@link AdminServerUiProperties#setResourceLocations(String[])}
   *   <li>{@link AdminServerUiProperties#setTemplateLocation(String)}
   *   <li>{@link AdminServerUiProperties#setTheme(UiTheme)}
   *   <li>{@link AdminServerUiProperties#setTitle(String)}
   *   <li>{@link AdminServerUiProperties#setViewSettings(List)}
   *   <li>{@link AdminServerUiProperties#toString()}
   *   <li>{@link AdminServerUiProperties#getAdditionalRouteExcludes()}
   *   <li>{@link AdminServerUiProperties#getAvailableLanguages()}
   *   <li>{@link AdminServerUiProperties#getBrand()}
   *   <li>{@link AdminServerUiProperties#getCache()}
   *   <li>{@link AdminServerUiProperties#getEnableToasts()}
   *   <li>{@link AdminServerUiProperties#getExtensionResourceLocations()}
   *   <li>{@link AdminServerUiProperties#getExternalViews()}
   *   <li>{@link AdminServerUiProperties#getFavicon()}
   *   <li>{@link AdminServerUiProperties#getFaviconDanger()}
   *   <li>{@link AdminServerUiProperties#getHideInstanceUrl()}
   *   <li>{@link AdminServerUiProperties#getLoginIcon()}
   *   <li>{@link AdminServerUiProperties#getPollTimer()}
   *   <li>{@link AdminServerUiProperties#getPublicUrl()}
   *   <li>{@link AdminServerUiProperties#getResourceLocations()}
   *   <li>{@link AdminServerUiProperties#getTemplateLocation()}
   *   <li>{@link AdminServerUiProperties#getTheme()}
   *   <li>{@link AdminServerUiProperties#getTitle()}
   *   <li>{@link AdminServerUiProperties#getViewSettings()}
   *   <li>{@link AdminServerUiProperties#isCacheTemplates()}
   *   <li>{@link AdminServerUiProperties#isRememberMeEnabled()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AdminServerUiProperties.getAdditionalRouteExcludes()",
      "List AdminServerUiProperties.getAvailableLanguages()", "String AdminServerUiProperties.getBrand()",
      "Cache AdminServerUiProperties.getCache()", "Boolean AdminServerUiProperties.getEnableToasts()",
      "String[] AdminServerUiProperties.getExtensionResourceLocations()",
      "List AdminServerUiProperties.getExternalViews()", "String AdminServerUiProperties.getFavicon()",
      "String AdminServerUiProperties.getFaviconDanger()", "Boolean AdminServerUiProperties.getHideInstanceUrl()",
      "String AdminServerUiProperties.getLoginIcon()", "PollTimer AdminServerUiProperties.getPollTimer()",
      "String AdminServerUiProperties.getPublicUrl()", "String[] AdminServerUiProperties.getResourceLocations()",
      "String AdminServerUiProperties.getTemplateLocation()", "UiTheme AdminServerUiProperties.getTheme()",
      "String AdminServerUiProperties.getTitle()", "List AdminServerUiProperties.getViewSettings()",
      "boolean AdminServerUiProperties.isCacheTemplates()", "boolean AdminServerUiProperties.isRememberMeEnabled()",
      "void AdminServerUiProperties.setAdditionalRouteExcludes(List)",
      "void AdminServerUiProperties.setAvailableLanguages(List)", "void AdminServerUiProperties.setBrand(String)",
      "void AdminServerUiProperties.setCache(Cache)", "void AdminServerUiProperties.setCacheTemplates(boolean)",
      "void AdminServerUiProperties.setEnableToasts(Boolean)",
      "void AdminServerUiProperties.setExtensionResourceLocations(String[])",
      "void AdminServerUiProperties.setExternalViews(List)", "void AdminServerUiProperties.setFavicon(String)",
      "void AdminServerUiProperties.setFaviconDanger(String)",
      "void AdminServerUiProperties.setHideInstanceUrl(Boolean)", "void AdminServerUiProperties.setLoginIcon(String)",
      "void AdminServerUiProperties.setPollTimer(PollTimer)", "void AdminServerUiProperties.setPublicUrl(String)",
      "void AdminServerUiProperties.setRememberMeEnabled(boolean)",
      "void AdminServerUiProperties.setResourceLocations(String[])",
      "void AdminServerUiProperties.setTemplateLocation(String)", "void AdminServerUiProperties.setTheme(UiTheme)",
      "void AdminServerUiProperties.setTitle(String)", "void AdminServerUiProperties.setViewSettings(List)",
      "String AdminServerUiProperties.toString()"})
  void testGettersAndSetters() {
    // Arrange
    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    ArrayList<String> additionalRouteExcludes = new ArrayList<>();

    // Act
    adminServerUiProperties.setAdditionalRouteExcludes(additionalRouteExcludes);
    ArrayList<String> availableLanguages = new ArrayList<>();
    adminServerUiProperties.setAvailableLanguages(availableLanguages);
    adminServerUiProperties.setBrand("Brand");
    Cache cache = new Cache();
    adminServerUiProperties.setCache(cache);
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    String[] extensionResourceLocations = new String[]{"Extension Resource Locations"};
    adminServerUiProperties.setExtensionResourceLocations(extensionResourceLocations);
    ArrayList<ExternalView> externalViews = new ArrayList<>();
    adminServerUiProperties.setExternalViews(externalViews);
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);
    adminServerUiProperties.setPollTimer(pollTimer);
    adminServerUiProperties.setPublicUrl("https://example.org/example");
    adminServerUiProperties.setRememberMeEnabled(true);
    String[] resourceLocations = new String[]{"Resource Locations"};
    adminServerUiProperties.setResourceLocations(resourceLocations);
    adminServerUiProperties.setTemplateLocation("Template Location");
    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");
    UiTheme theme = new UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    ArrayList<ViewSettings> viewSettings = new ArrayList<>();
    adminServerUiProperties.setViewSettings(viewSettings);
    adminServerUiProperties.toString();
    List<String> actualAdditionalRouteExcludes = adminServerUiProperties.getAdditionalRouteExcludes();
    List<String> actualAvailableLanguages = adminServerUiProperties.getAvailableLanguages();
    String actualBrand = adminServerUiProperties.getBrand();
    Cache actualCache = adminServerUiProperties.getCache();
    Boolean actualEnableToasts = adminServerUiProperties.getEnableToasts();
    String[] actualExtensionResourceLocations = adminServerUiProperties.getExtensionResourceLocations();
    List<ExternalView> actualExternalViews = adminServerUiProperties.getExternalViews();
    String actualFavicon = adminServerUiProperties.getFavicon();
    String actualFaviconDanger = adminServerUiProperties.getFaviconDanger();
    Boolean actualHideInstanceUrl = adminServerUiProperties.getHideInstanceUrl();
    String actualLoginIcon = adminServerUiProperties.getLoginIcon();
    PollTimer actualPollTimer = adminServerUiProperties.getPollTimer();
    String actualPublicUrl = adminServerUiProperties.getPublicUrl();
    String[] actualResourceLocations = adminServerUiProperties.getResourceLocations();
    String actualTemplateLocation = adminServerUiProperties.getTemplateLocation();
    UiTheme actualTheme = adminServerUiProperties.getTheme();
    String actualTitle = adminServerUiProperties.getTitle();
    List<ViewSettings> actualViewSettings = adminServerUiProperties.getViewSettings();
    boolean actualIsCacheTemplatesResult = adminServerUiProperties.isCacheTemplates();

    // Assert
    assertEquals("Brand", actualBrand);
    assertEquals("Dr", actualTitle);
    assertEquals("Favicon Danger", actualFaviconDanger);
    assertEquals("Favicon", actualFavicon);
    assertEquals("Login Icon", actualLoginIcon);
    assertEquals("Template Location", actualTemplateLocation);
    assertEquals("https://example.org/example", actualPublicUrl);
    assertTrue(actualEnableToasts);
    assertTrue(actualHideInstanceUrl);
    assertTrue(actualIsCacheTemplatesResult);
    assertTrue(adminServerUiProperties.isRememberMeEnabled());
    assertTrue(actualAdditionalRouteExcludes.isEmpty());
    assertTrue(actualAvailableLanguages.isEmpty());
    assertTrue(actualExternalViews.isEmpty());
    assertTrue(actualViewSettings.isEmpty());
    assertSame(cache, actualCache);
    assertSame(pollTimer, actualPollTimer);
    assertSame(theme, actualTheme);
    assertSame(additionalRouteExcludes, actualAdditionalRouteExcludes);
    assertSame(availableLanguages, actualAvailableLanguages);
    assertSame(externalViews, actualExternalViews);
    assertSame(viewSettings, actualViewSettings);
    assertSame(extensionResourceLocations, actualExtensionResourceLocations);
    assertSame(resourceLocations, actualResourceLocations);
    assertArrayEquals(new String[]{"Extension Resource Locations"}, actualExtensionResourceLocations);
    assertArrayEquals(new String[]{"Resource Locations"}, actualResourceLocations);
  }

  /**
   * Test new {@link AdminServerUiProperties} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link AdminServerUiProperties}
   */
  @Test
  @DisplayName("Test new AdminServerUiProperties (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AdminServerUiProperties.<init>()"})
  void testNewAdminServerUiProperties() {
    // Arrange and Act
    AdminServerUiProperties actualAdminServerUiProperties = new AdminServerUiProperties();

    // Assert
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        actualAdminServerUiProperties.getBrand());
    assertEquals("Spring Boot Admin", actualAdminServerUiProperties.getTitle());
    assertEquals("assets/img/favicon-danger.png", actualAdminServerUiProperties.getFaviconDanger());
    assertEquals("assets/img/favicon.png", actualAdminServerUiProperties.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", actualAdminServerUiProperties.getLoginIcon());
    assertEquals("classpath:/META-INF/spring-boot-admin-server-ui/",
        actualAdminServerUiProperties.getTemplateLocation());
    assertNull(actualAdminServerUiProperties.getPublicUrl());
    assertEquals(1, actualAdminServerUiProperties.getExtensionResourceLocations().length);
    assertEquals(1, actualAdminServerUiProperties.getResourceLocations().length);
    assertFalse(actualAdminServerUiProperties.getEnableToasts());
    assertFalse(actualAdminServerUiProperties.getHideInstanceUrl());
    assertTrue(actualAdminServerUiProperties.isCacheTemplates());
    assertTrue(actualAdminServerUiProperties.isRememberMeEnabled());
    assertTrue(actualAdminServerUiProperties.getAdditionalRouteExcludes().isEmpty());
    assertTrue(actualAdminServerUiProperties.getAvailableLanguages().isEmpty());
    assertTrue(actualAdminServerUiProperties.getExternalViews().isEmpty());
    assertTrue(actualAdminServerUiProperties.getViewSettings().isEmpty());
  }

  /**
   * Test Palette getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Palette}
   *   <li>{@link Palette#set100(String)}
   *   <li>{@link Palette#set200(String)}
   *   <li>{@link Palette#set300(String)}
   *   <li>{@link Palette#set400(String)}
   *   <li>{@link Palette#set500(String)}
   *   <li>{@link Palette#set50(String)}
   *   <li>{@link Palette#set600(String)}
   *   <li>{@link Palette#set700(String)}
   *   <li>{@link Palette#set800(String)}
   *   <li>{@link Palette#set900(String)}
   *   <li>{@link Palette#getShade100()}
   *   <li>{@link Palette#getShade200()}
   *   <li>{@link Palette#getShade300()}
   *   <li>{@link Palette#getShade400()}
   *   <li>{@link Palette#getShade50()}
   *   <li>{@link Palette#getShade500()}
   *   <li>{@link Palette#getShade600()}
   *   <li>{@link Palette#getShade700()}
   *   <li>{@link Palette#getShade800()}
   *   <li>{@link Palette#getShade900()}
   * </ul>
   */
  @Test
  @DisplayName("Test Palette getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Palette.<init>()", "String Palette.getShade100()", "String Palette.getShade200()",
      "String Palette.getShade300()", "String Palette.getShade400()", "String Palette.getShade50()",
      "String Palette.getShade500()", "String Palette.getShade600()", "String Palette.getShade700()",
      "String Palette.getShade800()", "String Palette.getShade900()", "void Palette.set100(String)",
      "void Palette.set200(String)", "void Palette.set300(String)", "void Palette.set400(String)",
      "void Palette.set50(String)", "void Palette.set500(String)", "void Palette.set600(String)",
      "void Palette.set700(String)", "void Palette.set800(String)", "void Palette.set900(String)"})
  void testPaletteGettersAndSetters() {
    // Arrange and Act
    Palette actualPalette = new Palette();
    actualPalette.set100("Shade100");
    actualPalette.set200("Shade200");
    actualPalette.set300("Shade300");
    actualPalette.set400("Shade400");
    actualPalette.set500("Shade500");
    actualPalette.set50("Shade50");
    actualPalette.set600("Shade600");
    actualPalette.set700("Shade700");
    actualPalette.set800("Shade800");
    actualPalette.set900("Shade900");
    String actualShade100 = actualPalette.getShade100();
    String actualShade200 = actualPalette.getShade200();
    String actualShade300 = actualPalette.getShade300();
    String actualShade400 = actualPalette.getShade400();
    String actualShade50 = actualPalette.getShade50();
    String actualShade500 = actualPalette.getShade500();
    String actualShade600 = actualPalette.getShade600();
    String actualShade700 = actualPalette.getShade700();
    String actualShade800 = actualPalette.getShade800();

    // Assert
    assertEquals("Shade100", actualShade100);
    assertEquals("Shade200", actualShade200);
    assertEquals("Shade300", actualShade300);
    assertEquals("Shade400", actualShade400);
    assertEquals("Shade50", actualShade50);
    assertEquals("Shade500", actualShade500);
    assertEquals("Shade600", actualShade600);
    assertEquals("Shade700", actualShade700);
    assertEquals("Shade800", actualShade800);
    assertEquals("Shade900", actualPalette.getShade900());
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}, and {@link PollTimer#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PollTimer#equals(Object)}
   *   <li>{@link PollTimer#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test PollTimer equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    // Act and Assert
    assertEquals(pollTimer, pollTimer2);
    int expectedHashCodeResult = pollTimer.hashCode();
    assertEquals(expectedHashCodeResult, pollTimer2.hashCode());
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}, and {@link PollTimer#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PollTimer#equals(Object)}
   *   <li>{@link PollTimer#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test PollTimer equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    // Act and Assert
    assertEquals(pollTimer, pollTimer);
    int expectedHashCodeResult = pollTimer.hashCode();
    assertEquals(expectedHashCodeResult, pollTimer.hashCode());
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PollTimer#equals(Object)}
   */
  @Test
  @DisplayName("Test PollTimer equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(3);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    // Act and Assert
    assertNotEquals(pollTimer, pollTimer2);
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PollTimer#equals(Object)}
   */
  @Test
  @DisplayName("Test PollTimer equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(3);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    // Act and Assert
    assertNotEquals(pollTimer, pollTimer2);
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PollTimer#equals(Object)}
   */
  @Test
  @DisplayName("Test PollTimer equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(3);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    // Act and Assert
    assertNotEquals(pollTimer, pollTimer2);
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PollTimer#equals(Object)}
   */
  @Test
  @DisplayName("Test PollTimer equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(3);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    // Act and Assert
    assertNotEquals(pollTimer, pollTimer2);
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PollTimer#equals(Object)}
   */
  @Test
  @DisplayName("Test PollTimer equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(3);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    // Act and Assert
    assertNotEquals(pollTimer, pollTimer2);
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PollTimer#equals(Object)}
   */
  @Test
  @DisplayName("Test PollTimer equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(3);
    pollTimer.setThreads(1);

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    // Act and Assert
    assertNotEquals(pollTimer, pollTimer2);
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PollTimer#equals(Object)}
   */
  @Test
  @DisplayName("Test PollTimer equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(3);

    PollTimer pollTimer2 = new PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    // Act and Assert
    assertNotEquals(pollTimer, pollTimer2);
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PollTimer#equals(Object)}
   */
  @Test
  @DisplayName("Test PollTimer equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    // Act and Assert
    assertNotEquals(pollTimer, null);
  }

  /**
   * Test PollTimer {@link PollTimer#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PollTimer#equals(Object)}
   */
  @Test
  @DisplayName("Test PollTimer equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PollTimer.equals(Object)", "int PollTimer.hashCode()"})
  void testPollTimerEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    PollTimer pollTimer = new PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    // Act and Assert
    assertNotEquals(pollTimer, "Different type to PollTimer");
  }

  /**
   * Test PollTimer getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link PollTimer}
   *   <li>{@link PollTimer#setCache(int)}
   *   <li>{@link PollTimer#setDatasource(int)}
   *   <li>{@link PollTimer#setGc(int)}
   *   <li>{@link PollTimer#setLogfile(int)}
   *   <li>{@link PollTimer#setMemory(int)}
   *   <li>{@link PollTimer#setProcess(int)}
   *   <li>{@link PollTimer#setThreads(int)}
   *   <li>{@link PollTimer#toString()}
   *   <li>{@link PollTimer#getCache()}
   *   <li>{@link PollTimer#getDatasource()}
   *   <li>{@link PollTimer#getGc()}
   *   <li>{@link PollTimer#getLogfile()}
   *   <li>{@link PollTimer#getMemory()}
   *   <li>{@link PollTimer#getProcess()}
   *   <li>{@link PollTimer#getThreads()}
   * </ul>
   */
  @Test
  @DisplayName("Test PollTimer getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void PollTimer.<init>()", "int PollTimer.getCache()", "int PollTimer.getDatasource()",
      "int PollTimer.getGc()", "int PollTimer.getLogfile()", "int PollTimer.getMemory()", "int PollTimer.getProcess()",
      "int PollTimer.getThreads()", "void PollTimer.setCache(int)", "void PollTimer.setDatasource(int)",
      "void PollTimer.setGc(int)", "void PollTimer.setLogfile(int)", "void PollTimer.setMemory(int)",
      "void PollTimer.setProcess(int)", "void PollTimer.setThreads(int)", "String PollTimer.toString()"})
  void testPollTimerGettersAndSetters() {
    // Arrange and Act
    PollTimer actualPollTimer = new PollTimer();
    actualPollTimer.setCache(1);
    actualPollTimer.setDatasource(1);
    actualPollTimer.setGc(1);
    actualPollTimer.setLogfile(1);
    actualPollTimer.setMemory(1);
    actualPollTimer.setProcess(1);
    actualPollTimer.setThreads(1);
    String actualToStringResult = actualPollTimer.toString();
    int actualCache = actualPollTimer.getCache();
    int actualDatasource = actualPollTimer.getDatasource();
    int actualGc = actualPollTimer.getGc();
    int actualLogfile = actualPollTimer.getLogfile();
    int actualMemory = actualPollTimer.getMemory();
    int actualProcess = actualPollTimer.getProcess();

    // Assert
    assertEquals("AdminServerUiProperties.PollTimer(cache=1, datasource=1, gc=1, process=1, memory=1, threads=1,"
        + " logfile=1)", actualToStringResult);
    assertEquals(1, actualCache);
    assertEquals(1, actualDatasource);
    assertEquals(1, actualGc);
    assertEquals(1, actualLogfile);
    assertEquals(1, actualMemory);
    assertEquals(1, actualProcess);
    assertEquals(1, actualPollTimer.getThreads());
  }

  /**
   * Test UiTheme {@link UiTheme#equals(Object)}, and {@link UiTheme#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiTheme#equals(Object)}
   *   <li>{@link UiTheme#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test UiTheme equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiTheme.equals(Object)", "int UiTheme.hashCode()"})
  void testUiThemeEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme uiTheme = new UiTheme();
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    // Act and Assert
    assertEquals(uiTheme, uiTheme);
    int expectedHashCodeResult = uiTheme.hashCode();
    assertEquals(expectedHashCodeResult, uiTheme.hashCode());
  }

  /**
   * Test UiTheme {@link UiTheme#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiTheme#equals(Object)}
   */
  @Test
  @DisplayName("Test UiTheme equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiTheme.equals(Object)", "int UiTheme.hashCode()"})
  void testUiThemeEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme uiTheme = new UiTheme();
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme uiTheme2 = new UiTheme();
    uiTheme2.setBackgroundEnabled(true);
    uiTheme2.setColor("Color");
    uiTheme2.setPalette(palette2);

    // Act and Assert
    assertNotEquals(uiTheme, uiTheme2);
  }

  /**
   * Test UiTheme {@link UiTheme#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiTheme#equals(Object)}
   */
  @Test
  @DisplayName("Test UiTheme equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiTheme.equals(Object)", "int UiTheme.hashCode()"})
  void testUiThemeEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme uiTheme = new UiTheme();
    uiTheme.setBackgroundEnabled(false);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    Palette palette2 = new Palette();
    palette2.set100("Shade100");
    palette2.set200("Shade200");
    palette2.set300("Shade300");
    palette2.set400("Shade400");
    palette2.set50("Shade50");
    palette2.set500("Shade500");
    palette2.set600("Shade600");
    palette2.set700("Shade700");
    palette2.set800("Shade800");
    palette2.set900("Shade900");

    UiTheme uiTheme2 = new UiTheme();
    uiTheme2.setBackgroundEnabled(true);
    uiTheme2.setColor("Color");
    uiTheme2.setPalette(palette2);

    // Act and Assert
    assertNotEquals(uiTheme, uiTheme2);
  }

  /**
   * Test UiTheme {@link UiTheme#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiTheme#equals(Object)}
   */
  @Test
  @DisplayName("Test UiTheme equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiTheme.equals(Object)", "int UiTheme.hashCode()"})
  void testUiThemeEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme uiTheme = new UiTheme();
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    // Act and Assert
    assertNotEquals(uiTheme, null);
  }

  /**
   * Test UiTheme {@link UiTheme#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UiTheme#equals(Object)}
   */
  @Test
  @DisplayName("Test UiTheme equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UiTheme.equals(Object)", "int UiTheme.hashCode()"})
  void testUiThemeEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");

    UiTheme uiTheme = new UiTheme();
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    // Act and Assert
    assertNotEquals(uiTheme, "Different type to UiTheme");
  }

  /**
   * Test UiTheme getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UiTheme#setBackgroundEnabled(Boolean)}
   *   <li>{@link UiTheme#setColor(String)}
   *   <li>{@link UiTheme#setPalette(Palette)}
   *   <li>{@link UiTheme#toString()}
   *   <li>{@link UiTheme#getBackgroundEnabled()}
   *   <li>{@link UiTheme#getColor()}
   *   <li>{@link UiTheme#getPalette()}
   * </ul>
   */
  @Test
  @DisplayName("Test UiTheme getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Boolean UiTheme.getBackgroundEnabled()", "String UiTheme.getColor()",
      "Palette UiTheme.getPalette()", "void UiTheme.setBackgroundEnabled(Boolean)", "void UiTheme.setColor(String)",
      "void UiTheme.setPalette(Palette)", "String UiTheme.toString()"})
  void testUiThemeGettersAndSetters() {
    // Arrange
    UiTheme uiTheme = new UiTheme();

    // Act
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    Palette palette = new Palette();
    palette.set100("Shade100");
    palette.set200("Shade200");
    palette.set300("Shade300");
    palette.set400("Shade400");
    palette.set50("Shade50");
    palette.set500("Shade500");
    palette.set600("Shade600");
    palette.set700("Shade700");
    palette.set800("Shade800");
    palette.set900("Shade900");
    uiTheme.setPalette(palette);
    uiTheme.toString();
    Boolean actualBackgroundEnabled = uiTheme.getBackgroundEnabled();
    String actualColor = uiTheme.getColor();

    // Assert
    assertEquals("Color", actualColor);
    assertTrue(actualBackgroundEnabled);
    assertSame(palette, uiTheme.getPalette());
  }

  /**
   * Test UiTheme new {@link UiTheme} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link UiTheme}
   */
  @Test
  @DisplayName("Test UiTheme new UiTheme (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UiTheme.<init>()"})
  void testUiThemeNewUiTheme() {
    // Arrange and Act
    UiTheme actualUiTheme = new UiTheme();

    // Assert
    Palette palette = actualUiTheme.getPalette();
    assertEquals("#0A2F2B", palette.getShade900());
    assertEquals("#14615A", palette.getShade800());
    assertEquals("#14615A", actualUiTheme.getColor());
    assertEquals("#1E9084", palette.getShade700());
    assertEquals("#27BEAF", palette.getShade600());
    assertEquals("#47D9CB", palette.getShade500());
    assertEquals("#6BE0D5", palette.getShade400());
    assertEquals("#91E8E0", palette.getShade300());
    assertEquals("#B7F0EA", palette.getShade200());
    assertEquals("#D9F7F4", palette.getShade100());
    assertEquals("#EEFCFA", palette.getShade50());
    assertTrue(actualUiTheme.getBackgroundEnabled());
  }
}
