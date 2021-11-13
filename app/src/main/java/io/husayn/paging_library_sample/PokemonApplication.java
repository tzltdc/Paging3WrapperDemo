package io.husayn.paging_library_sample;

import android.app.Application;
import com.uber.autodispose.ScopeProvider;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.app.config.AppConfig;
import io.app.config.AppContext;
import javax.inject.Inject;
import timber.log.ThreadTree;
import timber.log.Timber;

public class PokemonApplication extends DaggerApplication {

  private static Application context;

  @Inject AppWorkerBinder appWorkerBinder;

  @Override
  public void onCreate() {
    super.onCreate();
    Timber.plant(new ThreadTree());
    init(this);
    intiDB();
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

    return DaggerAppComponent.factory()
        .create(AppContext.create(AppConfig.DEFAULT_CONFIG, this).application());
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
