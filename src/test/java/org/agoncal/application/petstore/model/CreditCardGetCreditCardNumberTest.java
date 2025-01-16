
package org.agoncal.application.petstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreditCardGetCreditCardNumberTest {

	private CreditCard creditCard;

	@BeforeEach
	void setUp() {
		creditCard = new CreditCard();
	}

	@Test
	@Tag("valid")
	void getCreditCardNumberReturnsCorrectValue() {
		String expectedCreditCardNumber = "1234567890123456";
		creditCard = new CreditCard(expectedCreditCardNumber, CreditCardType.VISA, "12/25");
		String actualCreditCardNumber = creditCard.getCreditCardNumber();
		assertEquals(expectedCreditCardNumber, actualCreditCardNumber);
	}

	@Test
	@Tag("valid")
	void getCreditCardNumberReturnsNullWhenNotSet() {
		String actualCreditCardNumber = creditCard.getCreditCardNumber();
		assertNull(actualCreditCardNumber);
	}

	@Test
	@Tag("valid")
	void getCreditCardNumberReturnsCorrectValueAfterSetting() {
		String expectedCreditCardNumber = "9876543210987654";
		creditCard.setCreditCardNumber(expectedCreditCardNumber);
		String actualCreditCardNumber = creditCard.getCreditCardNumber();
		assertEquals(expectedCreditCardNumber, actualCreditCardNumber);
	}

}