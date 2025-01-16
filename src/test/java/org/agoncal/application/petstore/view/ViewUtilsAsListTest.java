
package org.agoncal.application.petstore.view;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import javax.persistence.Id;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class ViewUtilsAsListTest {

	@Test
	@Tag("valid")
	void testAsListWithNonEmptyCollection() {
		Collection<String> collection = Arrays.asList("apple", "banana", "cherry");
		List<String> list = ViewUtils.asList(collection);
		assertNotNull(list);
		assertEquals(3, list.size());
		assertTrue(list.containsAll(collection));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@Tag("boundary")
	void testAsListWithNullOrEmptyCollection(Collection<String> collection) {
		List<String> list = ViewUtils.asList(collection);
		if (collection == null) {
			assertNull(list);
		}
		else {
			assertNotNull(list);
			assertTrue(list.isEmpty());
		}
	}

	@Test
	@Tag("boundary")
	void testAsListWithCollectionContainingNullElements() {
		Collection<String> collection = new ArrayList<>(Arrays.asList("apple", null, "cherry"));
		List<String> list = ViewUtils.asList(collection);
		assertNotNull(list);
		assertEquals(3, list.size());
		assertNull(list.get(1));
	}

	@Test
	@Tag("invalid")
	void testAsListWithInvalidCollection() {
		Collection<String> invalidCollection = new InvalidCollection<>();
		List<String> list = ViewUtils.asList(invalidCollection);
		assertNull(list);
	}

	private static class InvalidCollection<T> extends ArrayList<T> {

		@Override
		public int size() {
			throw new RuntimeException("Invalid collection");
		}

	}

}