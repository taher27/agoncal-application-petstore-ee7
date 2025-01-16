
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
import java.io.Serializable;
import java.util.Objects;

class CategoryGetIdTest {

	@Test
	@Tag("valid")
	void getIdWhenValid() {
		// Arrange
		Long expectedId = 1L;
		Category category = new Category();
		category.setId(expectedId);
		// Act
		Long actualId = category.getId();
		// Assert
		assertEquals(expectedId, actualId);
	}

	@Test
	@Tag("valid")
	void getIdWhenNull() {
		// Arrange
		Category category = new Category();
		// Act
		Long actualId = category.getId();
		// Assert
		assertNull(actualId);
	}

	@Test
	@Tag("valid")
	void getIdAfterModification() {
		// Arrange
		Long initialId = 1L;
		Long newId = 2L;
		Category category = new Category();
		category.setId(initialId);
		// Act
		category.setId(newId);
		Long actualId = category.getId();
		// Assert
		assertEquals(newId, actualId);
	}

	@Test
	@Tag("boundary")
	void getIdAfterAssigningNull() {
		// Arrange
		Long initialId = 1L;
		Category category = new Category();
		category.setId(initialId);
		// Act
		category.setId(null);
		Long actualId = category.getId();
		// Assert
		assertNull(actualId);
	}

}