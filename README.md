# Task Worker for Spring

The *Task Worker* is a _Spring Integration_ ([Link](http://www.springsource.org/spring-integration)) module usable for situations where the actual work should not be done in the "main" application.
For example, a web application should not generate and render a huge pdf document itself because of performance issues with Java Heap and Java PermGen size issues.

*Note:* Currently, the Spring Integration configuration supports only a RabbitMQ/AMPQ variant. Feel free to provide an alternative. This could be easily done with Spring profiles.

![Overview](https://raw.githubusercontent.com/knalli/task-worker/master/overview.png)

## Getting Started
The project is split into two single ones: The [daemon](https://github.com/knalli/task-worker/tree/master/task-daemon) and the [producer](https://github.com/knalli/task-worker/tree/master/task-producer).

## License
Copyright (c) 2012 Jan Philipp
Licensed under the MIT licenses.

## Contributing
In lieu of a formal styleguide, take care to maintain the existing coding style. Add unit tests for any new or changed functionality.
