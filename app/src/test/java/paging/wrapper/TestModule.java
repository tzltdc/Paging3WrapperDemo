package paging.wrapper;

import dagger.Module;
import dagger.Provides;
import paging.wrapper.app.config.AppConfig;

@Module
public abstract class TestModule {

  @Provides
  public static AppConfig appConfig() {
    return AppConfig.DEFAULT_CONFIG;
  }
}
