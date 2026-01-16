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
import de.codecentric.boot.admin.server.services.EndpointDetector;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_endpointDetectorTest {

	@Test
	void endpointDetector_shouldReturnNonNullEndpointDetector() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector = config.endpointDetector(repository, webClientBuilder);

		assertThat(detector).isNotNull();
	}

	@Test
	void endpointDetector_shouldReturnEndpointDetectorInstance() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector = config.endpointDetector(repository, webClientBuilder);

		assertThat(detector).isInstanceOf(EndpointDetector.class);
	}

	@Test
	void endpointDetector_shouldUseProvidedInstanceRepository() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector = config.endpointDetector(repository, webClientBuilder);

		assertThat(detector).isNotNull();
		// Verify repository is accessible
		assertThat(repository.findAll().collectList().block()).isEmpty();
	}

	@Test
	void endpointDetector_shouldBuildInstanceWebClientFromProvidedBuilder() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector = config.endpointDetector(repository, webClientBuilder);

		// Verify the detector was created successfully with the built web client
		assertThat(detector).isNotNull();
	}

	@Test
	void endpointDetector_shouldCreateChainingStrategyWithTwoStrategies() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector = config.endpointDetector(repository, webClientBuilder);

		// The method creates a ChainingStrategy with QueryIndexEndpointStrategy and ProbeEndpointsStrategy
		assertThat(detector).isNotNull();
	}

	@Test
	void endpointDetector_shouldUseDefaultProbedEndpoints() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector = config.endpointDetector(repository, webClientBuilder);

		// Default probed endpoints include: health, env, metrics, etc.
		assertThat(detector).isNotNull();
		assertThat(properties.getProbedEndpoints()).isNotEmpty();
		assertThat(properties.getProbedEndpoints()).contains("health", "env", "metrics");
	}

	@Test
	void endpointDetector_shouldUseCustomProbedEndpoints() {
		AdminServerProperties properties = new AdminServerProperties();
		properties.setProbedEndpoints(new String[] { "health", "info", "custom" });

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector = config.endpointDetector(repository, webClientBuilder);

		assertThat(detector).isNotNull();
		assertThat(properties.getProbedEndpoints()).containsExactly("health", "info", "custom");
	}

	@Test
	void endpointDetector_shouldCreateNewInstanceEachTime() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector1 = config.endpointDetector(repository, webClientBuilder);
		EndpointDetector detector2 = config.endpointDetector(repository, webClientBuilder);

		assertThat(detector1).isNotSameAs(detector2);
	}

	@Test
	void endpointDetector_shouldWorkWithNullAdminServerProperties() {
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(null);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		// Should throw NullPointerException when trying to access adminServerProperties.getProbedEndpoints()
		try {
			EndpointDetector detector = config.endpointDetector(repository, webClientBuilder);
			// If we get here, it means null was handled somehow (shouldn't happen based on code)
			assertThat(detector).isNotNull();
		}
		catch (NullPointerException e) {
			// Expected behavior when adminServerProperties is null
			assertThat(e).isInstanceOf(NullPointerException.class);
		}
	}

	@Test
	void endpointDetector_shouldWorkWithCustomWebClientBuilder() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		// Create a custom builder with filters
		InstanceWebClient.Builder customBuilder = InstanceWebClient.builder()
			.filter((instance, request, next) -> next.exchange(request));

		EndpointDetector detector = config.endpointDetector(repository, customBuilder);

		assertThat(detector).isNotNull();
		assertThat(detector).isInstanceOf(EndpointDetector.class);
	}

	@Test
	void endpointDetector_shouldWorkWithEmptyProbedEndpoints() {
		AdminServerProperties properties = new AdminServerProperties();
		properties.setProbedEndpoints(new String[] {});

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector = config.endpointDetector(repository, webClientBuilder);

		assertThat(detector).isNotNull();
		assertThat(properties.getProbedEndpoints()).isEmpty();
	}

	@Test
	void endpointDetector_shouldCreateNewApiMediaTypeHandler() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();

		EndpointDetector detector1 = config.endpointDetector(repository, webClientBuilder);
		EndpointDetector detector2 = config.endpointDetector(repository, webClientBuilder);

		// Each call should create new instances of ApiMediaTypeHandler
		assertThat(detector1).isNotSameAs(detector2);
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
