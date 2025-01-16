
package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import java.io.Serializable;
import java.util.ArrayList;
import org.agoncal.application.petstore.util.Loggable;

class CountryServiceGetSearchPredicatesTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private Root<Country> root;

	private CountryService countryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		countryService = new CountryService();
		countryService.entityManager = entityManager;
		when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNonNullNonEmptyIsoCode() {
		Country country = new Country();
		country.setIsoCode("US");
		Predicate expectedPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("isoCode")), "%us%");
		when(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("isoCode")), "%us%"))
			.thenReturn(expectedPredicate);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertArrayEquals(new Predicate[] { expectedPredicate }, actualPredicates);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNullIsoCode() {
		Country country = new Country();
		country.setIsoCode(null);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertEquals(0, actualPredicates.length);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNonNullNonEmptyName() {
		Country country = new Country();
		country.setName("United States");
		Predicate expectedPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%united states%");
		when(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("name")), "%united states%"))
			.thenReturn(expectedPredicate);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertArrayEquals(new Predicate[] { expectedPredicate }, actualPredicates);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNullName() {
		Country country = new Country();
		country.setName(null);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertEquals(0, actualPredicates.length);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNonNullNonEmptyPrintableName() {
		Country country = new Country();
		country.setPrintableName("United States of America");
		Predicate expectedPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("printableName")),
				"%united states of america%");
		when(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("printableName")),
				"%united states of america%"))
			.thenReturn(expectedPredicate);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertArrayEquals(new Predicate[] { expectedPredicate }, actualPredicates);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNullPrintableName() {
		Country country = new Country();
		country.setPrintableName(null);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertEquals(0, actualPredicates.length);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNonNullNonEmptyIso3() {
		Country country = new Country();
		country.setIso3("USA");
		Predicate expectedPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("iso3")), "%usa%");
		when(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("iso3")), "%usa%"))
			.thenReturn(expectedPredicate);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertArrayEquals(new Predicate[] { expectedPredicate }, actualPredicates);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNullIso3() {
		Country country = new Country();
		country.setIso3(null);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertEquals(0, actualPredicates.length);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNonNullNonEmptyNumcode() {
		Country country = new Country();
		country.setNumcode("840");
		Predicate expectedPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("numcode")), "%840%");
		when(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("numcode")), "%840%"))
			.thenReturn(expectedPredicate);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertArrayEquals(new Predicate[] { expectedPredicate }, actualPredicates);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithNullNumcode() {
		Country country = new Country();
		country.setNumcode(null);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertEquals(0, actualPredicates.length);
	}

	@Test
	@Tag("invalid")
	void searchCountriesWithAllFieldsNullOrEmpty() {
		Country country = new Country();
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertEquals(0, actualPredicates.length);
	}

	@Test
	@Tag("valid")
	void searchCountriesWithCombinationOfNonNullAndNullFields() {
		Country country = new Country();
		country.setIsoCode("US");
		country.setName(null);
		country.setPrintableName("United States of America");
		country.setIso3(null);
		country.setNumcode("840");
		Predicate isoCodePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("isoCode")), "%us%");
		Predicate printableNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("printableName")),
				"%united states of america%");
		Predicate numcodePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("numcode")), "%840%");
		when(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("isoCode")), "%us%"))
			.thenReturn(isoCodePredicate);
		when(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("printableName")),
				"%united states of america%"))
			.thenReturn(printableNamePredicate);
		when(criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("numcode")), "%840%"))
			.thenReturn(numcodePredicate);
		Predicate[] actualPredicates = countryService.getSearchPredicates(root, country);
		assertArrayEquals(new Predicate[] { isoCodePredicate, printableNamePredicate, numcodePredicate },
				actualPredicates);
	}

}