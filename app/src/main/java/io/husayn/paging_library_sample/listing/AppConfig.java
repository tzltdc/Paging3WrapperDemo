package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class AppConfig {

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
