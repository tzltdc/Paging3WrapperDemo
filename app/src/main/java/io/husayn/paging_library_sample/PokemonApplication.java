package io.husayn.paging_library_sample;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import io.husayn.paging_library_sample.data.PokemonDBPopulator;

public class PokemonApplication extends Application {

  public static final String KEY_IS_DB_POPULATED = "DB_IS_POPULATED";
  private static Application context;

  @Override
  public void onCreate() {
    super.onCreate();
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
}
