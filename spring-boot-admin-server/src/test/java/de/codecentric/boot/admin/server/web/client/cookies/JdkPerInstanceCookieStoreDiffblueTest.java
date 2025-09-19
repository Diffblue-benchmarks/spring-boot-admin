package de.codecentric.boot.admin.server.web.client.cookies;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import java.net.CookieManager;
import java.net.CookiePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JdkPerInstanceCookieStore.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class JdkPerInstanceCookieStoreDiffblueTest {
  @Autowired private JdkPerInstanceCookieStore jdkPerInstanceCookieStore;

  /**
   * Test {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore()}.
   *
   * <p>Method under test: {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore()}
   */
  @Test
  @DisplayName("Test new JdkPerInstanceCookieStore()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JdkPerInstanceCookieStore.<init>()"})
  void testNewJdkPerInstanceCookieStore() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertTrue(new JdkPerInstanceCookieStore().createCookieHandler(null) instanceof CookieManager);
  }

  /**
   * Test {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore(CookiePolicy)}.
   *
   * <ul>
   *   <li>Then createCookieHandler {@code null} return {@link CookieManager}.
   * </ul>
   *
   * <p>Method under test: {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore(CookiePolicy)}
   */
  @Test
  @DisplayName(
      "Test new JdkPerInstanceCookieStore(CookiePolicy); then createCookieHandler 'null' return CookieManager")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JdkPerInstanceCookieStore.<init>(CookiePolicy)"})
  void testNewJdkPerInstanceCookieStore_thenCreateCookieHandlerNullReturnCookieManager() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertTrue(
        new JdkPerInstanceCookieStore(mock(CookiePolicy.class)).createCookieHandler(null)
            instanceof CookieManager);
  }

  /**
   * Test {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link JdkPerInstanceCookieStore}.
   *   <li>When {@link InstanceId}.
   * </ul>
   *
   * <p>Method under test: {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test getCookieHandler(InstanceId); given JdkPerInstanceCookieStore; when InstanceId")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.net.CookieHandler JdkPerInstanceCookieStore.getCookieHandler(InstanceId)"
  })
  void testGetCookieHandler_givenJdkPerInstanceCookieStore_whenInstanceId() {
    // Arrange, Act and Assert
    assertTrue(
        jdkPerInstanceCookieStore.getCookieHandler(mock(InstanceId.class))
            instanceof CookieManager);
  }

  /**
   * Test {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link JdkPerInstanceCookieStore}.
   *   <li>When {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test getCookieHandler(InstanceId); given JdkPerInstanceCookieStore; when InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.net.CookieHandler JdkPerInstanceCookieStore.getCookieHandler(InstanceId)"
  })
  void testGetCookieHandler_givenJdkPerInstanceCookieStore_whenInstanceIdWithValueIs42() {
    // Arrange, Act and Assert
    assertTrue(
        jdkPerInstanceCookieStore.getCookieHandler(InstanceId.of("42")) instanceof CookieManager);
  }

  /**
   * Test {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}.
   *
   * <ul>
   *   <li>Given {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore()}.
   *   <li>When {@link InstanceId} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}
   */
  @Test
  @DisplayName(
      "Test getCookieHandler(InstanceId); given JdkPerInstanceCookieStore(); when InstanceId with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.net.CookieHandler JdkPerInstanceCookieStore.getCookieHandler(InstanceId)"
  })
  void testGetCookieHandler_givenJdkPerInstanceCookieStore_whenInstanceIdWithValueIs422() {
    // Arrange
    JdkPerInstanceCookieStore jdkPerInstanceCookieStore = new JdkPerInstanceCookieStore();
    InstanceId instanceId = InstanceId.of("42");
    jdkPerInstanceCookieStore.put(instanceId, PagerdutyNotifier.DEFAULT_URI, new HttpHeaders());

    // Act and Assert
    assertTrue(
        jdkPerInstanceCookieStore.getCookieHandler(InstanceId.of("42")) instanceof CookieManager);
  }

  /**
   * Test {@link JdkPerInstanceCookieStore#createCookieHandler(InstanceId)}.
   *
   * <p>Method under test: {@link JdkPerInstanceCookieStore#createCookieHandler(InstanceId)}
   */
  @Test
  @DisplayName("Test createCookieHandler(InstanceId)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.net.CookieHandler JdkPerInstanceCookieStore.createCookieHandler(InstanceId)"
  })
  void testCreateCookieHandler() {
    // Arrange, Act and Assert
    assertTrue(
        jdkPerInstanceCookieStore.createCookieHandler(InstanceId.of("42"))
            instanceof CookieManager);
  }
}
