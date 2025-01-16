
package org.agoncal.application.petstore.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import javax.enterprise.inject.Produces;
import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConfigPropertyProducerProduceConfigPropertyTest {

	private Properties props;

	@BeforeEach
	void setUp() {
		props = new Properties();
		props.setProperty("key1", "value1");
		props.setProperty("key2", "value2");
		ConfigPropertyProducer.props = props;
	}

	@Test
	@Tag("valid")
	void validConfigKeyReturnsCorrectValue() {
		InjectionPoint ip = mockInjectionPoint("key1");
		String result = ConfigPropertyProducer.produceConfigProperty(ip);
		assertEquals("value1", result);
	}

	@ParameterizedTest
	@MethodSource("provideNonExistentKeys")
	@Tag("invalid")
	void nonExistentConfigKeyReturnsNull(String key) {
		InjectionPoint ip = mockInjectionPoint(key);
		String result = ConfigPropertyProducer.produceConfigProperty(ip);
		assertNull(result);
	}

	private Stream<Arguments> provideNonExistentKeys() {
		return Stream.of(Arguments.of("nonExistentKey"), Arguments.of("anotherNonExistentKey"));
	}

	@Test
	@Tag("invalid")
	void nullInjectionPointThrowsException() {
		assertThrows(NullPointerException.class, () -> ConfigPropertyProducer.produceConfigProperty(null));
	}

	@Test
	@Tag("invalid")
	void noConfigPropertyAnnotationThrowsException() {
		InjectionPoint ip = mock(InjectionPoint.class);
		assertThrows(NullPointerException.class, () -> ConfigPropertyProducer.produceConfigProperty(ip));
	}

	@Test
	@Tag("invalid")
	void emptyConfigKeyThrowsException() {
		InjectionPoint ip = mockInjectionPoint("");
		assertThrows(IllegalArgumentException.class, () -> ConfigPropertyProducer.produceConfigProperty(ip));
	}

	@Test
	@Tag("integration")
	void propertiesFileNotFoundHandling() {
		ConfigPropertyProducer.props = null;
		InjectionPoint ip = mockInjectionPoint("key1");
		assertThrows(NullPointerException.class, () -> ConfigPropertyProducer.produceConfigProperty(ip));
	}

	@Test
	@Tag("boundary")
	void concurrencyHandling() throws InterruptedException {
		int numThreads = 100;
		Thread[] threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			final InjectionPoint ip = mockInjectionPoint("key" + (i % 2 + 1));
			threads[i] = new Thread(() -> {
				String result = ConfigPropertyProducer.produceConfigProperty(ip);
				assertEquals("value" + (i % 2 + 1), result);
			});
			threads[i].start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
	}

	private InjectionPoint mockInjectionPoint(String configKey) {
		InjectionPoint ip = mock(InjectionPoint.class);
		Annotation annotation = mock(ConfigProperty.class);
		when(ip.getAnnotated().getAnnotation(ConfigProperty.class)).thenReturn(annotation);
		when(((ConfigProperty) annotation).value()).thenReturn(configKey);
		return ip;
	}

}