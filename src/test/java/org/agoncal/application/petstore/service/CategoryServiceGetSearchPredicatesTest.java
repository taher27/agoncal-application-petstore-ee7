
package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import java.io.Serializable;
import java.util.ArrayList;
import org.agoncal.application.petstore.util.Loggable;

@DisplayName("CategoryService.getSearchPredicates() method tests")
class CategoryServiceGetSearchPredicatesTest {

	private CategoryService categoryService;

	private CriteriaBuilder criteriaBuilder;

	private Root<Category> root;

	@BeforeEach
	void setUp() {
		categoryService = new CategoryService();
		criteriaBuilder = mock(CriteriaBuilder.class);
		root = mock(Root.class);
	}

	@Test
	@Tag("valid")
	@DisplayName("Test for a null category object")
	void getNullCategoryScenario() {
		Predicate[] result = categoryService.getSearchPredicates(root, null);
		assertArrayEquals(new Predicate[0], result, "Expected an empty array for null category");
	}

	@ParameterizedTest
	@MethodSource("provideEmptyStrings")
	@Tag("valid")
	@DisplayName("Test for an empty category name and description")
	void getEmptyCategoryNameAndDescriptionScenario(String name, String description) {
		Category category = new Category(name, description);
		Predicate[] result = categoryService.getSearchPredicates(root, category);
		assertArrayEquals(new Predicate[0], result, "Expected an empty array for empty category name and description");
	}

	private static Stream<Arguments> provideEmptyStrings() {
		return Stream.of(Arguments.arguments("", ""), Arguments.arguments(" ", " "));
	}

	@Test
	@Tag("valid")
	@DisplayName("Test for a non-empty category name")
	void getNonEmptyCategoryNameScenario() {
		String name = "Category Name";
		Category category = new Category(name, "");
		when(criteriaBuilder.lower(root.<String>get("name")))
			.thenReturn(criteriaBuilder.lower(mock(Root.class).get("name")));
		Predicate[] result = categoryService.getSearchPredicates(root, category);
		assertEquals(1, result.length, "Expected an array with one predicate for non-empty category name");
	}

	@Test
	@Tag("valid")
	@DisplayName("Test for a non-empty category description")
	void getNonEmptyCategoryDescriptionScenario() {
		String description = "Category Description";
		Category category = new Category("", description);
		when(criteriaBuilder.lower(root.<String>get("description")))
			.thenReturn(criteriaBuilder.lower(mock(Root.class).get("description")));
		Predicate[] result = categoryService.getSearchPredicates(root, category);
		assertEquals(1, result.length, "Expected an array with one predicate for non-empty category description");
	}

	@Test
	@Tag("valid")
	@DisplayName("Test for non-empty category name and description")
	void getNonEmptyCategoryNameAndDescriptionScenario() {
		String name = "Category Name";
		String description = "Category Description";
		Category category = new Category(name, description);
		when(criteriaBuilder.lower(root.<String>get("name")))
			.thenReturn(criteriaBuilder.lower(mock(Root.class).get("name")));
		when(criteriaBuilder.lower(root.<String>get("description")))
			.thenReturn(criteriaBuilder.lower(mock(Root.class).get("description")));
		Predicate[] result = categoryService.getSearchPredicates(root, category);
		assertEquals(2, result.length,
				"Expected an array with two predicates for non-empty category name and description");
	}

	@ParameterizedTest
	@MethodSource("provideSpecialCharacters")
	@Tag("boundary")
	@DisplayName("Test for edge cases with special characters")
	void getSpecialCharactersScenario(String name, String description) {
		Category category = new Category(name, description);
		when(criteriaBuilder.lower(root.<String>get("name")))
			.thenReturn(criteriaBuilder.lower(mock(Root.class).get("name")));
		when(criteriaBuilder.lower(root.<String>get("description")))
			.thenReturn(criteriaBuilder.lower(mock(Root.class).get("description")));
		Predicate[] result = categoryService.getSearchPredicates(root, category);
		int expectedPredicateCount = (name.isEmpty() && description.isEmpty()) ? 0
				: (name.isEmpty() || description.isEmpty()) ? 1 : 2;
		assertEquals(expectedPredicateCount, result.length,
				"Expected correct number of predicates for special characters");
	}

	private static Stream<Arguments> provideSpecialCharacters() {
		return Stream.of(Arguments.arguments("!@#$%^&*()", ""), Arguments.arguments("", "-+=[]{|}\\:;'\"<>?/~"),
				Arguments.arguments("!@#$%^&*()", "-+=[]{|}\\:;'\"<>?/~"));
	}

	@ParameterizedTest
	@MethodSource("provideLargeInputStrings")
	@Tag("boundary")
	@DisplayName("Test for boundary cases with large input strings")
	void getLargeInputStringsScenario(String name, String description) {
		Category category = new Category(name, description);
		when(criteriaBuilder.lower(root.<String>get("name")))
			.thenReturn(criteriaBuilder.lower(mock(Root.class).get("name")));
		when(criteriaBuilder.lower(root.<String>get("description")))
			.thenReturn(criteriaBuilder.lower(mock(Root.class).get("description")));
		Predicate[] result = categoryService.getSearchPredicates(root, category);
		int expectedPredicateCount = (name.isEmpty() && description.isEmpty()) ? 0
				: (name.isEmpty() || description.isEmpty()) ? 1 : 2;
		assertEquals(expectedPredicateCount, result.length,
				"Expected correct number of predicates for large input strings");
	}

	private static Stream<Arguments> provideLargeInputStrings() {
		String longString = "a".repeat(10000);
		return Stream.of(Arguments.arguments(longString, ""), Arguments.arguments("", longString),
				Arguments.arguments(longString, longString));
	}

}