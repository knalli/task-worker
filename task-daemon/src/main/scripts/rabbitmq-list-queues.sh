#!/bin/sh
rabbitmqctl list_queues name durable pid messages_ready messages_unacknowledged messages consumers memory arguments
