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

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.services.CloudFoundryInstanceIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
import de.codecentric.boot.admin.server.web.client.CloudFoundryHttpHeaderProvider;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerCloudFoundryAutoConfigurationClaudeTest {

	@Test
	void constructor_shouldCreateInstance() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		assertThat(config).isNotNull();
	}

	@Test
	void instanceIdGenerator_shouldReturnNonNullInstanceIdGenerator() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		assertThat(generator).isNotNull();
	}

	@Test
	void instanceIdGenerator_shouldReturnCloudFoundryInstanceIdGenerator() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		assertThat(generator).isInstanceOf(CloudFoundryInstanceIdGenerator.class);
	}

	@Test
	void instanceIdGenerator_shouldReturnFunctionalGenerator() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id = generator.generateId(registration);

		assertThat(id).isNotNull();
		assertThat(id.getValue()).isNotEmpty();
	}

	@Test
	void instanceIdGenerator_shouldGenerateCloudFoundryIdWithMetadata() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("applicationId", "app-123")
			.metadata("instanceId", "instance-456")
			.build();
		InstanceId id = generator.generateId(registration);

		assertThat(id).isNotNull();
		assertThat(id.getValue()).isEqualTo("app-123:instance-456");
	}

	@Test
	void instanceIdGenerator_shouldFallbackToHashWhenMetadataAbsent() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id = generator.generateId(registration);

		// Without CloudFoundry metadata, should fallback to HashingInstanceUrlIdGenerator
		// which produces 12-character hex strings
		assertThat(id.getValue()).hasSize(12);
		assertThat(id.getValue()).matches("[0-9a-f]{12}");
	}

	@Test
	void instanceIdGenerator_shouldFallbackWhenOnlyApplicationIdPresent() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("applicationId", "app-123")
			.build();
		InstanceId id = generator.generateId(registration);

		// Should fallback to hash-based ID when instanceId is missing
		assertThat(id.getValue()).hasSize(12);
		assertThat(id.getValue()).matches("[0-9a-f]{12}");
	}

	@Test
	void instanceIdGenerator_shouldFallbackWhenOnlyInstanceIdPresent() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("instanceId", "instance-456")
			.build();
		InstanceId id = generator.generateId(registration);

		// Should fallback to hash-based ID when applicationId is missing
		assertThat(id.getValue()).hasSize(12);
		assertThat(id.getValue()).matches("[0-9a-f]{12}");
	}

	@Test
	void instanceIdGenerator_shouldGenerateConsistentHashBasedIds() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id1 = generator.generateId(registration);
		InstanceId id2 = generator.generateId(registration);

		assertThat(id1).isEqualTo(id2);
		assertThat(id1.getValue()).isEqualTo(id2.getValue());
	}

	@Test
	void instanceIdGenerator_shouldGenerateConsistentCloudFoundryIds() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("applicationId", "app-123")
			.metadata("instanceId", "instance-456")
			.build();
		InstanceId id1 = generator.generateId(registration);
		InstanceId id2 = generator.generateId(registration);

		assertThat(id1).isEqualTo(id2);
		assertThat(id1.getValue()).isEqualTo(id2.getValue());
		assertThat(id1.getValue()).isEqualTo("app-123:instance-456");
	}

	@Test
	void instanceIdGenerator_shouldCreateNewInstanceEachTime() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator1 = config.instanceIdGenerator();
		InstanceIdGenerator generator2 = config.instanceIdGenerator();

		assertThat(generator1).isNotSameAs(generator2);
	}

	@Test
	void cloudFoundryHttpHeaderProvider_shouldReturnNonNullProvider() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		CloudFoundryHttpHeaderProvider provider = config.cloudFoundryHttpHeaderProvider();

		assertThat(provider).isNotNull();
	}

	@Test
	void cloudFoundryHttpHeaderProvider_shouldReturnCloudFoundryHttpHeaderProvider() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		CloudFoundryHttpHeaderProvider provider = config.cloudFoundryHttpHeaderProvider();

		assertThat(provider).isInstanceOf(CloudFoundryHttpHeaderProvider.class);
	}

	@Test
	void cloudFoundryHttpHeaderProvider_shouldReturnFunctionalProvider() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		CloudFoundryHttpHeaderProvider provider = config.cloudFoundryHttpHeaderProvider();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("applicationId", "app-123")
			.metadata("instanceId", "instance-456")
			.build();
		Instance instance = Instance.create(InstanceId.of("test-id")).register(registration);

		HttpHeaders headers = provider.getHeaders(instance);

		assertThat(headers).isNotNull();
		assertThat(headers.get("X-CF-APP-INSTANCE")).containsExactly("app-123:instance-456");
	}

	@Test
	void cloudFoundryHttpHeaderProvider_shouldReturnEmptyHeadersWhenNoMetadata() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		CloudFoundryHttpHeaderProvider provider = config.cloudFoundryHttpHeaderProvider();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		Instance instance = Instance.create(InstanceId.of("test-id")).register(registration);

		HttpHeaders headers = provider.getHeaders(instance);

		assertThat(headers).isEmpty();
	}

	@Test
	void cloudFoundryHttpHeaderProvider_shouldReturnEmptyHeadersWhenOnlyApplicationIdPresent() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		CloudFoundryHttpHeaderProvider provider = config.cloudFoundryHttpHeaderProvider();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("applicationId", "app-123")
			.build();
		Instance instance = Instance.create(InstanceId.of("test-id")).register(registration);

		HttpHeaders headers = provider.getHeaders(instance);

		assertThat(headers).isEmpty();
	}

	@Test
	void cloudFoundryHttpHeaderProvider_shouldReturnEmptyHeadersWhenOnlyInstanceIdPresent() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		CloudFoundryHttpHeaderProvider provider = config.cloudFoundryHttpHeaderProvider();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("instanceId", "instance-456")
			.build();
		Instance instance = Instance.create(InstanceId.of("test-id")).register(registration);

		HttpHeaders headers = provider.getHeaders(instance);

		assertThat(headers).isEmpty();
	}

	@Test
	void cloudFoundryHttpHeaderProvider_shouldCreateNewInstanceEachTime() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		CloudFoundryHttpHeaderProvider provider1 = config.cloudFoundryHttpHeaderProvider();
		CloudFoundryHttpHeaderProvider provider2 = config.cloudFoundryHttpHeaderProvider();

		assertThat(provider1).isNotSameAs(provider2);
	}

	@Test
	void cloudFoundryHttpHeaderProvider_shouldHandleVariousMetadataFormats() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		CloudFoundryHttpHeaderProvider provider = config.cloudFoundryHttpHeaderProvider();

		// Test with UUID-like values
		Registration registration1 = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("applicationId", "550e8400-e29b-41d4-a716-446655440000")
			.metadata("instanceId", "123e4567-e89b-12d3-a456-426614174000")
			.build();
		Instance instance1 = Instance.create(InstanceId.of("test-id-1")).register(registration1);

		HttpHeaders headers1 = provider.getHeaders(instance1);

		assertThat(headers1.get("X-CF-APP-INSTANCE"))
			.containsExactly("550e8400-e29b-41d4-a716-446655440000:123e4567-e89b-12d3-a456-426614174000");

		// Test with numeric values
		Registration registration2 = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("applicationId", "12345")
			.metadata("instanceId", "67890")
			.build();
		Instance instance2 = Instance.create(InstanceId.of("test-id-2")).register(registration2);

		HttpHeaders headers2 = provider.getHeaders(instance2);

		assertThat(headers2.get("X-CF-APP-INSTANCE")).containsExactly("12345:67890");
	}

	@Test
	void cloudFoundryHttpHeaderProvider_shouldHandleEmptyStringMetadata() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		CloudFoundryHttpHeaderProvider provider = config.cloudFoundryHttpHeaderProvider();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("applicationId", "")
			.metadata("instanceId", "instance-456")
			.build();
		Instance instance = Instance.create(InstanceId.of("test-id")).register(registration);

		HttpHeaders headers = provider.getHeaders(instance);

		// Empty string should be treated as not present
		assertThat(headers).isEmpty();
	}

	@Test
	void instanceIdGenerator_shouldHandleEmptyStringMetadata() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health")
			.metadata("applicationId", "")
			.metadata("instanceId", "")
			.build();
		InstanceId id = generator.generateId(registration);

		// Empty strings should cause fallback to hash-based ID
		assertThat(id.getValue()).hasSize(12);
		assertThat(id.getValue()).matches("[0-9a-f]{12}");
	}

	@Test
	void instanceIdGenerator_shouldGenerateDifferentHashIdsForDifferentUrls() {
		AdminServerCloudFoundryAutoConfiguration config = new AdminServerCloudFoundryAutoConfiguration();

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration1 = Registration.create("test-app", "http://localhost:8080/health").build();
		Registration registration2 = Registration.create("test-app", "http://localhost:8081/health").build();
		InstanceId id1 = generator.generateId(registration1);
		InstanceId id2 = generator.generateId(registration2);

		assertThat(id1).isNotEqualTo(id2);
		assertThat(id1.getValue()).isNotEqualTo(id2.getValue());
	}

}
