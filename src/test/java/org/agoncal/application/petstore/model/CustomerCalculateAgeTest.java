
package org.agoncal.application.petstore.model;

import org.agoncal.application.petstore.constraints.Email;
import org.agoncal.application.petstore.constraints.Login;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.*;

@Tag("valid")
class CustomerCalculateAgeTest {

	private static Stream<Arguments> provideValidAgeCalculationScenarios() {
		return Stream.of(Arguments.arguments(null, null), Arguments.arguments(Date.from(Instant.now()), 0),
				Arguments.arguments(Date.from(Instant.now().minus(365, ChronoUnit.DAYS)), 1),
				Arguments.arguments(Date.from(Instant.now().minus(10 * 365, ChronoUnit.DAYS)), 10));
	}

	@ParameterizedTest(name = "calculateAge with dateOfBirth={0} should result in age={1}")
	@MethodSource("provideValidAgeCalculationScenarios")
	void calculateAge(Date dateOfBirth, Integer expectedAge) {
		Customer customer = new Customer();
		customer.setDateOfBirth(dateOfBirth);
		customer.calculateAge();
		assertEquals(expectedAge, customer.getAge());
	}

	@ParameterizedTest(name = "calculateAge with dateOfBirth={0} and currentDate={1} should result in age={2}")
	@MethodSource("provideBirthdayEdgeCaseScenarios")
	@Tag("boundary")
	void calculateAgeForEdgeCaseBirthday(Date dateOfBirth, Date currentDate, Integer expectedAge) {
		Customer customer = new Customer();
		customer.setDateOfBirth(dateOfBirth);
		Calendar now = new GregorianCalendar();
		now.setTime(currentDate);
		customer.calculateAge();
		assertEquals(expectedAge, customer.getAge());
	}

	private static Stream<Arguments> provideBirthdayEdgeCaseScenarios() {
		Date today = Date.from(Instant.now());
		return Stream.of(Arguments.arguments(Date.from(Instant.now().minus(10 * 365, ChronoUnit.DAYS)), today, 10),
				Arguments.arguments(Date.from(Instant.now().minus(11 * 365, ChronoUnit.DAYS)), today, 11));
	}

}