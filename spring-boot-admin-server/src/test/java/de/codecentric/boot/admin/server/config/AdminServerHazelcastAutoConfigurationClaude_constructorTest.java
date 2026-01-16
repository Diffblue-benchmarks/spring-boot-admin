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

class AdminServerHazelcastAutoConfigurationClaude_constructorTest {

	@Test
	void constructor_shouldCreateInstanceSuccessfully() {
		AdminServerHazelcastAutoConfiguration config = new AdminServerHazelcastAutoConfiguration();

		assertThat(config).isNotNull();
	}

	@Test
	void constructor_shouldInitializeNameEventStoreMapToDefaultValue() throws Exception {
		// Reflection is necessary here because the nameEventStoreMap field is private
		// with no public getter, and there is no other way to verify that the constructor
		// properly initializes the field to its default value without setting up a full
		// Spring context and testing the bean methods that use this field.
		AdminServerHazelcastAutoConfiguration config = new AdminServerHazelcastAutoConfiguration();

		Field field = AdminServerHazelcastAutoConfiguration.class.getDeclaredField("nameEventStoreMap");
		field.setAccessible(true);
		String nameEventStoreMap = (String) field.get(config);

		assertThat(nameEventStoreMap).isEqualTo(AdminServerHazelcastAutoConfiguration.DEFAULT_NAME_EVENT_STORE_MAP);
		assertThat(nameEventStoreMap).isEqualTo("spring-boot-admin-event-store");
	}

	@Test
	void notifierTriggerConfiguration_constructor_shouldCreateInstanceSuccessfully() {
		AdminServerHazelcastAutoConfiguration.NotifierTriggerConfiguration config = new AdminServerHazelcastAutoConfiguration.NotifierTriggerConfiguration();

		assertThat(config).isNotNull();
	}

	@Test
	void notifierTriggerConfiguration_constructor_shouldInitializeNameSentNotificationsMapToDefaultValue()
			throws Exception {
		// Reflection is necessary here because the nameSentNotificationsMap field is private
		// with no public getter, and there is no other way to verify that the constructor
		// properly initializes the field to its default value without setting up a full
		// Spring context and testing the bean methods that use this field.
		AdminServerHazelcastAutoConfiguration.NotifierTriggerConfiguration config = new AdminServerHazelcastAutoConfiguration.NotifierTriggerConfiguration();

		Field field = AdminServerHazelcastAutoConfiguration.NotifierTriggerConfiguration.class
			.getDeclaredField("nameSentNotificationsMap");
		field.setAccessible(true);
		String nameSentNotificationsMap = (String) field.get(config);

		assertThat(nameSentNotificationsMap)
			.isEqualTo(AdminServerHazelcastAutoConfiguration.DEFAULT_NAME_SENT_NOTIFICATIONS_MAP);
		assertThat(nameSentNotificationsMap).isEqualTo("spring-boot-admin-sent-notifications");
	}

}
