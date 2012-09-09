package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyTaskImplTest {
	@Test
	public void testCall() throws Exception {
		final Workspace workspace = new Workspace();
		workspace.getFiles().put("abc", "def");
		final Result result = new DummyTaskImpl(null, workspace).call();

		Assert.assertNotNull(result, "The result should be always null.");
		Assert.assertTrue(result.isSuccess(), "The result should be always true.");
		Assert.assertEquals(result.getMessage(), "PONG: abc", "The result message is invalid.");
	}
}
