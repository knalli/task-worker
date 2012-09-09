package de.knallisworld.spring.worker.producer.example;

import de.knallisworld.spring.worker.mapping.Job;
import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.producer.AppConfig;
import de.knallisworld.spring.worker.producer.TaskService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainProcess {

	public static void main(String... args) throws ExecutionException, TimeoutException, InterruptedException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		TaskService taskService = ctx.getBean(TaskService.class);

		Workspace workspace = new Workspace();

		workspace.addParam("executable", "ls");
		workspace.addParam("arg1", "-lisa");

		Job job = new Job();
		job.setType("PROCESS");
		job.setWorkspace(workspace);

		try {
			final Future<Result> resultFuture = taskService.run(job);
			final Result result = resultFuture.get(10, TimeUnit.SECONDS);

			System.out.println("Result success = " + result.isSuccess());
			System.out.println("Result message = " + result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		// Wait to exit.
		Thread.sleep(2000);
		ctx.destroy();
	}

}
