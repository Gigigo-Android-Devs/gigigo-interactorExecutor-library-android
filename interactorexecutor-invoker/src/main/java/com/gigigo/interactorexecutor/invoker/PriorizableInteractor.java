package com.gigigo.interactorexecutor.invoker;

public interface PriorizableInteractor {
  int getPriority();

  String getDescription();
}
