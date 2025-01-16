
package org.agoncal.application.petstore.view.shopping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

class CredentialsBeanGetLoginTest {

	private CredentialsBean credentialsBean;

	@BeforeEach
	void setUp() {
		credentialsBean = new CredentialsBean();
	}

	@Test
	@Tag("valid")
	void getLoginWhenLoginIsNotNull() {
		// Arrange
		String expectedLogin = "testuser";
		credentialsBean.setLogin(expectedLogin);
		// Act
		String actualLogin = credentialsBean.getLogin();
		// Assert
		assertEquals(expectedLogin, actualLogin);
	}

	@Test
	@Tag("valid")
	void getLoginWhenLoginIsNull() {
		// Arrange
		credentialsBean.setLogin(null);
		// Act
		String actualLogin = credentialsBean.getLogin();
		// Assert
		assertNull(actualLogin);
	}

	@Test
	@Tag("valid")
	void getLoginWithoutSettingLogin() {
		// Act
		String actualLogin = credentialsBean.getLogin();
		// Assert
		assertNull(actualLogin);
	}

	@Test
	@Tag("valid")
	void getLoginAfterSettingLoginMultipleTimes() {
		// Arrange
		credentialsBean.setLogin("user1");
		credentialsBean.setLogin("user2");
		String expectedLogin = "user3";
		credentialsBean.setLogin(expectedLogin);
		// Act
		String actualLogin = credentialsBean.getLogin();
		// Assert
		assertEquals(expectedLogin, actualLogin);
	}

}