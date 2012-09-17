package de.knallisworld.spring.worker.producer.example;

import de.knallisworld.spring.worker.mapping.Job;
import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.producer.AppConfig;
import de.knallisworld.spring.worker.producer.TaskService;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainMail {

	public static void main(String... args) throws ExecutionException, TimeoutException, InterruptedException, MessagingException, IOException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		TaskService taskService = ctx.getBean(TaskService.class);

		Workspace workspace = new Workspace();

		// We just create an empty, not configured java mail sender...
		JavaMailSender mailSender = new JavaMailSenderImpl();
		// and build an empty mime message. We cannot actually send this message.
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		helper.setTo("knallisworld+github-test-to@googlemail.com");
		helper.setSubject("test");
		helper.setFrom("knallisworld+github-test-from@googlemail.com");
		helper.setText("Test with special chars: !\"§$%&/()=?äölü");

		// The message will be transformed into a encoded string.
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		mimeMessage.writeTo(byteArrayOutputStream);
		workspace.addParam("string", byteArrayOutputStream.toString(Charset.defaultCharset().name()));

		Job job = new Job();
		job.setType("MAIL");
		job.setWorkspace(workspace);

		try {
			final Future<Result> resultFuture = taskService.run(job);
			final Result result = resultFuture.get(10, TimeUnit.SECONDS);

			System.out.println("Result success = " + result.isSuccess());
			System.out.println("Result message = " + result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Wait to exit.
		Thread.sleep(2000);
		ctx.destroy();
	}

}
