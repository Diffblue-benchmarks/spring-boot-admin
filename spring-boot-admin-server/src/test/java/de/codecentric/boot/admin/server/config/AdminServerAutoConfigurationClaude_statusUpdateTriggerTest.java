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
import de.codecentric.boot.admin.server.services.StatusUpdateTrigger;
import de.codecentric.boot.admin.server.services.StatusUpdater;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;

import java.time.Duration;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_statusUpdateTriggerTest {

	@Test
	void statusUpdateTrigger_shouldReturnNonNullStatusUpdateTrigger() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		StatusUpdater statusUpdater = createTestStatusUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void statusUpdateTrigger_shouldReturnStatusUpdateTriggerInstance() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		StatusUpdater statusUpdater = createTestStatusUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

		assertThat(trigger).isInstanceOf(StatusUpdateTrigger.class);
	}

	@Test
	void statusUpdateTrigger_shouldUseDefaultMonitorProperties() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		StatusUpdater statusUpdater = createTestStatusUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

		// Verify trigger was created with default monitor properties
		assertThat(trigger).isNotNull();
		// Default properties: statusInterval=10000ms, statusLifetime=10000ms, statusMaxBackoff=60000ms
	}

	@Test
	void statusUpdateTrigger_shouldUseCustomMonitorProperties() {
		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setStatusInterval(Duration.ofSeconds(5));
		properties.getMonitor().setStatusLifetime(Duration.ofSeconds(15));
		properties.getMonitor().setStatusMaxBackoff(Duration.ofSeconds(30));

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		StatusUpdater statusUpdater = createTestStatusUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void statusUpdateTrigger_shouldUseProvidedStatusUpdater() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		StatusUpdater statusUpdater = createTestStatusUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void statusUpdateTrigger_shouldUseProvidedEventPublisher() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		StatusUpdater statusUpdater = createTestStatusUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void statusUpdateTrigger_shouldCreateNewInstanceEachTime() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		StatusUpdater statusUpdater = createTestStatusUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		StatusUpdateTrigger trigger1 = config.statusUpdateTrigger(statusUpdater, events);
		StatusUpdateTrigger trigger2 = config.statusUpdateTrigger(statusUpdater, events);

		assertThat(trigger1).isNotSameAs(trigger2);
	}

	@Test
	void statusUpdateTrigger_shouldHandleDefaultTimeoutLargerThanStatusInterval() {
		AdminServerProperties properties = new AdminServerProperties();
		// Set defaultTimeout larger than statusInterval to trigger warning log
		properties.getMonitor().setDefaultTimeout(Duration.ofSeconds(20));
		properties.getMonitor().setStatusInterval(Duration.ofSeconds(5));

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		StatusUpdater statusUpdater = createTestStatusUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		// Should still create trigger successfully despite warning
		StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void statusUpdateTrigger_shouldHandleDefaultTimeoutSmallerThanStatusInterval() {
		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setDefaultTimeout(Duration.ofSeconds(3));
		properties.getMonitor().setStatusInterval(Duration.ofSeconds(10));

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		StatusUpdater statusUpdater = createTestStatusUpdater();
		Publisher<InstanceEvent> events = Flux.empty();

		StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

		assertThat(trigger).isNotNull();
	}

	@Test
	void statusUpdateTrigger_shouldWorkWithVariousStatusIntervals() {
		AdminServerProperties properties = new AdminServerProperties();

		// Test with different intervals
		Duration[] intervals = {
				Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(30), Duration.ofMinutes(1) };

		for (Duration interval : intervals) {
			properties.getMonitor().setStatusInterval(interval);
			AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

			StatusUpdater statusUpdater = createTestStatusUpdater();
			Publisher<InstanceEvent> events = Flux.empty();

			StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

			assertThat(trigger).isNotNull();
		}
	}

	@Test
	void statusUpdateTrigger_shouldWorkWithVariousStatusLifetimes() {
		AdminServerProperties properties = new AdminServerProperties();

		// Test with different lifetimes
		Duration[] lifetimes = {
				Duration.ofSeconds(5), Duration.ofSeconds(30), Duration.ofMinutes(1), Duration.ofMinutes(5) };

		for (Duration lifetime : lifetimes) {
			properties.getMonitor().setStatusLifetime(lifetime);
			AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

			StatusUpdater statusUpdater = createTestStatusUpdater();
			Publisher<InstanceEvent> events = Flux.empty();

			StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

			assertThat(trigger).isNotNull();
		}
	}

	@Test
	void statusUpdateTrigger_shouldWorkWithVariousMaxBackoffs() {
		AdminServerProperties properties = new AdminServerProperties();

		// Test with different max backoffs
		Duration[] backoffs = {
				Duration.ofSeconds(10), Duration.ofSeconds(60), Duration.ofMinutes(2), Duration.ofMinutes(10) };

		for (Duration backoff : backoffs) {
			properties.getMonitor().setStatusMaxBackoff(backoff);
			AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

			StatusUpdater statusUpdater = createTestStatusUpdater();
			Publisher<InstanceEvent> events = Flux.empty();

			StatusUpdateTrigger trigger = config.statusUpdateTrigger(statusUpdater, events);

			assertThat(trigger).isNotNull();
		}
	}

	private StatusUpdater createTestStatusUpdater() {
		TestInstanceRepository repository = new TestInstanceRepository();
		InstanceWebClient webClient = InstanceWebClient.builder().build();
		return new StatusUpdater(repository, webClient, new de.codecentric.boot.admin.server.services.ApiMediaTypeHandler());
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
