package androidx.paging.wrapper;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LoadStates {

  public abstract LoadState refresh();

  public abstract LoadState prepend();

  public abstract LoadState append();

  public static Builder builder() {
    return new AutoValue_LoadStates.Builder();
  }

  public LoadState get(LoadType loadType) {
    switch (loadType) {
      case REFRESH:
        return refresh();
      case PREPEND:
        return prepend();
      case APPEND:
        return append();
      default:
        throw new IllegalStateException("Unknown LoadType:" + loadType);
    }
  }

  public static LoadStates IDLE =
      LoadStates.builder()
          .refresh(LoadState.INCOMPLETE)
          .prepend(LoadState.INCOMPLETE)
          .append(LoadState.INCOMPLETE)
          .build();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder refresh(LoadState refresh);

    public abstract Builder prepend(LoadState prepend);

    public abstract Builder append(LoadState append);

    public abstract LoadStates build();
  }
}
