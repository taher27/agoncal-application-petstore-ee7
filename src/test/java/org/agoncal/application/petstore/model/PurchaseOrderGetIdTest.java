
package org.agoncal.application.petstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import javax.persistence.*;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Tag("valid")
class PurchaseOrderGetIdTest {

	private EntityManagerFactory emf;

	private EntityManager em;

	private PurchaseOrder purchaseOrder;

	@BeforeEach
	void setUp() {
		emf = Persistence.createEntityManagerFactory("petstore");
		em = emf.createEntityManager();
	}

	@Test
	@Tag("integration")
	void getIdOfExistingPurchaseOrder() {
		em.getTransaction().begin();
		Customer customer = new Customer();
		CreditCard creditCard = new CreditCard();
		Address deliveryAddress = new Address();
		purchaseOrder = new PurchaseOrder(customer, creditCard, deliveryAddress);
		em.persist(purchaseOrder);
		em.getTransaction().commit();
		Long id = purchaseOrder.getId();
		assertNotNull(id);
	}

	@Test
	@Tag("boundary")
	void getIdOfNewPurchaseOrder() {
		purchaseOrder = new PurchaseOrder();
		Long id = purchaseOrder.getId();
		assertNull(id);
	}

	@Test
	@Tag("invalid")
	void getIdOfNullPurchaseOrder() {
		assertThrows(NullPointerException.class, () -> {
			PurchaseOrder nullPurchaseOrder = null;
			nullPurchaseOrder.getId();
		});
	}

	@Test
	@Tag("integration")
	void getIdAfterUpdatingPurchaseOrder() {
		em.getTransaction().begin();
		Customer customer = new Customer();
		CreditCard creditCard = new CreditCard();
		Address deliveryAddress = new Address();
		purchaseOrder = new PurchaseOrder(customer, creditCard, deliveryAddress);
		em.persist(purchaseOrder);
		em.getTransaction().commit();
		Long originalId = purchaseOrder.getId();
		em.getTransaction().begin();
		purchaseOrder.setTotal(100.0f);
		em.getTransaction().commit();
		Long updatedId = purchaseOrder.getId();
		assertNotNull(updatedId);
		assertEquals(originalId, updatedId);
	}

}