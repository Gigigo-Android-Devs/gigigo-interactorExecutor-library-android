package com.gigigo.interactorexecutor;

import com.gigigo.threaddecoratedview.views.qualifiers.NotDecorated;
import com.gigigo.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView
public interface SecondaryView {

  @NotDecorated
  void initUiForSecondaryView();

  void concat(String s);
}
