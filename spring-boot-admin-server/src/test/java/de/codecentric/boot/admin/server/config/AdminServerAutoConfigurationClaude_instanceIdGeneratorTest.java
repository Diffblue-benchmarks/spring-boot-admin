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

import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.domain.values.Registration;
import de.codecentric.boot.admin.server.services.HashingInstanceUrlIdGenerator;
import de.codecentric.boot.admin.server.services.InstanceIdGenerator;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_instanceIdGeneratorTest {

	@Test
	void instanceIdGenerator_shouldReturnNonNullInstanceIdGenerator() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceIdGenerator generator = config.instanceIdGenerator();

		assertThat(generator).isNotNull();
	}

	@Test
	void instanceIdGenerator_shouldReturnHashingInstanceUrlIdGenerator() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceIdGenerator generator = config.instanceIdGenerator();

		assertThat(generator).isInstanceOf(HashingInstanceUrlIdGenerator.class);
	}

	@Test
	void instanceIdGenerator_shouldReturnFunctionalGenerator() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id = generator.generateId(registration);

		assertThat(id).isNotNull();
		assertThat(id.getValue()).isNotEmpty();
	}

	@Test
	void instanceIdGenerator_shouldGenerateConsistentIds() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id1 = generator.generateId(registration);
		InstanceId id2 = generator.generateId(registration);

		assertThat(id1).isEqualTo(id2);
		assertThat(id1.getValue()).isEqualTo(id2.getValue());
	}

	@Test
	void instanceIdGenerator_shouldGenerateDifferentIdsForDifferentUrls() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration1 = Registration.create("test-app", "http://localhost:8080/health").build();
		Registration registration2 = Registration.create("test-app", "http://localhost:8081/health").build();
		InstanceId id1 = generator.generateId(registration1);
		InstanceId id2 = generator.generateId(registration2);

		assertThat(id1).isNotEqualTo(id2);
		assertThat(id1.getValue()).isNotEqualTo(id2.getValue());
	}

	@Test
	void instanceIdGenerator_shouldGenerateHashBasedIds() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceIdGenerator generator = config.instanceIdGenerator();

		Registration registration = Registration.create("test-app", "http://localhost:8080/health").build();
		InstanceId id = generator.generateId(registration);

		// HashingInstanceUrlIdGenerator produces 12-character hex strings
		assertThat(id.getValue()).hasSize(12);
		assertThat(id.getValue()).matches("[0-9a-f]{12}");
	}

	@Test
	void instanceIdGenerator_shouldCreateNewInstanceEachTime() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceIdGenerator generator1 = config.instanceIdGenerator();
		InstanceIdGenerator generator2 = config.instanceIdGenerator();

		assertThat(generator1).isNotSameAs(generator2);
	}

	@Test
	void instanceIdGenerator_shouldWorkWithNullAdminServerProperties() {
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(null);

		InstanceIdGenerator generator = config.instanceIdGenerator();

		assertThat(generator).isNotNull();
		assertThat(generator).isInstanceOf(HashingInstanceUrlIdGenerator.class);
	}

	@Test
	void instanceIdGenerator_shouldGenerateValidIdsForVariousHealthUrls() {
		AdminServerProperties properties = new AdminServerProperties();
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		InstanceIdGenerator generator = config.instanceIdGenerator();

		// Test with various URL formats
		Registration reg1 = Registration.create("app1", "http://example.com/health").build();
		Registration reg2 = Registration.create("app2", "https://secure.example.com:8443/actuator/health").build();
		Registration reg3 = Registration.create("app3", "http://192.168.1.100:9090/health").build();

		InstanceId id1 = generator.generateId(reg1);
		InstanceId id2 = generator.generateId(reg2);
		InstanceId id3 = generator.generateId(reg3);

		assertThat(id1).isNotNull();
		assertThat(id2).isNotNull();
		assertThat(id3).isNotNull();
		assertThat(id1.getValue()).matches("[0-9a-f]{12}");
		assertThat(id2.getValue()).matches("[0-9a-f]{12}");
		assertThat(id3.getValue()).matches("[0-9a-f]{12}");
	}

}
