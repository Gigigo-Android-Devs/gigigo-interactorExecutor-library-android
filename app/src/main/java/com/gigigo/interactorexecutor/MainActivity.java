package com.gigigo.interactorexecutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.gigigo.interactorexecutor.base.viewinjector.GenericViewInjector;
import com.gigigo.interactorexecutor.base.viewinjector.ThreadViewInjector;

public class MainActivity extends AppCompatActivity implements MainView, SecondaryView {

  public static final String TAG = "TAG";

  private TextView textView;

  private MainPresenter presenter;
  private SecondaryPresenter secondaryPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textView = (TextView) findViewById(R.id.textview);

    GenericViewInjector genericViewInjector = new ThreadViewInjector(new MainThreadSpec());
    presenter = new MainPresenter(genericViewInjector);
    presenter.attachView(this, MainView.class);
    secondaryPresenter = new SecondaryPresenter(genericViewInjector);
    secondaryPresenter.attachView(this, SecondaryView.class);

    Log.i(TAG + 1, Thread.currentThread().getName());
  }

  @Override public void initUi() {
    textView.setText("TEST-->");

    Log.i(TAG + 3, Thread.currentThread().getName());

    presenter.load();

    textView.append(" main start ");
    for (int i = 0; i < 9999; i++) {
      System.out.println("Main thread:" + i);
    }
    textView.append(" main end ");
  }

  @Override public void initUiForSecondaryView() {
    textView.setText("TEST-->");

    Log.i(TAG + 3, Thread.currentThread().getName());

    secondaryPresenter.load();

    textView.append(" main start ");
    for (int i = 0; i < 9999; i++) {
      System.out.println("Main thread:" + i);
    }
    textView.append(" main end ");

  }

  @Override public void concat(String s) {
    Log.i(TAG + 6, Thread.currentThread().getName());

    textView.append(s);
  }

  @Override public void append(String s) {
    Log.i(TAG + 6, Thread.currentThread().getName());

    textView.append(s);
  }
}
