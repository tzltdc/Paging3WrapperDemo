package io.thread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainScheduler {

  public static Scheduler get() {
    return AndroidSchedulers.mainThread();
  }
}
