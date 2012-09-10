# Task Worker for Spring [![Build Status](https://secure.travis-ci.org/knalli/task-worker.png?branch=master)](http://travis-ci.org/knalli/task-worker)

([back to home](../))

The *Task Worker* is a _Spring Integration_ module usable for situations where the actual work should not be done in the "main" application.
For example, a web application should not generate and render a huge pdf document itself because of performance issues with Java Heap and Java PermGen size issues.

This is an example of a  *Task Worker Producer* which sends messages to the queue and wait for an answer.

*Note:* Currently, the Spring Integration configuration supports only a RabbitMQ/AMPQ variant. Feel free to provide an alternative. This could be easily done with Spring profiles.

## Getting Started
In order to get startet, you have to install a suitable message broker. Since this project supports only RabbitMQ at this time, you have to install and configure it on your own (see http://www.rabbitmq.com/).

## How It Works
This module uses the _Spring Framework 3.1_ and _Spring Integration 2.1_ for a own context and the communication with a message browker.

First of all, this builds up a standard _context container_ in which are configured following beans:
* a connection factory (currently RabbitMQ),
* a queue,
* an AMQP outbound gateway which binds a (Spring Integration) channel to a messaging queue,
* and finally a service gateway which transforms each invocation into a message.

In a nutshell:

1. Invoking the service letsSpring Integration create a new message onto the channel.
2. The message on this channel will be send via the AMQP gateway right to RabbitMQ.
3. When a corresponding reply message is available via AMQP, the result will be available.

### Asynchronous Process
Because the process is asynchronous the service method `TaskService.run` returns a `Future`. The examples show how you can use timeouts via the Java API.

## License
Copyright (c) 2012 Jan Philipp
Licensed under the MIT licenses.

## Contributing
In lieu of a formal styleguide, take care to maintain the existing coding style. Add unit tests for any new or changed functionality.