package io.husayn.paging_library_sample;

import android.app.Application;
import com.uber.autodispose.ScopeProvider;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import javax.inject.Inject;
import timber.log.ThreadTree;
import timber.log.Timber;

public class PokemonApplication extends DaggerApplication {

  private static Application context;

  @Inject AppWorkerBinder appWorkerBinder;

  @Override
  public void onCreate() {
    initDagger();
    super.onCreate();
    Timber.plant(new ThreadTree());
    init(this);
    intiDB();
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerAppComponent.factory().newMyComponent(this);
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

  private void initDagger() {
    DaggerAppComponent.factory().newMyComponent(this).inject(this);
  }
}
