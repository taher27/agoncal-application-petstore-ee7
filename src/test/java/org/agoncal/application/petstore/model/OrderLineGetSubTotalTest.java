
package org.agoncal.application.petstore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Objects;

class OrderLineGetSubTotalTest {

	@Test
	@DisplayName("getSubTotalWithValidInputs")
	@Tag("valid")
	void getSubTotalWithValidInputs() {
		OrderLine orderLine = new OrderLine(2, new Item(null, 10.0f, null, null, null));
		Float result = orderLine.getSubTotal();
		assertEquals(20.0f, result);
	}

	@Test
	@DisplayName("getSubTotalWithZeroQuantity")
	@Tag("boundary")
	void getSubTotalWithZeroQuantity() {
		OrderLine orderLine = new OrderLine(0, new Item(null, 10.0f, null, null, null));
		Float result = orderLine.getSubTotal();
		assertEquals(0.0f, result);
	}

	@Test
	@DisplayName("getSubTotalWithNullItem")
	@Tag("invalid")
	void getSubTotalWithNullItem() {
		OrderLine orderLine = new OrderLine(2, null);
		assertThrows(NullPointerException.class, orderLine::getSubTotal);
	}

	@ParameterizedTest
	@MethodSource("negativeQuantities")
	@DisplayName("getSubTotalWithNegativeQuantity")
	@Tag("boundary")
	void getSubTotalWithNegativeQuantity(int quantity, float expectedResult) {
		OrderLine orderLine = new OrderLine(quantity, new Item(null, 10.0f, null, null, null));
		Float result = orderLine.getSubTotal();
		assertEquals(expectedResult, result);
	}

	private static Stream<Arguments> negativeQuantities() {
		return Stream.of(Arguments.of(-1, -10.0f), Arguments.of(-5, -50.0f));
	}

}