package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEndpointsDetectedEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.values.Endpoints;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import java.io.Serializable;
import java.net.URI;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.ConstructorResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.support.DataBindingPropertyAccessor;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardOperatorOverloader;
import org.springframework.expression.spel.support.StandardTypeComparator;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {MicrosoftTeamsNotifier.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class MicrosoftTeamsNotifierDiffblueTest {
  @MockBean
  private InstanceRepository instanceRepository;

  @Autowired
  private MicrosoftTeamsNotifier microsoftTeamsNotifier;

  @MockBean
  private RestTemplate restTemplate;

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#doNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testDoNotify() throws AssertionError {
    // Arrange
    InstanceId instance = InstanceId.of("42");

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(
        microsoftTeamsNotifier.doNotify(new InstanceEndpointsDetectedEvent(instance, 2L, Endpoints.empty()), null));
    createResult.expectComplete().verify();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Fact#hashCode()}
   * </ul>
   */
  @Test
  public void testFactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Fact fact = new MicrosoftTeamsNotifier.Fact("Name", "42");
    MicrosoftTeamsNotifier.Fact fact2 = new MicrosoftTeamsNotifier.Fact("Name", "42");

    // Act and Assert
    assertEquals(fact, fact2);
    int expectedHashCodeResult = fact.hashCode();
    assertEquals(expectedHashCodeResult, fact2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Fact#hashCode()}
   * </ul>
   */
  @Test
  public void testFactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    MicrosoftTeamsNotifier.Fact fact = new MicrosoftTeamsNotifier.Fact(null, "42");
    MicrosoftTeamsNotifier.Fact fact2 = new MicrosoftTeamsNotifier.Fact(null, "42");

    // Act and Assert
    assertEquals(fact, fact2);
    int expectedHashCodeResult = fact.hashCode();
    assertEquals(expectedHashCodeResult, fact2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Fact#hashCode()}
   * </ul>
   */
  @Test
  public void testFactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    MicrosoftTeamsNotifier.Fact fact = new MicrosoftTeamsNotifier.Fact("Name", null);
    MicrosoftTeamsNotifier.Fact fact2 = new MicrosoftTeamsNotifier.Fact("Name", null);

    // Act and Assert
    assertEquals(fact, fact2);
    int expectedHashCodeResult = fact.hashCode();
    assertEquals(expectedHashCodeResult, fact2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Fact#hashCode()}
   * </ul>
   */
  @Test
  public void testFactEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Fact fact = new MicrosoftTeamsNotifier.Fact("Name", "42");

    // Act and Assert
    assertEquals(fact, fact);
    int expectedHashCodeResult = fact.hashCode();
    assertEquals(expectedHashCodeResult, fact.hashCode());
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   */
  @Test
  public void testFactEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Fact fact = new MicrosoftTeamsNotifier.Fact("42", "42");

    // Act and Assert
    assertNotEquals(fact, new MicrosoftTeamsNotifier.Fact("Name", "42"));
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   */
  @Test
  public void testFactEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MicrosoftTeamsNotifier.Fact fact = new MicrosoftTeamsNotifier.Fact(null, "42");

    // Act and Assert
    assertNotEquals(fact, new MicrosoftTeamsNotifier.Fact("Name", "42"));
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   */
  @Test
  public void testFactEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MicrosoftTeamsNotifier.Fact fact = new MicrosoftTeamsNotifier.Fact("Name", "Name");

    // Act and Assert
    assertNotEquals(fact, new MicrosoftTeamsNotifier.Fact("Name", "42"));
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   */
  @Test
  public void testFactEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    MicrosoftTeamsNotifier.Fact fact = new MicrosoftTeamsNotifier.Fact("Name", null);

    // Act and Assert
    assertNotEquals(fact, new MicrosoftTeamsNotifier.Fact("Name", "42"));
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   */
  @Test
  public void testFactEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MicrosoftTeamsNotifier.Fact("Name", "42"), null);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Fact#equals(Object)}
   */
  @Test
  public void testFactEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MicrosoftTeamsNotifier.Fact("Name", "42"), "Different type to Fact");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Fact#Fact(String, String)}
   *   <li>{@link MicrosoftTeamsNotifier.Fact#toString()}
   *   <li>{@link MicrosoftTeamsNotifier.Fact#getName()}
   *   <li>{@link MicrosoftTeamsNotifier.Fact#getValue()}
   * </ul>
   */
  @Test
  public void testFactGettersAndSetters() {
    // Arrange and Act
    MicrosoftTeamsNotifier.Fact actualFact = new MicrosoftTeamsNotifier.Fact("Name", "42");
    String actualToStringResult = actualFact.toString();
    String actualName = actualFact.getName();

    // Assert
    assertEquals("42", actualFact.getValue());
    assertEquals("MicrosoftTeamsNotifier.Fact(name=Name, value=42)", actualToStringResult);
    assertEquals("Name", actualName);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Message#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Message#hashCode()}
   * </ul>
   */
  @Test
  public void testMessageEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Message buildResult = MicrosoftTeamsNotifier.Message.builder()
        .summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    MicrosoftTeamsNotifier.Message buildResult2 = MicrosoftTeamsNotifier.Message.builder()
        .summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Message#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Message#hashCode()}
   * </ul>
   */
  @Test
  public void testMessageEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder.summary(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message buildResult = messageBuilder.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder2 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder2.summary(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message buildResult2 = messageBuilder2.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Message#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Message#hashCode()}
   * </ul>
   */
  @Test
  public void testMessageEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder.themeColor(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder2 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder2.summary(Mockito.<String>any())).thenReturn(messageBuilder);
    MicrosoftTeamsNotifier.Message buildResult = messageBuilder2.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder3 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder3.themeColor(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder4 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder4.summary(Mockito.<String>any())).thenReturn(messageBuilder3);
    MicrosoftTeamsNotifier.Message buildResult2 = messageBuilder4.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Message#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Message#hashCode()}
   * </ul>
   */
  @Test
  public void testMessageEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Message buildResult = MicrosoftTeamsNotifier.Message.builder()
        .summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Message#equals(Object)}
   */
  @Test
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder.summary(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message buildResult = messageBuilder.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    MicrosoftTeamsNotifier.Message buildResult2 = MicrosoftTeamsNotifier.Message.builder()
        .summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Message#equals(Object)}
   */
  @Test
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder.themeColor(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder2 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder2.summary(Mockito.<String>any())).thenReturn(messageBuilder);
    MicrosoftTeamsNotifier.Message buildResult = messageBuilder2.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder3 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder3.summary(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message buildResult2 = messageBuilder3.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Message#equals(Object)}
   */
  @Test
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder.title(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder2 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder2.themeColor(Mockito.<String>any())).thenReturn(messageBuilder);
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder3 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder3.summary(Mockito.<String>any())).thenReturn(messageBuilder2);
    MicrosoftTeamsNotifier.Message buildResult = messageBuilder3.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder4 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder4.themeColor(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder5 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder5.summary(Mockito.<String>any())).thenReturn(messageBuilder4);
    MicrosoftTeamsNotifier.Message buildResult2 = messageBuilder5.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Message#equals(Object)}
   */
  @Test
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    MicrosoftTeamsNotifier.Message buildResult = MicrosoftTeamsNotifier.Message.builder()
        .summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    when(messageBuilder.build()).thenReturn(buildResult);
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder2 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder2.title(Mockito.<String>any())).thenReturn(messageBuilder);
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder3 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder3.themeColor(Mockito.<String>any())).thenReturn(messageBuilder2);
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder4 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder4.summary(Mockito.<String>any())).thenReturn(messageBuilder3);
    MicrosoftTeamsNotifier.Message buildResult2 = messageBuilder4.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder5 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder5.themeColor(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder6 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder6.summary(Mockito.<String>any())).thenReturn(messageBuilder5);
    MicrosoftTeamsNotifier.Message buildResult3 = messageBuilder6.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Message#equals(Object)}
   */
  @Test
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder.summary(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message buildResult = messageBuilder.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder2 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder2.build()).thenReturn(buildResult);
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder3 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder3.title(Mockito.<String>any())).thenReturn(messageBuilder2);
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder4 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder4.themeColor(Mockito.<String>any())).thenReturn(messageBuilder3);
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder5 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder5.summary(Mockito.<String>any())).thenReturn(messageBuilder4);
    MicrosoftTeamsNotifier.Message buildResult2 = messageBuilder5.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder6 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder6.themeColor(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Message.builder());
    MicrosoftTeamsNotifier.Message.MessageBuilder messageBuilder7 = mock(
        MicrosoftTeamsNotifier.Message.MessageBuilder.class);
    when(messageBuilder7.summary(Mockito.<String>any())).thenReturn(messageBuilder6);
    MicrosoftTeamsNotifier.Message buildResult3 = messageBuilder7.summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Message#equals(Object)}
   */
  @Test
  public void testMessageEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Message buildResult = MicrosoftTeamsNotifier.Message.builder()
        .summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Message#equals(Object)}
   */
  @Test
  public void testMessageEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Message buildResult = MicrosoftTeamsNotifier.Message.builder()
        .summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to Message");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MicrosoftTeamsNotifier.Message#Message(String, String, String, List)}
   *   <li>{@link MicrosoftTeamsNotifier.Message#toString()}
   *   <li>{@link MicrosoftTeamsNotifier.Message#getSections()}
   *   <li>{@link MicrosoftTeamsNotifier.Message#getSummary()}
   *   <li>{@link MicrosoftTeamsNotifier.Message#getThemeColor()}
   *   <li>{@link MicrosoftTeamsNotifier.Message#getTitle()}
   * </ul>
   */
  @Test
  public void testMessageGettersAndSetters() {
    // Arrange
    ArrayList<MicrosoftTeamsNotifier.Section> sections = new ArrayList<>();

    // Act
    MicrosoftTeamsNotifier.Message actualMessage = new MicrosoftTeamsNotifier.Message("Summary", "Theme Color", "Dr",
        sections);
    String actualToStringResult = actualMessage.toString();
    List<MicrosoftTeamsNotifier.Section> actualSections = actualMessage.getSections();
    String actualSummary = actualMessage.getSummary();
    String actualThemeColor = actualMessage.getThemeColor();

    // Assert
    assertEquals("Dr", actualMessage.getTitle());
    assertEquals("MicrosoftTeamsNotifier.Message(summary=Summary, themeColor=Theme Color, title=Dr, sections=[])",
        actualToStringResult);
    assertEquals("Summary", actualSummary);
    assertEquals("Theme Color", actualThemeColor);
    assertTrue(actualSections.isEmpty());
    assertSame(sections, actualSections);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Message.MessageBuilder#build()}
   *   <li>{@link MicrosoftTeamsNotifier.Message.MessageBuilder#summary(String)}
   *   <li>{@link MicrosoftTeamsNotifier.Message.MessageBuilder#themeColor(String)}
   *   <li>{@link MicrosoftTeamsNotifier.Message.MessageBuilder#title(String)}
   * </ul>
   */
  @Test
  public void testMessage_MessageBuilderBuild() {
    // Arrange and Act
    MicrosoftTeamsNotifier.Message actualBuildResult = MicrosoftTeamsNotifier.Message.builder()
        .summary("Summary")
        .themeColor("Theme Color")
        .title("Dr")
        .build();

    // Assert
    assertEquals("Dr", actualBuildResult.getTitle());
    assertEquals("Summary", actualBuildResult.getSummary());
    assertEquals("Theme Color", actualBuildResult.getThemeColor());
    assertTrue(actualBuildResult.getSections().isEmpty());
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier.Message.MessageBuilder#sections(List)}
   */
  @Test
  public void testMessage_MessageBuilderSections() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder builderResult = MicrosoftTeamsNotifier.Message.builder();
    ArrayList<MicrosoftTeamsNotifier.Section> sections = new ArrayList<>();

    // Act
    MicrosoftTeamsNotifier.Message.MessageBuilder actualSectionsResult = builderResult.sections(sections);

    // Assert
    assertSame(sections, builderResult.build().getSections());
    assertSame(builderResult, actualSectionsResult);
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier.Message.MessageBuilder#sections(List)}
   */
  @Test
  public void testMessage_MessageBuilderSections2() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder builderResult = MicrosoftTeamsNotifier.Message.builder();

    ArrayList<MicrosoftTeamsNotifier.Section> sections = new ArrayList<>();
    MicrosoftTeamsNotifier.Section buildResult = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();
    sections.add(buildResult);

    // Act
    MicrosoftTeamsNotifier.Message.MessageBuilder actualSectionsResult = builderResult.sections(sections);

    // Assert
    assertSame(sections, builderResult.build().getSections());
    assertSame(builderResult, actualSectionsResult);
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier.Message.MessageBuilder#sections(List)}
   */
  @Test
  public void testMessage_MessageBuilderSections3() {
    // Arrange
    MicrosoftTeamsNotifier.Message.MessageBuilder builderResult = MicrosoftTeamsNotifier.Message.builder();

    ArrayList<MicrosoftTeamsNotifier.Section> sections = new ArrayList<>();
    MicrosoftTeamsNotifier.Section buildResult = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();
    sections.add(buildResult);
    MicrosoftTeamsNotifier.Section buildResult2 = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();
    sections.add(buildResult2);

    // Act
    MicrosoftTeamsNotifier.Message.MessageBuilder actualSectionsResult = builderResult.sections(sections);

    // Assert
    assertSame(sections, builderResult.build().getSections());
    assertSame(builderResult, actualSectionsResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Section#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Section#hashCode()}
   * </ul>
   */
  @Test
  public void testSectionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Section buildResult = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();
    MicrosoftTeamsNotifier.Section buildResult2 = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Section#equals(Object)}
   *   <li>{@link MicrosoftTeamsNotifier.Section#hashCode()}
   * </ul>
   */
  @Test
  public void testSectionEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Section buildResult = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Section#equals(Object)}
   */
  @Test
  public void testSectionEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    when(sectionBuilder.activitySubtitle(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Section.builder());
    MicrosoftTeamsNotifier.Section buildResult = sectionBuilder.activitySubtitle("Dr").activityTitle("Dr").build();
    MicrosoftTeamsNotifier.Section buildResult2 = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Section#equals(Object)}
   */
  @Test
  public void testSectionEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    when(sectionBuilder.activityTitle(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Section.builder());
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder2 = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    when(sectionBuilder2.activitySubtitle(Mockito.<String>any())).thenReturn(sectionBuilder);
    MicrosoftTeamsNotifier.Section buildResult = sectionBuilder2.activitySubtitle("Dr").activityTitle("Dr").build();
    MicrosoftTeamsNotifier.Section buildResult2 = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Section#equals(Object)}
   */
  @Test
  public void testSectionEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    when(sectionBuilder.activityTitle(Mockito.<String>any())).thenReturn(MicrosoftTeamsNotifier.Section.builder());
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder2 = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    when(sectionBuilder2.activitySubtitle(Mockito.<String>any())).thenReturn(sectionBuilder);
    MicrosoftTeamsNotifier.Section buildResult = sectionBuilder2.activitySubtitle("Dr").activityTitle("Dr").build();
    MicrosoftTeamsNotifier.Section buildResult2 = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle(null)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Section#equals(Object)}
   */
  @Test
  public void testSectionEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    MicrosoftTeamsNotifier.Section buildResult = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();
    when(sectionBuilder.build()).thenReturn(buildResult);
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder2 = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    when(sectionBuilder2.activityTitle(Mockito.<String>any())).thenReturn(sectionBuilder);
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder3 = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    when(sectionBuilder3.activitySubtitle(Mockito.<String>any())).thenReturn(sectionBuilder2);
    MicrosoftTeamsNotifier.Section buildResult2 = sectionBuilder3.activitySubtitle("Dr").activityTitle("Dr").build();
    MicrosoftTeamsNotifier.Section buildResult3 = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle(null)
        .build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Section#equals(Object)}
   */
  @Test
  public void testSectionEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    MicrosoftTeamsNotifier.Section buildResult = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Mr")
        .activityTitle("Dr")
        .build();
    when(sectionBuilder.build()).thenReturn(buildResult);
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder2 = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    when(sectionBuilder2.activityTitle(Mockito.<String>any())).thenReturn(sectionBuilder);
    MicrosoftTeamsNotifier.Section.SectionBuilder sectionBuilder3 = mock(
        MicrosoftTeamsNotifier.Section.SectionBuilder.class);
    when(sectionBuilder3.activitySubtitle(Mockito.<String>any())).thenReturn(sectionBuilder2);
    MicrosoftTeamsNotifier.Section buildResult2 = sectionBuilder3.activitySubtitle("Dr").activityTitle("Dr").build();
    MicrosoftTeamsNotifier.Section buildResult3 = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Section#equals(Object)}
   */
  @Test
  public void testSectionEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Section buildResult = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier.Section#equals(Object)}
   */
  @Test
  public void testSectionEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    MicrosoftTeamsNotifier.Section buildResult = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to Section");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Section#Section(String, String, List)}
   *   <li>{@link MicrosoftTeamsNotifier.Section#toString()}
   *   <li>{@link MicrosoftTeamsNotifier.Section#getActivitySubtitle()}
   *   <li>{@link MicrosoftTeamsNotifier.Section#getActivityTitle()}
   *   <li>{@link MicrosoftTeamsNotifier.Section#getFacts()}
   * </ul>
   */
  @Test
  public void testSectionGettersAndSetters() {
    // Arrange
    ArrayList<MicrosoftTeamsNotifier.Fact> facts = new ArrayList<>();

    // Act
    MicrosoftTeamsNotifier.Section actualSection = new MicrosoftTeamsNotifier.Section("Dr", "Dr", facts);
    String actualToStringResult = actualSection.toString();
    String actualActivitySubtitle = actualSection.getActivitySubtitle();
    String actualActivityTitle = actualSection.getActivityTitle();
    List<MicrosoftTeamsNotifier.Fact> actualFacts = actualSection.getFacts();

    // Assert
    assertEquals("Dr", actualActivitySubtitle);
    assertEquals("Dr", actualActivityTitle);
    assertEquals("MicrosoftTeamsNotifier.Section(activityTitle=Dr, activitySubtitle=Dr, facts=[])",
        actualToStringResult);
    assertTrue(actualFacts.isEmpty());
    assertSame(facts, actualFacts);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier.Section.SectionBuilder#build()}
   *   <li>
   * {@link MicrosoftTeamsNotifier.Section.SectionBuilder#activitySubtitle(String)}
   *   <li>
   * {@link MicrosoftTeamsNotifier.Section.SectionBuilder#activityTitle(String)}
   * </ul>
   */
  @Test
  public void testSection_SectionBuilderBuild() {
    // Arrange and Act
    MicrosoftTeamsNotifier.Section actualBuildResult = MicrosoftTeamsNotifier.Section.builder()
        .activitySubtitle("Dr")
        .activityTitle("Dr")
        .build();

    // Assert
    assertEquals("Dr", actualBuildResult.getActivitySubtitle());
    assertEquals("Dr", actualBuildResult.getActivityTitle());
    assertTrue(actualBuildResult.getFacts().isEmpty());
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier.Section.SectionBuilder#facts(List)}
   */
  @Test
  public void testSection_SectionBuilderFacts() {
    // Arrange
    MicrosoftTeamsNotifier.Section.SectionBuilder builderResult = MicrosoftTeamsNotifier.Section.builder();
    ArrayList<MicrosoftTeamsNotifier.Fact> facts = new ArrayList<>();

    // Act
    MicrosoftTeamsNotifier.Section.SectionBuilder actualFactsResult = builderResult.facts(facts);

    // Assert
    assertSame(facts, builderResult.build().getFacts());
    assertSame(builderResult, actualFactsResult);
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier.Section.SectionBuilder#facts(List)}
   */
  @Test
  public void testSection_SectionBuilderFacts2() {
    // Arrange
    MicrosoftTeamsNotifier.Section.SectionBuilder builderResult = MicrosoftTeamsNotifier.Section.builder();

    ArrayList<MicrosoftTeamsNotifier.Fact> facts = new ArrayList<>();
    facts.add(new MicrosoftTeamsNotifier.Fact("Name", "42"));

    // Act
    MicrosoftTeamsNotifier.Section.SectionBuilder actualFactsResult = builderResult.facts(facts);

    // Assert
    assertSame(facts, builderResult.build().getFacts());
    assertSame(builderResult, actualFactsResult);
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier.Section.SectionBuilder#facts(List)}
   */
  @Test
  public void testSection_SectionBuilderFacts3() {
    // Arrange
    MicrosoftTeamsNotifier.Section.SectionBuilder builderResult = MicrosoftTeamsNotifier.Section.builder();

    ArrayList<MicrosoftTeamsNotifier.Fact> facts = new ArrayList<>();
    facts.add(new MicrosoftTeamsNotifier.Fact("Name", "42"));
    facts.add(new MicrosoftTeamsNotifier.Fact("Name", "42"));

    // Act
    MicrosoftTeamsNotifier.Section.SectionBuilder actualFactsResult = builderResult.facts(facts);

    // Assert
    assertSame(facts, builderResult.build().getFacts());
    assertSame(builderResult, actualFactsResult);
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#shouldNotify(InstanceEvent, Instance)}
   */
  @Test
  public void testShouldNotify() {
    // Arrange, Act and Assert
    assertTrue(microsoftTeamsNotifier.shouldNotify(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null));
    assertTrue(microsoftTeamsNotifier.shouldNotify(new InstanceRegisteredEvent(InstanceId.of("42"), 1L, null), null));
    assertFalse(microsoftTeamsNotifier.shouldNotify(null, null));
    assertTrue(microsoftTeamsNotifier.shouldNotify(mock(InstanceRegisteredEvent.class), null));
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#evaluateExpression(EvaluationContext, Expression)}
   */
  @Test
  public void testEvaluateExpression() {
    // Arrange
    StandardEvaluationContext context = new StandardEvaluationContext();

    // Act and Assert
    assertEquals("42", microsoftTeamsNotifier.evaluateExpression(context, new LiteralExpression("42")));
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#evaluateExpression(EvaluationContext, Expression)}
   */
  @Test
  public void testEvaluateExpression2() {
    // Arrange
    StandardEvaluationContext context = new StandardEvaluationContext();
    context.addConstructorResolver(mock(ConstructorResolver.class));

    // Act and Assert
    assertEquals("42", microsoftTeamsNotifier.evaluateExpression(context, new LiteralExpression("42")));
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#createEvaluationContext(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateEvaluationContext() {
    // Arrange
    InstanceDeregisteredEvent event = new InstanceDeregisteredEvent(InstanceId.of("42"), 1L);

    // Act
    EvaluationContext actualCreateEvaluationContextResult = microsoftTeamsNotifier.createEvaluationContext(event, null);

    // Assert
    TypedValue rootObject = actualCreateEvaluationContextResult.getRootObject();
    Object value = rootObject.getValue();
    assertTrue(value instanceof Map);
    List<PropertyAccessor> propertyAccessors = actualCreateEvaluationContextResult.getPropertyAccessors();
    assertEquals(2, propertyAccessors.size());
    PropertyAccessor getResult = propertyAccessors.get(1);
    assertTrue(getResult instanceof MapAccessor);
    PropertyAccessor getResult2 = propertyAccessors.get(0);
    assertTrue(getResult2 instanceof DataBindingPropertyAccessor);
    assertTrue(actualCreateEvaluationContextResult instanceof SimpleEvaluationContext);
    assertTrue(actualCreateEvaluationContextResult.getOperatorOverloader() instanceof StandardOperatorOverloader);
    assertTrue(actualCreateEvaluationContextResult.getTypeComparator() instanceof StandardTypeComparator);
    assertTrue(actualCreateEvaluationContextResult.getTypeConverter() instanceof StandardTypeConverter);
    assertEquals(3, ((Map<String, Object>) value).size());
    assertEquals("UNKNOWN", ((Map<String, Object>) value).get("lastStatus"));
    TypeDescriptor typeDescriptor = rootObject.getTypeDescriptor();
    assertEquals("java.util.HashMap", typeDescriptor.getName());
    assertNull(getResult2.getSpecificTargetClasses());
    ResolvableType resolvableType = typeDescriptor.getResolvableType();
    ResolvableType componentType = resolvableType.getComponentType();
    assertNull(componentType.getRawClass());
    ResolvableType[] generics = resolvableType.getGenerics();
    ResolvableType resolvableType2 = generics[0];
    assertNull(resolvableType2.getRawClass());
    ResolvableType resolvableType3 = generics[1];
    assertNull(resolvableType3.getRawClass());
    ResolvableType superType = resolvableType.getSuperType();
    ResolvableType[] interfaces = superType.getInterfaces();
    ResolvableType resolvableType4 = interfaces[0];
    ResolvableType[] generics2 = resolvableType4.getGenerics();
    ResolvableType resolvableType5 = generics2[0];
    assertNull(resolvableType5.getRawClass());
    ResolvableType resolvableType6 = generics2[1];
    assertNull(resolvableType6.getRawClass());
    assertNull(((Map<String, Object>) value).get("instance"));
    assertNull(typeDescriptor.getElementTypeDescriptor());
    assertNull(typeDescriptor.getMapKeyTypeDescriptor());
    assertNull(typeDescriptor.getMapValueTypeDescriptor());
    assertNull(actualCreateEvaluationContextResult.getBeanResolver());
    ResolvableType[] generics3 = componentType.getGenerics();
    assertEquals(0, generics3.length);
    assertEquals(0, typeDescriptor.getAnnotations().length);
    assertEquals(1, interfaces.length);
    Class<?>[] specificTargetClasses = getResult.getSpecificTargetClasses();
    assertEquals(1, specificTargetClasses.length);
    assertEquals(2, superType.getGenerics().length);
    assertEquals(2, generics.length);
    assertEquals(2, generics2.length);
    ResolvableType[] interfaces2 = resolvableType.getInterfaces();
    ResolvableType resolvableType7 = interfaces2[0];
    assertEquals(2, resolvableType7.getGenerics().length);
    assertEquals(3, interfaces2.length);
    assertFalse(componentType.hasGenerics());
    ResolvableType superType2 = superType.getSuperType();
    assertFalse(superType2.hasGenerics());
    assertFalse(resolvableType2.hasGenerics());
    assertFalse(resolvableType3.hasGenerics());
    assertFalse(resolvableType5.hasGenerics());
    assertFalse(resolvableType6.hasGenerics());
    ResolvableType resolvableType8 = interfaces2[1];
    assertFalse(resolvableType8.hasGenerics());
    ResolvableType resolvableType9 = interfaces2[2];
    assertFalse(resolvableType9.hasGenerics());
    assertFalse(componentType.hasResolvableGenerics());
    assertFalse(superType2.hasResolvableGenerics());
    assertFalse(superType.hasResolvableGenerics());
    assertFalse(resolvableType.hasResolvableGenerics());
    assertFalse(resolvableType2.hasResolvableGenerics());
    assertFalse(resolvableType3.hasResolvableGenerics());
    assertFalse(resolvableType5.hasResolvableGenerics());
    assertFalse(resolvableType6.hasResolvableGenerics());
    assertFalse(resolvableType4.hasResolvableGenerics());
    assertFalse(resolvableType7.hasResolvableGenerics());
    assertFalse(resolvableType8.hasResolvableGenerics());
    assertFalse(resolvableType9.hasResolvableGenerics());
    assertFalse(componentType.hasUnresolvableGenerics());
    assertFalse(superType2.hasUnresolvableGenerics());
    assertFalse(resolvableType2.hasUnresolvableGenerics());
    assertFalse(resolvableType3.hasUnresolvableGenerics());
    assertFalse(resolvableType5.hasUnresolvableGenerics());
    assertFalse(resolvableType6.hasUnresolvableGenerics());
    assertFalse(resolvableType8.hasUnresolvableGenerics());
    assertFalse(resolvableType9.hasUnresolvableGenerics());
    assertFalse(typeDescriptor.isArray());
    assertFalse(typeDescriptor.isCollection());
    assertFalse(typeDescriptor.isPrimitive());
    List<ConstructorResolver> constructorResolvers = actualCreateEvaluationContextResult.getConstructorResolvers();
    assertTrue(constructorResolvers.isEmpty());
    assertTrue(((MapAccessor) getResult).isCompilable());
    assertTrue(superType.hasGenerics());
    assertTrue(resolvableType.hasGenerics());
    assertTrue(resolvableType4.hasGenerics());
    assertTrue(resolvableType7.hasGenerics());
    assertTrue(superType.hasUnresolvableGenerics());
    assertTrue(resolvableType.hasUnresolvableGenerics());
    assertTrue(resolvableType4.hasUnresolvableGenerics());
    assertTrue(resolvableType7.hasUnresolvableGenerics());
    assertTrue(typeDescriptor.isMap());
    assertTrue(actualCreateEvaluationContextResult.isAssignmentEnabled());
    Class<Serializable> expectedRawClass = Serializable.class;
    Class<?> rawClass = resolvableType9.getRawClass();
    assertEquals(expectedRawClass, rawClass);
    Class<Cloneable> expectedRawClass2 = Cloneable.class;
    Class<?> rawClass2 = resolvableType8.getRawClass();
    assertEquals(expectedRawClass2, rawClass2);
    Class<Object> expectedPropertyType = Object.class;
    Class<?> propertyType = ((MapAccessor) getResult).getPropertyType();
    assertEquals(expectedPropertyType, propertyType);
    Class<AbstractMap> expectedRawClass3 = AbstractMap.class;
    assertEquals(expectedRawClass3, superType.getRawClass());
    Class<HashMap> expectedObjectType = HashMap.class;
    Class<?> objectType = typeDescriptor.getObjectType();
    assertEquals(expectedObjectType, objectType);
    Class<Map> expectedResultClass = Map.class;
    Class<?> resultClass = specificTargetClasses[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(event, ((Map<String, Object>) value).get("event"));
    assertSame(propertyType, superType2.getRawClass());
    assertSame(propertyType, superType2.getSource());
    assertSame(propertyType, superType2.getType());
    ResolvableType componentType2 = componentType.getComponentType();
    assertSame(componentType2, componentType2);
    assertSame(componentType2, superType2.getComponentType());
    assertSame(componentType2, superType.getComponentType());
    assertSame(componentType2, resolvableType2.getComponentType());
    assertSame(componentType2, resolvableType3.getComponentType());
    assertSame(componentType2, resolvableType5.getComponentType());
    assertSame(componentType2, resolvableType6.getComponentType());
    assertSame(componentType2, resolvableType4.getComponentType());
    assertSame(componentType2, resolvableType7.getComponentType());
    assertSame(componentType2, resolvableType8.getComponentType());
    assertSame(componentType2, resolvableType9.getComponentType());
    assertSame(componentType2, componentType.getSuperType());
    assertSame(componentType2, superType2.getSuperType());
    assertSame(componentType2, resolvableType2.getSuperType());
    assertSame(componentType2, resolvableType3.getSuperType());
    assertSame(componentType2, resolvableType5.getSuperType());
    assertSame(componentType2, resolvableType6.getSuperType());
    assertSame(componentType2, resolvableType4.getSuperType());
    assertSame(componentType2, resolvableType7.getSuperType());
    assertSame(componentType2, resolvableType8.getSuperType());
    assertSame(componentType2, resolvableType9.getSuperType());
    assertSame(generics3, superType2.getGenerics());
    assertSame(generics3, resolvableType2.getGenerics());
    assertSame(generics3, resolvableType3.getGenerics());
    assertSame(generics3, resolvableType5.getGenerics());
    assertSame(generics3, resolvableType6.getGenerics());
    assertSame(generics3, resolvableType8.getGenerics());
    assertSame(generics3, resolvableType9.getGenerics());
    assertSame(generics3, componentType.getInterfaces());
    assertSame(generics3, superType2.getInterfaces());
    assertSame(generics3, resolvableType2.getInterfaces());
    assertSame(generics3, resolvableType3.getInterfaces());
    assertSame(generics3, resolvableType5.getInterfaces());
    assertSame(generics3, resolvableType6.getInterfaces());
    assertSame(generics3, resolvableType4.getInterfaces());
    assertSame(generics3, resolvableType7.getInterfaces());
    assertSame(generics3, resolvableType8.getInterfaces());
    assertSame(generics3, resolvableType9.getInterfaces());
    assertSame(rawClass2, resolvableType8.getSource());
    assertSame(rawClass2, resolvableType8.getType());
    assertSame(rawClass, resolvableType9.getSource());
    assertSame(rawClass, resolvableType9.getType());
    assertSame(objectType, resolvableType.getRawClass());
    assertSame(objectType, resolvableType.getSource());
    assertSame(objectType, resolvableType.getType());
    assertSame(objectType, typeDescriptor.getSource());
    assertSame(objectType, typeDescriptor.getType());
    assertSame(constructorResolvers, actualCreateEvaluationContextResult.getIndexAccessors());
    assertSame(constructorResolvers, actualCreateEvaluationContextResult.getMethodResolvers());
    assertSame(resultClass, resolvableType4.getRawClass());
    assertSame(resultClass, resolvableType7.getRawClass());
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#createEvaluationContext(InstanceEvent, Instance)}
   */
  @Test
  public void testCreateEvaluationContext2() {
    // Arrange
    InstanceEvent event = mock(InstanceEvent.class);
    when(event.getInstance()).thenReturn(InstanceId.of("42"));

    // Act
    EvaluationContext actualCreateEvaluationContextResult = microsoftTeamsNotifier.createEvaluationContext(event, null);

    // Assert
    verify(event).getInstance();
    TypedValue rootObject = actualCreateEvaluationContextResult.getRootObject();
    Object value = rootObject.getValue();
    assertTrue(value instanceof Map);
    List<PropertyAccessor> propertyAccessors = actualCreateEvaluationContextResult.getPropertyAccessors();
    assertEquals(2, propertyAccessors.size());
    PropertyAccessor getResult = propertyAccessors.get(1);
    assertTrue(getResult instanceof MapAccessor);
    PropertyAccessor getResult2 = propertyAccessors.get(0);
    assertTrue(getResult2 instanceof DataBindingPropertyAccessor);
    assertTrue(actualCreateEvaluationContextResult instanceof SimpleEvaluationContext);
    assertTrue(actualCreateEvaluationContextResult.getOperatorOverloader() instanceof StandardOperatorOverloader);
    assertTrue(actualCreateEvaluationContextResult.getTypeComparator() instanceof StandardTypeComparator);
    assertTrue(actualCreateEvaluationContextResult.getTypeConverter() instanceof StandardTypeConverter);
    assertEquals(3, ((Map<String, Object>) value).size());
    assertEquals("UNKNOWN", ((Map<String, Object>) value).get("lastStatus"));
    TypeDescriptor typeDescriptor = rootObject.getTypeDescriptor();
    assertEquals("java.util.HashMap", typeDescriptor.getName());
    assertNull(getResult2.getSpecificTargetClasses());
    ResolvableType resolvableType = typeDescriptor.getResolvableType();
    ResolvableType componentType = resolvableType.getComponentType();
    assertNull(componentType.getRawClass());
    ResolvableType[] generics = resolvableType.getGenerics();
    ResolvableType resolvableType2 = generics[0];
    assertNull(resolvableType2.getRawClass());
    ResolvableType resolvableType3 = generics[1];
    assertNull(resolvableType3.getRawClass());
    ResolvableType superType = resolvableType.getSuperType();
    ResolvableType[] interfaces = superType.getInterfaces();
    ResolvableType resolvableType4 = interfaces[0];
    ResolvableType[] generics2 = resolvableType4.getGenerics();
    ResolvableType resolvableType5 = generics2[0];
    assertNull(resolvableType5.getRawClass());
    ResolvableType resolvableType6 = generics2[1];
    assertNull(resolvableType6.getRawClass());
    assertNull(((Map<String, Object>) value).get("instance"));
    assertNull(typeDescriptor.getElementTypeDescriptor());
    assertNull(typeDescriptor.getMapKeyTypeDescriptor());
    assertNull(typeDescriptor.getMapValueTypeDescriptor());
    assertNull(actualCreateEvaluationContextResult.getBeanResolver());
    ResolvableType[] generics3 = componentType.getGenerics();
    assertEquals(0, generics3.length);
    assertEquals(0, typeDescriptor.getAnnotations().length);
    assertEquals(1, interfaces.length);
    Class<?>[] specificTargetClasses = getResult.getSpecificTargetClasses();
    assertEquals(1, specificTargetClasses.length);
    assertEquals(2, superType.getGenerics().length);
    assertEquals(2, generics.length);
    assertEquals(2, generics2.length);
    ResolvableType[] interfaces2 = resolvableType.getInterfaces();
    ResolvableType resolvableType7 = interfaces2[0];
    assertEquals(2, resolvableType7.getGenerics().length);
    assertEquals(3, interfaces2.length);
    assertFalse(componentType.hasGenerics());
    ResolvableType superType2 = superType.getSuperType();
    assertFalse(superType2.hasGenerics());
    assertFalse(resolvableType2.hasGenerics());
    assertFalse(resolvableType3.hasGenerics());
    assertFalse(resolvableType5.hasGenerics());
    assertFalse(resolvableType6.hasGenerics());
    ResolvableType resolvableType8 = interfaces2[1];
    assertFalse(resolvableType8.hasGenerics());
    ResolvableType resolvableType9 = interfaces2[2];
    assertFalse(resolvableType9.hasGenerics());
    assertFalse(componentType.hasResolvableGenerics());
    assertFalse(superType2.hasResolvableGenerics());
    assertFalse(superType.hasResolvableGenerics());
    assertFalse(resolvableType.hasResolvableGenerics());
    assertFalse(resolvableType2.hasResolvableGenerics());
    assertFalse(resolvableType3.hasResolvableGenerics());
    assertFalse(resolvableType5.hasResolvableGenerics());
    assertFalse(resolvableType6.hasResolvableGenerics());
    assertFalse(resolvableType4.hasResolvableGenerics());
    assertFalse(resolvableType7.hasResolvableGenerics());
    assertFalse(resolvableType8.hasResolvableGenerics());
    assertFalse(resolvableType9.hasResolvableGenerics());
    assertFalse(componentType.hasUnresolvableGenerics());
    assertFalse(superType2.hasUnresolvableGenerics());
    assertFalse(resolvableType2.hasUnresolvableGenerics());
    assertFalse(resolvableType3.hasUnresolvableGenerics());
    assertFalse(resolvableType5.hasUnresolvableGenerics());
    assertFalse(resolvableType6.hasUnresolvableGenerics());
    assertFalse(resolvableType8.hasUnresolvableGenerics());
    assertFalse(resolvableType9.hasUnresolvableGenerics());
    assertFalse(typeDescriptor.isArray());
    assertFalse(typeDescriptor.isCollection());
    assertFalse(typeDescriptor.isPrimitive());
    List<ConstructorResolver> constructorResolvers = actualCreateEvaluationContextResult.getConstructorResolvers();
    assertTrue(constructorResolvers.isEmpty());
    assertTrue(((MapAccessor) getResult).isCompilable());
    assertTrue(superType.hasGenerics());
    assertTrue(resolvableType.hasGenerics());
    assertTrue(resolvableType4.hasGenerics());
    assertTrue(resolvableType7.hasGenerics());
    assertTrue(superType.hasUnresolvableGenerics());
    assertTrue(resolvableType.hasUnresolvableGenerics());
    assertTrue(resolvableType4.hasUnresolvableGenerics());
    assertTrue(resolvableType7.hasUnresolvableGenerics());
    assertTrue(typeDescriptor.isMap());
    assertTrue(actualCreateEvaluationContextResult.isAssignmentEnabled());
    Class<Serializable> expectedRawClass = Serializable.class;
    Class<?> rawClass = resolvableType9.getRawClass();
    assertEquals(expectedRawClass, rawClass);
    Class<Cloneable> expectedRawClass2 = Cloneable.class;
    Class<?> rawClass2 = resolvableType8.getRawClass();
    assertEquals(expectedRawClass2, rawClass2);
    Class<Object> expectedPropertyType = Object.class;
    Class<?> propertyType = ((MapAccessor) getResult).getPropertyType();
    assertEquals(expectedPropertyType, propertyType);
    Class<AbstractMap> expectedRawClass3 = AbstractMap.class;
    assertEquals(expectedRawClass3, superType.getRawClass());
    Class<HashMap> expectedObjectType = HashMap.class;
    Class<?> objectType = typeDescriptor.getObjectType();
    assertEquals(expectedObjectType, objectType);
    Class<Map> expectedResultClass = Map.class;
    Class<?> resultClass = specificTargetClasses[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(propertyType, superType2.getRawClass());
    assertSame(propertyType, superType2.getSource());
    assertSame(propertyType, superType2.getType());
    ResolvableType componentType2 = componentType.getComponentType();
    assertSame(componentType2, componentType2);
    assertSame(componentType2, superType2.getComponentType());
    assertSame(componentType2, superType.getComponentType());
    assertSame(componentType2, resolvableType2.getComponentType());
    assertSame(componentType2, resolvableType3.getComponentType());
    assertSame(componentType2, resolvableType5.getComponentType());
    assertSame(componentType2, resolvableType6.getComponentType());
    assertSame(componentType2, resolvableType4.getComponentType());
    assertSame(componentType2, resolvableType7.getComponentType());
    assertSame(componentType2, resolvableType8.getComponentType());
    assertSame(componentType2, resolvableType9.getComponentType());
    assertSame(componentType2, componentType.getSuperType());
    assertSame(componentType2, superType2.getSuperType());
    assertSame(componentType2, resolvableType2.getSuperType());
    assertSame(componentType2, resolvableType3.getSuperType());
    assertSame(componentType2, resolvableType5.getSuperType());
    assertSame(componentType2, resolvableType6.getSuperType());
    assertSame(componentType2, resolvableType4.getSuperType());
    assertSame(componentType2, resolvableType7.getSuperType());
    assertSame(componentType2, resolvableType8.getSuperType());
    assertSame(componentType2, resolvableType9.getSuperType());
    assertSame(generics3, superType2.getGenerics());
    assertSame(generics3, resolvableType2.getGenerics());
    assertSame(generics3, resolvableType3.getGenerics());
    assertSame(generics3, resolvableType5.getGenerics());
    assertSame(generics3, resolvableType6.getGenerics());
    assertSame(generics3, resolvableType8.getGenerics());
    assertSame(generics3, resolvableType9.getGenerics());
    assertSame(generics3, componentType.getInterfaces());
    assertSame(generics3, superType2.getInterfaces());
    assertSame(generics3, resolvableType2.getInterfaces());
    assertSame(generics3, resolvableType3.getInterfaces());
    assertSame(generics3, resolvableType5.getInterfaces());
    assertSame(generics3, resolvableType6.getInterfaces());
    assertSame(generics3, resolvableType4.getInterfaces());
    assertSame(generics3, resolvableType7.getInterfaces());
    assertSame(generics3, resolvableType8.getInterfaces());
    assertSame(generics3, resolvableType9.getInterfaces());
    assertSame(rawClass2, resolvableType8.getSource());
    assertSame(rawClass2, resolvableType8.getType());
    assertSame(rawClass, resolvableType9.getSource());
    assertSame(rawClass, resolvableType9.getType());
    assertSame(objectType, resolvableType.getRawClass());
    assertSame(objectType, resolvableType.getSource());
    assertSame(objectType, resolvableType.getType());
    assertSame(objectType, typeDescriptor.getSource());
    assertSame(objectType, typeDescriptor.getType());
    assertSame(constructorResolvers, actualCreateEvaluationContextResult.getIndexAccessors());
    assertSame(constructorResolvers, actualCreateEvaluationContextResult.getMethodResolvers());
    assertSame(resultClass, resolvableType4.getRawClass());
    assertSame(resultClass, resolvableType7.getRawClass());
    assertSame(event, ((Map<String, Object>) value).get("event"));
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier#getThemeColor()}
   */
  @Test
  public void testGetThemeColor() {
    // Arrange, Act and Assert
    assertEquals("event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        microsoftTeamsNotifier.getThemeColor());
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier#setThemeColor(String)}
   */
  @Test
  public void testSetThemeColor() {
    // Arrange and Act
    microsoftTeamsNotifier.setThemeColor("Theme Color");

    // Assert
    assertEquals("Theme Color", microsoftTeamsNotifier.getThemeColor());
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#getDeregisterActivitySubtitle()}
   */
  @Test
  public void testGetDeregisterActivitySubtitle() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        microsoftTeamsNotifier.getDeregisterActivitySubtitle());
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#setDeregisterActivitySubtitle(String)}
   */
  @Test
  public void testSetDeregisterActivitySubtitle() {
    // Arrange and Act
    microsoftTeamsNotifier.setDeregisterActivitySubtitle("Dr");

    // Assert
    assertEquals("Dr", microsoftTeamsNotifier.getDeregisterActivitySubtitle());
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#getRegisterActivitySubtitle()}
   */
  @Test
  public void testGetRegisterActivitySubtitle() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        microsoftTeamsNotifier.getRegisterActivitySubtitle());
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#setRegisterActivitySubtitle(String)}
   */
  @Test
  public void testSetRegisterActivitySubtitle() {
    // Arrange and Act
    microsoftTeamsNotifier.setRegisterActivitySubtitle("Dr");

    // Assert
    assertEquals("Dr", microsoftTeamsNotifier.getRegisterActivitySubtitle());
  }

  /**
   * Method under test: {@link MicrosoftTeamsNotifier#getStatusActivitySubtitle()}
   */
  @Test
  public void testGetStatusActivitySubtitle() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
        + ".statusInfo.status}", microsoftTeamsNotifier.getStatusActivitySubtitle());
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#setStatusActivitySubtitle(String)}
   */
  @Test
  public void testSetStatusActivitySubtitle() {
    // Arrange and Act
    microsoftTeamsNotifier.setStatusActivitySubtitle("Dr");

    // Assert
    assertEquals("Dr", microsoftTeamsNotifier.getStatusActivitySubtitle());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MicrosoftTeamsNotifier#setDeRegisteredTitle(String)}
   *   <li>{@link MicrosoftTeamsNotifier#setMessageSummary(String)}
   *   <li>{@link MicrosoftTeamsNotifier#setRegisteredTitle(String)}
   *   <li>{@link MicrosoftTeamsNotifier#setRestTemplate(RestTemplate)}
   *   <li>{@link MicrosoftTeamsNotifier#setStatusChangedTitle(String)}
   *   <li>{@link MicrosoftTeamsNotifier#setWebhookUrl(URI)}
   *   <li>{@link MicrosoftTeamsNotifier#getDeRegisteredTitle()}
   *   <li>{@link MicrosoftTeamsNotifier#getMessageSummary()}
   *   <li>{@link MicrosoftTeamsNotifier#getRegisteredTitle()}
   *   <li>{@link MicrosoftTeamsNotifier#getStatusChangedTitle()}
   *   <li>{@link MicrosoftTeamsNotifier#getWebhookUrl()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    MicrosoftTeamsNotifier microsoftTeamsNotifier = new MicrosoftTeamsNotifier(
        new EventsourcingInstanceRepository(new InMemoryEventStore()), mock(RestTemplate.class));

    // Act
    microsoftTeamsNotifier.setDeRegisteredTitle("Dr");
    microsoftTeamsNotifier.setMessageSummary("Message Summary");
    microsoftTeamsNotifier.setRegisteredTitle("Dr");
    microsoftTeamsNotifier.setRestTemplate(mock(RestTemplate.class));
    microsoftTeamsNotifier.setStatusChangedTitle("Dr");
    URI webhookUrl = PagerdutyNotifier.DEFAULT_URI;
    microsoftTeamsNotifier.setWebhookUrl(webhookUrl);
    String actualDeRegisteredTitle = microsoftTeamsNotifier.getDeRegisteredTitle();
    String actualMessageSummary = microsoftTeamsNotifier.getMessageSummary();
    String actualRegisteredTitle = microsoftTeamsNotifier.getRegisteredTitle();
    String actualStatusChangedTitle = microsoftTeamsNotifier.getStatusChangedTitle();
    URI actualWebhookUrl = microsoftTeamsNotifier.getWebhookUrl();

    // Assert that nothing has changed
    assertEquals("Dr", actualDeRegisteredTitle);
    assertEquals("Dr", actualRegisteredTitle);
    assertEquals("Dr", actualStatusChangedTitle);
    assertEquals("Message Summary", actualMessageSummary);
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualWebhookUrl.toString());
    assertSame(webhookUrl, actualWebhookUrl);
  }

  /**
   * Method under test:
   * {@link MicrosoftTeamsNotifier#MicrosoftTeamsNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  public void testNewMicrosoftTeamsNotifier() {
    // Arrange and Act
    MicrosoftTeamsNotifier actualMicrosoftTeamsNotifier = new MicrosoftTeamsNotifier(instanceRepository,
        mock(RestTemplate.class));

    // Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
        + ".statusInfo.status}", actualMicrosoftTeamsNotifier.getStatusActivitySubtitle());
    assertEquals("#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        actualMicrosoftTeamsNotifier.getDeregisterActivitySubtitle());
    assertEquals("#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        actualMicrosoftTeamsNotifier.getRegisterActivitySubtitle());
    assertEquals("De-Registered", actualMicrosoftTeamsNotifier.getDeRegisteredTitle());
    assertEquals("Registered", actualMicrosoftTeamsNotifier.getRegisteredTitle());
    assertEquals("Spring Boot Admin Notification", actualMicrosoftTeamsNotifier.getMessageSummary());
    assertEquals("Status Changed", actualMicrosoftTeamsNotifier.getStatusChangedTitle());
    assertEquals("event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        actualMicrosoftTeamsNotifier.getThemeColor());
    assertNull(actualMicrosoftTeamsNotifier.getWebhookUrl());
    assertTrue(actualMicrosoftTeamsNotifier.isEnabled());
    assertArrayEquals(new String[]{"UNKNOWN:UP"}, actualMicrosoftTeamsNotifier.getIgnoreChanges());
  }
}
