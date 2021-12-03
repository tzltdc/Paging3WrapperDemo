package paging.wrapper.di.thread;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import paging.wrapper.app.config.AppConfig;
import paging.wrapper.di.app.AppScope;

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

  @AppScope
  @Provides
  public static ThreadConfig threadConfig(AppConfig appConfig) {
    return appConfig.threadConfig();
  }
}
