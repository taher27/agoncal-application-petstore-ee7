
package org.agoncal.application.petstore.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

class ProductGetIdTest {

	@Test
	@Tag("valid")
	void getIdReturnsCorrectValue() {
		// Arrange
		Long expectedId = 1L;
		Product product = new Product();
		product.setId(expectedId);
		// Act
		Long actualId = product.getId();
		// Assert
		assertEquals(expectedId, actualId);
	}

	@Test
	@Tag("boundary")
	void getIdReturnsNullForUninitializedId() {
		// Arrange
		Product product = new Product();
		// Act
		Long actualId = product.getId();
		// Assert
		assertNull(actualId);
	}

	@Test
	@Tag("valid")
	void getIdReturnsCorrectValueAfterSetterCalled() {
		// Arrange
		Long expectedId = 2L;
		Product product = new Product();
		product.setId(expectedId);
		// Act
		Long actualId = product.getId();
		// Assert
		assertEquals(expectedId, actualId);
	}

}