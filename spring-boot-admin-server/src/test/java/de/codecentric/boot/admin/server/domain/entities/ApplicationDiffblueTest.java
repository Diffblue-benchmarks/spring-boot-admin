package de.codecentric.boot.admin.server.domain.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import de.codecentric.boot.admin.server.domain.values.BuildVersion;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ApplicationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Application.Builder#build()}
   *   <li>{@link Application.Builder#buildVersion(BuildVersion)}
   *   <li>{@link Application.Builder#instances(List)}
   *   <li>{@link Application.Builder#name(String)}
   *   <li>{@link Application.Builder#status(String)}
   *   <li>{@link Application.Builder#statusTimestamp(Instant)}
   * </ul>
   */
  @Test
  public void testBuilderBuild() {
    // Arrange
    Application.Builder builderResult = Application.builder();
    BuildVersion buildVersion = BuildVersion.valueOf("foo");
    Application.Builder buildVersionResult = builderResult.buildVersion(buildVersion);
    Application.Builder statusResult = buildVersionResult.instances(new ArrayList<>()).name("Name").status("Status");

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
}
