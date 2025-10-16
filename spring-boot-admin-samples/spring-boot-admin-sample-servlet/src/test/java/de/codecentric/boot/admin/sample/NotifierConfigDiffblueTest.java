package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NotifierConfig.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class NotifierConfigDiffblueTest {
  @MockitoBean private FilteringNotifier filteringNotifier;

  @MockitoBean private InstanceRepository instanceRepository;

  @Autowired private NotifierConfig notifierConfig;

  @MockitoBean private RemindingNotifier remindingNotifier;

  /**
   * Test {@link NotifierConfig#filteringNotifier()}.
   *
   * <ul>
   *   <li>Then return Enabled.
   * </ul>
   *
   * <p>Method under test: {@link NotifierConfig#filteringNotifier()}
   */
  @Test
  @DisplayName("Test filteringNotifier(); then return Enabled")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"FilteringNotifier NotifierConfig.filteringNotifier()"})
  void testFilteringNotifier_thenReturnEnabled() {
    // Arrange and Act
    FilteringNotifier actualFilteringNotifierResult = notifierConfig.filteringNotifier();

    // Assert
    assertTrue(actualFilteringNotifierResult.isEnabled());
    assertTrue(actualFilteringNotifierResult.getNotificationFilters().isEmpty());
  }
}
