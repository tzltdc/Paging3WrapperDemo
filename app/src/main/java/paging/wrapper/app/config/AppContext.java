package paging.wrapper.app.config;

import android.app.Application;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AppContext {

  public abstract AppConfig appConfig();

  public abstract Application application();

  public static AppContext create(AppConfig appConfig, Application application) {
    return new AutoValue_AppContext(appConfig, application);
  }
}
