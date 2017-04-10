# gigigo-interactor-executor-library
[![Build Status](https://travis-ci.org/Gigigo-Android-Devs/gigigo-interactorExecutor-library-android.svg?branch=develop)](https://travis-ci.org/Gigigo-Android-Devs/gigigo-interactorExecutor-library-android.svg?branch=develop)
[![](https://jitpack.io/v/Gigigo-Android-Devs/gigigo-interactorExecutor-library-android.svg)](https://jitpack.io/#Gigigo-Android-Devs/gigigo-interactorExecutor-library-android)

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

## Change thread
Change to Ui Thread when interactor task is finished we have to implement the Presenter interface in out presenter class.
```java
public class MainPresenter extends Presenter<MainView>
```

The interface which implements out main activity have to be like this:
```java
@ThreadDecoratedView
public interface MainView {

  @NotDecorated
  void initUi();

  void append(String s);
}
```

Where @ThreadDecoratedView annotation indicates the calls to the method in this interface will be done in a new thread.
The @NotDecorated annotation indicates this method is executed in the same thread which is called.

To return to new thread we have to call
```java
   getView().append("");
```

In our Activity we have to attach the view so we have to call
```java
   presenter.attachView(this);
```
and when the view is attached we have to call in our presenter and initialize all the UI elements
```java
@Override public void onViewAttached() {
    getView().initUi();
    Log.i(TAG + 2, Thread.currentThread().getName());
  }
```

## Gradle dependency
```gradle
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath 'com.android.tools.build:gradle:2.1.2'
    classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5'
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}
```

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```gradle
apply plugin: 'com.neenbedankt.android-apt'

dependencies {
	compile 'com.github.Gigigo-Android-Devs.gigigo-interactor-executor-library:interactorexecutor-invoker:1.0.6'
	apt 'com.github.Gigigo-Android-Devs.gigigo-interactor-executor-library:threaddecoratedview-compiler:1.0.6'
	compile 'com.github.Gigigo-Android-Devs.gigigo-interactor-executor-library:threaddecoratedview-common:1.0.6'
	}
```

### TODO
- Library has to notify user in the middle of a proccess, for example, the remaining time to finish the task.

License
----

Copyright 2016 Gigigo.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
