package com.gigigo.interactorexecutor.domain.invoker;

public class LogExceptionHandler implements Thread.UncaughtExceptionHandler {

  private static final String TAG = "LogExceptionHandler";

  @Override public void uncaughtException(Thread thread, Throwable ex) {
    System.out.println(TAG + "Unhandled Interactor Exception: " + ex);
  }
}