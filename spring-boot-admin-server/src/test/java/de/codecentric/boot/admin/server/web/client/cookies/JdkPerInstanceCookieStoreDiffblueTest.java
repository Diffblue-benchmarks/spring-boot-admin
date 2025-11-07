package de.codecentric.boot.admin.server.web.client.cookies;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.net.CookieManager;
import java.net.CookiePolicy;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {JdkPerInstanceCookieStore.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class JdkPerInstanceCookieStoreDiffblueTest {
  @Autowired
  private JdkPerInstanceCookieStore jdkPerInstanceCookieStore;

  /**
   * Test {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore()}.
   * <p>
   * Method under test: {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JdkPerInstanceCookieStore.<init>()"})
  public void testNewJdkPerInstanceCookieStore() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertTrue((new JdkPerInstanceCookieStore()).createCookieHandler(null) instanceof CookieManager);
  }

  /**
   * Test {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore(CookiePolicy)}.
   * <ul>
   *   <li>Then createCookieHandler {@code null} return {@link CookieManager}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JdkPerInstanceCookieStore#JdkPerInstanceCookieStore(CookiePolicy)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JdkPerInstanceCookieStore.<init>(CookiePolicy)"})
  public void testNewJdkPerInstanceCookieStore_thenCreateCookieHandlerNullReturnCookieManager() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertTrue(
        (new JdkPerInstanceCookieStore(mock(CookiePolicy.class))).createCookieHandler(null) instanceof CookieManager);
  }

  /**
   * Test {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}.
   * <ul>
   *   <li>When {@link InstanceId} with {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.net.CookieHandler JdkPerInstanceCookieStore.getCookieHandler(InstanceId)"})
  public void testGetCookieHandler_whenInstanceIdWithValue() {
    // Arrange, Act and Assert
    assertTrue(jdkPerInstanceCookieStore.getCookieHandler(InstanceId.of("Value")) instanceof CookieManager);
  }

  /**
   * Test {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}.
   * <ul>
   *   <li>When {@link InstanceId} with value is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JdkPerInstanceCookieStore#getCookieHandler(InstanceId)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.net.CookieHandler JdkPerInstanceCookieStore.getCookieHandler(InstanceId)"})
  public void testGetCookieHandler_whenInstanceIdWithValueIs42() {
    // Arrange, Act and Assert
    assertTrue(jdkPerInstanceCookieStore.getCookieHandler(InstanceId.of("42")) instanceof CookieManager);
  }

  /**
   * Test {@link JdkPerInstanceCookieStore#createCookieHandler(InstanceId)}.
   * <p>
   * Method under test: {@link JdkPerInstanceCookieStore#createCookieHandler(InstanceId)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.net.CookieHandler JdkPerInstanceCookieStore.createCookieHandler(InstanceId)"})
  public void testCreateCookieHandler() {
    // Arrange, Act and Assert
    assertTrue(jdkPerInstanceCookieStore.createCookieHandler(InstanceId.of("42")) instanceof CookieManager);
  }
}
