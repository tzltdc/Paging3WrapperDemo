package paging.wrapper.di.thread;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ThreadConfig {

  public abstract boolean alwaysOnMainThread();

  public static ThreadConfig create(boolean alwaysOnMainThread) {
    return new AutoValue_ThreadConfig(alwaysOnMainThread);
  }
}
