package paging.wrapper.di.app;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import paging.wrapper.app.config.AppConfig;
import paging.wrapper.app.config.AppContext;

@Module
public abstract class AppContextDerivedModule {

  @AppScope
  @Provides
  public static Application application(AppContext appContext) {
    return appContext.application();
  }

  @AppScope
  @Provides
  public static AppConfig appConfig(AppContext appContext) {
    return appContext.appConfig();
  }
}
