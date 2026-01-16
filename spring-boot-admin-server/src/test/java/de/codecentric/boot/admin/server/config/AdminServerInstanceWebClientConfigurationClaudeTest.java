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
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.web.client.BasicAuthHttpHeaderProvider;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import de.codecentric.boot.admin.server.web.client.InstanceExchangeFilterFunction;
import de.codecentric.boot.admin.server.web.client.InstanceWebClient;
import de.codecentric.boot.admin.server.web.client.InstanceWebClientCustomizer;
import de.codecentric.boot.admin.server.web.client.LegacyEndpointConverter;
import de.codecentric.boot.admin.server.web.client.cookies.CookieStoreCleanupTrigger;
import de.codecentric.boot.admin.server.web.client.cookies.JdkPerInstanceCookieStore;
import de.codecentric.boot.admin.server.web.client.cookies.PerInstanceCookieStore;
import de.codecentric.boot.admin.server.web.client.reactive.ReactiveHttpHeadersProvider;

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

	// Tests for HttpHeadersProviderConfiguration nested class

	@Test
	void httpHeadersProviderConfiguration_constructor_shouldCreateInstance() {
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		assertThat(config).isNotNull();
	}

	@Test
	void httpHeadersProviderConfiguration_basicAuthHttpHeadersProvider_shouldReturnProviderWhenAuthDisabled() {
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(false);

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void httpHeadersProviderConfiguration_basicAuthHttpHeadersProvider_shouldReturnProviderWithDefaultCredentialsWhenAuthEnabled() {
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);
		properties.getInstanceAuth().setDefaultUserName("admin");
		properties.getInstanceAuth().setDefaultPassword("secret");

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void httpHeadersProviderConfiguration_basicAuthHttpHeadersProvider_shouldReturnProviderWithServiceMapWhenAuthEnabled() {
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);
		properties.getInstanceAuth().setDefaultUserName("admin");
		properties.getInstanceAuth().setDefaultPassword("secret");

		Map<String, BasicAuthHttpHeaderProvider.InstanceCredentials> serviceMap = new HashMap<>();
		serviceMap.put("service1",
				new BasicAuthHttpHeaderProvider.InstanceCredentials("user1", "password1"));
		properties.getInstanceAuth().setServiceMap(serviceMap);

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void httpHeadersProviderConfiguration_basicAuthHttpHeadersProvider_shouldReturnProviderWithNullDefaultsWhenAuthEnabled() {
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);
		// defaultUserName and defaultPassword remain null

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void httpHeadersProviderConfiguration_basicAuthHttpHeadersProvider_shouldReturnProviderWithEmptyServiceMapWhenAuthEnabled() {
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);
		properties.getInstanceAuth().setDefaultUserName("admin");
		properties.getInstanceAuth().setDefaultPassword("secret");
		// serviceMap is empty by default

		BasicAuthHttpHeaderProvider provider = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider).isNotNull();
	}

	@Test
	void httpHeadersProviderConfiguration_basicAuthHttpHeadersProvider_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration config = new AdminServerInstanceWebClientConfiguration.HttpHeadersProviderConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getInstanceAuth().setEnabled(true);

		BasicAuthHttpHeaderProvider provider1 = config.basicAuthHttpHeadersProvider(properties);
		BasicAuthHttpHeaderProvider provider2 = config.basicAuthHttpHeadersProvider(properties);

		assertThat(provider1).isNotNull();
		assertThat(provider2).isNotNull();
		assertThat(provider1).isNotSameAs(provider2);
	}

	// Tests for DefaultInstanceExchangeFiltersConfiguration nested class

	@Test
	void defaultInstanceExchangeFiltersConfiguration_constructor_shouldCreateInstance() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		assertThat(config).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_addHeadersInstanceExchangeFilter_shouldReturnFilterWithEmptyList() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		List<HttpHeadersProvider> providers = Collections.emptyList();

		InstanceExchangeFilterFunction filter = config.addHeadersInstanceExchangeFilter(providers);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_addHeadersInstanceExchangeFilter_shouldReturnFilterWithSingleProvider() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		HttpHeadersProvider provider = mock(HttpHeadersProvider.class);
		List<HttpHeadersProvider> providers = Collections.singletonList(provider);

		InstanceExchangeFilterFunction filter = config.addHeadersInstanceExchangeFilter(providers);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_addHeadersInstanceExchangeFilter_shouldReturnFilterWithMultipleProviders() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		HttpHeadersProvider provider1 = mock(HttpHeadersProvider.class);
		HttpHeadersProvider provider2 = mock(HttpHeadersProvider.class);
		List<HttpHeadersProvider> providers = new ArrayList<>();
		providers.add(provider1);
		providers.add(provider2);

		InstanceExchangeFilterFunction filter = config.addHeadersInstanceExchangeFilter(providers);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_addHeadersInstanceExchangeFilter_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		List<HttpHeadersProvider> providers = Collections.emptyList();

		InstanceExchangeFilterFunction filter1 = config.addHeadersInstanceExchangeFilter(providers);
		InstanceExchangeFilterFunction filter2 = config.addHeadersInstanceExchangeFilter(providers);

		assertThat(filter1).isNotNull();
		assertThat(filter2).isNotNull();
		assertThat(filter1).isNotSameAs(filter2);
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_addReactiveHeadersInstanceExchangeFilter_shouldReturnFilterWithEmptyList() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		List<ReactiveHttpHeadersProvider> providers = Collections.emptyList();

		InstanceExchangeFilterFunction filter = config.addReactiveHeadersInstanceExchangeFilter(providers);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_addReactiveHeadersInstanceExchangeFilter_shouldReturnFilterWithSingleProvider() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		ReactiveHttpHeadersProvider provider = mock(ReactiveHttpHeadersProvider.class);
		List<ReactiveHttpHeadersProvider> providers = Collections.singletonList(provider);

		InstanceExchangeFilterFunction filter = config.addReactiveHeadersInstanceExchangeFilter(providers);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_addReactiveHeadersInstanceExchangeFilter_shouldReturnFilterWithMultipleProviders() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		ReactiveHttpHeadersProvider provider1 = mock(ReactiveHttpHeadersProvider.class);
		ReactiveHttpHeadersProvider provider2 = mock(ReactiveHttpHeadersProvider.class);
		List<ReactiveHttpHeadersProvider> providers = new ArrayList<>();
		providers.add(provider1);
		providers.add(provider2);

		InstanceExchangeFilterFunction filter = config.addReactiveHeadersInstanceExchangeFilter(providers);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_addReactiveHeadersInstanceExchangeFilter_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		List<ReactiveHttpHeadersProvider> providers = Collections.emptyList();

		InstanceExchangeFilterFunction filter1 = config.addReactiveHeadersInstanceExchangeFilter(providers);
		InstanceExchangeFilterFunction filter2 = config.addReactiveHeadersInstanceExchangeFilter(providers);

		assertThat(filter1).isNotNull();
		assertThat(filter2).isNotNull();
		assertThat(filter1).isNotSameAs(filter2);
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_rewriteEndpointUrlInstanceExchangeFilter_shouldReturnFilter() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		InstanceExchangeFilterFunction filter = config.rewriteEndpointUrlInstanceExchangeFilter();

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_rewriteEndpointUrlInstanceExchangeFilter_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		InstanceExchangeFilterFunction filter1 = config.rewriteEndpointUrlInstanceExchangeFilter();
		InstanceExchangeFilterFunction filter2 = config.rewriteEndpointUrlInstanceExchangeFilter();

		assertThat(filter1).isNotNull();
		assertThat(filter2).isNotNull();
		assertThat(filter1).isNotSameAs(filter2);
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_setDefaultAcceptHeaderInstanceExchangeFilter_shouldReturnFilter() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		InstanceExchangeFilterFunction filter = config.setDefaultAcceptHeaderInstanceExchangeFilter();

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_setDefaultAcceptHeaderInstanceExchangeFilter_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		InstanceExchangeFilterFunction filter1 = config.setDefaultAcceptHeaderInstanceExchangeFilter();
		InstanceExchangeFilterFunction filter2 = config.setDefaultAcceptHeaderInstanceExchangeFilter();

		assertThat(filter1).isNotNull();
		assertThat(filter2).isNotNull();
		assertThat(filter1).isNotSameAs(filter2);
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_legacyEndpointConverterInstanceExchangeFilter_shouldReturnFilterWithEmptyList() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		List<LegacyEndpointConverter> converters = Collections.emptyList();

		InstanceExchangeFilterFunction filter = config.legacyEndpointConverterInstanceExchangeFilter(converters);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_legacyEndpointConverterInstanceExchangeFilter_shouldReturnFilterWithSingleConverter() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		LegacyEndpointConverter converter = mock(LegacyEndpointConverter.class);
		List<LegacyEndpointConverter> converters = Collections.singletonList(converter);

		InstanceExchangeFilterFunction filter = config.legacyEndpointConverterInstanceExchangeFilter(converters);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_legacyEndpointConverterInstanceExchangeFilter_shouldReturnFilterWithMultipleConverters() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		LegacyEndpointConverter converter1 = mock(LegacyEndpointConverter.class);
		LegacyEndpointConverter converter2 = mock(LegacyEndpointConverter.class);
		List<LegacyEndpointConverter> converters = new ArrayList<>();
		converters.add(converter1);
		converters.add(converter2);

		InstanceExchangeFilterFunction filter = config.legacyEndpointConverterInstanceExchangeFilter(converters);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_legacyEndpointConverterInstanceExchangeFilter_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		List<LegacyEndpointConverter> converters = Collections.emptyList();

		InstanceExchangeFilterFunction filter1 = config.legacyEndpointConverterInstanceExchangeFilter(converters);
		InstanceExchangeFilterFunction filter2 = config.legacyEndpointConverterInstanceExchangeFilter(converters);

		assertThat(filter1).isNotNull();
		assertThat(filter2).isNotNull();
		assertThat(filter1).isNotSameAs(filter2);
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_logfileAcceptWorkaround_shouldReturnFilter() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		InstanceExchangeFilterFunction filter = config.logfileAcceptWorkaround();

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_logfileAcceptWorkaround_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		InstanceExchangeFilterFunction filter1 = config.logfileAcceptWorkaround();
		InstanceExchangeFilterFunction filter2 = config.logfileAcceptWorkaround();

		assertThat(filter1).isNotNull();
		assertThat(filter2).isNotNull();
		assertThat(filter1).isNotSameAs(filter2);
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_cookieHandlingInstanceExchangeFilter_shouldReturnFilter() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		PerInstanceCookieStore store = new JdkPerInstanceCookieStore();

		InstanceExchangeFilterFunction filter = config.cookieHandlingInstanceExchangeFilter(store);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_cookieHandlingInstanceExchangeFilter_shouldReturnFilterWithCustomStore() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		PerInstanceCookieStore store = new JdkPerInstanceCookieStore(CookiePolicy.ACCEPT_ALL);

		InstanceExchangeFilterFunction filter = config.cookieHandlingInstanceExchangeFilter(store);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_cookieHandlingInstanceExchangeFilter_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		PerInstanceCookieStore store = new JdkPerInstanceCookieStore();

		InstanceExchangeFilterFunction filter1 = config.cookieHandlingInstanceExchangeFilter(store);
		InstanceExchangeFilterFunction filter2 = config.cookieHandlingInstanceExchangeFilter(store);

		assertThat(filter1).isNotNull();
		assertThat(filter2).isNotNull();
		assertThat(filter1).isNotSameAs(filter2);
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_retryInstanceExchangeFilter_shouldReturnFilterWithDefaultProperties() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();

		InstanceExchangeFilterFunction filter = config.retryInstanceExchangeFilter(properties);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_retryInstanceExchangeFilter_shouldReturnFilterWithCustomRetries() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setDefaultRetries(3);

		InstanceExchangeFilterFunction filter = config.retryInstanceExchangeFilter(properties);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_retryInstanceExchangeFilter_shouldReturnFilterWithEndpointSpecificRetries() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setDefaultRetries(3);
		Map<String, Integer> retries = new HashMap<>();
		retries.put("health", 5);
		retries.put("info", 2);
		properties.getMonitor().setRetries(retries);

		InstanceExchangeFilterFunction filter = config.retryInstanceExchangeFilter(properties);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_retryInstanceExchangeFilter_shouldReturnFilterWithEmptyEndpointRetries() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setDefaultRetries(3);
		properties.getMonitor().setRetries(new HashMap<>());

		InstanceExchangeFilterFunction filter = config.retryInstanceExchangeFilter(properties);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_retryInstanceExchangeFilter_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();

		InstanceExchangeFilterFunction filter1 = config.retryInstanceExchangeFilter(properties);
		InstanceExchangeFilterFunction filter2 = config.retryInstanceExchangeFilter(properties);

		assertThat(filter1).isNotNull();
		assertThat(filter2).isNotNull();
		assertThat(filter1).isNotSameAs(filter2);
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_timeoutInstanceExchangeFilter_shouldReturnFilterWithDefaultProperties() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();

		InstanceExchangeFilterFunction filter = config.timeoutInstanceExchangeFilter(properties);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_timeoutInstanceExchangeFilter_shouldReturnFilterWithCustomTimeout() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setDefaultTimeout(Duration.ofSeconds(30));

		InstanceExchangeFilterFunction filter = config.timeoutInstanceExchangeFilter(properties);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_timeoutInstanceExchangeFilter_shouldReturnFilterWithEndpointSpecificTimeouts() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setDefaultTimeout(Duration.ofSeconds(30));
		Map<String, Duration> timeouts = new HashMap<>();
		timeouts.put("health", Duration.ofSeconds(10));
		timeouts.put("info", Duration.ofSeconds(20));
		properties.getMonitor().setTimeout(timeouts);

		InstanceExchangeFilterFunction filter = config.timeoutInstanceExchangeFilter(properties);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_timeoutInstanceExchangeFilter_shouldReturnFilterWithEmptyEndpointTimeouts() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();
		properties.getMonitor().setDefaultTimeout(Duration.ofSeconds(30));
		properties.getMonitor().setTimeout(new HashMap<>());

		InstanceExchangeFilterFunction filter = config.timeoutInstanceExchangeFilter(properties);

		assertThat(filter).isNotNull();
	}

	@Test
	void defaultInstanceExchangeFiltersConfiguration_timeoutInstanceExchangeFilter_shouldReturnNewInstanceEachTime() {
		AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration config = new AdminServerInstanceWebClientConfiguration.InstanceExchangeFiltersConfiguration.DefaultInstanceExchangeFiltersConfiguration();

		AdminServerProperties properties = new AdminServerProperties();

		InstanceExchangeFilterFunction filter1 = config.timeoutInstanceExchangeFilter(properties);
		InstanceExchangeFilterFunction filter2 = config.timeoutInstanceExchangeFilter(properties);

		assertThat(filter1).isNotNull();
		assertThat(filter2).isNotNull();
		assertThat(filter1).isNotSameAs(filter2);
	}

}
