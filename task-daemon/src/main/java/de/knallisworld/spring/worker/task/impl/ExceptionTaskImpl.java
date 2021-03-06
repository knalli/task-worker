package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * This dummy task will always throw an exception.
 */
public class ExceptionTaskImpl extends AbstractTaskImpl implements Task {

	public ExceptionTaskImpl(ApplicationContext ctx, Workspace workspace) {
		super(ctx, workspace);
	}

	@Override
	public Result call() throws Exception {
		Assert.state(false, "Bazinga!");
		return null;
	}
}
