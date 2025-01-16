
package org.agoncal.application.petstore.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.agoncal.application.petstore.model.Item;
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
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

class ItemEndpointCreateTest {

	@Mock
	private EntityManager em;

	@InjectMocks
	private ItemEndpoint itemEndpoint;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@Tag("valid")
	void createNewItem() {
		Item item = new Item();
		item.setId(1L);
		when(em.merge(item)).thenReturn(item);
		Response response = itemEndpoint.create(item);
		assertNotNull(response);
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		verify(em, times(1)).persist(item);
	}

	@Test
	@Tag("invalid")
	void createItemWithNullEntity() {
		Response response = itemEndpoint.create(null);
		assertNotNull(response);
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		verify(em, never()).persist(any(Item.class));
	}

	@ParameterizedTest
	@MethodSource("provideInvalidItems")
	@Tag("invalid")
	void createItemWithInvalidData(Item invalidItem, String errorMessage) {
		Response response = itemEndpoint.create(invalidItem);
		assertNotNull(response);
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		verify(em, never()).persist(any(Item.class));
	}

	private static Stream<Arguments> provideInvalidItems() {
		return Stream.of(
				Arguments.of(new Item(null, null, -1.0),
						"Item cannot have null name or null description or negative price"),
				Arguments.of(new Item("", "desc", 10.0), "Item cannot have empty name"));
	}

}