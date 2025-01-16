
package org.agoncal.application.petstore.view.admin;

import org.agoncal.application.petstore.model.OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
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
import org.agoncal.application.petstore.model.Item;
import org.agoncal.application.petstore.util.Loggable;

class OrderLineBeanGetIdTest {

	private OrderLineBean orderLineBean;

	@BeforeEach
	void setUp() {
		orderLineBean = new OrderLineBean();
	}

	@Test
	@Tag("valid")
	void shouldReturnNullWhenIdIsNull() {
		assertNull(orderLineBean.getId());
	}

	@Test
	@Tag("valid")
	void shouldReturnCorrectValueWhenIdIsNotNull() {
		Long expectedId = 1L;
		orderLineBean.setId(expectedId);
		assertEquals(expectedId, orderLineBean.getId());
	}

	@Test
	@Tag("boundary")
	void shouldHandleNullOrderLineBeanInstance() {
		OrderLineBean nullOrderLineBean = null;
		assertThrows(NullPointerException.class, nullOrderLineBean::getId);
	}

	@ParameterizedTest
	@ValueSource(longs = { Long.MAX_VALUE, Long.MIN_VALUE, 0L })
	@Tag("boundary")
	void shouldHandleDifferentLongIdValues(Long id) {
		orderLineBean.setId(id);
		assertEquals(id, orderLineBean.getId());
	}

	@ParameterizedTest
	@ValueSource(ints = { Integer.MAX_VALUE, Integer.MIN_VALUE, 0 })
	@Tag("invalid")
	void shouldThrowExceptionWhenIdIsNotLong(int invalidId) {
		assertThrows(ClassCastException.class, () -> {
			Long longId = (long) invalidId;
			orderLineBean.setId(longId);
			orderLineBean.getId();
		});
	}

}