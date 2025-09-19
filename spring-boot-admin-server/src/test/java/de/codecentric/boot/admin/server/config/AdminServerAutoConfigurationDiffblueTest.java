package de.codecentric.boot.admin.server.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import de.codecentric.boot.admin.server.config.AdminServerProperties.MonitorProperties;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.domain.entities.EventsourcingInstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventPublisher;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import de.codecentric.boot.admin.server.services.ApiMediaTypeHandler;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import de.codecentric.boot.admin.server.services.HashingInstanceUrlIdGenerator;
import de.codecentric.boot.admin.server.services.InfoUpdater;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.services.StatusUpdater;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.FirstStep;

@ExtendWith(MockitoExtension.class)
class AdminServerAutoConfigurationDiffblueTest {
  @InjectMocks private AdminServerAutoConfiguration adminServerAutoConfiguration;

  @InjectMocks private AdminServerProperties adminServerProperties;

  /**
   * Test {@link AdminServerAutoConfiguration#instanceFilter()}.
   *
   * <p>Method under test: {@link AdminServerAutoConfiguration#instanceFilter()}
   */
  @Test
  @DisplayName("Test instanceFilter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceFilter AdminServerAutoConfiguration.instanceFilter()"})
  void testInstanceFilter() {
    // Arrange, Act and Assert
    assertTrue(adminServerAutoConfiguration.instanceFilter().filter(mock(Instance.class)));
  }

  /**
   * Test {@link AdminServerAutoConfiguration#instanceRegistry(InstanceRepository,
   * InstanceIdGenerator, InstanceFilter)}.
   *
   * <p>Method under test: {@link AdminServerAutoConfiguration#instanceRegistry(InstanceRepository,
   * InstanceIdGenerator, InstanceFilter)}
   */
  @Test
  @DisplayName("Test instanceRegistry(InstanceRepository, InstanceIdGenerator, InstanceFilter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InstanceRegistry AdminServerAutoConfiguration.instanceRegistry(InstanceRepository, InstanceIdGenerator, InstanceFilter)"
  })
  void testInstanceRegistry() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            adminServerAutoConfiguration
                .instanceRegistry(
                    new EventsourcingInstanceRepository(new InMemoryEventStore()),
                    mock(InstanceIdGenerator.class),
                    mock(InstanceFilter.class))
                .getInstances());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AdminServerAutoConfiguration#applicationRegistry(InstanceRegistry,
   * InstanceEventPublisher)}.
   *
   * <p>Method under test: {@link AdminServerAutoConfiguration#applicationRegistry(InstanceRegistry,
   * InstanceEventPublisher)}
   */
  @Test
  @DisplayName("Test applicationRegistry(InstanceRegistry, InstanceEventPublisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ApplicationRegistry AdminServerAutoConfiguration.applicationRegistry(InstanceRegistry, InstanceEventPublisher)"
  })
  void testApplicationRegistry() throws AssertionError {
    // Arrange
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            new EventsourcingInstanceRepository(new InMemoryEventStore()),
            mock(InstanceIdGenerator.class),
            mock(InstanceFilter.class));

    // Act
    ApplicationRegistry actualApplicationRegistryResult =
        adminServerAutoConfiguration.applicationRegistry(
            instanceRegistry, mock(InstanceEventPublisher.class));

    // Assert
    FirstStep<Application> createResult =
        StepVerifier.create(actualApplicationRegistryResult.getApplications());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AdminServerAutoConfiguration#instanceIdGenerator()}.
   *
   * <p>Method under test: {@link AdminServerAutoConfiguration#instanceIdGenerator()}
   */
  @Test
  @DisplayName("Test instanceIdGenerator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InstanceIdGenerator AdminServerAutoConfiguration.instanceIdGenerator()"})
  void testInstanceIdGenerator() {
    // Arrange and Act
    InstanceIdGenerator actualInstanceIdGeneratorResult =
        adminServerAutoConfiguration.instanceIdGenerator();
    InstanceId actualGenerateIdResult =
        actualInstanceIdGeneratorResult.generateId(
            Registration.builder()
                .healthUrl("https://example.org/example")
                .managementUrl("https://example.org/example")
                .name("Name")
                .serviceUrl("https://example.org/example")
                .source("Source")
                .build());

    // Assert
    assertTrue(actualInstanceIdGeneratorResult instanceof HashingInstanceUrlIdGenerator);
    assertEquals("504149e8a3fa", actualGenerateIdResult.getValue());
    assertEquals("504149e8a3fa", actualGenerateIdResult.toString());
  }

  /**
   * Test {@link AdminServerAutoConfiguration#statusUpdateTrigger(StatusUpdater, Publisher)}.
   *
   * <p>Method under test: {@link AdminServerAutoConfiguration#statusUpdateTrigger(StatusUpdater,
   * Publisher)}
   */
  @Test
  @DisplayName("Test statusUpdateTrigger(StatusUpdater, Publisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.services.StatusUpdateTrigger AdminServerAutoConfiguration.statusUpdateTrigger(StatusUpdater, Publisher)"
  })
  void testStatusUpdateTrigger() {
    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration =
        new AdminServerAutoConfiguration(new AdminServerProperties());

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());

    // Act
    adminServerAutoConfiguration.statusUpdateTrigger(statusUpdater, events);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link AdminServerAutoConfiguration#statusUpdateTrigger(StatusUpdater, Publisher)}.
   *
   * <ul>
   *   <li>Given {@link MonitorProperties} (default constructor) DefaultRetries is one.
   * </ul>
   *
   * <p>Method under test: {@link AdminServerAutoConfiguration#statusUpdateTrigger(StatusUpdater,
   * Publisher)}
   */
  @Test
  @DisplayName(
      "Test statusUpdateTrigger(StatusUpdater, Publisher); given MonitorProperties (default constructor) DefaultRetries is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.services.StatusUpdateTrigger AdminServerAutoConfiguration.statusUpdateTrigger(StatusUpdater, Publisher)"
  })
  void testStatusUpdateTrigger_givenMonitorPropertiesDefaultRetriesIsOne() {
    // Arrange
    MonitorProperties monitor = new MonitorProperties();
    monitor.setDefaultRetries(1);
    monitor.setDefaultTimeout(Duration.ofSeconds(Long.MAX_VALUE));
    monitor.setInfoInterval(Duration.ofSeconds(1L));
    monitor.setInfoLifetime(Duration.ofSeconds(1L));
    monitor.setInfoMaxBackoff(Duration.ofSeconds(1L));
    monitor.setRetries(new HashMap<>());
    monitor.setStatusInterval(Duration.ofSeconds(1L));
    monitor.setStatusLifetime(Duration.ofSeconds(1L));
    monitor.setStatusMaxBackoff(Duration.ofSeconds(1L));
    monitor.setTimeout(new HashMap<>());

    AdminServerProperties adminServerProperties = new AdminServerProperties();
    adminServerProperties.setMonitor(monitor);
    AdminServerAutoConfiguration adminServerAutoConfiguration =
        new AdminServerAutoConfiguration(adminServerProperties);

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());

    // Act
    adminServerAutoConfiguration.statusUpdateTrigger(statusUpdater, events);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link AdminServerAutoConfiguration#infoUpdateTrigger(InfoUpdater, Publisher)}.
   *
   * <p>Method under test: {@link AdminServerAutoConfiguration#infoUpdateTrigger(InfoUpdater,
   * Publisher)}
   */
  @Test
  @DisplayName("Test infoUpdateTrigger(InfoUpdater, Publisher)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.services.InfoUpdateTrigger AdminServerAutoConfiguration.infoUpdateTrigger(InfoUpdater, Publisher)"
  })
  void testInfoUpdateTrigger() {
    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration =
        new AdminServerAutoConfiguration(new AdminServerProperties());

    Builder builder = mock(Builder.class);
    when(builder.build()).thenReturn(mock(WebClient.class));
    InstanceWebClient instanceWebClient = InstanceWebClient.builder().webClient(builder).build();
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore());

    InfoUpdater infoUpdater =
        new InfoUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());

    // Act
    adminServerAutoConfiguration.infoUpdateTrigger(infoUpdater, events);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link AdminServerAutoConfiguration#eventStore()}.
   *
   * <p>Method under test: {@link AdminServerAutoConfiguration#eventStore()}
   */
  @Test
  @DisplayName("Test eventStore()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"InMemoryEventStore AdminServerAutoConfiguration.eventStore()"})
  void testEventStore() throws AssertionError {
    // Arrange, Act and Assert
    FirstStep<InstanceEvent> createResult =
        StepVerifier.create(adminServerAutoConfiguration.eventStore().findAll());
    createResult.expectComplete().verify();
  }

  /**
   * Test {@link AdminServerAutoConfiguration#instanceRepository(InstanceEventStore)}.
   *
   * <p>Method under test: {@link
   * AdminServerAutoConfiguration#instanceRepository(InstanceEventStore)}
   */
  @Test
  @DisplayName("Test instanceRepository(InstanceEventStore)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository AdminServerAutoConfiguration.instanceRepository(InstanceEventStore)"
  })
  void testInstanceRepository() throws AssertionError {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration =
        new AdminServerAutoConfiguration(new AdminServerProperties());

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            adminServerAutoConfiguration.instanceRepository(new InMemoryEventStore()).findAll());
    createResult.expectComplete().verify();
  }
}
