package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EndpointsMixinDiffblueTest {
  /**
   * Test {@link EndpointsMixin#of(Collection)}.
   * <ul>
   *   <li>Then return stream limit five collect toList size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Endpoints EndpointsMixin.of(Collection)"})
  public void testOf_thenReturnStreamLimitFiveCollectToListSizeIsOne() {
    // Arrange
    Endpoint ofResult = Endpoint.of("42", "https://example.org/example");

    ArrayList<Endpoint> endpoints = new ArrayList<>();
    endpoints.add(ofResult);

    // Act
    Endpoints actualOfResult = EndpointsMixin.of(endpoints);

    // Assert
    Iterator<Endpoint> iteratorResult = actualOfResult.iterator();
    Endpoint actualNextResult = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    Stream<Endpoint> streamResult = actualOfResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(1, collectResult.size());
    assertFalse(actualHasNextResult);
    assertSame(ofResult, actualNextResult);
    assertSame(ofResult, collectResult.get(0));
  }

  /**
   * Test {@link EndpointsMixin#of(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return stream limit five collect toList Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Endpoints EndpointsMixin.of(Collection)"})
  public void testOf_whenArrayList_thenReturnStreamLimitFiveCollectToListEmpty() {
    // Arrange and Act
    Endpoints actualOfResult = EndpointsMixin.of(new ArrayList<>());

    // Assert
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link EndpointsMixin#of(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return stream limit five collect toList Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Endpoints EndpointsMixin.of(Collection)"})
  public void testOf_whenNull_thenReturnStreamLimitFiveCollectToListEmpty() {
    // Arrange and Act
    Endpoints actualOfResult = EndpointsMixin.of(null);

    // Assert
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }
}
