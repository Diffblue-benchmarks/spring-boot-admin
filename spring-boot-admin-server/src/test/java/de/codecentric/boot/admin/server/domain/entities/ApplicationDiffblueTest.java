package de.codecentric.boot.admin.server.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.entities.Application.Builder;
import de.codecentric.boot.admin.server.domain.values.BuildVersion;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {Builder.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationDiffblueTest {
  @Autowired
  private Builder builder;

  /**
   * Test Builder {@link Builder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Builder#build()}
   *   <li>{@link Builder#buildVersion(BuildVersion)}
   *   <li>{@link Builder#instances(List)}
   *   <li>{@link Builder#name(String)}
   *   <li>{@link Builder#status(String)}
   *   <li>{@link Builder#statusTimestamp(Instant)}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Builder.<init>()", "Application Builder.build()",
      "Builder Builder.buildVersion(BuildVersion)", "Builder Builder.instances(List)", "Builder Builder.name(String)",
      "Builder Builder.status(String)", "Builder Builder.statusTimestamp(Instant)", "String Builder.toString()"})
  public void testBuilderBuild() {
    // Arrange
    Builder builderResult = Application.builder();
    BuildVersion buildVersion = BuildVersion.valueOf("foo");
    Builder buildVersionResult = builderResult.buildVersion(buildVersion);
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");

    // Act
    Application actualBuildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Assert
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("Status", actualBuildResult.getStatus());
    Instant statusTimestamp = actualBuildResult.getStatusTimestamp();
    assertEquals(0, statusTimestamp.getNano());
    assertEquals(0L, statusTimestamp.getEpochSecond());
    assertTrue(actualBuildResult.getInstances().isEmpty());
    assertSame(buildVersion, actualBuildResult.getBuildVersion());
  }

  /**
   * Test {@link Application#equals(Object)}, and {@link Application#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Application#equals(Object)}
   *   <li>{@link Application#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Builder builderResult = Application.builder();
    Builder buildVersionResult = builderResult.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();
    Builder builderResult2 = Application.builder();
    Builder buildVersionResult2 = builderResult2.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult2 = buildVersionResult2.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult2 = statusResult2
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link Application#equals(Object)}, and {@link Application#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Application#equals(Object)}
   *   <li>{@link Application#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult = builder.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();
    Builder builder2 = mock(Builder.class);
    when(builder2.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult2 = builder2.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult2 = buildVersionResult2.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult2 = statusResult2
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link Application#equals(Object)}, and {@link Application#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Application#equals(Object)}
   *   <li>{@link Application#hashCode()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Builder builderResult = Application.builder();
    Builder buildVersionResult = builderResult.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult = builder.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();
    Builder builderResult = Application.builder();
    Builder buildVersionResult2 = builderResult.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult2 = buildVersionResult2.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult2 = statusResult2
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult = builder.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("42").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();
    Builder builderResult = Application.builder();
    Builder buildVersionResult2 = builderResult.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult2 = buildVersionResult2.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult2 = statusResult2
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult = builder.buildVersion(BuildVersion.valueOf("foo"));

    ArrayList<Instance> instances = new ArrayList<>();
    instances.add(Instance.create(InstanceId.of("42")));
    Builder statusResult = buildVersionResult.instances(instances).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();
    Builder builder2 = mock(Builder.class);
    when(builder2.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult2 = builder2.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult2 = buildVersionResult2.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult2 = statusResult2
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult = builder.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Name");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();
    Builder builder2 = mock(Builder.class);
    when(builder2.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult2 = builder2.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult2 = buildVersionResult2.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult2 = statusResult2
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Builder builder = mock(Builder.class);
    when(builder.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult = builder.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();
    Builder builder2 = mock(Builder.class);
    when(builder2.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult2 = builder2.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult2 = buildVersionResult2.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult2 = statusResult2
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Builder builderResult = Application.builder();
    builderResult.buildVersion(BuildVersion.valueOf("Name"));
    Builder builder = mock(Builder.class);
    when(builder.buildVersion(Mockito.<BuildVersion>any())).thenReturn(builderResult);
    Builder buildVersionResult = builder.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();
    Builder builder2 = mock(Builder.class);
    when(builder2.buildVersion(Mockito.<BuildVersion>any())).thenReturn(Application.builder());
    Builder buildVersionResult2 = builder2.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult2 = buildVersionResult2.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult2 = statusResult2
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Builder builderResult = Application.builder();
    Builder buildVersionResult = builderResult.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link Application#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Application#equals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean Application.equals(Object)", "int Application.hashCode()"})
  public void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Builder builderResult = Application.builder();
    Builder buildVersionResult = builderResult.buildVersion(BuildVersion.valueOf("foo"));
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to Application");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Application#create(String)}
   *   <li>{@link Application#toString()}
   *   <li>{@link Application#getBuildVersion()}
   *   <li>{@link Application#getInstances()}
   *   <li>{@link Application#getName()}
   *   <li>{@link Application#getStatus()}
   *   <li>{@link Application#getStatusTimestamp()}
   *   <li>{@link Application#toBuilder()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Builder Application.create(String)", "BuildVersion Application.getBuildVersion()",
      "List Application.getInstances()", "String Application.getName()", "String Application.getStatus()",
      "Instant Application.getStatusTimestamp()", "Builder Application.toBuilder()", "String Application.toString()"})
  public void testGettersAndSetters() {
    // Arrange
    Builder builderResult = Application.builder();
    BuildVersion buildVersion = BuildVersion.valueOf("foo");
    Builder buildVersionResult = builderResult.buildVersion(buildVersion);
    Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");
    Application buildResult = statusResult
        .statusTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .build();

    // Act
    buildResult.create("Name");
    String actualToStringResult = buildResult.toString();
    BuildVersion actualBuildVersion = buildResult.getBuildVersion();
    List<Instance> actualInstances = buildResult.getInstances();
    String actualName = buildResult.getName();
    String actualStatus = buildResult.getStatus();
    Instant actualStatusTimestamp = buildResult.getStatusTimestamp();
    buildResult.toBuilder();

    // Assert
    assertEquals("Application(name=Name, buildVersion=foo, status=Status, statusTimestamp=1970-01-01T00:00:00Z,"
        + " instances=[])", actualToStringResult);
    assertEquals("Name", actualName);
    assertEquals("Status", actualStatus);
    assertTrue(actualInstances.isEmpty());
    assertSame(buildVersion, actualBuildVersion);
    assertSame(actualStatusTimestamp.EPOCH, actualStatusTimestamp);
  }
}
