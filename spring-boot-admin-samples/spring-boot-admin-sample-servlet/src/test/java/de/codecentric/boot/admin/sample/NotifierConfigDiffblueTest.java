package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NotifierConfig.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class NotifierConfigDiffblueTest {
  @MockBean
  private FilteringNotifier filteringNotifier;

  @MockBean
  private InstanceRepository instanceRepository;

  @Autowired
  private NotifierConfig notifierConfig;

  /**
   * Test {@link NotifierConfig#filteringNotifier()}.
   * <p>
   * Method under test: {@link NotifierConfig#filteringNotifier()}
   */
  @Test
  @DisplayName("Test filteringNotifier()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FilteringNotifier NotifierConfig.filteringNotifier()"})
  void testFilteringNotifier() {
    // Arrange and Act
    FilteringNotifier actualFilteringNotifierResult = notifierConfig.filteringNotifier();

    // Assert
    assertTrue(actualFilteringNotifierResult.isEnabled());
    assertTrue(actualFilteringNotifierResult.getNotificationFilters().isEmpty());
  }
}
