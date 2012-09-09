package de.knallisworld.spring.worker;

import de.knallisworld.spring.worker.mapping.Job;
import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.task.TaskRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TaskService {

	@Autowired
	private TaskRunner taskRunner;

	@ServiceActivator(inputChannel = "taskRequestChannel", outputChannel = "taskReplyChannel")
	public Result receive(@Payload Job job, @Headers Map<String, Object> headers) {

		Result result;

		try {
			result = taskRunner.run(job.getType(), job.getWorkspace());
		} catch (Exception e) {
			result = new Result();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}

		return result;
	}

}
