package de.knallisworld.spring.worker;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String... args) throws InterruptedException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		ctx.getBean(TaskService.class);
	}

}
