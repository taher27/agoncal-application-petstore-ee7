
package org.agoncal.application.petstore.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

class CountryGetIdTest {

	@Test
	@Tag("valid")
	void getIdReturnsCorrectValue() {
		// Arrange
		Country country = new Country();
		Long expectedId = 123L;
		country.setId(expectedId);
		// Act
		Long actualId = country.getId();
		// Assert
		assertEquals(expectedId, actualId);
	}

	@Test
	@Tag("valid")
	void getIdReturnsNullForUninitializedId() {
		// Arrange
		Country country = new Country();
		// Act
		Long actualId = country.getId();
		// Assert
		assertNull(actualId);
	}

	@Test
	@Tag("valid")
	void getIdReturnsNullAfterSettingIdToNull() {
		// Arrange
		Country country = new Country();
		country.setId(123L);
		// Act
		country.setId(null);
		Long actualId = country.getId();
		// Assert
		assertNull(actualId);
	}

	@Test
	@Tag("valid")
	void getIdReturnsCorrectValueAfterSettingIdMultipleTimes() {
		// Arrange
		Country country = new Country();
		country.setId(123L);
		// Act
		country.setId(456L);
		country.setId(789L);
		Long actualId = country.getId();
		// Assert
		assertEquals(789L, actualId);
	}

}