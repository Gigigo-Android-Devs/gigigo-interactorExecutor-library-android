package com.gigigo.interactorexecutor;

import com.gigigo.interactorexecutor.interactors.Interactor;
import com.gigigo.interactorexecutor.interactors.InteractorResponse;
import com.gigigo.interactorexecutor.responses.BusinessError;
import com.gigigo.interactorexecutor.responses.BusinessObject;

public class InteractorExample implements Interactor<InteractorResponse<Boolean>> {

  @Override public InteractorResponse<Boolean> call() throws Exception {

    for (int i=0; i < 9999; i++) {
      System.out.println("Hilo asincrono:" + i);
    }

    BusinessObject<Boolean> boBoolean =
        new BusinessObject<>(true, BusinessError.createOKInstance());

    //BusinessObject<Boolean> boBooleanError =
    //    new BusinessObject<>(null, BusinessError.createKoInstance(""));

    if (boBoolean.isSuccess()) {
      return new InteractorResponse<>(true);
    } else {
      return new InteractorResponse<>(new InteractorErrorExample());
    }
  }
}
