# gigigo-interactor-executor-library
Execute asynchronous tasks in other thread in interactor 

## Initialitation
We neew to instance some classes to use this library. The ideal would be to define these classes in a dagger module.

```java
InteractorPriorityBlockingQueue blockingQueue = new InteractorPriorityBlockingQueue(100);

InteractorOutputThreadFactory threadFactory =
        new InteractorOutputThreadFactory();

PriorizableThreadPoolExecutor executorService =
        new PriorizableThreadPoolExecutor(CONCURRENT_INTERACTORS, CONCURRENT_INTERACTORS, 0L,
            TimeUnit.MILLISECONDS, blockingQueue, threadFactory);

InteractorInvokerImp interactorInvoker = new InteractorInvokerImp(executorService, new LogExceptionHandler());
```

## Use 
### Presenter
We have to call our interactor in the presenter like this:
```java
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
```

Interactor response is returned in the result or error callback methods. 

### Domain
Our interactors look like:
```java
public class InteractorExample implements Interactor<InteractorResponse<Boolean>> {

  @Override public InteractorResponse<Boolean> call() throws Exception {
    BusinessObject<Boolean> boBoolean =
        new BusinessObject<>(true, BusinessError.createOKInstance());

    //BusinessObject<Boolean> boBooleanError =
    //    new BusinessObject<>(null, BusinessError.createKoInstance(""));

    if (boBoolean.isSuccess()) {
      return new InteractorResponse<>(true);
    } else {
      return new InteractorResponse<>(new InteractorErrorExample());
    }
  }
}
```

### Interactor Errors 
We have to implements the InteractorError interface to have new classes which define our interactor errror.
```java
public class InteractorErrorExample implements InteractorError {

  @Override public BusinessError getError() {
    return null;
  }
}
```
