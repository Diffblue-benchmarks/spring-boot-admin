package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.AttributePropertyWriter;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RegistrationBeanSerializerModifierDiffblueTest {
  /**
   * Test {@link RegistrationBeanSerializerModifier#changeProperties(SerializationConfig,
   * BeanDescription, List)}.
   *
   * <ul>
   *   <li>Given {@link Object}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * RegistrationBeanSerializerModifier#changeProperties(SerializationConfig, BeanDescription,
   * List)}
   */
  @Test
  @DisplayName(
      "Test changeProperties(SerializationConfig, BeanDescription, List); given Object; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List RegistrationBeanSerializerModifier.changeProperties(SerializationConfig, BeanDescription, List)"
  })
  void testChangeProperties_givenObject_thenReturnEmpty() {
    // Arrange
    String[] patterns = new String[] {"Patterns"};
    SanitizingMapSerializer metadataSerializer = new SanitizingMapSerializer(patterns);
    RegistrationBeanSerializerModifier registrationBeanSerializerModifier =
        new RegistrationBeanSerializerModifier(metadataSerializer);
    SerializationConfig config = mock(SerializationConfig.class);

    BeanDescription beanDesc = mock(BeanDescription.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(beanDesc.getBeanClass()).thenReturn(forNameResult);

    // Act
    List<BeanPropertyWriter> actualChangePropertiesResult =
        registrationBeanSerializerModifier.changeProperties(config, beanDesc, new ArrayList<>());

    // Assert
    verify(beanDesc).getBeanClass();
    assertTrue(actualChangePropertiesResult.isEmpty());
  }

  /**
   * Test {@link RegistrationBeanSerializerModifier#changeProperties(SerializationConfig,
   * BeanDescription, List)}.
   *
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RegistrationBeanSerializerModifier#changeProperties(SerializationConfig, BeanDescription,
   * List)}
   */
  @Test
  @DisplayName(
      "Test changeProperties(SerializationConfig, BeanDescription, List); then return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List RegistrationBeanSerializerModifier.changeProperties(SerializationConfig, BeanDescription, List)"
  })
  void testChangeProperties_thenReturnArrayList() {
    // Arrange
    String[] patterns = new String[] {"Patterns"};
    SanitizingMapSerializer metadataSerializer = new SanitizingMapSerializer(patterns);
    RegistrationBeanSerializerModifier registrationBeanSerializerModifier =
        new RegistrationBeanSerializerModifier(metadataSerializer);
    SerializationConfig config = mock(SerializationConfig.class);

    BeanDescription beanDesc = mock(BeanDescription.class);
    Class<Registration> forNameResult = Registration.class;
    Mockito.<Class<?>>when(beanDesc.getBeanClass()).thenReturn(forNameResult);

    AttributePropertyWriter attributePropertyWriter = mock(AttributePropertyWriter.class);
    doNothing()
        .when(attributePropertyWriter)
        .assignSerializer(Mockito.<JsonSerializer<Object>>any());
    when(attributePropertyWriter.getName()).thenReturn("metadata");

    ArrayList<BeanPropertyWriter> beanProperties = new ArrayList<>();
    beanProperties.add(attributePropertyWriter);

    // Act
    List<BeanPropertyWriter> actualChangePropertiesResult =
        registrationBeanSerializerModifier.changeProperties(config, beanDesc, beanProperties);

    // Assert
    verify(beanDesc).getBeanClass();
    verify(attributePropertyWriter).assignSerializer(isA(JsonSerializer.class));
    verify(attributePropertyWriter).getName();
    assertSame(beanProperties, actualChangePropertiesResult);
  }

  /**
   * Test {@link RegistrationBeanSerializerModifier#changeProperties(SerializationConfig,
   * BeanDescription, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * RegistrationBeanSerializerModifier#changeProperties(SerializationConfig, BeanDescription,
   * List)}
   */
  @Test
  @DisplayName(
      "Test changeProperties(SerializationConfig, BeanDescription, List); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List RegistrationBeanSerializerModifier.changeProperties(SerializationConfig, BeanDescription, List)"
  })
  void testChangeProperties_whenArrayList_thenReturnEmpty() {
    // Arrange
    String[] patterns = new String[] {"Patterns"};
    SanitizingMapSerializer metadataSerializer = new SanitizingMapSerializer(patterns);
    RegistrationBeanSerializerModifier registrationBeanSerializerModifier =
        new RegistrationBeanSerializerModifier(metadataSerializer);
    SerializationConfig config = mock(SerializationConfig.class);

    BeanDescription beanDesc = mock(BeanDescription.class);
    Class<Registration> forNameResult = Registration.class;
    Mockito.<Class<?>>when(beanDesc.getBeanClass()).thenReturn(forNameResult);

    // Act
    List<BeanPropertyWriter> actualChangePropertiesResult =
        registrationBeanSerializerModifier.changeProperties(config, beanDesc, new ArrayList<>());

    // Assert
    verify(beanDesc).getBeanClass();
    assertTrue(actualChangePropertiesResult.isEmpty());
  }
}
