# Task Worker for Spring [![Build Status](https://secure.travis-ci.org/knalli/task-worker.png?branch=master)](http://travis-ci.org/knalli/task-worker)

([back to home](../))

The *Task Worker* is a _Spring Integration_ module usable for situations where the actual work should not be done in the "main" application.
For example, a web application should not generate and render a huge pdf document itself because of performance issues with Java Heap and Java PermGen size issues.

This is the *Task Worker Daemon* which works a simple worker slave. Depending on the Spring Integration setup, it is possible to run several worker daemons connected to the same queue.

*Note:* Currently, the Spring Integration configuration supports only a RabbitMQ/AMPQ variant. Feel free to provide an alternative. This could be easily done with Spring profiles.

## Getting Started
In order to get startet, you have to install a suitable message broker. Since this project supports only RabbitMQ at this time, you have to install and configure it on your own (see http://www.rabbitmq.com/).

### Build
Currently, no built-in "make script" is available. Therefore, the recommended way is to use _Maven_ and build the project on your own.

```
mvn package
```
(Later, this will be step 1 of a personal build.)

### Run
After the maven build is finished, you will found the artifact `target/task-worker-daemon.jar`.
Just start it:
```
java -jar target/task-worker-daemon.jar
```

If you want to provide a custom properties file:
```
java -jar target/task-worker-daemon.jar -Dconfig.file=configuration.properties
```

### Properties

The default properties are:
```properties
# RabbitMQ defaults
rabbitmq.host: localhost
rabbitmq.port: 5672
rabbitmq.username: guest
rabbitmq.password: guest
rabbitmq.queue: task.queue
rabbitmq.queue.ttl: 60000
rabbitmq.virtualhost: /

# JavaMailSender defaults
mailsender.enabled: false
mailsender.host:
mailsender.port:
mailsender.username:
mailsender.password:
mailsender.properties:
```

* `rabbitmq.host` defines the host or address of your RabbitMQ instance
* `rabbitmq.port` defines the port of your RabbitMQ instance
* `rabbitmq.username` defines the username of your RabbitMQ instance
* `rabbitmq.password` defines the password of your RabbitMQ instance
* `rabbitmq.queue` defines the queue on which the daemon will listen for new messages. The queue will be created if it does not exist.
* `rabbitmq.queue.ttl` defines the time-to-live of the queue. All incoming and outgoing messages will be flagged. The value is in milliseconds.
* `rabbitmq.virtualhost` defines a virtualhost of your RabbitMQ instance.
* `mailsender.enabled` defines if mailing is active. Read: If and only if this is `true` the following configuration options will be applied to the internal `JavaMailSender`.
* `mailsender.host` defines the (SMTP) host
* `mailsender.port` defines the (SMTP) port
* `mailsender.username` defines the user name
* `mailsender.password` defines the user password
* `mailsender.properties` defines a _file path_ of a properties file used for additional _javamail properties_

## How It Works
This module uses the _Spring Framework 3.1_ and _Spring Integration 2.1_ for a context container and the communication with a message broker.

First of all, this builds up a standard _context container_ in which are configured following beans:
* a connection factory (currently RabbitMQ),
* a queue,
* an AMQP inbound gateway which binds messages of the queue to a specific (Spring Integration) channel,
* and finally a service activator which will be invoked for every incoming message.

The service itself delegates the actual job to a dedicated task runner which decides _how_ the task will be done.
Currently, there are only following tasks available:

1. FopTaskImpl (FOP),
2. ProcessTaskImpl (PROCESS),
3. PhantomJsTaskImpl (PHANTOMJS),
4. MailSendTaskImpl (MAIL)

In a nutshell:

1. A new message (so called a new job task) will be recieved by Spring Integration via the AMQP gateway.
2. The message will be transformed to an object and the module's service will be invoked.
3. A task depending on the type of job will be created and started. It result will be returned.
4. The result will be used as a reply message and sent back via AMPQ.

## License
Copyright (c) 2012 Jan Philipp
Licensed under the MIT licenses.

## Contributing
In lieu of a formal styleguide, take care to maintain the existing coding style. Add unit tests for any new or changed functionality.