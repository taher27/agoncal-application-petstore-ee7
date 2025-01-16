
package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import org.agoncal.application.petstore.model.Country;
import org.agoncal.application.petstore.util.Loggable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CustomerServiceDoesLoginAlreadyExistTest {

	@Mock
	private TypedQuery<Customer> typedQuery;

	@InjectMocks
	private CustomerService customerService;

	@Test
	@DisplayName("Check if a login already exists")
	@Tag("valid")
	void checkIfLoginExists() {
		String existingLogin = "john.doe";
		Customer customer = new Customer();
		customer.setLogin(existingLogin);
		when(typedQuery.getSingleResult()).thenReturn(customer);
		boolean result = customerService.doesLoginAlreadyExist(existingLogin);
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("Check if a login does not exist")
	@Tag("valid")
	void checkIfLoginDoesNotExist() {
		String nonExistingLogin = "jane.doe";
		when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);
		boolean result = customerService.doesLoginAlreadyExist(nonExistingLogin);
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("Check for null login")
	@Tag("invalid")
	void checkForNullLogin() {
		assertThatThrownBy(() -> customerService.doesLoginAlreadyExist(null))
			.isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("Check for empty login")
	@Tag("boundary")
	void checkForEmptyLogin() {
		String emptyLogin = "";
		when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);
		boolean result = customerService.doesLoginAlreadyExist(emptyLogin);
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("Check for case-insensitive login")
	@Tag("valid")
	void checkForCaseInsensitiveLogin() {
		String existingLogin = "john.doe";
		Customer customer = new Customer();
		customer.setLogin(existingLogin);
		when(typedQuery.setParameter(eq("login"), existingLogin.toUpperCase())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(customer);
		boolean result = customerService.doesLoginAlreadyExist(existingLogin.toUpperCase());
		assertThat(result).isTrue();
	}

}