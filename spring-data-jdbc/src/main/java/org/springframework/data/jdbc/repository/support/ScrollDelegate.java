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
package org.springframework.data.jdbc.repository.support;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.relational.core.query.Query;
import org.springframework.util.Assert;

/**
 * Delegate to run {@link ScrollPosition scroll queries} and create result {@link Window}.
 *
 * @author Mark Paluch
 * @since 3.1.4
 */
public class ScrollDelegate {

	/**
	 * Run the {@link Query} and return a scroll {@link Window}.
	 *
	 * @param query must not be {@literal null}.
	 * @param scrollPosition must not be {@literal null}.
	 * @return the scroll {@link Window}.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Window<T> scroll(Query query, Function<Query, List<T>> queryFunction,
			ScrollPosition scrollPosition) {

		Assert.notNull(scrollPosition, "ScrollPosition must not be null");

		int limit = query.getLimit();
		if (limit > 0 && limit != Integer.MAX_VALUE) {
			query = query.limit(limit + 1);
		}

		List<T> result = queryFunction.apply(query);

		if (scrollPosition instanceof OffsetScrollPosition offset) {
			return createWindow(result, limit, offset.positionFunction());
		}

		throw new UnsupportedOperationException("ScrollPosition " + scrollPosition + " not supported");
	}

	private static <T> Window<T> createWindow(List<T> result, int limit,
			IntFunction<? extends ScrollPosition> positionFunction) {
		return Window.from(getFirst(limit, result), positionFunction, hasMoreElements(result, limit));
	}

	private static boolean hasMoreElements(List<?> result, int limit) {
		return !result.isEmpty() && result.size() > limit;
	}

	/**
	 * Return the first {@code count} items from the list.
	 *
	 * @param count the number of first elements to be included in the returned list.
	 * @param list must not be {@literal null}
	 * @return the returned sublist if the {@code list} is greater {@code count}.
	 * @param <T> the element type of the lists.
	 */
	public static <T> List<T> getFirst(int count, List<T> list) {

		if (count > 0 && list.size() > count) {
			return list.subList(0, count);
		}

		return list;
	}

}
