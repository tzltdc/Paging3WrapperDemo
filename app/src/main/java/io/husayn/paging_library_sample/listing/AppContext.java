package io.husayn.paging_library_sample.listing;

import android.app.Application;
import com.google.auto.value.AutoValue;

@AutoValue
abstract class AppContext {

  public abstract AppConfig appConfig();

  public abstract Application application();

  public static AppContext create(AppConfig appConfig, Application application) {
    return new AutoValue_AppContext(appConfig, application);
  }
}
