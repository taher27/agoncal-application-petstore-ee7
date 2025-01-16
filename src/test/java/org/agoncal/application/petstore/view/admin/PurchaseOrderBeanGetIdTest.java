
package org.agoncal.application.petstore.view.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.agoncal.application.petstore.model.PurchaseOrder;
import org.agoncal.application.petstore.util.Loggable;

@Tag("valid")
class PurchaseOrderBeanGetIdTest {

	private PurchaseOrderBean purchaseOrderBean;

	@Mock
	private EntityManager entityManager;

	@Mock
	private TypedQuery<PurchaseOrder> query;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		purchaseOrderBean = new PurchaseOrderBean();
		purchaseOrderBean.entityManager = entityManager;
	}

	@Test
	@Tag("valid")
	void getIdOfNewPurchaseOrder() {
		purchaseOrderBean.setPurchaseOrder(new PurchaseOrder());
		Long id = purchaseOrderBean.getId();
		assertNull(id, "ID should be null for a new PurchaseOrder");
	}

	@Test
	@Tag("valid")
	void getIdOfExistingPurchaseOrder() {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setId(1L);
		purchaseOrderBean.setPurchaseOrder(purchaseOrder);
		Long id = purchaseOrderBean.getId();
		assertEquals(1L, id, "ID should match the existing PurchaseOrder's ID");
	}

	@Test
	@Tag("valid")
	void getIdAfterUpdatingPurchaseOrder() {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setId(1L);
		purchaseOrderBean.setPurchaseOrder(purchaseOrder);
		when(entityManager.merge(purchaseOrder)).thenReturn(purchaseOrder);
		purchaseOrderBean.update();
		Long id = purchaseOrderBean.getId();
		assertEquals(1L, id, "ID should match the updated PurchaseOrder's ID");
	}

}