package com.gigigo.interactorexecutor.base.invoker;

import java.util.concurrent.Future;

public interface InteractorInvoker {
  <T> void execute(InteractorExecution<T> interactor);
}
