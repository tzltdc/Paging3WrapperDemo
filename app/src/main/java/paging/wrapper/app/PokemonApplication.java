package paging.wrapper.app;

import android.app.Application;
import com.uber.autodispose.ScopeProvider;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import javax.inject.Inject;
import paging.wrapper.app.config.AppConfig;
import paging.wrapper.app.config.AppContext;
import paging.wrapper.di.app.DaggerAppComponent;
import timber.log.ThreadTree;
import timber.log.Timber;

public class PokemonApplication extends DaggerApplication {

  private static Application context;

  @Inject AppWorkerBinder appWorkerBinder;

  @Override
  public void onCreate() {
    super.onCreate();
    initLog();
    init(this);
    intiDB();
  }

  private void initLog() {
    Timber.plant(new ThreadTree("resilience"));
    Timber.i("App started:%s", this);
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

    return DaggerAppComponent.factory().create(AppContext.create(AppConfig.DEFAULT_CONFIG, this));
  }

  public static void init(Application context) {
    PokemonApplication.context = context;
  }

  private void intiDB() {
    appWorkerBinder.attach(ScopeProvider.UNBOUND);
  }

  public static Application getContext() {
    return context;
  }
}
