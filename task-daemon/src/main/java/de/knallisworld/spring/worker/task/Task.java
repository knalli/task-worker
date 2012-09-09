package de.knallisworld.spring.worker.task;

import de.knallisworld.spring.worker.mapping.Result;

import java.util.concurrent.Callable;

public interface Task extends Callable<Result> {
}
