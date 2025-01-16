
package org.agoncal.application.petstore.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.agoncal.application.petstore.model.Product;
import org.agoncal.application.petstore.util.Loggable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Loggable
class ProductEndpointCreateTest {

	@Mock
	private EntityManager em;

	@InjectMocks
	private ProductEndpoint productEndpoint;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@Tag("valid")
	void createNewProductSuccessfully() {
		Product newProduct = new Product("Product Name", "Product Description", 10.0, null);
		when(em.merge(newProduct)).thenReturn(newProduct);
		newProduct.setId(1L);
		Response response = productEndpoint.create(newProduct);
		assertNotNull(response);
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		URI expectedLocation = UriBuilder.fromResource(ProductEndpoint.class).path("1").build();
		assertEquals(expectedLocation, response.getLocation());
	}

	@ParameterizedTest
	@MethodSource("provideInvalidProducts")
	@Tag("invalid")
	void createProductWithInvalidData(Product invalidProduct, Response.Status expectedStatus) {
		Response response = productEndpoint.create(invalidProduct);
		assertNotNull(response);
		assertEquals(expectedStatus.getStatusCode(), response.getStatus());
	}

	private static Stream<Arguments> provideInvalidProducts() {
		return Stream.of(Arguments.arguments(null, Response.Status.BAD_REQUEST),
				Arguments.arguments(new Product(null, "Description", 10.0, null), Response.Status.UNPROCESSABLE_ENTITY),
				Arguments.arguments(new Product("Name", null, 10.0, null), Response.Status.UNPROCESSABLE_ENTITY),
				Arguments.arguments(new Product("Name", "Description", null, null),
						Response.Status.UNPROCESSABLE_ENTITY));
	}

	@Test
	@Tag("invalid")
	void createProductWithExistingId() {
		Product productWithId = new Product("Product Name", "Product Description", 10.0, null);
		productWithId.setId(1L);
		Response response = productEndpoint.create(productWithId);
		assertNotNull(response);
		assertEquals(Response.Status.UNPROCESSABLE_ENTITY.getStatusCode(), response.getStatus());
	}

	@Test
	@Tag("boundary")
	void createProductWithNullEntityManager() {
		em = null;
		Product newProduct = new Product("Product Name", "Product Description", 10.0, null);
		Response response = productEndpoint.create(newProduct);
		assertNotNull(response);
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}

	@Test
	@Tag("integration")
	void createProductAndCheckEntityState() {
		Product newProduct = new Product("Product Name", "Product Description", 10.0, null);
		when(em.merge(newProduct)).thenReturn(newProduct);
		newProduct.setId(1L);
		Response response = productEndpoint.create(newProduct);
		Product createdProduct = new Product();
		createdProduct.setId(1L);
		createdProduct.setName("Product Name");
		createdProduct.setDescription("Product Description");
		createdProduct.setPrice(10.0);
		assertNotNull(response);
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		verify(em, times(1)).persist(createdProduct);
	}

}