package com.gigigo.interactorexecutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.gigigo.interactorexecutor.base.invoker.InteractorExecution;
import com.gigigo.interactorexecutor.base.invoker.InteractorResult;
import com.gigigo.interactorexecutor.invoker.InteractorInvokerImp;
import com.gigigo.interactorexecutor.invoker.InteractorOutputThreadFactory;
import com.gigigo.interactorexecutor.invoker.InteractorPriorityBlockingQueue;
import com.gigigo.interactorexecutor.invoker.LogExceptionHandler;
import com.gigigo.interactorexecutor.invoker.PriorizableThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = MainActivity.class.getSimpleName();

  private static final int CONCURRENT_INTERACTORS = 3;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final TextView textView = (TextView) findViewById(R.id.textview);

    InteractorPriorityBlockingQueue blockingQueue = new InteractorPriorityBlockingQueue(100);

    InteractorOutputThreadFactory threadFactory = new InteractorOutputThreadFactory();

    PriorizableThreadPoolExecutor executorService =
        new PriorizableThreadPoolExecutor(CONCURRENT_INTERACTORS, CONCURRENT_INTERACTORS, 0L,
            TimeUnit.MILLISECONDS, blockingQueue, threadFactory);

    InteractorInvokerImp interactorInvoker =
        new InteractorInvokerImp(executorService, new LogExceptionHandler());

    textView.setText("TEST-->");

    InteractorExample interactor = new InteractorExample();

    new InteractorExecution<>(interactor).result(new InteractorResult<Boolean>() {

      @Override public void onResult(Boolean result) {
        Log.d(TAG, "onSuccess: " + result);
        textView.append(" async onSuccess ");
      }
    }).error(InteractorErrorExample.class, new InteractorResult<InteractorErrorExample>() {
      @Override public void onResult(InteractorErrorExample result) {
        Log.d(TAG, "onError: " + result.toString());
        textView.append(" async onError ");
      }
    }).execute(interactorInvoker);

    textView.append(" main start ");
    for (int i = 0; i < 9999; i++) {
      System.out.println("Main thread:" + i);
    }
    textView.append(" main end ");
  }
}
