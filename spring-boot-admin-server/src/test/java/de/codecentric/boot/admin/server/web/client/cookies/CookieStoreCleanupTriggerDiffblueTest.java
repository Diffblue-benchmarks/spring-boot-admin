package de.codecentric.boot.admin.server.web.client.cookies;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import java.util.ArrayList;
import java.util.function.Function;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;

@ContextConfiguration(classes = {CookieStoreCleanupTrigger.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class CookieStoreCleanupTriggerDiffblueTest {
  @Autowired
  private CookieStoreCleanupTrigger cookieStoreCleanupTrigger;

  @MockBean
  private PerInstanceCookieStore perInstanceCookieStore;

  @MockBean
  private Publisher<InstanceEvent> publisher;

  /**
   * Test {@link CookieStoreCleanupTrigger#handle(Flux)}.
   * <ul>
   *   <li>Given fromIterable {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return fromIterable {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CookieStoreCleanupTrigger#handle(Flux)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Publisher CookieStoreCleanupTrigger.handle(Flux)"})
  public void testHandle_givenFromIterableArrayList_thenReturnFromIterableArrayList() {
    // Arrange
    Flux<InstanceDeregisteredEvent> publisher2 = mock(Flux.class);
    Flux<Object> fromIterableResult = Flux.fromIterable(new ArrayList<>());
    when(publisher2.flatMap(Mockito.<Function<InstanceDeregisteredEvent, Publisher<Object>>>any()))
        .thenReturn(fromIterableResult);

    // Act
    Publisher<Void> actualHandleResult = cookieStoreCleanupTrigger.handle(publisher2);

    // Assert
    verify(publisher2).flatMap(isA(Function.class));
    assertSame(fromIterableResult, actualHandleResult);
  }
}
