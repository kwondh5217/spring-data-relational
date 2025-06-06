/*
 * Copyright 2021-2025 the original author or authors.
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
package org.springframework.data.r2dbc.testing;

import org.springframework.data.r2dbc.mapping.OutboundRow;

/**
 * @author Mark Paluch
 */
public abstract class Assertions extends org.assertj.core.api.Assertions {

	private Assertions() {}

	/**
	 * Create assertion for {@link OutboundRow}.
	 *
	 * @param actual the actual value.
	 * @param <T> the type of the value contained in the {@link OutboundRow}.
	 * @return the created assertion object.
	 */
	public static OutboundRowAssert assertThat(OutboundRow actual) {
		return OutboundRowAssert.assertThat(actual);
	}
}
