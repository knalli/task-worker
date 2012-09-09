package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import org.springframework.util.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class ProcessTaskImplTest {

	@Test
	public void testCallWithValidUnixCommand() throws Exception {

		Workspace workspace = new Workspace();
		workspace.getParams().put("executable", "ls");

		Result result = new ProcessTaskImpl(null, workspace).call();
		Assert.assertTrue(result.isSuccess(), "The result is unexpected.");
		String message = result.getMessage();
		Assert.assertTrue(StringUtils.hasText(message));
	}

	@Test
	public void testCallWithValidUnixCommandAndArgument() throws Exception {

		Workspace workspace = new Workspace();
		workspace.getParams().put("executable", "ls");
		workspace.getParams().put("arg1", "-lisa");

		Result result = new ProcessTaskImpl(null, workspace).call();
		Assert.assertTrue(result.isSuccess(), "The result is unexpected.");
		String message = result.getMessage();
		Assert.assertTrue(StringUtils.hasText(message));
	}

	@Test
	public void testCallWithValidUnixCommandAndMagicArgument() throws Exception {

		Workspace workspace = new Workspace();
		workspace.getParams().put("executable", "echo");
		workspace.getParams().put("arg1@randomFile", "test.png");

		Result result = new ProcessTaskImpl(null, workspace).call();
		Assert.assertTrue(result.isSuccess(), "The result is unexpected.");
		String message = result.getMessage();
		Assert.assertTrue(StringUtils.hasText(message));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "No executable was defined.")
	public void testCallWithoutExecutable() throws Exception {

		Workspace workspace = new Workspace();

		new ProcessTaskImpl(null, workspace).call();
	}

	@Test(expectedExceptions = IOException.class, expectedExceptionsMessageRegExp = "Cannot run program.*")
	public void testCallWithInvalidUnixCommand() throws Exception {

		Workspace workspace = new Workspace();
		workspace.getParams().put("executable", "xxxxxxxxxx");

		new ProcessTaskImpl(null, workspace).call();
	}
}
