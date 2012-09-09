package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Result;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NullTaskImplTest {
	@Test
	public void testCall() throws Exception {
		final Result result = new NullTaskImpl(null, null).call();
		Assert.assertNull(result, "The result should be always null.");
	}
}
