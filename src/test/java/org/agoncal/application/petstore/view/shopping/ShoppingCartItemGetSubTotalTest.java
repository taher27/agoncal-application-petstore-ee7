
package org.agoncal.application.petstore.view.shopping;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.agoncal.application.petstore.model.Item;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

class ShoppingCartItemGetSubTotalTest {

	@Test
	@Tag("valid")
	void calculateSubTotalForValidItemAndQuantity() {
		// Arrange
		Item item = new Item();
		item.setUnitCost(10.0F);
		ShoppingCartItem cartItem = new ShoppingCartItem(item, 3);
		// Act
		Float subtotal = cartItem.getSubTotal();
		// Assert
		assertEquals(30.0F, subtotal, 0.01);
	}

	@Test
	@Tag("boundary")
	void calculateSubTotalWhenQuantityIsZero() {
		// Arrange
		Item item = new Item();
		item.setUnitCost(10.0F);
		ShoppingCartItem cartItem = new ShoppingCartItem(item, 0);
		// Act
		Float subtotal = cartItem.getSubTotal();
		// Assert
		assertEquals(0.0F, subtotal, 0.01);
	}

	@Test
	@Tag("invalid")
	void calculateSubTotalWhenItemIsNull() {
		// Arrange
		ShoppingCartItem cartItem = new ShoppingCartItem(null, 3);
		// Act
		Float subtotal = cartItem.getSubTotal();
		// Assert
		assertEquals(0.0F, subtotal, 0.01);
	}

	@Test
	@Tag("invalid")
	void calculateSubTotalWhenQuantityIsNegative() {
		// Arrange
		Item item = new Item();
		item.setUnitCost(10.0F);
		ShoppingCartItem cartItem = new ShoppingCartItem(item, -3);
		// Act
		Float subtotal = cartItem.getSubTotal();
		// Assert
		assertEquals(0.0F, subtotal, 0.01);
	}

	@Test
	void equalsContract() {
		EqualsVerifier.forClass(ShoppingCartItem.class).verify();
	}

}