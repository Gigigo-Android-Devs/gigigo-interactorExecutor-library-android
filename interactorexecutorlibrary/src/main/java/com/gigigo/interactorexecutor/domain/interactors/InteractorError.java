package com.gigigo.interactorexecutor.domain.interactors;

import com.gigigo.interactorexecutor.domain.responses.BusinessError;

public interface InteractorError {
  BusinessError getError();
}
