package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

/**
 * This dummy task will return always a message with "PONG: <comma delimited file names>"
 */
public class DummyTaskImpl extends AbstractTaskImpl implements Task {

	public DummyTaskImpl(ApplicationContext ctx, Workspace workspace) {
		super(ctx, workspace);
	}

	@Override
	public Result call() throws Exception {
		Result result = buildResult(true);
		result.setMessage("PONG: " + StringUtils.collectionToCommaDelimitedString(workspace.getFiles().keySet()));
		return result;
	}
}
