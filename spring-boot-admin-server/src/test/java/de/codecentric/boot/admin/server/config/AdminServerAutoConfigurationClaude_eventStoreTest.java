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

import java.time.Instant;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_eventStoreTest {

	@Test
	void eventStore_shouldReturnNonNullEventStore() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = config.eventStore();

		assertThat(eventStore).isNotNull();
	}

	@Test
	void eventStore_shouldReturnInMemoryEventStore() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = config.eventStore();

		assertThat(eventStore).isInstanceOf(InMemoryEventStore.class);
	}

	@Test
	void eventStore_shouldReturnInstanceEventStore() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = config.eventStore();

		assertThat(eventStore).isInstanceOf(InstanceEventStore.class);
	}

	@Test
	void eventStore_shouldReturnFunctionalEventStore() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = config.eventStore();

		// Verify the event store is functional by checking findAll() works
		Flux<InstanceEvent> events = eventStore.findAll();
		assertThat(events).isNotNull();
	}

	@Test
	void eventStore_shouldBeAbleToAppendAndRetrieveEvents() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = config.eventStore();

		InstanceId instanceId = InstanceId.of("test-instance");
		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceEvent event = new InstanceRegisteredEvent(instanceId, 0L, Instant.now(), registration);

		// Append an event
		StepVerifier.create(eventStore.append(Collections.singletonList(event))).verifyComplete();

		// Verify we can retrieve the event
		StepVerifier.create(eventStore.find(instanceId)).expectNext(event).verifyComplete();
	}

	@Test
	void eventStore_shouldReturnEmptyFluxForNonExistentInstance() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = config.eventStore();

		InstanceId nonExistentId = InstanceId.of("non-existent");

		// Verify findAll returns empty for a non-existent instance
		StepVerifier.create(eventStore.find(nonExistentId)).verifyComplete();
	}

	@Test
	void eventStore_shouldCreateNewInstanceEachTime() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore1 = config.eventStore();
		InMemoryEventStore eventStore2 = config.eventStore();

		assertThat(eventStore1).isNotSameAs(eventStore2);
	}

	@Test
	void eventStore_shouldCreateIndependentInstances() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore1 = config.eventStore();
		InMemoryEventStore eventStore2 = config.eventStore();

		InstanceId instanceId = InstanceId.of("test-instance");
		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceEvent event = new InstanceRegisteredEvent(instanceId, 0L, Instant.now(), registration);

		// Append event to first store
		StepVerifier.create(eventStore1.append(Collections.singletonList(event))).verifyComplete();

		// Verify second store doesn't have the event
		StepVerifier.create(eventStore2.find(instanceId)).verifyComplete();
	}

	@Test
	void eventStore_shouldWorkWithNullAdminServerProperties() {
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(null);

		InMemoryEventStore eventStore = config.eventStore();

		assertThat(eventStore).isNotNull();
		assertThat(eventStore).isInstanceOf(InMemoryEventStore.class);
	}

	@Test
	void eventStore_shouldBeAbleToStoreMultipleEvents() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = config.eventStore();

		InstanceId instanceId1 = InstanceId.of("test-instance-1");
		InstanceId instanceId2 = InstanceId.of("test-instance-2");
		Registration registration1 = Registration.create("test-app-1", "http://localhost:8080/health").build();
		Registration registration2 = Registration.create("test-app-2", "http://localhost:8081/health").build();
		InstanceEvent event1 = new InstanceRegisteredEvent(instanceId1, 0L, Instant.now(), registration1);
		InstanceEvent event2 = new InstanceRegisteredEvent(instanceId2, 0L, Instant.now(), registration2);

		// Append events
		StepVerifier.create(eventStore.append(Collections.singletonList(event1))).verifyComplete();
		StepVerifier.create(eventStore.append(Collections.singletonList(event2))).verifyComplete();

		// Verify we can retrieve all events
		StepVerifier.create(eventStore.findAll()).expectNext(event1).expectNext(event2).verifyComplete();
	}

}
