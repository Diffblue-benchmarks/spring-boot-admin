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
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Tests for {@link AdminServerAutoConfiguration} constructor.
 */
class AdminServerAutoConfigurationClaude_constructorTest {

	@Test
	void constructor_shouldAcceptNonNullAdminServerProperties() {
		AdminServerProperties properties = new AdminServerProperties();

		assertThatCode(() -> new AdminServerAutoConfiguration(properties))
			.doesNotThrowAnyException();
	}

	@Test
	void constructor_shouldStoreAdminServerPropertiesInField() throws Exception {
		AdminServerProperties properties = new AdminServerProperties();
		properties.setContextPath("/admin");

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		// We must use reflection to verify the field is set correctly because the field is private
		// and there is no getter method. The constructor's sole purpose is to store the properties
		// parameter in the adminServerProperties field for later use by the bean factory methods.
		// Without reflection, there is no way to verify the constructor actually stores the reference.
		Field field = AdminServerAutoConfiguration.class.getDeclaredField("adminServerProperties");
		field.setAccessible(true);
		AdminServerProperties storedProperties = (AdminServerProperties) field.get(config);

		assertThat(storedProperties).isSameAs(properties);
		assertThat(storedProperties.getContextPath()).isEqualTo("/admin");
	}

	@Test
	void constructor_shouldStoreExactReferenceNotCopy() throws Exception {
		AdminServerProperties properties = new AdminServerProperties();

		AdminServerAutoConfiguration config = new AdminServerAutoConfiguration(properties);

		// We must use reflection to verify the exact reference is stored because the field is private
		// and there is no getter method. This verifies the constructor does reference assignment
		// rather than creating a copy.
		Field field = AdminServerAutoConfiguration.class.getDeclaredField("adminServerProperties");
		field.setAccessible(true);
		AdminServerProperties storedProperties = (AdminServerProperties) field.get(config);

		assertThat(storedProperties).isSameAs(properties);
	}

	@Test
	void constructor_shouldAcceptNullAdminServerProperties() {
		// The constructor doesn't validate null input - it will cause NullPointerException
		// later when bean factory methods try to access the properties
		assertThatCode(() -> new AdminServerAutoConfiguration(null))
			.doesNotThrowAnyException();
	}

}
