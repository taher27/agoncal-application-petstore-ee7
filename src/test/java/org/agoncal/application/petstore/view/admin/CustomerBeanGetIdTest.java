
package org.agoncal.application.petstore.view.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
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
import org.agoncal.application.petstore.model.Customer;
import org.agoncal.application.petstore.util.Loggable;

@Tag("valid")
class CustomerBeanGetIdTest {

	private CustomerBean customerBean;

	@BeforeEach
	void setUp() {
		customerBean = new CustomerBean();
	}

	@Test
	@Tag("valid")
	void getIdReturnsNullWhenIdIsNull() {
		assertNull(customerBean.getId());
	}

	@Test
	@Tag("valid")
	void getIdReturnsCorrectValueWhenIdIsSet() {
		Long expectedId = 1L;
		customerBean.setId(expectedId);
		assertEquals(expectedId, customerBean.getId());
	}

	@Test
	@Tag("boundary")
	void getIdReturnsNullWhenObjectIsNull() {
		CustomerBean nullCustomerBean = null;
		assertNull(nullCustomerBean.getId());
	}

	@Test
	@Tag("valid")
	void getIdDoesNotModifyIdValue() {
		Long expectedId = 2L;
		customerBean.setId(expectedId);
		Long actualId = customerBean.getId();
		assertEquals(expectedId, actualId);
		assertEquals(expectedId, customerBean.getId());
	}

}