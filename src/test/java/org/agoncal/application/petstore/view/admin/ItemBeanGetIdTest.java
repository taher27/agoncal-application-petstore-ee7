
package org.agoncal.application.petstore.view.admin;

import org.agoncal.application.petstore.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.agoncal.application.petstore.model.Product;
import org.agoncal.application.petstore.util.Loggable;

@ExtendWith(MockitoExtension.class)
class ItemBeanGetIdTest {

	@InjectMocks
	private ItemBean itemBean;

	@Test
	@Tag("valid")
	void getIdOfExistingItem() {
		// Arrange
		Long existingId = 1L;
		Item item = new Item();
		item.setId(existingId);
		itemBean.setItem(item);
		// Act
		Long actualId = itemBean.getId();
		// Assert
		Assertions.assertEquals(existingId, actualId);
	}

	@Test
	@Tag("valid")
	void getIdOfNewItem() {
		// Arrange
		Item newItem = new Item();
		itemBean.setItem(newItem);
		// Act
		Long actualId = itemBean.getId();
		// Assert
		Assertions.assertNull(actualId);
	}

	@Test
	@Tag("invalid")
	void getIdOfItemWithInvalidId() {
		// Arrange
		Long invalidId = -1L;
		Item item = new Item();
		item.setId(invalidId);
		itemBean.setItem(item);
		// Act
		Long actualId = itemBean.getId();
		// Assert
		Assertions.assertNull(actualId);
	}

	@Test
	@Tag("boundary")
	void getIdOfNullItem() {
		// Arrange
		itemBean.setItem(null);
		// Act and Assert
		Assertions.assertThrows(NullPointerException.class, () -> itemBean.getId());
	}

}