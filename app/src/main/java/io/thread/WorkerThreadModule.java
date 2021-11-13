package io.thread;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.husayn.paging_library_sample.AppScope;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public abstract class WorkerThreadModule {

  @Binds
  @AppScope
  abstract ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor);

  @Provides
  @AppScope
  static Scheduler defaultScheduler(ThreadExecutor threadExecutor) {
    return Schedulers.from(threadExecutor);
  }
}
