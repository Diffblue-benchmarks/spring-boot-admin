package de.codecentric.boot.admin.server.domain.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EndpointDiffblueTest {
  /**
   * Test {@link Endpoint#of(String, String)}.
   * <ul>
   *   <li>When {@code https://example.org/example}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Endpoint#of(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Endpoint Endpoint.of(String, String)"})
  public void testOf_whenHttpsExampleOrgExample_thenReturnIdIs42() {
    // Arrange and Act
    Endpoint actualOfResult = Endpoint.of("42", "https://example.org/example");

    // Assert
    assertEquals("42", actualOfResult.getId());
    assertEquals("https://example.org/example", actualOfResult.getUrl());
  }

  /**
   * Test {@link Endpoint#equals(Object)}, and {@link Endpoint#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Endpoint#equals(Object)}
   *   <li>{@link Endpoint#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Endpoint.equals(Object)", "int Endpoint.hashCode()"})
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
   * Test {@link Endpoint#equals(Object)}, and {@link Endpoint#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Endpoint#equals(Object)}
   *   <li>{@link Endpoint#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Endpoint.equals(Object)", "int Endpoint.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Endpoint ofResult = Endpoint.of("42", "https://example.org/example");

    // Act and Assert
    assertEquals(ofResult, ofResult);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult.hashCode());
  }

  /**
   * Test {@link Endpoint#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Endpoint#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Endpoint.equals(Object)", "int Endpoint.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Endpoint ofResult = Endpoint.of("https://example.org/example", "https://example.org/example");

    // Act and Assert
    assertNotEquals(ofResult, Endpoint.of("42", "https://example.org/example"));
  }

  /**
   * Test {@link Endpoint#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Endpoint#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Endpoint.equals(Object)", "int Endpoint.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Endpoint ofResult = Endpoint.of("42", "42");

    // Act and Assert
    assertNotEquals(ofResult, Endpoint.of("42", "https://example.org/example"));
  }

  /**
   * Test {@link Endpoint#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Endpoint#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Endpoint.equals(Object)", "int Endpoint.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Endpoint.of("42", "https://example.org/example"), null);
  }

  /**
   * Test {@link Endpoint#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Endpoint#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Endpoint.equals(Object)", "int Endpoint.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(Endpoint.of("42", "https://example.org/example"), "Different type to Endpoint");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Endpoint#toString()}
   *   <li>{@link Endpoint#getId()}
   *   <li>{@link Endpoint#getUrl()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String Endpoint.getId()", "String Endpoint.getUrl()", "String Endpoint.toString()"})
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
