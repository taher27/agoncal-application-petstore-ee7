
package org.agoncal.application.petstore.view.admin;

import org.agoncal.application.petstore.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
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
import org.agoncal.application.petstore.util.Loggable;

class CategoryBeanGetIdTest {

	private CategoryBean categoryBean;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		categoryBean = new CategoryBean();
	}

	@Test
	@Tag("valid")
	void testGetIdWithNonNullValue() {
		Long expectedId = 1L;
		categoryBean.setId(expectedId);
		Long actualId = categoryBean.getId();
		assertEquals(expectedId, actualId);
	}

	@Test
	@Tag("valid")
	void testGetIdWithNullValue() {
		categoryBean.setId(null);
		Long actualId = categoryBean.getId();
		assertNull(actualId);
	}

	@Test
	@Tag("valid")
	void testGetIdOnNewInstance() {
		Long actualId = categoryBean.getId();
		assertNull(actualId);
	}

	@Test
	@Tag("boundary")
	void testGetIdConcurrently() throws InterruptedException {
		Long expectedId = 1L;
		categoryBean.setId(expectedId);
		int numThreads = 10;
		Thread[] threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new Thread(() -> {
				Long actualId = categoryBean.getId();
				assertEquals(expectedId, actualId);
			});
			threads[i].start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
	}

}