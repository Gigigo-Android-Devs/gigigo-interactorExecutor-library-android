package com.gigigo.interactorexecutor.base;

import com.gigigo.interactorexecutor.base.viewinjector.GenericViewInjector;

public abstract class Presenter<V> {
  private GenericViewInjector viewInjector;
  private V view;

  public Presenter(GenericViewInjector viewInjector) {
    this.viewInjector = viewInjector;
  }

  public void attachView(V view) {
    this.view = viewInjector.injectView(view);
    onViewAttached();
  }

  public void detachView(V view) {
    this.view = viewInjector.nullObjectPatternView(view);
  }

  public V getView() {
    return view;
  }

  public abstract void onViewAttached();
}
