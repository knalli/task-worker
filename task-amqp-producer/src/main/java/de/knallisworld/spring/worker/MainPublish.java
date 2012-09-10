package de.knallisworld.spring.worker;

import com.rabbitmq.client.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * This examples demonstrates the direct usage of AMQP.
 * <p/>
 * A json formatted message will be sent to the queue and a reply will be consumed.
 */
public class MainPublish {

	private static final String QUEUE_NAME = "task.queue";

	public static void main(String... args) throws IOException, InterruptedException {

		// This is a json formatted message which the daemon will accept.
		String message = FileUtils.readFileToString(new File(MainPublish.class.getClassLoader().getResource("message.json").getFile()));

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setVirtualHost("/");
		factory.setUsername("guest");
		factory.setPassword("guest");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		String replyQueueName = channel.queueDeclare().getQueue();
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(replyQueueName, true, consumer);

		//channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		String response = call(channel, replyQueueName, consumer, message);
		System.out.println("Response: " + response);

		channel.close();
		connection.close();
	}

	public static String call(Channel channel, String replyToChannelname, QueueingConsumer consumer, String message) throws IOException, InterruptedException {
		String corrId = java.util.UUID.randomUUID().toString();
		final AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
		builder.replyTo(replyToChannelname);
		builder.correlationId(corrId).contentType("text/plain").contentEncoding("UTF-8").headers(new HashMap<String, Object>());
		AMQP.BasicProperties props = builder.build();

		channel.basicPublish("", QUEUE_NAME, props, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		while (true) {
			System.out.println("Wait..");
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			System.out.println("Delivery: " + delivery.getProperties());
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				return new String(delivery.getBody());
			}
		}
	}

}
