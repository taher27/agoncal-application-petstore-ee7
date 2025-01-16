
package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Item;
import org.agoncal.application.petstore.model.OrderLine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import java.io.Serializable;
import java.util.ArrayList;
import org.agoncal.application.petstore.util.Loggable;

@ExtendWith(MockitoExtension.class)
class OrderLineServiceGetSearchPredicatesTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private Root<OrderLine> root;

	private OrderLineService orderLineService;

	@BeforeEach
	void setUp() {
		orderLineService = new OrderLineService();
		orderLineService.entityManager = entityManager;
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
	}

	@AfterEach
	void tearDown() {
		orderLineService = null;
	}

	@Test
	@Tag("valid")
	void testGetSearchPredicatesWithQuantityAndItem() {
		OrderLine example = new OrderLine();
		example.setQuantity(10);
		example.setItem(new Item());
		when(criteriaBuilder.equal(root.get("quantity"), 10)).thenReturn(new Predicate() {
		});
		when(criteriaBuilder.equal(root.get("item"), example.getItem())).thenReturn(new Predicate() {
		});
		Predicate[] predicates = orderLineService.getSearchPredicates(root, example);
		assertNotNull(predicates);
		assertEquals(2, predicates.length);
	}

	@Test
	@Tag("valid")
	void testGetSearchPredicatesWithOnlyQuantity() {
		OrderLine example = new OrderLine();
		example.setQuantity(5);
		when(criteriaBuilder.equal(root.get("quantity"), 5)).thenReturn(new Predicate() {
		});
		Predicate[] predicates = orderLineService.getSearchPredicates(root, example);
		assertNotNull(predicates);
		assertEquals(1, predicates.length);
	}

	@Test
	@Tag("valid")
	void testGetSearchPredicatesWithOnlyItem() {
		OrderLine example = new OrderLine();
		example.setItem(new Item());
		when(criteriaBuilder.equal(root.get("item"), example.getItem())).thenReturn(new Predicate() {
		});
		Predicate[] predicates = orderLineService.getSearchPredicates(root, example);
		assertNotNull(predicates);
		assertEquals(1, predicates.length);
	}

	@Test
	@Tag("valid")
	void testGetSearchPredicatesWithNullQuantityAndItem() {
		OrderLine example = new OrderLine();
		Predicate[] predicates = orderLineService.getSearchPredicates(root, example);
		assertNotNull(predicates);
		assertEquals(0, predicates.length);
	}

	@Test
	@Tag("boundary")
	void testGetSearchPredicatesWithZeroQuantity() {
		OrderLine example = new OrderLine();
		example.setQuantity(0);
		example.setItem(new Item());
		when(criteriaBuilder.equal(root.get("item"), example.getItem())).thenReturn(new Predicate() {
		});
		Predicate[] predicates = orderLineService.getSearchPredicates(root, example);
		assertNotNull(predicates);
		assertEquals(1, predicates.length);
	}

	@Test
	@Tag("invalid")
	void testGetSearchPredicatesWithNullExample() {
		Predicate[] predicates = orderLineService.getSearchPredicates(root, null);
		assertNotNull(predicates);
		assertEquals(0, predicates.length);
	}

}