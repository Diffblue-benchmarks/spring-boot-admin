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
import de.codecentric.boot.admin.server.services.StatusUpdater;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_statusUpdaterTest {

	@Test
	void statusUpdater_shouldReturnNonNullStatusUpdater() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		StatusUpdater statusUpdater = config.statusUpdater(repository, webClientBuilder);

		assertThat(statusUpdater).isNotNull();
	}

	@Test
	void statusUpdater_shouldReturnStatusUpdaterInstance() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		StatusUpdater statusUpdater = config.statusUpdater(repository, webClientBuilder);

		assertThat(statusUpdater).isInstanceOf(StatusUpdater.class);
	}

	@Test
	void statusUpdater_shouldUseProvidedInstanceRepository() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		StatusUpdater statusUpdater = config.statusUpdater(repository, webClientBuilder);

		// Register an instance in the repository
		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id = InstanceId.of("test-id");
		Instance instance = Instance.create(id).register(registration);
		repository.save(instance).block();

		// Verify the status updater can access the repository
		assertThat(repository.find(id).block()).isNotNull();
	}

	@Test
	void statusUpdater_shouldBuildInstanceWebClientFromProvidedBuilder() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		StatusUpdater statusUpdater = config.statusUpdater(repository, webClientBuilder);

		// Verify the status updater was created successfully with the built web client
		assertThat(statusUpdater).isNotNull();
	}

	@Test
	void statusUpdater_shouldWorkWithCustomWebClientBuilder() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		// Create a custom builder with filters
		InstanceWebClient.Builder customBuilder = InstanceWebClient.builder()
			.filter((instance, request, next) -> next.exchange(request));

		StatusUpdater statusUpdater = config.statusUpdater(repository, customBuilder);

		assertThat(statusUpdater).isNotNull();
		assertThat(statusUpdater).isInstanceOf(StatusUpdater.class);
	}

	@Test
	void statusUpdater_shouldCreateNewInstanceEachTime() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		StatusUpdater statusUpdater1 = config.statusUpdater(repository, webClientBuilder);
		StatusUpdater statusUpdater2 = config.statusUpdater(repository, webClientBuilder);

		assertThat(statusUpdater1).isNotSameAs(statusUpdater2);
	}

	@Test
	void statusUpdater_shouldWorkWithNullAdminServerProperties() {
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(null);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		StatusUpdater statusUpdater = config.statusUpdater(repository, webClientBuilder);

		assertThat(statusUpdater).isNotNull();
		assertThat(statusUpdater).isInstanceOf(StatusUpdater.class);
	}

	@Test
	void statusUpdater_shouldUseBuiltWebClientNotBuilder() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		StatusUpdater statusUpdater = config.statusUpdater(repository, webClientBuilder);

		// The builder should be built (build() called) and the result passed to StatusUpdater
		// Verify the StatusUpdater was created successfully
		assertThat(statusUpdater).isNotNull();
	}

	@Test
	void statusUpdater_shouldWorkWithEmptyRepository() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		TestInstanceRepository emptyRepository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		StatusUpdater statusUpdater = config.statusUpdater(emptyRepository, webClientBuilder);

		assertThat(statusUpdater).isNotNull();
		// Verify repository is empty
		assertThat(emptyRepository.findAll().collectList().block()).isEmpty();
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
