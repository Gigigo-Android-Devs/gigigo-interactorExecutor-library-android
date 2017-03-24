package com.gigigo.threaddecoratedview.views;

import com.gigigo.threaddecoratedview.views.qualifiers.ThreadDecoratedView;
import java.lang.reflect.Constructor;

public class ViewInjector {

  private static final String DECORATED_CLASS_PREFIX = "Decorated";
  private static final String NULL_CLASS_PREFIX = "Null";

  public static <T> T inject(T viewImplementation, ThreadSpec mainThreadSpec) {
    try {
      Class<?> viewInterface = findThreadDecoratedView(viewImplementation.getClass());
      Class<?> decoratedView = findViewClassWithPrefix(DECORATED_CLASS_PREFIX, viewInterface);
      Constructor<?> constructor = decoratedView.getConstructor(viewInterface, ThreadSpec.class);
      return (T) constructor.newInstance(viewImplementation, mainThreadSpec);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T inject(T view, Class<T> viewClass, ThreadSpec threadSpec) {
    try {
      Class<?> viewInterface = findThreadDecoratedView(view.getClass(), viewClass);
      Class<?> decoratedView = findViewClassWithPrefix(DECORATED_CLASS_PREFIX, viewInterface);
      Constructor<?> constructor = decoratedView.getConstructor(viewInterface, ThreadSpec.class);
      return (T) constructor.newInstance(view, threadSpec);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T nullObjectPatternView(T viewImplementation) {
    try {
      Class<?> viewInterface = findThreadDecoratedView(viewImplementation.getClass());
      Class<?> decoratedView = findViewClassWithPrefix(NULL_CLASS_PREFIX, viewInterface);
      Constructor<?> constructor = decoratedView.getConstructor();
      return (T) constructor.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static Class<?> findThreadDecoratedView(Class<?> implementedViewClass) {
    Class<?>[] viewInterfaces = implementedViewClass.getInterfaces();
    for (Class<?> viewInterface : viewInterfaces) {
      if (viewInterface.isAnnotationPresent(ThreadDecoratedView.class)) {
        return viewInterface;
      }
    }
    if (implementedViewClass.getSuperclass() != null) {
      return findThreadDecoratedView(implementedViewClass.getSuperclass());
    }
    throw new RuntimeException(
        "Cannot find any View annotated with @" + ThreadDecoratedView.class.getName());
  }

  private static <T> Class<?> findThreadDecoratedView(Class<?> aClass, Class<T> viewClass) {
    Class<?>[] viewInterfaces = aClass.getInterfaces();
    for (Class<?> viewInterface : viewInterfaces) {
      String sName = viewInterface.getSimpleName();
      String sName2 = viewClass.getSimpleName();
      boolean isDecorated = viewInterface.isAnnotationPresent(ThreadDecoratedView.class);
      if (viewInterface.isAnnotationPresent(ThreadDecoratedView.class) && viewInterface.getSimpleName().equals(viewClass.getSimpleName())) {
        return viewInterface;
      }
    }
    if (aClass.getSuperclass() != null) {
      return findThreadDecoratedView(aClass.getSuperclass());
    }
    throw new RuntimeException(
        "Cannot find any View annotated with @" + ThreadDecoratedView.class.getName());
  }

  private static Class<?> findViewClassWithPrefix(String classPrefix, Class<?> viewInterface) {
    String packageName = viewInterface.getPackage().getName();
    String className = packageName + "." + classPrefix + viewInterface.getSimpleName();
    try {
      Class<?> decoratedViewClass = Class.forName(className);
      if (decoratedViewClass == Void.TYPE) {
        throw new RuntimeException("Can't find decoratedView class");
      }
      return decoratedViewClass;
    } catch (ClassNotFoundException e) {
      System.err.println(
          String.format("Class %s not found. Please annotate with @%s the class %s", className,
              ThreadDecoratedView.class.getSimpleName(), viewInterface.getCanonicalName()));
    }
    return Void.TYPE;
  }
}
