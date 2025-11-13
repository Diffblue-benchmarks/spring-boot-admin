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
import org.mockito.Mock;
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

  @Mock private AdminServerProperties adminServerProperties;

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
    assertTrue(
        new AdminServerAutoConfiguration(new AdminServerProperties())
            .instanceFilter()
            .filter(mock(Instance.class)));
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
    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration =
        new AdminServerAutoConfiguration(new AdminServerProperties());

    // Act and Assert
    FirstStep<Instance> createResult =
        StepVerifier.create(
            adminServerAutoConfiguration
                .instanceRegistry(
                    new EventsourcingInstanceRepository(new InMemoryEventStore(3)),
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
    "de.codecentric.boot.admin.server.services.ApplicationRegistry AdminServerAutoConfiguration.applicationRegistry(InstanceRegistry, InstanceEventPublisher)"
  })
  void testApplicationRegistry() throws AssertionError {
    // Arrange
    AdminServerAutoConfiguration adminServerAutoConfiguration =
        new AdminServerAutoConfiguration(new AdminServerProperties());
    EventsourcingInstanceRepository repository =
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));
    InstanceRegistry instanceRegistry =
        new InstanceRegistry(
            repository, mock(InstanceIdGenerator.class), mock(InstanceFilter.class));

    // Act and Assert
    FirstStep<Application> createResult =
        StepVerifier.create(
            adminServerAutoConfiguration
                .applicationRegistry(instanceRegistry, mock(InstanceEventPublisher.class))
                .getApplications());
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
        new AdminServerAutoConfiguration(new AdminServerProperties()).instanceIdGenerator();
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
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

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
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

    StatusUpdater statusUpdater =
        new StatusUpdater(repository, instanceWebClient, new ApiMediaTypeHandler());
    Flux<InstanceEvent> events = Flux.fromIterable(new ArrayList<>());

    // Act
    adminServerAutoConfiguration.statusUpdateTrigger(statusUpdater, events);

    // Assert
    verify(builder).build();
  }

  /**
   * Test {@link AdminServerAutoConfiguration#endpointDetector(InstanceRepository, Builder)}.
   *
   * <ul>
   *   <li>Then calls {@link AdminServerProperties#getProbedEndpoints()}.
   * </ul>
   *
   * <p>Method under test: {@link AdminServerAutoConfiguration#endpointDetector(InstanceRepository,
   * InstanceWebClient.Builder)}
   */
  @Test
  @DisplayName(
      "Test endpointDetector(InstanceRepository, Builder); then calls getProbedEndpoints()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "de.codecentric.boot.admin.server.services.EndpointDetector AdminServerAutoConfiguration.endpointDetector(InstanceRepository, InstanceWebClient.Builder)"
  })
  void testEndpointDetector_thenCallsGetProbedEndpoints() {
    // Arrange
    when(adminServerProperties.getProbedEndpoints())
        .thenReturn(new String[] {"https://config.us-east-2.amazonaws.com"});

    // Act
    adminServerAutoConfiguration.endpointDetector(
        new EventsourcingInstanceRepository(new InMemoryEventStore(3)),
        InstanceWebClient.builder());

    // Assert
    verify(adminServerProperties).getProbedEndpoints();
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
        new EventsourcingInstanceRepository(new InMemoryEventStore(3));

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
        StepVerifier.create(
            new AdminServerAutoConfiguration(new AdminServerProperties()).eventStore().findAll());
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
            adminServerAutoConfiguration.instanceRepository(new InMemoryEventStore(3)).findAll());
    createResult.expectComplete().verify();
  }
}
