
package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.exceptions.ValidationException;
import org.agoncal.application.petstore.model.*;
import org.agoncal.application.petstore.view.shopping.ShoppingCartItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.agoncal.application.petstore.util.Loggable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ExtendWith(MockitoExtension.class)
class PurchaseOrderServiceCreateOrderTest {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private PurchaseOrderService purchaseOrderService;

	@Test
	@Tag("valid")
	void createOrderWithValidInputs() {
		// Arrange
		Customer customer = new Customer();
		Address address = new Address();
		Country country = new Country();
		address.setCountry(country);
		customer.setHomeAddress(address);
		CreditCard creditCard = new CreditCard();
		List<ShoppingCartItem> cartItems = new ArrayList<>();
		cartItems.add(new ShoppingCartItem(1, new Item()));
		when(entityManager.merge(customer)).thenReturn(customer);
		when(entityManager.find(Country.class, customer.getHomeAddress().getCountry().getId())).thenReturn(country);
		// Act
		PurchaseOrder order = purchaseOrderService.createOrder(customer, creditCard, cartItems);
		// Assert
		assertNotNull(order);
		assertFalse(order.getOrderLines().isEmpty());
	}

	@Test
	@Tag("invalid")
	void createOrderWithEmptyShoppingCart() {
		// Arrange
		Customer customer = new Customer();
		CreditCard creditCard = new CreditCard();
		List<ShoppingCartItem> cartItems = new ArrayList<>();
		// Act & Assert
		assertThrows(ValidationException.class,
				() -> purchaseOrderService.createOrder(customer, creditCard, cartItems));
	}

	@Test
	@Tag("invalid")
	void createOrderWithNullShoppingCart() {
		// Arrange
		Customer customer = new Customer();
		CreditCard creditCard = new CreditCard();
		// Act & Assert
		assertThrows(ValidationException.class, () -> purchaseOrderService.createOrder(customer, creditCard, null));
	}

	@Test
	@Tag("invalid")
	void createOrderWithNullCustomer() {
		// Arrange
		CreditCard creditCard = new CreditCard();
		List<ShoppingCartItem> cartItems = new ArrayList<>();
		// Act & Assert
		assertThrows(NullPointerException.class, () -> purchaseOrderService.createOrder(null, creditCard, cartItems));
	}

	@Test
	@Tag("invalid")
	void createOrderWithNullCreditCard() {
		// Arrange
		Customer customer = new Customer();
		List<ShoppingCartItem> cartItems = new ArrayList<>();
		// Act & Assert
		assertThrows(NullPointerException.class, () -> purchaseOrderService.createOrder(customer, null, cartItems));
	}

	@Test
	@Tag("integration")
	void persistedPurchaseOrder() {
		// Arrange
		Customer customer = new Customer();
		Address address = new Address();
		Country country = new Country();
		address.setCountry(country);
		customer.setHomeAddress(address);
		CreditCard creditCard = new CreditCard();
		List<ShoppingCartItem> cartItems = new ArrayList<>();
		cartItems.add(new ShoppingCartItem(1, new Item()));
		when(entityManager.merge(customer)).thenReturn(customer);
		when(entityManager.find(Country.class, customer.getHomeAddress().getCountry().getId())).thenReturn(country);
		// Act
		PurchaseOrder order = purchaseOrderService.createOrder(customer, creditCard, cartItems);
		// Assert
		verify(entityManager, times(1)).persist(order);
	}

}