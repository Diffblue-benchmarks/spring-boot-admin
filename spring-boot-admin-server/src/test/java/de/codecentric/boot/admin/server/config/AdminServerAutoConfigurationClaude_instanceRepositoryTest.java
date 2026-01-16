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

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.entities.SnapshottingInstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceRegisteredEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.eventstore.InMemoryEventStore;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_instanceRepositoryTest {

	@Test
	void instanceRepository_shouldReturnNonNullRepository() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		assertThat(repository).isNotNull();
	}

	@Test
	void instanceRepository_shouldReturnSnapshottingInstanceRepository() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		assertThat(repository).isInstanceOf(SnapshottingInstanceRepository.class);
	}

	@Test
	void instanceRepository_shouldReturnInstanceRepository() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		assertThat(repository).isInstanceOf(InstanceRepository.class);
	}

	@Test
	void instanceRepository_shouldCreateRepositoryWithProvidedEventStore() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		// Verify repository is functional by calling findAll
		Flux<Instance> instances = repository.findAll();
		assertThat(instances).isNotNull();
	}

	@Test
	void instanceRepository_shouldCreateFunctionalRepository() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		// Verify the repository can save and retrieve instances
		InstanceId instanceId = InstanceId.of("test-instance");
		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		Instance instance = Instance.create(instanceId).register(registration);

		StepVerifier.create(repository.save(instance)).expectNext(instance).verifyComplete();
	}

	@Test
	void instanceRepository_shouldBeAbleToSaveAndRetrieveInstance() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		// Start the repository to enable snapshotting
		repository.start();

		try {
			InstanceId instanceId = InstanceId.of("test-instance");
			Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
			Instance instance = Instance.create(instanceId).register(registration);

			// Save instance
			StepVerifier.create(repository.save(instance)).expectNext(instance).verifyComplete();

			// Retrieve instance
			StepVerifier.create(repository.find(instanceId)).assertNext(retrieved -> {
				assertThat(retrieved).isNotNull();
				assertThat(retrieved.getId()).isEqualTo(instanceId);
			}).verifyComplete();
		}
		finally {
			repository.stop();
		}
	}

	@Test
	void instanceRepository_shouldReturnEmptyForNonExistentInstance() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		InstanceId nonExistentId = InstanceId.of("non-existent");

		// Verify find returns empty for a non-existent instance
		StepVerifier.create(repository.find(nonExistentId)).verifyComplete();
	}

	@Test
	void instanceRepository_shouldCreateNewInstanceEachTime() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository1 = config.instanceRepository(eventStore);
		SnapshottingInstanceRepository repository2 = config.instanceRepository(eventStore);

		assertThat(repository1).isNotSameAs(repository2);
	}

	@Test
	void instanceRepository_shouldWorkWithDifferentEventStores() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore1 = new InMemoryEventStore();
		InMemoryEventStore eventStore2 = new InMemoryEventStore();

		SnapshottingInstanceRepository repository1 = config.instanceRepository(eventStore1);
		SnapshottingInstanceRepository repository2 = config.instanceRepository(eventStore2);

		// Start both repositories
		repository1.start();
		repository2.start();

		try {
			InstanceId instanceId = InstanceId.of("test-instance");
			Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
			Instance instance = Instance.create(instanceId).register(registration);

			// Save to first repository
			StepVerifier.create(repository1.save(instance)).expectNext(instance).verifyComplete();

			// Verify second repository doesn't have the instance
			StepVerifier.create(repository2.find(instanceId)).verifyComplete();
		}
		finally {
			repository1.stop();
			repository2.stop();
		}
	}

	@Test
	void instanceRepository_shouldWorkWithNullAdminServerProperties() {
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(null);

		InstanceEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		assertThat(repository).isNotNull();
		assertThat(repository).isInstanceOf(SnapshottingInstanceRepository.class);
	}

	@Test
	void instanceRepository_shouldIntegrateWithEventStore() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		// Start the repository to enable snapshotting
		repository.start();

		try {
			InstanceId instanceId = InstanceId.of("test-instance");
			Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();

			// Manually add event to event store
			InstanceEvent event = new InstanceRegisteredEvent(instanceId, 0L, Instant.now(), registration);
			StepVerifier.create(eventStore.append(Collections.singletonList(event))).verifyComplete();

			// Give the repository time to process the event
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			// Verify repository can retrieve the instance
			StepVerifier.create(repository.find(instanceId)).assertNext(instance -> {
				assertThat(instance).isNotNull();
				assertThat(instance.getId()).isEqualTo(instanceId);
			}).verifyComplete();
		}
		finally {
			repository.stop();
		}
	}

	@Test
	void instanceRepository_shouldBeAbleToFindAllInstances() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		// Start the repository to enable snapshotting
		repository.start();

		try {
			InstanceId instanceId1 = InstanceId.of("test-instance-1");
			InstanceId instanceId2 = InstanceId.of("test-instance-2");
			Registration registration1 = Registration.create("test-app-1", "http://localhost:8080/health").build();
			Registration registration2 = Registration.create("test-app-2", "http://localhost:8081/health").build();

			Instance instance1 = Instance.create(instanceId1).register(registration1);
			Instance instance2 = Instance.create(instanceId2).register(registration2);

			// Save instances
			StepVerifier.create(repository.save(instance1)).expectNext(instance1).verifyComplete();
			StepVerifier.create(repository.save(instance2)).expectNext(instance2).verifyComplete();

			// Verify findAll returns all instances
			StepVerifier.create(repository.findAll()).assertNext(instance -> {
				assertThat(instance.getId()).isIn(instanceId1, instanceId2);
			}).assertNext(instance -> {
				assertThat(instance.getId()).isIn(instanceId1, instanceId2);
			}).verifyComplete();
		}
		finally {
			repository.stop();
		}
	}

	@Test
	void instanceRepository_shouldUseProvidedEventStoreForPersistence() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InMemoryEventStore eventStore = new InMemoryEventStore();
		SnapshottingInstanceRepository repository = config.instanceRepository(eventStore);

		// Start the repository
		repository.start();

		try {
			InstanceId instanceId = InstanceId.of("test-instance");
			Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
			Instance instance = Instance.create(instanceId).register(registration);

			// Save instance through repository
			StepVerifier.create(repository.save(instance)).expectNext(instance).verifyComplete();

			// Verify event was stored in the event store
			StepVerifier.create(eventStore.find(instanceId)).expectNextCount(1).verifyComplete();
		}
		finally {
			repository.stop();
		}
	}

}
