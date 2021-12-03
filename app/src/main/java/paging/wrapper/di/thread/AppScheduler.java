package paging.wrapper.di.thread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppScheduler {

  private ThreadConfig threadConfig;
  private final ThreadExecutor threadExecutor;

  public AppScheduler(ThreadConfig threadConfig, ThreadExecutor threadExecutor) {
    this.threadConfig = threadConfig;
    this.threadExecutor = threadExecutor;
  }

  public Scheduler worker() {
    return threadConfig.alwaysOnMainThread() ? ui() : Schedulers.from(threadExecutor);
  }

  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}
