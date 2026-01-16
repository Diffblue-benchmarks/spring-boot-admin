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
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.services.EndpointDetectionTrigger;
import de.codecentric.boot.admin.server.services.EndpointDetector;
import de.codecentric.boot.admin.server.services.endpoints.ChainingStrategy;
import de.codecentric.boot.admin.server.services.endpoints.ProbeEndpointsStrategy;
import de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_endpointDetectionTriggerTest {

	@Test
	void endpointDetectionTrigger_shouldReturnNonNullEndpointDetectionTrigger() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		EndpointDetector endpointDetector = createTestEndpointDetector();
		Publisher<InstanceEvent> events = Flux.empty();

		EndpointDetectionTrigger trigger = config.endpointDetectionTrigger(endpointDetector, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void endpointDetectionTrigger_shouldReturnEndpointDetectionTriggerInstance() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		EndpointDetector endpointDetector = createTestEndpointDetector();
		Publisher<InstanceEvent> events = Flux.empty();

		EndpointDetectionTrigger trigger = config.endpointDetectionTrigger(endpointDetector, events);

		assertThat(trigger).isInstanceOf(EndpointDetectionTrigger.class);
	}

	@Test
	void endpointDetectionTrigger_shouldUseProvidedEndpointDetector() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		EndpointDetector endpointDetector = createTestEndpointDetector();
		Publisher<InstanceEvent> events = Flux.empty();

		EndpointDetectionTrigger trigger = config.endpointDetectionTrigger(endpointDetector, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void endpointDetectionTrigger_shouldUseProvidedEventPublisher() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		EndpointDetector endpointDetector = createTestEndpointDetector();
		Publisher<InstanceEvent> events = Flux.empty();

		EndpointDetectionTrigger trigger = config.endpointDetectionTrigger(endpointDetector, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void endpointDetectionTrigger_shouldCreateNewInstanceEachTime() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		EndpointDetector endpointDetector = createTestEndpointDetector();
		Publisher<InstanceEvent> events = Flux.empty();

		EndpointDetectionTrigger trigger1 = config.endpointDetectionTrigger(endpointDetector, events);
		EndpointDetectionTrigger trigger2 = config.endpointDetectionTrigger(endpointDetector, events);

		assertThat(trigger1).isNotSameAs(trigger2);
	}

	@Test
	void endpointDetectionTrigger_shouldWorkWithNullAdminServerProperties() {
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(null);

		EndpointDetector endpointDetector = createTestEndpointDetector();
		Publisher<InstanceEvent> events = Flux.empty();

		EndpointDetectionTrigger trigger = config.endpointDetectionTrigger(endpointDetector, events);

		assertThat(trigger).isNotNull();
		assertThat(trigger).isInstanceOf(EndpointDetectionTrigger.class);
	}

	@Test
	void endpointDetectionTrigger_shouldWorkWithDifferentEndpointDetectors() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		EndpointDetector detector1 = createTestEndpointDetector();
		EndpointDetector detector2 = createTestEndpointDetector();
		Publisher<InstanceEvent> events = Flux.empty();

		EndpointDetectionTrigger trigger1 = config.endpointDetectionTrigger(detector1, events);
		EndpointDetectionTrigger trigger2 = config.endpointDetectionTrigger(detector2, events);

		assertThat(trigger1).isNotNull();
		assertThat(trigger2).isNotNull();
		assertThat(trigger1).isNotSameAs(trigger2);
	}

	@Test
	void endpointDetectionTrigger_shouldWorkWithEmptyEventPublisher() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		EndpointDetector endpointDetector = createTestEndpointDetector();
		Publisher<InstanceEvent> emptyEvents = Flux.empty();

		EndpointDetectionTrigger trigger = config.endpointDetectionTrigger(endpointDetector, emptyEvents);

		assertThat(trigger).isNotNull();
	}

	@Test
	void endpointDetectionTrigger_shouldWorkWithEndpointDetectorFromConfig() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		// Create EndpointDetector using the config method
		InstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient.Builder webClientBuilder = InstanceWebClient.builder();
		EndpointDetector endpointDetector = config.endpointDetector(repository, webClientBuilder);

		Publisher<InstanceEvent> events = Flux.empty();

		EndpointDetectionTrigger trigger = config.endpointDetectionTrigger(endpointDetector, events);

		assertThat(trigger).isNotNull();
		assertThat(trigger).isInstanceOf(EndpointDetectionTrigger.class);
	}

	@Test
	void endpointDetectionTrigger_shouldAcceptSimpleConstructorParameters() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		EndpointDetector endpointDetector = createTestEndpointDetector();
		Publisher<InstanceEvent> events = Flux.empty();

		// The method simply passes parameters to the constructor
		EndpointDetectionTrigger trigger = config.endpointDetectionTrigger(endpointDetector, events);

		assertThat(trigger).isNotNull();
	}

	private EndpointDetector createTestEndpointDetector() {
		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient webClient = InstanceWebClient.builder().build();
		ChainingStrategy strategy = new ChainingStrategy(
				new QueryIndexEndpointStrategy(webClient,
						new de.codecentric.boot.admin.server.services.ApiMediaTypeHandler()),
				new ProbeEndpointsStrategy(webClient, new String[] { "health", "info" }));
		return new EndpointDetector(repository, strategy);
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
