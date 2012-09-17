package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mail.MailAdapter;
import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

public class MailSendTaskImpl extends AbstractTaskImpl implements Task {

	public MailSendTaskImpl(ApplicationContext ctx, Workspace workspace) {
		super(ctx, workspace);
	}

	@Override
	public Result call() throws Exception {
		MailAdapter adapter = ctx.getBean(MailAdapter.class);

		Result result = new Result();

		try {
			String rawMessage = workspace.getParams().get("string");
			Assert.hasText(rawMessage, "No mail message found, expect a value for key 'raw'.");
			MimeMessage mimeMessage = adapter.sendRawMessage(rawMessage);
			result.setSuccess(true);
			final Address[] recipients = mimeMessage.getRecipients(Message.RecipientType.TO);
			result.setMessage("Recipients: " + StringUtils.collectionToCommaDelimitedString(Arrays.asList(recipients)));
		} catch (MailException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
}
