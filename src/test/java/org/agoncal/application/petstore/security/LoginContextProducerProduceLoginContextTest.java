
package org.agoncal.application.petstore.security;

import org.agoncal.application.petstore.util.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.net.URISyntaxException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.File;

@ExtendWith(MockitoExtension.class)
class LoginContextProducerProduceLoginContextTest {

	@Mock
	private SimpleCallbackHandler callbackHandler;

	private LoginContextProducer loginContextProducer;

	@BeforeEach
	void setUp() {
		loginContextProducer = new LoginContextProducer();
		loginContextProducer.callbackHandler = callbackHandler;
	}

	@Test
	@DisplayName("Successful login with valid configuration")
	@Tag("valid")
	void successfulLoginWithValidConfiguration() throws LoginException, URISyntaxException {
		String validConfigFileName = "/login.conf";
		String validLoginModuleName = "MyLoginModule";
		LoginContext loginContext = loginContextProducer.produceLoginContext(validConfigFileName, validLoginModuleName);
		assertNotNull(loginContext);
	}

	@Test
	@DisplayName("Null configuration file name")
	@Tag("invalid")
	void nullConfigurationFileName() {
		String nullConfigFileName = null;
		String validLoginModuleName = "MyLoginModule";
		assertThrows(LoginException.class,
				() -> loginContextProducer.produceLoginContext(nullConfigFileName, validLoginModuleName));
	}

	@Test
	@DisplayName("Empty configuration file name")
	@Tag("invalid")
	void emptyConfigurationFileName() {
		String emptyConfigFileName = "";
		String validLoginModuleName = "MyLoginModule";
		assertThrows(LoginException.class,
				() -> loginContextProducer.produceLoginContext(emptyConfigFileName, validLoginModuleName));
	}

	@Test
	@DisplayName("Null login module name")
	@Tag("invalid")
	void nullLoginModuleName() throws URISyntaxException {
		String validConfigFileName = "/login.conf";
		String nullLoginModuleName = null;
		assertThrows(LoginException.class,
				() -> loginContextProducer.produceLoginContext(validConfigFileName, nullLoginModuleName));
	}

	@Test
	@DisplayName("Empty login module name")
	@Tag("invalid")
	void emptyLoginModuleName() throws URISyntaxException {
		String validConfigFileName = "/login.conf";
		String emptyLoginModuleName = "";
		assertThrows(LoginException.class,
				() -> loginContextProducer.produceLoginContext(validConfigFileName, emptyLoginModuleName));
	}

	@Test
	@DisplayName("Exception handling")
	@Tag("integration")
	void exceptionHandling() throws URISyntaxException {
		String validConfigFileName = "/login.conf";
		String validLoginModuleName = "MyLoginModule";
		doThrow(LoginException.class).when(callbackHandler).handle(any());
		LoginContext loginContext = loginContextProducer.produceLoginContext(validConfigFileName, validLoginModuleName);
		assertNull(loginContext);
	}

	@Test
	@DisplayName("Null callback handler")
	@Tag("invalid")
	void nullCallbackHandler() throws URISyntaxException {
		loginContextProducer.callbackHandler = null;
		String validConfigFileName = "/login.conf";
		String validLoginModuleName = "MyLoginModule";
		assertThrows(LoginException.class,
				() -> loginContextProducer.produceLoginContext(validConfigFileName, validLoginModuleName));
	}

	@Test
	@DisplayName("Invalid configuration file")
	@Tag("invalid")
	void invalidConfigurationFile() {
		String invalidConfigFileName = "/invalid.conf";
		String validLoginModuleName = "MyLoginModule";
		assertThrows(LoginException.class,
				() -> loginContextProducer.produceLoginContext(invalidConfigFileName, validLoginModuleName));
	}

	@Test
	@DisplayName("Invalid login module name")
	@Tag("invalid")
	void invalidLoginModuleName() throws URISyntaxException {
		String validConfigFileName = "/login.conf";
		String invalidLoginModuleName = "InvalidModule";
		assertThrows(LoginException.class,
				() -> loginContextProducer.produceLoginContext(validConfigFileName, invalidLoginModuleName));
	}

	@Test
	@DisplayName("Concurrent access")
	@Tag("integration")
	void concurrentAccess() throws LoginException, URISyntaxException, InterruptedException {
		String validConfigFileName = "/login.conf";
		String validLoginModuleName = "MyLoginModule";
		int numThreads = 10;
		Thread[] threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new Thread(() -> {
				try {
					LoginContext loginContext = loginContextProducer.produceLoginContext(validConfigFileName,
							validLoginModuleName);
					assertNotNull(loginContext);
				}
				catch (LoginException | URISyntaxException e) {
					fail("Exception thrown during concurrent access: " + e.getMessage());
				}
			});
			threads[i].start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
	}

}