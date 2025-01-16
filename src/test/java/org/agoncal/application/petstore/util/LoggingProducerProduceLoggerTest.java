
package org.agoncal.application.petstore.util;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import javax.enterprise.inject.Produces;

@ExtendWith(MockitoExtension.class)
class LoggingProducerProduceLoggerTest {

	@Mock
	private InjectionPoint injectionPoint;

	@Mock
	private AnnotatedMember<?> annotatedMember;

	@Mock
	private Class<?> declaringClass;

	@Test
    @Tag("valid")
    void produceLoggerWithValidInjectionPoint() {
        when(injectionPoint.getMember()).thenReturn(annotatedMember);
        when(annotatedMember.getDeclaringClass()).thenReturn(declaringClass);
        when(declaringClass.getName()).thenReturn("org.example.MyClass");
        LoggingProducer loggingProducer = new LoggingProducer();
        Logger logger = loggingProducer.produceLogger(injectionPoint);
        assertNotNull(logger);
        assertEquals("org.example.MyClass", logger.getName());
    }

	@Test
	@Tag("invalid")
	void produceLoggerWithNullInjectionPoint() {
		LoggingProducer loggingProducer = new LoggingProducer();
		Logger logger = loggingProducer.produceLogger(null);
		assertNotNull(logger);
		assertEquals(Logger.getLogger("").getName(), logger.getName());
	}

	@Test
    @Tag("invalid")
    void produceLoggerWithNullMember() {
        when(injectionPoint.getMember()).thenReturn(null);
        LoggingProducer loggingProducer = new LoggingProducer();
        Logger logger = loggingProducer.produceLogger(injectionPoint);
        assertNotNull(logger);
        assertEquals(Logger.getLogger("").getName(), logger.getName());
    }

	@Test
    @Tag("invalid")
    void produceLoggerWithMemberWithoutDeclaringClass() {
        when(injectionPoint.getMember()).thenReturn(annotatedMember);
        when(annotatedMember.getDeclaringClass()).thenReturn(null);
        LoggingProducer loggingProducer = new LoggingProducer();
        Logger logger = loggingProducer.produceLogger(injectionPoint);
        assertNotNull(logger);
        assertEquals(Logger.getLogger("").getName(), logger.getName());
    }

}