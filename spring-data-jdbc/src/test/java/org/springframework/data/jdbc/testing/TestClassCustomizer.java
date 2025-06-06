/*
 * Copyright 2023-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.jdbc.testing;

import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.MergedContextConfiguration;

/**
 * {@link ContextCustomizer} registering {@link TestClass}.
 *
 * @author Mark Paluch
 */
class TestClassCustomizer implements ContextCustomizer {

	private final Class<?> testClass;

	public TestClassCustomizer(Class<?> testClass) {
		this.testClass = testClass;
	}

	@Override
	public void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
		context.getBeanFactory().registerSingleton(TestClass.class.getSimpleName(), TestClass.of(testClass));
	}

	static class TestClassCustomizerFactory implements ContextCustomizerFactory {

		@Override
		public ContextCustomizer createContextCustomizer(Class<?> testClass,
				List<ContextConfigurationAttributes> configAttributes) {
			return new TestClassCustomizer(testClass);
		}

	}
}
