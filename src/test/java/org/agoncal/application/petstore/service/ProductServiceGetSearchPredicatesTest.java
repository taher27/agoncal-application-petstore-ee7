
package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Category;
import org.agoncal.application.petstore.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import java.io.Serializable;
import java.util.ArrayList;
import org.agoncal.application.petstore.util.Loggable;

class ProductServiceGetSearchPredicatesTest {

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private Root<Product> root;

	private ProductService productService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		productService = new ProductService();
	}

	@ParameterizedTest(name = "{2}")
	@MethodSource("searchPredicatesTestCases")
	@Tag("valid")
	void testGetSearchPredicates(String name, String description, Category category, List<Predicate> expectedPredicates,
			String testCase) {
		Product example = new Product();
		example.setName(name);
		example.setDescription(description);
		example.setCategory(category);
		when(criteriaBuilder.lower(root.get("name"))).thenReturn(criteriaBuilder.lower(root.get("name")));
		when(criteriaBuilder.lower(root.get("description"))).thenReturn(criteriaBuilder.lower(root.get("description")));
		Predicate[] actualPredicates = productService.getSearchPredicates(root, example);
		assertEquals(expectedPredicates.size(), actualPredicates.length, testCase);
		for (Predicate expectedPredicate : expectedPredicates) {
			assertTrue(List.of(actualPredicates).contains(expectedPredicate), testCase);
		}
	}

	private static Stream<Arguments> searchPredicatesTestCases() {
		return Stream.of(
				Arguments.of("Product A", "", null, List.of(predicateBuilder("name", "%product a%")),
						"Scenario 1: Search for products with a non-empty name"),
				Arguments.of("", "Product Description", null,
						List.of(predicateBuilder("description", "%product description%")),
						"Scenario 3: Search for products with a non-empty description"),
				Arguments.of("Product A", "Product Description", new Category(),
						List.of(predicateBuilder("name", "%product a%"),
								predicateBuilder("description", "%product description%"),
								predicateBuilder("category", new Category())),
						"Scenario 7: Search for products with all criteria"),
				Arguments.of("", "", null, List.of(), "Scenario 6: Search for products without any criteria"),
				Arguments.of("", "Product Description", new Category(),
						List.of(predicateBuilder("description", "%product description%"),
								predicateBuilder("category", new Category())),
						"Scenario 4: Search for products with an empty name"),
				Arguments.of("Product A", "", new Category(),
						List.of(predicateBuilder("name", "%product a%"), predicateBuilder("category", new Category())),
						"Scenario 5: Search for products with a specific category"),
				Arguments.of("", "", new Category(), List.of(predicateBuilder("category", new Category())),
						"Scenario 8: Search for products with null category"));
	}

	private static Predicate predicateBuilder(String field, Object value) {
		return new Predicate() {
			@Override
			public boolean isCompound() {
				return false;
			}

			@Override
			public BooleanOperator getOperator() {
				return null;
			}

			@Override
			public List<Expression<Boolean>> getExpressionRoots() {
				return null;
			}

			@Override
			public String toString() {
				return field + " = " + value;
			}
		};
	}

}