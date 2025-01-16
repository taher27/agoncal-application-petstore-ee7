
package org.agoncal.application.petstore.view.admin;

import org.agoncal.application.petstore.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.agoncal.application.petstore.model.Category;
import org.agoncal.application.petstore.util.Loggable;

@Tag("valid")
class ProductBeanGetIdTest {

	@Test
	void getIdWhenProductExists() {
		// Arrange
		Product product = new Product();
		Long expectedId = 1L;
		product.setId(expectedId);
		ProductBean productBean = new ProductBean();
		productBean.setProduct(product);
		// Act
		Long actualId = productBean.getId();
		// Assert
		assertEquals(expectedId, actualId, "The retrieved ID should match the expected value");
	}

	@Test
	@Tag("invalid")
	void getIdWhenProductIsNull() {
		// Arrange
		ProductBean productBean = new ProductBean();
		productBean.setProduct(null);
		// Act
		Long actualId = productBean.getId();
		// Assert
		assertNull(actualId, "The retrieved ID should be null when the Product object is null");
	}

	@Test
	@Tag("boundary")
	void getIdWhenIdIsNull() {
		// Arrange
		Product product = new Product();
		product.setId(null);
		ProductBean productBean = new ProductBean();
		productBean.setProduct(product);
		// Act
		Long actualId = productBean.getId();
		// Assert
		assertNull(actualId, "The retrieved ID should be null when the Product ID is null");
	}

	@Test
	@Tag("boundary")
	void getIdWhenIdIsNegative() {
		// Arrange
		Product product = new Product();
		Long negativeId = -1L;
		product.setId(negativeId);
		ProductBean productBean = new ProductBean();
		productBean.setProduct(product);
		// Act
		Long actualId = productBean.getId();
		// Assert
		assertEquals(negativeId, actualId, "The retrieved ID should match the negative ID value");
	}

}