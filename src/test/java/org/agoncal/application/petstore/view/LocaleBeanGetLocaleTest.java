
package org.agoncal.application.petstore.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import org.agoncal.application.petstore.util.Loggable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

@ExtendWith(MockitoExtension.class)
class LocaleBeanGetLocaleTest {

	@Mock
	private FacesContext facesContext;

	private LocaleBean localeBean;

	@BeforeEach
	void setUp() {
		localeBean = new LocaleBean();
		localeBean.setFacesContext(facesContext);
	}

	@Test
	@Tag("valid")
	void getLocaleFromFacesContext() {
		Locale expectedLocale = Locale.FRENCH;
		when(facesContext.getViewRoot().getLocale()).thenReturn(expectedLocale);
		Locale actualLocale = localeBean.getLocale();
		assertEquals(expectedLocale, actualLocale);
	}

	@Test
    @Tag("invalid")
    void getNullLocaleFromFacesContext() {
        when(facesContext.getViewRoot().getLocale()).thenReturn(null);
        Locale actualLocale = localeBean.getLocale();
        assertNull(actualLocale);
    }

	@Test
	@Tag("integration")
	void concurrentAccessToLocale() throws InterruptedException {
		Locale expectedLocale = Locale.GERMAN;
		when(facesContext.getViewRoot().getLocale()).thenReturn(expectedLocale);
		int numThreads = 10;
		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
		for (int i = 0; i < numThreads; i++) {
			executorService.submit(() -> {
				Locale actualLocale = localeBean.getLocale();
				assertEquals(expectedLocale, actualLocale);
			});
		}
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);
	}

	@Test
	@Tag("boundary")
	void localeInitialization() {
		Locale expectedLocale = Locale.getDefault();
		Locale actualLocale = localeBean.getLocale();
		assertEquals(expectedLocale, actualLocale);
	}

}