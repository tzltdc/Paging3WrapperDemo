package paging.wrapper.test;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import paging.wrapper.di.app.ActivityScope;

@Module
public abstract class AppIdleStateStreamModule {

  @ActivityScope
  @Provides
  public static AppIdleStateStreamImpl appIdleStateStreamImpl() {
    return new AppIdleStateStreamImpl();
  }

  @ActivityScope
  @Binds
  public abstract AppIdleStateStream appIdleStateStream(AppIdleStateStreamImpl impl);

  @ActivityScope
  @Binds
  public abstract AppIdleStateStreaming appIdleStateStreaming(AppIdleStateStreamImpl impl);
}
