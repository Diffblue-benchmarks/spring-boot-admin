package de.codecentric.boot.admin.server.notify;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.notify.MicrosoftTeamsNotifier.Fact;
import de.codecentric.boot.admin.server.notify.MicrosoftTeamsNotifier.Message;
import de.codecentric.boot.admin.server.notify.MicrosoftTeamsNotifier.Message.MessageBuilder;
import de.codecentric.boot.admin.server.notify.MicrosoftTeamsNotifier.Section;
import de.codecentric.boot.admin.server.notify.MicrosoftTeamsNotifier.Section.SectionBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.ConstructorResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.support.DataBindingPropertyAccessor;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardOperatorOverloader;
import org.springframework.expression.spel.support.StandardTypeComparator;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {MicrosoftTeamsNotifier.class, MessageBuilder.class, SectionBuilder.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class MicrosoftTeamsNotifierDiffblueTest {
  @MockBean
  private InstanceRepository instanceRepository;

  @Autowired
  private MicrosoftTeamsNotifier microsoftTeamsNotifier;

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private MessageBuilder messageBuilder;

  @Autowired
  private SectionBuilder sectionBuilder;

  /**
   * Test Fact {@link Fact#equals(Object)}, and {@link Fact#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Fact#equals(Object)}
   *   <li>{@link Fact#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Fact.equals(Object)", "int Fact.hashCode()"})
  public void testFactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Fact fact = new Fact("Name", "42");
    Fact fact2 = new Fact("Name", "42");

    // Act and Assert
    assertEquals(fact, fact2);
    int expectedHashCodeResult = fact.hashCode();
    assertEquals(expectedHashCodeResult, fact2.hashCode());
  }

  /**
   * Test Fact {@link Fact#equals(Object)}, and {@link Fact#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Fact#equals(Object)}
   *   <li>{@link Fact#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Fact.equals(Object)", "int Fact.hashCode()"})
  public void testFactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Fact fact = new Fact("Name", null);
    Fact fact2 = new Fact("Name", null);

    // Act and Assert
    assertEquals(fact, fact2);
    int expectedHashCodeResult = fact.hashCode();
    assertEquals(expectedHashCodeResult, fact2.hashCode());
  }

  /**
   * Test Fact {@link Fact#equals(Object)}, and {@link Fact#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Fact#equals(Object)}
   *   <li>{@link Fact#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Fact.equals(Object)", "int Fact.hashCode()"})
  public void testFactEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Fact fact = new Fact("Name", "42");

    // Act and Assert
    assertEquals(fact, fact);
    int expectedHashCodeResult = fact.hashCode();
    assertEquals(expectedHashCodeResult, fact.hashCode());
  }

  /**
   * Test Fact {@link Fact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Fact#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Fact.equals(Object)", "int Fact.hashCode()"})
  public void testFactEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Fact fact = new Fact("42", "42");

    // Act and Assert
    assertNotEquals(fact, new Fact("Name", "42"));
  }

  /**
   * Test Fact {@link Fact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Fact#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Fact.equals(Object)", "int Fact.hashCode()"})
  public void testFactEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Fact fact = new Fact("Name", "Name");

    // Act and Assert
    assertNotEquals(fact, new Fact("Name", "42"));
  }

  /**
   * Test Fact {@link Fact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Fact#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Fact.equals(Object)", "int Fact.hashCode()"})
  public void testFactEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Fact fact = new Fact("Name", null);

    // Act and Assert
    assertNotEquals(fact, new Fact("Name", "42"));
  }

  /**
   * Test Fact {@link Fact#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Fact#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Fact.equals(Object)", "int Fact.hashCode()"})
  public void testFactEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Fact("Name", "42"), null);
  }

  /**
   * Test Fact {@link Fact#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Fact#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Fact.equals(Object)", "int Fact.hashCode()"})
  public void testFactEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Fact("Name", "42"), "Different type to Fact");
  }

  /**
   * Test Fact getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Fact#Fact(String, String)}
   *   <li>{@link Fact#toString()}
   *   <li>{@link Fact#getName()}
   *   <li>{@link Fact#getValue()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Fact.<init>(String, String)", "String Fact.getName()", "String Fact.getValue()",
      "String Fact.toString()"})
  public void testFactGettersAndSetters() {
    // Arrange and Act
    Fact actualFact = new Fact("Name", "42");
    String actualToStringResult = actualFact.toString();
    String actualName = actualFact.getName();

    // Assert
    assertEquals("42", actualFact.getValue());
    assertEquals("MicrosoftTeamsNotifier.Fact(name=Name, value=42)", actualToStringResult);
    assertEquals("Name", actualName);
  }

  /**
   * Test Message {@link Message#equals(Object)}, and {@link Message#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Message#equals(Object)}
   *   <li>{@link Message#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Message buildResult = Message.builder().summary("Summary").themeColor("Theme Color").title("Dr").build();
    Message buildResult2 = Message.builder().summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test Message {@link Message#equals(Object)}, and {@link Message#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Message#equals(Object)}
   *   <li>{@link Message#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    when(messageBuilder.summary(Mockito.<String>any())).thenReturn(Message.builder());
    Message buildResult = messageBuilder.summary("Summary").themeColor("Theme Color").title("Dr").build();
    MessageBuilder messageBuilder2 = mock(MessageBuilder.class);
    when(messageBuilder2.summary(Mockito.<String>any())).thenReturn(Message.builder());
    Message buildResult2 = messageBuilder2.summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test Message {@link Message#equals(Object)}, and {@link Message#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Message#equals(Object)}
   *   <li>{@link Message#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    when(messageBuilder.themeColor(Mockito.<String>any())).thenReturn(Message.builder());
    MessageBuilder messageBuilder2 = mock(MessageBuilder.class);
    when(messageBuilder2.summary(Mockito.<String>any())).thenReturn(messageBuilder);
    Message buildResult = messageBuilder2.summary("Summary").themeColor("Theme Color").title("Dr").build();
    MessageBuilder messageBuilder3 = mock(MessageBuilder.class);
    when(messageBuilder3.themeColor(Mockito.<String>any())).thenReturn(Message.builder());
    MessageBuilder messageBuilder4 = mock(MessageBuilder.class);
    when(messageBuilder4.summary(Mockito.<String>any())).thenReturn(messageBuilder3);
    Message buildResult2 = messageBuilder4.summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test Message {@link Message#equals(Object)}, and {@link Message#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Message#equals(Object)}
   *   <li>{@link Message#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Message buildResult = Message.builder().summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test Message {@link Message#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    when(messageBuilder.summary(Mockito.<String>any())).thenReturn(Message.builder());
    Message buildResult = messageBuilder.summary("Summary").themeColor("Theme Color").title("Dr").build();
    Message buildResult2 = Message.builder().summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test Message {@link Message#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    when(messageBuilder.themeColor(Mockito.<String>any())).thenReturn(Message.builder());
    MessageBuilder messageBuilder2 = mock(MessageBuilder.class);
    when(messageBuilder2.summary(Mockito.<String>any())).thenReturn(messageBuilder);
    Message buildResult = messageBuilder2.summary("Summary").themeColor("Theme Color").title("Dr").build();
    MessageBuilder messageBuilder3 = mock(MessageBuilder.class);
    when(messageBuilder3.summary(Mockito.<String>any())).thenReturn(Message.builder());
    Message buildResult2 = messageBuilder3.summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test Message {@link Message#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    when(messageBuilder.title(Mockito.<String>any())).thenReturn(Message.builder());
    MessageBuilder messageBuilder2 = mock(MessageBuilder.class);
    when(messageBuilder2.themeColor(Mockito.<String>any())).thenReturn(messageBuilder);
    MessageBuilder messageBuilder3 = mock(MessageBuilder.class);
    when(messageBuilder3.summary(Mockito.<String>any())).thenReturn(messageBuilder2);
    Message buildResult = messageBuilder3.summary("Summary").themeColor("Theme Color").title("Dr").build();
    MessageBuilder messageBuilder4 = mock(MessageBuilder.class);
    when(messageBuilder4.themeColor(Mockito.<String>any())).thenReturn(Message.builder());
    MessageBuilder messageBuilder5 = mock(MessageBuilder.class);
    when(messageBuilder5.summary(Mockito.<String>any())).thenReturn(messageBuilder4);
    Message buildResult2 = messageBuilder5.summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test Message {@link Message#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    Message buildResult = Message.builder().summary("Summary").themeColor("Theme Color").title("Dr").build();
    when(messageBuilder.build()).thenReturn(buildResult);
    MessageBuilder messageBuilder2 = mock(MessageBuilder.class);
    when(messageBuilder2.title(Mockito.<String>any())).thenReturn(messageBuilder);
    MessageBuilder messageBuilder3 = mock(MessageBuilder.class);
    when(messageBuilder3.themeColor(Mockito.<String>any())).thenReturn(messageBuilder2);
    MessageBuilder messageBuilder4 = mock(MessageBuilder.class);
    when(messageBuilder4.summary(Mockito.<String>any())).thenReturn(messageBuilder3);
    Message buildResult2 = messageBuilder4.summary("Summary").themeColor("Theme Color").title("Dr").build();
    MessageBuilder messageBuilder5 = mock(MessageBuilder.class);
    when(messageBuilder5.themeColor(Mockito.<String>any())).thenReturn(Message.builder());
    MessageBuilder messageBuilder6 = mock(MessageBuilder.class);
    when(messageBuilder6.summary(Mockito.<String>any())).thenReturn(messageBuilder5);
    Message buildResult3 = messageBuilder6.summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Test Message {@link Message#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    when(messageBuilder.summary(Mockito.<String>any())).thenReturn(Message.builder());
    Message buildResult = messageBuilder.summary("Summary").themeColor("Theme Color").title("Dr").build();
    MessageBuilder messageBuilder2 = mock(MessageBuilder.class);
    when(messageBuilder2.build()).thenReturn(buildResult);
    MessageBuilder messageBuilder3 = mock(MessageBuilder.class);
    when(messageBuilder3.title(Mockito.<String>any())).thenReturn(messageBuilder2);
    MessageBuilder messageBuilder4 = mock(MessageBuilder.class);
    when(messageBuilder4.themeColor(Mockito.<String>any())).thenReturn(messageBuilder3);
    MessageBuilder messageBuilder5 = mock(MessageBuilder.class);
    when(messageBuilder5.summary(Mockito.<String>any())).thenReturn(messageBuilder4);
    Message buildResult2 = messageBuilder5.summary("Summary").themeColor("Theme Color").title("Dr").build();
    MessageBuilder messageBuilder6 = mock(MessageBuilder.class);
    when(messageBuilder6.themeColor(Mockito.<String>any())).thenReturn(Message.builder());
    MessageBuilder messageBuilder7 = mock(MessageBuilder.class);
    when(messageBuilder7.summary(Mockito.<String>any())).thenReturn(messageBuilder6);
    Message buildResult3 = messageBuilder7.summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Test Message {@link Message#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Message buildResult = Message.builder().summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test Message {@link Message#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Message.equals(Object)", "int Message.hashCode()"})
  public void testMessageEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Message buildResult = Message.builder().summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to Message");
  }

  /**
   * Test Message getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Message#Message(String, String, String, List)}
   *   <li>{@link Message#toString()}
   *   <li>{@link Message#getSections()}
   *   <li>{@link Message#getSummary()}
   *   <li>{@link Message#getThemeColor()}
   *   <li>{@link Message#getTitle()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Message.<init>(String, String, String, List)", "List Message.getSections()",
      "String Message.getSummary()", "String Message.getThemeColor()", "String Message.getTitle()",
      "String Message.toString()"})
  public void testMessageGettersAndSetters() {
    // Arrange
    ArrayList<Section> sections = new ArrayList<>();

    // Act
    Message actualMessage = new Message("Summary", "Theme Color", "Dr", sections);
    String actualToStringResult = actualMessage.toString();
    List<Section> actualSections = actualMessage.getSections();
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
   * Test Message_MessageBuilder {@link Message.MessageBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Message.MessageBuilder#build()}
   *   <li>{@link Message.MessageBuilder#summary(String)}
   *   <li>{@link Message.MessageBuilder#themeColor(String)}
   *   <li>{@link Message.MessageBuilder#title(String)}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Message.MessageBuilder.<init>()", "Message Message.MessageBuilder.build()",
      "Message.MessageBuilder Message.MessageBuilder.summary(String)",
      "Message.MessageBuilder Message.MessageBuilder.themeColor(String)",
      "Message.MessageBuilder Message.MessageBuilder.title(String)", "String Message.MessageBuilder.toString()"})
  public void testMessage_MessageBuilderBuild() {
    // Arrange and Act
    Message actualBuildResult = Message.builder().summary("Summary").themeColor("Theme Color").title("Dr").build();

    // Assert
    assertEquals("Dr", actualBuildResult.getTitle());
    assertEquals("Summary", actualBuildResult.getSummary());
    assertEquals("Theme Color", actualBuildResult.getThemeColor());
    assertTrue(actualBuildResult.getSections().isEmpty());
  }

  /**
   * Test Message_MessageBuilder {@link Message.MessageBuilder#sections(List)}.
   * <ul>
   *   <li>Then builder build Sections is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message.MessageBuilder#sections(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Message.MessageBuilder Message.MessageBuilder.sections(List)"})
  public void testMessage_MessageBuilderSections_thenBuilderBuildSectionsIsArrayList() {
    // Arrange
    MessageBuilder builderResult = Message.builder();
    ArrayList<Section> sections = new ArrayList<>();

    // Act
    MessageBuilder actualSectionsResult = builderResult.sections(sections);

    // Assert
    assertSame(sections, builderResult.build().getSections());
    assertSame(builderResult, actualSectionsResult);
  }

  /**
   * Test Message_MessageBuilder {@link Message.MessageBuilder#sections(List)}.
   * <ul>
   *   <li>Then return build Sections is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message.MessageBuilder#sections(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Message.MessageBuilder Message.MessageBuilder.sections(List)"})
  public void testMessage_MessageBuilderSections_thenReturnBuildSectionsIsArrayList() {
    // Arrange
    MessageBuilder builderResult = Message.builder();

    ArrayList<Section> sections = new ArrayList<>();
    Section buildResult = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();
    sections.add(buildResult);

    // Act and Assert
    assertSame(sections, builderResult.sections(sections).build().getSections());
  }

  /**
   * Test Message_MessageBuilder {@link Message.MessageBuilder#sections(List)}.
   * <ul>
   *   <li>Then return build Sections size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message.MessageBuilder#sections(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Message.MessageBuilder Message.MessageBuilder.sections(List)"})
  public void testMessage_MessageBuilderSections_thenReturnBuildSectionsSizeIsTwo() {
    // Arrange
    MessageBuilder builderResult = Message.builder();

    ArrayList<Section> sections = new ArrayList<>();
    Section buildResult = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();
    sections.add(buildResult);
    Section buildResult2 = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();
    sections.add(buildResult2);

    // Act and Assert
    List<Section> sections2 = builderResult.sections(sections).build().getSections();
    assertEquals(2, sections2.size());
    assertEquals(sections2.get(0), sections2.get(1));
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#MicrosoftTeamsNotifier(InstanceRepository, RestTemplate)}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#MicrosoftTeamsNotifier(InstanceRepository, RestTemplate)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.<init>(InstanceRepository, RestTemplate)"})
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

  /**
   * Test {@link MicrosoftTeamsNotifier#evaluateExpression(EvaluationContext, Expression)}.
   * <ul>
   *   <li>When {@link StandardEvaluationContext#StandardEvaluationContext()}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#evaluateExpression(EvaluationContext, Expression)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.evaluateExpression(EvaluationContext, Expression)"})
  public void testEvaluateExpression_whenStandardEvaluationContext_thenReturn42() {
    // Arrange
    StandardEvaluationContext context = new StandardEvaluationContext();

    // Act and Assert
    assertEquals("42", microsoftTeamsNotifier.evaluateExpression(context, new LiteralExpression("42")));
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#createEvaluationContext(InstanceEvent, Instance)}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#createEvaluationContext(InstanceEvent, Instance)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"EvaluationContext MicrosoftTeamsNotifier.createEvaluationContext(InstanceEvent, Instance)"})
  public void testCreateEvaluationContext() {
    // Arrange and Act
    EvaluationContext actualCreateEvaluationContextResult = microsoftTeamsNotifier
        .createEvaluationContext(new InstanceDeregisteredEvent(InstanceId.of("42"), 1L), null);

    // Assert
    Object value = actualCreateEvaluationContextResult.getRootObject().getValue();
    assertTrue(value instanceof Map);
    List<PropertyAccessor> propertyAccessors = actualCreateEvaluationContextResult.getPropertyAccessors();
    assertEquals(2, propertyAccessors.size());
    assertTrue(propertyAccessors.get(1) instanceof MapAccessor);
    assertTrue(propertyAccessors.get(0) instanceof DataBindingPropertyAccessor);
    assertTrue(actualCreateEvaluationContextResult instanceof SimpleEvaluationContext);
    assertTrue(actualCreateEvaluationContextResult.getOperatorOverloader() instanceof StandardOperatorOverloader);
    assertTrue(actualCreateEvaluationContextResult.getTypeComparator() instanceof StandardTypeComparator);
    assertTrue(actualCreateEvaluationContextResult.getTypeConverter() instanceof StandardTypeConverter);
    assertNull(actualCreateEvaluationContextResult.getBeanResolver());
    assertEquals(3, ((Map<String, Object>) value).size());
    List<ConstructorResolver> constructorResolvers = actualCreateEvaluationContextResult.getConstructorResolvers();
    assertTrue(constructorResolvers.isEmpty());
    assertTrue(((Map<String, Object>) value).containsKey("event"));
    assertTrue(((Map<String, Object>) value).containsKey("instance"));
    assertTrue(((Map<String, Object>) value).containsKey("lastStatus"));
    assertTrue(actualCreateEvaluationContextResult.isAssignmentEnabled());
    assertSame(constructorResolvers, actualCreateEvaluationContextResult.getIndexAccessors());
    assertSame(constructorResolvers, actualCreateEvaluationContextResult.getMethodResolvers());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getThemeColor()}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#getThemeColor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.getThemeColor()"})
  public void testGetThemeColor() {
    // Arrange, Act and Assert
    assertEquals("event.type == 'STATUS_CHANGED' ? (event.statusInfo.status=='UP' ? '6db33f' : 'b32d36') : '439fe0'",
        microsoftTeamsNotifier.getThemeColor());
  }

  /**
   * Test Section {@link Section#equals(Object)}, and {@link Section#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Section#equals(Object)}
   *   <li>{@link Section#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Section.equals(Object)", "int Section.hashCode()"})
  public void testSectionEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Section buildResult = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();
    Section buildResult2 = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test Section {@link Section#equals(Object)}, and {@link Section#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Section#equals(Object)}
   *   <li>{@link Section#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Section.equals(Object)", "int Section.hashCode()"})
  public void testSectionEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Section buildResult = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test Section {@link Section#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Section#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Section.equals(Object)", "int Section.hashCode()"})
  public void testSectionEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SectionBuilder sectionBuilder = mock(SectionBuilder.class);
    when(sectionBuilder.activitySubtitle(Mockito.<String>any())).thenReturn(Section.builder());
    Section buildResult = sectionBuilder.activitySubtitle("Dr").activityTitle("Dr").build();
    Section buildResult2 = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test Section {@link Section#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Section#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Section.equals(Object)", "int Section.hashCode()"})
  public void testSectionEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SectionBuilder sectionBuilder = mock(SectionBuilder.class);
    when(sectionBuilder.activityTitle(Mockito.<String>any())).thenReturn(Section.builder());
    SectionBuilder sectionBuilder2 = mock(SectionBuilder.class);
    when(sectionBuilder2.activitySubtitle(Mockito.<String>any())).thenReturn(sectionBuilder);
    Section buildResult = sectionBuilder2.activitySubtitle("Dr").activityTitle("Dr").build();
    Section buildResult2 = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test Section {@link Section#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Section#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Section.equals(Object)", "int Section.hashCode()"})
  public void testSectionEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Section buildResult = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test Section {@link Section#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Section#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Section.equals(Object)", "int Section.hashCode()"})
  public void testSectionEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Section buildResult = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to Section");
  }

  /**
   * Test Section getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Section#Section(String, String, List)}
   *   <li>{@link Section#toString()}
   *   <li>{@link Section#getActivitySubtitle()}
   *   <li>{@link Section#getActivityTitle()}
   *   <li>{@link Section#getFacts()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Section.<init>(String, String, List)", "String Section.getActivitySubtitle()",
      "String Section.getActivityTitle()", "List Section.getFacts()", "String Section.toString()"})
  public void testSectionGettersAndSetters() {
    // Arrange
    ArrayList<Fact> facts = new ArrayList<>();

    // Act
    Section actualSection = new Section("Dr", "Dr", facts);
    String actualToStringResult = actualSection.toString();
    String actualActivitySubtitle = actualSection.getActivitySubtitle();
    String actualActivityTitle = actualSection.getActivityTitle();
    List<Fact> actualFacts = actualSection.getFacts();

    // Assert
    assertEquals("Dr", actualActivitySubtitle);
    assertEquals("Dr", actualActivityTitle);
    assertEquals("MicrosoftTeamsNotifier.Section(activityTitle=Dr, activitySubtitle=Dr, facts=[])",
        actualToStringResult);
    assertTrue(actualFacts.isEmpty());
    assertSame(facts, actualFacts);
  }

  /**
   * Test Section_SectionBuilder {@link SectionBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SectionBuilder#build()}
   *   <li>{@link SectionBuilder#activitySubtitle(String)}
   *   <li>{@link SectionBuilder#activityTitle(String)}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SectionBuilder.<init>()", "SectionBuilder SectionBuilder.activitySubtitle(String)",
      "SectionBuilder SectionBuilder.activityTitle(String)", "Section SectionBuilder.build()",
      "String SectionBuilder.toString()"})
  public void testSection_SectionBuilderBuild() {
    // Arrange and Act
    Section actualBuildResult = Section.builder().activitySubtitle("Dr").activityTitle("Dr").build();

    // Assert
    assertEquals("Dr", actualBuildResult.getActivitySubtitle());
    assertEquals("Dr", actualBuildResult.getActivityTitle());
    assertTrue(actualBuildResult.getFacts().isEmpty());
  }

  /**
   * Test Section_SectionBuilder {@link SectionBuilder#facts(List)}.
   * <ul>
   *   <li>Then return build Facts is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SectionBuilder#facts(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SectionBuilder SectionBuilder.facts(List)"})
  public void testSection_SectionBuilderFacts_thenReturnBuildFactsIsArrayList() {
    // Arrange
    SectionBuilder builderResult = Section.builder();

    ArrayList<Fact> facts = new ArrayList<>();
    facts.add(new Fact("Name", "42"));

    // Act and Assert
    assertSame(facts, builderResult.facts(facts).build().getFacts());
  }

  /**
   * Test Section_SectionBuilder {@link SectionBuilder#facts(List)}.
   * <ul>
   *   <li>Then return build Facts size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link SectionBuilder#facts(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SectionBuilder SectionBuilder.facts(List)"})
  public void testSection_SectionBuilderFacts_thenReturnBuildFactsSizeIsTwo() {
    // Arrange
    SectionBuilder builderResult = Section.builder();

    ArrayList<Fact> facts = new ArrayList<>();
    facts.add(new Fact("Name", "42"));
    Fact fact = new Fact("Name", "42");

    facts.add(fact);

    // Act and Assert
    List<Fact> facts2 = builderResult.facts(facts).build().getFacts();
    assertEquals(2, facts2.size());
    assertSame(fact, facts2.get(1));
  }

  /**
   * Test Section_SectionBuilder {@link SectionBuilder#facts(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then builder build Facts is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SectionBuilder#facts(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SectionBuilder SectionBuilder.facts(List)"})
  public void testSection_SectionBuilderFacts_whenArrayList_thenBuilderBuildFactsIsArrayList() {
    // Arrange
    SectionBuilder builderResult = Section.builder();
    ArrayList<Fact> facts = new ArrayList<>();

    // Act
    SectionBuilder actualFactsResult = builderResult.facts(facts);

    // Assert
    assertSame(facts, builderResult.build().getFacts());
    assertSame(builderResult, actualFactsResult);
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#setThemeColor(String)}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#setThemeColor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.setThemeColor(String)"})
  public void testSetThemeColor() {
    // Arrange and Act
    microsoftTeamsNotifier.setThemeColor("Theme Color");

    // Assert
    assertEquals("Theme Color", microsoftTeamsNotifier.getThemeColor());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getDeregisterActivitySubtitle()}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#getDeregisterActivitySubtitle()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.getDeregisterActivitySubtitle()"})
  public void testGetDeregisterActivitySubtitle() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} has de-registered from Spring Boot Admin",
        microsoftTeamsNotifier.getDeregisterActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#setDeregisterActivitySubtitle(String)}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#setDeregisterActivitySubtitle(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.setDeregisterActivitySubtitle(String)"})
  public void testSetDeregisterActivitySubtitle() {
    // Arrange and Act
    microsoftTeamsNotifier.setDeregisterActivitySubtitle("Dr");

    // Assert
    assertEquals("Dr", microsoftTeamsNotifier.getDeregisterActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getRegisterActivitySubtitle()}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#getRegisterActivitySubtitle()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.getRegisterActivitySubtitle()"})
  public void testGetRegisterActivitySubtitle() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} has registered with Spring Boot Admin",
        microsoftTeamsNotifier.getRegisterActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#setRegisterActivitySubtitle(String)}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#setRegisterActivitySubtitle(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.setRegisterActivitySubtitle(String)"})
  public void testSetRegisterActivitySubtitle() {
    // Arrange and Act
    microsoftTeamsNotifier.setRegisterActivitySubtitle("Dr");

    // Assert
    assertEquals("Dr", microsoftTeamsNotifier.getRegisterActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#getStatusActivitySubtitle()}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#getStatusActivitySubtitle()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.getStatusActivitySubtitle()"})
  public void testGetStatusActivitySubtitle() {
    // Arrange, Act and Assert
    assertEquals("#{instance.registration.name} with id #{instance.id} changed status from #{lastStatus} to #{event"
        + ".statusInfo.status}", microsoftTeamsNotifier.getStatusActivitySubtitle());
  }

  /**
   * Test {@link MicrosoftTeamsNotifier#setStatusActivitySubtitle(String)}.
   * <p>
   * Method under test: {@link MicrosoftTeamsNotifier#setStatusActivitySubtitle(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MicrosoftTeamsNotifier.setStatusActivitySubtitle(String)"})
  public void testSetStatusActivitySubtitle() {
    // Arrange and Act
    microsoftTeamsNotifier.setStatusActivitySubtitle("Dr");

    // Assert
    assertEquals("Dr", microsoftTeamsNotifier.getStatusActivitySubtitle());
  }

  /**
   * Test getters and setters.
   * <p>
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String MicrosoftTeamsNotifier.getDeRegisteredTitle()",
      "String MicrosoftTeamsNotifier.getMessageSummary()", "String MicrosoftTeamsNotifier.getRegisteredTitle()",
      "String MicrosoftTeamsNotifier.getStatusChangedTitle()", "URI MicrosoftTeamsNotifier.getWebhookUrl()",
      "void MicrosoftTeamsNotifier.setDeRegisteredTitle(String)",
      "void MicrosoftTeamsNotifier.setMessageSummary(String)", "void MicrosoftTeamsNotifier.setRegisteredTitle(String)",
      "void MicrosoftTeamsNotifier.setRestTemplate(RestTemplate)",
      "void MicrosoftTeamsNotifier.setStatusChangedTitle(String)", "void MicrosoftTeamsNotifier.setWebhookUrl(URI)"})
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

    // Assert
    assertEquals("Dr", actualDeRegisteredTitle);
    assertEquals("Dr", actualRegisteredTitle);
    assertEquals("Dr", actualStatusChangedTitle);
    assertEquals("Message Summary", actualMessageSummary);
    assertEquals("https://events.pagerduty.com/generic/2010-04-15/create_event.json", actualWebhookUrl.toString());
    assertSame(webhookUrl, actualWebhookUrl);
  }
}
