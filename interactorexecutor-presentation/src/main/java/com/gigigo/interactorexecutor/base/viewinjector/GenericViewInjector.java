package com.gigigo.interactorexecutor.base.viewinjector;

public interface GenericViewInjector {
  <V> V injectView(V view);
  <V> V nullObjectPatternView(V view);
}
