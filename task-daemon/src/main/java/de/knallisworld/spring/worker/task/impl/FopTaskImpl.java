package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.fop.FopAdapter;
import de.knallisworld.spring.worker.task.Task;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class FopTaskImpl extends AbstractTaskImpl implements Task {

	public FopTaskImpl(ApplicationContext ctx, Workspace workspace) {
		super(ctx, workspace);
	}

	@Override
	public Result call() throws Exception {
		FopAdapter fopAdapter = ctx.getBean(FopAdapter.class);

		Result result = new Result();

		try {
			final Map<String, String> files = workspace.getFiles();
			final Map<String, String> params = workspace.getParams();

			String xmlFile = files.get("xml");
			String xslFile = files.get("xsl");

			Map<String, String> userAgentParams = filterEntriesByPrefix(params, "userAgent");
			Map<String, String> transformerParams = filterEntriesByPrefix(params, "transformer");

			final String mimeType = params.get("fop.mimeType");
			boolean outputFoOnly = params.containsKey("debugging.outputFoOnly");

			String file = fopAdapter.render(xmlFile, xslFile, mimeType, outputFoOnly, userAgentParams, transformerParams);
			result.setMessage(file);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
}
