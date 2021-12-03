package androidx.pagingx;

import androidx.annotation.NonNull;
import androidx.paging.LoadState.Error;
import androidx.paging.LoadState.Loading;
import androidx.paging.LoadState.NotLoading;

public class CombinedLoadStatesMapper {

  public static CombinedLoadStates wrap(androidx.paging.CombinedLoadStates random) {
    return CombinedLoadStates.builder()
        .refresh(wrap(random.getRefresh()))
        .prepend(wrap(random.getPrepend()))
        .append(wrap(random.getAppend()))
        .source(wrap(random.getSource()))
        .mediator(random.getMediator() == null ? null : wrap(random.getMediator()))
        .build();
  }

  private static LoadStates wrap(androidx.paging.LoadStates loadStates) {
    return LoadStates.builder()
        .refresh(wrap(loadStates.getRefresh()))
        .prepend(wrap(loadStates.getPrepend()))
        .append(wrap(loadStates.getAppend()))
        .build();
  }

  private static LoadState wrap(androidx.paging.LoadState state) {
    if (state instanceof NotLoading) {
      return notLoading(state);
    } else if (state instanceof Loading) {
      return loading(state);
    } else if (state instanceof Error) {
      return error(state);
    } else {
      throw new RuntimeException(
          "Invalid androidx.paging.LoadState:" + state.getClass().getCanonicalName());
    }
  }

  @NonNull
  private static LoadState error(androidx.paging.LoadState state) {
    return LoadState.ofError(
        LoadState.LoadError.create(
            ((Error) state).getError().getMessage(), state.getEndOfPaginationReached()));
  }

  @NonNull
  private static LoadState loading(androidx.paging.LoadState state) {
    return LoadState.ofLoading(LoadState.Loading.create(state.getEndOfPaginationReached()));
  }

  @NonNull
  private static LoadState notLoading(androidx.paging.LoadState state) {
    return LoadState.ofNotLoading(LoadState.NotLoading.create(state.getEndOfPaginationReached()));
  }
}
