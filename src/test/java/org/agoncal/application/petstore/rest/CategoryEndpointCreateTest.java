
package org.agoncal.application.petstore.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.agoncal.application.petstore.model.Category;
import org.agoncal.application.petstore.util.Loggable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CategoryEndpointCreateTest {

	@Mock
	private EntityManager em;

	@InjectMocks
	private CategoryEndpoint categoryEndpoint;

	private Category validCategory;

	private Category invalidCategory;

	@BeforeEach
	void setUp() {
		validCategory = new Category("Valid Category");
		invalidCategory = new Category("");
	}

	@Test
    @Tag("valid")
    void testCreateNewCategorySuccessfully() {
        when(em.merge(any(Category.class))).thenReturn(validCategory);
        URI uri = UriBuilder.fromResource(CategoryEndpoint.class).path(String.valueOf(validCategory.getId())).build();
        Response response = categoryEndpoint.create(validCategory);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(uri, response.getLocation());
        verify(em, times(1)).persist(validCategory);
    }

	@Test
	@Tag("invalid")
	void testCreateCategoryWithNullData() {
		Response response = categoryEndpoint.create(null);
		assertNotNull(response);
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		verify(em, never()).persist(any(Category.class));
	}

	@Test
	@Tag("invalid")
	void testCreateCategoryWithInvalidData() {
		Response response = categoryEndpoint.create(invalidCategory);
		assertNotNull(response);
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		verify(em, never()).persist(any(Category.class));
	}

}