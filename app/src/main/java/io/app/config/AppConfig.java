package io.app.config;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AppConfig {

  public static final AppConfig DEFAULT_CONFIG = builder().initializeDatabase(true).build();

  public abstract boolean initializeDatabase();

  public static Builder builder() {

    return new AutoValue_AppConfig.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder initializeDatabase(boolean initializeDatabase);

    public abstract AppConfig build();
  }
}
