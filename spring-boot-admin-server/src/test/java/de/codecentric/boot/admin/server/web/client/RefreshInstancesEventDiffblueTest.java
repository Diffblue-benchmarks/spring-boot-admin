package de.codecentric.boot.admin.server.web.client;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RefreshInstancesEventDiffblueTest {
  /**
   * Test {@link RefreshInstancesEvent#RefreshInstancesEvent(Object)}.
   * <p>
   * Method under test: {@link RefreshInstancesEvent#RefreshInstancesEvent(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void RefreshInstancesEvent.<init>(Object)"})
  public void testNewRefreshInstancesEvent() {
    // Arrange, Act and Assert
    assertEquals("Source", (new RefreshInstancesEvent("Source")).getSource());
  }
}
