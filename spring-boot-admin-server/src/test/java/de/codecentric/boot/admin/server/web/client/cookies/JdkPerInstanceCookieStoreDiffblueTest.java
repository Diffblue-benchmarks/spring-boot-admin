package de.codecentric.boot.admin.server.web.client.cookies;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.notify.PagerdutyNotifier;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {JdkPerInstanceCookieStore.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class JdkPerInstanceCookieStoreDiffblueTest {
  @Autowired
  private JdkPerInstanceCookieStore jdkPerInstanceCookieStore;

  /**
   * Method under test:
   * {@link JdkPerInstanceCookieStore#get(InstanceId, URI, MultiValueMap)}
   */
  @Test
  public void testGet() {
    // Arrange
    InstanceId instanceId = InstanceId.of("42");

    // Act and Assert
    assertTrue(jdkPerInstanceCookieStore.get(instanceId, PagerdutyNotifier.DEFAULT_URI, new HttpHeaders()).isEmpty());
  }

  /**
   * Method under test:
   * {@link JdkPerInstanceCookieStore#get(InstanceId, URI, MultiValueMap)}
   */
  @Test
  public void testGet2() {
    // Arrange
    InstanceId instanceId = InstanceId.of("42");
    URI requestUri = Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri();

    // Act and Assert
    assertTrue(jdkPerInstanceCookieStore.get(instanceId, requestUri, new HttpHeaders()).isEmpty());
  }

  /**
   * Method under test:
   * {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}
   */
  @Test
  public void testGetCookieHandler() {
    // Arrange, Act and Assert
    assertTrue(jdkPerInstanceCookieStore.getCookieHandler(InstanceId.of("42")) instanceof CookieManager);
  }

  /**
   * Method under test:
   * {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}
   */
  @Test
  public void testGetCookieHandler2() {
    // Arrange, Act and Assert
    assertTrue(jdkPerInstanceCookieStore.getCookieHandler(InstanceId.of("Value")) instanceof CookieManager);
  }

  /**
   * Method under test:
   * {@link JdkPerInstanceCookieStore#createCookieHandler(InstanceId)}
   */
  @Test
  public void testCreateCookieHandler() {
    // Arrange, Act and Assert
    assertTrue(jdkPerInstanceCookieStore.createCookieHandler(InstanceId.of("42")) instanceof CookieManager);
  }

  /**
   * Method under test:
   * {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore()}
   */
  @Test
  public void testNewJdkPerInstanceCookieStore() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertTrue((new JdkPerInstanceCookieStore()).createCookieHandler(null) instanceof CookieManager);
    assertTrue(
        (new JdkPerInstanceCookieStore(mock(CookiePolicy.class))).createCookieHandler(null) instanceof CookieManager);
  }
}
