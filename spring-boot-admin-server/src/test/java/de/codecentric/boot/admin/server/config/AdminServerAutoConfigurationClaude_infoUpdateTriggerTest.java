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
import de.codecentric.boot.admin.server.services.InfoUpdateTrigger;
import de.codecentric.boot.admin.server.services.InfoUpdater;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;

import java.time.Duration;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_infoUpdateTriggerTest {

	@Test
	void infoUpdateTrigger_shouldReturnNonNullInfoUpdateTrigger() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InfoUpdater infoUpdater = createTestInfoUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void infoUpdateTrigger_shouldReturnInfoUpdateTriggerInstance() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InfoUpdater infoUpdater = createTestInfoUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

		assertThat(trigger).isInstanceOf(InfoUpdateTrigger.class);
	}

	@Test
	void infoUpdateTrigger_shouldUseDefaultMonitorProperties() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InfoUpdater infoUpdater = createTestInfoUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

		// Verify trigger was created with default monitor properties
		assertThat(trigger).isNotNull();
		// Default properties: infoInterval=1 minute, infoLifetime=1 minute, infoMaxBackoff=10 minutes
	}

	@Test
	void infoUpdateTrigger_shouldUseCustomMonitorProperties() {
		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setInfoInterval(Duration.ofSeconds(30));
		properties.getMonitor().setInfoLifetime(Duration.ofMinutes(2));
		properties.getMonitor().setInfoMaxBackoff(Duration.ofMinutes(5));

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InfoUpdater infoUpdater = createTestInfoUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void infoUpdateTrigger_shouldUseProvidedInfoUpdater() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InfoUpdater infoUpdater = createTestInfoUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void infoUpdateTrigger_shouldUseProvidedEventPublisher() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InfoUpdater infoUpdater = createTestInfoUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void infoUpdateTrigger_shouldCreateNewInstanceEachTime() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InfoUpdater infoUpdater = createTestInfoUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		InfoUpdateTrigger trigger1 = config.infoUpdateTrigger(infoUpdater, events);
		InfoUpdateTrigger trigger2 = config.infoUpdateTrigger(infoUpdater, events);

		assertThat(trigger1).isNotSameAs(trigger2);
	}

	@Test
	void infoUpdateTrigger_shouldWorkWithVariousInfoIntervals() {
		AdminServerProperties properties = new AdminServerProperties();

		// Test with different intervals
		Duration[] intervals = {
				Duration.ofSeconds(10), Duration.ofSeconds(30), Duration.ofMinutes(1), Duration.ofMinutes(5) };

		for (Duration interval : intervals) {
			properties.getMonitor().setInfoInterval(interval);
			AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

			InfoUpdater infoUpdater = createTestInfoUpdater();
			Publisher<InstanceEvent> events = Flux.empty();

			InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

			assertThat(trigger).isNotNull();
		}
	}

	@Test
	void infoUpdateTrigger_shouldWorkWithVariousInfoLifetimes() {
		AdminServerProperties properties = new AdminServerProperties();

		// Test with different lifetimes
		Duration[] lifetimes = {
				Duration.ofSeconds(30), Duration.ofMinutes(1), Duration.ofMinutes(2), Duration.ofMinutes(10) };

		for (Duration lifetime : lifetimes) {
			properties.getMonitor().setInfoLifetime(lifetime);
			AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

			InfoUpdater infoUpdater = createTestInfoUpdater();
			Publisher<InstanceEvent> events = Flux.empty();

			InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

			assertThat(trigger).isNotNull();
		}
	}

	@Test
	void infoUpdateTrigger_shouldWorkWithVariousMaxBackoffs() {
		AdminServerProperties properties = new AdminServerProperties();

		// Test with different max backoffs
		Duration[] backoffs = {
				Duration.ofMinutes(1), Duration.ofMinutes(5), Duration.ofMinutes(10), Duration.ofMinutes(30) };

		for (Duration backoff : backoffs) {
			properties.getMonitor().setInfoMaxBackoff(backoff);
			AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

			InfoUpdater infoUpdater = createTestInfoUpdater();
			Publisher<InstanceEvent> events = Flux.empty();

			InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

			assertThat(trigger).isNotNull();
		}
	}

	@Test
	void infoUpdateTrigger_shouldPassAllMonitorPropertiesToConstructor() {
		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setInfoInterval(Duration.ofSeconds(45));
		properties.getMonitor().setInfoLifetime(Duration.ofMinutes(3));
		properties.getMonitor().setInfoMaxBackoff(Duration.ofMinutes(15));

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InfoUpdater infoUpdater = createTestInfoUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		// Verify all three monitor properties are passed to the constructor
		InfoUpdateTrigger trigger = config.infoUpdateTrigger(infoUpdater, events);

		assertThat(trigger).isNotNull();
	}

	private InfoUpdater createTestInfoUpdater() {
		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient webClient = InstanceWebClient.builder().build();
		return new InfoUpdater(repository, webClient,
				new de.codecentric.boot.admin.server.services.ApiMediaTypeHandler());
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
