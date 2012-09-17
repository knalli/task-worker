package de.knallisworld.spring.worker.task;

import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.task.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TaskRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);

	@Autowired
	private ApplicationContext ctx;

	public Result run(String type, Workspace workspace) {

		Result result;
		TaskType taskType = null;

		try {
			taskType = TaskType.valueOf(type);
		} catch (IllegalArgumentException e) {
			result = new Result();
			result.setSuccess(false);
			result.setMessage("This type is not valid.");
		}

		if (taskType != null) {
			result = run(taskType, workspace);
		} else {
			result = new Result();
			result.setSuccess(false);
			result.setMessage("This type is not valid.");
		}

		return result;
	}

	public Result run(TaskType type, Workspace workspace) {

		LOGGER.info("TaskRunner.receive " + type);

		Result result;

		if (type != null) {
			try {
				Task task = buildTask(type, workspace);
				if (task != null) {
					result = task.call();
				} else {
					result = new Result();
					result.setMessage("This type is not implemented.");
					result.setSuccess(false);
				}
			} catch (Exception e) {
				result = new Result();
				result.setSuccess(false);
				result.setMessage(e.getMessage());
			}
		} else {
			result = new Result();
			result.setSuccess(false);
			result.setMessage("No type specified");
		}

		return result;
	}

	private Task buildTask(TaskType type, Workspace workspace) {
		Task task = null;
		switch (type) {
			case NULL:
				task = new NullTaskImpl(ctx, workspace);
				break;
			case DUMMY:
				task = new DummyTaskImpl(ctx, workspace);
				break;
			case EXCEPTION:
				task = new ExceptionTaskImpl(ctx, workspace);
				break;
			case FOP:
				task = new FopTaskImpl(ctx, workspace);
				break;
			case PROCESS:
				task = new ProcessTaskImpl(ctx, workspace);
				break;
			case PHANTOMJS:
				task = new PhantomJsTaskImpl(ctx, workspace);
				break;
			case MAIL:
				task = new MailSendTaskImpl(ctx, workspace);
				break;
		}
		return task;
	}

}
