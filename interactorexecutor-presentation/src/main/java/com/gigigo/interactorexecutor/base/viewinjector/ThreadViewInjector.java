package com.gigigo.interactorexecutor.base.viewinjector;

import com.gigigo.threaddecoratedview.views.ThreadSpec;
import com.gigigo.threaddecoratedview.views.ViewInjector;

public class ThreadViewInjector implements GenericViewInjector {
  private ThreadSpec threadSpec;

  public ThreadViewInjector(ThreadSpec threadSpec) {
    this.threadSpec = threadSpec;
  }

  @Override public <V> V injectView(V view) {
    return ViewInjector.inject(view, threadSpec);
  }

  @Override public <V> V nullObjectPatternView(V view) {
    return ViewInjector.nullObjectPatternView(view);
  }

  @Override public <V> V injectView(V view, Class<V> viewClass) {
    return ViewInjector.inject(view, viewClass, threadSpec);
  }
}
