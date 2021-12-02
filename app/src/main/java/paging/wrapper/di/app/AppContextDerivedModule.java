package paging.wrapper.di.app;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import paging.wrapper.app.config.AppConfig;
import paging.wrapper.app.config.AppContext;
import paging.wrapper.db.RemoteDataServer;
import paging.wrapper.db.ServerDto;

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

  @AppScope
  @Provides
  public static ServerDto serverDto(AppContext appContext) {
    return ServerDto.create(RemoteDataServer.fromResource(appContext.application().getResources()));
  }
}
