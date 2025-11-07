package de.codecentric.boot.admin.server.utils.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import java.util.List;
import org.junit.Test;

public class AdminServerModuleDiffblueTest {
  /**
   * Method under test: {@link AdminServerModule#AdminServerModule(String[])}
   */
  @Test
  public void testNewAdminServerModule() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    AdminServerModule actualAdminServerModule = new AdminServerModule(new String[]{"Metadata Key Patterns"});

    // Assert
    Iterable<? extends Module> dependencies = actualAdminServerModule.getDependencies();
    assertTrue(dependencies instanceof List);
    Version versionResult = actualAdminServerModule.version();
    assertEquals("", versionResult.getArtifactId());
    assertEquals("", versionResult.getGroupId());
    assertEquals("//0.0.0", versionResult.toFullString());
    assertEquals("de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
        actualAdminServerModule.getModuleName());
    assertEquals("de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
        actualAdminServerModule.getTypeId());
    assertEquals(0, versionResult.getMajorVersion());
    assertEquals(0, versionResult.getMinorVersion());
    assertEquals(0, versionResult.getPatchLevel());
    assertFalse(versionResult.isSnapshot());
    assertTrue(versionResult.isUknownVersion());
    assertTrue(versionResult.isUnknownVersion());
    assertTrue(((List<? extends Module>) dependencies).isEmpty());
  }

  /**
   * Method under test: {@link AdminServerModule#AdminServerModule(String[])}
   */
  @Test
  public void testNewAdminServerModule2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    AdminServerModule actualAdminServerModule = new AdminServerModule(
        new String[]{"de.codecentric.boot.admin.server.domain.values.Registration"});

    // Assert
    Iterable<? extends Module> dependencies = actualAdminServerModule.getDependencies();
    assertTrue(dependencies instanceof List);
    Version versionResult = actualAdminServerModule.version();
    assertEquals("", versionResult.getArtifactId());
    assertEquals("", versionResult.getGroupId());
    assertEquals("//0.0.0", versionResult.toFullString());
    assertEquals("de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
        actualAdminServerModule.getModuleName());
    assertEquals("de.codecentric.boot.admin.server.utils.jackson.AdminServerModule",
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
