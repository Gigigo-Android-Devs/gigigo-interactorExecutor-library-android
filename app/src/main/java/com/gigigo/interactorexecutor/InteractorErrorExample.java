package com.gigigo.interactorexecutor;

import com.gigigo.interactorexecutor.interactors.InteractorError;
import com.gigigo.interactorexecutor.responses.BusinessError;

public class InteractorErrorExample implements InteractorError {

  @Override public BusinessError getError() {
    return null;
  }
}
