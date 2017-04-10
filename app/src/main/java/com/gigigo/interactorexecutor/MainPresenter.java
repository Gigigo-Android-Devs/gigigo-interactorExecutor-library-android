package com.gigigo.interactorexecutor;

import android.util.Log;
import com.gigigo.interactorexecutor.base.Presenter;
import com.gigigo.interactorexecutor.base.invoker.InteractorExecution;
import com.gigigo.interactorexecutor.base.invoker.InteractorResult;
import com.gigigo.interactorexecutor.base.viewinjector.GenericViewInjector;
import com.gigigo.interactorexecutor.invoker.InteractorInvokerImp;
import com.gigigo.interactorexecutor.invoker.InteractorOutputThreadFactory;
import com.gigigo.interactorexecutor.invoker.InteractorPriorityBlockingQueue;
import com.gigigo.interactorexecutor.invoker.LogExceptionHandler;
import com.gigigo.interactorexecutor.invoker.PriorizableThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainPresenter extends Presenter<MainView> {

  private static final String TAG = "TAG";
  private static final int CONCURRENT_INTERACTORS = 3;

  private InteractorInvokerImp interactorInvoker;

  public MainPresenter(GenericViewInjector viewInjector) {
    super(viewInjector);

    InteractorPriorityBlockingQueue blockingQueue = new InteractorPriorityBlockingQueue(100);

    InteractorOutputThreadFactory threadFactory = new InteractorOutputThreadFactory();

    PriorizableThreadPoolExecutor executorService =
        new PriorizableThreadPoolExecutor(CONCURRENT_INTERACTORS, CONCURRENT_INTERACTORS, 0L,
            TimeUnit.MILLISECONDS, blockingQueue, threadFactory);

    interactorInvoker = new InteractorInvokerImp(executorService, new LogExceptionHandler());
  }

  @Override public void onViewAttached() {
    getView().initUi();
    Log.i(TAG + 2, Thread.currentThread().getName());
  }

  public void load() {
    Log.i(TAG + 4, Thread.currentThread().getName());

    InteractorExample interactor = new InteractorExample();

    new InteractorExecution<>(interactor).result(new InteractorResult<Boolean>() {

      @Override public void onResult(Boolean result) {
        Log.i(TAG + 5, Thread.currentThread().getName());

        Log.d(TAG, "onSuccess: " + result);
        getView().append(" async onSuccess ");
      }
    }).error(InteractorErrorExample.class, new InteractorResult<InteractorErrorExample>() {
      @Override public void onResult(InteractorErrorExample result) {
        Log.i(TAG + 5, Thread.currentThread().getName());

        Log.d(TAG, "onError: " + result.toString());
        getView().append(" async onError ");
      }
    }).execute(interactorInvoker);
  }
}
