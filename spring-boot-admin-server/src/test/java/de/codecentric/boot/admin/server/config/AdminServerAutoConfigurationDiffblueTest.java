package de.codecentric.boot.admin.server.config;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.eventstore.HazelcastEventStore;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventPublisher;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import org.junit.Test;
import reactor.test.StepVerifier;

public class AdminServerAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#instanceRegistry(InstanceRepository, InstanceIdGenerator, InstanceFilter)}
   */
  @Test
  public void testInstanceRegistry() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration = new AdminServerAutoConfiguration(
        new AdminServerProperties());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier.create(
        adminServerAutoConfiguration
            .instanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
                mock(InstanceIdGenerator.class), mock(InstanceFilter.class))
            .getInstances());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#applicationRegistry(InstanceRegistry, InstanceEventPublisher)}
   */
  @Test
  public void testApplicationRegistry() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration = new AdminServerAutoConfiguration(
        new AdminServerProperties());

    // Act and Assert
    StepVerifier.FirstStep<Application> createResult = StepVerifier.create(adminServerAutoConfiguration
        .applicationRegistry(new InstanceRegistry(new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class), mock(InstanceFilter.class)), null)
        .getApplications());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#statusUpdater(InstanceRepository, InstanceWebClient.Builder)}
   */
  @Test
  public void testStatusUpdater() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration = new AdminServerAutoConfiguration(
        new AdminServerProperties());
    EventsourcingInstanceRepository instanceRepository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(
        adminServerAutoConfiguration.statusUpdater(instanceRepository, InstanceWebClient.builder()).updateStatus(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#statusUpdater(InstanceRepository, InstanceWebClient.Builder)}
   */
  @Test
  public void testStatusUpdater2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration = new AdminServerAutoConfiguration(
        new AdminServerProperties());
    EventsourcingInstanceRepository instanceRepository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InstanceWebClient.Builder instanceWebClientBulder = mock(InstanceWebClient.Builder.class);
    when(instanceWebClientBulder.build()).thenReturn(null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(
        adminServerAutoConfiguration.statusUpdater(instanceRepository, instanceWebClientBulder).updateStatus(null));
    createResult.expectError().verify();
    verify(instanceWebClientBulder).build();
  }

  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#endpointDetector(InstanceRepository, InstanceWebClient.Builder)}
   */
  @Test
  public void testEndpointDetector() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration = new AdminServerAutoConfiguration(
        new AdminServerProperties());
    EventsourcingInstanceRepository instanceRepository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(adminServerAutoConfiguration.endpointDetector(instanceRepository, InstanceWebClient.builder())
            .detectEndpoints(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#endpointDetector(InstanceRepository, InstanceWebClient.Builder)}
   */
  @Test
  public void testEndpointDetector2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration = new AdminServerAutoConfiguration(
        new AdminServerProperties());
    EventsourcingInstanceRepository instanceRepository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InstanceWebClient.Builder instanceWebClientBuilder = mock(InstanceWebClient.Builder.class);
    when(instanceWebClientBuilder.build()).thenReturn(null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier
        .create(adminServerAutoConfiguration.endpointDetector(instanceRepository, instanceWebClientBuilder)
            .detectEndpoints(null));
    createResult.expectError().verify();
    verify(instanceWebClientBuilder).build();
  }

  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#infoUpdater(InstanceRepository, InstanceWebClient.Builder)}
   */
  @Test
  public void testInfoUpdater() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration = new AdminServerAutoConfiguration(
        new AdminServerProperties());
    EventsourcingInstanceRepository instanceRepository = new EventsourcingInstanceRepository(new InMemoryEventStore());

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(
        adminServerAutoConfiguration.infoUpdater(instanceRepository, InstanceWebClient.builder()).updateInfo(null));
    createResult.expectError().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#infoUpdater(InstanceRepository, InstanceWebClient.Builder)}
   */
  @Test
  public void testInfoUpdater2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration = new AdminServerAutoConfiguration(
        new AdminServerProperties());
    EventsourcingInstanceRepository instanceRepository = new EventsourcingInstanceRepository(new InMemoryEventStore());
    InstanceWebClient.Builder instanceWebClientBuilder = mock(InstanceWebClient.Builder.class);
    when(instanceWebClientBuilder.build()).thenReturn(null);

    // Act and Assert
    StepVerifier.FirstStep<Void> createResult = StepVerifier.create(
        adminServerAutoConfiguration.infoUpdater(instanceRepository, instanceWebClientBuilder).updateInfo(null));
    createResult.expectError().verify();
    verify(instanceWebClientBuilder).build();
  }

  /**
   * Method under test: {@link AdminServerAutoConfiguration#eventStore()}
   */
  @Test
  public void testEventStore() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    StepVerifier.FirstStep<InstanceEvent> createResult = StepVerifier
        .create((new AdminServerAutoConfiguration(new AdminServerProperties())).eventStore().findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test: {@link AdminServerAutoConfiguration#eventStore()}
   */
  @Test
  public void testEventStore2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerProperties.ServerProperties server = mock(AdminServerProperties.ServerProperties.class);
    doNothing().when(server).setEnabled(anyBoolean());
    server.setEnabled(true);

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setServer(server);

    // Act and Assert
    StepVerifier.FirstStep<InstanceEvent> createResult = StepVerifier
        .create((new AdminServerAutoConfiguration(adminServerProperties)).eventStore().findAll());
    createResult.expectComplete().verify();
    verify(server).setEnabled(eq(true));
  }

  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#instanceRepository(InstanceEventStore)}
   */
  @Test
  public void testInstanceRepository() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration = new AdminServerAutoConfiguration(
        new AdminServerProperties());

    // Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create(adminServerAutoConfiguration.instanceRepository(new InMemoryEventStore()).findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Method under test:
   * {@link AdminServerAutoConfiguration#instanceRepository(InstanceEventStore)}
   */
  @Test
  public void testInstanceRepository2() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    StepVerifier.FirstStep<Instance> createResult = StepVerifier
        .create((new AdminServerAutoConfiguration(new AdminServerProperties()))
            .instanceRepository(mock(HazelcastEventStore.class))
            .findAll());
    createResult.expectComplete().verify();
  }
}
