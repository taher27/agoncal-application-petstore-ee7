
package org.agoncal.application.petstore.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.agoncal.application.petstore.util.Loggable;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
class AbstractBeanAddInformationMessageTest {

	@Mock
	private FacesContext facesContext;

	@Mock
	private ResourceBundle resourceBundle;

	@InjectMocks
	private AbstractBean abstractBean;

	@Test
    @Tag("valid")
    void addInformationMessageWithValidMessageAndArguments() {
        when(facesContext.getViewRoot().getLocale()).thenReturn(Locale.ENGLISH);
        when(resourceBundle.getString("validKey")).thenReturn("Valid message {0}");
        abstractBean.addInformationMessage("validKey", "arg1");
        verify(facesContext).addMessage(eq(null), any(FacesMessage.class));
    }

	@Test
	@Tag("invalid")
	void addInformationMessageWithEmptyMessage() {
		abstractBean.addInformationMessage("");
		verify(facesContext, never()).addMessage(any(), any());
	}

	@Test
    @Tag("boundary")
    void addInformationMessageWithNullArguments() {
        when(facesContext.getViewRoot().getLocale()).thenReturn(Locale.ENGLISH);
        when(resourceBundle.getString("nullArgsKey")).thenReturn("Null args message");
        abstractBean.addInformationMessage("nullArgsKey", (Object) null);
        verify(facesContext).addMessage(eq(null), any(FacesMessage.class));
    }

	@Test
    @Tag("boundary")
    void addInformationMessageWithFacesContextUnavailable() {
        when(FacesContext.getCurrentInstance()).thenReturn(null);
        abstractBean.addInformationMessage("someKey");
        // No exceptions should be thrown
    }

	@Test
    @Tag("boundary")
    void addInformationMessageWithResourceBundleUnavailable() {
        when(facesContext.getViewRoot().getLocale()).thenReturn(Locale.ENGLISH);
        when(resourceBundle.getString(anyString())).thenThrow(new RuntimeException());
        abstractBean.addInformationMessage("someKey");
        // No exceptions should be thrown
    }

	@Test
    @Tag("valid")
    void addInformationMessageWithMultipleArguments() {
        when(facesContext.getViewRoot().getLocale()).thenReturn(Locale.ENGLISH);
        when(resourceBundle.getString("multiArgsKey")).thenReturn("Message with {0} and {1}");
        abstractBean.addInformationMessage("multiArgsKey", "arg1", "arg2");
        verify(facesContext).addMessage(eq(null), any(FacesMessage.class));
    }

	@Test
    @Tag("boundary")
    void addInformationMessageWithMessageFormatExceptions() {
        when(facesContext.getViewRoot().getLocale()).thenReturn(Locale.ENGLISH);
        when(resourceBundle.getString("invalidFormatKey")).thenReturn("Invalid format {0");
        abstractBean.addInformationMessage("invalidFormatKey", "arg1");
        // No exceptions should be thrown
    }

}