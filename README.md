# Task Worker for Spring [![Build Status](https://secure.travis-ci.org/knalli/task-worker.png?branch=master)](http://travis-ci.org/knalli/task-worker)

The *Task Worker* is a _Spring Integration_ module usable for situations where the actual work should not be done in the "main" application.
For example, a web application should not generate and render a huge pdf document itself because of performance issues with Java Heap and Java PermGen size issues.

*Note:* Currently, the Spring Integration configuration supports only a RabbitMQ/AMPQ variant. Feel free to provide an alternative. This could be easily done with Spring profiles.

## Getting Started
The project is split into two single ones: The [deamon](task-deamon/README.md) and the [producer](task-producereamon/README.md).

## License
Copyright (c) 2012 Jan Philipp
Licensed under the MIT licenses.

## Contributing
In lieu of a formal styleguide, take care to maintain the existing coding style. Add unit tests for any new or changed functionality.