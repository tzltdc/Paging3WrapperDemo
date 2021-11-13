package io.husayn.paging_library_sample;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import io.husayn.paging_library_sample.data.PokemonDBPopulator;
import javax.inject.Inject;
import timber.log.ThreadTree;
import timber.log.Timber;

public class PokemonApplication extends Application implements HasAndroidInjector {

  @Inject DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

  public static final String KEY_IS_DB_POPULATED = "DB_IS_POPULATED";
  private static Application context;

  @Override
  public void onCreate() {
    initDagger();
    super.onCreate();
    Timber.plant(new ThreadTree());
    init(this);
    intiDB();
  }

  public static void init(Application context) {
    PokemonApplication.context = context;
  }

  private void intiDB() {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    if (!preferences.getBoolean(KEY_IS_DB_POPULATED, true)) {
      PokemonDBPopulator.with(this).populateDB();
      preferences.edit().putBoolean(KEY_IS_DB_POPULATED, true).apply();
    }
  }

  public static Application getContext() {
    return context;
  }

  private void initDagger() {
    DaggerAppComponent.factory().newMyComponent(this).inject(this);
  }

  @Override
  public AndroidInjector<Object> androidInjector() {
    return dispatchingAndroidInjector;
  }
}
