package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import de.codecentric.boot.admin.server.domain.values.Endpoint;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

public class EndpointsMixinDiffblueTest {
  /**
   * Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  public void testOf() {
    // Arrange and Act
    Endpoints actualOfResult = EndpointsMixin.of(new ArrayList<>());

    // Assert
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  public void testOf2() {
    // Arrange and Act
    Endpoints actualOfResult = EndpointsMixin.of(null);

    // Assert
    assertFalse(actualOfResult.iterator().hasNext());
    Stream<Endpoint> streamResult = actualOfResult.stream();
    assertTrue(streamResult.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Method under test: {@link EndpointsMixin#of(Collection)}
   */
  @Test
  public void testOf3() {
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
}
