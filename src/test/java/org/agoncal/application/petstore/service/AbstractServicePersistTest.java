
package org.agoncal.application.petstore.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import org.agoncal.application.petstore.util.Loggable;

@ExtendWith(MockitoExtension.class)
class AbstractServicePersistTest {

	@Mock
	private EntityManager entityManager;

	private AbstractService<TestEntity> abstractService;

	@BeforeEach
	void setUp() {
		abstractService = new AbstractService<>(TestEntity.class);
		abstractService.entityManager = entityManager;
	}

	@Test
	@Tag("valid")
	void testPersistValidEntity() {
		// Arrange
		TestEntity entity = new TestEntity();
		// Act
		TestEntity persistedEntity = abstractService.persist(entity);
		// Assert
		assertNotNull(persistedEntity);
		verify(entityManager, times(1)).persist(entity);
	}

	@Test
	@Tag("invalid")
	void testPersistNullEntity() {
		// Arrange
		TestEntity entity = null;
		// Act & Assert
		assertThrows(NullPointerException.class, () -> abstractService.persist(entity));
		verify(entityManager, never()).persist(any());
	}

	@Test
	@Tag("valid")
	void testPersistDetachedEntity() {
		// Arrange
		TestEntity entity = new TestEntity();
		// Act
		TestEntity persistedEntity = abstractService.persist(entity);
		// Assert
		assertNotNull(persistedEntity);
		verify(entityManager, times(1)).persist(entity);
	}

	@Test
	@Tag("valid")
	void testPersistExistingEntity() {
		// Arrange
		TestEntity entity = new TestEntity();
		when(entityManager.merge(entity)).thenReturn(entity);
		// Act
		TestEntity persistedEntity = abstractService.persist(entity);
		// Assert
		assertNotNull(persistedEntity);
		verify(entityManager, times(1)).persist(entity);
		verify(entityManager, times(1)).merge(entity);
	}

	private static class TestEntity {

		// Dummy entity class for testing

	}

}