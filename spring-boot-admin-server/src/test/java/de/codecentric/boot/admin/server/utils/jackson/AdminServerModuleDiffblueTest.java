package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AdminServerModuleDiffblueTest {
  /**
   * Test {@link AdminServerModule#AdminServerModule(String[])}.
   *
   * <p>Method under test: {@link AdminServerModule#AdminServerModule(String[])}
   */
  @Test
  @DisplayName("Test new AdminServerModule(String[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AdminServerModule.<init>(String[])"})
  void testNewAdminServerModule() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    String[] metadataKeyPatterns = new String[] {"Metadata Key Patterns"};

    // Act
    AdminServerModule actualAdminServerModule = new AdminServerModule(metadataKeyPatterns);

    // Assert
    Iterable<? extends Module> dependencies = actualAdminServerModule.getDependencies();
    assertTrue(dependencies instanceof List);
    Version versionResult = actualAdminServerModule.version();
    assertEquals("", versionResult.getArtifactId());
    assertEquals("", versionResult.getGroupId());
    assertEquals("//0.0.0", versionResult.toFullString());
    assertEquals(
        "de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
        actualAdminServerModule.getModuleName());
    assertEquals(
        "de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
        actualAdminServerModule.getTypeId());
    assertEquals(0, versionResult.getMajorVersion());
    assertEquals(0, versionResult.getMinorVersion());
    assertEquals(0, versionResult.getPatchLevel());
    assertFalse(versionResult.isSnapshot());
    assertTrue(versionResult.isUknownVersion());
    assertTrue(versionResult.isUnknownVersion());
    assertTrue(((List<? extends Module>) dependencies).isEmpty());
  }
}
