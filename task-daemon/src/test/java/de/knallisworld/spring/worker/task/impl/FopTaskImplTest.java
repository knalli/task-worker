package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.fop.FopAdapter;
import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class FopTaskImplTest {

	/**
	 * Checks that the fop adapter would be called correctly.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCallWithValidArguments() throws Exception {

		FopAdapter adapter = Mockito.mock(FopAdapter.class);

		ApplicationContext ctx = Mockito.mock(ApplicationContext.class);
		Mockito.when(ctx.getBean(FopAdapter.class)).thenReturn(adapter);

		Workspace workspace = new Workspace();
		workspace.getFiles().put("xml", "example1.xml");
		workspace.getFiles().put("xsl", "example1.xsl");
		workspace.getParams().put("userAgent.author", "Jan");

		String expectedOutputName = "3245678uzdfghjk";

		Map<String, String> userAgentParams = new HashMap<String, String>();
		userAgentParams.put("author", "Jan");
		Map<String, String> transformerParams = new HashMap<String, String>();

		Mockito.when(adapter.render("example1.xml", "example1.xsl", null, false, userAgentParams, transformerParams)).thenReturn(expectedOutputName);
		Result result = new FopTaskImpl(ctx, workspace).call();
		Mockito.verify(adapter).render("example1.xml", "example1.xsl", null, false, userAgentParams, transformerParams);

		Assert.assertTrue(result.isSuccess(), "The result is unexpected.");
		Assert.assertEquals(result.getMessage(), expectedOutputName, "The output file name is not valid.");
	}

}
