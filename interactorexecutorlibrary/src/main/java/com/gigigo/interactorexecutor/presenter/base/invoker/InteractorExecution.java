package com.gigigo.interactorexecutor.presenter.base.invoker;


import com.gigigo.interactorexecutor.domain.interactors.Interactor;
import com.gigigo.interactorexecutor.domain.interactors.InteractorError;
import com.gigigo.interactorexecutor.domain.interactors.InteractorResponse;
import java.util.HashMap;
import java.util.Map;

public class InteractorExecution<T> {
  private final Map<Class<? extends InteractorError>, InteractorResult<? extends InteractorError>>
      errors = new HashMap<>(0);
  private final Interactor<InteractorResponse<T>> interactor;
  private InteractorResult<T> interactorResult;
  private int priority;

  public InteractorExecution(Interactor<InteractorResponse<T>> interactor) {
    this.interactor = interactor;
  }

  public InteractorExecution<T> result(InteractorResult<T> interactorResult) {
    this.interactorResult = interactorResult;
    return this;
  }

  public InteractorExecution<T> error(Class<? extends InteractorError> errorClass,
      InteractorResult<? extends InteractorError> interactorError) {
    this.errors.put(errorClass, interactorError);
    return this;
  }

  public InteractorExecution<T> priority(int priority) {
    this.priority = priority;
    return this;
  }

  public Interactor<InteractorResponse<T>> getInteractor() {
    return interactor;
  }

  public InteractorResult<? extends InteractorError> getInteractorErrorResult(
      Class<? extends InteractorError> errorClass) {
    return errors.get(errorClass);
  }

  public InteractorResult<T> getInteractorResult() {
    return interactorResult;
  }

  public void execute(InteractorInvoker interactorInvoker) {
    interactorInvoker.execute(this);
  }

  public int getPriority() {
    return priority;
  }
}
