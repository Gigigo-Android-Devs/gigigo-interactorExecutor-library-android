package com.gigigo.interactorexecutor.invoker;

import com.gigigo.interactorexecutor.base.invoker.InteractorExecution;
import com.gigigo.interactorexecutor.base.invoker.InteractorInvoker;
import com.gigigo.interactorexecutor.base.invoker.InteractorResult;
import com.gigigo.interactorexecutor.interactors.InteractorError;
import com.gigigo.interactorexecutor.interactors.InteractorResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class InteractorInvokerImp implements InteractorInvoker {

  private ExecutorService executor;
  private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
  private String description;

  public InteractorInvokerImp(ExecutorService executor,
      Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
    this.executor = executor;
    this.uncaughtExceptionHandler = uncaughtExceptionHandler;

  }

  @Override public <T> void execute(InteractorExecution<T> execution) {
    if (execution.getInteractorResult() != null) {
      executeAndHandleResult(execution);
    } else {
      executeWithoutResult(execution);
    }
  }

  private <T> void executeAndHandleResult(InteractorExecution<T> execution) {
    try {
      this.description = execution.getInteractor().getClass().toString();
      Future<InteractorResponse<T>> responseFuture = executor.submit(execution.getInteractor());
      handleResponse(execution, responseFuture.get());
    } catch (Exception e) {
      handleException(e);
    }
  }

  private <T> void executeWithoutResult(InteractorExecution<T> execution) {
    executor.submit(new PriorityInteractorDecorator<>(execution));
  }

  private <T> void handleResponse(InteractorExecution<T> execution, InteractorResponse<T> response) {
    if (response.hasError()) {
      handleError(execution, response.getError());
    } else {
      handleResult(execution, response.getResult());
    }
  }

  private <T> void handleResult(InteractorExecution<T> execution, T result) {
    execution.getInteractorResult().onResult(result);
  }

  private <T> void handleError(InteractorExecution<T> execution, InteractorError error) {
    InteractorResult errorResult = execution.getInteractorErrorResult(error.getClass());
    if (errorResult != null) {
      errorResult.onResult(error);
    } else {
      unhandledException(
          new RuntimeException("Unhandled handleError action: " + error.getClass().toString()));
    }
  }

  private void handleException(Exception e) {
    Throwable causeException = e.getCause();
    unhandledException(causeException != null ? causeException : e);
  }

  private void unhandledException(Throwable cause) {
    UnhandledInteractorException e =
        new UnhandledInteractorException(description, cause.getClass().getName());
    e.initCause(cause);
    uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e);
  }
}