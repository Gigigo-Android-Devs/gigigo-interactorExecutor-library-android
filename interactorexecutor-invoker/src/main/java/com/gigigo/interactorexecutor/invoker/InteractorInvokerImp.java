package com.gigigo.interactorexecutor.invoker;

import com.gigigo.interactorexecutor.base.invoker.InteractorExecution;
import com.gigigo.interactorexecutor.base.invoker.InteractorInvoker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class InteractorInvokerImp implements InteractorInvoker {

  private ExecutorService executor;
  private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

  public InteractorInvokerImp(ExecutorService executor,
      Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
    this.executor = executor;
    this.uncaughtExceptionHandler = uncaughtExceptionHandler;
  }

  @Override public <T> Future<T> execute(InteractorExecution<T> execution) {
    if (execution.getInteractorResult() != null) {
      return (Future<T>) executor.submit(
          new InteractorExecutionFutureTask<>(execution, uncaughtExceptionHandler));
    } else {
      return executor.submit(new PriorityInteractorDecorator<>(execution));
    }
  }
}