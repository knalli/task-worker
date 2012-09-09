package de.knallisworld.spring.worker.task.impl;

import org.testng.annotations.Test;

public class ExceptionTaskImplTest {
	@Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Bazinga!")
	public void testCall() throws Exception {
		new ExceptionTaskImpl(null, null).call();
	}
}
