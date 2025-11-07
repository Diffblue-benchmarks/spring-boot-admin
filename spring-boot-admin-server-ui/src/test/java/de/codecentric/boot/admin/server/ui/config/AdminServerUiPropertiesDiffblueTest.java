package de.codecentric.boot.admin.server.ui.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.ui.web.UiController;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AdminServerUiPropertiesDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties.Cache#equals(Object)}
   *   <li>{@link AdminServerUiProperties.Cache#hashCode()}
   * </ul>
   */
  @Test
  void testCacheEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    AdminServerUiProperties.Cache cache2 = new AdminServerUiProperties.Cache();

    // Act and Assert
    assertEquals(cache, cache2);
    int expectedHashCodeResult = cache.hashCode();
    assertEquals(expectedHashCodeResult, cache2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties.Cache#equals(Object)}
   *   <li>{@link AdminServerUiProperties.Cache#hashCode()}
   * </ul>
   */
  @Test
  void testCacheEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    cache.setMaxAge(null);

    AdminServerUiProperties.Cache cache2 = new AdminServerUiProperties.Cache();
    cache2.setMaxAge(null);

    // Act and Assert
    assertEquals(cache, cache2);
    int expectedHashCodeResult = cache.hashCode();
    assertEquals(expectedHashCodeResult, cache2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties.Cache#equals(Object)}
   *   <li>{@link AdminServerUiProperties.Cache#hashCode()}
   * </ul>
   */
  @Test
  void testCacheEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();

    // Act and Assert
    assertEquals(cache, cache);
    int expectedHashCodeResult = cache.hashCode();
    assertEquals(expectedHashCodeResult, cache.hashCode());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#equals(Object)}
   */
  @Test
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AdminServerUiProperties.Cache(), 1);
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#equals(Object)}
   */
  @Test
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    cache.setMaxAge(null);

    // Act and Assert
    assertNotEquals(cache, new AdminServerUiProperties.Cache());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#equals(Object)}
   */
  @Test
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    cache.setNoCache(true);

    // Act and Assert
    assertNotEquals(cache, new AdminServerUiProperties.Cache());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#equals(Object)}
   */
  @Test
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    cache.setNoStore(true);

    // Act and Assert
    assertNotEquals(cache, new AdminServerUiProperties.Cache());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#equals(Object)}
   */
  @Test
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();

    AdminServerUiProperties.Cache cache2 = new AdminServerUiProperties.Cache();
    cache2.setMaxAge(null);

    // Act and Assert
    assertNotEquals(cache, cache2);
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#equals(Object)}
   */
  @Test
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    cache.setNoCache(null);

    // Act and Assert
    assertNotEquals(cache, new AdminServerUiProperties.Cache());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#equals(Object)}
   */
  @Test
  void testCacheEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    cache.setNoStore(null);

    // Act and Assert
    assertNotEquals(cache, new AdminServerUiProperties.Cache());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#equals(Object)}
   */
  @Test
  void testCacheEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AdminServerUiProperties.Cache(), null);
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#equals(Object)}
   */
  @Test
  void testCacheEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AdminServerUiProperties.Cache(), "Different type to Cache");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties.Cache#setMaxAge(Duration)}
   *   <li>{@link AdminServerUiProperties.Cache#setNoCache(Boolean)}
   *   <li>{@link AdminServerUiProperties.Cache#setNoStore(Boolean)}
   *   <li>{@link AdminServerUiProperties.Cache#toString()}
   *   <li>{@link AdminServerUiProperties.Cache#getMaxAge()}
   *   <li>{@link AdminServerUiProperties.Cache#getNoCache()}
   *   <li>{@link AdminServerUiProperties.Cache#getNoStore()}
   * </ul>
   */
  @Test
  void testCacheGettersAndSetters() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();

    // Act
    cache.setMaxAge(null);
    cache.setNoCache(true);
    cache.setNoStore(true);
    String actualToStringResult = cache.toString();
    cache.getMaxAge();
    Boolean actualNoCache = cache.getNoCache();

    // Assert that nothing has changed
    assertEquals("AdminServerUiProperties.Cache(maxAge=null, noCache=true, noStore=true)", actualToStringResult);
    assertTrue(actualNoCache);
    assertTrue(cache.getNoStore());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AdminServerUiProperties.Cache}
   */
  @Test
  void testCacheNewCache() {
    // Arrange and Act
    AdminServerUiProperties.Cache actualCache = new AdminServerUiProperties.Cache();

    // Assert
    assertEquals(3600000000000L, actualCache.getMaxAge().toNanos());
    assertFalse(actualCache.getNoCache());
    assertFalse(actualCache.getNoStore());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#toCacheControl()}
   */
  @Test
  void testCacheToCacheControl() {
    // Arrange, Act and Assert
    assertEquals("max-age=3600", (new AdminServerUiProperties.Cache()).toCacheControl().getHeaderValue());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#toCacheControl()}
   */
  @Test
  void testCacheToCacheControl2() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    cache.setMaxAge(null);

    // Act and Assert
    assertNull(cache.toCacheControl().getHeaderValue());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#toCacheControl()}
   */
  @Test
  void testCacheToCacheControl3() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    cache.setNoCache(true);

    // Act and Assert
    assertEquals("no-cache", cache.toCacheControl().getHeaderValue());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.Cache#toCacheControl()}
   */
  @Test
  void testCacheToCacheControl4() {
    // Arrange
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    cache.setNoStore(true);

    // Act and Assert
    assertEquals("no-store", cache.toCacheControl().getHeaderValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties#equals(Object)}
   *   <li>{@link AdminServerUiProperties#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ArrayList<String> additionalRouteExcludes = new ArrayList<>();
    additionalRouteExcludes.add("Template Location");

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(additionalRouteExcludes);
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ArrayList<String> availableLanguages = new ArrayList<>();
    availableLanguages.add("Template Location");

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(availableLanguages);
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Template Location");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand(null);
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(null);
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(mock(AdminServerUiProperties.Cache.class));
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(null);
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    ArrayList<UiController.ExternalView> externalViews = new ArrayList<>();
    externalViews.add(
        new UiController.ExternalView("Template Location", "https://example.org/example", 1, true, new ArrayList<>()));

    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
    pollTimer2.setCache(1);
    pollTimer2.setDatasource(1);
    pollTimer2.setGc(1);
    pollTimer2.setLogfile(1);
    pollTimer2.setMemory(1);
    pollTimer2.setProcess(1);
    pollTimer2.setThreads(1);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme2 = new AdminServerUiProperties.UiTheme();
    theme2.setBackgroundEnabled(true);
    theme2.setColor("Color");
    theme2.setPalette(palette2);

    AdminServerUiProperties adminServerUiProperties2 = new AdminServerUiProperties();
    adminServerUiProperties2.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties2.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties2.setBrand("Brand");
    adminServerUiProperties2.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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
   * Method under test: {@link AdminServerUiProperties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);

    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    adminServerUiProperties.setAdditionalRouteExcludes(new ArrayList<>());
    adminServerUiProperties.setAvailableLanguages(new ArrayList<>());
    adminServerUiProperties.setBrand("Brand");
    adminServerUiProperties.setCache(new AdminServerUiProperties.Cache());
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
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties#setAdditionalRouteExcludes(List)}
   *   <li>{@link AdminServerUiProperties#setAvailableLanguages(List)}
   *   <li>{@link AdminServerUiProperties#setBrand(String)}
   *   <li>{@link AdminServerUiProperties#setCache(AdminServerUiProperties.Cache)}
   *   <li>{@link AdminServerUiProperties#setCacheTemplates(boolean)}
   *   <li>{@link AdminServerUiProperties#setEnableToasts(Boolean)}
   *   <li>{@link AdminServerUiProperties#setExtensionResourceLocations(String[])}
   *   <li>{@link AdminServerUiProperties#setExternalViews(List)}
   *   <li>{@link AdminServerUiProperties#setFavicon(String)}
   *   <li>{@link AdminServerUiProperties#setFaviconDanger(String)}
   *   <li>{@link AdminServerUiProperties#setHideInstanceUrl(Boolean)}
   *   <li>{@link AdminServerUiProperties#setLoginIcon(String)}
   *   <li>
   * {@link AdminServerUiProperties#setPollTimer(AdminServerUiProperties.PollTimer)}
   *   <li>{@link AdminServerUiProperties#setPublicUrl(String)}
   *   <li>{@link AdminServerUiProperties#setRememberMeEnabled(boolean)}
   *   <li>{@link AdminServerUiProperties#setResourceLocations(String[])}
   *   <li>{@link AdminServerUiProperties#setTemplateLocation(String)}
   *   <li>{@link AdminServerUiProperties#setTheme(AdminServerUiProperties.UiTheme)}
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
  void testGettersAndSetters() {
    // Arrange
    AdminServerUiProperties adminServerUiProperties = new AdminServerUiProperties();
    ArrayList<String> additionalRouteExcludes = new ArrayList<>();

    // Act
    adminServerUiProperties.setAdditionalRouteExcludes(additionalRouteExcludes);
    ArrayList<String> availableLanguages = new ArrayList<>();
    adminServerUiProperties.setAvailableLanguages(availableLanguages);
    adminServerUiProperties.setBrand("Brand");
    AdminServerUiProperties.Cache cache = new AdminServerUiProperties.Cache();
    adminServerUiProperties.setCache(cache);
    adminServerUiProperties.setCacheTemplates(true);
    adminServerUiProperties.setEnableToasts(true);
    String[] extensionResourceLocations = new String[]{"Extension Resource Locations"};
    adminServerUiProperties.setExtensionResourceLocations(extensionResourceLocations);
    ArrayList<UiController.ExternalView> externalViews = new ArrayList<>();
    adminServerUiProperties.setExternalViews(externalViews);
    adminServerUiProperties.setFavicon("Favicon");
    adminServerUiProperties.setFaviconDanger("Favicon Danger");
    adminServerUiProperties.setHideInstanceUrl(true);
    adminServerUiProperties.setLoginIcon("Login Icon");
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
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
    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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
    AdminServerUiProperties.UiTheme theme = new AdminServerUiProperties.UiTheme();
    theme.setBackgroundEnabled(true);
    theme.setColor("Color");
    theme.setPalette(palette);
    adminServerUiProperties.setTheme(theme);
    adminServerUiProperties.setTitle("Dr");
    ArrayList<UiController.ViewSettings> viewSettings = new ArrayList<>();
    adminServerUiProperties.setViewSettings(viewSettings);
    adminServerUiProperties.toString();
    List<String> actualAdditionalRouteExcludes = adminServerUiProperties.getAdditionalRouteExcludes();
    List<String> actualAvailableLanguages = adminServerUiProperties.getAvailableLanguages();
    String actualBrand = adminServerUiProperties.getBrand();
    AdminServerUiProperties.Cache actualCache = adminServerUiProperties.getCache();
    Boolean actualEnableToasts = adminServerUiProperties.getEnableToasts();
    String[] actualExtensionResourceLocations = adminServerUiProperties.getExtensionResourceLocations();
    List<UiController.ExternalView> actualExternalViews = adminServerUiProperties.getExternalViews();
    String actualFavicon = adminServerUiProperties.getFavicon();
    String actualFaviconDanger = adminServerUiProperties.getFaviconDanger();
    Boolean actualHideInstanceUrl = adminServerUiProperties.getHideInstanceUrl();
    String actualLoginIcon = adminServerUiProperties.getLoginIcon();
    AdminServerUiProperties.PollTimer actualPollTimer = adminServerUiProperties.getPollTimer();
    String actualPublicUrl = adminServerUiProperties.getPublicUrl();
    String[] actualResourceLocations = adminServerUiProperties.getResourceLocations();
    String actualTemplateLocation = adminServerUiProperties.getTemplateLocation();
    AdminServerUiProperties.UiTheme actualTheme = adminServerUiProperties.getTheme();
    String actualTitle = adminServerUiProperties.getTitle();
    List<UiController.ViewSettings> actualViewSettings = adminServerUiProperties.getViewSettings();
    boolean actualIsCacheTemplatesResult = adminServerUiProperties.isCacheTemplates();

    // Assert that nothing has changed
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
   * Method under test: default or parameterless constructor of
   * {@link AdminServerUiProperties}
   */
  @Test
  void testNewAdminServerUiProperties() {
    // Arrange and Act
    AdminServerUiProperties actualAdminServerUiProperties = new AdminServerUiProperties();

    // Assert
    AdminServerUiProperties.UiTheme theme = actualAdminServerUiProperties.getTheme();
    AdminServerUiProperties.Palette palette = theme.getPalette();
    assertEquals("#0A2F2B", palette.getShade900());
    assertEquals("#14615A", palette.getShade800());
    assertEquals("#14615A", theme.getColor());
    assertEquals("#1E9084", palette.getShade700());
    assertEquals("#27BEAF", palette.getShade600());
    assertEquals("#47D9CB", palette.getShade500());
    assertEquals("#6BE0D5", palette.getShade400());
    assertEquals("#91E8E0", palette.getShade300());
    assertEquals("#B7F0EA", palette.getShade200());
    assertEquals("#D9F7F4", palette.getShade100());
    assertEquals("#EEFCFA", palette.getShade50());
    assertEquals("<img src=\"assets/img/icon-spring-boot-admin.svg\"><span>Spring Boot Admin</span>",
        actualAdminServerUiProperties.getBrand());
    assertEquals("Spring Boot Admin", actualAdminServerUiProperties.getTitle());
    assertEquals("assets/img/favicon-danger.png", actualAdminServerUiProperties.getFaviconDanger());
    assertEquals("assets/img/favicon.png", actualAdminServerUiProperties.getFavicon());
    assertEquals("assets/img/icon-spring-boot-admin.svg", actualAdminServerUiProperties.getLoginIcon());
    assertEquals("classpath:/META-INF/spring-boot-admin-server-ui/",
        actualAdminServerUiProperties.getTemplateLocation());
    assertNull(actualAdminServerUiProperties.getPublicUrl());
    AdminServerUiProperties.PollTimer pollTimer = actualAdminServerUiProperties.getPollTimer();
    assertEquals(1000, pollTimer.getLogfile());
    assertEquals(2500, pollTimer.getCache());
    assertEquals(2500, pollTimer.getDatasource());
    assertEquals(2500, pollTimer.getGc());
    assertEquals(2500, pollTimer.getMemory());
    assertEquals(2500, pollTimer.getProcess());
    assertEquals(2500, pollTimer.getThreads());
    AdminServerUiProperties.Cache cache = actualAdminServerUiProperties.getCache();
    assertEquals(3600000000000L, cache.getMaxAge().toNanos());
    assertFalse(actualAdminServerUiProperties.getEnableToasts());
    assertFalse(actualAdminServerUiProperties.getHideInstanceUrl());
    assertFalse(cache.getNoCache());
    assertFalse(cache.getNoStore());
    assertTrue(actualAdminServerUiProperties.isCacheTemplates());
    assertTrue(actualAdminServerUiProperties.isRememberMeEnabled());
    assertTrue(theme.getBackgroundEnabled());
    assertTrue(actualAdminServerUiProperties.getAdditionalRouteExcludes().isEmpty());
    assertTrue(actualAdminServerUiProperties.getAvailableLanguages().isEmpty());
    assertTrue(actualAdminServerUiProperties.getExternalViews().isEmpty());
    assertTrue(actualAdminServerUiProperties.getViewSettings().isEmpty());
    assertArrayEquals(new String[]{"classpath:/META-INF/spring-boot-admin-server-ui/"},
        actualAdminServerUiProperties.getResourceLocations());
    assertArrayEquals(new String[]{"classpath:/META-INF/spring-boot-admin-server-ui/extensions/"},
        actualAdminServerUiProperties.getExtensionResourceLocations());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link AdminServerUiProperties.Palette}
   *   <li>{@link AdminServerUiProperties.Palette#set100(String)}
   *   <li>{@link AdminServerUiProperties.Palette#set200(String)}
   *   <li>{@link AdminServerUiProperties.Palette#set300(String)}
   *   <li>{@link AdminServerUiProperties.Palette#set400(String)}
   *   <li>{@link AdminServerUiProperties.Palette#set500(String)}
   *   <li>{@link AdminServerUiProperties.Palette#set50(String)}
   *   <li>{@link AdminServerUiProperties.Palette#set600(String)}
   *   <li>{@link AdminServerUiProperties.Palette#set700(String)}
   *   <li>{@link AdminServerUiProperties.Palette#set800(String)}
   *   <li>{@link AdminServerUiProperties.Palette#set900(String)}
   *   <li>{@link AdminServerUiProperties.Palette#getShade100()}
   *   <li>{@link AdminServerUiProperties.Palette#getShade200()}
   *   <li>{@link AdminServerUiProperties.Palette#getShade300()}
   *   <li>{@link AdminServerUiProperties.Palette#getShade400()}
   *   <li>{@link AdminServerUiProperties.Palette#getShade50()}
   *   <li>{@link AdminServerUiProperties.Palette#getShade500()}
   *   <li>{@link AdminServerUiProperties.Palette#getShade600()}
   *   <li>{@link AdminServerUiProperties.Palette#getShade700()}
   *   <li>{@link AdminServerUiProperties.Palette#getShade800()}
   *   <li>{@link AdminServerUiProperties.Palette#getShade900()}
   * </ul>
   */
  @Test
  void testPaletteGettersAndSetters() {
    // Arrange and Act
    AdminServerUiProperties.Palette actualPalette = new AdminServerUiProperties.Palette();
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

    // Assert that nothing has changed
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
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties.PollTimer#equals(Object)}
   *   <li>{@link AdminServerUiProperties.PollTimer#hashCode()}
   * </ul>
   */
  @Test
  void testPollTimerEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
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
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties.PollTimer#equals(Object)}
   *   <li>{@link AdminServerUiProperties.PollTimer#hashCode()}
   * </ul>
   */
  @Test
  void testPollTimerEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
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
   * Method under test: {@link AdminServerUiProperties.PollTimer#equals(Object)}
   */
  @Test
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(3);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
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
   * Method under test: {@link AdminServerUiProperties.PollTimer#equals(Object)}
   */
  @Test
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(3);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
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
   * Method under test: {@link AdminServerUiProperties.PollTimer#equals(Object)}
   */
  @Test
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(3);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
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
   * Method under test: {@link AdminServerUiProperties.PollTimer#equals(Object)}
   */
  @Test
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(3);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
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
   * Method under test: {@link AdminServerUiProperties.PollTimer#equals(Object)}
   */
  @Test
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(3);
    pollTimer.setProcess(1);
    pollTimer.setThreads(1);

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
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
   * Method under test: {@link AdminServerUiProperties.PollTimer#equals(Object)}
   */
  @Test
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(3);
    pollTimer.setThreads(1);

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
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
   * Method under test: {@link AdminServerUiProperties.PollTimer#equals(Object)}
   */
  @Test
  void testPollTimerEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
    pollTimer.setCache(1);
    pollTimer.setDatasource(1);
    pollTimer.setGc(1);
    pollTimer.setLogfile(1);
    pollTimer.setMemory(1);
    pollTimer.setProcess(1);
    pollTimer.setThreads(3);

    AdminServerUiProperties.PollTimer pollTimer2 = new AdminServerUiProperties.PollTimer();
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
   * Method under test: {@link AdminServerUiProperties.PollTimer#equals(Object)}
   */
  @Test
  void testPollTimerEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
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
   * Method under test: {@link AdminServerUiProperties.PollTimer#equals(Object)}
   */
  @Test
  void testPollTimerEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.PollTimer pollTimer = new AdminServerUiProperties.PollTimer();
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
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link AdminServerUiProperties.PollTimer}
   *   <li>{@link AdminServerUiProperties.PollTimer#setCache(int)}
   *   <li>{@link AdminServerUiProperties.PollTimer#setDatasource(int)}
   *   <li>{@link AdminServerUiProperties.PollTimer#setGc(int)}
   *   <li>{@link AdminServerUiProperties.PollTimer#setLogfile(int)}
   *   <li>{@link AdminServerUiProperties.PollTimer#setMemory(int)}
   *   <li>{@link AdminServerUiProperties.PollTimer#setProcess(int)}
   *   <li>{@link AdminServerUiProperties.PollTimer#setThreads(int)}
   *   <li>{@link AdminServerUiProperties.PollTimer#toString()}
   *   <li>{@link AdminServerUiProperties.PollTimer#getCache()}
   *   <li>{@link AdminServerUiProperties.PollTimer#getDatasource()}
   *   <li>{@link AdminServerUiProperties.PollTimer#getGc()}
   *   <li>{@link AdminServerUiProperties.PollTimer#getLogfile()}
   *   <li>{@link AdminServerUiProperties.PollTimer#getMemory()}
   *   <li>{@link AdminServerUiProperties.PollTimer#getProcess()}
   *   <li>{@link AdminServerUiProperties.PollTimer#getThreads()}
   * </ul>
   */
  @Test
  void testPollTimerGettersAndSetters() {
    // Arrange and Act
    AdminServerUiProperties.PollTimer actualPollTimer = new AdminServerUiProperties.PollTimer();
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

    // Assert that nothing has changed
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
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties.UiTheme#equals(Object)}
   *   <li>{@link AdminServerUiProperties.UiTheme#hashCode()}
   * </ul>
   */
  @Test
  void testUiThemeEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme = new AdminServerUiProperties.UiTheme();
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    // Act and Assert
    assertEquals(uiTheme, uiTheme);
    int expectedHashCodeResult = uiTheme.hashCode();
    assertEquals(expectedHashCodeResult, uiTheme.hashCode());
  }

  /**
   * Method under test: {@link AdminServerUiProperties.UiTheme#equals(Object)}
   */
  @Test
  void testUiThemeEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme = new AdminServerUiProperties.UiTheme();
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme2 = new AdminServerUiProperties.UiTheme();
    uiTheme2.setBackgroundEnabled(true);
    uiTheme2.setColor("Color");
    uiTheme2.setPalette(palette2);

    // Act and Assert
    assertNotEquals(uiTheme, uiTheme2);
  }

  /**
   * Method under test: {@link AdminServerUiProperties.UiTheme#equals(Object)}
   */
  @Test
  void testUiThemeEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme = new AdminServerUiProperties.UiTheme();
    uiTheme.setBackgroundEnabled(false);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme2 = new AdminServerUiProperties.UiTheme();
    uiTheme2.setBackgroundEnabled(true);
    uiTheme2.setColor("Color");
    uiTheme2.setPalette(palette2);

    // Act and Assert
    assertNotEquals(uiTheme, uiTheme2);
  }

  /**
   * Method under test: {@link AdminServerUiProperties.UiTheme#equals(Object)}
   */
  @Test
  void testUiThemeEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme = new AdminServerUiProperties.UiTheme();
    uiTheme.setBackgroundEnabled(null);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme2 = new AdminServerUiProperties.UiTheme();
    uiTheme2.setBackgroundEnabled(true);
    uiTheme2.setColor("Color");
    uiTheme2.setPalette(palette2);

    // Act and Assert
    assertNotEquals(uiTheme, uiTheme2);
  }

  /**
   * Method under test: {@link AdminServerUiProperties.UiTheme#equals(Object)}
   */
  @Test
  void testUiThemeEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AdminServerUiProperties.Palette palette = mock(AdminServerUiProperties.Palette.class);
    doNothing().when(palette).set100(Mockito.<String>any());
    doNothing().when(palette).set200(Mockito.<String>any());
    doNothing().when(palette).set300(Mockito.<String>any());
    doNothing().when(palette).set400(Mockito.<String>any());
    doNothing().when(palette).set50(Mockito.<String>any());
    doNothing().when(palette).set500(Mockito.<String>any());
    doNothing().when(palette).set600(Mockito.<String>any());
    doNothing().when(palette).set700(Mockito.<String>any());
    doNothing().when(palette).set800(Mockito.<String>any());
    doNothing().when(palette).set900(Mockito.<String>any());
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

    AdminServerUiProperties.UiTheme uiTheme = new AdminServerUiProperties.UiTheme();
    uiTheme.setBackgroundEnabled(false);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    AdminServerUiProperties.Palette palette2 = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme2 = new AdminServerUiProperties.UiTheme();
    uiTheme2.setBackgroundEnabled(true);
    uiTheme2.setColor("Color");
    uiTheme2.setPalette(palette2);

    // Act and Assert
    assertNotEquals(uiTheme, uiTheme2);
  }

  /**
   * Method under test: {@link AdminServerUiProperties.UiTheme#equals(Object)}
   */
  @Test
  void testUiThemeEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme = new AdminServerUiProperties.UiTheme();
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    // Act and Assert
    assertNotEquals(uiTheme, null);
  }

  /**
   * Method under test: {@link AdminServerUiProperties.UiTheme#equals(Object)}
   */
  @Test
  void testUiThemeEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    AdminServerUiProperties.UiTheme uiTheme = new AdminServerUiProperties.UiTheme();
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    uiTheme.setPalette(palette);

    // Act and Assert
    assertNotEquals(uiTheme, "Different type to UiTheme");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AdminServerUiProperties.UiTheme#setBackgroundEnabled(Boolean)}
   *   <li>{@link AdminServerUiProperties.UiTheme#setColor(String)}
   *   <li>
   * {@link AdminServerUiProperties.UiTheme#setPalette(AdminServerUiProperties.Palette)}
   *   <li>{@link AdminServerUiProperties.UiTheme#toString()}
   *   <li>{@link AdminServerUiProperties.UiTheme#getBackgroundEnabled()}
   *   <li>{@link AdminServerUiProperties.UiTheme#getColor()}
   *   <li>{@link AdminServerUiProperties.UiTheme#getPalette()}
   * </ul>
   */
  @Test
  void testUiThemeGettersAndSetters() {
    // Arrange
    AdminServerUiProperties.UiTheme uiTheme = new AdminServerUiProperties.UiTheme();

    // Act
    uiTheme.setBackgroundEnabled(true);
    uiTheme.setColor("Color");
    AdminServerUiProperties.Palette palette = new AdminServerUiProperties.Palette();
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

    // Assert that nothing has changed
    assertEquals("Color", actualColor);
    assertTrue(actualBackgroundEnabled);
    assertSame(palette, uiTheme.getPalette());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AdminServerUiProperties.UiTheme}
   */
  @Test
  void testUiThemeNewUiTheme() {
    // Arrange and Act
    AdminServerUiProperties.UiTheme actualUiTheme = new AdminServerUiProperties.UiTheme();

    // Assert
    AdminServerUiProperties.Palette palette = actualUiTheme.getPalette();
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
