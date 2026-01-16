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
import de.codecentric.boot.admin.server.services.HashingInstanceUrlIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceFilter;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceRegistry;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_instanceRegistryTest {

	@Test
	void instanceRegistry_shouldReturnInstanceRegistryWithProvidedDependencies() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceIdGenerator idGenerator = new HashingInstanceUrlIdGenerator();
		InstanceFilter filter = (instance) -> true;

		InstanceRegistry registry = config.instanceRegistry(repository, idGenerator, filter);

		assertThat(registry).isNotNull();
		assertThat(registry).isInstanceOf(InstanceRegistry.class);
	}

	@Test
	void instanceRegistry_shouldCreateFunctionalRegistryThatCanRegisterInstances() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceIdGenerator idGenerator = new HashingInstanceUrlIdGenerator();
		InstanceFilter filter = (instance) -> true;

		InstanceRegistry registry = config.instanceRegistry(repository, idGenerator, filter);

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		Mono<InstanceId> result = registry.register(registration);

		assertThat(result).isNotNull();
		InstanceId id = result.block();
		assertThat(id).isNotNull();
	}

	@Test
	void instanceRegistry_shouldCreateRegistryThatUsesProvidedIdGenerator() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceIdGenerator customIdGenerator = (registration) -> InstanceId.of("custom-id-12345");
		InstanceFilter filter = (instance) -> true;

		InstanceRegistry registry = config.instanceRegistry(repository, customIdGenerator, filter);

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id = registry.register(registration).block();

		assertThat(id).isNotNull();
		assertThat(id.getValue()).isEqualTo("custom-id-12345");
	}

	@Test
	void instanceRegistry_shouldCreateRegistryThatUsesProvidedFilter() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceIdGenerator idGenerator = new HashingInstanceUrlIdGenerator();
		InstanceFilter rejectAllFilter = (instance) -> false;

		InstanceRegistry registry = config.instanceRegistry(repository, idGenerator, rejectAllFilter);

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId registeredId = registry.register(registration).block();

		// Register should succeed, but getInstance should return empty due to filter
		Mono<Instance> result = registry.getInstance(registeredId);
		assertThat(result.blockOptional()).isEmpty();
	}

	@Test
	void instanceRegistry_shouldCreateRegistryThatUsesProvidedRepository() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		TestInstanceRepository customRepository = new TestInstanceRepository();
		InstanceIdGenerator idGenerator = new HashingInstanceUrlIdGenerator();
		InstanceFilter filter = (instance) -> true;

		InstanceRegistry registry = config.instanceRegistry(customRepository, idGenerator, filter);

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		registry.register(registration).block();

		// Verify the repository was used by checking instances were stored
		assertThat(customRepository.findAll().collectList().block()).isNotEmpty();
	}

	@Test
	void instanceRegistry_shouldWorkWithDefaultInstanceFilter() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceIdGenerator idGenerator = new HashingInstanceUrlIdGenerator();
		// Use the default filter from the config
		InstanceFilter defaultFilter = config.instanceFilter();

		InstanceRegistry registry = config.instanceRegistry(repository, idGenerator, defaultFilter);

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id = registry.register(registration).block();

		// With default filter (returns true for all), getInstance should return the instance
		Mono<Instance> result = registry.getInstance(id);
		assertThat(result.blockOptional()).isPresent();
	}

	@Test
	void instanceRegistry_shouldWorkWithDefaultIdGenerator() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		// Use the default ID generator from the config
		InstanceIdGenerator defaultGenerator = config.instanceIdGenerator();
		InstanceFilter filter = (instance) -> true;

		InstanceRegistry registry = config.instanceRegistry(repository, defaultGenerator, filter);

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id = registry.register(registration).block();

		// Default generator should produce a valid ID
		assertThat(id).isNotNull();
		assertThat(id.getValue()).isNotEmpty();
	}

	/**
	 * Simple test implementation of InstanceRepository for testing purposes.
	 * This implementation uses an in-memory store to track instances.
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
		public Mono<Instance> compute(InstanceId id, BiFunction<InstanceId, Instance, Mono<Instance>> remappingFunction) {
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
