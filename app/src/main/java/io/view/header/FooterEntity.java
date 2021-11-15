package io.view.header;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoOneOf;
import com.google.auto.value.AutoValue;
import io.view.header.FooterEntity.State;

@AutoOneOf(State.class)
public abstract class FooterEntity {

  public abstract State state();

  public abstract LoadingMore loadingMore();

  public abstract ErrorData error();

  public abstract NoMore noMore();

  public static FooterEntity ofLoading(LoadingMore data) {
    return AutoOneOf_FooterEntity.loadingMore(data);
  }

  public static FooterEntity ofError(ErrorData data) {
    return AutoOneOf_FooterEntity.error(data);
  }

  public static FooterEntity ofNoMore(NoMore data) {
    return AutoOneOf_FooterEntity.noMore(data);
  }

  public enum State {
    LOADING_MORE,
    ERROR,
    NO_MORE
  }

  @AutoValue
  public abstract static class LoadingMore {

    @Nullable
    public abstract String message();

    public static LoadingMore create(@Nullable String message) {
      return new AutoValue_FooterEntity_LoadingMore(message);
    }
  }

  @AutoValue
  public abstract static class NoMore {

    public abstract String message();

    public static NoMore create(String message) {
      return new AutoValue_FooterEntity_NoMore(message);
    }
  }
}
