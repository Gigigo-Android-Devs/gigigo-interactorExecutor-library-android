package com.gigigo.interactorexecutor;

import com.gigigo.threaddecoratedview.views.qualifiers.NotDecorated;
import com.gigigo.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView
public interface MainView {

  @NotDecorated
  void initUi();

  void append(String s);
}
