
package org.agoncal.application.petstore.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.agoncal.application.petstore.model.Country;
import org.agoncal.application.petstore.util.Loggable;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
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

class CountryEndpointCreateTest {

	@ParameterizedTest
	@MethodSource("provideValidCountries")
	@Tag("valid")
	void createNewCountrySuccessfully(Country country) {
		EntityManager entityManager = mock(EntityManager.class);
		CountryEndpoint countryEndpoint = new CountryEndpoint();
		countryEndpoint.em = entityManager;
		when(entityManager.merge(country)).thenReturn(country);
		Response response = countryEndpoint.create(country);
		assertNotNull(response);
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		URI location = response.getLocation();
		assertNotNull(location);
		assertTrue(location.toString().endsWith(String.valueOf(country.getId())));
		verify(entityManager, times(1)).persist(country);
	}

	@Test
	@Tag("invalid")
	void createCountryWithNullEntity() {
		EntityManager entityManager = mock(EntityManager.class);
		CountryEndpoint countryEndpoint = new CountryEndpoint();
		countryEndpoint.em = entityManager;
		assertThrows(NullPointerException.class, () -> countryEndpoint.create(null));
		verifyNoInteractions(entityManager);
	}

	private static Stream<Arguments> provideValidCountries() {
		return Stream.of(Arguments.arguments(new Country(1L, "Country 1", "C1")),
				Arguments.arguments(new Country(2L, "Country 2", "C2")),
				Arguments.arguments(new Country(3L, "Country 3", "C3")));
	}

}