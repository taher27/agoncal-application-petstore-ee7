
package org.agoncal.application.petstore.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.logging.Logger;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import javax.inject.Inject;
import javax.interceptor.Interceptor;

@ExtendWith(MockitoExtension.class)
class ExceptionInterceptorCatchExceptionTest {

	@Mock
	private InvocationContext mockInvocationContext;

	@Mock
	private FacesContext mockFacesContext;

	@Mock
	private Logger mockLogger;

	@Test
	@Tag("valid")
	void testSuccessfulExecution() throws Exception {
		// Arrange
		Object expectedResult = new Object();
		when(mockInvocationContext.proceed()).thenReturn(expectedResult);
		// Act
		Object result = new ExceptionInterceptor().catchException(mockInvocationContext);
		// Assert
		assertEquals(expectedResult, result);
		verify(mockInvocationContext, times(1)).proceed();
	}

	@Test
	@Tag("valid")
	void testExceptionHandlingAndLogging() throws Exception {
		// Arrange
		Exception exception = new RuntimeException("Test exception");
		when(mockInvocationContext.proceed()).thenThrow(exception);
		when(mockInvocationContext.getTarget().getClass().getName()).thenReturn("TestClass");
		when(mockInvocationContext.getMethod().getName()).thenReturn("testMethod");
		// Act
		Object result = new ExceptionInterceptor().catchException(mockInvocationContext);
		// Assert
		assertNull(result);
		verify(mockLogger, times(1)).severe(startsWith("/!\\ TestClass - testMethod - Test exception"));
		exception.printStackTrace();
	}

	@Test
	@Tag("valid")
	void testErrorMessageAddition() throws Exception {
		// Arrange
		Exception exception = new RuntimeException("Test exception");
		when(mockInvocationContext.proceed()).thenThrow(exception);
		when(FacesContext.getCurrentInstance()).thenReturn(mockFacesContext);
		// Act
		new ExceptionInterceptor().catchException(mockInvocationContext);
		// Assert
		verify(mockFacesContext, times(1)).addMessage(isNull(), any(FacesMessage.class));
	}

	@Test
    @Tag("boundary")
    void testNullExceptionHandling() throws Exception {
        // Arrange
        when(mockInvocationContext.proceed()).thenThrow(null);
        // Act
        Object result = new ExceptionInterceptor().catchException(mockInvocationContext);
        // Assert
        assertNull(result);
    }

	@Test
	@Tag("valid")
	void testMultipleExceptions() throws Exception {
		// Arrange
		Exception exception1 = new RuntimeException("Test exception 1");
		Exception exception2 = new RuntimeException("Test exception 2", exception1);
		when(mockInvocationContext.proceed()).thenThrow(exception2);
		when(mockInvocationContext.getTarget().getClass().getName()).thenReturn("TestClass");
		when(mockInvocationContext.getMethod().getName()).thenReturn("testMethod");
		// Act
		Object result = new ExceptionInterceptor().catchException(mockInvocationContext);
		// Assert
		assertNull(result);
		verify(mockLogger, times(1)).severe(startsWith("/!\\ TestClass - testMethod - Test exception 2"));
		exception2.printStackTrace();
	}

}