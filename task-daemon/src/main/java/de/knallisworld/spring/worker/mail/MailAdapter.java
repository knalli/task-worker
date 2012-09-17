package de.knallisworld.spring.worker.mail;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class MailAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailAdapter.class);

	@Autowired
	private JavaMailSender mailSender;

	@PostConstruct
	public void initialize() {

	}

	public MimeMessage sendRawMessage(String raw) throws MailException {
		final InputStream inputStream = IOUtils.toInputStream(raw);
		MimeMessage message = mailSender.createMimeMessage(inputStream);
		mailSender.send(message);
		return message;
	}

}
