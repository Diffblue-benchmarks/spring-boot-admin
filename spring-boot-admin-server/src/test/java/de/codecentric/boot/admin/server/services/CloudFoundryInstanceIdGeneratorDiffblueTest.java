package de.codecentric.boot.admin.server.services;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CloudFoundryInstanceIdGeneratorDiffblueTest {
  @InjectMocks
  private CloudFoundryInstanceIdGenerator cloudFoundryInstanceIdGenerator;

  @Mock
  private InstanceIdGenerator instanceIdGenerator;

  /**
   * Test {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}.
   * <ul>
   *   <li>Then return {@link InstanceId} with value is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CloudFoundryInstanceIdGenerator#generateId(Registration)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"InstanceId CloudFoundryInstanceIdGenerator.generateId(Registration)"})
  public void testGenerateId_thenReturnInstanceIdWithValueIs42() {
    // Arrange
    InstanceId ofResult = InstanceId.of("42");
    when(instanceIdGenerator.generateId(Mockito.<Registration>any())).thenReturn(ofResult);
    Registration registration = Registration.builder()
        .healthUrl("https://example.org/example")
        .managementUrl("https://example.org/example")
        .name("Name")
        .serviceUrl("https://example.org/example")
        .source("Source")
        .build();

    // Act
    InstanceId actualGenerateIdResult = cloudFoundryInstanceIdGenerator.generateId(registration);

    // Assert
    verify(instanceIdGenerator).generateId(isA(Registration.class));
    assertSame(ofResult, actualGenerateIdResult);
  }
}
