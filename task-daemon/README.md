# Task Worker for Spring [![Build Status](https://secure.travis-ci.org/knalli/task-worker.png?branch=master)](http://travis-ci.org/knalli/task-worker)

([../](back to home))

The *Task Worker* is a _Spring Integration_ module usable for situations where the actual work should not be done in the "main" application.
For example, a web application should not generate and render a huge pdf document itself because of performance issues with Java Heap and Java PermGen size issues.

This is the *Task Worker Deamon* which works a simple worker slave. Depending on the Spring Integration setup, it is possible to run several worker deamons connected to the same queue.

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
After the maven build is finished, you will found the artifact `target/task-worker-deamonjar`.
Just start it:
```
java -jar target/task-worker-deamon.jar
```

If you want to provide a custom properties file:
```
java -jar target/task-worker-deamon.jar -Ddeamon.config=configuration.properties
```

## How It Works
This module uses the _Spring Framework 3.1_ and _Spring Integration 2.1_ for a own context and the communication with a message browker.

First of all, this builds up a standard _context container_ in which are configured following beans:
* a connection factory (currently RabbitMQ),
* a queue,
* an AMQP inbound gateway which binds messages of the queue to a specific (Spring Integration) channel,
* and finally a service activator which will be invoked for every incoming message.

The service itself delegates the actual job to a dedicated task runner which decides _how_ the task will be done. Currently, there are only following tasks available: FopTaskImpl (FOP), ProcessTaskImpl (PROCESS) and PhantomJsTaskImpl (PHANTOMJS).

In a nutshell:
1. A new message (so called a new job task) will be recieved by Spring Integration via the AMQP gateway.
1. The message will be transformed to an object and the module's service will be invoked.
1. A task depending on the type of job will be created and started. It result will be returned.
1. The result will be used as a reply message and sent back via AMPQ.

## License
Copyright (c) 2012 Jan Philipp
Licensed under the MIT licenses.

## Contributing
In lieu of a formal styleguide, take care to maintain the existing coding style. Add unit tests for any new or changed functionality.