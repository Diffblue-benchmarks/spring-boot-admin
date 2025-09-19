package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty.Bogus;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.CoercionConfigs;
import com.fasterxml.jackson.databind.cfg.ConfigOverrides;
import com.fasterxml.jackson.databind.cfg.DatatypeFeatures;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.ext.CoreXMLSerializers;
import com.fasterxml.jackson.databind.ext.CoreXMLSerializers.QNameSerializer;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector.MixInResolver;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy.Provider;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator.Builder;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.AttributePropertyWriter;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import de.codecentric.boot.admin.server.domain.values.Registration;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
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
   *   <li>Given {@link Object}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * RegistrationBeanSerializerModifier#changeProperties(SerializationConfig, BeanDescription,
   * List)}
   */
  @Test
  @DisplayName(
      "Test changeProperties(SerializationConfig, BeanDescription, List); given Object; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List RegistrationBeanSerializerModifier.changeProperties(SerializationConfig, BeanDescription, List)"
  })
  void testChangeProperties_givenObject_thenReturnSizeIsOne() {
    // Arrange
    String[] patterns = new String[] {"Patterns"};
    SanitizingMapSerializer metadataSerializer = new SanitizingMapSerializer(patterns);
    RegistrationBeanSerializerModifier registrationBeanSerializerModifier =
        new RegistrationBeanSerializerModifier(metadataSerializer);
    SerializationConfig config = mock(SerializationConfig.class);

    BeanDescription beanDesc = mock(BeanDescription.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(beanDesc.getBeanClass()).thenReturn(forNameResult);

    ArrayList<BeanPropertyWriter> beanProperties = new ArrayList<>();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.createDefaultInstance();
    StdTypeResolverBuilder typer = new StdTypeResolverBuilder();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder().build();

    BaseSettings base =
        new BaseSettings(
            ci, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64, ptv, new Provider());
    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();

    DeserializationConfig config2 =
        new DeserializationConfig(
            base,
            str,
            mixins,
            rootNames,
            configOverrides,
            new CoercionConfigs(),
            mock(DatatypeFeatures.class));
    JacksonAnnotationIntrospector ai2 = new JacksonAnnotationIntrospector();

    POJOPropertyBuilder propDef =
        new POJOPropertyBuilder(config2, ai2, true, PropertyName.construct("Simple Name"));
    AnnotationMap contextAnnotations = new AnnotationMap();
    PlaceholderForType declaredType = new PlaceholderForType(1);
    QNameSerializer ser = new QNameSerializer();
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.createDefaultInstance();

    Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv2 = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv2);
    AsArrayTypeSerializer typeSer = new AsArrayTypeSerializer(idRes, new Bogus());

    BeanPropertyWriter beanPropertyWriter =
        new BeanPropertyWriter(
            propDef,
            null,
            contextAnnotations,
            declaredType,
            ser,
            typeSer,
            new PlaceholderForType(1),
            true,
            "Suppressable Value");
    beanProperties.add(beanPropertyWriter);

    // Act
    List<BeanPropertyWriter> actualChangePropertiesResult =
        registrationBeanSerializerModifier.changeProperties(config, beanDesc, beanProperties);

    // Assert
    verify(beanDesc).getBeanClass();
    assertEquals(1, actualChangePropertiesResult.size());
    assertSame(beanPropertyWriter, actualChangePropertiesResult.get(0));
  }

  /**
   * Test {@link RegistrationBeanSerializerModifier#changeProperties(SerializationConfig,
   * BeanDescription, List)}.
   *
   * <ul>
   *   <li>Given {@link SimpleDateFormat#SimpleDateFormat(String)} with {@code yyyy/mm/dd}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * RegistrationBeanSerializerModifier#changeProperties(SerializationConfig, BeanDescription,
   * List)}
   */
  @Test
  @DisplayName(
      "Test changeProperties(SerializationConfig, BeanDescription, List); given SimpleDateFormat(String) with 'yyyy/mm/dd'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List RegistrationBeanSerializerModifier.changeProperties(SerializationConfig, BeanDescription, List)"
  })
  void testChangeProperties_givenSimpleDateFormatWithYyyyMmDd_thenReturnSizeIsOne() {
    // Arrange
    String[] patterns = new String[] {"Patterns"};
    SanitizingMapSerializer metadataSerializer = new SanitizingMapSerializer(patterns);
    RegistrationBeanSerializerModifier registrationBeanSerializerModifier =
        new RegistrationBeanSerializerModifier(metadataSerializer);
    SerializationConfig config = mock(SerializationConfig.class);

    BeanDescription beanDesc = mock(BeanDescription.class);
    Class<Registration> forNameResult = Registration.class;
    Mockito.<Class<?>>when(beanDesc.getBeanClass()).thenReturn(forNameResult);

    ArrayList<BeanPropertyWriter> beanProperties = new ArrayList<>();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.createDefaultInstance();
    StdTypeResolverBuilder typer = new StdTypeResolverBuilder();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder().build();

    BaseSettings base =
        new BaseSettings(
            ci, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64, ptv, new Provider());
    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();

    DeserializationConfig config2 =
        new DeserializationConfig(
            base,
            str,
            mixins,
            rootNames,
            configOverrides,
            new CoercionConfigs(),
            mock(DatatypeFeatures.class));
    JacksonAnnotationIntrospector ai2 = new JacksonAnnotationIntrospector();

    POJOPropertyBuilder propDef =
        new POJOPropertyBuilder(config2, ai2, true, PropertyName.construct("Simple Name"));
    AnnotationMap contextAnnotations = new AnnotationMap();
    PlaceholderForType declaredType = new PlaceholderForType(1);
    QNameSerializer ser = new QNameSerializer();
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.createDefaultInstance();

    Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv2 = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv2);
    AsArrayTypeSerializer typeSer = new AsArrayTypeSerializer(idRes, new Bogus());

    BeanPropertyWriter beanPropertyWriter =
        new BeanPropertyWriter(
            propDef,
            null,
            contextAnnotations,
            declaredType,
            ser,
            typeSer,
            new PlaceholderForType(1),
            true,
            "Suppressable Value");
    beanProperties.add(beanPropertyWriter);

    // Act
    List<BeanPropertyWriter> actualChangePropertiesResult =
        registrationBeanSerializerModifier.changeProperties(config, beanDesc, beanProperties);

    // Assert
    verify(beanDesc).getBeanClass();
    assertEquals(1, actualChangePropertiesResult.size());
    assertSame(beanPropertyWriter, actualChangePropertiesResult.get(0));
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
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * RegistrationBeanSerializerModifier#changeProperties(SerializationConfig, BeanDescription,
   * List)}
   */
  @Test
  @DisplayName(
      "Test changeProperties(SerializationConfig, BeanDescription, List); then return size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List RegistrationBeanSerializerModifier.changeProperties(SerializationConfig, BeanDescription, List)"
  })
  void testChangeProperties_thenReturnSizeIsTwo() {
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
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.createDefaultInstance();
    StdTypeResolverBuilder typer = new StdTypeResolverBuilder();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder().build();

    BaseSettings base =
        new BaseSettings(
            ci, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64, ptv, new Provider());
    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();

    DeserializationConfig config2 =
        new DeserializationConfig(
            base,
            str,
            mixins,
            rootNames,
            configOverrides,
            new CoercionConfigs(),
            mock(DatatypeFeatures.class));
    JacksonAnnotationIntrospector ai2 = new JacksonAnnotationIntrospector();

    POJOPropertyBuilder propDef =
        new POJOPropertyBuilder(config2, ai2, true, PropertyName.construct("Simple Name"));
    AnnotationMap contextAnnotations = new AnnotationMap();
    PlaceholderForType declaredType = new PlaceholderForType(1);
    QNameSerializer ser = new QNameSerializer();
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.createDefaultInstance();

    Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv2 = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv2);
    AsArrayTypeSerializer typeSer = new AsArrayTypeSerializer(idRes, new Bogus());

    BeanPropertyWriter beanPropertyWriter =
        new BeanPropertyWriter(
            propDef,
            null,
            contextAnnotations,
            declaredType,
            ser,
            typeSer,
            new PlaceholderForType(1),
            true,
            "Suppressable Value");
    beanProperties.add(beanPropertyWriter);
    beanProperties.add(attributePropertyWriter);

    // Act
    List<BeanPropertyWriter> actualChangePropertiesResult =
        registrationBeanSerializerModifier.changeProperties(config, beanDesc, beanProperties);

    // Assert
    verify(beanDesc).getBeanClass();
    verify(attributePropertyWriter).assignSerializer(isA(JsonSerializer.class));
    verify(attributePropertyWriter).getName();
    assertEquals(2, actualChangePropertiesResult.size());
    assertSame(beanPropertyWriter, actualChangePropertiesResult.get(0));
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
