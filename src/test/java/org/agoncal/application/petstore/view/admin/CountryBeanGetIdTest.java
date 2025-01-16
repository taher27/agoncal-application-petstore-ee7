
package org.agoncal.application.petstore.view.admin;

import org.agoncal.application.petstore.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.faces.context.FacesContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
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
import org.agoncal.application.petstore.util.Loggable;

class CountryBeanGetIdTest {

	private CountryBean countryBean;

	@Mock
	private FacesContext facesContext;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		countryBean = new CountryBean();
	}

	@Test
	@Tag("valid")
	void getIdWhenIdIsNull() {
		countryBean.setId(null);
		assertNull(countryBean.getId());
	}

	@Test
	@Tag("valid")
	void getIdWhenIdIsNonNull() {
		Long expectedId = 123L;
		countryBean.setId(expectedId);
		assertEquals(expectedId, countryBean.getId());
	}

	@Test
	@Tag("valid")
	void getIdAfterSettingIdToDifferentValue() {
		Long initialId = 123L;
		Long newId = 456L;
		countryBean.setId(initialId);
		countryBean.setId(newId);
		assertEquals(newId, countryBean.getId());
	}

	@Test
	@Tag("valid")
	void getIdWithMultipleInstances() {
		CountryBean countryBean1 = new CountryBean();
		countryBean1.setId(123L);
		CountryBean countryBean2 = new CountryBean();
		countryBean2.setId(456L);
		assertEquals(123L, countryBean1.getId());
		assertEquals(456L, countryBean2.getId());
	}

	@Test
	@Tag("valid")
	void getIdAfterCallingOtherMethods() {
		Long expectedId = 789L;
		countryBean.setId(expectedId);
		countryBean.create();
		countryBean.retrieve();
		assertEquals(expectedId, countryBean.getId());
	}

}