package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EndpointsMixinDiffblueTest {
  /**
   * Test {@link EndpointsMixin#of(Collection)}.
   *
   * <ul>
   *   <li>Then return iterator next Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  @DisplayName("Test of(Collection); then return iterator next Id is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints EndpointsMixin.of(Collection)"})
  void testOf_thenReturnIteratorNextIdIs42() {
    // Arrange
    ArrayList<Endpoint> endpoints = new ArrayList<>();
    Endpoint ofResult = Endpoint.of("42", "https://example.org/example");
    endpoints.add(ofResult);

    // Act
    Endpoints actualOfResult = EndpointsMixin.of(endpoints);

    // Assert
    Iterator<Endpoint> iteratorResult = actualOfResult.iterator();
    Endpoint nextResult = iteratorResult.next();
    assertEquals("42", nextResult.getId());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(1, collectResult.size());
    assertFalse(iteratorResult.hasNext());
    assertSame(ofResult, nextResult);
    assertSame(ofResult, collectResult.get(0));
  }

  /**
   * Test {@link EndpointsMixin#of(Collection)}.
   *
   * <ul>
   *   <li>Then return iterator next Id is {@code Id}.
   * </ul>
   *
   * <p>Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  @DisplayName("Test of(Collection); then return iterator next Id is 'Id'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints EndpointsMixin.of(Collection)"})
  void testOf_thenReturnIteratorNextIdIsId() {
    // Arrange
    Endpoint ofResult = Endpoint.of("Id", "https://example.org/example");

    ArrayList<Endpoint> endpoints = new ArrayList<>();
    endpoints.add(ofResult);
    Endpoint ofResult2 = Endpoint.of("42", "https://example.org/example");
    endpoints.add(ofResult2);

    // Act
    Endpoints actualOfResult = EndpointsMixin.of(endpoints);

    // Assert
    Iterator<Endpoint> iteratorResult = actualOfResult.iterator();
    Endpoint nextResult = iteratorResult.next();
    Endpoint actualNextResult = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertEquals("Id", nextResult.getId());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(2, collectResult.size());
    assertFalse(actualHasNextResult);
    assertSame(ofResult2, actualNextResult);
    assertSame(ofResult2, collectResult.get(1));
    assertSame(ofResult, nextResult);
    assertSame(ofResult, collectResult.get(0));
  }

  /**
   * Test {@link EndpointsMixin#of(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return spliterator ExactSizeIfKnown is minus one.
   * </ul>
   *
   * <p>Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  @DisplayName(
      "Test of(Collection); when ArrayList(); then return spliterator ExactSizeIfKnown is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints EndpointsMixin.of(Collection)"})
  void testOf_whenArrayList_thenReturnSpliteratorExactSizeIfKnownIsMinusOne() {
    // Arrange and Act
    Endpoints actualOfResult = EndpointsMixin.of(new ArrayList<>());

    // Assert
    assertEquals(-1L, actualOfResult.spliterator().getExactSizeIfKnown());
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link EndpointsMixin#of(Collection)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return spliterator ExactSizeIfKnown is minus one.
   * </ul>
   *
   * <p>Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  @DisplayName(
      "Test of(Collection); when 'null'; then return spliterator ExactSizeIfKnown is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints EndpointsMixin.of(Collection)"})
  void testOf_whenNull_thenReturnSpliteratorExactSizeIfKnownIsMinusOne() {
    // Arrange and Act
    Endpoints actualOfResult = EndpointsMixin.of(null);

    // Assert
    assertEquals(-1L, actualOfResult.spliterator().getExactSizeIfKnown());
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }
}
