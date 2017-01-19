package com.gigigo.interactorexecutor.presenter.base.invoker;

public interface InteractorResult<T> {
  void onResult(T result);
}
