package paging.wrapper.di.thread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppScheduler {

  private final ThreadExecutor threadExecutor;

  public AppScheduler(ThreadExecutor threadExecutor) {
    this.threadExecutor = threadExecutor;
  }

  public Scheduler worker() {
    return Schedulers.from(threadExecutor);
  }

  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}
