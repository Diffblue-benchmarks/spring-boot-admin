package de.codecentric.boot.admin.server.domain.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EndpointsDiffblueTest {
  /**
   * Test {@link Endpoints#get(String)}.
   *
   * <p>Method under test: {@link Endpoints#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional Endpoints.get(String)"})
  void testGet() {
    // Arrange, Act and Assert
    assertFalse(Endpoints.empty().get("42").isPresent());
  }

  /**
   * Test {@link Endpoints#isPresent(String)}.
   *
   * <ul>
   *   <li>Given empty.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link Endpoints#isPresent(String)}
   */
  @Test
  @DisplayName("Test isPresent(String); given empty; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Endpoints.isPresent(String)"})
  void testIsPresent_givenEmpty_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(Endpoints.empty().isPresent("42"));
  }

  /**
   * Test {@link Endpoints#isPresent(String)}.
   *
   * <ul>
   *   <li>Given single {@code 42} and {@code https://example.org/example}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link Endpoints#isPresent(String)}
   */
  @Test
  @DisplayName(
      "Test isPresent(String); given single '42' and 'https://example.org/example'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Endpoints.isPresent(String)"})
  void testIsPresent_givenSingle42AndHttpsExampleOrgExample_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(Endpoints.single("42", "https://example.org/example").isPresent("42"));
  }

  /**
   * Test {@link Endpoints#iterator()}.
   *
   * <p>Method under test: {@link Endpoints#iterator()}
   */
  @Test
  @DisplayName("Test iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Iterator Endpoints.iterator()"})
  void testIterator() {
    // Arrange, Act and Assert
    assertFalse(Endpoints.empty().iterator().hasNext());
  }

  /**
   * Test {@link Endpoints#empty()}.
   *
   * <p>Method under test: {@link Endpoints#empty()}
   */
  @Test
  @DisplayName("Test empty()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints Endpoints.empty()"})
  void testEmpty() {
    // Arrange and Act
    Endpoints actualEmptyResult = Endpoints.empty();

    // Assert
    assertEquals(-1L, actualEmptyResult.spliterator().getExactSizeIfKnown());
    assertFalse(actualEmptyResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualEmptyResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link Endpoints#single(String, String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return iterator next Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Endpoints#single(String, String)}
   */
  @Test
  @DisplayName("Test single(String, String); when '42'; then return iterator next Id is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints Endpoints.single(String, String)"})
  void testSingle_when42_thenReturnIteratorNextIdIs42() {
    // Arrange and Act
    Endpoints actualSingleResult = Endpoints.single("42", "https://example.org/example");

    // Assert
    Iterator<Endpoint> iteratorResult = actualSingleResult.iterator();
    Endpoint nextResult = iteratorResult.next();
    assertEquals("42", nextResult.getId());
    assertEquals("https://example.org/example", nextResult.getUrl());
    assertEquals(-1L, actualSingleResult.spliterator().getExactSizeIfKnown());
    Stream<Endpoint> streamResult = actualSingleResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(1, collectResult.size());
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, collectResult.get(0));
  }

  /**
   * Test {@link Endpoints#of(Collection)}.
   *
   * <ul>
   *   <li>Then return iterator next Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link Endpoints#of(Collection)}
   */
  @Test
  @DisplayName("Test of(Collection); then return iterator next Id is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints Endpoints.of(Collection)"})
  void testOf_thenReturnIteratorNextIdIs42() {
    // Arrange
    ArrayList<Endpoint> endpoints = new ArrayList<>();
    Endpoint ofResult = Endpoint.of("42", "https://example.org/example");
    endpoints.add(ofResult);

    // Act
    Endpoints actualOfResult = Endpoints.of(endpoints);

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
   * Test {@link Endpoints#of(Collection)}.
   *
   * <ul>
   *   <li>Then return iterator next Id is {@code Id}.
   * </ul>
   *
   * <p>Method under test: {@link Endpoints#of(Collection)}
   */
  @Test
  @DisplayName("Test of(Collection); then return iterator next Id is 'Id'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints Endpoints.of(Collection)"})
  void testOf_thenReturnIteratorNextIdIsId() {
    // Arrange
    Endpoint ofResult = Endpoint.of("Id", "https://example.org/example");

    ArrayList<Endpoint> endpoints = new ArrayList<>();
    endpoints.add(ofResult);
    Endpoint ofResult2 = Endpoint.of("42", "https://example.org/example");
    endpoints.add(ofResult2);

    // Act
    Endpoints actualOfResult = Endpoints.of(endpoints);

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
   * Test {@link Endpoints#of(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return spliterator ExactSizeIfKnown is minus one.
   * </ul>
   *
   * <p>Method under test: {@link Endpoints#of(Collection)}
   */
  @Test
  @DisplayName(
      "Test of(Collection); when ArrayList(); then return spliterator ExactSizeIfKnown is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints Endpoints.of(Collection)"})
  void testOf_whenArrayList_thenReturnSpliteratorExactSizeIfKnownIsMinusOne() {
    // Arrange and Act
    Endpoints actualOfResult = Endpoints.of(new ArrayList<>());

    // Assert
    assertEquals(-1L, actualOfResult.spliterator().getExactSizeIfKnown());
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link Endpoints#of(Collection)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return spliterator ExactSizeIfKnown is minus one.
   * </ul>
   *
   * <p>Method under test: {@link Endpoints#of(Collection)}
   */
  @Test
  @DisplayName(
      "Test of(Collection); when 'null'; then return spliterator ExactSizeIfKnown is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints Endpoints.of(Collection)"})
  void testOf_whenNull_thenReturnSpliteratorExactSizeIfKnownIsMinusOne() {
    // Arrange and Act
    Endpoints actualOfResult = Endpoints.of(null);

    // Assert
    assertEquals(-1L, actualOfResult.spliterator().getExactSizeIfKnown());
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link Endpoints#withEndpoint(String, String)}.
   *
   * <ul>
   *   <li>Then return iterator next Id is {@code 'id' must not be empty.}.
   * </ul>
   *
   * <p>Method under test: {@link Endpoints#withEndpoint(String, String)}
   */
  @Test
  @DisplayName(
      "Test withEndpoint(String, String); then return iterator next Id is ''id' must not be empty.'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints Endpoints.withEndpoint(String, String)"})
  void testWithEndpoint_thenReturnIteratorNextIdIsIdMustNotBeEmpty() {
    // Arrange and Act
    Endpoints actualWithEndpointResult =
        Endpoints.single("42", "https://example.org/example")
            .withEndpoint("'id' must not be empty.", "https://example.org/example");

    // Assert
    Iterator<Endpoint> iteratorResult = actualWithEndpointResult.iterator();
    Endpoint nextResult = iteratorResult.next();
    assertEquals("'id' must not be empty.", nextResult.getId());
    Endpoint nextResult2 = iteratorResult.next();
    assertEquals("42", nextResult2.getId());
    assertEquals("https://example.org/example", nextResult2.getUrl());
    Stream<Endpoint> streamResult = actualWithEndpointResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(2, collectResult.size());
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, collectResult.get(0));
    assertSame(nextResult2, collectResult.get(1));
  }

  /**
   * Test {@link Endpoints#withEndpoint(String, String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return stream limit five collect toList size is one.
   * </ul>
   *
   * <p>Method under test: {@link Endpoints#withEndpoint(String, String)}
   */
  @Test
  @DisplayName(
      "Test withEndpoint(String, String); when '42'; then return stream limit five collect toList size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Endpoints Endpoints.withEndpoint(String, String)"})
  void testWithEndpoint_when42_thenReturnStreamLimitFiveCollectToListSizeIsOne() {
    // Arrange and Act
    Endpoints actualWithEndpointResult =
        Endpoints.empty().withEndpoint("42", "https://example.org/example");

    // Assert
    Iterator<Endpoint> iteratorResult = actualWithEndpointResult.iterator();
    Endpoint nextResult = iteratorResult.next();
    assertEquals("42", nextResult.getId());
    Stream<Endpoint> streamResult = actualWithEndpointResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(1, collectResult.size());
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, collectResult.get(0));
  }

  /**
   * Test {@link Endpoints#stream()}.
   *
   * <p>Method under test: {@link Endpoints#stream()}
   */
  @Test
  @DisplayName("Test stream()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Stream Endpoints.stream()"})
  void testStream() {
    // Arrange and Act
    Stream<Endpoint> actualStreamResult = Endpoints.empty().stream();

    // Assert
    assertTrue(actualStreamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }
}
