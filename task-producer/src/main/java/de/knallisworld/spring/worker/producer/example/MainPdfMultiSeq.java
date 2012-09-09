package de.knallisworld.spring.worker.producer.example;

import de.knallisworld.spring.worker.mapping.Job;
import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.producer.AppConfig;
import de.knallisworld.spring.worker.producer.TaskService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StopWatch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MainPdfMultiSeq {

	public static void main(String... args) throws Exception, TimeoutException, InterruptedException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		final TaskService taskService = ctx.getBean(TaskService.class);

		final File xmlFile = new File(MainPdfMultiSeq.class.getClassLoader().getResource("example1.xml").getFile());
		final File xslFile = new File(MainPdfMultiSeq.class.getClassLoader().getResource("example1.xsl").getFile());

		List<Callable<Void>> callables = new ArrayList<Callable<Void>>();
		for (int i = 0; i < 10; i++) {
			callables.add(new Callable<Void>() {
				@Override
				public Void call() {
					Workspace workspace = new Workspace();
					workspace.addFile("xml", xmlFile.getAbsolutePath());
					workspace.addFile("xsl", xslFile.getAbsolutePath());

					workspace.addParam("userAgent.@baseUrl", "true");

					Job job = new Job();
					job.setType("FOP");
					job.setWorkspace(workspace);

					try {
						final Future<Result> resultFuture = taskService.run(job);
						final Result result = resultFuture.get(20, TimeUnit.SECONDS);

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
					return null;
				}
			});
		}

		StopWatch watch = new StopWatch();
		watch.start("Invoking " + callables.size() + " jobs.");
		for (Callable<Void> callable : callables) {
			callable.call();
		}
		watch.stop();

		watch.prettyPrint();

		// Wait to exit.
		Thread.sleep(2000);
		ctx.destroy();
	}

}
