package io.husayn.paging_library_sample;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import io.app.config.AppConfig;
import io.app.config.AppContext;

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
