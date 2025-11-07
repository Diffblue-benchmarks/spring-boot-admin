package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class EndpointDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Endpoint#equals(Object)}
   *   <li>{@link Endpoint#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Endpoint ofResult = Endpoint.of("42", "https://example.org/example");
    Endpoint ofResult2 = Endpoint.of("42", "https://example.org/example");

    // Act and Assert
    assertEquals(ofResult, ofResult2);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult2.hashCode());
  }

  /**
   * Method under test: {@link Endpoint#of(String, String)}
   */
  @Test
  public void testOf() {
    // Arrange and Act
    Endpoint actualOfResult = Endpoint.of("42", "https://example.org/example");

    // Assert
    assertEquals("42", actualOfResult.getId());
    assertEquals("https://example.org/example", actualOfResult.getUrl());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Endpoint#equals(Object)}
   *   <li>{@link Endpoint#hashCode()}
   * </ul>
   */
  @Test
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Endpoint ofResult = Endpoint.of("42", "https://example.org/example");

    // Act and Assert
    assertEquals(ofResult, ofResult);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult.hashCode());
  }

  /**
   * Method under test: {@link Endpoint#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Endpoint ofResult = Endpoint.of("https://example.org/example", "https://example.org/example");

    // Act and Assert
    assertNotEquals(ofResult, Endpoint.of("42", "https://example.org/example"));
  }

  /**
   * Method under test: {@link Endpoint#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Endpoint ofResult = Endpoint.of("42", "42");

    // Act and Assert
    assertNotEquals(ofResult, Endpoint.of("42", "https://example.org/example"));
  }

  /**
   * Method under test: {@link Endpoint#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Endpoint.of("42", "https://example.org/example"), null);
  }

  /**
   * Method under test: {@link Endpoint#equals(Object)}
   */
  @Test
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Endpoint.of("42", "https://example.org/example"), "Different type to Endpoint");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Endpoint#toString()}
   *   <li>{@link Endpoint#getId()}
   *   <li>{@link Endpoint#getUrl()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Endpoint ofResult = Endpoint.of("42", "https://example.org/example");

    // Act
    String actualToStringResult = ofResult.toString();
    String actualId = ofResult.getId();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Endpoint(id=42, url=https://example.org/example)", actualToStringResult);
    assertEquals("https://example.org/example", ofResult.getUrl());
  }
}
