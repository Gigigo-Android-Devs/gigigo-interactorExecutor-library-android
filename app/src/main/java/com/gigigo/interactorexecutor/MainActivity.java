package com.gigigo.interactorexecutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.gigigo.interactorexecutor.domain.interactors.InteractorError;
import com.gigigo.interactorexecutor.domain.invoker.InteractorInvokerImp;
import com.gigigo.interactorexecutor.domain.invoker.InteractorOutputThreadFactory;
import com.gigigo.interactorexecutor.domain.invoker.InteractorPriorityBlockingQueue;
import com.gigigo.interactorexecutor.domain.invoker.LogExceptionHandler;
import com.gigigo.interactorexecutor.domain.invoker.PriorizableThreadPoolExecutor;
import com.gigigo.interactorexecutor.presenter.base.invoker.InteractorExecution;
import com.gigigo.interactorexecutor.presenter.base.invoker.InteractorResult;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = MainActivity.class.getSimpleName();

  private static final int CONCURRENT_INTERACTORS = 3;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView textView = (TextView) findViewById(R.id.textview);

    InteractorPriorityBlockingQueue blockingQueue = new InteractorPriorityBlockingQueue(100);

    InteractorOutputThreadFactory threadFactory =
        new InteractorOutputThreadFactory();

    PriorizableThreadPoolExecutor executorService =
        new PriorizableThreadPoolExecutor(CONCURRENT_INTERACTORS, CONCURRENT_INTERACTORS, 0L,
            TimeUnit.MILLISECONDS, blockingQueue, threadFactory);

    InteractorInvokerImp interactorInvoker = new InteractorInvokerImp(executorService, new LogExceptionHandler());


    textView.setText("Prueba: ");

    InteractorExample interactor = new InteractorExample();

    new InteractorExecution<>(interactor)
    .result(new InteractorResult<Boolean>() {

      @Override public void onResult(Boolean result) {
        Log.d(TAG, "onSuccess: " + result);
      }
    })
    .error(InteractorErrorExample.class, new InteractorResult<InteractorErrorExample>() {
      @Override public void onResult(InteractorErrorExample result) {
        Log.d(TAG, "onError: " + result.toString());
      }
    }).execute(interactorInvoker);


    for (int i=0; i < 9999; i++) {
      System.out.println("Hilo principal:" + i);
    }
  }
}
