package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.task.Task;
import org.springframework.context.ApplicationContext;

/**
 * This dummy task will return always "null".
 */
public class NullTaskImpl extends AbstractTaskImpl implements Task {

	public NullTaskImpl(ApplicationContext ctx, Workspace workspace) {
		super(ctx, workspace);
	}

	@Override
	public Result call() throws Exception {
		return null;
	}
}
