package com.gigigo.interactorexecutor.domain.invoker;

import com.gigigo.interactorexecutor.presenter.base.invoker.InteractorExecution;
import java.util.concurrent.Callable;

public class PriorityInteractorDecorator<T> implements Callable<T>, PriorizableInteractor {

  private InteractorExecution<T> execution;

  public PriorityInteractorDecorator(InteractorExecution<T> execution) {
    this.execution = execution;
  }

  @Override public T call() throws Exception {
    return (T) execution.getInteractor().call();
  }

  @Override public int getPriority() {
    return execution.getPriority();
  }

  @Override public String getDescription() {
    return execution.getClass().toString();
  }
}