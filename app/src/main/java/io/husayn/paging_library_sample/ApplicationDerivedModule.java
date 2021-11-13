package io.husayn.paging_library_sample;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;

@Module
public abstract class ApplicationDerivedModule {

  @AppScope
  public SharedPreferences appSharedPreferences(Application application) {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }
}
