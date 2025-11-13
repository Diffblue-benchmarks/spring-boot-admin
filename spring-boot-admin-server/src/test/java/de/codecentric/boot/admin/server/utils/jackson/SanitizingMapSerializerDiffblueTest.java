package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SanitizingMapSerializerDiffblueTest {
  /**
   * Test {@link SanitizingMapSerializer#SanitizingMapSerializer(String[])}.
   *
   * <p>Method under test: {@link SanitizingMapSerializer#SanitizingMapSerializer(String[])}
   */
  @Test
  @DisplayName("Test new SanitizingMapSerializer(String[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SanitizingMapSerializer.<init>(String[])"})
  void testNewSanitizingMapSerializer() {
    // Arrange
    String[] patterns = new String[] {"Patterns"};

    // Act
    SanitizingMapSerializer actualSanitizingMapSerializer = new SanitizingMapSerializer(patterns);

    // Assert
    assertNull(actualSanitizingMapSerializer.getDelegatee());
    assertFalse(actualSanitizingMapSerializer.isUnwrappingSerializer());
  }

  /**
   * Test {@link SanitizingMapSerializer#serialize(Map, JsonGenerator, SerializerProvider)} with
   * {@code Map}, {@code JsonGenerator}, {@code SerializerProvider}.
   *
   * <p>Method under test: {@link SanitizingMapSerializer#serialize(Map, JsonGenerator,
   * SerializerProvider)}
   */
  @Test
  @DisplayName(
      "Test serialize(Map, JsonGenerator, SerializerProvider) with 'Map', 'JsonGenerator', 'SerializerProvider'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SanitizingMapSerializer.serialize(Map, JsonGenerator, SerializerProvider)"
  })
  void testSerializeWithMapJsonGeneratorSerializerProvider() throws IOException {
    // Arrange
    SanitizingMapSerializer sanitizingMapSerializer = new SanitizingMapSerializer(new String[] {});

    HashMap<String, String> value = new HashMap<>();
    value.put("Key", "42");

    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeFieldName(Mockito.<String>any());
    doNothing().when(d).writeString(Mockito.<String>any());
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(d);
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d2, true);

    // Act
    sanitizingMapSerializer.serialize(value, gen, new Impl());

    // Assert
    verify(d).writeEndObject();
    verify(d).writeFieldName("Key");
    verify(d).writeStartObject();
    verify(d).writeString("42");
  }

  /**
   * Test {@link SanitizingMapSerializer#serialize(Map, JsonGenerator, SerializerProvider)} with
   * {@code Map}, {@code JsonGenerator}, {@code SerializerProvider}.
   *
   * <p>Method under test: {@link SanitizingMapSerializer#serialize(Map, JsonGenerator,
   * SerializerProvider)}
   */
  @Test
  @DisplayName(
      "Test serialize(Map, JsonGenerator, SerializerProvider) with 'Map', 'JsonGenerator', 'SerializerProvider'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SanitizingMapSerializer.serialize(Map, JsonGenerator, SerializerProvider)"
  })
  void testSerializeWithMapJsonGeneratorSerializerProvider2() throws IOException {
    // Arrange
    SanitizingMapSerializer sanitizingMapSerializer =
        new SanitizingMapSerializer(new String[] {"patterns", "Patterns"});

    HashMap<String, String> value = new HashMap<>();
    value.put("Key", "42");

    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeFieldName(Mockito.<String>any());
    doNothing().when(d).writeString(Mockito.<String>any());
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(d);
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d2, true);

    // Act
    sanitizingMapSerializer.serialize(value, gen, new Impl());

    // Assert
    verify(d).writeEndObject();
    verify(d).writeFieldName("Key");
    verify(d).writeStartObject();
    verify(d).writeString("42");
  }

  /**
   * Test {@link SanitizingMapSerializer#serialize(Map, JsonGenerator, SerializerProvider)} with
   * {@code Map}, {@code JsonGenerator}, {@code SerializerProvider}.
   *
   * <ul>
   *   <li>Given {@code patterns}.
   * </ul>
   *
   * <p>Method under test: {@link SanitizingMapSerializer#serialize(Map, JsonGenerator,
   * SerializerProvider)}
   */
  @Test
  @DisplayName(
      "Test serialize(Map, JsonGenerator, SerializerProvider) with 'Map', 'JsonGenerator', 'SerializerProvider'; given 'patterns'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SanitizingMapSerializer.serialize(Map, JsonGenerator, SerializerProvider)"
  })
  void testSerializeWithMapJsonGeneratorSerializerProvider_givenPatterns() throws IOException {
    // Arrange
    String[] patterns = new String[] {"Patterns"};
    SanitizingMapSerializer sanitizingMapSerializer = new SanitizingMapSerializer(patterns);

    HashMap<String, String> value = new HashMap<>();
    value.put("patterns", "42");

    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeFieldName(Mockito.<String>any());
    doNothing().when(d).writeString(Mockito.<String>any());
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(d);
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d2, true);

    // Act
    sanitizingMapSerializer.serialize(value, gen, new Impl());

    // Assert
    verify(d).writeEndObject();
    verify(d).writeFieldName("patterns");
    verify(d).writeStartObject();
    verify(d).writeString("******");
  }

  /**
   * Test {@link SanitizingMapSerializer#serialize(Map, JsonGenerator, SerializerProvider)} with
   * {@code Map}, {@code JsonGenerator}, {@code SerializerProvider}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link SanitizingMapSerializer#serialize(Map, JsonGenerator,
   * SerializerProvider)}
   */
  @Test
  @DisplayName(
      "Test serialize(Map, JsonGenerator, SerializerProvider) with 'Map', 'JsonGenerator', 'SerializerProvider'; when HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SanitizingMapSerializer.serialize(Map, JsonGenerator, SerializerProvider)"
  })
  void testSerializeWithMapJsonGeneratorSerializerProvider_whenHashMap() throws IOException {
    // Arrange
    String[] patterns = new String[] {"Patterns"};
    SanitizingMapSerializer sanitizingMapSerializer = new SanitizingMapSerializer(patterns);
    HashMap<String, String> value = new HashMap<>();

    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(d);
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d2, true);

    // Act
    sanitizingMapSerializer.serialize(value, gen, new Impl());

    // Assert
    verify(d).writeEndObject();
    verify(d).writeStartObject();
  }

  /**
   * Test {@link SanitizingMapSerializer#serialize(Map, JsonGenerator, SerializerProvider)} with
   * {@code Map}, {@code JsonGenerator}, {@code SerializerProvider}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SanitizingMapSerializer#serialize(Map, JsonGenerator,
   * SerializerProvider)}
   */
  @Test
  @DisplayName(
      "Test serialize(Map, JsonGenerator, SerializerProvider) with 'Map', 'JsonGenerator', 'SerializerProvider'; when HashMap() 'Key' is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SanitizingMapSerializer.serialize(Map, JsonGenerator, SerializerProvider)"
  })
  void testSerializeWithMapJsonGeneratorSerializerProvider_whenHashMapKeyIs42() throws IOException {
    // Arrange
    String[] patterns = new String[] {"Patterns"};
    SanitizingMapSerializer sanitizingMapSerializer = new SanitizingMapSerializer(patterns);

    HashMap<String, String> value = new HashMap<>();
    value.put("Key", "42");

    JsonGenerator d = mock(JsonGenerator.class);
    doNothing().when(d).writeFieldName(Mockito.<String>any());
    doNothing().when(d).writeString(Mockito.<String>any());
    doNothing().when(d).writeEndObject();
    doNothing().when(d).writeStartObject();
    JsonGeneratorDelegate d2 = new JsonGeneratorDelegate(d);
    JsonGeneratorDelegate gen = new JsonGeneratorDelegate(d2, true);

    // Act
    sanitizingMapSerializer.serialize(value, gen, new Impl());

    // Assert
    verify(d).writeEndObject();
    verify(d).writeFieldName("Key");
    verify(d).writeStartObject();
    verify(d).writeString("42");
  }
}
