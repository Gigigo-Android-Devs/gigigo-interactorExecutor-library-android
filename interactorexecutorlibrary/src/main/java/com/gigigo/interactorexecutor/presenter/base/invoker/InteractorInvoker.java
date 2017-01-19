package com.gigigo.interactorexecutor.presenter.base.invoker;

import java.util.concurrent.Future;

public interface InteractorInvoker {
  <T> Future<T> execute(InteractorExecution<T> interactor);
}
