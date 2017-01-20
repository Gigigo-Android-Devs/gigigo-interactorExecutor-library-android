package com.gigigo.interactorexecutor.invoker;

import java.util.concurrent.ThreadFactory;

public class InteractorOutputThreadFactory implements ThreadFactory {
  @Override public Thread newThread(Runnable r) {
    return new Thread(r, "InteractorInvoker" + r.toString());
  }
}
