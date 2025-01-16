
package org.agoncal.application.petstore.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.*;

@ExtendWith(MockitoExtension.class)
class AddressGetStreet1Test {

	@Test
	@Tag("valid")
	void getStreet1WithNonNullValue() {
		// Arrange
		String expectedStreet1 = "123 Main St";
		Country country = new Country("US", "United States", "United States", "USA", "840");
		Address address = new Address(expectedStreet1, "City", "12345", country);
		// Act
		String actualStreet1 = address.getStreet1();
		// Assert
		assertEquals(expectedStreet1, actualStreet1);
	}

	@Test
	@Tag("valid")
	void getStreet1WithNullValue() {
		// Arrange
		Address address = new Address(null, "City", "12345",
				new Country("US", "United States", "United States", "USA", "840"));
		// Act
		String actualStreet1 = address.getStreet1();
		// Assert
		assertNull(actualStreet1);
	}

	@Test
	@Tag("valid")
	void getStreet1WithEmptyString() {
		// Arrange
		String emptyStreet1 = "";
		Address address = new Address(emptyStreet1, "City", "12345",
				new Country("US", "United States", "United States", "USA", "840"));
		// Act
		String actualStreet1 = address.getStreet1();
		// Assert
		assertEquals(emptyStreet1, actualStreet1);
	}

	@Test
	@Tag("valid")
	void getStreet1WithWhitespace() {
		// Arrange
		String street1WithWhitespace = "   123 Main St   ";
		Address address = new Address(street1WithWhitespace, "City", "12345",
				new Country("US", "United States", "United States", "USA", "840"));
		// Act
		String actualStreet1 = address.getStreet1();
		// Assert
		assertEquals(street1WithWhitespace, actualStreet1);
	}

}