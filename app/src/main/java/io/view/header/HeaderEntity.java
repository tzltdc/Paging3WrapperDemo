package io.view.header;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoOneOf;
import com.google.auto.value.AutoValue;
import io.view.header.HeaderEntity.State;

@AutoOneOf(State.class)
public abstract class HeaderEntity {

  public abstract State state();

  public abstract Loading loading();

  public abstract HeaderError error();

  public abstract Empty empty();

  public static HeaderEntity ofLoading(Loading data) {
    return AutoOneOf_HeaderEntity.loading(data);
  }

  public static HeaderEntity ofError(HeaderError data) {
    return AutoOneOf_HeaderEntity.error(data);
  }

  public static HeaderEntity ofEmpty(Empty data) {
    return AutoOneOf_HeaderEntity.empty(data);
  }

  public enum State {
    LOADING,
    ERROR,
    EMPTY
  }

  @AutoValue
  public abstract static class Loading {

    @Nullable
    public abstract String message();

    public static Loading create(@Nullable String message) {
      return new AutoValue_HeaderEntity_Loading(message);
    }
  }

  @AutoValue
  public abstract static class Empty {

    public abstract String message();

    public static Empty create(String message) {
      return new AutoValue_HeaderEntity_Empty(message);
    }
  }
}
