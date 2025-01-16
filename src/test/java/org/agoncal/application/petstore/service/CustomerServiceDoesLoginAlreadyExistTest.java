
// ********RoostGPT********
/*
Test generated by RoostGPT for test test-petstore-with-awsbedrock using AI Type AWS Bedrock Runtime AI and AI Model anthropic.claude-3-sonnet-20240229-v1:0

ROOST_METHOD_HASH=doesLoginAlreadyExist_d6c4476a87
ROOST_METHOD_SIG_HASH=doesLoginAlreadyExist_9ab25f1102

```
Scenario 1: Check if a login already exists

Details:
  TestName: checkIfLoginExists
  Description: This test verifies that the doesLoginAlreadyExist method returns true when a customer with the given login exists in the database.
  Execution:
    Arrange: Create a mock customer entity with a specific login value and set up the EntityManager to return this customer when queried.
    Act: Invoke the doesLoginAlreadyExist method with the same login value used for the mock customer.
    Assert: Assert that the method returns true, indicating that the login already exists.
  Validation:
    The assertion confirms that the method correctly identifies an existing login in the database. This test scenario is essential to ensure that the application enforces the uniqueness constraint on the login field for customers.

Scenario 2: Check if a login does not exist

Details:
  TestName: checkIfLoginDoesNotExist
  Description: This test verifies that the doesLoginAlreadyExist method returns false when no customer with the given login exists in the database.
  Execution:
    Arrange: Set up the EntityManager to throw a NoResultException when queried with a specific login value.
    Act: Invoke the doesLoginAlreadyExist method with the same login value used in the arrange step.
    Assert: Assert that the method returns false, indicating that the login does not exist.
  Validation:
    The assertion confirms that the method correctly handles the case where no customer with the given login exists in the database. This test scenario ensures that the method can handle the absence of a matching login and return the expected result.

Scenario 3: Check for null login

Details:
  TestName: checkForNullLogin
  Description: This test verifies that the doesLoginAlreadyExist method throws a NullPointerException when invoked with a null login value.
  Execution:
    Arrange: No setup is required for this test case.
    Act: Invoke the doesLoginAlreadyExist method with a null value for the login parameter.
    Assert: Assert that a NullPointerException is thrown.
  Validation:
    The assertion confirms that the method correctly handles the case where a null login value is provided. This test scenario ensures that the method adheres to the @NotNull constraint on the login parameter and throws an appropriate exception when violated.

Scenario 4: Check for empty login

Details:
  TestName: checkForEmptyLogin
  Description: This test verifies that the doesLoginAlreadyExist method returns false when invoked with an empty login value.
  Execution:
    Arrange: Set up the EntityManager to throw a NoResultException when queried with an empty login value.
    Act: Invoke the doesLoginAlreadyExist method with an empty string as the login parameter.
    Assert: Assert that the method returns false, indicating that the empty login does not exist.
  Validation:
    The assertion confirms that the method correctly handles the case where an empty login value is provided. This test scenario ensures that the method interprets an empty login as a non-existing login and returns the expected result.

Scenario 5: Check for case-insensitive login

Details:
  TestName: checkForCaseInsensitiveLogin
  Description: This test verifies that the doesLoginAlreadyExist method correctly identifies a login regardless of its case sensitivity.
  Execution:
    Arrange: Create a mock customer entity with a specific login value and set up the EntityManager to return this customer when queried with the same login value in different cases.
    Act: Invoke the doesLoginAlreadyExist method with the same login value used for the mock customer, but with different cases (e.g., uppercase, lowercase, or a mix of both).
    Assert: Assert that the method returns true, indicating that the login already exists, regardless of the case used.
  Validation:
    The assertion confirms that the method correctly identifies a login regardless of its case sensitivity. This test scenario ensures that the application treats logins as case-insensitive, which is a common requirement for user authentication systems.
```
*/

// ********RoostGPT********
package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Country;
import org.agoncal.application.petstore.model.Customer;
import org.agoncal.application.petstore.util.Loggable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Loggable
@LocalBean
@Stateless
public class CustomerServiceDoesLoginAlreadyExistTest extends AbstractService<Customer> {

	public CustomerServiceDoesLoginAlreadyExistTest() {
		super(Customer.class);
	}

	public boolean doesLoginAlreadyExist(@NotNull String login) {
		// Login has to be unique
		TypedQuery<Customer> typedQuery = entityManager.createNamedQuery(Customer.FIND_BY_LOGIN, Customer.class);
		typedQuery.setParameter("login", login);
		try {
			typedQuery.getSingleResult();
			return true;
		}
		catch (NoResultException e) {
			return false;
		}
	}

}