
package org.agoncal.application.petstore.security;

import org.agoncal.application.petstore.view.shopping.CredentialsBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.callback.CallbackHandler;

@DisplayName("SimpleCallbackHandler Handle Test")
class SimpleCallbackHandlerHandleTest {

	@Mock
	private CredentialsBean credentialsBean;

	private SimpleCallbackHandler simpleCallbackHandler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		simpleCallbackHandler = new SimpleCallbackHandler(credentialsBean);
	}

	@Nested
	@DisplayName("Valid Scenarios")
	@Tag("valid")
	class ValidScenarios {

		@Test
		@DisplayName("Handle Valid NameCallback")
		void handleValidNameCallback() throws IOException, UnsupportedCallbackException {
			String expectedLogin = "testLogin";
			when(credentialsBean.getLogin()).thenReturn(expectedLogin);
			NameCallback nameCallback = new NameCallback("Name:");
			Callback[] callbacks = { nameCallback };
			simpleCallbackHandler.handle(callbacks);
			assertEquals(expectedLogin, nameCallback.getName());
		}

		@Test
		@DisplayName("Handle Valid PasswordCallback")
		void handleValidPasswordCallback() throws IOException, UnsupportedCallbackException {
			String expectedPassword = "testPassword";
			when(credentialsBean.getPassword()).thenReturn(expectedPassword);
			PasswordCallback passwordCallback = new PasswordCallback("Password:", false);
			Callback[] callbacks = { passwordCallback };
			simpleCallbackHandler.handle(callbacks);
			assertEquals(expectedPassword, new String(passwordCallback.getPassword()));
		}

		@Test
		@DisplayName("Handle Multiple Callbacks")
		void handleMultipleCallbacks() throws IOException, UnsupportedCallbackException {
			String expectedLogin = "testLogin";
			String expectedPassword = "testPassword";
			when(credentialsBean.getLogin()).thenReturn(expectedLogin);
			when(credentialsBean.getPassword()).thenReturn(expectedPassword);
			NameCallback nameCallback = new NameCallback("Name:");
			PasswordCallback passwordCallback = new PasswordCallback("Password:", false);
			Callback[] callbacks = { nameCallback, passwordCallback };
			simpleCallbackHandler.handle(callbacks);
			assertEquals(expectedLogin, nameCallback.getName());
			assertEquals(expectedPassword, new String(passwordCallback.getPassword()));
		}

	}

	@Nested
	@DisplayName("Invalid Scenarios")
	@Tag("invalid")
	class InvalidScenarios {

		@Test
		@DisplayName("Handle Unsupported Callback")
		void handleUnsupportedCallback() {
			Callback unsupportedCallback = new Callback() {
			};
			Callback[] callbacks = { unsupportedCallback };
			assertThrows(UnsupportedCallbackException.class, () -> simpleCallbackHandler.handle(callbacks));
		}

	}

	@Nested
	@DisplayName("Boundary Scenarios")
	class BoundaryScenarios {

		@Test
		@DisplayName("Handle Null Callbacks")
		@Tag("boundary")
		void handleNullCallbacks() throws IOException, UnsupportedCallbackException {
			Callback[] callbacks = null;
			assertDoesNotThrow(() -> simpleCallbackHandler.handle(callbacks));
		}

		@Test
		@DisplayName("Handle Empty Callbacks")
		@Tag("boundary")
		void handleEmptyCallbacks() throws IOException, UnsupportedCallbackException {
			Callback[] callbacks = {};
			assertDoesNotThrow(() -> simpleCallbackHandler.handle(callbacks));
		}

	}

}