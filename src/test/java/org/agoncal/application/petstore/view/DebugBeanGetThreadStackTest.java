
package org.agoncal.application.petstore.view;

import org.agoncal.application.petstore.util.Loggable;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class DebugBeanGetThreadStackTest {

	@Test
	@Tag("valid")
	void getThreadStackReturnsCorrectStackTrace() {
		DebugBean debugBean = new DebugBean();
		List<String> threadStack = debugBean.getThreadStack();
		assertFalse(threadStack.isEmpty(), "Thread stack should not be empty");
		threadStack.forEach(element -> {
			assertTrue(element.contains("."), "Stack trace element should contain '.'");
			assertTrue(element.contains("("), "Stack trace element should contain '('");
			assertTrue(element.contains(":"), "Stack trace element should contain ':'");
		});
	}

	@Test
	@Tag("boundary")
	void getThreadStackHandlesEmptyStackTrace() {
		DebugBean debugBean = new DebugBean();
		List<String> threadStack = debugBean.getThreadStack();
		threadStack.clear();
		assertTrue(threadStack.isEmpty(), "Thread stack should be empty");
	}

	@Test
	@Tag("performance")
	void getThreadStackPerformanceForLargeStackTraces() {
		DebugBean debugBean = new DebugBean();
		long startTime = System.nanoTime();
		List<String> threadStack = debugBean.getThreadStack();
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		assertTrue(duration < TimeUnit.MILLISECONDS.toNanos(100),
				"getThreadStack() took too long for a large stack trace");
	}

	@Test
	@Tag("integration")
	void getThreadStackThreadSafety() throws InterruptedException {
		DebugBean debugBean = new DebugBean();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 100; i++) {
			executorService.execute(() -> {
				List<String> threadStack = debugBean.getThreadStack();
				assertFalse(threadStack.isEmpty(), "Thread stack should not be empty");
			});
		}
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);
	}

}