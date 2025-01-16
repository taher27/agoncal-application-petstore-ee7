
package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.agoncal.application.petstore.model.Item;
import org.agoncal.application.petstore.model.Product;
import org.agoncal.application.petstore.util.Loggable;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ExtendWith(MockitoExtension.class)
class CatalogServiceFindCategoryTest {

	@Mock
	private EntityManager em;

	@InjectMocks
	private CatalogService catalogService;

	private Category existingCategory;

	private Long validCategoryId;

	private Long invalidCategoryId;

	@BeforeEach
	void setUp() {
		existingCategory = new Category(1L, "ExistingCategory");
		validCategoryId = 1L;
		invalidCategoryId = 999L;
	}

	@Test
    @Tag("valid")
    void findExistingCategoryByValidId() {
        when(em.find(Category.class, validCategoryId)).thenReturn(existingCategory);
        Category result = catalogService.findCategory(validCategoryId);
        assertNotNull(result);
        assertEquals(existingCategory, result);
        verify(em, times(1)).find(Category.class, validCategoryId);
    }

	@Test
    @Tag("invalid")
    void findNonExistentCategoryByInvalidId() {
        when(em.find(Category.class, invalidCategoryId)).thenReturn(null);
        Category result = catalogService.findCategory(invalidCategoryId);
        assertNull(result);
        verify(em, times(1)).find(Category.class, invalidCategoryId);
    }

	@Test
	@Tag("boundary")
	void findCategoryWithNullId() {
		assertThrows(IllegalArgumentException.class, () -> catalogService.findCategory(null));
		verifyNoInteractions(em);
	}

	@Test
    @Tag("integration")
    void findCategoryWithConcurrentAccess() throws InterruptedException {
        when(em.find(Category.class, validCategoryId)).thenReturn(existingCategory);
        int numThreads = 10;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                Category result = catalogService.findCategory(validCategoryId);
                assertNotNull(result);
                assertEquals(existingCategory, result);
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        verify(em, times(numThreads)).find(Category.class, validCategoryId);
    }

}