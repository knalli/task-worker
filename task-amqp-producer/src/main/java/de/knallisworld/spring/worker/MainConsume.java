package de.knallisworld.spring.worker;

import com.rabbitmq.client.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * This examples demonstrates the direct usage of AMQP acting as a consumer.
 */
public class MainConsume {

	private static final String QUEUE_NAME = "task.queue";

	public static void main(String... args) throws IOException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setVirtualHost("/");
		factory.setUsername("guest");
		factory.setPassword("guest");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		//channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println("Consumer Tag: " + consumerTag);
				String message = new String(body);
				System.out.println("message: " + message);
				System.out.println("envelope.deliveryTag: " + envelope.getDeliveryTag());
				System.out.println("envelope.exchange: " + envelope.getExchange());
				System.out.println("envelope.routingKey: " + envelope.getRoutingKey());
				System.out.println("envelope.isReleliver: " + envelope.isRedeliver());
				System.out.println(properties);
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

}
