package io.husayn.paging_library_sample;

import android.content.SharedPreferences;
import javax.inject.Inject;

public class AppStatusRepo {

  private static final String KEY_IS_DB_POPULATED = "DB_IS_POPULATED";

  private final SharedPreferences sharedPreferences;

  @Inject
  public AppStatusRepo(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  public boolean initialized() {
    return sharedPreferences.getBoolean(KEY_IS_DB_POPULATED, false);
  }

  public void markAsInitialized() {
    sharedPreferences.edit().putBoolean(KEY_IS_DB_POPULATED, true);
  }
}
