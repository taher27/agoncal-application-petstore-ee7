
package org.agoncal.application.petstore.view.shopping;

import org.agoncal.application.petstore.model.Product;
import org.agoncal.application.petstore.service.CatalogService;
import org.agoncal.application.petstore.view.AbstractBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import org.agoncal.application.petstore.model.Item;
import org.agoncal.application.petstore.util.Loggable;
import org.agoncal.application.petstore.view.CatchException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

class CatalogBeanDoFindProductsTest {

	private CatalogBean catalogBean;

	@Mock
	private CatalogService catalogService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		catalogBean = new CatalogBean();
		catalogBean.catalogService = catalogService;
	}

	@Test
	@Tag("valid")
	void findProductsForValidCategoryName() {
		// Arrange
		String validCategoryName = "Dogs";
		catalogBean.setCategoryName(validCategoryName);
		List<Product> expectedProducts = new ArrayList<>();
		expectedProducts.add(new Product());
		when(catalogService.findProducts(validCategoryName)).thenReturn(expectedProducts);
		// Act
		String navigationOutcome = catalogBean.doFindProducts();
		// Assert
		assertNotNull(catalogBean.getProducts());
		assertEquals(expectedProducts, catalogBean.getProducts());
		assertEquals("showproducts.faces", navigationOutcome);
	}

	@Test
	@Tag("valid")
	void findProductsForValidCategoryWithNoResults() {
		// Arrange
		String validCategoryName = "Birds";
		catalogBean.setCategoryName(validCategoryName);
		when(catalogService.findProducts(validCategoryName)).thenReturn(new ArrayList<>());
		// Act
		String navigationOutcome = catalogBean.doFindProducts();
		// Assert
		assertNotNull(catalogBean.getProducts());
		assertTrue(catalogBean.getProducts().isEmpty());
		assertEquals("showproducts.faces", navigationOutcome);
	}

	@Test
	@Tag("invalid")
	void findProductsForInvalidCategoryName() {
		// Arrange
		catalogBean.setCategoryName(null);
		// Act
		String navigationOutcome = catalogBean.doFindProducts();
		// Assert
		assertNull(catalogBean.getProducts());
		assertEquals("showproducts.faces", navigationOutcome);
	}

	@Test
	@Tag("integration")
	void handleExceptionInFindProducts() {
		// Arrange
		String validCategoryName = "Cats";
		catalogBean.setCategoryName(validCategoryName);
		when(catalogService.findProducts(validCategoryName)).thenThrow(new NullPointerException());
		// Act
		String navigationOutcome = catalogBean.doFindProducts();
		// Assert
		assertNull(catalogBean.getProducts());
		assertEquals("showproducts.faces", navigationOutcome);
	}

	@Test
	@Tag("boundary")
	void findProductsWithNullCatalogService() {
		// Arrange
		catalogBean.catalogService = null;
		String validCategoryName = "Fish";
		catalogBean.setCategoryName(validCategoryName);
		// Act
		String navigationOutcome = catalogBean.doFindProducts();
		// Assert
		assertNull(catalogBean.getProducts());
		assertEquals("showproducts.faces", navigationOutcome);
	}

}