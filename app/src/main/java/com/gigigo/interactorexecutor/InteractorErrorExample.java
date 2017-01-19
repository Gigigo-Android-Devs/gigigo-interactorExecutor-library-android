package com.gigigo.interactorexecutor;

import com.gigigo.interactorexecutor.domain.interactors.InteractorError;
import com.gigigo.interactorexecutor.domain.responses.BusinessError;

public class InteractorErrorExample implements InteractorError {

  @Override public BusinessError getError() {
    return null;
  }
}
