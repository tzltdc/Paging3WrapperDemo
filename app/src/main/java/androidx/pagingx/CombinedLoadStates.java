package androidx.pagingx;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;

/**
 * Collection of pagination LoadStates for both a PagingSource, and RemoteMediator. Note: The
 * LoadTypeLoadType.REFRESH always has LoadState.endOfPaginationReached set to false.
 */
@GenerateTypeAdapter
@AutoValue
public abstract class CombinedLoadStates {

  /**
   * Convenience for combined behavior of REFRESH LoadState, which generally defers to mediator if
   * it exists, but if previously was LoadState.Loading, awaits for both source and mediator to
   * become LoadState.NotLoading to ensure the remote load was applied. For use cases that require
   * reacting to LoadState of source and mediator specifically, e.g., showing cached data when
   * network loads via mediator fail, LoadStates exposed via source and mediator should be used
   * directly.
   */
  public abstract LoadState getRefresh();

  /**
   * Convenience for combined behavior of PREPEND LoadState, which generally defers to mediator if
   * it exists, but if previously was LoadState.Loading, awaits for both source and mediator to
   * become LoadState.NotLoading to ensure the remote load was applied. For use cases that require
   * reacting to LoadState of source and mediator specifically, e.g., showing cached data when
   * network loads via mediator fail, LoadStates exposed via source and mediator should be used
   * directly.
   */
  public abstract LoadState getPrepend();

  /**
   * Convenience for combined behavior of APPEND LoadState, which generally defers to mediator if it
   * exists, but if previously was LoadState.Loading, awaits for both source and mediator to become
   * LoadState.NotLoading to ensure the remote load was applied. For use cases that require reacting
   * to LoadState of source and mediator specifically, e.g., showing cached data when network loads
   * via mediator fail, LoadStates exposed via source and mediator should be used directly.
   */
  public abstract LoadState getAppend();

  /** LoadStates corresponding to loads from a PagingSource. */
  public abstract LoadStates getSource();

  /**
   * LoadStates corresponding to loads from a RemoteMediator, or null if RemoteMediator not present.
   */
  @Nullable
  public abstract LoadStates getMediator();

  public static Builder builder() {
    return new AutoValue_CombinedLoadStates.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder refresh(LoadState refresh);

    public abstract Builder prepend(LoadState prepend);

    public abstract Builder append(LoadState append);

    public abstract Builder source(LoadStates source);

    public abstract Builder mediator(@Nullable LoadStates mediator);

    public abstract CombinedLoadStates build();
  }
}
