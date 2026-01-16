/*
 * Copyright 2014-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.codecentric.boot.admin.server.config;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventPublisher;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import de.codecentric.boot.admin.server.services.HashingInstanceUrlIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_applicationRegistryTest {

	@Test
	void applicationRegistry_shouldReturnApplicationRegistryWithProvidedDependencies() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRegistry instanceRegistry = createTestInstanceRegistry();
		InstanceEventPublisher eventPublisher = new InMemoryEventStore();

		ApplicationRegistry applicationRegistry = config.applicationRegistry(instanceRegistry, eventPublisher);

		assertThat(applicationRegistry).isNotNull();
		assertThat(applicationRegistry).isInstanceOf(ApplicationRegistry.class);
	}

	@Test
	void applicationRegistry_shouldCreateFunctionalRegistryThatCanGetApplications() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceRegistry instanceRegistry = new InstanceRegistry(repository, new HashingInstanceUrlIdGenerator(),
				(instance) -> true);
		InstanceEventPublisher eventPublisher = new InMemoryEventStore();

		ApplicationRegistry applicationRegistry = config.applicationRegistry(instanceRegistry, eventPublisher);

		// Register an instance
		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		instanceRegistry.register(registration).block();

		// Verify we can get applications
		Flux<de.codecentric.boot.admin.server.domain.entities.Application> applications = applicationRegistry
			.getApplications();
		assertThat(applications).isNotNull();
		assertThat(applications.collectList().block()).isNotEmpty();
	}

	@Test
	void applicationRegistry_shouldUseProvidedInstanceRegistry() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceRegistry instanceRegistry = new InstanceRegistry(repository, new HashingInstanceUrlIdGenerator(),
				(instance) -> true);
		InstanceEventPublisher eventPublisher = new InMemoryEventStore();

		ApplicationRegistry applicationRegistry = config.applicationRegistry(instanceRegistry, eventPublisher);

		// Register an instance through the instance registry
		Registration registration = Registration.create("my-app", "http://localhost:9090/health").build();
		instanceRegistry.register(registration).block();

		// Verify the application registry sees it
		de.codecentric.boot.admin.server.domain.entities.Application app = applicationRegistry.getApplication("my-app")
			.block();
		assertThat(app).isNotNull();
		assertThat(app.getName()).isEqualTo("my-app");
	}

	@Test
	void applicationRegistry_shouldWorkWithMultipleInstances() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceRegistry instanceRegistry = new InstanceRegistry(repository, new HashingInstanceUrlIdGenerator(),
				(instance) -> true);
		InstanceEventPublisher eventPublisher = new InMemoryEventStore();

		ApplicationRegistry applicationRegistry = config.applicationRegistry(instanceRegistry, eventPublisher);

		// Register multiple instances of the same app
		Registration registration1 = Registration.create("multi-app", "http://localhost:8081/health").build();
		Registration registration2 = Registration.create("multi-app", "http://localhost:8082/health").build();
		instanceRegistry.register(registration1).block();
		instanceRegistry.register(registration2).block();

		// Verify the application registry groups them
		de.codecentric.boot.admin.server.domain.entities.Application app = applicationRegistry
			.getApplication("multi-app")
			.block();
		assertThat(app).isNotNull();
		assertThat(app.getName()).isEqualTo("multi-app");
		assertThat(app.getInstances()).hasSize(2);
	}

	@Test
	void applicationRegistry_shouldWorkWithDifferentApplications() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceRegistry instanceRegistry = new InstanceRegistry(repository, new HashingInstanceUrlIdGenerator(),
				(instance) -> true);
		InstanceEventPublisher eventPublisher = new InMemoryEventStore();

		ApplicationRegistry applicationRegistry = config.applicationRegistry(instanceRegistry, eventPublisher);

		// Register instances of different apps
		Registration registration1 = Registration.create("app-one", "http://localhost:8081/health").build();
		Registration registration2 = Registration.create("app-two", "http://localhost:8082/health").build();
		instanceRegistry.register(registration1).block();
		instanceRegistry.register(registration2).block();

		// Verify both applications are returned
		Flux<de.codecentric.boot.admin.server.domain.entities.Application> applications = applicationRegistry
			.getApplications();
		assertThat(applications.collectList().block()).hasSize(2);
	}

	@Test
	void applicationRegistry_shouldWorkWithDefaultInstanceRegistryConfiguration() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		// Use the default beans from the config
		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceIdGenerator idGenerator = config.instanceIdGenerator();
		InstanceFilter filter = config.instanceFilter();
		InstanceRegistry instanceRegistry = config.instanceRegistry(repository, idGenerator, filter);

		InstanceEventPublisher eventPublisher = new InMemoryEventStore();

		ApplicationRegistry applicationRegistry = config.applicationRegistry(instanceRegistry, eventPublisher);

		// Register an instance
		Registration registration = Registration.create("default-app", "http://localhost:8080/health").build();
		instanceRegistry.register(registration).block();

		// Verify it works end-to-end
		de.codecentric.boot.admin.server.domain.entities.Application app = applicationRegistry
			.getApplication("default-app")
			.block();
		assertThat(app).isNotNull();
		assertThat(app.getName()).isEqualTo("default-app");
	}

	@Test
	void applicationRegistry_shouldReturnEmptyWhenNoApplicationsRegistered() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRegistry instanceRegistry = createTestInstanceRegistry();
		InstanceEventPublisher eventPublisher = new InMemoryEventStore();

		ApplicationRegistry applicationRegistry = config.applicationRegistry(instanceRegistry, eventPublisher);

		// Verify no applications when none are registered
		Flux<de.codecentric.boot.admin.server.domain.entities.Application> applications = applicationRegistry
			.getApplications();
		assertThat(applications.collectList().block()).isEmpty();
	}

	private InstanceRegistry createTestInstanceRegistry() {
		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceIdGenerator idGenerator = new HashingInstanceUrlIdGenerator();
		InstanceFilter filter = (instance) -> true;
		return new InstanceRegistry(repository, idGenerator, filter);
	}

	/**
	 * Simple test implementation of InstanceRepository for testing purposes. This
	 * implementation uses an in-memory store to track instances.
	 */
	private static class TestInstanceRepository implements InstanceRepository {

		private final java.util.concurrent.ConcurrentHashMap<InstanceId, Instance> instances = new java.util.concurrent.ConcurrentHashMap<>();

		@Override
		public Mono<Instance> save(Instance instance) {
			instances.put(instance.getId(), instance);
			return Mono.just(instance);
		}

		@Override
		public Flux<Instance> findAll() {
			return Flux.fromIterable(instances.values());
		}

		@Override
		public Mono<Instance> find(InstanceId id) {
			Instance instance = instances.get(id);
			return instance != null ? Mono.just(instance) : Mono.empty();
		}

		@Override
		public Flux<Instance> findByName(String name) {
			return Flux.fromIterable(instances.values())
				.filter(instance -> instance.getRegistration() != null
						&& instance.getRegistration().getName().equals(name));
		}

		@Override
		public Mono<Instance> compute(InstanceId id,
				BiFunction<InstanceId, Instance, Mono<Instance>> remappingFunction) {
			Instance currentInstance = instances.get(id);
			Mono<Instance> result = remappingFunction.apply(id, currentInstance);
			return result.flatMap(this::save);
		}

		@Override
		public Mono<Instance> computeIfPresent(InstanceId id,
				BiFunction<InstanceId, Instance, Mono<Instance>> remappingFunction) {
			Instance currentInstance = instances.get(id);
			if (currentInstance != null) {
				Mono<Instance> result = remappingFunction.apply(id, currentInstance);
				return result.flatMap(this::save);
			}
			return Mono.empty();
		}

	}

}
