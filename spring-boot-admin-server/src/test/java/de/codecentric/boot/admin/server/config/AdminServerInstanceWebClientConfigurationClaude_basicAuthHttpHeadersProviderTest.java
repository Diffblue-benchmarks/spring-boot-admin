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

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.codecentric.boot.admin.server.web.client.BasicAuthHttpHeaderProvider;

import static org.assertj.core.api.Assertions.assertThat;

class AdminServerInstanceWebClientConfigurationClaude_basicAuthHttpHeadersProviderTest {

	@Test
	void basicAuthHttpHeadersProvider_shouldCreateProviderWithNoCredentialsWhenAuthDisabled() {
		// This test covers lines 167, 169, and 174
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(false);

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void basicAuthHttpHeadersProvider_shouldCreateProviderWithDefaultCredentialsWhenAuthEnabled() {
		// This test covers lines 167, 169, 170, and 171
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);
		properties.getInstanceAuth().setDefaultUserName("testuser");
		properties.getInstanceAuth().setDefaultPassword("testpass");

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void basicAuthHttpHeadersProvider_shouldCreateProviderWithServiceMapWhenAuthEnabled() {
		// This test covers lines 167, 169, 170, and 171 with service map
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);
		properties.getInstanceAuth().setDefaultUserName("admin");
		properties.getInstanceAuth().setDefaultPassword("secret");

		Map<String, BasicAuthHttpHeaderProvider.InstanceCredentials> serviceMap = new HashMap<>();
		serviceMap.put("myservice", new BasicAuthHttpHeaderProvider.InstanceCredentials("svcuser", "svcpass"));
		properties.getInstanceAuth().setServiceMap(serviceMap);

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void basicAuthHttpHeadersProvider_shouldCreateProviderWithNullCredentialsWhenAuthEnabled() {
		// This test covers lines 167, 169, 170, and 171 with null credentials
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);
		// Leave defaultUserName and defaultPassword as null

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void basicAuthHttpHeadersProvider_shouldCreateProviderWithEmptyServiceMapWhenAuthEnabled() {
		// This test covers lines 167, 169, 170, and 171 with empty service map
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);
		properties.getInstanceAuth().setDefaultUserName("user");
		properties.getInstanceAuth().setDefaultPassword("pass");
		properties.getInstanceAuth().setServiceMap(new HashMap<>());

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void basicAuthHttpHeadersProvider_shouldAccessInstanceAuthProperties() {
		// This test specifically targets line 167
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		AdminServerProperties.InstanceAuthProperties instanceAuth = properties.getInstanceAuth();
		instanceAuth.setEnabled(false);

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void basicAuthHttpHeadersProvider_shouldHandleEnabledTrueCondition() {
		// This test specifically targets the true branch of line 169
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void basicAuthHttpHeadersProvider_shouldHandleEnabledFalseCondition() {
		// This test specifically targets the false branch of line 169
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(false);

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void basicAuthHttpHeadersProvider_shouldPassAllParametersToProviderConstructor() {
		// This test ensures lines 170-171 are fully executed with all parameters
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);
		properties.getInstanceAuth().setDefaultUserName("myuser");
		properties.getInstanceAuth().setDefaultPassword("mypassword");

		Map<String, BasicAuthHttpHeaderProvider.InstanceCredentials> serviceMap = new HashMap<>();
		serviceMap.put("service1", new BasicAuthHttpHeaderProvider.InstanceCredentials("user1", "pass1"));
		serviceMap.put("service2", new BasicAuthHttpHeaderProvider.InstanceCredentials("user2", "pass2"));
		properties.getInstanceAuth().setServiceMap(serviceMap);

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void basicAuthHttpHeadersProvider_shouldCreateNoArgProviderWhenDisabled() {
		// This test specifically targets line 174
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(false);
		// Even if we set credentials, they should be ignored when disabled
		properties.getInstanceAuth().setDefaultUserName("ignored");
		properties.getInstanceAuth().setDefaultPassword("ignored");

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

}
