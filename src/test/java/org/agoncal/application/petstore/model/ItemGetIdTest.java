
package org.agoncal.application.petstore.model;

import org.agoncal.application.petstore.constraints.NotEmpty;
import org.agoncal.application.petstore.constraints.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

@XmlRootElement
@Entity
class ItemGetIdTest {

	@Test
	@Tag("valid")
	void getIdReturnsCorrectValue() {
		// Arrange
		Long expectedId = 1L;
		Item item = new Item();
		item.setId(expectedId);
		// Act
		Long actualId = item.getId();
		// Assert
		assertEquals(expectedId, actualId);
	}

	@Test
	@Tag("valid")
	void getIdReturnsNullForUninitializedItem() {
		// Arrange
		Item item = new Item();
		// Act
		Long actualId = item.getId();
		// Assert
		assertNull(actualId);
	}

	@Test
	@Tag("valid")
	void getIdReturnsConsistentValueAfterSettingId() {
		// Arrange
		Long expectedId = 2L;
		Item item = new Item();
		item.setId(expectedId);
		// Act
		Long actualId1 = item.getId();
		Long actualId2 = item.getId();
		Long actualId3 = item.getId();
		// Assert
		assertEquals(expectedId, actualId1);
		assertEquals(actualId1, actualId2);
		assertEquals(actualId2, actualId3);
	}

}