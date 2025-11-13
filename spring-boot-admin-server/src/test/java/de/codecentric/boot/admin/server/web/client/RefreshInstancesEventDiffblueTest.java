package de.codecentric.boot.admin.server.web.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RefreshInstancesEventDiffblueTest {
  /**
   * Test {@link RefreshInstancesEvent#RefreshInstancesEvent(Object)}.
   *
   * <p>Method under test: {@link RefreshInstancesEvent#RefreshInstancesEvent(Object)}
   */
  @Test
  @DisplayName("Test new RefreshInstancesEvent(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RefreshInstancesEvent.<init>(Object)"})
  void testNewRefreshInstancesEvent() {
    // Arrange, Act and Assert
    assertEquals("Source", new RefreshInstancesEvent("Source").getSource());
  }
}
