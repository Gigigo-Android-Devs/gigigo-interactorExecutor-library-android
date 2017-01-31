package com.gigigo.interactorexecutor;

import android.os.Handler;
import com.gigigo.threaddecoratedview.views.ThreadSpec;

public class MainThreadSpec implements ThreadSpec
{
  private Handler handler = new Handler();

  @Override
  public void execute(Runnable action) {
    handler.post(action);
  }
}