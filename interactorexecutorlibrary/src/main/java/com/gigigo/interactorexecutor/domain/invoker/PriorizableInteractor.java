package com.gigigo.interactorexecutor.domain.invoker;

public interface PriorizableInteractor {
  int getPriority();

  String getDescription();
}
