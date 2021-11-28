package paging.wrapper.test;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import paging.wrapper.di.app.AppScope;

@Module
public abstract class AppIdleStateStreamModule {

  @AppScope
  @Provides
  public static AppIdleStateStreamImpl footerLoadStateStreamImpl() {
    return new AppIdleStateStreamImpl();
  }

  @AppScope
  @Binds
  public abstract AppIdleStateStream footerLoadStateStream(AppIdleStateStreamImpl impl);

  @AppScope
  @Binds
  public abstract AppIdleStateStreaming footerLoadStateStreaming(AppIdleStateStreamImpl impl);
}
