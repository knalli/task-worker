package de.knallisworld.spring.worker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ImportResource("META-INF/spring/spring-config.xml")
@ComponentScan(basePackages = {"de.knallisworld.spring.worker"})
public class AppConfig {

	@Value("${mailsender.enabled}")
	private boolean mailSenderEnabled;

	@Value("${mailsender.host}")
	private String mailSenderHost;

	@Value("${mailsender.port}")
	private Integer mailSenderPort;

	@Value("${mailsender.username}")
	private String mailSenderUsername;

	@Value("${mailsender.password}")
	private String mailSenderPassword;

	@Value("${mailsender.properties}")
	private String mailSenderProperties;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		if (mailSenderEnabled) {
			if (mailSenderHost != null) {
				mailSender.setHost(mailSenderHost);
			}
			if (mailSenderPort != null) {
				mailSender.setPort(mailSenderPort);
			}
			if (mailSenderUsername != null) {
				mailSender.setUsername(mailSenderUsername);
			}
			if (mailSenderPassword != null) {
				mailSender.setPassword(mailSenderPassword);
			}
			if (mailSenderProperties != null) {
				Properties props = new Properties();
				try {
					props.load(new FileReader(mailSenderProperties));
				} catch (IOException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
				mailSender.setJavaMailProperties(props);
			}
		}

		return mailSender;
	}

}
