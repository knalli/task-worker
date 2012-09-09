package de.knallisworld.spring.worker.producer.example;

import de.knallisworld.spring.worker.mapping.Job;
import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.producer.AppConfig;
import de.knallisworld.spring.worker.producer.TaskService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainPdf {

	public static void main(String... args) throws ExecutionException, TimeoutException, InterruptedException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		TaskService taskService = ctx.getBean(TaskService.class);

		File xmlFile = new File(MainPdf.class.getClassLoader().getResource("example1.xml").getFile());
		File xslFile = new File(MainPdf.class.getClassLoader().getResource("example1.xsl").getFile());

		Workspace workspace = new Workspace();
		workspace.addFile("xml", xmlFile.getAbsolutePath());
		workspace.addFile("xsl", xslFile.getAbsolutePath());

		workspace.addParam("userAgent.@baseUrl", "true");

		Job job = new Job();
		job.setType("FOP");
		job.setWorkspace(workspace);

		try {
			final Future<Result> resultFuture = taskService.run(job);
			final Result result = resultFuture.get(10, TimeUnit.SECONDS);

			System.out.println("Result success = " + result.isSuccess());
			System.out.println("Result message = " + result.getMessage());
			final String file = result.getMessage();
			if (file != null) {
				System.out.println("Result file = " + file);
				System.out.println("Result file size = " + new File(file).getTotalSpace());
			}
		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		// Wait to exit.
		Thread.sleep(2000);
		ctx.destroy();
	}

}
