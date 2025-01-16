
package org.agoncal.application.petstore.view.shopping;

import org.agoncal.application.petstore.model.Customer;
import org.agoncal.application.petstore.service.CustomerService;
import org.agoncal.application.petstore.view.CatchException;
import org.agoncal.application.petstore.view.CredentialsBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.security.auth.login.LoginException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.agoncal.application.petstore.util.Loggable;
import org.agoncal.application.petstore.view.AbstractBean;
import org.agoncal.application.petstore.view.LoggedIn;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.login.LoginContext;
import java.io.Serializable;

class AccountBeanDoLoginTest {

	private AccountBean accountBean;

	@Mock
	private CustomerService customerService;

	@Mock
	private CatchException catchException;

	@Mock
	private CredentialsBean credentials;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		accountBean = new AccountBean();
		accountBean.customerService = customerService;
		accountBean.catchException = catchException;
		accountBean.credentials = credentials;
	}

	@Test
    @Tag("invalid")
    void testLoginWithNullUsername() throws LoginException {
        when(credentials.getLogin()).thenReturn(null);
        when(credentials.getPassword()).thenReturn("password");
        String result = accountBean.doLogin();
        assertThat(result).isNull();
        verify(catchException).addWarningMessage("id_filled");
    }

	@Test
    @Tag("invalid")
    void testLoginWithEmptyUsername() throws LoginException {
        when(credentials.getLogin()).thenReturn("");
        when(credentials.getPassword()).thenReturn("password");
        String result = accountBean.doLogin();
        assertThat(result).isNull();
        verify(catchException).addWarningMessage("id_filled");
    }

	@Test
    @Tag("invalid")
    void testLoginWithNullPassword() throws LoginException {
        when(credentials.getLogin()).thenReturn("username");
        when(credentials.getPassword()).thenReturn(null);
        String result = accountBean.doLogin();
        assertThat(result).isNull();
        verify(catchException).addWarningMessage("pwd_filled");
    }

	@Test
    @Tag("invalid")
    void testLoginWithEmptyPassword() throws LoginException {
        when(credentials.getLogin()).thenReturn("username");
        when(credentials.getPassword()).thenReturn("");
        String result = accountBean.doLogin();
        assertThat(result).isNull();
        verify(catchException).addWarningMessage("pwd_filled");
    }

	@Test
	@Tag("valid")
	void testSuccessfulLoginWithValidCredentials() throws LoginException {
		Customer customer = new Customer();
		when(credentials.getLogin()).thenReturn("username");
		when(credentials.getPassword()).thenReturn("password");
		when(customerService.findCustomer("username")).thenReturn(customer);
		String result = accountBean.doLogin();
		assertThat(result).isEqualTo("main.faces");
		assertThat(accountBean.getLoggedinCustomer()).isEqualTo(customer);
	}

	@Test
    @Tag("invalid")
    void testLoginWithNonExistentUsername() throws LoginException {
        when(credentials.getLogin()).thenReturn("nonexistentUsername");
        when(credentials.getPassword()).thenReturn("password");
        when(customerService.findCustomer("nonexistentUsername")).thenReturn(null);
        String result = accountBean.doLogin();
        assertThat(result).isNull();
        assertThat(accountBean.getLoggedinCustomer()).isNull();
    }

	@Test
    @Tag("integration")
    void testExceptionDuringLogin() throws LoginException {
        when(credentials.getLogin()).thenReturn("username");
        when(credentials.getPassword()).thenReturn("password");
        when(customerService.findCustomer("username")).thenThrow(new RuntimeException());
        String result = accountBean.doLogin();
        assertThat(result).isNull();
        assertThat(accountBean.getLoggedinCustomer()).isNull();
    }

}