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

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerAutoConfigurationClaude_constructorTest {

	@Test
	void constructor_shouldAcceptNonNullAdminServerProperties() {
		AdminServerProperties properties = new AdminServerProperties();

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		assertThat(config).isNotNull();
	}

	@Test
	void constructor_shouldAcceptPropertiesWithDefaultValues() {
		AdminServerProperties properties = new AdminServerProperties();

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		assertThat(config).isNotNull();
		// Verify the constructor accepts properties with default values
		assertThat(properties.getContextPath()).isEqualTo("");
		assertThat(properties.getServer()).isNotNull();
		assertThat(properties.getMonitor()).isNotNull();
	}

	@Test
	void constructor_shouldAcceptPropertiesWithCustomValues() {
		AdminServerProperties properties = new AdminServerProperties();
		properties.setContextPath("/admin");

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		assertThat(config).isNotNull();
	}

	@Test
	void constructor_shouldAcceptNullAdminServerProperties() {
		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(null);

		assertThat(config).isNotNull();
	}

	@Test
	void constructor_shouldStoreAdminServerPropertiesInField() throws Exception {
		// Reflection is necessary here because the adminServerProperties field is private
		// with no public getter, and there is no other way to verify that the constructor
		// properly stores the parameter value without setting up a full Spring context
		// and testing the bean methods that use this field.
		AdminServerProperties properties = new AdminServerProperties();
		properties.setContextPath("/custom");

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		Field field = AdminServerAutoConfiguration.class.getDeclaredField("adminServerProperties");
		field.setAccessible(true);
		AdminServerProperties storedProperties = (AdminServerProperties) field.get(config);

		assertThat(storedProperties).isSameAs(properties);
		assertThat(storedProperties.getContextPath()).isEqualTo("/custom");
	}

}
