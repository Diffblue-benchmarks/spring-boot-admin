package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

public class EndpointsDiffblueTest {
  /**
   * Method under test: {@link Endpoints#get(String)}
   */
  @Test
  public void testGet() {
    // Arrange, Act and Assert
    assertFalse(Endpoints.empty().get("42").isPresent());
  }

  /**
   * Method under test: {@link Endpoints#isPresent(String)}
   */
  @Test
  public void testIsPresent() {
    // Arrange, Act and Assert
    assertFalse(Endpoints.empty().isPresent("42"));
    assertTrue(Endpoints.single("42", "https://example.org/example").isPresent("42"));
  }

  /**
   * Method under test: {@link Endpoints#iterator()}
   */
  @Test
  public void testIterator() {
    // Arrange, Act and Assert
    assertFalse(Endpoints.empty().iterator().hasNext());
  }

  /**
   * Method under test: {@link Endpoints#empty()}
   */
  @Test
  public void testEmpty() {
    // Arrange and Act
    Endpoints actualEmptyResult = Endpoints.empty();

    // Assert
    assertFalse(actualEmptyResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualEmptyResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Method under test: {@link Endpoints#single(String, String)}
   */
  @Test
  public void testSingle() {
    // Arrange and Act
    Endpoints actualSingleResult = Endpoints.single("42", "https://example.org/example");

    // Assert
    Iterator<Endpoint> iteratorResult = actualSingleResult.iterator();
    Endpoint nextResult = iteratorResult.next();
    assertEquals("42", nextResult.getId());
    assertEquals("https://example.org/example", nextResult.getUrl());
    Stream<Endpoint> streamResult = actualSingleResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(1, collectResult.size());
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, collectResult.get(0));
  }

  /**
   * Method under test: {@link Endpoints#of(Collection)}
   */
  @Test
  public void testOf() {
    // Arrange and Act
    Endpoints actualOfResult = Endpoints.of(new ArrayList<>());

    // Assert
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Method under test: {@link Endpoints#of(Collection)}
   */
  @Test
  public void testOf2() {
    // Arrange and Act
    Endpoints actualOfResult = Endpoints.of(null);

    // Assert
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Method under test: {@link Endpoints#of(Collection)}
   */
  @Test
  public void testOf3() {
    // Arrange
    Endpoint ofResult = Endpoint.of("42", "https://example.org/example");

    ArrayList<Endpoint> endpoints = new ArrayList<>();
    endpoints.add(ofResult);

    // Act
    Endpoints actualOfResult = Endpoints.of(endpoints);

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
   * Method under test: {@link Endpoints#withEndpoint(String, String)}
   */
  @Test
  public void testWithEndpoint() {
    // Arrange and Act
    Endpoints actualWithEndpointResult = Endpoints.empty().withEndpoint("42", "https://example.org/example");

    // Assert
    Iterator<Endpoint> iteratorResult = actualWithEndpointResult.iterator();
    Endpoint nextResult = iteratorResult.next();
    assertEquals("42", nextResult.getId());
    assertEquals("https://example.org/example", nextResult.getUrl());
    Stream<Endpoint> streamResult = actualWithEndpointResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(1, collectResult.size());
    assertFalse(iteratorResult.hasNext());
    assertSame(nextResult, collectResult.get(0));
  }

  /**
   * Method under test: {@link Endpoints#withEndpoint(String, String)}
   */
  @Test
  public void testWithEndpoint2() {
    // Arrange and Act
    Endpoints actualWithEndpointResult = Endpoints.single("Id", "https://example.org/example")
        .withEndpoint("42", "https://example.org/example");

    // Assert
    Iterator<Endpoint> iteratorResult = actualWithEndpointResult.iterator();
    Endpoint nextResult = iteratorResult.next();
    Endpoint nextResult2 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertEquals("42", nextResult2.getId());
    assertEquals("Id", nextResult.getId());
    assertEquals("https://example.org/example", nextResult.getUrl());
    assertEquals("https://example.org/example", nextResult2.getUrl());
    Stream<Endpoint> streamResult = actualWithEndpointResult.stream();
    List<Endpoint> collectResult = streamResult.limit(5).collect(Collectors.toList());
    assertEquals(2, collectResult.size());
    assertFalse(actualHasNextResult);
    assertSame(nextResult, collectResult.get(0));
    assertSame(nextResult2, collectResult.get(1));
  }

  /**
   * Method under test: {@link Endpoints#stream()}
   */
  @Test
  public void testStream() {
    // Arrange and Act
    Stream<Endpoint> actualStreamResult = Endpoints.empty().stream();

    // Assert
    assertTrue(actualStreamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Endpoints#equals(Object)}
   *   <li>{@link Endpoints#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Endpoints emptyResult = Endpoints.empty();
    Endpoints emptyResult2 = Endpoints.empty();

    // Act and Assert
    assertEquals(emptyResult, emptyResult2);
    int expectedHashCodeResult = emptyResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Endpoints#equals(Object)}
   *   <li>{@link Endpoints#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Endpoints singleResult = Endpoints.single("42", "https://example.org/example");
    Endpoints singleResult2 = Endpoints.single("42", "https://example.org/example");

    // Act and Assert
    assertEquals(singleResult, singleResult2);
    int expectedHashCodeResult = singleResult.hashCode();
    assertEquals(expectedHashCodeResult, singleResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Endpoints#equals(Object)}
   *   <li>{@link Endpoints#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Endpoints emptyResult = Endpoints.empty();

    // Act and Assert
    assertEquals(emptyResult, emptyResult);
    int expectedHashCodeResult = emptyResult.hashCode();
    assertEquals(expectedHashCodeResult, emptyResult.hashCode());
  }

  /**
   * Method under test: {@link Endpoints#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Endpoints singleResult = Endpoints.single("42", "https://example.org/example");

    // Act and Assert
    assertNotEquals(singleResult, Endpoints.empty());
  }

  /**
   * Method under test: {@link Endpoints#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Endpoints.empty(), null);
  }

  /**
   * Method under test: {@link Endpoints#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Endpoints.empty(), "Different type to Endpoints");
  }

  /**
   * Method under test: {@link Endpoints#toString()}
   */
  @Test
  public void testToString() {
    // Arrange, Act and Assert
    assertEquals("Endpoints(endpoints={})", Endpoints.empty().toString());
  }
}
