package paging.wrapper.app.config;

import com.google.auto.value.AutoValue;
import paging.wrapper.di.thread.ThreadConfig;

@AutoValue
public abstract class AppConfig {

  public static final AppConfig DEFAULT_CONFIG =
      builder().initializeDatabase(false).threadConfig(ThreadConfig.create(false)).build();

  public abstract boolean initializeDatabase();

  public abstract ThreadConfig threadConfig();

  public static Builder builder() {

    return new AutoValue_AppConfig.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder initializeDatabase(boolean initializeDatabase);

    public abstract Builder threadConfig(ThreadConfig threadConfig);

    public abstract AppConfig build();
  }
}
