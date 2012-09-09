package de.knallisworld.spring.worker.producer;

import de.knallisworld.spring.worker.mapping.Job;
import de.knallisworld.spring.worker.mapping.Result;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.Payload;

import java.util.concurrent.Future;

public interface TaskService {

	@Gateway(requestChannel = "taskRequestChannel", replyChannel = "taskReplyChannel")
	Future<Result> run(@Payload Job job);

}
