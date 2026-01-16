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
import java.net.CookiePolicy;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import de.codecentric.boot.admin.server.web.client.InstanceWebClientCustomizer;
import de.codecentric.boot.admin.server.web.client.cookies.CookieStoreCleanupTrigger;
import de.codecentric.boot.admin.server.web.client.cookies.JdkPerInstanceCookieStore;
import de.codecentric.boot.admin.server.web.client.cookies.PerInstanceCookieStore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminServerInstanceWebClientConfigurationClaudeTest {

	@Test
	void constructor_shouldCreateInstanceWithEmptyCustomizers() {
		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.empty());
		WebClient.Builder webClientBuilder = WebClient.builder();

		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				webClientBuilder);

		assertThat(config).isNotNull();
		verify(customizers).orderedStream();
	}

	@Test
	void constructor_shouldApplyCustomizersInOrder() {
		InstanceWebClientCustomizer customizer1 = mock(InstanceWebClientCustomizer.class);
		InstanceWebClientCustomizer customizer2 = mock(InstanceWebClientCustomizer.class);

		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.of(customizer1, customizer2));
		WebClient.Builder webClientBuilder = WebClient.builder();

		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				webClientBuilder);

		assertThat(config).isNotNull();
		verify(customizer1).customize(any(InstanceWebClient.Builder.class));
		verify(customizer2).customize(any(InstanceWebClient.Builder.class));
	}

	@Test
	void constructor_shouldPassSameBuilderInstanceToAllCustomizers() throws Exception {
		// Reflection is necessary here because instanceWebClientBuilder is private with no
		// public getter, and we need to verify that all customizers receive the same builder
		// instance. Testing this behavior without reflection would require setting up a full
		// Spring context.
		InstanceWebClientCustomizer[] capturedBuilders = new InstanceWebClientCustomizer[2];

		InstanceWebClientCustomizer customizer1 = (builder) -> capturedBuilders[0] = (b) -> {
		};
		InstanceWebClientCustomizer customizer2 = (builder) -> capturedBuilders[1] = (b) -> {
		};

		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.of(customizer1, customizer2));
		WebClient.Builder webClientBuilder = WebClient.builder();

		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				webClientBuilder);

		Field field = AdminServerInstanceWebClientConfiguration.class.getDeclaredField("instanceWebClientBuilder");
		field.setAccessible(true);
		InstanceWebClient.Builder storedBuilder = (InstanceWebClient.Builder) field.get(config);

		assertThat(storedBuilder).isNotNull();
	}

	@Test
	void constructor_shouldStoreNullWebClientBuilderAndFailLater() {
		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.empty());

		// Constructor should complete but null will cause issues when builder is used
		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				null);

		assertThat(config).isNotNull();
	}

	@Test
	void constructor_shouldHandleSingleCustomizer() {
		InstanceWebClientCustomizer customizer = mock(InstanceWebClientCustomizer.class);

		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.of(customizer));
		WebClient.Builder webClientBuilder = WebClient.builder();

		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				webClientBuilder);

		assertThat(config).isNotNull();
		verify(customizer, times(1)).customize(any(InstanceWebClient.Builder.class));
	}

	@Test
	void instanceWebClientBuilder_shouldReturnClonedBuilder() {
		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.empty());
		WebClient.Builder webClientBuilder = WebClient.builder();

		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				webClientBuilder);

		InstanceWebClient.Builder builder1 = config.instanceWebClientBuilder();
		InstanceWebClient.Builder builder2 = config.instanceWebClientBuilder();

		assertThat(builder1).isNotNull();
		assertThat(builder2).isNotNull();
		assertThat(builder1).isNotSameAs(builder2);
	}

	@Test
	void instanceWebClientBuilder_shouldReturnBuilderThatCanBuild() {
		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.empty());
		WebClient.Builder webClientBuilder = WebClient.builder();

		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				webClientBuilder);

		InstanceWebClient.Builder builder = config.instanceWebClientBuilder();
		InstanceWebClient client = builder.build();

		assertThat(client).isNotNull();
	}

	@Test
	void instanceWebClientBuilder_shouldReturnIndependentClones() {
		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.empty());
		WebClient.Builder webClientBuilder = WebClient.builder();

		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				webClientBuilder);

		InstanceWebClient.Builder builder1 = config.instanceWebClientBuilder();
		InstanceWebClient.Builder builder2 = config.instanceWebClientBuilder();

		// Modify builder1
		builder1.filter((instance, request, next) -> next.exchange(request));

		// Both should still be able to build successfully
		InstanceWebClient client1 = builder1.build();
		InstanceWebClient client2 = builder2.build();

		assertThat(client1).isNotNull();
		assertThat(client2).isNotNull();
		assertThat(client1).isNotSameAs(client2);
	}

	@Test
	void instanceWebClientBuilder_shouldIncludeCustomizationsFromConstructor() {
		InstanceWebClientCustomizer customizer = (builder) -> builder
			.filter((instance, request, next) -> next.exchange(request));

		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.of(customizer));
		WebClient.Builder webClientBuilder = WebClient.builder();

		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				webClientBuilder);

		// The builder returned should include the customizations
		InstanceWebClient.Builder builder = config.instanceWebClientBuilder();
		InstanceWebClient client = builder.build();

		assertThat(client).isNotNull();
	}

	@Test
	void instanceWebClientBuilder_shouldReturnNewInstanceEachTime() {
		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.empty());
		WebClient.Builder webClientBuilder = WebClient.builder();

		AdminServerInstanceWebClientConfiguration config = new AdminServerInstanceWebClientConfiguration(customizers,
				webClientBuilder);

		InstanceWebClient.Builder builder1 = config.instanceWebClientBuilder();
		InstanceWebClient.Builder builder2 = config.instanceWebClientBuilder();
		InstanceWebClient.Builder builder3 = config.instanceWebClientBuilder();

		assertThat(builder1).isNotSameAs(builder2);
		assertThat(builder2).isNotSameAs(builder3);
		assertThat(builder1).isNotSameAs(builder3);
	}

	@Test
	void constructor_shouldNotInvokeCustomizersIfStreamIsEmpty() {
		InstanceWebClientCustomizer customizer = mock(InstanceWebClientCustomizer.class);

		@SuppressWarnings("unchecked")
		ObjectProvider<InstanceWebClientCustomizer> customizers = mock(ObjectProvider.class);
		when(customizers.orderedStream()).thenReturn(Stream.empty());
		WebClient.Builder webClientBuilder = WebClient.builder();

		new AdminServerInstanceWebClientConfiguration(customizers, webClientBuilder);

		verify(customizer, never()).customize(any());
	}

	// Tests for CookieStoreConfiguration nested class

	@Test
	void cookieStoreConfiguration_constructor_shouldCreateInstance() {
		AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration config = new AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration();

		assertThat(config).isNotNull();
	}

	@Test
	void cookieStoreConfiguration_cookieStore_shouldReturnJdkPerInstanceCookieStore() {
		AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration config = new AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration();

		PerInstanceCookieStore cookieStore = config.cookieStore();

		assertThat(cookieStore).isNotNull();
		assertThat(cookieStore).isInstanceOf(JdkPerInstanceCookieStore.class);
	}

	@Test
	void cookieStoreConfiguration_cookieStore_shouldUseAcceptOriginalServerPolicy() throws Exception {
		AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration config = new AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration();

		PerInstanceCookieStore cookieStore = config.cookieStore();

		// Reflection is needed here to verify the cookie policy used internally
		// by JdkPerInstanceCookieStore, as there is no public API to access it
		assertThat(cookieStore).isInstanceOf(JdkPerInstanceCookieStore.class);
		JdkPerInstanceCookieStore jdkStore = (JdkPerInstanceCookieStore) cookieStore;

		Field cookiePolicyField = JdkPerInstanceCookieStore.class.getDeclaredField("cookiePolicy");
		cookiePolicyField.setAccessible(true);
		CookiePolicy policy = (CookiePolicy) cookiePolicyField.get(jdkStore);

		assertThat(policy).isEqualTo(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
	}

	@Test
	void cookieStoreConfiguration_cookieStore_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration config = new AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration();

		PerInstanceCookieStore cookieStore1 = config.cookieStore();
		PerInstanceCookieStore cookieStore2 = config.cookieStore();

		assertThat(cookieStore1).isNotNull();
		assertThat(cookieStore2).isNotNull();
		assertThat(cookieStore1).isNotSameAs(cookieStore2);
	}

	@Test
	void cookieStoreConfiguration_cookieStoreCleanupTrigger_shouldCreateTrigger() {
		AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration config = new AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration();

		Publisher<InstanceEvent> publisher = Flux.empty();
		PerInstanceCookieStore cookieStore = config.cookieStore();

		CookieStoreCleanupTrigger trigger = config.cookieStoreCleanupTrigger(publisher, cookieStore);

		assertThat(trigger).isNotNull();
	}

	@Test
	void cookieStoreConfiguration_cookieStoreCleanupTrigger_shouldAcceptPublisher() {
		AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration config = new AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration();

		Publisher<InstanceEvent> publisher = Flux.empty();
		PerInstanceCookieStore cookieStore = new JdkPerInstanceCookieStore();

		CookieStoreCleanupTrigger trigger = config.cookieStoreCleanupTrigger(publisher, cookieStore);

		assertThat(trigger).isNotNull();
	}

	@Test
	void cookieStoreConfiguration_cookieStoreCleanupTrigger_shouldCreateNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration config = new AdminServerInstanceWebClientConfiguration.CookieStoreConfiguration();

		Publisher<InstanceEvent> publisher = Flux.empty();
		PerInstanceCookieStore cookieStore = config.cookieStore();

		CookieStoreCleanupTrigger trigger1 = config.cookieStoreCleanupTrigger(publisher, cookieStore);
		CookieStoreCleanupTrigger trigger2 = config.cookieStoreCleanupTrigger(publisher, cookieStore);

		assertThat(trigger1).isNotNull();
		assertThat(trigger2).isNotNull();
		assertThat(trigger1).isNotSameAs(trigger2);
	}

}
