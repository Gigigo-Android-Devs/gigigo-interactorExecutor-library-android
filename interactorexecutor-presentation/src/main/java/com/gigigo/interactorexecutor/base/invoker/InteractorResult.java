package com.gigigo.interactorexecutor.base.invoker;

public interface InteractorResult<T> {
  void onResult(T result);
}
