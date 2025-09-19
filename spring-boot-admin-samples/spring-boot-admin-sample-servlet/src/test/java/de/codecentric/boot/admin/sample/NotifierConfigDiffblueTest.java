package de.codecentric.boot.admin.sample;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NotifierConfig.class})
@DisabledInAotMode
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class NotifierConfigDiffblueTest {
  @MockitoBean private FilteringNotifier filteringNotifier;

  @MockitoBean private InstanceRepository instanceRepository;

  @Autowired private NotifierConfig notifierConfig;

  @InjectMocks private NotifierConfig notifierConfig2;

  @Mock private ObjectProvider<List<Notifier>> objectProvider;

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

  /**
   * Test {@link NotifierConfig#remindingNotifier()}.
   *
   * <p>Method under test: {@link NotifierConfig#remindingNotifier()}
   */
  @Test
  @DisplayName("Test remindingNotifier()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"RemindingNotifier NotifierConfig.remindingNotifier()"})
  void testRemindingNotifier() throws BeansException {
    // Arrange
    when(objectProvider.getIfAvailable(Mockito.<Supplier<List<Notifier>>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    RemindingNotifier actualRemindingNotifierResult = notifierConfig2.remindingNotifier();

    // Assert
    verify(objectProvider).getIfAvailable(isA(Supplier.class));
    assertTrue(actualRemindingNotifierResult.isEnabled());
  }
}
