
package org.agoncal.application.petstore.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.agoncal.application.petstore.model.Customer;
import org.agoncal.application.petstore.util.Loggable;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Stateless
public class CustomerEndpointCreateTest {

	@Mock
	private EntityManager em;

	@InjectMocks
	private CustomerEndpoint customerEndpoint;

	@Captor
	private ArgumentCaptor<Customer> customerCaptor;

	@Test
	@Tag("valid")
	void createCustomerWithValidData() {
		Customer validCustomer = new Customer();
		// Set valid customer data
		when(em.merge(validCustomer)).thenReturn(validCustomer);
		Response response = customerEndpoint.create(validCustomer);
		verify(em, times(1)).persist(customerCaptor.capture());
		Customer capturedCustomer = customerCaptor.getValue();
		assertNotNull(capturedCustomer);
		assertEquals(validCustomer, capturedCustomer);
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		URI locationUri = response.getLocation();
		assertNotNull(locationUri);
		assertTrue(locationUri.toString().endsWith(String.valueOf(validCustomer.getId())));
	}

	@ParameterizedTest
	@MethodSource("provideInvalidCustomers")
	@Tag("invalid")
	void createCustomerWithMissingRequiredFields(Customer invalidCustomer) {
		Response response = customerEndpoint.create(invalidCustomer);
		verify(em, never()).persist(any(Customer.class));
		int expectedStatusCode = Response.Status.BAD_REQUEST.getStatusCode();
		assertEquals(expectedStatusCode, response.getStatus());
	}

	private static Stream<Arguments> provideInvalidCustomers() {
		return Stream.of(Arguments.of(new Customer(null, null, null, null, null)),
				Arguments.of(new Customer("", "", null, null, null)));
	}

	@Test
	@Tag("boundary")
	void createCustomerWithNullEntity() {
		Response response = customerEndpoint.create(null);
		verify(em, never()).persist(any(Customer.class));
		int expectedStatusCode = Response.Status.BAD_REQUEST.getStatusCode();
		assertEquals(expectedStatusCode, response.getStatus());
	}

	@Test
	@Tag("integration")
	void createCustomerWithConcurrentRequests() throws InterruptedException {
		int numThreads = 10;
		Customer validCustomer = new Customer();
		// Set valid customer data
		Thread[] threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new Thread(() -> customerEndpoint.create(validCustomer));
			threads[i].start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
		verify(em, times(numThreads)).persist(customerCaptor.capture());
		assertEquals(numThreads, customerCaptor.getAllValues().size());
	}

}