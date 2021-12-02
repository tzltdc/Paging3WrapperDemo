package androidx.paging.wrapper;

import com.google.auto.value.AutoValue;

/** Collection of pagination LoadStates - refresh, prepend, and append. */
@AutoValue
public abstract class LoadStates {

  /** LoadState corresponding to LoadType.REFRESH loads. */
  public abstract LoadState refresh();

  /** LoadState corresponding to LoadType.PREPEND loads. */
  public abstract LoadState prepend();

  /** LoadState corresponding to LoadType.APPEND loads. */
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
