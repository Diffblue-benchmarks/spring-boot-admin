package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Duration;
import java.util.function.Function;
import org.junit.Test;
import reactor.core.publisher.Mono;

public class IntervalCheckDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link IntervalCheck#IntervalCheck(String, Function, Duration, Duration, Duration)}
   *   <li>{@link IntervalCheck#setInterval(Duration)}
   *   <li>{@link IntervalCheck#setMaxBackoff(Duration)}
   *   <li>{@link IntervalCheck#setMinRetention(Duration)}
   *   <li>{@link IntervalCheck#getInterval()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    IntervalCheck actualIntervalCheck = new IntervalCheck("Name", mock(Function.class), null, null, null);
    actualIntervalCheck.setInterval(null);
    actualIntervalCheck.setMaxBackoff(null);
    actualIntervalCheck.setMinRetention(null);

    // Assert that nothing has changed
    assertNull(actualIntervalCheck.getInterval());
  }
}
