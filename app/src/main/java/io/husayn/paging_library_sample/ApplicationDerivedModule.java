package io.husayn.paging_library_sample;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationDerivedModule {

  @Provides
  @AppScope
  public SharedPreferences appSharedPreferences(Application application) {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }
}
