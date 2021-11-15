package io.view.header;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoOneOf;
import com.google.auto.value.AutoValue;
import io.view.header.FooterEntity.State;

@AutoOneOf(State.class)
public abstract class FooterEntity {

  public abstract State state();

  public abstract Loading loading();

  public abstract Error error();

  public abstract NoMore noMore();

  public static FooterEntity ofLoading(Loading data) {
    return AutoOneOf_FooterEntity.loading(data);
  }

  public static FooterEntity ofError(Error data) {
    return AutoOneOf_FooterEntity.error(data);
  }

  public static FooterEntity ofNoMore(NoMore data) {
    return AutoOneOf_FooterEntity.noMore(data);
  }

  public enum State {
    LOADING,
    ERROR,
    NO_MORE
  }

  @AutoValue
  public abstract static class Loading {

    @Nullable
    public abstract String message();

    public static Loading create(@Nullable String message) {
      return new AutoValue_FooterEntity_Loading(message);
    }
  }

  @AutoValue
  public abstract static class NoMore {

    public abstract String message();

    public static NoMore create(String message) {
      return new AutoValue_FooterEntity_NoMore(message);
    }
  }

  @AutoValue
  public abstract static class Error {

    public abstract String message();

    @Nullable
    public abstract ErrorAction action();

    public static Error create(String message, ErrorAction action) {
      return new AutoValue_FooterEntity_Error(message, action);
    }

    @AutoValue
    public abstract static class ErrorAction {

      public abstract String text();

      public abstract FooterErrorCallback callback();

      public static ErrorAction create(String text, FooterErrorCallback callback) {
        return new AutoValue_FooterEntity_Error_ErrorAction(text, callback);
      }
    }
  }
}
